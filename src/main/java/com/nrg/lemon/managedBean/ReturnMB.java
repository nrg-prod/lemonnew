package com.nrg.lemon.managedBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.lemon.bo.LemonBo;
import com.nrg.lemon.domain.ReturnDataBean;
import com.nrg.lemon.domain.StockDataBean;
import com.nrg.lemon.util.CommonValidate;


@ManagedBean(name="returnMB")
public class ReturnMB {
	ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	LemonBo bo=(LemonBo)ctx.getBean("bo");
	String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PersonID");
	ReturnDataBean returnDataBean=new ReturnDataBean();
	private List<StockDataBean> itemnamelist=null;

	public List<StockDataBean> getItemnamelist() {
		return itemnamelist;
	}

	public void setItemnamelist(List<StockDataBean> itemnamelist) {
		this.itemnamelist = itemnamelist;
	}

	public ReturnDataBean getReturnDataBean() {
		return returnDataBean;
	}

	public void setReturnDataBean(ReturnDataBean returnDataBean) {
		this.returnDataBean = returnDataBean;
	}

	public String supplyPage(){
		System.out.println("okkkk");
		try{
			bo.action(personID,returnDataBean);			
		}catch(Exception e){
			e.printStackTrace();
		}		
		return "supplyReturn";
	}
	public String supplyviewpage(){
		System.out.println(">>>>>>>>>>>>eeeeeeeww");
		bo.supplyview(personID,returnDataBean);
		return "";
		
	}
	
	public String supplyPage1(){
		System.out.println("okkkk");
		try{
			returnDataBean.setFlag(false);
			returnDataBean.setItemname("");
			returnDataBean.setDate(new Date());
			bo.getreturnsupply(personID,returnDataBean);
		}catch(Exception e){
			e.printStackTrace();
		}		
		return "";
	}

	public void categorySelect(ValueChangeEvent e){
		itemnamelist=new ArrayList<StockDataBean>();
		String category=e.getNewValue().toString();
		try{			
			returnDataBean.setItemname(category);
			bo.datatable(personID,returnDataBean);			
			System.out.println("list size"+returnDataBean.getReturnDataBeanList().size());
			if(!category.equals("")){
				returnDataBean.setFlag(true);
			}else {
				returnDataBean.setFlag(false);
			}		
		}catch(Exception v){
			v.printStackTrace();	
		}
	}
	
