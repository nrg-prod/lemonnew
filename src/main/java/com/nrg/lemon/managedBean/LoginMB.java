package com.nrg.lemon.managedBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;
import com.nrg.lemon.bo.LemonBo;
import com.nrg.lemon.domain.LoginDataBean;
import com.nrg.lemon.shared.Subproduct;
import com.nrg.lemon.shared.UserProduct;
import com.nrg.lemon.util.CommonValidate;
import com.nrg.lemon.util.Util;

@ManagedBean(name="loginMB")
public class LoginMB {
	
	ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	LemonBo bo=(LemonBo)ctx.getBean("bo");
	private static Logger logger = Logger.getLogger(LoginMB.class);
	LoginDataBean loginDataBean=new LoginDataBean();
	private MenuModel model;
	private List<UserProduct> menulist=null;
	private List<Subproduct> submenulist=null;
	public int stockcount;
	private int sablonquantity;
	private int cmtquantity;
	
	public int getSablonquantity() {
		return sablonquantity;
	}
	public void setSablonquantity(int sablonquantity) {
		this.sablonquantity = sablonquantity;
	}
	public int getCmtquantity() {
		return cmtquantity;
	}
	public void setCmtquantity(int cmtquantity) {
		this.cmtquantity = cmtquantity;
	}
	public int getStockcount() {
		return stockcount;
	}
	public void setStockcount(int stockcount) {
		this.stockcount = stockcount;
	}
	
	public MenuModel getModel() {
		return model;
	}
	public void setModel(MenuModel model) {
		this.model = model;
	}
	public List<UserProduct> getMenulist() {
		return menulist;
	}
	public void setMenulist(List<UserProduct> menulist) {
		this.menulist = menulist;
	}
	public List<Subproduct> getSubmenulist() {
		return submenulist;
	}
	public void setSubmenulist(List<Subproduct> submenulist) {
		this.submenulist = submenulist;
	}

	public LoginDataBean getLoginDataBean() {
		return loginDataBean;
	}
	public void setLoginDataBean(LoginDataBean loginDataBean) {
		this.loginDataBean = loginDataBean;
	}
	
