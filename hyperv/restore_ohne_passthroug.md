---
layout: default
title: Hyper-V
---


# Legacy-Restore-Medien in Hyper-V ohne USB-Passthrough

## Einleitung

Viele ältere Linux-basierte Industrie- oder Embedded-Systeme erwarteten ihr Installations- oder Restore-Medium in Form von:

* CompactFlash-Karten
* USB-Speichermedien
* wechselbaren Linux-Blockdevices

Moderne Virtualisierungsplattformen wie Hyper-V bieten jedoch kein einfaches USB-Passthrough für klassische USB-Speichermedien wie man es beispielsweise von älteren Desktop-Virtualisierungslösungen kennt.

Dieser Artikel zeigt, wie sich ein altes Restore-/Installationssystem, das ursprünglich ein CF-/USB-Medium erwartete, erfolgreich innerhalb einer Hyper-V-VM betreiben lässt — ausschließlich mit einer virtuellen Festplatte.

---

# Das Problem

Die alte Restore-Umgebung erwartete:

* ein Linux-Blockdevice (`/dev/sdX`)
* ein bestimmtes Filesystem-Label
* eine definierte Verzeichnisstruktur
* Installations- und Backupdaten
* die Erkennung des Geräts als USB-/Wechselmedium

Die Restore-Skripte suchten explizit nach USB-Geräten und ignorierten normale virtuelle Festplatten.

Typische Logik:

```bash
if [ "$(readlink -f $device | grep /usb[0-9]*/)" = "" ]; then
    continue
fi
```

Auf echter Hardware funktioniert dies problemlos.
Innerhalb von Hyper-V schlagen solche Prüfungen jedoch fehl, da virtuelle VHDX-Festplatten keine USB-Geräte sind.

---

# Lösungsansatz

Anstatt ein echtes USB-Gerät durchzureichen, wurde eine zweite virtuelle Festplatte erzeugt und so vorbereitet, dass sie sich exakt wie das ursprüngliche Restore-Medium verhält.

Die Lösung besteht aus folgenden Schritten:

1. Erzeugen einer zweiten virtuellen Festplatte in Hyper-V
2. Partitionieren und Formatieren mit ext4
3. Setzen des erwarteten Filesystem-Labels
4. Nachbilden der ursprünglichen Verzeichnisstruktur
5. Kopieren der Installations- und Backupdaten
6. Entfernen der USB-only-Prüfung in den Restore-Skripten

---

# Schritt 1 – Virtuelle Festplatte in Hyper-V anlegen

Innerhalb von Hyper-V:

* VM herunterfahren
* VM-Einstellungen öffnen
* zweite virtuelle Festplatte hinzufügen

Empfehlung:

* IDE-Controller (beste Kompatibilität für ältere Linux-Systeme)
* VHDX-Format
* dynamisch wachsend
* Beispielgröße: 15 GB

Beispiel:

```text
legacy_restore.vhdx
```

---

# Schritt 2 – Festplatte unter Linux vorbereiten

Nach dem Start der VM:

```bash
lsblk
```

Beispiel:

```text
sda -> Systemplatte
sdb -> virtuelles Restore-Medium
```

---

## Partition anlegen

```bash
fdisk /dev/sdb
```

Eingaben:

```text
n
p
1
ENTER
ENTER
w
```

---

## Filesystem erzeugen

```bash
mkfs.ext4 /dev/sdb1
```

---

## Filesystem-Label setzen

Dieser Schritt ist wichtig, da die Restore-Umgebung nach einem bestimmten Label sucht.

```bash
e2label /dev/sdb1 restore-media
```

Prüfen:

```bash
blkid /dev/sdb1
```

Erwartete Ausgabe:

```text
LABEL="restore-media"
```

---

# Schritt 3 – Ursprüngliche Verzeichnisstruktur nachbilden

Festplatte mounten:

```bash
mkdir -p /mnt/restore
mount /dev/sdb1 /mnt/restore
```

Verzeichnisse anlegen:

```bash
mkdir /mnt/restore/installation
mkdir /mnt/restore/backup
mkdir /mnt/restore/data
```

---

# Schritt 4 – Installationsdaten kopieren

Die Inhalte des ursprünglichen Installationsmediums nach:

```text
/mnt/restore/installation
```

kopieren.

Typische Dateien:

```text
INSTALL.log
INSTALL_SCRIPT
install_helper
```

Ausführungsrechte prüfen:

```bash
chmod +x /mnt/restore/installation/*
```

---

# Schritt 5 – Backupdaten kopieren und entpacken

Backuparchive nach:

```text
/mnt/restore/backup
```

kopieren.

Beispiel:

```text
SYSTEM_BACKUP_2024-06-13_1-1.tgz
```

Archiv entpacken:

```bash
cd /mnt/restore/backup
tar xzf SYSTEM_BACKUP_2024-06-13_1-1.tgz
```

Viele Restore-Umgebungen erwarten eine Verzeichnisstruktur und nicht nur die reine Archivdatei.

---

# Schritt 6 – Restore-Skripte patchen

Die ursprünglichen Restore-Skripte suchten ausschließlich nach USB-Geräten.

Typischer Code:

```bash
if [ "$(readlink -f $device | grep /usb[0-9]*/)" = "" ]; then
    continue
fi
```

Diese Prüfung muss deaktiviert oder entfernt werden.

Angepasste Version:

```bash
# if [ "$(readlink -f $device | grep /usb[0-9]*/)" = "" ]; then
#     continue
# fi
```

Danach akzeptieren die Skripte auch:

* virtuelle IDE-Festplatten
* virtuelle SCSI-Festplatten
* VHDX-Medien

als gültiges Restore-Medium.

---

# Schritt 7 – Mount-Logik prüfen

Auch die Hilfsskripte zum Mounten des Installations- und Backupmediums müssen angepasst werden.

Typische Hilfsskripte:

```text
mount_install_media
mount_backup_media
```

Nach dem Patch:

```bash
./mount_install_media
```

Prüfen:

```bash
mount | grep eod
ls -la /eod
```

Erwartete Dateien:

```text
/eod/INSTALL.log
/eod/INSTALL_SCRIPT
```

---

# Ergebnis

Das alte Restore-System funktioniert vollständig innerhalb von Hyper-V:

* ohne echte CF-Karte
* ohne USB-Passthrough
* ausschließlich mit einer virtuellen VHDX-Datei
* mit ext4-Dateisystem
* über ein definiertes Filesystem-Label
* nach Anpassung der USB-Erkennung

Die virtuelle VHDX ersetzt damit das ursprüngliche physische Restore-Medium vollständig.

---

# Fazit

Gerade bei älteren Industrie- oder Embedded-Systemen scheitert eine Virtualisierung häufig nicht an Linux selbst, sondern an alten Hardware-Annahmen innerhalb der Installations- oder Restore-Logik.

Durch kleine Anpassungen der Geräteerkennung lassen sich solche Systeme jedoch häufig erstaunlich sauber virtualisieren — ohne komplizierte USB-Weiterleitungen oder Spezialhardware.

In diesem Fall genügte:

* eine zusätzliche virtuelle Festplatte
* ein passendes Filesystem-Label
* die ursprüngliche Verzeichnisstruktur
* eine kleine Anpassung der Restore-Skripte

um ein ursprünglich hardwaregebundenes Restore-System vollständig virtualisierbar zu machen.
