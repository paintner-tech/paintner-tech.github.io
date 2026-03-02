
---
layout: default
title: title: <Unit-Name>.service
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)


# Überschrift 1

# Beispiel
Start einen lsync wrapper. Der lsync-wrapper wird von einen supervisior gestartet.

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


# Abschnitt [Service]
## Erklärung des verwendeten Typs:
###Type=simple

Standard-Typ (wenn nichts angegeben ist)
systemd startet den Prozess
Service gilt sofort als „active“
systemd wartet nicht auf irgendein Signal
geeignet für normale, dauerhaft laufende Programme

Typischer Einsatz:
* Python-Server
* Webserver
* Daemons, die im Vordergrund laufen

-->  Am häufigsten verwendet.

🔹 Type=oneshot

Führt ein Kommando aus

Wartet bis es fertig ist

Danach endet der Prozess

Mit RemainAfterExit=yes bleibt Service „active (exited)“

Typischer Einsatz:

VIP setzen

Firewall-Regeln

Mount-Skripte

einmalige Setup-Aktionen

👉 Ideal für Zustandsänderungen.

