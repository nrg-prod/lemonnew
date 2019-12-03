package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@NamedQuery(name="Client.findAll", query="SELECT c FROM Client c")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int client_ID;

	private String address;

	@Column(name="client_name")
	private String clientName;

	@Temporal(TemporalType.DATE)
	@Column(name="`created date`")
	private Date created_date;

	private String logoname;

	@Column(name="phone_number")
	private String phoneNumber;

	private String status;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="client")
	private List<Person> persons;

	public Client() {
	}

	public int getClient_ID() {
		return this.client_ID;
	}

	public void setClient_ID(int client_ID) {
		this.client_ID = client_ID;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getCreated_date() {
		return this.created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getLogoname() {
		return this.logoname;
	}

	public void setLogoname(String logoname) {
		this.logoname = logoname;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Person addPerson(Person person) {
		getPersons().add(person);
		person.setClient(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setClient(null);

		return person;
	}

}