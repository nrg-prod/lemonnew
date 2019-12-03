package com.nrg.lemon.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReturnDataBean {	
	private Date date;
	private boolean flag=false;
	private boolean flag1=false;
	private List<ReturnDataBean> returnDataBeanList=new ArrayList<ReturnDataBean>();
    private String shoppname;
    private String serialno;
    private String itemname;
	private List<String> itemnamelist=null;
	private String stockin;
	private String quantity;
	private String price;
	private String netprice;
	private String netamount;
	private int supplyreturnid;
	private String quantity1;
	
	
	public String getQuantity1() {
		return quantity1;
	}
	public void setQuantity1(String quantity1) {
		this.quantity1 = quantity1;
	}
	public int getSupplyreturnid() {
		return supplyreturnid;
	}
	public void setSupplyreturnid(int supplyreturnid) {
		this.supplyreturnid = supplyreturnid;
	}
	public String getNetamount() {
		return netamount;
	}
	public void setNetamount(String netamount) {
		this.netamount = netamount;
	}
	public List<ReturnDataBean> getReturnDataBeanList() {
		return returnDataBeanList;
	}
	public String getShoppname() {
		return shoppname;
	}
	public void setShoppname(String shoppname) {
		this.shoppname = shoppname;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public void setReturnDataBeanList(List<ReturnDataBean> returnDataBeanList) {
		this.returnDataBeanList = returnDataBeanList;
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
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public List<String> getItemnamelist() {
		return itemnamelist;
	}
	public void setItemnamelist(List<String> itemnamelist) {
		this.itemnamelist = itemnamelist;
	}	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStockin() {
		return stockin;
	}
	public void setStockin(String stockin) {
		this.stockin = stockin;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
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

}
