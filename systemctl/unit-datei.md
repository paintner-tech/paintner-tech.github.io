---
layout: default
title: Unit-Datei
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)


# Einführendes Beispiel
Start eines lsync wrapper. Der lsync-wrapper wird von einen supervisior gestartet.

```bash
[Unit]
Description=Warm-Standby Lsync Wrapper (lsyncd master->slave)
After=network-online.target warmstandby-supervisor.service
Wants=network-online.target

# Fail-safe: lsync darf nur laufen, wenn der Supervisor läuft
PartOf=warmstandby-supervisor.service

[Service]
Type=simple

ExecStart=/usr/bin/python3 /opt/warmstandby/bin/lsync-wrapper.py

# Wenn lsyncd oder Wrapper stirbt -> automatisch neu starten
Restart=always
RestartSec=2

# Saubere Beendigung: SIGTERM an lsyncd (kommt direkt an, wegen execvp)
KillSignal=SIGTERM
TimeoutStopSec=15

# Logs gehen ins Journal
StandardOutput=journal
StandardError=journal

# Sicherheit (optional, aber gut)
NoNewPrivileges=true
PrivateTmp=true

[Install]
WantedBy=multi-user.target
```

# Abschnitt [Unit]
## Wants=

Bedeutung

Wants= definiert eine weiche Abhängigkeit. Wenn diese Unit gestartet wird, versucht systemd auch die angegebene Unit zu starten.

Aber:
Wenn die gewünschte Unit fehlschlägt, läuft der aktuelle Service trotzdem weiter.

> ** Merksatz **
„Bitte starte das mit – aber wenn es nicht klappt, mache trotzdem weiter.“

Beispiel
Wants=network-online.target
After=network-online.target


## Requires=
Bedeutung

Requires= definiert eine harte Abhängigkeit. Das bedeutet, wenn diese Unit startet, muss die angegebene Unit ebenfalls erfolgreich starten. Falls sie fehlschlägt, wird auch der aktuelle Service nicht gestartet.


Verhalten im Detail
Beim Start:
* systemd startet zuerst die required Unit
* wenn diese fehlschlägt → Start wird abgebrochen

Während der Laufzeit

* Wenn die required Unit stoppt oder crasht, wird auch die abhängige Unit gestoppt

> ** Merksatz **
„Ohne dich starte ich nicht – und wenn du stirbst, gehe ich mit.“

#PartOf=

Bedeutung

PartOf= koppelt diese Unit an eine andere Unit.

* Wenn die referenzierte Unit gestoppt oder neugestartet wird,wird diese Unit automatisch mit gestoppt oder neugestartet.

Wichtig:
PartOf= wirkt nur bei:
* stop
* restart
* reload
Nicht beim Start.

> ** Merksatz **
„Wenn der Chef stoppt, stoppe ich auch.“

Beispiel im Projekt
PartOf=warmstandby-supervisor.service

Bedeutung:

* Stoppt der Supervisor → VIP wird ebenfalls gestoppt
* Restart des Supervisors → VIP wird ebenfalls neu gestartet
* Start des Supervisors → VIP startet NICHT automatisch

Das verhindert inkonsistente Zustände.

# Abschnitt [Service] --> Type
## Type=simple

Standard-Typ (wenn nichts angegeben ist)
* systemd startet den Prozess
* Service gilt sofort als „active“
* systemd wartet nicht auf irgendein Signal
* geeignet für normale, dauerhaft laufende Programme

Typischer Einsatz:
* Python-Server
* Webserver
* Daemons, die im Vordergrund laufen

-->  Am häufigsten verwendet.

## Type=oneshot

* Führt ein Kommando aus
* Wartet bis es fertig ist
* Danach endet der Prozess
* Mit RemainAfterExit=yes bleibt Service „active (exited)“

Typischer Einsatz:

* VIP setzen
* Firewall-Regeln
* Mount-Skripte
* einmalige Setup-Aktionen

--> Ideal für Zustandsänderungen.

## Type=forking

* Prozess startet
* dieser forkt (geht in Hintergrund)
* Elternprozess beendet sich
* Kindprozess läuft weiter

Wird gebraucht für ältere Programme, die sich selbst daemonisieren.

Typischer Einsatz:

* klassische alte Daemons
* Programme mit --daemon Option

--> Heute meist unnötig, wenn man Programme im Vordergrund starten kann.

## Type=notify

* Service signalisiert systemd aktiv:
* „Ich bin jetzt vollständig gestartet.“

Benötigt Unterstützung im Programm (sd_notify)

Typischer Einsatz:

* moderne Daemons
* komplexe Services mit langer Initialisierung

--> Sehr sauber für Hochverfügbarkeit.

 ## Type=dbus

* Service gilt als gestartet,
* wenn er sich erfolgreich am D-Bus registriert hat

--> Wird selten manuell verwendet.
