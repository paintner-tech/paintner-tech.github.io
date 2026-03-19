
---
layout: default
title: Colored Terminal Output in Python
---

# Colored Terminal Output in Python
Adding colors to terminal output makes your scripts easier to read and helps highlight important information like errors or status messages.

In Python, this can be done using simple ANSI escape codes — no external libraries required.

This guide shows how to:

* print colored text (red, green, yellow)
* improve readability of CLI tools

## Features

- Simple colored output using ANSI escape codes
- No external dependencies required
- Highlight errors, warnings, and success states
- Easy to integrate into existing scripts

## Usage

Define simple helper functions cprint():

```python
def cprint_red(text):
    print(f"\033[91m{text}\033[0m")

def cprint_green(text):
    print(f"\033[92m{text}\033[0m")

def cprint_yellow(text):
    print(f"\033[93m{text}\033[0m")
```

Define simple helper functions bool_to_text():
```python
def bool_to_text(val: bool) -> str:
    if val is True:
        return color("true", "92")   # green
    if val is False:
        return color("false", "91")  # red
    return color("unknown", "93")    # yellow
```
## Example cprint()
* cprint_green("OK: Service is running")
* cprint_yellow("WARNING: High memory usage")
* cprint_red("ERROR: Service failed")

Output in terminal cprint():

* OK → green
* WARNING → yellow
* ERROR → red

## Example bool_to_text()
    has_dongle = dongle_present()
    print(f"Local Dongle present:  {bool_to_text(has_dongle)}")

Output in terminal bool_to_text()

* Local Dongle present: true → green
* Local Dongle present: false → red




# ANSI-Colors
Color	Code
| Color   | Code |
|---------|------|
| Rot     | 91   |
| Grün    | 92   |
| Gelb    | 93   |
| Blau    | 94   |
| Magenta | 95   |
| Cyan    | 96   |
| Weiß    | 97   |

