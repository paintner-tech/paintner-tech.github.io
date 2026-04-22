---
layout: default
title: Hello World
---

[Home](/) . [Technische Dokumentation](/#technische-dokumentation)

# Einführung
Erstes einfachs go Programm. Wie kann es anders sein, Hello World!

# Code
```code
package main

import "fmt"

func main() {
    fmt.Println("Hello, World!")
```

# Mod Datei erzeugen
```code
root@datsrv1:/var/go/helloworld# go mod init helloworld
```

Inhalt der Datei
```code
module helloworld

go 1.22.2
```

# Binary erzeugen
```code
root@datsrv1:/var/go/helloworld# go buil
```

# Ausgabe
```bash
root@datsrv1:/var/go/helloworld# ./helloworld
Hello, World!
root@datsrv1:/var/go/helloworld#
```
Welche Überraschung. 
