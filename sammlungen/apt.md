---
layout: default
title: journalctl
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)


# Was ist apt
APT steht für Advanced Package Tool und ist das Paketverwaltungssystem von Debian und darauf basierenden Systemen wie Ubuntu.

Kurz erklärt

APT wird verwendet, um Software zu installieren, zu aktualisieren und zu entfernen.
Es kümmert sich automatisch um Abhängigkeiten, also zusätzliche Pakete, die ein Programm benötigt.

# apt search "string"

apt search sucht in allen aktuell konfigurierten Repositories, deren Paketlisten vorher mit apt update heruntergeladen wurden.

```bash
ptops@pt-lab01:~$ apt search x11vnc
Sortierung… Fertig
Volltextsuche… Fertig
ssvnc/noble 1.0.29-6ubuntu3 amd64
  Erweiterter TightVNC-Betrachter mit Hilfe für SSL/SSH-Tunnel

x11vnc/noble 0.9.16-10 amd64
  VNC-Server gestattet Zugriff auf existierende X-Umgebung von außerhalb

ptops@pt-lab01:~$
```
# apt install Paketname
apt install wird verwendet, um ein Softwarepaket zu installieren.

APT macht dabei automatisch:

* Prüft, ob das Paket in den konfigurierten Repositories vorhanden ist
* Lädt das Paket herunter
* Installiert es
* Installiert alle benötigten Abhängigkeiten mit

```bash
ptops@pt-lab01:~$ sudo apt install x11vnc
```

# apt list --installed | grep Paketname

apt list --installed
→ listet alle installierten Pakete auf dem System auf.
grep paketname
→ filtert die Ausgabe nach dem angegebenen Suchbegriff.

```bash
tops@pt-lab01:~$ apt list --installed | grep x11vnc
x11vnc/noble,now 0.9.16-10 amd64  [installiert]
ptops@pt-lab01:~$
```

# apt download Paketname
apt download lädt ein Paket als .deb Datei herunter, ohne es zu installieren.
```bash
ptops@pt-lab01:~$ apt download x11vnc
ptops@pt-lab01:~$ ll x11vnc_0.9.16-10_amd64.deb
-rw-r--r-- 1 ptops ptops 1017502 Apr  3  2024 x11vnc_0.9.16-10_amd64.deb
ptops@pt-lab01:~$
```
