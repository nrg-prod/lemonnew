package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the month database table.
 * 
 */
@Entity
@NamedQuery(name="Month.findAll", query="SELECT m FROM Month m")
public class Month implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="month_id")
	private int monthId;

	private String type;

	public Month() {
	}

	public int getMonthId() {
		return this.monthId;
	}

	public void setMonthId(int monthId) {
		this.monthId = monthId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}