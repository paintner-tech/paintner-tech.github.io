---
layout: default
title: Proxmox – Nützliches
---

[Home](/) · [Technische Dokumentation](/#technische-dokumentation)

# USB Device mittelt qm einer VM hinzufügen
## Hinzufügen
```bash
qm set 115 -usb0 host=0e50:0002
```
## Entfernen
```
qm set 115 -delete usb0
```



