# Portfolio flagship standard

Version 2.0 — 30 October 2026. Applies to every new flagship edition and material expansion. Original coursework restoration remains separate.

**Acceptance standard:** complete an important, defined user job exceptionally well, demonstrate a worthwhile advantage over credible alternatives, and let users install, understand, use, correct and recover the product reliably. “World class” is the ambition. Claims of being “best”, “top” or a market leader need comparative evidence with a stated scope and date.

Every mandatory gate passes independently. A high aggregate score cannot compensate for a broken essential workflow, serious accessibility barrier, incorrect domain result, data loss or exploitable security boundary. This document does not certify that existing projects meet the standard.

## 1. Research foundation

The [foundation review](foundation/README.md) counts **200 papers**, with 18 further context appraisals held outside the count. Each record contains design/context, findings, limitations, transfer judgments and requirement implications. The [evidence matrix](foundation/EVIDENCE_MATRIX.json) retains source hashes, bibliographic identity, publication checks and counting decisions.

Reading depth was focused full-text appraisal of methods/development, results and discussion/limitations passages. It was not an exhaustive examination of every paragraph, supplement or dataset. Twenty-eight counted papers contain explicit review statements in retrieved article material; 172 use publication identity, article type and verified venue/publisher-policy support. Individual review histories were not all audited.

This purposive, open-access, health-app-heavy corpus is not an exhaustive systematic review of software, a pooled effect estimate or validation of a finance product. One AI-assisted reviewer assessed it without independent duplicate extraction. These limitations accompany future use. See the [synthesis](foundation/SYNTHESIS.md).

Methodological guidance, expert consensus, observational associations, experiments and reviews have different evidential roles. Companion guidelines, overlapping reviews and papers using related samples are evidence clusters, not independent replications. The paper count does not prove demand or guarantee quality.

## 2. Purpose before implementation

Produce an opportunity brief specifying target users, geography/language, essential job, current workaround, frequency/consequences, constraints and intended measurable benefit. Include people who struggle with existing tools and people who choose not to use them. A widespread problem does not establish demand for another app.

Compare improving an existing workflow/tool, a smaller focused intervention, and a new application. Include free services, paper and spreadsheets where relevant. Select the strongest credible baseline. Every proposed gap links the affected user, dated evidence, existing capability, remaining friction, improvement hypothesis and falsifiable test. Keep observed problems, plausible opportunities and validated benefits distinct.

Stop or narrow when alternatives already meet the job, the advantage is immaterial, delivery is infeasible or evidence contradicts the premise. Research must be allowed to reject the product. An unobserved competitor feature is “not established”, not automatically absent.

## 3. Research required for each flagship

**100 relevant peer-reviewed papers and 20 qualified comparable apps per individual flagship remain mandatory.** The shared foundation gives no automatic credit. Reusing a paper requires a product-specific applicability assessment. Unrelated health studies cannot establish German debt-counselling need.

For papers, retain exact queries/sources/dates, eligibility, deduplication, inaccessible text and exclusions. Separate retrieval, abstract screening and completed appraisal. Verify identity and review provenance. Preprints without reviewed publication, duplicate versions/translations, corrections, retractions, marketing and unrelated padding do not count. A review counts once; its references count only when separately assessed. Standards and official statistics are additional evidence.

Each review records the question, design, sample/context, measures, findings, bias/precision limits, causal strength, transfer limits and decisions. Use design-appropriate appraisal where material. Preserve contradictory, null and adverse results. A reporting checklist is not proof of low bias. Trace decisive claims to source text and define refresh/correction triggers.

For apps, establish category, market and selection rules first. Record dated adoption/popularity evidence separately from effectiveness. Do not claim a definitive top-20 ranking without comparable data. Distinguish direct substitutes, adjacent workflows and visual references; unrelated visual references cannot fill the comparator quota.

For each comparator record version/platform/date, access tier/price, language, rationale, task scripts, observations, sources and unknowns. Benchmark onboarding, core tasks, navigation, input effort, error/recovery, accessibility, data control/export and support. Documentation/screenshots begin research; they do not replace actual accessible workflow tests. Never mark unavailable paid/account-only flows as tested. If access or qualified comparator count falls short, keep the gate open and disclose the shortfall.

