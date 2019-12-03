package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the cmt_stockin database table.
 * 
 */
@Entity
@Table(name="cmt_stockin")
@NamedQuery(name="CmtStockin.findAll", query="SELECT c FROM CmtStockin c")
public class CmtStockin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cmt_stockin_id")
	private int cmtStockinId;

	@Column(name="cmt_value")
	private String cmtValue;

	//bi-directional many-to-one association to person
	@ManyToOne
	@JoinColumn(name="cmtstock_id")
	private Cmtstock cmtstockId;

	//bi-directional many-to-one association to person
	@ManyToOne
	@JoinColumn(name="cmtreceive_ID")
	private Cmtreceiveorder cmtreceive;
		
	@Temporal(TemporalType.DATE)
	@Column(name="stockin_date")
	private Date stockinDate;

	@Column(name="stockin_quantity")
	private String stockinQuantity;

	@Column(name="total_quantity")
	private String totalQuantity;

	private String status;
	
	
	public Cmtreceiveorder getCmtreceive() {
		return cmtreceive;
	}

	public void setCmtreceive(Cmtreceiveorder cmtreceive) {
		this.cmtreceive = cmtreceive;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CmtStockin() {
	}

	public int getCmtStockinId() {
		return this.cmtStockinId;
	}

	public void setCmtStockinId(int cmtStockinId) {
		this.cmtStockinId = cmtStockinId;
	}

	public String getCmtValue() {
		return this.cmtValue;
	}

	public void setCmtValue(String cmtValue) {
		this.cmtValue = cmtValue;
	}



	public Cmtstock getCmtstockId() {
		return cmtstockId;
	}

	public void setCmtstockId(Cmtstock cmtstockId) {
		this.cmtstockId = cmtstockId;
	}

	public Date getStockinDate() {
		return this.stockinDate;
	}

	public void setStockinDate(Date stockinDate) {
		this.stockinDate = stockinDate;
	}

	public String getStockinQuantity() {
		return this.stockinQuantity;
	}

	public void setStockinQuantity(String stockinQuantity) {
		this.stockinQuantity = stockinQuantity;
	}

	public String getTotalQuantity() {
		return this.totalQuantity;
	}

	public void setTotalQuantity(String totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

}