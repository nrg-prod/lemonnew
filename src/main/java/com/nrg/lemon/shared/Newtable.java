package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the newtable database table.
 * 
 */
@Entity
@NamedQuery(name="Newtable.findAll", query="SELECT n FROM Newtable n")
public class Newtable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="table_id")
	private int tableId;

	private String empty;

	private String filled;

	private String status;

	@Column(name="table_name")
	private String tableName;

	@Column(name="table_no")
	private String tableNo;

	private String tablecolumn;

	private String tablerow;

	//bi-directional many-to-one association to TableDetail
	@OneToMany(mappedBy="newtable")
	private List<TableDetail> tableDetails;

	public Newtable() {
	}

	public int getTableId() {
		return this.tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public String getEmpty() {
		return this.empty;
	}

	public void setEmpty(String empty) {
		this.empty = empty;
	}

	public String getFilled() {
		return this.filled;
	}

	public void setFilled(String filled) {
		this.filled = filled;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableNo() {
		return this.tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public String getTablecolumn() {
		return this.tablecolumn;
	}

	public void setTablecolumn(String tablecolumn) {
		this.tablecolumn = tablecolumn;
	}

	public String getTablerow() {
		return this.tablerow;
	}

	public void setTablerow(String tablerow) {
		this.tablerow = tablerow;
	}

	public List<TableDetail> getTableDetails() {
		return this.tableDetails;
	}

	public void setTableDetails(List<TableDetail> tableDetails) {
		this.tableDetails = tableDetails;
	}

	public TableDetail addTableDetail(TableDetail tableDetail) {
		getTableDetails().add(tableDetail);
		tableDetail.setNewtable(this);

		return tableDetail;
	}

	public TableDetail removeTableDetail(TableDetail tableDetail) {
		getTableDetails().remove(tableDetail);
		tableDetail.setNewtable(null);

		return tableDetail;
	}

}