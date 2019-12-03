package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the vendor database table.
 * 
 */
@Entity
@NamedQuery(name="Vendor.findAll", query="SELECT v FROM Vendor v")
public class Vendor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="vendor_id")
	private int vendorId;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;
				
	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	@Column(name="division_vendor")
	private String divisionVendor;

	private String status;

	@Column(name="vendor_address")
	private String vendorAddress;

	private String vendor_Hpnumber1;

	private String vendor_Hpnumber2;

	@Column(name="vendor_name")
	private String vendorName;

	public Vendor() {
	}

	public int getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getDivisionVendor() {
		return this.divisionVendor;
	}

	public void setDivisionVendor(String divisionVendor) {
		this.divisionVendor = divisionVendor;
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

	public String getVendorAddress() {
		return this.vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public String getVendor_Hpnumber1() {
		return this.vendor_Hpnumber1;
	}

	public void setVendor_Hpnumber1(String vendor_Hpnumber1) {
		this.vendor_Hpnumber1 = vendor_Hpnumber1;
	}

	public String getVendor_Hpnumber2() {
		return this.vendor_Hpnumber2;
	}

	public void setVendor_Hpnumber2(String vendor_Hpnumber2) {
		this.vendor_Hpnumber2 = vendor_Hpnumber2;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

}