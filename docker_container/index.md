---
layout: default
title: Docker und Container
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)

# Docker vs Container – einfach erklärt (Praxis)

Viele setzen Docker und Container gleich.  
Das ist aber nur die halbe Wahrheit.

---

## 🧱 Container (das Konzept)

Ein Container ist **kein Tool**, sondern eine Technik.

- isolierter Prozess auf einem Linux-System
- nutzt den Kernel des Host-Systems
- basiert auf:
  - Namespaces (Isolation)
  - cgroups (Ressourcen)

👉 Kein eigenes Betriebssystem  
👉 Kein eigener Kernel  
👉 Sehr leichtgewichtig

**Merksatz:**  
Container = „Mini-System ohne eigenen Kernel“

---

## 🐳 Docker (das Tool)

Docker ist eine Software, die Container **einfach nutzbar macht**.

- Images bauen (`Dockerfile`)
- Container starten (`docker run`)
- Images verteilen (Registry)
- Netzwerk & Volumes verwalten

👉 Docker ist nicht der Container selbst!

**Merksatz:**  
Docker = Werkzeug für Container

---

## 🔥 Der Unterschied (kurz & klar)

| Thema        | Container                  | Docker                    |
|--------------|---------------------------|---------------------------|
| Was ist es?  | Technik / Konzept         | Software / Tool           |
| Aufgabe      | Isolation                 | Nutzung & Verwaltung      |
| Beispiel     | LXC                       | Docker                    |

---

## Docker installieren
```bash
root@datsrv1:~# apt install docker.io
```

## Docker testen
docker run -it ubuntu bash

👉 Image ubuntu wird geladen (falls nicht vorhanden → Download)
👉 daraus wird ein Container gestartet
👉du bist direkt im System drin


```bash
root@datsrv1:~# docker run -it ubuntu bash
Unable to find image 'ubuntu:latest' locally
latest: Pulling from library/ubuntu
b40150c1c271: Pull complete
92842f25412d: Download complete
Digest: sha256:c4a8d5503dfb2a3eb8ab5f807da5bc69a85730fb49b5cfca2330194ebcc41c7b
Status: Downloaded newer image for ubuntu:latest
root@204c34995ed3:/#
root@204c34995ed3:/#
root@204c34995ed3:/# who
who     whoami
root@204c34995ed3:/# who
who     whoami
root@204c34995ed3:/# whoami
root
root@204c34995ed3:/# hostname
204c34995ed3
root@204c34995ed3:/#
exit
```

## Beispiele zum Erlernen





