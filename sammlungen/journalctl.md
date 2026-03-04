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
## journalctl -n
Zeigt die letzten n Einträge
```bash
root@pt-lab01:~# journalctl -n 5
Mär 04 08:05:59 pt-lab01 systemd[1]: motd-news.service: Deactivated successfully.
Mär 04 08:05:59 pt-lab01 systemd[1]: Finished motd-news.service - Message of the Day.
Mär 04 08:10:17 pt-lab01 systemd[1]: Starting sysstat-collect.service - system activity accounting tool...
Mär 04 08:10:17 pt-lab01 systemd[1]: sysstat-collect.service: Deactivated successfully.
Mär 04 08:10:17 pt-lab01 systemd[1]: Finished sysstat-collect.service - system activity accounting tool.
root@pt-lab01:~#
```

## journalctl -f
Zeigt fortlaufend live
```bash
root@pt-lab01:~# journalctl -f
Mär 04 08:05:59 pt-lab01 systemd[1]: Finished motd-news.service - Message of the Day.
Mär 04 08:10:17 pt-lab01 systemd[1]: Starting sysstat-collect.service - system activity accounting tool...
Mär 04 08:10:17 pt-lab01 systemd[1]: sysstat-collect.service: Deactivated successfully.
Mär 04 08:10:17 pt-lab01 systemd[1]: Finished sysstat-collect.service - system activity accounting tool.
Mär 04 08:15:01 pt-lab01 CRON[15274]: pam_unix(cron:session): session opened for user root(uid=0) by root(uid=0)
Mär 04 08:15:01 pt-lab01 CRON[15275]: (root) CMD (command -v debian-sa1 > /dev/null && debian-sa1 1 1)
Mär 04 08:15:01 pt-lab01 CRON[15274]: pam_unix(cron:session): session closed for user root
Mär 04 08:17:01 pt-lab01 CRON[15302]: pam_unix(cron:session): session opened for user root(uid=0) by root(uid=0)
Mär 04 08:17:01 pt-lab01 CRON[15303]: (root) CMD (cd / && run-parts --report /etc/cron.hourly)
Mär 04 08:17:01 pt-lab01 CRON[15302]: pam_unix(cron:session): session closed for user root
```

## journalctl -u <dienst>
Zeigt Logs eines bestimmten Dienstes
```bash
root@pt-lab01:~# journalctl -u ssh
Mär 02 09:25:50 pt-lab01 systemd[1]: Starting ssh.service - OpenBSD Secure Shell server...
Mär 02 09:25:50 pt-lab01 sshd[4631]: Server listening on 0.0.0.0 port 22.
Mär 02 09:25:50 pt-lab01 sshd[4631]: Server listening on :: port 22.
Mär 02 09:25:50 pt-lab01 systemd[1]: Started ssh.service - OpenBSD Secure Shell server.
Mär 02 09:25:55 pt-lab01 sshd[4633]: Accepted password for ptops from 192.168.193.1 port 51416 ssh2
Mär 02 09:25:55 pt-lab01 sshd[4633]: pam_unix(sshd:session): session opened for user ptops(uid=1000) by ptops(uid=0)
root@pt-lab01:~#
```

## journalctl -u <dienst> -f
Zeigt logs einen bestimmten Dienstes fortlaufen an. Kombination aus -u und -f
```bash
root@pt-lab01:~# journalctl -u ssh -f
Mär 02 09:25:50 pt-lab01 systemd[1]: Starting ssh.service - OpenBSD Secure Shell server...
Mär 02 09:25:50 pt-lab01 sshd[4631]: Server listening on 0.0.0.0 port 22.
Mär 02 09:25:50 pt-lab01 sshd[4631]: Server listening on :: port 22.
Mär 02 09:25:50 pt-lab01 systemd[1]: Started ssh.service - OpenBSD Secure Shell server.
Mär 02 09:25:55 pt-lab01 sshd[4633]: Accepted password for ptops from 192.168.193.1 port 51416 ssh2
Mär 02 09:25:55 pt-lab01 sshd[4633]: pam_unix(sshd:session): session opened for user ptops(uid=1000) by ptops(uid=0)
```

## journalctl -p err
Zeigt nur Fehler an

```bash
root@pt-lab01:~# journalctl -p err
Mär 02 09:20:09 pt-lab01 kernel: piix4_smbus 0000:00:07.3: SMBus Host Controller not enabled!
Mär 02 09:20:22 pt-lab01 gdm-autologin][1711]: gkr-pam: couldn't unlock the login keyring.
Mär 02 09:20:22 pt-lab01 gdm3[1702]: Gdm: on_display_added: assertion 'GDM_IS_REMOTE_DISPLAY (display)' failed
Mär 02 09:20:22 pt-lab01 gdm3[1702]: Gdm: on_display_removed: assertion 'GDM_IS_REMOTE_DISPLAY (display)' failed

```
```bash

```


