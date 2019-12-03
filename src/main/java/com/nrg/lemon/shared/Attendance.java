package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the attendance database table.
 * 
 */
@Entity
@NamedQuery(name="Attendance.findAll", query="SELECT a FROM Attendance a")
public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int attendance_ID;

	private String attend;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String forfeit;

	private String late;

	@Column(name="over_time")
	private String overTime;


	private String reward;

	private String status;
	
	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;
		
	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="branch_ID")
	private Branch branch;
	
	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="employee_ID")
	private Employee employee;


	public Attendance() {
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


	public Branch getBranch() {
		return branch;
	}


	public void setBranch(Branch branch) {
		this.branch = branch;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public int getAttendance_ID() {
		return this.attendance_ID;
	}

	public void setAttendance_ID(int attendance_ID) {
		this.attendance_ID = attendance_ID;
	}

	public String getAttend() {
		return this.attend;
	}

	public void setAttend(String attend) {
		this.attend = attend;
	}
	
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getForfeit() {
		return this.forfeit;
	}

	public void setForfeit(String forfeit) {
		this.forfeit = forfeit;
	}

	public String getLate() {
		return this.late;
	}

	public void setLate(String late) {
		this.late = late;
	}

	public String getOverTime() {
		return this.overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}


	public String getReward() {
		return this.reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}