## 4. Testable requirements

Trace **user evidence → requirement → risk → implementation → test → observed result → release claim**. Record must/should/could/won't priorities, ownership, dependencies, exclusions and uncertainty. Every feature must earn its complexity and maintenance burden.

| ID | Mandatory requirement | Required acceptance evidence |
|---|---|---|
| VAL-01 | Complete a defined important job with meaningful benefit over the strongest feasible baseline. | Prespecified comparative tasks/outcomes, observed results, uncertainty and limits. |
| VAL-02 | Choose scope from evidence and consider smaller/existing solutions. | Opportunity brief, gap matrix, alternatives and decision log, including no-go reasons. |
| FUN-01 | Every advertised essential feature works through the real UI and persistence layer. | Complete end-to-end journeys; no decorative controls, fabricated success or unlabelled demo outputs. |
| FUN-02 | Users can start, resume, inspect, edit, correct and finish reliably. | First-use, restart, interruption, invalid/partial input, repeat-action, cancellation and recovery scenarios. |
| FUN-03 | Empty, loading, offline, denied-permission, timeout and error states provide a useful next action. | State inventory and exercised failures; actual network requirements disclosed. |
| DATA-01 | Domain calculations and records preserve meaning and precision. | Independently derived examples, boundary/property tests; explicit units, money precision and rounding. |
| DATA-02 | Imports, exports, migrations and concurrent changes preserve intended records. | Round trips, version/corruption/conflict checks and provenance; no silent overwrite or invented values. |
| DATA-03 | Backup, restore, deletion and portability work within stated boundaries. | Restore into a clean environment; verify actual downloaded/exported bytes and applicable deletion/backup behavior. |
| UX-01 | Information architecture follows the user's language, priorities and sequence. | Task/content model, label/navigation tests, clear primary actions and progressive disclosure. |
| UX-02 | Visual identity is deliberate, coherent and competitive in its category. | Reference-flow critique, distinct design directions, selection rationale and component/token specification. |
| UX-03 | Design craft survives real content and all important states. | Responsive layouts, long/localised text, dense/empty data, readable charts, consistent feedback and useful motion. |
| UX-04 | Appeal accompanies successful performance. | Observed task completion, errors, effort/time, comprehension and recovery plus appropriate subjective measures. |
| ACC-01 | In-scope web content targets WCAG 2.2 AA; native apps also follow relevant platform accessibility practices. | Criterion-level applicability, automated checks and manual keyboard/focus/zoom/screen-reader/device testing. |
| ACC-02 | Intended users with relevant access/literacy/language constraints can complete essential tasks. | Adapted testing with affected audiences; technical conformance alone is insufficient. |
| SEC-01 | Security controls follow assets, actors, trust boundaries and credible abuse. | Threat model, versioned applicable web/mobile controls, negative authentication/authorisation/input tests. |
| SEC-02 | Safe defaults and least privilege hold across the lifecycle. | Secrets/dependency checks, session/storage/transport configuration, log/export/backup inspection and retest. |
| PRIV-01 | Data collection/transmission is necessary, transparent and controllable. | Inventory/retention matched against actual storage, requests, SDKs, logs, exports and deletion. |
| ETH-01 | Intended use, rights and applicable deployment obligations are established. | Asset/data/model licence register, accurate authorship/change record and product-specific jurisdiction/risk review. |
| ENG-01 | Architecture fits workload, risks and available support resources. | Decision records, clear boundaries, proportionate dependencies and operating assumptions. |
| ENG-02 | Tests address meaningful failures at appropriate layers. | Unit/integration/E2E/domain/security tests plus real UI checks; coverage is not a correctness certificate. |
| ENG-03 | A clean environment can install, build and run the release. | Locked/versioned dependencies, clean build, realistic demo, reproducible instructions and artifact identity. |
| PERF-01 | Target devices, data sizes and networks have explicit budgets. | Cold/warm measurements, p50/p95 action latency, relevant memory/battery/storage checks and regressions. |
| OPS-01 | Support and operation are feasible and honestly described. | Owner, costs/quotas, minimal-data monitoring, failure response, restore, rollback/update/end-of-life plan. |
| AI-01 | Optional AI earns inclusion against a simpler credible alternative. | Intended-use evaluation on representative independent data and complete human-plus-system tasks. |
| AI-02 | AI uncertainty, error, bias and change are manageable. | Version/data lineage, leakage/shift checks, justified subgroup analysis, user correction, abstention and fallback. |
| REL-01 | Claims match the shipped version and demonstrated scope. | Dossier linking revision, tests, UI evidence, artifact hashes, remaining risks and validation gaps. |

