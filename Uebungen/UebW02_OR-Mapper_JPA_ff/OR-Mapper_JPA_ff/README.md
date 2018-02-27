# Übung 2: OR Mapper (Fortsetzung)

## Zielsetzung

Vertiefung in der Anwendung von JPA als OR Mapper.

-   Änderungen an Relationen
-   Abbildung von Vererbung
-   `JPQL`
-   explizites Locking

## Vorbereitung

Diese Übung baut auf dem Resultat der [letzten
Übung](OR-Mapper_JPA/README.md) und der Datenbank
[`bank`](Databases/bank) auf.

### Vorlagen

Die Projektvorlage für diese Aufgabe ist in dieser
[Zip-Datei](/../-/jobs/artifacts/master/raw/OR-Mapper_JPA_ff.zip?job=OR-Mapper_JPA_ff).
In dieser finden Sie das Eclipse-Projekt
[`dbs2.jpa_exercise_continued`](OR-Mapper_JPA_ff/.project) mit Java
Klassen inklusive JPA-Annotationen. Die JPA Libraries und
PostgreSQL-JDBC Drivers für Java-8 sind im Ordner
[`lib/`](OR-Mapper_JPA_ff/lib) **bereits enthalten**.

In der Vorlage zur Übung finden Sie die Datei
[`persistence.xml`](OR-Mapper_JPA_ff/src/META-INF/persistence.xml),
welche Ihnen als Vorlage zur Konfiguration Ihres JPA-Programms dienen
kann. Sie müssen dieses eventuell noch an Ihre Umgebung anpassen wenn
Sie beispielsweise einen anderen Port zum
[PostgreSQL](https://www.postgresql.org) Server verwenden.

### Datenbank `bank`

-   Für diese Übung benötigen Sie
    [PostgreSQL](https://www.postgresql.org) und die Datenbank
    [`bank`](Databases/bank)

    Import von `bank` geschieht durch Eingabe des folgenden Kommandos
    innerhalb des entsprechenden Unterverzeichnis

    ``` sql
    psql -U postgres -f 0_runAllScripts.sql
    ```

    oder durch Ausführen von [`run.bat`](Databases/bank/run.bat) oder
    [`run.sh`](Databases/bank/run.sh) im entsprechenden Verzeichnis.

    Die Daten befinden sich in [diesem Ordner](Databases/bank) und in
    dieser [zip
    Datei](/../-/jobs/artifacts/master/raw/Databases.zip?job=Databases).

## Aufgaben

### Aufgabe 1: Bidirektionale Beziehung

Für Ihre JPA Anwendung soll die `N:M`-Beziehung zwischen den Entities
`BankManager` und `BankCustomer` bei Änderungen konsistent bleiben. Wenn
also beispielsweise `BankCustomer` `K` nicht mehr von `BankManager` `M`
betreut wird, so darf `BankManager` `M` auch nicht mehr in der
Manager-Liste des `BankCustomer` `K` erscheinen. Zur Veranschaulichung
ist nachfolgend nochmals das Objektmodell abgebildet.

1.  Implementieren Sie die konsistente Beziehung für Änderungen und
    testen Sie es mit einem Beispiel
    -   indem Sie eine neue Beziehung einfügen
    -   und eine existierende Beziehung entfernen.

Persistentes Objektmodell:

``` plantuml
caption <b>bank</b> object model diagram
object BankCustomer {
  Name
  Birthdate
}
object BankAccount {
  Balance
  Currency
}
object Address {
  Street
  Zip
  City
}
object BankManager {
  Name
}
BankManager "*" - "*" BankCustomer
BankManager "0..1" -- "0..1" Address
BankCustomer "0..1" -- "0..1" Address
BankCustomer "1" *-- "*" BankAccount
center footer Note: not all attributes shown
```

### Aufgabe 2: Vererbung

Neu sollen zwei Sub-Klassen von `BankCustomer`, wie im folgenden
Klassendiagramm abgebildet, eingeführt werden:

``` plantuml
caption <b>bank</b> inheritance diagram
object BankCustomer {
  Name
  Birthdate
}
object BankAccount {
  Balance
  Currency
}
object Address {
  Street
  Zip
  City
}
object BankManager {
  Name
}
object PrivateBankCustomer {
  BonusProgramId
}
object RetailBankCustomer {
  AnnualFees
}
BankManager "*" - "*" BankCustomer
BankManager "0..1" -- "0..1" Address
BankCustomer "0..1" -- "0..1" Address
BankCustomer <|-- PrivateBankCustomer
BankCustomer <|-- RetailBankCustomer
BankCustomer "1" *-- "*" BankAccount
center footer Note: not all attributes shown
```

-   `PrivateBankCustomer` (mit spezifischem Attribut
    `BonusProgramId (int)`)
-   `RetailBankCustomer` (mit spezifischem Attribut
    `AnnualFees (double)`)

1.  Wählen Sie eine der vorgestellten Vererbungsmodellierungen auf der
    DB (`Single Class, Joined Table, Table per Class`) aus.

    <!-- SOLUTION
    SOLUTION -->
2.  Erweitern Sie die Datenbank `bank` zur Unterstützung dieser
    Sub-Typen.
    -   Erzeugen neuer Tabellen mittels `CREATE TABLE ...`, abhängig vom
        gewählten Vererbungsmodell.
    -   Spaltenerweiterung der Tabelle `bankcustomer` mittels
        `ALTER TABLE ... ADD COLUMN ...`. Denken Sie dabei auch an einen
        sinnvollen `DEFAULT` Wert.
    -   Setzen Sie den entsprechenden Klassentyp für bestehenden
        Einträge.
    -   Erstellen von ForeignKey Constraints mittels
        `ALTER TABLE ... ADD FOREIGN KEY ...`, abhängig vom gewählten
        Vererbungsmodell.
    -   Füllen Sie geeignete Werte für `BonusProgramId` und `AnnualFees`
        ein.

    **Tipp**
    :   Verwenden Sie dazu eine separate sql-Skript Datei, z.B.
        `5_inheritance.sql`

3.  Implementieren Sie das Mapping der Klassen in JPA anhand des von
    Ihnen gewählten Vererbungsmodells.

### Aufgabe 3: JPQL

Setzen Sie folgende Abfragen als JPQL in Ihrem Java-Programm für die
Datenbank `bank` um. Erstellen Sie dazu jeweils ein `DynamicQuery`
**und** ein `NamedQuery`.

1.  Menge aller Kunden mit der Summe der Kontostände pro Kunde.
2.  Alle `PrivateBankCustomer` mit Alter \>= 30 Jahre, sortiert nach
    Namen.

### Aufgabe 4: Lost Updates

In der letzten Übung wurde die Methode `transfer()` zur Kontoüberweisung
auf Basis von JPA implementiert.

1.  Definieren Sie ein Szenario, welches einen Lost Update Fehler
    verursacht.
2.  Korrigieren Sie anschliessend den Code mit Hilfe von expliziten
    Locks `EntityManager.lock()`, so dass diese Lost Updates vermieden
    werden.
3.  Testen Sie das gleiche Szenario noch einmal.

**Hinweis:**

-   Um einen Lost Update zu verursachen, können Sie beispielsweise das
    Programm zu einem bestimmten Zeitpunkt anhalten und derweilen eine
    externe Transaktion (in der SQL-Konsole) ausführen.

## Musterlösung

Die Musterlösung zu den Übungen finden Sie im [Branch
OR-Mapper\_JPA\_ff-Solutions](/../tree/OR-Mapper_JPA_ff-Solutions/OR-Mapper_JPA_ff)
oder als
[zip-Datei](/../-/jobs/artifacts/OR-Mapper_JPA_ff-Solutions/raw/OR-Mapper_JPA_ff-Solutions.zip?job=OR-Mapper_JPA_ff-Solutions).

---
keywords:
- database
- sql
- jdbc
- jpa
- orm
- postgresql
- java
title: 'Übung 2: OR Mapper (Fortsetzung)'
---

