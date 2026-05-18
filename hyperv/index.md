---
layout: default
title: Hyper-V
---


# Einleitung
Hyper-V ist die Virtualisierungslösung von Microsoft und Bestandteil von Windows Server sowie Windows Pro/Enterprise Editionen. Mit Hyper-V lassen sich virtuelle Maschinen (VMs) erstellen und betreiben, um verschiedene Betriebssysteme parallel auf einem Host auszuführen.

Ich beschäftige mich mit Hyper-V, da neben Linux-, Proxmox- und VMware-Umgebungen auch Microsoft-Virtualisierung im industriellen Umfeld und bei Kunden zunehmend gefragt ist.


> Wie immer ist dies kein vollständiges Tutorial.
> Vielmehr dokumentiere ich hier Dinge, über die ich im praktischen Einsatz gestolpert bin, dokumentationswürdige Erfahrungen, typische Probleme sowie deren Lösungen und Empfehlungen aus der Praxis.

# Checkpoints (Snapshots)

In Hyper-V werden Snapshots offiziell als Checkpoints bezeichnet. Ein Checkpoint speichert den Zustand einer virtuellen Maschine zu einem bestimmten Zeitpunkt. Dadurch kann später jederzeit auf diesen Zustand zurückgesetzt werden.

## Unterschied zwischen Checkpoints und klassischen Snapshots

Der Begriff „Snapshot“ wird häufig allgemein verwendet.
Hyper-V nutzt jedoch den Begriff Checkpoint.

Es gibt zwei Arten:

**Standard-Checkpoint**
Speichert zusätzlich den RAM-Inhalt
VM läuft später exakt an derselben Stelle weiter
Eher für Test- und Entwicklungsumgebungen geeignet

**Produktions-Checkpoint**
Nutzt konsistente Backup-Mechanismen
Kein gespeicherter RAM-Zustand
Sicherer für produktive Systeme
Wichtiger Hinweis

Checkpoints sind kein vollwertiges Backup.
Sie sollten nicht dauerhaft bestehen bleiben, da Hyper-V dabei zusätzliche AVHDX-Differenzdateien erzeugt, die Speicherplatz verbrauchen und die Performance beeinflussen können.

## Artikel
- [Restore in Hyper-V ohne echte CF-/USB-Karte](restore_ohne_passthroug.html)
