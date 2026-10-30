# Maze Bank Prepare — research candidate definition

30 October 2026. Germany-first; intended flagship product line. **Bounded research prototype, not validated demand or a regulated service.** User approval permits implementation and verification while away. It does not supply missing human evidence.

## Purpose and decision

Help an adult prepare a usable, correctable overview before an initial German debt-counselling appointment: claims, household income/costs, documents and questions. The person keeps control of their records and explicitly reviews anything exported. A complete app is not a prerequisite to seek help.

[ISKA's preparation guidance](https://www.iska-nuernberg.de/schuldnerberatung/vorbereitung.html) establishes that income evidence, account/credit documents, official notices, recent creditor letters, household plans and creditor overviews are relevant tasks. It does not establish unmet software demand. [Verbraucherzentrale's creditor overview](https://www.verbraucherzentrale.nrw/wissen/geld-versicherungen/kredit-schulden-insolvenz/glaeubigeruebersicht-16335) is a serious free baseline.

**Conditional go for a synthetic research candidate; no full flagship validation claim.** A general bank, lender portal, repayment-order engine or financial-health score has no justified role in this scope. Twenty desk profiles reveal substantial feature overlap. The joined preparation/handover workflow is a hypothesis to compare with free forms, Norta and appropriate finance tools, not an established unique gap.

## Research decisions and limits

The product set contains 100 retained focused full-text appraisals from 118 appraised papers and 510 candidates. Publication identity and primary review policies are corroborated; fourteen have article-specific review statements. It is a purposive open-access, health/socioeconomic-heavy, single AI-assisted review, without independent extraction or replication. Related German OID, Swiss advice and NFCS cohorts are clustered rather than counted as independent confirmations. See `research/ANNOTATED_REVIEW.md`, `EVIDENCE_MATRIX.json` and `PUBLICATION_POLICY_REGISTER.json`.

| Evidence | Requirement consequence | Boundary |
|---|---|---|
| M0336 actual advice conversations; M0370 stigma experiments | Neutral language, optional steps, easy correction, no blame/streaks | Not proof that friendly interface copy reduces debt or stigma |
| M0188, M0241 advice/navigation work | Documents, questions, reusable reviewed overview | A workspace does not replace staff, entitlement resources or advocacy |
| M0031 timing of refunds; M0499 uncertain costs | Pending income separate; source/date/estimate explicit | Budget average is not a cash-flow forecast or safe repayment amount |
| M0222 language difficulties; M0242/M0392 numeracy; M0219/M0224 ability | Plain terms, concrete examples, visible arithmetic, no health/age gate | No diagnosis, incapacity inference or causal teaching benefit |
| M0060/M0067/M0435 heterogeneous service trials/reviews | Prespecified preparation outcomes and useful non-digital comparison | Education/coaching evidence cannot prove this app effective |
| M0346 actual debt-account relief | Separate creditors, collectors and repeated records | Grouping on screen is not debt elimination or a recommendation to consolidate |
| M0228 financial-wellbeing measurement review | Measure preparation correctness/time and user comprehension directly | No invented wellbeing score or health benefit |

## Core journeys and acceptance scenarios

| ID | Required behaviour | Concrete check |
|---|---|---|
| PREP-01 | Explore immediately without account or bank connection | Fictional sample opens; all data clearly labelled; blank workspace also works |
| PREP-02 | Distinguish claim from collecting organisation | Original creditor, current contact/collector, reference, dated amount, source and uncertainty can be edited |
| PREP-03 | Unknown is not zero | Blank amount preserved as null; unknown count beside known total; exports retain this distinction |
| PREP-04 | Dispute is not an automatic exclusion | Disputed claim remains identified; total never described as legally owed/approved |
| PREP-05 | Prevent double counting without silent merging | Possible matches flagged; only explicit user linkage excludes repeated record; cycles/dangling links rejected; parent deletion blocked until unlink |
| PREP-06 | Correctness independent of display locale | EUR integer cents, strict de-DE/en input, no floating-point money; malformed/ambiguous dates and numbers rejected |
| PREP-07 | Explain irregular household costs | Weekly ×52/12; fortnightly ×26/12; quarterly ÷3; annual ÷12; round half-up per line; show original frequency and converted monthly amount |
| PREP-08 | Do not overstate spending capacity | Pending income excluded from current recorded income; unknowns/estimates shown; difference labelled arithmetic, never safe-to-spend/repay |
| PREP-09 | Track documents without uploading originals | Editable checklist state and notes; add custom item; explicit metadata-only storage |
| PREP-10 | Set personal follow-up/questions | Manual date, completion/edit/reopen; no legal deadline calculator, notifications or creditor contact |
| PREP-11 | Preserve recoverability | Encrypted local save + portable encrypted backup; wrong passphrase/tamper/malformed backup fails without replacing current session; preview before import replacement |
| PREP-12 | Prevent accidental disclosure | No third-party assets/analytics/API; reviewed export requires explicit acknowledgement; report is plaintext and clearly labelled; no automatic upload/share |
| PREP-13 | Honest persistence | Session-only state explicit; encrypted local save manual with dirty indicator; lock/reload/unlock tested; failed save keeps session and says unsaved |
| PREP-14 | Avoid stale-tab overwrites | Revision mismatch blocks save and offers backup/reload; no silent overwrite |
| PREP-15 | Accessible, complete states | Semantic controls/labels, keyboard dialog focus, visible focus, no colour-only states, responsive 360/768/1440, reduced motion; empty/error/unknown states |
| PREP-16 | Reviewed portable handover | Preview lists included claims/duplicates, budget formulas, checklist, questions and caveats; corrections invalidate review; download bytes inspected, print CSS provided |
| PREP-17 | No historical rewrite | Original Java restoration and archive untouched; new assisted extension separately documented in same repository |

Limits: 400 claim records, 500 budget lines, 100 checklist items, 200 next steps, 9,999,999.99 EUR per nonnegative amount, bounded text/import size. These are engineering limits, not realistic goals or financial rules. No document attachments, automatic OCR/AI, bank connections, payments, eligibility, legal prioritisation, health questions or real-data deployment in this research preview.

## Three design directions and selection

1. **Quiet Ledger:** warm paper, restrained serif headings, compact list. Calm but too close to Norta's public mapper and potentially document-like on small screens.
2. **Bright Steps — selected:** light lavender workspace, deep ink type, iris actions, mint contextual accents, strong typographic hierarchy, generous action-focused cards, clear left navigation and mobile navigation. A case workspace that keeps original amounts and missing information visible. No ornamental wealth charts.
3. **Counsel Room:** slate/teal dense professional table, efficient for trained advisers but too heavy for first-time preparation and narrow screens.

Observed references: Norta public mapper (warm neutral/serif, short guided inventory), Actual public demo 26.10.0 (persistent sidebar, dense data and recurring-cost editor) and Finanzguru public landing/provider screens (confident type/contrast and single-feature emphasis). Installed Finanzguru app was not inspected; these observations do not certify usability. No copied artwork, logos, CSS or screenshots enter the product. The candidate uses its own CSS/SVG and system fonts. Human preference/comparative tests remain outstanding.

## Architecture, threat model and verification budget

Static browser app with no runtime dependencies, no external requests, no service/backend/accounts. App shell served at loopback or opened from an extracted folder. Exact support is established by testing, not assumed. Domain logic and vault code separable from UI. HTML export escapes all record text. Imports are schema-validated, bounded and atomic; never evaluated as code.

Web Crypto PBKDF2-SHA256 (600,000 iterations, random 16-byte salt) and AES-256-GCM (random 12-byte nonce, authenticated format/version) protect local saved data and portable backups while locked. Passphrase/key not persisted. This is not an independent security audit, protection against malicious extensions/compromised OS, shared-screen access or already downloaded plaintext reports. Forgotten passphrases cannot be recovered. Public preview is synthetic-only.

Target lab budgets: first usable app <2.5 s for <250 KB compressed-free app assets on this Windows desktop; ordinary 100-record in-memory actions <200 ms; 400-claim export <1 s excluding browser file dialogue; crypto operation <3 s desktop. Record actual results with browser/build/data. No field Core Web Vitals or universal device performance claims. Minimum viewport 360 CSS px, text at 200% and keyboard-only checks. Desktop Chrome/Edge-family initially; Firefox/Safari/real mobile remain unverified until tested.

Mandatory scenarios: all PREP cases, bad-input boundaries, totals/rounding, unknowns, duplicate-link integrity, escaped hostile text, encrypted round-trip/wrong phrase/tamper, invalid import atomicity, stale-save prevention, reload/unlock, local-storage failure, download byte inspection, narrow viewport and keyboard/dialog focus. Candidate changes must not alter Java source/archive bytes. Independent review and human/practitioner comparison remain explicit open gates.

## Validation before a flagship claim

G1 remains open: twenty documented comparator profiles are not twenty qualified hands-on benchmarks. G4 remains open: no user or counsellor sessions have occurred. Prespecify incorrect/missing fields, false double counting, exported handover errors, task completion/time, resumption and comprehension versus free forms and strongest accessible comparators. Include decliners, low digital/language confidence and adviser acceptance. Do not recruit or contact people under generic implementation approval. If the candidate fails to improve preparation or imposes extra work, narrow or stop it. A verified working preview can be published with its actual maturity and this evidence boundary.
