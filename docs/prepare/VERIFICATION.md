# Maze Bank Prepare — verification record

30 October 2026. Status: **working research preview**. This record separates functional checks from scientific, user and production validation. Verification was performed by the implementing agent; independent review is unavailable.

## Executed checks

- **15 Node tests passed** on Windows: strict German/English money parsing; cent bounds; independent weekly/fortnightly/quarterly/annual examples and rounding; unknown/pending/disputed/repeated distinctions; exact sample totals; link cycles/dangling references/parent deletion; immutable edits; schema/type/date/size limits; escaped report text; 400-record bounds; cryptographic round-trip/randomisation/non-extractable keys; wrong phrase, salt/cipher/revision tamper; bounded KDF; stale saves/storage failure; malformed decrypted schema.
- **Desktop in-app browser, loopback HTTP:** German and English navigation; add and edit a claim; reject `1e3`; preserve a blank disputed balance as unknown; render hostile HTML as text; detect matching reference without merging; explicitly link a repeat and restore the original total; block removing its parent.
- **Household:** add a quarterly EUR 90 entry; observe EUR 30/month, recorded costs EUR 1,519.67 and arithmetic difference EUR 530.33. Weekly EUR 110 remains EUR 476.67/month; annual EUR 120 remains EUR 10/month. Pending EUR 350 remains excluded.
- **Checklist/steps:** add a requested document with note; add a dated follow-up for 4 November 2026; mark it complete. These remain metadata/manual reminders, not file uploads or notifications.
- **Persistence:** explicitly create encrypted local vault; reload into a locked screen; reject wrong phrase; unlock correctly and recover the added records. Save after import and lock/unlock were exercised.
- **Two tabs:** save a newer alias in tab B; tab A displays a persistent conflict status and blocks stale saving. The recovery route opens the current saved version for unlocking; the newer alias survives. Web Locks serialise the revision check/write between participating tabs.
- **Portable backup:** browser downloaded an encrypted JSON file. A separate Node process decrypted the actual downloaded bytes and checked five claims, eight household lines, six checklist items, three steps and expected totals. Wrong phrase was rejected in the import UI. A correctly authenticated but invalid-schema fixture was also rejected without replacing the current workspace. Valid import requires a separate confirmation.
- **Actual report download:** review acknowledgement blocks download until checked. Actual downloaded HTML bytes contain the expected added records, EUR 1,980.50 known claims, EUR 530.33 difference, linked repeats and escaped hostile text. See `DOWNLOAD_VERIFICATION.json` for byte counts/hashes. The sample download predates later cosmetic/count-label polish; the final source report generator is covered by the final Node tests.
- **Large UI fixture:** import 400 synthetic claims, amounts 1–400 cents; observe the independently expected EUR 802.00 total; render 400 claim articles; reject record 401 while keeping existing data. No claim of realistic debt distribution is made from this load fixture.
- **Static server:** GET and HEAD succeed for allowlisted files; traversal returns 404, POST returns 405, and the restrictive CSP is present. The first CLI connection attempt was blocked by the local sandbox; the same authorised check outside that sandbox passed.
- **Responsive:** all six views checked at requested 360, 768 and 1440 widths. Measured CSS client/scroll widths match in all 18 cases (345; 753/768; 1425/1440 depending on scrollbar), with no page-level horizontal overflow. Visible button names were checked at the phone breakpoint; browser console warnings/errors were empty. These are desktop viewport checks, not real-phone or WCAG certification.
- **Keyboard/empty state:** blank workspace, empty claims, Enter activation/submission, first-field dialog focus, reverse focus containment, remove and Undo. These are spot checks, not a complete assistive-technology audit.

The app makes no runtime third-party requests by design: no external assets, fetch calls, accounts, analytics or API clients; restrictive CSP and a read-only static loopback server support this boundary. This is source-level checking and exercised behaviour, **not an independent traffic/privacy audit**.

## Performance and support limits

Seven app assets total about 134 KB before compression at this stage. A Windows Node 400-record microbenchmark (20 iterations) recorded p95 approximately 67 ms for summary and 75 ms for report generation. Exact runtime/results are in `PERFORMANCE_CHECK.json`. These are Node measurements, not browser interaction latency, first usable rendering time or field Core Web Vitals.

The responsive/keyboard spot-check record must be read with actual measured CSS viewport dimensions; in-app browser viewport changes and capture scaling do not themselves establish real-phone behaviour. No real Android/iOS device, Firefox, Safari, native screen reader, complete WCAG audit, 200% browser zoom audit, independent security audit or long-term reliability study has been completed. Local-storage write failure was tested at the vault-module boundary, not by filling a browser's disk quota.

The browser security policy blocked `file://` navigation. No alternate browser, raw automation or protocol workaround was used. Standalone downloaded-file rendering and native Print-to-PDF were therefore **not verified**. Report bytes were independently inspected; print styles and the browser print action are supplied. The verified run path is loopback HTTP or the static HTTPS preview. The Windows launcher is supplied; its native double-click/browser-opening sequence was not separately GUI-verified.

## Research and release gates

The shared foundation has 200 counted focused full-text appraisals; the product set has 100 retained appraisals from 118 reviewed records, with publication identity and primary review-policy support. These are scoped AI-assisted reviews with the corpus/transfer limitations documented alongside them, not independent systematic-review findings.

Twenty comparator **desk profiles** and two partial public workflows are available. **Zero completed qualified 20-app benchmarks** and **zero human/practitioner comparative sessions** are claimed. Demand, superiority to free forms/established tools, counsellor handover acceptance and outcome effectiveness remain unproven. G1/G4 are open; this is not a validated flagship or production-supported service.

Original Java source/resources, archive branch and existing release are to remain unchanged. Public-source/remote-download verification is recorded separately after publication; this document must not be taken as proof that publication has already occurred.
