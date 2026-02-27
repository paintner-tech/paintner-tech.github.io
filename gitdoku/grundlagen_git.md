---
layout: default
title: Git Grundlagen und Praxis
---

# Git – Grundlagen und praktische Anwendung

Diese Seite beschreibt die grundlegende Arbeit mit Git:
- Konfiguration
- Repository erstellen
- Änderungen verfolgen
- Commits verwalten
- Unterschiede anzeigen
- Auf ältere Stände wechseln

---

# 1. Grundkonfiguration

Aktuelle Git-Konfiguration anzeigen:

```bash
git config -l
```

Globale Identität setzen:

```bash
git config --global user.email "user@example.com"
git config --global user.name  "username"
```

Diese Werte werden in jedem Commit gespeichert.

---

# 2. Zentrale Begriffe

## Commit

Ein Commit ist eine Momentaufnahme (Snapshot) des aktuellen Projektzustands.  
Er speichert alle seit dem letzten Commit vorgenommenen Änderungen dauerhaft im Repository.

---

## Branch

Ein Branch ist eine parallele Entwicklungslinie innerhalb eines Repositories.

Branches ermöglichen:
- Entwicklung neuer Features
- Bugfixes
- Experimente ohne Beeinflussung der Hauptlinie (`main` oder `master`)

---

## Repository

Ein Repository (Repo) ist eine Sammlung von Dateien inklusive vollständiger Versionshistorie.

Ein Git-Repository speichert:
- Aktuellen Stand
- Alle vorherigen Versionen
- Commit-Historie
- Branches

---

# 3. Beispiel: Repository erstellen

Projektverzeichnis vorbereiten:

```bash
mkdir project
cd project
git init
```

Status anzeigen:

```bash
git status
```

---

# 4. Dateien hinzufügen

Alle Dateien hinzufügen:

```bash
git add --all
```

Einzelne Datei hinzufügen:

```bash
git add <datei>
```

Datei entfernen:

```bash
git rm <datei>
```

Datei umbenennen:

```bash
git mv <alt> <neu>
```

---

# 5. Commit erstellen

Commit mit Nachricht:

```bash
git commit -m "Initial commit"
```

Alle geänderten Dateien automatisch hinzufügen und committen:

```bash
git commit -am "Änderung"
```

---

# 6. Änderungen prüfen

## Status prüfen

```bash
git status
```

Zeigt:
- Untracked files
- Geänderte Dateien
- Gestagte Dateien

---

## Nicht gestagte Änderungen anzeigen

```bash
git diff
```

---

## Gestagte Änderungen anzeigen

```bash
git diff --cached
```

---

# 7. Historie anzeigen

Kurzform:

```bash
git log --oneline
```

Mit Details:

```bash
git log
```

Mit Statistik:

```bash
git log --stat
```

Mit Patch (Änderungsdetails):

```bash
git log -p
```

Ein bestimmtes Commit anzeigen:

```bash
git show <commit-id>
```

Nur Commit-Kommentar anzeigen:

```bash
git show -s <commit-id>
```

---

# 8. Unterschiede zwischen Commits

Zwischen zwei Commits vergleichen:

```bash
git diff <commit1> <commit2>
```

Unterschied zum aktuellen Stand:

```bash
git diff <commit-id>
```

---

# 9. Commit nachträglich ändern

Letzten Commit bearbeiten:

```bash
git commit --amend
```

Autor zurücksetzen:

```bash
git commit --amend --reset-author
```

---

# 10. Auf ältere Version wechseln

| Befehl | Dateien behalten | Historie behalten | Zweck |
|--------|------------------|------------------|-------|
| `git checkout <id>` | Ja | Ja | Testen |
| `git reset --soft <id>` | Ja (im Staging) | Nein | Commit neu schreiben |
| `git reset --mixed <id>` | Ja (Arbeitsverzeichnis) | Nein | Historie ändern |
| `git reset --hard <id>` | Nein | Nein | Komplettes Zurücksetzen |
| `git revert <id>` | Ja | Ja | Sicheres Rückgängigmachen |

---

# 11. Wichtige Praxisbefehle

Blame anzeigen:

```bash
git blame <datei>
```

Rebase (Historie neu ordnen):

```bash
git rebase
```

---

# Zusammenfassung

Typischer Workflow:

```bash
git status
git add <datei>
git commit -m "Beschreibung"
git log --oneline
```

Git arbeitet mit drei Bereichen:

1. Working Directory  
2. Staging Area  
3. Repository  

Ein sauberer Workflow besteht aus:
- kleine, klare Commits  
- sinnvolle Commit-Nachrichten  
- regelmäßige Statuskontrolle  
- Historie bewusst pflegen  

---
