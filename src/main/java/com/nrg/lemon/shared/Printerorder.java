package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the printerorder database table.
 * 
 */
@Entity
@NamedQuery(name="Printerorder.findAll", query="SELECT p FROM Printerorder p")
public class Printerorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="poredr_id")
	private int poredrId;

	private String motive;

	//bi-directional many-to-one association to Printerreceiveorder
	@ManyToOne
	@JoinColumn(name="purchase_id")
	private Printerreceiveorder printerreceiveorder;
	
	//bi-directional many-to-one association to Printerpurchaseorder
		@ManyToOne
		@JoinColumn(name="printerpurchase_id")
		private Printerpurchaseorder printerpurchaseorder;


	private String quantity;

	public Printerpurchaseorder getPrinterpurchaseorder() {
		return printerpurchaseorder;
	}

	public void setPrinterpurchaseorder(Printerpurchaseorder printerpurchaseorder) {
		this.printerpurchaseorder = printerpurchaseorder;
	}

	private String ser;

	private String status;

	private String value;
	
	private String orderstatus;
	
	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public Printerorder() {
	}

	public int getPoredrId() {
		return this.poredrId;
	}

	public void setPoredrId(int poredrId) {
		this.poredrId = poredrId;
	}

	
	public Printerreceiveorder getPrinterreceiveorder() {
		return printerreceiveorder;
	}

	public void setPrinterreceiveorder(Printerreceiveorder printerreceiveorder) {
		this.printerreceiveorder = printerreceiveorder;
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

	public String getSer() {
		return this.ser;
	}

	public void setSer(String ser) {
		this.ser = ser;
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

}