package com.nrg.lemon.managedBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.lemon.bo.LemonBo;
import com.nrg.lemon.domain.FinanceDataBean;
import com.nrg.lemon.util.CommonValidate;
@ManagedBean(name="financeMB")
@RequestScoped
public class FinanceMB {
	FinanceDataBean financeDataBean=new FinanceDataBean();
	String personID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PersonID");
	String clientID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ClientID");
	ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	LemonBo bo=(LemonBo)ctx.getBean("bo");
	private boolean wastequanFlag=false;
	private boolean staffFlag=false;
	private boolean subconFlag=false;
	private List<String> nameList=null;
	private List<FinanceDataBean> domainList=new ArrayList<FinanceDataBean>();
	
	public boolean isWastequanFlag() {
		return wastequanFlag;
	}
	public void setWastequanFlag(boolean wastequanFlag) {
		this.wastequanFlag = wastequanFlag;
	}
	public List<FinanceDataBean> getDomainList() {
		return domainList;
	}
	public void setDomainList(List<FinanceDataBean> domainList) {
		this.domainList = domainList;
	}
	public List<String> getNameList() {
		return nameList;
	}
	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}
	public boolean isStaffFlag() {
		return staffFlag;
	}
	public void setStaffFlag(boolean staffFlag) {
		this.staffFlag = staffFlag;
	}
	public boolean isSubconFlag() {
		return subconFlag;
	}
	public void setSubconFlag(boolean subconFlag) {
		this.subconFlag = subconFlag;
	}
	public FinanceDataBean getFinanceDataBean() {
		return financeDataBean;
	}
	public void setFinanceDataBean(FinanceDataBean financeDataBean) {
		this.financeDataBean = financeDataBean;
	}
	public String debtPage(){
		financeDataBean.setDebtCategory(null);
		financeDataBean.setDebtName("");
		financeDataBean.setDebtAmount("");
		financeDataBean.setDebtDate(null);
		financeDataBean.setDebtReason("");
		 financeDataBean.setDebtBalance("");
		setStaffFlag(false);
		setSubconFlag(false);
		financeDataBean.setDebtStatus("taken");
		domainList=bo.getdebtList(personID,clientID,financeDataBean);
		return "debtPage";
	}
	public String debtPage1(){
		financeDataBean.setDebtCategory(null);
		financeDataBean.setDebtName("");
		financeDataBean.setDebtAmount("");
		financeDataBean.setDebtDate(null);
		financeDataBean.setDebtReason("");
		 financeDataBean.setDebtBalance("");
		setStaffFlag(false);
		setSubconFlag(false);
		financeDataBean.setDebtStatus("receive");
		domainList=bo.getdebtList(personID,clientID,financeDataBean);
		return "debtPage";
	}
	public void categoryValueChange(ValueChangeEvent vc){
		try{
			String str=vc.getNewValue().toString();
			if(str.equalsIgnoreCase("staff")){
				nameList=new ArrayList<String>();
				setStaffFlag(true);setSubconFlag(false);
			}else if(str.equalsIgnoreCase("subcon")){
				nameList=new ArrayList<String>();
				setStaffFlag(false);setSubconFlag(true);
			}
			nameList=bo.getdebtname(personID,clientID,str);			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	public void categoryValueChange1(ValueChangeEvent vc){
		try{
			String str=vc.getNewValue().toString();
			if(str.equalsIgnoreCase("staff")){
				nameList=new ArrayList<String>();
				setStaffFlag(true);setSubconFlag(false);
			}else if(str.equalsIgnoreCase("subcon")){
				nameList=new ArrayList<String>();
				setStaffFlag(false);setSubconFlag(true);
			}
			nameList=bo.getdebtreceivename(personID,clientID,str);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void nameValueChange(ValueChangeEvent vc){
		try{
			String str=vc.getNewValue().toString();
			financeDataBean.setDebtName(str);
			bo.getdebtnamevaluChange(personID,clientID,financeDataBean);
			financeDataBean.setDebtBalances(financeDataBean.getDebtBalance());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void amountChange(ValueChangeEvent vc){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			String str=vc.getNewValue().toString();
			if(new BigDecimal(str).compareTo(new BigDecimal(financeDataBean.getDebtBalances()))==1){
				fieldName=CommonValidate.findComponentInRoot("amount1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("you entered the exceed amount"));
			}else{
				financeDataBean.setDebtBalance(String.valueOf(new BigDecimal(financeDataBean.getDebtBalances()).subtract(new BigDecimal(str))));
			}				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void onTabChange(TabChangeEvent event) {
	    setStaffFlag(false);
	    setSubconFlag(false);
	    financeDataBean.setDebtCategory(null); 
	    financeDataBean.setDebtAmount("");
	    financeDataBean.setDebtName("");
	    financeDataBean.setDebtDate(null);
	    financeDataBean.setDebtReason("");
	    financeDataBean.setDebtBalance("");
	    String str=event.getTab().getTitle();
	    if(str.equalsIgnoreCase("debt taken")){
	    	financeDataBean.setDebtStatus("taken");
			domainList=bo.getdebtList(personID,clientID,financeDataBean);
	    }else if(str.equalsIgnoreCase("debt receive")){
	    	financeDataBean.setDebtStatus("receive");
			domainList=bo.getdebtList(personID,clientID,financeDataBean);
	    }
	}
	private boolean validation(boolean valid){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(financeDataBean.getDebtCategory())){
			fieldName=CommonValidate.findComponentInRoot("cate").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the category"));
			valid=false;
		}
		else{
			if(financeDataBean.getDebtCategory().equalsIgnoreCase("staff")){
				if(financeDataBean.getDebtName().equalsIgnoreCase("")){
					fieldName=CommonValidate.findComponentInRoot("staffname").getClientId();
					fc.addMessage(fieldName, new FacesMessage("please enter the staff name"));
					valid=false;
				}
			}else if(financeDataBean.getDebtCategory().equalsIgnoreCase("subcon")){
				if(financeDataBean.getDebtName().equalsIgnoreCase("")){
					fieldName=CommonValidate.findComponentInRoot("subconname").getClientId();
					fc.addMessage(fieldName, new FacesMessage("please enter the vendor name"));
					valid=false;
				}
			}
		}
		if(financeDataBean.getDebtDate() == null){
			fieldName=CommonValidate.findComponentInRoot("date").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please select the date"));
			valid=false;
		}
		if(StringUtils.isEmpty(financeDataBean.getDebtAmount())){
			fieldName=CommonValidate.findComponentInRoot("amount").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
			valid=false;
		}
		if(StringUtils.isEmpty(financeDataBean.getDebtReason())){
			fieldName=CommonValidate.findComponentInRoot("reason").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please enter the reason"));
			valid=false;
		}
		return valid;
	}
	public String savetakenDebt(){
		String status="Fail";
		try{
			if(personID != null && clientID != null){
				if(validation(true)){
					financeDataBean.setDebtStatus("taken");
					status=bo.saveDebt(personID,clientID,financeDataBean);
					if(status.equalsIgnoreCase("Success")){
						  RequestContext.getCurrentInstance().execute("PF('confirm').show();");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	private boolean validation1(boolean valid){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(financeDataBean.getDebtCategory())){
			fieldName=CommonValidate.findComponentInRoot("cate1").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the category"));
			valid=false;
		}
		else{
			if(financeDataBean.getDebtCategory().equalsIgnoreCase("staff")){
				if(financeDataBean.getDebtName().equalsIgnoreCase("")){
					fieldName=CommonValidate.findComponentInRoot("staffname1").getClientId();
					fc.addMessage(fieldName, new FacesMessage("please enter the staff name"));
					valid=false;
				}
			}else if(financeDataBean.getDebtCategory().equalsIgnoreCase("subcon")){
				if(financeDataBean.getDebtName().equalsIgnoreCase("")){
					fieldName=CommonValidate.findComponentInRoot("subconname1").getClientId();
					fc.addMessage(fieldName, new FacesMessage("please enter the vendor name"));
					valid=false;
				}
			}
		}
		if(financeDataBean.getDebtDate() == null){
			fieldName=CommonValidate.findComponentInRoot("date1").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please select the date"));
			valid=false;
		}
		if(StringUtils.isEmpty(financeDataBean.getDebtAmount())){
			fieldName=CommonValidate.findComponentInRoot("amount1").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
			valid=false;
		}else{
			if(new BigDecimal(financeDataBean.getDebtAmount()).compareTo(new BigDecimal(financeDataBean.getDebtBalances()))==1){
				fieldName=CommonValidate.findComponentInRoot("amount1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("you entered the exceed amount"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(financeDataBean.getDebtReason())){
			fieldName=CommonValidate.findComponentInRoot("reason1").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please enter the reason"));
			valid=false;
		}
		return valid;
	}
	public String savereceiveDebt(){
		String status="Fail";
		try{
			if(personID != null && clientID != null){
				if(validation1(true)){
					financeDataBean.setDebtStatus("receive");
					status=bo.saveDebt(personID,clientID,financeDataBean);
					if(status.equalsIgnoreCase("Success")){
						  RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	public String debtView(){
		try{
			bo.getDebtView(personID,clientID,financeDataBean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	private boolean debteditValidation(boolean valid) {
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(financeDataBean.getDebtAmount())){
			fieldName=CommonValidate.findComponentInRoot("eamount").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
			valid=false;
		}else{
			if(financeDataBean.getDebtStatus().equalsIgnoreCase("receive")){
				if(new BigDecimal(financeDataBean.getDebtAmount()).compareTo(new BigDecimal(financeDataBean.getDebtBalance()))==1){
					fieldName=CommonValidate.findComponentInRoot("eamount").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("you entered the exceed amount"));
					valid=false;
				}
			}
		}
		if(financeDataBean.getDebtReason().equalsIgnoreCase("")){
			fieldName=CommonValidate.findComponentInRoot("ereason").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please enter the reason"));
			valid=false;
		}
		return valid;
	}
	public String debtUpdate(){
		try{
			if(personID!=null && clientID!=null){
				if(debteditValidation(true)){
					String status=bo.debitUpdate(personID,clientID,financeDataBean);
					if(status.equalsIgnoreCase("Success")){
						RequestContext.getCurrentInstance().execute("PF('editDialog').hide()");
						if(financeDataBean.getDebtStatus().equalsIgnoreCase("taken")){
							RequestContext.getCurrentInstance().execute("PF('edit').show()");
						}else if(financeDataBean.getDebtStatus().equalsIgnoreCase("receive")){
							RequestContext.getCurrentInstance().execute("PF('edit1').show()");
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	/*INCOME*/
	
	public String incomePage(){
		financeDataBean.setType("");
		financeDataBean.setFlag(false);
		return "income";
	}
	
	public void divisionValueChnage(ValueChangeEvent vc){
		try{
			String type=vc.getNewValue().toString();
			if(type.equalsIgnoreCase("select")){
				financeDataBean.setFlag(false);
			}else{
				financeDataBean.setDebtStatus(type);
				financeDataBean.setFinanceList(bo.getIncomedetails(personID,financeDataBean));
				financeDataBean.setFlag(true);				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//TREASURY MODULE START//
		
		public String categoryReg(){
			financeDataBean.setTransactionCategory("");
			financeDataBean.setTransactionDescription("");
			return "";
		}
		public String saveCategory(){
			String status="Fail";String fieldName;boolean valid=true;
			FacesContext fc=FacesContext.getCurrentInstance();
			try{
				if(personID != null && clientID !=null){
					if(StringUtils.isEmpty(financeDataBean.getTransactionCategory())){
						fieldName=CommonValidate.findComponentInRoot("categoryname").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("please enter the category"));
						valid=false;
					}
					if(valid==true){
						status=bo.saveCategory(personID,clientID,financeDataBean);
						if(status.equalsIgnoreCase("Success")){
							RequestContext.getCurrentInstance().execute("PF('category').show()");
							RequestContext.getCurrentInstance().execute("PF('categoryDialog').hide()");
						}else if(status.equalsIgnoreCase("Exist")){
							fieldName=CommonValidate.findComponentInRoot("categoryname").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("This category already exist"));
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return "";
		}
		public String transactionPage(){
			try{
				domainList=new ArrayList<FinanceDataBean>();
				domainList=bo.transactionview(personID,clientID,financeDataBean);
			}catch(Exception e){
				e.printStackTrace();
			}
			return "transactionRegisteration";
		}
		public String transactionReg(){
			financeDataBean.setDate(null);
			financeDataBean.setPaymentmode("");
			financeDataBean.setPaymenttype("");
			financeDataBean.setName("");
			financeDataBean.setAmount("");
			financeDataBean.setNote("");
			financeDataBean.setBankname("");
			financeDataBean.setCardno("");
			financeDataBean.setAccountno("");
			financeDataBean.setChequeno("");
			financeDataBean.setChequedate(null);
			financeDataBean.setTransactionCategory("");
			financeDataBean.setWasteQuantity("");
			nameList=bo.gettransactioncategory(personID,clientID);
			return "";
		}
		public String transactionsave(){
			String status="";
			try{
				if(personID!=null & clientID!=null){
					if(savevalidation(true)){
						status=bo.transactionsave(personID,clientID,financeDataBean);
						if(status.equalsIgnoreCase("Success")){
							RequestContext.getCurrentInstance().execute("PF('confirm').show()");
							RequestContext.getCurrentInstance().execute("PF('transactionDialog').hide()");
						}
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return"";
		}
		private boolean savevalidation(boolean valid) {
			String fieldName;
			FacesContext fc=FacesContext.getCurrentInstance();
			if(financeDataBean.getDate()==null){
				fieldName=CommonValidate.findComponentInRoot("date").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please select the date"));
				valid=false;
			}if(StringUtils.isEmpty(financeDataBean.getPaymentmode())){
				fieldName=CommonValidate.findComponentInRoot("mode").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please select the payment mode"));
				valid=false;
			}else if(financeDataBean.getPaymentmode().equalsIgnoreCase("card")){
				if(StringUtils.isEmpty(financeDataBean.getBankname())){
					fieldName=CommonValidate.findComponentInRoot("bank").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the bank name"));
					valid=false;
				}
				if(StringUtils.isEmpty(financeDataBean.getCardno())){
					fieldName=CommonValidate.findComponentInRoot("card").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the card number"));
					valid=false;
				}
			}else if(financeDataBean.getPaymentmode().equalsIgnoreCase("cheque")){
				if(StringUtils.isEmpty(financeDataBean.getBankname())){
					fieldName=CommonValidate.findComponentInRoot("bankna").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the bank name"));
					valid=false;
				}if(StringUtils.isEmpty(financeDataBean.getChequeno())){
					fieldName=CommonValidate.findComponentInRoot("chequeno").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the cheque number"));
					valid=false;
				}if(financeDataBean.getChequedate()==null){
					fieldName=CommonValidate.findComponentInRoot("chequedate").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the cheque date"));
					valid=false;
				}
			}else if(financeDataBean.getPaymentmode().equalsIgnoreCase("transfer")){
				if(StringUtils.isEmpty(financeDataBean.getBankname())){
					fieldName=CommonValidate.findComponentInRoot("bankname").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the bank name"));
					valid=false;
				}
				if(StringUtils.isEmpty(financeDataBean.getAccountno())){
					fieldName=CommonValidate.findComponentInRoot("accno").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the account number"));
					valid=false;
				}
			}
			if(StringUtils.isEmpty(financeDataBean.getPaymenttype())){
				fieldName=CommonValidate.findComponentInRoot("type").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please select the payement type"));
				valid=false;
			}if(StringUtils.isEmpty(financeDataBean.getAmount())){
				fieldName=CommonValidate.findComponentInRoot("amount").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
				valid=false;
			}
			if(StringUtils.isEmpty(financeDataBean.getName())){
				fieldName=CommonValidate.findComponentInRoot("name").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the name"));
				valid=false;
			}
			if(StringUtils.isEmpty(financeDataBean.getTransactionCategory())){
				fieldName=CommonValidate.findComponentInRoot("ctype").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please select the category"));
				valid=false;
			}
			if(financeDataBean.getTransactionCategory().equals("waste")){
			    if(StringUtils.isEmpty(financeDataBean.getWasteQuantity())){
			     fieldName=CommonValidate.findComponentInRoot("quan").getClientId(fc);
			     fc.addMessage(fieldName, new FacesMessage("please enter the quantity"));
			     valid=false;
			    }
			   }
			return valid;
		}
		public void tcategoryValueChange(ValueChangeEvent vc){
			try{
				if(vc.getNewValue().toString().equalsIgnoreCase("waste"))
					setWastequanFlag(true);
				else
					setWastequanFlag(false);
				}catch(Exception e){
				
			}
		}
		public String view(){
			try{
				if(personID!=null && clientID!=null){
					bo.viewtransaction(personID,clientID,financeDataBean);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return "";
		}
		public String edit(){
			try{
				if(personID!=null && clientID!=null){
					bo.viewtransaction(personID,clientID,financeDataBean);
					nameList=bo.gettransactioncategory(personID,clientID);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return "";
		}
		public String updatetransaction(){
			try{
				if(personID!=null && clientID!=null){
					if(editvalidation(true)){
						String status=bo.updatetransaction(personID,clientID,financeDataBean);
						if(status.equalsIgnoreCase("Success")){
							RequestContext.getCurrentInstance().execute("PF('edit').hide()");	
							RequestContext.getCurrentInstance().execute("PF('update').show()");
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return "";
		}
		
		private boolean editvalidation(boolean valid) {
			String fieldName;
			FacesContext fc=FacesContext.getCurrentInstance();
			 if(financeDataBean.getPaymentmode().equalsIgnoreCase("card")){
				if(StringUtils.isEmpty(financeDataBean.getBankname())){
					fieldName=CommonValidate.findComponentInRoot("bank").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the bank name"));
					valid=false;
				}
				if(StringUtils.isEmpty(financeDataBean.getCardno())){
					fieldName=CommonValidate.findComponentInRoot("card").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the card number"));
					valid=false;
				}
			}else if(financeDataBean.getPaymentmode().equalsIgnoreCase("cheque")){
				if(StringUtils.isEmpty(financeDataBean.getBankname())){
					fieldName=CommonValidate.findComponentInRoot("bankna").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the bank name"));
					valid=false;
				}if(StringUtils.isEmpty(financeDataBean.getChequeno())){
					fieldName=CommonValidate.findComponentInRoot("chequeno").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the cheque number"));
					valid=false;
				}if(financeDataBean.getChequedate()==null){
					fieldName=CommonValidate.findComponentInRoot("chequedate").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the cheque date"));
					valid=false;
				}
			}else if(financeDataBean.getPaymentmode().equalsIgnoreCase("transfer")){
				if(StringUtils.isEmpty(financeDataBean.getBankname())){
					fieldName=CommonValidate.findComponentInRoot("bankname").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the bank name"));
					valid=false;
				}
				if(StringUtils.isEmpty(financeDataBean.getAccountno())){
					fieldName=CommonValidate.findComponentInRoot("accno").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the account number"));
					valid=false;
				}
			}
			if(StringUtils.isEmpty(financeDataBean.getAmount())){
				fieldName=CommonValidate.findComponentInRoot("amount").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
				valid=false;
			}
			if(StringUtils.isEmpty(financeDataBean.getTransactionCategory())){
				fieldName=CommonValidate.findComponentInRoot("ctype").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please select the category"));
				valid=false;
			}
			if(StringUtils.isEmpty(financeDataBean.getWasteQuantity())){
				fieldName=CommonValidate.findComponentInRoot("quan").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the quantity"));
				valid=false;
			}
			return valid;
		}
		public void transactiondelete(){
			try{
				if(personID!=null && clientID!=null){
					bo.deletetransaction(personID,clientID,financeDataBean);
					RequestContext.getCurrentInstance().execute("PF('delete').show()");	
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public String waste(){
			bo.transactionwaste(personID,financeDataBean);
			return "transactionWaste";
		}
		
		public String finance() {
			bo.reportfinance(personID,financeDataBean);
			return"reportFinance";
		}
		
	//SAVING//
	public String savingPage(){
		try{
			domainList=new ArrayList<FinanceDataBean>();
			domainList=bo.getSaving(personID,clientID);
		}catch(Exception e){
		}
		return "financeSaving";
	}
}
