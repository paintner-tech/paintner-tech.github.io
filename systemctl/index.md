---
layout: default
title: systemctl
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)


# Einleitung
Wenn man – so wie ich – von Debian 7 „Wheezy“ kommt und viele Jahre mit klassischen `init.d`-Skripten gearbeitet hat, wirkt `systemctl` beim ersten Kontakt unnötig kompliziert.

Zuerst sieht man keinen wirklichen Vorteil – nur mehr Komplexität und neue Befehle.

Doch sobald man ernsthaft damit arbeitet, erkennt man sehr schnell die großen Vorteile von `systemd`:

- saubere Abhängigkeitssteuerung zwischen Diensten  
- integrierte Timer als Ersatz für Cronjobs  
- klar definierte Service-Typen  
- zentrales Logging über `journalctl`  
- konsistente Verwaltung aller Units  

Heute würde ich nicht mehr zurück wollen

## Beschreibung
`systemctl` ist das Kommandozeilenwerkzeug, um **systemd-Services** zu verwalten: starten/stoppen, Status prüfen, Logs ansehen, Autostart aktivieren, Timer prüfen usw.

Typische Aufgaben:

- Services starten/stoppen/restarten
- Status & letzte Logs prüfen
- Autostart (enable/disable) verwalten
- Fehler analysieren (journalctl)
- Timer und Units auflisten

> Merke:  
> **Unit =** „Ding, das systemd verwaltet“ (Service, Timer, Mount, Socket, …).  
> **Service =** eine Unit vom Typ `.service`.


## Typische Befehle

### Start / Stop / Restart
```ini
sudo systemctl start ssh
sudo systemctl stop ssh
sudo systemctl restart ssh

### Autostart aktivieren/deaktivieren
'''ini
sudo systemctl enable ssh
sudo systemctl disable ssh
```

### Läuft der Service wirklich?
```ini
systemctl is-active ssh
echo $?
```
### Units suchen / auflisten
``ìni
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl list-unit-files --type=service
```

### Status prüfen
```bash
systemctl status ssh
systemctl status ssh.service
```


### Beispiele

#### Dienst aktivieren und starten
```bash
tops@pt-lab01:~$ sudo systemctl enable apache2
Synchronizing state of apache2.service with SysV service script with /usr/lib/systemd/systemd-sysv-install.
Executing: /usr/lib/systemd/systemd-sysv-install enable apache2
ptops@pt-lab01:~$ sudo systemctl start apache2
ptops@pt-lab01:~$
```


#### Überprüfen ob der service läuft
```bash
ptops@pt-lab01:~$ systemctl is-active apache2
active
ptops@pt-lab01:~$
```

#### Status überprüfen
```bash
ptops@pt-lab01:~$ systemctl status apache2
● apache2.service - The Apache HTTP Server
     Loaded: loaded (/usr/lib/systemd/system/apache2.service; enabled; preset: >
     Active: active (running) since Mon 2026-03-02 09:26:20 CET; 2min 22s ago
       Docs: https://httpd.apache.org/docs/2.4/
   Main PID: 5569 (apache2)
      Tasks: 55 (limit: 4546)
     Memory: 5.2M (peak: 5.7M)
        CPU: 47ms
     CGroup: /system.slice/apache2.service
             ├─5569 /usr/sbin/apache2 -k start
             ├─5571 /usr/sbin/apache2 -k start
             └─5572 /usr/sbin/apache2 -k start

Mär 02 09:26:20 pt-lab01 systemd[1]: Starting apache2.service - The Apache HTTP>
Mär 02 09:26:20 pt-lab01 apachectl[5568]: AH00558: apache2: Could not reliably >
Mär 02 09:26:20 pt-lab01 systemd[1]: Started apache2.service - The Apache HTTP >
lines 1-16/16 (END)
```
#### Dienst stoppen
```bash
ptops@pt-lab01:~$ sudo systemctl stop apache2
ptops@pt-lab01:~$
```

#### Dienst deaktivieren
```bash
ptops@pt-lab01:~$ sudo systemctl disable apache2
Synchronizing state of apache2.service with SysV service script with /usr/lib/systemd/systemd-sysv-install.
Executing: /usr/lib/systemd/systemd-sysv-install disable apache2
Removed "/etc/systemd/system/multi-user.target.wants/apache2.service".
ptops@pt-lab01:~$
```




## Unit-Dateien (.service, .timer, …)

Eine *Unit-Datei* beschreibt, wie systemd einen Dienst startet, überwacht und beendet.

Typische Speicherorte:

- `/etc/systemd/system/` → eigene / manuell installierte Units
- `/lib/systemd/system/` oder `/usr/lib/systemd/system/` → vom Paketmanager

Eine einfache Service-Unit besteht aus drei Bereichen:

* [Unit] → Abhängigkeiten und Reihenfolge
* [Service] → Wie wird gestartet? Wie überwacht?
* [Install] → Wann soll der Dienst aktiviert werden?

### Aufbau einer Service-Unit

Diese Unit-Datei stammt aus einem Warm-Standby-Projekt. Beim Erkennen eines definierten Ereignisses – beispielsweise einer Rollenänderung von Slave zu Master – wird eine virtuelle IP-Adresse (VIP) aktiviert oder deaktiviert.
Die VIP stellt sicher, dass Dienste im Netzwerk stets unter derselben IP-Adresse erreichbar sind.

```ini
[Unit]
Description=PECOM Virtual IP (VIP)
After=network-online.target
Wants=network-online.target
PartOf=warmstandby-supervisor.service

[Service]
Type=oneshot
RemainAfterExit=yes
ExecStart=/usr/bin/python3 /opt/pecom/warmstandby/bin/vip.py up
ExecStop=/usr/bin/python3 /opt/pecom/warmstandby/bin/vip.py down

StandardOutput=journal
StandardError=journal

[Install]
WantedBy=multi-user.target

```

#### After=network-online.target

Der Service startet erst nachdem das Netzwerk vollständig online ist.
Wichtig bei VIPs, da sonst das Interface evtl. noch nicht existiert.


#### Wants=network-online.target
Stellt sicher, dass das Target gestartet wird, falls es noch nicht aktiv ist.

Unterschied zu Requires=:

Wants= → weich (Service startet trotzdem weiter)
Requires= → hart (bei Fehler wird Start abgebrochen)

#### PartOf=warmstandby-supervisor.service
Wenn der Supervisor gestoppt wird, wird auch diese Unit gestoppt.
Das sorgt für saubere Kopplung innerhalb des Warm-Standby-Systems.

#### Type=oneshot
Der Service führt ein Kommando aus und beendet sich danach wieder.

Perfekt für:

- VIP setzen
- Konfiguration anwenden
- einmalige Aktionen

Er läuft nicht dauerhaft im Hintergrund.

#### RemainAfterExit=yes
Obwohl der Prozess endet, bleibt der Service-Status auf „active“.

Das ist extrem wichtig hier:
Die VIP ist weiterhin gesetzt, obwohl das Python-Skript schon beendet ist.

systemd merkt sich: „Dieser Service ist aktiv“

Ohne diese Option wäre der Service nach ExecStart sofort „inactive“.


#### ExecStart=/usr/bin/python3 /opt/warmstandby/bin/vip.py up
Beim Start wird das Skript mit Parameter up aufgerufen.

→ VIP wird gesetzt.

#### ExecStop=/usr/bin/python3 /opt/warmstandby/bin/vip.py down
Beim Stoppen wird das Skript mit down ausgeführt.

→ VIP wird entfernt.

Logik bleibt im Python-Code, systemd übernimmt nur Steuerung.
