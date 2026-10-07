---
layout: default
title: Docker / compose
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)

* TOC
{:toc}


# Einleitung
Mit Docker Compose werden Container in einer YAML-Datei definiert und verwaltet. Im folgenden wird ein Nginx-Webserver mit einem eingebundenen Webseitenordner eingerichtet, gestartet und gestoppt. Der Unterschied zum Container ohne Comppose: Bei docker exec wird der Containernamen angegeben. Bei docker compose exec wird der Dienstnamen aus der Compose-Datei verwendet.

Hier ist beschrieben, wie man eine Webserver ohne compose erstellt: [Docker: Webserver](./webserver)

## Konfiurationsdatei erstellen

Inhalt Datei ~/~/docker-uebungen/config/compose.yaml

```
services:
  webserver82:
    image: nginx:stable
    ports:
      - "8082:80"
```

| Eintrag | Bedeutung |
|---|---|
| `services` | Die Dienste deiner Anwendung |
| `webserver82` | Frei gewählter Name dieses Dienstes |
| `image` | Verwendetes Docker-Image |
| `8082:80` | Port 8081 der VM führt zu Port 80 im Container |

## Konfiguration überprüfen

### Konfiguration im aktuellen Ordner überprüfen

```code
ptops@pt-lab01:~/docker-uebungen/config$ sudo docker compose config
name: config
services:
  webserver82:
    image: nginx:stable
    networks:
      default: null
    ports:
      - mode: ingress
        target: 80
        published: "8082"
        protocol: tcp
networks:
  default:
    name: config_default
ptops@pt-lab01:~/docker-uebungen/config$
```
### Konfigurationsdatei überprüfen, Ordner explizit angeben

```code
ptops@pt-lab01:~$ sudo docker compose -f ~/docker-uebungen/config/compose.yaml config
name: config
services:
  webserver82:
    image: nginx:stable
    networks:
      default: null
    ports:
      - mode: ingress
        target: 80
        published: "8082"
        protocol: tcp
networks:
  default:
    name: config_default
ptops@pt-lab01:~$
```

## Container starten

```code
ptops@pt-lab01:~/docker-uebungen/config$ sudo docker compose up -d
[+] Running 2/2
 ✔ Network config_default          Created                                                                                                                                      0.1s
 ✔ Container config-webserver82-1  Started                                                                                                                                      0.5s
ptops@pt-lab01:~/docker-uebungen/config$
```

## Logs live ansehen

Zugriff auf Webserver auf ip
```code
ptops@pt-lab01:~/docker-uebungen/config$ sudo docker compose logs -f webserver82
ebserver82-1  | ip - - [07/Oct/2026:12:47:40 +0000] "GET / HTTP/1.1" 304 0 "-" "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:157.0) Gecko/20100101 Firefox/157.0" "-"
```

## Eine Shell im Container öffnen

```code
ptops@pt-lab01:~/docker-uebungen/config$ sudo docker compose exec webserver82 sh
```





