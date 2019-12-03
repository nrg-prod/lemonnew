package com.nrg.lemon.managedBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.lemon.bo.LemonBo;
import com.nrg.lemon.domain.PayrollDataBean;
import com.nrg.lemon.domain.ProductionDataBean;
import com.nrg.lemon.shared.Client;
import com.nrg.lemon.util.CommonValidate;

@ManagedBean(name="payrollMB")
public class PayrollMB {
	ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	LemonBo bo=(LemonBo)ctx.getBean("bo");
	String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PersonID");
	String clientID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ClientID");
	PayrollDataBean payrollDataBean=new PayrollDataBean();
	ProductionDataBean productionDataBean=new ProductionDataBean();
	Date date=new Date();
	private List<ProductionDataBean> cutterconfirmationList=null;
	private List<String> cmtList=null;
	private List<String> printerList=null;
	private List<String> cutterlist=null;
	
	
	public List<String> getPrinterList() {
		return printerList;
	}

	public void setPrinterList(List<String> printerList) {
		this.printerList = printerList;
	}

	public List<String> getCutterlist() {
		return cutterlist;
	}

	public void setCutterlist(List<String> cutterlist) {
		this.cutterlist = cutterlist;
	}

	public List<String> getCmtList() {
		return cmtList;
	}

	public void setCmtList(List<String> cmtList) {
		this.cmtList = cmtList;
	}

	public List<ProductionDataBean> getCutterconfirmationList() {
		return cutterconfirmationList;
	}

	public void setCutterconfirmationList(
			List<ProductionDataBean> cutterconfirmationList) {
		this.cutterconfirmationList = cutterconfirmationList;
	}
	public ProductionDataBean getProductionDataBean() {
		return productionDataBean;
	}

	public void setProductionDataBean(ProductionDataBean productionDataBean) {
		this.productionDataBean = productionDataBean;
	}

	public PayrollDataBean getPayrollDataBean() {
		return payrollDataBean;
	}

	public void setPayrollDataBean(PayrollDataBean payrollDataBean) {
		this.payrollDataBean = payrollDataBean;
	}
	
	/*PENALTY */
	
	public String panaltyPage(){
		payrollDataBean.setPayrollList(bo.getPenaltyDetails(personID));
		return "penaltyForm";
	}
	
	public String addPenalty(){
		payrollDataBean.setDate(date);
		payrollDataBean.setAmount("");
		payrollDataBean.setReason("");
		payrollDataBean.setStaffName("");
		payrollDataBean.setType("");
		payrollDataBean.setFlag(false);
		return "";
	}
	
	public void staffChange(ValueChangeEvent v){
		System.out.println("staffs "+v.getNewValue().toString());
		if(!v.getNewValue().toString().equals("select")){
			payrollDataBean.setFlag(true);
			payrollDataBean.setStaffNames(bo.getStaffNames(personID,v.getNewValue().toString()));
		}else{
			payrollDataBean.setFlag(false);
		}
	}
	
	public String penaltySave(){
		if(validateEdit(true)){
			bo.savePenalty(personID,payrollDataBean);
			RequestContext.getCurrentInstance().execute("PF('penaltyreg').hide();");
			RequestContext.getCurrentInstance().execute("PF('confirm').show();");
		}
		return "";
	}
	
	public boolean validateEdit(boolean flag){
		boolean valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();		
		if(payrollDataBean.getReason().equals("")){
			fieldName=CommonValidate.findComponentInRoot("reason").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Enter reason "));
			valid=false;
		}
		if(payrollDataBean.getAmount().equals("")){
			fieldName=CommonValidate.findComponentInRoot("amount").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Enter Amount "));
			valid=false;
		}
		if(payrollDataBean.getType().equals("select")){
			fieldName=CommonValidate.findComponentInRoot("type").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select category"));
			valid=false;
		}
		if(!payrollDataBean.getType().equals("select")){
			if(payrollDataBean.getStaffName().equals("")){
				fieldName=CommonValidate.findComponentInRoot("staff").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Please Select nAme"));
				valid=false;
			}			
		}
		return valid;
	}
	
