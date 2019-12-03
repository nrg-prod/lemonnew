package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the printerreceiveorder database table.
 * 
 */
@Entity
@NamedQuery(name="Printerreceiveorder.findAll", query="SELECT p FROM Printerreceiveorder p")
public class Printerreceiveorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int purchase_ID;

	private String category;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;
			
	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	private String invoice;

	private String model;

	private String quantityseris;

	private String printer;

	@Temporal(TemporalType.DATE)
	private Date receivedate;

	private String status;

	private String totalquantitry;

	private String value;
	
	private String invoiceStatus;
	
	private String orderInvoiceno;
	
	private String cmtStatus;

	public String getCmtStatus() {
		return cmtStatus;
	}

	public void setCmtStatus(String cmtStatus) {
		this.cmtStatus = cmtStatus;
	}

	public String getOrderInvoiceno() {
		return orderInvoiceno;
	}

	public void setOrderInvoiceno(String orderInvoiceno) {
		this.orderInvoiceno = orderInvoiceno;
	}

	public String getQuantityseris() {
		return quantityseris;
	}

	public void setQuantityseris(String quantityseris) {
		this.quantityseris = quantityseris;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Printerreceiveorder() {
	}

	public int getPurchase_ID() {
		return this.purchase_ID;
	}

	public void setPurchase_ID(int purchase_ID) {
		this.purchase_ID = purchase_ID;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	public String getInvoice() {
		return this.invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	

	public String getPrinter() {
		return this.printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	public Date getReceivedate() {
		return this.receivedate;
	}

	public void setReceivedate(Date receivedate) {
		this.receivedate = receivedate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalquantitry() {
		return this.totalquantitry;
	}

	public void setTotalquantitry(String totalquantitry) {
		this.totalquantitry = totalquantitry;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}