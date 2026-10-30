# Maze Bank Prepare

A working **research preview** for preparing for an initial German debt-counselling appointment. Organise claims, household costs, documents and questions, then review a portable handover. This is the intended flagship product line; its value has not yet been validated with clients or advisers.

**Use fictional data only.** No account, bank connection, payment, eligibility decision or automated legal advice is involved.

## Run on a PC

Extract the release ZIP and double-click **Run.cmd** on Windows. It starts a server on `127.0.0.1:4173` and opens your browser. Keep its console open; press Ctrl+C to stop. Node.js **22 or newer** is required; the launcher also recognises the local Codex bundled runtime when present. No `npm install`, API key or paid service is needed.

On other systems, or from source:

```sh
node server.cjs
```

Open `http://127.0.0.1:4173`. macOS/Linux launch behaviour and real mobile devices remain unverified. Local `file://` opening is not part of the verified run path. A static HTTPS deployment can serve the contents of `app/` without the server.

## Try the whole journey

1. Explore the fictional case. Switch German/English in the top bar. Text entered into records keeps its original language.
2. In **Claims**, add an unknown balance, identify the original creditor and collector, flag a dispute, and correct the source/date. Potential duplicates only warn; explicit repeated-record links prevent double counting.
3. In **Household**, add weekly or annual costs. See the original frequency, monthly conversion, estimates and pending income separately. The difference is arithmetic, never a safe repayment/spending recommendation.
4. Update **Documents** and add a personal **Next step** or question. The checklist stores notes, not document files. Dates do not trigger notifications or calculate legal deadlines.
5. **Save securely** with a passphrase of at least 12 characters. Save again after edits. Lock, reload and unlock to resume. Create an encrypted portable backup as well.
6. In **Prepare handover**, inspect every section and acknowledge that the report is unencrypted before downloading HTML. It includes unknowns, linked repeated records and calculation assumptions. The browser print button is provided; native PDF output was not verified.

A complete overview is not a prerequisite for seeking counselling. Check required documents with your chosen service.

## Data and recovery

Entries stay in browser memory until explicitly saved or backed up. The app makes no external API, analytics, advertising or bank requests. It uses its own CSS/SVG and system fonts.

Local vaults and backups use PBKDF2-SHA256 (600,000 iterations, random salt) and AES-256-GCM with fresh nonces and authenticated format/version/revision. Passphrases and keys are not persisted. There is no forgotten-passphrase recovery. Encryption does not secure a compromised device, unlocked session, extension, shared screen or an already downloaded plaintext report. No independent security audit has occurred.

Local save requires Web Crypto and Web Locks in a current secure-context browser. Failed save leaves the session open. Stale revisions block overwriting another tab's saved copy. Browser storage is origin-specific and can be cleared; portable encrypted backups matter. Import checks size, format and structure before a separate confirmation replaces the current session. It does not automatically overwrite local saved data.

While unlocked, **Save & backup → Remove local vault** removes only the local saved copy after typing `REMOVE`. The session and downloaded backups remain. Do not remove the only copy you need.

Engineering bounds: 400 claims, 500 household lines, 100 checklist items and 200 steps; 9,999,999.99 EUR per amount; 3 MB encrypted backup. Unknown amounts are not zero. Monthly conversion rounds each line half-up; this is an average, not a dated cash forecast.

## Development

```sh
node --test test/*.test.cjs
```

No runtime or test dependencies are downloaded. Domain, vault and report modules are separate from UI code. The loopback server serves only seven allowlisted static files, with restrictive security headers, and accepts no writes.

See the [research, requirements and validation dossier](https://github.com/ahmad-527/maze-bank-loan-system/tree/main/docs/prepare) for actual evidence and remaining gates. The shared 200-paper foundation is separate from this product's 100-paper review. Twenty comparator desk profiles are **not twenty completed qualified app benchmarks**. There are no completed human comparative sessions or adviser acceptance studies. This preview makes no debt-reduction, health, market-leadership or production-readiness claim.

Portfolio: Ahmad Sajjad Siddiqui. Later product research/design/implementation with Codex assistance. The original Java coursework/restoration is preserved separately in the same repository.
