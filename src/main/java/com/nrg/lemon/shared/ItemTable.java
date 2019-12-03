package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;



/**
 * The persistent class for the item_table database table.
 * 
 */
@Entity
@Table(name="item_table")
@NamedQuery(name="ItemTable.findAll", query="SELECT i FROM ItemTable i")
public class ItemTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="item_id")
	private int itemId;

	@Column(name="buy_price")
	private String buyPrice;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	@Column(name="item_name")
	private String itemName;

	@Column(name="item_status")
	private String itemStatus;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	@Column(name="sell_price")
	private String sellPrice;

	private String status;

	public ItemTable() {
	}

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getBuyPrice() {
		return this.buyPrice;
	}

	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
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

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemStatus() {
		return this.itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}