# Übung 1: OR Mapper (Einführung)

## Zielsetzung

Einsatz von JPA als OR-Mapper

-   Sammeln von Erfahrung in der Abbildung von Entitäten und Beziehungen
    zur DB.
-   Umsetzung von Lese- und Schreiboperationen auf dem persistenten
    Objektmodell.

## Vorbereitung

Für diese Übung verwenden Sie JPA mittels
[EclipseLink](https://wiki.eclipse.org/EclipseLink) basierend auf einer
[PostgreSQL](https://www.postgresql.org/) Datenbank.

### Eingesetzte Software

Die eingesetzten Tools können Sie entweder als *Native Programme* oder
als *Docker services* laufen lassen. Eine Mischung ist natürlich auch
möglich.

##### Native Programme

Stellen Sie sicher, dass nachfolgende Programme auf Ihrem Rechner
verfügbar sind.

-   [PostgreSQL \>=9.6](https://www.postgresql.org/download/) (enthält
    `psql` command line Tool)
-   [pgAdmin](https://www.pgadmin.org/)
-   Java 8 JDK
-   Java-IDE;
    [Eclipse-JEE-oxygen](https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen2)
    (empfohlen),
    [Eclipse-Java-oxygen](https://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/oxygen2)
    oder [IntelliJ-IDEA](https://www.jetbrains.com/idea/)

##### Programme als Docker services

Linux, respektive Docker Benutzer, können die aufgelisteten Programme
auch als Container laufen lassen. Folgende Container brauchen wir in
dieser Übung:

-   `db2-postgres`
-   `psql`
-   `eclipse-jee`
-   `pgadmin`

Die Spezifikation der verwendeten Services ist in der Datei
[`crane.yml`](Helpers/crane.yml) aufgelistet. Ein Service kann dann mit
dem Tool [`crane`](https://github.com/michaelsauter/crane) gestartet
werden. Eine kurze Anleitung dazu finden Sie auf [dieser
Seite](Helpers/README.md#crane).

### Vorlagen

Die Projektvorlage für diese Aufgabe finden Sie in dieser
[Zip-Datei](/../-/jobs/artifacts/master/raw/OR-Mapper_JPA.zip?job=OR-Mapper_JPA).
In dieser finden Sie das Eclipse-Projekt
[`dbs2.jpa_exercise`](OR-Mapper_JPA/.project) mit Java Klassen, jedoch
noch ohne JPA-Annotationen. Die JPA Libraries und PostgreSQL-JDBC
Drivers für Java-8 sind im Ordner [`lib/`](OR-Mapper_JPA/lib) **bereits
enthalten**. Daher können Sie den Schritt [JPA Libraries und
PostgreSQL-JDBC Drivers
beziehen](#jpa-libraries-und-postgresql-jdbc-drivers-beziehen)
überspringen wenn die verwendete Software dazu passt.

In der Vorlage zur Übung finden Sie die Datei
[`persistence.xml`](OR-Mapper_JPA/src/META-INF/persistence.xml), welche
Ihnen als Vorlage zur Konfiguration Ihres JPA-Programms dienen kann. Sie
müssen dieses eventuell noch an Ihre Umgebung anpassen wenn Sie
beispielsweise einen anderen Port zum
[PostgreSQL](https://www.postgresql.org) Server verwenden.

Die Verzeichnisstruktur eines Java Eclipse Projektes für JPA sieht
demnach ungefähr wie folgt aus:

        |-- lib
        |   |-- eclipselink.jar
        |   |-- javax.persistence_2.2.0.v201708071007.jar
        |   `-- postgresql-42.2.1.jar
        |-- src
        |   |-- ch
        |   |   `-- hsr
        |   |   ...
        |   `-- META-INF
        |       `-- persistence.xml

**Hinweis:**

-   Eine gute Dokumentation zu JPA und Annotationen finden Sie bei
    [`objectdb.org`](https://objectdb.org/java/jpa). Sie können aber
    auch in der neuen [EclipseLink
    Dokumentation](https://wiki.eclipse.org/EclipseLink) oder **besser**
    in der [alten EclipseLink
    Dokumentation](https://wiki.eclipse.org/EclipseLink/UserGuide/JPA)
    nachschlagen.

##### Datei `persistence.xml`

Die Datei
[`persistence.xml`](OR-Mapper_JPA/src/META-INF/persistence.xml) wird von
JPA im Folder [`src/META-INF`](OR-Mapper_JPA/src/META-INF) im
Eclipse-Projekt bzw. im Folder `META_INF` im JAR-File benötigt.

Mit der Einstellung
[`<property name="eclipselink.logging.level" value="ALL"/>`](OR-Mapper_JPA/src/META-INF/persistence.xml#L14)
können Sie den Log Level von EclipseLink konfigurieren damit Sie
beispielsweise die generierten SQL Queries sehen.

##### JPA Libraries und PostgreSQL-JDBC Drivers beziehen

Wenn Sie die Libraries selber einrichten wollen oder Treiber für eine
andere Java Version oder Datenbank benötigen müssen Sie diese vorgängig
herunterladen. Vergessen Sie dabei nicht, die entsprechenden `.jar`
Dateien im `classpath` einzutragen!

-   Beziehen Sie die neuste Version von [EclipseLink Installer
    Zip](https://www.eclipse.org/eclipselink/#download). Für diese Übung haben
    wir die Version aus diesem [Link](https://www.eclipse.org/downloads/download.php?file=/rt/eclipselink/releases/2.7.1/eclipselink-2.7.1.v20171221-bd47e8f.zip&r=1) verwendet. Im Paket finden
    Sie zwei JAR-Libraries, die Sie für das JPA Programm benötigen:

    -   [`eclipselink/jlib/eclipselink.jar`](OR-Mapper_JPA/lib/eclipselink.jar)
    -   [`eclipselink/jlib/jpa/javax.persistence_2.2.0.v201708071007.jar`](OR-Mapper_JPA/lib/javax.persistence_2.2.0.v201708071007.jar)

-   [JDBC-Driver für
    PostgreSQL](https://jdbc.postgresql.org/download.html).

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

## Ausgangslage

Ausgangslage ist die Datenbank [`bank`](Databases/bank) mit folgendem
Relationenmodell:

``` plantuml
caption <b>bank</b> relations diagram
hide circle
class BankCustomer {
}
class CustomerManager {
}
class BankManager {
}
class BankAccount {
}
class Address {
}
BankManager <- CustomerManager
CustomerManager -> BankCustomer
BankManager --> Address
BankCustomer --> Address
BankCustomer <-- BankAccount
```

Ergänzen Sie die vorbereiteten Klassen sukzessive mit den entsprechenden
JPA Annotationen, sodass folgendes Objektmodell daraus resultiert:

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

## Aufgaben

### Aufgabe 1: Entity Mapping

1.  Bilden Sie die Tabellen `BankCustomer`, `BankAccount`, `BankManager`
    und `Address` mit JPA als entsprechende Entity-Klassen **vorerst
    ohne Beziehungen** in ein Java-Programm ab. Setzen Sie zunächst die
    einzelnen Klassen mit den Basisattributen (Integer, Double, String,
    Date, etc.) um.

    **Tipp**
    :   Beginnen Sie mit einer einfachen Abbildung, z.B. `Address`

    <!-- SOLUTION
    SOLUTION -->
2.  Schreiben Sie ein Programm, welches alle Instanzen der
    Entity-Klassen von der DB lädt und deren Zustände in der Standard
    Output Konsole ausgibt.

### Aufgabe 2: Entity Relations

Erweitern Sie das Programm der Aufgabe [Entity
Mapping](#aufgabe-1-entity-mapping) so, dass es auch Beziehungen
zwischen den Entities unterstützt.

1.  Implementieren Sie alle Beziehungen zwischen den Datenbank-Tabellen
    als entsprechende Beziehungen zwischen den Entities.

2.  Schreiben Sie ein Programm, welches alle Instanzen der
    `BankCustomer` direkt von der DB lädt und dann über den Objektgraph
    zu allen benachbarten Entities navigiert (`BankAccount`, `Address`,
    `BankManager`).

3.  Bei welchen der modellierten Beziehungen verwendet JPA Lazy Loading?
    Konfigurieren Sie diese Relationen auch für Eager Loading.

### Aufgabe 3: Entity Updates

Implementieren Sie eine Methode, die eine Banküberweisung zwischen zwei
Konten durchführt. Dies soll über JPA mittels Änderungen auf den
Entities realisiert werden.

``` java
transfer(fromAccountId, toAccountId, amount)
```

Bei nicht existenten Konten soll eine Exception geworfen werden.

### Aufgabe 4: Entity Deletes

Entwickeln Sie eine Funktionalität, um ein Bankkonto zu schliessen. Dazu
soll die entsprechende `BankAccount` Entity in JPA entfernt werden.

``` java
closeAccount(accountId)
```

Falls das Konto nicht existiert, soll eine Exception geworfen werden.

### Aufgabe 5: Entity Inserts

Programmieren Sie die Funktionalität zum Erfassen eines neuen
Bankkunden. Konkret soll via JPA eine neue persistente `BankCustomer`
Entity alloziert werden und in die DB gespeichert werden.

``` java
openAccount(name, birthDate)
```

**Hinweis:**

-   Verwenden Sie die Annotation `@GeneratedValue` bei dem
    Id-Field/Property der Entity-Klasse. Dies bewirkt, dass automatisch
    ein neuer Primary Key für die Id der Entity beim Speichern generiert
    wird.

    ``` java
    @Entity
    public class BankCustomer {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long customerId;
      ...
    }
    ```

    Denken Sie daran, dass Sie eventuell selber sicherstellen müssen,
    dass die entsprechende `SEQUENCE` den richtigen Startwert gesetzt
    hat. Das passiert dann, wenn Sie Datensätze mit expliziten `id`
    Werten in die Datenbank einfüllen weil Sie diese beispielsweise zur
    Referenzierung aus einem anderen Datensatz verwenden. Mehr dazu
    finden Sie in [diesem Issue](/../issues/2).

#### Fakultativ

-   Beim Erfassen des neuen Kunden kann auch gleichzeitig ein neues
    zugehöriges BankAccount-Objekt mit Kontostand 0 angelegt und in der
    DB gespeichert werden.

## Musterlösung

Die Musterlösung zu den Übungen finden Sie im [Branch
OR-Mapper\_JPA-Solutions](/../tree/OR-Mapper_JPA-Solutions/OR-Mapper_JPA)
oder als
[zip-Datei](/../-/jobs/artifacts/OR-Mapper_JPA-Solutions/raw/OR-Mapper_JPA.zip?job=OR-Mapper_JPA).

---
keywords:
- database
- sql
- jdbc
- jpa
- orm
- postgresql
- java
title: 'Übung 1: OR Mapper (Einführung) <!-- SOLUTION Musterlösung SOLUTION -->'
---

