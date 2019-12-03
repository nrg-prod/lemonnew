package com.nrg.lemon.domain;

import java.util.Date;
import java.util.List;

public class FinanceDataBean {

public int debtId;
public String debtCategory;
public String debtName;
public Date debtDate;
public String debtAmount;
public String debtReason;
public String debtBalance;
public String debtStatus;
public String debtPayStatus;
public String debtBalances;
private String type;
private boolean flag=false;
private List<FinanceDataBean> financeList=null;
private String serialNo;
private String invoiceNo;
private String paymentmode;
private Date date;
private String paymenttype;
private String name;
private String amount;
private String note;
private String bankname;
private String cardno;
private String chequeno;
private Date chequedate;
private String accountno;
private String transactionCategory;
private String transactionDescription;
private String wasteQuantity;
private String transactionNumber;
public String getTransactionNumber() {
	return transactionNumber;
}

public void setTransactionNumber(String transactionNumber) {
	this.transactionNumber = transactionNumber;
}

public String getPaymentmode() {
	return paymentmode;
}

public void setPaymentmode(String paymentmode) {
	this.paymentmode = paymentmode;
}

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public String getPaymenttype() {
	return paymenttype;
}

public void setPaymenttype(String paymenttype) {
	this.paymenttype = paymenttype;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getAmount() {
	return amount;
}

public void setAmount(String amount) {
	this.amount = amount;
}

public String getNote() {
	return note;
}

public void setNote(String note) {
	this.note = note;
}

public String getBankname() {
	return bankname;
}

public void setBankname(String bankname) {
	this.bankname = bankname;
}

public String getCardno() {
	return cardno;
}

public void setCardno(String cardno) {
	this.cardno = cardno;
}

public String getChequeno() {
	return chequeno;
}

public void setChequeno(String chequeno) {
	this.chequeno = chequeno;
}

public Date getChequedate() {
	return chequedate;
}

public void setChequedate(Date chequedate) {
	this.chequedate = chequedate;
}

public String getAccountno() {
	return accountno;
}

public void setAccountno(String accountno) {
	this.accountno = accountno;
}

public String getTransactionCategory() {
	return transactionCategory;
}

public void setTransactionCategory(String transactionCategory) {
	this.transactionCategory = transactionCategory;
}

public String getTransactionDescription() {
	return transactionDescription;
}

public void setTransactionDescription(String transactionDescription) {
	this.transactionDescription = transactionDescription;
}

public String getWasteQuantity() {
	return wasteQuantity;
}

public void setWasteQuantity(String wasteQuantity) {
	this.wasteQuantity = wasteQuantity;
}

public String getInvoiceNo() {
	return invoiceNo;
}

public void setInvoiceNo(String invoiceNo) {
	this.invoiceNo = invoiceNo;
}

public String getSerialNo() {
	return serialNo;
}

public void setSerialNo(String serialNo) {
	this.serialNo = serialNo;
}

public List<FinanceDataBean> getFinanceList() {
	return financeList;
}

public void setFinanceList(List<FinanceDataBean> financeList) {
	this.financeList = financeList;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public boolean isFlag() {
	return flag;
}

public void setFlag(boolean flag) {
	this.flag = flag;
}

public String getDebtBalances() {
	return debtBalances;
}

public void setDebtBalances(String debtBalances) {
	this.debtBalances = debtBalances;
}

public int getDebtId() {
	return debtId;
}

public void setDebtId(int debtId) {
	this.debtId = debtId;
}

public String getDebtPayStatus() {
	return debtPayStatus;
}

public void setDebtPayStatus(String debtPayStatus) {
	this.debtPayStatus = debtPayStatus;
}

public String getDebtStatus() {
	return debtStatus;
}

public void setDebtStatus(String debtStatus) {
	this.debtStatus = debtStatus;
}

public Date getDebtDate() {
	return debtDate;
}

public void setDebtDate(Date debtDate) {
	this.debtDate = debtDate;
}

public String getDebtAmount() {
	return debtAmount;
}

public void setDebtAmount(String debtAmount) {
	this.debtAmount = debtAmount;
}

public String getDebtReason() {
	return debtReason;
}

public void setDebtReason(String debtReason) {
	this.debtReason = debtReason;
}

public String getDebtBalance() {
	return debtBalance;
}

public void setDebtBalance(String debtBalance) {
	this.debtBalance = debtBalance;
}

public String getDebtName() {
	return debtName;
}

public void setDebtName(String debtName) {
	this.debtName = debtName;
}

public String getDebtCategory() {
	return debtCategory;
}

public void setDebtCategory(String debtCategory) {
	this.debtCategory = debtCategory;
}

}
