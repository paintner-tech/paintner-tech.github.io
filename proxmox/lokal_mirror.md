---
layout: default
title: Eine lokalen Proxmox-Mirror erstellen
---


# Anleitung

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

2. Konfiguration überpürfen

Hier is die ID zu sehen, mit der im weiter Verlauf gearbeit wird.

```base
root@pve9:/mirror/pve# proxmox-offline-mirror config mirror list
┌──────────────────┬───────────────────────────────────────────────────────────────────────┬──────────────────┬────────┬──────┐
│ ID               │ repository                                                            │ base-dir         │ verify │ sync │
╞══════════════════╪═══════════════════════════════════════════════════════════════════════╪══════════════════╪════════╪══════╡
│ pve-trixie-nosub │ deb http://download.proxmox.com/debian/pve trixie pve-no-subscription │ /mirror/pve/base │      1 │    1 │
└──────────────────┴───────────────────────────────────────────────────────────────────────┴──────────────────┴────────┴──────┘
root@pve9:/mirror/pve#
```
3. Snapshot erstellen

Beim Erstellen des Snapshots muss man die ID angeben. In diesem Beispie: pve-trixie-nosub

```
proxmox-offline-mirror mirror snapshot create pve-trixie-nosub
```
3. ceph mirror hinzufügen

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
```
root@pve9:/mirror/pve# proxmox-offline-mirror mirror snapshot create debian-trixie-main
```







## gesammelte Beispiele
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