	public String penaltyEdit(){
		payrollDataBean.setStatus("edit");
		String no=payrollDataBean.getSerialNo(); 
		PayrollDataBean paylist=new PayrollDataBean();
		paylist.setDate(payrollDataBean.getPayrollList().get(Integer.parseInt(no)-1).getDate());
		paylist.setSerialNo(payrollDataBean.getPayrollList().get(Integer.parseInt(no)-1).getSerialNo());
		paylist.setPenaltID(payrollDataBean.getPayrollList().get(Integer.parseInt(no)-1).getPenaltID());
		paylist.setType(payrollDataBean.getPayrollList().get(Integer.parseInt(no)-1).getType());
		paylist.setStaffName(payrollDataBean.getPayrollList().get(Integer.parseInt(no)-1).getStaffName());
		paylist.setAmount(payrollDataBean.getPayrollList().get(Integer.parseInt(no)-1).getAmount());
		paylist.setReason(payrollDataBean.getPayrollList().get(Integer.parseInt(no)-1).getReason());
		paylist.setAflag(false);
		paylist.setAflag1(true);
		paylist.setRflag(false);
		paylist.setRflag1(true);
		payrollDataBean.getPayrollList().set(Integer.parseInt(no)-1, paylist);
		return "";
	}
	
	public String penaltyUpdate(){
		bo.penaltyUpdate(payrollDataBean);
		RequestContext.getCurrentInstance().execute("PF('edit').show();");
		return "";
	}
	
	public String penaltyDelete(){
		payrollDataBean.setStatus("delete");
		bo.penaltyUpdate(payrollDataBean);
		RequestContext.getCurrentInstance().execute("PF('delete').show();");
		return "";
	}
	
	/*PURCHASE*/
	
	public String purchasePage(){
		payrollDataBean.setType("");
		payrollDataBean.setSupplier("");
		payrollDataBean.setFromdate(null);
		payrollDataBean.setTodate(null);
		payrollDataBean.setFlag(false);payrollDataBean.setAflag(false);
		return "purchasepayment";
	}
	
	/*public void divisionValueChnage(ValueChangeEvent vc){
		int count=0;
		try{
			String type=vc.getNewValue().toString();
			if(type.equalsIgnoreCase("select")){
				payrollDataBean.setFlag(false);payrollDataBean.setAflag(false);
			}else{
				payrollDataBean.setStatus(type);
				payrollDataBean.setPayrollList(bo.getPurchasePayment(personID,payrollDataBean));
				if(type.equalsIgnoreCase("fabric")){
					payrollDataBean.setFlag(true);payrollDataBean.setAflag(false);
				}else{
					payrollDataBean.setFlag(false);payrollDataBean.setAflag(true);
				}
				for (int i = 0; i < payrollDataBean.getPayrollList().size(); i++) {
					if(payrollDataBean.getPayrollList().get(i).isFlag()==false){
						count++;
					}
				}
				if(count==payrollDataBean.getPayrollList().size()){
					payrollDataBean.setAflag1(false);
				}else{
					payrollDataBean.setAflag1(true);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	
	public void divisionValueChnage(ValueChangeEvent vc){
		try{
			String type=vc.getNewValue().toString();
			if(type.equalsIgnoreCase("select")){
				payrollDataBean.setFlag(false);payrollDataBean.setAflag(false);
			}else{
				payrollDataBean.setStatus(type);
				payrollDataBean.setSupplierList(bo.getsuppliernamelist(personID,type));
				System.out.println("supplier --- "+payrollDataBean.getSupplierList().size());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getpurchasepayments()
	{
		int count=0;
		try{
			if(validation(true)){
				if(payrollDataBean.getType().equalsIgnoreCase("select")||payrollDataBean.getType()==null){
					payrollDataBean.setFlag(false);payrollDataBean.setAflag(false);
				}else{
					payrollDataBean.setPayrollList(bo.getPurchasePayment(personID,payrollDataBean));
					if(payrollDataBean.getType().equalsIgnoreCase("fabric")){
						payrollDataBean.setFlag(true);payrollDataBean.setAflag(false);
					}else{
						payrollDataBean.setFlag(false);payrollDataBean.setAflag(true);
					}
					for (int i = 0; i < payrollDataBean.getPayrollList().size(); i++) {
						if(payrollDataBean.getPayrollList().get(i).isFlag()==false){
							count++;
						}
					}
					if(count==payrollDataBean.getPayrollList().size()){
						payrollDataBean.setAflag1(false);
					}else{
						payrollDataBean.setAflag1(true);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	private boolean validation(boolean flag)
	{
		System.out.println("innnnnnnnnnnnnnn "+payrollDataBean.getType());
		System.out.println("innnnnnnnnnnnnnn "+payrollDataBean.getFromdate());
		System.out.println("innnnnnnnnnnnnnn "+payrollDataBean.getTodate());
		boolean valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(payrollDataBean.getType().equalsIgnoreCase("select") || payrollDataBean.getType()==null || payrollDataBean.getType().equalsIgnoreCase(""))
		{
			System.out.println("typppppeeeeeeeeee");
			if(flag){
				fieldName=CommonValidate.findComponentInRoot("divisionid").getClientId();
				fc.addMessage(fieldName, new FacesMessage("PLEASE CHOOSE THE DIVISION"));
			}
			valid=false;
		}
		if(payrollDataBean.getFromdate()==null && payrollDataBean.getTodate()!=null)
		{
			System.out.println("frommmmmmmm");
			if(flag){
				fieldName=CommonValidate.findComponentInRoot("fdate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("PLEASE CHOOSE FROM DATE"));
			}
			valid=false;
		}
		if(payrollDataBean.getTodate()==null && payrollDataBean.getFromdate()!=null)
		{
			System.out.println("ttttooooooooooo");
			if(flag){
				fieldName=CommonValidate.findComponentInRoot("tdate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("PLEASE CHOOSE TO DATE"));
			}
			valid=false;
		}
		
		return valid;
	}
	public String purchasePayments(){
		try{
			String payment=bo.payPurchases(personID,payrollDataBean);
			if(payment.equalsIgnoreCase("Success"))
			RequestContext.getCurrentInstance().execute("PF('paySuccess').show();");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*EMPLOYEE SALARY*/
	
