package ch.hsr.dbs2.jpa_exercise.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the bankcustomer database table.
 *
 */

@Entity
public class BankCustomer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	@OneToOne(optional = true)
	@JoinColumn(name = "customer_addressid")
	private Address address;
	
	@ManyToMany(mappedBy = "customers", fetch=FetchType.EAGER)
	private Collection<BankManager> managers = new ArrayList<>();
	
	@OneToMany
	@JoinColumn(name = "account_customerid", referencedColumnName = "customerid")
	private Collection<BankAccount> accounts = new ArrayList<>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Collection<BankManager> getManagers() {
		return managers;
	}
	
	public void setManagers(Collection<BankManager> managers) {
		this.managers = managers;
	}
	
	public Collection<BankAccount> getAccounts() {
		return accounts;
	}
	
	public void setAccount(Collection<BankAccount> accounts) {
		this.accounts = accounts;
	}
	

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "\t" + "TODO";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BankCustomer)) return false;
		return ((BankCustomer)obj).id == id;
	}

}
