package ch.hsr.dbs2.jpa_exercise_continued.main;

import javax.persistence.*;

public class TheBank {

	private static EntityManagerFactory factory;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory("Bank");
		aufgabe1();
		aufgabe2();
		aufgabe3a();
		aufgabe3b();
		aufgabe4();
	}

	/*
	 * Aufgabe 1: Bidirektionale Beziehung
	 */
	private static void aufgabe1() {
		EntityManager em = factory.createEntityManager();
		try {
			/* Szenario Beziehung Einfügen */

			/* TODO: Lade Daten zum Kunden 3 */

			/* TODO: Erstelle den Manager "Edward Miller" */

			/* TODO: Füge dem Kunden 3 den neuen Manager hinzu */

			/* TODO: Teste ob neuer Manager Kunde 3 in seiner Liste hat */

			/* TODO: Persistiere die Objekte zur Datenbank */

			/*
			 * Szenario Beziehung Entfernen; setzt voraus, dass in der Datenbank
			 * die Beziehung "Kunde 4 wird von Manager 1 betreut" vorhanden ist
			 */

			/* TODO: Lade Kunde 4 */

			/* TODO: Entferne den Manager 1 für Kunde 4 */

			/* TODO: Teste ob Manager 1 Kunde 4 noch in seiner Liste hat */

			/* TODO: Persistiere die Änderungen */
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.err.println("Failed to execute transaction with message [" + e.getMessage() + "]");
		} finally {
			em.close();
		}
	}

	/*
	 * Aufgabe 2: Vererbung
	 *
	 * Wir wählen die JOIN Strategie. (In der Musterlösung wird JOINED
	 * verwendet) Die nötigen Anpassungen an den Tabellen finden Sie im Skript
	 * src/db/5_inheritance.sql
	 */
	private static void aufgabe2() {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			/*
			 * TODO: Erstelle neuen RetailBankCustomer mit Name, Geburtsdatum
			 * (>30J) und AnnualFees
			 */

			/*
			 * TODO: Erstelle neuen PrivatBankCustomer mit Name, Geburtsdatum
			 * (<30J) und BonusProgrammId
			 */

			/*
			 * TODO: Erstelle weiteren PrivatBankCustomer mit Name, Geburtsdatum
			 * (>30J) und BonusProgrammId
			 */

			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.err.println("Failed to execute transaction with message [" + e.getMessage() + "]");
		} finally {
			em.close();
		}
	}

	/*
	 * Aufgabe 3a: JPQL; DynamicQuery
	 */
	@SuppressWarnings("unchecked")
	private static void aufgabe3a() {
		EntityManager em = factory.createEntityManager();
		try {
			System.out.println();
			System.out.println("DynamicQuery: Menge aller Kunden mit der Summe der Kontostände pro Kunde");

			/* TODO: Dynamic Query erstellen */

			/* TODO: Ausgabe Kundenname und Kontostand */

			System.out.println();
			System.out.println("DynamicQuery: Alle PrivateBankCustomer mit Alter >= 30 Jahre, sortiert nach Namen");

			/* TODO: Dynamic Query mit Übergabeparameter 'Alter' erstellen */

			/*
			 * TODO: Abfrage und Ausgabe der gefundenen PrivatBankCustomers >=
			 * 30J
			 */

		} catch (Exception e) {
			System.err.println("Failed to execute transaction with message [" + e.getMessage() + "]");
		} finally {
			em.close();
		}
	}

	/*
	 * Aufgabe 3b: JPQL; NamedQuery
	 */
	@SuppressWarnings("unchecked")
	private static void aufgabe3b() {
		EntityManager em = factory.createEntityManager();
		try {
			System.out.println();
			System.out.println("NamedQuery: Menge aller Kunden mit der Summe der Kontostände pro Kunde");

			/* TODO: Named Query erstellen */

			/* TODO: Ausgabe Kundenname und Kontostand */

			System.out.println();
			System.out.println("NamedQuery: Alle PrivateBankCustomer mit Alter >= 30 Jahre, sortiert nach Namen");

			/* TODO: Named Query mit Übergabeparameter 'Alter' erstellen */

			/*
			 * TODO: Abfrage und Ausgabe der gefundenen PrivatBankCustomers >=
			 * 30J
			 */

		} catch (Exception e) {
			System.err.println("Failed to execute transaction with message [" + e.getMessage() + "]");
		} finally {
			em.close();
		}
	}

	/*
	 * Aufgabe4: Lost Updates
	 *
	 * Lost-Update-Szenario etwa wie hier beschrieben:
	 * http://www.developer.com/java/manage-concurrent-access-to-jpa-entity-with
	 * -locking.html
	 *
	 */
	private static void aufgabe4() {
		/* Szenario Lost update Fehler */
		/* Szenario Lösungsvariante implementieren */
		/* Szenario Lost update Fehler behoben? */
	}

}
