package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int employee_ID;

	private String address;

	private String alergy;

	private String bloodgroup;

	private String comission;

	@Column(name="contact_name1")
	private String contactName1;

	@Column(name="contact_name2")
	private String contactName2;

	private String daily;

	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name="emergency_address1")
	private String emergencyAddress1;

	@Column(name="emergency_address2")
	private String emergencyAddress2;

	@Column(name="emergency_contactno1")
	private String emergencyContactno1;

	@Column(name="emergency_contactno2")
	private String emergencyContactno2;

	@Column(name="emergency_contactno3")
	private String emergencyContactno3;

	@Column(name="emergency_contactno4")
	private String emergencyContactno4;

	@Column(name="employee_name")
	private String employeeName;

	@Temporal(TemporalType.DATE)
	@Column(name="entry_date")
	private Date entryDate;

	@Column(name="phone_no1")
	private String phoneNo1;

	@Column(name="phone_no2")
	private String phoneNo2;

	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name="update_date")
	private Date updateDate;

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
		
	public Employee() {
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

	public int getEmployee_ID() {
		return this.employee_ID;
	}

	public void setEmployee_ID(int employee_ID) {
		this.employee_ID = employee_ID;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAlergy() {
		return this.alergy;
	}

	public void setAlergy(String alergy) {
		this.alergy = alergy;
	}

	public String getBloodgroup() {
		return this.bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}


	public String getComission() {
		return this.comission;
	}

	public void setComission(String comission) {
		this.comission = comission;
	}

	public String getContactName1() {
		return this.contactName1;
	}

	public void setContactName1(String contactName1) {
		this.contactName1 = contactName1;
	}

	public String getContactName2() {
		return this.contactName2;
	}

	public void setContactName2(String contactName2) {
		this.contactName2 = contactName2;
	}

	public String getDaily() {
		return this.daily;
	}

	public void setDaily(String daily) {
		this.daily = daily;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmergencyAddress1() {
		return this.emergencyAddress1;
	}

	public void setEmergencyAddress1(String emergencyAddress1) {
		this.emergencyAddress1 = emergencyAddress1;
	}

	public String getEmergencyAddress2() {
		return this.emergencyAddress2;
	}

	public void setEmergencyAddress2(String emergencyAddress2) {
		this.emergencyAddress2 = emergencyAddress2;
	}

	public String getEmergencyContactno1() {
		return this.emergencyContactno1;
	}

	public void setEmergencyContactno1(String emergencyContactno1) {
		this.emergencyContactno1 = emergencyContactno1;
	}

	public String getEmergencyContactno2() {
		return this.emergencyContactno2;
	}

	public void setEmergencyContactno2(String emergencyContactno2) {
		this.emergencyContactno2 = emergencyContactno2;
	}

	public String getEmergencyContactno3() {
		return this.emergencyContactno3;
	}

	public void setEmergencyContactno3(String emergencyContactno3) {
		this.emergencyContactno3 = emergencyContactno3;
	}

	public String getEmergencyContactno4() {
		return this.emergencyContactno4;
	}

	public void setEmergencyContactno4(String emergencyContactno4) {
		this.emergencyContactno4 = emergencyContactno4;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}


	public String getPhoneNo1() {
		return this.phoneNo1;
	}

	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}

	public String getPhoneNo2() {
		return this.phoneNo2;
	}

	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}