package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the printerpurchaseorder database table.
 * 
 */
@Entity
@NamedQuery(name="Printerpurchaseorder.findAll", query="SELECT p FROM Printerpurchaseorder p")
public class Printerpurchaseorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int purchase_ID;

	private String category;

	
	private String invoice;

	private String model;

	@Temporal(TemporalType.DATE)
	private Date orderDate;

	
	private String printer;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	private String quantityser;

	private String status;

	@Column(name="`total quantity`")
	private String total_quantity;

	private String value;
	
	private String invoiceStatus;

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Printerpurchaseorder() {
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

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	public String getPrinter() {
		return this.printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	public String getQuantityser() {
		return this.quantityser;
	}

	public void setQuantityser(String quantityser) {
		this.quantityser = quantityser;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotal_quantity() {
		return this.total_quantity;
	}

	public void setTotal_quantity(String total_quantity) {
		this.total_quantity = total_quantity;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}