	public String login()
	{
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		String status="";
		if(validation(true)){
			try
			{
				status=	bo.login(loginDataBean);
				if(status.equalsIgnoreCase("Success")){
				 ExternalContext externalcontext = FacesContext.getCurrentInstance().getExternalContext();
				 Map<String,Object> sessionMap = externalcontext.getSessionMap();
				 sessionMap.put("user", loginDataBean.getUserName());
				 sessionMap.put("PersonID", loginDataBean.getPersonID());
				 sessionMap.put("ClientID", loginDataBean.getClientID());
				 logger.debug("check personid-----"+loginDataBean.getPersonID());
				 logger.debug("check clientid------"+loginDataBean.getClientID());
				 loadmenu();
				 return "reminder";
				}else if(status.equalsIgnoreCase("Fail1")){
					fieldName=CommonValidate.findComponentInRoot("username").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("PLEASE ENTER VALID USERNAME"));
					loginDataBean.setUserName("");
					 return "lemonLogin";
				}
				else{
					fieldName=CommonValidate.findComponentInRoot("password").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("PLEASE ENTER VALID PASSWORD"));
					loginDataBean.setPassword("");
					 return "lemonLogin";
				}
			
			}catch(Exception e)
			{
			logger.error("Error Message"+e);
			return"";
			}
			}else{
				return "lemonLogin";
			}
		
	}
	
	
	public String usermanagement(){
				System.out.println("inside user managerment");
			loginDataBean.setPassword("");
			loginDataBean.setNewpassword("");
			loginDataBean.setCfpassword("");
			
			loginDataBean.setNewusername("");
			loginDataBean.setCfusername("");
				
		return "userManagement";
	}
	
	public String userchange(){
		System.out.println("inside test");
		String status="";
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		try
		{
			if(usernamevalidation(true)){
				status=	bo.usernamechanged(loginDataBean);
				System.out.println("chk status"+status);
				if(status.equalsIgnoreCase("Exists")){
					fieldName=CommonValidate.findComponentInRoot("cnewusername").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Give New user name"));
					fieldName=CommonValidate.findComponentInRoot("newusername").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Give New user name"));
				}
				if(status.equalsIgnoreCase("success")){
					RequestContext.getCurrentInstance().execute("PF('confirm').show()");
					System.out.println(loginDataBean.getUserName());
					System.out.println(loginDataBean.getCfusername());
					loginDataBean.setUserName(loginDataBean.getCfusername());
					System.out.println(loginDataBean.getUserName());
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
		return "";
	}
	private boolean usernamevalidation(boolean b) {
		b=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		System.out.println("------>"+loginDataBean.getCfusername()+">>>>>>>>"+loginDataBean.getNewusername());
		if(loginDataBean.getNewusername().equalsIgnoreCase("")){
			fieldName=CommonValidate.findComponentInRoot("newusername").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("ENTER NEW USERNAME"));
			b=false;
		}
		if(loginDataBean.getCfusername().equalsIgnoreCase("")){
			fieldName=CommonValidate.findComponentInRoot("cnewusername").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("ENTER Confirm USERNAME"));
			b=false;
		}
		else if(!loginDataBean.getCfusername().equalsIgnoreCase(loginDataBean.getNewusername())){
			fieldName=CommonValidate.findComponentInRoot("cnewusername").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("User Name Not Matched"));
			b=false;
		}
		return b;
	}
	public String passchange(){
		System.out.println("inside test 1");
		String status="";
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		
		try
		{
			if(passvalidation(true)){
				status=	bo.passwordchange(loginDataBean);
				if(status.equalsIgnoreCase("wrongpass")){
					fieldName=CommonValidate.findComponentInRoot("currentpassword").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Check current password"));
				}
				if(status.equalsIgnoreCase("success")){
					RequestContext.getCurrentInstance().execute("PF('confirm').show()");
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return "";
	}
	
	private boolean passvalidation(boolean b) {
		b=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(loginDataBean.getPassword().equalsIgnoreCase("")){
			fieldName=CommonValidate.findComponentInRoot("currentpassword").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("ENTER Current Password"));
			b=false;
		}
		if(loginDataBean.getNewpassword().equalsIgnoreCase("")){
			fieldName=CommonValidate.findComponentInRoot("newpassword").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("ENTER NEW Password"));
			b=false;
		}
		if(loginDataBean.getCfpassword().equalsIgnoreCase("")){
			fieldName=CommonValidate.findComponentInRoot("cnewpassword").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Confirm NEW Password"));
			b=false;
		}
		else if(!loginDataBean.getCfpassword().equalsIgnoreCase(loginDataBean.getNewpassword())){
			fieldName=CommonValidate.findComponentInRoot("cnewpassword").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Password Not Matched"));
			b=false;
		}
		return b;
	}
	private boolean validation(boolean flag)
	{
		boolean valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(loginDataBean.getUserName()))
		{
			if(flag){
				fieldName=CommonValidate.findComponentInRoot("username").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("PLEASE ENTER USERNAME"));
			}
			valid=false;
		}
		if(StringUtils.isEmpty(loginDataBean.getPassword()))
		{
			if(flag){
				fieldName=CommonValidate.findComponentInRoot("password").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("PLEASE ENTER PASSWORD"));
			}
			valid=false;
		}
		return valid;
	}
	
	public String logout()
	{		
		try
		 {
			 HttpSession session = Util.getSession();
		     session.invalidate();
		     HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();  
	         HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	         Cookie[] cookies = request.getCookies();  
	         Cookie opentoken = null;  
	         for(Cookie c : cookies){  
	             if (c.getName().equals("opentoken"))
	             {  
	                 opentoken = c;  
	                 opentoken.setMaxAge(0);  
	                 opentoken.setValue(""); // it is more elegant to clear the value but not necessary  
	                 response.addCookie(opentoken);  
	                 response.sendRedirect(request.getContextPath());  
	                 break;  
	             }  
	         }  		      
		     return "lemonLogin";
		 }
		 catch(Exception e)
		 {
			 return "failure";			 
		 }
	}	
	
	//menu loading from data base
	private void loadmenu(){
		model=null;
		menulist=null;
		submenulist=null;
		try{
			 model = new DefaultMenuModel();
			 menulist=new ArrayList<UserProduct>();
			 submenulist=new ArrayList<Subproduct>();
			 menulist=bo.loadmenu(loginDataBean);
			 if(menulist.size()>0){
				 for(int i=0;i<menulist.size();i++){
					 DefaultSubMenu submenu = new DefaultSubMenu(menulist.get(i).getProduct().getProductName());
					 submenulist=bo.submenus(menulist.get(i).getProduct().getProduct_ID(),menulist.get(i).getProduct().getProductCode());
					 if(submenulist.size()>0){
						 for(int j=0;j<submenulist.size();j++){
							if(submenulist.get(j).getProductCode().equalsIgnoreCase("BRN001")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{employeeMB.branchPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("SAL001")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{employeeMB.salaryPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("ATT001")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{employeeMB.attendancePage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("ANN001")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{employeeMB.announcementPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("SPLIR002")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{supplyMB.supplierPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("FAB002")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{supplyMB.fabricPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("CHY002")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{supplyMB.chemistryPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("HAG002")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{supplyMB.hangtagPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("LBA002")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{supplyMB.labelPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("ACCES002")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{supplyMB.accessorisPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("PLST002")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{supplyMB.plasticPage}");
								submenu.addElement(menu);
							 }else if(submenulist.get(j).getProductCode().equalsIgnoreCase("INV002")){
									DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
									menu.setAjax(false);
									menu.setCommand("#{supplyMB.invoicePage}");
									submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("VEN003")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{productionMB.vendorRegPage}");
								submenu.addElement(menu);	 
							 } else if(submenulist.get(j).getProductCode().equalsIgnoreCase("CAT003")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{productionMB.categoryRegPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("MOD003")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{productionMB.modelRegPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("TAB003")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{productionMB.tableRegPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("CUTT003")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{productionMB.cutterPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("PNTR003")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{productionMB.productionPrinterPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("CMT003")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{productionMB.cmtPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("PROT003")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{productionMB.productPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("CMTSTK003")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{productionMB.cmtstockPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("WAR004")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{stockMB.warehousePage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("PURC004")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{stockMB.manualStockOutPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("SALE004")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{stockMB.stockOutPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("LITM004")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{stockMB.stockViewPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("ADJNT004")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{stockMB.stkAdjustmentPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("OPSK004")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{stockMB.openingStockPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("SPLY005")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{returnMB.supplyPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("CMT005")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{stockMB.cmtModel}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("PRNT005")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{stockMB.printerModel}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("SPLY006")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{payrollMB.printerPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("CUTR006")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{payrollMB.cmtPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("EMPL006")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{payrollMB.purchasePage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("PET006")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{payrollMB.panaltyPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("EMS006")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{payrollMB.employeeSalaryPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("TRSUY007")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{financeMB.transactionPage}");
								submenu.addElement(menu);
							 }else if(submenulist.get(j).getProductCode().equalsIgnoreCase("SAV007")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{financeMB.savingPage}");
								submenu.addElement(menu);	 
							 } else if(submenulist.get(j).getProductCode().equalsIgnoreCase("WAE007")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{financeMB.waste}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("INCO007")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{financeMB.incomePage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("DEBT004")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{financeMB.debtPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("REM008")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setUrl("../../pages/xhtml/reminder.xhtml");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("EMP008")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{employeeMB.employeeReport}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("SUPP008")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{supplyMB.supplyReport}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("PRDU008")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{productionMB.productionReminder}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("STK008")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{stockMB.stockReport}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("RET008")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{returnMB.returnreport}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("PAYR008")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{payrollMB.payrollReport}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("FIN008")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{financeMB.finance}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("CUT006")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{payrollMB.cutterPage}");
								submenu.addElement(menu);
							}else if(submenulist.get(j).getProductCode().equalsIgnoreCase("CUSO009")){
								DefaultMenuItem menu=new DefaultMenuItem(submenulist.get(j).getProductName());
								menu.setAjax(false);
								menu.setCommand("#{customerRegistrationMB.customerView}");
								submenu.addElement(menu);
							}
						 }
				     }
					 model.addElement(submenu);
				 }
			 }
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
		
}
	

