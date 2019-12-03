package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int transaction_ID;

	private String accountno;

	private String amount;

	private String bankname;

	private String cardno;

	@Column(name="category_name")
	private String categoryName;

	@Temporal(TemporalType.DATE)
	private Date chequedate;

	private String chequeno;

	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String name;

	private String note;

	private String paymentmode;

	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	private String status;

	private String transactiontype;
	
	@Column(name="transaction_number")
	private String transactionNumber;

	@Column(name="waste_quantity")
	private String wasteQuantity;

	//bi-directional many-to-one association to TransactionCategory
	@ManyToOne
	@JoinColumn(name="transaction_category_id")
	private TransactionCategory transactionCategory;

	public Transaction() {
	}

	public int getTransaction_ID() {
		return this.transaction_ID;
	}

	public void setTransaction_ID(int transaction_ID) {
		this.transaction_ID = transaction_ID;
	}

	public String getAccountno() {
		return this.accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getChequedate() {
		return this.chequedate;
	}

	public void setChequedate(Date chequedate) {
		this.chequedate = chequedate;
	}

	public String getChequeno() {
		return this.chequeno;
	}

	public void setChequeno(String chequeno) {
		this.chequeno = chequeno;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPaymentmode() {
		return this.paymentmode;
	}

	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactiontype() {
		return this.transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public String getWasteQuantity() {
		return this.wasteQuantity;
	}

	public void setWasteQuantity(String wasteQuantity) {
		this.wasteQuantity = wasteQuantity;
	}

	public TransactionCategory getTransactionCategory() {
		return this.transactionCategory;
	}

	public void setTransactionCategory(TransactionCategory transactionCategory) {
		this.transactionCategory = transactionCategory;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

}