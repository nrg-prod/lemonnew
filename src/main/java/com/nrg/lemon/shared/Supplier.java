package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the supplier database table.
 * 
 */
@Entity
@NamedQuery(name="Supplier.findAll", query="SELECT s FROM Supplier s")
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="supplier_id")
	private int supplierId;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;
				
	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	@Column(name="contact_name")
	private String contactName;

	private String division;

	@Column(name="hp_number1")
	private String hpNumber1;

	@Column(name="hp_number2")
	private String hpNumber2;

	@Column(name="office_number1")
	private String officeNumber1;

	@Column(name="office_number2")
	private String officeNumber2;

	private String status;

	@Column(name="supplier_name")
	private String supplierName;

	public Supplier() {
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getHpNumber1() {
		return this.hpNumber1;
	}

	public void setHpNumber1(String hpNumber1) {
		this.hpNumber1 = hpNumber1;
	}

	public String getHpNumber2() {
		return this.hpNumber2;
	}

	public void setHpNumber2(String hpNumber2) {
		this.hpNumber2 = hpNumber2;
	}

	public String getOfficeNumber1() {
		return this.officeNumber1;
	}

	public void setOfficeNumber1(String officeNumber1) {
		this.officeNumber1 = officeNumber1;
	}

	public String getOfficeNumber2() {
		return this.officeNumber2;
	}

	public void setOfficeNumber2(String officeNumber2) {
		this.officeNumber2 = officeNumber2;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}