package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the supply_return database table.
 * 
 */
@Entity
@Table(name="supply_return")
@NamedQuery(name="SupplyReturn.findAll", query="SELECT s FROM SupplyReturn s")
public class SupplyReturn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="`supply_return id`")
	private int supplyReturn_id;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name="item_name")
	private String itemName;

	@Column(name="net_amount")
	private String netAmount;

	//bi-directional many-to-one association to person
	@ManyToOne
	@JoinColumn(name="person_id")
	private Person person;


	private String price;

	private String quantity;

	private String status;

	public SupplyReturn() {
	}

	public int getSupplyReturn_id() {
		return this.supplyReturn_id;
	}

	public void setSupplyReturn_id(int supplyReturn_id) {
		this.supplyReturn_id = supplyReturn_id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getNetAmount() {
		return this.netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}