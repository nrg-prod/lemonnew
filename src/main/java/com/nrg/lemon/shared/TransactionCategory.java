package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the transaction_category database table.
 * 
 */
@Entity
@Table(name="transaction_category")
@NamedQuery(name="TransactionCategory.findAll", query="SELECT t FROM TransactionCategory t")
public class TransactionCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="transaction_category_id")
	private int transactionCategoryId;

	@Column(name="category_name")
	private String categoryName;

	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	private String description;

	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	private String status;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="transactionCategory")
	private List<Transaction> transactions;

	public TransactionCategory() {
	}

	public int getTransactionCategoryId() {
		return this.transactionCategoryId;
	}

	public void setTransactionCategoryId(int transactionCategoryId) {
		this.transactionCategoryId = transactionCategoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setTransactionCategory(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setTransactionCategory(null);

		return transaction;
	}

}