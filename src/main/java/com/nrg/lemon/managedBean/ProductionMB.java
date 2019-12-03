package com.nrg.lemon.managedBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.ocsp.Req;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.nrg.lemon.bo.LemonBo;
import com.nrg.lemon.domain.ProductionDataBean;
import com.nrg.lemon.util.CommonValidate;


@ManagedBean(name="productionMB")
@RequestScoped
public class ProductionMB {

	String personID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PersonID");
	String clientID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ClientID");
	ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	LemonBo bo=(LemonBo)ctx.getBean("bo");
	
	ProductionDataBean productionDataBean=new ProductionDataBean();
	private List<ProductionDataBean> cutterorderlist=null;
	private List<ProductionDataBean> cmtorderlist=null;
	List<ProductionDataBean> printerorderlist=null;
	List<ProductionDataBean> printerViewList=null;
	List<ProductionDataBean> productionList=new ArrayList<ProductionDataBean>();
	List<String> categoryList=new ArrayList<String>();
	private List<String> cutterList=null;
	private List<String> modelList=null;
	private List<ProductionDataBean> cutterPurchaselist=null;
	private List<String> cmtList=null;
	private List<ProductionDataBean> cmtPurchaselist=null;
	private List<ProductionDataBean> cmtHoldinglist=null;
	private List<ProductionDataBean> printerHoldinglist=null;
	private List<ProductionDataBean> printerPurchaselist=null;
	private List<String> modelNamelist=null;
	private List<String> seriList=null;
	private List<String> seriList1=null;
	private List<String> printerModellist=null;
	private List<String> invoiceList=null;
	private List<ProductionDataBean> cmtReceivelist=null;
	private List<ProductionDataBean> stockList=null;
	private boolean status=false;
	private List<ProductionDataBean> productList=null;
	private List<ProductionDataBean> producthistoryList=null;
	private List<String> cutternameList=null;
	private boolean tableflag=false;
	private List<String> tablelist=null;
	private List<String> tablecolumnlist=null;
	private List<String> tablerowlist=null;
	private List<ProductionDataBean> tablevaluelist=null;
	private boolean aflag=false;
	private boolean bflag=false;
	private boolean cflag=false;
	private boolean dflag=false;
	private boolean eflag=false;
	private boolean fflag=false;
	private boolean gflag=false;
	private boolean hflag=false;
	private boolean iflag=false;
	private boolean jflag=false;
	private List<ProductionDataBean> tableviewlist=null;
	private List<ProductionDataBean> tablehistorylist=null;
	
	
	public List<ProductionDataBean> getTableviewlist() {
		return tableviewlist;
	}

	public void setTableviewlist(List<ProductionDataBean> tableviewlist) {
		this.tableviewlist = tableviewlist;
	}

	public List<ProductionDataBean> getTablehistorylist() {
		return tablehistorylist;
	}

	public void setTablehistorylist(List<ProductionDataBean> tablehistorylist) {
		this.tablehistorylist = tablehistorylist;
	}

	public boolean isAflag() {
		return aflag;
	}

	public void setAflag(boolean aflag) {
		this.aflag = aflag;
	}

	public boolean isBflag() {
		return bflag;
	}

	public void setBflag(boolean bflag) {
		this.bflag = bflag;
	}

	public boolean isCflag() {
		return cflag;
	}

	public void setCflag(boolean cflag) {
		this.cflag = cflag;
	}

	public boolean isDflag() {
		return dflag;
	}

	public void setDflag(boolean dflag) {
		this.dflag = dflag;
	}

	public boolean isEflag() {
		return eflag;
	}

	public void setEflag(boolean eflag) {
		this.eflag = eflag;
	}

	public boolean isFflag() {
		return fflag;
	}

	public void setFflag(boolean fflag) {
		this.fflag = fflag;
	}

	public boolean isGflag() {
		return gflag;
	}

	public void setGflag(boolean gflag) {
		this.gflag = gflag;
	}

	public boolean isHflag() {
		return hflag;
	}

	public void setHflag(boolean hflag) {
		this.hflag = hflag;
	}

	public boolean isIflag() {
		return iflag;
	}

	public void setIflag(boolean iflag) {
		this.iflag = iflag;
	}

	public boolean isJflag() {
		return jflag;
	}

	public void setJflag(boolean jflag) {
		this.jflag = jflag;
	}

	public List<String> getSeriList1() {
		return seriList1;
	}

	public void setSeriList1(List<String> seriList1) {
		this.seriList1 = seriList1;
	}

	public List<String> getTablerowlist() {
		return tablerowlist;
	}

	public void setTablerowlist(List<String> tablerowlist) {
		this.tablerowlist = tablerowlist;
	}

	public List<String> getTablelist() {
		return tablelist;
	}

	public void setTablelist(List<String> tablelist) {
		this.tablelist = tablelist;
	}

	public List<String> getTablecolumnlist() {
		return tablecolumnlist;
	}

	public void setTablecolumnlist(List<String> tablecolumnlist) {
		this.tablecolumnlist = tablecolumnlist;
	}

	public List<ProductionDataBean> getTablevaluelist() {
		return tablevaluelist;
	}

	public void setTablevaluelist(List<ProductionDataBean> tablevaluelist) {
		this.tablevaluelist = tablevaluelist;
	}

	public boolean isTableflag() {
		return tableflag;
	}

	public void setTableflag(boolean tableflag) {
		this.tableflag = tableflag;
	}

	public List<String> getCutternameList() {
		return cutternameList;
	}

	public void setCutternameList(List<String> cutternameList) {
		this.cutternameList = cutternameList;
	}

	public List<ProductionDataBean> getProducthistoryList() {
		return producthistoryList;
	}

	public void setProducthistoryList(List<ProductionDataBean> producthistoryList) {
		this.producthistoryList = producthistoryList;
	}

	public List<ProductionDataBean> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductionDataBean> productList) {
		this.productList = productList;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<ProductionDataBean> getStockList() {
		return stockList;
	}

	public void setStockList(List<ProductionDataBean> stockList) {
		this.stockList = stockList;
	}

	public List<ProductionDataBean> getCmtReceivelist() {
		return cmtReceivelist;
	}

	public void setCmtReceivelist(List<ProductionDataBean> cmtReceivelist) {
		this.cmtReceivelist = cmtReceivelist;
	}

	public List<String> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<String> invoiceList) {
		this.invoiceList = invoiceList;
	}
	
	public List<String> getPrinterModellist() {
		return printerModellist;
	}

	public void setPrinterModellist(List<String> printerModellist) {
		this.printerModellist = printerModellist;
	}

	public List<String> getSeriList() {
		return seriList;
	}

	public void setSeriList(List<String> seriList) {
		this.seriList = seriList;
	}

	public List<String> getModelNamelist() {
		return modelNamelist;
	}

	public void setModelNamelist(List<String> modelNamelist) {
		this.modelNamelist = modelNamelist;
	}

	public List<ProductionDataBean> getPrinterPurchaselist() {
		return printerPurchaselist;
	}

	public void setPrinterPurchaselist(List<ProductionDataBean> printerPurchaselist) {
		this.printerPurchaselist = printerPurchaselist;
	}

	public List<ProductionDataBean> getPrinterHoldinglist() {
		return printerHoldinglist;
	}

	public void setPrinterHoldinglist(List<ProductionDataBean> printerHoldinglist) {
		this.printerHoldinglist = printerHoldinglist;
	}

	private List<String> printerList=null;
	
	public List<String> getPrinterList() {
		return printerList;
	}

	public void setPrinterList(List<String> printerList) {
		this.printerList = printerList;
	}

	public List<ProductionDataBean> getCmtHoldinglist() {
		return cmtHoldinglist;
	}

	public void setCmtHoldinglist(List<ProductionDataBean> cmtHoldinglist) {
		this.cmtHoldinglist = cmtHoldinglist;
	}

	public List<ProductionDataBean> getCmtPurchaselist() {
		return cmtPurchaselist;
	}

	public void setCmtPurchaselist(List<ProductionDataBean> cmtPurchaselist) {
		this.cmtPurchaselist = cmtPurchaselist;
	}

	public List<String> getCmtList() {
		return cmtList;
	}

	public void setCmtList(List<String> cmtList) {
		this.cmtList = cmtList;
	}

	public List<ProductionDataBean> getPrinterViewList() {
		return printerViewList;
	}

	public void setPrinterViewList(List<ProductionDataBean> printerViewList) {
		this.printerViewList = printerViewList;
	}
	
	public List<ProductionDataBean> getCutterPurchaselist() {
		return cutterPurchaselist;
	}

	public void setCutterPurchaselist(List<ProductionDataBean> cutterPurchaselist) {
		this.cutterPurchaselist = cutterPurchaselist;
	}

	public List<String> getModelList() {
		return modelList;
	}

	public void setModelList(List<String> modelList) {
		this.modelList = modelList;
	}

	public List<String> getCutterList() {
		return cutterList;
	}

	public void setCutterList(List<String> cutterList) {
		this.cutterList = cutterList;
	}

	public List<ProductionDataBean> getPrinterorderlist() {
		return printerorderlist;
	}

	public void setPrinterorderlist(List<ProductionDataBean> printerorderlist) {
		this.printerorderlist = printerorderlist;
	}

	public List<ProductionDataBean> getProductionList() {
		return productionList;
	}

	public void setProductionList(List<ProductionDataBean> productionList) {
		this.productionList = productionList;
	}

	public List<String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}

	public List<ProductionDataBean> getCmtorderlist() {
		return cmtorderlist;
	}

	public void setCmtorderlist(List<ProductionDataBean> cmtorderlist) {
		this.cmtorderlist = cmtorderlist;
	}

	public List<ProductionDataBean> getCutterorderlist() {
		return cutterorderlist;
	}

	public void setCutterorderlist(List<ProductionDataBean> cutterorderlist) {
		this.cutterorderlist = cutterorderlist;
	}

	public ProductionDataBean getProductionDataBean() {
		return productionDataBean;
	}

	public void setProductionDataBean(ProductionDataBean productionDataBean) {
		this.productionDataBean = productionDataBean;
	}
	
