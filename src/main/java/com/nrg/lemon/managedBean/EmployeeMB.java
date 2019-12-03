package com.nrg.lemon.managedBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.lemon.bo.LemonBo;
import com.nrg.lemon.domain.EmployeeDataBean;
import com.nrg.lemon.shared.Attendance;
import com.nrg.lemon.shared.Employee;
import com.nrg.lemon.util.CommonValidate;

@ManagedBean(name="employeeMB")
@RequestScoped
public class EmployeeMB {

	EmployeeDataBean employeeDataBean=new EmployeeDataBean();
	String personID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PersonID");
	String clientID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ClientID");
	ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	LemonBo bo=(LemonBo)ctx.getBean("bo");
	
	private List<EmployeeDataBean> branchinfoList=null;
	private List<String> branchlist=null;
	private List<EmployeeDataBean> employeeList=null;
	private List<Employee> employeeDetaillist=null;
	private List<EmployeeDataBean> announcementList=null;
	private List<EmployeeDataBean> attendanceList=null;
	private List<EmployeeDataBean> attendanceInfolist=null;
	private List<String> employeenameList=null;
	private List<String> branchList=null;
	
	
	

	public List<String> getEmployeenameList() {
		return employeenameList;
	}

	public void setEmployeenameList(List<String> employeenameList) {
		this.employeenameList = employeenameList;
	}

	public List<String> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<String> branchList) {
		this.branchList = branchList;
	}

	public List<EmployeeDataBean> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(List<EmployeeDataBean> attendanceList) {
		this.attendanceList = attendanceList;
	}

	public List<EmployeeDataBean> getAttendanceInfolist() {
		return attendanceInfolist;
	}

	public void setAttendanceInfolist(List<EmployeeDataBean> attendanceInfolist) {
		this.attendanceInfolist = attendanceInfolist;
	}

	public List<EmployeeDataBean> getAnnouncementList() {
		return announcementList;
	}

	public void setAnnouncementList(List<EmployeeDataBean> announcementList) {
		this.announcementList = announcementList;
	}

	public List<Employee> getEmployeeDetaillist() {
		return employeeDetaillist;
	}

	public void setEmployeeDetaillist(List<Employee> employeeDetaillist) {
		this.employeeDetaillist = employeeDetaillist;
	}

	public List<EmployeeDataBean> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeDataBean> employeeList) {
		this.employeeList = employeeList;
	}

	public List<String> getBranchlist() {
		return branchlist;
	}

	public void setBranchlist(List<String> branchlist) {
		this.branchlist = branchlist;
	}

	public List<EmployeeDataBean> getBranchinfoList() {
		return branchinfoList;
	}

	public void setBranchinfoList(List<EmployeeDataBean> branchinfoList) {
		this.branchinfoList = branchinfoList;
	}

	public EmployeeDataBean getEmployeeDataBean() {
		return employeeDataBean;
	}

	public void setEmployeeDataBean(EmployeeDataBean employeeDataBean) {
		this.employeeDataBean = employeeDataBean;
	}

