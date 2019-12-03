package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the subproduct database table.
 * 
 */
@Entity
@NamedQuery(name="Subproduct.findAll", query="SELECT s FROM Subproduct s")
public class Subproduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int subproduct_ID;

	private String productCode;

	private String productName;

	private String status;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_ID")
	private Product product;

	public Subproduct() {
	}

	public int getSubproduct_ID() {
		return this.subproduct_ID;
	}

	public void setSubproduct_ID(int subproduct_ID) {
		this.subproduct_ID = subproduct_ID;
	}

	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}