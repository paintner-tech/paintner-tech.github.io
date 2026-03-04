---
layout: default
title: journalctl
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)


# Was ist journal eigentlich?

„Ein Journal ist das moderne, zentrale Tagebuch des Systems – systemd speichert darin alles, was passiert, zusammen mit wichtigen Zusatzinfos.“

journalctl liest das systemd-Journal, das alle Logs zentral sammelt. systemd ersetzt viele klassische Logdateien (/var/log/*) durch ein binäres, zentralisiertes Logging. 
Das Journal ist standardmäßig binär und nicht direkt Text wie alte /var/log-Dateien.

Merksatz:

/var/log/apache2/error.log = direktes Apaches-Log
journalctl -u apache2 = systemd-zentrale Sicht auf den Apache-Service + Systemmeldungen

# Beispiele
## journalctl -n <x>
Zeigt die letzten x Einträge
```bash
root@pt-lab01:~# journalctl -n 5
Mär 04 08:05:59 pt-lab01 systemd[1]: motd-news.service: Deactivated successfully.
Mär 04 08:05:59 pt-lab01 systemd[1]: Finished motd-news.service - Message of the Day.
Mär 04 08:10:17 pt-lab01 systemd[1]: Starting sysstat-collect.service - system activity accounting tool...
Mär 04 08:10:17 pt-lab01 systemd[1]: sysstat-collect.service: Deactivated successfully.
Mär 04 08:10:17 pt-lab01 systemd[1]: Finished sysstat-collect.service - system activity accounting tool.
root@pt-lab01:~#
```
