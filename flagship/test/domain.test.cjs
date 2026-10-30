"use strict";
const { test } = require("node:test"),
  assert = require("node:assert/strict"),
  D = require("../app/domain.js"),
  R = require("../app/report.js");
test("strict locale money parsing, decimal cents and unknowns", () => {
  assert.equal(D.parseMoney("1.200,50", "de"), 120050);
  assert.equal(D.parseMoney("1,200.50", "en"), 120050);
  assert.equal(D.parseMoney("0,01", "de"), 1);
  assert.equal(D.parseMoney("1200", "de"), 120000);
  assert.equal(D.parseMoney("", "de"), null);
  assert.equal(D.parseMoney("0", "en"), 0);
  for (const v of [
    "NaN",
    "Infinity",
    "1e3",
    "-10",
    "1.2",
    "12.34",
    "1,234",
    "0,001",
    "12,00 EUR",
    "9.999.999,999",
    "10.000.000,00",
    "1 200,50",
  ])
    assert.throws(() => D.parseMoney(v, "de"), v);
  assert.equal(D.parseMoney("9.999.999,99", "de"), 999999999);
  assert.throws(() => D.parseMoney("0x10", "en"));
});
test("independent monthly conversion examples and half-up rounding", () => {
  assert.equal(D.monthly(12000, "yearly"), 1000);
  assert.equal(D.monthly(9000, "quarterly"), 3000);
  assert.equal(D.monthly(12000, "weekly"), 52000);
  assert.equal(D.monthly(12000, "fortnightly"), 26000);
  assert.equal(D.monthly(1, "yearly"), 0);
  assert.equal(D.monthly(6, "yearly"), 1);
  assert.equal(D.monthly(11000, "weekly"), 47667);
  assert.equal(D.monthly(null, "monthly"), null);
  for (const f of Object.keys(D.FREQUENCIES))
    for (let n = 0; n < 1000; n += 7) {
      const [a, b] = D.FREQUENCIES[f];
      assert.equal(D.monthly(n, f), Math.floor((n * a) / b + 0.5));
    }
});
test("sample totals do not count repeats, unknowns or pending refunds as available cash", () => {
  const s = D.demo("en"),
    a = D.summarize(s);
  assert.equal(a.claimsTotal, 198050);
  assert.equal(a.claimCount, 3);
  assert.equal(a.unknownClaims, 1);
  assert.equal(a.repeatedClaims, 1);
  assert.equal(a.disputedClaims, 1);
  assert.equal(a.expenses, 148967);
  assert.equal(a.income, 205000);
  assert.equal(a.difference, 56033);
  assert.equal(a.pendingIncome, 35000);
  s.budget.push({
    id: "unknown-income",
    label: "Unknown income",
    kind: "income",
    amount: null,
    frequency: "monthly",
    certainty: "unknown",
    source: "",
  });
  assert.equal(D.summarize(s).unknownBudget, 1);
  assert.equal(D.summarize(s).income, 205000);
});
test("duplicate candidates warn without merging or reducing the sum", () => {
  const s = D.demo(),
    c = D.clone(s.claims[0]);
  c.id = "extra";
  s.claims.push(c);
  assert.deepEqual(D.potentialDuplicates(s.claims), [["c-energy", "extra"]]);
  assert.equal(D.summarize(s).claimsTotal, 318050);
  c.repeatOf = "c-energy";
  assert.equal(D.summarize(s).claimsTotal, 198050);
});
test("link integrity rejects cycles, missing parents and parent deletion", () => {
  const s = D.demo();
  assert.throws(() => D.remove(s, "claims", "c-phone"), /linked-records/);
  const a = D.clone(s);
  a.claims[1].repeatOf = "c-repeat";
  assert.throws(() => D.validate(a), /duplicate-link/);
  a.claims[1].repeatOf = "missing";
  assert.throws(() => D.validate(a), /duplicate-link/);
  const unlinked = D.upsert(s, "claims", { ...s.claims[3], repeatOf: "" });
  assert.equal(D.remove(unlinked, "claims", "c-phone").claims.length, 3);
  assert.equal(s.claims.length, 4);
});
test("schema rejects unsafe types, extra fields, duplicate IDs and silent unknown/zero changes", () => {
  const s = D.demo();
  for (const mutate of [
    (x) => (x.claims[0].amount = NaN),
    (x) => (x.claims[0].amount = -1),
    (x) => (x.claims[2].amount = 0),
    (x) => (x.budget[0].frequency = "daily"),
    (x) => (x.claims[0].admin = true),
    (x) => (x.tasks[0].done = "false"),
    (x) => (x.documents[0].id = x.claims[0].id),
    (x) => (x.appointment = "2026-02-30"),
    (x) => (x.claims[0].repeatOf = "c-energy"),
  ]) {
    const n = D.clone(s);
    mutate(n);
    assert.throws(() => D.validate(n));
  }
  assert.throws(() => D.importWorkspace('{"__proto__":{"admin":true}}'));
  assert.equal({}.admin, undefined);
  assert.throws(() => D.importWorkspace("x".repeat(2000001)));
});
test("date validation and blank state", () => {
  assert.equal(D.dateValid("2024-02-29"), true);
  for (const d of [
    "2026-02-29",
    "2026-13-01",
    "2026-1-1",
    "2026-04-31",
    "0001-01-01",
  ])
    assert.equal(D.dateValid(d), false, d);
  assert.equal(D.dateValid(""), true);
  const a = D.summarize(D.empty());
  assert.equal(a.claimsTotal, 0);
  assert.equal(a.claimCount, 0);
  assert.equal(a.income, 0);
});
test("report escapes hostile content and retains uncertainty, links and conversion boundaries", () => {
  const s = D.demo("en");
  s.alias = "<img src=x onerror=alert(1)>";
  s.claims[0].notes = "</td><script>alert(1)</script>";
  const html = R.document(s);
  assert.ok(!html.includes("<script>"));
  assert.ok(!html.includes("<img"));
  assert.ok(html.includes("&lt;script&gt;"));
  assert.ok(html.includes("1 amount unknown"));
  assert.ok(html.includes("Repeated record of"));
  assert.ok(html.includes("Pending · excluded"));
  assert.ok(html.includes("1,980.50"));
  assert.ok(html.includes("560.33"));
  assert.ok(html.includes("52÷12"));
  assert.ok(html.includes("default-src 'none'"));
});
test("400-record maximum is accepted and overflow rejected; output remains exact", () => {
  const s = D.empty();
  const base = D.demo().claims[0];
  s.claims = Array.from({ length: 400 }, (_, i) => ({
    ...base,
    id: "large-" + i,
    reference: "UNIQUE-" + i,
    amount: 999999999,
  }));
  assert.equal(D.summarize(s).claimsTotal, 399999999600);
  s.claims.push({ ...base, id: "overflow" });
  assert.throws(() => D.validate(s), /record-limit/);
});
test("upsert and remove return independent validated objects", () => {
  const s = D.empty();
  const c = D.demo().claims[0];
  const n = D.upsert(s, "claims", c);
  assert.equal(s.claims.length, 0);
  n.claims[0].creditor = "Changed";
  assert.notEqual(c.creditor, "Changed");
  assert.throws(() => D.remove(n, "budget", "missing"), /not-found/);
});
