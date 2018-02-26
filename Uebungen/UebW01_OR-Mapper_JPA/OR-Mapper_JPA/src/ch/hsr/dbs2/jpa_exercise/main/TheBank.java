package ch.hsr.dbs2.jpa_exercise.main;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import ch.hsr.dbs2.jpa_exercise.model.Address;
import ch.hsr.dbs2.jpa_exercise.model.BankAccount;
import ch.hsr.dbs2.jpa_exercise.model.BankCustomer;
import ch.hsr.dbs2.jpa_exercise.model.BankManager;
import ch.hsr.dbs2.jpa_exercise.model.Currency;

public class TheBank {

	private static EntityManagerFactory factory;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory("Bank");
		EntityManager em = factory.createEntityManager();
		try {
			printAddresses(em);
			printBankAccounts(em);
			printBankCustomers(em);
			printBankManagers(em);
		} finally {
			em.close();
		}
	}

	private static void printAddresses(EntityManager em) {
		Query query = em.createQuery("select a from Address a");
		@SuppressWarnings("unchecked")
		List<Address> list = query.getResultList();
		for (Address b : list) {
			System.out.println(b);
		}
	}

	private static void printBankAccounts(EntityManager em) {
		Query query = em.createQuery("select a from BankAccount a");
		@SuppressWarnings("unchecked")
		List<BankAccount> list = query.getResultList();
		for (BankAccount b : list) {
			System.out.println(b);
		}
	}

	private static void printBankCustomers(EntityManager em) {
		Query query = em.createQuery("select a from BankCustomer a");
		@SuppressWarnings("unchecked")
		List<BankCustomer> list = query.getResultList();
		for (BankCustomer b : list) {
			System.out.println(b);
		}
	}

	private static void printBankManagers(EntityManager em) {
		Query query = em.createQuery("select a from BankManager a");
		@SuppressWarnings("unchecked")
		List<BankManager> list = query.getResultList();
		for (BankManager b : list) {
			System.out.println(b);
		}
	}
	
	public static void transfer(long fromAccountId, long toAccountId, double amount) {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			BankAccount from = em.find(BankAccount.class, fromAccountId);
			BankAccount to = em.find(BankAccount.class, toAccountId);
			from.setBalance(from.getBalance() - amount);
			to.setBalance(to.getBalance() + amount);
			System.out.println("ACTION TRANSFER: " + from + " => " + to + ", " + amount);
			em.getTransaction().commit();
		} catch(Exception e) {
			em.getTransaction().rollback();
			System.err.println("Failed to execute transfer with message [" + e.getMessage() + "]");
		} finally {
			em.close();
		}
	}
	
	public static void closeAccount(long accountId) {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			BankAccount account = em.find(BankAccount.class, accountId);
			em.remove(account);
			System.out.println("ACTION CLOSE: " + accountId);
			em.getTransaction().commit();
		} catch (Exception e){
			em.getTransaction().rollback();
			System.err.println("Failed to close account with message [" + e.getMessage() + "]");
		} finally {
			em.close();
		}
	}
	
	public static void openAccount(String name, Date birthDate) {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			BankCustomer customer = new BankCustomer();
			customer.setName(name);
			customer.setBirthdate(birthDate);
			
			BankAccount account = new BankAccount();
			account.setBalance(0);
			account.setCurrency(Currency.CHF);
			account.setCustomer(customer);
			em.persist(account);
			
			customer.getAccounts().add(account);
			em.persist(customer);
			
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.err.println("Failed to open account with message [" + e.getMessage() + "]");
		} finally {
			em.close();
		}
	}

}