	public String employeeSalaryPage(){
		bo.getEmployeePayrolls(personID,payrollDataBean);
		return "employeePayroll";
	}
	
	public String payroll(){
		payrollDataBean.setMonth("");
		payrollDataBean.setYear("");
		payrollDataBean.setMonths(null);
		payrollDataBean.setYears(null);
		payrollDataBean.setEmployee("");
		bo.getMonthYears(personID,payrollDataBean);
		return "";
	}
	
	public String generatePayroll(){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();	
		payrollDataBean.setSalary("");
		payrollDataBean.setOverTime("");
		payrollDataBean.setLoanAmount("");
		payrollDataBean.setAttendanceBonus("");
		if(payrollValidate(true)){
			String status=bo.getemployeeDetails(personID,payrollDataBean);
			if(status.equals("Exist")){
				fieldName=CommonValidate.findComponentInRoot("errmsg").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Already generated the payroll"));
				RequestContext.getCurrentInstance().execute("PF('empPayreg').show();");
			}else{				
				RequestContext.getCurrentInstance().execute("PF('empPayreg').hide();");
				RequestContext.getCurrentInstance().execute("PF('empPayreg1').show();");
			}
		}		
		return "";
	}
	
	public boolean payrollValidate(boolean valid){
		valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();	
		System.out.println("emp "+payrollDataBean.getEmployee());
		try{
			if(payrollDataBean.getMonth().equals("")){
				fieldName=CommonValidate.findComponentInRoot("month").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Please Select Month "));
				valid=false;
			}
		}catch(Exception e){
			fieldName=CommonValidate.findComponentInRoot("month").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select Month "));
			valid=false;
		}
		try{
			if(payrollDataBean.getYear().equals("")){
				fieldName=CommonValidate.findComponentInRoot("year").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Please Select Year "));
				valid=false;
			}
		}catch(Exception e){
			fieldName=CommonValidate.findComponentInRoot("year").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select Year "));
			valid=false;
		}try{
			if(payrollDataBean.getEmployee().equals("")){
				fieldName=CommonValidate.findComponentInRoot("employee").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Please Select Employee"));
				valid=false;
			}
		}catch(Exception e){
			fieldName=CommonValidate.findComponentInRoot("employee").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select Employee"));
			valid=false;
		}		
		return valid;
	}
	
	public String payrollClose(){
		RequestContext.getCurrentInstance().execute("PF('empPayreg').show();");
		RequestContext.getCurrentInstance().execute("PF('empPayreg1').hide();");
		return "";
	}
	
