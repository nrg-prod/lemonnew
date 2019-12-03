package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the cmtready database table.
 * 
 */
@Entity
@NamedQuery(name="Cmtready.findAll", query="SELECT c FROM Cmtready c")
public class Cmtready implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int cmtready_ID;

	private String model;

	private String orderStatus;

	private String quantity;

	private String seri;

	private String status;
	
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

	public Cmtready() {
	}

	public int getCmtready_ID() {
		return this.cmtready_ID;
	}

	public void setCmtready_ID(int cmtready_ID) {
		this.cmtready_ID = cmtready_ID;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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