---
layout: default
title: lsyncd – Kurz & kompakt
---

# lsyncd – Kurz & kompakt

**lsyncd** (Live Syncing Daemon) synchronisiert Dateien in (nahezu) Echtzeit von einem Server auf einen anderen.  
Ich nutze es als einfache Lösung für **Master → Slave Replikation** (z. B. für Warm-Standby-Setups).

## Wann lsyncd sinnvoll ist
- Replikation von Projektverzeichnissen
- Verteilung von Konfigurationsdateien
- einfache Master → Slave Szenarien
- wenn „nah an Echtzeit“ reicht

## Wann lsyncd *nicht* die richtige Wahl ist
- Datenbanken (hier besser native Replikation nutzen)
- bidirektionale Synchronisation (Konfliktgefahr)
- große Datenmengen mit sehr vielen kleinen Dateien ohne Tuning

## Minimal-Setup (Beispiel)
Beispiel für eine einfache Konfiguration:

```lua
settings {
    logfile    = "/var/log/lsyncd.log",
    statusFile = "/var/log/lsyncd.status",
    statusInterval = 20
}

sync {
    default.rsync,
    source = "/data/master",
    target = "slave:/data/master",
    delete = true,
    rsync = {
        archive = true,
        compress = true
    }
}
