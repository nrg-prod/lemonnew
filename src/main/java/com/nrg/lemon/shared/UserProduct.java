package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_product database table.
 * 
 */
@Entity
@Table(name="user_product")
@NamedQuery(name="UserProduct.findAll", query="SELECT u FROM UserProduct u")
public class UserProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int user_product_ID;

	private String status;

	//bi-directional many-to-one association to User
		@ManyToOne
		@JoinColumn(name="user_ID")
		private User user;

		//bi-directional many-to-one association to Product
		@ManyToOne
		@JoinColumn(name="product_ID")
		private Product product;
		
	public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

	public UserProduct() {
	}

	public int getUser_product_ID() {
		return this.user_product_ID;
	}

	public void setUser_product_ID(int user_product_ID) {
		this.user_product_ID = user_product_ID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}