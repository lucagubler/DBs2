package ch.hsr.dbs2.jpa_exercise.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

/**
 * The persistent class for the bankmanager database table.
 *
 */
@Entity
public class BankManager {
	@OneToOne(optional = true)
	@JoinColumn(name = "manager_addressid")
	private Address address;
	
	@ManyToMany
	@JoinTable(name = "customermanager", joinColumns = {@JoinColumn(name="managerid")}, inverseJoinColumns = {@JoinColumn(name="customerid")})
	private Collection<BankCustomer> customers = new ArrayList<>();
	

	
	
	@Id
	@Column(name="managerid")
	private long id;
	
	public long getId() {
		return id;
	}
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName() {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "\t" + "TODO";
	}

}
