---
layout: default
title: Eine lokalen Proxmox-Mirror erstellen
---

# Einleitung
In produktiven Umgebungen ist es oft sinnvoll, **lokale Paket-Repositories (Mirror)** zu betreiben.  
Gerade bei Proxmox-Clustern oder Installationen in abgeschotteten Netzwerken (z.B. Produktionsnetzen ohne direkten Internetzugang) können Updates sonst nur eingeschränkt durchgeführt werden.

Ein lokaler Mirror bietet mehrere Vorteile:

- schnellere Updates, da Pakete lokal im Netzwerk liegen
- keine Abhängigkeit von externen Repositories
- reproduzierbare Paketstände durch Snapshots
- ideal für **Offline-Installationen oder Testumgebungen**

Proxmox stellt dafür das Tool **`proxmox-offline-mirror`** bereit.  
Damit können komplette Paket-Repositories gespiegelt und als Snapshot lokal bereitgestellt werden.

In dieser Anleitung wird ein lokaler Mirror für:

- Proxmox VE Repository
- Ceph Repository
- Debian Repository (optional)

erstellt.

---


# Anleitung
## 1. PVE Mirror lokal erzeugen

Hier wird das Proxmox Repository als Mirror konfiguriert.

1. PVE Mirror lokal erzeugen

```bash
root@pve9:/etc/apt/sources.list.d# proxmox-offline-mirror config mirror add \
  --id pve-trixie-nosub \
  --architectures amd64 \
  --architectures all \
  --repository "deb http://download.proxmox.com/debian/pve trixie pve-no-subscription" \
  --key-path /usr/share/keyrings/proxmox-archive-keyring.gpg \
  --sync true \
  --verify true \
  --base-dir /mirror/pve/base
root@pve9:/etc/apt/sources.list.d#
root@pve9:/etc#
```
Wichtige Parameter

Parameter	Bedeutung
- id	eindeutiger Name des Mirrors
- architectures	Paketarchitekturen die gespiegelt werden
- repository	Quelle des Repositories
- base-dir	lokaler Speicherort für den Mirror
- sync true	aktiviert automatische Synchronisation
- verify true	prüft Repository Signaturen

2. Konfiguration überpürfen

Hier wird kontrolliert, ob der Mirror korrekt angelegt wurde.

```base
root@pve9:/mirror/pve# proxmox-offline-mirror config mirror list
┌──────────────────┬───────────────────────────────────────────────────────────────────────┬──────────────────┬────────┬──────┐
│ ID               │ repository                                                            │ base-dir         │ verify │ sync │
╞══════════════════╪═══════════════════════════════════════════════════════════════════════╪══════════════════╪════════╪══════╡
│ pve-trixie-nosub │ deb http://download.proxmox.com/debian/pve trixie pve-no-subscription │ /mirror/pve/base │      1 │    1 │
└──────────────────┴───────────────────────────────────────────────────────────────────────┴──────────────────┴────────┴──────┘
root@pve9:/mirror/pve#
```

Hier is die ID zu sehen, mit der im weiter Verlauf gearbeit wird.


3. Snapshot erstellen
Der Snapshot lädt die Pakete lokal herunter. Beim Erstellen des Snapshots muss man die ID angeben. In diesem Beispie: pve-trixie-nosub

```
proxmox-offline-mirror mirror snapshot create pve-trixie-nosub
```

Ein Snapshot ist im Prinzip eine lokale Momentaufnahme des Repositories.

Das hat zwei Vorteile:

- reproduzierbare Paketstände
- Updates können kontrolliert getestet werden

3. ceph mirror hinzufügen

Für Proxmox-Cluster mit Ceph wird zusätzlich das Ceph Repository gespiegelt.

```
root@pve9:/mirror/pve# proxmox-offline-mirror config mirror add \
  --id ceph-trixie-nosub \
  --architectures amd64 \
  --architectures all \
  --repository "deb http://download.proxmox.com/debian/ceph-squid trixie no-subscription" \
  --key-path /usr/share/keyrings/proxmox-archive-keyring.gpg \
  --base-dir /mirror/pve/base \
  --sync true \
  --verify true
root@pve9:/mirror/pve#

```

4. Konfiguration überprüfen

```bash
root@pve9:/mirror/pve# proxmox-offline-mirror config mirror list
┌───────────────────┬──────────────────────────────────────────────────────────────────────────┬──────────────────┬────────┬──────┐
│ ID                │ repository                                                               │ base-dir         │ verify │ sync │
╞═══════════════════╪══════════════════════════════════════════════════════════════════════════╪══════════════════╪════════╪══════╡
│ ceph-trixie-nosub │ deb http://download.proxmox.com/debian/ceph-squid trixie no-subscription │ /mirror/pve/base │      1 │    1 │
├───────────────────┼──────────────────────────────────────────────────────────────────────────┼──────────────────┼────────┼──────┤
│ pve-trixie-nosub  │ deb http://download.proxmox.com/debian/pve trixie pve-no-subscription    │ /mirror/pve/base │      1 │    1 │
└───────────────────┴──────────────────────────────────────────────────────────────────────────┴──────────────────┴────────┴──────┘

5. Snapshot erstellen
```
root@pve9:/mirror/pve# proxmox-offline-mirror mirror snapshot create ceph-trixie-nosub
Fetching Release/Release.gpg files
```
6. Trixie Mirror erstelle

Zusätzlich kann auch das Debian Repository gespiegelt werden.

```bash
root@pve9:/mirror/pve# proxmox-offline-mirror config mirror add \
  --id debian-trixie-main \
  --architectures amd64 \
  --architectures all \
  --repository "deb http://deb.debian.org/debian trixie main" \
  --key-path /usr/share/keyrings/debian-archive-keyring.gpg \
  --base-dir /mirror/pve/base \
  --sync true \
  --verify true \
  --skip-packages "*-dbgsym" \
  --skip-packages "*-dbg"