The [evidence-category map](foundation/REQUIREMENT_EVIDENCE_MAP.json) supplies rationale. It does not mean every paper independently validates every requirement. Engineering policies and proposed targets are labelled separately from experimentally established effects.

## 5. Visible design quality

For a new identity, explore at least three genuinely distinct visual/interaction directions as a portfolio working rule. Select one using task/user evidence and document the tradeoff. Study competitors' actual flows and appropriate platform references such as [Apple HIG](https://developer.apple.com/design/human-interface-guidelines) and [Material](https://m3.material.io/). Reference quality without copying branding, protected assets or complete screen compositions.

Specify typography/hierarchy, spacing/grid, colour/contrast, icons, component anatomy, density, chart semantics, states and motion/reduced motion. Use plausible content and data volumes. Warnings and state changes remain understandable without colour alone. Touch controls must be comfortable; dimensions and exceptions belong in the platform specification.

Inspect the built product at target sizes/input modes: alignment, clipping, focus, readable lines, scrolling, modal/keyboard behavior, responsive layout and feedback. Complete the detail, editing and recovery journeys as carefully as the home screen. Retest redesigns; a user-centred process can still make an experience worse.

## 6. Measurement and comparative proof

Before evaluation, define essential tasks, success/failure, primary outcome, meaningful improvement, harm/regression limits, sampling rationale and analysis. Begin with realistic synthetic scenarios. Include first/return use as relevant; address familiarity, assistance, learning and order effects. Counterbalance comparisons where feasible. Retain failed attempts, withdrawals, missing results and exclusions.

Small formative studies discover problems; they do not establish population-level superiority. Quantitative sample sizes follow precision/effect and design, not a universal “five users” rule. Report raw counts/denominators, variation and uncertainty. Relevant subgroup checks must not become unnecessary sensitive-data collection or confident claims from tiny groups.

Use completion, errors, effort/time, comprehension and correction/recovery as appropriate. Use SUS, MAUQ, MARS or other instruments only for their validated purpose, language and population. Adaptations require justification. SUS is not a percentage; 68 is not a universal pass mark. Ratings cannot demonstrate clinical/financial efficacy. Do not choose a convenient instrument after seeing results.

Adoption, repeat use, engagement and beneficial outcomes are separate. More app time is not inherently better. An occasional preparation task may succeed through a useful completed output with little return use. Reminders need purpose, control and burden assessment. Avoid manipulative streaks, excessive notifications and unnecessary analytics.

## 7. Security, privacy, integrity and AI

