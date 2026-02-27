---
layout: default
title: Git – Praxis-Doku
---

# Git – Praxis-Dokumentation

Hier sammle ich grundlegende Git-Befehle, typische Workflows und kleine Praxisbeispiele, die im Alltag bei der Versionsverwaltung nützlich sind.

> **Hinweis:** Alle Beispiele sind bewusst einfach gehalten und direkt im Terminal nachvollziehbar.

---

## Warum Git?

Git ist für mich das zentrale Werkzeug, um Änderungen nachvollziehbar, reproduzierbar und strukturiert zu verwalten.

Gerade bei:

- Skripten
- Konfigurationsdateien
- Systemdiensten
- Infrastruktur-Projekten

ist es wichtig, jederzeit zu wissen:

- Was wurde geändert?
- Wann wurde es geändert?
- Warum wurde es geändert?
- Wer hat es geändert?

Git liefert diese Historie automatisch.

---

## Vorteile im Alltag

- vollständige Versionshistorie
- schnelle Branches für Experimente
- klare Commit-Struktur
- Vergleich zwischen beliebigen Ständen
- sicheres Rückgängigmachen von Änderungen
- Zusammenarbeit ohne Überschreiben fremder Arbeit

Ein typischer Vorteil:  
Statt eine Datei mehrfach mit `final.sh`, `final2.sh`, `wirklich_final.sh` zu speichern, verwaltet Git jede Änderung sauber in der Historie.

---

## Typischer Workflow

1. Änderungen durchführen  
2. Status prüfen (`git status`)  
3. Änderungen hinzufügen (`git add`)  
4. Commit erstellen (`git commit -m "Beschreibung"`)  
5. Historie prüfen (`git log --oneline`)

---

## Artikel

- [lsyncd – Kurz & kompakt](lsyncd-kurz.md)

---

Diese Seite dient als praktische Referenz für den täglichen Einsatz.
