package com.nrg.lemon.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the payment database table.
 * 
 */
@Entity
@NamedQuery(name="Payment.findAll", query="SELECT p FROM Payment p")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int payment_ID;

	@Column(name="balance_amount")
	private String balanceAmount;

	@Column(name="paid_amount")
	private String paidAmount;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date")
	private Date date;
	
	private String status;

	@Column(name="total_amount")
	private String totalAmount;

	//bi-directional many-to-one association to PursalesItem
	@ManyToOne
	@JoinColumn(name="pursalesitem_id")
	private PursalesItem pursalesItem;
	
	//bi-directional many-to-one association to fabric
	@ManyToOne
	@JoinColumn(name="fabric_id")
	private Fabric fabric;
	
	//bi-directional many-to-one association to Cutterpurchaseorder
	@ManyToOne
	@JoinColumn(name="cutter_ID")
	private Cutterpurchaseorder cutterPurchase;
	
	//bi-directional many-to-one association to Cutterpurchaseorder
	@ManyToOne
	@JoinColumn(name="printer_ID")
	private Printerreceiveorder printerReceiver;
	
	//bi-directional many-to-one association to Cutterpurchaseorder
	@ManyToOne
	@JoinColumn(name="cmt_ID")
	private Cmtreceiveorder cmtReceiver;
	
	public Cmtreceiveorder getCmtReceiver() {
		return cmtReceiver;
	}

	public void setCmtReceiver(Cmtreceiveorder cmtReceiver) {
		this.cmtReceiver = cmtReceiver;
	}

	public Printerreceiveorder getPrinterReceiver() {
		return printerReceiver;
	}

	public void setPrinterReceiver(Printerreceiveorder printerReceiver) {
		this.printerReceiver = printerReceiver;
	}

	public Cutterpurchaseorder getCutterPurchase() {
		return cutterPurchase;
	}

	public void setCutterPurchase(Cutterpurchaseorder cutterPurchase) {
		this.cutterPurchase = cutterPurchase;
	}

	public PursalesItem getPursalesItem() {
		return pursalesItem;
	}

	public void setPursalesItem(PursalesItem pursalesItem) {
		this.pursalesItem = pursalesItem;
	}

	public Payment() {
	}

	public int getPayment_ID() {
		return this.payment_ID;
	}

	public void setPayment_ID(int payment_ID) {
		this.payment_ID = payment_ID;
	}

	public String getBalanceAmount() {
		return this.balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getPaidAmount() {
		return this.paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Fabric getFabric() {
		return fabric;
	}

	public void setFabric(Fabric fabric) {
		this.fabric = fabric;
	}
	
	

}