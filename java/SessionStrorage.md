# SessionStorage und HostEntry – Einfaches Beispiel für Dateispeicherung in Java

Dieses Beispiel zeigt, wie man in Java Objekte in einer Datei speichern und später wieder laden kann.

Dabei arbeiten zwei Klassen zusammen:

- `HostEntry.java` → beschreibt einen einzelnen Datensatz
- `SessionStorage.java` → speichert und lädt mehrere Datensätze

---

## Ziel des Beispiels

Angenommen, ich habe mehrere SSH-Verbindungen, die ich speichern möchte:

| ID | IP-Adresse      | Benutzername | Passwort |
|----|------------------|---------------|-----------|
| 1  | 192.168.1.10     | root          | geheim1   |
| 2  | 10.0.0.5         | admin         | geheim2   |

Diese Daten sollen:

1. In eine Datei geschrieben werden
2. Später wieder geladen werden

---

# 1. Die Klasse HostEntry

`HostEntry` ist eine einfache Datenklasse.

Sie enthält:

- ID
- IP-Adresse
- Benutzername
- Passwort

### Beispiel:

```java
HostEntry host = new HostEntry(1, "192.168.1.10", "root", "geheim1");
