(function (root, factory) {
  const api = factory();
  if (typeof module === "object" && module.exports) module.exports = api;
  else root.MazeDomain = api;
})(typeof globalThis !== "undefined" ? globalThis : this, function () {
  "use strict";
  const MAX_AMOUNT = 999999999;
  const LIMITS = Object.freeze({
    claims: 400,
    budget: 500,
    documents: 100,
    tasks: 200,
  });
  const FREQUENCIES = Object.freeze({
    monthly: [1, 1],
    weekly: [52, 12],
    fortnightly: [26, 12],
    quarterly: [1, 3],
    yearly: [1, 12],
  });
  const fail = (message) => {
    throw new Error(message);
  };
  const clone = (obj) => JSON.parse(JSON.stringify(obj));
  const text = (v, max = 400, optional = true) =>
    typeof v === "string" &&
    v.length <= max &&
    (optional || v.trim().length > 0) &&
    !/[\u0000-\u0008\u000b\u000c\u000e-\u001f]/u.test(v);
  function dateValid(v, optional = true) {
    if (v === "" && optional) return true;
    if (
      typeof v !== "string" ||
      !/^\d{4}-\d{2}-\d{2}$/.test(v) ||
      v < "1900-01-01" ||
      v > "2200-12-31"
    )
      return false;
    const d = new Date(v + "T12:00:00Z");
    return !Number.isNaN(d.valueOf()) && d.toISOString().slice(0, 10) === v;
  }
  function parseMoney(input, locale = "de") {
    if (typeof input !== "string") fail("money");
    const v = input.trim();
    if (v === "") return null;
    if (v.length > 20 || !["de", "en"].includes(locale)) fail("money");
    const pattern =
      locale === "de"
        ? /^(?:\d+|\d{1,3}(?:\.\d{3})+)(?:,\d{1,2})?$/
        : /^(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d{1,2})?$/;
    if (!pattern.test(v)) fail("money");
    const normalized =
      locale === "de"
        ? v.replaceAll(".", "").replace(",", ".")
        : v.replaceAll(",", "");
    const [whole, fraction = ""] = normalized.split(".");
    const cents = BigInt(whole) * 100n + BigInt(fraction.padEnd(2, "0"));
    if (cents > BigInt(MAX_AMOUNT)) fail("money-limit");
    return Number(cents);
  }
  function moneyInput(cents, locale = "de") {
    if (cents === null) return "";
    if (!Number.isInteger(cents) || cents < 0 || cents > MAX_AMOUNT)
      fail("money");
    return (
      Math.floor(cents / 100) +
      (locale === "de" ? "," : ".") +
      String(cents % 100).padStart(2, "0")
    );
  }
  function formatMoney(cents, locale = "de") {
    return new Intl.NumberFormat(locale === "de" ? "de-DE" : "en-IE", {
      style: "currency",
      currency: "EUR",
    }).format(cents / 100);
  }
  function monthly(cents, frequency) {
    if (cents === null) return null;
    if (
      !Number.isInteger(cents) ||
      cents < 0 ||
      cents > MAX_AMOUNT ||
      !FREQUENCIES[frequency]
    )
      fail("frequency");
    const [n, d] = FREQUENCIES[frequency];
    return Number(
      (BigInt(cents) * BigInt(n) * 2n + BigInt(d)) / (BigInt(d) * 2n),
    );
  }
  function onlyKeys(obj, keys) {
    return (
      obj &&
      typeof obj === "object" &&
      !Array.isArray(obj) &&
      Object.keys(obj).every((k) => keys.includes(k)) &&
      keys.every((k) => Object.hasOwn(obj, k))
    );
  }
  function validate(state) {
    if (
      !onlyKeys(state, [
        "schema",
        "locale",
        "alias",
        "appointment",
        "claims",
        "budget",
        "documents",
        "tasks",
      ]) ||
      state.schema !== 1 ||
      !["de", "en"].includes(state.locale) ||
      !text(state.alias, 80, false) ||
      !dateValid(state.appointment)
    )
      fail("workspace");
    const allIds = new Set();
    for (const [key, limit] of Object.entries(LIMITS)) {
      if (!Array.isArray(state[key]) || state[key].length > limit)
        fail("record-limit");
      for (const item of state[key]) {
        if (
          !item ||
          !text(item.id, 80, false) ||
          !/^[a-zA-Z0-9_-]+$/.test(item.id) ||
          allIds.has(item.id)
        )
          fail("record-id");
        allIds.add(item.id);
      }
    }
    const amount = (v) =>
      v === null || (Number.isInteger(v) && v >= 0 && v <= MAX_AMOUNT);
    for (const c of state.claims) {
      if (
        !onlyKeys(c, [
          "id",
          "creditor",
          "collector",
          "reference",
          "amount",
          "certainty",
          "asOf",
          "source",
          "disputed",
          "repeatOf",
          "notes",
        ]) ||
        !text(c.creditor, 160, false) ||
        !text(c.collector, 160) ||
        !text(c.reference, 100) ||
        !amount(c.amount) ||
        !["confirmed", "estimated", "unknown"].includes(c.certainty) ||
        (c.amount === null) !== (c.certainty === "unknown") ||
        !dateValid(c.asOf) ||
        !text(c.source, 200) ||
        typeof c.disputed !== "boolean" ||
        !text(c.repeatOf, 80) ||
        !text(c.notes, 1500)
      )
        fail("claim");
      if (c.repeatOf) {
        const parent = state.claims.find((x) => x.id === c.repeatOf);
        if (!parent || parent.id === c.id || parent.repeatOf)
          fail("duplicate-link");
      }
    }
    for (const b of state.budget) {
      if (
        !onlyKeys(b, [
          "id",
          "label",
          "kind",
          "amount",
          "frequency",
          "certainty",
          "source",
        ]) ||
        !text(b.label, 120, false) ||
        !["income", "expense"].includes(b.kind) ||
        !amount(b.amount) ||
        !Object.hasOwn(FREQUENCIES, b.frequency) ||
        !["confirmed", "estimated", "unknown", "pending"].includes(
          b.certainty,
        ) ||
        (b.amount === null) !== (b.certainty === "unknown") ||
        (b.certainty === "pending" && b.kind !== "income") ||
        !text(b.source, 200)
      )
        fail("budget");
    }
    for (const d of state.documents)
      if (
        !onlyKeys(d, ["id", "title", "status", "notes"]) ||
        !text(d.title, 180, false) ||
        !["missing", "requested", "ready", "notneeded"].includes(d.status) ||
        !text(d.notes, 1000)
      )
        fail("document");
    for (const t of state.tasks)
      if (
        !onlyKeys(t, ["id", "title", "date", "done", "notes"]) ||
        !text(t.title, 180, false) ||
        !dateValid(t.date) ||
        typeof t.done !== "boolean" ||
        !text(t.notes, 1000)
      )
        fail("task");
    return clone(state);
  }
  const normalize = (s) =>
    s
      .normalize("NFKC")
      .toLowerCase()
      .replace(/[^\p{L}\p{N}]/gu, "");
  function potentialDuplicates(claims) {
    const pairs = [];
    for (let i = 0; i < claims.length; i++)
      for (let j = i + 1; j < claims.length; j++) {
        const a = claims[i],
          b = claims[j];
        if (a.repeatOf || b.repeatOf) continue;
        const ref = normalize(a.reference);
        const sameParties =
          normalize(a.creditor) === normalize(b.creditor) ||
          (a.collector && normalize(a.collector) === normalize(b.creditor)) ||
          (b.collector && normalize(b.collector) === normalize(a.creditor));
        if (
          (ref.length >= 4 && ref === normalize(b.reference)) ||
          (sameParties && a.amount !== null && a.amount === b.amount)
        )
          pairs.push([a.id, b.id]);
      }
    return pairs;
  }
  function summarize(state) {
    validate(state);
    const canonical = state.claims.filter((c) => !c.repeatOf);
    const lines = state.budget.filter((b) => b.certainty !== "pending");
    const sum = (kind) =>
      lines
        .filter((b) => b.kind === kind && b.amount !== null)
        .reduce((n, b) => n + monthly(b.amount, b.frequency), 0);
    return {
      claimsTotal: canonical.reduce((n, c) => n + (c.amount ?? 0), 0),
      claimCount: canonical.length,
      unknownClaims: canonical.filter((c) => c.amount === null).length,
      disputedClaims: canonical.filter((c) => c.disputed).length,
      estimatedClaims: canonical.filter((c) => c.certainty === "estimated")
        .length,
      repeatedClaims: state.claims.length - canonical.length,
      undatedClaims: canonical.filter((c) => !c.asOf).length,
      unsourcedClaims: canonical.filter((c) => !c.source).length,
      possibleDuplicates: potentialDuplicates(state.claims),
      income: sum("income"),
      expenses: sum("expense"),
      difference: sum("income") - sum("expense"),
      unknownBudget: lines.filter((b) => b.amount === null).length,
      estimatedBudget: lines.filter((b) => b.certainty === "estimated").length,
      pendingIncome: state.budget
        .filter((b) => b.certainty === "pending")
        .reduce((n, b) => n + monthly(b.amount, b.frequency), 0),
      readyDocuments: state.documents.filter((d) => d.status === "ready")
        .length,
      missingDocuments: state.documents.filter((d) =>
        ["missing", "requested"].includes(d.status),
      ).length,
      openTasks: state.tasks.filter((t) => !t.done).length,
    };
  }
  function remove(state, collection, id) {
    const next = validate(state);
    if (!Object.hasOwn(LIMITS, collection)) fail("collection");
    if (collection === "claims" && next.claims.some((c) => c.repeatOf === id))
      fail("linked-records");
    const index = next[collection].findIndex((x) => x.id === id);
    if (index < 0) fail("not-found");
    next[collection].splice(index, 1);
    return validate(next);
  }
  function upsert(state, collection, item) {
    const next = clone(state);
    if (!Object.hasOwn(LIMITS, collection)) fail("collection");
    const i = next[collection].findIndex((x) => x.id === item.id);
    if (i < 0) next[collection].push(clone(item));
    else next[collection][i] = clone(item);
    return validate(next);
  }
  function empty(locale = "de") {
    const de = locale === "de";
    return validate({
      schema: 1,
      locale,
      alias: de ? "Meine Vorbereitung" : "My preparation",
      appointment: "",
      claims: [],
      budget: [],
      documents: [
        ["doc-income", de ? "Einkommensnachweise" : "Income evidence"],
        ["doc-statements", de ? "Kontoauszüge" : "Account statements"],
        [
          "doc-letters",
          de ? "Aktuelle Gläubigerschreiben" : "Recent creditor letters",
        ],
        [
          "doc-contracts",
          de ? "Kredit- und Mietverträge" : "Credit and tenancy agreements",
        ],
        [
          "doc-official",
          de
            ? "Gerichtliche Schreiben, falls vorhanden"
            : "Official notices, if any",
        ],
      ].map(([id, title]) => ({ id, title, status: "missing", notes: "" })),
      tasks: [],
    });
  }
  function demo(locale = "de") {
    const s = empty(locale),
      de = locale === "de";
    s.alias = de ? "Beispielfall · Alex" : "Example case · Alex";
    s.appointment = "";
    const c = (id, creditor, amount, certainty, other = {}) => ({
      id,
      creditor,
      collector: "",
      reference: "",
      amount,
      certainty,
      asOf: "2026-10-01",
      source: de
        ? "Beispielbrief vom 01.10.2026"
        : "Example letter, 1 October 2026",
      disputed: false,
      repeatOf: "",
      notes: "",
      ...other,
    });
    s.claims = [
      c("c-energy", "Beispiel Strom GmbH", 120000, "confirmed", {
        reference: "DEMO-1042",
      }),
      c("c-phone", "Beispiel Mobilfunk", 78050, "estimated", {
        collector: "Beispiel Inkasso",
        reference: "DEMO-2087",
        disputed: true,
        notes: de
          ? "Gebühren beim Termin besprechen."
          : "Discuss fees at the appointment.",
      }),
      c("c-shop", "Beispiel Versand", null, "unknown", {
        asOf: "",
        source: "",
        notes: de ? "Aktuellen Betrag erfragen." : "Ask for a current amount.",
      }),
      c("c-repeat", "Beispiel Mobilfunk", 78050, "estimated", {
        collector: "Beispiel Inkasso",
        reference: "DEMO-2087",
        repeatOf: "c-phone",
        notes: de
          ? "Zweiter Brief zur gleichen Forderung."
          : "Second letter about the same claim.",
      }),
    ];
    const b = (
      id,
      label,
      kind,
      amount,
      frequency = "monthly",
      certainty = "confirmed",
    ) => ({
      id,
      label,
      kind,
      amount,
      frequency,
      certainty,
      source: de ? "Fiktive Beispieldaten" : "Fictional sample data",
    });
    s.budget = [
      b("b-income", de ? "Nettoeinkommen" : "Net income", "income", 205000),
      b(
        "b-refund",
        de ? "Erwartete Erstattung" : "Pending refund",
        "income",
        35000,
        "monthly",
        "pending",
      ),
      b(
        "b-rent",
        de ? "Warmmiete" : "Rent and service charges",
        "expense",
        85000,
      ),
      b(
        "b-food",
        de ? "Lebensmittel" : "Groceries",
        "expense",
        11000,
        "weekly",
        "estimated",
      ),
      b("b-energy", de ? "Stromabschlag" : "Electricity", "expense", 9500),
      b("b-travel", de ? "ÖPNV" : "Public transport", "expense", 5800),
      b(
        "b-insurance",
        de ? "Jahresversicherung" : "Annual insurance",
        "expense",
        12000,
        "yearly",
      ),
    ];
    s.documents[0].status = "ready";
    s.documents[2].status = "ready";
    s.documents[4].status = "notneeded";
    s.tasks = [
      {
        id: "t-amount",
        title: de
          ? "Aktuellen Betrag bei Beispiel Versand anfragen"
          : "Request a current amount from Beispiel Versand",
        date: "",
        done: false,
        notes: de
          ? "Nur eine persönliche Notiz; nichts wird versendet."
          : "Personal reminder only; nothing is sent.",
      },
      {
        id: "t-question",
        title: de
          ? "Fragen zu Inkassogebühren notieren"
          : "Write down questions about collection fees",
        date: "",
        done: false,
        notes: "",
      },
    ];
    return validate(s);
  }
  function importWorkspace(raw) {
    if (
      typeof raw !== "string" ||
      new TextEncoder().encode(raw).length > 2000000
    )
      fail("import-size");
    return validate(JSON.parse(raw));
  }
  return {
    MAX_AMOUNT,
    LIMITS,
    FREQUENCIES,
    clone,
    dateValid,
    parseMoney,
    moneyInput,
    formatMoney,
    monthly,
    validate,
    summarize,
    potentialDuplicates,
    upsert,
    remove,
    empty,
    demo,
    importWorkspace,
  };
});
