Einführung: Migration von PegaProx als LXC-Container per Restore eines Backups

In diesem Fall wurde PegaProx nicht neu installiert, sondern durch Wiederherstellung eines bestehenden Proxmox-Backups migriert.

Als Ausgangspunkt lag ein vorhandenes Backup vor:

vzdump-lxc-125-2026_03_20-20_44_40.tar.zst

Dieses Backup wurde heruntergeladen und anschließend auf dem Ziel-Proxmox-System als vollständiger LXC-Container wiederhergestellt.

Migrationsmethode

Die Migration erfolgte per:

Backup → Transfer → Restore

Das bedeutet:

Export des bestehenden PegaProx-Containers als vzdump-Backup
Download der Backup-Datei
Übertragung auf das neue Proxmox-System
Restore als neuer oder identischer Container

Dadurch bleibt der komplette Zustand erhalten:

Betriebssystem
installierte Pakete
PegaProx-Anwendung
Konfigurationen
Datenbestände
Vorteil dieser Methode

Der Restore eines LXC-Backups ist besonders effizient, weil:

keine Neuinstallation nötig ist
alle Einstellungen übernommen werden
Ausfallzeiten gering bleiben
Fehlerquellen reduziert werden
Verwendete Backup-Datei

Die Datei:

vzdump-lxc-125-2026_03_20-20_44_40.tar.zst

enthält:

komplettes Root-Dateisystem des Containers
Container-Konfiguration
komprimierte Daten im Zstandard-Format (.zst)
Restore-Vorgang

Der Restore erfolgte auf dem Zielsystem über Proxmox:

Beispiel:
pct restore 125 /var/lib/vz/dump/vzdump-lxc-125-2026_03_20-20_44_40.tar.zst --storage local-lvm

Alternativ über die Proxmox-Weboberfläche:

Datacenter → Storage → Backups → Restore

Besonderheit bei .tar.zst

Obwohl die Datei als .tar.zst vorliegt, erkennt Proxmox das Format automatisch.

Wichtig:
Beim manuellen Prüfen kann eine Datei wie .tar aussehen, intern aber dennoch Zstandard-komprimiert sein.

Beispiel Prüfung:

file vzdump-lxc-125-2026_03_20-20_44_40.tar

Ausgabe:

Zstandard compressed data
Nach dem Restore prüfen

Nach erfolgreicher Wiederherstellung:

Kontrolle:
Container starten:
pct start 125
Status prüfen:
pct status 125
Login testen:
pct enter 125
PegaProx-Dienst prüfen:
systemctl status pegaprox
Ergebnis

Nach dem Restore steht der ursprüngliche PegaProx-Container vollständig wieder zur Verfügung – identisch zur Quelle.

Die Migration ist damit schnell, sauber und nahezu verlustfrei abgeschlossen.
