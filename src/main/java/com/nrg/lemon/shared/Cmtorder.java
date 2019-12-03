package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cmtorder database table.
 * 
 */
@Entity
@NamedQuery(name="Cmtorder.findAll", query="SELECT c FROM Cmtorder c")
public class Cmtorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int order_ID;

	private String motive;

	private String quantity;

	private String seri;

	private String status;

	private String value;

	//bi-directional many-to-one association to Cmtpurchaseorder
	@ManyToOne
	@JoinColumn(name="purchase_ID")
	private Cmtpurchaseorder cmtpurchaseorder;
	
	//bi-directional many-to-one association to Cmtpurchaseorder
	@ManyToOne
	@JoinColumn(name="receive_ID")
	private Cmtreceiveorder cmtreceiveorder;

	public Cmtorder() {
	}

	public int getOrder_ID() {
		return this.order_ID;
	}

	public void setOrder_ID(int order_ID) {
		this.order_ID = order_ID;
	}

	public String getMotive() {
		return this.motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
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

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Cmtpurchaseorder getCmtpurchaseorder() {
		return this.cmtpurchaseorder;
	}

	public void setCmtpurchaseorder(Cmtpurchaseorder cmtpurchaseorder) {
		this.cmtpurchaseorder = cmtpurchaseorder;
	}

	public Cmtreceiveorder getCmtreceiveorder() {
		return cmtreceiveorder;
	}

	public void setCmtreceiveorder(Cmtreceiveorder cmtreceiveorder) {
		this.cmtreceiveorder = cmtreceiveorder;
	}

	
}