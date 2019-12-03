package com.nrg.lemon.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the table_detail database table.
 * 
 */
@Entity
@Table(name="table_detail")
@NamedQuery(name="TableDetail.findAll", query="SELECT t FROM TableDetail t")
public class TableDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tabledetail_id")
	private int tabledetailId;

	@Column(name="a_cutter")
	private String aCutter;

	@Column(name="a_model")
	private String aModel;

	@Column(name="a_seri")
	private String aSeri;

	@Temporal(TemporalType.DATE)
	@Column(name="a_tanggal")
	private Date aTanggal;

	@Column(name="b_cutter")
	private String bCutter;

	@Column(name="b_model")
	private String bModel;

	@Column(name="b_seri")
	private String bSeri;

	@Temporal(TemporalType.DATE)
	@Column(name="b_tanggal")
	private Date bTanggal;

	@Column(name="c_cutter")
	private String cCutter;

	@Column(name="c_model")
	private String cModel;

	@Column(name="c_seri")
	private String cSeri;

	@Temporal(TemporalType.DATE)
	@Column(name="c_tanggal")
	private Date cTanggal;

	@Column(name="d_cutter")
	private String dCutter;

	@Column(name="d_model")
	private String dModel;

	@Column(name="d_seri")
	private String dSeri;

	@Temporal(TemporalType.DATE)
	@Column(name="d_tanggal")
	private Date dTanggal;

	@Column(name="e_cutter")
	private String eCutter;

	@Column(name="e_model")
	private String eModel;

	@Column(name="e_seri")
	private String eSeri;

	@Temporal(TemporalType.DATE)
	@Column(name="e_tanggal")
	private Date eTanggal;

	@Column(name="f_cutter")
	private String fCutter;

	@Column(name="f_model")
	private String fModel;

	@Column(name="f_seri")
	private String fSeri;

	@Temporal(TemporalType.DATE)
	@Column(name="f_tanggal")
	private Date fTanggal;

	@Column(name="g_cutter")
	private String gCutter;

	@Column(name="g_model")
	private String gModel;

	@Column(name="g_seri")
	private String gSeri;

	@Temporal(TemporalType.DATE)
	@Column(name="g_tanggal")
	private Date gTanggal;

	@Column(name="h_cutter")
	private String hCutter;

	@Column(name="h_model")
	private String hModel;

	@Column(name="h_seri")
	private String hSeri;

	@Temporal(TemporalType.DATE)
	@Column(name="h_tanggal")
	private Date hTanggal;

	@Column(name="i_cutter")
	private String iCutter;

	@Column(name="i_model")
	private String iModel;

	@Column(name="i_seri")
	private String iSeri;

	@Temporal(TemporalType.DATE)
	@Column(name="i_tanggal")
	private Date iTanggal;

	@Column(name="j_cutter")
	private String jCutter;

	@Column(name="j_model")
	private String jModel;

	@Column(name="j_seri")
	private String jSeri;

	@Temporal(TemporalType.DATE)
	@Column(name="j_tanggal")
	private Date jTanggal;

	private String status;

	//bi-directional many-to-one association to Newtable
	@ManyToOne
	@JoinColumn(name="table_id")
	private Newtable newtable;

	public TableDetail() {
	}

	public int getTabledetailId() {
		return this.tabledetailId;
	}

	public void setTabledetailId(int tabledetailId) {
		this.tabledetailId = tabledetailId;
	}

	public String getACutter() {
		return this.aCutter;
	}

	public void setACutter(String aCutter) {
		this.aCutter = aCutter;
	}

	public String getAModel() {
		return this.aModel;
	}

	public void setAModel(String aModel) {
		this.aModel = aModel;
	}

	public String getASeri() {
		return this.aSeri;
	}

	public void setASeri(String aSeri) {
		this.aSeri = aSeri;
	}

	public Date getATanggal() {
		return this.aTanggal;
	}

	public void setATanggal(Date aTanggal) {
		this.aTanggal = aTanggal;
	}

	public String getBCutter() {
		return this.bCutter;
	}

	public void setBCutter(String bCutter) {
		this.bCutter = bCutter;
	}

	public String getBModel() {
		return this.bModel;
	}

	public void setBModel(String bModel) {
		this.bModel = bModel;
	}

	public String getBSeri() {
		return this.bSeri;
	}

	public void setBSeri(String bSeri) {
		this.bSeri = bSeri;
	}

	public Date getBTanggal() {
		return this.bTanggal;
	}

	public void setBTanggal(Date bTanggal) {
		this.bTanggal = bTanggal;
	}

	public String getCCutter() {
		return this.cCutter;
	}

	public void setCCutter(String cCutter) {
		this.cCutter = cCutter;
	}

	public String getCModel() {
		return this.cModel;
	}

	public void setCModel(String cModel) {
		this.cModel = cModel;
	}

	public String getCSeri() {
		return this.cSeri;
	}

	public void setCSeri(String cSeri) {
		this.cSeri = cSeri;
	}

	public Date getCTanggal() {
		return this.cTanggal;
	}

	public void setCTanggal(Date cTanggal) {
		this.cTanggal = cTanggal;
	}

	public String getDCutter() {
		return this.dCutter;
	}

	public void setDCutter(String dCutter) {
		this.dCutter = dCutter;
	}

	public String getDModel() {
		return this.dModel;
	}

	public void setDModel(String dModel) {
		this.dModel = dModel;
	}

	public String getDSeri() {
		return this.dSeri;
	}

	public void setDSeri(String dSeri) {
		this.dSeri = dSeri;
	}

	public Date getDTanggal() {
		return this.dTanggal;
	}

	public void setDTanggal(Date dTanggal) {
		this.dTanggal = dTanggal;
	}

	public String getECutter() {
		return this.eCutter;
	}

	public void setECutter(String eCutter) {
		this.eCutter = eCutter;
	}

	public String getEModel() {
		return this.eModel;
	}

	public void setEModel(String eModel) {
		this.eModel = eModel;
	}

	public String getESeri() {
		return this.eSeri;
	}

	public void setESeri(String eSeri) {
		this.eSeri = eSeri;
	}

	public Date getETanggal() {
		return this.eTanggal;
	}

	public void setETanggal(Date eTanggal) {
		this.eTanggal = eTanggal;
	}

	public String getFCutter() {
		return this.fCutter;
	}

	public void setFCutter(String fCutter) {
		this.fCutter = fCutter;
	}

	public String getFModel() {
		return this.fModel;
	}

	public void setFModel(String fModel) {
		this.fModel = fModel;
	}

	public String getFSeri() {
		return this.fSeri;
	}

	public void setFSeri(String fSeri) {
		this.fSeri = fSeri;
	}

	public Date getFTanggal() {
		return this.fTanggal;
	}

	public void setFTanggal(Date fTanggal) {
		this.fTanggal = fTanggal;
	}

	public String getGCutter() {
		return this.gCutter;
	}

	public void setGCutter(String gCutter) {
		this.gCutter = gCutter;
	}

	public String getGModel() {
		return this.gModel;
	}

	public void setGModel(String gModel) {
		this.gModel = gModel;
	}

	public String getGSeri() {
		return this.gSeri;
	}

	public void setGSeri(String gSeri) {
		this.gSeri = gSeri;
	}

	public Date getGTanggal() {
		return this.gTanggal;
	}

	public void setGTanggal(Date gTanggal) {
		this.gTanggal = gTanggal;
	}

	public String getHCutter() {
		return this.hCutter;
	}

	public void setHCutter(String hCutter) {
		this.hCutter = hCutter;
	}

	public String getHModel() {
		return this.hModel;
	}

	public void setHModel(String hModel) {
		this.hModel = hModel;
	}

	public String getHSeri() {
		return this.hSeri;
	}

	public void setHSeri(String hSeri) {
		this.hSeri = hSeri;
	}

	public Date getHTanggal() {
		return this.hTanggal;
	}

	public void setHTanggal(Date hTanggal) {
		this.hTanggal = hTanggal;
	}

	public String getICutter() {
		return this.iCutter;
	}

	public void setICutter(String iCutter) {
		this.iCutter = iCutter;
	}

	public String getIModel() {
		return this.iModel;
	}

	public void setIModel(String iModel) {
		this.iModel = iModel;
	}

	public String getISeri() {
		return this.iSeri;
	}

	public void setISeri(String iSeri) {
		this.iSeri = iSeri;
	}

	public Date getITanggal() {
		return this.iTanggal;
	}

	public void setITanggal(Date iTanggal) {
		this.iTanggal = iTanggal;
	}

	public String getJCutter() {
		return this.jCutter;
	}

	public void setJCutter(String jCutter) {
		this.jCutter = jCutter;
	}

	public String getJModel() {
		return this.jModel;
	}

	public void setJModel(String jModel) {
		this.jModel = jModel;
	}

	public String getJSeri() {
		return this.jSeri;
	}

	public void setJSeri(String jSeri) {
		this.jSeri = jSeri;
	}

	public Date getJTanggal() {
		return this.jTanggal;
	}

	public void setJTanggal(Date jTanggal) {
		this.jTanggal = jTanggal;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Newtable getNewtable() {
		return this.newtable;
	}

	public void setNewtable(Newtable newtable) {
		this.newtable = newtable;
	}

}