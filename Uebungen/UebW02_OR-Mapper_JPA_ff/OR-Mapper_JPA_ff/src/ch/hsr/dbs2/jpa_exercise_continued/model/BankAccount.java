package ch.hsr.dbs2.jpa_exercise_continued.model;

import javax.persistence.*;

/**
 * The persistent class for the bankaccount database table.
 *
 */
@Entity
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountid;

	private double balance;

	@Enumerated(EnumType.STRING)
	private Currency currency;

	@OneToOne
	@JoinColumn(name="Account_CustomerId")
	private BankCustomer customer;

	public long getAccountid() {
		return accountid;
	}

	public void setAccountid(long accountid) {
		this.accountid = accountid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BankCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(BankCustomer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "\t" + accountid + "\t"
				+ balance + " " + currency;
	}
}
