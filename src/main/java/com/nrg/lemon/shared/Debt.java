package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the debt database table.
 * 
 */
@Entity
@NamedQuery(name="Debt.findAll", query="SELECT d FROM Debt d")
public class Debt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="debt_id")
	private int debtId;

	private String amount;

	private String balance;

	private String category;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;
					
	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	@Temporal(TemporalType.DATE)
	@Column(name="debt_date")
	private Date debtDate;

	@Column(name="debt_status")
	private String debtStatus;

	private String name;

	private String reason;

	private String status;

	public Debt() {
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

	public int getDebtId() {
		return this.debtId;
	}

	public void setDebtId(int debtId) {
		this.debtId = debtId;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBalance() {
		return this.balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDebtDate() {
		return this.debtDate;
	}

	public void setDebtDate(Date debtDate) {
		this.debtDate = debtDate;
	}

	public String getDebtStatus() {
		return this.debtStatus;
	}

	public void setDebtStatus(String debtStatus) {
		this.debtStatus = debtStatus;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}