---
layout: default
title: Konfiguration auslesen und auswerten
---

[Home](/) · [Technische Dokumentation](/#technische-dokumentation)

# Python Debugging mit pdb

Beim Entwickeln von Python-Skripten ist es oft hilfreich, den Programmablauf an einer bestimmten Stelle anzuhalten und sich den aktuellen Zustand des Programms anzusehen.

Der in Python integrierte Debugger **pdb** ermöglicht genau das.

Damit kann man:

- Variableninhalte prüfen
- den Code schrittweise ausführen
- Funktionsaufrufe verfolgen
- Fehlerursachen schneller finden

Der Debugger ist bereits Bestandteil der Python-Standardbibliothek und muss nicht separat installiert werden.


## Beispiel:

```python
def main():

    cfg = load_cfg(CONFIG_FILE)
    import pdb; pdb.set_trace()
    pg_port = (cfg.get("db_rep") or {}).get("pg_port")
```

In diesem Beispiel stoppt das Programm direkt nach dem Laden der Konfiguration. Der Programmstelle zum stoppen wird einfach mit "import pdb; pdb.set_trace()" festgelegt.

So kann überprüft werden:

* ob die Konfiguration korrekt geladen wurde
* ob die gewünschten Werte vorhanden sind
* ob Variablen den erwarteten Inhalt haben


# Programm starten

Das Skript wird normal gestartet:

./install_wsby.py

Sobald die Debugger-Stelle erreicht wird, erscheint eine Ausgabe ähnlich wie:

root@wstby:/opt/pecom/warmstandby/install# ./install_wsby.py

> /opt/pecom/warmstandby/install/install_wsby.py(328)main()
-> pg_port = (cfg.get("db_rep") or {}).get("pg_port")
(Pdb)

Jetzt befindet sich das Programm im Debug-Modus.


# Variablen anzeigen

Im Debugger können Variablen direkt ausgegeben werden.

Beispiel:
```bash
root@wstbyz:/opt/pecom/warmstandby/install# ./install_wsby.py
> /opt/pecom/warmstandby/install/install_wsby.py(328)main()
-> pg_port = (cfg.get("db_rep") or {}).get("pg_port")
(Pdb) n
> /opt/pecom/warmstandby/install/install_wsby.py(329)main()
-> repuser = (cfg.get("db_rep") or {}).get("pg_user")
(Pdb) p pg_port
5631
(Pdb)
```


## Wichtige Befehle im Debugger

| Befehl | Bedeutung |
|------|-----------|
| `n` | next (nächste Zeile) |
| `s` | step (in Funktion rein) |
| `c` | continue |
| `l` | Code anzeigen |
| `p var` | Variable anzeigen |
| `q` | Debugger verlassen |
