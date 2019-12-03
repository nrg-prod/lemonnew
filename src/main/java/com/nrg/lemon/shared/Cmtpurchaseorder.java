package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cmtpurchaseorder database table.
 * 
 */
@Entity
@NamedQuery(name="Cmtpurchaseorder.findAll", query="SELECT c FROM Cmtpurchaseorder c")
public class Cmtpurchaseorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int purchase_ID;

	private String category;

	private String cmt;

	@Column(name="invoice_no")
	private String invoiceNo;

	private String model;

	@Temporal(TemporalType.DATE)
	@Column(name="order_date")
	private Date orderDate;

	@Column(name="quantity_seri")
	private String quantitySeri;

	private String status;

	@Column(name="total_quantity")
	private String totalQuantity;

	private String value;
	
	private String seri;
	
	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	//bi-directional many-to-one association to Cmtorder
	@OneToMany(mappedBy="cmtpurchaseorder")
	private List<Cmtorder> cmtorders;
	
	private String printerInvoice;
	
	private String invoiceStatus;

	public String getPrinterInvoice() {
		return printerInvoice;
	}

	public void setPrinterInvoice(String printerInvoice) {
		this.printerInvoice = printerInvoice;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Cmtpurchaseorder() {
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

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getQuantitySeri() {
		return this.quantitySeri;
	}

	public void setQuantitySeri(String quantitySeri) {
		this.quantitySeri = quantitySeri;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalQuantity() {
		return this.totalQuantity;
	}

	public void setTotalQuantity(String totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Cmtorder> getCmtorders() {
		return this.cmtorders;
	}

	public void setCmtorders(List<Cmtorder> cmtorders) {
		this.cmtorders = cmtorders;
	}

	public Cmtorder addCmtorder(Cmtorder cmtorder) {
		getCmtorders().add(cmtorder);
		cmtorder.setCmtpurchaseorder(this);

		return cmtorder;
	}

	public Cmtorder removeCmtorder(Cmtorder cmtorder) {
		getCmtorders().remove(cmtorder);
		cmtorder.setCmtpurchaseorder(null);

		return cmtorder;
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

	public String getSeri() {
		return seri;
	}

	public void setSeri(String seri) {
		this.seri = seri;
	}
	
}