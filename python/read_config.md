---
layout: default
title: Rotating Logger
---

# Konfiguration auslesen und auswerten

## Beschreibung
In vielen Python-Programmen werden Einstellungen nicht direkt im Code hinterlegt,
sondern in einer separaten YAML-Datei gespeichert.

Vorteile:
* Konfiguration kann geändert werden, ohne den Code anzupassen
* Klare Trennung zwischen Logik und Parametern
* Gut geeignet für produktive Umgebungen (z. B. Ports, Flags, Pfade, VIP-Adressen)
* Die YAML-Datei wird beim Start des Programms geladen und als Dictionary verfügbar gemacht.
* Einzelne Werte können anschließend gezielt ausgelesen und weiterverwendet werden.

## Beispiel
### Beispielkonfiguration (YAML): cfg.yaml

```yaml
db_rep:
  enable: true
  port: 5999
  timeout: 5
```

### Implementierung in Python
```python
# Konfiguartiondatei bekannt machen
CFG = "/path/cfg.yaml"

# Einfach Funktion um Datei zu laden
def load_cfg():
    with open(CFG, "r") as f:
        return yaml.safe_load(f)

# Hauptteil
def main():

# Datei laden
cfg = load_cfg()
# Wert auslesen und in Variable schreiben
pg_port = cfg.get("db_rep", {}).get("pg_port")
# timeout wird gelesen, wenn nicht vorhanden dann 2 verwenden
timeout = int(cfg.get("db_rep", {}).get("timeout", 2))

#... weiter verarbeiten
```

## Erklärung

* yaml.safe_load() wandelt die YAML-Datei in ein Python-Dictionary um
* Mit get() wird ein Key sicher gelesen
* Durch {} als Default wird verhindert, dass ein KeyError entsteht
* Default-Werte sorgen dafür, dass das Programm auch bei fehlenden Parametern stabil bleibt



