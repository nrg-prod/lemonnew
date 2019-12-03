package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the penalty database table.
 * 
 */
@Entity
@NamedQuery(name="Penalty.findAll", query="SELECT p FROM Penalty p")
public class Penalty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int penalty_ID;

	private String amount;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String reason;

	@Column(name="staff_name")
	private String staffName;

	private String status;

	private String type;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;
	
	public Penalty() {
	}

	public int getPenalty_ID() {
		return this.penalty_ID;
	}

	public void setPenalty_ID(int penalty_ID) {
		this.penalty_ID = penalty_ID;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	

}