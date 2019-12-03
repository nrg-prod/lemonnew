package com.nrg.lemon.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the branch database table.
 * 
 */
@Entity
@NamedQuery(name="Branch.findAll", query="SELECT b FROM Branch b")
public class Branch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int branch_ID;

	private String bonus;

	private String branchName;

	private String late;

	private String overtime;

	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	private String status;
	
	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;


	public Branch() {
	}

	public int getBranch_ID() {
		return this.branch_ID;
	}

	public void setBranch_ID(int branch_ID) {
		this.branch_ID = branch_ID;
	}

	public String getBonus() {
		return this.bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	public String getLate() {
		return this.late;
	}

	public void setLate(String late) {
		this.late = late;
	}

	public String getOvertime() {
		return this.overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}