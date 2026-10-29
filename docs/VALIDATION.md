# Validation scope

## Automated checks

JDK 17 and Maven 3.9.11: `mvn --batch-mode --no-transfer-progress --strict-checksums clean verify` completed successfully with **15 tests, zero failures, errors or skips**. Each service test creates an isolated temporary H2 database with fictional records.

The suite checks seed-account roles and repeat initialisation, customer registration and duplicate handling, password confirmation and fictional-reference requirements, salted password storage and malformed-hash handling, rejected SQL-injection logins, literal handling of apostrophes/SQL-like text in records, password-array clearing, unauthenticated and wrong-role access, guarded admin creation, invalid fields/amounts/types/terms without inserted rows, customer record ownership, administrator visibility, logout/session invalidation and persistence after reopening the database.

These are application/service checks, not a penetration test, independent audit or verification of real-world lending rules. No Oracle database was connected.

## Windows desktop walkthrough

The application was run in a Windows graphical desktop using a private folder of synthetic records. Native UI interactions checked customer registration and success feedback, customer login, loan submission and committed-save confirmation, own-application review, query entry/reload, sign-out, administrator login/dashboard routing, administrator application review and read-only query review across customers. An application restart retained the fictional registered account and loan. Final admin-login label changes were rebuilt, the service suite rerun and the changed screen checked again, followed by administrator application/query review on that build.

Screenshots show fictional examples only. The final JAR's SHA-256 is recorded in the private build/publication receipt; the downloadable ZIP is bound by the release's `SHA256SUMS.txt`. No test database or locally created account is shipped; fresh installations seed only the two documented demo accounts and their sample application/query.

Only Windows desktop execution was checked. macOS/Linux GUI execution, physical machines other than the current workstation, an existing Oracle installation, real-bank integrations and production deployment are outside this validation. CI's Linux service tests, when green, do not establish Linux GUI behavior.

## Publication checks

Publication uses an explicit reviewed file list. Original selected source/resource hashes are checked against the initial private snapshot. Historical Oracle connection credentials, phone numbers and student email identifiers are removed from the public copy. Generated classes, local databases, caches, submission documents and private review logs are excluded.

After publication, an independent fresh clone is compared against both branches' reviewed file hashes and modes, and release assets are downloaded anonymously and hashed. The publication receipt records those results separately from the build and GUI checks.

## Material limits

The app is a local coursework simulation. The embedded data file is unencrypted; public demo credentials and local administrator review are intentional. The project has no credit approval engine, repayment/balance ledger, account recovery, production access-control perimeter or independent security certification. Use fictional information and a password created only for this demo.
