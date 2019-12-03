package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the printer_return database table.
 * 
 */
@Entity
@Table(name="printer_return")
@NamedQuery(name="PrinterReturn.findAll", query="SELECT p FROM PrinterReturn p")
public class PrinterReturn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="printer_return_id")
	private int printerReturnId;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String model;

	@Column(name="net_price")
	private String netPrice;

	//bi-directional many-to-one association to person
	@ManyToOne
	@JoinColumn(name="person_id")
	private Person person;

	//bi-directional many-to-one association to person
	@ManyToOne
	@JoinColumn(name="porder_id")
	private Printerorder printerorder;
	
	private String price;

	private String quantity;

	private String seri;

	private String status;

	public PrinterReturn() {
	}

	public int getPrinterReturnId() {
		return this.printerReturnId;
	}

	public void setPrinterReturnId(int printerReturnId) {
		this.printerReturnId = printerReturnId;
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

	public String getNetPrice() {
		return this.netPrice;
	}

	public void setNetPrice(String netPrice) {
		this.netPrice = netPrice;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Printerorder getPrinterorder() {
		return printerorder;
	}

	public void setPrinterorder(Printerorder printerorder) {
		this.printerorder = printerorder;
	}
	

}