---
layout: default
title: Docker / compose
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)

# Einleitung
Mit Docker Compose werden Container in einer YAML-Datei definiert und verwaltet. Im folgenden wird ein Nginx-Webserver mit einem eingebundenen Webseitenordner eingerichtet, gestartet und gestoppt.

Hier ist beschrieben, wie man eine webserver ohne compose erstellt: [Docker: Webserver](./webserver)

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



