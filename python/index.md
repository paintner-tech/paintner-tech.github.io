---
layout: default
title: Python – Praxis-Doku
---

# Python – Praxis-Dokumentation

Hier sammle ich kleine, wiederverwendbare Python-Snippets und Muster, die ich im Alltag nützlich sein können.

> **Hinweis:** Alle Beispiele sind bewusst klein und kopierbar.

## Warum Python

Python bietet gegenüber Bash-Skripting einige entscheidende Vorteile – vor allem bei größeren oder langlebigen Automatisierungen.Der wichtigste Vorteil aus meiner Sicht ist die umfangreiche Standardbibliothek und die Verfügbarkeit fertiger, gut getesteter Klassen und Module.

Beispiele:

- Logging mit Rotation (RotatingFileHandler)
- flexible Formatter für Log-Ausgaben
- saubere Argumentverarbeitung (argparse)
- strukturierte Konfigurationsdateien (YAML/INI/JSON)
- Fehlerbehandlung mit Exceptions
- bessere Testbarkeit

Funktionen wie ein rotierender Logger müsste man in Bash erst aufwendig selbst bauen (Logrotate einbinden, Dateigrößen prüfen, alte Logs löschen, Formatierung definieren). In Python ist das mit wenigen Zeilen Code bereits robust gelöst – durch vorhandene Bibliotheken.



## Inhalte
- [Logging – einfacher Rotating Logger](logging.html)
  

