package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pursalesdetail_items database table.
 * 
 */
@Entity
@Table(name="pursalesdetail_items")
@NamedQuery(name="PursalesdetailItem.findAll", query="SELECT p FROM PursalesdetailItem p")
public class PursalesdetailItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="pursalesitem_details_id")
	private int pursalesitemDetailsId;

	//bi-directional many-to-one association to ItemTable
	@ManyToOne
	@JoinColumn(name="item_id")
	private ItemTable itemId;

	@Column(name="item_name")
	private String itemName;

	@Column(name="payment_status")
	private String paymentStatus;

	private String price;

	private String quantity;

	private String status;

	private String status2;

	@Column(name="stock_status")
	private String stockStatus;

	@Column(name="total_price")
	private String totalPrice;

	//bi-directional many-to-one association to PursalesItem
	@ManyToOne
	@JoinColumn(name="pursalesitem_id")
	private PursalesItem pursalesItem;

	public PursalesdetailItem() {
	}

	public int getPursalesitemDetailsId() {
		return this.pursalesitemDetailsId;
	}

	public void setPursalesitemDetailsId(int pursalesitemDetailsId) {
		this.pursalesitemDetailsId = pursalesitemDetailsId;
	}

	public ItemTable getItemId() {
		return itemId;
	}

	public void setItemId(ItemTable itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus2() {
		return this.status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getStockStatus() {
		return this.stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	public String getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public PursalesItem getPursalesItem() {
		return this.pursalesItem;
	}

	public void setPursalesItem(PursalesItem pursalesItem) {
		this.pursalesItem = pursalesItem;
	}

}