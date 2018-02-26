package ch.hsr.dbs2.jpa_exercise.model;

import javax.persistence.*;

/**
 * The persistent class for the address database table.
 *
 */

@Entity
public class Address {
	
	@Id
	@Column(name="addressid")
	private long id;
	
	private String street;
	
	private int zip;
	
	private String city;
	
	@OneToOne(mappedBy = "address")
	private BankCustomer customer;
	
	@OneToOne(mappedBy = "address")
	private BankManager manager;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "\t" + "TODO";
	}

}
