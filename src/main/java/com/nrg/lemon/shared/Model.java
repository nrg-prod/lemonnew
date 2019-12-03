package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the model database table.
 * 
 */
@Entity
@NamedQuery(name="Model.findAll", query="SELECT m FROM Model m")
public class Model implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="model_id")
	private int modelId;

	private String capital;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;
			
	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_ID")
	private Client client;

	private String cmt;

	private String cutter;

	private String etc;

	@Column(name="fab_price")
	private String fabPrice;

	private String model;

	private String pole;

	private String printer;

	@Column(name="profit_dz")
	private String profitDz;

	@Column(name="profit_percentage")
	private String profitPercentage;

	@Column(name="sell_price")
	private String sellPrice;

	private String status;
	
	private String seri;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	public Model() {
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getModelId() {
		return this.modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getCapital() {
		return this.capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getCmt() {
		return this.cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public String getCutter() {
		return this.cutter;
	}

	public void setCutter(String cutter) {
		this.cutter = cutter;
	}

	public String getEtc() {
		return this.etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public String getFabPrice() {
		return this.fabPrice;
	}

	public void setFabPrice(String fabPrice) {
		this.fabPrice = fabPrice;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPole() {
		return this.pole;
	}

	public void setPole(String pole) {
		this.pole = pole;
	}

	public String getPrinter() {
		return this.printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	public String getProfitDz() {
		return this.profitDz;
	}

	public void setProfitDz(String profitDz) {
		this.profitDz = profitDz;
	}

	public String getProfitPercentage() {
		return this.profitPercentage;
	}

	public void setProfitPercentage(String profitPercentage) {
		this.profitPercentage = profitPercentage;
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

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getSeri() {
		return seri;
	}

	public void setSeri(String seri) {
		this.seri = seri;
	}
	
	

}