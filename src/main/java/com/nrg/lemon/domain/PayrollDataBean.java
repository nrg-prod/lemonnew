package com.nrg.lemon.domain;

import java.util.Date;
import java.util.List;

public class PayrollDataBean {
	
	private Date date;
	private String reason;
	private String amount;
	private String type;
	private String staffName;
	private List<PayrollDataBean> payrollList=null;
	private boolean flag=false;
	private List<String> staffNames=null;
	private String serialNo;
	private int penaltID;
	private boolean aflag=false;
	private boolean aflag1=false;
	private boolean rflag=false;
	private boolean rflag1=false;
	private String status;
	private String invoiceNo;
	private String name;
	private String balAmount;
	private String month;
	private String year;
	private List<String> months;
	private List<String> years;
	private List<String> employees;
	private String employee;
	private String branch;
	private String salary;
	private String overTime;
	private String loanAmount;
	private String monthSalary;
	private String attendanceBonus;
	private boolean bflag=false;
	private int purchaseId;
	private String supplier;
	private Date fromdate;
	private Date todate;
	private String cmt;
	private List<String> supplierList=null;
	private boolean selectCheckbox=false;
	
	public boolean isSelectCheckbox() {
		return selectCheckbox;
	}

	public void setSelectCheckbox(boolean selectCheckbox) {
		this.selectCheckbox = selectCheckbox;
	}

	
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public List<String> getSupplierList() {
		return supplierList;
	}
	public void setSupplierList(List<String> supplierList) {
		this.supplierList = supplierList;
	}
	public Date getFromdate() {
		return fromdate;
	}
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	public Date getTodate() {
		return todate;
	}
	public void setTodate(Date todate) {
		this.todate = todate;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
	public boolean isBflag() {
		return bflag;
	}
	public void setBflag(boolean bflag) {
		this.bflag = bflag;
	}
	public String getAttendanceBonus() {
		return attendanceBonus;
	}
	public void setAttendanceBonus(String attendanceBonus) {
		this.attendanceBonus = attendanceBonus;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getMonthSalary() {
		return monthSalary;
	}
	public void setMonthSalary(String monthSalary) {
		this.monthSalary = monthSalary;
	}
	public List<String> getEmployees() {
		return employees;
	}
	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public List<String> getMonths() {
		return months;
	}
	public void setMonths(List<String> months) {
		this.months = months;
	}
	public List<String> getYears() {
		return years;
	}
	public void setYears(List<String> years) {
		this.years = years;
	}
	public String getBalAmount() {
		return balAmount;
	}
	public void setBalAmount(String balAmount) {
		this.balAmount = balAmount;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isAflag() {
		return aflag;
	}
	public void setAflag(boolean aflag) {
		this.aflag = aflag;
	}
	public boolean isAflag1() {
		return aflag1;
	}
	public void setAflag1(boolean aflag1) {
		this.aflag1 = aflag1;
	}
	public boolean isRflag() {
		return rflag;
	}
	public void setRflag(boolean rflag) {
		this.rflag = rflag;
	}
	public boolean isRflag1() {
		return rflag1;
	}
	public void setRflag1(boolean rflag1) {
		this.rflag1 = rflag1;
	}
	public int getPenaltID() {
		return penaltID;
	}
	public void setPenaltID(int penaltID) {
		this.penaltID = penaltID;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public List<String> getStaffNames() {
		return staffNames;
	}
	public void setStaffNames(List<String> staffNames) {
		this.staffNames = staffNames;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public List<PayrollDataBean> getPayrollList() {
		return payrollList;
	}
	public void setPayrollList(List<PayrollDataBean> payrollList) {
		this.payrollList = payrollList;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
}
