package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the cmtreceiveorder database table.
 * 
 */
@Entity
@NamedQuery(name="Cmtreceiveorder.findAll", query="SELECT c FROM Cmtreceiveorder c")
public class Cmtreceiveorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int receive_ID;

	private String amount;

	private String cmt;

	@Column(name="invoice_no")
	private String invoiceNo;

	@Column(name="invoice_status")
	private String invoiceStatus;

	private String model;

	@Column(name="order_invoiceno")
	private String orderInvoiceno;

	@Temporal(TemporalType.DATE)
	@Column(name="receive_date")
	private Date receiveDate;

	private String status;

	@Column(name="stock_status")
	private String stockStatus;

	@Column(name="total_quantity")
	private String totalQuantity;

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

	public Cmtreceiveorder() {
	}

	public int getReceive_ID() {
		return this.receive_ID;
	}

	public void setReceive_ID(int receive_ID) {
		this.receive_ID = receive_ID;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCmt() {
		return this.cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOrderInvoiceno() {
		return this.orderInvoiceno;
	}

	public void setOrderInvoiceno(String orderInvoiceno) {
		this.orderInvoiceno = orderInvoiceno;
	}

	public Date getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
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

	public String getTotalQuantity() {
		return this.totalQuantity;
	}

	public void setTotalQuantity(String totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

}