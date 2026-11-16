---
layout: default
title: Git Beispiel aus der Praxis / Arbeiten mit work und bare
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)

Git Workflow & Repository Setup
Für meine Projekte  nutzen ich ein strukturiertes Setup aus einem zentralen Bare-Repository (bare/) und einem lokalen Arbeitsverzeichnis (work/).

Folgend eine kurze Erklärung mit Bespielen

---
📂 Verzeichnisstruktur auf dem Zielserver
Im Projektverzeichnis (/<projektname>/) liegt die Standardstruktur wie folgt vor:

bare/ — Das zentrale, nackte Remote-Repository (git init --bare), das als Single Source of Truth dient.

work/ — Das ausgecheckte Arbeitsverzeichnis (geklont aus dem bare-Ordner), in dem die eigentliche Entwicklung und Skript-Pflege stattfindet.

** bare Initialisieren

```bash
cd /projektname
mkdir bare
cd bare
init bare
```

** bare als remote festlege

```
sudo git remote set-url hostname user@ip:/projekt/bare
```
---

🔄 Workflow: Arbeiten, Pushen & Aktualisieren
Änderungen ins zentrale Repo pushen (vom Dev-Host):

Bash
git push hostname master
Änderungen ins Arbeitsverzeichnis (work) übernehmen:
Nach einem Push in das bare-Repository muss das work-Verzeichnis auf dem Zielserver nicht neu geklont werden. Ein einfacher Pull reicht aus:

Beispiel:

Datei README.txt wurde hinzugeügt. Commit ausgeführt.

```bash
ptops@pt-lab01:/projekt$ sudo git commit -m "README.txt hinzugeügt"
[master 03a645b] README.txt hinzugeügt
 1 file changed, 0 insertions(+), 0 deletions(-)
 create mode 100644 README.txt
ptops@pt-lab01:/var/hardwareueberwachung$
```

Push ins bare
```
ptops@pt-lab01:/projekt$ git push hostname
user@192.0.2.1's password:
Objekte aufzählen: 4, fertig.
Zähle Objekte: 100% (4/4), fertig.
Delta-Kompression verwendet bis zu 2 Threads.
Komprimiere Objekte: 100% (2/2), fertig.
Schreibe Objekte: 100% (3/3), 299 Bytes | 299.00 KiB/s, fertig.
Gesamt 3 (Delta 1), Wiederverwendet 0 (Delta 0), Pack wiederverwendet 0
To 192.0.2.1:/prj/ztg/12_hardwareueberwachung/bare
   97619f7..03a645b  master -> master

ptops@pt-lab01:/projket$

Änderungen ins work übernehmen
```bash
usern@hostname:/prj/ztg/12_hardwareueberwachung/work$ git pull
remote: Objekte aufzählen: 4, Fertig.
remote: Zähle Objekte: 100% (4/4), Fertig.
remote: Komprimiere Objekte: 100% (2/2), Fertig.
remote: Gesamt 3 (Delta 1), Wiederverwendet 0 (Delta 0)
Entpacke Objekte: 100% (3/3), Fertig.
Von /projekt/bare
   97619f7..03a645b  master     -> origin/master
Aktualisiere 97619f7..03a645b
Fast-forward
 README.txt | 0
 1 file changed, 0 insertions(+), 0 deletions(-)
 create mode 100644 README.txt
user@hostname:/projekt/work$
```