//***CUTTER***//
	public String cutterPage(){
		try{
			cutterList=new ArrayList<String>();
			cutterList=bo.getcutterList(personID,clientID);
			productionDataBean.setCutter("");
			productionDataBean.setFromDate(null);
			productionDataBean.setToDate(null);
			cutterPurchaselist=new ArrayList<ProductionDataBean>();
			cutterPurchaselist=bo.cutterPurchasedetails(personID,clientID,productionDataBean);
		}catch(Exception e){	
		}
		return "productionCutter";
	}
	
	public String cutterOrder(){
		try{
			modelList=new ArrayList<String>();
			cutterorderlist=new ArrayList<ProductionDataBean>();
			categoryList=new ArrayList<String>();
			categoryList=bo.getcategorynamelist(personID);
			cutternameList=new ArrayList<String>();
			cutternameList=bo.getcutterName(personID,clientID);
			for(int i=1;i<13;i++){
				productionDataBean=new ProductionDataBean();
				productionDataBean.setSerialNo(String.valueOf(i));
				cutterorderlist.add(productionDataBean);
			}
			productionDataBean.setOrderDate(new Date());
			productionDataBean.setInvoiceNo(bo.generateiInvoiceno(personID,clientID,productionDataBean));
		}catch(Exception e){	
		}
		return "";
	}
	
	public void categoryValuechange(){
		try{
				modelList=new ArrayList<String>();
				modelList=bo.modelList(personID,clientID,productionDataBean);
		}catch(Exception e){
		}
	}
	
	public void cuttermodelChange(){
		try{
			if(productionDataBean.getModelName()!=null){
				bo.modelChange(productionDataBean, personID, clientID);
			}
		}catch(Exception e){
		}
	}
	
	public  void cmtSerichange(ValueChangeEvent v){
		String value=v.getNewValue().toString();
		String serialNo=v.getComponent().getAttributes().get("serial").toString();
		BigDecimal amount=BigDecimal.valueOf(0);
		BigDecimal qty=BigDecimal.valueOf(0);
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(value!=null){
				String quantity=bo.getSerivaluechange(value,productionDataBean);
				if(quantity.equalsIgnoreCase("")){
					if(StringUtils.isEmpty(productionDataBean.getCategoryName())){
						   fieldName=CommonValidate.findComponentInRoot("cmtorder").getClientId(fc);
						   fc.addMessage(fieldName, new FacesMessage("please enter the valid seri"));
					}
				}else{
					ProductionDataBean pro=new ProductionDataBean();
					pro.setSerialNo(serialNo);
					pro.setQuantity(quantity);
					pro.setMotive(productionDataBean.getTotalseri());
					pro.setValue(new BigDecimal(productionDataBean.getPrinterr()).multiply(new BigDecimal(quantity)).toString());
					cmtorderlist.set(Integer.parseInt(serialNo)-1, pro);
					for(int i=0;i<cmtorderlist.size();i++){
					try{
						if(cmtorderlist.get(i).getValue()!=null){
							amount=amount.add(new BigDecimal(cmtorderlist.get(i).getValue()));
						}
						if(cmtorderlist.get(i).getQuantity()!=null){
							qty=qty.add(new BigDecimal(cmtorderlist.get(i).getQuantity()));
						}
					}catch(NullPointerException exception){
					}
					}
					productionDataBean.setTotalValue(amount.toString());
					productionDataBean.setTotalQuantity(qty.toString());
				}
			}
		}catch(Exception e){
		}
	}
	
	public void seriValuechange(ValueChangeEvent event){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			String value=event.getNewValue().toString();
			if(value!=null){
				if(value.length()>6 || value.length()<6){
					fieldName=CommonValidate.findComponentInRoot("hidden").getClientId();
					fc.addMessage(fieldName, new FacesMessage("seri must be 6 digits"));
				}
			}
		}catch(Exception w){
		}
	}
	
	public void quantityValuechange(ValueChangeEvent event){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal finalValue=BigDecimal.valueOf(0);
		try{
			String value=event.getNewValue().toString();
			String serialNo=event.getComponent().getAttributes().get("serial").toString();
			String seri=event.getComponent().getAttributes().get("seri").toString();
			//String motive=event.getComponent().getAttributes().get("motive").toString();
			if(value!=null){
				if(!CommonValidate.validateNumber(value)){
					fieldName=CommonValidate.findComponentInRoot("hidden").getClientId();
					fc.addMessage(fieldName, new FacesMessage("the quantity must be number"));
				}else{
						for(int i=0;i<cutterorderlist.size();i++){
							ProductionDataBean pro=new ProductionDataBean();
							pro.setQuantity(value);
							pro.setSerialNo(serialNo);
							//pro.setMotive(motive);
							pro.setSeri(seri);
							cutterorderlist.set(Integer.parseInt(serialNo)-1, pro);
							try{
								if(!cutterorderlist.get(i).getQuantity().equalsIgnoreCase("") ||cutterorderlist.get(i).getQuantity()!=null){	
									finalValue=(finalValue.add(new BigDecimal(cutterorderlist.get(i).getQuantity())));
								}
								}catch(NullPointerException e){
									
								}
							}
					productionDataBean.setTotalQuantity(finalValue.toString());
				}
			}
		}catch(Exception w){
		}
	}
	
	public void weigthChange(){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal result=BigDecimal.valueOf(0);
		BigDecimal saving=BigDecimal.valueOf(0);
		BigDecimal value=BigDecimal.valueOf(0);
		BigDecimal finalvalue=BigDecimal.valueOf(0);
		BigDecimal weightpercentage=BigDecimal.valueOf(0);
		float result1=0;float result2=0;float percent=100;
		try{
		if(StringUtils.isEmpty(productionDataBean.getTotalWeight())){
			fieldName=CommonValidate.findComponentInRoot("total").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the total weight"));
		}else{
			if(!productionDataBean.getRemainWeight().equals("")){
				/*result1=Integer.parseInt(productionDataBean.getRemainWeight())/Integer.parseInt(productionDataBean.getTotalQuantity());
				result=(new BigDecimal(productionDataBean.getTotalWeight()).subtract(new BigDecimal(result1)));
				productionDataBean.setResult(String.valueOf(result));
				saving=(new BigDecimal(productionDataBean.getPole()).subtract(new BigDecimal(result1)));
				productionDataBean.setSaving(saving.multiply(new BigDecimal(productionDataBean.getFabricPrice())).toString());
				value=(new BigDecimal(productionDataBean.getSellingPrice()).multiply(new BigDecimal(productionDataBean.getTotalQuantity())));
				productionDataBean.setValue(value.toString());*/
				result=(new BigDecimal(productionDataBean.getTotalWeight()).subtract(new BigDecimal(productionDataBean.getRemainWeight())));
				result1=Float.parseFloat(String.valueOf(result))/Float.parseFloat(productionDataBean.getTotalQuantity());
				finalvalue=new BigDecimal(result1).setScale(2,BigDecimal.ROUND_HALF_UP);
				productionDataBean.setResult(String.valueOf(finalvalue));
				saving=(new BigDecimal(productionDataBean.getPole()).subtract(new BigDecimal(productionDataBean.getResult())));
				productionDataBean.setSaving(saving.multiply(new BigDecimal(productionDataBean.getFabricPrice())).toString());
				System.out.println("cutter "+productionDataBean.getSellingPrice());
				value=(new BigDecimal(productionDataBean.getSellingPrice()).multiply(new BigDecimal(productionDataBean.getTotalQuantity())));
				productionDataBean.setValue(value.toString());
				//result2=new BigDecimal(productionDataBean.getRemainWeight()).divide(result).multiply(percent);
				result2=Float.parseFloat(String.valueOf(result))/Float.parseFloat(productionDataBean.getTotalWeight())*percent;
				weightpercentage=new BigDecimal(result2).setScale(2,BigDecimal.ROUND_HALF_UP);
				productionDataBean.setWeightPercentage(weightpercentage.toString());
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String saveCutterorder(){
		try{
			if(cutterOrdervalidation(true)){
				System.out.println("Inside validation");
				String status=bo.insertCutterorder(personID,clientID,productionDataBean,cutterorderlist);
				if("Success".equalsIgnoreCase(status)){ 
					RequestContext.getCurrentInstance().execute("PF('cutorder').hide();");
					RequestContext.getCurrentInstance().execute("PF('confirm').show();");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	private boolean cutterOrdervalidation(boolean valid) {
		 valid=true;
		 String fieldName="";
		 FacesContext fc=FacesContext.getCurrentInstance();
		 for (int i = 0; i < cutterorderlist.size(); i++) {
			if(cutterorderlist.get(i).getSeri()!=null){
				String status=bo.seriCheck(cutterorderlist.get(i).getSeri(),productionDataBean.getModelName());
				System.out.println("status -- "+status);
				if(status.equalsIgnoreCase("EXIST")){
					fieldName=CommonValidate.findComponentInRoot("hidden").getClientId();
					fc.addMessage(fieldName, new FacesMessage("seri already exist"));
					 valid=false;
				}else{
					valid=true;
				}
			}
		 }
		 if(StringUtils.isEmpty(productionDataBean.getCutter())){
		   fieldName=CommonValidate.findComponentInRoot("cutter").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please select the cutter"));
		   valid=false;
		 }if(StringUtils.isEmpty(productionDataBean.getCategoryName())){
			   fieldName=CommonValidate.findComponentInRoot("category").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the category"));
			   valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getModelName())){
			   fieldName=CommonValidate.findComponentInRoot("model").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the model"));
			   valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getTotalWeight())){
			fieldName=CommonValidate.findComponentInRoot("total").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please enter the total weight"));
			valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getRemainWeight())){
			fieldName=CommonValidate.findComponentInRoot("remain").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please enter the remain weight"));
			valid=false;
		}if(productionDataBean.getOrderDate()==null){
			fieldName=CommonValidate.findComponentInRoot("date").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please select the date"));
			valid=false;
		}
			return valid;
		}

	public String cutterOrderview(){
		if(personID!=null && clientID!=null){
			try{
				bo.getcutterOrderview(personID,clientID,productionDataBean);
			}catch(Exception e){
			}
		}
		return "";
	}
	
	public String cutterOrderedit(){
		if(personID!=null && clientID!=null){
			try{
				modelList=new ArrayList<String>();
				categoryList=new ArrayList<String>();
				categoryList=bo.getcategorynamelist(personID);
				bo.getcutterOrderview(personID,clientID,productionDataBean);
				modelList.add(productionDataBean.getModelName());
			}catch(Exception e){
			}
		}
		return "";
	}
	
	public void serieditValuechange(ValueChangeEvent event) {
			String fieldName;
		 FacesContext fc=FacesContext.getCurrentInstance();
		String value=event.getNewValue().toString();
		if(value!=null){
			if(!CommonValidate.validateNumber(event.getNewValue().toString())){
				fieldName=CommonValidate.findComponentInRoot("orderdata1").getClientId();
				fc.addMessage(fieldName, new FacesMessage("the seri must be number"));
			}
		}
	}
	
	public void quantityeditValuechange(ValueChangeEvent event) {
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal finalValue=BigDecimal.valueOf(0);
		try{
			String value=event.getNewValue().toString();
			String serialNo=event.getComponent().getAttributes().get("serial").toString();
			String seri=event.getComponent().getAttributes().get("seri").toString();
			//String motive=event.getComponent().getAttributes().get("motive").toString();
			if(value!=null){
				if(!CommonValidate.validateNumber(value)){
					fieldName=CommonValidate.findComponentInRoot("orderdata1").getClientId();
					fc.addMessage(fieldName, new FacesMessage("the quantity must be number"));
				}else{
						for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
							ProductionDataBean pro=new ProductionDataBean();
							pro.setQuantity(value);
							pro.setSerialNo(serialNo);
							//pro.setMotive(motive);
							pro.setSeri(seri);
							productionDataBean.getOrderQuantitylist().set(Integer.parseInt(serialNo)-1, pro);
							try{
								if(!productionDataBean.getOrderQuantitylist().get(i).getQuantity().equalsIgnoreCase("") ||productionDataBean.getOrderQuantitylist().get(i).getQuantity()!=null){	
									finalValue=(finalValue.add(new BigDecimal(productionDataBean.getOrderQuantitylist().get(i).getQuantity())));
								}
								}catch(NullPointerException e){
								}
							}
					productionDataBean.setTotalQuantity(finalValue.toString());
				}
			}
		}catch(Exception w){
		}
 }
	public String cutterOrderupdate(){
		if(personID!=null && clientID!=null){
			try{
				if(cutterOrdereditvalidation(true)){
					String status=bo.updateCutterorder(personID,clientID,productionDataBean);
					if("Success".equalsIgnoreCase(status)){
						RequestContext.getCurrentInstance().execute("PF('purchaseEdit').hide();");
						RequestContext.getCurrentInstance().execute("PF('edit').show();");
					}
				}
			}catch(Exception e){
			}
		}
			return "";
	}
	
	private boolean cutterOrdereditvalidation(boolean valid) {
		 valid=true;
		 String fieldName;
		 FacesContext fc=FacesContext.getCurrentInstance();
		 if(StringUtils.isEmpty(productionDataBean.getCutter())){
		   fieldName=CommonValidate.findComponentInRoot("ecutter").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please select the cutter"));
		   valid=false;
		 }if(StringUtils.isEmpty(productionDataBean.getCategoryName())){
			   fieldName=CommonValidate.findComponentInRoot("ecategory").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the category"));
			   valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getModelName())){
			   fieldName=CommonValidate.findComponentInRoot("emodel").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the model"));
			   valid=false;
		}
		return valid;
	}

	public String cutterOrderdelete(){
		if(personID!=null && clientID!=null){
			try{
				String status=bo.deleteCutterorder(personID,clientID,productionDataBean);
				if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('delete').show();");
				}
			}catch(Exception e){
			}
		}
		return"";
	}
	
	public void cutterorderSearch(){
		if(personID!=null && clientID!=null){
			cutterPurchaselist=new ArrayList<ProductionDataBean>();
			String fieldName="";
			FacesContext fc=FacesContext.getCurrentInstance();
			try{
				if(!productionDataBean.getCutter().equals("")){
					if("ALL".equalsIgnoreCase(productionDataBean.getCutter())){
						cutterPurchaselist=bo.cutterPurchasedetails(personID,clientID,productionDataBean);
					}else{
						cutterPurchaselist=bo.cutterorderSearch(personID,clientID,productionDataBean);
					}
				}else{
					if(productionDataBean.getFromDate()==null && productionDataBean.getToDate()!=null){
						fieldName=CommonValidate.findComponentInRoot("fromdate").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("please select from date"));
					}
					else if(productionDataBean.getFromDate()!=null && productionDataBean.getToDate()==null){
						fieldName=CommonValidate.findComponentInRoot("todate").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("please select to date"));
					}
					else if(productionDataBean.getFromDate()!=null && productionDataBean.getToDate()!=null){
						cutterPurchaselist=bo.cutterPurchasedetails(personID,clientID,productionDataBean);
					}
					 
				}
				
			}catch(Exception e){
			}
		}
	}
	
	public String cutterInvoice(){
		if(personID!=null && clientID!=null){
		try{
			if(productionDataBean.getInvoiceNo()!=null){
				String status=bo.cutterInvoice(personID,clientID,productionDataBean);
				if("Success".equalsIgnoreCase(status)){
					bo.getcutterOrderview(personID,clientID,productionDataBean);
					cutterPurchaselist=new ArrayList<ProductionDataBean>();
					cutterPurchaselist=bo.cutterPurchasedetails(personID,clientID,productionDataBean);
					RequestContext.getCurrentInstance().update("center_content");
				}
			}
		}catch(Exception e){
		}
		}
		return "";
	}
	
	public String cutterPayment(){
		if(personID!=null && clientID!=null)
		try{
			if(productionDataBean.getInvoiceNo()!=null)
			bo.cutterPayment(personID,clientID,productionDataBean);
			productionDataBean.setPayingAmount("");
		}catch(Exception e){	
		}
		return "";
	}
	
	public String cutterPaymentsave(){
		boolean valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(productionDataBean.getPayingAmount().equalsIgnoreCase("") || productionDataBean.getPayingAmount()==null){
				fieldName=CommonValidate.findComponentInRoot("payamount").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
				valid=false;
			}else if(!productionDataBean.getPayingAmount().equalsIgnoreCase("") || productionDataBean.getPayingAmount()!=null){
				if(new BigDecimal(productionDataBean.getPayingAmount()).compareTo(new BigDecimal(productionDataBean.getBalanceAmount()))==1){
					fieldName=CommonValidate.findComponentInRoot("payamount").getClientId();
					fc.addMessage(fieldName, new FacesMessage("entered amount is exceed"));
					valid=false;
				}
			}
			if(valid==true){
				String status=bo.cutterPaymentsave(personID,clientID,productionDataBean);
				if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('orderpayment').hide()");
					RequestContext.getCurrentInstance().execute("PF('paymentconfirm').show()");
				}
			}
		}catch(Exception e){
		}
		return"";
	}
	
	public String cutterReady(){
		try{
			productionDataBean.setSelectCheckbox(false);
			cutterorderlist=new ArrayList<ProductionDataBean>();
			cutterorderlist=bo.getcutterData(personID,clientID);
		}catch(Exception e){
		}
		return "";
	}
	
	public String cutterReadysave(){
		printerorderlist=new ArrayList<ProductionDataBean>();
		RequestContext rc=RequestContext.getCurrentInstance();
		BigDecimal totalvalue=BigDecimal.valueOf(0);
		BigDecimal totalquantity=BigDecimal.valueOf(0);
		BigDecimal value=BigDecimal.valueOf(0);
		try{
			String status=bo.cutterReadysave(personID,clientID,cutterorderlist);
			if("Success".equalsIgnoreCase(status)){
				System.out.println("status -- "+status);
				productionDataBean.setModelName(cutterorderlist.get(0).getModelName());
				bo.modelChange(productionDataBean, personID, clientID);
				System.out.println("--------");
				if(cutterorderlist.size()> 0){
					for (int i = 0; i < cutterorderlist.size(); i++) {
						if(cutterorderlist.get(i).isCheckBox()==true){
							ProductionDataBean production = new ProductionDataBean();
							production.setSerialNo(String.valueOf(i+1));
							production.setSeri(cutterorderlist.get(i).getSeri());
							production.setQuantity(cutterorderlist.get(i).getQuantity());
							System.out.println("quantity -- "+cutterorderlist.get(i).getQuantity());
							System.out.println("printerr --"+productionDataBean.getPrinterr());
							value=new BigDecimal(cutterorderlist.get(i).getQuantity()).multiply(new BigDecimal(productionDataBean.getPrinterr()));
							production.setModelName(cutterorderlist.get(i).getModelName());
							production.setValue(value.toString());
							totalvalue=totalvalue.add(value);
							totalquantity=totalquantity.add(new BigDecimal(cutterorderlist.get(i).getQuantity()));
							printerorderlist.add(production);
						}
					}
					productionDataBean.setTotalQuantity(totalquantity.toString());
					productionDataBean.setTotalValue(totalvalue.toString());
					invoiceList=new ArrayList<String>();
					invoiceList=bo.printerInvoiceList(clientID);
					System.out.println("invoiceList "+invoiceList.size());
					System.out.println("printerorderlist size "+printerorderlist.size());
				}
				rc.execute("PF('cutterready').hide();");
				rc.execute("PF('printerOrderFormDialog1').show();");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return"";
	}
	//***CMT STOCK***//	
	public String cmtstockPage(){
		try{
			productionDataBean.setCmt("");
			cmtList=new ArrayList<String>();
			cmtList=bo.getCmt(personID,clientID);
			cmtPurchaselist=new ArrayList<ProductionDataBean>();
			cmtPurchaselist=bo.getcmtorderView1(personID,clientID,productionDataBean);
		}catch(Exception e){
		}
		return"productionCmtstock";
	}
	//***CMT***//	
	public String cmtPage(){
		try{
			productionDataBean.setCmt("");
			cmtList=new ArrayList<String>();
			cmtList=bo.getCmt(personID,clientID);
			cmtPurchaselist=new ArrayList<ProductionDataBean>();
			cmtPurchaselist=bo.getcmtorderView(personID,clientID,productionDataBean);
		}catch(Exception e){
		}
		return"productionCmt";
	}

	public String cmtOrder(){
		try{
			seriList=new ArrayList<String>();
			invoiceList=new ArrayList<String>();
			invoiceList=bo.cmtInvoiceList(clientID);
			modelList=new ArrayList<String>();
			modelList=bo.cmtreadyModel(personID,clientID);
			cmtorderlist=new ArrayList<ProductionDataBean>();
			for(int i=1;i<13;i++){
				productionDataBean=new ProductionDataBean();
				productionDataBean.setSerialNo(String.valueOf(i));
				cmtorderlist.add(productionDataBean);
			}
			productionDataBean.setOrderDate(new Date());
			productionDataBean.setInvoiceNo(bo.getCmtinvoice(personID,clientID));
		}catch(Exception e){
		}
		return"";
	}
	
	public String cmtReceive(){
		try{
			seriList=new ArrayList<String>();
			modelList=new ArrayList<String>();
			cmtReceivelist=new ArrayList<ProductionDataBean>();
			modelList=bo.getmodelnameReady(personID,clientID);
			for(int i=1;i<13;i++){
				productionDataBean=new ProductionDataBean();
				productionDataBean.setSerialNo(String.valueOf(i));
				cmtReceivelist.add(productionDataBean);
			}
			productionDataBean.setReceivedate(new Date());
			productionDataBean.setInvoiceNo(bo.getCmtReceiveinvoice(personID,clientID));
		}catch(Exception e){
		}
		return"";
	}
	
	public void invoiceValuechange(){
		try{
			if(productionDataBean.getCutterInvoiceno()!=null){
				modelList=new ArrayList<String>();
				modelList=bo.printerReceivemodel(clientID,productionDataBean.getCutterInvoiceno());
			}
		}catch(Exception e){
		}
	}
	
	public void cmtInvoicechange(){
		try{
			if(productionDataBean.getCutterInvoiceno()!=null){
				modelList=new ArrayList<String>();
				modelList=bo.cmtOrderdemodel(clientID,productionDataBean.getCutterInvoiceno());
			}
		}catch(Exception e){
		}
	}
	
	public void modelValueChange(){
		try{
			if(productionDataBean.getModelName()!=null){
				seriList=new ArrayList<String>();
				seriList=bo.getmodelData(personID,clientID,productionDataBean);
			}
		}catch(Exception e){
		}
	}
	
	public List<String> seriAutocomplete(String name){
		try{
			seriList1=new ArrayList<String>();
			for (int i = 0; i < seriList.size(); i++) {
				if(seriList.get(i).contains(name)){
					seriList1.add(seriList.get(i));
				}
			}
		}catch(Exception e){
		}
		return seriList1;
	}
	
	public void cmtreceiveModelchange(){
		try{
			if(productionDataBean.getModelName()!=null){
				seriList=new ArrayList<String>();
				seriList=bo.cmtSerilist(clientID,productionDataBean);
			}
		}catch(Exception e){	
		}
	}
	
	public void cmtreceiveSerichange(ValueChangeEvent v){
		String value=v.getNewValue().toString();
		String serialNo=v.getComponent().getAttributes().get("serial").toString();
		BigDecimal amount=BigDecimal.valueOf(0);
		BigDecimal qty=BigDecimal.valueOf(0);
		FacesContext fc=FacesContext.getCurrentInstance();
		String fieldName="";
		try{
			if(value!=null){
				String quantity=bo.cmtreceiveSerichange(value,productionDataBean);
				if(quantity.equalsIgnoreCase("")){
					if(!CommonValidate.validateNumber(quantity)){
						fieldName=CommonValidate.findComponentInRoot("cmtreceive").getClientId(fc);
						   fc.addMessage(fieldName, new FacesMessage("please enter the valid seri"));
					}
				}else{
					ProductionDataBean pro=new ProductionDataBean();
					pro.setSerialNo(serialNo);
					pro.setQuantity(quantity);
					pro.setMotive(productionDataBean.getTotalseri());
					pro.setValue(new BigDecimal(productionDataBean.getPrinterr()).multiply(new BigDecimal(quantity)).toString());
					cmtReceivelist.set(Integer.parseInt(serialNo)-1, pro);
					for(int i=0;i<cmtReceivelist.size();i++){
					try{
						if(cmtReceivelist.get(i).getValue()!=null){
							amount=amount.add(new BigDecimal(cmtReceivelist.get(i).getValue()));
						}
						if(cmtReceivelist.get(i).getQuantity()!=null){
							qty=qty.add(new BigDecimal(cmtReceivelist.get(i).getQuantity()));
						}
					}catch(NullPointerException exception){
					}
					}
					productionDataBean.setTotalValue(amount.toString());
					productionDataBean.setTotalQuantity(qty.toString());
				}
			}
		}catch(Exception e){
		}
	}
	
	public void quantityChange(ValueChangeEvent event){
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal addValue=BigDecimal.valueOf(0);
		BigDecimal tempValue=BigDecimal.valueOf(0);
		BigDecimal finalValue=BigDecimal.valueOf(0);
		try{
		String value=event.getNewValue().toString();
		String serialNo=event.getComponent().getAttributes().get("serial").toString();
		String seri=event.getComponent().getAttributes().get("seri").toString();
		String motive=event.getComponent().getAttributes().get("motive").toString();
		if(value!=null){
			if(!CommonValidate.validateNumber(value)){
				String fieldName = CommonValidate.findComponentInRoot("cmtorder").getClientId();
				fc.addMessage(fieldName, new FacesMessage("the quantity must be number"));
			}
			else{
				addValue=(new BigDecimal(value).add(new BigDecimal(0)));
				tempValue=addValue;
				if(productionDataBean.getTotalQuantity()!=null){
					finalValue=(new BigDecimal(value).add(new BigDecimal(productionDataBean.getTotalQuantity())));
					productionDataBean.setTotalQuantity(finalValue.toString());
				}else{
					productionDataBean.setTotalQuantity(tempValue.toString());
				}
			}
			ProductionDataBean production=new ProductionDataBean();
			production.setSeri(seri);
			production.setSerialNo(serialNo);
			production.setMotive(motive);
			production.setValue(new BigDecimal(seri).multiply(new BigDecimal(value)).toString());
			cmtorderlist.set(Integer.parseInt(serialNo)-1, production);
			if(productionDataBean.getTotalValue()==null || productionDataBean.getTotalValue().equalsIgnoreCase("")){
			productionDataBean.setTotalValue(production.getValue());
			}else{
				productionDataBean.setTotalValue(new BigDecimal(productionDataBean.getTotalValue()).add(new BigDecimal(production.getValue())).toString());
			}
		}
		}catch(Exception e){
		}
	}
	
	public void quantityprinterChange(ValueChangeEvent event){
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal addValue=BigDecimal.valueOf(0);
		BigDecimal tempValue=BigDecimal.valueOf(0);
		BigDecimal finalValue=BigDecimal.valueOf(0);
		try{
		String value=event.getNewValue().toString();
		String serialNo=event.getComponent().getAttributes().get("serial").toString();
		String seri=event.getComponent().getAttributes().get("seri").toString();
		String motive=event.getComponent().getAttributes().get("motive").toString();
		if(value!=null){
			if(!CommonValidate.validateNumber(value)){
				String fieldName = CommonValidate.findComponentInRoot("cmtorder").getClientId();
				fc.addMessage(fieldName, new FacesMessage("the quantity must be number"));
			}
			else{
				addValue=(new BigDecimal(value).add(new BigDecimal(0)));
				tempValue=addValue;
				if(productionDataBean.getTotalQuantity()!=null){
					finalValue=(new BigDecimal(value).add(new BigDecimal(productionDataBean.getTotalQuantity())));
					productionDataBean.setTotalQuantity(finalValue.toString());
				}else{
					productionDataBean.setTotalQuantity(tempValue.toString());
				}
			}
			ProductionDataBean production=new ProductionDataBean();
			production.setSeri(seri);
			production.setSerialNo(serialNo);
			production.setMotive(motive);
			production.setValue(new BigDecimal(seri).multiply(new BigDecimal(value)).toString());
			printerorderlist.set(Integer.parseInt(serialNo)-1, production);
			if(productionDataBean.getTotalValue()==null || productionDataBean.getTotalValue().equalsIgnoreCase("")){
			productionDataBean.setTotalValue(production.getValue());
			}else{
				productionDataBean.setTotalValue(new BigDecimal(productionDataBean.getTotalValue()).add(new BigDecimal(production.getValue())).toString());
			}
		}
		}catch(Exception e){
		}
	}
	
	public String cmtorderSave(){
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal value=BigDecimal.valueOf(0);
		try{
			if(cmtorderValidation(true)){
				for(int i=0;i<cmtorderlist.size();i++){
					if(cmtorderlist.get(i).getQuantity()!=null){
						value=value.add(new BigDecimal(cmtorderlist.get(i).getQuantity()));
					}
				}
				productionDataBean.setPrinterQuantity(value.toString());
				if(productionDataBean.getTotalQuantity().compareTo(productionDataBean.getPrinterQuantity())==0){
					String status=bo.insertCmtorder(personID,clientID,productionDataBean,cmtorderlist);
					if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('cmtorder').hide()");
					RequestContext.getCurrentInstance().execute("PF('confirm').show()");
					}
				}else{
					fieldName=CommonValidate.findComponentInRoot("cmtorder").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please order the full quantity"));
				}
			}
		}catch(Exception e){
		}
		return "";
	}
	
	private boolean cmtorderValidation(boolean valid) {
		valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		if(productionDataBean.getOrderDate()==null){
			 fieldName=CommonValidate.findComponentInRoot("date").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the date"));
			   valid=false;
		}
		 if(StringUtils.isEmpty(productionDataBean.getCmt())){
			   fieldName=CommonValidate.findComponentInRoot("cmt").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the cmt"));
			   valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getModelName())){
				   fieldName=CommonValidate.findComponentInRoot("model").getClientId(fc);
				   fc.addMessage(fieldName, new FacesMessage("please select the model"));
				   valid=false;
		}	
		return valid;
	}

	public String cmtReceivesave(){
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal value=BigDecimal.valueOf(0);
		try{
			if(cmtreceiveValidation(true)){
				for(int i=0;i<cmtReceivelist.size();i++){
					if(cmtReceivelist.get(i).getQuantity()!=null){
						value=value.add(new BigDecimal(cmtReceivelist.get(i).getQuantity()));
					}
				}
				productionDataBean.setPrinterQuantity(value.toString());
				if(productionDataBean.getTotalQuantity().compareTo(productionDataBean.getPrinterQuantity())==0){
					String status=bo.insertCmtreceive(personID,clientID,productionDataBean,cmtReceivelist);
					if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('cmtReceiveForm').hide()");
					RequestContext.getCurrentInstance().execute("PF('confirm').show()");
					}
				}else{
					fieldName=CommonValidate.findComponentInRoot("orderdata1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please order the full quantity"));
				}
			}
		}catch(Exception e){
		}
		return "";
	}
	
	private boolean cmtreceiveValidation(boolean valid) {
		valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		if(productionDataBean.getReceivedate()==null){
			 fieldName=CommonValidate.findComponentInRoot("date").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the date"));
			   valid=false;
		}
		 if(StringUtils.isEmpty(productionDataBean.getCmt())){
			   fieldName=CommonValidate.findComponentInRoot("receivecmt").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the cmt"));
			   valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getModelName())){
				   fieldName=CommonValidate.findComponentInRoot("receivemodel").getClientId(fc);
				   fc.addMessage(fieldName, new FacesMessage("please select the model"));
				   valid=false;
		}	
		return valid;
	}

	public void cmtSearch(){
		try{
			if(productionDataBean.getCmt()!=null){
			cmtPurchaselist=new ArrayList<ProductionDataBean>();
			if("ALL".equalsIgnoreCase(productionDataBean.getCmt())){
				cmtPurchaselist=bo.getcmtorderView(personID,clientID,productionDataBean);
			}else{
				cmtPurchaselist=bo.getcmtSearch(personID,clientID,productionDataBean);
			}
		  }
		}catch(Exception e){
		}
	}
	
	public String cmtpurchaseView(){
		try{
			if(productionDataBean.getInvoiceNo()!=null){
				bo.getcmtpurchesView(personID,clientID,productionDataBean);
			}
		}catch(Exception e){
		}
		return"";
	}
	
	public String cmtpurchaseEdit(){
		try{
			modelList=new ArrayList<String>();
			bo.getcmtpurchesView(personID,clientID,productionDataBean);
			modelList.add(productionDataBean.getModelName());
			seriList=new ArrayList<String>();
			for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
				seriList.add(productionDataBean.getOrderQuantitylist().get(i).getSeri());
			}
		}catch(Exception e){	
		}
		return"";
	}
	
	public void quantityeditChange(ValueChangeEvent event){
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal addValue=BigDecimal.valueOf(0);
		BigDecimal tempValue=BigDecimal.valueOf(0);
		BigDecimal finalValue=BigDecimal.valueOf(0);
		try{
		String value=event.getNewValue().toString();
		String serialNo=event.getComponent().getAttributes().get("serial").toString();
		String seri=event.getComponent().getAttributes().get("seri").toString();
		String motive=event.getComponent().getAttributes().get("motive").toString();
		if(value!=null){
			if(!CommonValidate.validateNumber(value)){
				String fieldName = CommonValidate.findComponentInRoot("orderdata1").getClientId();
				fc.addMessage(fieldName, new FacesMessage("the quantity must be number"));
			}
			else{
				addValue=(new BigDecimal(value).add(new BigDecimal(0)));
				tempValue=addValue;
				if(productionDataBean.getTotalQuantity()!=null){
					finalValue=(new BigDecimal(value).add(new BigDecimal(productionDataBean.getTotalQuantity())));
					productionDataBean.setTotalQuantity(finalValue.toString());
				}else{
					productionDataBean.setTotalQuantity(tempValue.toString());
				}
			}
			ProductionDataBean production=new ProductionDataBean();
			production.setSeri(seri);
			production.setSerialNo(serialNo);
			production.setMotive(motive);
			production.setValue(new BigDecimal(seri).multiply(new BigDecimal(value)).toString());
			productionDataBean.getOrderQuantitylist().set(Integer.parseInt(serialNo)-1, production);
			if(productionDataBean.getTotalValue()==null || productionDataBean.getTotalValue().equalsIgnoreCase("")){
			productionDataBean.setTotalValue(production.getValue());
			}else{
				productionDataBean.setTotalValue(new BigDecimal(productionDataBean.getTotalValue()).add(new BigDecimal(production.getValue())).toString());
			}
		}
		}catch(Exception e){
		}
	}
	
	public void serieditChange(ValueChangeEvent v){
		BigDecimal amount=BigDecimal.valueOf(0);
		try{
			String value=v.getNewValue().toString();
			String serialNo=v.getComponent().getAttributes().get("serial").toString();
			String motive=v.getComponent().getAttributes().get("motive").toString();
			if(value!=null){
				String quantity=bo.getSerivaluechange(value,productionDataBean);
				ProductionDataBean pro=new ProductionDataBean();
				pro.setSerialNo(serialNo);
				pro.setQuantity(quantity);
				pro.setMotive(motive);
				pro.setValue(new BigDecimal(productionDataBean.getPrinterr()).multiply(new BigDecimal(quantity)).toString());
				productionDataBean.getOrderQuantitylist().set(Integer.parseInt(serialNo)-1, pro);
				for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
				try{
					if(productionDataBean.getOrderQuantitylist().get(i).getValue()!=null){
						amount=amount.add(new BigDecimal(productionDataBean.getOrderQuantitylist().get(i).getValue()));
					}
				}catch(NullPointerException exception){
				}
				}
				productionDataBean.setTotalValue(amount.toString());
			}
		}catch(Exception e){
		}
	}
	
	public String cmtorderUpdate(){
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal value=BigDecimal.valueOf(0);
		try{
			if(cmteditValidation(true)){
				for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
					if(productionDataBean.getOrderQuantitylist().get(i).getQuantity()!=null){
						value=value.add(new BigDecimal(productionDataBean.getOrderQuantitylist().get(i).getQuantity()));
					}
				}
				productionDataBean.setPrinterQuantity(value.toString());
				if(productionDataBean.getTotalQuantity().compareTo(productionDataBean.getPrinterQuantity())==0){
				String status=bo.cmtorderUpdate(personID,clientID,productionDataBean);
				if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('purchaseEdit').hide()");
					RequestContext.getCurrentInstance().execute("PF('edit').show()");
				}
				}else{
					fieldName=CommonValidate.findComponentInRoot("orderdata1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please order the full quantity"));
				}
			}
		}catch(Exception e){
		}
		return "";
	}
	private boolean cmteditValidation(boolean valid) {
		valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		 if(StringUtils.isEmpty(productionDataBean.getCmt())){
			   fieldName=CommonValidate.findComponentInRoot("ecmt").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the cmt"));
			   valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getModelName())){
				   fieldName=CommonValidate.findComponentInRoot("emodel").getClientId(fc);
				   fc.addMessage(fieldName, new FacesMessage("please select the model"));
				   valid=false;
		}	
		return valid;
	}

	public String cmtOrderdelete(){
		try{
			if(productionDataBean.getInvoiceNo()!=null){
				String status=bo.cmtOrderdelete(personID,clientID,productionDataBean);
				if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('delete').show()");
				}
			}
		}catch(Exception e){
		}
		return"";
	}
	
	public String cmtInvoice(){
		try{
			System.out.println("-------------------------");
			if(productionDataBean.getInvoiceNo()!=null){
				String status=bo.cmtInvoice(clientID,productionDataBean.getInvoiceNo());
				if("Success".equalsIgnoreCase(status)){
					bo.getcmtpurchesView(personID,clientID,productionDataBean);
					cmtPurchaselist=new ArrayList<ProductionDataBean>();
					cmtPurchaselist=bo.getcmtorderView(personID,clientID,productionDataBean);
					System.out.println("value "+productionDataBean.getValue());
					RequestContext.getCurrentInstance().update("center_content");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return"";
	}
	
	public String cmtPayment(){
		try{
			productionDataBean.setPayingAmount("");
			if(productionDataBean.getInvoiceNo()!=null){
				bo.cmtPayment(clientID,productionDataBean);
			}
		}catch(Exception e){
		}
		return"";
	}
	
	public String cmtPaymentsave(){
		boolean valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(StringUtils.isEmpty(productionDataBean.getPayingAmount())){
				fieldName=CommonValidate.findComponentInRoot("payamount").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
				valid=false;
			}else if(!StringUtils.isEmpty(productionDataBean.getPayingAmount())){
				if(new BigDecimal(productionDataBean.getPayingAmount()).compareTo(new BigDecimal(productionDataBean.getBalanceAmount()))==1){
					fieldName=CommonValidate.findComponentInRoot("payamount").getClientId();
					fc.addMessage(fieldName, new FacesMessage("entered amount is exceed"));
					valid=false;
				}
			}
			if(valid==true){
			String payment=bo.cmtPaymentsave(clientID,productionDataBean);
			if("Success".equalsIgnoreCase(payment)){
				RequestContext.getCurrentInstance().execute("PF('orderpayment').hide();");
				RequestContext.getCurrentInstance().execute("PF('paymentconfirm').show();");
			}
			}
		}catch(Exception e){	
		}
		return"";
	}
	
	public void holdingCmt() {
		cmtHoldinglist=new ArrayList<ProductionDataBean>();
		productionDataBean.setCmt("");
		productionDataBean.setTotalQuantity("");
		productionDataBean.setTotalValue("");
	}	
	public void cmtHolding(){
		try{
			if(productionDataBean.getCmt()!=null){
			cmtHoldinglist=new ArrayList<ProductionDataBean>();
			cmtHoldinglist=bo.getcmtHolding(personID,clientID,productionDataBean);
			}
		}catch(Exception e){
		}
	}
	
	//***PRODUCT***//
	public String productPage(){
		try{
			productList=new ArrayList<ProductionDataBean>();
			//productList=bo.productDetails(personID,clientID);
			//productionDataBean.setCmtList(null);
			//productionDataBean.setCutterList(null);
			//productionDataBean.setPrinterlist(null);
			productList=bo.getProduction(personID,clientID,productionDataBean);
		}catch(Exception e){
		}
		return"productionProduct";
	}
	
	public String productHistory(){
		try{
			producthistoryList=new ArrayList<ProductionDataBean>();
			producthistoryList=bo.productHistory(clientID,personID);
		}catch(Exception e){
			
		}
		return"";
	}
	public String productionView(){
		try{
			if(productionDataBean.getInvoiceNo()!=null){
				bo.productionView(clientID,productionDataBean);
			}
		}catch(Exception e){
		}
		return"";
	}
	
	//vendor//category//model
	public String vendorRegPage(){
		productionDataBean.setDivisionVendor("");
		productionList=bo.getvendorlist(personID);
		return "vendorRegistration";
	}
	public String categoryRegPage(){
		productionList=bo.getcategorylist(personID);
		return "categoryRegistration";
	}
	public String modelRegPage(){
		categoryList=bo.getcategorynamelist(personID);
		productionList=bo.getmodelList(personID);
		return "modelRegistration";
	}
	
	public String addvendormethod(){
		productionDataBean.setDivisionVendor("");
		productionDataBean.setVendorName("");
		productionDataBean.setVendorAddress("");
		productionDataBean.setVendorHpnumber1("");
		productionDataBean.setVendorHpnumber2("");
		productionDataBean.setModelSeri("");;
		return "";
	}
	
	public String addVendor(){
		System.out.println("add vendor method calling");
		String fieldName;FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(addVendorValidate(true)){
				if(personID != null && clientID !=null){
					String status=bo.insertvendor(personID,clientID,productionDataBean);
					if(status.equalsIgnoreCase("Success")){
						RequestContext.getCurrentInstance().execute("PF('addvendorDialog').hide();");
						RequestContext.getCurrentInstance().execute("PF('confirm').show();");
					}else if(status.equalsIgnoreCase("Exist")){
						fieldName=CommonValidate.findComponentInRoot("hpnoid").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("This vendor already exist"));
					}
				}
			}
		}catch(Exception e){
		}
		return "";
	}
	private boolean addVendorValidate(boolean valid) {
		  valid=true;String fieldName;
		  FacesContext fc=FacesContext.getCurrentInstance();
		  if(StringUtils.isEmpty(productionDataBean.getDivisionVendor())){
		   fieldName=CommonValidate.findComponentInRoot("divisionid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please select the division"));
		   valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getVendorName())){
		   fieldName=CommonValidate.findComponentInRoot("vendornameid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the vendor name"));
		   valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getVendorAddress())){
		   fieldName=CommonValidate.findComponentInRoot("addressid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the vendor address"));
		   valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getVendorHpnumber1())){
		   fieldName=CommonValidate.findComponentInRoot("hpnoid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the hp number1"));
		   valid=false;
		  }else if (!StringUtils.isEmpty(productionDataBean.getVendorHpnumber1())) {
				if (!CommonValidate.validateNumber(productionDataBean.getVendorHpnumber1())) {
						fieldName = CommonValidate.findComponentInRoot("hpnoid").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("please enter the valid hp No 1."));
						valid = false;
				}
		  }
		  if (!StringUtils.isEmpty(productionDataBean.getVendorHpnumber2())) {
				if (!CommonValidate.validateNumber(productionDataBean.getVendorHpnumber2())) {
					fieldName = CommonValidate.findComponentInRoot("hpnoid1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the valid hp No 2."));
					valid = false;
			}
		  }
		  return valid;
		}
	public String editVendor(){
		try{
			bo.editVendor(personID,productionDataBean);
		}catch(Exception e){
		}
		return "";
	}
	public String updateVendor(){
		String status="Fail";
		try{
			if(editVendorValidate(true)){
				if(personID != null && clientID !=null){
					status=bo.updatevendor(personID,productionDataBean);
					if(status.equalsIgnoreCase("Success")){
						RequestContext.getCurrentInstance().execute("PF('editDialog').hide();");
						RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
					}
				}
			}
		}catch(Exception e){
		}
		return "";
	}
	private boolean editVendorValidate(boolean valid) {
		  valid=true;String fieldName;
		  FacesContext fc=FacesContext.getCurrentInstance();
		  if(productionDataBean.getDivisionVendor().equalsIgnoreCase("select")){
		   fieldName=CommonValidate.findComponentInRoot("edivisionid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please select the division"));
		   valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getVendorName())){
		   fieldName=CommonValidate.findComponentInRoot("evendornameid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the vendor name"));
		   valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getVendorAddress())){
			   fieldName=CommonValidate.findComponentInRoot("eaddressid").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please enter the vendor address"));
			   valid=false;
		  }
		  if(!StringUtils.isEmpty(productionDataBean.getVendorHpnumber2())) {
				if (!CommonValidate.validateNumber(productionDataBean.getVendorHpnumber2())) {
					fieldName = CommonValidate.findComponentInRoot("ehpnoid2").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please enter the valid hp No 2."));
					valid = false;
			}
		  }
		  return valid;
		 }
	public String deleteVendor(){
		try{
			bo.deletevendor(personID,productionDataBean);
		}catch(Exception e){
		}
		return "";
	}
	
	public void vendorSearch(){
	try{
		productionList=new ArrayList<ProductionDataBean>();
		productionList=bo.vendorDetails(personID,clientID,productionDataBean);
	}catch(Exception e){
	}
	}
	
	public String addCategoryMethod(){
		productionDataBean.setCategoryName("");
		productionDataBean.setCategoryFabprice("");
		return "";
	}
	public String categoryRegistration(){
		System.out.println("add category method calling");
		String fieldName;FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(addCategoryValidate(true)){
				if(personID != null && clientID !=null){
					String status=bo.insertcategory(personID,clientID,productionDataBean);
					if(status.equalsIgnoreCase("Success")){
						RequestContext.getCurrentInstance().execute("PF('addcategoryDialog').hide();");
						RequestContext.getCurrentInstance().execute("PF('confirm').show();");
					}else if(status.equalsIgnoreCase("Exist")){
						fieldName=CommonValidate.findComponentInRoot("categoryid").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("This Category already exist"));
					}
				}
			}
		}catch(Exception e){
		}
		return "";
	}
	private boolean addCategoryValidate(boolean valid) {
		  valid=true;String fieldName;
		  FacesContext fc=FacesContext.getCurrentInstance();
		  if(StringUtils.isEmpty(productionDataBean.getCategoryName())){
		   fieldName=CommonValidate.findComponentInRoot("categoryid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the category"));
		   valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getCategoryFabprice())){
		   fieldName=CommonValidate.findComponentInRoot("fabricpriceid").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the fabric price"));
		   valid=false;
		  }
		  return valid;
		}
	
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((ProductionDataBean) event.getObject()).getCategoryName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	private boolean validates(boolean valid) {
		valid=true;
		if (StringUtils.isEmpty(productionDataBean.getCategoryName())) {
			FacesMessage msg = new FacesMessage("please enter the category name");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} 
		if (StringUtils.isEmpty(productionDataBean.getCategoryFabprice())) {
			FacesMessage msg = new FacesMessage("Please enter the fabric price");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} 
		return valid;
	}
	
	public void updatecategory(RowEditEvent event){
		String status = "Fail";
		try {
			if (personID != null) {
				String categoryname = ((ProductionDataBean) event.getObject()).getCategoryName().toString();
				String categoryprice = ((ProductionDataBean) event.getObject()).getCategoryFabprice().toString();
				int cateid = ((ProductionDataBean) event.getObject()).getCategoryId();
				productionDataBean.setCategoryName(categoryname);
				productionDataBean.setCategoryFabprice(categoryprice);
				productionDataBean.setCategoryId(cateid);
				if (validates(true)) {
					status = bo.editcategory(personID,productionDataBean);
					productionList=bo.getcategorylist(personID);
					if (status.equalsIgnoreCase("Success")) {
						RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
					}else if(status.equalsIgnoreCase("Fail")){
						RequestContext.getCurrentInstance().execute("PF('confirm2').show();");
					}
				}
			}
		}
		catch(Exception e){
		}
	}
	
	public String deleteCategory(){
		try{
			bo.deletecategory(personID,productionDataBean);
		}catch(Exception e){
		}
		return "";
	}
	
	public String addModel(){
		try{
			categoryList=bo.getcategorynamelist(personID);
			productionDataBean.setModelCmt("");
			productionDataBean.setModelCutter("");
			productionDataBean.setModelEtc("");
			productionDataBean.setModelName("");
			productionDataBean.setModelPole("");
			productionDataBean.setModelPrinter("");
			productionDataBean.setModelSellprice("");
			productionDataBean.setCategoryName("");
		}catch(Exception e){
		}
		return "";
	}
	
	public String saveModelReg(){
		System.out.println("save model method calling");
		String fieldName;FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(addModelValidate(true)){
				if(personID != null && clientID !=null){
					String status=bo.insertmodel(personID,clientID,productionDataBean);
					if(status.equalsIgnoreCase("Success")){
						RequestContext.getCurrentInstance().execute("PF('addmodelDialog').hide();");
						RequestContext.getCurrentInstance().execute("PF('confirm').show();");
					}else if(status.equalsIgnoreCase("Exist")){
						fieldName=CommonValidate.findComponentInRoot("modelid").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("this model already exist"));
					}
				}
			}
		}catch(Exception e){
		}
		return "";
	}
	
	private boolean addModelValidate(boolean valid) {
		  valid=true;String fieldName;
		  FacesContext fc=FacesContext.getCurrentInstance();
		  if(StringUtils.isEmpty(productionDataBean.getCategoryName())){
			  fieldName=CommonValidate.findComponentInRoot("category").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please choose the category"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelName())){
			  fieldName=CommonValidate.findComponentInRoot("modelid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the model"));
			  valid=false;
		  }/*else if(!StringUtils.isEmpty(productionDataBean.getModelName())){
			  if(productionDataBean.getModelName().length()<4 || productionDataBean.getModelName().length()>4){
				  fieldName=CommonValidate.findComponentInRoot("modelid").getClientId(fc);
				  fc.addMessage(fieldName, new FacesMessage("model must be 4 digits"));
				  valid=false;
			  }
		  }*/
		  /*if(StringUtils.isEmpty(productionDataBean.getModelSeri())){
			  fieldName=CommonValidate.findComponentInRoot("seriid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the seri"));
			  valid=false;
		  }*/
		  if(StringUtils.isEmpty(productionDataBean.getModelPole())){
			  fieldName=CommonValidate.findComponentInRoot("poleid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the pole"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelCutter())){
			  fieldName=CommonValidate.findComponentInRoot("cutterid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the cutter"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelPrinter())){
			  fieldName=CommonValidate.findComponentInRoot("printerid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the printer"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelCmt())){
			  fieldName=CommonValidate.findComponentInRoot("cmtid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the cmt"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelEtc())){
			  fieldName=CommonValidate.findComponentInRoot("etcid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the etc"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelSellprice())){
			  fieldName=CommonValidate.findComponentInRoot("sellpriceid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the sell price"));
			  valid=false;
		  }
		  return valid;
		}
	
	public String editModel(){
		try{
			bo.editModel(personID,productionDataBean);
			categoryList=bo.getcategorynamelist(personID);
		}catch(Exception e){
		}
		return "";
	}
	
	public String updateModel(){
		String status="Fail";
		try{
			if(editModelValidate(true)){
				if(personID != null && clientID !=null){
					status=bo.updatemodel(personID,productionDataBean);
					if(status.equalsIgnoreCase("Success")){
						RequestContext.getCurrentInstance().execute("PF('editmodelDialog').hide();");
						RequestContext.getCurrentInstance().execute("PF('confirm1').show();");
					}
				}
			}
		}catch(Exception e){
		}
		return "";
	}
	
	private boolean editModelValidate(boolean valid) {
		  valid=true;String fieldName;
		  FacesContext fc=FacesContext.getCurrentInstance();
		  if(productionDataBean.getCategoryName().equalsIgnoreCase("select")){
			  fieldName=CommonValidate.findComponentInRoot("ecategoryid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please choose the category"));
			  valid=false;
		  }
		 /* if(StringUtils.isEmpty(productionDataBean.getModelSeri())){
			  fieldName=CommonValidate.findComponentInRoot("eseriid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the seri"));
			  valid=false;
		  }*/
		  if(StringUtils.isEmpty(productionDataBean.getModelPole())){
			  fieldName=CommonValidate.findComponentInRoot("epoleid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the pole"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelCutter())){
			  fieldName=CommonValidate.findComponentInRoot("ecutterid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the cutter"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelPrinter())){
			  fieldName=CommonValidate.findComponentInRoot("eprinterid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the printer"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelCmt())){
			  fieldName=CommonValidate.findComponentInRoot("ecmtid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the cmt"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelEtc())){
			  fieldName=CommonValidate.findComponentInRoot("eetcid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the etc"));
			  valid=false;
		  }
		  if(StringUtils.isEmpty(productionDataBean.getModelSellprice())){
			  fieldName=CommonValidate.findComponentInRoot("esellpriceid").getClientId(fc);
			  fc.addMessage(fieldName, new FacesMessage("please enter the sell price"));
			  valid=false;
		  }
		  return valid;
		}
	
	public String deleteModel(){
		try{
			bo.deletemodel(personID,productionDataBean);
		}catch(Exception e){
		}
		return "";
	}
	
	//PRINTER
	public String productionPrinterPage(){
		productionDataBean.setPrinter("");
		//categoryList=bo.getcategorynamelist(personID);
		printerList=new ArrayList<String>();
		printerList=bo.getPrinterlist(personID,clientID);
		printerViewList=new ArrayList<ProductionDataBean>();
		printerViewList=bo.getprinterView(clientID,personID);
		return "productionPrinter";
	}
	
	public String addPrinterOrderForm(){
		seriList=new ArrayList<String>();
		printerorderlist=new ArrayList<ProductionDataBean>();
		invoiceList=new ArrayList<String>();
		try{
			invoiceList=bo.cutterInvoicelist(clientID);
			printerModellist=new ArrayList<String>();
			printerModellist=bo.getPrinterreadymodel(personID,clientID);
			for (int i = 1; i < 13; i++) {
				productionDataBean=new ProductionDataBean();
				productionDataBean.setSerialNo(String.valueOf(i));
				printerorderlist.add(productionDataBean);
			}
			productionDataBean.setOrderDate(new Date());
			productionDataBean.setInvoiceNo(bo.printerInvoiceNum(personID,productionDataBean));
		}catch(Exception e){	
		}
		return "";
	}
	
	public void cutterInvoicechange(){
		modelNamelist=new ArrayList<String>();
		modelNamelist=bo.getmodelName(clientID,productionDataBean.getCutterInvoiceno());
	}

	public void printerModelchange(){
		try{
			if(productionDataBean.getModelName()!=null){
				seriList=new ArrayList<String>();
				seriList=bo.getmodelInfo(personID,clientID,productionDataBean);
			}
		}catch(Exception e){	
		}
	}
	
	public String addPrinterRecieveForm(){
		seriList=new ArrayList<String>();
		printerorderlist=new ArrayList<ProductionDataBean>();
		//invoiceList=new ArrayList<String>();
		try{
			//invoiceList=bo.printerInvoicelist(clientID);
			printerModellist=new ArrayList<String>();
			printerModellist=bo.getModel(clientID);
			for (int i = 1; i < 13; i++) {
				productionDataBean=new ProductionDataBean();
				productionDataBean.setSerialNo(String.valueOf(i));
				printerorderlist.add(productionDataBean);
			}
			productionDataBean.setReceivedate(new Date());
			productionDataBean.setInvoiceNo(bo.printerreceiveInvoiceNum(personID,productionDataBean));
		}catch(Exception e){
		}
		return "";
	}
	
	public void printerInvoicechange(){
		printerModellist=new ArrayList<String>();
		printerModellist=bo.printerModellist(clientID,productionDataBean.getCutterInvoiceno());
	}
	
	public void receiverModelchange(){
		try{
			if(productionDataBean.getModelName()!=null){
				seriList=new ArrayList<String>();
				seriList=bo.printerSerilist(clientID,productionDataBean);
			}
		}catch(Exception e){	
		}
	}
	
	public void categoryChange(ValueChangeEvent ve){
		String category=ve.getNewValue().toString();
		if(category.equalsIgnoreCase("all")){
			productionList=bo.getmodelList(personID);
		}else{
			productionList=bo.getparticularmodelList(personID,category);
		}
	}
	
