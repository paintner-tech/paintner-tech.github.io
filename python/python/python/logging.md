---
layout: default
title: Python Logging – Praxis
---

# Python Logging – Praxis

Kleines Beispiel für einen Rotating Logger, den ich in vielen Skripten verwende.

```python
import logging
from logging.handlers import RotatingFileHandler
from pathlib import Path

def setup_logger(logfile: str):
    log_path = Path(logfile)
    log_path.parent.mkdir(parents=True, exist_ok=True)

    logger = logging.getLogger("app")
    logger.setLevel(logging.INFO)

    if logger.handlers:
        return logger

    handler = RotatingFileHandler(
        logfile, maxBytes=5 * 1024 * 1024, backupCount=3
    )
    formatter = logging.Formatter(
        "%(asctime)s %(levelname)s %(message)s"
    )
    handler.setFormatter(formatter)
    logger.addHandler(handler)

    return logger
