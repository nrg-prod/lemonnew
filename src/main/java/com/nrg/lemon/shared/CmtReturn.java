package com.nrg.lemon.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the cmt_return database table.
 * 
 */
@Entity
@Table(name="cmt_return")
@NamedQuery(name="CmtReturn.findAll", query="SELECT c FROM CmtReturn c")
public class CmtReturn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cmt_return_id")
	private int cmtReturnId;

	private String model;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	private String netprice;
	@ManyToOne
	@JoinColumn(name="person_ID") 
	private Person person;

	private String price;

	private String quantity;

	private String seri;

	private String shop;

	private String status;

	
	@ManyToOne
	@JoinColumn(name="stock_ID") 
	private Stock stocks;

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Stock getStocks() {
		return stocks;
	}

	public void setStocks(Stock stocks) {
		this.stocks = stocks;
	}

	public CmtReturn() {
	}

	public int getCmtReturnId() {
		return this.cmtReturnId;
	}

	public void setCmtReturnId(int cmtReturnId) {
		this.cmtReturnId = cmtReturnId;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getNetprice() {
		return this.netprice;
	}

	public void setNetprice(String netprice) {
		this.netprice = netprice;
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

	public String getSeri() {
		return this.seri;
	}

	public void setSeri(String seri) {
		this.seri = seri;
	}

	public String getShop() {
		return this.shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}