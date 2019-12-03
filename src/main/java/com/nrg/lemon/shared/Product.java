package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int product_ID;

	private String productCode;

	private String productName;

	private String status;

	//bi-directional many-to-one association to Subproduct
	@OneToMany(mappedBy="product")
	private List<Subproduct> subproducts;

	public Product() {
	}

	public int getProduct_ID() {
		return this.product_ID;
	}

	public void setProduct_ID(int product_ID) {
		this.product_ID = product_ID;
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

	public List<Subproduct> getSubproducts() {
		return this.subproducts;
	}

	public void setSubproducts(List<Subproduct> subproducts) {
		this.subproducts = subproducts;
	}

	public Subproduct addSubproduct(Subproduct subproduct) {
		getSubproducts().add(subproduct);
		subproduct.setProduct(this);

		return subproduct;
	}

	public Subproduct removeSubproduct(Subproduct subproduct) {
		getSubproducts().remove(subproduct);
		subproduct.setProduct(null);

		return subproduct;
	}

}