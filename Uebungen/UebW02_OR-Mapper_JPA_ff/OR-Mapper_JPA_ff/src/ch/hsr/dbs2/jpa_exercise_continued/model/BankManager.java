package ch.hsr.dbs2.jpa_exercise_continued.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

/**
 * The persistent class for the bankmanager database table.
 *
 */
@Entity
public class BankManager {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long managerid;

	private String name;

	public long getManagerid() {
		return managerid;
	}

	@OneToOne(optional = true)
	@JoinColumn(name = "Manager_AddressId")
	private Address address;

	@ManyToMany
	@JoinTable(name = "CustomerManager", joinColumns = { @JoinColumn(name = "ManagerId") }, inverseJoinColumns = { @JoinColumn(name = "CustomerId") })
	private Collection<BankCustomer> customers = new ArrayList<>();

	public void setManagerid(long managerid) {
		this.managerid = managerid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Collection<BankCustomer> getCustomers() {
		return customers;
	}

	public void setCustomers(Collection<BankCustomer> customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "\t" + managerid + "\t" + name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BankManager))
			return false;
		return ((BankManager) obj).managerid == managerid;
	}

	public void addCustomer(BankCustomer customer) {
		this.customers.add(customer);
		if(!customer.getManagers().contains(this)) {
			customer.getManagers().add(this);
		}
	}

	public void removeCustomer(BankCustomer customer) {
		this.customers.remove(customer);
		if(customer.getManagers().contains(this)) {
			customer.getManagers().remove(this);
		}
	}
}
