package com.nrg.lemon.managedBean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.lemon.bo.LemonBo;
import com.nrg.lemon.domain.StockDataBean;
import com.nrg.lemon.util.CommonValidate;
import com.nrg.lemon.util.StockOutJDBC;

@ManagedBean(name="stockMB")
public class StockMB {
	
	ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	LemonBo bo=(LemonBo)ctx.getBean("bo");
	String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PersonID");
	StockDataBean stockDataBean=new StockDataBean();
	private List<StockDataBean> warelists=null;
	private List<String> cmtProducts=null;
	private List<StockDataBean> returnList=null; /*25-02*/   
	private List<StockDataBean>returnList1=null;
	private List<StockDataBean>returnList2=null;
	private Date date=new Date();
	public List<StockDataBean> getReturnList2() {
		return returnList2;
	}
	public void setReturnList2(List<StockDataBean> returnList2) {
		this.returnList2 = returnList2;
	}
	public List<StockDataBean> getReturnList() {
		return returnList;
	}
	public List<StockDataBean> getReturnList1() {
		return returnList1;
	}
	public void setReturnList1(List<StockDataBean> returnList1) {
		this.returnList1 = returnList1;
	}
	public void setReturnList(List<StockDataBean> returnList) {
		this.returnList = returnList;
	}
	public List<String> getCmtProducts() {
		return cmtProducts;
	}
	public void setCmtProducts(List<String> cmtProducts) {
		this.cmtProducts = cmtProducts;
	}
	public List<StockDataBean> getWarelists() {
		return warelists;
	}
	public void setWarelists(List<StockDataBean> warelists) {
		this.warelists = warelists;
	}
	public StockDataBean getStockDataBean() {
		return stockDataBean;
	}
	public void setStockDataBean(StockDataBean stockDataBean) {
		this.stockDataBean = stockDataBean;
	}
	
	/*WAREHOUSE REGISTRATION*/
	public String warehousePage(){
		stockDataBean.setWarelists(null);
		bo.getWarehouseDetails(personID,stockDataBean);
		return "warehouseRegistration";
	}
	
