---
layout: default
title: Proxmox – USB Device einer VM hinzufügen
---

[Home](/) · [Technische Dokumentation](/#technische-dokumentation)

# USB Device identifizieren
```bash
ptops:/root # lsusb
Bus 001 Device 012: ID 0e50:0002 USBStorage 
ptobs:/root #
```

# USB Device mittels qm einer VM hinzufügen
## Hinzufügen
```bash
qm set 115 -usb0 host=0e50:0002
```
## Entfernen
```
qm set 115 -delete usb0
```
