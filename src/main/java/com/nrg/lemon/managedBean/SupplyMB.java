package com.nrg.lemon.managedBean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.lemon.bo.LemonBo;
import com.nrg.lemon.domain.SupplyDataBean;
import com.nrg.lemon.util.CommonValidate;

@ManagedBean(name="supplyMB")
@RequestScoped
public class SupplyMB {
	
ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
LemonBo bo=(LemonBo)ctx.getBean("bo");
private static Logger logger = Logger.getLogger(SupplyMB.class);
String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PersonID");
String clientID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ClientID");
String itemStatus="";
SupplyDataBean supplyDataBean=new SupplyDataBean();
List<SupplyDataBean> supplierList=new ArrayList<SupplyDataBean>();
List<SupplyDataBean> purchaseFabricList=new ArrayList<SupplyDataBean>();
List<String> supplierNameList=new ArrayList<String>();
List<String> itemList=new ArrayList<String>();
List<String> vendorList=new ArrayList<String>();
public String weightTotal;
public String quantityTotal;
public String totalAmount;
DecimalFormat myFormatter = new DecimalFormat("#,###");
public String itempurchaseFlag="none";
public String itemsalesFlag="none";
public String validate;
public String itemQuantity3;
private boolean fabricDataTableFlag=false;
private boolean supplyDataTableFlag=false;
private boolean invoiceDataTableFlag=false;


public boolean isInvoiceDataTableFlag() {
	return invoiceDataTableFlag;
}

public void setInvoiceDataTableFlag(boolean invoiceDataTableFlag) {
	this.invoiceDataTableFlag = invoiceDataTableFlag;
}

public boolean isFabricDataTableFlag() {
	return fabricDataTableFlag;
}

public void setFabricDataTableFlag(boolean fabricDataTableFlag) {
	this.fabricDataTableFlag = fabricDataTableFlag;
}

public boolean isSupplyDataTableFlag() {
	return supplyDataTableFlag;
}

public void setSupplyDataTableFlag(boolean supplyDataTableFlag) {
	this.supplyDataTableFlag = supplyDataTableFlag;
}
public String getItemQuantity3() {
	return itemQuantity3;
}

public void setItemQuantity3(String itemQuantity3) {
	this.itemQuantity3 = itemQuantity3;
}

public String getValidate() {
	return validate;
}

public void setValidate(String validate) {
	this.validate = validate;
}

public List<String> getVendorList() {
	return vendorList;
}

public void setVendorList(List<String> vendorList) {
	this.vendorList = vendorList;
}

public String getItempurchaseFlag() {
	return itempurchaseFlag;
}

public void setItempurchaseFlag(String itempurchaseFlag) {
	this.itempurchaseFlag = itempurchaseFlag;
}

public String getItemsalesFlag() {
	return itemsalesFlag;
}

public void setItemsalesFlag(String itemsalesFlag) {
	this.itemsalesFlag = itemsalesFlag;
}

public List<String> getItemList() {
	return itemList;
}

public void setItemList(List<String> itemList) {
	this.itemList = itemList;
}

public List<SupplyDataBean> getPurchaseFabricList() {
	return purchaseFabricList;
}

public void setPurchaseFabricList(List<SupplyDataBean> purchaseFabricList) {
	this.purchaseFabricList = purchaseFabricList;
}

public String getTotalAmount() {
	return totalAmount;
}

public void setTotalAmount(String totalAmount) {
	this.totalAmount = totalAmount;
}
public String getQuantityTotal() {
	return quantityTotal;
}

public void setQuantityTotal(String quantityTotal) {
	this.quantityTotal = quantityTotal;
}

public String getWeightTotal() {
	return weightTotal;
}

public void setWeightTotal(String weightTotal) {
	this.weightTotal = weightTotal;
}

public List<String> getSupplierNameList() {
	return supplierNameList;
}

public void setSupplierNameList(List<String> supplierNameList) {
	this.supplierNameList = supplierNameList;
}

public List<SupplyDataBean> getSupplierList() {
	return supplierList;
}

public void setSupplierList(List<SupplyDataBean> supplierList) {
	this.supplierList = supplierList;
}

public SupplyDataBean getSupplyDataBean() {
	return supplyDataBean;
}

public void setSupplyDataBean(SupplyDataBean supplyDataBean) {
	this.supplyDataBean = supplyDataBean;
}

//prema supplier module begin
public String supplierPage(){
	supplierList=bo.getsupplierlist(personID);
	return "supplierRegistration.xhtml";
}
public String addsuppliermethod(){
	supplyDataBean.setDivision("");
	supplyDataBean.setSupplierName("");
	supplyDataBean.setContactName("");
	supplyDataBean.setHpNumber1("");
	supplyDataBean.setHpNumber2("");
	supplyDataBean.setOfficeNumber1("");
	supplyDataBean.setOfficeNumber2("");
	return "";
}
public String addsupplier(){
	System.out.println("add supplier method calling");
	String fieldName;FacesContext fc=FacesContext.getCurrentInstance();
	try{
		if(addSupplyValidate(true)){
			if(personID != null && clientID !=null){
				String status=bo.insertsupplier(personID,clientID,supplyDataBean);
				if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('addsupplierDialog').hide();");
					RequestContext.getCurrentInstance().execute("PF('confirm').show();");
					addsuppliermethod();
				}else if(status.equalsIgnoreCase("Exist")){
					fieldName=CommonValidate.findComponentInRoot("hpnoid").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("This supplier already exist"));
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
	private boolean addSupplyValidate(boolean valid) {
	  valid=true;String fieldName;
	  FacesContext fc=FacesContext.getCurrentInstance();
	  if(StringUtils.isEmpty(supplyDataBean.getDivision())){
	   fieldName=CommonValidate.findComponentInRoot("divisionid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please select the division"));
	   valid=false;
	  }
	  if(StringUtils.isEmpty(supplyDataBean.getSupplierName())){
	   fieldName=CommonValidate.findComponentInRoot("suppliernameid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please enter the supplier name"));
	   valid=false;
	  }
	  if(StringUtils.isEmpty(supplyDataBean.getContactName())){
	   fieldName=CommonValidate.findComponentInRoot("contactnameid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please enter the contact name"));
	   valid=false;
	  }
	  if(StringUtils.isEmpty(supplyDataBean.getHpNumber1())){
	   fieldName=CommonValidate.findComponentInRoot("hpnoid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please enter the hp number1"));
	   valid=false;
	  }else if (!StringUtils.isEmpty(supplyDataBean.getHpNumber1())) {
			if (!CommonValidate.validateNumber(supplyDataBean.getHpNumber1())) {
					fieldName = CommonValidate.findComponentInRoot("hpnoid").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the valid hp No."));
					valid = false;
			}
	  }
	  if (!StringUtils.isEmpty(supplyDataBean.getHpNumber2())) {
			if (!CommonValidate.validateNumber(supplyDataBean.getHpNumber2())) {
					fieldName = CommonValidate.findComponentInRoot("hpnoid2").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the valid hp No 2."));
					valid = false;
			}
	  }
	  if(StringUtils.isEmpty(supplyDataBean.getOfficeNumber1())){
		   fieldName=CommonValidate.findComponentInRoot("offcnoid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the office number1"));
		   valid=false;
	  }else if (!StringUtils.isEmpty(supplyDataBean.getOfficeNumber1())) {
			if (!CommonValidate.validateNumber(supplyDataBean.getOfficeNumber1())) {
				fieldName = CommonValidate.findComponentInRoot("offcnoid").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the valid office No."));
				valid = false;
		}
	  }
	  if (!StringUtils.isEmpty(supplyDataBean.getOfficeNumber2())) {
			if (!CommonValidate.validateNumber(supplyDataBean.getOfficeNumber2())) {
				fieldName = CommonValidate.findComponentInRoot("offcnoid2").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the valid office No 2."));
				valid = false;
		}
	  }
	  return valid;
	}