root@pve9:/mirror/pve# proxmox-offline-mirror mirror snapshot create ceph-trixie-nosub
```

Die Optionen

```code
--skip-packages "*-dbgsym"
--skip-packages "*-dbg"
```

sparen erheblich Speicherplatz, da Debug-Pakete nicht benötigt werden.

7. Konfiguation anzeigen
```bash
root@pve9:/mirror/pve# proxmox-offline-mirror config mirror list
┌────────────────────┬──────────────────────────────────────────────────────────────────────────┬──────────────────┬────────┬──────┐
│ ID                 │ repository                                                               │ base-dir         │ verify │ sync │
╞════════════════════╪══════════════════════════════════════════════════════════════════════════╪══════════════════╪════════╪══════╡
│ ceph-trixie-nosub  │ deb http://download.proxmox.com/debian/ceph-squid trixie no-subscription │ /mirror/pve/base │      1 │    1 │
├────────────────────┼──────────────────────────────────────────────────────────────────────────┼──────────────────┼────────┼──────┤
│ debian-trixie-main │ deb http://deb.debian.org/debian trixie main                             │ /mirror/pve/base │      1 │    1 │
├────────────────────┼──────────────────────────────────────────────────────────────────────────┼──────────────────┼────────┼──────┤
│ pve-trixie-nosub   │ deb http://download.proxmox.com/debian/pve trixie pve-no-subscription    │ /mirror/pve/base │      1 │    1 │
└────────────────────┴──────────────────────────────────────────────────────────────────────────┴──────────────────┴────────┴──────┘
root@pve9:/mirror/pve#
```

8. Snapshort erstellen

>Achtung: trixie main ist >180GB. Für einen pve Testumgebung sind ceph  und pve völlig ausreichen
  
```
root@pve9:/mirror/pve# proxmox-offline-mirror mirror snapshot create debian-trixie-main
```

---



# Gesammelte Beispiele
## PVE Mirror erzeugen

```bash
root@pve9:/etc/apt/sources.list.d# proxmox-offline-mirror config mirror add \
  --id pve-trixie-nosub \
  --architectures amd64 \
  --architectures all \
  --repository "deb http://download.proxmox.com/debian/pve trixie pve-no-subscription" \
  --key-path /usr/share/keyrings/proxmox-archive-keyring.gpg \
  --sync true \
  --verify true \
  --base-dir /mirror/pom/base
root@pve9:/etc/apt/sources.list.d#
```
## Erstellten mirror anzeigen

Die Konfiguration wird in eine Konfiguationsdatei geschrieben (etc/proxmox-offline-mirror.cfg)

Dies kann auch mittels Kommando angezeigt werden:

```bash
root@pve9:/etc# proxmox-offline-mirror config mirror show --id pve-trixie-nosub
┌───────────────┬───────────────────────────────────────────────────────────────────────┐
│ Name          │ Value                                                                 │
╞═══════════════╪═══════════════════════════════════════════════════════════════════════╡
│ architectures │ ["amd64","all"]                                                       │
├───────────────┼───────────────────────────────────────────────────────────────────────┤
│ base-dir      │ /mirror/pom/base                                                      │
├───────────────┼───────────────────────────────────────────────────────────────────────┤
│ id            │ pve-trixie-nosub                                                      │
├───────────────┼───────────────────────────────────────────────────────────────────────┤
│ key-path      │ /usr/share/keyrings/proxmox-archive-keyring.gpg                       │
├───────────────┼───────────────────────────────────────────────────────────────────────┤
│ repository    │ deb http://download.proxmox.com/debian/pve trixie pve-no-subscription │
├───────────────┼───────────────────────────────────────────────────────────────────────┤
│ sync          │ 1                                                                     │
├───────────────┼───────────────────────────────────────────────────────────────────────┤
│ verify        │ 1                                                                     │
├───────────────┼───────────────────────────────────────────────────────────────────────┤
│ ignore-errors │ 0                                                                     │
└───────────────┴───────────────────────────────────────────────────────────────────────┘
root@pve9:/etc#
```

## Basename ändern
```bash
root@pve9:/etc# proxmox-offline-mirror config mirror update \
--id pve-trixie-nosub \
--base-dir /mirror/pve
root@pve9:/etc#
```