	public void quantityChange(ValueChangeEvent v){
		System.out.println("quantity."+v.getNewValue().toString());		
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();		
		String quan=v.getNewValue().toString();
		BigDecimal price=BigDecimal.valueOf(0);
		try{
			String stockin=v.getComponent().getAttributes().get("stockin").toString();
			System.out.println("stock in"+stockin);
			String price1=v.getComponent().getAttributes().get("price").toString();
			String serialno=v.getComponent().getAttributes().get("serialno").toString();
			System.out.println("price"+price1);
			if (new BigDecimal(quan).compareTo(new BigDecimal(stockin))==1) {
				fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Only "+stockin+ " Quantity in Stock"));				
			}
			else {
				price=new BigDecimal(quan).multiply(new BigDecimal(price1));
				System.out.println("price"+price);
				ReturnDataBean net=new ReturnDataBean();
				net.setItemname(returnDataBean.getReturnDataBeanList().get(Integer.parseInt(serialno)-1).getItemname());
				net.setSerialno(serialno);
				net.setStockin(returnDataBean.getReturnDataBeanList().get(Integer.parseInt(serialno)-1).getStockin());
				net.setShoppname(returnDataBean.getReturnDataBeanList().get(Integer.parseInt(serialno)-1).getShoppname());
				net.setPrice(returnDataBean.getReturnDataBeanList().get(Integer.parseInt(serialno)-1).getPrice());
				net.setNetprice(String.valueOf(price));
				returnDataBean.getReturnDataBeanList().set(Integer.parseInt(serialno)-1,net);
				
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String submit() {
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();	
		boolean flag=true;		
		for (int i = 0; i < returnDataBean.getReturnDataBeanList().size(); i++) 
		{
			if (returnDataBean.getReturnDataBeanList().get(i).getQuantity().equals("")) {
				fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
				fc.addMessage(fieldName, new FacesMessage(" Enter the Quantity "));	
				flag=false;				
			}					
		}
		if (flag==true) {
			bo.save(personID,returnDataBean);
			RequestContext.getCurrentInstance().execute("PF('stockDialog').hide();");
			RequestContext.getCurrentInstance().execute("PF('confirm1').show();");						
		}
		return "";
	}
	public String updates(){
	System.out.println("...........ttttt");
	String fieldName;
	boolean valid=true;
	FacesContext fc=FacesContext.getCurrentInstance();	
	try{
		for (int i = 0; i < returnDataBean.getReturnDataBeanList().size(); i++) {
			if (returnDataBean.getReturnDataBeanList().get(i).getQuantity().equals("")) {
				fieldName=CommonValidate.findComponentInRoot("updatepanel").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Please Enter Quantity"));
				valid=false;
			}else if(!CommonValidate.validateNumber(returnDataBean.getReturnDataBeanList().get(i).getQuantity())){
				fieldName=CommonValidate.findComponentInRoot("updatepanel").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Quantity must be number"));
				valid=false;
			}
		}	
		if(valid==true){
			bo.update(personID,returnDataBean);
			RequestContext.getCurrentInstance().execute("PF('editdialog').hide();");
			RequestContext.getCurrentInstance().execute("PF('confirms').show();");
		}	
	}		    
	catch(Exception e){
		e.printStackTrace();
	}
	return"";
	}
	
	public void quantityChange1(ValueChangeEvent v){
		System.out.println("quantity."+v.getNewValue().toString());		
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();BigDecimal price=BigDecimal.valueOf(0);
		String quan=v.getNewValue().toString();BigDecimal totQuan=BigDecimal.valueOf(0);
		try{
			String itemname=v.getComponent().getAttributes().get("itemname").toString();
			String price1=v.getComponent().getAttributes().get("price").toString();
			returnDataBean.setItemname(itemname);
			bo.updates(personID, returnDataBean);
			System.out.println("stk in "+returnDataBean.getStockin()+" quan "+returnDataBean.getQuantity1());
			totQuan=new BigDecimal(returnDataBean.getStockin()).add(new BigDecimal(returnDataBean.getQuantity1()));
			if(new BigDecimal(quan).compareTo(totQuan)==1){
				fieldName=CommonValidate.findComponentInRoot("updatepanel").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Only "+totQuan+" quantity in stock"));
			}else{
				price=new BigDecimal(quan).multiply(new BigDecimal(price1));
				System.out.println("price"+price);
				ReturnDataBean net=new ReturnDataBean();
				net.setItemname(returnDataBean.getReturnDataBeanList().get(0).getItemname());
				net.setStockin(returnDataBean.getReturnDataBeanList().get(0).getStockin());
				net.setShoppname(returnDataBean.getReturnDataBeanList().get(0).getShoppname());
				net.setPrice(returnDataBean.getReturnDataBeanList().get(0).getPrice());
				net.setNetprice(String.valueOf(price));
				returnDataBean.getReturnDataBeanList().set(0,net);
			}
		}			
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String delete(){
		
		bo.updatedelete(personID,returnDataBean);
	
	return null;
	}
	
	public String returnreport(){
		returnDataBean.setSerialno("");		
		returnDataBean.setFlag(false);
		returnDataBean.setFlag1(false);
		
		
		return"returnRoll";
	}
	
	public void categoryValueChange(ValueChangeEvent c) {
		returnDataBean.setReturnDataBeanList(null);
		try{
			String str=c.getNewValue().toString();
			if (str.equalsIgnoreCase("supply")) {
				returnDataBean.setFlag(true);
				returnDataBean.setFlag1(false);
				
			}else if(str.equalsIgnoreCase("cmt")){
				returnDataBean.setFlag(false);
				returnDataBean.setFlag1(true);
			}			
			returnDataBean.setSerialno(str);
			returnDataBean.setItemname(str);			
			bo.supply(personID,returnDataBean);
		}catch(Exception e){
			e.printStackTrace();
		}		
	} 
	}

			
				
				
		
		
	