package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the manual_stock database table.
 * 
 */
@Entity
@Table(name="manual_stock")
@NamedQuery(name="ManualStock.findAll", query="SELECT m FROM ManualStock m")
public class ManualStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="manual_stock_id")
	private int manualStockId;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name="shop_name")
	private String shopName;

	private String status;

	@Column(name="total_price")
	private String totalPrice;

	//bi-directional many-to-one association to ManualStockOut
	@OneToMany(mappedBy="manualStock")
	private List<ManualStockOut> manualStockOuts;
	
	//bi-directional many-to-one association to person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	public ManualStock() {
	}

	public int getManualStockId() {
		return this.manualStockId;
	}

	public void setManualStockId(int manualStockId) {
		this.manualStockId = manualStockId;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ManualStockOut> getManualStockOuts() {
		return this.manualStockOuts;
	}

	public void setManualStockOuts(List<ManualStockOut> manualStockOuts) {
		this.manualStockOuts = manualStockOuts;
	}

	public ManualStockOut addManualStockOut(ManualStockOut manualStockOut) {
		getManualStockOuts().add(manualStockOut);
		manualStockOut.setManualStock(this);

		return manualStockOut;
	}

	public ManualStockOut removeManualStockOut(ManualStockOut manualStockOut) {
		getManualStockOuts().remove(manualStockOut);
		manualStockOut.setManualStock(null);

		return manualStockOut;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	

}