public void dateValueChange(SelectEvent event){
		Date purdate = (Date) event.getObject();
		productionDataBean.setOrderDate(purdate);
		if(productionDataBean.getOrderDate()!=null){
			productionDataBean.setInvoiceNo(bo.printerInvoiceNum(personID,productionDataBean));
		}
		}
	
	public void dateValueReceiveChange(SelectEvent event){
		Date receivedate = (Date) event.getObject();
		productionDataBean.setReceivedate(receivedate);
		if(productionDataBean.getReceivedate()!=null){
			productionDataBean.setInvoiceNo(bo.printerreceiveInvoiceNum(personID,productionDataBean));
		}
		}
	
	public void printerSerichange(ValueChangeEvent v){
		String value=v.getNewValue().toString();
		String serialNo=v.getComponent().getAttributes().get("serial").toString();
		BigDecimal amount=BigDecimal.valueOf(0);
		BigDecimal quat=BigDecimal.valueOf(0);
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(value!=null){
				String quantity=bo.getSerivalue(value,productionDataBean);
				if(quantity.equalsIgnoreCase("")){
					System.out.println("inside iffff");
					if(StringUtils.isEmpty(quantity)){
						   fieldName=CommonValidate.findComponentInRoot("orderdata").getClientId(fc);
						   fc.addMessage(fieldName, new FacesMessage("please enter the valid seri"));
					}
				}else{
					ProductionDataBean pro=new ProductionDataBean();
					pro.setSerialNo(serialNo);
					pro.setQuantity(quantity);
					pro.setMotive(productionDataBean.getTotalseri());
					pro.setValue(new BigDecimal(productionDataBean.getPrinterr()).multiply(new BigDecimal(quantity)).toString());
					printerorderlist.set(Integer.parseInt(serialNo)-1, pro);
					for(int i=0;i<printerorderlist.size();i++){
					try{
						if(printerorderlist.get(i).getValue()!=null){
							amount=amount.add(new BigDecimal(printerorderlist.get(i).getValue()));
						}
						if(printerorderlist.get(i).getQuantity()!=null){
							quat=quat.add(new BigDecimal(printerorderlist.get(i).getQuantity()));
						}
					}catch(NullPointerException exception){
						exception.printStackTrace();
					}
					}
					productionDataBean.setTotalValue(amount.toString());
					productionDataBean.setTotalQuantity(quat.toString());
				}
				
			}
		}catch(Exception e){
		}
	}
	public String printerOrderRecSave(){
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal value=BigDecimal.valueOf(0);
		try{
			if(printerRecValidation(true)){
				for(int i=0;i<printerorderlist.size();i++){
					if(printerorderlist.get(i).getQuantity()!=null){
						value=value.add(new BigDecimal(printerorderlist.get(i).getQuantity()));
					}
				}
				productionDataBean.setPrinterQuantity(value.toString());
				if(productionDataBean.getTotalQuantity().compareTo(productionDataBean.getPrinterQuantity())==0){
					String status=bo.insertPrinterRecOrder(personID,clientID,productionDataBean,printerorderlist);
					if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('printerReceiveFormDialog').hide()");
					RequestContext.getCurrentInstance().execute("PF('confirm').show()");
				    }
				}else{
					fieldName=CommonValidate.findComponentInRoot("orderdata1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please receive the full quantity"));
				}
			}
		}catch(Exception e){	
		}
		return "";
	}
	
	private boolean printerRecValidation(boolean valid) {
		valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		if(productionDataBean.getReceivedate()==null){
			 fieldName=CommonValidate.findComponentInRoot("date").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the date"));
			   valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getPrinter())){
			   fieldName=CommonValidate.findComponentInRoot("printer").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the printer"));
			   valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getModelName())){
				fieldName=CommonValidate.findComponentInRoot("model").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("please select the model"));
				valid=false;
		}/*if(StringUtils.isEmpty(productionDataBean.getCutterInvoiceno())){
			fieldName=CommonValidate.findComponentInRoot("orderinvoiceno").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please select the order invoiceno"));
			valid=false;
		}*/
		return valid;
	}
	
	public String printerOrderSave(){
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal value=BigDecimal.valueOf(0);
		try{
			if(printerOrderValidation(true)){
				for(int i=0;i<printerorderlist.size();i++){
					if(printerorderlist.get(i).getQuantity()!=null){
						value=value.add(new BigDecimal(printerorderlist.get(i).getQuantity()));
					}
				}
				productionDataBean.setPrinterQuantity(value.toString());
					if(productionDataBean.getTotalQuantity().compareTo(productionDataBean.getPrinterQuantity())==0){
					String status=bo.insertPrinterOrder(personID,clientID,productionDataBean,printerorderlist);
					if("Success".equalsIgnoreCase(status)){
						RequestContext.getCurrentInstance().execute("PF('printerOrderFormDialog').hide()");
						RequestContext.getCurrentInstance().execute("PF('confirm').show()");
					}
				}else{
					fieldName=CommonValidate.findComponentInRoot("orderdata").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please order the full quantity"));
				}
			}
		}catch(Exception e){	
		}
		return "";
	}
	
	public String printerOrderSave1(){
		System.out.println("Inside printerOrderSave1 ");
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal value=BigDecimal.valueOf(0);
		try{

				for(int i=0;i<printerorderlist.size();i++){
					if(printerorderlist.get(i).getQuantity()!=null){
						value=value.add(new BigDecimal(printerorderlist.get(i).getQuantity()));
					}
				}
				productionDataBean.setPrinterQuantity(value.toString());
					if(productionDataBean.getTotalQuantity().compareTo(productionDataBean.getPrinterQuantity())==0){
					String status=bo.insertPrinterOrder(personID,clientID,productionDataBean,printerorderlist);
					if("Success".equalsIgnoreCase(status)){
						RequestContext.getCurrentInstance().execute("PF('printerOrderFormDialog1').hide()");
						RequestContext.getCurrentInstance().execute("PF('confirm').show()");
					}
				}else{
					fieldName=CommonValidate.findComponentInRoot("orderdata").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please order the full quantity"));
				}
			
		}catch(Exception e){	
		}
		return "";
	}
	
	private boolean printerOrderValidation(boolean valid) {
		valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		if(productionDataBean.getOrderDate()==null){
			 fieldName=CommonValidate.findComponentInRoot("datee").getClientId(fc);
			 fc.addMessage(fieldName, new FacesMessage("please select the date"));
			 valid=false;
		}
		 if(StringUtils.isEmpty(productionDataBean.getPrinter())){
			 fieldName=CommonValidate.findComponentInRoot("printerr").getClientId(fc);
			 fc.addMessage(fieldName, new FacesMessage("please select the printer"));
			 valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getModelName())){
			fieldName=CommonValidate.findComponentInRoot("modell").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("please select the model"));
			valid=false;
		}
		return valid;
	}
	
	public String printerpurchaseView(){
		try{
			if(productionDataBean.getInvoiceNo()!=null){
				bo.getprinterpurchaseView(personID,clientID,productionDataBean);
			}
		}catch(Exception e){	
		}
		return"";
	}
	
	public String printerpurchaseEdit(){
		try{
			modelList=new ArrayList<String>();
			printerList=new ArrayList<String>();
			printerList=bo.getPrinterlist(personID,clientID);
			bo.getprinterpurchaseView(personID,clientID,productionDataBean);
			modelList.add(productionDataBean.getModelName());
			seriList=new ArrayList<String>();
			for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
			seriList.add(productionDataBean.getOrderQuantitylist().get(i).getSeri());
			}
		}catch(Exception e){
		}
		return"";
		
	}
	
	public void printereditSerichange(ValueChangeEvent v){
		String value=v.getNewValue().toString();
		String serialNo=v.getComponent().getAttributes().get("serial").toString();
		String motive=v.getComponent().getAttributes().get("motive").toString();
		BigDecimal amount=BigDecimal.valueOf(0);
		try{
			if(value!=null){
				String quantity=bo.getSerivalue(value,productionDataBean);
				ProductionDataBean pro=new ProductionDataBean();
				pro.setSerialNo(serialNo);
				pro.setQuantity(quantity);
				pro.setMotive(motive);
				pro.setValue(new BigDecimal(productionDataBean.getPrinterr()).multiply(new BigDecimal(quantity)).toString());
				productionDataBean.getOrderQuantitylist().set(Integer.parseInt(serialNo)-1, pro);
				for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
				try{
					if(productionDataBean.getOrderQuantitylist().get(i).getValue()!=null){
						amount=amount.add(new BigDecimal(productionDataBean.getOrderQuantitylist().get(i).getValue()));
					}
				}catch(NullPointerException exception){
				}
				}
				productionDataBean.setTotalValue(amount.toString());
			}
		}catch(Exception e){
			
		}
	}
	
	public String printerpurchaseUpdate(){
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		BigDecimal value=BigDecimal.valueOf(0);
		try{
			if(purchaseEditvalidation(true)){
				for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
					if(productionDataBean.getOrderQuantitylist().get(i).getQuantity()!=null){
						value=value.add(new BigDecimal(productionDataBean.getOrderQuantitylist().get(i).getQuantity()));
					}
				}
				productionDataBean.setPrinterQuantity(value.toString());
				if(productionDataBean.getTotalQuantity().compareTo(productionDataBean.getPrinterQuantity())==0){
				String status=bo.printerpurchaseUpdate(personID,clientID,productionDataBean);
					if("Success".equalsIgnoreCase(status)){
						RequestContext.getCurrentInstance().execute("PF('printerpurchaseorderEdit').hide()");
						RequestContext.getCurrentInstance().execute("PF('edit').show()");
					}
				}else{
					fieldName=CommonValidate.findComponentInRoot("orderdata1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please order the full quantity"));
				}
			}
		}catch(Exception e){
		}
		return "";
	}
	private boolean purchaseEditvalidation(boolean valid) {
		valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		 if(StringUtils.isEmpty(productionDataBean.getPrinter())){
			   fieldName=CommonValidate.findComponentInRoot("eprinter").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please select the printer"));
			   valid=false;
		}if(StringUtils.isEmpty(productionDataBean.getModelName())){
				   fieldName=CommonValidate.findComponentInRoot("emodel").getClientId(fc);
				   fc.addMessage(fieldName, new FacesMessage("please select the model"));
				   valid=false;
		}	
		return valid;
	}
		
	
	public String printerpurchaseDelete(){
		try{
			if(productionDataBean.getInvoiceNo()!=null){
				String status=bo.printerpurchaseDelete(personID,clientID,productionDataBean);
				if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('delete').show()");
				}
			}
		}catch(Exception e){	
		}
		return"";
	}
	public void holdingPrinter() {
		printerHoldinglist=new ArrayList<ProductionDataBean>();
		productionDataBean.setPrinter("");
		productionDataBean.setTotalQuantity("");
		productionDataBean.setTotalAmount("");
	}	
	public void printerHolding(){
		try{ 
			if(productionDataBean.getPrinter()!=null){
			printerHoldinglist=new ArrayList<ProductionDataBean>();
			printerHoldinglist=bo.getprinterHolding(personID,clientID,productionDataBean);
			}
		}catch(Exception e){
		}
	}
	
	public void printerSearch(){
		try{
			if(productionDataBean.getPrinter()!=null){
				printerViewList=new ArrayList<ProductionDataBean>();
			if("ALL".equalsIgnoreCase(productionDataBean.getPrinter())){
				printerViewList=bo.getprinterView(clientID,personID);
			}else{
				printerViewList=bo.getprinterSearch(personID,clientID,productionDataBean);
			}
		  }
		}catch(Exception e){
		}
	}

	public String printerInvoice(){
		try{
			if(productionDataBean.getInvoiceNo()!=null){
				String status=bo.printerInvoice(clientID,productionDataBean.getInvoiceNo());
				if(status.equalsIgnoreCase("Success")){
					bo.getprinterpurchaseView(personID,clientID,productionDataBean);
					printerViewList=new ArrayList<ProductionDataBean>();
					printerViewList=bo.getprinterView(clientID,personID);
					RequestContext.getCurrentInstance().update("center_content");
				}
			}
		}catch(Exception e){
		}
		return "";
	}
	
	public String printerPayment(){
		try{
			productionDataBean.setPayingAmount("");
			if(productionDataBean.getInvoiceNo()!=null){
				bo.printerPayment(clientID,productionDataBean);
			}
		}catch(Exception e){
		}
		return"";
	}
	
	public String printerPaymentsave(){
		boolean valid=true;
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(StringUtils.isEmpty(productionDataBean.getPayingAmount())){
				fieldName=CommonValidate.findComponentInRoot("payamount").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the amount"));
				valid=false;
			}else if(!StringUtils.isEmpty(productionDataBean.getPayingAmount())){
				if(new BigDecimal(productionDataBean.getPayingAmount()).compareTo(new BigDecimal(productionDataBean.getBalanceAmount()))==1){
					fieldName=CommonValidate.findComponentInRoot("payamount").getClientId();
					fc.addMessage(fieldName, new FacesMessage("entered amount is exceed"));
					valid=false;
				}
			}
			if(valid==true){
			String payment=bo.printerpaymentsave(clientID,productionDataBean);
			if("Success".equalsIgnoreCase(payment)){
				RequestContext.getCurrentInstance().execute("PF('orderpayment').hide();");
				RequestContext.getCurrentInstance().execute("PF('paymentconfirm').show();");
			}
			}
		}catch(Exception e){	
		}
		return"";
	}
	 
	public String printerReady(){
		try{
			productionDataBean.setSelectCheckbox(false);
			printerPurchaselist=new ArrayList<ProductionDataBean>();
			printerPurchaselist=bo.getprinterData(personID,clientID);
		}catch(Exception e){
		}
		return"";
	}
	
	public String printerReadysave(){
		RequestContext rq=RequestContext.getCurrentInstance();
		try{
			String save=bo.printerReadysave(personID,clientID,printerPurchaselist);
			if("Success".equalsIgnoreCase(save)){
				rq.execute("PF('printerready').hide()");
				rq.execute("PF('readyconfirm').show();");
			}
		}catch(Exception e){
		}
		return"";
	}
	
	//STOCK//
	public String cmtStock(){
		try{
			if(productionDataBean.getInvoiceNo()!=null){
				stockList=new ArrayList<ProductionDataBean>();
				stockList=bo.cmtstockDetails(clientID,productionDataBean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return"";
	}

	public void stockValuechange(ValueChangeEvent v){
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			String value=v.getNewValue().toString();
			String serialNo=v.getComponent().getAttributes().get("serial").toString();
			String model=v.getComponent().getAttributes().get("model").toString();
			String seri=v.getComponent().getAttributes().get("seri").toString();
			String quantity=v.getComponent().getAttributes().get("quantity").toString();
			String stockQuantity=v.getComponent().getAttributes().get("stockquantity").toString();
			String stockId=v.getComponent().getAttributes().get("stockid").toString();
			if(value!=null){
				if(!CommonValidate.validateNumber(value)){
					fieldName=CommonValidate.findComponentInRoot("stockdata").getClientId();
					fc.addMessage(fieldName, new FacesMessage("the quantity must be number"));
					status=false;
				}else{
					if(Integer.parseInt(quantity)<Integer.parseInt(value)){
						fieldName=CommonValidate.findComponentInRoot("stockdata").getClientId();
						fc.addMessage(fieldName, new FacesMessage("the quantity should not more than"+" "+quantity));
						status=false;
					}else{
						if(stockQuantity!=null){
							if(Integer.parseInt(stockQuantity)<Integer.parseInt(value)){	
								fieldName=CommonValidate.findComponentInRoot("stockdata").getClientId();
								fc.addMessage(fieldName, new FacesMessage("the quantity should not more than"+" "+stockQuantity));
								status=false;
							}else{
								stockQuantity=(new BigDecimal(stockQuantity).subtract(new BigDecimal(value)).toString());
								status=true;
							}
						}else{
						stockQuantity=(new BigDecimal(quantity).subtract(new BigDecimal(value)).toString());
						status=true;
						}
					}
					ProductionDataBean production=new ProductionDataBean();
					production.setSerialNo(serialNo);
					production.setModelName(model);
					production.setSeri(seri);
					production.setQuantity(quantity);
					production.setStockinQuantity(stockQuantity);
					production.setCategoryId(Integer.parseInt(stockId));
					stockList.set(Integer.parseInt(serialNo)-1, production);
					
				}
			}
			
		}catch(Exception e){
		}
	}
	
	public String stocksave(){
		String save="";
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(status==true){
				save=bo.cmtstockSave(personID,clientID,productionDataBean,stockList);
				RequestContext rc=RequestContext.getCurrentInstance();
				if("Success".equalsIgnoreCase(save)){
					rc.execute("PF('orderstockin').hide()");
					rc.execute("PF('confirm').show()");
				}
			}else if(status==false){
				fieldName=CommonValidate.findComponentInRoot("stockdata").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the quantity"));
			}
		}catch(Exception e){
		}
		return"";
	}
	
	//PRODUCTION REMINDER
	public String productionReminder(){
		try{
			productionList=new ArrayList<ProductionDataBean>();
			productionDataBean.setFromDate(null);
			productionDataBean.setToDate(null);
			productionDataBean.setCategoryName("");
			productionList=bo.getproductionData(personID,clientID,productionDataBean);
		}catch(Exception e){
		}
		return"productionReminder";
	}
	
	public void productionSearch(){
		if(personID!=null && clientID!=null){
			productionList=new ArrayList<ProductionDataBean>();
			String fieldName="";
			FacesContext fc=FacesContext.getCurrentInstance();
			try{
				if(productionDataBean.getFromDate()==null && productionDataBean.getToDate()!=null){
					fieldName=CommonValidate.findComponentInRoot("fromdate").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please select from date"));
				}
				else if(productionDataBean.getFromDate()!=null && productionDataBean.getToDate()==null){
					fieldName=CommonValidate.findComponentInRoot("todate").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please select to date"));
				}
				else {
					productionList=bo.getproductionData(personID,clientID,productionDataBean);
				}
			}catch(Exception e){
			}
		}
	}
	public void holdingReminder(){
		productionDataBean.setOrderQuantitylist(null);
		bo.getHoldingDetails(personID,productionDataBean);
	}
	
	public void checkboxPrinter(){
		try{
			if(productionDataBean.isSelectCheckbox()==true){
				for(int i=0;i<cutterorderlist.size();i++){
					cutterorderlist.get(i).setCheckBox(true);
				}
			}else if(productionDataBean.isSelectCheckbox()==false){
				for(int i=0;i<cutterorderlist.size();i++){
					cutterorderlist.get(i).setCheckBox(false);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void checkboxCmt(){
		try{
			if(productionDataBean.isSelectCheckbox()==true){
				for(int i=0;i<printerPurchaselist.size();i++){
					printerPurchaselist.get(i).setCheckBox(true);
				}
			}else if(productionDataBean.isSelectCheckbox()==false){
				for(int i=0;i<printerPurchaselist.size();i++){
					printerPurchaselist.get(i).setCheckBox(false);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*TABLE*/
	
	public String tableRegPage()
	{
		tableflag=false;
		productionDataBean.setCategoryName("");
		productionDataBean.setTableName("");
		productionDataBean.setTableNo("");
		productionDataBean.setTableRow("");
		productionDataBean.setTableColumn("");
		productionDataBean.setCheckBox(false);
		tableviewlist=new ArrayList<ProductionDataBean>();
		tableviewlist=bo.gettableview(productionDataBean);
		return "successTableRegPage";
	}
	
	public String addtable(){
		productionDataBean.setTableName("");
		productionDataBean.setTableNo("");
		productionDataBean.setTableRow("");
		productionDataBean.setTableColumn("");
		productionDataBean.setAmodelName("");
		productionDataBean.setAseri("");
		productionDataBean.setAcutter("");
		productionDataBean.setAtanggal(null);
		productionDataBean.setBmodelName("");
		productionDataBean.setBseri("");
		productionDataBean.setBcutter("");
		productionDataBean.setBtanggal(null);
		productionDataBean.setCmodelName("");
		productionDataBean.setCseri("");
		productionDataBean.setCcutter("");;
		productionDataBean.setCtanggal(null);
		productionDataBean.setDmodelName("");
		productionDataBean.setDseri("");
		productionDataBean.setDcutter("");
		productionDataBean.setDtanggal(null);
		productionDataBean.setEmodelName("");
		productionDataBean.setEseri("");
		productionDataBean.setEcutter("");
		productionDataBean.setEtanggal(null);
		productionDataBean.setFmodelName("");
		productionDataBean.setFseri("");
		productionDataBean.setFcutter("");
		productionDataBean.setFtanggal(null);
		productionDataBean.setGmodelName("");
		productionDataBean.setGseri("");
		productionDataBean.setGcutter("");
		productionDataBean.setGtanggal(null);
		productionDataBean.setHmodelName("");
		productionDataBean.setHseri("");
		productionDataBean.setHcutter("");
		productionDataBean.setHtanggal(null);
		productionDataBean.setImodelName("");
		productionDataBean.setIseri("");
		productionDataBean.setIcutter("");
		productionDataBean.setItanggal(null);
		productionDataBean.setJmodelName("");
		productionDataBean.setJseri("");
		productionDataBean.setJcutter("");
		productionDataBean.setJtanggal(null);
		tableflag=false;
		return"";
	}
		
	public String gettable(){
		String status="";
		System.out.println("Inside gettable");
		tablelist=new ArrayList<String>();
		tablecolumnlist=new ArrayList<String>();
		tablevaluelist=new ArrayList<ProductionDataBean>();
		System.out.println(tablevaluelist);
		try{
			
			if(tableValidate(true)){
				tableflag=true;
				int row=Integer.parseInt(productionDataBean.getTableRow());
				System.out.println(row);
				for (int i = 0; i < row ; i++) {
					ProductionDataBean production=new ProductionDataBean();
					production.setSerialNo(""+(i+1));
					tablevaluelist.add(production);
				   }
			
			if(productionDataBean.getTableColumn().equalsIgnoreCase("1")){
				
				aflag=true;
				bflag=false;
				cflag=false;
				dflag=false;
				eflag=false;
				fflag=false;
				gflag=false;
				hflag=false;
				iflag=false;
				jflag=false;
			}else if (productionDataBean.getTableColumn().equalsIgnoreCase("2")) {
				aflag=true;
				bflag=true;
				cflag=false;
				dflag=false;
				eflag=false;
				fflag=false;
				gflag=false;
				hflag=false;
				iflag=false;
				jflag=false;
		
			}else if (productionDataBean.getTableColumn().equalsIgnoreCase("3")) {
				aflag=true;
				bflag=true;
				cflag=true;
				dflag=false;
				eflag=false;
				fflag=false;
				gflag=false;
				hflag=false;
				iflag=false;
				jflag=false;
			}else if (productionDataBean.getTableColumn().equalsIgnoreCase("4")) {
				aflag=true;
				bflag=true;
				cflag=true;
				dflag=true;
				eflag=false;
				fflag=false;
				gflag=false;
				hflag=false;
				iflag=false;
				jflag=false;
				
			}else if (productionDataBean.getTableColumn().equalsIgnoreCase("5")) {
				aflag=true;
				bflag=true;
				cflag=true;
				dflag=true;
				eflag=true;
				fflag=false;
				gflag=false;
				hflag=false;
				iflag=false;
				jflag=false;
			}else if (productionDataBean.getTableColumn().equalsIgnoreCase("6")) {
				aflag=true;
				bflag=true;
				cflag=true;
				dflag=true;
				eflag=true;
				fflag=true;
				gflag=false;
				hflag=false;
				iflag=false;
				jflag=false;
				
			}else if (productionDataBean.getTableColumn().equalsIgnoreCase("7")) {
				aflag=true;
				bflag=true;
				cflag=true;
				dflag=true;
				eflag=true;
				fflag=true;
				gflag=true;
				hflag=false;
				iflag=false;
				jflag=false;
			}else if (productionDataBean.getTableColumn().equalsIgnoreCase("8")) {
				aflag=true;
				bflag=true;
				cflag=true;
				dflag=true;
				eflag=true;
				fflag=true;
				gflag=true;
				hflag=true;
				iflag=false;
				jflag=false;
			}else if (productionDataBean.getTableColumn().equalsIgnoreCase("9")) {
				aflag=true;
				bflag=true;
				cflag=true;
				dflag=true;
				eflag=true;
				fflag=true;
				gflag=true;
				hflag=true;
				iflag=true;
				jflag=false;
			}else if (productionDataBean.getTableColumn().equalsIgnoreCase("10")) {
				aflag=true;
				bflag=true;
				cflag=true;
				dflag=true;
				eflag=true;
				fflag=true;
				gflag=true;
				hflag=true;
				iflag=true;
				jflag=true;
				
			}
		}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	private boolean tableValidate(boolean valid) {
		  valid=true;String fieldName;
		  FacesContext fc=FacesContext.getCurrentInstance();
		  
		  if(StringUtils.isEmpty(productionDataBean.getTableName())){
		   fieldName=CommonValidate.findComponentInRoot("name").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please Enter the Table Name"));
		   valid=false;
		  }
		  
		  if(StringUtils.isEmpty(productionDataBean.getTableNo())){
			   fieldName=CommonValidate.findComponentInRoot("no").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please Enter the Table No"));
			   valid=false;
		 }else{
			 String status="";
			 status=bo.checkTableno(productionDataBean);
			 if(status.equalsIgnoreCase("EXIST")){
				 fieldName=CommonValidate.findComponentInRoot("no").getClientId(fc);
				   fc.addMessage(fieldName, new FacesMessage("Table No already exist"));
				   valid=false;
			 }
		 }
		  
		  if(StringUtils.isEmpty(productionDataBean.getTableColumn())){
		   fieldName=CommonValidate.findComponentInRoot("column").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the table column"));
		   
		   valid=false;
		  }else if (!StringUtils.isEmpty(productionDataBean.getTableColumn())) {
			 
				if (!CommonValidate.validateNumber(productionDataBean.getTableColumn())) {
					System.out.println(">>>>>>>>>>>>>>>>ADFgvgjyj");
						fieldName = CommonValidate.findComponentInRoot("column").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("table column should be numeric"));
						valid = false;
				}else{
					 int column=Integer.parseInt(productionDataBean.getTableColumn());
					 if (column >10 || column==0) {
							System.out.println(">>>>>>>>>>>>>>");
								fieldName = CommonValidate.findComponentInRoot("column").getClientId(fc);
								fc.addMessage(fieldName, new FacesMessage("table column should be 10 below"));
								valid = false;
							
						}
				}
				
		  }
		  
		  if(StringUtils.isEmpty(productionDataBean.getTableRow())){
			   fieldName=CommonValidate.findComponentInRoot("row").getClientId(fc);
			   fc.addMessage(fieldName, new FacesMessage("please enter the table row"));
			   valid=false;
			  }else if (!StringUtils.isEmpty(productionDataBean.getTableRow())) {
					if (!CommonValidate.validateNumber(productionDataBean.getTableRow())) {
							fieldName = CommonValidate.findComponentInRoot("row").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("table row should be numeric"));
							valid = false;
					}
			  }
		  return valid;
		}
	
	public String tablesave(){
		String status="";
		System.out.println("Inside tablesave");
		System.out.println("value submit" +tablevaluelist.size());
		try{
			productionDataBean.setTablevaluelist(tablevaluelist);
			status=bo.savetable(personID,clientID,productionDataBean);
			if(status.equalsIgnoreCase("Success"));{
				RequestContext.getCurrentInstance().execute("PF('tableReg').hide()");
				RequestContext.getCurrentInstance().execute("PF('tablesuccess').show()");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public String addhistory() {
		String status="";
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(tableviewlist.size()>0){
				productionDataBean.setTableviewlist(tableviewlist);
				status=bo.addhistory(productionDataBean);
				if(status.equalsIgnoreCase("success")){
					RequestContext.getCurrentInstance().execute("PF('tablesuccess1').show()");
				}else{
					fieldName = CommonValidate.findComponentInRoot("tblmsg").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please select the table"));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public String tableHistory() {
		try{
			tablehistorylist=new ArrayList<ProductionDataBean>();
			tablehistorylist=bo.gettableHistory(productionDataBean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
}