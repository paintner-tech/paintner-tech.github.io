---
layout: default
title: Migration von PegaProx als LXC-Container per Restore eines Backups
---

[Home](/) · [Technische Dokumentation](/#technische-dokumentation)

# Einführung: Migration von PegaProx als LXC-Container per Restore eines Backups

In diesem Fall wurde PegaProx nicht neu installiert, sondern durch Wiederherstellung eines bestehenden Proxmox-Backups migriert. Als Ausgangspunkt wurde ein Backup von https://pegaprox.com/ heruntergeladen

vzdump-lxc-125-2026_03_20-20_44_40.tar.zst

Dieses Backup wurde heruntergeladen und anschließend auf dem Ziel-Proxmox-System als vollständiger LXC-Container wiederhergestellt.

# Migrationsmethode

Die Migration erfolgte per:

Backup → Transfer → Restore

Das bedeutet:

Export des bestehenden PegaProx-Containers als vzdump-Backup
Download der Backup-Datei
Übertragung auf das neue Proxmox-System
Restore als neuer oder identischer Container

Dadurch bleibt der komplette Zustand erhalten:

```code
- Betriebssystem
- installierte Pakete
- PegaProx-Anwendung
- Konfigurationen
- Datenbestände
``` 
Der Restore eines LXC-Backups ist besonders effizient, weil:

```code
keine Neuinstallation nötig ist
alle Einstellungen übernommen werden
Ausfallzeiten gering bleiben
Fehlerquellen reduziert werden
Verwendete Backup-Datei
```

## Beispiel:

```code
pct restore 125 /var/lib/vz/dump/vzdump-lxc-125-2026_03_20-20_44_40.tar.zst --storage local-lvm
```

Alternativ über die Proxmox-Weboberfläche:

```code
Datacenter → Storage → Backups → Restore
```


## Kontrolle:
Container starten:
```bash
pct start 125
```
Status prüfen:
```bash
pct status 125
```
Login testen:
```bash
pct enter 125
```
PegaProx-Dienst prüfen:
```bash
systemctl status pegaprox
```

# Ergebnis

Nach dem Restore steht der ursprüngliche PegaProx-Container vollständig wieder zur Verfügung – identisch zur Quelle.

Die Migration ist damit schnell, sauber und nahezu verlustfrei abgeschlossen.
