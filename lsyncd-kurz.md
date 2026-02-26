---
layout: default
title: lsyncd – Kurz & kompakt
---

# lsyncd – Kurz & kompakt

**lsyncd** (Live Syncing Daemon) synchronisiert Dateien in (nahezu) Echtzeit von einem Server auf einen anderen.  
Ich nutze es als einfache Lösung für **Master → Slave Replikation** (z. B. für Warm-Standby-Setups).

## Wann lsyncd sinnvoll ist
- Replikation von Konfigurationsverzeichnisen
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
    target = "slave_host:/data/master",
    delete = true,
    exclude = {
    "*.tmp",
    "*.pgdump.tmp",
    rsync = {
        archive = true,
        compress = true
    }
}
```

Das Verzeichnis /data/master wird vom eigenen Host zum Hosts slave_host repliziert. Ausgeommen von der Replikation sind alle *.tmp und *pgdump.tmp Dateien.

## Typische Stolperfallen aus der Praxis

- SSH-Keys vergessen → Sync läuft nicht
- Rechte/Ownership nicht beachtet
- lsyncd läuft, aber rsync scheitert still im Hintergrund
- keine Monitoring-Prüfung eingebaut

## Beispiel Aufruf aus Python
```python
OUT_LUA   = "/pfad_zur_konnfiguration/lsync.path.lua"



    #lsyncd starten (im Vordergrund)
    cmd = ["lsyncd", "-nodaemon", OUT_LUA]
    LOGGER.info("INFO: lsync gestartet: ")
    #LOGGER.info("INFO: lsync gestartet: %s",  cmd)
    # Wichtig: exec ersetzt den Wrapper-Prozess -> systemd sieht direkt lsyncd
    os.execvp(cmd[0], cmd)
```

