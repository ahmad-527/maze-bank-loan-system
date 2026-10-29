# Maze Bank - selected coursework source archive

This branch preserves two inspected historical Oracle-oriented source variants for Ahmad Sajjad Siddiqui's undergraduate Object Oriented Programming portfolio project. The principal screen classes are retained. The `bank` variant was used as the base for the later restoration; `maze-bank-copy` also contains duplicate or misrouted dashboard launches. Folder names alone do not establish which submission was final.

**To run the repaired app, use [main](https://github.com/ahmad-527/maze-bank-loan-system) or [Releases](https://github.com/ahmad-527/maze-bank-loan-system/releases).** This branch is historical source for inspection, not a validated runnable original.

Each variant contains 13 Java classes, its SQL file and the two PNG resources referenced by those classes. Unused photos/screenshots, compiled files, caches, reports and databases are omitted. The SQL file is incomplete relative to the UI's expected tables and columns. No working Oracle database was supplied or connected during restoration.

## Explicit publication redactions

- Oracle connection URL/user/password literals are replaced with `jdbc:oracle:thin:@localhost:1521:ORCL`, `YOUR_DB_USER` and `YOUR_DB_PASSWORD` placeholders.
- The contact screen's private phone numbers and student email identifiers are replaced with labelled placeholders. Historical names remain in that source.
- No other selected source/resource bytes are changed. `SOURCE_MANIFEST.json` records each published file's SHA-256 and redaction categories. The original folders remain untouched and private.

Known historical defects, including plaintext account passwords, concatenated SQL and navigation inconsistencies, are preserved for transparency. Do not use this source as a secure application or enter real information. The main branch documents the subsequent portable-storage, authentication, validation, ownership and UI repairs separately.

No blanket licence grant for the coursework or resource files is made by this repository.
