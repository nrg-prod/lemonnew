package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the opening_stock database table.
 * 
 */
@Entity
@Table(name="opening_stock")
@NamedQuery(name="OpeningStock.findAll", query="SELECT o FROM OpeningStock o")
public class OpeningStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="opening_stock_id")
	private int openingStockId;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name="item_name")
	private String itemName;

	@Column(name="model_no")
	private String modelNo;

	private String quantity;

	private String seri;

	private String status;

	@Column(name="stock_status")
	private String stockStatus;
	
	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	public OpeningStock() {
	}

	public int getOpeningStockId() {
		return this.openingStockId;
	}

	public void setOpeningStockId(int openingStockId) {
		this.openingStockId = openingStockId;
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

	public String getModelNo() {
		return this.modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
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

	public String getStockStatus() {
		return this.stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}