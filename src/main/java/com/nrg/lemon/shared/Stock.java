package com.nrg.lemon.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the stock database table.
 * 
 */
@Entity
@NamedQuery(name="Stock.findAll", query="SELECT s FROM Stock s")
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int stock_ID;

	@Column(name="item_name")
	private String itemName;
	
	@Column(name="seri_no")
	private String seriNo;

	private String status;

	@Column(name="stock_in")
	private String stockIn;

	@Column(name="stock_out")
	private String stockOut;	
	
	@Column(name="cmt_status")
	private String cmtStatus;
	
	@Column(name="warehouse_status")
	private String warehouseStatus;
	
	//bi-directional many-to-one association to person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	//bi-directional many-to-one association to item_table
	@ManyToOne
	@JoinColumn(name="item_id")
	private ItemTable itemTable;
	
	@Temporal(TemporalType.DATE)
	private Date stockDate;
	private String motive;
	
	
	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	public Stock() {
	}

	public int getStock_ID() {
		return this.stock_ID;
	}

	public void setStock_ID(int stock_ID) {
		this.stock_ID = stock_ID;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStockIn() {
		return this.stockIn;
	}

	public void setStockIn(String stockIn) {
		this.stockIn = stockIn;
	}

	public String getStockOut() {
		return this.stockOut;
	}

	public void setStockOut(String stockOut) {
		this.stockOut = stockOut;
	}

	public ItemTable getItemTable() {
		return itemTable;
	}

	public void setItemTable(ItemTable itemTable) {
		this.itemTable = itemTable;
	}

	public String getCmtStatus() {
		return cmtStatus;
	}

	public void setCmtStatus(String cmtStatus) {
		this.cmtStatus = cmtStatus;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getWarehouseStatus() {
		return warehouseStatus;
	}

	public void setWarehouseStatus(String warehouseStatus) {
		this.warehouseStatus = warehouseStatus;
	}

	public String getSeriNo() {
		return seriNo;
	}

	public void setSeriNo(String seriNo) {
		this.seriNo = seriNo;
	}

	
	
}