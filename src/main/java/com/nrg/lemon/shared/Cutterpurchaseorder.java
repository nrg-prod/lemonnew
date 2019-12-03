package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cutterpurchaseorder database table.
 * 
 */
@Entity
@NamedQuery(name="Cutterpurchaseorder.findAll", query="SELECT c FROM Cutterpurchaseorder c")
public class Cutterpurchaseorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int purchase_ID;

	private String category;

	private String cutter;

	@Column(name="fabric_price")
	private String fabricPrice;

	@Column(name="invoice_no")
	private String invoiceNo;

	private String model;

	@Temporal(TemporalType.DATE)
	@Column(name="order_date")
	private Date orderDate;

	private String pole;

	@Column(name="quantity_seri")
	private String quantitySeri;

	@Column(name="remain_weight")
	private String remainWeight;

	private String result;

	private String saving;

	private String seri;

	private String status;

	@Column(name="total_quantity")
	private String totalQuantity;

	@Column(name="total_weight")
	private String totalWeight;

	private String value;
	
	private String invoiceStatus;
	
	private String printerStatus;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	//bi-directional many-to-one association to Cutterpurchase
	@OneToMany(mappedBy="cutterpurchaseorder")
	private List<Cutterpurchase> cutterpurchases;

	public Cutterpurchaseorder() {
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


	public String getCutter() {
		return this.cutter;
	}

	public void setCutter(String cutter) {
		this.cutter = cutter;
	}

	public String getFabricPrice() {
		return this.fabricPrice;
	}

	public void setFabricPrice(String fabricPrice) {
		this.fabricPrice = fabricPrice;
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


	public String getPole() {
		return this.pole;
	}

	public void setPole(String pole) {
		this.pole = pole;
	}

	public String getQuantitySeri() {
		return this.quantitySeri;
	}

	public void setQuantitySeri(String quantitySeri) {
		this.quantitySeri = quantitySeri;
	}

	public String getRemainWeight() {
		return this.remainWeight;
	}

	public void setRemainWeight(String remainWeight) {
		this.remainWeight = remainWeight;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSaving() {
		return this.saving;
	}

	public void setSaving(String saving) {
		this.saving = saving;
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

	public String getTotalQuantity() {
		return this.totalQuantity;
	}

	public void setTotalQuantity(String totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getTotalWeight() {
		return this.totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Cutterpurchase> getCutterpurchases() {
		return this.cutterpurchases;
	}

	public void setCutterpurchases(List<Cutterpurchase> cutterpurchases) {
		this.cutterpurchases = cutterpurchases;
	}

	public Cutterpurchase addCutterpurchas(Cutterpurchase cutterpurchas) {
		getCutterpurchases().add(cutterpurchas);
		cutterpurchas.setCutterpurchaseorder(this);

		return cutterpurchas;
	}

	public Cutterpurchase removeCutterpurchas(Cutterpurchase cutterpurchas) {
		getCutterpurchases().remove(cutterpurchas);
		cutterpurchas.setCutterpurchaseorder(null);

		return cutterpurchas;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
	public String getPrinterStatus() {
		return printerStatus;
	}

	public void setPrinterStatus(String printerStatus) {
		this.printerStatus = printerStatus;
	}
}