public String editSupplier(){
	try{
		bo.editsupplier(personID,supplyDataBean);
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public String updateSupplier(){
	String status="Fail";String fieldName;
	  FacesContext fc=FacesContext.getCurrentInstance();
	try{
		if(editSupplyValidate(true)){
			if(personID != null && clientID !=null){
				status=bo.updatesupplier(personID,supplyDataBean);
				if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('editDialog').hide();");
					RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
				}else if(status.equalsIgnoreCase("Exist")){
					fieldName=CommonValidate.findComponentInRoot("ehpnoid").getClientId(fc);
				   	fc.addMessage(fieldName, new FacesMessage("this supplier already exist"));
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
private boolean editSupplyValidate(boolean valid) {
	  valid=true;String fieldName;
	  FacesContext fc=FacesContext.getCurrentInstance();
	  if(supplyDataBean.getDivision().equalsIgnoreCase("select")){
	   fieldName=CommonValidate.findComponentInRoot("edivisionid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please select the division"));
	   valid=false;
	  }
	  if(StringUtils.isEmpty(supplyDataBean.getContactName())){
	   fieldName=CommonValidate.findComponentInRoot("econtactnameid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please enter the contact name"));
	   valid=false;
	  }
	  if(StringUtils.isEmpty(supplyDataBean.getSupplierName())){
		   fieldName=CommonValidate.findComponentInRoot("esuppliernameid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the supplier name"));
		   valid=false;
	  }
	  if (!StringUtils.isEmpty(supplyDataBean.getHpNumber2())) {
			if (!CommonValidate.validateNumber(supplyDataBean.getHpNumber2())) {
					fieldName = CommonValidate.findComponentInRoot("ehpnoid2").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the valid hp No 2."));
					valid = false;
			}
	  }
	  if(StringUtils.isEmpty(supplyDataBean.getOfficeNumber1())){
		   fieldName=CommonValidate.findComponentInRoot("eoffcnoid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the office number1"));
		   valid=false;
	  }else if (!StringUtils.isEmpty(supplyDataBean.getOfficeNumber1())) {
			if (!CommonValidate.validateNumber(supplyDataBean.getOfficeNumber1())) {
				fieldName = CommonValidate.findComponentInRoot("eoffcnoid").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the valid office No."));
				valid = false;
		}
	  }
	  if (!StringUtils.isEmpty(supplyDataBean.getOfficeNumber2())) {
			if (!CommonValidate.validateNumber(supplyDataBean.getOfficeNumber2())) {
				fieldName = CommonValidate.findComponentInRoot("eoffcnoid2").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the valid office No 2."));
				valid = false;
		}
	  }
	  return valid;
	 }
public String deleteSupplier(){
	try{
		bo.deletesupplier(personID,supplyDataBean);
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
//prema supplier module end
//prema fabric module begin
public String fabricPage(){
	System.out.println("Inside fabricPage ");
	supplyDataBean.setFromDate(null);
	supplyDataBean.setToDate(null);
	supplyDataBean.setSupplierName(null);
	supplyDataBean.setFabricDay("");
	purchaseFabricList=bo.getfabricpurchaseList(personID);
	supplierNameList=bo.getsuppliername(personID);
	return "fabricRegistration";
}
public String addPurchaseFabric(){
	supplierList=new ArrayList<SupplyDataBean>();
	supplyDataBean.setFabricPurchaseDate(null);
	supplyDataBean.setFabricInvoiceNumber("");
	supplyDataBean.setSupplierName("");
	supplyDataBean.setFabricName("");
	supplyDataBean.setFabricColour("");
	supplyDataBean.setFabricPrice("");
	supplyDataBean.setFabricWeight("");
	supplyDataBean.setFabricQuantity("");
	supplyDataBean.setFabricTotal("");
	setWeightTotal("");
	setQuantityTotal("");
	setTotalAmount("");
	setValidate("");
	String str="fabric";
	supplierNameList=bo.getsuppliernamelist(personID,str);
	for (int i = 1; i < 7; i++) {
		supplyDataBean=new SupplyDataBean();
		supplyDataBean.setSerialNo(String.valueOf(i));
		supplierList.add(supplyDataBean);
	}
	return "";
}
public void dateValueChange(SelectEvent event){
Date purdate = (Date) event.getObject();
supplyDataBean.setFabricPurchaseDate(purdate);
if(supplyDataBean.getFabricPurchaseDate()!=null){
	supplyDataBean.setFabricInvoiceNumber(bo.purchaseFabricInvoice(personID,supplyDataBean));
}
}
public String addButton(){
		SupplyDataBean supply= new SupplyDataBean();
		supply.setSerialNo(String.valueOf(supplierList.size()+1));
		supplierList.add(supply);
	return "";
}
public String addItemButton(){
	SupplyDataBean supply= new SupplyDataBean();
	supply.setSerialNo(String.valueOf(supplierList.size()+1));
	supply.setItemStatus(supplierList.get(0).getItemStatus());
	supplierList.add(supply);
return "";
}
public void weightValueChange(ValueChangeEvent vc){
	String serialNo="";String fabPrice="";
	try{
		serialNo=vc.getComponent().getAttributes().get("serial").toString();
		try{
			fabPrice=vc.getComponent().getAttributes().get("fabprice").toString();
		}catch(Exception e){
			fabPrice="0";
		}
		SupplyDataBean supply= new SupplyDataBean();
		supply.setFabricWeight(vc.getNewValue().toString());
		supply.setSerialNo(serialNo);
		supply.setFabricPrice(fabPrice);
		supply.setFabricColour(supplierList.get(Integer.parseInt(serialNo) - 1).getFabricColour());
		supply.setFabricQuantity(supplierList.get(Integer.parseInt(serialNo) - 1).getFabricQuantity());
		BigDecimal total=(new BigDecimal(fabPrice).multiply(new BigDecimal(supply.getFabricWeight())));
		supply.setFabricTotal(total.toString());
		supplierList.set(Integer.parseInt(serialNo) -1, supply);
		BigDecimal temp = BigDecimal.valueOf(0);
		BigDecimal temp1 = BigDecimal.valueOf(0);
		for (int i = 0; i < supplierList.size(); i++) {
			try {
				if (!supplierList.get(i).getFabricTotal().equals("")) {
					temp1 = temp1.add(new BigDecimal(supplierList.get(i).getFabricTotal()));
				}
				if(!supply.getFabricWeight().equalsIgnoreCase("")){
					temp = temp.add(new BigDecimal(supplierList.get(i).getFabricWeight()));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		System.out.println("total---"+temp1);System.out.println("total111---"+temp);
		setWeightTotal(temp.toString());
		String output = myFormatter.format(temp1);
		setTotalAmount(output);
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void priceValueChange(ValueChangeEvent vc){
	String serialNo="";String fabWeight="";BigDecimal total=BigDecimal.valueOf(0);
	try{
		serialNo=vc.getComponent().getAttributes().get("serial").toString();
		fabWeight=vc.getComponent().getAttributes().get("fabweight").toString();
		SupplyDataBean supply= new SupplyDataBean();
		supply.setFabricPrice(vc.getNewValue().toString());
		supply.setSerialNo(serialNo);
		supply.setFabricWeight(fabWeight);
		supply.setFabricColour(supplierList.get(Integer.parseInt(serialNo) - 1).getFabricColour());
		supply.setFabricQuantity(supplierList.get(Integer.parseInt(serialNo) - 1).getFabricQuantity());
		if(fabWeight.equalsIgnoreCase(""))
			total=BigDecimal.valueOf(0);
		else 
			total=new BigDecimal(fabWeight).multiply(new BigDecimal(supply.getFabricPrice()));
		supply.setFabricTotal(total.toString());
		supplierList.set(Integer.parseInt(serialNo) -1, supply);
		BigDecimal temp1 = BigDecimal.valueOf(0);
		for (int i = 0; i < supplierList.size(); i++) {
			try {
				if (!supplierList.get(i).getFabricTotal().equals("")) {
					temp1 = temp1.add(new BigDecimal(supplierList.get(i).getFabricTotal()));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		setTotalAmount(temp1.toString());
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void quantityValueChange(ValueChangeEvent vc){
	String serialNo="";
	try{
		serialNo=vc.getComponent().getAttributes().get("serial").toString();
		SupplyDataBean supply=new SupplyDataBean();
		supply.setFabricColour(supplierList.get(Integer.parseInt(serialNo) - 1).getFabricColour());
		supply.setFabricPrice(supplierList.get(Integer.parseInt(serialNo) - 1).getFabricPrice());
		supply.setFabricWeight(supplierList.get(Integer.parseInt(serialNo) - 1).getFabricWeight());
		supply.setFabricTotal(supplierList.get(Integer.parseInt(serialNo) - 1).getFabricTotal());
		supply.setFabricQuantity(vc.getNewValue().toString());
		supply.setSerialNo(serialNo);
		supplierList.set(Integer.parseInt(serialNo) -1, supply);
		System.out.println("quantity"+vc.getNewValue().toString());
		BigDecimal temp1 = BigDecimal.valueOf(0);
		for (int i = 0; i < supplierList.size(); i++) {
			try {
				if (!supplierList.get(i).getFabricQuantity().equals("")) {
					temp1 = temp1.add(new BigDecimal(supplierList.get(i).getFabricQuantity()));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		setQuantityTotal(temp1.toString());
	}catch(Exception e){
		e.printStackTrace();
	}
}
public String savePurchaseFabric(){
	try{
		setValidate("");
		supplyDataBean.setFabricList(supplierList);
		supplyDataBean.setQuantityTotal(getQuantityTotal());
		supplyDataBean.setWeightTotal(getWeightTotal());
		supplyDataBean.setTotalAmount(getTotalAmount());
		if(personID != null){
			if(fabricorderValidation(true)){
			String status=bo.savepurchasefabric(personID,supplyDataBean);
			if(status.equalsIgnoreCase("Success")){
				RequestContext.getCurrentInstance().execute("PF('purchasefabricDialog').hide();");
				RequestContext.getCurrentInstance().execute("PF('confirm').show();");
			}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
private boolean fabricorderValidation(boolean valid) {
	valid=true;
	String name="";
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		if(supplyDataBean.getFabricPurchaseDate()==null){
			name=CommonValidate.findComponentInRoot("date").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please select the date"));
			valid=false;
		}if(StringUtils.isEmpty(supplyDataBean.getSupplierName())){
			name=CommonValidate.findComponentInRoot("suppliername").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please enter the supplier name"));
			valid=false;
		}if(StringUtils.isEmpty(supplyDataBean.getFabricName())){
			name=CommonValidate.findComponentInRoot("fabric").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please enter the fabric name"));
			valid=false;
		}
		int count = 0;
		for (int i = 0; i < supplierList.size(); i++) {
			try{
				if(supplierList.get(i).getFabricColour().equalsIgnoreCase("")){
					name=CommonValidate.findComponentInRoot("fabricdatatable1").getClientId(fc);
					fc.addMessage(name, new FacesMessage("please enter the all fields"));
					valid=false;
				}else{
					if(supplierList.get(i).getFabricPrice() == null){
						name=CommonValidate.findComponentInRoot("fabricdatatable1").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the price"));
						valid=false;
					}else if(supplierList.get(i).getFabricWeight() == null){
						name=CommonValidate.findComponentInRoot("fabricdatatable1").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the weight"));
						valid=false;
					}else if(supplierList.get(i).getFabricQuantity() == null){
						name=CommonValidate.findComponentInRoot("fabricdatatable1").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the quantity"));
						valid=false;
					}else if(supplierList.get(i).getFabricQuantity() != null){
						if (!supplierList.get(i).getFabricQuantity().matches("^\\d+(\\.\\d+)*$")) {
							name=CommonValidate.findComponentInRoot("fabricdatatable1").getClientId(fc);
							fc.addMessage(name, new FacesMessage("please enter the valid quantity"));
							valid=false;
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				count++;
			}
		}
		if (count == supplierList.size()) {
			name=CommonValidate.findComponentInRoot("fabricdatatable1").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please enter the all fields"));
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return valid;
}

public String viewPurchaseFabric(){
	try{
		if(personID != null){
			bo.viewpurchasefabric(personID,supplyDataBean);
			setSupplierList(supplyDataBean.getFabricList());
			setWeightTotal(supplyDataBean.getWeightTotal());
			setQuantityTotal(supplyDataBean.getQuantityTotal());
			String output=myFormatter.format(Integer.parseInt(supplyDataBean.getTotalAmount()));
			setTotalAmount(output);
			if(supplyDataBean.getInvoiceStatus().equals("insert")){
				System.out.println("inside if"+supplyDataBean.getInvoiceStatus());
				bo.fabricgenerateInvoice(personID,supplyDataBean);
				purchaseFabricList=bo.getfabricpurchaseList(personID);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public String viewPurchaseFabric1(){
	try{
		if(personID != null){
			bo.viewpurchasefabric(personID,supplyDataBean);
			setSupplierList(supplyDataBean.getFabricList());
			setWeightTotal(supplyDataBean.getWeightTotal());
			setQuantityTotal(supplyDataBean.getQuantityTotal());
			String output=myFormatter.format(Integer.parseInt(supplyDataBean.getTotalAmount()));
			setTotalAmount(output);
			bo.fabricgenerateInvoice(personID,supplyDataBean);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public String editPurchaseFabric(){
	try{
		if(personID != null){
			bo.viewpurchasefabric(personID,supplyDataBean);
			System.out.println("list size"+supplyDataBean.getFabricList().size());
			setSupplierList(supplyDataBean.getFabricList());
			String str="fabric";
			supplierNameList=bo.getsuppliernamelist(personID, str);
			setWeightTotal(supplyDataBean.getWeightTotal());
			setQuantityTotal(supplyDataBean.getQuantityTotal());
			String output=myFormatter.format(Integer.parseInt(supplyDataBean.getTotalAmount()));
			setTotalAmount(output);
		}
	}catch(Exception e){
		
	}
	return "";
}
private boolean fabricorderValidation1(boolean valid) {
	valid=true;
	String name="";
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		if(StringUtils.isEmpty(supplyDataBean.getSupplierName())){
			name=CommonValidate.findComponentInRoot("esuppliername").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please enter the supplier name"));
			valid=false;
		}if(StringUtils.isEmpty(supplyDataBean.getFabricName())){
			name=CommonValidate.findComponentInRoot("efabric").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please enter the fabric name"));
			valid=false;
		}
		for (int i = 0; i < supplierList.size(); i++) {
				if(supplierList.get(i).getFabricColour().equalsIgnoreCase("")){
					name=CommonValidate.findComponentInRoot("editfabricdatatable").getClientId(fc);
					fc.addMessage(name, new FacesMessage("please enter the colour"));
					valid=false;
				}
				else if(supplierList.get(i).getFabricPrice().equalsIgnoreCase("")){
						name=CommonValidate.findComponentInRoot("editfabricdatatable").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the price"));
						valid=false;
				}
				else if(supplierList.get(i).getFabricWeight().equalsIgnoreCase("")){
						name=CommonValidate.findComponentInRoot("editfabricdatatable").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the weight"));
						valid=false;
				}
				else if(supplierList.get(i).getFabricQuantity().equalsIgnoreCase("")){
						name=CommonValidate.findComponentInRoot("editfabricdatatable").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the quantity"));
						valid=false;
				}else if(!supplierList.get(i).getFabricQuantity().equalsIgnoreCase("")){
						if (!supplierList.get(i).getFabricQuantity().matches("^\\d+(\\.\\d+)*$")) {
							name=CommonValidate.findComponentInRoot("editfabricdatatable").getClientId(fc);
							fc.addMessage(name, new FacesMessage("please enter the valid quantity"));
							valid=false;
						}
					}
				}
	}catch(Exception e){
		e.printStackTrace();
	}
	return valid;
}
public String updatePurchaseFabric(){
	String status="Fail";
	try{
		if(personID != null){
			supplyDataBean.setWeightTotal(weightTotal);
			supplyDataBean.setQuantityTotal(quantityTotal);
			supplyDataBean.setTotalAmount(totalAmount);
			if(fabricorderValidation1(true)){
				status=bo.updatepurchasefabric(personID,supplyDataBean);
				if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('editDialog').hide();");
					RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
				}else if(status.equalsIgnoreCase("Exist")){
					RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
				}
				setWeightTotal(supplyDataBean.getWeightTotal());
				setQuantityTotal(supplyDataBean.getQuantityTotal());
				String output=myFormatter.format(Integer.parseInt(supplyDataBean.getTotalAmount()));
				setTotalAmount(output);
			}
		}
	}catch(Exception e){
		
	}
	return "";
}
public String deleteFabric(){
	try{
		if(personID!=null){
			bo.deletepurchasefabric(personID,supplyDataBean);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public void dayValueChange(ValueChangeEvent vc){
	purchaseFabricList=new ArrayList<SupplyDataBean>();
	try{
		String str=vc.getNewValue().toString();
		if(!str.equalsIgnoreCase("")){
			purchaseFabricList=bo.getfabricpurchaseList(personID,str);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void supplierValueChange(ValueChangeEvent vc){
	System.out.println("Inside supplierValueChange");
	purchaseFabricList=new ArrayList<SupplyDataBean>();
	try{
		String str=vc.getNewValue().toString();
		if(!str.equalsIgnoreCase("")){
			purchaseFabricList=bo.getsupplierfabricpurchaseList(personID,str);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void todateValueChange(ValueChangeEvent vc){
	purchaseFabricList=new ArrayList<SupplyDataBean>();
	String fieldName;
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		Date todate = (Date) vc.getNewValue();
		if(supplyDataBean.getFromDate()==null)
		{
			fieldName=CommonValidate.findComponentInRoot("fdate").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please Choose From Date"));
		}
		else{
			if(todate != null){
				purchaseFabricList=bo.getdatefabricpurchaseList(personID,supplyDataBean.getFromDate(),todate);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

public void fromdateValueChange(ValueChangeEvent vc){
	purchaseFabricList=new ArrayList<SupplyDataBean>();
	String fieldName;
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		Date fromdate = (Date) vc.getNewValue();
		if(supplyDataBean.getToDate()==null)
		{
			/*fieldName=CommonValidate.findComponentInRoot("tdate").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please Choose To Date"));*/
		}
		else{
			if(fromdate != null){
				purchaseFabricList=bo.getdatefabricpurchaseList(personID,fromdate,supplyDataBean.getToDate());
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
//prema fabric module end
//prema chemistry module begin
public String chemistryPage(){
	supplyDataBean.setItemStatus("CHEMISTRY ITEM");
	supplyDataBean.setItemStatus2("");
	supplyDataBean.setItemStatus3("");
	purchaseFabricList=bo.getitemtableview(personID,supplyDataBean);
	return "chemistryRegistration";
}
public String chemistryitempage(){
	itemStatus="Chemistry Item";
	supplyDataBean.setItemStatus(itemStatus);
	supplierList=bo.getitems(personID,clientID,itemStatus);
	return "chemistryItemPage";
}
public String chemistryitempages(){
	itemStatus="Chemistry Item";
	supplierList=bo.getitems(personID,clientID,itemStatus);
	return "chemistryItemRegistration";
}
public String saveChemistryItem(){
	String fieldName;FacesContext fc=FacesContext.getCurrentInstance();
	try{
		if(addItemValidate(true)){
			if(personID != null && clientID !=null){
				supplyDataBean.setItemStatus(supplyDataBean.getItemStatus());
				String status=bo.insertitems(personID,clientID,supplyDataBean);
				if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('itemDialog').hide();");
					RequestContext.getCurrentInstance().execute("PF('confirm').show();");
					addsuppliermethod();
				}else if(status.equalsIgnoreCase("Exist")){
					fieldName=CommonValidate.findComponentInRoot("itemnameid").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("This item name already exist"));
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
private boolean addItemValidate(boolean valid) {
	  valid=true;String fieldName;
	  FacesContext fc=FacesContext.getCurrentInstance();
	  if(StringUtils.isEmpty(supplyDataBean.getItemName())){
	   fieldName=CommonValidate.findComponentInRoot("itemnameid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please enter the item name"));
	   valid=false;
	  }
	  if(StringUtils.isEmpty(supplyDataBean.getItemBuyPrice())){
	   fieldName=CommonValidate.findComponentInRoot("buypriceid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please enter the buy price"));
	   valid=false;
	  }
	  if(StringUtils.isEmpty(supplyDataBean.getItemSellPrice())){
	   fieldName=CommonValidate.findComponentInRoot("sellpriceid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please enter the sell price"));
	   valid=false;
	  }
	  return valid;
}
private boolean editItemValidate(boolean valid) {
	  valid=true;String fieldName;
	  FacesContext fc=FacesContext.getCurrentInstance();
	  if(StringUtils.isEmpty(supplyDataBean.getItemBuyPrice())){
	   fieldName=CommonValidate.findComponentInRoot("ebuypriceid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please enter the buy price"));
	   valid=false;
	  }
	  if(StringUtils.isEmpty(supplyDataBean.getItemSellPrice())){
	   fieldName=CommonValidate.findComponentInRoot("esellpriceid").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("please enter the sell price"));
	   valid=false;
	  }
	  return valid;
}
public String viewChemistryItem(){
	try{
		bo.viewitems(personID,clientID,supplyDataBean);
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public String updateChemistryItem(){
	String status="Fail";
	try{
		if(editItemValidate(true)){
			if(personID!=null && clientID !=null){
				status=bo.updateitems(personID,supplyDataBean);
				if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('editDialog').hide();");
					RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public String deleteChemistry(){
	try{
		bo.deleteitems(personID,supplyDataBean);
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public String addpurChemistry(){
	supplyDataBean.setSupplierName("");
	supplyDataBean.setItemName("");
	setTotalAmount("");
	setQuantityTotal("");
	supplierList=new ArrayList<SupplyDataBean>();
	Date d = new Date();
	supplyDataBean.setTodayDate(d);
	supplyDataBean.setItemStatus("CHEMISTRY ITEM");
	supplyDataBean.setStatus2("PURCHASE");
	supplyDataBean.setPaymentStatus("UNPAID");
	supplyDataBean.setStockStatus("NOTADDED TO STOCK");
	String str="chemistry";
	supplierNameList=bo.getsuppliernamelist(personID,str);
	itemList=bo.getitemNameList(personID,supplyDataBean);
	if(supplyDataBean.getTodayDate()!=null){
		supplyDataBean.setFabricInvoiceNumber(bo.generateItemsInvoice(personID,supplyDataBean));
	}
	bo.addStock(personID,supplyDataBean);
	for (int i = 1; i < 7; i++) {
		SupplyDataBean supplys=new SupplyDataBean();
		supplys.setItemStatus(supplyDataBean.getItemStatus());
		supplys.setSerialNo(String.valueOf(i));
		supplierList.add(supplys);
	}
	return "";
}
public void itemValueChange(ValueChangeEvent vc){
	String serialNo="";String itemStatus="";
	try{
		serialNo=vc.getComponent().getAttributes().get("serial").toString();
		itemStatus=vc.getComponent().getAttributes().get("status").toString();
		supplyDataBean.setItemStatus(itemStatus);
		supplyDataBean.setItemName(vc.getNewValue().toString());
		if(!supplyDataBean.getItemName().equalsIgnoreCase("")){
			SupplyDataBean sup=new SupplyDataBean();
			bo.getItemPrice(personID,supplyDataBean);
			sup.setSerialNo(serialNo);
			sup.setItemStatus(itemStatus);
			sup.setProductFlag(false);
			sup.setProductFlag1(true);
			sup.setItemName(supplyDataBean.getItemName());
			sup.setItemBuyPrice(supplyDataBean.getItemBuyPrice());
			sup.setItemSellPrice(supplyDataBean.getItemSellPrice());
			supplierList.set(Integer.parseInt(serialNo) -1,sup);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void itemQuantityValueChange(ValueChangeEvent vc){
	String serialNo="";BigDecimal totalprice=BigDecimal.valueOf(0);
	try{
		serialNo=vc.getComponent().getAttributes().get("serial").toString();
		SupplyDataBean supply= new SupplyDataBean();
		supply.setSerialNo(serialNo);
		supply.setItemQuantity(vc.getNewValue().toString());
		supply.setItemStatus(supplierList.get(Integer.parseInt(serialNo) - 1).getItemStatus());
		supply.setItemName(supplierList.get(Integer.parseInt(serialNo) - 1).getItemName());
		supply.setItemBuyPrice(supplierList.get(Integer.parseInt(serialNo) - 1).getItemBuyPrice());
		if(!supply.getItemQuantity().equalsIgnoreCase("")){
			totalprice=new BigDecimal(supply.getItemQuantity()).multiply(new BigDecimal(supply.getItemBuyPrice()));
		}
		supply.setTotalPrice(totalprice.toString());
		supplierList.set(Integer.parseInt(serialNo) -1, supply);
		BigDecimal temp = BigDecimal.valueOf(0);BigDecimal temp1 = BigDecimal.valueOf(0);
		for (int i = 0; i < supplierList.size(); i++) {
			try {
				if (!supplierList.get(i).getItemQuantity().equals("")) {
					temp1 = temp1.add(new BigDecimal(supplierList.get(i).getItemQuantity()));
					temp = temp.add(new BigDecimal(supplierList.get(i).getTotalPrice()));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		setQuantityTotal(myFormatter.format(temp1).toString());
		setTotalAmount(myFormatter.format(temp).toString());
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void itemQuantityValueChange2(ValueChangeEvent vc){
	String serialNo="";BigDecimal totalprice=BigDecimal.valueOf(0);
	String name="";boolean flag=true;
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		serialNo=vc.getComponent().getAttributes().get("serial").toString();
		SupplyDataBean supply= new SupplyDataBean();
		supply.setSerialNo(serialNo);
		supply.setItemQuantity(vc.getNewValue().toString());
		supply.setItemStatus(supplierList.get(Integer.parseInt(serialNo) - 1).getItemStatus());
		supply.setItemName(supplierList.get(Integer.parseInt(serialNo) - 1).getItemName());
		supply.setItemBuyPrice(supplierList.get(Integer.parseInt(serialNo) - 1).getItemBuyPrice());
		supply.setItemQuantity1(supplierList.get(Integer.parseInt(serialNo) - 1).getItemQuantity1());
		supplyDataBean.setItemName(supplierList.get(Integer.parseInt(serialNo) - 1).getItemName());
		supplyDataBean.setItemStatus(supplierList.get(Integer.parseInt(serialNo) - 1).getItemStatus());
		bo.getItemPrice(personID,supplyDataBean);
		if(!supply.getItemQuantity().equalsIgnoreCase("")){
			setItemQuantity3(new BigDecimal(supplyDataBean.getQuantity()).add(new BigDecimal(supply.getItemQuantity1())).toString());
			if(new BigDecimal(supply.getItemQuantity()).compareTo(new BigDecimal(getItemQuantity3()))==1){
				System.out.println("inside if");
				name=CommonValidate.findComponentInRoot("esalesdatatable").getClientId(fc);
				fc.addMessage(name, new FacesMessage("only "+getItemQuantity3()+" Quantity in stock"));
				flag=false;
			}
			totalprice=new BigDecimal(supply.getItemQuantity()).multiply(new BigDecimal(supply.getItemBuyPrice()));
		}
		supply.setTotalPrice(totalprice.toString());
		supplierList.set(Integer.parseInt(serialNo) -1, supply);
		BigDecimal temp = BigDecimal.valueOf(0);BigDecimal temp1 = BigDecimal.valueOf(0);
		for (int i = 0; i < supplierList.size(); i++) {
			try {
				if (!supplierList.get(i).getItemQuantity().equals("")) {
					temp1 = temp1.add(new BigDecimal(supplierList.get(i).getItemQuantity()));
					temp = temp.add(new BigDecimal(supplierList.get(i).getTotalPrice()));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		setQuantityTotal(temp1.toString());
		setTotalAmount(temp.toString());
	}catch(Exception e){
		e.printStackTrace();
	}
}
private boolean itempurchaseorderValidation(boolean valid) {
	valid=true;
	String name="";
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		if(StringUtils.isEmpty(supplyDataBean.getSupplierName())){
			name=CommonValidate.findComponentInRoot("supplier").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please enter the supplier name"));
			valid=false;
		}
		int count = 0;
		for (int i = 0; i < supplierList.size(); i++) {
			try{
				if(supplierList.get(i).getItemName().equalsIgnoreCase("")){
					name=CommonValidate.findComponentInRoot("purhasedatatable").getClientId(fc);
					fc.addMessage(name, new FacesMessage("please enter the all fields"));
					valid=false;
				}else{
					if(supplierList.get(i).getItemQuantity() == null){
						name=CommonValidate.findComponentInRoot("purhasedatatable").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the quantity"));
						valid=false;
					}else if(supplierList.get(i).getItemQuantity() != null){
						if (!supplierList.get(i).getItemQuantity().matches("^\\d+(\\.\\d+)*$")) {
							name=CommonValidate.findComponentInRoot("purhasedatatable").getClientId(fc);
							fc.addMessage(name, new FacesMessage("please enter the valid quantity"));
							valid=false;
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				count++;
			}
		}
		if (count == supplierList.size()) {
			name=CommonValidate.findComponentInRoot("purhasedatatable").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please enter the all fields"));
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return valid;
}
public String saveItemsPurchase(){
	String status="Fail";
	try{
		if(personID != null && clientID != null){
			if(itempurchaseorderValidation(true)){
				supplyDataBean.setQuantityTotal(getQuantityTotal());
				supplyDataBean.setTotalAmount(getTotalAmount());
				supplyDataBean.setFabricList(supplierList);
				status=bo.savePurchaseSalesItems(personID,clientID,supplyDataBean);
				bo.addStock(personID,supplyDataBean);
				if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('purchaseDialog').hide();");
					RequestContext.getCurrentInstance().execute("PF('confirm').show();");
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public String addSalesChemistry(){
	supplyDataBean.setVendorName("");supplyDataBean.setDueDate(null);
	setQuantityTotal("");setTotalAmount("");
	supplierList=new ArrayList<SupplyDataBean>();
	Date d = new Date();
	supplyDataBean.setTodayDate(d);
	supplyDataBean.setItemStatus("CHEMISTRY ITEM");
	supplyDataBean.setStatus2("SALES");
	supplyDataBean.setPaymentStatus("PAID");
	supplyDataBean.setStockStatus("ADDED TO STOCK");
	String str="printer";
	supplierNameList=bo.getvendornamelist(personID,str);
	itemList=bo.getsalesitemNameList(personID,supplyDataBean);
	if(supplyDataBean.getTodayDate()!=null){
		supplyDataBean.setFabricInvoiceNumber(bo.generateItemsInvoice(personID,supplyDataBean));
	}
	for (int i = 1; i < 7; i++) {
		SupplyDataBean supplys=new SupplyDataBean();
		supplys.setProductFlag1(false);
		supplys.setProductFlag(true);
		supplys.setItemStatus(supplyDataBean.getItemStatus());
		supplys.setSerialNo(String.valueOf(i));
		supplierList.add(supplys);
	}
	return "";
}
public void itemQuantityValueChange1(ValueChangeEvent vc){
	String serialNo="";BigDecimal totalprice=BigDecimal.valueOf(0);
	String name="";boolean flag=true;
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		serialNo=vc.getComponent().getAttributes().get("serial").toString();
		System.out.println("quantity "+vc.getNewValue().toString()+" -- "+supplyDataBean.getQuantity());		
		if(new BigDecimal(vc.getNewValue().toString()).compareTo(new BigDecimal(supplyDataBean.getQuantity()))==1)
		{
			name=CommonValidate.findComponentInRoot("salesdatatable").getClientId(fc);
			fc.addMessage(name, new FacesMessage("only "+supplyDataBean.getQuantity()+" Quantity in stock"));
			flag=false;
		}
		if(flag==true){
			SupplyDataBean supply= new SupplyDataBean();
			supply.setSerialNo(serialNo);
			supply.setItemQuantity(vc.getNewValue().toString());
			supply.setProductFlag(false);
			supply.setProductFlag1(true);
			supply.setItemStatus(supplierList.get(Integer.parseInt(serialNo) - 1).getItemStatus());
			supply.setItemName(supplierList.get(Integer.parseInt(serialNo) - 1).getItemName());
			supply.setItemSellPrice(supplierList.get(Integer.parseInt(serialNo) - 1).getItemSellPrice());
			if(!supply.getItemQuantity().equalsIgnoreCase("")){
				totalprice=new BigDecimal(supply.getItemQuantity()).multiply(new BigDecimal(supply.getItemSellPrice()));
			}
			supply.setTotalPrice(totalprice.toString());
			supplierList.set(Integer.parseInt(serialNo) -1, supply);
			BigDecimal temp = BigDecimal.valueOf(0);BigDecimal temp1 = BigDecimal.valueOf(0);
			for (int i = 0; i < supplierList.size(); i++) {
				try {
					if (!supplierList.get(i).getItemQuantity().equals("")) {
						temp1 = temp1.add(new BigDecimal(supplierList.get(i).getItemQuantity()));
						temp = temp.add(new BigDecimal(supplierList.get(i).getTotalPrice()));	
						itemList.remove(i);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}	
			setQuantityTotal(myFormatter.format(temp1).toString());
			setTotalAmount(myFormatter.format(temp).toString());			
		}		
	}catch(Exception e){
		e.printStackTrace();
	}
}
public String saveItemsSales(){
	String status="Fail";
	try{
		if(personID != null && clientID != null){
			if(salesValidation(true)){
				supplyDataBean.setQuantityTotal(getQuantityTotal());
				supplyDataBean.setTotalAmount(getTotalAmount());
				supplyDataBean.setFabricList(supplierList);
				status=bo.savePurchaseSalesItems(personID,clientID,supplyDataBean);
				System.out.println("status--"+status);
				if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('salesDialog').hide();");
					RequestContext.getCurrentInstance().execute("PF('confirm').show();");
				}
				}
			}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public String saveItemsSales1(){
	String status="Fail";
	try{
		if(personID != null && clientID != null){
			if(salesValidation1(true)){
				supplyDataBean.setQuantityTotal(getQuantityTotal());
				supplyDataBean.setTotalAmount(getTotalAmount());
				supplyDataBean.setFabricList(supplierList);
				status=bo.savePurchaseSalesItems(personID,clientID,supplyDataBean);
				System.out.println("status--"+status);
				if(status.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("PF('salesDialog').hide();");
					RequestContext.getCurrentInstance().execute("PF('confirm').show();");
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
private boolean salesValidation(boolean valid) {
	valid=true;
	String name="";
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		if(supplyDataBean.getDueDate()==null){
			name=CommonValidate.findComponentInRoot("duedate").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please select the duedate"));
			valid=false;
		}if(StringUtils.isEmpty(supplyDataBean.getVendorName())){
			name=CommonValidate.findComponentInRoot("user").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please select the printer"));
			valid=false;
		}
		int count = 0;
		for (int i = 0; i < supplierList.size(); i++) {
			try{
				if(supplierList.get(i).getItemName().equalsIgnoreCase("")){
					name=CommonValidate.findComponentInRoot("salesdatatable").getClientId(fc);
					fc.addMessage(name, new FacesMessage("please enter the all fields"));
					valid=false;
				}else{
					if(supplierList.get(i).getItemQuantity() == null){
						name=CommonValidate.findComponentInRoot("salesdatatable").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the quantity"));
						valid=false;
					}else if(supplierList.get(i).getItemQuantity() != null){
						if (!supplierList.get(i).getItemQuantity().matches("^\\d+(\\.\\d+)*$")) {
							name=CommonValidate.findComponentInRoot("salesdatatable").getClientId(fc);
							fc.addMessage(name, new FacesMessage("please enter the valid quantity"));
							valid=false;
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				count++;
			}
		}
		if (count == supplierList.size()) {
			name=CommonValidate.findComponentInRoot("salesdatatable").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please enter the all fields"));
		}
}catch(Exception e){
	e.printStackTrace();
}
return valid;
}
private boolean salesValidation1(boolean valid) {
	valid=true;
	String name="";
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		int count = 0;
		for (int i = 0; i < supplierList.size(); i++) {
			try{
				if(supplierList.get(i).getItemName().equalsIgnoreCase("")){
					name=CommonValidate.findComponentInRoot("salesdatatable").getClientId(fc);
					fc.addMessage(name, new FacesMessage("please enter the all fields"));
					valid=false;
				}else{
					if(supplierList.get(i).getItemQuantity() == null){
						name=CommonValidate.findComponentInRoot("salesdatatable").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the quantity"));
						valid=false;
					}else if(supplierList.get(i).getItemQuantity() != null){
						if (!supplierList.get(i).getItemQuantity().matches("^\\d+(\\.\\d+)*$")) {
							name=CommonValidate.findComponentInRoot("salesdatatable").getClientId(fc);
							fc.addMessage(name, new FacesMessage("please enter the valid quantity"));
							valid=false;
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				count++;
			}
		}
		if (count == supplierList.size()) {
			name=CommonValidate.findComponentInRoot("salesdatatable").getClientId(fc);
			fc.addMessage(name, new FacesMessage("please enter the all fields"));
		}
}catch(Exception e){
	e.printStackTrace();
}
return valid;
}

public String hangtagPage(){
	supplyDataBean.setItemStatus("HANGTAG ITEM");
	supplyDataBean.setItemStatus2("");
	supplyDataBean.setItemStatus3("");
	purchaseFabricList=bo.getitemtableview(personID,supplyDataBean);
	return "hangtagRegistration";
}
public String hangtagitempage(){
	itemStatus="HANGTAG ITEM";
	supplyDataBean.setItemStatus(itemStatus);
	supplierList=bo.getitems(personID,clientID,itemStatus);
	return "successHangtagItemPage";
}
public String hangtagitempages(){
	itemStatus="HANGTAG ITEM";
	supplierList=bo.getitems(personID,clientID,itemStatus);
	return "hangtagItemRegistration";
}
public String labelPage(){
	supplyDataBean.setItemStatus("LABEL ITEM");
	supplyDataBean.setItemStatus2("");
	supplyDataBean.setItemStatus3("");
	purchaseFabricList=bo.getitemtableview(personID,supplyDataBean);
	return "labelRegistration";
}
public String labelitempage(){
	itemStatus="LABEL ITEM";
	supplyDataBean.setItemStatus(itemStatus);
	supplierList=bo.getitems(personID,clientID,itemStatus);
	return "successLabelItemPage";
}
public String labelitempages(){
	itemStatus="LABEL ITEM";
	supplierList=bo.getitems(personID,clientID,itemStatus);
	return "labelItemRegistration";
}
public String onclickitem(){
	supplyDataBean.setItemName("");
	supplyDataBean.setItemBuyPrice("");
	supplyDataBean.setItemSellPrice("");
	return "";
}
public String accessorisPage(){
	supplyDataBean.setItemStatus("ACCESSORIS ITEM");
	supplyDataBean.setItemStatus2("");
	supplyDataBean.setItemStatus3("");
	purchaseFabricList=bo.getitemtableview(personID,supplyDataBean);
	return "accessorisRegistration";
}
public String accessorisitempage(){
	itemStatus="ACCESSORIS ITEM";
	supplyDataBean.setItemStatus(itemStatus);
	supplierList=bo.getitems(personID,clientID,itemStatus);
	return "successAccessorisItemPage";
}
public String accessorisitempages(){
	itemStatus="ACCESSORIS ITEM";
	supplierList=bo.getitems(personID,clientID,itemStatus);
	return "accessorisItemRegistration";
}
public String plasticPage(){
	supplyDataBean.setItemStatus("PLASTIC ITEM");
	supplyDataBean.setItemStatus2("");
	supplyDataBean.setItemStatus3("");
	purchaseFabricList=bo.getitemtableview(personID,supplyDataBean);
	return "plasticRegistration";
}
public String plasticitempage(){
	itemStatus="PLASTIC ITEM";
	supplyDataBean.setItemStatus(itemStatus);
	supplierList=bo.getitems(personID,clientID,itemStatus);
	return "successPlasticItemPage";
}
public String plasticitempages(){
	itemStatus="PLASTIC ITEM";
	supplierList=bo.getitems(personID,clientID,itemStatus);
	return "plasticItemRegistration";
}
public String invoicePage(){
	setInvoiceDataTableFlag(false);
	supplyDataBean.setCategory(null);
	supplyDataBean.setFromDate(null);
	supplyDataBean.setToDate(null);
	supplyDataBean.setSupplierName(null);
	return "invoice";
}
public String addpurHangtag(){
	supplyDataBean.setSupplierName("");
	supplyDataBean.setItemName("");
	setTotalAmount("");
	setQuantityTotal("");
	supplierList=new ArrayList<SupplyDataBean>();
	Date d = new Date();
	supplyDataBean.setTodayDate(d);
	supplyDataBean.setItemStatus("HANGTAG ITEM");
	supplyDataBean.setStatus2("PURCHASE");
	supplyDataBean.setPaymentStatus("UNPAID");
	supplyDataBean.setStockStatus("NOTADDED TO STOCK");
	String str="hangtag";
	supplierNameList=bo.getsuppliernamelist(personID,str);
	itemList=bo.getitemNameList(personID,supplyDataBean);
	if(supplyDataBean.getTodayDate()!=null){
		supplyDataBean.setFabricInvoiceNumber(bo.generateItemsInvoice(personID,supplyDataBean));
	}
	for (int i = 1; i < 7; i++) {
		SupplyDataBean supplys=new SupplyDataBean();
		supplys.setItemStatus(supplyDataBean.getItemStatus());
		supplys.setSerialNo(String.valueOf(i));
		supplierList.add(supplys);
	}
	return "";
}
public String addsalesHangtag(){
	supplyDataBean.setVendorName("");supplyDataBean.setDueDate(null);
	setQuantityTotal("");setTotalAmount("");
	supplierList=new ArrayList<SupplyDataBean>();
	Date d = new Date();
	supplyDataBean.setTodayDate(d);
	supplyDataBean.setItemStatus("HANGTAG ITEM");
	supplyDataBean.setStatus2("SALES");
	supplyDataBean.setPaymentStatus("PAID");
	supplyDataBean.setStockStatus("ADDED TO STOCK");
	String str="cmt";
	supplierNameList=bo.getvendornamelist(personID,str);
	itemList=bo.getsalesitemNameList(personID,supplyDataBean);
	if(supplyDataBean.getTodayDate()!=null){
		supplyDataBean.setFabricInvoiceNumber(bo.generateItemsInvoice(personID,supplyDataBean));
	}
	for (int i = 1; i < 7; i++) {
		SupplyDataBean supplys=new SupplyDataBean();
		supplys.setItemStatus(supplyDataBean.getItemStatus());
		supplys.setSerialNo(String.valueOf(i));
		supplys.setProductFlag1(false);
		supplys.setProductFlag(true);
		supplierList.add(supplys);
	}
	return "";
}
public String addpurLabel(){
	supplyDataBean.setSupplierName("");
	supplyDataBean.setItemName("");
	setTotalAmount("");
	setQuantityTotal("");
	supplierList=new ArrayList<SupplyDataBean>();
	Date d = new Date();
	supplyDataBean.setTodayDate(d);
	supplyDataBean.setItemStatus("LABEL ITEM");
	supplyDataBean.setStatus2("PURCHASE");
	supplyDataBean.setPaymentStatus("UNPAID");
	supplyDataBean.setStockStatus("NOTADDED TO STOCK");
	String str="label";
	supplierNameList=bo.getsuppliernamelist(personID,str);
	itemList=bo.getitemNameList(personID,supplyDataBean);
	if(supplyDataBean.getTodayDate()!=null){
		supplyDataBean.setFabricInvoiceNumber(bo.generateItemsInvoice(personID,supplyDataBean));
	}
	for (int i = 1; i < 7; i++) {
		SupplyDataBean supplys=new SupplyDataBean();
		supplys.setItemStatus(supplyDataBean.getItemStatus());
		supplys.setSerialNo(String.valueOf(i));
		supplierList.add(supplys);
	}
	return "";
}
public String addsalesLabel(){
	supplyDataBean.setVendorName("");supplyDataBean.setDueDate(null);
	setQuantityTotal("");setTotalAmount("");
	supplierList=new ArrayList<SupplyDataBean>();
	Date d = new Date();
	supplyDataBean.setTodayDate(d);
	supplyDataBean.setItemStatus("LABEL ITEM");supplyDataBean.setStatus2("SALES");
	supplyDataBean.setPaymentStatus("PAID");
	supplyDataBean.setStockStatus("ADDED TO STOCK");
	String str="cmt";
	supplierNameList=bo.getvendornamelist(personID,str);
	itemList=bo.getsalesitemNameList(personID,supplyDataBean);
	if(supplyDataBean.getTodayDate()!=null){
		supplyDataBean.setFabricInvoiceNumber(bo.generateItemsInvoice(personID,supplyDataBean));
	}
	for (int i = 1; i < 7; i++) {
		SupplyDataBean supplys=new SupplyDataBean();
		supplys.setItemStatus(supplyDataBean.getItemStatus());
		supplys.setSerialNo(String.valueOf(i));
		supplys.setProductFlag1(false);
		supplys.setProductFlag(true);
		supplierList.add(supplys);
	}
	return "";
}
public String addpurAccessoris(){
	supplyDataBean.setSupplierName("");
	supplyDataBean.setItemName("");
	setTotalAmount("");
	setQuantityTotal("");
	supplierList=new ArrayList<SupplyDataBean>();
	Date d = new Date();
	supplyDataBean.setTodayDate(d);
	supplyDataBean.setItemStatus("ACCESSORIS ITEM");
	supplyDataBean.setStatus2("PURCHASE");
	supplyDataBean.setPaymentStatus("UNPAID");
	supplyDataBean.setStockStatus("NOTADDED TO STOCK");
	String str="accessoris";
	supplierNameList=bo.getsuppliernamelist(personID,str);
	itemList=bo.getitemNameList(personID,supplyDataBean);
	if(supplyDataBean.getTodayDate()!=null){
		supplyDataBean.setFabricInvoiceNumber(bo.generateItemsInvoice(personID,supplyDataBean));
	}
	for (int i = 1; i < 7; i++) {
		SupplyDataBean supplys=new SupplyDataBean();
		supplys.setItemStatus(supplyDataBean.getItemStatus());
		supplys.setSerialNo(String.valueOf(i));
		supplierList.add(supplys);
	}
	return "";
}
public String addsalesAccessoris(){
	supplyDataBean.setVendorName("");supplyDataBean.setDueDate(null);
	setQuantityTotal("");setTotalAmount("");
	supplierList=new ArrayList<SupplyDataBean>();
	Date d = new Date();
	supplyDataBean.setTodayDate(d);
	supplyDataBean.setItemStatus("ACCESSORIS ITEM");
	supplyDataBean.setStatus2("SALES");
	supplyDataBean.setPaymentStatus("PAID");
	supplyDataBean.setStockStatus("ADDED TO STOCK");
	String str="cmt";
	supplierNameList=bo.getvendornamelist(personID,str);
	itemList=bo.getsalesitemNameList(personID,supplyDataBean);
	if(supplyDataBean.getTodayDate()!=null){
		supplyDataBean.setFabricInvoiceNumber(bo.generateItemsInvoice(personID,supplyDataBean));
	}
	for (int i = 1; i < 7; i++) {
		SupplyDataBean supplys=new SupplyDataBean();
		supplys.setItemStatus(supplyDataBean.getItemStatus());
		supplys.setSerialNo(String.valueOf(i));
		supplys.setProductFlag1(false);
		supplys.setProductFlag(true);
		supplierList.add(supplys);
	}
	return "";
}
public String addpurPlastic(){
	supplyDataBean.setSupplierName("");
	supplyDataBean.setItemName("");
	setTotalAmount("");
	setQuantityTotal("");
	supplierList=new ArrayList<SupplyDataBean>();
	Date d = new Date();
	supplyDataBean.setTodayDate(d);
	supplyDataBean.setItemStatus("PLASTIC ITEM");
	supplyDataBean.setStatus2("PURCHASE");
	supplyDataBean.setPaymentStatus("UNPAID");
	supplyDataBean.setStockStatus("NOTADDED TO STOCK");
	String str="plastic";
	supplierNameList=bo.getsuppliernamelist(personID,str);
	itemList=bo.getitemNameList(personID,supplyDataBean);
	if(supplyDataBean.getTodayDate()!=null){
		supplyDataBean.setFabricInvoiceNumber(bo.generateItemsInvoice(personID,supplyDataBean));
	}
	for (int i = 1; i < 7; i++) {
		SupplyDataBean supplys=new SupplyDataBean();
		supplys.setItemStatus(supplyDataBean.getItemStatus());
		supplys.setSerialNo(String.valueOf(i));
		supplierList.add(supplys);
	}
	return "";
}
public String addsalesPlastic(){
	supplyDataBean.setVendorName("");supplyDataBean.setDueDate(null);
	setQuantityTotal("");setTotalAmount("");
	supplierList=new ArrayList<SupplyDataBean>();
	Date d = new Date();
	supplyDataBean.setTodayDate(d);
	supplyDataBean.setVendorName("LEMONE");
	supplyDataBean.setItemStatus("PLASTIC ITEM");
	supplyDataBean.setStatus2("SALES");
	supplyDataBean.setPaymentStatus("PAID");
	supplyDataBean.setStockStatus("ADDED TO STOCK");
	itemList=bo.getsalesitemNameList(personID,supplyDataBean);
	if(supplyDataBean.getTodayDate()!=null){
		supplyDataBean.setFabricInvoiceNumber(bo.generateItemsInvoice(personID,supplyDataBean));
	}
	for (int i = 1; i < 7; i++) {
		SupplyDataBean supplys=new SupplyDataBean();
		supplys.setItemStatus(supplyDataBean.getItemStatus());
		supplys.setSerialNo(String.valueOf(i));
		supplys.setProductFlag1(false);
		supplys.setProductFlag(true);
		supplierList.add(supplys);
	}
	return "";
}
public String chemistryitemsView(){
	String str1="";String str="";
	try{
		if(personID != null){
			if(supplyDataBean.getStatus2().equalsIgnoreCase("PURCHASE")){
				itempurchaseFlag="1";
				itemsalesFlag="none";
			}else if(supplyDataBean.getStatus2().equalsIgnoreCase("SALES")){
				itempurchaseFlag="none";
				itemsalesFlag="1";
			}
			bo.viewpurchasesaleitems(personID,supplyDataBean);
			if(supplyDataBean.getItemStatus().equalsIgnoreCase("CHEMISTRY ITEM")){
				str1="chemistry";str="printer";
			}else if(supplyDataBean.getItemStatus().equalsIgnoreCase("HANGTAG ITEM")){
				str1="hangtag";str="cmt";
			}else if(supplyDataBean.getItemStatus().equalsIgnoreCase("LABEL ITEM")){
				str1="label";str="cmt";
			}else if(supplyDataBean.getItemStatus().equalsIgnoreCase("ACCESSORIS ITEM")){
				str1="accessoris";str="cmt";
			}else if(supplyDataBean.getItemStatus().equalsIgnoreCase("PLASTIC ITEM")){
				str1="plastic";
			}
			itemList=bo.getsalesitemNameList(personID,supplyDataBean);
			supplierNameList=bo.getsuppliernamelist(personID,str1);
			vendorList=bo.getvendornamelist(personID,str);
			setSupplierList(supplyDataBean.getFabricList());
			setQuantityTotal(supplyDataBean.getQuantityTotal());
			if(supplyDataBean.getStatus2().equalsIgnoreCase("PURCHASE")){
				setTotalAmount(supplyDataBean.getTotalAmount());
				String output=myFormatter.format(Integer.parseInt(supplyDataBean.getTotalAmount()));
				setTotalAmount(output);
			}else if(supplyDataBean.getStatus2().equalsIgnoreCase("SALES")){
				setTotalAmount(supplyDataBean.getTotalPrice());
				String output=myFormatter.format(Integer.parseInt(supplyDataBean.getTotalAmount()));
				setTotalAmount(output);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
private boolean itemeditValidation(boolean valid) {
	valid=true;
	String name="";
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		for (int i = 0; i < supplierList.size(); i++) {
				if(supplierList.get(i).getItemQuantity().equalsIgnoreCase("")){
						name=CommonValidate.findComponentInRoot("esalesdatatable").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the quantity"));
						valid=false;
				}else if(!supplierList.get(i).getItemQuantity().equalsIgnoreCase("")){
						if (!supplierList.get(i).getItemQuantity().matches("^\\d+(\\.\\d+)*$")) {
							name=CommonValidate.findComponentInRoot("esalesdatatable").getClientId(fc);
							fc.addMessage(name, new FacesMessage("please enter the valid quantity"));
							valid=false;
						}
				}
			}
	}catch(Exception e){
		name=CommonValidate.findComponentInRoot("esalesdatatable").getClientId(fc);
		fc.addMessage(name, new FacesMessage("please enter the quantity"));
		e.printStackTrace();
	}
	return valid;
}
public String updateItemsPurchaseSale(){
	String status="Fail";boolean valid=true;String name="";
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		if(personID != null && clientID != null){
				supplyDataBean.setQuantityTotal(quantityTotal);
				supplyDataBean.setTotalAmount(totalAmount);
				supplyDataBean.setFabricList(getSupplierList());
				if(supplyDataBean.getStatus2().equalsIgnoreCase("PURCHASE")){
					if(StringUtils.isEmpty(supplyDataBean.getSupplierName())){
						name=CommonValidate.findComponentInRoot("esupplier").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please enter the supplier name"));
						valid=false;
					}
					supplyDataBean.setStatus2(supplyDataBean.getStatus2());
					supplyDataBean.setPaymentStatus("UNPAID");
					supplyDataBean.setStockStatus("NOTADDED TO STOCK");
				}else{
					if(StringUtils.isEmpty(supplyDataBean.getVendorName())){
						name=CommonValidate.findComponentInRoot("users").getClientId(fc);
						fc.addMessage(name, new FacesMessage("please select the user"));
						valid=false;
					}
					supplyDataBean.setStatus2(supplyDataBean.getStatus2());
					supplyDataBean.setPaymentStatus("PAID");
					supplyDataBean.setStockStatus("ADDED TO STOCK");
				}
				if(valid==true && itemeditValidation(true)){
					System.out.println("inside if");
						status=bo.updatepurchasesalesitems(personID,clientID,supplyDataBean);
						setQuantityTotal(supplyDataBean.getQuantityTotal());
						setTotalAmount(supplyDataBean.getTotalAmount());
						if(status.equalsIgnoreCase("Success")){
							RequestContext.getCurrentInstance().execute("PF('editDialog').hide();");
							RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
						}else if(status.equalsIgnoreCase("Exist")){
							RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
						}
				}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public String deletepursaleitems(){
	try{
		if(personID!=null){
			bo.deletepursalesitems(personID,supplyDataBean);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public void pursaleValueChange1(ValueChangeEvent vc){
	purchaseFabricList=null;
	String value=vc.getNewValue().toString();
	try{
		if(value.equals("all") || value.equals("purchase") || value.equals("sales") ){
			supplyDataBean.setItemStatus2(vc.getNewValue().toString());
		}else if(value.equals("allday") || value.equals("30days") || value.equals("60days") || value.equals("90days") || value.equals("120days")){
			supplyDataBean.setItemStatus3(vc.getNewValue().toString());			
		}		
		if(supplyDataBean.getItemStatus2().equals("")) supplyDataBean.setItemStatus2("all");
		else if(supplyDataBean.getItemStatus3().equals("")) supplyDataBean.setItemStatus3("allday");
		if(!supplyDataBean.getItemStatus2().equalsIgnoreCase("") || !supplyDataBean.getItemStatus3().equalsIgnoreCase("")){
			purchaseFabricList=bo.getitemtableviews(personID,supplyDataBean);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

public String stockChemistry(){
	supplyDataBean.setDeliveredDate(null);
	if(personID != null){
			bo.viewpurchasesaleitems(personID,supplyDataBean);		
	}
	return "";	
}

public String invoiceGenerate(){
	supplyDataBean.setDeliveredDate(null);
	if(personID != null){
		bo.viewpurchasesaleitems(personID,supplyDataBean);	
		bo.generateiInvoiceno(personID, supplyDataBean);
	}
	return "";	
}

public String invoice(){
	try{
		System.out.println("id "+supplyDataBean.getFabricId());
		bo.generateiInvoiceno(personID, supplyDataBean);
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public String payment(){
	supplyDataBean.setRemaining("");
	bo.getPaymentDetails(personID,supplyDataBean);
	return "";
}

public String payAmount(){
	System.out.println("amount "+supplyDataBean.getRemaining());
	String fieldName;FacesContext fc=FacesContext.getCurrentInstance();
	boolean flag=true;
	try{
		try{
			if(supplyDataBean.getRemaining().equals(null) || supplyDataBean.getRemaining().equals("")){
				fieldName=CommonValidate.findComponentInRoot("amount").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
				flag=false;
			}else if(new BigDecimal(supplyDataBean.getRemaining()).compareTo(new BigDecimal(supplyDataBean.getBalanceAmoount()))==1){
				fieldName=CommonValidate.findComponentInRoot("amount").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("you entered the exceed amount"));
				flag=false;
			}
		}catch(Exception e){
			fieldName=CommonValidate.findComponentInRoot("amount").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
			flag=false;
		}	
		if(flag==true){
			bo.payAmount(personID,supplyDataBean);
			RequestContext.getCurrentInstance().execute("PF('paymentForm').hide();");
			RequestContext.getCurrentInstance().execute("PF('paymentDialog').show();");
		}
	}catch(Exception e){
		e.printStackTrace();
	}	
	return "";
}

/*public String addStock(){
	System.out.println("add stock  ");
	String fieldName;FacesContext fc=FacesContext.getCurrentInstance();
	boolean flag=true;
	try{
		if(supplyDataBean.getDeliveredDate()==null){
			fieldName=CommonValidate.findComponentInRoot("deldate").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please select delivered date"));
			flag=false;
		}
		if(flag==true){
			bo.addStock(personID,supplyDataBean);
			RequestContext.getCurrentInstance().execute("PF('stockForm').hide();");
			RequestContext.getCurrentInstance().execute("PF('stockdialog').show();");
		}
	}catch(Exception e){
		e.printStackTrace();
	}	
	return "";
}*/

public String fabricInvGenerate(){
	try{
		if(personID!=null){
			bo.fabricgenerateInvoice(personID,supplyDataBean);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public String purchaseFabricPayment(){
	try{
		if(personID!=null){
			bo.purchaseFabricPayment(personID,supplyDataBean);
			supplyDataBean.setRemaining("");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}
public String payButton(){
	String fieldName="";boolean flag=true;
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		if(personID!=null){
			if(supplyDataBean.getRemaining().equals(null) || supplyDataBean.getRemaining().equals("")){
			    fieldName=CommonValidate.findComponentInRoot("amount").getClientId(fc);
			    fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
			    flag=false;
			   }else if(new BigDecimal(supplyDataBean.getRemaining()).compareTo(new BigDecimal(supplyDataBean.getBalanceAmoount()))==1){
			    fieldName=CommonValidate.findComponentInRoot("amount").getClientId(fc);
			    fc.addMessage(fieldName, new FacesMessage("you entered the exceed amount"));
			    flag=false;
			  }
			  if(flag==true){
				  bo.insertPayment(personID,supplyDataBean);
				  RequestContext.getCurrentInstance().execute("PF('paymentForm').hide();");
				  RequestContext.getCurrentInstance().execute("PF('payconfirm').show();");
			  }
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

//Supply Report
public String supplyReport(){
	setFabricDataTableFlag(false);
	setSupplyDataTableFlag(false);
	supplyDataBean.setDivision("");
	return "supplyReport";
}

	public void divisionValueChnage(ValueChangeEvent vc){
		try{
			purchaseFabricList=new ArrayList<SupplyDataBean>();
			supplierList=new ArrayList<SupplyDataBean>();
			String str1=vc.getNewValue().toString();
			if(str1.equalsIgnoreCase("select")){
				setFabricDataTableFlag(false);setSupplyDataTableFlag(false);
			}
			else if(str1.equalsIgnoreCase("fabric")){
				String str="all";
				purchaseFabricList=bo.getfabricpurchaseList(personID,str);
				setFabricDataTableFlag(true);setSupplyDataTableFlag(false);
			}else{
				if(str1.equalsIgnoreCase("chemistry")){
					supplyDataBean.setItemStatus("CHEMISTRY ITEM");
				}else if(str1.equalsIgnoreCase("hangtag")){
					supplyDataBean.setItemStatus("HANGTAG ITEM");
				}else if(str1.equalsIgnoreCase("label")){
					supplyDataBean.setItemStatus("LABEL ITEM");
				}else if(str1.equalsIgnoreCase("accessoris")){
					supplyDataBean.setItemStatus("ACCESSORIS ITEM");
				}else if(str1.equalsIgnoreCase("plastic")){
					supplyDataBean.setItemStatus("PLASTIC ITEM");
				}
				supplierList=bo.getsupplyreportview(personID,supplyDataBean);
				setFabricDataTableFlag(false);setSupplyDataTableFlag(true);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void categoryChange(ValueChangeEvent v){
		System.out.println("category "+v.getNewValue());
		if(v.getNewValue()==null){
			setSupplierNameList(null);
		}
		supplyDataBean.setCategory(v.getNewValue().toString());
		supplierNameList=bo.getsuppliernamelist(personID,supplyDataBean.getCategory());
		System.out.println("supplierNameList----"+supplierNameList.size());
	}
	
	public String getInvoice(){
		try{
			System.out.println("Inside getInvoice ");
			if(invoiceValidation(true)){
				setInvoiceDataTableFlag(true);
				purchaseFabricList=bo.getinvoicetableview(personID,supplyDataBean);
				System.out.println("purchaseFabricList -- "+purchaseFabricList.size());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean invoiceValidation(boolean valid) {
		  valid=true;String fieldName;
		  FacesContext fc=FacesContext.getCurrentInstance();
		  if(StringUtils.isEmpty(supplyDataBean.getCategory())){
		   fieldName=CommonValidate.findComponentInRoot("catgry").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please select the Category"));
		   valid=false;
		  }
		  if(supplyDataBean.getToDate()==null && supplyDataBean.getFromDate()!=null){
			  fieldName=CommonValidate.findComponentInRoot("tdate").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please choose To Date"));
			  valid=false;
		  }
		  if(supplyDataBean.getToDate()!=null && supplyDataBean.getFromDate()==null){
			  fieldName=CommonValidate.findComponentInRoot("fdate").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please choose From Date"));
			  valid=false;
		  }
		  return valid;
	}
}
