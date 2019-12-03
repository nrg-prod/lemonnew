package com.nrg.lemon.managedBean;


import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.lemon.bo.LemonBo;
import com.nrg.lemon.domain.CustomerDatabean;
import com.nrg.lemon.shared.Customer;
import com.nrg.lemon.util.CommonValidate;



@ManagedBean(name="customerRegistrationMB")
public class CustomerRegistrationMB {
	ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	LemonBo bo=(LemonBo)ctx.getBean("bo");
	String personID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PersonID");
	String clientID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ClientID");
	CustomerDatabean customerDatabean=new CustomerDatabean();
	List<CustomerDatabean> cus1 = new ArrayList<CustomerDatabean>();
	private List<Customer> reglist=null;

	public CustomerDatabean getCustomerDatabean() {
		return customerDatabean;
	}

	public void setCustomerDatabean(CustomerDatabean customerDatabean) {
		this.customerDatabean = customerDatabean;
	}
	
	public List<CustomerDatabean> getCus1() {
		return cus1;
	}

	public void setCus1(List<CustomerDatabean> cus1) {
		this.cus1 = cus1;
	}


	public List<Customer> getReglist() {
		return reglist;
	}

	public void setReglist(List<Customer> reglist) {
		this.reglist = reglist;
	}
	
	public String customerRegister(){
		customerDatabean.setCustomername("");
		customerDatabean.setDate(null);
		customerDatabean.setCity("");
		customerDatabean.setCountry("");
		customerDatabean.setEmail("");
		customerDatabean.setShippingaddress("");
		customerDatabean.setState("");
		customerDatabean.setCategoryname("");
		customerDatabean.setNote("");
		customerDatabean.setTaxnumber("");
		customerDatabean.setPhonenumber("");
		return "";
	}
	public void reset(){
		customerDatabean.setCustomername("");
		customerDatabean.setDate(null);
		customerDatabean.setCity("");
		customerDatabean.setCountry("");
		customerDatabean.setEmail("");
		customerDatabean.setShippingaddress("");
		customerDatabean.setState("");
	    customerDatabean.setCategoryname("");
		customerDatabean.setNote("");
		customerDatabean.setPhonenumber("");
		customerDatabean.setTaxnumber("");
		
	}
	public String register(){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		RequestContext rag=RequestContext.getCurrentInstance();
		try{
			if(customerValidation(true)){
				String status=bo.register(personID,clientID,customerDatabean);
				if(status.equalsIgnoreCase("success")){
					rag.execute("PF('registration').hide();");
					rag.execute("PF('customersuccess').show();");
					customerDatabean.setCustomername("");
					customerDatabean.setDate(null);
					customerDatabean.setCity("");
					customerDatabean.setCountry("");
					customerDatabean.setEmail("");
					customerDatabean.setPhonenumber("");
					customerDatabean.setPhonenumber2("");
					customerDatabean.setShippingaddress("");
					customerDatabean.setState("");
					customerDatabean.setCategoryname("");
					customerDatabean.setNote("");
					customerDatabean.setTaxnumber("");
					}else if(status.equalsIgnoreCase("Exists")){
						fieldName=CommonValidate.findComponentInRoot("ename").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("this name already exists"));
					}
				}
			}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	private boolean customerValidation(boolean valid) {
		valid=true;
		String name;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
			if(StringUtils.isEmpty(customerDatabean.getCustomername())){
				fieldName=CommonValidate.findComponentInRoot("ename").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the customer name"));
				valid=false;
			}
			if(StringUtils.isEmpty(customerDatabean.getCity())){
				fieldName=CommonValidate.findComponentInRoot("city1").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the city name"));
				valid=false;
			}
			if(StringUtils.isEmpty(customerDatabean.getCountry())){
				fieldName=CommonValidate.findComponentInRoot("country").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the country name"));
				valid=false;
			}
			if(StringUtils.isEmpty(customerDatabean.getCategoryname())){
				fieldName=CommonValidate.findComponentInRoot("country").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please select the gender"));
				valid=false;
			}
			if(StringUtils.isEmpty(customerDatabean.getShippingaddress())){
				fieldName=CommonValidate.findComponentInRoot("address1").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the SHIPPING ADDRESS"));
				valid=false;
			}
			/*if(StringUtils.isEmpty(customerDatabean.getEmail())){
				fieldName=CommonValidate.findComponentInRoot("email1").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the email address"));
				valid=false;
			}else if(!StringUtils.isEmpty(customerDatabean.getEmail())){
				if(!CommonValidate.validateEmail(customerDatabean.getEmail())){
					fieldName=CommonValidate.findComponentInRoot("email1").getClientId();
					fc.addMessage(fieldName, new FacesMessage("please enter the valid email address"));
					valid=false;
				}
			}
			if(customerDatabean.getDate()==null){
				fieldName=CommonValidate.findComponentInRoot("date1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the DOB"));
				valid=false;
			}
			if(StringUtils.isEmpty(customerDatabean.getState())){
				fieldName=CommonValidate.findComponentInRoot("state1").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the state name"));
				valid=false;
			}*/
			if(StringUtils.isEmpty(customerDatabean.getPhonenumber())){
				fieldName=CommonValidate.findComponentInRoot("phone").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the phone number"));
				valid=false;
			}
			else if(!StringUtils.isEmpty(customerDatabean.getPhonenumber())){
				if(!CommonValidate.validateNumber(customerDatabean.getPhonenumber())){
					name=CommonValidate.findComponentInRoot("phone").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Enter The Valid phone Number"));
					valid=false;
				}
			}
			if(StringUtils.isEmpty(customerDatabean.getCategoryname())){
				name=CommonValidate.findComponentInRoot("category").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please choose the gender"));
				valid=false;
			}
			return valid;
			}
		
		
	

