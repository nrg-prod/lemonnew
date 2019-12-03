package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the fabric database table.
 * 
 */
@Entity
@NamedQuery(name="Fabric.findAll", query="SELECT f FROM Fabric f")
public class Fabric implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="fabric_id")
	private int fabricId;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="fabric_name")
	private String fabricName;

	@Column(name="invoice_number")
	private String invoiceNumber;

	@Column(name="payment_status")
	private String paymentStatus;
	
	@Column(name="invoice_status")
	private String invoiceStatus;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	@Temporal(TemporalType.DATE)
	@Column(name="purchase_date")
	private Date purchaseDate;

	private String status;

	@Column(name="supplier_name")
	private String supplierName;

	@Column(name="total_amount")
	private String totalAmount;

	@Column(name="total_quantity")
	private String totalQuantity;

	@Column(name="total_weight")
	private String totalWeight;

	//bi-directional many-to-one association to FabricDetail
	@OneToMany(mappedBy="fabric")
	private List<FabricDetail> fabricDetails;

	public Fabric() {
	}
	
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public int getFabricId() {
		return this.fabricId;
	}

	public void setFabricId(int fabricId) {
		this.fabricId = fabricId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getFabricName() {
		return this.fabricName;
	}

	public void setFabricName(String fabricName) {
		this.fabricName = fabricName;
	}

	public String getInvoiceNumber() {
		return this.invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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

	public List<FabricDetail> getFabricDetails() {
		return this.fabricDetails;
	}

	public void setFabricDetails(List<FabricDetail> fabricDetails) {
		this.fabricDetails = fabricDetails;
	}

	public FabricDetail addFabricDetail(FabricDetail fabricDetail) {
		getFabricDetails().add(fabricDetail);
		fabricDetail.setFabric(this);

		return fabricDetail;
	}

	public FabricDetail removeFabricDetail(FabricDetail fabricDetail) {
		getFabricDetails().remove(fabricDetail);
		fabricDetail.setFabric(null);

		return fabricDetail;
	}

}