	public String warehouseReg(){
		System.out.println("reg warehouse");
		warelists=new ArrayList<StockDataBean>();
		try{
			for (int i = 0; i < 7; i++) {
				StockDataBean warelist=new StockDataBean();
				warelist.setSerialNo(""+(i+1));
				warelist.setLocation("");
				warelist.setPersonIncharge("");
				warelist.setWarehouseName("");
				warelists.add(warelist);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public String addButton(){
		StockDataBean warelist=new StockDataBean();
		warelist.setSerialNo(String.valueOf(warelists.size()+1));
		warelist.setLocation("");
		warelist.setPersonIncharge("");
		warelist.setWarehouseName("");
		warelists.add(warelist);
		return "";
	}
	
	public String warehouseSave(){
		try{
			if(validate(true)){
				stockDataBean.setWarelists(warelists);
				bo.warehouseSave(personID,stockDataBean);
				RequestContext.getCurrentInstance().execute("PF('warehousereg').hide();");
				RequestContext.getCurrentInstance().execute("PF('confirm').show();");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean validate(boolean flag){
		boolean valid=true;
		String fieldName;int count=0;
		FacesContext fc=FacesContext.getCurrentInstance();
		for (int i = 0; i < warelists.size(); i++) {
			if(warelists.get(i).getWarehouseName().equals("")){
				count++;
			}			
		}
		if(count==warelists.size()){
			fieldName=CommonValidate.findComponentInRoot("errmsg").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please fill atleast one "));
			valid=false;
		}else{
			for (int i = 0; i < warelists.size(); i++) {
				if(!warelists.get(i).getWarehouseName().equals("")){
					if(warelists.get(i).getLocation().equals("")){
						fieldName=CommonValidate.findComponentInRoot("errmsg").getClientId();
						fc.addMessage(fieldName, new FacesMessage("Please Enter Location at row "+(i+1)));
						valid=false;
					}else if(warelists.get(i).getPersonIncharge().equals("")){
						fieldName=CommonValidate.findComponentInRoot("errmsg").getClientId();
						fc.addMessage(fieldName, new FacesMessage("Please Enter Person Incharge at row "+(i+1)));
						valid=false;
					}
				}			
			}
		}
		return valid;
	}
	
	public void warehouseEdit(RowEditEvent row){
		System.out.println("warehosue edit");
		stockDataBean.setStatus("edit");
		try{
			int ware_ID=((StockDataBean)row.getObject()).getWarehouse_id();
			String name=((StockDataBean)row.getObject()).getWarehouseName();
			String location=((StockDataBean)row.getObject()).getLocation();
			String incharge=((StockDataBean)row.getObject()).getPersonIncharge();
			stockDataBean.setWarehouse_id(ware_ID);
			stockDataBean.setWarehouseName(name);
			stockDataBean.setLocation(location);
			stockDataBean.setPersonIncharge(incharge);
			System.out.println("id "+ware_ID);
			if(validateEdit(true)){
				bo.updateWarehouse(stockDataBean);
				RequestContext.getCurrentInstance().execute("PF('edit').show();");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean validateEdit(boolean flag){
		boolean valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();		
		if(stockDataBean.getWarehouseName().equals("")){
			fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Enter Name "));
			valid=false;
		}
		if(stockDataBean.getLocation().equals("")){
			fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Enter Location "));
			valid=false;
		}
		if(stockDataBean.getPersonIncharge().equals("")){
			fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Enter Person Incharge"));
			valid=false;
		}
		return valid;
	}
	
	public String deleteWarehouse(){
		System.out.println("warehosue delete");
		stockDataBean.setStatus("delete");
		bo.updateWarehouse(stockDataBean);
		RequestContext.getCurrentInstance().execute("PF('delete').show();");
		return "";
	}
	
	/*MANUAL STOCK OUT*/
	
	public String manualStockOutPage(){
		stockDataBean.setWarelists(null);
		stockDataBean.setWarehouseList(null);
		stockDataBean.setFlag(false);
		stockDataBean.setFlag1(false);
		bo.getManualStockDetails(personID,stockDataBean);
		return "manualStockOut";
	}
	
	public String manualStockOut(){
		warelists=new ArrayList<StockDataBean>();
		stockDataBean.setFlag(false);
		stockDataBean.setFlag1(false);
		stockDataBean.setDate(date);
		stockDataBean.setWarehouseName("");
		stockDataBean.setNewWarehouse("");
		stockDataBean.setTotalPrice("0");
		//bo.getManualStockOutetails(personID,stockDataBean);
		for (int i = 0; i < 7; i++) {
			StockDataBean stocks=new StockDataBean();
			stocks.setSerialNo(""+(i+1));
			stocks.setModelNo("");
			stocks.setQuantity("");
			stocks.setSeri("");
			stocks.setSflag(true);
			stocks.setSflag1(false);
			stocks.setSellingPrice("");
			stocks.setTotalPrice("");
			warelists.add(stocks);
		}
		return "";
	}
	
	public void categorySelect(ValueChangeEvent v){
		stockDataBean.setCmtProducts(null);
		stockDataBean.setSeris(null);
		try{
			String category=v.getNewValue().toString();
			stockDataBean.setWarehouseName(category);
			bo.getManualStockOutetails(personID,stockDataBean);
			/*if(category.equals("")){
				stockDataBean.setFlag(false);stockDataBean.setFlag1(false);
			}else if(category.equals("Main Warehouse")){
				stockDataBean.setFlag(true);stockDataBean.setFlag1(false);
			}else{
				stockDataBean.setFlag1(true);stockDataBean.setFlag(false);
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void modelselect(ValueChangeEvent v){
		stockDataBean.setSerin("");
		try{
			String serialNo=v.getComponent().getAttributes().get("serial").toString();
			stockDataBean.setModelNo(v.getNewValue().toString());
			bo.getModelDetails(personID,stockDataBean);
			StockDataBean stocks=new StockDataBean();
			stocks.setSerialNo(serialNo);
			stocks.setModelNo(v.getNewValue().toString());
			stocks.setQuantity("");
			stocks.setSeri("");
			stocks.setSflag(true);
			stocks.setSflag1(false);
			stocks.setSellingPrice(stockDataBean.getSellingPrice());
			stocks.setTotalPrice("");
			warelists.set(Integer.parseInt(serialNo)-1, stocks);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void seriselect(ValueChangeEvent v){
		try{
			String serialNo=v.getComponent().getAttributes().get("serial").toString();
			String model=v.getComponent().getAttributes().get("model").toString();
			stockDataBean.setModelNo(model);
			stockDataBean.setSerin(v.getNewValue().toString());
			bo.getModelDetails(personID,stockDataBean);
			StockDataBean stocks=new StockDataBean();
			stocks.setSerialNo(serialNo);
			stocks.setSeri(v.getNewValue().toString());
			stocks.setModelNo(model);
			stocks.setQuantity("");
			stocks.setSflag(false);
			stocks.setSflag1(true);
			stocks.setSellingPrice(stockDataBean.getSellingPrice());
			stocks.setTotalPrice("");
			warelists.set(Integer.parseInt(serialNo)-1, stocks);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void quantityChange(ValueChangeEvent v){
		boolean valid=true;String fieldName;int c=0;
		FacesContext fc=FacesContext.getCurrentInstance();		
		String quan=v.getNewValue().toString();
		try{
			String serialNo=v.getComponent().getAttributes().get("serial").toString();
			String model=v.getComponent().getAttributes().get("model").toString();
			String seri=v.getComponent().getAttributes().get("seri").toString();
			BigDecimal quant=BigDecimal.valueOf(0), quants=BigDecimal.valueOf(0);			
			for (int i = 0; i < warelists.size(); i++) {
				if(Integer.parseInt(serialNo)==1){
					if(new BigDecimal(quan).compareTo(new BigDecimal(stockDataBean.getQuantity()))==1){
						fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
						fc.addMessage(fieldName, new FacesMessage("Only "+stockDataBean.getQuantity()+ " Quantity in "+stockDataBean.getWarehouseName()));
						valid=false;
					}
				}else if(i==(Integer.parseInt(serialNo)-1)){
					System.out.println(" equal ");
					if(new BigDecimal(quan).compareTo(new BigDecimal(stockDataBean.getQuantity()))==1){
						fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
						fc.addMessage(fieldName, new FacesMessage("Only "+stockDataBean.getQuantity()+ " Quantity in "+stockDataBean.getWarehouseName()));
						valid=false;
					}
				}else if(i!=(Integer.parseInt(serialNo)-1)){
					System.out.println(" ! ");
					if(model.equals(warelists.get(i).getModelNo()) && seri.equals(warelists.get(i).getSeri())){
						if(!warelists.get(i).getQuantity().equals("")){
							quant=quant.add(new BigDecimal(warelists.get(i).getQuantity()));
						}
						c++;
					}
				}
			}
			if(c>0){
				quants=new BigDecimal(stockDataBean.getQuantity()).subtract(quant);
				if(new BigDecimal(quan).compareTo(quants)==1){
					fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
					fc.addMessage(fieldName, new FacesMessage("Only "+quants+ " Quantity in "+stockDataBean.getWarehouseName()));
					valid=false;
				}
			}
			if(valid==true){
				System.out.println("true");
				BigDecimal total=BigDecimal.valueOf(0);
				StockDataBean stocks=new StockDataBean();
				stocks.setQuantity(v.getNewValue().toString());
				stocks.setSerialNo(serialNo);
				stocks.setSflag(false);
				stocks.setSflag1(true);
				stocks.setModelNo(warelists.get(Integer.parseInt(serialNo)-1).getModelNo());
				stocks.setSeri(warelists.get(Integer.parseInt(serialNo)-1).getSeri());
				stocks.setSellingPrice(warelists.get(Integer.parseInt(serialNo)-1).getSellingPrice());
				stocks.setTotalPrice(String.valueOf(new BigDecimal(warelists.get(Integer.parseInt(serialNo)-1).getSellingPrice()).
						multiply(new BigDecimal(v.getNewValue().toString()))));
				stocks.setWarehouseName(warelists.get(Integer.parseInt(serialNo)-1).getWarehouseName());
				warelists.set(Integer.parseInt(serialNo)-1, stocks);
				for (int i = 0; i < warelists.size(); i++) {					
					if(!warelists.get(i).getQuantity().equals("")){
						total=total.add((new BigDecimal(warelists.get(i).getQuantity()).
								multiply(new BigDecimal(warelists.get(i).getSellingPrice()))));
					}
				}				
				stockDataBean.setTotalPrice(String.valueOf(total));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String addmanualStk(){
		StockDataBean stocks=new StockDataBean();
		stocks.setSerialNo(String.valueOf(warelists.size()+1));
		stocks.setModelNo("");
		stocks.setQuantity("");
		stocks.setSellingPrice("");
		stocks.setTotalPrice("");
		stocks.setSeri("");
		stocks.setSflag(true);
		stocks.setSflag1(false);
		stocks.setWarehouseName("");
		warelists.add(stocks);
		return "";
	}
	
	public String manualStockSave(){
		try{
			if(validate1(true)){
				stockDataBean.setWarelists(warelists);
				bo.saveManualStockOut(personID,stockDataBean);
				RequestContext.getCurrentInstance().execute("PF('stockDialog').hide();");
				RequestContext.getCurrentInstance().execute("PF('confirm').show();");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean validate1(boolean valid){
		valid=true;
		String fieldName;int count=0;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(stockDataBean.getWarehouseName().equals("")){
			fieldName=CommonValidate.findComponentInRoot("warehouse").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select from Warehouse "));
			valid=false;
		}
		if(stockDataBean.getNewWarehouse().equals("")){
			fieldName=CommonValidate.findComponentInRoot("newwarehouse").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select to Warehouse "));
			valid=false;
		}
		else{
			for (int i = 0; i < warelists.size(); i++) {
					if(warelists.get(i).getModelNo().equals("")){
						count++;
					}			
			}
			if(count==warelists.size()){
				fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Please fill atleast one "));
				valid=false;
			}else{
				for (int i = 0; i < warelists.size(); i++) {
						if(!warelists.get(i).getModelNo().equals("")){
							if(warelists.get(i).getSeri().equals("")){
								fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
								fc.addMessage(fieldName, new FacesMessage("Please Select Seri at row "+(i+1)));
								valid=false;
							}else if(warelists.get(i).getQuantity().equals("")){
								fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
								fc.addMessage(fieldName, new FacesMessage("Please Enter Quantity at row "+(i+1)));
								valid=false;
							}else if(!CommonValidate.validateNumber(warelists.get(i).getQuantity())){
								fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
								fc.addMessage(fieldName, new FacesMessage("Quantity must be number"));
								valid=false;
							}
						}		
					}
			}
		}
		return valid;
	}
	
	public String manualStockView(){
		bo.getmanualStckView(personID,stockDataBean);
		return "";
	}
	
	public String manualStockEdit(){
		BigDecimal quantity=BigDecimal.valueOf(0),subquan=BigDecimal.valueOf(0),addquan=BigDecimal.valueOf(0);
		try{				
			stockDataBean.setSerin(stockDataBean.getSeri());
			bo.getModelDetails(personID,stockDataBean);
			System.out.println("model "+stockDataBean.getModelNo()+" seri "+stockDataBean.getSeri());
			for (int i = 0; i < stockDataBean.getStklists().size(); i++) {
				if(i!=(Integer.parseInt(stockDataBean.getSerialNo())-1)){
					if(stockDataBean.getModelNo().equals(stockDataBean.getStklists().get(i).getModelNo()) && 
							stockDataBean.getSeri().equals(stockDataBean.getStklists().get(i).getSeri())){
						if(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity1()).compareTo(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity()))==-1){
							subquan=subquan.add(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity()).subtract(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity1())));
						}else if(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity1()).compareTo(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity()))==1){
							addquan=addquan.add(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity1()).subtract(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity())));
						}
					}
				}
			}
			quantity=new BigDecimal(stockDataBean.getQuantity()).subtract(subquan).add(addquan).add(new BigDecimal(stockDataBean.getQuantity1()));
			System.out.println("sub quan "+subquan+" - add "+addquan+" quan "+stockDataBean.getQuantity()+" quantity "+quantity);
			stockDataBean.setChkQuantity(String.valueOf(quantity));
			StockDataBean stocks=new StockDataBean();
			stocks.setSerialNo(stockDataBean.getSerialNo());
			stocks.setModelNo(stockDataBean.getStklists().get(Integer.parseInt(stockDataBean.getSerialNo())-1).getModelNo());
			stocks.setQuantity(stockDataBean.getStklists().get(Integer.parseInt(stockDataBean.getSerialNo())-1).getQuantity());
			stocks.setQuantity1(stockDataBean.getStklists().get(Integer.parseInt(stockDataBean.getSerialNo())-1).getQuantity());
			stocks.setSellingPrice(stockDataBean.getStklists().get(Integer.parseInt(stockDataBean.getSerialNo())-1).getSellingPrice());
			stocks.setTotalPrice(stockDataBean.getStklists().get(Integer.parseInt(stockDataBean.getSerialNo())-1).getTotalPrice());
			stocks.setSflag(true);
			stocks.setSflag1(false);
			stocks.setSeri(stockDataBean.getStklists().get(Integer.parseInt(stockDataBean.getSerialNo())-1).getSeri());
			stocks.setWarehouseName(stockDataBean.getStklists().get(Integer.parseInt(stockDataBean.getSerialNo())-1).getWarehouseName());
			stockDataBean.getStklists().set(Integer.parseInt(stockDataBean.getSerialNo())-1, stocks);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public void quantityChangeEdit(ValueChangeEvent v){
		System.out.println("quantity -- "+v.getNewValue().toString());
		boolean valid=true;String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();		
		String quan=v.getNewValue().toString();
		try{
			String serialNo=v.getComponent().getAttributes().get("serial").toString();
			if(new BigDecimal(quan).compareTo(new BigDecimal(stockDataBean.getChkQuantity()))==1){
				fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Only "+stockDataBean.getChkQuantity()+ " Quantity in "+stockDataBean.getWarehouseName()));
				valid=false;
			}			
			if(valid==true){
				System.out.println("true");
				BigDecimal total=BigDecimal.valueOf(0);
				StockDataBean stocks=new StockDataBean();
				stocks.setQuantity(v.getNewValue().toString());
				stocks.setSerialNo(serialNo);
				stocks.setSflag(false);
				stocks.setSflag1(true);
				stocks.setQuantity1(stockDataBean.getStklists().get(Integer.parseInt(serialNo)-1).getQuantity1());
				stocks.setModelNo(stockDataBean.getStklists().get(Integer.parseInt(serialNo)-1).getModelNo());
				stocks.setSeri(stockDataBean.getStklists().get(Integer.parseInt(serialNo)-1).getSeri());
				stocks.setSellingPrice(stockDataBean.getStklists().get(Integer.parseInt(serialNo)-1).getSellingPrice());
				stocks.setTotalPrice(String.valueOf(new BigDecimal(stockDataBean.getStklists().get(Integer.parseInt(serialNo)-1).getSellingPrice()).
						multiply(new BigDecimal(v.getNewValue().toString()))));
				stocks.setWarehouseName(stockDataBean.getStklists().get(Integer.parseInt(serialNo)-1).getWarehouseName());
				stockDataBean.getStklists().set(Integer.parseInt(serialNo)-1, stocks);
				for (int i = 0; i < stockDataBean.getStklists().size(); i++) {					
					if(!stockDataBean.getStklists().get(i).getQuantity().equals("")){
						total=total.add((new BigDecimal(stockDataBean.getStklists().get(i).getQuantity()).
								multiply(new BigDecimal(stockDataBean.getStklists().get(i).getSellingPrice()))));
					}
				}				
				stockDataBean.setTotalPrice(String.valueOf(total));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String manualStkupdate(){
		System.out.println("manual stck update");
		boolean valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			for (int i = 0; i < stockDataBean.getStklists().size(); i++) {
				if(stockDataBean.getStklists().get(i).getQuantity().equals("")){						
					fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
					fc.addMessage(fieldName, new FacesMessage("Please Enter Quantity at row "+(i+1)));
					valid=false;
				}else if(!CommonValidate.validateNumber(stockDataBean.getStklists().get(i).getQuantity())){
					fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
					fc.addMessage(fieldName, new FacesMessage("Quantity must be number"));
					valid=false;
				}
			}	
			if(valid==true){
				bo.updateManualStockOut(personID,stockDataBean);
				RequestContext.getCurrentInstance().execute("PF('editDialog').hide();");
				RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public String manualStockDelete(){
		bo.deleteManualStockOut(personID,stockDataBean);
		return "";		
	}
	
/*	STOCK OUT*/
	
	public String stockOutPage() throws SQLException{
		stockDataBean.setFromDate(null);
		stockDataBean.setToDate(null);
		warelists=StockOutJDBC.getStockOut();
		return "stockOutForm";
	}
	
	public String stockoutview() throws SQLException{
		if(outValidate(true)){
			warelists=StockOutJDBC.getStockOutView(stockDataBean);
			System.out.println("size "+warelists.size());
		}		
		return "";
	}
	
	public boolean outValidate(boolean valid){
		valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		if(stockDataBean.getFromDate()==null){
			fieldName=CommonValidate.findComponentInRoot("fdate").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select From Date "));
			valid=false;
		}
		if(stockDataBean.getToDate()==null){
			fieldName=CommonValidate.findComponentInRoot("tdate").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select To Date "));
			valid=false;
		}
		return valid;
	}
	
	/*STOCK VIEW */
	
	public String stockViewPage(){
		System.out.println("stock view");
		stockDataBean.setModelNo("");stockDataBean.setSeri("");stockDataBean.setFlag(false);
		stockDataBean.setCmtProducts(null);stockDataBean.setSeris(null);
		bo.getModels(personID,stockDataBean);
		System.out.println("models "+stockDataBean.getCmtProducts());
		return "stockView";
	}
	
	/*public void modelsChange(ValueChangeEvent v){
		System.out.println("model "+v.getNewValue().toString());
		stockDataBean.setFlag(false);
		stockDataBean.setModelNo(v.getNewValue().toString());
		bo.getModels(personID,stockDataBean);
		System.out.println("seri "+stockDataBean.getSeris());
	}*/
	
	public String stockView(){
		stockDataBean.setStatus("view");
		if(viewValidate(true)){
			warelists=bo.getStockDetails(personID,stockDataBean);
			stockDataBean.setFlag(true);
		}
		return "";
	}
	
	public boolean viewValidate(boolean valid){
		valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		if(stockDataBean.getModelNo().equals("")){
			fieldName=CommonValidate.findComponentInRoot("model").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select Model "));
			valid=false;
		}
		return valid;
	}
	
	/*STOCK ADJUSTMENT*/
	
	public String stkAdjustmentPage()
	{
		stockDataBean.setType("");
		stockDataBean.setItemName("");
		stockDataBean.setCategory("");
		stockDataBean.setSeris(null);
		stockDataBean.setModelNo("");stockDataBean.setFlag(false);
		stockDataBean.setFlag1(false);
		return "stockAdjustment";
	}

	public void typeChange(ValueChangeEvent v){
		System.out.println("type "+v.getNewValue());
		stockDataBean.setType(v.getNewValue().toString());
		bo.getProductDetails(personID,stockDataBean);
		stockDataBean.setFlag(false);stockDataBean.setFlag1(false);
	}
	public void categoryChange(ValueChangeEvent v){
		System.out.println("category "+v.getNewValue());
		stockDataBean.setCategory(v.getNewValue().toString());
		bo.getItems(personID,stockDataBean);
		stockDataBean.setFlag(false);stockDataBean.setFlag1(false);
	}
	public String stockEdit(){
		stockDataBean.setStatus("edit");		
		if(editValidate(true)){
			warelists=bo.getStockDetails(personID,stockDataBean);
			stockDataBean.setFlag(true);
			if(warelists.size()>0) stockDataBean.setFlag1(true);
			else stockDataBean.setFlag1(false);
		}
		return "";
	}
	
	public boolean editValidate(boolean valid){
		valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		if(stockDataBean.getType().equals("")){
			fieldName=CommonValidate.findComponentInRoot("type").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select Type "));
			valid=false;
		}else{
			if(stockDataBean.getType().equals("raw")){
				if(stockDataBean.getItemName().equals("")){
					fieldName=CommonValidate.findComponentInRoot("item").getClientId();
					fc.addMessage(fieldName, new FacesMessage("Please Select Item Name "));
					valid=false;
				}
			}else if(stockDataBean.getType().equals("ready")){
				if(stockDataBean.getModelNo().equals("")){
					fieldName=CommonValidate.findComponentInRoot("model").getClientId();
					fc.addMessage(fieldName, new FacesMessage("Please Select Model "));
					valid=false;
				}
			}
		}
		return valid;
	}
	
	public void quantityEditChange(ValueChangeEvent v){
		System.out.println("edit quantity "+v.getNewValue().toString());
		//System.out.println("=========="+v.getComponent().getAttributes().get("motive").toString());
		try{
			String quan=v.getNewValue().toString();
			String serial=v.getComponent().getAttributes().get("serial").toString();
			if(stockDataBean.getType().equals("ready")){
				StockDataBean stocks=new StockDataBean();
				stocks.setSerialNo(serial);
				stocks.setWarehouseName(warelists.get(Integer.parseInt(serial)-1).getWarehouseName());
				stocks.setQuantity(warelists.get(Integer.parseInt(serial)-1).getQuantity());
				stocks.setSellingPrice(warelists.get(Integer.parseInt(serial)-1).getSellingPrice());
				stocks.setModelNo(warelists.get(Integer.parseInt(serial)-1).getModelNo());
				stocks.setSeri(warelists.get(Integer.parseInt(serial)-1).getSeri());
				System.out.println("-----"+warelists.get(Integer.parseInt(serial)-1).getMotive());
				stocks.setMotive(warelists.get(Integer.parseInt(serial)-1).getMotive());
				stocks.setQuantity1(quan);
				stocks.setMnaualStockID(warelists.get(Integer.parseInt(serial)-1).getMnaualStockID());
				warelists.set(Integer.parseInt(serial)-1, stocks);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String updateStock(){
		boolean valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			int count=0;
			for (int i = 0; i < warelists.size(); i++) {
				if(warelists.get(i).getQuantity1().equals("")){
					count++;
				}
			}
			if(count==warelists.size()){
				fieldName=CommonValidate.findComponentInRoot("msgs").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Please edit atleast one record "));
				valid=false;
			}else{
				for (int i = 0; i < warelists.size(); i++) {
					if(!warelists.get(i).getQuantity1().equals("")){
						if(!CommonValidate.validateNumber(warelists.get(i).getQuantity1())){
							fieldName=CommonValidate.findComponentInRoot("msgs").getClientId();
							fc.addMessage(fieldName, new FacesMessage("Quantity must be Number "));
							valid=false;
						}
					}
				}
			}
			if(valid==true){
				stockDataBean.setWarelists(warelists);
				bo.updateStocks(personID,stockDataBean);
				RequestContext.getCurrentInstance().execute("PF('editStkDialog').show();");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	/*STOCK REPORT*/
	
	public String stockReport(){
		stockDataBean.setType("");
		stockDataBean.setModelNo("");
		stockDataBean.setFlag(false);
		return "stockReport";
	}
	
	public void categoryValueChange(ValueChangeEvent vc){
		try{
			 String str=vc.getNewValue().toString();
			 stockDataBean.setType(str);
			 if(str.equalsIgnoreCase("raw")){
				 stockDataBean.setFlag(true);				 
				 warelists=new ArrayList<StockDataBean>();
				 warelists=bo.getrawstockreport(personID,stockDataBean);  
				 System.out.println("size----"+warelists.size());
			 }else if(str.equalsIgnoreCase("ready")){
				 stockDataBean.setModelNo("");
				 bo.getProductDetails(personID,stockDataBean);
				 stockDataBean.setFlag(false);
			 }
		}catch(Exception e){
			   e.printStackTrace();
		}
	}
	public void modelValueChange(ValueChangeEvent vc){
		try{
			String str=vc.getNewValue().toString();
			stockDataBean.setModelNo(str);
			warelists=bo.getrawstockreport(personID,stockDataBean);
			stockDataBean.setFlag(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*OPENING STOCK*/
	
	public String openingStockPage(){
		stockDataBean.setDate(date);
		stockDataBean.setFlag(true);
		stockDataBean.setSflag1(false);
		stockDataBean.setSflag(false);
		stockDataBean.setType("");
		stockDataBean.setStatus("view");
		bo.getOpeningStockDetails(personID,stockDataBean);
		return "openingStock";
	}
	
	public String addOpeningStock(){
		stockDataBean.setItemName("");
		stockDataBean.setQuantity("");
		stockDataBean.setModelNo("");
		stockDataBean.setSeri("");
		stockDataBean.setType("");
		stockDataBean.setStatus("add");
		return "";
	}
	
	public void opngCatgrySelect(ValueChangeEvent v){
		System.out.println("type "+v.getNewValue().toString());
		stockDataBean.setFlag(false);
		stockDataBean.setType(v.getNewValue().toString());
		if(stockDataBean.getStatus().equals("add")){
			bo.getOpeningStockProducts(personID,stockDataBean);
		}else if(stockDataBean.getStatus().equals("view")){
			stockDataBean.setStatus("change");
			bo.getOpeningStockDetails(personID,stockDataBean);
			if(stockDataBean.getType().equals("raw")){
				stockDataBean.setSflag(true);stockDataBean.setSflag1(false);
			}else if(stockDataBean.getType().equals("ready")){
				stockDataBean.setSflag1(true);stockDataBean.setSflag(false);
			}
			stockDataBean.setStatus("view");
		}		
	}
	
	public void modelSelect(ValueChangeEvent v){
		System.out.println("model "+v.getNewValue());
		stockDataBean.setModelNo(v.getNewValue().toString());
		bo.getModelSeris(personID,stockDataBean);
	}
	
	public String saveOpeningStock(){
		System.out.println("save opening stock mb");
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		if(opngStkValidate(true)){
			String status=bo.saveOpeningStock(personID,stockDataBean);
			if(status.equals("Exist")){
				fieldName=CommonValidate.findComponentInRoot("errmsg").getClientId();
				fc.addMessage(fieldName, new FacesMessage("Already add the opening stock for this "));
			}else{
				RequestContext.getCurrentInstance().execute("PF('opngStkreg').hide();");
				RequestContext.getCurrentInstance().execute("PF('confirm').show();");
				stockDataBean.setType("");
			}
		}
		return "";
	}
	
	public boolean opngStkValidate(boolean valid){
		valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		if(stockDataBean.getType().equals("")){
			fieldName=CommonValidate.findComponentInRoot("type").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Select Category "));
			valid=false;
		}else{
			if(stockDataBean.getType().equals("raw")){
				if(stockDataBean.getItemName().equals("")){
					fieldName=CommonValidate.findComponentInRoot("item").getClientId();
					fc.addMessage(fieldName, new FacesMessage("Please Select Item Name "));
					valid=false;
				}
			}else if(stockDataBean.getType().equals("ready")){
				if(stockDataBean.getModelNo().equals("")){
					fieldName=CommonValidate.findComponentInRoot("model").getClientId();
					fc.addMessage(fieldName, new FacesMessage("Please Select Model "));
					valid=false;
				}
				if(stockDataBean.getSeri().equals("")){
					fieldName=CommonValidate.findComponentInRoot("seri").getClientId();
					fc.addMessage(fieldName, new FacesMessage("Please Select Seri "));
					valid=false;
				}
			}
		}
		if(stockDataBean.getQuantity().equals("")){
			fieldName=CommonValidate.findComponentInRoot("quantity").getClientId();
			fc.addMessage(fieldName, new FacesMessage("Please Enter Quantity "));
			valid=false;
		}
		return valid;
	}
	
	
//	STANLEY CODE
	
	/*CMT RETURN */
	
	public List<StockDataBean> test=null; 
	 private List<String> itemnameList=null;
	 private List<String> seriNo=null;
	
	public List<StockDataBean> getTest() {
		return test;
	}
	public void setTest(List<StockDataBean> test) {
		this.test = test;
	}
	public List<String> getItemnameList() {
		return itemnameList;
	}
	public void setItemnameList(List<String> itemnameList) {
		this.itemnameList = itemnameList;
	}
	public List<String> getSeriNo() {
		return seriNo;
	}
	public void setSeriNo(List<String> seriNo) {
		this.seriNo = seriNo;
	}

	public void seriNo(ValueChangeEvent v)
	{
		System.out.println("-------------- seriNo -----------------");
		try{
			stockDataBean.setSeriNolist(""); 
			String valuechange=v.getNewValue().toString();
			seriNo=bo.getseriNo(valuechange,personID);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
 public void quantites(ValueChangeEvent v){
	 System.out.println("-------------valuechange for quantity-------------");
	 	String serialNo="";
	 	String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		boolean flag=true;
	 try{
		 serialNo=v.getComponent().getAttributes().get("cmtSerial").toString();
		 String quantity =v.getNewValue().toString();
		 StockDataBean stockbean=new StockDataBean();
		 stockbean.setSerialNo(serialNo); 		
		 stockbean.setShop(test.get(Integer.parseInt(serialNo)-1).getShop());    
		 stockbean.setModel(test.get(Integer.parseInt(serialNo)-1).getModel()); 
		 stockbean.setSeriDetails(test.get(Integer.parseInt(serialNo)-1).getSeriDetails()); 
		 stockbean.setStock(test.get(Integer.parseInt(serialNo)-1).getStock()); 
		 stockbean.setPrice(test.get(Integer.parseInt(serialNo) - 1).getPrice());
		 stockbean.setStockID(test.get(Integer.parseInt(serialNo)-1).getStockID()); 
		 System.out.println("---------------------------setprice--------------->"+stockbean.getPrice()); 
		 System.out.println("-------------serialNo----->"+serialNo); 
		 if(new BigDecimal(quantity).compareTo(new BigDecimal(stockbean.getStock()))==1){
			 	fieldName=CommonValidate.findComponentInRoot("errmsgs1").getClientId();
				fc.addMessage(fieldName, new FacesMessage(" Enter the Quantity less than stockin quantity"));	
				flag=false;
		 }else{
			 System.out.println("===============else condition==============");
			 stockbean.setNetprice(new BigDecimal(quantity).multiply(new BigDecimal(stockbean.getPrice())).toString());
		 }		  
		 test.set(Integer.parseInt(serialNo) -1, stockbean);
		 System.out.println("-------------Netprice------>"+stockbean.getNetprice()); 		 
		 }catch(Exception e){
		 e.printStackTrace();
	 }
 }	

public String tableClear(){
	 try{
		 Date d=new Date();
		 stockDataBean.setDate(d);
		 stockDataBean.setItemname(""); 
		 stockDataBean.setSeriNolist(""); 
		 stockDataBean.setCmtflag(false); 
		 stockDataBean.setModel(""); 
		 stockDataBean.setSeriDetails(""); 
		 stockDataBean.setPrinterflag(false);
	 }catch(Exception e){
	  e.printStackTrace();
	 }
	 return "";
	}

public void cmtTable(ValueChangeEvent v){
	  test=new ArrayList<StockDataBean>();     
	  String cmtView=v.getNewValue().toString();
	  try{
	   stockDataBean.setSeriNolist(cmtView); 
	   test=bo.getcmtTable(personID,stockDataBean); 
	   System.out.println("test size"+test.size()); 
	   if(cmtView.equals("")){
	    stockDataBean.setCmtflag(false);  
	   }else {
	    stockDataBean.setCmtflag(true); 
	   }
	  }catch(Exception e){
	   e.printStackTrace();
	  }
	 }

public void printerTable(ValueChangeEvent v){
	  test=new ArrayList<StockDataBean>();     
	  String printerView=v.getNewValue().toString();
	  try{
	   stockDataBean.setSeriNolist(printerView); 
	   test=bo.getprinterTable(personID,stockDataBean); 
	   System.out.println("test size"+test.size()); 
	   if(printerView.equals("")){
	    stockDataBean.setPrinterflag(false);  
	   }else {
	    stockDataBean.setPrinterflag(true); 
	   }
	  }catch(Exception e){
	   e.printStackTrace();
	  }
	 }
/*========================25-02-2017===========*/
public String cmtSave(){ 
	System.out.println("=================inside cmtsave======================");  
	String fieldName;
	FacesContext fc=FacesContext.getCurrentInstance();
	boolean flag=true;
	try{
		stockDataBean.setStklists(test); 
		for(int i = 0; i < stockDataBean.getStklists().size(); i++){  
			try{
				if(stockDataBean.getStklists().get(i).getQty()==null){   
					fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
					fc.addMessage(fieldName, new FacesMessage(" Enter the Quantity "));	
					flag=false;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			}
			if (flag==true){
				stockDataBean.setCmttableview(test); 
				test=bo.getcmtSave(stockDataBean,personID); 
				RequestContext.getCurrentInstance().execute("PF('stockDialog').hide();");
				RequestContext.getCurrentInstance().execute("PF('confirm').show();");	
		
	}}catch(Exception e){
		e.printStackTrace();
	}
	return "test";	
}

public String printerSave(){ 
	System.out.println("=================inside printersave======================");  
	String fieldName;
	FacesContext fc=FacesContext.getCurrentInstance();
	boolean flag=true;
	try{
		stockDataBean.setStklists(test); 
		for(int i = 0; i < stockDataBean.getStklists().size(); i++){  
			try{
				if(stockDataBean.getStklists().get(i).getQty()==null){   
					fieldName=CommonValidate.findComponentInRoot("errmsgs").getClientId();
					fc.addMessage(fieldName, new FacesMessage(" Enter the Quantity "));	
					flag=false;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			}
			if (flag==true){
				stockDataBean.setPrintertableview(test); 
				test=bo.getprinterSave(stockDataBean,personID); 
				RequestContext.getCurrentInstance().execute("PF('stockDialog').hide();");
				RequestContext.getCurrentInstance().execute("PF('confirm').show();");	
		
	}}catch(Exception e){
		e.printStackTrace();
	}
	return "test";	
}
public String cmtModel()
{
	try{
		stockDataBean.setCmtflag(false); 
		stockDataBean.setItemname(""); 
		stockDataBean.setSeriNolist(""); 
		itemnameList=bo.getcmtModelName(personID);
		Collections.sort(itemnameList);
		returnList=new ArrayList<StockDataBean>();
		returnList=bo.getmtReturn(stockDataBean,personID);
	}catch(Exception e){
		e.printStackTrace();
	}
	return "returnCmt";
}
public String printerModel()
{
	try{
		stockDataBean.setPrinterflag(false);
		stockDataBean.setItemname(""); 
		stockDataBean.setSeriNolist(""); 
		itemnameList=bo.getprinterModelName(personID);
		Collections.sort(itemnameList);
		returnList=new ArrayList<StockDataBean>();
		returnList=bo.getprinterReturn(stockDataBean,personID);
	}catch(Exception e){
		e.printStackTrace();
	}
	return "returnPrinter";
}
public void getseriNo(ValueChangeEvent v)
{
	System.out.println("-------------- seriNo -----------------");
	try{
		stockDataBean.setSeriNolist(""); 
		String modelname=v.getNewValue().toString();
		seriNo=bo.getprinterseriNo(modelname,personID);
		System.out.println("seriNo "+seriNo.size());
	}catch(Exception e){
		e.printStackTrace();
	}
}
 public String cmtView()
 {
	 try{		
			returnList1=new ArrayList<StockDataBean>();
			returnList1=bo.getmtReturn1(stockDataBean,personID);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ""; 
 }
public String cmtDelete(){
	bo.getcmtDelete(stockDataBean,personID);
	return "";
}

public String cmtEdit(){
	try{
		returnList2=new ArrayList<StockDataBean>();
		returnList2=bo.getedit(stockDataBean,personID);
		System.out.println("list si----"+returnList2.size());
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public String cmtreturnEdit(){
	try{
		if(returnList2.get(0).getReturnquantity1()!=null){
			System.out.println("stock -----"+stockDataBean.getReturnID());
			String status=bo.cmtreturnEdit(personID,returnList2,stockDataBean);
			if("Success".equalsIgnoreCase(status)){
				RequestContext.getCurrentInstance().execute("PF('editDialog').hide()");
				RequestContext.getCurrentInstance().execute("PF('editDialogconfirm').show()");				
			}
		}
	}catch(Exception v){
		v.printStackTrace();
	}
	return"";
}



/*PRINTER RETURN*/

public String printerView()
{
	 try{		
			returnList1=new ArrayList<StockDataBean>();
			returnList1=bo.getprinterReturn1(stockDataBean,personID);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ""; 
}

public String printerEdit(){
	try{
		returnList2=new ArrayList<StockDataBean>();
		returnList2=bo.getPrinteredit(stockDataBean,personID);
		System.out.println("list si----"+returnList2.size());
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public String printerreturnEdit(){
	BigDecimal quant=BigDecimal.valueOf(0); 
	BigDecimal quant1=BigDecimal.valueOf(0); 
	BigDecimal quant2=BigDecimal.valueOf(0);
	String fieldName;
	FacesContext fc=FacesContext.getCurrentInstance();
	try{
		if(returnList2.get(0).getReturnquantity1()!=null){
			
			String status=bo.printerreturnEdit(personID,returnList2,stockDataBean);
			System.out.println("status "+status+"==="+stockDataBean.getAvail_quant());
			if("Success".equalsIgnoreCase(status)){
				RequestContext.getCurrentInstance().execute("PF('editDialog').hide()");
				RequestContext.getCurrentInstance().execute("PF('editDialogconfirm').show()");				
			}else{
					fieldName=CommonValidate.findComponentInRoot("returnquant").getClientId();
					fc.addMessage(fieldName, new FacesMessage("Only "+stockDataBean.getAvail_quant()+ " Quantity Available for this Printer"));
				
			}
		}
	}catch(Exception v){
		v.printStackTrace();
	}
	return"";
}


public String printerDelete(){
	System.out.println("Inside printerDelete");
	bo.getprinterDelete(stockDataBean,personID);
	return "";
}
/*STOCK REMINDER*/

public void modelsReminder(){
	stockDataBean.setWarelists(null);
	bo.getModelsDetails(personID,stockDataBean);
}

/*Warehouse REMINDER*/
public void warehouseReminder(){
	stockDataBean.setWarelists(null);
	bo.getWarehouseActivity(personID,stockDataBean);
}

public String bestSeller(){
	stockDataBean.setWarelists(null);
	bo.getbestSeller(personID,stockDataBean);
	return"";
}
}
