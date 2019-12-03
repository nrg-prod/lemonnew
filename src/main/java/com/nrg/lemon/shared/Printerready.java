package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the printerready database table.
 * 
 */
@Entity
@NamedQuery(name="Printerready.findAll", query="SELECT p FROM Printerready p")
public class Printerready implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int printerready_ID;

	private String model;

	private String quantity;

	private String seri;

	private String status;
	
	private String orderStatus;
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

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

	public Printerready() {
	}

	public int getPrinterready_ID() {
		return this.printerready_ID;
	}

	public void setPrinterready_ID(int printerready_ID) {
		this.printerready_ID = printerready_ID;
	}


	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}


	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSeri() {
		return this.seri;
	}

	public void setSeri(String seri) {
		this.seri = seri;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}