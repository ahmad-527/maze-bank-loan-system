# Source selection and restoration scope

The portfolio owner confirmed Maze Bank as their project. Two local source variants were found, each with 13 Java classes, one SQL file and resources. The `bank` variant was selected as the restoration base: the other variant also opened a duplicate customer dashboard after customer login and opened a customer dashboard before the administrator dashboard. Selection is based on the inspected differences, not on a claim that a folder name proves which submission was final.

The later runnable restoration was prepared with Codex assistance. The historical coursework and the subsequent repairs are kept distinct in the branches and notes.

The `coursework-source` branch preserves both selected source variants, their SQL file and the two PNG resources actually referenced by the application. It replaces each historical Oracle connection's URL/user/password literals with documented placeholders and removes private telephone numbers and student email identifiers from the contact screen. Names remain in that historical source. A public manifest records the sanitised file hashes and the kinds of redaction applied. Unused photographs, screenshots, generated classes, IDE caches, compiled dependencies, reports and local database files are omitted. The untouched original folders remain private.

## Runnable edition

| Area | Historical source | Runnable restoration |
|---|---|---|
| Storage | Oracle-specific connections; one incomplete registration table; inconsistent column names | Embedded file H2 with users, applications, questions and schema version metadata |
| Credentials | Literal database connection values; plaintext account passwords | No Oracle connection; local public fictional seed accounts; salted PBKDF2 account hashes |
| Queries | SQL assembled from user-entered strings | Prepared statements and bounded field validation |
| Ownership | Application/query forms did not reliably bind records to an authenticated user | Customer records bind to session ID; customers see their own, admins can review all |
| Registration | Public admin registration/navigation inconsistencies | Customer self-registration; administrator creation requires a current administrator session |
| Save feedback | Query UI could display a row after persistence failed | Success follows a committed database write; rows reload from storage |
| Navigation | Dashboard misrouting and inconsistent back/exit behavior | One dashboard per login, sign-out clears session, back buttons respect the current role |
| UI | Missing custom fonts, clipped labels, inaccessible lower controls | System fonts, fitting labels/buttons, scrolling and resized windows |
| Claims | Banking-style marketing text and terms | Explicit local coursework simulation and fictional-data notice |

The restored UI continues to use the coursework's Swing screens and maze/X PNG resources. About, support and query screens were simplified for clear local-demo behavior. SQL access, hashing, validation, session state and shared UI behavior were separated into small classes. Background workers keep password hashing and database work off the Swing event thread.

The runnable edition does not claim compatibility with an existing Oracle database or migrate its records. Its complete H2 schema is initialised by `Database.java`. Unknown future schema versions are rejected without attempting a destructive migration. There is no network server, web console, real-bank integration, credit model or decision engine.

## Reproducibility and limitations

`pom.xml` pins direct dependencies and build plugins and sets a fixed build timestamp. Tests create temporary synthetic H2 databases, not the user's normal demo folder. No claim of byte-reproducible builds across different JDK/Maven environments is made. A file manifest and release checksums bind the inspected publication and download bytes.

The source archive is intentionally not silently upgraded. Its Oracle wiring and defects are historical evidence; use `main` or a release to run the repaired demo. The branch contains sanitised selected source, not a byte-identical copy of every file in the original project folders.
