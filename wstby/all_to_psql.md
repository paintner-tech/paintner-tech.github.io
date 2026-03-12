---
layout: default
title: Warmstandby - PGdb
---
[Home](/) . [Technische Dokumentation](/#technische-dokumentation)


# DB überpüfen
## Master
### select pg_is_in_recovery() 
Wenn hier f angezeigt wird, dann ist die DB nicht in Recovery. t wurde bedueten Recovery aktiv

```bash
rot@wstbyz:/opt/pecom/warmstandby/install# sudo -u postgres psql -p 5631 -d postgres -c "select pg_is_in_recovery();"
 pg_is_in_recovery
-------------------
 f
(1 row)

root@wstbyz196tpr11:/opt/pecom/warmstandby/install#
```

### select * from pg_stat_replication;
Wenn dort der Slave auftaucht, dann ist das ein sehr starker Beweis: dieser Server ist Primary und der andere hängt als Replica dran

```bash
  pid   | usesysid | usename | application_name |  client_addr  | client_hostname | client_port |         backend_start         | backend_xmin |   state   |  sent_lsn  | write_lsn  | flush_lsn  | replay_lsn | write_lag | flush_lag | replay_lag | sync_priority | sync_state |          reply_time
--------+----------+---------+------------------+---------------+-----------------+-------------+-------------------------------+--------------+-----------+------------+------------+------------+------------+-----------+-----------+------------+---------------+------------+-------------------------------
 505207 |    20205 | repl    | walreceiver      | 192.0.2.114 |                 |       36660 | 2026-03-11 17:48:27.278414+01 |              | streaming | 0/93006250 | 0/93006250 | 0/93006250 | 0/93006250 |           |           |            |             0 | async      | 2026-03-12 09:09:07.684219+01
(1 row)
```

## Slave
### select * from pg_stat_wal_receiver;
Wenn dort eine Verbindung zum Primary steht, dann läuft er als Standby und empfängt WAL.

```bash
postgres=# select * from pg_stat_wal_receiver;
  pid   |  status   | receive_start_lsn | receive_start_tli | written_lsn | flushed_lsn | received_tli |      last_msg_send_time       |     last_msg_receipt_time     | latest_end_lsn |        latest_end_time        | slot_name |  sender_host   | sender_port |                                                                                                                                                                              conninfo                                                                                                           
--------+-----------+-------------------+-------------------+-------------+-------------+--------------+-------------------------------+-------------------------------+----------------+-------------------------------+-----------+----------------+-------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 169752 | streaming | 0/93000000        |                 1 | 0/93006250  | 0/93006250  |            1 | 2026-03-12 04:49:40.812366+01 | 2026-03-12 09:19:37.915977+01 | 0/93006250     | 2026-03-12 04:35:10.498444+01 |           | wstbyz196tpr11 |        5631 | user=repl passfile=/var/lib/postgresql/.pgpass channel_binding=prefer dbname=replication host=wstby11 port=5631 fallback_application_name=walreceiver sslmode=disable sslcompression=0 sslcertmode=allow sslsni=1 ssl_min_protocol_version=TLSv1.2 gssencmode=prefer krbsrvname=postgres gssdelegation=0 target_session_attrs=any load_balance_hosts=disable
(1 row)
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
