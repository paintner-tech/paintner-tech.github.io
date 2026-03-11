---
layout: default
title: Warmstandby - PGdb
---
[Home](/) . [Technische Dokumentation](/#technische-dokumentation)


# Datenbank überprüfen
```bash
psql -p 5631 -d postgres -c "select pg_is_in_recovery();"
```

## Beispiel 
Db ist nicht hier nicht in Recovery
```bash
rot@wstbyz:/opt/pecom/warmstandby/install# sudo -u postgres psql -p 5631 -d postgres -c "select pg_is_in_recovery();"
 pg_is_in_recovery
-------------------
 f
(1 row)

root@wstbyz196tpr11:/opt/pecom/warmstandby/install#
```




# Replikationsuser in der DB anlegen


## Beispiel aus einem Python Skript
```bash
def create_replication_user():

    sql = """
    DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT FROM pg_roles WHERE rolname = 'rep'
        ) THEN
            CREATE ROLE rep WITH REPLICATION LOGIN PASSWORD 'rep_pass';
        END IF;
    END
    $$;
    """

    subprocess.run(
        ["sudo", "-u", "postgres", "psql", "-p", "5631", "-d", "postgres", "-c", sql],
        check=True
    )

    print("Replication User 'rep' geprüft/angelegt")

```

## Kontrolle welche User vorhanden sind



Mit psql mit der DB verbinden und dann mit "\du" alle vorhanden User anzeigen lassen.
```bash
root@wstbyz196tpr11:/opt/pecom/warmstandby/install# sudo -u postgres psql -p 5631 -d postgres
psql (16.10 (Ubuntu 16.10-0ubuntu0.24.04.1))
Type "help" for help.

postgres=# \du
                             List of roles
 Role name |                         Attributes
-----------+------------------------------------------------------------
superuser  | Superuser
 postgres  | Superuser, Create role, Create DB, Replication, Bypass RLS
 rep       | Replication

postgres=#
```

# Kontrolle welches pg_hba.conf verwendet wird
```bash
postgres=#
postgres=# SHOW hba_file;
             hba_file
-----------------------------------
 /etc/postgresql/16/main/pg_hba.conf/
(1 row)

postgres=#
```
