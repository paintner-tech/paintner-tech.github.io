---
layout: default
title: Python Logging – Praxis
---

# Python Logging – Praxis

## Beschreibung
Ein Rotating Logger sorgt dafür, dass Logdateien nicht unendlich groß werden.
Sobald die aktuelle Logdatei eine bestimmte Größe erreicht, wird sie „rotiert“ und eine neue Logdatei begonnen. Größe und Anzahl der Logdateien kann man einfach beim rezeugen des Logger definieren

## Kleines Beispiel für einen Rotating Logger, den ich in vielen Skripten verwende.

```python
import logging
from logging.handlers import RotatingFileHandler
from pathlib import Path

def setup_rotating_logger(log_path, max_mb=10, backups=5):
    log_path = Path(log_path)
    log_path.parent.mkdir(parents=True, exist_ok=True)

    logger = logging.getLogger("warmstandby.lsyncd")
    logger.setLevel(logging.INFO)

    # verhindert doppelte Handler, falls mehrfach initialisiert
    if logger.handlers:
        return logger

    handler = RotatingFileHandler(
        filename=str(log_path),
        maxBytes=max_mb * 1024 * 1024,
        backupCount=backups,
        encoding="utf-8",
    )
    formatter = logging.Formatter("%(asctime)s %(levelname)s %(message)s",
                                  datefmt="%Y-%m-%d %H:%M:%S")
    handler.setFormatter(formatter)
    logger.addHandler(handler)
    return logger

```

Aufruf mit Anzahl Logdateien = 5 und maximaler Größe = 10 MB

```python
def main():
    global LOGGER
    LOGGER = setup_rotating_logger(log_path, max_mb=10, backups=5)
    LOGGER.info("######## NEUSTART lsyncd-wrapper pid=%s ########", os.getpid())

```





