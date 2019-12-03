package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fabric_details database table.
 * 
 */
@Entity
@Table(name="fabric_details")
@NamedQuery(name="FabricDetail.findAll", query="SELECT f FROM FabricDetail f")
public class FabricDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="fabric_details_id")
	private int fabricDetailsId;

	@Column(name="fabric_colour")
	private String fabricColour;

	@Column(name="fabric_price")
	private String fabricPrice;

	@Column(name="fabric_quantity")
	private String fabricQuantity;

	@Column(name="fabric_total")
	private String fabricTotal;

	@Column(name="fabric_weight")
	private String fabricWeight;

	private String status;

	//bi-directional many-to-one association to Fabric
	@ManyToOne
	@JoinColumn(name="fabric_id")
	private Fabric fabric;

	public FabricDetail() {
	}

	public int getFabricDetailsId() {
		return this.fabricDetailsId;
	}

	public void setFabricDetailsId(int fabricDetailsId) {
		this.fabricDetailsId = fabricDetailsId;
	}

	public String getFabricColour() {
		return this.fabricColour;
	}

	public void setFabricColour(String fabricColour) {
		this.fabricColour = fabricColour;
	}

	public String getFabricPrice() {
		return this.fabricPrice;
	}

	public void setFabricPrice(String fabricPrice) {
		this.fabricPrice = fabricPrice;
	}

	public String getFabricQuantity() {
		return this.fabricQuantity;
	}

	public void setFabricQuantity(String fabricQuantity) {
		this.fabricQuantity = fabricQuantity;
	}

	public String getFabricTotal() {
		return this.fabricTotal;
	}

	public void setFabricTotal(String fabricTotal) {
		this.fabricTotal = fabricTotal;
	}

	public String getFabricWeight() {
		return this.fabricWeight;
	}

	public void setFabricWeight(String fabricWeight) {
		this.fabricWeight = fabricWeight;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Fabric getFabric() {
		return this.fabric;
	}

	public void setFabric(Fabric fabric) {
		this.fabric = fabric;
	}

}