	public String savePayroll(){
		if(payrollValidates(true)){
			bo.payrollSave(personID,payrollDataBean);			
			RequestContext.getCurrentInstance().execute("PF('empPayreg1').hide();");
			RequestContext.getCurrentInstance().execute("PF('confirm').show();");
		}		
		return "";
	}
	
	public boolean payrollValidates(boolean valid){
		valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		System.out.println("salray "+payrollDataBean.getSalary());
		try{
			if(payrollDataBean.getSalary().equals("")){
				fieldName=CommonValidate.findComponentInRoot("salary").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Please Enter Salary "));
				valid=false;
			}
		}catch(Exception e){
			fieldName=CommonValidate.findComponentInRoot("salary").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Enter Salary "));
			valid=false;
		}
		return valid;
	}
	
	public String payrollView(){
		bo.getPayrollDetails(personID,payrollDataBean);
		return "";
	}
	
	public String payrollUpdate(){
		payrollDataBean.setStatus("edit");
		if(payrollValidates1(true)){
			bo.updatePayroll(payrollDataBean);
			RequestContext.getCurrentInstance().execute("PF('editDialog').hide();");
			RequestContext.getCurrentInstance().execute("PF('edit').show();");
		}
		return "";
	}
	
	public String payrollDelete(){
		payrollDataBean.setStatus("delete");
		bo.updatePayroll(payrollDataBean);
		RequestContext.getCurrentInstance().execute("PF('delete').show();");
		return "";
	}
	
	public boolean payrollValidates1(boolean valid){
		valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		System.out.println("salray "+payrollDataBean.getSalary());
		try{
			if(payrollDataBean.getSalary().equals("")){
				fieldName=CommonValidate.findComponentInRoot("salary1").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Please Enter Salary "));
				valid=false;
			}
		}catch(Exception e){
			fieldName=CommonValidate.findComponentInRoot("salary1").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Enter Salary "));
			valid=false;
		}
		return valid;
	}
	
	/*PRINTER*/
	
	public String printerPage(){
	productionDataBean.setPrinterr("");
		printerList=new ArrayList<String>();
		printerList=bo.getPrinterlist(personID, clientID);
		productionDataBean.setInvoiceStatus("printer");
		productionDataBean.setOrderQuantitylist(null);
		productionDataBean.setCheckBox(false);
		productionDataBean.setSelectCheckbox(false);
		bo.getPrinterPayments(personID,productionDataBean);
		payrollDataBean.setFlag(true);payrollDataBean.setAflag(false);payrollDataBean.setBflag(false);
		return "printerPayroll";
	}
	
	/*CMT*/
	
