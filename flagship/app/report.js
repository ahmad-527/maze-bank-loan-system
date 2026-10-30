(function (root, factory) {
  const api = factory(
    typeof module === "object" && module.exports
      ? require("./domain.js")
      : root.MazeDomain,
  );
  if (typeof module === "object" && module.exports) module.exports = api;
  else root.MazeReport = api;
})(globalThis, function (D) {
  "use strict";
  const esc = (v) =>
    String(v ?? "").replace(
      /[&<>"']/g,
      (c) =>
        ({
          "&": "&amp;",
          "<": "&lt;",
          ">": "&gt;",
          '"': "&quot;",
          "'": "&#39;",
        })[c],
    );
  function content(state) {
    const s = D.validate(state),
      a = D.summarize(s),
      de = s.locale === "de",
      t = (x, y) => (de ? x : y);
    const amount = (v) =>
      v === null ? t("Unbekannt", "Unknown") : D.formatMoney(v, s.locale);
    const certainty = {
      confirmed: t("Aus Unterlage übernommen", "Recorded from document"),
      estimated: t("Geschätzt", "Estimated"),
      unknown: t("Unbekannt", "Unknown"),
      pending: t("Ausstehend · nicht eingerechnet", "Pending · excluded"),
    };
    const frequencies = {
      monthly: t("monatlich", "monthly"),
      weekly: t("wöchentlich", "weekly"),
      fortnightly: t("alle 2 Wochen", "every 2 weeks"),
      quarterly: t("vierteljährlich", "quarterly"),
      yearly: t("jährlich", "yearly"),
    };
    const statuses = {
      missing: t("Fehlt noch", "Still needed"),
      requested: t("Angefragt", "Requested"),
      ready: t("Vorhanden", "Ready"),
      notneeded: t("Nicht zutreffend", "Not applicable"),
    };
    const table = (heads, rows) =>
      `<div class="report-table"><table><thead><tr>${heads.map((x) => `<th scope="col">${esc(x)}</th>`).join("")}</tr></thead><tbody>${rows.join("") || `<tr><td colspan="${heads.length}">${t("Noch keine Einträge.", "No entries yet.")}</td></tr>`}</tbody></table></div>`;
    const tr = (cells) =>
      `<tr>${cells.map((x) => `<td>${x}</td>`).join("")}</tr>`;
    return `<article class="report-content"><p class="report-kicker">MAZE BANK / PREPARE · ${t("FORSCHUNGSVORSCHAU · FIKTIVE DATEN", "RESEARCH PREVIEW · FICTIONAL DATA")}</p><h1>${t("Meine Terminvorbereitung", "My appointment preparation")}</h1><p>${esc(s.alias)} · ${t("Erstellt", "Created")} ${esc(new Date().toISOString().slice(0, 10))}</p><p>${t("Termin", "Appointment")}: ${esc(s.appointment || t("Noch offen", "Not set"))}</p><div class="report-note">${t("Persönliche Arbeitsübersicht. Beträge und Zuordnungen sind Angaben der nutzenden Person, keine rechtliche Prüfung oder Zahlungsanweisung. Unbekannte Angaben bleiben offen. Unterlagen und Originalschreiben zum Termin mitbringen.", "Personal working overview. Amounts and relationships are user records, not a legal assessment or payment instruction. Unknown information stays open. Bring supporting documents and original letters to the appointment.")}</div>
      <h2>1. ${t("Forderungen und Kontakte", "Claims and contacts")}</h2><p><strong>${t("Summe erfasster bekannter Beträge", "Sum of recorded known amounts")}: ${amount(a.claimsTotal)}</strong> · ${a.unknownClaims} ${t(a.unknownClaims === 1 ? "Betrag unbekannt" : "Beträge unbekannt", a.unknownClaims === 1 ? "amount unknown" : "amounts unknown")} · ${a.repeatedClaims} ${t("verknüpfte Wiederholungen nicht erneut eingerechnet", "linked repeated records excluded from double counting")}. ${t("Keine Bestätigung, dass diese Beträge geschuldet sind.", "Does not establish that these amounts are owed.")}</p>
      ${table(
        [
          t("Gläubiger / Kontakt", "Creditor / contact"),
          t("Betrag / Stand", "Amount / as of"),
          t("Einordnung / Herkunft", "Status / source"),
        ],
        s.claims.map((c) =>
          tr([
            `<strong>${esc(c.creditor)}</strong><br>${esc(c.collector)}<br>${esc(c.reference)}`,
            `${amount(c.amount)}<br>${esc(c.asOf || t("Stand unbekannt", "Date unknown"))}`,
            `${esc(certainty[c.certainty])}${c.disputed ? "<br>" + t("Bestritten / zu klären", "Disputed / to clarify") : ""}${c.repeatOf ? "<br>" + t("Wiederholung zu", "Repeated record of") + ": " + esc(s.claims.find((x) => x.id === c.repeatOf).creditor) : ""}<br>${esc(c.source || t("Quelle noch offen", "Source not recorded"))}<br>${esc(c.notes)}`,
          ]),
        ),
      )}
      <h2>2. ${t("Haushalt · Monatsdurchschnitt", "Household · monthly average")}</h2><p>${t("Erfasste Einnahmen", "Recorded income")}: ${amount(a.income)} · ${t("Erfasste Ausgaben", "Recorded costs")}: ${amount(a.expenses)} · ${t("Rechnerische Differenz", "Arithmetic difference")}: ${amount(a.difference)}.</p><p>${a.unknownBudget} ${t("unbekannte Posten", "unknown entries")}; ${a.estimatedBudget} ${t("geschätzte Posten", "estimated entries")}; ${t("ausstehende Einnahmen ausgeschlossen", "pending income excluded")}: ${amount(a.pendingIncome)}.</p>
      ${table(
        [
          t("Posten", "Entry"),
          t("Originalbetrag / Rhythmus", "Original amount / frequency"),
          t("Monatsdurchschnitt", "Monthly average"),
          t("Einordnung / Quelle", "Status / source"),
        ],
        s.budget.map((b) =>
          tr([
            `${esc(b.label)}<br>${b.kind === "income" ? t("Einnahme", "Income") : t("Ausgabe", "Cost")}`,
            `${amount(b.amount)} · ${esc(frequencies[b.frequency])}`,
            b.certainty === "pending"
              ? t("Nicht eingerechnet", "Excluded")
              : amount(D.monthly(b.amount, b.frequency)),
            `${esc(certainty[b.certainty])}<br>${esc(b.source)}`,
          ]),
        ),
      )}
      <p class="report-note">${t("Umrechnung je Posten: Woche ×52÷12, zwei Wochen ×26÷12, Quartal ÷3, Jahr ÷12; auf Cent kaufmännisch gerundet. Eine Durchschnittsrechnung zeigt keine Fälligkeiten und keinen sicheren Zahlungs- oder Tilgungsspielraum. Laufende Ausgaben hier erfassen; Forderungsbeträge oben sind keine zusätzlichen monatlichen Ausgaben.", "Conversion per entry: weekly ×52÷12, fortnightly ×26÷12, quarterly ÷3, annual ÷12; half-up to cents. An average does not show cash timing or money safely available for spending or repayments. Record recurring costs here; claim balances above are not additional monthly costs.")}</p>
      <h2>3. ${t("Unterlagen", "Documents")}</h2>${table(
        [t("Unterlage", "Document"), t("Status / Notiz", "Status / note")],
        s.documents.map((d) =>
          tr([esc(d.title), esc(statuses[d.status]) + "<br>" + esc(d.notes)]),
        ),
      )}<p>${t("Nur Checkliste und Notizen; keine Originaldateien angehängt. Anforderungen mit der gewählten Beratungsstelle abstimmen.", "Checklist and notes only; no original files attached. Check requirements with your chosen counselling service.")}</p>
      <h2>4. ${t("Nächste Schritte und Fragen", "Next steps and questions")}</h2>${table(
        [
          t("Schritt / Frage", "Step / question"),
          t("Persönliches Datum", "Personal follow-up date"),
          t("Status / Notiz", "Status / note"),
        ],
        s.tasks.map((x) =>
          tr([
            esc(x.title),
            esc(x.date || t("Offen", "Not set")),
            t(x.done ? "Erledigt" : "Offen", x.done ? "Done" : "Open") +
              "<br>" +
              esc(x.notes),
          ]),
        ),
      )}
      <footer>${t("Manuelle Erinnerungstermine sind keine berechneten Rechtsfristen. Diese Vorschau wurde noch nicht durch Ratsuchende oder Beratungsstellen validiert. Eine vollständige Übersicht ist keine Voraussetzung, Hilfe zu suchen.", "Manual follow-up dates are not calculated legal deadlines. This preview has not yet been validated with clients or counselling services. A complete overview is not a prerequisite for seeking help.")}</footer></article>`;
  }
  function document(state) {
    return `<!doctype html><html lang="${state.locale}"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><meta http-equiv="Content-Security-Policy" content="default-src 'none'; style-src 'unsafe-inline'; img-src 'none'; base-uri 'none'; form-action 'none'"><title>Maze Bank Prepare — ${esc(state.alias)}</title><style>body{font:15px/1.6 system-ui,sans-serif;color:#17223b;margin:40px auto;padding:0 24px;max-width:1000px}h1{font-size:32px;line-height:1.2}h2{margin-top:32px;font-size:21px}.report-kicker{font-size:12px;letter-spacing:.12em}.report-note{padding:16px;background:#f2f3f8;border-left:3px solid #5145cf}table{border-collapse:collapse;width:100%;font-size:13px;table-layout:fixed}th,td{text-align:left;vertical-align:top;padding:12px;border-bottom:1px solid #cdd2df;overflow-wrap:anywhere;white-space:pre-wrap}th{background:#f4f5fa}footer{margin-top:32px;font-size:12px;color:#42516b}@media print{body{margin:0;padding:0;font-size:10pt;max-width:none}h2{break-after:avoid}tr{break-inside:avoid}.report-note{background:none}thead{display:table-header-group}@page{size:A4;margin:16mm}}</style></head><body>${content(state)}</body></html>`;
  }
  return { esc, content, document };
});
