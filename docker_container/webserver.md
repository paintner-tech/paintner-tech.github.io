---
layout: default
title: Docker und Container / Beispiele
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)

# Nginx-Webserver

## webserver erstellen
```code
ptops@pt-lab01:/var/lib$ sudo docker run -d --name webserver -p 8080:80 nginx:stable
Unable to find image 'nginx:stable' locally
stable: Pulling from library/nginx
f1a6a629b824: Pull complete
ecc510c1e359: Pull complete
f4616bb1be1c: Pull complete
220a6ac36368: Pull complete
3d774efa34e3: Pull complete
cb44c3fe542b: Pull complete
121907e00c1c: Pull complete
32970ab31784: Download complete
50204dda2e59: Download complete
Digest: sha256:9bf97bd7714f5e24c1ccd545ecb9eb5435cb6d109c97cebb15e7e455e0239edb
Status: Downloaded newer image for nginx:stable
7a08b25f21dda869bd2178b43ee5554f911919f8d9041a9f2451e677b23a5d81
ptops@pt-lab01:/var/lib$
```

* run	Einen neuen Container erstellen und starten
* -d	Im Hintergrund laufen lassen
* --name webserver	Dem Container den Namen webserver geben
* -p 8080:80	Port 8080 der VM mit Port 80 des Containers verbinden
* nginx:stable	Image nginx mit dem Tag stable verwenden

## images auflisten

```code
ptops@pt-lab01:/var/lib$ sudo docker images
                                                                                                                                                              i Info →   U  In Use
IMAGE                ID             DISK USAGE   CONTENT SIZE   EXTRA
hello-world:latest   5e2309035332       25.9kB         9.49kB    U
nginx:stable         9bf97bd7714f        242MB         66.5MB    U
ptops@pt-lab01:/var/lib$
```

* Image	    --> nginx:stable	Vorlage mit Nginx und den benötigten Dateien
* Container -->	webserver	Eine aus diesem Image erzeugte Instanz


## Überpürfen ob Docker läuft

```code
ptops@pt-lab01:/var/lib$ sudo docker ps
CONTAINER ID   IMAGE          COMMAND                  CREATED         STATUS         PORTS                                     NAMES
7a08b25f21dd   nginx:stable   "/docker-entrypoint.…"   7 minutes ago   Up 7 minutes   0.0.0.0:8080->80/tcp, [::]:8080->80/tcp   webserver
ptops@pt-lab01:/var/lib$
```


