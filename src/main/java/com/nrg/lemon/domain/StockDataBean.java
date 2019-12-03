package com.nrg.lemon.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class StockDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8849402321207262540L;
	private String warehouseName;
	private String location;
	private String personIncharge;
	private String serialNo;
	private int warehouse_id;
	private String status;
	private List<String> warehouseList=null;
	private List<StockDataBean> warelists=null;
	private Date date;
	private boolean flag=false;
	private boolean flag1=false;
	private List<String> cmtProducts=null;
	private String modelNo;
	private String quantity;
	private String sellingPrice;
	private String totalPrice;
	private String seri;
	private String serin;
	private List<String> seris=null;
	private boolean sflag=false;
	private boolean sflag1=false;
	private int mnaualStockID;
	private List<StockDataBean> stklists=null;
	private String quantity1;
	private String chkQuantity;
	private Date fromDate;
	private Date toDate;
	private String type;
	private String itemName;
	private String stockIn;
	private String stockOut;
	private String itemname;
	private String seriNolist;
	private boolean cmtflag=false;
	private boolean printerflag=false;
	private List<StockDataBean> Cmttableview=null;
	private List<StockDataBean> printertableview=null;
	private String shop;
	private String model;
	private String seriDetails;
	private String stock;
	private String qty;
	private String price;
	private String netprice;
	private String returnshop;
	private String returnmodel;
	private String returnseri;
	private String returnshop1;
	private String returnmodel1;
	private String returnseri1;
	private String returnstatus1;
	private String returnquantity1;
	private String returnquantity2;
	private String returnprice1;
	private String returnnetprice1;
	private int returnID;
	private String newWarehouse;
	private String category;
	private String motive;
	BigDecimal quant=BigDecimal.valueOf(0);
	private String avail_quant;
	
	
	public String getAvail_quant() {
		return avail_quant;
	}
	public void setAvail_quant(String avail_quant) {
		this.avail_quant = avail_quant;
	}
	public BigDecimal getQuant() {
		return quant;
	}
	public void setQuant(BigDecimal quant) {
		this.quant = quant;
	}
	public String getReturnquantity2() {
		return returnquantity2;
	}
	public void setReturnquantity2(String returnquantity2) {
		this.returnquantity2 = returnquantity2;
	}
	public List<StockDataBean> getPrintertableview() {
		return printertableview;
	}
	public void setPrintertableview(List<StockDataBean> printertableview) {
		this.printertableview = printertableview;
	}
	public boolean isPrinterflag() {
		return printerflag;
	}
	public void setPrinterflag(boolean printerflag) {
		this.printerflag = printerflag;
	}
	public String getMotive() {
		return motive;
	}
	public void setMotive(String motive) {
		this.motive = motive;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getNewWarehouse() {
		return newWarehouse;
	}
	public void setNewWarehouse(String newWarehouse) {
		this.newWarehouse = newWarehouse;
	}
	public int getReturnID() {
		return returnID;
	}
	public void setReturnID(int returnID) {
		this.returnID = returnID;
	}
	public String getReturnshop1() {
		return returnshop1;
	}
	public void setReturnshop1(String returnshop1) {
		this.returnshop1 = returnshop1;
	}
	public String getReturnmodel1() {
		return returnmodel1;
	}
	public void setReturnmodel1(String returnmodel1) {
		this.returnmodel1 = returnmodel1;
	}
	public String getReturnseri1() {
		return returnseri1;
	}
	public void setReturnseri1(String returnseri1) {
		this.returnseri1 = returnseri1;
	}
	public String getReturnstatus1() {
		return returnstatus1;
	}
	public void setReturnstatus1(String returnstatus1) {
		this.returnstatus1 = returnstatus1;
	}
	public String getReturnquantity1() {
		return returnquantity1;
	}
	public void setReturnquantity1(String returnquantity1) {
		this.returnquantity1 = returnquantity1;
	}
	public String getReturnprice1() {
		return returnprice1;
	}
	public void setReturnprice1(String returnprice1) {
		this.returnprice1 = returnprice1;
	}
	public String getReturnnetprice1() {
		return returnnetprice1;
	}
	public void setReturnnetprice1(String returnnetprice1) {
		this.returnnetprice1 = returnnetprice1;
	}
	public String getReturnshop() {
		return returnshop;
	}
	public void setReturnshop(String returnshop) {
		this.returnshop = returnshop;
	}
	public String getReturnmodel() {
		return returnmodel;
	}
	public void setReturnmodel(String returnmodel) {
		this.returnmodel = returnmodel;
	}
	public String getReturnseri() {
		return returnseri;
	}
	public void setReturnseri(String returnseri) {
		this.returnseri = returnseri;
	}
	public String getReturnstatus() {
		return returnstatus;
	}
	public void setReturnstatus(String returnstatus) {
		this.returnstatus = returnstatus;
	}
	public String getReturnquantity() {
		return returnquantity;
	}
	public void setReturnquantity(String returnquantity) {
		this.returnquantity = returnquantity;
	}
	public String getReturnprice() {
		return returnprice;
	}
	public void setReturnprice(String returnprice) {
		this.returnprice = returnprice;
	}
	public String getReturnnetprice() {
		return returnnetprice;
	}
	public void setReturnnetprice(String returnnetprice) {
		this.returnnetprice = returnnetprice;
	}
	private String returnstatus;
	private String returnquantity;
	private String returnprice;
	private String returnnetprice;	
	private int stockID;
	public int getStockID() {
		return stockID;
	}
	public void setStockID(int stockID) {
		this.stockID = stockID;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getSeriNolist() {
		return seriNolist;
	}
	public void setSeriNolist(String seriNolist) {
		this.seriNolist = seriNolist;
	}
	public boolean isCmtflag() {
		return cmtflag;
	}
	public void setCmtflag(boolean cmtflag) {
		this.cmtflag = cmtflag;
	}
	public List<StockDataBean> getCmttableview() {
		return Cmttableview;
	}
	public void setCmttableview(List<StockDataBean> cmttableview) {
		Cmttableview = cmttableview;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSeriDetails() {
		return seriDetails;
	}
	public void setSeriDetails(String seriDetails) {
		this.seriDetails = seriDetails;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNetprice() {
		return netprice;
	}
	public void setNetprice(String netprice) {
		this.netprice = netprice;
	}
	public String getStockOut() {
		return stockOut;
	}
	public void setStockOut(String stockOut) {
		this.stockOut = stockOut;
	}
	public String getStockIn() {
		return stockIn;
	}
	public void setStockIn(String stockIn) {
		this.stockIn = stockIn;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getChkQuantity() {
		return chkQuantity;
	}
	public void setChkQuantity(String chkQuantity) {
		this.chkQuantity = chkQuantity;
	}
	public String getQuantity1() {
		return quantity1;
	}
	public void setQuantity1(String quantity1) {
		this.quantity1 = quantity1;
	}
	public List<StockDataBean> getStklists() {
		return stklists;
	}
	public void setStklists(List<StockDataBean> stklists) {
		this.stklists = stklists;
	}
	public int getMnaualStockID() {
		return mnaualStockID;
	}
	public void setMnaualStockID(int mnaualStockID) {
		this.mnaualStockID = mnaualStockID;
	}
	public boolean isSflag() {
		return sflag;
	}
	public void setSflag(boolean sflag) {
		this.sflag = sflag;
	}
	public boolean isSflag1() {
		return sflag1;
	}
	public void setSflag1(boolean sflag1) {
		this.sflag1 = sflag1;
	}
	public String getSerin() {
		return serin;
	}
	public void setSerin(String serin) {
		this.serin = serin;
	}
	public List<String> getSeris() {
		return seris;
	}
	public void setSeris(List<String> seris) {
		this.seris = seris;
	}
	public String getSeri() {
		return seri;
	}
	public void setSeri(String seri) {
		this.seri = seri;
	}
	public String getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public List<String> getCmtProducts() {
		return cmtProducts;
	}
	public void setCmtProducts(List<String> cmtProducts) {
		this.cmtProducts = cmtProducts;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public boolean isFlag1() {
		return flag1;
	}
	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<String> getWarehouseList() {
		return warehouseList;
	}
	public void setWarehouseList(List<String> warehouseList) {
		this.warehouseList = warehouseList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(int warehouse_id) {
		this.warehouse_id = warehouse_id;
	}	
	public List<StockDataBean> getWarelists() {
		return warelists;
	}
	public void setWarelists(List<StockDataBean> warelists) {
		this.warelists = warelists;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPersonIncharge() {
		return personIncharge;
	}
	public void setPersonIncharge(String personIncharge) {
		this.personIncharge = personIncharge;
	}
		
}