//***BRANCH****//
	public String branchPage(){
		try{
			branchinfoList=new ArrayList<EmployeeDataBean>();
			branchinfoList=bo.getbranchInfo(personID,clientID);
			employeeDataBean.setBonus("");
			employeeDataBean.setBranchName("");
			employeeDataBean.setLate("");
			employeeDataBean.setOvertime("");
		}catch(Exception e){
			e.printStackTrace();
		}
		 return "employeeBranch";
	}
	
	public String saveBranch(){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			if(personID!=null && clientID!=null){
				if(addbranceValidation(true)){
				String status=bo.insertBranch(employeeDataBean,personID,clientID);
				if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('branchreg').hide();");
					RequestContext.getCurrentInstance().execute("PF('confirm').show();");
					employeeDataBean.setBonus("");
					employeeDataBean.setBranchName("");
					employeeDataBean.setLate("");
					employeeDataBean.setOvertime("");
				}else if("Exists".equalsIgnoreCase(status)){
					 fieldName=CommonValidate.findComponentInRoot("name").getClientId(fc);
					   fc.addMessage(fieldName, new FacesMessage("the branch name already Exists"));
				}
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return"";
	}

	private boolean addbranceValidation(boolean valid) {
		valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		  if(StringUtils.isEmpty(employeeDataBean.getBranchName())){
		   fieldName=CommonValidate.findComponentInRoot("name").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the branch name"));
		   valid=false;
		  }
		  if(StringUtils.isEmpty(employeeDataBean.getBonus())){
		   fieldName=CommonValidate.findComponentInRoot("bonus").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the bonus 6 days"));
		   valid=false;
		  }
		  if(StringUtils.isEmpty(employeeDataBean.getOvertime())){
		   fieldName=CommonValidate.findComponentInRoot("overtime").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the overtime/hour"));
		   valid=false;
		  }
		  if(StringUtils.isEmpty(employeeDataBean.getLate())){
		   fieldName=CommonValidate.findComponentInRoot("late").getClientId(fc);
		   fc.addMessage(fieldName, new FacesMessage("please enter the late/minute"));
		   valid=false;
		  }
		  return valid;
	}

	public void branchedit(RowEditEvent row){
		if(personID!=null && clientID!=null){
		try{
			String name=((EmployeeDataBean)row.getObject()).getBranchName();
			String bonus=((EmployeeDataBean) row.getObject()).getBonus();
			String overtime=((EmployeeDataBean) row.getObject()).getOvertime();
			String late=((EmployeeDataBean) row.getObject()).getLate();
			employeeDataBean.setBranchName(name);
			employeeDataBean.setBonus(bonus);
			employeeDataBean.setOvertime(overtime);
			employeeDataBean.setLate(late);
			if(brancheditValidation(true)){
			String status=bo.updateBranch(personID,clientID,employeeDataBean);
			if("Success".equalsIgnoreCase(status)){
				RequestContext.getCurrentInstance().execute("PF('edit').show()");
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	}
	
	private boolean brancheditValidation(boolean valid) {
		valid=true;
		if(StringUtils.isEmpty(employeeDataBean.getBonus())){
			FacesMessage msg=new FacesMessage("PLEASE ENTER THE BONUS");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getOvertime())){
			FacesMessage msg=new FacesMessage("PLEASE ENTER THE OVERTIME");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getLate())){
			FacesMessage msg=new FacesMessage("PLEASE ENTER THE LATE/MINUTE");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid=false;
		}
		return valid;
	}

	public String branchDelete(EmployeeDataBean employeeDataBean){
		if(personID!=null && clientID!=null){
		try{
			String status=bo.deleteBranch(personID,clientID,employeeDataBean);
			if("Success".equalsIgnoreCase(status)){
				RequestContext.getCurrentInstance().execute("PF('delete').show()");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return "";
	}
	
//****SALARY****//
	public String salaryPage(){
		try{
			branchlist=new ArrayList<String>();
			employeeList=new ArrayList<EmployeeDataBean>();
			branchlist=bo.branchlist(personID,clientID);
			employeeList=bo.getemployeeInfo(personID,clientID);
			employeeDataBean.setBranchName("");
			employeeDataBean.setAddress("");
			employeeDataBean.setAlergy("");
			employeeDataBean.setBloodType("");
			employeeDataBean.setCommission("");
			employeeDataBean.setContactName1("");
			employeeDataBean.setContactName2("");
			employeeDataBean.setDaily("");
			employeeDataBean.setDateofBirth(null);
			employeeDataBean.setEmergencyAddress1("");
			employeeDataBean.setEmergencyAddress2("");
			employeeDataBean.setEmergencyContactno1("");
			employeeDataBean.setEmergencyContactno2("");
			employeeDataBean.setEmergencyContactno3("");
			employeeDataBean.setEmergencyContactno4("");
			employeeDataBean.setEmployeeName("");
			employeeDataBean.setEntryDate(null);
			employeeDataBean.setPhoneNo1("");
			employeeDataBean.setPhoneNo2("");
			employeeDataBean.setEmployeeBranch("");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "employeeSalary";
	}
	
	public String saveEmployee(){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(personID!=null && clientID!=null){
		try{
			if(employeeValidation(true)){
				String status=bo.inserteEmployee(employeeDataBean,personID,clientID);
				if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('employeereg').hide()");
					RequestContext.getCurrentInstance().execute("PF('confirm').show()");
				}else if("Exists".equalsIgnoreCase(status)){
					fieldName=CommonValidate.findComponentInRoot("no1").getClientId();
					fc.addMessage(fieldName, new FacesMessage("the phonenumber already exists"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return "";
	}
	
private boolean employeeValidation(boolean valid) {
		valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(employeeDataBean.getEmployeeBranch())){
			fieldName=CommonValidate.findComponentInRoot("branch").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please select the branch "));
			valid=false;
		}
		if(employeeDataBean.getEntryDate()==null){
			fieldName=CommonValidate.findComponentInRoot("edate").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please select the entery date "));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getEmployeeName())){
			fieldName=CommonValidate.findComponentInRoot("name").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the name"));
			valid=false;
		}else if(!StringUtils.isEmpty(employeeDataBean.getEmployeeName())){
			if(!CommonValidate.validateName(employeeDataBean.getEmployeeName())){
				fieldName=CommonValidate.findComponentInRoot("name").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the valid name"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(employeeDataBean.getDaily())){
			fieldName=CommonValidate.findComponentInRoot("daily").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the daily"));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getCommission())){
			fieldName=CommonValidate.findComponentInRoot("comm").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the commission"));
			valid=false;
		}
		if(employeeDataBean.getDateofBirth()==null){
			fieldName=CommonValidate.findComponentInRoot("dob").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the date of birth"));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getAddress())){
			fieldName=CommonValidate.findComponentInRoot("address").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the address"));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getPhoneNo1())){
			fieldName=CommonValidate.findComponentInRoot("no1").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the phonenumber"));
			valid=false;
		}else if(!StringUtils.isEmpty(employeeDataBean.getPhoneNo1())){
			if(!CommonValidate.validateNumber(employeeDataBean.getPhoneNo1())){
				fieldName=CommonValidate.findComponentInRoot("no1").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the valid phonenumber"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(employeeDataBean.getBloodType())){
			fieldName=CommonValidate.findComponentInRoot("blood").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the bloodtype"));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getContactName1())){
			fieldName=CommonValidate.findComponentInRoot("name1").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the contactname"));
			valid=false;
		}else if(!StringUtils.isEmpty(employeeDataBean.getContactName1())){
			if(!CommonValidate.validateName(employeeDataBean.getContactName1())){
				fieldName=CommonValidate.findComponentInRoot("name1").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the valid contactname"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(employeeDataBean.getEmergencyContactno1())){
			fieldName=CommonValidate.findComponentInRoot("no3").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the contact number"));
			valid=false;
		}else if(!StringUtils.isEmpty(employeeDataBean.getEmergencyContactno1())){
			if(!CommonValidate.validateNumber(employeeDataBean.getEmergencyContactno1())){
				fieldName=CommonValidate.findComponentInRoot("no3").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the valid contactnumber"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(employeeDataBean.getEmergencyAddress1())){
			fieldName=CommonValidate.findComponentInRoot("address1").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the emergency address"));
			valid=false;
		}
		if(!StringUtils.isEmpty(employeeDataBean.getContactName2())){
			if(!CommonValidate.validateName(employeeDataBean.getContactName2())){
				fieldName=CommonValidate.findComponentInRoot("name2").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the valid contactname"));
				valid=false;
			}
		}
		return valid;
	}

	public void reset() {
		employeeDataBean.setAddress("");
		employeeDataBean.setAlergy("");
		employeeDataBean.setBloodType("");
		employeeDataBean.setCommission("");
		employeeDataBean.setContactName1("");
		employeeDataBean.setContactName2("");
		employeeDataBean.setDaily("");
		employeeDataBean.setDateofBirth(null);
		employeeDataBean.setEmergencyAddress1("");
		employeeDataBean.setEmergencyAddress2("");
		employeeDataBean.setEmergencyContactno1("");
		employeeDataBean.setEmergencyContactno2("");
		employeeDataBean.setEmergencyContactno3("");
		employeeDataBean.setEmergencyContactno4("");
		employeeDataBean.setEmployeeName("");
		employeeDataBean.setEntryDate(null);
		employeeDataBean.setPhoneNo1("");
		employeeDataBean.setPhoneNo2("");
		employeeDataBean.setEmployeeBranch("");
	}
	
	public String employeeView(){
		if(personID!=null && clientID!=null){
		try{
			employeeDetaillist=bo.employeeDetails(employeeDataBean,personID,clientID);
			if(employeeDetaillist.size()>0){
				employeeDataBean.setEmployeeBranch(employeeDetaillist.get(0).getBranch().getBranchName());
				employeeDataBean.setEntryDate(employeeDetaillist.get(0).getEntryDate());
				employeeDataBean.setEmployeeName(employeeDetaillist.get(0).getEmployeeName());
				employeeDataBean.setDaily(employeeDetaillist.get(0).getDaily());
				employeeDataBean.setCommission(employeeDetaillist.get(0).getComission());
				employeeDataBean.setDateofBirth(employeeDetaillist.get(0).getDob());
				employeeDataBean.setAddress(employeeDetaillist.get(0).getAddress());
				employeeDataBean.setPhoneNo2(employeeDetaillist.get(0).getPhoneNo2());
				employeeDataBean.setBloodType(employeeDetaillist.get(0).getBloodgroup());
				employeeDataBean.setAlergy(employeeDetaillist.get(0).getAlergy());
				employeeDataBean.setContactName1(employeeDetaillist.get(0).getContactName1());
				employeeDataBean.setContactName2(employeeDetaillist.get(0).getContactName2());
				employeeDataBean.setEmergencyContactno1(employeeDetaillist.get(0).getEmergencyContactno1());
				employeeDataBean.setEmergencyContactno2(employeeDetaillist.get(0).getEmergencyContactno2());
				employeeDataBean.setEmergencyContactno3(employeeDetaillist.get(0).getEmergencyContactno3());
				employeeDataBean.setEmergencyContactno4(employeeDetaillist.get(0).getEmergencyContactno4());
				employeeDataBean.setEmergencyAddress1(employeeDetaillist.get(0).getEmergencyAddress1());
				employeeDataBean.setEmergencyAddress2(employeeDetaillist.get(0).getEmergencyAddress2());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return "";
	}
	
	public String employeeUpdate(){
		if(personID!=null && clientID!=null){
			try{
				if(employeeeditValidation(true))
				{	
					String status=bo.updateEmployee(employeeDataBean,personID,clientID);
					if("Success".equalsIgnoreCase(status)){
						RequestContext.getCurrentInstance().execute("PF('edit').show()");
						RequestContext.getCurrentInstance().execute("PF('employeeEdit').hide()");
					}
				}
				}catch(Exception e){
				e.printStackTrace();
			}
		}
		return "";
	}
	private boolean employeeeditValidation(boolean valid) {
		valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(employeeDataBean.getEmployeeBranch())){
			fieldName=CommonValidate.findComponentInRoot("Ebranch").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please select the branch "));
			valid=false;
		}
		if(employeeDataBean.getEntryDate()==null){
			fieldName=CommonValidate.findComponentInRoot("Eedate").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please select the entery date "));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getEmployeeName())){
			fieldName=CommonValidate.findComponentInRoot("Ename").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the name"));
			valid=false;
		}else if(!StringUtils.isEmpty(employeeDataBean.getEmployeeName())){
			if(!CommonValidate.validateName(employeeDataBean.getEmployeeName())){
				fieldName=CommonValidate.findComponentInRoot("Ename").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the valid name"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(employeeDataBean.getDaily())){
			fieldName=CommonValidate.findComponentInRoot("Edaily").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the daily"));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getCommission())){
			fieldName=CommonValidate.findComponentInRoot("Ecomm").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the commission"));
			valid=false;
		}
		if(employeeDataBean.getDateofBirth()==null){
			fieldName=CommonValidate.findComponentInRoot("Edob").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the date of birth"));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getAddress())){
			fieldName=CommonValidate.findComponentInRoot("Eaddress").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the address"));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getBloodType())){
			fieldName=CommonValidate.findComponentInRoot("Eblood").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the bloodtype"));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getContactName1())){
			fieldName=CommonValidate.findComponentInRoot("Ename1").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the contactname"));
			valid=false;
		}else if(!StringUtils.isEmpty(employeeDataBean.getContactName1())){
			if(!CommonValidate.validateName(employeeDataBean.getContactName1())){
				fieldName=CommonValidate.findComponentInRoot("Ename1").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the valid contactname"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(employeeDataBean.getEmergencyContactno1())){
			fieldName=CommonValidate.findComponentInRoot("Eno3").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the contact number"));
			valid=false;
		}else if(!StringUtils.isEmpty(employeeDataBean.getEmergencyContactno1())){
			if(!CommonValidate.validateNumber(employeeDataBean.getEmergencyContactno1())){
				fieldName=CommonValidate.findComponentInRoot("Eno3").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the valid contactnumber"));
				valid=false;
			}
		}
		if(StringUtils.isEmpty(employeeDataBean.getEmergencyAddress1())){
			fieldName=CommonValidate.findComponentInRoot("Eaddress1").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the emergency address"));
			valid=false;
		}
		if(!StringUtils.isEmpty(employeeDataBean.getContactName2())){
			if(!CommonValidate.validateName(employeeDataBean.getContactName2())){
				fieldName=CommonValidate.findComponentInRoot("Ename2").getClientId();
				fc.addMessage(fieldName, new FacesMessage("please enter the valid contactname"));
				valid=false;
			}
		}
		return valid;
	}
	
	public String employeeDelete(EmployeeDataBean employeeDataBean){
		if(personID!=null && clientID!=null){
		try{
			String status=bo.deleteEmployee(personID,clientID,employeeDataBean);
			if("Success".equalsIgnoreCase(status)){
				RequestContext.getCurrentInstance().execute("PF('delete').show()");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return "";
	}

	public void employeeSearch(){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(personID!=null && clientID!=null){
		try{
			if(employeeDataBean.getBranchName()!=null){
			employeeList=new ArrayList<EmployeeDataBean>();
			employeeList=bo.employeeSearch(employeeDataBean,personID,clientID);
			if(employeeList.size()==0){
				fieldName=CommonValidate.findComponentInRoot("bran").getClientId();
				fc.addMessage(fieldName, new FacesMessage("no record in given branch"));
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	}
//****ATTENDANCE****//
	public String attendancePage(){
		try{
			attendanceList=new ArrayList<EmployeeDataBean>();
			attendanceInfolist=new ArrayList<EmployeeDataBean>();
			branchlist=new ArrayList<String>();
			branchlist=bo.branchlist(personID,clientID);
			employeeDataBean.setBranchName("");
			employeeDataBean.setAttendanceDate(new Date());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "employeeAttendance";
	}
	
	public void attendanceSearch(){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(personID!=null && clientID!=null){
		try{
			if(employeeDataBean.getBranchName()!=null){
			attendanceList=new ArrayList<EmployeeDataBean>();
			attendanceList=bo.attendanceSearch(employeeDataBean,personID,clientID);
			if(attendanceList.size()==0){
				fieldName=CommonValidate.findComponentInRoot("branch").getClientId();
				fc.addMessage(fieldName, new FacesMessage("no record in given branch"));
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	}
	
	public String saveAttendance(){
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(personID!=null && clientID!=null){
		try{
			if(attendanceValidation(true)){
				List<Attendance> list=new ArrayList<Attendance>();
				list=bo.checkAttendance(employeeDataBean,personID,clientID);
				if(list.size()==0){
				String status=bo.saveAttendance(personID,clientID,employeeDataBean,attendanceList);
				if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('confirm').show()");
				}
				}else{
					fieldName=CommonValidate.findComponentInRoot("date").getClientId();
					fc.addMessage(fieldName, new FacesMessage("already give the attendance for the day"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return"";
	}
	
   private boolean attendanceValidation(boolean valid) {
	   valid=true;
	   String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(employeeDataBean.getBranchName())){
			fieldName=CommonValidate.findComponentInRoot("branch").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please select the branch "));
			valid=false;
		}
		if(employeeDataBean.getAttendanceDate()==null){
			fieldName=CommonValidate.findComponentInRoot("date").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please select the  date "));
			valid=false;
		}
		return valid;
	}

   public void attendanceView(){
	   	String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(personID!=null && clientID!=null){
			try{
				if(employeeDataBean.getBranchName()!=null && employeeDataBean.getAttendanceDate()!=null){
					attendanceInfolist=new ArrayList<EmployeeDataBean>();
					attendanceInfolist=bo.attendanceView(personID,clientID,employeeDataBean);
					if(attendanceInfolist.size()==0){
						fieldName=CommonValidate.findComponentInRoot("branch1").getClientId();
						fc.addMessage(fieldName, new FacesMessage("no record in given branch"));
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void attendanceEdit(RowEditEvent row){
		if(personID!=null && clientID!=null){
		try{
			int id=((EmployeeDataBean)row.getObject()).getHiddenValue();
			String attend=((EmployeeDataBean)row.getObject()).getAttend();
			String late=((EmployeeDataBean)row.getObject()).getLate();
			String  overtime=((EmployeeDataBean)row.getObject()).getOvertime();
			//String forfeit=((EmployeeDataBean)row.getObject()).getForfeit();
			String reward=((EmployeeDataBean)row.getObject()).getReward();
			employeeDataBean.setHiddenValue(id);
			employeeDataBean.setAttend(attend);
			employeeDataBean.setOvertime(overtime);
			employeeDataBean.setLate(late);
			//employeeDataBean.setForfeit(forfeit);
			employeeDataBean.setReward(reward);
			String status=bo.attendanceUpdate(employeeDataBean, personID, clientID);
			if("Success".equalsIgnoreCase(status)){
				RequestContext.getCurrentInstance().execute("PF('edit').show()");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	}
	
	//****ANNOUNCEMENT****//
	public String announcementPage(){
	  try{
	   String post=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
	   employeeDataBean.setAnnouncementDate(new Date());
	   employeeDataBean.setPostBy(post);
	   employeeDataBean.setAnnouncement("");
	   announcementList=new ArrayList<EmployeeDataBean>();
	   announcementList=bo.announcementInfo(personID,clientID);
	  }catch(Exception e){
	   e.printStackTrace();
	  }
	  return "employeeAnnouncement";
	 }
	
	public String saveAnnouncement(){
		if(personID!=null && clientID!=null ){
		try{
			if(announcementValidation(true)){
			   String status=bo.insertAnnouncement(employeeDataBean,personID,clientID);
			   if("Success".equalsIgnoreCase(status)){
				   RequestContext.getCurrentInstance().execute("PF('confirm').show()");
			   }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return"";
	}

	private boolean announcementValidation(boolean valid) {
		valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(employeeDataBean.getPostBy())){
			fieldName=CommonValidate.findComponentInRoot("postby").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the post by "));
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getAnnouncement())){
			fieldName=CommonValidate.findComponentInRoot("annoce").getClientId();
			fc.addMessage(fieldName, new FacesMessage("please enter the announcement"));
			valid=false;
		}
		return valid;
	}
	
	public void announcementEdit(RowEditEvent row){
		if(personID!=null && clientID!=null){
			try{
				int id=((EmployeeDataBean)row.getObject()).getHiddenValue();
				String posyBy=((EmployeeDataBean) row.getObject()).getPostBy();
				String announcement=((EmployeeDataBean) row.getObject()).getAnnouncement();
				employeeDataBean.setHiddenValue(id);
				employeeDataBean.setPostBy(posyBy);
				employeeDataBean.setAnnouncement(announcement);
				if(announcementeditValidation(true)){
				String status=bo.updateAnnouncement(personID,clientID,employeeDataBean);
				if("Success".equalsIgnoreCase(status)){
					RequestContext.getCurrentInstance().execute("PF('edit').show()");
				}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private boolean announcementeditValidation(boolean valid) {
		valid=true;
		if(StringUtils.isEmpty(employeeDataBean.getPostBy())){
			FacesMessage msg=new FacesMessage("PLEASE ENTER THE POST BY");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid=false;
		}
		if(StringUtils.isEmpty(employeeDataBean.getAnnouncement())){
			FacesMessage msg=new FacesMessage("PLEASE ENTER THE ANNOUNCEMENT");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid=false;
		}
		
		return valid;
	}
	
	public String announcementDelete(EmployeeDataBean employeeDataBean){
		if(personID!=null && clientID!=null){
		try{
			String status=bo.deleteAnnouncement(personID,clientID,employeeDataBean);
			if("Success".equalsIgnoreCase(status)){
				RequestContext.getCurrentInstance().execute("PF('delete').show()");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return "";
	}
	
	public String employeeReport(){
		try{
			employeeDataBean.setBranchName("");
			employeeDataBean.setEmployeeName("");
			employeeDataBean.setFromDate(null);
			employeeDataBean.setToDate(null);
			if(personID != null && clientID != null){
				employeeList=new ArrayList<EmployeeDataBean>();
				employeeList=bo.getEmployeeReportList(personID,clientID);
				branchList=bo.getbaranchname(personID,clientID);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "employeeReport";
	}
	public void getemployeename(){
		try{
			if(personID != null && clientID != null){
				employeenameList=new ArrayList<String>();
				employeenameList=bo.getemployeeNames(personID,clientID,employeeDataBean);
				employeeList=new ArrayList<EmployeeDataBean>();
				employeeList=bo.getEmployeeReportList1(personID,clientID,employeeDataBean);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getemploydetail(){
		String fieldName="";
		FacesContext fc=FacesContext.getCurrentInstance();
		try{
			employeeList=new ArrayList<EmployeeDataBean>();
			if(personID != null && clientID != null){
				if(employeeDataBean.getFromDate()==null && employeeDataBean.getToDate()!=null){
					fieldName=CommonValidate.findComponentInRoot("fromdate").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please select from date"));
				}
				else if(employeeDataBean.getFromDate()!=null && employeeDataBean.getToDate()==null){
					fieldName=CommonValidate.findComponentInRoot("todate").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("please select to date"));
				}
				else{
					employeeList=bo.getEmployeeReportList1(personID,clientID,employeeDataBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
