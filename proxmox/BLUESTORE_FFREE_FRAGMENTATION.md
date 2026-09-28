---
layout: default
title: Proxmox – Nützliches
---

[Home](/) · [Technische Dokumentation](/#technische-dokumentation)

# Problem
Der Cluster meldet HEALTH_WARN, zeigt aber zunächst keinen klaren Grund.

```code
MGWS-BSP_pve1:/root # ceph status
  cluster:
    id:     98c4f89d-3bd7-487f-8c3b-7483b5090f5e
    health: HEALTH_WARN
            3 OSD(s)
```


Mit folgendem Befehl gibt Ceph die Warnung genauer aus:

```code
pve1:/root # ceph health detail
HEALTH_WARN 3 OSD(s)
[WRN] BLUESTORE_FREE_FRAGMENTATION: 3 OSD(s)
     osd.0 0.825436
     osd.1 0.819154
     osd.2 0.817841

```
Steht dort BLUESTORE_FREE_FRAGMENTATION, ist der freie Speicherplatz auf einem oder mehreren OSDs stark fragmentiert. Das kann neue Schreibvorgänge ausbremsen. Eine einfache Defragmentierung wie unter Windows gibt es dafür nicht.
Welche OSDs betroffen sind, zeigt:

```code
MGWS-BSP_pve1:/root # ceph tell osd.* bluestore allocator score block
osd.0: {
    "fragmentation_rating": 0.82522076449835025
}
osd.1: {
    "fragmentation_rating": 0.81925334676448447
}
osd.2: {
    "fragmentation_rating": 0.81800306414065038
}
osd.3: {
    "fragmentation_rating": 0.018168666465137814
}
osd.4: {
    "fragmentation_rating": 0.018560372496041505
}
osd.5: {
    "fragmentation_rating": 0.017182322032072681
}
MGWS-BSP_pve1:/root #
```

Je näher der Wert an 1, desto stärker die Fragmentierung.

# Lösung
Eine schnelle Defragmentierung gibt es nicht. Falls die Fragmentierung tatsächlich Probleme verursacht, können die betroffenen OSDs einzeln neu aufgebaut werden. Vor jedem OSD prüfen, ob der Cluster den Ausfall verkraftet; danach warten, bis wieder alle PGs active+clean sind.

> [!IMPORTANT]
> Immer nur **einen OSD gleichzeitig** neu aufbauen. Erst wenn alle PGs wieder `active+clean` sind, den nächsten OSD bearbeiten. Vorher freien Speicher und Redundanz prüfen.
> 
