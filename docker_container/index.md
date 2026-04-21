---
layout: default
title: Docker und Container
---

[Home](../README.md)

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
## Beispiel

### Docker installieren
```bash
root@pt-lab01:~# apt install docker.io -y
```