Pin applicable security standards and requirement identifiers. Checked on 30 October 2026: [OWASP ASVS 5.0.0](https://asvs.owasp.org/) is the stable web/service reference and [MASVS 2.1.0](https://github.com/OWASP/masvs/releases) the mobile reference. Select assurance depth from risk; sensitive financial/health workflows warrant stronger controls. Checklist use does not confer certification.

Use [NIST SSDF](https://csrc.nist.gov/Projects/ssdf/publications) for process structure. Its listed final foundation is version 1.1; listed version 1.2 is a draft. Refresh references at planning and release. Separate legislation, standards and research. A paper or WCAG target is not legal clearance: assess actual jurisdiction, data, intended use and deployment. Obtain specialist review before regulated claims/services when required.

Public demos use synthetic records. Inspect accidental disclosure and abuse through logs, clipboard, notifications, exports, telemetry and backups. Verify isolation where accounts exist. Local storage reduces some risks without automatically providing confidentiality. Synthetic data, federated learning and on-device AI also need threat/leakage assessment.

AI is optional. Record rights, model/version, hardware needs, operating domain and uncertainty. Keep evaluation independent of tuning and check leakage. Assess calibration/decision utility when relevant. Exercise human-plus-system workflows with wrong, ambiguous and out-of-domain outputs. Explanations can increase misplaced trust; correction, abstention and useful non-AI fallback matter. One successful external test does not permanently validate a model.

## 8. Performance and operation

Fill device/browser/OS, network, dataset, latency, resource and availability budgets before implementation. Missing values remain open requirements. Test cold start, warm interactions, large data, resource pressure, interruption and degraded dependencies. Distinguish lab and field results.

For applicable websites, use [Core Web Vitals](https://web.dev/articles/vitals) “good” guidance as a baseline: LCP ≤2.5 seconds, INP ≤200 milliseconds and CLS ≤0.1 at the 75th percentile, segmented by mobile/desktop. This is provider guidance, not proof of world leadership. Without field traffic, report lab conditions; do not invent measured field percentiles.

Set meaningful p95 action/export budgets, record recovery objectives and availability targets when hosting exists. Define remediation/rollback. Do not promise indefinite support, enterprise resilience, millions of users or 24/7 operation without resources and evidence.

## 9. Independent release gates

| Gate | Required exit evidence | If not met |
|---|---|---|
| G0 — Opportunity | User/job brief, alternatives, plausible meaningful gap and adverse evidence considered. | Reject/narrow before a full programme. |
| G1 — Product research | 100 relevant completed paper appraisals; 20 qualified app benchmarks with actual task evidence; synthesis and justified scope. | Continue discovery; disclose shortfalls, no flagship build commitment. |
| G2 — Definition/design | Testable requirements, complete journeys/states, visual system, risk/domain rules and device budgets. | Resolve critical uncertainties with bounded research/prototypes. |
| G3 — Functional candidate | All mandatory scripted scenarios pass on the candidate; clean install, persistence, calculations, recovery/export and negative tests verified. | Repair; screenshots or unit tests alone cannot pass. |
| G4 — Comparative validation | Prespecified user/practitioner evaluation where relevant demonstrates meaningful benefit with acceptable risk and no unacceptable essential-task regression. | Iterate, narrow claims or stop; agent walkthroughs are not human validation. |
| G5 — Release/support | No unresolved critical/high issue; residual risks, rights, accessibility/security evidence, reproducible artifacts and support boundaries documented. | An authorised candidate may be labelled accurately; do not claim validation/production readiness. |

Severity follows consequences. Data loss, cross-user disclosure, materially wrong essential outputs and blocked essential tasks are release blockers. Retest affected layers after repairs. Separate code/domain checking substantively from implementation judgment; disclose unavailable independent review rather than manufacturing independence.

Use precise maturity labels: **research concept**, **working prototype**, **verified release candidate**, **user-validated product**, **production-supported release**. “Flagship edition” identifies the intended product line; “validated flagship” requires the evidence. Do not silently lower requirements to meet a deadline.

## 10. Project dossier and original editions

Maintain an opportunity brief; research protocol/matrix; comparator selection/task log; gap synthesis; requirements/traceability; design; architecture/risk/data/rights records; verification; comparative evaluation plan/results; release/support instructions; and an honest status file. Cross-link them without duplicating unmaintainable narrative.

Preserve original editions and promised archival bytes. Keep restoration and later flagship distinct in the same repository where requested. Describe repairs, privacy redactions and later assistance accurately. Do not rewrite history, claim sole authorship of assisted extensions, publish others' samples or distribute assets without established permission.

**Maze Bank:** coursework restoration is published separately. The generic bank/loan-origination clone was rejected. Germany/EU debt-counselling preparation remains a candidate needing product-specific research, benchmarking and validation. The user's instruction to continue while away authorises continued work; it does not turn unperformed research or user testing into completed evidence.
