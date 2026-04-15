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

## Donwload Source
[SessionStorage.java herunterladen](SessionStorage.java)
[HostEntry.java herunterladen](HostEntry.java)
# 1. Die Klasse HostEntry

`HostEntry` ist eine einfache Datenklasse.

Sie enthält:

- ID
- IP-Adresse
- Benutzername
- Passwort

# 2. Die Klasse SessionStorage
Diese Klasse übernimmt:

Speichern in Datei
Laden aus Datei

### Beispiel:

```java
HostEntry host = new HostEntry(1, "192.168.1.10", "root", "geheim1");
```
erzeugt einen Objekt mit den Werten.

```java
List<HostEntry> list = new ArrayList<>();
list.add(new HostEntry(1, "192.168.1.10", "root", "geheim1"));
list.add(new HostEntry(2, "10.0.0.5", "admin", "geheim2"));

SessionStorage.saveSessions("sessions.txt", list);
```


#### 1. Liste erzeugen
List<HostEntry> list = new ArrayList<>();

Hier wird eine neue leere Liste erstellt.

Bedeutung:
List<HostEntry>
Die Liste darf nur Objekte vom Typ HostEntry enthalten.
new ArrayList<>()
Erstellt die konkrete Liste im Speicher.

Man kann sich das vorstellen wie eine leere Box, in die später Host-Einträge gelegt werden.

#### 2. Ersten Host hinzufügen
list.add(new HostEntry(1, "192.168.1.10", "root", "geheim1"));

Hier passiert zweierlei gleichzeitig:

Schritt A:

Ein neues HostEntry-Objekt wird erzeugt:

new HostEntry(1, "192.168.1.10", "root", "geheim1")

Das bedeutet:

Wert	Bedeutung
1	ID
192.168.1.10	IP-Adresse
root	Benutzername
geheim1	Passwort
Schritt B:

Dieses Objekt wird in die Liste eingefügt:

list.add(...)

Jetzt enthält die Liste 1 Eintrag.

#### 3. Zweiten Host hinzufügen
list.add(new HostEntry(2, "10.0.0.5", "admin", "geheim2"));

Das gleiche passiert noch einmal.

Nun enthält die Liste:

Inhalt der Liste:
[ Host1, Host2 ]

also:

1 → 192.168.1.10
2 → 10.0.0.5

#### 4. Liste speichern
SessionStorage.saveSessions("sessions.txt", list);

Jetzt wird die komplette Liste an die Methode saveSessions() übergeben.

Parameter:
"sessions.txt"

Name der Datei, in die gespeichert wird.

list

Die Liste mit allen HostEntry-Objekten.

#### 5. Was macht saveSessions intern?

Die Methode geht jeden Listeneintrag einzeln durch:

for (HostEntry entry : sessions)

Für jeden Host wird eine Zeile erzeugt:

1;192.168.1.10;root;geheim1
2;10.0.0.5;admin;geheim2

Dann wird alles in die Datei geschrieben.

#### 6. Ergebnis in der Datei

Datei sessions.txt:

1;192.168.1.10;root;geheim1
2;10.0.0.5;admin;geheim2
Zusammenfassung
Der Code macht also:
Leere Liste erstellen
Zwei Host-Objekte erzeugen
Diese in die Liste einfügen
Liste in Datei speichern
Bildlich vorgestellt:
Leere Liste
   ↓
Host 1 hinzufügen
   ↓
Host 2 hinzufügen
   ↓
Liste speichern
   ↓
Datei sessions.txt entsteht
Warum ist das elegant?

Weil:

beliebig viele Hosts hinzugefügt werden können
alles sauber in einer Liste gesammelt wird
Speicherung zentral über eine Methode erfolgt

Das ist typisch objektorientiertes Java.

