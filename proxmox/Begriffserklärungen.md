---
layout: default
title: PegaProx Erkärungen 
---

[Home](/) · [Technische Dokumentation](/#technische-dokumentation)


# Migration Threshold


![PegaProx Thresold](images/Threshold.jpg)

## Grundidee
PegaProx berechnet für jeden Node einen internen Load Score aus Faktoren wie:

* CPU-Auslastung
* RAM-Auslastung
* evtl. IO/Storage-Last
* Anzahl laufender VMs/CTs

Wenn ein Node deutlich stärker belastet ist als ein anderer, prüft PegaProx:

Ist die Differenz größer als der Threshold? -> Nur dann wird migriert.

## Beispiel

Angenommen:

Node	Score
pve1	70
pve2	45
pve3	40

Differenz pve1 ↔ pve3 = 30 Punkte

Bei Threshold 20 %:
→ Migration wird ausgelöst.

Bei Threshold 40 %:
→ keine Migration.


## Empfehlung:

15–25 %

Das ist meist ideal für:

* stabile Balance
* wenig unnötige Migrationen
* gute Lastverteilung


## Threshold betrifft nur:

automatische Lastverteilung (Balancing)

Nicht zwingend:

Maintenance Mode Drain
manuelle Migration
HA Failover

Diese können Migration unabhängig davon auslösen.
