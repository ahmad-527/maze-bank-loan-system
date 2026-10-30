"use strict";
const { test } = require("node:test"),
  assert = require("node:assert/strict"),
  V = require("../app/vault.js"),
  D = require("../app/domain.js");
const pass = "Synthetic passphrase 2026";
test("encrypted round trip has no plaintext and independent random salt/nonce", async () => {
  const plain = JSON.stringify(D.demo("en")),
    a = await V.create(plain, pass),
    b = await V.create(plain, pass);
  assert.ok(!a.raw.includes("Beispiel"));
  assert.ok(!a.raw.includes("Net income"));
  assert.notEqual(a.raw, b.raw);
  assert.notEqual(JSON.parse(a.raw).salt, JSON.parse(b.raw).salt);
  assert.equal((await V.open(a.raw, pass)).plaintext, plain);
  assert.equal(a.key.extractable, false);
  const c = await V.seal(plain, a.key, a.salt);
  assert.notEqual(JSON.parse(a.raw).iv, JSON.parse(c).iv);
  assert.equal((await V.open(c, pass)).plaintext, plain);
});
test("wrong passphrase, changed revision, salt and ciphertext fail authentication", async () => {
  const a = await V.create(JSON.stringify(D.empty()), pass);
  await assert.rejects(V.open(a.raw, "Wrong passphrase 2026"));
  for (const key of ["revision", "ciphertext", "salt"]) {
    const v = JSON.parse(a.raw);
    v[key] = (v[key][0] === "a" ? "b" : "a") + v[key].slice(1);
    await assert.rejects(V.open(JSON.stringify(v), pass));
  }
});
test("format/size/KDF bounds are rejected before derivation", async () => {
  const a = await V.create("{}", pass);
  for (const update of [
    { iterations: 1 },
    { iterations: 999999999 },
    { version: 2 },
    { iv: "AAAA" },
    { salt: "AAAA" },
    { ciphertext: "==" },
    { format: "other" },
    { revision: "<script>" },
    { extra: true },
  ])
    assert.throws(() =>
      V.inspect(JSON.stringify({ ...JSON.parse(a.raw), ...update })),
    );
  assert.throws(() => V.inspect("x".repeat(V.MAX_BYTES + 1)));
  await assert.rejects(V.create("{}", "short"));
});
test("stale save and storage failure keep previous vault bytes", async () => {
  const a = await V.create("{}", pass),
    b = await V.seal('{"updated":true}', a.key, a.salt),
    store = new Map(),
    storage = {
      getItem: (k) => store.get(k) ?? null,
      setItem: (k, v) => store.set(k, v),
    };
  const revision = V.save(storage, "vault", a.raw, null);
  assert.equal(revision, JSON.parse(a.raw).revision);
  assert.throws(() => V.save(storage, "vault", b, null), /stale-vault/);
  assert.equal(storage.getItem("vault"), a.raw);
  V.save(storage, "vault", b, revision);
  assert.equal(storage.getItem("vault"), b);
  const broken = {
    getItem: () => null,
    setItem: () => {
      throw new Error("quota");
    },
  };
  assert.throws(() => V.save(broken, "vault", a.raw, null), /quota/);
});
test("malformed decrypted workspace cannot pass domain import", async () => {
  const a = await V.create('{"schema":99}', pass);
  const plain = (await V.open(a.raw, pass)).plaintext;
  assert.throws(() => D.importWorkspace(plain));
});
