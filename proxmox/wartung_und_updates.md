---
layout: default
title: Wartung und Updates
---

[Home](/) · [Technische Dokumentation](/#technische-dokumentation)

# Wartungsarbeit an einem Ceph - Cluster

## noout
ceph osd set noout verhindert, dass Ceph während geplanter Wartungsarbeiten ausgefallene OSDs automatisch als out markiert und dadurch unnötiges Rebalancing bzw. eine Datenreplikation startet.
```code
ceph osd set noout
```
