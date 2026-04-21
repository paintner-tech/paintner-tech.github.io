---
layout: default
title: Docker und Container / Beispiele
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)

# Definitionen (Wiederholung)


> Container → laufende Instanz eines Images
> 
> Docker → Plattform/Werkzeug zum Erstellen, Verwalten und Ausführen von Containers



# Vorhandene Images anzeigen
Die Images hello-world und ubuntu sind bereits lokal auf dem Host vorhanden und können direkt verwendet werden.

Anzeige der lokal verfügbaren Images, mit:

docker images

Diese Images können anschließend ohne erneuten Download gestartet werden.

```bash
root@datsrv1:/var/lib/docker/containers# docker images
                                                            i Info →   U  In Use
IMAGE                ID             DISK USAGE   CONTENT SIZE   EXTRA
hello-world:latest   f9078146db2e       25.9kB         9.49kB    U
ubuntu:latest        c4a8d5503dfb        119MB         31.7MB    U
root@datsrv1:/var/lib/docker/containers#
```

# Vorhandes Image starten und in dem daraus erzeugten Container arbeiten

-i → interaktiv
-t → Terminal
```
root@datsrv1:~# docker run -it ubuntu bash
root@b6a8924d53e4:/#
```

# Einfachs Image erstellen
## Verzeichnis erzeugen
```bash
root@datsrv1:/var/docker# mkdir myFirstImage
root@datsrv1:/var/docker# cd myFirstImage/
root@datsrv1:/var/docker/myFirstImage#
```

## Dockerfile mit folgedem Inhalt erzeugen
```bash
FROM alpine
CMD ["sh"]
```

## Image erzeugen
```bash
root@datsrv1:/var/docker/myFirstImage# docker build -t shell_image .
```

## Container aus Image starten
```bash
root@datsrv1:/var/docker/myFirstImage# docker run -it shell_image
/ #
/ #
/ #
/ # whoami
root
```



