---
layout: default
title: Proxmox – Praxis-Doku
---

[Home](/) · [Technische Dokumentation](/#technische-dokumentation)

# Proxmox – Praxis-Dokumentation

Hier sammle ich praxisnahe Notizen, Beispiele und kleine Anleitungen rund um **Proxmox Virtual Environment (PVE)**.

Die Inhalte entstehen direkt aus dem täglichen Einsatz in Testumgebungen und Produktionssystemen und sollen vor allem als **kompakte Referenz** dienen.

> **Hinweis:** Die Beispiele sind bewusst kurz gehalten und sollen schnell verständlich und kopierbar sein.

---

# Warum Proxmox

Proxmox VE ist eine **Open-Source Virtualisierungsplattform**, die mehrere Technologien in einer integrierten Umgebung vereint.

Dazu gehören unter anderem:

- **KVM Virtualisierung** für virtuelle Maschinen
- **LXC Container**
- **Cluster-Funktionalität**
- **Ceph Storage Integration**
- **Live-Migration von VMs**
- **Backup-Integration mit Proxmox Backup Server**

Gerade im Vergleich zu vielen klassischen Virtualisierungslösungen bietet Proxmox einige entscheidende Vorteile:

- vollständig **Open Source**
- einfache **Clusterbildung**
- integrierte **Software Defined Storage** Lösung (Ceph)
- zentrale Verwaltung über **Webinterface und CLI**
- sehr gut automatisierbar über **CLI und API**

Dadurch eignet sich Proxmox sowohl für **Homelabs**, **Testumgebungen** als auch für **produktive Infrastrukturen**.

---

# Inhalte

- [Lokalen Proxmox Mirror erstellen](lokal_mirror.html)
- [Pegaprox als lxc container migrieren](pegaprox_lxc.html)
