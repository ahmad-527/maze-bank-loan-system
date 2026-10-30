(function (root, factory) {
  const api = factory();
  if (typeof module === "object" && module.exports) module.exports = api;
  else root.MazeVault = api;
})(globalThis, function () {
  "use strict";
  const FORMAT = "mazebank-prepare-vault",
    VERSION = 1,
    ITERATIONS = 600000,
    MAX_BYTES = 3000000;
  const encoder = new TextEncoder(),
    decoder = new TextDecoder("utf-8", { fatal: true });
  const subtle = () => {
    if (!globalThis.crypto?.subtle) throw new Error("crypto-unavailable");
    return globalThis.crypto.subtle;
  };
  function b64(bytes) {
    let s = "";
    for (const b of bytes) s += String.fromCharCode(b);
    return btoa(s);
  }
  function bytes(s, max) {
    if (
      typeof s !== "string" ||
      s.length > max ||
      s.length % 4 !== 0 ||
      !/^[A-Za-z0-9+/]*={0,2}$/.test(s)
    )
      throw new Error("vault-format");
    return Uint8Array.from(atob(s), (c) => c.charCodeAt(0));
  }
  const aad = (revision) =>
    encoder.encode(FORMAT + ":" + VERSION + ":" + revision);
  function revision() {
    return crypto.randomUUID();
  }
  async function derive(passphrase, salt) {
    if (
      typeof passphrase !== "string" ||
      passphrase.length < 12 ||
      passphrase.length > 256
    )
      throw new Error("passphrase-length");
    const material = await subtle().importKey(
      "raw",
      encoder.encode(passphrase),
      "PBKDF2",
      false,
      ["deriveKey"],
    );
    return subtle().deriveKey(
      { name: "PBKDF2", salt, iterations: ITERATIONS, hash: "SHA-256" },
      material,
      { name: "AES-GCM", length: 256 },
      false,
      ["encrypt", "decrypt"],
    );
  }
  function inspect(raw) {
    if (typeof raw !== "string" || encoder.encode(raw).length > MAX_BYTES)
      throw new Error("vault-size");
    const v = JSON.parse(raw),
      keys = [
        "format",
        "version",
        "iterations",
        "salt",
        "iv",
        "revision",
        "ciphertext",
      ];
    if (
      !v ||
      Array.isArray(v) ||
      Object.keys(v).length !== keys.length ||
      !keys.every((k) => Object.hasOwn(v, k)) ||
      v.format !== FORMAT ||
      v.version !== VERSION ||
      v.iterations !== ITERATIONS ||
      typeof v.revision !== "string" ||
      !/^[a-f0-9-]{36}$/i.test(v.revision)
    )
      throw new Error("vault-format");
    const salt = bytes(v.salt, 24),
      iv = bytes(v.iv, 16),
      ciphertext = bytes(v.ciphertext, MAX_BYTES);
    if (salt.length !== 16 || iv.length !== 12 || ciphertext.length < 16)
      throw new Error("vault-format");
    return { v, salt, iv, ciphertext };
  }
  async function seal(plaintext, key, salt) {
    if (
      typeof plaintext !== "string" ||
      encoder.encode(plaintext).length > 2000000
    )
      throw new Error("vault-size");
    const iv = crypto.getRandomValues(new Uint8Array(12)),
      rev = revision();
    const cipher = await subtle().encrypt(
      { name: "AES-GCM", iv, additionalData: aad(rev) },
      key,
      encoder.encode(plaintext),
    );
    return JSON.stringify({
      format: FORMAT,
      version: VERSION,
      iterations: ITERATIONS,
      salt: b64(salt),
      iv: b64(iv),
      revision: rev,
      ciphertext: b64(new Uint8Array(cipher)),
    });
  }
  async function create(plaintext, passphrase) {
    const salt = crypto.getRandomValues(new Uint8Array(16));
    const key = await derive(passphrase, salt);
    return { raw: await seal(plaintext, key, salt), key, salt };
  }
  async function open(raw, passphrase) {
    const { v, salt, iv, ciphertext } = inspect(raw),
      key = await derive(passphrase, salt);
    const clear = await subtle().decrypt(
      { name: "AES-GCM", iv, additionalData: aad(v.revision) },
      key,
      ciphertext,
    );
    return {
      plaintext: decoder.decode(clear),
      key,
      salt,
      revision: v.revision,
    };
  }
  // The captured revision is checked immediately before replacing storage. Callers serialize async saves.
  function save(storage, key, raw, expectedRevision) {
    const current = storage.getItem(key);
    const currentRevision = current ? inspect(current).v.revision : null;
    if (currentRevision !== expectedRevision) throw new Error("stale-vault");
    inspect(raw);
    storage.setItem(key, raw);
    if (storage.getItem(key) !== raw) throw new Error("storage-failed");
    return inspect(raw).v.revision;
  }
  return {
    FORMAT,
    VERSION,
    ITERATIONS,
    MAX_BYTES,
    inspect,
    create,
    open,
    seal,
    save,
  };
});
