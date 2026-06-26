---
layout: default
title: Git Beispiel aus der Praxis
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)


# Globale Identität setzen
```bash
ptops@pt-lab01:/var/repos$ git config --global user.email "ptops.example.com"
ptops@pt-lab01:/var/repos$ git config --global user.name "ptops"
```


# git - Repo clonen
```
ptops@pt-lab01:/var/repos$ sudo git clone root@192.0.2.10:/opt/warmstandby
Klone nach 'warmstandby' …
root@192.0.2.10's password:
remote: Enumerating objects: 29, done.
remote: Counting objects: 100% (29/29), done.
remote: Compressing objects: 100% (29/29), done.
remote: Total 29 (delta 1), reused 0 (delta 0), pack-reused 0
Empfange Objekte: 100% (29/29), 382.23 KiB | 21.23 MiB/s, fertig.
Löse Unterschiede auf: 100% (1/1), fertig.
ptops@pt-lab01:/var/repos$
```

# Remote host konfigurieren
```bash
ptops@pt-lab01:/var/repos/warmstandby$ git remote add origin git@github.com:paintner-tech/warmstandby.git
```

# Remote host ändern
--> alten Remotehost löschen und neuen anlegen
```
ptops@pt-lab01:/var/repos/warmstandby$ git remote remove origin
ptops@pt-lab01:/var/repos/warmstandby$ git remote add origin git@github.com::paintner-tech/warmstandby.git
```

# Git Repository von Linux-Host nach GitHub pushen (SSH über Port 443)

## ssh Konfigurieren
* config in .ssh mit folgendem Inhalt anlegen
* Key muss vorhanden sein 

```bash
Host github.com
  HostName ssh.github.com
  Port 443
  User git
  IdentityFile ~/.ssh/id_ed25519
```
Test
```bash
ptops@pt-lab01:/var/repos/warmstandby$ ssh -T git@github.com
Hi paintner-tech! You've successfully authenticated, but GitHub does not provide shell access.
ptops@pt-lab01:/var/repos/warmstandby$
```

## Git-Remote korrekt konfigurieren

Remote Anzeige 
``` bash
ptops@pt-lab01:/var/repos/warmstandby$ git remote -v
origin  git@github.com:paintner-tech/warmstandby.git (fetch)
origin  git@github.com:paintner-tech/warmstandby.git (push)
```
 Remote konfigurieren
```
git remote remove origin
git remote add origin git@github.com:paintner-tech/warmstandby.git
```

Branch auf main setzen
```bash
ptops@pt-lab01:/var/repos/warmstandby$ git branch -M main
``` 

Upstream setzte
```bash
ptops@pt-lab01:/var/repos/warmstandby$ git branch --set-upstream-to=origin/main main
Branch 'main' folgt nun 'origin/main'.
```

Ab jetzt kann das Repo gepusht werden
```
ptops@pt-lab01:/var/repos/warmstandby$ git push
Everything up-to-date
ptops@pt-lab01:/var/repos/warmstandby$

```

# Zusätzliches Target einrichten
Von einem Repo auf zwei andere pushen

Remote anzeigen
```bash
ptops@pt-lab01:/var/warmstandby$ git remote -v
origin  git@github.com:paintner-tech/warmstandby.git (fetch)
origin  git@github.com:paintner-tech/warmstandby.git (push)
```

Zweites Target eintragen
```bash
ptops@pt-lab01:/var/warmstandby$ git remote add rechner2 root@ip:/prj/13_warmstandby/bare
```

Auf dem Zielrechner ein bare Repo initalisieren
```bash
ptops@rechner2:/prj/13_warmstandby/bare$ git init --bare
```

Repo pushen
```bash
ptops@pt-lab01:/var/warmstandby$ git push rechner2 master
```

Arbeitsverzeichnis clonen
```bash
ptops@rechner2:/prj/13_warmstandby$ git clone bare work
Klone nach 'work' ...
Fertig.
```

## Änderungen verteilen

git push nach rechner2
```bash
ptops@pt-lab01:/var/warmstandby$ git push rechner2
ptops@192.0.2.10's password:
Objekte aufzählen: 13, fertig.
Zähle Objekte: 100% (13/13), fertig.
Delta-Kompression verwendet bis zu 2 Threads.
Komprimiere Objekte: 100% (6/6), fertig.
Schreibe Objekte: 100% (7/7), 519 Bytes | 519.00 KiB/s, fertig.
Gesamt 7 (Delta 5), Wiederverwendet 0 (Delta 0), Pack wiederverwendet 0
To 192.0.2.10:/prj/13_warmstandby/bare
   6952d88..baf6210  master -> master
```

auf Rechner 2 ins work directory wechseln und git pull ausführen

```bash
ptops@rechner2:/prj/13_warmstandby/work$ git pul
git: 'pul' ist kein Git-Befehl. Siehe 'git --help'.

Die ähnlichsten Befehle sind
        pull
        push
ptops@rechner2:/prj/13_warmstandby/work$ git pull
remote: Objekte aufzählen: 13, Fertig.
remote: Zähle Objekte: 100% (13/13), Fertig.
remote: Komprimiere Objekte: 100% (6/6), Fertig.
remote: Gesamt 7 (Delta 5), Wiederverwendet 0 (Delta 0)
Entpacke Objekte: 100% (7/7), Fertig.
Von /prj/13_warmstandby/bare
   6952d88..baf6210  master     -> origin/master
Aktualisiere 6952d88..baf6210
Fast-forward
 .../opt/pecom/warmstandby/control/data_demo.py~    | 305 ---------------------
 1 file changed, 305 deletions(-)
```

# GIT commit Rückgängig machen
Es wurde ein commit gemacht, der Fehlerhaften Dateien enthielt und so nicht in die Entwicklungsreihenfolge passt. 

Backup vom Branch erstellen und mit reset auf alten commit zurück.
## Commit zurücksetzen
```bash
ptops@pt-lab01:/var/warmstandby$ git branch backup-eb32d2a
ptops@pt-lab01:/var/warmstandby$ git reset --hard 1f34cb0
```
Commit pushen
## Commit pushen
```
ptops@pt-lab01:/var/warmstandby$ git push --force-with-lease origin master
hostkeys_find_by_key_hostfile: hostkeys_foreach failed for /home/ptops/.ssh/known_hosts: Permission denied
The authenticity of host '[ssh.github.com]:443 ([140.82.121.36]:443)' can't be established.
ED25519 key fingerprint is SHA256:+DiY3wvvV6TuJJhbpZisF/zLDA0zPMSvHdkr4UvCOqU.
This key is not known by any other names.
Are you sure you want to continue connecting (yes/no/[fingerprint])? yes
Failed to add the host to the list of known hosts (/home/ptops/.ssh/known_hosts).
client_input_hostkeys: hostkeys_foreach failed for /home/ptops/.ssh/known_hosts: Permission denied
Gesamt 0 (Delta 0), Wiederverwendet 0 (Delta 0), Pack wiederverwendet 0
To github.com:paintner-tech/warmstandby.git
 + eb32d2a...1f34cb0 master -> master (forced update)
ptops@pt-lab01:/var/warmstandby$
```

## Backup branch
```

ptops@pt-lab01:/var/warmstandby$ git branch
  backup-eb32d2a
* master
ptops@pt-lab01:/var/warmstandby$
```


