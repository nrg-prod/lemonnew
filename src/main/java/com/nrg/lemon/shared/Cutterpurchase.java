package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cutterpurchase database table.
 * 
 */
@Entity
@NamedQuery(name="Cutterpurchase.findAll", query="SELECT c FROM Cutterpurchase c")
public class Cutterpurchase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int order_ID;

	private String quantity;

	private String seri;

	private String status;
	
	private String motive;
	
	private String orderStatus;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	//bi-directional many-to-one association to Cutterpurchaseorder
	@ManyToOne
	@JoinColumn(name="purchase_ID")
	private Cutterpurchaseorder cutterpurchaseorder;

	public Cutterpurchase() {
	}

	public int getOrder_ID() {
		return this.order_ID;
	}

	public void setOrder_ID(int order_ID) {
		this.order_ID = order_ID;
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

	public Cutterpurchaseorder getCutterpurchaseorder() {
		return this.cutterpurchaseorder;
	}

	public void setCutterpurchaseorder(Cutterpurchaseorder cutterpurchaseorder) {
		this.cutterpurchaseorder = cutterpurchaseorder;
	}

}