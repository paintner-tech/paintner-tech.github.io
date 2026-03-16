---
layout: default
title: Warmstandby - PGdb
---
[Home](/) . [Technische Dokumentation](/#technische-dokumentation)



# Installatiion
## Host A
Du installierst PostgreSQL ganz normal und startest die DB.

Host A läuft dann als Primary.

Typischer Zustand:

Host A
PostgreSQL läuft
pg_is_in_recovery() = false

Das bedeutet:

Primary / Master

## Host B
Basebackup von Host A nach Host B

Auf Host B führst du aus:

pg_basebackup -h hostA -D /var/lib/postgresql/data -U replication -Fp -Xs -P -R

Wichtig ist hier:

-R

Das bewirkt automatisch:

PostgreSQL erzeugt eine Datei:

standby.signal

und schreibt in postgresql.auto.conf:

primary_conninfo = 'host=hostA user=replication ...'