	public String customerView()
	{
		cus1=bo.customerView();
		return "customerRegistration";
	}
 public String  cusView(){
	 System.out.println("cusview method calling");
	  try
	  {
		 reglist=new ArrayList<Customer>();
		 reglist=bo.editcustomer(personID,clientID,customerDatabean);
	   if(reglist.size()>0){
			customerDatabean.setCustomername(reglist.get(0).getCustomerName());
			customerDatabean.setCity(reglist.get(0).getCity());
			customerDatabean.setCountry(reglist.get(0).getCountry());
			customerDatabean.setShippingaddress(reglist.get(0).getShippingAddress());
			customerDatabean.setEmail(reglist.get(0).getEmail());
			customerDatabean.setDate(reglist.get(0).getDate());	
			customerDatabean.setState(reglist.get(0).getState());
			customerDatabean.setPhonenumber(reglist.get(0).getPhoneNumber());
			customerDatabean.setPhonenumber2(reglist.get(0).getPhoneNumber2());
			customerDatabean.setTaxnumber(reglist.get(0).getTaxNumber());
			customerDatabean.setNote(reglist.get(0).getNote());
			customerDatabean.setCategoryname(reglist.get(0).getCategoryName());
	 }     
	  }
	  catch(Exception e)
	  {
	   e.printStackTrace();
	  }
	  return "";
	 }
	
	 public String updateCustomer(){
		 String status="Fail";
		 if(personID !=null && clientID!=null ){
		 try{
			 if(validation(true)){
			status=bo.updatecustomer(personID,clientID,customerDatabean);
			System.out.println("status"+status);
			if(status.equalsIgnoreCase("Success")){
				RequestContext.getCurrentInstance().execute("PF('edit').hide();");
				RequestContext.getCurrentInstance().execute("PF('edit1').show();");
			}
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }
		 }
		 return "";
	 }
	 
	 public void clear(){
			RequestContext.getCurrentInstance().closeDialog("customerEdit");
	 }
	 
	 public String cusDelete(){
		 System.out.println("cusdelete method calling");
		 if(personID !=null && clientID!=null ){
			 try{
				 String status=bo.cusDelete(personID,clientID,customerDatabean);
				 if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('delete').show()"); 
				 }
			 }catch(Exception e){
				e.printStackTrace();
			 }
		 }
		return "";		 
	 }
	 
	 private boolean validation(boolean valid) {
			String fieldName;
			valid=true;
			FacesContext fc=FacesContext.getCurrentInstance();
			if(StringUtils.isEmpty(customerDatabean.getCity())){
				fieldName=CommonValidate.findComponentInRoot("ecity").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the city"));
				valid=false;
			}
			if(customerDatabean.getCountry().equals(" ")){
			    fieldName=CommonValidate.findComponentInRoot("ecountry").getClientId(fc);
			    fc.addMessage(fieldName, new FacesMessage("Choose the country"));
			   valid=false;
			  }  
			if(customerDatabean.getCategoryname().equals(" ")){
				fieldName=CommonValidate.findComponentInRoot("ecategory").getClientId(fc);
			    fc.addMessage(fieldName, new FacesMessage("Choose the category name "));
			   valid=false;
			  }  
			if(StringUtils.isEmpty(customerDatabean.getShippingaddress())){
				fieldName=CommonValidate.findComponentInRoot("eaddress").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the shipping address"));
				valid=false;
			}
			if(StringUtils.isEmpty(customerDatabean.getEmail())){
				fieldName=CommonValidate.findComponentInRoot("eemail").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the email"));
				valid=false;
			}else if(!StringUtils.isEmpty(customerDatabean.getEmail())){
				if(!CommonValidate.validateEmail(customerDatabean.getEmail())){
					fieldName=CommonValidate.findComponentInRoot("eemail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the valid email"));
					valid=false;
				}
			}
			if(customerDatabean.getDate()==null){
			    fieldName=CommonValidate.findComponentInRoot("edate").getClientId(fc);
			    fc.addMessage(fieldName, new FacesMessage("Choose the date"));
			   valid=false;
			  }  
			if(StringUtils.isEmpty(customerDatabean.getState())){
				fieldName=CommonValidate.findComponentInRoot("estate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the state"));
				valid=false;
			}
			if(StringUtils.isEmpty(customerDatabean.getPhonenumber())){
				fieldName=CommonValidate.findComponentInRoot("ephone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the phone number"));
				valid=false;
			}else if(!StringUtils.isEmpty(customerDatabean.getPhonenumber())){
				if(!CommonValidate.validateNumber(customerDatabean.getPhonenumber())){
					fieldName=CommonValidate.findComponentInRoot("ephone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the valid phone number"));
					valid=false;
				}
			}
			return valid;
		}
	
}
	