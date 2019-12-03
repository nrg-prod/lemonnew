package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the manual_stock_out database table.
 * 
 */
@Entity
@Table(name="manual_stock_out")
@NamedQuery(name="ManualStockOut.findAll", query="SELECT m FROM ManualStockOut m")
public class ManualStockOut implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="manual_stock_out_id")
	private int manualStockOutId;

	private String model;

	@Column(name="net_amount")
	private String netAmount;
	
	@Column(name="seri")
	private String seri;

	private String quantity;

	@Column(name="selling_price")
	private String sellingPrice;

	@Column(name="shop_name")
	private String shopName;

	//bi-directional many-to-one association to ManualStock
	@ManyToOne
	@JoinColumn(name="manual_stock_id")
	private ManualStock manualStock;

	public ManualStockOut() {
	}

	public int getManualStockOutId() {
		return this.manualStockOutId;
	}

	public void setManualStockOutId(int manualStockOutId) {
		this.manualStockOutId = manualStockOutId;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getNetAmount() {
		return this.netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSellingPrice() {
		return this.sellingPrice;
	}

	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public ManualStock getManualStock() {
		return this.manualStock;
	}

	public void setManualStock(ManualStock manualStock) {
		this.manualStock = manualStock;
	}

	public String getSeri() {
		return seri;
	}

	public void setSeri(String seri) {
		this.seri = seri;
	}

}