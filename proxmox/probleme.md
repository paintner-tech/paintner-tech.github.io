---
layout: default
title: Proxmox – Probleme und Lösungen
---

[Home](/) · [Technische Dokumentation](/#technische-dokumentation)


# Einführung:
Hier dokumentiere ich Probleme aus dem Proxmox-Alltag und die Schritte, mit denen ich sie gelöst habe. Dazu gehören Fehlermeldungen, Ursachen und die passenden Befehle.

# health warning: BLUESTORE_FREE_FRAGMENTATION

Problem: Der Cluster zeigt HEALTH_WARN ohne weitere Details an. ceph health detail meldet BLUESTORE_FREE_FRAGMENTATION.
Das bedeutet, dass der freie Speicherplatz auf den betroffenen OSDs fragmentiert ist. Eine Defragmentierung wie unter Windows gibt es dafür nicht.

