package com.nrg.lemon.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplyDataBean {

public String serialNo;
public String division;
public String supplierName;
public String contactName;
public String hpNumber1;
public String hpNumber2;
public String officeNumber1;
public String officeNumber2;
public Date fabricPurchaseDate;
public String fabricInvoiceNumber;
public String fabricName;
public String fabricColour;
public String fabricPrice;
public String fabricWeight;
public String fabricQuantity;
public String fabricTotal;
public String weightTotal;
public String quantityTotal;
public String totalAmount;
public String paymentStatus;
public int fabricId;
public String fabricDay;
public String itemName;
public String itemBuyPrice;
public String itemSellPrice;
public String itemStatus;
public Date todayDate;
public String itemQuantity;
public String totalPrice;
public String vendorName;
public Date dueDate;
public String status2;
public String stockStatus;
public String itemStatus2;
public String itemStatus3;
List<SupplyDataBean> fabricList=new ArrayList<SupplyDataBean>();
public String invoiceStatus;
public String paidAmoount;
public String balanceAmoount;
public String remaining;
public Date deliveredDate;
public String quantity;
public boolean productFlag=false;
public boolean productFlag1=false;
public String itemQuantity1;
public Date fromDate;
public Date toDate;
public String category;

public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
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
public String getItemQuantity1() {
	return itemQuantity1;
}
public void setItemQuantity1(String itemQuantity1) {
	this.itemQuantity1 = itemQuantity1;
}
public boolean isProductFlag() {
	return productFlag;
}
public void setProductFlag(boolean productFlag) {
	this.productFlag = productFlag;
}
public boolean isProductFlag1() {
	return productFlag1;
}
public void setProductFlag1(boolean productFlag1) {
	this.productFlag1 = productFlag1;
}
public String getQuantity() {
	return quantity;
}
public void setQuantity(String quantity) {
	this.quantity = quantity;
}
public Date getDeliveredDate() {
	return deliveredDate;
}
public void setDeliveredDate(Date deliveredDate) {
	this.deliveredDate = deliveredDate;
}
public String getPaidAmoount() {
	return paidAmoount;
}
public void setPaidAmoount(String paidAmoount) {
	this.paidAmoount = paidAmoount;
}
public String getBalanceAmoount() {
	return balanceAmoount;
}
public void setBalanceAmoount(String balanceAmoount) {
	this.balanceAmoount = balanceAmoount;
}
public String getRemaining() {
	return remaining;
}
public void setRemaining(String remaining) {
	this.remaining = remaining;
}
public String getInvoiceStatus() {
	return invoiceStatus;
}
public void setInvoiceStatus(String invoiceStatus) {
	this.invoiceStatus = invoiceStatus;
}
public String getItemStatus3() {
	return itemStatus3;
}
public void setItemStatus3(String itemStatus3) {
	this.itemStatus3 = itemStatus3;
}
public String getItemStatus2() {
	return itemStatus2;
}
public void setItemStatus2(String itemStatus2) {
	this.itemStatus2 = itemStatus2;
}
public String getStatus2() {
	return status2;
}
public void setStatus2(String status2) {
	this.status2 = status2;
}
public String getStockStatus() {
	return stockStatus;
}
public void setStockStatus(String stockStatus) {
	this.stockStatus = stockStatus;
}
public Date getDueDate() {
	return dueDate;
}
public void setDueDate(Date dueDate) {
	this.dueDate = dueDate;
}
public String getVendorName() {
	return vendorName;
}
public void setVendorName(String vendorName) {
	this.vendorName = vendorName;
}
public String getTotalPrice() {
	return totalPrice;
}
public void setTotalPrice(String totalPrice) {
	this.totalPrice = totalPrice;
}
public String getItemQuantity() {
	return itemQuantity;
}
public void setItemQuantity(String itemQuantity) {
	this.itemQuantity = itemQuantity;
}
public Date getTodayDate() {
	return todayDate;
}
public void setTodayDate(Date todayDate) {
	this.todayDate = todayDate;
}
public String getItemStatus() {
	return itemStatus;
}
public void setItemStatus(String itemStatus) {
	this.itemStatus = itemStatus;
}
public String getItemName() {
	return itemName;
}
public void setItemName(String itemName) {
	this.itemName = itemName;
}
public String getItemBuyPrice() {
	return itemBuyPrice;
}
public void setItemBuyPrice(String itemBuyPrice) {
	this.itemBuyPrice = itemBuyPrice;
}
public String getItemSellPrice() {
	return itemSellPrice;
}
public void setItemSellPrice(String itemSellPrice) {
	this.itemSellPrice = itemSellPrice;
}
public String getFabricDay() {
	return fabricDay;
}
public void setFabricDay(String fabricDay) {
	this.fabricDay = fabricDay;
}
public int getFabricId() {
	return fabricId;
}
public void setFabricId(int fabricId) {
	this.fabricId = fabricId;
}
public String getPaymentStatus() {
	return paymentStatus;
}
public void setPaymentStatus(String paymentStatus) {
	this.paymentStatus = paymentStatus;
}
public String getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(String totalAmount) {
	this.totalAmount = totalAmount;
}
public List<SupplyDataBean> getFabricList() {
	return fabricList;
}
public void setFabricList(List<SupplyDataBean> fabricList) {
	this.fabricList = fabricList;
}
public String getWeightTotal() {
	return weightTotal;
}
public void setWeightTotal(String weightTotal) {
	this.weightTotal = weightTotal;
}
public String getQuantityTotal() {
	return quantityTotal;
}
public void setQuantityTotal(String quantityTotal) {
	this.quantityTotal = quantityTotal;
}
public String getSerialNo() {
	return serialNo;
}
public void setSerialNo(String serialNo) {
	this.serialNo = serialNo;
}
public Date getFabricPurchaseDate() {
	return fabricPurchaseDate;
}
public void setFabricPurchaseDate(Date fabricPurchaseDate) {
	this.fabricPurchaseDate = fabricPurchaseDate;
}
public String getFabricInvoiceNumber() {
	return fabricInvoiceNumber;
}
public void setFabricInvoiceNumber(String fabricInvoiceNumber) {
	this.fabricInvoiceNumber = fabricInvoiceNumber;
}
public String getFabricName() {
	return fabricName;
}
public void setFabricName(String fabricName) {
	this.fabricName = fabricName;
}
public String getFabricColour() {
	return fabricColour;
}
public void setFabricColour(String fabricColour) {
	this.fabricColour = fabricColour;
}
public String getFabricPrice() {
	return fabricPrice;
}
public void setFabricPrice(String fabricPrice) {
	this.fabricPrice = fabricPrice;
}
public String getFabricWeight() {
	return fabricWeight;
}
public void setFabricWeight(String fabricWeight) {
	this.fabricWeight = fabricWeight;
}
public String getFabricQuantity() {
	return fabricQuantity;
}
public void setFabricQuantity(String fabricQuantity) {
	this.fabricQuantity = fabricQuantity;
}
public String getFabricTotal() {
	return fabricTotal;
}
public void setFabricTotal(String fabricTotal) {
	this.fabricTotal = fabricTotal;
}
public String getDivision() {
	return division;
}
public void setDivision(String division) {
	this.division = division;
}
public String getSupplierName() {
	return supplierName;
}
public void setSupplierName(String supplierName) {
	this.supplierName = supplierName;
}
public String getContactName() {
	return contactName;
}
public void setContactName(String contactName) {
	this.contactName = contactName;
}
public String getHpNumber1() {
	return hpNumber1;
}
public void setHpNumber1(String hpNumber1) {
	this.hpNumber1 = hpNumber1;
}
public String getHpNumber2() {
	return hpNumber2;
}
public void setHpNumber2(String hpNumber2) {
	this.hpNumber2 = hpNumber2;
}
public String getOfficeNumber1() {
	return officeNumber1;
}
public void setOfficeNumber1(String officeNumber1) {
	this.officeNumber1 = officeNumber1;
}
public String getOfficeNumber2() {
	return officeNumber2;
}
public void setOfficeNumber2(String officeNumber2) {
	this.officeNumber2 = officeNumber2;
}
}
