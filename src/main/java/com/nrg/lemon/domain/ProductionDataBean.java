package com.nrg.lemon.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProductionDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8849402321207262540L;
	private String serialNo;
	public String divisionVendor;
	public String vendorName;
	public String vendorAddress;
	public String vendorHpnumber1;
	public String vendorHpnumber2;
	public String categoryName;
	public String categoryFabprice;
	public int categoryId;
	public String modelName;
	public String modelPole;
	public String modelCutter;
	public String modelPrinter;
	public String modelCmt;
	public String modelEtc;
	public String modelSellprice;
	public String modelCapital;
	private Date orderDate;
	private String invoiceNo;
	private String cutter;
	private String seri;
	private String quantity;
	private String totalseri;
	private String totalQuantity;
	private String totalWeight;
	private String remainWeight;
	private String fabricPrice;
	private String pole;
	private String result;
	private String saving;
	private String value;
	private String modelSeri;
	private List<ProductionDataBean> orderQuantitylist=null;
	private String cmt;
	private String cmtvalue;
	private String totalValue;
	private String motive;
	private String printer;
	private String printerr;
	private String printerQuantity;
	private String invoiceStatus;
	private String totalAmount;
	private String balanceAmount;
	private String paidAmount;
	private String payingAmount;
	private String sellingPrice;
	private String cutterInvoiceno;
	private String stockinQuantity;
	private String addedQuantity;
	private boolean checkBox;
	private List<ProductionDataBean> printerlist=null;
	private List<ProductionDataBean> cutterList=null;
	private List<ProductionDataBean> cmtList=null;
	private List<ProductionDataBean> cutterconfirmationList=null;
	private boolean selectCheckbox=false;
	private String weightPercentage;
	private String tableName;
	private String tableNo;
	private String tableRow;
	private String tableColumn;
	private Date tanggal;
	private String rowcolumn;
	private String filled;
	private String emptycell;
	private Date fromDate;
	private Date toDate;
	private String profit;
	private String profit_percentage;
	private boolean cutterflag=false;
	private boolean printerflag=false;
	private boolean cmtflag=false;
	private List<ProductionDataBean> tablevaluelist=null;
	private List<ProductionDataBean> tableviewlist=null;
	private List<ProductionDataBean> tablehistorylist=null;
	private String tableid;
	
	 
	

	public List<ProductionDataBean> getTablehistorylist() {
		return tablehistorylist;
	}

	public void setTablehistorylist(List<ProductionDataBean> tablehistorylist) {
		this.tablehistorylist = tablehistorylist;
	}

	public List<ProductionDataBean> getTableviewlist() {
		return tableviewlist;
	}

	public void setTableviewlist(List<ProductionDataBean> tableviewlist) {
		this.tableviewlist = tableviewlist;
	}

	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getFilled() {
		return filled;
	}

	public void setFilled(String filled) {
		this.filled = filled;
	}
	public String getEmptycell() {
		return emptycell;
	}

	public void setEmptycell(String emptycell) {
		this.emptycell = emptycell;
	}

	public String getRowcolumn() {
		return rowcolumn;
	}

	public void setRowcolumn(String rowcolumn) {
		this.rowcolumn = rowcolumn;
	}

	public boolean isCutterflag() {
		return cutterflag;
	}

	public void setCutterflag(boolean cutterflag) {
		this.cutterflag = cutterflag;
	}

	public boolean isPrinterflag() {
		return printerflag;
	}

	public void setPrinterflag(boolean printerflag) {
		this.printerflag = printerflag;
	}

	public boolean isCmtflag() {
		return cmtflag;
	}

	public void setCmtflag(boolean cmtflag) {
		this.cmtflag = cmtflag;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getProfit_percentage() {
		return profit_percentage;
	}

	public void setProfit_percentage(String profit_percentage) {
		this.profit_percentage = profit_percentage;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getCmtvalue() {
		return cmtvalue;
	}

	public void setCmtvalue(String cmtvalue) {
		this.cmtvalue = cmtvalue;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public String getTableRow() {
		return tableRow;
	}

	public void setTableRow(String tableRow) {
		this.tableRow = tableRow;
	}

	public String getTableColumn() {
		return tableColumn;
	}

	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}

	public String getWeightPercentage() {
		return weightPercentage;
	}

	public void setWeightPercentage(String weightPercentage) {
		this.weightPercentage = weightPercentage;
	}

	public boolean isSelectCheckbox() {
		return selectCheckbox;
	}

	public void setSelectCheckbox(boolean selectCheckbox) {
		this.selectCheckbox = selectCheckbox;
	}

	public List<ProductionDataBean> getCutterconfirmationList() {
		return cutterconfirmationList;
	}

	public void setCutterconfirmationList(
			List<ProductionDataBean> cutterconfirmationList) {
		this.cutterconfirmationList = cutterconfirmationList;
	}

	public List<ProductionDataBean> getPrinterlist() {
		return printerlist;
	}

	public void setPrinterlist(List<ProductionDataBean> printerlist) {
		this.printerlist = printerlist;
	}

	public List<ProductionDataBean> getCutterList() {
		return cutterList;
	}

	public void setCutterList(List<ProductionDataBean> cutterList) {
		this.cutterList = cutterList;
	}

	public List<ProductionDataBean> getCmtList() {
		return cmtList;
	}

	public void setCmtList(List<ProductionDataBean> cmtList) {
		this.cmtList = cmtList;
	}

	public boolean isCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

	public String getStockinQuantity() {
		return stockinQuantity;
	}

	public void setStockinQuantity(String stockinQuantity) {
		this.stockinQuantity = stockinQuantity;
	}

	public String getAddedQuantity() {
		return addedQuantity;
	}

	public void setAddedQuantity(String addedQuantity) {
		this.addedQuantity = addedQuantity;
	}

	public String getCutterInvoiceno() {
		return cutterInvoiceno;
	}

	public void setCutterInvoiceno(String cutterInvoiceno) {
		this.cutterInvoiceno = cutterInvoiceno;
	}

	public String getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getPayingAmount() {
		return payingAmount;
	}

	public void setPayingAmount(String payingAmount) {
		this.payingAmount = payingAmount;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getPrinterQuantity() {
		return printerQuantity;
	}

	public void setPrinterQuantity(String printerQuantity) {
		this.printerQuantity = printerQuantity;
	}

	public String getPrinterr() {
		return printerr;
	}

	public void setPrinterr(String printerr) {
		this.printerr = printerr;
	}

	public String getPrinter() {
		return printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	private Date receivedate;
	public Date getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(Date receivedate) {
		this.receivedate = receivedate;
	}

	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
	
	public String getCmt() {
		return cmt;
	}
	
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public List<ProductionDataBean> getOrderQuantitylist() {
		return orderQuantitylist;
	}

	public void setOrderQuantitylist(List<ProductionDataBean> orderQuantitylist) {
		this.orderQuantitylist = orderQuantitylist;
	}

	public String getModelSeri() {
		return modelSeri;
	}

	public void setModelSeri(String modelSeri) {
		this.modelSeri = modelSeri;
	}

	public String getCutter() {
		return cutter;
	}

	public void setCutter(String cutter) {
		this.cutter = cutter;
	}

	public String getSeri() {
		return seri;
	}

	public void setSeri(String seri) {
		this.seri = seri;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTotalseri() {
		return totalseri;
	}

	public void setTotalseri(String totalseri) {
		this.totalseri = totalseri;
	}

	public String getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(String totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getRemainWeight() {
		return remainWeight;
	}

	public void setRemainWeight(String remainWeight) {
		this.remainWeight = remainWeight;
	}

	public String getFabricPrice() {
		return fabricPrice;
	}

	public void setFabricPrice(String fabricPrice) {
		this.fabricPrice = fabricPrice;
	}

	public String getPole() {
		return pole;
	}

	public void setPole(String pole) {
		this.pole = pole;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSaving() {
		return saving;
	}

	public void setSaving(String saving) {
		this.saving = saving;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDivisionVendor() {
		return divisionVendor;
	}

	public void setDivisionVendor(String divisionVendor) {
		this.divisionVendor = divisionVendor;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public String getVendorHpnumber1() {
		return vendorHpnumber1;
	}

	public void setVendorHpnumber1(String vendorHpnumber1) {
		this.vendorHpnumber1 = vendorHpnumber1;
	}

	public String getVendorHpnumber2() {
		return vendorHpnumber2;
	}

	public void setVendorHpnumber2(String vendorHpnumber2) {
		this.vendorHpnumber2 = vendorHpnumber2;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryFabprice() {
		return categoryFabprice;
	}

	public void setCategoryFabprice(String categoryFabprice) {
		this.categoryFabprice = categoryFabprice;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelPole() {
		return modelPole;
	}

	public void setModelPole(String modelPole) {
		this.modelPole = modelPole;
	}

	public String getModelCutter() {
		return modelCutter;
	}

	public void setModelCutter(String modelCutter) {
		this.modelCutter = modelCutter;
	}

	public String getModelPrinter() {
		return modelPrinter;
	}

	public void setModelPrinter(String modelPrinter) {
		this.modelPrinter = modelPrinter;
	}

	public String getModelCmt() {
		return modelCmt;
	}

	public void setModelCmt(String modelCmt) {
		this.modelCmt = modelCmt;
	}

	public String getModelEtc() {
		return modelEtc;
	}

	public void setModelEtc(String modelEtc) {
		this.modelEtc = modelEtc;
	}

	public String getModelSellprice() {
		return modelSellprice;
	}

	public void setModelSellprice(String modelSellprice) {
		this.modelSellprice = modelSellprice;
	}

	public String getModelCapital() {
		return modelCapital;
	}

	public void setModelCapital(String modelCapital) {
		this.modelCapital = modelCapital;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	private String amodelName;
	private String aseri;
	private String acutter;
	private Date atanggal;
	private String bmodelName;
	private String bseri;
	private String bcutter;
	private Date btanggal;
	private String cmodelName;
	private String cseri;
	private String ccutter;
	private Date ctanggal;
	private String dmodelName;
	private String dseri;
	private String dcutter;
	private Date dtanggal;
	private String emodelName;
	private String eseri;
	private String ecutter;
	private Date etanggal;
	private String fmodelName;
	private String fseri;
	private String fcutter;
	private Date ftanggal;
	private String gmodelName;
	private String gseri;
	private String gcutter;
	private Date gtanggal;
	private String hmodelName;
	private String hseri;
	private String hcutter;
	private Date htanggal;
	private String imodelName;
	private String iseri;
	private String icutter;
	private Date itanggal;
	private String jmodelName;
	private String jseri;
	private String jcutter;
	private Date jtanggal;

	public List<ProductionDataBean> getTablevaluelist() {
		return tablevaluelist;
	}

	public void setTablevaluelist(List<ProductionDataBean> tablevaluelist) {
		this.tablevaluelist = tablevaluelist;
	}

	public String getAmodelName() {
		return amodelName;
	}

	public void setAmodelName(String amodelName) {
		this.amodelName = amodelName;
	}

	public String getAseri() {
		return aseri;
	}

	public void setAseri(String aseri) {
		this.aseri = aseri;
	}

	public String getAcutter() {
		return acutter;
	}

	public void setAcutter(String acutter) {
		this.acutter = acutter;
	}

	public Date getAtanggal() {
		return atanggal;
	}

	public void setAtanggal(Date atanggal) {
		this.atanggal = atanggal;
	}

	public String getBmodelName() {
		return bmodelName;
	}

	public void setBmodelName(String bmodelName) {
		this.bmodelName = bmodelName;
	}

	public String getBseri() {
		return bseri;
	}

	public void setBseri(String bseri) {
		this.bseri = bseri;
	}

	public String getBcutter() {
		return bcutter;
	}

	public void setBcutter(String bcutter) {
		this.bcutter = bcutter;
	}

	public Date getBtanggal() {
		return btanggal;
	}

	public void setBtanggal(Date btanggal) {
		this.btanggal = btanggal;
	}

	public String getCmodelName() {
		return cmodelName;
	}

	public void setCmodelName(String cmodelName) {
		this.cmodelName = cmodelName;
	}

	public String getCseri() {
		return cseri;
	}

	public void setCseri(String cseri) {
		this.cseri = cseri;
	}

	public String getCcutter() {
		return ccutter;
	}

	public void setCcutter(String ccutter) {
		this.ccutter = ccutter;
	}

	public Date getCtanggal() {
		return ctanggal;
	}

	public void setCtanggal(Date ctanggal) {
		this.ctanggal = ctanggal;
	}

	public String getDmodelName() {
		return dmodelName;
	}

	public void setDmodelName(String dmodelName) {
		this.dmodelName = dmodelName;
	}

	public String getDseri() {
		return dseri;
	}

	public void setDseri(String dseri) {
		this.dseri = dseri;
	}

	public String getDcutter() {
		return dcutter;
	}

	public void setDcutter(String dcutter) {
		this.dcutter = dcutter;
	}

	public Date getDtanggal() {
		return dtanggal;
	}

	public void setDtanggal(Date dtanggal) {
		this.dtanggal = dtanggal;
	}

	public String getEmodelName() {
		return emodelName;
	}

	public void setEmodelName(String emodelName) {
		this.emodelName = emodelName;
	}

	public String getEseri() {
		return eseri;
	}

	public void setEseri(String eseri) {
		this.eseri = eseri;
	}

	public String getEcutter() {
		return ecutter;
	}

	public void setEcutter(String ecutter) {
		this.ecutter = ecutter;
	}

	public Date getEtanggal() {
		return etanggal;
	}

	public void setEtanggal(Date etanggal) {
		this.etanggal = etanggal;
	}

	public String getFmodelName() {
		return fmodelName;
	}

	public void setFmodelName(String fmodelName) {
		this.fmodelName = fmodelName;
	}

	public String getFseri() {
		return fseri;
	}

	public void setFseri(String fseri) {
		this.fseri = fseri;
	}

	public String getFcutter() {
		return fcutter;
	}

	public void setFcutter(String fcutter) {
		this.fcutter = fcutter;
	}

	public Date getFtanggal() {
		return ftanggal;
	}

	public void setFtanggal(Date ftanggal) {
		this.ftanggal = ftanggal;
	}

	public String getGmodelName() {
		return gmodelName;
	}

	public void setGmodelName(String gmodelName) {
		this.gmodelName = gmodelName;
	}

	public String getGseri() {
		return gseri;
	}

	public void setGseri(String gseri) {
		this.gseri = gseri;
	}

	public String getGcutter() {
		return gcutter;
	}

	public void setGcutter(String gcutter) {
		this.gcutter = gcutter;
	}

	public Date getGtanggal() {
		return gtanggal;
	}

	public void setGtanggal(Date gtanggal) {
		this.gtanggal = gtanggal;
	}

	public String getHmodelName() {
		return hmodelName;
	}

	public void setHmodelName(String hmodelName) {
		this.hmodelName = hmodelName;
	}

	public String getHseri() {
		return hseri;
	}

	public void setHseri(String hseri) {
		this.hseri = hseri;
	}

	public String getHcutter() {
		return hcutter;
	}

	public void setHcutter(String hcutter) {
		this.hcutter = hcutter;
	}

	public Date getHtanggal() {
		return htanggal;
	}

	public void setHtanggal(Date htanggal) {
		this.htanggal = htanggal;
	}

	public String getImodelName() {
		return imodelName;
	}

	public void setImodelName(String imodelName) {
		this.imodelName = imodelName;
	}

	public String getIseri() {
		return iseri;
	}

	public void setIseri(String iseri) {
		this.iseri = iseri;
	}

	public String getIcutter() {
		return icutter;
	}

	public void setIcutter(String icutter) {
		this.icutter = icutter;
	}

	public Date getItanggal() {
		return itanggal;
	}

	public void setItanggal(Date itanggal) {
		this.itanggal = itanggal;
	}

	public String getJmodelName() {
		return jmodelName;
	}

	public void setJmodelName(String jmodelName) {
		this.jmodelName = jmodelName;
	}

	public String getJseri() {
		return jseri;
	}

	public void setJseri(String jseri) {
		this.jseri = jseri;
	}

	public String getJcutter() {
		return jcutter;
	}

	public void setJcutter(String jcutter) {
		this.jcutter = jcutter;
	}

	public Date getJtanggal() {
		return jtanggal;
	}

	public void setJtanggal(Date jtanggal) {
		this.jtanggal = jtanggal;
	}
}