	public String cmtPage(){
		List<ProductionDataBean> printerList=new ArrayList<ProductionDataBean>();
		try{
			productionDataBean.setInvoiceStatus("cmt");
			productionDataBean.setCmt(null);
			productionDataBean.setOrderQuantitylist(null);
			bo.getPrinterPayments(personID,productionDataBean);
			payrollDataBean.setFlag(false);payrollDataBean.setAflag(true);payrollDataBean.setBflag(false);
			if(productionDataBean.getOrderQuantitylist().size()>0){
				for (int i = 0; i < productionDataBean.getOrderQuantitylist().size(); i++) {
					ProductionDataBean printers=new ProductionDataBean();
					printers.setSerialNo(String.valueOf(i+1));
					printers.setOrderDate(productionDataBean.getOrderQuantitylist().get(i).getOrderDate());
					printers.setInvoiceNo(productionDataBean.getOrderQuantitylist().get(i).getInvoiceNo());
					printers.setInvoiceStatus(productionDataBean.getOrderQuantitylist().get(i).getInvoiceStatus());
					printers.setValue(productionDataBean.getOrderQuantitylist().get(i).getValue());
					printers.setTotalQuantity(productionDataBean.getOrderQuantitylist().get(i).getTotalQuantity());
					printers.setCmt(productionDataBean.getOrderQuantitylist().get(i).getCmt());
					printerList.add(printers);
					System.out.println("invoice status --- "+productionDataBean.getOrderQuantitylist().get(i).getInvoiceStatus());
				}
				productionDataBean.setOrderQuantitylist(printerList);
			}
			cmtList=new ArrayList<String>();
			cmtList=bo.getCmt(personID,clientID);
			System.out.println("cmtlist "+cmtList.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "printerPayroll";
	}
	
	
	
	public void cmtSearch(){
		System.out.println("Inside cmtSearch value change");
		List<ProductionDataBean> printerList=new ArrayList<ProductionDataBean>();
		try{
			productionDataBean.setInvoiceStatus("cmt");
			productionDataBean.setOrderQuantitylist(null);
			System.out.println("cmt "+productionDataBean.getCmt());
			if(productionDataBean.getCmt().equalsIgnoreCase("ALL")){
				bo.getPrinterPayments(personID,productionDataBean);
			}else{
				bo.getvendorCmtPayments(personID,productionDataBean);
			}
			payrollDataBean.setFlag(false);payrollDataBean.setAflag(true);payrollDataBean.setBflag(false);
			if(productionDataBean.getOrderQuantitylist().size()>0){
				for (int i = 0; i < productionDataBean.getOrderQuantitylist().size(); i++) {
					ProductionDataBean printers=new ProductionDataBean();
					printers.setSerialNo(String.valueOf(i+1));
					printers.setOrderDate(productionDataBean.getOrderQuantitylist().get(i).getOrderDate());
					printers.setInvoiceNo(productionDataBean.getOrderQuantitylist().get(i).getInvoiceNo());
					printers.setInvoiceStatus(productionDataBean.getOrderQuantitylist().get(i).getInvoiceStatus());
					printers.setValue(productionDataBean.getOrderQuantitylist().get(i).getValue());
					printers.setTotalQuantity(productionDataBean.getOrderQuantitylist().get(i).getTotalQuantity());
					printers.setCmt(productionDataBean.getOrderQuantitylist().get(i).getCmt());
					printerList.add(printers);
					System.out.println("invoice status --- "+productionDataBean.getOrderQuantitylist().get(i).getInvoiceStatus());
				}
				productionDataBean.setOrderQuantitylist(printerList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*CUTTER*/
	
	public String cutterPage(){
		try{
			productionDataBean.setCutter("");
			cutterlist=new ArrayList<String>();
			cutterlist=bo.getcutterList(personID, clientID);
			productionDataBean.setInvoiceStatus("cutter");
			productionDataBean.setOrderQuantitylist(null);
			bo.getPrinterPayments(personID,productionDataBean);
			payrollDataBean.setFlag(false);
			payrollDataBean.setAflag(false);
			payrollDataBean.setBflag(true);
		}catch(Exception e){
		}
		return "printerPayroll";
	}
	
	/*PAYROLL REPORT*/
	
	public String payrollReport(){
		payrollDataBean.setType("");
		payrollDataBean.setFlag(false);payrollDataBean.setAflag(false);
		return "payrollReport";
	}
	
	public void typeSelect(ValueChangeEvent v){
		System.out.println("type "+v.getNewValue().toString());
		payrollDataBean.setPayrollList(null);
		payrollDataBean.setType(v.getNewValue().toString());		
		if(v.getNewValue().toString().equals("select")){
			payrollDataBean.setFlag(false);payrollDataBean.setAflag(false);
		}else{
			bo.getPayrollReports(personID,payrollDataBean);
			if(v.getNewValue().toString().equals("Salary")){			
				payrollDataBean.setFlag(true);payrollDataBean.setAflag(false);
			}else if(v.getNewValue().toString().equals("Penalty")){
				payrollDataBean.setFlag(false);payrollDataBean.setAflag(true);
			}
		}
	}
	
	private boolean valid=false;
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	
	public String paymentsave(){
		RequestContext rc=RequestContext.getCurrentInstance();
		  try{
		    System.out.println("inside payment save");
		    if(productionDataBean.getOrderQuantitylist().get(0).getInvoiceNo().startsWith("Pr")){
		    	String payment=bo.payment(personID,clientID,productionDataBean.getOrderQuantitylist());
			    if("Success".equalsIgnoreCase(payment)){
			    	paymentconfirmation();
			    	System.out.println("=============");
			    	rc.execute("PF('printerpaymentconfirmation').show();");
			    }
		    }else if(productionDataBean.getOrderQuantitylist().get(0).getInvoiceNo().startsWith("CMR")){
		    	String payment=bo.payment1(personID,clientID,productionDataBean.getOrderQuantitylist());
			    if("Success".equalsIgnoreCase(payment)){
			    	paymentconfirmation();
			    	rc.execute("PF('cmtpaymentconfirmation').show();");
			    }
		    }else if(productionDataBean.getOrderQuantitylist().get(0).getInvoiceNo().startsWith("CU")){
		    	String payment=bo.payment(personID,clientID,productionDataBean.getOrderQuantitylist());
			    if("Success".equalsIgnoreCase(payment)){
			    	paymentconfirmation();
			    	rc.execute("PF('cuttersuccess1').show();");
			    }
		    	
		    }
		  }catch(Exception e){
		  }
		  return"";
		 }
	
	
	
	public String paymentconfirmation(){
		cutterconfirmationList=new ArrayList<ProductionDataBean>();
		int sno=1;
		try{
			if(productionDataBean.getOrderQuantitylist().size()>0){
				System.out.println("sizee "+productionDataBean.getOrderQuantitylist().size());
				for (int i = 0; i < productionDataBean.getOrderQuantitylist().size(); i++) {
					if(productionDataBean.getOrderQuantitylist().get(i).isCheckBox()==true){
						System.out.println("111");
						ProductionDataBean prd=new ProductionDataBean();
						prd.setSerialNo(""+sno);
						prd.setOrderDate(productionDataBean.getOrderQuantitylist().get(i).getOrderDate());
						prd.setInvoiceNo(productionDataBean.getOrderQuantitylist().get(i).getInvoiceNo());
						prd.setCutter(productionDataBean.getOrderQuantitylist().get(i).getCutter());
						prd.setModelName(productionDataBean.getOrderQuantitylist().get(i).getModelName());
						prd.setTotalQuantity(productionDataBean.getOrderQuantitylist().get(i).getTotalQuantity());
						prd.setValue(productionDataBean.getOrderQuantitylist().get(i).getValue());
						prd.setInvoiceStatus(productionDataBean.getOrderQuantitylist().get(i).getInvoiceStatus());
						prd.setSaving(productionDataBean.getOrderQuantitylist().get(i).getSaving());
						prd.setCmt(productionDataBean.getOrderQuantitylist().get(i).getCmt());
						prd.setPrinter(productionDataBean.getOrderQuantitylist().get(i).getPrinter());
						cutterconfirmationList.add(prd);
						sno++;
					}
				}
			}System.out.println("cutterconfirmationList -- "+cutterconfirmationList.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	public void cutterChange(){
		try{
			if(productionDataBean.getCutter().equalsIgnoreCase("ALL")){
				productionDataBean.setInvoiceStatus("cutter");
				productionDataBean.setOrderQuantitylist(null);
				bo.getPrinterPayments(personID,productionDataBean);
				payrollDataBean.setFlag(false);
				payrollDataBean.setAflag(false);
				payrollDataBean.setBflag(true);
			}else{
				bo.payrollCutterchange(personID,clientID,productionDataBean);
			}
		}catch(Exception e){
		}
	}
	
	public void printerChange(){
		try{
			if(productionDataBean.getPrinterr().equalsIgnoreCase("ALL")){
				printerList=bo.getPrinterlist(personID, clientID);
				productionDataBean.setInvoiceStatus("printer");
				productionDataBean.setOrderQuantitylist(null);
				bo.getPrinterPayments(personID,productionDataBean);
				payrollDataBean.setFlag(true);payrollDataBean.setAflag(false);payrollDataBean.setBflag(false);
			}else{
				bo.payrollPrinterchange(personID,clientID,productionDataBean);
			}
		}catch(Exception e){
			
		}
	}
	
	public void checkboxPrinter(){
		try{
			if(payrollDataBean.isSelectCheckbox()==true){
				for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
					if(!productionDataBean.getOrderQuantitylist().get(i).getInvoiceStatus().equalsIgnoreCase("PAID")){
						productionDataBean.getOrderQuantitylist().get(i).setCheckBox(true);
					}
				}
			}else if(payrollDataBean.isSelectCheckbox()==false){
				for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
					productionDataBean.getOrderQuantitylist().get(i).setCheckBox(false);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void checkboxCutter(){
		try{
			if(payrollDataBean.isSelectCheckbox()==true){
				for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
					if(!productionDataBean.getOrderQuantitylist().get(i).getInvoiceStatus().equalsIgnoreCase("PAID")){
						productionDataBean.getOrderQuantitylist().get(i).setCheckBox(true);
					}
				}
			}else if(payrollDataBean.isSelectCheckbox()==false){
				for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
					productionDataBean.getOrderQuantitylist().get(i).setCheckBox(false);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
