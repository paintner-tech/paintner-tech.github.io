
---
layout: default
title: CUPS in old linux systems
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)


# PPD-Dateien im alten Linux verstehen

Im Zusammenhang mit CUPS (dem Drucksystem unter Linux) spielen PPD-Dateien eine zentrale Rolle. Hier ist, wie sie auf alten Systemen organisiert sind:

## Standard-Verzeichnis für PPD-Dateien
Alle allgemeinen und installierten PPD-Dateien liegen unter:

```bash
/usr/share/cups/model
```
Dieses Verzeichnis dient als Quelle für die PPD-Auswahl in der CUPS-GUI. Wenn du also möchtest, dass die CUPS-Oberfläche eine PPD anbietet, legst du sie dort ab.

## Drucker-spezifische PPD-Dateien
Sobald du einer Druckerwarteschlange eine PPD zugewiesen hast, kopiert CUPS diese Datei in:

```bash
/etc/cups/ppd
```
Die PPD-Datei trägt dann den Namen des zugehörigen Druckers. Das bedeutet, für einen Drucker namens "mein_drucker" findest du die PPD unter:

```
/etc/cups/ppd/mein_drucker.ppd
```
##Dynamik der PPD-Zuweisung
Die PPD unter /etc/cups/ppd repräsentiert den Treiber, den der Drucker aktuell verwendet. Änderungen über die GUI oder lpadmin wirken sich hier aus. Die Original-PPD bleibt in /usr/share/cups/model, aber die aktive Kopie im PPD-Verzeichnis ist entscheidend für den Druckprozess.

D.h. es wird eine ppd - Datei erzeugt, die eine Kopie der ausgewählten Datei ist und den Namen des Drucker aus /etc/hosts beinhaltet.

