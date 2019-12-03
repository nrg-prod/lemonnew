package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the cmtstock database table.
 * 
 */
@Entity
@NamedQuery(name="Cmtstock.findAll", query="SELECT c FROM Cmtstock c")
public class Cmtstock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int cmtstock_ID;

	@Column(name="add_quantity")
	private String addQuantity;

	@Column(name="balance_quantity")
	private String balanceQuantity;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String model;

	@Column(name="order_quantity")
	private String orderQuantity;

	private String quantity;

	private String seri;

	private String status;
	
	//bi-directional many-to-one association to person
	@ManyToOne
	@JoinColumn(name="cmtreceive_ID")
	private Cmtreceiveorder cmtreceive;
			
	public Cmtreceiveorder getCmtreceive() {
		return cmtreceive;
	}

	public void setCmtreceive(Cmtreceiveorder cmtreceive) {
		this.cmtreceive = cmtreceive;
	}


	public Cmtstock() {
	}

	public int getCmtstock_ID() {
		return this.cmtstock_ID;
	}

	public void setCmtstock_ID(int cmtstock_ID) {
		this.cmtstock_ID = cmtstock_ID;
	}

	public String getAddQuantity() {
		return this.addQuantity;
	}

	public void setAddQuantity(String addQuantity) {
		this.addQuantity = addQuantity;
	}

	public String getBalanceQuantity() {
		return this.balanceQuantity;
	}

	public void setBalanceQuantity(String balanceQuantity) {
		this.balanceQuantity = balanceQuantity;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOrderQuantity() {
		return this.orderQuantity;
	}

	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
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