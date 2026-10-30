/* Maze Bank Prepare: a synthetic research candidate. No network or financial services. */
(function () {
  "use strict";
  const D = window.MazeDomain,
    V = window.MazeVault,
    R = window.MazeReport,
    $ = (q) => document.querySelector(q),
    e = R.esc;
  const STORAGE = "maze-prepare-v1";
  let state = D.demo("de"),
    view = "overview",
    dirty = false,
    reviewed = false,
    undoState = null,
    search = "",
    filter = "all";
  let localRaw = null,
    localError = false,
    locked = false,
    vaultKey = null,
    vaultSalt = null,
    expectedRevision = null,
    busy = false,
    stale = false,
    toastTimer;
  try {
    localRaw = localStorage.getItem(STORAGE);
    if (localRaw) {
      V.inspect(localRaw);
      locked = true;
      expectedRevision = V.inspect(localRaw).v.revision;
    }
  } catch {
    localError = true;
    locked = !!localRaw;
  }
  const t = (de, en) => (state.locale === "de" ? de : en),
    fmt = (n) => D.formatMoney(n, state.locale);
  const paths = {
    overview:
      '<rect x="3" y="3" width="7" height="7" rx="2"/><rect x="14" y="3" width="7" height="7" rx="2"/><rect x="3" y="14" width="7" height="7" rx="2"/><path d="M14 17h7m-3.5-3.5v7"/>',
    claims:
      '<rect x="4" y="3" width="16" height="18" rx="3"/><path d="M8 8h8M8 12h5M8 16h8"/>',
    budget: '<path d="M3 6h17v14H3zM3 6V4h14v2m-2 6h6v4h-6z"/>',
    documents:
      '<path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8zM14 2v6h6M8 13h8M8 17h5"/>',
    tasks:
      '<rect x="3" y="5" width="18" height="16" rx="3"/><path d="M7 3v4m10-4v4M3 11h18m-13 5 2 2 5-4"/>',
    export: '<path d="M12 16V3m-4 4 4-4 4 4M4 14v6h16v-6"/>',
    arrow: '<path d="M5 12h14m-5-5 5 5-5 5"/>',
    plus: '<path d="M12 5v14M5 12h14"/>',
    lock: '<rect x="4" y="10" width="16" height="11" rx="3"/><path d="M8 10V7a4 4 0 0 1 8 0v3m-4 5v2"/>',
    shield: '<path d="M12 2 3 6v6c0 5 9 10 9 10s9-5 9-10V6zM8 12l3 3 5-6"/>',
    check: '<path d="m5 12 4 4L19 6"/>',
    close: '<path d="m6 6 12 12M6 18 18 6"/>',
    edit: '<path d="m14 4 6 6M4 20l2-6L17 3l4 4-11 11z"/>',
    info: '<circle cx="12" cy="12" r="9"/><path d="M12 11v6m0-10v.1"/>',
    globe:
      '<circle cx="12" cy="12" r="9"/><path d="M3 12h18M12 3c6 6 6 12 0 18-6-6-6-12 0-18"/>',
    in: '<path d="M12 3v16m-5-5 5 5 5-5M4 5v3m16-3v3"/>',
    out: '<path d="M12 21V5m-5 5 5-5 5 5M4 19v-3m16 3v-3"/>',
    clock: '<circle cx="12" cy="12" r="9"/><path d="M12 7v6l4 2"/>',
    help: '<circle cx="12" cy="12" r="9"/><path d="M9 9a3 3 0 1 1 4 3c-1 0-1 1-1 2m0 3v.1"/>',
  };
  const icon = (name) =>
    `<svg class="icon" viewBox="0 0 24 24" aria-hidden="true">${paths[name] || paths.info}</svg>`;
  const button = (label, action, kind = "", id = "", ic = "") =>
    `<button type="button" class="btn ${kind}" data-action="${action}"${id ? ` data-id="${e(id)}"` : ""}>${ic ? icon(ic) : ""}${e(label)}</button>`;
  const pill = (label, kind = "") =>
    `<span class="pill ${kind}">${e(label)}</span>`;
  const info = (text, kind = "") =>
    `<div class="info-box ${kind}">${icon("info")}<span>${text}</span></div>`;
  const amount = (v) => (v === null ? t("Unbekannt", "Unknown") : fmt(v));
  const cert = (c) =>
    ({
      confirmed: t("Aus Unterlage", "From document"),
      estimated: t("Geschätzt", "Estimated"),
      unknown: t("Betrag offen", "Amount unknown"),
      pending: t("Ausstehend", "Pending"),
    })[c];
  const freq = (f) =>
    ({
      monthly: t("monatlich", "monthly"),
      weekly: t("wöchentlich", "weekly"),
      fortnightly: t("alle 2 Wochen", "every 2 weeks"),
      quarterly: t("vierteljährlich", "quarterly"),
      yearly: t("jährlich", "yearly"),
    })[f];
  const docStatus = (s) =>
    ({
      missing: t("Fehlt noch", "Still needed"),
      requested: t("Angefragt", "Requested"),
      ready: t("Vorhanden", "Ready"),
      notneeded: t("Nicht zutreffend", "Not applicable"),
    })[s];
  const viewName = (v) =>
    ({
      overview: t("Übersicht", "Overview"),
      claims: t("Forderungen", "Claims"),
      budget: t("Haushalt", "Household"),
      documents: t("Unterlagen", "Documents"),
      tasks: t("Nächste Schritte", "Next steps"),
      export: t("Übersicht teilen", "Prepare handover"),
    })[v];
  const date = (v) =>
    v
      ? new Intl.DateTimeFormat(state.locale === "de" ? "de-DE" : "en-GB", {
          day: "numeric",
          month: "short",
          year: "numeric",
          timeZone: "UTC",
        }).format(new Date(v + "T12:00:00Z"))
      : t("Noch offen", "Not set");
  const today = () => {
    const d = new Date();
    return [
      d.getFullYear(),
      String(d.getMonth() + 1).padStart(2, "0"),
      String(d.getDate()).padStart(2, "0"),
    ].join("-");
  };
  function toast(message, undo = false) {
    clearTimeout(toastTimer);
    $("#toast").innerHTML =
      `<span>${e(message)}</span>${undo ? `<button type="button" data-action="undo">${t("Rückgängig", "Undo")}</button>` : ""}`;
    $("#toast").classList.add("show");
    toastTimer = setTimeout(
      () => $("#toast").classList.remove("show"),
      undo ? 15000 : 6500,
    );
  }
  function commit(next, message, allowUndo = true) {
    const before = D.clone(state);
    state = D.validate(next);
    undoState = allowUndo ? before : null;
    dirty = true;
    reviewed = false;
    render();
    if (message) toast(message, allowUndo);
  }
  function navigate(next) {
    view = next;
    search = "";
    filter = "all";
    render(true);
  }
  function shell() {
    const a = D.summarize(state),
      counts = {
        claims: a.claimCount,
        documents: a.missingDocuments,
        tasks: a.openTasks,
      };
    return `<div class="shell"><aside class="sidebar"><div class="sidebar-top"><div class="brand"><img src="mark.svg" alt=""><div><div class="brand-name">Maze Bank</div><div class="brand-sub">PREPARE</div></div></div><button class="btn ghost compact mobile-label" data-action="about" aria-label="${t("Über diese Vorschau", "About this preview")}">${icon("help")}</button></div><div><p class="eyebrow nav-label">${t("Meine Vorbereitung", "My preparation")}</p><nav class="nav" aria-label="${t("Hauptnavigation", "Main navigation")}">${["overview", "claims", "budget", "documents", "tasks", "export"].map((n) => `<button type="button" data-action="nav" data-id="${n}"${view === n ? ' aria-current="page"' : ""}>${icon(n)}<span>${viewName(n)}</span>${counts[n] !== undefined ? `<span class="count">${counts[n]}</span>` : ""}</button>`).join("")}</nav></div><div class="sidebar-bottom"><div class="local-note">${icon("shield")}<span><strong>${t("Deine Daten. Dein Überblick.", "Your data. Your overview.")}</strong><br>${t("Ohne Konto oder Bankverbindung.", "No account or bank connection.")}</span></div><div class="sidebar-foot"><button data-action="about">${t("Hilfe & Datenschutz", "Help & privacy")}</button><span class="eyebrow">v0.1</span></div></div></aside><div class="workspace"><header class="topbar"><div class="breadcrumb">${t("Arbeitsbereich", "Workspace")}<span>/</span><strong>${e(viewName(view))}</strong></div><span class="saved-status">${localRaw && vaultKey ? (stale ? t("Andere Version gespeichert", "Another version was saved") : dirty ? t("Änderungen noch ungespeichert", "Unsaved changes") : t("Verschlüsselt gespeichert", "Saved encrypted")) : t("Nur in dieser Sitzung", "Session only")}</span><div class="top-actions">${button(state.locale === "de" ? "EN" : "DE", "language", "ghost compact", "", "globe")}${button(vaultKey ? t("Speichern", "Save") : t("Sicher speichern", "Save securely"), "save-local", "compact", "", "lock")}${vaultKey ? button(t("Sperren", "Lock"), "lock", "ghost compact") : ""}</div></header><main id="main" class="main" tabindex="-1"><div class="preview-ribbon"><span class="pill violet"><span class="status-dot"></span>${t("Forschungsvorschau · nur fiktive Daten", "Research preview · fictional data only")}</span><button class="text-btn" data-action="case">${e(state.alias)} ${icon("edit")}</button></div>${page()}</main></div></div>`;
  }
  function heading(kicker, title, description, action = "") {
    return `<div class="page-heading"><div><span class="eyebrow">${e(kicker)}</span><h1>${e(title)}</h1><p>${e(description)}</p></div>${action}</div>`;
  }
  function empty(title, description, action = "", ic = "claims") {
    return `<div class="empty">${icon(ic)}<h3>${e(title)}</h3><p>${e(description)}</p>${action}</div>`;
  }
  const art = `<div class="hero-art" aria-hidden="true"><svg viewBox="0 0 180 180"><rect x="26" y="12" width="130" height="154" rx="18" fill="white" stroke="#dad5fa" stroke-width="2"/><rect x="44" y="30" width="48" height="7" rx="3.5" fill="#c4bcfa"/><path d="M47 66h31v30h32V66h28M47 125h31V96m32 0v29h28" fill="none" stroke="#7669e7" stroke-width="9" stroke-linecap="round" stroke-linejoin="round"/><circle cx="138" cy="125" r="21" fill="#c3eddd"/><path d="m128 125 7 7 12-15" fill="none" stroke="#27765b" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/><circle cx="47" cy="66" r="6" fill="#5145cd"/></svg></div>`;
  function overview() {
    const a = D.summarize(state),
      next = a.unknownClaims
        ? "claims"
        : a.unknownBudget
          ? "budget"
          : a.missingDocuments
            ? "documents"
            : a.openTasks
              ? "tasks"
              : "export";
    const steps = [
      [
        "claims",
        t("Forderungen sortieren", "Organise claims"),
        `${t("Forderungen", "Claims")}: ${a.claimCount} · ${t("Beträge offen", "Amounts unknown")}: ${a.unknownClaims}`,
      ],
      [
        "budget",
        t("Haushalt erfassen", "Map your household"),
        `${state.budget.length} ${t("Posten", "entries")} · ${a.estimatedBudget} ${t("geschätzt", "estimated")}`,
      ],
      [
        "documents",
        t("Unterlagen sammeln", "Gather documents"),
        `${a.readyDocuments} ${t("vorhanden", "ready")} · ${a.missingDocuments} ${t("noch offen", "still needed")}`,
      ],
      [
        "export",
        t("Prüfen und mitnehmen", "Review and take along"),
        t(
          "Deine Übersicht für das Gespräch",
          "Your overview for the conversation",
        ),
      ],
    ];
    const tasks = state.tasks.filter((x) => !x.done).slice(0, 3);
    return `${heading(t("Ein guter Anfang", "A good start"), t("Mehr Klarheit. Schritt für Schritt.", "A clearer picture. One step at a time."), t("Sortiere deine Unterlagen und Fragen für ein erstes Beratungsgespräch. Du entscheidest, womit du beginnst.", "Organise your records and questions before a first counselling appointment. Start wherever works for you."))}<div class="grid two"><div class="stack"><section class="hero"><div class="hero-copy"><div class="eyebrow">${t("Dein nächster kleiner Schritt", "Your next manageable step")}</div><h2>${t(next === "claims" ? "Offene Beträge festhalten." : next === "documents" ? "Ein Dokument nach dem anderen." : next === "budget" ? "Auch Schätzungen sind ein Anfang." : next === "tasks" ? "Platz für deine Fragen." : "Deine Übersicht ist bereit zum Prüfen.", next === "claims" ? "Leave room for what’s unknown." : next === "documents" ? "One document at a time." : next === "budget" ? "Estimates are a starting point." : next === "tasks" ? "Make room for your questions." : "Your overview is ready to review.")}</h2><p>${t("Du musst nicht alles wissen. Halte offene Punkte fest und ergänze sie später.", "You don’t need every answer now. Record what is missing and come back to it.")}</p>${button(t("Weiter vorbereiten", "Continue preparing"), "nav", "", next, "arrow")}</div>${art}</section><div class="metric-grid"><div class="metric"><div class="metric-label">${icon("claims")}${t("Erfasste Beträge", "Recorded amounts")}</div><div class="value number">${fmt(a.claimsTotal)}</div><p>${a.unknownClaims} ${t("unbekannt · keine vollständige Schuldensumme", "unknown · not a complete debt total")}</p></div><div class="metric"><div class="metric-label">${icon("budget")}${t("Monatliche Ausgaben", "Monthly costs")}</div><div class="value number">${fmt(a.expenses)}</div><p>${t("Durchschnitt bekannter Posten", "Average of known entries")}</p></div><div class="metric"><div class="metric-label">${icon("documents")}${t("Unterlagen", "Documents")}</div><div class="value">${a.readyDocuments}<span class="muted small"> / ${state.documents.filter((x) => x.status !== "notneeded").length}</span></div><p>${t("vorhanden · Checkliste anpassbar", "ready · checklist is editable")}</p></div></div><section class="card"><div class="section-head"><h2>${t("Dein roter Faden", "Your preparation path")}</h2>${pill(t("In deinem Tempo", "At your pace"), "green")}</div><div class="step-list">${steps.map(([v, title, desc], i) => `<div class="step"><div class="step-number">0${i + 1}</div><div class="step-copy"><h3>${title}</h3><p>${desc}</p></div><button class="text-btn" data-action="nav" data-id="${v}">${t("Öffnen", "Open")} ${icon("arrow")}</button></div>`).join("")}</div></section></div><div class="stack"><section class="card"><div class="section-head"><h2>${t("Für dein Gespräch", "For your conversation")}</h2>${icon("tasks")}</div><p class="eyebrow">${t("Termin", "Appointment")}</p><p class="vspace"><strong>${date(state.appointment)}</strong></p><button class="text-btn" data-action="case">${t("Termin und Fallname bearbeiten", "Edit appointment and case name")} ${icon("edit")}</button><div class="section-divider"></div><h3>${t("Offene Schritte", "Open steps")}</h3>${tasks.length ? tasks.map((x) => `<div class="step"><div class="step-copy"><p>${e(x.title)}</p>${x.date ? `<span class="date-chip">${date(x.date)}</span>` : ""}</div></div>`).join("") : `<p class="small muted vspace">${t("Notiere eine Frage oder einen kleinen nächsten Schritt.", "Add a question or a manageable next step.")}</p>`}<button class="text-btn spacer" data-action="nav" data-id="tasks">${t("Alle Schritte ansehen", "View all steps")} ${icon("arrow")}</button></section><aside class="next-callout"><strong>${t("Unvollständig ist auch okay.", "Incomplete is okay.")}</strong>${t("Eine perfekte Übersicht ist keine Voraussetzung für Beratung. Bring mit, was du hast.", "A perfect overview is not a prerequisite for counselling. Bring what you have.")}</aside><section class="card"><span class="pill green">${icon("shield")}${t("Du behältst die Kontrolle", "You stay in control")}</span><h3 class="spacer">${t("Speichern, wenn es dir passt.", "Save when it suits you.")}</h3><p class="small muted vspace">${t("Speichere Änderungen verschlüsselt, bevor du schließt. Mit einer Dateisicherung kannst du später weiterarbeiten.", "Save changes encrypted before closing. A file backup lets you continue working later.")}</p><button class="text-btn spacer" data-action="storage">${t("Speichern & Sicherung", "Save & backup")} ${icon("arrow")}</button></section></div></div>`;
  }
  function claimCard(c) {
    const parent = c.repeatOf
      ? state.claims.find((x) => x.id === c.repeatOf)
      : null;
    return `<article class="claim"><div class="claim-top"><div class="avatar" aria-hidden="true">${e(c.creditor.trim().slice(0, 1).toUpperCase())}</div><div class="claim-info"><h3>${e(c.creditor)}</h3><p class="claim-sub">${c.collector ? t("Kontakt: ", "Contact: ") + e(c.collector) : t("Direkter Kontakt zum Gläubiger", "Direct creditor contact")}${c.reference ? " · " + e(c.reference) : ""}</p></div><div class="claim-amount"><strong>${amount(c.amount)}</strong><small>${c.asOf ? t("Stand ", "As of ") + date(c.asOf) : t("Stand noch offen", "Date not recorded")}</small></div></div>${parent ? `<p class="claim-note">${t("Verknüpfte Wiederholung zu", "Linked repeated record of")}: ${e(parent.creditor)}. ${t("Nicht erneut eingerechnet.", "Not counted again.")}</p>` : ""}${c.notes ? `<p class="claim-note">${e(c.notes)}</p>` : ""}<div class="claim-bottom"><div class="tags">${pill(cert(c.certainty), c.certainty === "unknown" ? "amber" : c.certainty === "estimated" ? "violet" : "")}${c.disputed ? pill(t("Bestritten / zu klären", "Disputed / to clarify"), "amber") : ""}${parent ? pill(t("Wiederholung", "Repeated record")) : ""}</div>${button(t("Bearbeiten", "Edit"), "edit-claim", "compact", c.id, "edit")}</div></article>`;
  }
  function claimResults() {
    const list = state.claims.filter(
      (c) =>
        (filter === "all" ||
          (filter === "unknown" && c.amount === null) ||
          (filter === "disputed" && c.disputed) ||
          (filter === "repeated" && c.repeatOf)) &&
        [c.creditor, c.collector, c.reference, c.notes]
          .join(" ")
          .toLocaleLowerCase()
          .includes(search.toLocaleLowerCase()),
    );
    return list.length
      ? list.map(claimCard).join("")
      : empty(
          t("Hier ist noch Platz.", "Nothing here yet."),
          t(
            "Erfasse eine Forderung oder ändere den Filter. Ein unbekannter Betrag ist erlaubt.",
            "Add a claim or change the filter. An unknown amount is allowed.",
          ),
          button(
            t("Forderung hinzufügen", "Add claim"),
            "add-claim",
            "primary",
            "",
            "plus",
          ),
        );
  }
  function claims() {
    const a = D.summarize(state);
    return `${heading(t("Sortieren statt raten", "Organise, don’t guess"), t("Forderungen & Kontakte", "Claims & contacts"), t("Ein Eintrag je Forderung. Gläubiger und Inkassokontakt können zur gleichen Forderung gehören.", "One entry per claim. A creditor and a collection contact can refer to the same claim."), button(t("Forderung hinzufügen", "Add claim"), "add-claim", "primary", "", "plus"))}<div class="card"><div class="split"><div><p class="eyebrow">${t("Summe bekannter Beträge", "Sum of known amounts")}</p><h2 class="number vspace">${fmt(a.claimsTotal)}</h2></div><div class="tags">${pill(`${a.unknownClaims} ${t("unbekannt", "unknown")}`, "amber")}${pill(`${t("Wiederholungen ausgeschlossen", "Repeated records excluded")}: ${a.repeatedClaims}`)}</div></div><p class="small muted vspace">${t("Erfasste Angaben, keine rechtlich geprüfte Schuldensumme. Bestrittene Beträge bleiben sichtbar und sind in der bekannten Summe enthalten.", "Recorded information, not a legally verified debt total. Disputed amounts remain visible and are included in the known sum.")}</p>${a.possibleDuplicates.length ? info(`${a.possibleDuplicates.length} ${t("mögliche Übereinstimmung(en). Prüfe Referenzen und Kontakte; verknüpfe nur tatsächlich wiederholte Einträge. Es wird nichts automatisch zusammengeführt.", "possible match(es). Check references and contacts; link only genuinely repeated records. Nothing is merged automatically.")}`, "amber") : ""}</div><div class="toolbar spacer"><input class="search" id="claim-search" type="search" maxlength="160" placeholder="${t("Gläubiger, Kontakt, Referenz suchen", "Search creditor, contact, reference")}" aria-label="${t("Forderungen durchsuchen", "Search claims")}" value="${e(search)}"><div class="filters">${[
      ["all", t("Alle", "All")],
      ["unknown", t("Betrag offen", "Unknown")],
      ["disputed", t("Bestritten", "Disputed")],
      ["repeated", t("Wiederholt", "Repeated")],
    ]
      .map(
        ([id, label]) =>
          `<button class="btn compact ${filter === id ? "soft" : ""}" data-action="filter" data-id="${id}" aria-pressed="${filter === id}">${label}</button>`,
      )
      .join(
        "",
      )}</div></div><div id="claim-results" class="claim-list">${claimResults()}</div><p class="footer-note">${t("Ein fehlender Betrag wird nicht zu null. Ändere die Angaben, wenn neuere Unterlagen vorliegen; eine Verknüpfung löscht kein Originalschreiben.", "A missing amount is not zero. Update records when newer documents arrive; linking an entry does not delete any original letter.")}</p>`;
  }
  function budgetRows(kind) {
    const rows = state.budget.filter((b) => b.kind === kind);
    return rows.length
      ? rows
          .map(
            (b) =>
              `<div class="budget-row"><div class="budget-symbol">${icon(kind === "income" ? "in" : "out")}</div><div class="label"><strong>${e(b.label)}</strong><small>${amount(b.amount)} · ${freq(b.frequency)} · ${cert(b.certainty)}</small></div><div class="budget-money">${b.certainty === "pending" ? t("Nicht eingerechnet", "Excluded") : amount(D.monthly(b.amount, b.frequency))}<small>${t("pro Monat", "per month")}</small></div><button class="btn ghost icon-only" data-action="edit-budget" data-id="${e(b.id)}" aria-label="${t("Bearbeiten: ", "Edit: ") + e(b.label)}">${icon("edit")}</button></div>`,
          )
          .join("")
      : empty(
          t("Noch keine Posten", "No entries yet"),
          t(
            "Bekannte Beträge oder Schätzungen hinzufügen.",
            "Add known amounts or estimates.",
          ),
          button(
            t("Posten hinzufügen", "Add entry"),
            "add-budget",
            "",
            kind,
            "plus",
          ),
          "budget",
        );
  }
  function budget() {
    const a = D.summarize(state);
    return `${heading(t("Dein Alltag in Zahlen", "Your everyday numbers"), t("Der Haushalt im Überblick", "Your household at a glance"), t("Erfasse laufende Einnahmen und Kosten. Verschiedene Rhythmen werden nachvollziehbar auf einen Monat umgerechnet.", "Record recurring income and costs. Different frequencies are converted transparently into a monthly average."), button(t("Posten hinzufügen", "Add entry"), "add-budget", "primary", "expense", "plus"))}<div class="grid two"><div class="stack"><section class="card"><div class="section-head"><h2>${t("Einnahmen", "Income")}</h2>${button(t("Hinzufügen", "Add"), "add-budget", "ghost compact", "income", "plus")}</div>${budgetRows("income")}</section><section class="card"><div class="section-head"><h2>${t("Ausgaben", "Costs")}</h2>${button(t("Hinzufügen", "Add"), "add-budget", "ghost compact", "expense", "plus")}</div>${budgetRows("expense")}</section></div><div class="stack"><aside class="card budget-summary"><div><span class="eyebrow">${t("Rechnerische Monatsübersicht", "Arithmetic monthly overview")}</span><div class="total number">${fmt(a.difference)}</div><p class="small muted">${t("Differenz bekannter Einnahmen und Ausgaben", "Difference between known income and costs")}</p></div><hr><div class="split"><span>${t("Erfasste Einnahmen", "Recorded income")}</span><strong>${fmt(a.income)}</strong></div><div class="split"><span>${t("Erfasste Ausgaben", "Recorded costs")}</span><strong>${fmt(a.expenses)}</strong></div><hr><div class="tags">${pill(`${a.unknownBudget} ${t("unbekannt", "unknown")}`, "amber")}${pill(`${a.estimatedBudget} ${t("geschätzt", "estimated")}`, "violet")}</div><p class="small muted">${t("Ausstehende Einnahmen nicht eingerechnet", "Pending income excluded")}: ${fmt(a.pendingIncome)}</p>${info(t("Das ist kein sicher verfügbarer Betrag zum Ausgeben oder Tilgen. Unbekannte Kosten, Schwankungen und Fälligkeiten können fehlen.", "This is not an amount safely available to spend or repay. Unknown costs, changes and payment timing may be missing."), "neutral")}</aside><section class="card"><h3>${t("So wird umgerechnet", "How conversion works")}</h3><p class="formula vspace">${t("Wöchentlich ×52÷12<br>Alle 2 Wochen ×26÷12<br>Vierteljährlich ÷3<br>Jährlich ÷12", "Weekly ×52÷12<br>Every 2 weeks ×26÷12<br>Quarterly ÷3<br>Yearly ÷12")}</p><p class="formula vspace">${t("Jeder Posten wird auf Cent gerundet. Ein Durchschnitt ist keine Vorhersage für dein Konto. Forderungssummen werden nicht automatisch als Monatsausgaben gezählt.", "Each entry is rounded to cents. An average is not an account forecast. Claim balances are not automatically counted as monthly costs.")}</p></section></div></div>`;
  }
  function documents() {
    const a = D.summarize(state);
    return `${heading(t("Alles Wichtige dabei", "Bring what matters"), t("Deine Unterlagen", "Your documents"), t("Eine anpassbare Checkliste für das Gespräch. Hier werden nur Status und Notizen gespeichert, keine Originaldateien.", "An editable checklist for the conversation. Only status and notes are stored here, not original files."), button(t("Unterlage hinzufügen", "Add document"), "add-document", "primary", "", "plus"))}<div class="grid two"><section class="card"><div class="section-head"><h2>${t("Checkliste", "Checklist")}</h2>${pill(`${a.readyDocuments} ${t("vorhanden", "ready")}`, "green")}</div>${state.documents.length ? state.documents.map((d) => `<div class="document-item"><span class="check-icon ${d.status === "ready" ? "ready" : ""}">${icon(d.status === "ready" ? "check" : "documents")}</span><div class="doc-copy"><h3>${e(d.title)}</h3>${pill(docStatus(d.status), d.status === "ready" ? "green" : d.status === "missing" ? "amber" : "")}${d.notes ? `<p>${e(d.notes)}</p>` : ""}</div>${button(t("Bearbeiten", "Edit"), "edit-document", "compact", d.id, "edit")}</div>`).join("") : empty(t("Deine Liste beginnt hier.", "Your list starts here."), t("Füge passende Unterlagen hinzu.", "Add the documents that fit your situation."))}</section><div class="stack"><aside class="next-callout"><strong>${t("Originale bleiben bei dir.", "Originals stay with you.")}</strong>${t("Lege Schreiben und Nachweise getrennt bereit. Diese Checkliste hängt keine Dateien an deine exportierte Übersicht an.", "Keep letters and supporting evidence separately. This checklist does not attach files to your exported overview.")}</aside><section class="card"><h3>${t("Was braucht meine Beratungsstelle?", "What does my counselling service need?")}</h3><p class="small muted vspace">${t("Die Startliste orientiert sich an öffentlichen Vorbereitungshinweisen von ISKA Nürnberg. Die Anforderungen deiner Beratungsstelle können abweichen.", "The starting checklist draws on public preparation guidance from ISKA Nürnberg. Your chosen service may have different requirements.")}</p><a class="text-btn spacer" href="https://www.iska-nuernberg.de/schuldnerberatung/vorbereitung.html" target="_blank" rel="noopener noreferrer">${t("Vorbereitungshinweise ansehen", "Read preparation guidance")} ${icon("arrow")}</a></section></div></div>`;
  }
  function tasks() {
    const ordered = [...state.tasks].sort(
      (a, b) =>
        Number(a.done) - Number(b.done) ||
        (a.date || "9999").localeCompare(b.date || "9999"),
    );
    return `${heading(t("Ein Schritt nach dem anderen", "One step at a time"), t("Deine nächsten Schritte", "Your next steps"), t("Sammle Fragen und setze persönliche Erinnerungstermine. Du entscheidest, was als Nächstes passt.", "Collect questions and set personal follow-up dates. Choose what works next."), button(t("Schritt hinzufügen", "Add step"), "add-task", "primary", "", "plus"))}<div class="grid two"><section class="card">${ordered.length ? ordered.map((x) => `<article class="task-row ${x.done ? "done" : ""}"><input type="checkbox" data-action="toggle-task" data-id="${e(x.id)}" aria-label="${t("Erledigt: ", "Done: ") + e(x.title)}" ${x.done ? "checked" : ""}><div class="task-copy"><h3>${e(x.title)}</h3>${x.notes ? `<p>${e(x.notes)}</p>` : ""}<span class="date-chip ${x.date === today() ? "today" : ""}">${x.date ? date(x.date) : t("Ohne Datum", "No date")}${x.done ? " · " + t("Erledigt", "Done") : ""}</span></div><button class="btn ghost icon-only" data-action="edit-task" data-id="${e(x.id)}" aria-label="${t("Bearbeiten: ", "Edit: ") + e(x.title)}">${icon("edit")}</button></article>`).join("") : empty(t("Platz für deine Fragen", "Room for your questions"), t("Zum Beispiel: Welche Unterlagen fehlen noch?", "For example: Which documents are still missing?"), button(t("Frage hinzufügen", "Add a question"), "add-task", "primary", "", "plus"), "tasks")}</section><aside class="card"><h3>${t("Nur deine persönliche Liste.", "Just your personal list.")}</h3><p class="small muted vspace">${t("Es werden keine Nachrichten verschickt und keine Benachrichtigungen geplant. Datumsangaben sind selbst gewählte Erinnerungen, keine berechneten Rechtsfristen.", "No messages are sent and no notifications are scheduled. Dates are your own reminders, not calculated legal deadlines.")}</p><button class="text-btn spacer" data-action="case">${t("Beratungstermin eintragen", "Set counselling appointment")} ${icon("arrow")}</button></aside></div>`;
  }
  function exportPage() {
    const a = D.summarize(state);
    return `${heading(t("Für das Gespräch", "For the conversation"), t("Prüfen. Mitnehmen. Besprechen.", "Review. Take along. Discuss."), t("Prüfe, welche Angaben du mitnehmen möchtest. Offene Beträge und fehlende Unterlagen dürfen offen bleiben.", "Review what you want to take along. Unknown amounts and missing documents can remain open."))}<section class="export-choice"><div class="split"><div><h3>${t("Deine persönliche Übersicht", "Your personal overview")}</h3><p>${a.claimCount} ${t("Forderungen", "claims")} · ${a.unknownClaims} ${t("Beträge offen", "amounts unknown")} · ${state.budget.length} ${t("Haushaltsposten", "household entries")} · ${state.documents.length} ${t("Checklistenpunkte", "checklist items")}</p></div>${pill(t("Kein automatischer Versand", "No automatic sharing"), "green")}</div><div class="check-field"><input id="review-check" type="checkbox" ${reviewed ? "checked" : ""}><label for="review-check">${t("Ich habe die Vorschau geprüft. Der heruntergeladene Bericht ist unverschlüsselt und für Personen mit Dateizugriff lesbar.", "I have reviewed the preview. The downloaded report is unencrypted and readable by anyone with access to the file.")}</label></div><div class="row-actions">${button(t("HTML-Übersicht herunterladen", "Download HTML overview"), "download-report", "primary", "", "export")}${button(t("Drucken / als PDF speichern", "Print / save as PDF"), "print-report", "", "", "documents")}${button(t("Verschlüsselte Sicherung", "Encrypted backup"), "backup", "ghost", "", "lock")}</div></section><section class="review-preview" aria-label="${t("Berichtsvorschau", "Report preview")}">${R.content(state)}</section><p class="footer-note">${t("Die HTML-Datei funktioniert ohne diese App. Öffne sie im Browser zum Drucken oder Speichern als PDF. Die verschlüsselte Sicherung dient zum späteren Weiterarbeiten.", "The HTML file works without this app. Open it in a browser to print or save as PDF. Use an encrypted backup to continue working later.")}</p>`;
  }
  function page() {
    return { overview, claims, budget, documents, tasks, export: exportPage }[
      view
    ]();
  }
  function lockedPage() {
    return `<main class="locked"><section class="card"><img src="mark.svg" alt=""><span class="eyebrow">MAZE BANK / PREPARE</span><h1 class="spacer">${t("Deine Vorbereitung ist gesperrt.", "Your preparation is locked.")}</h1><p>${t("Öffne deine lokal gespeicherte Übersicht mit deiner Passphrase. Sie wird nur auf diesem Gerät entschlüsselt.", "Open your locally saved overview with your passphrase. It is decrypted on this device.")}</p><form id="unlock-form"><div class="field"><label for="unlock-pass">${t("Passphrase", "Passphrase")}</label><input id="unlock-pass" type="password" minlength="12" maxlength="256" autocomplete="off" required></div><div id="unlock-error" class="error" role="alert"></div><button class="btn primary" type="submit">${icon("lock")}${t("Entsperren", "Unlock")}</button></form><div class="section-divider"></div><div class="row-actions">${button(t("Beispiel in neuer Sitzung öffnen", "Explore sample in a session"), "sample-session", "ghost")}${button(state.locale === "de" ? "English" : "Deutsch", "language", "ghost")}</div><p class="footer-note">${t("Passphrase vergessen? Ohne sie ist keine Wiederherstellung möglich. Eine Beispielsitzung überschreibt deinen gespeicherten Tresor nicht.", "Forgotten passphrase? It cannot be recovered. A sample session does not overwrite your saved vault.")}</p>${button(t("Hilfe & Sicherung importieren", "Help & import backup"), "storage", "ghost")}</section></main>`;
  }
  function render(focus = false) {
    const active = document.activeElement,
      restore = active?.dataset?.action
        ? { action: active.dataset.action, id: active.dataset.id || "" }
        : null;
    document.documentElement.lang = state.locale;
    document.title =
      "Maze Bank Prepare — " +
      (locked ? t("Gesperrt", "Locked") : viewName(view));
    $("#app").innerHTML = locked ? lockedPage() : shell();
    if (locked) $("#unlock-form").addEventListener("submit", unlock);
    if (focus && !locked) {
      $("#main").focus({ preventScroll: true });
      window.scrollTo(0, 0);
    } else if (restore) {
      const target = [...document.querySelectorAll("[data-action]")].find(
        (x) =>
          x.dataset.action === restore.action &&
          (x.dataset.id || "") === restore.id,
      );
      if (target) target.focus();
      else $("#main")?.focus({ preventScroll: true });
    }
  }
  function closeDialog() {
    if (busy) return;
    $("#dialog").close();
    $("#dialog").innerHTML = "";
    $("#dialog").classList.remove("wide");
  }
  function openDialog(title, description, body, onSubmit, wide = false) {
    const d = $("#dialog");
    if (d.open) d.close();
    d.classList.toggle("wide", wide);
    d.innerHTML = `<div class="dialog-header"><div><h2 id="dialog-title">${e(title)}</h2>${description ? `<p>${e(description)}</p>` : ""}</div><button type="button" class="btn ghost icon-only" data-action="close-dialog" aria-label="${t("Schließen", "Close")}">${icon("close")}</button></div>${onSubmit ? '<form id="editor">' : ""}${body}${onSubmit ? '<div id="form-error" class="error" role="alert"></div></form>' : ""}`;
    if (onSubmit)
      $("#editor").addEventListener("submit", (ev) => {
        ev.preventDefault();
        if (busy) return;
        onSubmit(new FormData(ev.currentTarget));
      });
    d.showModal();
    d.querySelector("input:not([type=file]),select,textarea")?.focus();
  }
  function field(
    id,
    label,
    value = "",
    type = "text",
    hint = "",
    max = 160,
    required = false,
  ) {
    return `<div class="field"><label for="f-${id}">${e(label)}${required ? " *" : ""}</label><input id="f-${id}" name="${id}" type="${type}" value="${e(value)}" ${type === "date" ? 'min="1900-01-01" max="2200-12-31"' : `maxlength="${max}"`} ${required ? "required" : ""}${hint ? ` aria-describedby="h-${id}"` : ""}${type === "password" ? ' autocomplete="off"' : ""}${id === "amount" ? ' inputmode="decimal"' : ""}>${hint ? `<span class="hint" id="h-${id}">${e(hint)}</span>` : ""}</div>`;
  }
  function select(id, label, value, options) {
    return `<div class="field"><label for="f-${id}">${e(label)}</label><select id="f-${id}" name="${id}">${options.map(([v, l]) => `<option value="${e(v)}" ${value === v ? "selected" : ""}>${e(l)}</option>`).join("")}</select></div>`;
  }
  function textarea(id, label, value = "", max = 1000) {
    return `<div class="field full"><label for="f-${id}">${e(label)}</label><textarea id="f-${id}" name="${id}" maxlength="${max}">${e(value)}</textarea></div>`;
  }
  function actions(collection, id, submit = t("Speichern", "Save")) {
    return `<div class="dialog-actions">${id ? button(t("Eintrag entfernen", "Remove entry"), "remove", "danger", collection + ":" + id) : ""}${button(t("Abbrechen", "Cancel"), "close-dialog")}<button class="btn primary" type="submit">${e(submit)}</button></div>`;
  }
  function formError(message) {
    const target = $("#form-error");
    if (target) {
      target.textContent = message;
      target.setAttribute("tabindex", "-1");
      target.focus();
    } else toast(message);
  }
  function errorMessage(err) {
    if (err.message === "linked-records" || err.message === "duplicate-link")
      return t(
        "Verknüpfung prüfen: Ein Haupteintrag mit Wiederholungen kann nicht selbst verknüpft oder entfernt werden. Löse zuerst die zugehörigen Verknüpfungen.",
        "Check links: a main record with repeated entries cannot itself be linked or removed. Unlink its repeated entries first.",
      );
    if (err.message === "record-limit")
      return t(
        "Die Höchstzahl der Einträge ist erreicht. Sichere die Übersicht und reduziere Einträge.",
        "The record limit has been reached. Back up the workspace and reduce entries.",
      );
    return t(
      "Bitte prüfe Betrag, Datum und Pflichtfelder. Beträge bis 9.999.999,99 €; Dezimaltrennzeichen Komma. Unbekannte Beträge leer lassen.",
      "Check amount, date and required fields. Amounts up to €9,999,999.99; use a decimal point. Leave unknown amounts blank.",
    );
  }
  function editClaim(id) {
    const c = state.claims.find((x) => x.id === id) || {
      id: crypto.randomUUID(),
      creditor: "",
      collector: "",
      reference: "",
      amount: null,
      certainty: "confirmed",
      asOf: "",
      source: "",
      disputed: false,
      repeatOf: "",
      notes: "",
    };
    openDialog(
      id
        ? t("Forderung bearbeiten", "Edit claim")
        : t("Forderung erfassen", "Add a claim"),
      t(
        "Pflichtfeld: Gläubiger. Unbekannte Angaben dürfen leer bleiben.",
        "Required: creditor. Unknown information can stay blank.",
      ),
      `<div class="form-grid">${field("creditor", t("Ursprünglicher Gläubiger", "Original creditor"), c.creditor, "text", "", 160, true)}${field("collector", t("Aktueller Kontakt / Inkasso", "Current contact / collector"), c.collector)}${field("reference", t("Referenz / Aktenzeichen", "Reference"), c.reference, "text", "", 100)}${field("amount", t("Betrag in EUR", "Amount in EUR"), D.moneyInput(c.amount, state.locale), "text", t("Zum Beispiel 1.200,50 · leer = unbekannt", "For example 1,200.50 · blank = unknown"), 20)}${select(
        "certainty",
        t("Herkunft des Betrags", "Amount basis"),
        c.certainty === "unknown" ? "confirmed" : c.certainty,
        [
          [
            "confirmed",
            t("Aus einer Unterlage übernommen", "Recorded from a document"),
          ],
          ["estimated", t("Geschätzt", "Estimated")],
        ],
      )}${field("asOf", t("Betrag gilt zum", "Amount as of"), c.asOf, "date")}${field("source", t("Quelle / Schreiben", "Source / letter"), c.source, "text", "", 200)}${select("repeatOf", t("Wiederholter Eintrag zu", "Repeated record of"), c.repeatOf, [["", t("Eigene Forderung / noch nicht zugeordnet", "Separate claim / not linked")], ...state.claims.filter((x) => x.id !== c.id && !x.repeatOf).map((x) => [x.id, x.creditor + " · " + (x.reference || amount(x.amount))])])}</div><div class="check-field"><input name="disputed" id="f-disputed" type="checkbox" ${c.disputed ? "checked" : ""}><label for="f-disputed">${t("Bestritten oder noch zu klären", "Disputed or needs clarification")}</label></div>${textarea("notes", t("Notizen / Fragen", "Notes / questions"), c.notes, 1500)}${info(t("Nur eine ausdrücklich verknüpfte Wiederholung wird nicht erneut addiert. Eine bestrittene Forderung wird weiterhin mit ihrem bekannten Betrag gezeigt.", "Only an explicitly linked repeated record is excluded from double counting. Disputed claims still display their known amount."), "neutral")}${actions("claims", id)}`,
      (data) => {
        try {
          const amount = D.parseMoney(data.get("amount"), state.locale),
            item = {
              ...c,
              creditor: data.get("creditor").trim(),
              collector: data.get("collector").trim(),
              reference: data.get("reference").trim(),
              amount,
              certainty: amount === null ? "unknown" : data.get("certainty"),
              asOf: data.get("asOf"),
              source: data.get("source").trim(),
              repeatOf: data.get("repeatOf"),
              disputed: data.has("disputed"),
              notes: data.get("notes").trim(),
            };
          const next = D.upsert(state, "claims", item);
          closeDialog();
          commit(next, t("Forderung gespeichert.", "Claim saved."));
        } catch (err) {
          formError(errorMessage(err));
        }
      },
    );
  }
  function editBudget(id, kind = "expense") {
    const b = state.budget.find((x) => x.id === id) || {
      id: crypto.randomUUID(),
      label: "",
      kind,
      amount: null,
      frequency: "monthly",
      certainty: "confirmed",
      source: "",
    };
    openDialog(
      id
        ? t("Haushaltsposten bearbeiten", "Edit household entry")
        : t("Haushaltsposten erfassen", "Add a household entry"),
      t(
        "Originalbetrag und Rhythmus eintragen. Der Monatsdurchschnitt wird berechnet.",
        "Enter the original amount and frequency. The monthly average is calculated.",
      ),
      `<div class="form-grid">${field("label", t("Bezeichnung", "Label"), b.label, "text", "", 120, true)}${select(
        "kind",
        t("Art", "Type"),
        b.kind,
        [
          ["expense", t("Ausgabe", "Cost")],
          ["income", t("Einnahme", "Income")],
        ],
      )}${field("amount", t("Originalbetrag in EUR", "Original amount in EUR"), D.moneyInput(b.amount, state.locale), "text", t("Komma für Cent · leer = unbekannt", "Decimal point for cents · blank = unknown"), 20)}${select(
        "frequency",
        t("Rhythmus", "Frequency"),
        b.frequency,
        Object.keys(D.FREQUENCIES).map((x) => [x, freq(x)]),
      )}${select(
        "certainty",
        t("Einordnung", "Status"),
        b.certainty === "unknown" ? "confirmed" : b.certainty,
        [
          ["confirmed", t("Aus Unterlage / bekannt", "From document / known")],
          ["estimated", t("Geschätzt", "Estimated")],
          [
            "pending",
            t(
              "Ausstehende Einnahme · ausgeschlossen",
              "Pending income · excluded",
            ),
          ],
        ],
      )}${field("source", t("Quelle / Annahme", "Source / assumption"), b.source, "text", "", 200)}</div>${info(t("Ausstehend ist nur für Einnahmen mit bekanntem Betrag möglich. Laufende Raten nur hier erfassen, wenn sie zu den Haushaltsausgaben gehören.", "Pending applies only to income with a known amount. Record recurring repayments here only if they are part of household costs."), "neutral")}${actions("budget", id)}`,
      (data) => {
        try {
          const amount = D.parseMoney(data.get("amount"), state.locale),
            certainty = data.get("certainty");
          if (
            certainty === "pending" &&
            (amount === null || data.get("kind") !== "income")
          )
            throw new Error("budget");
          const next = D.upsert(state, "budget", {
            ...b,
            label: data.get("label").trim(),
            kind: data.get("kind"),
            amount,
            frequency: data.get("frequency"),
            certainty: amount === null ? "unknown" : certainty,
            source: data.get("source").trim(),
          });
          closeDialog();
          commit(
            next,
            t("Haushaltsposten gespeichert.", "Household entry saved."),
          );
        } catch (err) {
          formError(errorMessage(err));
        }
      },
    );
  }
  function editDocument(id) {
    const d = state.documents.find((x) => x.id === id) || {
      id: crypto.randomUUID(),
      title: "",
      status: "missing",
      notes: "",
    };
    openDialog(
      id
        ? t("Unterlage bearbeiten", "Edit document")
        : t("Unterlage hinzufügen", "Add document"),
      t(
        "Nur eine Checkliste; keine Datei wird hochgeladen.",
        "Checklist only; no file is uploaded.",
      ),
      `${field("title", t("Unterlage", "Document"), d.title, "text", "", 180, true)}${select(
        "status",
        t("Status", "Status"),
        d.status,
        ["missing", "requested", "ready", "notneeded"].map((x) => [
          x,
          docStatus(x),
        ]),
      )}${textarea("notes", t("Notiz, zum Beispiel Aufbewahrungsort", "Note, for example where to find it"), d.notes)}${actions("documents", id)}`,
      (data) => {
        try {
          const next = D.upsert(state, "documents", {
            ...d,
            title: data.get("title").trim(),
            status: data.get("status"),
            notes: data.get("notes").trim(),
          });
          closeDialog();
          commit(next, t("Checkliste aktualisiert.", "Checklist updated."));
        } catch (err) {
          formError(errorMessage(err));
        }
      },
    );
  }
  function editTask(id) {
    const x = state.tasks.find((x) => x.id === id) || {
      id: crypto.randomUUID(),
      title: "",
      date: "",
      done: false,
      notes: "",
    };
    openDialog(
      id
        ? t("Schritt bearbeiten", "Edit step")
        : t("Schritt oder Frage ergänzen", "Add a step or question"),
      t(
        "Keine Nachricht wird versendet. Termine sind persönliche Erinnerungen.",
        "No message is sent. Dates are personal reminders.",
      ),
      `${field("title", t("Schritt / Frage", "Step / question"), x.title, "text", "", 180, true)}${field("date", t("Persönliches Erinnerungsdatum, optional", "Personal follow-up date, optional"), x.date, "date")}${textarea("notes", t("Notizen", "Notes"), x.notes)}${actions("tasks", id)}`,
      (data) => {
        try {
          const next = D.upsert(state, "tasks", {
            ...x,
            title: data.get("title").trim(),
            date: data.get("date"),
            notes: data.get("notes").trim(),
          });
          closeDialog();
          commit(next, t("Schritt gespeichert.", "Step saved."));
        } catch (err) {
          formError(errorMessage(err));
        }
      },
    );
  }
  function editCase() {
    openDialog(
      t("Dein Arbeitsbereich", "Your workspace"),
      t(
        "Ein Fallname genügt. Verwende in dieser Vorschau nur fiktive Daten.",
        "A case name is enough. Use fictional data only in this preview.",
      ),
      `${field("alias", t("Fallname", "Case name"), state.alias, "text", "", 80, true)}${field("appointment", t("Beratungstermin, optional", "Counselling appointment, optional"), state.appointment, "date")}${actions("", null)}`,
      (data) => {
        try {
          const next = D.validate({
            ...state,
            alias: data.get("alias").trim(),
            appointment: data.get("appointment"),
          });
          closeDialog();
          commit(next, t("Arbeitsbereich aktualisiert.", "Workspace updated."));
        } catch (err) {
          formError(errorMessage(err));
        }
      },
    );
  }
  function download(raw, type, name) {
    const url = URL.createObjectURL(new Blob([raw], { type })),
      a = document.createElement("a");
    a.href = url;
    a.download = name;
    document.body.appendChild(a);
    a.click();
    a.remove();
    setTimeout(() => URL.revokeObjectURL(url), 60000);
  }
  function setBusy(value) {
    busy = value;
    $("#dialog")
      .querySelectorAll("button,input,select,textarea")
      .forEach((el) => (el.disabled = value));
    if (value)
      $("#form-error")?.replaceChildren(
        document.createTextNode(t("Wird verarbeitet …", "Processing …")),
      );
  }
  async function withStorageLock(fn) {
    if (!navigator.locks?.request) throw new Error("storage-lock-unavailable");
    return navigator.locks.request("maze-prepare-storage", fn);
  }
  async function saveLocal() {
    if (busy) return;
    if (stale) {
      storageDialog(
        t(
          "Ein anderer Tab hat gespeichert. Sichere diese Sitzung als Datei oder öffne die aktuelle gespeicherte Vorbereitung.",
          "Another tab saved changes. Back up this session or open the current saved preparation.",
        ),
      );
      return;
    }
    if (!vaultKey) {
      if (localRaw) {
        storageDialog(
          t(
            "Ein gespeicherter Tresor existiert. Entsperre ihn zuerst oder sichere diese Sitzung als Datei.",
            "A saved vault exists. Unlock it first or back up this session to a file.",
          ),
        );
        return;
      }
      passphraseDialog("create");
      return;
    }
    busy = true;
    try {
      const raw = await V.seal(JSON.stringify(state), vaultKey, vaultSalt);
      expectedRevision = await withStorageLock(() =>
        V.save(localStorage, STORAGE, raw, expectedRevision),
      );
      localRaw = raw;
      dirty = false;
      render();
      toast(
        t(
          "Verschlüsselt auf diesem Gerät gespeichert.",
          "Saved encrypted on this device.",
        ),
      );
    } catch (err) {
      storageFailure(err);
    } finally {
      busy = false;
    }
  }
  function storageFailure(err) {
    if (err.message === "stale-vault") {
      stale = true;
      render();
    }
    toast(
      err.message === "stale-vault"
        ? t(
            "Ein anderer Tab hat gespeichert. Sichere diese Sitzung als Datei; lade dann den aktuellen Tresor.",
            "Another tab saved changes. Back up this session to a file, then load the current vault.",
          )
        : t(
            "Lokales Speichern ist fehlgeschlagen. Deine Sitzung bleibt offen. Erstelle eine verschlüsselte Dateisicherung.",
            "Local save failed. Your session is still open. Create an encrypted file backup.",
          ),
    );
  }
  function passphraseDialog(mode) {
    if (locked) {
      storageDialog();
      return;
    }
    openDialog(
      mode === "create"
        ? t("Sicher auf diesem Gerät speichern", "Save securely on this device")
        : t("Verschlüsselte Sicherung erstellen", "Create an encrypted backup"),
      t(
        "Mindestens 12 Zeichen. Bewahre die Passphrase getrennt auf; ohne sie ist keine Wiederherstellung möglich.",
        "At least 12 characters. Keep the passphrase separately; without it recovery is impossible.",
      ),
      `${field("pass", t("Neue Passphrase", "New passphrase"), "", "password", "", 256, true)}${field("confirm", t("Passphrase wiederholen", "Repeat passphrase"), "", "password", "", 256, true)}${info(t("Eine Dateisicherung ist unabhängig vom Browserspeicher. Für lokale Änderungen erneut auf „Speichern“ klicken. Berichte sind separat und unverschlüsselt.", "A file backup is independent of browser storage. Click Save again after local edits. Reports are separate and unencrypted."), "neutral")}${actions("", null, mode === "create" ? t("Verschlüsselt speichern", "Save encrypted") : t("Sicherung herunterladen", "Download backup"))}`,
      async (data) => {
        const pass = data.get("pass");
        if (pass.length < 12 || pass !== data.get("confirm")) {
          formError(
            t(
              "Mindestens 12 Zeichen verwenden und beide Eingaben gleich schreiben.",
              "Use at least 12 characters and match both entries.",
            ),
          );
          return;
        }
        setBusy(true);
        try {
          const result = await V.create(JSON.stringify(state), pass);
          if (mode === "create") {
            const rev = await withStorageLock(() =>
              V.save(localStorage, STORAGE, result.raw, null),
            );
            vaultKey = result.key;
            vaultSalt = result.salt;
            expectedRevision = rev;
            localRaw = result.raw;
            dirty = false;
          } else
            download(
              result.raw,
              "application/json",
              "maze-prepare-backup.json",
            );
          setBusy(false);
          closeDialog();
          render();
          toast(
            mode === "create"
              ? t(
                  "Verschlüsselt gespeichert. Änderungen später erneut speichern.",
                  "Saved encrypted. Save again after future changes.",
                )
              : t(
                  "Sicherung angefordert. Prüfe den Download, bevor du diese Sitzung schließt.",
                  "Backup download requested. Check the file before closing this session.",
                ),
          );
        } catch (err) {
          setBusy(false);
          formError(
            err.message === "crypto-unavailable"
              ? t(
                  "Verschlüsselung ist hier nicht verfügbar. Öffne die App über den lokalen Starter in einem aktuellen Browser.",
                  "Encryption is unavailable here. Open the app through the local launcher in a current browser.",
                )
              : err.message === "stale-vault"
                ? t(
                    "Ein Tresor wurde inzwischen gespeichert. Diese Sitzung wurde nicht überschrieben. Sichere sie als Datei.",
                    "A vault was saved in the meantime. This session was not overwritten. Back it up to a file.",
                  )
                : t(
                    "Speichern fehlgeschlagen. Die Sitzung bleibt erhalten. Nutze eine Dateisicherung oder den lokalen Starter.",
                    "Save failed. Your session is preserved. Use a file backup or the local launcher.",
                  ),
          );
        }
      },
    );
  }
  async function unlock(ev) {
    ev.preventDefault();
    if (busy) return;
    busy = true;
    const input = $("#unlock-pass"),
      pass = input.value;
    input.value = "";
    try {
      const raw = localStorage.getItem(STORAGE);
      if (!raw) throw new Error("missing");
      const result = await V.open(raw, pass);
      state = D.importWorkspace(result.plaintext);
      vaultKey = result.key;
      vaultSalt = result.salt;
      expectedRevision = result.revision;
      localRaw = raw;
      dirty = false;
      locked = false;
      stale = false;
      localError = false;
      undoState = null;
      reviewed = false;
      render();
      toast(t("Vorbereitung entsperrt.", "Preparation unlocked."));
    } catch {
      $("#unlock-error").textContent = t(
        "Entsperren fehlgeschlagen: Passphrase oder Datei prüfen. Gespeicherte Daten wurden nicht verändert.",
        "Unlock failed: check the passphrase or file. Saved data was not changed.",
      );
    } finally {
      busy = false;
    }
  }
  function storageDialog(message = "") {
    openDialog(
      t("Speichern & Sicherung", "Save & backup"),
      message ||
        t(
          "Nur verschlüsselte Daten werden lokal gespeichert. Änderungen werden erst durch „Speichern“ gesichert.",
          "Only encrypted data is stored locally. Changes are saved only when you click Save.",
        ),
      `<div class="settings-grid">${localRaw && (stale || !vaultKey) && !locked ? `<div class="settings-tile"><h3>${t("Gespeicherte Vorbereitung", "Saved preparation")}</h3><p>${t("Eine getrennte Sitzung ist geöffnet. Dein gespeicherter Tresor wurde nicht ersetzt.", "A separate session is open. Your saved vault was not replaced.")}</p>${button(t("Gespeicherte Vorbereitung öffnen", "Open saved preparation"), "open-saved", "primary")}</div>` : ""}${!locked ? `<div class="settings-tile"><h3>${t("Auf diesem Gerät", "On this device")}</h3><p>${vaultKey ? t("Dein Tresor ist entsperrt. Speichere Änderungen und sperre ihn, wenn du den Platz verlässt.", "Your vault is unlocked. Save changes and lock it when you step away.") : t("Eine Passphrase schützt den gespeicherten Tresor. Browserspeicher kann beim Aufräumen verloren gehen.", "A passphrase protects the saved vault. Browser storage can be lost when cleared.")}</p>${button(t("Verschlüsselt speichern", "Save encrypted"), "save-local", "primary", "", "lock")}${vaultKey ? button(t("Sperren", "Lock"), "lock", "ghost") + button(t("Lokalen Tresor entfernen", "Remove local vault"), "remove-vault", "ghost") : ""}</div><div class="settings-tile"><h3>${t("Tragbare Sicherung", "Portable backup")}</h3><p>${t("Eine verschlüsselte Datei zum Wiederherstellen oder Weiterarbeiten auf einem anderen Gerät.", "An encrypted file for recovery or continuing on another device.")}</p>${button(t("Sicherung erstellen", "Create backup"), "backup", "", "", "export")}</div>` : ""}<div class="settings-tile"><h3>${t("Sicherung wiederherstellen", "Restore a backup")}</h3><p>${t("Verschlüsselte JSON-Sicherung öffnen, prüfen und erst dann als Sitzung übernehmen. Eine lokale Speicherung wird nicht automatisch ersetzt.", "Open an encrypted JSON backup, review it and then load it into a session. Local saved data is not automatically replaced.")}</p><div class="field"><label for="backup-file">${t("Sicherungsdatei auswählen", "Choose backup file")}</label><input id="backup-file" type="file" accept=".json,application/json"></div></div>${!locked ? `<div class="settings-tile"><h3>${t("Neu beginnen", "Start fresh")}</h3><p>${t("Leere oder neue Beispielsitzung öffnen. Die gespeicherte Datei bleibt erhalten; sichere vorher Änderungen.", "Open a blank or new sample session. Saved local data is kept; back up changes first.")}</p>${button(t("Leere Sitzung", "Blank session"), "new-blank")}${button(t("Beispiel neu laden", "Reload sample"), "new-demo", "ghost")}</div>` : ""}</div><p class="footer-note">${t("Keine Passphrase-Wiederherstellung und keine Cloud-Synchronisierung. Verschlüsselung schützt keine bereits heruntergeladenen Klartextberichte und kein entsperrtes Gerät.", "No passphrase recovery or cloud sync. Encryption does not protect previously downloaded plaintext reports or an unlocked device.")}</p>`,
      null,
    );
  }
  async function importFile(file) {
    if (!file) return;
    if (file.size > V.MAX_BYTES) {
      toast(
        t(
          "Die Sicherung ist zu groß. Maximal 3 MB.",
          "Backup too large. Maximum 3 MB.",
        ),
      );
      return;
    }
    try {
      const raw = await file.text();
      V.inspect(raw);
      openDialog(
        t("Sicherung öffnen", "Open backup"),
        t(
          "Die aktuelle Sitzung bleibt unverändert, bis du die geprüfte Sicherung übernimmst.",
          "The current session stays unchanged until you confirm the reviewed backup.",
        ),
        `${field("pass", t("Passphrase der Sicherung", "Backup passphrase"), "", "password", "", 256, true)}${actions("", null, t("Entschlüsseln und prüfen", "Decrypt and review"))}`,
        async (data) => {
          setBusy(true);
          try {
            const result = await V.open(raw, data.get("pass")),
              next = D.importWorkspace(result.plaintext),
              a = D.summarize(next);
            setBusy(false);
            openDialog(
              t("Sicherung geprüft", "Backup checked"),
              t(
                "Das ersetzt nur die geöffnete Sitzung. Nicht gespeicherte Änderungen vorher sichern.",
                "This replaces only the open session. Back up unsaved changes first.",
              ),
              `<div class="next-callout"><strong>${e(next.alias)}</strong>${a.claimCount} ${t("Forderungen", "claims")} · ${next.budget.length} ${t("Haushaltsposten", "household entries")} · ${next.tasks.length} ${t("Schritte", "steps")}</div>${info(t("Die Sicherung wurde entschlüsselt und auf Struktur geprüft. Das bestätigt keine inhaltliche Richtigkeit.", "The backup was decrypted and structurally checked. This does not establish that its content is correct."), "neutral")}${actions("", null, t("Als Sitzung übernehmen", "Load into session"))}`,
              () => {
                closeDialog();
                locked = false;
                commit(
                  next,
                  t(
                    "Sicherung als Sitzung geöffnet. Lokale Speicherung blieb erhalten.",
                    "Backup loaded into a session. Local saved vault was kept.",
                  ),
                  false,
                );
              },
            );
          } catch {
            setBusy(false);
            formError(
              t(
                "Sicherung konnte nicht geöffnet werden. Passphrase, Dateiformat oder Unversehrtheit prüfen. Die Sitzung blieb unverändert.",
                "Could not open backup. Check passphrase, format or file integrity. The session stayed unchanged.",
              ),
            );
          }
        },
      );
    } catch {
      toast(
        t(
          "Keine gültige Maze-Bank-Sicherung. Die Sitzung blieb unverändert.",
          "Not a valid Maze Bank backup. The session stayed unchanged.",
        ),
      );
    }
  }
  function confirmReset(useDemo) {
    openDialog(
      t("Neue Sitzung öffnen?", "Open a new session?"),
      t(
        "Nicht gesicherte Änderungen gehen beim Wechsel verloren. Deine lokale gespeicherte Datei bleibt erhalten.",
        "Unsaved session changes will be lost. Your locally saved file is kept.",
      ),
      `<p class="small muted">${t("Erstelle vorher eine Sicherung, wenn du die aktuelle Vorbereitung behalten möchtest.", "Create a backup first if you want to keep the current preparation.")}</p>${actions("", null, t("Neue Sitzung öffnen", "Open new session"))}`,
      () => {
        closeDialog();
        vaultKey = null;
        vaultSalt = null;
        locked = false;
        dirty = false;
        reviewed = false;
        undoState = null;
        state = useDemo ? D.demo(state.locale) : D.empty(state.locale);
        view = "overview";
        render(true);
        toast(t("Neue Sitzung geöffnet.", "New session opened."));
      },
    );
  }
  function lock() {
    if (!vaultKey) return;
    if (dirty) {
      openDialog(
        t("Änderungen noch nicht gespeichert", "Changes are not saved yet"),
        t(
          "Speichere die Änderungen vor dem Sperren.",
          "Save your changes before locking.",
        ),
        `<div class="dialog-actions">${button(t("Abbrechen", "Cancel"), "close-dialog")}${button(t("Speichern und sperren", "Save and lock"), "save-lock", "primary")}</div>`,
        null,
      );
      return;
    }
    vaultKey = null;
    vaultSalt = null;
    state = D.empty(state.locale);
    undoState = null;
    reviewed = false;
    locked = true;
    closeDialog();
    render();
  }
  function about() {
    openDialog(
      t("Über Maze Bank Prepare", "About Maze Bank Prepare"),
      t(
        "Eine Forschungsvorschau für die Vorbereitung auf eine deutsche Schuldnerberatung.",
        "A research preview for preparing for German debt counselling.",
      ),
      `<div class="about-copy"><p>${t("Diese Vorschau ist für fiktive Daten vorgesehen. Sie ist keine Bank, keine Rechts- oder Finanzberatung und keine Zahlungsplattform. Es wurden noch keine Nutzungs- oder Beratungsstellenstudien durchgeführt.", "This preview is for fictional data. It is not a bank, legal or financial advice, or a payment platform. User and counselling-service studies have not yet been conducted.")}</p><h3>${t("Was gespeichert wird", "What is stored")}</h3><ul><li>${t("Sitzung: Angaben im Arbeitsspeicher, bis du die Seite schließt.", "Session: entries in memory until the page closes.")}</li><li>${t("Lokaler Tresor: nur nach „Speichern“, verschlüsselt mit deiner Passphrase. Keine automatische Speicherung.", "Local vault: only after Save, encrypted using your passphrase. No automatic saving.")}</li><li>${t("Sicherung: verschlüsselte Datei. Bericht: unverschlüsselte Datei nach deiner Prüfung.", "Backup: encrypted file. Report: unencrypted file after your review.")}</li><li>${t("Keine Bankdaten-Anbindung, Analytik, Werbung, KI- oder Cloud-Abfrage. Keine Dokumentdateien werden hochgeladen.", "No bank link, analytics, ads, AI or cloud requests. No document files are uploaded.")}</li></ul><h3>${t("Grenzen", "Limits")}</h3><p>${t("Keine unabhängige Sicherheitsprüfung; lokale Verschlüsselung schützt nicht vor einem kompromittierten Gerät oder einer entsperrten Sitzung. Passphrasen können nicht wiederhergestellt werden. Eine Checkliste ist keine Voraussetzung, Hilfe zu suchen.", "No independent security audit; local encryption does not protect a compromised device or unlocked session. Passphrases cannot be recovered. A checklist is not a prerequisite for seeking help.")}</p><p>${t("Die Startcheckliste orientiert sich an", "The starting checklist draws on")} <a href="https://www.iska-nuernberg.de/schuldnerberatung/vorbereitung.html" target="_blank" rel="noopener noreferrer">ISKA Nürnberg</a>. ${t("Eine weitere freie Vorlage gibt es bei der", "Another free template is provided by")} <a href="https://www.verbraucherzentrale.nrw/wissen/geld-versicherungen/kredit-schulden-insolvenz/glaeubigeruebersicht-16335" target="_blank" rel="noopener noreferrer">Verbraucherzentrale</a>.</p><p class="small muted">${t("Portfolio: Ahmad Sajjad Siddiqui. Spätere Erweiterung mit Codex-Unterstützung; ursprüngliche Java-Kursarbeit separat im Repository.", "Portfolio: Ahmad Sajjad Siddiqui. Later extension built with Codex assistance; original Java coursework is separate in the repository.")}</p></div>`,
      null,
      true,
    );
  }
  async function act(action, id, target) {
    if (busy) return;
    if (action === "nav") navigate(id);
    else if (action === "language") {
      state = { ...state, locale: state.locale === "de" ? "en" : "de" };
      if (!locked) {
        dirty = true;
        reviewed = false;
      }
      render();
    } else if (action === "case") editCase();
    else if (action === "about") about();
    else if (action === "storage") storageDialog();
    else if (action === "close-dialog") closeDialog();
    else if (action === "add-claim" || action === "edit-claim") editClaim(id);
    else if (action === "add-budget") editBudget(null, id);
    else if (action === "edit-budget") editBudget(id);
    else if (action === "add-document" || action === "edit-document")
      editDocument(id);
    else if (action === "add-task" || action === "edit-task") editTask(id);
    else if (action === "filter") {
      filter = id;
      render();
    } else if (action === "toggle-task") {
      const item = state.tasks.find((x) => x.id === id);
      commit(
        D.upsert(state, "tasks", { ...item, done: target.checked }),
        t("Schritt aktualisiert.", "Step updated."),
      );
    } else if (action === "remove") {
      const [key, item] = id.split(":");
      try {
        const next = D.remove(state, key, item);
        closeDialog();
        commit(next, t("Eintrag entfernt.", "Entry removed."));
      } catch (err) {
        formError(errorMessage(err));
      }
    } else if (action === "undo" && undoState) {
      const next = undoState;
      undoState = null;
      state = D.validate(next);
      dirty = true;
      reviewed = false;
      render();
      toast(t("Änderung rückgängig gemacht.", "Change undone."));
    } else if (action === "save-local") {
      closeDialog();
      await saveLocal();
    } else if (action === "backup") passphraseDialog("backup");
    else if (action === "lock") lock();
    else if (action === "save-lock") {
      closeDialog();
      await saveLocal();
      if (!dirty) lock();
    } else if (action === "remove-vault") {
      openDialog(
        t("Lokalen Tresor entfernen?", "Remove local vault?"),
        t(
          "Nur diese lokale gespeicherte Kopie wird entfernt. Die geöffnete Sitzung und Dateisicherungen bleiben erhalten. Sichere sie zuerst, wenn du weiterarbeiten möchtest.",
          "Only the locally saved copy is removed. The open session and file backups remain. Back up first if you want to continue later.",
        ),
        field(
          "confirmation",
          t("Zum Bestätigen REMOVE eingeben", "Type REMOVE to confirm"),
        ) + actions("", null, t("Lokale Kopie entfernen", "Remove local copy")),
        async (data) => {
          if (data.get("confirmation") !== "REMOVE") {
            formError(
              t("Zum Bestätigen REMOVE eingeben.", "Type REMOVE to confirm."),
            );
            return;
          }
          try {
            await withStorageLock(() => {
              const current = localStorage.getItem(STORAGE);
              if (
                !current ||
                V.inspect(current).v.revision !== expectedRevision
              )
                throw new Error("stale-vault");
              localStorage.removeItem(STORAGE);
            });
            localRaw = null;
            vaultKey = null;
            vaultSalt = null;
            expectedRevision = null;
            dirty = true;
            closeDialog();
            render();
            toast(
              t(
                "Lokale Kopie entfernt. Die Sitzung bleibt offen.",
                "Local copy removed. The session stays open.",
              ),
            );
          } catch (err) {
            formError(
              t(
                "Nicht entfernt. Der Tresor wurde geändert oder der Speicher ist nicht zugänglich.",
                "Not removed. The vault changed or storage is unavailable.",
              ),
            );
          }
        },
      );
    } else if (action === "sample-session") {
      locked = false;
      state = D.demo(state.locale);
      dirty = false;
      view = "overview";
      render();
    } else if (action === "open-saved") {
      openDialog(
        t("Gespeicherte Vorbereitung öffnen?", "Open saved preparation?"),
        t(
          "Sichere diese Sitzung zuerst. Danach wird die gespeicherte Vorbereitung zur Entsperrung geöffnet.",
          "Back up this session first. The saved preparation will then open for unlocking.",
        ),
        actions("", null, t("Zur Entsperrung", "Open unlock screen")),
        () => {
          closeDialog();
          dirty = false;
          vaultKey = null;
          vaultSalt = null;
          undoState = null;
          reviewed = false;
          locked = true;
          render();
        },
      );
    } else if (action === "new-blank") confirmReset(false);
    else if (action === "new-demo") confirmReset(true);
    else if (action === "download-report" || action === "print-report") {
      if (!reviewed) {
        toast(
          t(
            "Bitte die Vorschau prüfen und das Kontrollkästchen bestätigen.",
            "Review the preview and confirm the checkbox first.",
          ),
        );
        $("#review-check")?.focus();
        return;
      }
      if (action === "download-report") {
        download(
          R.document(state),
          "text/html;charset=utf-8",
          "maze-prepare-overview.html",
        );
        toast(
          t(
            "Download angefordert. Bericht unverschlüsselt; sicher aufbewahren.",
            "Download requested. Report is unencrypted; keep it safely.",
          ),
        );
      } else window.print();
    }
  }
  document.addEventListener("click", (ev) => {
    const target = ev.target.closest("[data-action]");
    if (target)
      act(target.dataset.action, target.dataset.id || "", target).catch(() =>
        toast(
          t(
            "Die Aktion konnte nicht abgeschlossen werden. Deine Sitzung bleibt offen.",
            "Could not finish the action. Your session remains open.",
          ),
        ),
      );
  });
  document.addEventListener("input", (ev) => {
    if (ev.target.id === "claim-search") {
      search = ev.target.value;
      $("#claim-results").innerHTML = claimResults();
    }
  });
  document.addEventListener("change", (ev) => {
    if (ev.target.id === "review-check") reviewed = ev.target.checked;
    if (ev.target.id === "backup-file") importFile(ev.target.files[0]);
  });
  $("#dialog").addEventListener("cancel", (ev) => {
    if (busy) ev.preventDefault();
  });
  window.addEventListener("beforeunload", (ev) => {
    if (dirty) {
      ev.preventDefault();
      ev.returnValue = "";
    }
  });
  window.addEventListener("storage", (ev) => {
    if (ev.key === STORAGE && vaultKey) {
      stale = true;
      render();
      toast(
        t(
          "Der gespeicherte Tresor hat sich in einem anderen Tab geändert. Sichere diese Sitzung als Datei vor dem Neuladen.",
          "The saved vault changed in another tab. Back up this session to a file before reloading.",
        ),
      );
    }
  });
  render();
  if (localError && !locked)
    toast(
      t(
        "Browserspeicher ist nicht verfügbar. Eine verschlüsselte Dateisicherung ist weiterhin möglich.",
        "Browser storage is unavailable. An encrypted file backup may still be available.",
      ),
    );
})();
