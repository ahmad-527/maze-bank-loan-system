# Evidence synthesis and requirements decisions

30 October 2026. Read with the [protocol](REVIEW_PROTOCOL.md), [counts/limits](README.md) and [paper-level matrix](EVIDENCE_MATRIX.json). This is a qualitative, purposive foundation synthesis. No pooled effect size or claim of exhaustive coverage is made. IDs below resolve to full bibliographic records and critical appraisals in the matrix.

## Value must survive a credible comparison

An attractive or acceptable-rated application may still be harder to use. In C0070, a new smoking-cessation app underperformed a comparator on completion and time despite acceptable SUS. In C0262, app-supported smart packaging improved reported usability without a statistically significant adherence difference; a small null result does not establish equivalence. C0832 shows why a credible print comparison changes interpretation of online interventions. These results do not predict the effect of a finance product, but they challenge rating-based definitions of success.

**Decision:** VAL-01, UX-04 and G4 require observed task/outcome evidence against a credible alternative, with a prespecified worthwhile improvement and unacceptable-regression boundary. Satisfaction, clinical/financial benefit and adoption remain separate. Essential task failure blocks release rather than being averaged into a quality score.

## Participation and process are means, not outcome guarantees

C0713 reports heterogeneous participatory-design results; C0721 describes modifications that could worsen experience. C0706/C0709 explain development frameworks rather than proving causal superiority. C0735 identifies prototype interaction problems that can contaminate interpretation of the substantive intervention. C0140 demonstrates a prioritisation process with only four professionals, not proof of optimal prioritisation. C0103 shows budget/platform and late-content constraints in real development.

**Decision:** UX-01–03, FUN-01–03 and G2/G3 require complete journey/state inventories, documented design tradeoffs and testing of the final implementation. Workshops, personas and design-system adherence cannot pass the outcome gate by themselves. Three design directions are a portfolio working rule, not an experimentally proven universal number.

## Include the people that convenient research misses

C0690's very low response from active portal users cannot establish nonuser usability. C0807 found more advantaged focus-group participants than survey respondents. C0050 still overrepresented technically confident people despite grounded personas. C0067 adapted research for communication difficulties while acknowledging missing severe-vision and language groups. C0146's small psychophysiological experiment in nondisabled, non-native-English adults cannot demonstrate disabled-user access. C0187's landing-page audit cannot establish whole-site conformance.

**Decision:** ACC-01/02 and product discovery require manual/assistive task checks, appropriately adapted participation, and explicit sampling/exclusion limits. Automated scans and WCAG are necessary references, not evidence that every user need is met. C1030 supports examining numerical comprehension; its observational relationship between numeracy and financial well-being does not prove an app intervention works.

## Measure the construct actually claimed

eHEALS assesses perceived skills (C0646), while C0650 questions its relationship with demonstrated performance. MARS (C0647), MAUQ (C0648), translated scales (for example C0659/C0660/C0677/C0701) and professional pMARS (C0080) address different constructs/populations. C0649 does not establish a universal SUS cutoff. C1033's financial-well-being scale has a specific convenience sample and model structure, not automatic German validity.

**Decision:** REQ-MEASUREMENT maps to VAL-01, UX-04, ACC-02 and G4. Specify construct, instrument, language, task and interpretation before data collection. Avoid arbitrary universal composite scores, percentage interpretations of SUS and post hoc success thresholds. A custom scale needs validation rather than a scientific-sounding label.

## Engagement must serve the user's objective

C0824 separates usage attrition from follow-up attrition. C0827/C0828 distinguish meaningful engagement from raw exposure and caution against treating more use as inherently better. C0831's heterogeneous prompt evidence does not justify constant reminders. C0863 supports defining meaningful usage logs; C0857 highlights missing/incomplete usage reporting. C0219/C0228 use related project evidence and are not independent replications.

**Decision:** VAL-02, UX-01 and OPS-01 specify intended use and success for the actual job. An occasional preparation tool may succeed with a completed export and little daily retention. Notifications need user control and a burden hypothesis. Data-minimising measurement takes priority over vanity analytics.

## Verify actual data behavior and recoverability

C0884/C0885 demonstrate discrepancies between accreditation/disclosure and observed data handling in historical app samples. C0279 explicitly does not verify technical implementation from policy text. C0283's four-app privacy analysis is version/time bounded. C0897 tests de-identification settings on a limited tag benchmark; its result is not universal anonymisation assurance. C0887/C0901 concern security perceptions/preferences, not technical assurance.

**Decision:** PRIV-01, SEC-01/02 and DATA-02/03 require direct inspection of storage, traffic, logs, exports, defaults, deletion and recovery against stated behavior. Privacy policies, badges, favourable perceptions and clean bounded scans cannot certify safety. Current primary legal text and deployment facts govern legal applicability. Supporting context papers C0933/C0938 remain outside the counted minimum pending confirmation; their presence is not concealed as verified eligibility.

## Reproducibility and engineering need separate layers of proof

SciPy's project account (C0586) differentiates correctness and performance testing. C0588/C0590/C0591/C0595/C0601/C0617/C0628 support explicit environments, inputs, metadata, execution and maintenance. C0596 demonstrates practical performance costs from abstractions in one toolkit; its coverage percentage is not a universal acceptance threshold. C0614/C0633 discuss maintenance resources. None establishes that reproducible output is semantically correct or beneficial.

**Decision:** ENG-01–03, PERF-01, OPS-01 and DATA-01 require proportional architecture, meaningful tests, independent expected domain results, clean-environment execution, performance budgets and support boundaries. Reproducible wrong answers still fail. Passing unit tests does not prove UI workflow success; UI success does not prove exported-byte integrity.

## AI must improve the complete workflow and retain correction

C0943/C0945/C0953/C0955 are reporting/consensus guidance, not efficacy trials. C0947 distinguishes bias/applicability from development/reporting quality. C0951 warns that overall performance can mask setting/group deficiencies. C0963 makes ongoing setting/drift evaluation explicit. C0949/C0971/C0993 reject universal validation sample-size rules. C0972 finds explanations can increase, decrease or fail to change trust. C0991 shows a bounded specialist collaboration result, not universal human–AI superiority. C0199 tests screenshot-based verification intention without incorrect outputs; it cannot demonstrate live calibrated reliance. C0211's local-AI prototype does not establish generated-content correctness or access for all disabled users.

**Decision:** AI-01/02 require a credible deterministic baseline, task/data rights, independent evaluation, uncertainty, wrong-answer trials, applicable group/shift checks, correction, abstention and non-AI fallback. On-device operation is a deployment constraint, not an accuracy, privacy or accessibility guarantee. AI is not mandatory for a flagship.

## Transfer and unresolved evidence

This corpus is strongly concentrated in digital health and accessible open literature. Finance-specific effectiveness, Germany/EU workflows, actual market popularity, product economics, modern visual design preferences and real prospective-user demand remain product research questions. Studies with overlapping samples and methodological companion papers inform a shared rationale without increasing the number of independent confirmations. No vote count, meta-analysis, GRADE rating or universal numerical quality score was produced.

The standard therefore combines three explicit sources of authority: limited empirical/methodological rationale from this review; separately verified normative/provider references; and portfolio engineering/design policies selected as requirements. The latter are testable commitments, not claims that 200 studies experimentally proved each rule.
