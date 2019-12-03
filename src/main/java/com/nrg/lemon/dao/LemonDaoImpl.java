package com.nrg.lemon.dao;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Status;

import org.apache.commons.collections.set.CompositeSet.SetMutator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nrg.lemon.domain.CustomerDatabean;
import com.nrg.lemon.domain.EmployeeDataBean;
import com.nrg.lemon.domain.FinanceDataBean;
import com.nrg.lemon.domain.LoginDataBean;
import com.nrg.lemon.domain.PayrollDataBean;
import com.nrg.lemon.domain.ProductionDataBean;
import com.nrg.lemon.domain.ReturnDataBean;
import com.nrg.lemon.domain.StockDataBean;
import com.nrg.lemon.domain.SupplyDataBean;
import com.nrg.lemon.shared.Announcement;
import com.nrg.lemon.shared.Attendance;
import com.nrg.lemon.shared.Branch;
import com.nrg.lemon.shared.Category;
import com.nrg.lemon.shared.Client;
import com.nrg.lemon.shared.CmtReturn;
import com.nrg.lemon.shared.CmtStockin;
import com.nrg.lemon.shared.Cmtorder;
import com.nrg.lemon.shared.Cmtpurchaseorder;
import com.nrg.lemon.shared.Cmtready;
import com.nrg.lemon.shared.Cmtreceiveorder;
import com.nrg.lemon.shared.Cmtstock;
import com.nrg.lemon.shared.Customer;
import com.nrg.lemon.shared.Cutterpurchase;
import com.nrg.lemon.shared.Cutterpurchaseorder;
import com.nrg.lemon.shared.Debt;
import com.nrg.lemon.shared.Employee;
import com.nrg.lemon.shared.EmployeePayroll;
import com.nrg.lemon.shared.Fabric;
import com.nrg.lemon.shared.FabricDetail;
import com.nrg.lemon.shared.ItemTable;
import com.nrg.lemon.shared.ManualStock;
import com.nrg.lemon.shared.ManualStockOut;
import com.nrg.lemon.shared.Model;
import com.nrg.lemon.shared.Month;
import com.nrg.lemon.shared.Newtable;
import com.nrg.lemon.shared.OpeningStock;
import com.nrg.lemon.shared.Payment;
import com.nrg.lemon.shared.Penalty;
import com.nrg.lemon.shared.Person;
import com.nrg.lemon.shared.PrinterReturn;
import com.nrg.lemon.shared.Printerorder;
import com.nrg.lemon.shared.Printerpurchaseorder;
import com.nrg.lemon.shared.Printerready;
import com.nrg.lemon.shared.Printerreceiveorder;
import com.nrg.lemon.shared.PursalesItem;
import com.nrg.lemon.shared.PursalesdetailItem;
import com.nrg.lemon.shared.Stock;
import com.nrg.lemon.shared.Subproduct;
import com.nrg.lemon.shared.Supplier;
import com.nrg.lemon.shared.SupplyReturn;
import com.nrg.lemon.shared.TableDetail;
import com.nrg.lemon.shared.Transaction;
import com.nrg.lemon.shared.TransactionCategory;
import com.nrg.lemon.shared.User;
import com.nrg.lemon.shared.UserProduct;
import com.nrg.lemon.shared.Vendor;
import com.nrg.lemon.shared.Warehouse;
import com.nrg.lemon.shared.Year;


@Repository
@Singleton
public class LemonDaoImpl implements LemonDao
{
	private static Logger logger = Logger.getLogger(LemonDaoImpl.class);

	@PersistenceContext(unitName="lemon-pu")
	private EntityManager entityManager;

	Date date=new Date();
	SimpleDateFormat formate=new SimpleDateFormat("dd-MM-yyyy");
	
	@Override
	public String login(LoginDataBean loginDataBean) {
		Query v=null;
		String status="Fail";
		try
		{
			v=entityManager.createQuery("from User where userName=?");
			v.setParameter(1, loginDataBean.getUserName());
			List<User> login=(List<User>)v.getResultList();
			if(login.size()>0)
			{
				 if(login.get(0).getPassword().equalsIgnoreCase(loginDataBean.getPassword()))
				{
					String personID=String.valueOf(login.get(0).getPerson().getPerson_ID());
					String clientID=String.valueOf(login.get(0).getPerson().getClient().getClient_ID());
					loginDataBean.setPersonID(personID);
					loginDataBean.setClientID(clientID);
					status="Success";
				}
				else
				{
					status="Fail";
				}
			}else{
				status="Fail1";
			}
		}
		catch(Exception e){
			logger.error("Error Message"+e);
		}
		return status;
	}
	

	@Override
	public List<UserProduct> loadmenu(LoginDataBean loginDataBean) {
		Query q=null;
		int userid=0;
		List<UserProduct> list=null;
		try{
			userid=getUserID(loginDataBean.getUserName());
			q=entityManager.createQuery("from UserProduct where user_ID=? and status='ACTIVE'");
			q.setParameter(1, userid);
			list=(List<UserProduct>)q.getResultList();
		}catch(Exception e){
			logger.error("Error Message"+e);
		}
		return list;
	}
	
	private int getUserID(String username) {
		int res=0;
		Query q=null;
		try{
			q=entityManager.createQuery("from User where userName=? and status='ACTIVE'");
			q.setParameter(1, username);
			List<User> result=(List<User>)q.getResultList();
			if(result.size() >0){
				res=result.get(0).getUser_ID();
			}
		}catch(Exception e){
			logger.error("Error Message"+e);
		}
		return res;
	}


	@Override
	public List<Subproduct> submenus(int product_ID, String productCode) {
		Query q=null;
		List<Subproduct> list=null;
		try{
			q=entityManager.createQuery("from Subproduct where product_ID=? and status='ACTIVE'");
			q.setParameter(1, product_ID);
			list=(List<Subproduct>)q.getResultList();
		}catch(Exception e){
			logger.error("Error Message"+e);
		}
		return list;
	}

	@Transactional(value="transactionManager")
	@Override
	public String insertBranch(EmployeeDataBean employeeDataBean,String personID, String clientID) {
		String status="Fail";
		Query q=null;
		if(personID !=null && clientID !=null){
		try{
			q=entityManager.createQuery("from Branch where branchName=? and client_ID=? and status='ACTIVE'");
			q.setParameter(1, employeeDataBean.getBranchName());
			q.setParameter(2, Integer.parseInt(clientID));
			List<Branch> branchlist=(List<Branch>)q.getResultList();
			if(branchlist.size()>0){
				status="Exists";
			}else{
				int personid=Integer.parseInt(personID);
				int clientid=Integer.parseInt(clientID);
				Branch branch=new Branch();
				branch.setPerson(entityManager.find(Person.class, personid));
				branch.setClient(entityManager.find(Client.class, clientid));
				branch.setBranchName(employeeDataBean.getBranchName());
				branch.setBonus(employeeDataBean.getBonus());
				branch.setOvertime(employeeDataBean.getOvertime());
				branch.setLate(employeeDataBean.getLate());
				branch.setCreatedDate(date);
				branch.setStatus("ACTIVE");
				entityManager.persist(branch);
				status="Success";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return status;
	}


	@Override
	public List<EmployeeDataBean> getbranchInfo(String personID, String clientID) {
		List<EmployeeDataBean> branchlist=null;
		Query q=null;
		if(personID!=null && clientID!=null){
			try{
				branchlist=new ArrayList<EmployeeDataBean>();
				q=entityManager.createQuery("from Branch where client_ID=? and status='ACTIVE' ORDER BY branch_ID DESC");
				q.setParameter(1, Integer.parseInt(clientID));
				List<Branch> list=(List<Branch>)q.getResultList();
				if(list.size()>0){
					for(Branch branch:list){
						EmployeeDataBean employee=new EmployeeDataBean();
						employee.setBranchName(branch.getBranchName());
						employee.setBonus(branch.getBonus());
						employee.setOvertime(branch.getOvertime());
						employee.setLate(branch.getLate());
						branchlist.add(employee);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return branchlist;
	}

	@Transactional(value="transactionManager")
	@Override
	public String updateBranch(String personID, String clientID,
			EmployeeDataBean employeeDataBean) {
		String status="Fail";
		int branchID=0;
		Query q=null;
		if(personID!=null && clientID!=null){
			try{
				q=entityManager.createQuery("from Branch where branchName=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, employeeDataBean.getBranchName());
				q.setParameter(2, Integer.parseInt(clientID));
				List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
				if(branchlist.size()>0){
					branchID=branchlist.get(0).getBranch_ID();
					Branch branch=entityManager.find(Branch.class, branchID);
					branch.setBonus(employeeDataBean.getBonus());
					branch.setOvertime(employeeDataBean.getOvertime());
					branch.setLate(employeeDataBean.getLate());
					entityManager.merge(branch);
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}

	@Transactional(value="transactionManager")
	@Override
	public String deleteBranch(String personID, String clientID,
			EmployeeDataBean employeeDataBean) {
		String status="Fail";
		int branchID=0;
		Query q=null;
		if(personID!=null && clientID!=null){
			try{
				q=entityManager.createQuery("from Branch where branchName=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, employeeDataBean.getBranchName());
				q.setParameter(2, Integer.parseInt(clientID));
				List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
				if(branchlist.size()>0){
					branchID=branchlist.get(0).getBranch_ID();
					Branch branch=entityManager.find(Branch.class, branchID);
					branch.setStatus("DEACTIVE");
					entityManager.merge(branch);
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}


	@Override
	public List<String> branchlist(String personID, String clientID) {
		Query q=null;
		List<String> branchList=null;
		if(personID!=null && clientID!=null){
		try{
			q=entityManager.createQuery("select branchName from Branch where client_ID=? and status='ACTIVE'");
			q.setParameter(1, Integer.parseInt(clientID));
			branchList=(ArrayList<String>)q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return branchList;
	}

	@Transactional(value="transactionManager")
	@Override
	public String insertEmployee(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		String status="Fail";
		Query q=null;
		int branchid=0;
		if(personID!=null && clientID!=null){
			try{
				q=entityManager.createQuery("from Employee where phoneNo1=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, employeeDataBean.getPhoneNo1());
				q.setParameter(2, Integer.parseInt(clientID));
				List<Employee> employeeList=(ArrayList<Employee>)q.getResultList();
				if(employeeList.size()>0){
					status="Exists";
				}else{
					q=entityManager.createQuery("from Branch where branchName=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, employeeDataBean.getEmployeeBranch());
					q.setParameter(2, Integer.parseInt(clientID));
					List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
					branchid=branchlist.get(0).getBranch_ID();
					Employee employee=new Employee();
					employee.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
					employee.setClient(entityManager.find(Client.class, Integer.parseInt(clientID)));
					employee.setBranch(entityManager.find(Branch.class, branchid));
					employee.setEntryDate(employeeDataBean.getEntryDate());
					employee.setEmployeeName(employeeDataBean.getEmployeeName());
					employee.setDaily(employeeDataBean.getDaily());
					employee.setComission(employeeDataBean.getCommission());
					employee.setDob(employeeDataBean.getDateofBirth());
					employee.setAddress(employeeDataBean.getAddress());
					employee.setPhoneNo1(employeeDataBean.getPhoneNo1());
					employee.setPhoneNo2(employeeDataBean.getPhoneNo2());
					employee.setBloodgroup(employeeDataBean.getBloodType());
					employee.setAlergy(employeeDataBean.getAlergy());
					employee.setContactName1(employeeDataBean.getContactName1());
					employee.setEmergencyContactno1(employeeDataBean.getEmergencyContactno1());
					employee.setEmergencyContactno2(employeeDataBean.getEmergencyContactno2());
					employee.setEmergencyAddress1(employeeDataBean.getEmergencyAddress1());
					employee.setContactName2(employeeDataBean.getContactName2());
					employee.setEmergencyContactno3(employeeDataBean.getEmergencyContactno3());
					employee.setEmergencyContactno4(employeeDataBean.getEmergencyContactno4());
					employee.setEmergencyAddress2(employeeDataBean.getEmergencyAddress2());
					employee.setStatus("ACTIVE");
					entityManager.persist(employee);
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}


	@Override
	public List<EmployeeDataBean> getemployeeInfo(String personID,
			String clientID) {
		Query q=null;
		List<EmployeeDataBean> employeeList=null;
		if(personID!=null && clientID!=null){
			try{
				employeeList=new ArrayList<EmployeeDataBean>();
				q=entityManager.createQuery("from Employee where client_ID=? and status='ACTIVE' ORDER BY employee_ID DESC");
				q.setParameter(1, Integer.parseInt(clientID));
				List<Employee> list=(ArrayList<Employee>)q.getResultList();
				if(list.size()>0){
					for(Employee employee:list){
					q=entityManager.createQuery("from Branch where branch_ID=?");
					q.setParameter(1, employee.getBranch().getBranch_ID());
					List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
					EmployeeDataBean employeeDataBean=new EmployeeDataBean();
					employeeDataBean.setEmployeeBranch(branchlist.get(0).getBranchName());
					employeeDataBean.setEntryDate(employee.getEntryDate());
					employeeDataBean.setEmployeeName(employee.getEmployeeName());
					employeeDataBean.setDaily(employee.getDaily());
					employeeDataBean.setUpdatedDate(employee.getUpdateDate());
					employeeDataBean.setPhoneNo1(employee.getPhoneNo1());
					employeeList.add(employeeDataBean);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return employeeList;
	}


	@Override
	public List<Employee> emolpoyeeDetails(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		Query q=null;
		List<Employee> employeelist=null;
		if(personID!=null && clientID!=null){
			try{
				q=entityManager.createQuery("from Employee where phoneNo1=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, employeeDataBean.getPhoneNo1());
				q.setParameter(2, Integer.parseInt(clientID));
				employeelist=(ArrayList<Employee>)q.getResultList();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return employeelist;
	}

	@Transactional(value="transactionManager")
	@Override
	public String updateEmployee(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		String status="Fail";
		Query q=null;
		int employeeid=0;
		int branchid=0;
		if(personID!=null && clientID!=null){
			try{
				q=entityManager.createQuery("from Employee where phoneNo1=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, employeeDataBean.getPhoneNo1());
				q.setParameter(2, Integer.parseInt(clientID));
				List<Employee> employeelist=(ArrayList<Employee>)q.getResultList();
				if(employeelist.size()>0){
					employeeid=employeelist.get(0).getEmployee_ID();
					q=entityManager.createQuery("from Branch where branchName=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, employeeDataBean.getEmployeeBranch());
					q.setParameter(2, Integer.parseInt(clientID));
					List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
					branchid=branchlist.get(0).getBranch_ID();
					Employee employee=entityManager.find(Employee.class, employeeid);
					employee.setBranch(entityManager.find(Branch.class, branchid));
					employee.setEntryDate(employeeDataBean.getEntryDate());
					employee.setEmployeeName(employeeDataBean.getEmployeeName());
					employee.setDaily(employeeDataBean.getDaily());
					employee.setComission(employeeDataBean.getCommission());
					employee.setDob(employeeDataBean.getDateofBirth());
					employee.setAddress(employeeDataBean.getAddress());
					employee.setPhoneNo2(employeeDataBean.getPhoneNo2());
					employee.setBloodgroup(employeeDataBean.getBloodType());
					employee.setAlergy(employeeDataBean.getAlergy());
					employee.setContactName1(employeeDataBean.getContactName1());
					employee.setEmergencyContactno1(employeeDataBean.getEmergencyContactno1());
					employee.setEmergencyContactno2(employeeDataBean.getEmergencyContactno2());
					employee.setEmergencyAddress1(employeeDataBean.getEmergencyAddress1());
					employee.setContactName2(employeeDataBean.getContactName2());
					employee.setEmergencyContactno3(employeeDataBean.getEmergencyContactno3());
					employee.setEmergencyContactno4(employeeDataBean.getEmergencyContactno4());
					employee.setEmergencyAddress2(employeeDataBean.getEmergencyAddress2());
					employee.setUpdateDate(date);
					entityManager.merge(employee);
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}

	@Transactional(value="transactionManager")
	@Override
	public String deleteEmployee(String personID, String clientID,
			EmployeeDataBean employeeDataBean) {
		String status="Fail";
		int employeeid=0;
		Query q=null;
		if(personID!=null && clientID!=null){
			try{
				q=entityManager.createQuery("from Employee where phoneNo1=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, employeeDataBean.getPhoneNo1());
				q.setParameter(2, Integer.parseInt(clientID));
				List<Employee> branchlist=(ArrayList<Employee>)q.getResultList();
				if(branchlist.size()>0){
					employeeid=branchlist.get(0).getEmployee_ID();
					Employee employee=entityManager.find(Employee.class, employeeid);
					employee.setStatus("DEACTIVE");
					entityManager.merge(employee);
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}


	@Override
	public List<EmployeeDataBean> employeeSearch(
			EmployeeDataBean employeeDataBean, String personID, String clientID) {
		Query q=null;
		int branchid=0;
		List<EmployeeDataBean> employeeList=null;
		if(personID!=null && clientID!=null){
			try{
				employeeList=new ArrayList<EmployeeDataBean>();
				if("ALL".equalsIgnoreCase(employeeDataBean.getBranchName())){
					q=entityManager.createQuery("from Employee where client_ID=? and status='ACTIVE' ORDER BY employee_ID DESC");
					q.setParameter(1, Integer.parseInt(clientID));
					List<Employee> list=(ArrayList<Employee>)q.getResultList();
					if(list.size()>0){
						for(Employee employee:list){
						q=entityManager.createQuery("from Branch where branch_ID=?");
						q.setParameter(1, employee.getBranch().getBranch_ID());
						List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
						employeeDataBean=new EmployeeDataBean();
						employeeDataBean.setEmployeeBranch(branchlist.get(0).getBranchName());
						employeeDataBean.setEntryDate(employee.getEntryDate());
						employeeDataBean.setEmployeeName(employee.getEmployeeName());
						employeeDataBean.setDaily(employee.getDaily());
						employeeDataBean.setUpdatedDate(employee.getUpdateDate());
						employeeDataBean.setPhoneNo1(employee.getPhoneNo1());
						employeeList.add(employeeDataBean);
						}
					}
				}else{
					q=entityManager.createQuery("from Branch where branchName=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, employeeDataBean.getBranchName());
					q.setParameter(2, Integer.parseInt(clientID));
					List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
					if(branchlist.size()>0){
						branchid=branchlist.get(0).getBranch_ID();
						q=entityManager.createQuery("from Employee where branch_ID=? and client_ID=? and status='ACTIVE' ORDER BY employee_ID DESC");
						q.setParameter(1, branchid);
						q.setParameter(2, Integer.parseInt(clientID));
						List<Employee> list=(ArrayList<Employee>)q.getResultList();
						if(list.size()>0){
							employeeDataBean.setEmployeeBranch(branchlist.get(0).getBranchName());
							employeeDataBean.setEntryDate(list.get(0).getEntryDate());
							employeeDataBean.setEmployeeName(list.get(0).getEmployeeName());
							employeeDataBean.setDaily(list.get(0).getDaily());
							employeeDataBean.setUpdatedDate(list.get(0).getUpdateDate());
							employeeDataBean.setPhoneNo1(list.get(0).getPhoneNo1());
							employeeList.add(employeeDataBean);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return employeeList;
	}

	@Transactional(value="transactionManager")
	@Override
	public String insertAnnouncement(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		String status="Fail";
		if(personID!=null && clientID!=null){
			try{
				Announcement announce=new Announcement();
				announce.setAnnouncementDate(employeeDataBean.getAnnouncementDate());
				announce.setPostBy(employeeDataBean.getPostBy());
				announce.setAnnouncement(employeeDataBean.getAnnouncement());
				announce.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
				announce.setClient(entityManager.find(Client.class, Integer.parseInt(clientID)));
				announce.setStatus("Active");
				entityManager.persist(announce);
				status="Success";
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}


	@Override
	public List<EmployeeDataBean> announcementInfo(String personID,
			String clientID) {
		Query q=null;
		List<EmployeeDataBean> employeeList=null;
		if(personID!=null && clientID!=null){
			try{
				employeeList=new ArrayList<EmployeeDataBean>();
				q=entityManager.createQuery("from Announcement where client_ID=? and status='ACTIVE' ORDER BY announcement_ID DESC");
				q.setParameter(1, Integer.parseInt(clientID));
				List<Announcement> announcelist=(ArrayList<Announcement>)q.getResultList();
				if(announcelist.size()>0){
					for(Announcement announce:announcelist){
						EmployeeDataBean employee=new EmployeeDataBean();
						employee.setAnnouncement(announce.getAnnouncement());
						employee.setAnnouncementDate(announce.getAnnouncementDate());
						employee.setPostBy(announce.getPostBy());
						employee.setHiddenValue(announce.getAnnouncement_ID());
						employeeList.add(employee);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return employeeList;
	}

	@Transactional(value="transactionManager")
	@Override
	public String updateAnnouncement(String personID, String clientID,
			EmployeeDataBean employeeDataBean) {
		String status="Fail";
		if(personID!=null &&  clientID!=null){
			try{
				Announcement announcement=entityManager.find(Announcement.class, employeeDataBean.getHiddenValue());
				announcement.setPostBy(employeeDataBean.getPostBy());
				announcement.setAnnouncement(employeeDataBean.getAnnouncement());
				entityManager.merge(announcement);
				status="Success";
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}

	@Transactional(value="transactionManager")
	@Override
	public String deleteAnnouncement(String personID, String clientID,
			EmployeeDataBean employeeDataBean) {
		String status="Fail";
		if (personID!=null && clientID!=null) {
			try{
				Announcement announcement=entityManager.find(Announcement.class, employeeDataBean.getHiddenValue());
				announcement.setStatus("DEACTIVE");
				entityManager.merge(announcement);
				status="Success";
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}


	@Override
	public List<EmployeeDataBean> attendanceSearch(
			EmployeeDataBean employeeDataBean, String personID, String clientID) {
		Query q=null;
		int branchid=0;
		List<EmployeeDataBean> employeeList=null;
		if(personID!=null && clientID!=null){
			try{
				employeeList=new ArrayList<EmployeeDataBean>();
				if("ALL".equalsIgnoreCase(employeeDataBean.getBranchName())){
						q=entityManager.createQuery("from Branch where client_ID=? and status='ACTIVE'");
						q.setParameter(1, Integer.parseInt(clientID) );
						List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
						if(branchlist.size()>0){
							for(int i=0;i<branchlist.size();i++){
								branchid=branchlist.get(i).getBranch_ID();
								q=entityManager.createQuery("from Employee where branch_ID=? and client_ID=? and status='ACTIVE'");
								q.setParameter(1, branchid);
								q.setParameter(2, Integer.parseInt(clientID));
								List<Employee> list=(ArrayList<Employee>)q.getResultList();
								if(list.size()>0){
									for(int j=0;j<list.size();j++){
									employeeDataBean=new EmployeeDataBean();
									employeeDataBean.setEmployeeBranch(branchlist.get(i).getBranchName());
									employeeDataBean.setEmployeeName(list.get(j).getEmployeeName());
									employeeDataBean.setAttend("0");
									employeeDataBean.setLate("0");
									employeeDataBean.setOvertime("0");
									employeeDataBean.setForfeit("0");
									employeeDataBean.setReward("0");
									employeeList.add(employeeDataBean);
									}
								}
							}
						}
					}
				else{
					q=entityManager.createQuery("from Branch where branchName=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, employeeDataBean.getBranchName());
					q.setParameter(2, Integer.parseInt(clientID));
					List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
					if(branchlist.size()>0){
						for(int i=0;i<branchlist.size();i++){
						branchid=branchlist.get(i).getBranch_ID();
						q=entityManager.createQuery("from Employee where branch_ID=? and client_ID=? and status='ACTIVE'");
						q.setParameter(1, branchid);
						q.setParameter(2, Integer.parseInt(clientID));
						List<Employee> list=(ArrayList<Employee>)q.getResultList();
						if(list.size()>0){
							for(int j=0;j<list.size();j++){
							employeeDataBean=new EmployeeDataBean();
							employeeDataBean.setEmployeeBranch(branchlist.get(i).getBranchName());
							employeeDataBean.setEmployeeName(list.get(j).getEmployeeName());
							employeeDataBean.setAttend("0");
							employeeDataBean.setLate("0");
							employeeDataBean.setOvertime("0");
							employeeDataBean.setForfeit("0");
							employeeDataBean.setReward("0");
							employeeList.add(employeeDataBean);
							}
						}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return employeeList;
	}

	@Transactional(value="transactionManager")
	@Override
	public String saveAttendance(String personID, String clientID,
			EmployeeDataBean employeeDataBean,
			List<EmployeeDataBean> attendanceList) {
		String status="Fail";
		Query q=null;
		int branchid=0;
		int employeeid=0;
		if(personID!=null && clientID!=null){
			try{
					for(int i=0;i<attendanceList.size();i++){
						Attendance attendance=new Attendance();
						q=entityManager.createQuery("from Branch where branchName=? and client_ID=? and status='ACTIVE'");
						q.setParameter(1, attendanceList.get(i).getEmployeeBranch());
						q.setParameter(2, Integer.parseInt(clientID));
						List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
						branchid=branchlist.get(0).getBranch_ID();
						q=entityManager.createQuery("from Employee where employeeName=? and client_ID=? and status='ACTIVE'");
						q.setParameter(1, attendanceList.get(i).getEmployeeName());
						q.setParameter(2, Integer.parseInt(clientID));
						List<Employee> list=(ArrayList<Employee>)q.getResultList();
						employeeid=list.get(0).getEmployee_ID();
						attendance.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
						attendance.setClient(entityManager.find(Client.class, Integer.parseInt(clientID)));
						attendance.setBranch(entityManager.find(Branch.class, branchid));
						attendance.setEmployee(entityManager.find(Employee.class, employeeid));
						attendance.setAttend(attendanceList.get(i).getAttend());
						attendance.setOverTime(attendanceList.get(i).getOvertime());
						attendance.setDate(employeeDataBean.getAttendanceDate());
						attendance.setLate(attendanceList.get(i).getLate());
						//attendance.setForfeit(attendanceList.get(i).getForfeit());
						attendance.setReward(attendanceList.get(i).getReward());
						attendance.setStatus("ACTIVE");
						entityManager.persist(attendance);
						entityManager.flush();
						entityManager.clear();
						status="Success";
					}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}


	@Override
	public List<Attendance> checkAttendance(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		Query q=null;
		int branchid=0;
		List<Attendance> attendancelist=new ArrayList<Attendance>();
		if(personID!=null && clientID!=null){
			try{
				if(!employeeDataBean.getBranchName().equalsIgnoreCase("ALL")){
					q=entityManager.createQuery("from Branch where branchName=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, employeeDataBean.getBranchName());
					q.setParameter(2, Integer.parseInt(clientID));
					List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
					if(branchlist.size()>0){
						branchid=branchlist.get(0).getBranch_ID();
						q=entityManager.createQuery("from Attendance where branch_ID=? and date=?");
						q.setParameter(1, branchid);
						q.setParameter(2, employeeDataBean.getAttendanceDate());
						attendancelist=(ArrayList<Attendance>)q.getResultList();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return attendancelist;
	}


	@Override
	public List<EmployeeDataBean> attendanceView(String personID,
			String clientID, EmployeeDataBean employeeDataBean) {
		Query q=null;
		int branchid=0;
		List<EmployeeDataBean> employeeList=null;
		if(personID!=null && clientID!=null){
			try{
				employeeList=new ArrayList<EmployeeDataBean>();
				if("ALL".equalsIgnoreCase(employeeDataBean.getBranchName())){
								q=entityManager.createQuery("from Attendance where client_ID=? and status='ACTIVE'");
								q.setParameter(1, Integer.parseInt(clientID));
								List<Attendance> list=(ArrayList<Attendance>)q.getResultList();
								if(list.size()>0){
									for(int i=0;i<list.size();i++){
									employeeDataBean=new EmployeeDataBean();
									employeeDataBean.setHiddenValue(list.get(i).getAttendance_ID());
									employeeDataBean.setEmployeeBranch(list.get(i).getBranch().getBranchName());
									employeeDataBean.setEmployeeName(list.get(i).getEmployee().getEmployeeName());
									employeeDataBean.setAttend(list.get(i).getAttend());
									employeeDataBean.setLate(list.get(i).getLate());
									employeeDataBean.setOvertime(list.get(i).getOverTime());
									//employeeDataBean.setForfeit(list.get(i).getForfeit());
									employeeDataBean.setReward(list.get(i).getReward());
									employeeList.add(employeeDataBean);
									}
								}
					}
				else{
					q=entityManager.createQuery("from Branch where branchName=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, employeeDataBean.getBranchName());
					q.setParameter(2, Integer.parseInt(clientID));
					List<Branch> branchlist=(ArrayList<Branch>)q.getResultList();
					if(branchlist.size()>0){
						branchid=branchlist.get(0).getBranch_ID();
						q=entityManager.createQuery("from Attendance where branch_ID=? and date=? and  client_ID=? and status='ACTIVE'");
						q.setParameter(1, branchid);
						q.setParameter(2, employeeDataBean.getAttendanceDate());
						q.setParameter(3, Integer.parseInt(clientID));
						List<Attendance> list=(ArrayList<Attendance>)q.getResultList();
						if(list.size()>0){
							for(int j=0;j<list.size();j++){
							employeeDataBean=new EmployeeDataBean();
							employeeDataBean.setHiddenValue(list.get(j).getAttendance_ID());
							employeeDataBean.setEmployeeBranch(list.get(j).getBranch().getBranchName());
							employeeDataBean.setEmployeeName(list.get(j).getEmployee().getEmployeeName());
							employeeDataBean.setAttend(list.get(j).getAttend());
							employeeDataBean.setLate(list.get(j).getLate());
							employeeDataBean.setOvertime(list.get(j).getOverTime());
							//employeeDataBean.setForfeit(list.get(j).getForfeit());
							employeeDataBean.setReward(list.get(j).getReward());
							employeeList.add(employeeDataBean);
							}
						}
						}
					}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return employeeList;
	}

	@Transactional(value="transactionManager")
	@Override
	public String attendanceUpdate(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		String status="Fail";
		if(personID!=null && clientID!=null){
			try{
				Attendance attendance=entityManager.find(Attendance.class, employeeDataBean.getHiddenValue());
				attendance.setAttend(employeeDataBean.getAttend());
				attendance.setOverTime(employeeDataBean.getOvertime());
				attendance.setLate(employeeDataBean.getLate());
				//attendance.setForfeit(employeeDataBean.getForfeit());
				attendance.setReward(employeeDataBean.getReward());
				entityManager.merge(attendance);
				status="Success";
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return status;
	}
	
	//prema 07/01/2017 supplier module begin
	
		@Override
		@Transactional(value="transactionManager")
		public String insertsupplier(String personID,String clientID, SupplyDataBean supplyDataBean) {
		Query q=null;String status="Fail";
		try{
			q=entityManager.createQuery("from Supplier where hpNumber1=? and division=? and person_ID=? and client_ID=? and status='ACTIVE'");
			q.setParameter(1, supplyDataBean.getHpNumber1());
			q.setParameter(2, supplyDataBean.getDivision());
			q.setParameter(3, personID);
			q.setParameter(4, clientID);
			List<Supplier> supplierlist=(List<Supplier>)q.getResultList();
			if(supplierlist.size()>0){
				status="Exist";
			}else{
				Supplier supplier=new Supplier();
				int personid=Integer.parseInt(personID);
				int clientid=Integer.parseInt(personID);
				supplier.setDivision(supplyDataBean.getDivision());
				supplier.setSupplierName(supplyDataBean.getSupplierName());
				supplier.setContactName(supplyDataBean.getContactName());
				supplier.setHpNumber1(supplyDataBean.getHpNumber1());
				supplier.setHpNumber2(supplyDataBean.getHpNumber2());
				supplier.setOfficeNumber1(supplyDataBean.getOfficeNumber1());
				supplier.setOfficeNumber2(supplyDataBean.getOfficeNumber2());
				supplier.setPerson(entityManager.find(Person.class, personid));
				supplier.setClient(entityManager.find(Client.class, clientid));
				supplier.setStatus("ACTIVE");
				entityManager.persist(supplier);
				entityManager.flush();
				entityManager.clear();
				status="Success";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
		}

		@Override
		public List<SupplyDataBean> getsupplierlist(String personID) {
			List<SupplyDataBean> supplierList=new ArrayList<SupplyDataBean>();Query q=null;
			try{
				q=entityManager.createQuery("from Supplier where person_ID=? and status='ACTIVE' ORDER BY supplierId DESC");
				q.setParameter(1, personID);
				List<Supplier> supplierlist=(List<Supplier>)q.getResultList();
				if(supplierlist.size()>0){
					for (int j = 0; j < supplierlist.size(); j++) {
						SupplyDataBean supply=new SupplyDataBean();
						supply.setFabricId(supplierlist.get(j).getSupplierId());
						supply.setDivision(supplierlist.get(j).getDivision());
						supply.setSupplierName(supplierlist.get(j).getSupplierName());
						supply.setContactName(supplierlist.get(j).getContactName());
						supply.setHpNumber1(supplierlist.get(j).getHpNumber1());
						supply.setOfficeNumber1(supplierlist.get(j).getOfficeNumber1());
						supplierList.add(supply);	
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return supplierList;
		}

		@Override
		public String editsupplier(String personID, SupplyDataBean supplyDataBean) {
			Query q=null;
			try{
				q=entityManager.createQuery("from Supplier where supplierId=? and person_ID=?");
				q.setParameter(1, supplyDataBean.getFabricId());
				q.setParameter(2, personID);
				List<Supplier> supplierlist=(List<Supplier>)q.getResultList();
				if(supplierlist.size()>0){
					supplyDataBean.setSupplierName(supplierlist.get(0).getSupplierName());
					supplyDataBean.setDivision(supplierlist.get(0).getDivision());
					supplyDataBean.setContactName(supplierlist.get(0).getContactName());
					supplyDataBean.setHpNumber1(supplierlist.get(0).getHpNumber1());
					supplyDataBean.setOfficeNumber1(supplierlist.get(0).getOfficeNumber1());
					supplyDataBean.setHpNumber2(supplierlist.get(0).getHpNumber2());
					supplyDataBean.setOfficeNumber2(supplierlist.get(0).getOfficeNumber2());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		@Transactional(value="transactionManager")
		public String updatesupplier(String personID, SupplyDataBean supplyDataBean) {
			Query q=null;String status="Fail";int supplier_id=0;
			try{
				System.out.println("no "+supplyDataBean.getHpNumber1()+" div "+supplyDataBean.getDivision());
				q=entityManager.createQuery("from Supplier where hpNumber1=? and division=? and person_ID=? and status='ACTIVE'");
				q.setParameter(1, supplyDataBean.getHpNumber1());
				q.setParameter(2, supplyDataBean.getDivision());
				q.setParameter(3, personID);
				List<Supplier> supplierlist=(List<Supplier>)q.getResultList();
				if(supplierlist.size()>0){
					if(supplierlist.get(0).getSupplierId() == supplyDataBean.getFabricId()){
						supplier_id=supplierlist.get(0).getSupplierId();
						Supplier supp=entityManager.find(Supplier.class, supplyDataBean.getFabricId());
						supp.setDivision(supplyDataBean.getDivision());
						supp.setSupplierName(supplyDataBean.getSupplierName());
						supp.setContactName(supplyDataBean.getContactName());
						supp.setHpNumber1(supplyDataBean.getHpNumber1());
						supp.setHpNumber2(supplyDataBean.getHpNumber2());
						supp.setOfficeNumber1(supplyDataBean.getOfficeNumber1());
						supp.setOfficeNumber2(supplyDataBean.getOfficeNumber2());
						entityManager.merge(supp);
						status="Success";
					}else{
						status="Exist";
					}
				}else{
					Supplier supp=entityManager.find(Supplier.class, supplyDataBean.getFabricId());
					supp.setDivision(supplyDataBean.getDivision());
					supp.setSupplierName(supplyDataBean.getSupplierName());
					supp.setContactName(supplyDataBean.getContactName());
					supp.setHpNumber1(supplyDataBean.getHpNumber1());
					supp.setHpNumber2(supplyDataBean.getHpNumber2());
					supp.setOfficeNumber1(supplyDataBean.getOfficeNumber1());
					supp.setOfficeNumber2(supplyDataBean.getOfficeNumber2());
					entityManager.merge(supp);
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
		}
		
		@Override
		@Transactional(value="transactionManager")
		public String deletesupplier(String personID, SupplyDataBean supplyDataBean) {
			Query q=null;
			try{
				q=entityManager.createQuery("from Supplier where supplierId=? and person_ID=?");
				q.setParameter(1, supplyDataBean.getFabricId());	
				q.setParameter(2, personID);
				List<Supplier> supplierlist=(List<Supplier>)q.getResultList();
				if(supplierlist.size()>0){
					Supplier supplier=entityManager.find(Supplier.class, supplierlist.get(0).getSupplierId());
					supplier.setStatus("DE-ACTIVE");
					entityManager.merge(supplier);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		//prema supplier module end
		//prema fabric module begin
		@Override
		public String purchaseFabricInvoice(String personID,SupplyDataBean supplyDataBean) {
			Query q=null;String invoiceno="";String fablistsize="";
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			String purdate = sf.format(supplyDataBean.getFabricPurchaseDate());
			try{
				q=entityManager.createQuery("from Fabric where purchaseDate=? and person_ID=?");
				q.setParameter(1, supplyDataBean.getFabricPurchaseDate());
				q.setParameter(2, personID);
				List<Fabric> fabricList=(List<Fabric>)q.getResultList();
				fablistsize=String.valueOf(fabricList.size()+1);
				if(fabricList.size()>0){
					if(fabricList.size()==0){
						invoiceno="Fa"+purdate+"00"+1;
					}
					if(fablistsize.length()==1){
						invoiceno="Fa"+purdate+"00"+fablistsize;
					}else if(fablistsize.length()==2){
						invoiceno="Fa"+purdate+"0"+fablistsize;
					}else{
						invoiceno="Fa"+purdate+fablistsize;
					}
				}else{
					if(fabricList.size()==0){
						invoiceno="Fa"+purdate+"00"+1;
					}
					if(fablistsize.length()==1){
						invoiceno="Fa"+purdate+"00"+fablistsize;
					}else if(fablistsize.length()==2){
						invoiceno="Fa"+purdate+"0"+fablistsize;
					}else{
						invoiceno="Fa"+purdate+fablistsize;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return invoiceno;
		}
		@Override
		public List<String> getsuppliernamelist(String personID,String str) {
			Query q=null;List<String> suppList=new ArrayList<String>();
			try{
				System.out.println("str "+str);
				q=entityManager.createQuery("select supplierName from Supplier where person_ID=? and division=? and status='ACTIVE'");
				q.setParameter(1, personID);
				q.setParameter(2, str);
				List<String> supplist=(List<String>)q.getResultList();
				System.out.println("supplist "+supplist.size());
				if(supplist.size()>0){
					suppList.addAll(supplist);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return suppList;
		}
		//prema fabric module end
		//prema vendor module begin
		@Override
		@Transactional(value="transactionManager")
		public String insertvendor(String personID, String clientID,ProductionDataBean productionDataBean) {
			Query q=null;String status="Fail";
			try{
				q=entityManager.createQuery("from Vendor where vendor_Hpnumber1=? and person_ID=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, productionDataBean.getVendorHpnumber1());
				q.setParameter(2, personID);
				q.setParameter(3, clientID);
				List<Vendor> vendorlist=(List<Vendor>)q.getResultList();
				if(vendorlist.size()>0){
					status="Exist";
				}else{
					Vendor vendor=new Vendor();
					int personid=Integer.parseInt(personID);
					int clientid=Integer.parseInt(personID);
					vendor.setDivisionVendor(productionDataBean.getDivisionVendor());
					vendor.setVendorName(productionDataBean.getVendorName());
					vendor.setVendorAddress(productionDataBean.getVendorAddress());
					vendor.setVendor_Hpnumber1(productionDataBean.getVendorHpnumber1());
					vendor.setVendor_Hpnumber2(productionDataBean.getVendorHpnumber2());
					vendor.setPerson(entityManager.find(Person.class, personid));
					vendor.setClient(entityManager.find(Client.class, clientid));
					vendor.setStatus("ACTIVE");
					entityManager.persist(vendor);
					entityManager.flush();
					entityManager.clear();
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
		}

		@Override
		public List<ProductionDataBean> getvendorlist(String personID) {
			List<ProductionDataBean> vendorList=new ArrayList<ProductionDataBean>();Query q=null;
			try{
				q=entityManager.createQuery("from Vendor where person_ID=? and status='ACTIVE' ORDER BY vendorId DESC");
				q.setParameter(1, personID);
				List<Vendor> vendorlist=(List<Vendor>)q.getResultList();
				if(vendorlist.size()>0){
					for (int j = 0; j < vendorlist.size(); j++) {
						ProductionDataBean prod=new ProductionDataBean();
						prod.setDivisionVendor(vendorlist.get(j).getDivisionVendor());
						prod.setVendorName(vendorlist.get(j).getVendorName());
						prod.setVendorAddress(vendorlist.get(j).getVendorAddress());
						prod.setVendorHpnumber1(vendorlist.get(j).getVendor_Hpnumber1());
						prod.setVendorHpnumber2(vendorlist.get(j).getVendor_Hpnumber2());
						vendorList.add(prod);	
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return vendorList;
		}

		@Override
		public String editVendor(String personID,ProductionDataBean productionDataBean) {
			Query q=null;
			try{
				q=entityManager.createQuery("from Vendor where vendor_Hpnumber1=? and person_ID=?");
				q.setParameter(1, productionDataBean.getVendorHpnumber1());
				q.setParameter(2, personID);
				List<Vendor> vendorlist=(List<Vendor>)q.getResultList();
				if(vendorlist.size()>0){
					productionDataBean.setDivisionVendor(vendorlist.get(0).getDivisionVendor());
					productionDataBean.setVendorName(vendorlist.get(0).getVendorName());
					productionDataBean.setVendorAddress(vendorlist.get(0).getVendorAddress());
					productionDataBean.setVendorHpnumber2(vendorlist.get(0).getVendor_Hpnumber2());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		@Transactional(value="transactionManager")
		public String updatevendor(String personID,ProductionDataBean productionDataBean) {
			Query q=null;String status="Fail";
			try{
				q=entityManager.createQuery("from Vendor where vendor_Hpnumber1=? and person_ID=?");
				q.setParameter(1, productionDataBean.getVendorHpnumber1());
				q.setParameter(2, personID);
				List<Vendor> vendorlist=(List<Vendor>)q.getResultList();
				if(vendorlist.size()>0){
					int vendor_id=vendorlist.get(0).getVendorId();
					Vendor ven=entityManager.find(Vendor.class, vendor_id);
					ven.setDivisionVendor(productionDataBean.getDivisionVendor());
					ven.setVendorName(productionDataBean.getVendorName());
					ven.setVendorAddress(productionDataBean.getVendorAddress());
					ven.setVendor_Hpnumber2(productionDataBean.getVendorHpnumber2());
					entityManager.merge(ven);
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
		}

		@Override
		@Transactional(value="transactionManager")
		public String deletevendor(String personID,ProductionDataBean productionDataBean) {
			Query q=null;
			try{
				q=entityManager.createQuery("from Vendor where vendor_Hpnumber1=? and person_ID=?");
				q.setParameter(1,  productionDataBean.getVendorHpnumber1());
				q.setParameter(2, personID);
				List<Vendor> vendorlist=(List<Vendor>)q.getResultList();
				if(vendorlist.size()>0){
					Vendor ven=entityManager.find(Vendor.class, vendorlist.get(0).getVendorId());
					ven.setStatus("DE-ACTIVE");
					entityManager.merge(ven);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		//prema vendor module end
		//prema category module begin
		@Override
		@Transactional(value="transactionManager")
		public String insertcategory(String personID, String clientID,ProductionDataBean productionDataBean) {
			Query q=null;String status="Fail";
			try{
				q=entityManager.createQuery("from Category where categoryName=? and person_ID=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, productionDataBean.getCategoryName());
				q.setParameter(2, personID);
				q.setParameter(3, clientID);
				List<Category> categorylist=(List<Category>)q.getResultList();
				if(categorylist.size()>0){
					status="Exist";
				}else{
					Category category=new Category();
					int personid=Integer.parseInt(personID);
					int clientid=Integer.parseInt(personID);
					category.setCategoryName(productionDataBean.getCategoryName());
					category.setCategoryFabprice(productionDataBean.getCategoryFabprice());
					category.setPerson(entityManager.find(Person.class, personid));
					category.setClient(entityManager.find(Client.class, clientid));
					category.setStatus("ACTIVE");
					entityManager.persist(category);
					entityManager.flush();
					entityManager.clear();
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
		}

		@Override
		public List<ProductionDataBean> getcategorylist(String personID) {
			List<ProductionDataBean> categoryList=new ArrayList<ProductionDataBean>();Query q=null;
			try{
				q=entityManager.createQuery("from Category where person_ID=? and status='ACTIVE' ORDER BY categoryId DESC");
				q.setParameter(1, personID);
				List<Category> categorylist=(List<Category>)q.getResultList();
				if(categorylist.size()>0){
					for (int j = 0; j < categorylist.size(); j++) {
						ProductionDataBean prod=new ProductionDataBean();
						prod.setCategoryName(categorylist.get(j).getCategoryName());
						prod.setCategoryFabprice(categorylist.get(j).getCategoryFabprice());
						prod.setCategoryId(categorylist.get(j).getCategoryId());
						categoryList.add(prod);	
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return categoryList;
		}

		@Override
		@Transactional(value="transactionManager")
		public String editcategory(String personID,ProductionDataBean productionDataBean) {
			Query q=null;String status="Fail";
			try{
			q = entityManager.createQuery("from Category where categoryName=? and person_ID=? and status='Active'");
			q.setParameter(1, productionDataBean.getCategoryName());
			q.setParameter(2, personID);
			List<Category> categorylist = (List<Category>) q.getResultList();
				if (categorylist.size() > 0) {
					if(String.valueOf(categorylist.get(0).getCategoryId()).equals(String.valueOf(productionDataBean.getCategoryId()))){
						Category obj = entityManager.find(Category.class, productionDataBean.getCategoryId());
						obj.setCategoryName(productionDataBean.getCategoryName());
						obj.setCategoryFabprice(productionDataBean.getCategoryFabprice());
						obj.setStatus("ACTIVE");
						entityManager.merge(obj);
						status = "Success";
					}else{
						status="Fail";
					}
				}else{
					Category obj = entityManager.find(Category.class, productionDataBean.getCategoryId());
					obj.setCategoryName(productionDataBean.getCategoryName());
					obj.setCategoryFabprice(productionDataBean.getCategoryFabprice());
					obj.setStatus("ACTIVE");
					entityManager.merge(obj);
					status = "Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
		}

		@Override
		@Transactional(value="transactionManager")
		public String deletecategory(String personID,ProductionDataBean productionDataBean) {
			Query q=null;
			try{
				q=entityManager.createQuery("from Category where categoryName=? and person_ID=?");
				q.setParameter(1, productionDataBean.getCategoryName());
				q.setParameter(2, personID);
				List<Category> categorylist=(List<Category>)q.getResultList();
				if(categorylist.size()>0){
					Category cate=entityManager.find(Category.class, categorylist.get(0).getCategoryId());
					cate.setStatus("DE-ACTIVE");
					entityManager.merge(cate);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		//prema category module end
		//prema model module begin
		@Override
		public List<String> getcategorynamelist(String personID) {
			Query q=null;List<String> categorynamelist=null;
			try{
				q=entityManager.createQuery("select categoryName from Category where person_ID=? and status='ACTIVE'");
				q.setParameter(1, personID);
				categorynamelist=(List<String>)q.getResultList();
			}catch(Exception e){
				e.printStackTrace();
			}
			return categorynamelist;
		}

		@Override
		@Transactional(value="transactionManager")
		public String insertmodel(String personID, String clientID,ProductionDataBean productionDataBean) {
			Query q=null;String status="Fail";int category_id=0;
			try{
				q=entityManager.createQuery("from Category where categoryName=? and person_ID=?");
				q.setParameter(1, productionDataBean.getCategoryName());
				q.setParameter(2, personID);
				List<Category> categorylist=(List<Category>)q.getResultList();
				if(categorylist.size()>0){
					category_id=categorylist.get(0).getCategoryId();
				}
				q=null;
				q=entityManager.createQuery("from Model where model=? and person_ID=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, productionDataBean.getModelName());
				q.setParameter(2, personID);
				q.setParameter(3, clientID);
				List<Model> modellist=(List<Model>)q.getResultList();
				if(modellist.size()>0){
					status="Exist";
				}else{
					String capital=new BigDecimal(categorylist.get(0).getCategoryFabprice()).multiply(new BigDecimal(productionDataBean.getModelPole())).toString();
					String capital1=new BigDecimal(productionDataBean.getModelCutter()).add(new BigDecimal(productionDataBean.getModelPrinter()).
							add(new BigDecimal(productionDataBean.getModelCmt()).add(new BigDecimal(productionDataBean.getModelEtc())))).toString();
					Model model=new Model();
					int personid=Integer.parseInt(personID);
					int clientid=Integer.parseInt(personID);
					model.setCategory(entityManager.find(Category.class, category_id));
					model.setFabPrice(categorylist.get(0).getCategoryFabprice());
					model.setModel(productionDataBean.getModelName());
					model.setSeri(productionDataBean.getModelSeri());
					model.setPole(productionDataBean.getModelPole());
					model.setCutter(productionDataBean.getModelCutter());
					model.setPrinter(productionDataBean.getModelPrinter());
					model.setCmt(productionDataBean.getModelCmt());
					model.setEtc(productionDataBean.getModelEtc());
					model.setSellPrice(productionDataBean.getModelSellprice());
					model.setPerson(entityManager.find(Person.class, personid));
					model.setClient(entityManager.find(Client.class, clientid));
					model.setCapital(new BigDecimal(capital).add(new BigDecimal(capital1)).toString());
					model.setStatus("ACTIVE");
					entityManager.persist(model);
					entityManager.flush();
					entityManager.clear();
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
		}

		@Override
		public List<ProductionDataBean> getmodelList(String personID) {
			List<ProductionDataBean> modelList=new ArrayList<ProductionDataBean>();Query q=null;
			try{
				q=entityManager.createQuery("from Model where person_ID=? and status='ACTIVE' ORDER BY modelId DESC");
				q.setParameter(1, personID);
				List<Model> modellist=(List<Model>)q.getResultList();
				BigDecimal profit=BigDecimal.valueOf(0);
				BigDecimal profitpercent=BigDecimal.valueOf(0);
				BigDecimal percent=BigDecimal.valueOf(0);
				if(modellist.size()>0){
					for (int j = 0; j < modellist.size(); j++) {
						q=null;
						q=entityManager.createQuery("from Category where category_id=?");
						q.setParameter(1, modellist.get(j).getCategory().getCategoryId());
						List<Category> categorylist=(List<Category>)q.getResultList();
						ProductionDataBean prod=new ProductionDataBean();
						prod.setCategoryName(categorylist.get(0).getCategoryName());
						prod.setCategoryFabprice(modellist.get(j).getFabPrice());
						prod.setModelName(modellist.get(j).getModel());
						prod.setModelPole(modellist.get(j).getPole());
						prod.setModelCutter(modellist.get(j).getCutter());
						prod.setModelPrinter(modellist.get(j).getPrinter());
						prod.setModelCmt(modellist.get(j).getCmt());
						prod.setModelEtc(modellist.get(j).getEtc());
						prod.setModelCapital(modellist.get(j).getCapital());
						prod.setModelSellprice(modellist.get(j).getSellPrice());
						profit=new BigDecimal(modellist.get(j).getSellPrice()).subtract(new BigDecimal(modellist.get(j).getCapital()));
						profitpercent=profit.divide(new BigDecimal(modellist.get(j).getSellPrice()));
						percent=profitpercent.multiply(new BigDecimal(100));
						prod.setProfit(profit.toString());
						prod.setProfit_percentage(percent.toString());
						System.out.println("----------"+(profit.divide(new BigDecimal(modellist.get(j).getSellPrice())).toString()));
						//prod.setModelSeri(modellist.get(j).getSeri());
						modelList.add(prod);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return modelList;
		}

		@Override
		public String editModel(String personID,ProductionDataBean productionDataBean) {
			Query q=null;
			try{
				q=entityManager.createQuery("from Model where model=? and person_ID=?");
				q.setParameter(1, productionDataBean.getModelName());
				q.setParameter(2, personID);
				List<Model> modellist=(List<Model>)q.getResultList();
				if(modellist.size()>0){
					q=null;
					q=entityManager.createQuery("from Category where category_id=?");
					q.setParameter(1, modellist.get(0).getCategory().getCategoryId());
					List<Category> categorylist=(List<Category>)q.getResultList();
					productionDataBean.setCategoryName(categorylist.get(0).getCategoryName());
					productionDataBean.setCategoryFabprice(modellist.get(0).getFabPrice());
					productionDataBean.setModelPole(modellist.get(0).getPole());
					productionDataBean.setModelCutter(modellist.get(0).getCutter());
					productionDataBean.setModelPrinter(modellist.get(0).getPrinter());
					productionDataBean.setModelCmt(modellist.get(0).getCmt());
					productionDataBean.setModelEtc(modellist.get(0).getEtc());
					productionDataBean.setModelSellprice(modellist.get(0).getSellPrice());
					//productionDataBean.setModelSeri(modellist.get(0).getSeri());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		@Transactional(value="transactionManager")
		public String updatemodel(String personID,ProductionDataBean productionDataBean) {
			Query q=null;String status="Fail";
				try{
					q=entityManager.createQuery("from Model where model=? and person_ID=?");
					q.setParameter(1, productionDataBean.getModelName());
					q.setParameter(2, personID);
					List<Model> modellist=(List<Model>)q.getResultList();
					if(modellist.size()>0){
						int model_id=modellist.get(0).getModelId();
						q=null;
						q=entityManager.createQuery("from Category where categoryName=?");
						q.setParameter(1, productionDataBean.getCategoryName());
						List<Category> categorylist=(List<Category>)q.getResultList();
						String capital=new BigDecimal(categorylist.get(0).getCategoryFabprice()).multiply(new BigDecimal(productionDataBean.getModelPole())).toString();
						String capital1=new BigDecimal(productionDataBean.getModelCutter()).add(new BigDecimal(productionDataBean.getModelPrinter()).
								add(new BigDecimal(productionDataBean.getModelCmt()).add(new BigDecimal(productionDataBean.getModelEtc())))).toString();
						Model model=entityManager.find(Model.class, model_id);
						model.setCategory(entityManager.find(Category.class,categorylist.get(0).getCategoryId()));
						model.setFabPrice(categorylist.get(0).getCategoryFabprice());
						model.setPole(productionDataBean.getModelPole());
						model.setCutter(productionDataBean.getModelCutter());
						model.setPrinter(productionDataBean.getModelPrinter());
						model.setCmt(productionDataBean.getModelCmt());
						model.setEtc(productionDataBean.getModelEtc());
						model.setSellPrice(productionDataBean.getModelSellprice());
						model.setCapital(new BigDecimal(capital).add(new BigDecimal(capital1)).toString());
						//model.setSeri(productionDataBean.getModelSeri());
						model.setStatus("ACTIVE");
						entityManager.merge(model);
						status="Success";
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				return status;
		}

		@Override
		@Transactional(value="transactionManager")
		public String deletemodel(String personID,ProductionDataBean productionDataBean) {
				Query q=null;
				try{
					q=entityManager.createQuery("from Model where model=? and person_ID=?");
					q.setParameter(1, productionDataBean.getModelName());
					q.setParameter(2, personID);
					List<Model> modellist=(List<Model>)q.getResultList();
					if(modellist.size()>0){
						Model model=entityManager.find(Model.class, modellist.get(0).getModelId());
						model.setStatus("DE-ACTIVE");
						entityManager.merge(model);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				return null;
		}


		@Override
		public List<ProductionDataBean> getparticularmodelList(String personID,String category) {
			List<ProductionDataBean> modelList=new ArrayList<ProductionDataBean>();Query q=null;
			try{
				q=entityManager.createQuery("from Category where categoryName=? and person_ID=? and status='ACTIVE'");
				q.setParameter(1, category);
				q.setParameter(2, personID);
				List<Category> catelist=(List<Category>)q.getResultList();
				q=null;
				q=entityManager.createQuery("from Model where category_id=? and person_ID=? and status='ACTIVE' ORDER BY modelId DESC");			
				q.setParameter(1, catelist.get(0).getCategoryId());
				q.setParameter(2, personID);
				List<Model> modellist=(List<Model>)q.getResultList();
				if(modellist.size()>0){
					for (int j = 0; j < modellist.size(); j++) {
						ProductionDataBean prod=new ProductionDataBean();
						prod.setCategoryName(catelist.get(0).getCategoryName());
						prod.setCategoryFabprice(modellist.get(j).getFabPrice());
						prod.setModelName(modellist.get(j).getModel());
						prod.setModelPole(modellist.get(j).getPole());
						prod.setModelCutter(modellist.get(j).getCutter());
						prod.setModelPrinter(modellist.get(j).getPrinter());
						prod.setModelCmt(modellist.get(j).getCmt());
						prod.setModelEtc(modellist.get(j).getEtc());
						prod.setModelCapital(modellist.get(j).getCapital());
						prod.setModelSellprice(modellist.get(j).getSellPrice());
						//prod.setModelSeri(modellist.get(j).getSeri());
						modelList.add(prod);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return modelList;
		}
		//prema model module end

		@Override
		@Transactional(value="transactionManager")
		public String savepurchasefabric(String personID,SupplyDataBean supplyDataBean) {
			Query q=null;int fab_id=0;String status="Fail";
			try{
				int personid=Integer.parseInt(personID);
				Fabric fab=new Fabric();
				fab.setPurchaseDate(supplyDataBean.getFabricPurchaseDate());
				fab.setInvoiceNumber(supplyDataBean.getFabricInvoiceNumber());
				fab.setSupplierName(supplyDataBean.getSupplierName());
				fab.setFabricName(supplyDataBean.getFabricName());
				fab.setTotalWeight(supplyDataBean.getWeightTotal());
				fab.setTotalQuantity(supplyDataBean.getQuantityTotal()); 
				fab.setTotalAmount(supplyDataBean.getTotalAmount().replace(",", ""));
				fab.setPerson(entityManager.find(Person.class, personid));
				fab.setStatus("INSERTED");
				fab.setInvoiceStatus("insert");
				fab.setPaymentStatus("UNPAID");
				fab.setCreatedDate(date);
				entityManager.persist(fab);
				entityManager.flush();
				entityManager.clear();
				q=entityManager.createQuery("from Fabric");
				List<Fabric> fabricList=(List<Fabric>)q.getResultList();
				if(fabricList.size()>0){
					fab_id=fabricList.get(fabricList.size()-1).getFabricId();
						for (int i = 0; i < supplyDataBean.getFabricList().size(); i++) {
							try{
								if(!supplyDataBean.getFabricList().get(i).getFabricColour().equalsIgnoreCase(null)){
									FabricDetail fabdetail=new FabricDetail();
									fabdetail.setFabric(entityManager.find(Fabric.class, fab_id));
									fabdetail.setFabricColour(supplyDataBean.getFabricList().get(i).getFabricColour());
									fabdetail.setFabricPrice(supplyDataBean.getFabricList().get(i).getFabricPrice());
									fabdetail.setFabricWeight(supplyDataBean.getFabricList().get(i).getFabricWeight());
									fabdetail.setFabricQuantity(supplyDataBean.getFabricList().get(i).getFabricQuantity());
									fabdetail.setFabricTotal(supplyDataBean.getFabricList().get(i).getFabricTotal());
									fabdetail.setStatus("INSERTED");
									entityManager.persist(fabdetail);
									entityManager.flush();
									entityManager.clear();
									status="Success";
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
		}

		@Override
		public List<SupplyDataBean> getfabricpurchaseList(String personID) {
			Query q=null;List<SupplyDataBean> purchaseFabricList=new ArrayList<SupplyDataBean>();
			Calendar cal=Calendar.getInstance();
			try{
				cal.add(Calendar.DATE, -30);
			    Date todate1 = cal.getTime();    
			    q=entityManager.createQuery("from Fabric where person_ID=? and purchaseDate between ? and ? and status='INSERTED' ORDER BY fabricId DESC");
				q.setParameter(1, personID);
				q.setParameter(2, todate1);
				q.setParameter(3, date);
				List<Fabric> fabriclist=(List<Fabric>)q.getResultList();
				if(fabriclist.size()>0){
					for (int i = 0; i < fabriclist.size(); i++) {
						SupplyDataBean supp=new SupplyDataBean();
						supp.setSerialNo(String.valueOf(i+1));
						supp.setFabricId(fabriclist.get(i).getFabricId());
						supp.setFabricPurchaseDate(fabriclist.get(i).getPurchaseDate());
						supp.setFabricInvoiceNumber(fabriclist.get(i).getInvoiceNumber());
						supp.setSupplierName(fabriclist.get(i).getSupplierName());
						supp.setFabricName(fabriclist.get(i).getFabricName());
						supp.setWeightTotal(fabriclist.get(i).getTotalWeight());
						supp.setQuantityTotal(fabriclist.get(i).getTotalQuantity());
						supp.setTotalAmount(fabriclist.get(i).getTotalAmount());
						supp.setPaymentStatus(fabriclist.get(i).getPaymentStatus());
						supp.setInvoiceStatus(fabriclist.get(i).getInvoiceStatus());
						purchaseFabricList.add(supp);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return purchaseFabricList;
		}

		@Override
		public String viewpurchasefabric(String personID,SupplyDataBean supplyDataBean) {
			Query q=null;List<SupplyDataBean> fabriclist=new ArrayList<SupplyDataBean>();
			try{
				q=entityManager.createQuery("from Fabric where fabric_id=? and person_ID=?");
				q.setParameter(1, supplyDataBean.getFabricId());
				q.setParameter(2, personID);
				List<Fabric> fabricList=(List<Fabric>)q.getResultList();
				if(fabricList.size()>0){
					supplyDataBean.setFabricPurchaseDate(fabricList.get(0).getPurchaseDate());
					supplyDataBean.setFabricInvoiceNumber(fabricList.get(0).getInvoiceNumber());
					supplyDataBean.setSupplierName(fabricList.get(0).getSupplierName());
					supplyDataBean.setFabricName(fabricList.get(0).getFabricName());
					supplyDataBean.setWeightTotal(fabricList.get(0).getTotalWeight());
					supplyDataBean.setQuantityTotal(fabricList.get(0).getTotalQuantity());
					supplyDataBean.setTotalAmount(fabricList.get(0).getTotalAmount());
					q=null;
					q=entityManager.createQuery("from FabricDetail where fabric_id=?");
					q.setParameter(1, fabricList.get(0).getFabricId());
					List<FabricDetail> fabricdetailList=(List<FabricDetail>)q.getResultList();
					if(fabricdetailList.size()>0){
						for (int i = 0; i < fabricdetailList.size(); i++) {
							SupplyDataBean sup=new SupplyDataBean();
							sup.setSerialNo(String.valueOf(i+1));
							sup.setFabricColour(fabricdetailList.get(i).getFabricColour());
							sup.setFabricPrice(fabricdetailList.get(i).getFabricPrice());
							sup.setFabricWeight(fabricdetailList.get(i).getFabricWeight());
							sup.setFabricQuantity(fabricdetailList.get(i).getFabricQuantity());
							sup.setFabricTotal(fabricdetailList.get(i).getFabricTotal());
							fabriclist.add(sup);
							supplyDataBean.setFabricList(fabriclist);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return "";
		}

		@Override
		@Transactional(value="transactionManager")
		public String updatepurchasefabric(String personID,SupplyDataBean supplyDataBean) {
			String status="Fail";Query q=null;
			try{
				int personid=Integer.parseInt(personID);
				Fabric fab=entityManager.find(Fabric.class, supplyDataBean.getFabricId());
				fab.setPurchaseDate(supplyDataBean.getFabricPurchaseDate());
				fab.setInvoiceNumber(supplyDataBean.getFabricInvoiceNumber());
				fab.setSupplierName(supplyDataBean.getSupplierName());
				fab.setFabricName(supplyDataBean.getFabricName());
				fab.setTotalWeight(supplyDataBean.getWeightTotal());
				fab.setTotalQuantity(supplyDataBean.getQuantityTotal());
				fab.setTotalAmount(supplyDataBean.getTotalAmount().replace(",",""));
				fab.setPerson(entityManager.find(Person.class, personid));
				fab.setCreatedDate(date);
				entityManager.merge(fab);
				q=entityManager.createQuery("from FabricDetail where fabric_id=?");
				q.setParameter(1, supplyDataBean.getFabricId());
				List<FabricDetail> fablist=(List<FabricDetail>)q.getResultList();
				if(fablist.size()>0){
					for (int i = 0; i < supplyDataBean.getFabricList().size(); i++) {
						FabricDetail fabdetail=entityManager.find(FabricDetail.class, fablist.get(i).getFabricDetailsId());
						fabdetail.setFabric(entityManager.find(Fabric.class, supplyDataBean.getFabricId()));
						fabdetail.setFabricColour(supplyDataBean.getFabricList().get(i).getFabricColour());
						fabdetail.setFabricPrice(supplyDataBean.getFabricList().get(i).getFabricPrice());
						fabdetail.setFabricWeight(supplyDataBean.getFabricList().get(i).getFabricWeight());
						fabdetail.setFabricQuantity(supplyDataBean.getFabricList().get(i).getFabricQuantity());
						fabdetail.setFabricTotal(supplyDataBean.getFabricList().get(i).getFabricTotal());
						entityManager.merge(fabdetail);
						status="Success";
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
		}

		@Override
		@Transactional(value="transactionManager")
		public String deletepurchasefabric(String personID,SupplyDataBean supplyDataBean) {
			Query q=null;
			try{
				Fabric fab=entityManager.find(Fabric.class, supplyDataBean.getFabricId());
				fab.setStatus("DELETED");
				entityManager.merge(fab);
				q=entityManager.createQuery("from FabricDetail where fabric_id=?");
				q.setParameter(1, supplyDataBean.getFabricId());
				List<FabricDetail> fablist=(List<FabricDetail>)q.getResultList();
				if(fablist.size()>0){
					for (int i = 0; i < fablist.size(); i++) {
						FabricDetail fabdetail=entityManager.find(FabricDetail.class, fablist.get(i).getFabricDetailsId());
						fabdetail.setStatus("DELETED");
						entityManager.merge(fabdetail);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public List<SupplyDataBean> getfabricpurchaseList(String personID,String str) {
			Query q=null;Calendar cal = Calendar.getInstance();List<SupplyDataBean> purchaseFabricList=new ArrayList<SupplyDataBean>();
			try{
				if(str.equalsIgnoreCase("all")){
					q=entityManager.createQuery("from Fabric where person_ID=? and status='INSERTED'");
					q.setParameter(1, personID);
				}else if(str.equalsIgnoreCase("30day")){
					cal.add(Calendar.DATE, -30);
				    Date todate1 = cal.getTime();    
				    q=entityManager.createQuery("from Fabric where person_ID=? and purchaseDate between ? and ? and status='INSERTED' ORDER BY fabricId DESC");
					q.setParameter(1, personID);
					q.setParameter(2, todate1);
					q.setParameter(3, date);
				}else if(str.equalsIgnoreCase("60day")){
					cal.add(Calendar.DATE, -60);
				    Date todate1 = cal.getTime();    
				    q=entityManager.createQuery("from Fabric where person_ID=? and purchaseDate between ? and ? and status='INSERTED' ORDER BY fabricId DESC");
					q.setParameter(1, personID);
					q.setParameter(2, todate1);
					q.setParameter(3, date);
				}else if(str.equalsIgnoreCase("90day")){
					cal.add(Calendar.DATE, -90);
				    Date todate1 = cal.getTime();    
				    q=entityManager.createQuery("from Fabric where person_ID=? and purchaseDate between ? and ? and status='INSERTED' ORDER BY fabricId DESC");
					q.setParameter(1, personID);
					q.setParameter(2, todate1);
					q.setParameter(3, date);
				}else if(str.equalsIgnoreCase("120day")){
					cal.add(Calendar.DATE, -120);
				    Date todate1 = cal.getTime();    
				    q=entityManager.createQuery("from Fabric where person_ID=? and purchaseDate between ? and ? and status='INSERTED' ORDER BY fabricId DESC");
					q.setParameter(1, personID);
					q.setParameter(2, todate1);
					q.setParameter(3, date);
				}
				List<Fabric> fabriclist=(List<Fabric>)q.getResultList();
				if(fabriclist.size()>0){
					for (int i = 0; i < fabriclist.size(); i++) {
						SupplyDataBean supp=new SupplyDataBean();
						supp.setSerialNo(String.valueOf(i+1));
						supp.setFabricId(fabriclist.get(i).getFabricId());
						supp.setFabricPurchaseDate(fabriclist.get(i).getPurchaseDate());
						supp.setFabricInvoiceNumber(fabriclist.get(i).getInvoiceNumber());
						supp.setSupplierName(fabriclist.get(i).getSupplierName());
						supp.setFabricName(fabriclist.get(i).getFabricName());
						supp.setWeightTotal(fabriclist.get(i).getTotalWeight());
						supp.setQuantityTotal(fabriclist.get(i).getTotalQuantity());
						supp.setTotalAmount(fabriclist.get(i).getTotalAmount());
						supp.setPaymentStatus(fabriclist.get(i).getPaymentStatus());
						supp.setInvoiceStatus(fabriclist.get(i).getInvoiceStatus());
						purchaseFabricList.add(supp);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return purchaseFabricList;
		}

		@Override
		@Transactional(value="transactionManager")
		public String insertitems(String personID, String clientID,SupplyDataBean supplyDataBean) {
			Query q=null;String status="Fail";
			try{
				q=entityManager.createQuery("from ItemTable where person_ID=? and client_ID=? and itemName=? and itemStatus=? and status='ACTIVE'");
				q.setParameter(1, personID);
				q.setParameter(2, clientID);
				q.setParameter(3, supplyDataBean.getItemName());
				q.setParameter(4, supplyDataBean.getItemStatus());
				List<ItemTable> itemtablelist=(List<ItemTable>)q.getResultList();
				if(itemtablelist.size()>0){
					status="Exist";
				}else{
					int personid=Integer.parseInt(personID);
					int clientid=Integer.parseInt(clientID);
					ItemTable item=new ItemTable();
					item.setPerson(entityManager.find(Person.class, personid));
					item.setClient(entityManager.find(Client.class, clientid));
					item.setItemName(supplyDataBean.getItemName());
					item.setBuyPrice(supplyDataBean.getItemBuyPrice());
					item.setSellPrice(supplyDataBean.getItemSellPrice());
					item.setItemStatus(supplyDataBean.getItemStatus());
					item.setStatus("ACTIVE");
					status="Success";
					entityManager.persist(item);
					entityManager.flush();
					entityManager.clear();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
		}

		@Override
		public List<SupplyDataBean> getitems(String personID, String clientID,String itemStatus) {
			Query q=null;List<SupplyDataBean> itemlist=new ArrayList<SupplyDataBean>();
			try{
				q=entityManager.createQuery("from ItemTable where person_ID=? and client_ID=? and itemStatus=? and status='ACTIVE' ORDER BY itemId DESC");
				q.setParameter(1, personID);
				q.setParameter(2, clientID);
				q.setParameter(3, itemStatus);
				List<ItemTable> itemList=(List<ItemTable>)q.getResultList();
				if(itemList.size()>0){
					for (int i = 0; i < itemList.size(); i++) {
						SupplyDataBean supp=new SupplyDataBean();
						supp.setSerialNo(String.valueOf(i+1));
						supp.setItemName(itemList.get(i).getItemName());
						supp.setItemBuyPrice(itemList.get(i).getBuyPrice());
						supp.setItemSellPrice(itemList.get(i).getSellPrice());
						supp.setItemStatus(itemList.get(i).getItemStatus());
						itemlist.add(supp);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return itemlist;
		}

		@Override
		public String viewitems(String personID, String clientID,SupplyDataBean supplyDataBean) {
			Query q=null;
			try{
				q=entityManager.createQuery("from ItemTable where person_ID=? and client_ID=? and itemStatus=? and itemName=?");
				q.setParameter(1, personID);
				q.setParameter(2, clientID);
				q.setParameter(3, supplyDataBean.getItemStatus());
				q.setParameter(4, supplyDataBean.getItemName());
				List<ItemTable> itemList=(List<ItemTable>)q.getResultList();
				if(itemList.size()>0){
					supplyDataBean.setItemName(itemList.get(0).getItemName());
					supplyDataBean.setItemBuyPrice(itemList.get(0).getBuyPrice());
					supplyDataBean.setItemSellPrice(itemList.get(0).getSellPrice());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		@Transactional(value="transactionManager")
		public String updateitems(String personID, SupplyDataBean supplyDataBean) {
			Query q=null;String status="Fail";
			try{
				q=entityManager.createQuery("from ItemTable where person_ID=? and itemStatus=? and itemName=?");
				q.setParameter(1, personID);
				q.setParameter(2, supplyDataBean.getItemStatus());
				q.setParameter(3, supplyDataBean.getItemName());
				List<ItemTable> itemList=(List<ItemTable>)q.getResultList();
				if(itemList.size()>0){
					ItemTable item=entityManager.find(ItemTable.class, itemList.get(0).getItemId());
					item.setBuyPrice(supplyDataBean.getItemBuyPrice());
					item.setSellPrice(supplyDataBean.getItemSellPrice());
					item.setStatus("ACTIVE");
					entityManager.merge(item);
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();;
			}
			return status;
		}

		@Override
		@Transactional(value="transactionManager")
		public String deleteitems(String personID, SupplyDataBean supplyDataBean) {
			Query q=null;String status="Fail";
			try{
				q=entityManager.createQuery("from ItemTable where person_ID=? and itemStatus=? and itemName=?");
				q.setParameter(1, personID);
				q.setParameter(2, supplyDataBean.getItemStatus());
				q.setParameter(3, supplyDataBean.getItemName());
				List<ItemTable> itemList=(List<ItemTable>)q.getResultList();
				if(itemList.size()>0){
					ItemTable item=entityManager.find(ItemTable.class, itemList.get(0).getItemId());
					item.setStatus("DE-ACTIVE");
					entityManager.merge(item);
					status="Success";
				}
			}catch(Exception e){
				e.printStackTrace();;
			}
			return status;
		}

		
		@Override
		public List<String> getcutterList(String personID, String clientID) {
			Query q=null;
			List<String> cutterlist=null;
			try{
				q=entityManager.createQuery("select cutter from Cutterpurchaseorder where client_ID=?  and status='ACTIVE'");
				q.setParameter(1, Integer.parseInt(clientID));
				cutterlist=(ArrayList<String>)q.getResultList();
				Set<String> dublicate=new HashSet<String>(cutterlist);
				cutterlist.clear();
				cutterlist.addAll(dublicate);
			}catch(Exception e){
				e.printStackTrace();
			}
			return cutterlist;
		}


		@Override
		public List<String> modelList(String personID, String clientID,ProductionDataBean productionDataBean) {
			Query q=null;
			List<String> modelList=null;
			int id=0;
			try{
				modelList=new ArrayList<String>();
				if(productionDataBean.getCategoryName()!=null){
					q=entityManager.createQuery("from Category where categoryName=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, productionDataBean.getCategoryName());
					q.setParameter(2, Integer.parseInt(clientID));
					List<Category> list=(ArrayList<Category>)q.getResultList();
					if(list.size()>0){
						id=list.get(0).getCategoryId();
						productionDataBean.setFabricPrice(list.get(0).getCategoryFabprice());
						q=null;
						q=entityManager.createQuery("from Model where category_id=? and status='Active'");
						q.setParameter(1, id);
						List<Model> model=(List<Model>)q.getResultList();
						if(model.size()>0){
						for(Model m:model){
							String modelname=m.getModel();
							modelList.add(modelname);
						}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return modelList;
		}


		@Override
		public String generateiInvoiceno(String personID, String clientID,
				ProductionDataBean productionDataBean) {
			Query q=null;
			String invoiceno="";
			String cutterlistsize="";
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			String purdate = sf.format(date);
			try{
					q=entityManager.createQuery("from Cutterpurchaseorder");
					List<Cutterpurchaseorder> cutterList=(List<Cutterpurchaseorder>)q.getResultList();
					cutterlistsize=String.valueOf(cutterList.size()+1);
					if(cutterList.size()==0){
						invoiceno="CU"+purdate+"00"+1;
					}
					else if(cutterlistsize.length()==1){
						invoiceno="CU"+purdate+"00"+cutterlistsize;
					}else if(cutterlistsize.length()==2){
						invoiceno="CU"+purdate+"0"+cutterlistsize;
					}else{
						invoiceno="CU"+purdate+cutterlistsize;
					}
			}catch(Exception e){
				e.printStackTrace();
			}
			return invoiceno;
		}
		
		@Override
		public String modelChange(ProductionDataBean productionDataBean,
				String personID, String clientID) {
			Query q=null;
			if(personID!=null && clientID!=null){
				try{
					q=entityManager.createQuery("from Model where model=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, productionDataBean.getModelName());
					q.setParameter(2, Integer.parseInt(clientID));
					List<Model> modelList=(ArrayList<Model>)q.getResultList();
					System.out.println("modelList size "+modelList.size());
					if(modelList.size()>0){
						productionDataBean.setPole(modelList.get(0).getPole());
						//productionDataBean.setModelSeri(modelList.get(0).getSeri());
						productionDataBean.setTotalseri(modelList.get(0).getCmt());
						productionDataBean.setSellingPrice(modelList.get(0).getCutter());
						productionDataBean.setPrinterr(modelList.get(0).getPrinter());
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return "";
		}

		@Transactional(value="transactionManager")
		@Override
		public String insertCutterorder(String personID, String clientID,ProductionDataBean productionDataBean,List<ProductionDataBean> cutterorderlist) {
			Query q=null;
			String status="Fail";
			if(personID!=null && clientID!=null){
			try{
				Cutterpurchaseorder cutterpurchase=new Cutterpurchaseorder();
				cutterpurchase.setCategory(productionDataBean.getCategoryName());
				cutterpurchase.setCutter(productionDataBean.getCutter());
				cutterpurchase.setModel(productionDataBean.getModelName());
				cutterpurchase.setOrderDate(productionDataBean.getOrderDate());
				cutterpurchase.setInvoiceNo(productionDataBean.getInvoiceNo());
				//cutterpurchase.setSeri(productionDataBean.getModelSeri());
				cutterpurchase.setTotalWeight(productionDataBean.getTotalWeight());
				cutterpurchase.setRemainWeight(productionDataBean.getRemainWeight());
				//cutterpurchase.setQuantitySeri(productionDataBean.getTotalseri());
				cutterpurchase.setTotalQuantity(productionDataBean.getTotalQuantity());
				cutterpurchase.setFabricPrice(productionDataBean.getFabricPrice());
				cutterpurchase.setPole(productionDataBean.getPole());
				cutterpurchase.setResult(productionDataBean.getResult());
				cutterpurchase.setSaving(productionDataBean.getSaving());
				cutterpurchase.setValue(productionDataBean.getValue());
				cutterpurchase.setStatus("ACTIVE");
				cutterpurchase.setInvoiceStatus("INSERTED");
				cutterpurchase.setPrinterStatus("WAITING");
				cutterpurchase.setClient(entityManager.find(Client.class, Integer.parseInt(clientID)));
				cutterpurchase.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
				entityManager.persist(cutterpurchase);
				q=entityManager.createQuery("from Cutterpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, productionDataBean.getInvoiceNo());
				q.setParameter(2, Integer.parseInt(clientID));
				List<Cutterpurchaseorder> list=(ArrayList<Cutterpurchaseorder>)q.getResultList();
				if(list.size()>0){
					for(int i=0;i<cutterorderlist.size();i++){
						if(cutterorderlist.get(i).getSeri()!=null ||!cutterorderlist.get(i).getSeri().equalsIgnoreCase("") && cutterorderlist.get(i).getQuantity()!=null ||!cutterorderlist.get(i).getQuantity().equalsIgnoreCase("")){
							Cutterpurchase purchase=new Cutterpurchase();
							purchase.setCutterpurchaseorder(entityManager.find(Cutterpurchaseorder.class, list.get(0).getPurchase_ID()));
							purchase.setSeri(cutterorderlist.get(i).getSeri());
							purchase.setQuantity(cutterorderlist.get(i).getQuantity());
							//purchase.setMotive(cutterorderlist.get(i).getMotive());
							purchase.setStatus("ACTIVE");
							purchase.setOrderStatus("NOT READY");
							entityManager.persist(purchase);
							entityManager.flush();
							entityManager.clear();
							status="Success";
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			}
			return status;
		}


		@Override
		public List<ProductionDataBean> cutterPurchasedetails(String personID,String clientID,ProductionDataBean productionDataBean) {
			Query q=null;
			List<ProductionDataBean> beanList=null;
			if(personID!=null && clientID!=null){
				try{
					beanList=new ArrayList<ProductionDataBean>();
					if(productionDataBean.getFromDate()!=null && productionDataBean.getToDate()!=null){
						q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and orderDate between ? and ? and status='ACTIVE' ORDER BY purchase_ID DESC");
						q.setParameter(1, Integer.parseInt(clientID));
						q.setParameter(2, productionDataBean.getFromDate());
						q.setParameter(3, productionDataBean.getToDate());
					}else{
						q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and status='ACTIVE' ORDER BY purchase_ID DESC");
						q.setParameter(1, Integer.parseInt(clientID));
					}
					 List<Cutterpurchaseorder> purchaseList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
					 if(purchaseList.size()>0){
						 for(int p=0;p<purchaseList.size();p++){
							 ProductionDataBean order=new ProductionDataBean();
							 order.setOrderDate(purchaseList.get(p).getOrderDate());
							 order.setInvoiceNo(purchaseList.get(p).getInvoiceNo());
							 order.setCutter(purchaseList.get(p).getCutter());
							 order.setModelName(purchaseList.get(p).getModel());
							// order.setModelSeri(purchaseList.get(p).getSeri());
							 order.setTotalseri(purchaseList.get(p).getQuantitySeri());
							 order.setTotalQuantity(purchaseList.get(p).getTotalQuantity());
							 order.setSaving(purchaseList.get(p).getSaving());
							 order.setValue(purchaseList.get(p).getValue());
							 order.setInvoiceStatus(purchaseList.get(p).getInvoiceStatus());
							 beanList.add(order);
						 }
					 }
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return beanList;
		}

		
		@Override
		public String getcutterOrderview(String personID, String clientID,
				ProductionDataBean productionDataBean) {
			Query q=null;
			int id=0;
			List<ProductionDataBean> beanList=new ArrayList<ProductionDataBean>();
			if(personID!=null && clientID!=null){
				try{
					 q=entityManager.createQuery("from Cutterpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
					 q.setParameter(1, productionDataBean.getInvoiceNo());
					 q.setParameter(2, Integer.parseInt(clientID));
					 List<Cutterpurchaseorder> purchaseList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
					 if(purchaseList.size()>0){
						 id=purchaseList.get(0).getPurchase_ID();
						 productionDataBean.setOrderDate(purchaseList.get(0).getOrderDate());
						 productionDataBean.setInvoiceNo(purchaseList.get(0).getInvoiceNo());
						 productionDataBean.setCutter(purchaseList.get(0).getCutter());
						 productionDataBean.setCategoryName(purchaseList.get(0).getCategory());
						 productionDataBean.setModelName(purchaseList.get(0).getModel());
						 //productionDataBean.setModelSeri(purchaseList.get(0).getSeri());
						 productionDataBean.setTotalWeight(purchaseList.get(0).getTotalWeight());
						 productionDataBean.setTotalseri(purchaseList.get(0).getQuantitySeri());
						 productionDataBean.setRemainWeight(purchaseList.get(0).getRemainWeight());
						 productionDataBean.setTotalQuantity(purchaseList.get(0).getTotalQuantity());
						 productionDataBean.setFabricPrice(purchaseList.get(0).getFabricPrice());
						 productionDataBean.setPole(purchaseList.get(0).getPole());
						 productionDataBean.setResult(purchaseList.get(0).getResult());
						 productionDataBean.setValue(purchaseList.get(0).getValue());
						 productionDataBean.setSaving(purchaseList.get(0).getSaving());
						 productionDataBean.setTotalValue(purchaseList.get(0).getValue());
						 q=null;
						 q=entityManager.createQuery("from Cutterpurchase where purchase_ID=? and status='ACTIVE'");
						 q.setParameter(1, id);
						 List<Cutterpurchase> list=(ArrayList<Cutterpurchase>)q.getResultList();
						 if(list.size()>0){
							 for(int i=0;i<list.size();i++){
								 ProductionDataBean production=new ProductionDataBean();
								 production.setSerialNo(String.valueOf(i+1));
								 production.setSeri(list.get(i).getSeri());
								 production.setQuantity(list.get(i).getQuantity());
								 //production.setMotive(list.get(i).getMotive());
								 beanList.add(production);
								 productionDataBean.setOrderQuantitylist(beanList);
							 }
						 }
						 /*q=null;
						 q=entityManager.createQuery("from Model where model=? and status='ACTIVE'");
						 q.setParameter(1, purchaseList.get(0).getModel());
						 List<Model> model=(List<Model>)q.getResultList();
						 if(model.size()>0){
							 String cutterfee=model.get(0).getCutter();
							 productionDataBean.setTotalValue(new BigDecimal(cutterfee).multiply(new BigDecimal(purchaseList.get(0).getTotalQuantity())).toString());
						 }*/
					 }
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return null;
		}

		@Transactional(value="transactionManager")
		@Override
		public String updateCutterorder(String personID, String clientID,
				ProductionDataBean productionDataBean) {
			Query q=null;
			String status="Fail";
			int id=0;
			if(personID!=null && clientID!=null){
				try{
					 q=entityManager.createQuery("from Cutterpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
					 q.setParameter(1, productionDataBean.getInvoiceNo());
					 q.setParameter(2, Integer.parseInt(clientID));
					 List<Cutterpurchaseorder> purchaseList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
					 if(purchaseList.size()>0){
						 id=purchaseList.get(0).getPurchase_ID();
						 Cutterpurchaseorder cutter=entityManager.find(Cutterpurchaseorder.class, id);
						 cutter.setCategory(productionDataBean.getCategoryName());
						 cutter.setModel(productionDataBean.getModelName());
						 //cutter.setSeri(productionDataBean.getModelSeri());
						 cutter.setCutter(productionDataBean.getCutter());
						 cutter.setTotalWeight(productionDataBean.getTotalWeight());
						 cutter.setRemainWeight(productionDataBean.getRemainWeight());
						 cutter.setFabricPrice(productionDataBean.getFabricPrice());
						 cutter.setPole(productionDataBean.getPole());
						 cutter.setResult(productionDataBean.getResult());
						 cutter.setValue(productionDataBean.getValue());
						 cutter.setSaving(productionDataBean.getSaving());
						 cutter.setTotalQuantity(productionDataBean.getTotalQuantity());
						 entityManager.merge(cutter);
						 status="Success";
						 q=entityManager.createQuery("from Cutterpurchase where purchase_ID=? and status='ACTIVE'");
						 q.setParameter(1, id);
						 List<Cutterpurchase> list=(ArrayList<Cutterpurchase>)q.getResultList();
						 	if(list.size()>0){
								for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
										Cutterpurchase purchase=entityManager.find(Cutterpurchase.class, list.get(i).getOrder_ID());
										purchase.setSeri(productionDataBean.getOrderQuantitylist().get(i).getSeri());
										purchase.setQuantity(productionDataBean.getOrderQuantitylist().get(i).getQuantity());
										//purchase.setMotive(productionDataBean.getOrderQuantitylist().get(i).getMotive());
										entityManager.merge(purchase);
										entityManager.flush();
										entityManager.clear();
										status="Success";
								}
							}
						}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return status;
		}

		@Transactional(value="transactionManager")
		@Override
		public String deleteCutterorder(String personID, String clientID,
				ProductionDataBean productionDataBean) {
			Query q=null;
			String status="Fail";
			int id=0;
			if(personID!=null && clientID!=null){
				try{
					 q=entityManager.createQuery("from Cutterpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
					 q.setParameter(1, productionDataBean.getInvoiceNo());
					 q.setParameter(2, Integer.parseInt(clientID));
					 List<Cutterpurchaseorder> purchaseList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
					 if(purchaseList.size()>0){
						 id=purchaseList.get(0).getPurchase_ID();
						 Cutterpurchaseorder cutter=entityManager.find(Cutterpurchaseorder.class, id);
						 cutter.setStatus("DEACTIVE");
						 entityManager.merge(cutter);
						 q=entityManager.createQuery("from Cutterpurchase where purchase_ID=? and status='ACTIVE'");
						 q.setParameter(1, id);
						 List<Cutterpurchase> list=(ArrayList<Cutterpurchase>)q.getResultList();
						 	if(list.size()>0){
						 		for(int k=0;k<list.size();k++){
						 			Cutterpurchase purchase=entityManager.find(Cutterpurchase.class, list.get(k).getOrder_ID());
						 			purchase.setStatus("DEACTIVE");
						 			entityManager.merge(purchase);
						 			status="Success";
						 		}
						 	}
					 }
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return status;
		}


		@Override
		public List<ProductionDataBean> cutterorderSearch(String personID,
				String clientID, ProductionDataBean productionDataBean) {
			Query q=null;
			List<ProductionDataBean> beanList=null;
			if(personID!=null && clientID!=null){
				try{
					beanList=new ArrayList<ProductionDataBean>();
					if(productionDataBean.getFromDate() != null && productionDataBean.getToDate()!=null){
						q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and cutter=? and orderDate between ? and ? and status='ACTIVE' ORDER BY purchase_ID DESC");
						q.setParameter(1, Integer.parseInt(clientID));
						q.setParameter(2, productionDataBean.getCutter());
						q.setParameter(3, productionDataBean.getFromDate());
						q.setParameter(4, productionDataBean.getToDate());
					}else{
						 q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and cutter=? and status='ACTIVE' ORDER BY purchase_ID DESC");
						 q.setParameter(1, Integer.parseInt(clientID));
						 q.setParameter(2, productionDataBean.getCutter());
					}
					 List<Cutterpurchaseorder> purchaseList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
					 if(purchaseList.size()>0){
						 for(int p=0;p<purchaseList.size();p++){
							 ProductionDataBean order=new ProductionDataBean();
							 order.setOrderDate(purchaseList.get(p).getOrderDate());
							 order.setInvoiceNo(purchaseList.get(p).getInvoiceNo());
							 order.setCutter(purchaseList.get(p).getCutter());
							 order.setModelName(purchaseList.get(p).getModel());
							 order.setModelSeri(purchaseList.get(p).getSeri());
							 order.setTotalseri(purchaseList.get(p).getQuantitySeri());
							 order.setTotalQuantity(purchaseList.get(p).getTotalQuantity());
							 order.setSaving(purchaseList.get(p).getSaving());
							 order.setValue(purchaseList.get(p).getValue());
							 order.setInvoiceStatus(purchaseList.get(p).getInvoiceStatus());
							 beanList.add(order);
						 }
					 }
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return beanList;
		}


		@Override
		public List<String> getCmt(String personID, String clientID) {
			Query q=null;
			List<String> cmtlist=null;
			try{
				q=entityManager.createQuery("select cmt from Cmtpurchaseorder where client_ID=?  and status='ACTIVE'");
				q.setParameter(1, Integer.parseInt(clientID));
				cmtlist=(ArrayList<String>)q.getResultList();
				Set<String> dublicate=new HashSet<String>(cmtlist);
				cmtlist.clear();
				cmtlist.addAll(dublicate);
			}catch(Exception e){
				e.printStackTrace();
			}
			return cmtlist;
		}


		@Override
		public String getCmtinvoice(String personID, String clientID) {
			Query q=null;
			String invoiceno="";
			String cmtlistsize="";
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			String purdate = sf.format(date);
			try{
					q=entityManager.createQuery("from Cmtpurchaseorder");
					List<Cmtpurchaseorder> cmtList=(List<Cmtpurchaseorder>)q.getResultList();
					cmtlistsize=String.valueOf(cmtList.size()+1);
					if(cmtList.size()==0){
						invoiceno="CMO"+purdate+"00"+1;
					}
					if(cmtlistsize.length()==1){
						invoiceno="CMO"+purdate+"00"+cmtlistsize;
					}else if(cmtlistsize.length()==2){
						invoiceno="CMO"+purdate+"0"+cmtlistsize;
					}else{
						invoiceno="CMO"+purdate+cmtlistsize;
					}
			}catch(Exception e){
				e.printStackTrace();
			}
			return invoiceno;
		}

		@Transactional(value="transactionManager")
		@Override
		public String insertCmtorder(String personID, String clientID,ProductionDataBean productionDataBean,
				List<ProductionDataBean> cmtorderlist) {
			String status="Fail";
			Query q=null;
			if(personID!=null && clientID!=null){
				try{
					Cmtpurchaseorder purchase=new Cmtpurchaseorder();
					purchase.setCmt(productionDataBean.getCmt());
					purchase.setCategory(productionDataBean.getCategoryName());
					purchase.setOrderDate(productionDataBean.getOrderDate());
					purchase.setInvoiceNo(productionDataBean.getInvoiceNo());
					purchase.setModel(productionDataBean.getModelName());
					purchase.setQuantitySeri(productionDataBean.getTotalseri());
					purchase.setTotalQuantity(productionDataBean.getTotalQuantity());
					purchase.setValue(productionDataBean.getTotalValue());
					purchase.setSeri(productionDataBean.getModelSeri());
					purchase.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
					purchase.setClient(entityManager.find(Client.class, Integer.parseInt(clientID)));
					purchase.setStatus("ACTIVE");
					purchase.setPrinterInvoice(productionDataBean.getCutterInvoiceno());
					purchase.setInvoiceStatus("HOLDING");
					entityManager.persist(purchase);
					q=entityManager.createQuery("from Cmtpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, productionDataBean.getInvoiceNo());
					q.setParameter(2, clientID);
					List<Cmtpurchaseorder> cmtpurchaseList=(List<Cmtpurchaseorder>)q.getResultList();
					if(cmtpurchaseList.size()>0){
					int id=cmtpurchaseList.get(0).getPurchase_ID();
					for(int i=0;i<cmtorderlist.size();i++){
						try{
						if(cmtorderlist.get(i).getSeri()!=null || !cmtorderlist.get(i).getSeri().equalsIgnoreCase("")){
							Cmtorder order=new Cmtorder();
							order.setMotive(cmtorderlist.get(i).getMotive());
							order.setCmtpurchaseorder(entityManager.find(Cmtpurchaseorder.class, id));
							order.setSeri(cmtorderlist.get(i).getSeri());
							order.setQuantity(cmtorderlist.get(i).getQuantity());
							order.setValue(cmtorderlist.get(i).getValue());
							order.setStatus("ACTIVE");
							entityManager.persist(order);
							entityManager.flush();
							entityManager.clear();
						}
						}catch(NullPointerException e){
							
						}
					}
					}
					for (int i = 0; i < cmtorderlist.size(); i++) {
						if(cmtorderlist.get(i).getSeri()!=null || !cmtorderlist.get(i).getSeri().equalsIgnoreCase("")){
						q=null;
						q=entityManager.createQuery("from Cmtready where model=? and client_ID=? and seri=? and status='ACTIVE' and orderStatus='WAITING'");
						q.setParameter(1, productionDataBean.getModelName());
						q.setParameter(2, clientID);
						q.setParameter(3, cmtorderlist.get(i).getSeri());
						List<Cmtready> cmtList=(ArrayList<Cmtready>)q.getResultList();
						if(cmtList.size()>0){
							Cmtready order=entityManager.find(Cmtready.class, cmtList.get(0).getCmtready_ID());
							order.setOrderStatus("ORDERED");
							entityManager.merge(order);
							entityManager.flush();entityManager.clear();
							status="Success";
						}
						}
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return status;
		}

		@Override
		public List<ProductionDataBean> getcmtorderView(String personID,
				String clientID, ProductionDataBean productionDataBean) {
			Query q=null;
			List<ProductionDataBean> beanList=new ArrayList<ProductionDataBean>();
			if (personID!=null && clientID!=null) {
				try{
					/*q=entityManager.createQuery("from Cmtpurchaseorder where client_ID=? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, clientID);
					List<Cmtpurchaseorder> purchaselist=(ArrayList<Cmtpurchaseorder>)q.getResultList();
					if(purchaselist.size()>0){
						for (int i = 0; i < purchaselist.size(); i++) {
							System.out.println("invoice---------"+purchaselist.get(i).getInvoiceStatus());
							if(purchaselist.get(i).getInvoiceStatus().equals("HOLDING")){
								ProductionDataBean production=new ProductionDataBean();
								production.setOrderDate(purchaselist.get(i).getOrderDate());
								production.setInvoiceNo(purchaselist.get(i).getInvoiceNo());
								production.setCmt(purchaselist.get(i).getCmt());
								production.setCategoryName(purchaselist.get(i).getCategory());
								production.setModelName(purchaselist.get(i).getModel());
								production.setTotalQuantity(purchaselist.get(i).getTotalQuantity());
								production.setTotalValue(purchaselist.get(i).getValue());
								production.setInvoiceStatus(purchaselist.get(i).getInvoiceStatus());
								beanList.add(production);
							}else if(purchaselist.get(i).getInvoiceStatus().equalsIgnoreCase("RECEIVED")){
								q=null;
								q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and model=? and status='ACTIVE' ORDER BY receive_ID DESC");
								q.setParameter(1, clientID);
								q.setParameter(2, purchaselist.get(i).getModel());
								List<Cmtreceiveorder> receiverList=(ArrayList<Cmtreceiveorder>)q.getResultList();
								if(receiverList.size()>0){
									for(int j=0;j<receiverList.size();j++){
										ProductionDataBean pro=new ProductionDataBean();
										pro.setOrderDate(receiverList.get(j).getReceiveDate());
										pro.setInvoiceNo(receiverList.get(j).getInvoiceNo());
										pro.setCmt(receiverList.get(j).getCmt());
										pro.setModelName(receiverList.get(j).getModel());
										pro.setTotalQuantity(receiverList.get(j).getTotalQuantity());
										pro.setTotalValue(receiverList.get(j).getAmount());
										pro.setInvoiceStatus(receiverList.get(j).getInvoiceStatus());
										beanList.add(pro);
									}
								}
							}
						}
					}*/
					
					q=entityManager.createQuery("select model from Cmtpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus='HOLDING' ");
					q.setParameter(1, clientID);
					List<String> modelList=(ArrayList<String>)q.getResultList();
					Set<String> dublicateList=new HashSet<String>(modelList);
					modelList.clear();
					modelList.addAll(dublicateList);
					if(modelList.size()>0){
						for(int i=0;i<modelList.size();i++){
							q=null;
							q=entityManager.createQuery("from Cmtpurchaseorder where model=? and client_ID=? and status='ACTIVE' ");
							q.setParameter(1, modelList.get(i));
							q.setParameter(2, clientID);
							List<Cmtpurchaseorder> cmtpurchaseList=(ArrayList<Cmtpurchaseorder>)q.getResultList();
							if(cmtpurchaseList.size()>0){
								for(int j=0;j<cmtpurchaseList.size();j++){
								ProductionDataBean production=new ProductionDataBean();
								production.setOrderDate(cmtpurchaseList.get(j).getOrderDate());
								production.setInvoiceNo(cmtpurchaseList.get(j).getInvoiceNo());
								production.setCmt(cmtpurchaseList.get(j).getCmt());
								production.setModelName(cmtpurchaseList.get(j).getModel());
								production.setTotalQuantity(cmtpurchaseList.get(j).getTotalQuantity());
								production.setTotalValue(cmtpurchaseList.get(j).getValue());
								production.setInvoiceStatus(cmtpurchaseList.get(j).getInvoiceStatus());
								beanList.add(production);
								}
							}
						}
					}
					q=entityManager.createQuery("select model from Cmtpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus='RECEIVED'");
					q.setParameter(1, clientID);
					List<String> modelnameList=(ArrayList<String>)q.getResultList();
					Set<String> dublicate=new HashSet<String>(modelnameList);
					modelnameList.clear();
					modelnameList.addAll(dublicate);
					if(modelnameList.size()>0){
						for(int i=0;i<modelnameList.size();i++){
							q=null;
							q=entityManager.createQuery("from Cmtreceiveorder where model=? and client_ID=? and status='ACTIVE' ");
							q.setParameter(1, modelnameList.get(i));
							q.setParameter(2, clientID);
							List<Cmtreceiveorder> cmtreceiveList=(ArrayList<Cmtreceiveorder>)q.getResultList();
							if(cmtreceiveList.size()>0){
								for(int j=0;j<cmtreceiveList.size();j++){
								ProductionDataBean production=new ProductionDataBean();
								production.setOrderDate(cmtreceiveList.get(j).getReceiveDate());
								production.setInvoiceNo(cmtreceiveList.get(j).getInvoiceNo());
								production.setCmt(cmtreceiveList.get(j).getCmt());
								production.setModelName(cmtreceiveList.get(j).getModel());
								production.setTotalQuantity(cmtreceiveList.get(j).getTotalQuantity());
								production.setTotalValue(cmtreceiveList.get(j).getAmount());
								production.setInvoiceStatus(cmtreceiveList.get(j).getInvoiceStatus());
								beanList.add(production);
								}
							}
							
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return beanList;
		}

		@Override
		public List<ProductionDataBean> getcmtorderView1(String personID,
				String clientID, ProductionDataBean productionDataBean) {
			Query q=null;
			List<ProductionDataBean> beanList=new ArrayList<ProductionDataBean>();
			if (personID!=null && clientID!=null) {
				try{
					q=entityManager.createQuery("select model from Cmtpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus='RECEIVED'");
					q.setParameter(1, clientID);
					List<String> modelnameList=(ArrayList<String>)q.getResultList();
					Set<String> dublicate=new HashSet<String>(modelnameList);
					modelnameList.clear();
					modelnameList.addAll(dublicate);
					if(modelnameList.size()>0){
						for(int i=0;i<modelnameList.size();i++){
							q=null;
							q=entityManager.createQuery("from Cmtreceiveorder where model=? and client_ID=? and status='ACTIVE' and stockStatus='NOT ADDED' and (invoiceStatus='STOCK IN' or invoiceStatus='PARTIAL STOCKIN')");
							q.setParameter(1, modelnameList.get(i));
							q.setParameter(2, clientID);
							List<Cmtreceiveorder> cmtreceiveList=(ArrayList<Cmtreceiveorder>)q.getResultList();
							if(cmtreceiveList.size()>0){
								for(int j=0;j<cmtreceiveList.size();j++){
								ProductionDataBean production=new ProductionDataBean();
								production.setOrderDate(cmtreceiveList.get(j).getReceiveDate());
								production.setInvoiceNo(cmtreceiveList.get(j).getInvoiceNo());
								production.setCmt(cmtreceiveList.get(j).getCmt());
								production.setModelName(cmtreceiveList.get(j).getModel());
								production.setTotalQuantity(cmtreceiveList.get(j).getTotalQuantity());
								production.setTotalValue(cmtreceiveList.get(j).getAmount());
								production.setInvoiceStatus(cmtreceiveList.get(j).getInvoiceStatus());
								beanList.add(production);
								}
							}
							
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return beanList;
		}
		
		@Override
		public List<ProductionDataBean> getcmtSearch(String personID,
				String clientID, ProductionDataBean productionDataBean) {
			Query q=null;
			List<ProductionDataBean> beanList=new ArrayList<ProductionDataBean>();
			if (personID!=null && clientID!=null) {
				try{
					q=entityManager.createQuery("from Cmtpurchaseorder where client_ID=? and cmt=? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, clientID);
					q.setParameter(2, productionDataBean.getCmt());
					List<Cmtpurchaseorder> purchaselist=(ArrayList<Cmtpurchaseorder>)q.getResultList();
					if(purchaselist.size()>0){
						for (int i = 0; i < purchaselist.size(); i++) {
							if(purchaselist.get(i).getInvoiceStatus().equals("HOLDING")){
								ProductionDataBean production=new ProductionDataBean();
								production.setOrderDate(purchaselist.get(i).getOrderDate());
								production.setInvoiceNo(purchaselist.get(i).getInvoiceNo());
								production.setCmt(purchaselist.get(i).getCmt());
								production.setCategoryName(purchaselist.get(i).getCategory());
								production.setModelName(purchaselist.get(i).getModel());
								production.setTotalQuantity(purchaselist.get(i).getTotalQuantity());
								production.setTotalValue(purchaselist.get(i).getValue());
								production.setInvoiceStatus(purchaselist.get(i).getInvoiceStatus());
								beanList.add(production);
							}else if(purchaselist.get(i).getInvoiceStatus().equalsIgnoreCase("STOCK IN")){
								q=null;
								q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and model=? and status='ACTIVE' ORDER BY receive_ID DESC");
								q.setParameter(1, clientID);
								q.setParameter(2, purchaselist.get(i).getModel());
								List<Cmtreceiveorder> receiverList=(ArrayList<Cmtreceiveorder>)q.getResultList();
								if(receiverList.size()>0){
									for(Cmtreceiveorder receive:receiverList){
										ProductionDataBean pro=new ProductionDataBean();
										pro.setOrderDate(receive.getReceiveDate());
										pro.setInvoiceNo(receive.getInvoiceNo());
										pro.setCmt(receive.getCmt());
										pro.setModelName(receive.getModel());
										pro.setTotalQuantity(receive.getTotalQuantity());
										pro.setTotalValue(receive.getAmount());
										pro.setInvoiceStatus(receive.getInvoiceStatus());
										beanList.add(pro);
								}
							}
							}
						}
					}
				}catch(Exception e){
				}
			}
			return beanList;
		}

		@Override
		public void getcmtpurchesView(String personID, String clientID,
				ProductionDataBean productionDataBean) {
			Query q=null;
			int id=0;
			BigDecimal totalquant=BigDecimal.valueOf(0);
			BigDecimal totalval=BigDecimal.valueOf(0);
			BigDecimal val=BigDecimal.valueOf(0);
			List<ProductionDataBean> beanList=new ArrayList<ProductionDataBean>();
			if(personID!=null && clientID!=null){
				try{
					if(productionDataBean.getInvoiceNo().startsWith("CMO")){
					q=entityManager.createQuery("from Cmtpurchaseorder where client_ID=? and invoiceNo=? and status='ACTIVE'");
					q.setParameter(1, clientID);
					q.setParameter(2, productionDataBean.getInvoiceNo());
					List<Cmtpurchaseorder> list=(List<Cmtpurchaseorder>)q.getResultList();
					if(list.size()>0){
						id=list.get(0).getPurchase_ID();
						productionDataBean.setOrderDate(list.get(0).getOrderDate());
						productionDataBean.setCmt(list.get(0).getCmt());
						productionDataBean.setModelName(list.get(0).getModel());
						productionDataBean.setTotalQuantity(list.get(0).getTotalQuantity());
						productionDataBean.setTotalValue(list.get(0).getValue());
						q=null;
						 q=entityManager.createQuery("from Cmtorder where purchase_ID=? and status='ACTIVE'");
						 q.setParameter(1, id);
						 List<Cmtorder> orderlist=(ArrayList<Cmtorder>)q.getResultList();
						 if(list.size()>0){
							 for(int i=0;i<orderlist.size();i++){
								 ProductionDataBean production=new ProductionDataBean();
								 production.setSerialNo(String.valueOf(i+1));
								 production.setSeri(orderlist.get(i).getSeri());
								 production.setQuantity(orderlist.get(i).getQuantity());
								 production.setMotive(orderlist.get(i).getMotive());
								 production.setValue(orderlist.get(i).getValue());
								 beanList.add(production);
								 productionDataBean.setOrderQuantitylist(beanList);
							 }
						 }
					}
					}else if(productionDataBean.getInvoiceNo().startsWith("CMR")){
						q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and invoiceNo=? and status='ACTIVE'");
						q.setParameter(1, clientID);
						q.setParameter(2, productionDataBean.getInvoiceNo());
						List<Cmtreceiveorder> list=(List<Cmtreceiveorder>)q.getResultList();
						if(list.size()>0){
							id=list.get(0).getReceive_ID();
							productionDataBean.setOrderDate(list.get(0).getReceiveDate());
							productionDataBean.setCmt(list.get(0).getCmt());
							productionDataBean.setModelName(list.get(0).getModel());
							q=null;
							 q=entityManager.createQuery("from Cmtorder where receive_ID=? and status='ACTIVE'");
							 q.setParameter(1, id);
							 List<Cmtorder> orderlist=(ArrayList<Cmtorder>)q.getResultList();
							 if(list.size()>0){
								 for(int i=0;i<orderlist.size();i++){
									 ProductionDataBean production=new ProductionDataBean();
									 production.setSerialNo(String.valueOf(i+1));
									 production.setSeri(orderlist.get(i).getSeri());
									 //get added quantity
									 q=entityManager.createQuery("from Cmtstock where cmtreceive_ID=? and seri=?");
									 q.setParameter(1, id);
									 q.setParameter(2, orderlist.get(i).getSeri());
									 List<Cmtstock> cmtstocklist=(List<Cmtstock>)q.getResultList();
									 if(cmtstocklist.size()>0){
										 q=null;
										 q=entityManager.createQuery("from Model where model=? and client_ID=? and status='ACTIVE'");
										 q.setParameter(1, list.get(0).getModel());
										 q.setParameter(2, Integer.parseInt(clientID));
										 List<Model> modelList=(ArrayList<Model>)q.getResultList();
										 if(modelList.size()>0){
											 productionDataBean.setCmtvalue(modelList.get(0).getCmt());
										 }
										 System.out.println("cmt "+productionDataBean.getCmtvalue());
										 production.setQuantity(cmtstocklist.get(0).getAddQuantity());
										 val=new BigDecimal(cmtstocklist.get(0).getAddQuantity()).multiply(new BigDecimal(productionDataBean.getCmtvalue()));
										 production.setValue(val.toString());
										 totalquant=totalquant.add(new BigDecimal(cmtstocklist.get(0).getAddQuantity()));
										 productionDataBean.setTotalQuantity(totalquant.toString());
										 totalval=totalquant.multiply(new BigDecimal(productionDataBean.getCmtvalue()));
										 productionDataBean.setTotalValue(totalval.toString());
									 }else{
										 production.setQuantity(orderlist.get(i).getQuantity());
										 production.setValue(orderlist.get(i).getValue());
										 productionDataBean.setTotalQuantity(list.get(0).getTotalQuantity());
										 productionDataBean.setTotalValue(list.get(0).getAmount());
										 
									 }
									 production.setMotive(orderlist.get(i).getMotive());
									 beanList.add(production);
									 productionDataBean.setOrderQuantitylist(beanList);
								 }
							 }
							
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}

		@Transactional(value="transactionManager")
		@Override
		public String cmtorderUpdate(String personID, String clientID,
		    ProductionDataBean productionDataBean) {
			Query q=null;
			String status="Fail";
			int id=0;
			BigDecimal value=BigDecimal.valueOf(0);
			BigDecimal quantity=BigDecimal.valueOf(0);
			if(personID!=null && clientID!=null){
				try{
					if(productionDataBean.getInvoiceNo().startsWith("CMO")){
					 q=entityManager.createQuery("from Cmtpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
					 q.setParameter(1, productionDataBean.getInvoiceNo());
					 q.setParameter(2, Integer.parseInt(clientID));
					 List<Cmtpurchaseorder> purchaseList=(ArrayList<Cmtpurchaseorder>)q.getResultList();
					 if(purchaseList.size()>0){
						 id=purchaseList.get(0).getPurchase_ID();
						 Cmtpurchaseorder cmt=entityManager.find(Cmtpurchaseorder.class, id);
						 cmt.setModel(productionDataBean.getModelName());
						 cmt.setValue(productionDataBean.getTotalValue());
						 cmt.setTotalQuantity(productionDataBean.getTotalQuantity());
						 entityManager.merge(cmt);
						 status="Success";
						 q=entityManager.createQuery("from Cmtorder where purchase_ID=? and status='ACTIVE'");
						 q.setParameter(1, id);
						 List<Cmtorder> list=(ArrayList<Cmtorder>)q.getResultList();
						 	if(list.size()>0){
								for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
									Cmtorder purchase=entityManager.find(Cmtorder.class, list.get(i).getOrder_ID());
										purchase.setSeri(productionDataBean.getOrderQuantitylist().get(i).getSeri());
										purchase.setQuantity(productionDataBean.getOrderQuantitylist().get(i).getQuantity());
										purchase.setMotive(productionDataBean.getOrderQuantitylist().get(i).getMotive());
										purchase.setValue(productionDataBean.getOrderQuantitylist().get(i).getValue());
										entityManager.merge(purchase);
										entityManager.flush();
										entityManager.clear();
										status="Success";
								}
							}
						}
					}else if(productionDataBean.getInvoiceNo().startsWith("CMR")){
						q=entityManager.createQuery("from Cmtreceiveorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
						 q.setParameter(1, productionDataBean.getInvoiceNo());
						 q.setParameter(2, Integer.parseInt(clientID));
						 List<Cmtreceiveorder> purchaseList=(ArrayList<Cmtreceiveorder>)q.getResultList();
						 if(purchaseList.size()>0){
							 id=purchaseList.get(0).getReceive_ID();
							 Cmtreceiveorder cmt=entityManager.find(Cmtreceiveorder.class, id);
							 cmt.setModel(productionDataBean.getModelName());
							 cmt.setAmount(productionDataBean.getTotalValue());
							 cmt.setTotalQuantity(productionDataBean.getTotalQuantity());
							 entityManager.merge(cmt);
							 status="Success";
							 q=entityManager.createQuery("from Cmtorder where receive_ID=? and status='ACTIVE'");
							 q.setParameter(1, id);
							 List<Cmtorder> list=(ArrayList<Cmtorder>)q.getResultList();
							 	if(list.size()>0){
									for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
										Cmtorder purchase=entityManager.find(Cmtorder.class, list.get(i).getOrder_ID());
											purchase.setSeri(productionDataBean.getOrderQuantitylist().get(i).getSeri());
											purchase.setQuantity(productionDataBean.getOrderQuantitylist().get(i).getQuantity());
											purchase.setMotive(productionDataBean.getOrderQuantitylist().get(i).getMotive());
											purchase.setValue(productionDataBean.getOrderQuantitylist().get(i).getValue());
											entityManager.merge(purchase);
											entityManager.flush();
											entityManager.clear();
											status="Success";
									}
								}
							}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return status;
		}
		
		@Transactional(value="transactionManager")
		@Override
		public String cmtOrderdelete(String personID, String clientID,
				ProductionDataBean productionDataBean) {
			Query q=null;
			String status="Fail";
			int id=0;
			if(personID!=null && clientID!=null){
				try{
					if(productionDataBean.getInvoiceNo().startsWith("CMO")){
					 q=entityManager.createQuery("from Cmtpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
					 q.setParameter(1, productionDataBean.getInvoiceNo());
					 q.setParameter(2, Integer.parseInt(clientID));
					 List<Cmtpurchaseorder> purchaseList=(ArrayList<Cmtpurchaseorder>)q.getResultList();
					 if(purchaseList.size()>0){
						 id=purchaseList.get(0).getPurchase_ID();
						 Cmtpurchaseorder cutter=entityManager.find(Cmtpurchaseorder.class, id);
						 cutter.setStatus("DEACTIVE");
						 entityManager.merge(cutter);
						 q=entityManager.createQuery("from Cmtorder where purchase_ID=? and status='ACTIVE'");
						 q.setParameter(1, id);
						 List<Cmtorder> list=(ArrayList<Cmtorder>)q.getResultList();
						 	if(list.size()>0){
						 		for(int k=0;k<list.size();k++){
						 			Cmtorder purchase=entityManager.find(Cmtorder.class, list.get(k).getOrder_ID());
						 			purchase.setStatus("DEACTIVE");
						 			entityManager.merge(purchase);
						 			status="Success";
						 		}
						 	}
					 }
					}else if(productionDataBean.getInvoiceNo().startsWith("CMR")){
						q=entityManager.createQuery("from Cmtreceiveorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
						 q.setParameter(1, productionDataBean.getInvoiceNo());
						 q.setParameter(2, Integer.parseInt(clientID));
						 List<Cmtreceiveorder> purchaseList=(ArrayList<Cmtreceiveorder>)q.getResultList();
						 if(purchaseList.size()>0){
							 id=purchaseList.get(0).getReceive_ID();
							 Cmtreceiveorder cutter=entityManager.find(Cmtreceiveorder.class, id);
							 cutter.setStatus("DEACTIVE");
							 entityManager.merge(cutter);
							 q=entityManager.createQuery("from Cmtorder where receive_ID=? and status='ACTIVE'");
							 q.setParameter(1, id);
							 List<Cmtorder> list=(ArrayList<Cmtorder>)q.getResultList();
							 	if(list.size()>0){
							 		for(int k=0;k<list.size();k++){
							 			Cmtorder purchase=entityManager.find(Cmtorder.class, list.get(k).getOrder_ID());
							 			purchase.setStatus("DEACTIVE");
							 			entityManager.merge(purchase);
							 			status="Success";
							 		}
							 	}
						 }
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return status;
		}

		@Override
		public List<ProductionDataBean> getcmtHolding(String personID,
				String clientID, ProductionDataBean productionDataBean) {
			Query q=null;
			List<ProductionDataBean> beanList=null;
			List<Cmtpurchaseorder> purchaselist=null;
			BigDecimal totalAmount=BigDecimal.valueOf(0);
			BigDecimal totalQuantity=BigDecimal.valueOf(0);
			DecimalFormat myFormatter = new DecimalFormat("#,###");
			if(personID!=null && clientID !=null){
				try{
					beanList=new ArrayList<ProductionDataBean>();
					if("ALL".equalsIgnoreCase(productionDataBean.getCmt())){
						q=entityManager.createQuery("from Cmtpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus='HOLDING' ORDER BY purchase_ID DESC");
						q.setParameter(1, clientID);
						purchaselist=(ArrayList<Cmtpurchaseorder>)q.getResultList();
					}else{
						q=entityManager.createQuery("from Cmtpurchaseorder where client_ID=? and cmt=? and status='ACTIVE' and invoiceStatus='HOLDING' ORDER BY purchase_ID DESC");
						q.setParameter(1, clientID);
						q.setParameter(2, productionDataBean.getCmt());
						purchaselist=(ArrayList<Cmtpurchaseorder>)q.getResultList();
					}
					if(purchaselist.size()>0){
						for (int i = 0; i < purchaselist.size(); i++) {
							q=null;
							q=entityManager.createQuery("from Cmtorder where purchase_ID=? ");
							q.setParameter(1, purchaselist.get(i).getPurchase_ID());
							List<Cmtorder> list=(ArrayList<Cmtorder>)q.getResultList();
							if(list.size()>0){
								for(int j=0;j<list.size();j++){
									ProductionDataBean production=new ProductionDataBean();
									production.setModelName(purchaselist.get(i).getModel());
									production.setCmt(purchaselist.get(i).getCmt());
									production.setSeri(list.get(j).getSeri());
									production.setMotive(list.get(j).getMotive());
									production.setOrderDate(purchaselist.get(i).getOrderDate());
									production.setInvoiceNo(purchaselist.get(i).getInvoiceNo());
									production.setValue(list.get(j).getValue());
									production.setQuantity(list.get(j).getQuantity());
									beanList.add(production);
									totalQuantity=totalQuantity.add(new BigDecimal( purchaselist.get(i).getTotalQuantity()));
									productionDataBean.setTotalQuantity(totalQuantity.toString());
									totalAmount=totalAmount.add(new BigDecimal( purchaselist.get(i).getValue()));
									String amount=myFormatter.format(totalAmount);
									productionDataBean.setTotalValue(amount);
								}
							}
							
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return beanList;
		}


		@Override
		public List<String> getPrinterlist(String personID, String clientID) {
			Query q=null;
			List<String> printerlist=null;
			try{
				q=entityManager.createQuery("select printer from Printerpurchaseorder where client_ID=?  and status='ACTIVE'");
				q.setParameter(1, Integer.parseInt(clientID));
				printerlist=(ArrayList<String>)q.getResultList();
				Set<String> dublicate=new HashSet<String>(printerlist);
				printerlist.clear();
				printerlist.addAll(dublicate);
			}catch(Exception e){
				e.printStackTrace();
			}
			return printerlist;
		}
		
		@Override
		public String printerInvoiceNum(String personID,
				ProductionDataBean productionDataBean) {
			Query q=null;String invoiceno="";String printerlistsize="";
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			String purdate = sf.format(productionDataBean.getOrderDate());
			try{
				q=entityManager.createQuery("from Printerpurchaseorder");
				List<Printerpurchaseorder> printerList=(List<Printerpurchaseorder>)q.getResultList();
				printerlistsize=String.valueOf(printerList.size()+1);
				if(printerList.size()==0){
					invoiceno="Po"+purdate+"00"+1;
				}
				if(printerlistsize.length()==1){
					invoiceno="Po"+purdate+"00"+printerlistsize;
				}else if(printerlistsize.length()==2){
					invoiceno="Po"+purdate+"0"+printerlistsize;
				}else{
					invoiceno="Po"+purdate+printerlistsize;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return invoiceno;
		}


		@Override
		public String printerreceiveInvoiceNum(String personID,
				ProductionDataBean productionDataBean) {
			
			Query q=null;String invoiceno="";String printerlistsize="";
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			String purdate = sf.format(productionDataBean.getReceivedate());
			try{
				q=entityManager.createQuery("from Printerreceiveorder");
				List<Printerreceiveorder> printerList=(List<Printerreceiveorder>)q.getResultList();
				printerlistsize=String.valueOf(printerList.size()+1);
				if(printerList.size()==0){
					invoiceno="Pr"+purdate+"00"+1;
				}
				if(printerlistsize.length()==1){
					invoiceno="Pr"+purdate+"00"+printerlistsize;
				}else if(printerlistsize.length()==2){
					invoiceno="Pr"+purdate+"0"+printerlistsize;
				}else{
					invoiceno="Pr"+purdate+printerlistsize;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return invoiceno;
		}


		@Transactional(value="transactionManager")
		@Override
		public String insertPrinterRecOrder(String personID, String clientID,
				ProductionDataBean productionDataBean,List<ProductionDataBean> printerorderlist) {
			System.out.println("inside");
			String status="Fail";
			Query q=null;
			if(personID!=null && clientID!=null){
				try{
					Printerreceiveorder purchase=new Printerreceiveorder();
					purchase.setReceivedate(productionDataBean.getReceivedate());
					purchase.setPrinter(productionDataBean.getPrinter());
					purchase.setCategory(productionDataBean.getCategoryName());
					purchase.setInvoice(productionDataBean.getInvoiceNo());
					purchase.setModel(productionDataBean.getModelName());
					purchase.setTotalquantitry(productionDataBean.getTotalQuantity());
					purchase.setValue(productionDataBean.getTotalValue());
					purchase.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
					purchase.setClient(entityManager.find(Client.class, Integer.parseInt(clientID)));
					purchase.setStatus("ACTIVE");
					purchase.setInvoiceStatus("INVOICE");
					purchase.setCmtStatus("WAITING");
					purchase.setOrderInvoiceno(productionDataBean.getCutterInvoiceno());
					entityManager.persist(purchase);
					q=entityManager.createQuery("from Printerreceiveorder where invoice=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, productionDataBean.getInvoiceNo());
					q.setParameter(2, clientID);
					List<Printerreceiveorder> printerrecepurlist=(List<Printerreceiveorder>)q.getResultList();
					if(printerrecepurlist.size()>0){
					int id=printerrecepurlist.get(0).getPurchase_ID();
					for(int i=0;i<printerorderlist.size();i++){
						try{
						if(printerorderlist.get(i).getSeri()!=null || !printerorderlist.get(i).getSeri().equalsIgnoreCase("") 
								&& printerorderlist.get(i).getQuantity()!=null || !printerorderlist.get(i).getQuantity().equalsIgnoreCase("") ){
							Printerorder receive=new Printerorder();
							receive.setMotive(printerorderlist.get(i).getMotive());
							receive.setPrinterreceiveorder(entityManager.find(Printerreceiveorder.class, id));
							receive.setSer(printerorderlist.get(i).getSeri());
							receive.setQuantity(printerorderlist.get(i).getQuantity());
							receive.setValue(printerorderlist.get(i).getValue());
							receive.setStatus("ACTIVE");
							receive.setOrderstatus("NOT READY");
							entityManager.persist(receive);
							entityManager.flush();
							entityManager.clear();
						}
						}catch(NullPointerException e){
						}
					}
					}
					q=null;
					q=entityManager.createQuery("from Printerpurchaseorder where model=? and client_ID=? and status='ACTIVE' and invoiceStatus='HOLDING'");
					q.setParameter(1, productionDataBean.getModelName());
					q.setParameter(2, clientID);
					List<Printerpurchaseorder> printerList=(ArrayList<Printerpurchaseorder>)q.getResultList();
					if(printerList.size()>0){
						for(int k=0;k<printerList.size();k++){
						Printerpurchaseorder order=entityManager.find(Printerpurchaseorder.class, printerList.get(k).getPurchase_ID());
						order.setInvoiceStatus("RECEIVED");
						entityManager.merge(order);
						entityManager.flush();entityManager.clear();
						status="Success";
						}
					}
					for(int i=0;i<printerorderlist.size();i++){
						if(printerorderlist.get(i).getSeri()!=null || !printerorderlist.get(i).getSeri().equalsIgnoreCase("")){
					q=null;
					q=entityManager.createQuery("from Printerready where model=? and seri=? and status='ACTIVE' and orderStatus='ORDERED'");
					q.setParameter(1, productionDataBean.getModelName());
					q.setParameter(2, printerorderlist.get(i).getSeri());
					List<Printerready> readyList=(ArrayList<Printerready>)q.getResultList();
					if(readyList.size()>0){
						for(int r=0;r<readyList.size();r++){
							Printerready ready=entityManager.find(Printerready.class, readyList.get(r).getPrinterready_ID());
							ready.setOrderStatus("RECEIVED");
							entityManager.merge(ready);
							entityManager.flush();entityManager.clear();
							status="Success";
						}
					}
					}
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return status;
		}

		@Transactional(value="transactionManager")
		@Override
		public String insertPrinterOrder(String personID, String clientID,
				ProductionDataBean productionDataBean,
				List<ProductionDataBean> printerorderlist) {
			String status="Fail";
			Query q=null;
			if(personID!=null && clientID!=null){
				try{
					Printerpurchaseorder purchase=new Printerpurchaseorder();
					purchase.setOrderDate(productionDataBean.getOrderDate());
					purchase.setPrinter(productionDataBean.getPrinter());
					purchase.setCategory(productionDataBean.getCategoryName());
					purchase.setInvoice(productionDataBean.getInvoiceNo());
					purchase.setModel(productionDataBean.getModelName());
					purchase.setTotal_quantity(productionDataBean.getTotalQuantity());
					purchase.setValue(productionDataBean.getTotalValue());
					purchase.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
					purchase.setClient(entityManager.find(Client.class, Integer.parseInt(clientID)));
					purchase.setStatus("ACTIVE");
					purchase.setInvoiceStatus("HOLDING");
					entityManager.persist(purchase);
					q=null;
					q=entityManager.createQuery("from Printerpurchaseorder where invoice=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, productionDataBean.getInvoiceNo());
					q.setParameter(2, clientID);
					List<Printerpurchaseorder> printerrecepurlist=(List<Printerpurchaseorder>)q.getResultList();
					if(printerrecepurlist.size()>0){
						int id=printerrecepurlist.get(0).getPurchase_ID();
						for(int i=0;i<printerorderlist.size();i++){
							try{
							if(printerorderlist.get(i).getSeri()!=null || !printerorderlist.get(i).getSeri().equalsIgnoreCase("") 
								&& printerorderlist.get(i).getQuantity()!=null || !printerorderlist.get(i).getQuantity().equalsIgnoreCase("") ){
								Printerorder receive=new Printerorder();
								receive.setMotive(printerorderlist.get(i).getMotive());
								receive.setPrinterpurchaseorder(entityManager.find(Printerpurchaseorder.class, id));
								receive.setSer(printerorderlist.get(i).getSeri());
								receive.setQuantity(printerorderlist.get(i).getQuantity());
								receive.setValue(printerorderlist.get(i).getValue());
								receive.setStatus("ACTIVE");
								entityManager.persist(receive);
								entityManager.flush();
								entityManager.clear();
							}
							}catch(NullPointerException e){
							}
					}
					}
					for(int i=0;i<printerorderlist.size();i++){
						if(printerorderlist.get(i).getSeri()!=null || !printerorderlist.get(i).getSeri().equalsIgnoreCase("")){
					q=null;
					q=entityManager.createQuery("from Printerready where model=? and seri=? and status='ACTIVE' and orderStatus='WAITING'");
					q.setParameter(1, productionDataBean.getModelName());
					q.setParameter(2, printerorderlist.get(i).getSeri());
					List<Printerready> readyList=(ArrayList<Printerready>)q.getResultList();
					if(readyList.size()>0){
						for(int r=0;r<readyList.size();r++){
							Printerready ready=entityManager.find(Printerready.class, readyList.get(r).getPrinterready_ID());
							ready.setOrderStatus("ORDERED");
							entityManager.merge(ready);
							entityManager.flush();entityManager.clear();
							status="Success";
						}
						}
					}
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return status;
		}


		@Override
		public List<ProductionDataBean> getprinterView(String clientID,
				String personID) {
			List<ProductionDataBean> printerlist=new ArrayList<ProductionDataBean>();
			Query q=null;
			if(personID!=null && clientID!=null){
				try{
					/*printerlist=new ArrayList<ProductionDataBean>();
					q=entityManager.createQuery("from Printerpurchaseorder where client_ID=? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, Integer.parseInt(clientID));
					List<Printerpurchaseorder> list=(List<Printerpurchaseorder>)q.getResultList();
					if(list.size()>0){
						for(Printerpurchaseorder branch:list){
							if(branch.getInvoiceStatus().equalsIgnoreCase("HOLDING")){
								ProductionDataBean printer=new ProductionDataBean();
								printer.setOrderDate(branch.getOrderDate());
								printer.setInvoiceNo(branch.getInvoice());
								printer.setPrinter(branch.getPrinter());
								printer.setCategoryName(branch.getCategory());
								printer.setModelName(branch.getModel());
								printer.setSeri(branch.getQuantityser());
								printer.setTotalQuantity(branch.getTotal_quantity());
								printer.setValue(branch.getValue());
								printer.setInvoiceStatus(branch.getInvoiceStatus());
								printerlist.add(printer);
							}else if(branch.getInvoiceStatus().equalsIgnoreCase("RECEIVED")){
								q=null;
								q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and model=? and status='ACTIVE' ORDER BY purchase_ID DESC");
								q.setParameter(1, clientID);
								q.setParameter(2, branch.getModel());
								List<Printerreceiveorder> receiverList=(ArrayList<Printerreceiveorder>)q.getResultList();
								if(receiverList.size()>0){
									for(Printerreceiveorder receive:receiverList){
										ProductionDataBean pro=new ProductionDataBean();
										pro.setOrderDate(receive.getReceivedate());
										pro.setInvoiceNo(receive.getInvoice());
										pro.setPrinter(receive.getPrinter());
										pro.setModelName(receive.getModel());
										pro.setTotalQuantity(receive.getTotalquantitry());
										pro.setValue(receive.getValue());
										pro.setInvoiceStatus(receive.getInvoiceStatus());
										printerlist.add(pro);
									}
								}
							}
						}				
					}*/
					q=entityManager.createQuery("select model from Printerpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus='HOLDING'");
					q.setParameter(1, clientID);
					List<String> modelList=(ArrayList<String>)q.getResultList();
					Set<String> dublicate=new HashSet<String>(modelList);
					modelList.clear();
					modelList.addAll(dublicate);
					if(modelList.size()>0){
						for(int i=0;i<modelList.size();i++){
							q=null;
							q=entityManager.createQuery("from Printerpurchaseorder where model=? and client_ID=? and status='ACTIVE'");
							q.setParameter(1, modelList.get(i));
							q.setParameter(2, clientID);
							List<Printerpurchaseorder> printerpurchaseList=(ArrayList<Printerpurchaseorder>)q.getResultList();
							if(printerpurchaseList.size()>0){
								for(int p=0;p<printerpurchaseList.size();p++){
									ProductionDataBean printer=new ProductionDataBean();
									printer.setOrderDate(printerpurchaseList.get(p).getOrderDate());
									printer.setInvoiceNo(printerpurchaseList.get(p).getInvoice());
									printer.setPrinter(printerpurchaseList.get(p).getPrinter());
									printer.setModelName(printerpurchaseList.get(p).getModel());
									printer.setSeri(printerpurchaseList.get(p).getQuantityser());
									printer.setTotalQuantity(printerpurchaseList.get(p).getTotal_quantity());
									printer.setValue(printerpurchaseList.get(p).getValue());
									printer.setInvoiceStatus(printerpurchaseList.get(p).getInvoiceStatus());
									printerlist.add(printer);
								}
							}
						}
					}
					q=null;
					q=entityManager.createQuery("select model from Printerpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus='RECEIVED'");
					q.setParameter(1, clientID);
					List<String> modelnameList=(ArrayList<String>)q.getResultList();
					Set<String> dublicateList=new HashSet<String>(modelnameList);
					modelnameList.clear();
					modelnameList.addAll(dublicateList);
					if(modelnameList.size()>0){
						for(int j=0;j<modelnameList.size();j++){
							q=null;
							q=entityManager.createQuery("from Printerreceiveorder where model=? and client_ID=? and status='ACTIVE'");
							q.setParameter(1, modelnameList.get(j));
							q.setParameter(2, clientID);
							List<Printerreceiveorder> receiveList=(ArrayList<Printerreceiveorder>)q.getResultList();
							for(int r=0;r<receiveList.size();r++){
								ProductionDataBean pro=new ProductionDataBean();
								pro.setOrderDate(receiveList.get(r).getReceivedate());
								pro.setInvoiceNo(receiveList.get(r).getInvoice());
								pro.setPrinter(receiveList.get(r).getPrinter());
								pro.setModelName(receiveList.get(r).getModel());
								pro.setTotalQuantity(receiveList.get(r).getTotalquantitry());
								pro.setValue(receiveList.get(r).getValue());
								pro.setInvoiceStatus(receiveList.get(r).getInvoiceStatus());
								printerlist.add(pro);
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return printerlist;
		}


		@Override
		public void getprinterpurchaseView(String personID, String clientID,
				ProductionDataBean productionDataBean) {
			Query q=null;
			int id=0;
			List<ProductionDataBean> printerList=new ArrayList<ProductionDataBean>();
			if(personID!=null && clientID!=null){
				try{
					if(productionDataBean.getInvoiceNo().startsWith("Po")){
					q=entityManager.createQuery("from Printerpurchaseorder where client_ID=? and invoice=? and status='ACTIVE'");
					q.setParameter(1, clientID);
					q.setParameter(2, productionDataBean.getInvoiceNo());
					List<Printerpurchaseorder> list=(List<Printerpurchaseorder>)q.getResultList();
					if(list.size()>0){
						id=list.get(0).getPurchase_ID();
						productionDataBean.setOrderDate(list.get(0).getOrderDate());
						productionDataBean.setPrinter(list.get(0).getPrinter());
						productionDataBean.setModelName(list.get(0).getModel());
						productionDataBean.setTotalQuantity(list.get(0).getTotal_quantity());
						productionDataBean.setTotalValue(list.get(0).getValue());
						q=null;
						 q=entityManager.createQuery("from Printerorder where printerpurchase_id=? and status='ACTIVE'");
						 q.setParameter(1, id);
						 List<Printerorder> orderlist=(ArrayList<Printerorder>)q.getResultList();
						 if(list.size()>0){
							 for(int i=0;i<orderlist.size();i++){
								 ProductionDataBean production=new ProductionDataBean();
								 production.setSerialNo(String.valueOf(i+1));
								 production.setSeri(orderlist.get(i).getSer());
								 production.setQuantity(orderlist.get(i).getQuantity());
								 production.setMotive(orderlist.get(i).getMotive());
								 production.setValue(orderlist.get(i).getValue());
								 productionDataBean.setSeri(orderlist.get(i).getSer());
								 printerList.add(production);
								 productionDataBean.setOrderQuantitylist(printerList);
							 }
						 }
					}
					}else {
						q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and invoice=? and status='ACTIVE'");
						q.setParameter(1, clientID);
						q.setParameter(2, productionDataBean.getInvoiceNo());
						List<Printerreceiveorder> list=(List<Printerreceiveorder>)q.getResultList();
						if(list.size()>0){
							id=list.get(0).getPurchase_ID();
							productionDataBean.setOrderDate(list.get(0).getReceivedate());
							productionDataBean.setPrinter(list.get(0).getPrinter());
							productionDataBean.setModelName(list.get(0).getModel());
							productionDataBean.setTotalQuantity(list.get(0).getTotalquantitry());
							productionDataBean.setTotalValue(list.get(0).getValue());
							q=null;
							 q=entityManager.createQuery("from Printerorder where purchase_id=? and status='ACTIVE'");
							 q.setParameter(1, id);
							 List<Printerorder> orderlist=(ArrayList<Printerorder>)q.getResultList();
							 if(list.size()>0){
								 for(int i=0;i<orderlist.size();i++){
									 ProductionDataBean production=new ProductionDataBean();
									 production.setSerialNo(String.valueOf(i+1));
									 production.setSeri(orderlist.get(i).getSer());
									 production.setQuantity(orderlist.get(i).getQuantity());
									 production.setMotive(orderlist.get(i).getMotive());
									 production.setValue(orderlist.get(i).getValue());
									 printerList.add(production);
									 productionDataBean.setOrderQuantitylist(printerList);
								 }
							 }
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		
		}

		@Transactional(value="transactionManager")
		@Override
		public String printerpurchaseUpdate(String personID, String clientID,
				ProductionDataBean productionDataBean) {
					Query q=null;
					String status="Fail";
					int id=0;
					if(personID!=null && clientID!=null){
						try{
							if(productionDataBean.getInvoiceNo().startsWith("Po"))
							 q=entityManager.createQuery("from Printerpurchaseorder where invoice=? and client_ID=? and status='ACTIVE'");
							 q.setParameter(1, productionDataBean.getInvoiceNo());
							 q.setParameter(2, Integer.parseInt(clientID));
							 List<Printerpurchaseorder> purchaseList=(ArrayList<Printerpurchaseorder>)q.getResultList();
							 if(purchaseList.size()>0){
								 id=purchaseList.get(0).getPurchase_ID();
								 Printerpurchaseorder print=entityManager.find(Printerpurchaseorder.class, id);
								 print.setPrinter(productionDataBean.getPrinter());
								 print.setModel(productionDataBean.getModelName());
								 print.setTotal_quantity(productionDataBean.getTotalQuantity());
								 print.setValue(productionDataBean.getTotalValue());
								 entityManager.merge(print);
								 q=entityManager.createQuery("from Printerorder where printerpurchase_id=? and status='ACTIVE'");
								 q.setParameter(1, id);
								 List<Printerorder> list=(ArrayList<Printerorder>)q.getResultList();
								 	if(list.size()>0){
										for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
											Printerorder purchase=entityManager.find(Printerorder.class, list.get(i).getPoredrId());
												purchase.setSer(productionDataBean.getOrderQuantitylist().get(i).getSeri());
												purchase.setQuantity(productionDataBean.getOrderQuantitylist().get(i).getQuantity());
												purchase.setMotive(productionDataBean.getOrderQuantitylist().get(i).getMotive());
												purchase.setValue(productionDataBean.getOrderQuantitylist().get(i).getValue());
												entityManager.merge(purchase);
												entityManager.flush();
												entityManager.clear();
												status="Success";
										}
									}
							 }else{
								 q=entityManager.createQuery("from Printerreceiveorder where invoice=? and client_ID=? and status='ACTIVE'");
								 q.setParameter(1, productionDataBean.getInvoiceNo());
								 q.setParameter(2, Integer.parseInt(clientID));
								 List<Printerreceiveorder> receiveList=(ArrayList<Printerreceiveorder>)q.getResultList();
								 if(receiveList.size()>0){
									 id=receiveList.get(0).getPurchase_ID();
									 Printerreceiveorder print=entityManager.find(Printerreceiveorder.class, id);
									 print.setPrinter(productionDataBean.getPrinter());
									 print.setModel(productionDataBean.getModelName());
									 print.setTotalquantitry(productionDataBean.getTotalQuantity());
									 print.setValue(productionDataBean.getTotalValue());
									 entityManager.merge(print);
									 q=entityManager.createQuery("from Printerorder where purchase_id=? and status='ACTIVE'");
									 q.setParameter(1, id);
									 List<Printerorder> list=(ArrayList<Printerorder>)q.getResultList();
									 	if(list.size()>0){
											for(int i=0;i<productionDataBean.getOrderQuantitylist().size();i++){
												Printerorder purchase=entityManager.find(Printerorder.class, list.get(i).getPoredrId());
													purchase.setSer(productionDataBean.getOrderQuantitylist().get(i).getSeri());
													purchase.setQuantity(productionDataBean.getOrderQuantitylist().get(i).getQuantity());
													purchase.setMotive(productionDataBean.getOrderQuantitylist().get(i).getMotive());
													purchase.setValue(productionDataBean.getOrderQuantitylist().get(i).getValue());
													entityManager.merge(purchase);
													entityManager.flush();
													entityManager.clear();
													status="Success";
											}
										}
								 }
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
			
					return status;
		}

	
	
@Transactional(value="transactionManager")
@Override
public String printerpurchaseDelete(String personID, String clientID,
		ProductionDataBean productionDataBean) {
	Query q=null;
	String status="Fail";
	int id=0;
	if(personID!=null && clientID!=null){
		try{
			if(productionDataBean.getInvoiceNo().startsWith("Po")){
			 q=entityManager.createQuery("from Printerpurchaseorder where invoice=? and client_ID=? and status='ACTIVE'");
			 q.setParameter(1, productionDataBean.getInvoiceNo());
			 q.setParameter(2, Integer.parseInt(clientID));
			 List<Printerpurchaseorder> purchaseList=(ArrayList<Printerpurchaseorder>)q.getResultList();
			 if(purchaseList.size()>0){
				 id=purchaseList.get(0).getPurchase_ID();
				 Printerpurchaseorder cutter=entityManager.find(Printerpurchaseorder.class, id);
				 cutter.setStatus("DEACTIVE");
				 entityManager.merge(cutter);
				 q=entityManager.createQuery("from Printerorder where printerpurchase_ID=? and status='ACTIVE'");
				 q.setParameter(1, id);
				 List<Printerorder> list=(ArrayList<Printerorder>)q.getResultList();
				 	if(list.size()>0){
				 		for(int k=0;k<list.size();k++){
				 			Printerorder purchase=entityManager.find(Printerorder.class, list.get(k).getPoredrId());
				 			purchase.setStatus("DEACTIVE");
				 			entityManager.merge(purchase);
				 			status="Success";
				 		}
				 	}
			 }
			}else{
				 q=entityManager.createQuery("from Printerreceiveorder where invoice=? and client_ID=? and status='ACTIVE'");
				 q.setParameter(1, productionDataBean.getInvoiceNo());
				 q.setParameter(2, Integer.parseInt(clientID));
				 List<Printerreceiveorder> purchaseList=(ArrayList<Printerreceiveorder>)q.getResultList();
				 if(purchaseList.size()>0){
					 id=purchaseList.get(0).getPurchase_ID();
					 Printerreceiveorder cutter=entityManager.find(Printerreceiveorder.class, id);
					 cutter.setStatus("DEACTIVE");
					 entityManager.merge(cutter);
					 q=entityManager.createQuery("from Printerorder where purchase_id=? and status='ACTIVE'");
					 q.setParameter(1, id);
					 List<Printerorder> list=(ArrayList<Printerorder>)q.getResultList();
					 	if(list.size()>0){
					 		for(int k=0;k<list.size();k++){
					 			Printerorder purchase=entityManager.find(Printerorder.class, list.get(k).getPoredrId());
					 			purchase.setStatus("DEACTIVE");
					 			entityManager.merge(purchase);
					 			status="Success";
					 		}
					 	}
				 }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return status;
}



@Override
public List<ProductionDataBean> getprinterHolding(String personID,
		String clientID, ProductionDataBean productionDataBean) {
	Query q=null;
	List<ProductionDataBean> beanList=null;
	List<Printerpurchaseorder> purchaselist=null;
	BigDecimal totalAmount=BigDecimal.valueOf(0);
	BigDecimal totalQuantity=BigDecimal.valueOf(0);
	DecimalFormat myFormatter = new DecimalFormat("#,###");
	if(personID!=null && clientID !=null){
		try{
			beanList=new ArrayList<ProductionDataBean>();
			if("ALL".equalsIgnoreCase(productionDataBean.getPrinter())){
				q=entityManager.createQuery("from Printerpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus='HOLDING' ORDER BY purchase_ID DESC");
				q.setParameter(1, clientID);
				purchaselist=(ArrayList<Printerpurchaseorder>)q.getResultList();
			}else{
				q=entityManager.createQuery("from Printerpurchaseorder where client_ID=? and printer=? and status='ACTIVE' and invoiceStatus='HOLDING' ORDER BY purchase_ID DESC");
				q.setParameter(1, clientID);
				q.setParameter(2, productionDataBean.getPrinter());
				purchaselist=(ArrayList<Printerpurchaseorder>)q.getResultList();
			}
			if(purchaselist.size()>0){
				for (int i = 0; i < purchaselist.size(); i++) {
					q=null;
					q=entityManager.createQuery("from Printerorder where printerpurchase_id=?");
					q.setParameter(1, purchaselist.get(i).getPurchase_ID());
					List<Printerorder> printer=(List<Printerorder>)q.getResultList();
					if(printer.size()>0){
						for (int j = 0; j < printer.size(); j++) {
							ProductionDataBean production=new ProductionDataBean();
							production.setInvoiceNo(purchaselist.get(i).getInvoice());
							production.setModelName(purchaselist.get(i).getModel());
							production.setMotive(printer.get(j).getMotive());
							production.setSeri(printer.get(j).getSer());
							production.setPrinter(purchaselist.get(i).getPrinter());
							production.setOrderDate(purchaselist.get(i).getOrderDate());
							production.setQuantity(printer.get(j).getQuantity());
							production.setValue(printer.get(j).getValue());
							beanList.add(production);
							totalQuantity=totalQuantity.add(new BigDecimal( purchaselist.get(i).getTotal_quantity()));
							productionDataBean.setTotalQuantity(totalQuantity.toString());
							totalAmount=totalAmount.add(new BigDecimal( purchaselist.get(i).getValue()));
							String amount=myFormatter.format(totalAmount);
							productionDataBean.setTotalAmount(amount);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}          
	}
	return beanList;
}

@Override
public List<ProductionDataBean> getprinterView(String personID,
		String clientID, ProductionDataBean productionDataBean) {
	Query q=null;
	List<ProductionDataBean> beanList=new ArrayList<ProductionDataBean>();
	if (personID!=null && clientID!=null) {
		try{
			q=entityManager.createQuery("from Printerpurchaseorder where client_ID=?  and printer=? and status='ACTIVE' ORDER BY purchase_ID DESC");
			q.setParameter(1, clientID);
			q.setParameter(2, productionDataBean.getPrinter());
			List<Printerpurchaseorder> purchaselist=(ArrayList<Printerpurchaseorder>)q.getResultList();
			if(purchaselist.size()>0){
				for (int i = 0; i < purchaselist.size(); i++) {
					ProductionDataBean production=new ProductionDataBean();
					production.setOrderDate(purchaselist.get(i).getOrderDate());
					production.setInvoiceNo(purchaselist.get(i).getInvoice());
					production.setCmt(purchaselist.get(i).getPrinter());
					production.setCategoryName(purchaselist.get(i).getCategory());
					production.setModelName(purchaselist.get(i).getModel());
					production.setModelSeri(purchaselist.get(i).getModel());
					production.setTotalseri(purchaselist.get(i).getQuantityser());
					production.setTotalQuantity(purchaselist.get(i).getTotal_quantity());
					production.setTotalValue(purchaselist.get(i).getValue());
					beanList.add(production);
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return beanList;
}


@Override
public List<ProductionDataBean> getprinterSearch(String personID,
		String clientID, ProductionDataBean productionDataBean) {
	Query q=null;
	List<ProductionDataBean> beanList=new ArrayList<ProductionDataBean>();
	if (personID!=null && clientID!=null) {
		try{
			q=entityManager.createQuery("from Printerpurchaseorder where client_ID=? and printer=? and status='ACTIVE' ORDER BY purchase_ID DESC");
			q.setParameter(1, clientID);
			q.setParameter(2, productionDataBean.getPrinter());
			List<Printerpurchaseorder> purchaselist=(ArrayList<Printerpurchaseorder>)q.getResultList();
			if(purchaselist.size()>0){
				for (int i = 0; i < purchaselist.size(); i++) {
					if(purchaselist.get(i).getInvoiceStatus().equalsIgnoreCase("HOLDING")){
					ProductionDataBean production=new ProductionDataBean();
					production.setOrderDate(purchaselist.get(i).getOrderDate());
					production.setInvoiceNo(purchaselist.get(i).getInvoice());
					production.setPrinter(purchaselist.get(i).getPrinter());
					production.setModelName(purchaselist.get(i).getModel());
					production.setTotalQuantity(purchaselist.get(i).getTotal_quantity());
					production.setValue(purchaselist.get(i).getValue());
					production.setInvoiceStatus(purchaselist.get(i).getInvoiceStatus());
					beanList.add(production);
					
					}else if(purchaselist.get(i).getInvoiceStatus().equalsIgnoreCase("INVOICE")){
						q=null;
						q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and model=? and status='ACTIVE' ORDER BY purchase_ID DESC");
						q.setParameter(1, clientID);
						q.setParameter(2, purchaselist.get(i).getModel());
						List<Printerreceiveorder> receiverList=(ArrayList<Printerreceiveorder>)q.getResultList();
						if(receiverList.size()>0){	
							for(Printerreceiveorder receive:receiverList){
								ProductionDataBean pro=new ProductionDataBean();
								pro.setOrderDate(receive.getReceivedate());
								pro.setInvoiceNo(receive.getInvoice());
								pro.setPrinter(receive.getPrinter());
								pro.setModelName(receive.getModel());
								pro.setTotalQuantity(receive.getTotalquantitry());
								pro.setValue(receive.getValue());
								pro.setInvoiceStatus(receive.getInvoiceStatus());
								beanList.add(pro);
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return beanList;
}


@Override
public List<ProductionDataBean> vendorDetails(String personID, String clientID,
		ProductionDataBean productionDataBean) {
	Query q=null;
	List<ProductionDataBean> productionList=null;
	if(personID!=null && clientID!=null){
	try{
		productionList=new ArrayList<ProductionDataBean>();
		q=entityManager.createQuery("from Vendor where divisionVendor=? and client_ID=? and status='ACTIVE' ORDER BY vendorId DESC");
		q.setParameter(1, productionDataBean.getDivisionVendor());
		q.setParameter(2, clientID);
		List<Vendor> vendorList=(ArrayList<Vendor>)q.getResultList();
		if(vendorList.size()>0){
			for(int j=0;j<vendorList.size();j++){
				ProductionDataBean prod=new ProductionDataBean();
				prod.setDivisionVendor(vendorList.get(j).getDivisionVendor());
				prod.setVendorName(vendorList.get(j).getVendorName());
				prod.setVendorAddress(vendorList.get(j).getVendorAddress());
				prod.setVendorHpnumber1(vendorList.get(j).getVendor_Hpnumber1());
				prod.setVendorHpnumber2(vendorList.get(j).getVendor_Hpnumber2());
				productionList.add(prod);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	return productionList;
}

@Override
public String generateItemsInvoice(String personID,SupplyDataBean supplyDataBean) {
	Query q=null;String invoiceno="";String listsize="";String str="";
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	String purdate = sf.format(supplyDataBean.getTodayDate());
	try{
		if(supplyDataBean.getItemStatus().equalsIgnoreCase("CHEMISTRY ITEM")){
			str="CH";
		}else if(supplyDataBean.getItemStatus().equalsIgnoreCase("ACCESSORIS ITEM")){
			str="AC";
		}else if(supplyDataBean.getItemStatus().equalsIgnoreCase("HANGTAG ITEM")){
			str="HT";
		}else if(supplyDataBean.getItemStatus().equalsIgnoreCase("LABEL ITEM")){
			str="LB";
		}else if(supplyDataBean.getItemStatus().equalsIgnoreCase("PLASTIC ITEM")){
			str="PL";
		}
		q=entityManager.createQuery("from PursalesItem where orderDate=? and itemStatus=? and person_ID=?");
		q.setParameter(1, supplyDataBean.getTodayDate());
		q.setParameter(2, supplyDataBean.getItemStatus());
		q.setParameter(3, personID);
		List<PursalesItem> itemList=(List<PursalesItem>)q.getResultList();
		listsize=String.valueOf(itemList.size()+1);
		if(itemList.size()>0){
			if(itemList.size()==0){
				invoiceno=str+purdate+"00"+1;
			}
			if(listsize.length()==1){
				invoiceno=str+purdate+"00"+listsize;
			}else if(listsize.length()==2){
				invoiceno=str+purdate+"0"+listsize;
			}else{
				invoiceno=str+purdate+listsize;
			}
		}else{
			if(itemList.size()==0){
				invoiceno=str+purdate+"00"+1;
			}
			if(listsize.length()==1){
				invoiceno=str+purdate+"00"+listsize;
			}else if(listsize.length()==2){
				invoiceno=str+purdate+"0"+listsize;
			}else{
				invoiceno=str+purdate+listsize;
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return invoiceno;
}

@Override
public List<String> getitemNameList(String personID,SupplyDataBean supplyDataBean) {
	Query q=null;List<String> itemNameList=new ArrayList<String>();
	try{
		q=entityManager.createQuery("select itemName from ItemTable where person_ID=? and itemStatus=? and status='ACTIVE'");
		q.setParameter(1, personID);
		q.setParameter(2, supplyDataBean.getItemStatus());
		itemNameList=(List<String>)q.getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
	return itemNameList;
}

@Override
public void getItemPrice(String personID, SupplyDataBean supplyDataBean) {
	Query q=null;
	try{
		q=entityManager.createQuery("from ItemTable where person_ID=? and itemName=? and itemStatus=?");
		q.setParameter(1, personID);
		q.setParameter(2, supplyDataBean.getItemName());
		q.setParameter(3, supplyDataBean.getItemStatus());
		List<ItemTable> itemList=(List<ItemTable>)q.getResultList();
		if(itemList.size()>0){
			q=null;
			q=entityManager.createQuery("from Stock where item_id=? and warehouseStatus=? and person_ID=? and cmtStatus='Raw' and status='Active'");
			q.setParameter(1, itemList.get(0).getItemId());
			q.setParameter(2, "Main Warehouse");
			q.setParameter(3, Integer.parseInt(personID));
			List<Stock> stock=(List<Stock>)q.getResultList();
			if(stock.size()>0){
				supplyDataBean.setQuantity(stock.get(0).getStockIn());					
			}else{
				supplyDataBean.setQuantity("No stock");		
			}
			supplyDataBean.setItemBuyPrice(itemList.get(0).getBuyPrice());
			supplyDataBean.setItemSellPrice(itemList.get(0).getSellPrice());
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

@Override
@Transactional(value="transactionManager")
public String savePurchaseSalesItems(String personID, String clientID,SupplyDataBean supplyDataBean) {
	Query q=null;int p_id=0;String status="Fail";
	try{
		int personid=Integer.parseInt(personID);
		int clientid=Integer.parseInt(clientID);
		PursalesItem puritem = new PursalesItem();
		puritem.setPerson(entityManager.find(Person.class, personid));
		puritem.setClient(entityManager.find(Client.class, clientid));
		puritem.setOrderDate(supplyDataBean.getTodayDate());
		if(supplyDataBean.getStatus2().equals("PURCHASE")){
			puritem.setSupplierName(supplyDataBean.getSupplierName());
			puritem.setVendorName("");
			puritem.setDueDate(null);
		}
		else if(supplyDataBean.getStatus2().equals("SALES")){
			puritem.setVendorName(supplyDataBean.getVendorName());
			puritem.setDueDate(supplyDataBean.getDueDate());
			puritem.setSupplierName("");
		}
		puritem.setInvoiceNumber(supplyDataBean.getFabricInvoiceNumber());
		puritem.setTotalQuantity(supplyDataBean.getQuantityTotal());
		puritem.setTotalPrice(supplyDataBean.getTotalAmount().replace(",", ""));
		puritem.setStatus("INSERTED");
		puritem.setInvoiceStatus("Insert");
		puritem.setStatus2(supplyDataBean.getStatus2());
		puritem.setPaymentStatus(supplyDataBean.getPaymentStatus());
		puritem.setItemStatus(supplyDataBean.getItemStatus());
		puritem.setStockStatus(supplyDataBean.getStockStatus());
		entityManager.persist(puritem);
		entityManager.flush();
		entityManager.clear();
		
		q=entityManager.createQuery("from PursalesItem");
		List<PursalesItem> pursaleList=(List<PursalesItem>)q.getResultList();
		if(pursaleList.size()>0){
			p_id=pursaleList.get(pursaleList.size()-1).getPursalesitemId();
			supplyDataBean.setFabricId(p_id);
			for (int i = 0; i < supplyDataBean.getFabricList().size(); i++) {
					q=null;
					q=entityManager.createQuery("from ItemTable where person_ID=? and itemName=? and itemStatus=?");
					q.setParameter(1, personID);
					q.setParameter(2, supplyDataBean.getFabricList().get(i).getItemName());
					q.setParameter(3, supplyDataBean.getFabricList().get(i).getItemStatus());
					List<ItemTable> itemlist=(List<ItemTable>)q.getResultList();
					if(itemlist.size()>0){
						for (int j = 0; j < itemlist.size(); j++) {
							if(supplyDataBean.getStatus2().equalsIgnoreCase("SALES")){
								System.out.println("inside if-----");
								q=null;
								q=entityManager.createQuery("from Stock where item_id=? and warehouseStatus=? and person_ID=? and cmtStatus='Raw' and status='Active'");
								q.setParameter(1, itemlist.get(j).getItemId());
								q.setParameter(2, "Main Warehouse");
								q.setParameter(3, Integer.parseInt(personID));
								List<Stock> stocklist=(List<Stock>)q.getResultList();
								if(stocklist.size()>0){
									System.out.println("stoc id"+stocklist.get(0).getStock_ID());
									Stock stock=entityManager.find(Stock.class, stocklist.get(0).getStock_ID());
									stock.setStockIn(new BigDecimal(stocklist.get(0).getStockIn()).
											subtract(new BigDecimal(supplyDataBean.getFabricList().get(i).getItemQuantity())).toString());
									stock.setStockOut(new BigDecimal(stocklist.get(0).getStockOut()).
											add(new BigDecimal(supplyDataBean.getFabricList().get(i).getItemQuantity())).toString());
									entityManager.merge(stock);
								}
							}
							PursalesdetailItem purdetailitem=new PursalesdetailItem();
							purdetailitem.setPursalesItem(entityManager.find(PursalesItem.class, p_id));
							purdetailitem.setItemId(entityManager.find(ItemTable.class, itemlist.get(j).getItemId()));
							purdetailitem.setItemName(supplyDataBean.getFabricList().get(i).getItemName());
							purdetailitem.setQuantity(supplyDataBean.getFabricList().get(i).getItemQuantity());
							if(supplyDataBean.getStatus2().equalsIgnoreCase("PURCHASE")){
								purdetailitem.setPrice(supplyDataBean.getFabricList().get(i).getItemBuyPrice());
							}else if(supplyDataBean.getStatus2().equals("SALES")){
								purdetailitem.setPrice(supplyDataBean.getFabricList().get(i).getItemSellPrice());
							}
							purdetailitem.setTotalPrice(supplyDataBean.getFabricList().get(i).getTotalPrice());
							purdetailitem.setStatus("INSERTED");
							purdetailitem.setStatus2(supplyDataBean.getStatus2());
							purdetailitem.setPaymentStatus(supplyDataBean.getPaymentStatus());
							purdetailitem.setStockStatus(supplyDataBean.getStockStatus());
							entityManager.persist(purdetailitem);
							entityManager.flush();
							entityManager.clear();
							status="Success";
						}
					}
			}
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return status;
}

@Override
public List<String> getvendornamelist(String personID,String str) {
	Query q=null;List<String> vendorList=new ArrayList<String>();
	try{
		q=entityManager.createQuery("select vendorName from Vendor where person_ID=? and divisionVendor=? and status='ACTIVE'");
		q.setParameter(1, personID);
		q.setParameter(2, str);
		vendorList=(List<String>)q.getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
	return vendorList;
}

@Override
public List<String> getsalesitemNameList(String personID,SupplyDataBean supplyDataBean) {
	Query q=null;List<String> itemList=new ArrayList<String>();
	try{
		q=entityManager.createQuery("from PursalesItem where person_ID=? and status=? and status2=? and itemStatus=? and stockStatus=?");
		q.setParameter(1, personID);
		q.setParameter(2, "INSERTED");
		q.setParameter(3, "PURCHASE");
		q.setParameter(4, supplyDataBean.getItemStatus());
		q.setParameter(5, "ADDED TO STOCK");
		List<PursalesItem> pursaleslist=(List<PursalesItem>)q.getResultList();
		if(pursaleslist.size()>0){
			for (int i = 0; i < pursaleslist.size(); i++) {
				q=null;
				q=entityManager.createQuery("from PursalesdetailItem where pursalesitem_id=?");
				q.setParameter(1, pursaleslist.get(i).getPursalesitemId());
				List<PursalesdetailItem> purdetailList=(List<PursalesdetailItem>)q.getResultList();
				if(purdetailList.size()>0){
					for (int j = 0; j < purdetailList.size(); j++) {
						q=null;
						q=entityManager.createQuery("from Stock where item_id=? and warehouseStatus=? and person_ID=? and status='Active' and cmtStatus='Raw' and stockIn!='0'");
						q.setParameter(1, purdetailList.get(j).getItemId());
						q.setParameter(2, "Main Warehouse");
						q.setParameter(3, Integer.parseInt(personID));
						List<Stock> stock=(List<Stock>)q.getResultList();
						if(stock.size()>0){
							itemList.add(stock.get(0).getItemName());
						}
					}
				}
			}
		}
		HashSet<String> hasList=new HashSet<String>(itemList);
		itemList.clear();
		itemList.addAll(hasList);
	}catch(Exception e){
		e.printStackTrace();
	}
	return itemList;
}

@Override
public List<SupplyDataBean> getitemtableview(String personID,SupplyDataBean supplyDataBean) {
	Query q=null;List<SupplyDataBean> pursaleslist=new ArrayList<SupplyDataBean>();
	Calendar cal=Calendar.getInstance();
	try{
		cal.add(Calendar.DATE, -30);
	    Date todate1 = cal.getTime();    
	    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' ORDER BY orderDate DESC");
		q.setParameter(1, personID);
		q.setParameter(2, supplyDataBean.getItemStatus());
		q.setParameter(3, todate1);
		q.setParameter(4, date);
		List<PursalesItem> pursalesList=(List<PursalesItem>)q.getResultList();
		if(pursalesList.size()>0){
			for (int i = 0; i < pursalesList.size(); i++) {
				SupplyDataBean sup=new SupplyDataBean();
				sup.setSerialNo(String.valueOf(i+1));
				sup.setTodayDate(pursalesList.get(i).getOrderDate());
				sup.setFabricInvoiceNumber(pursalesList.get(i).getInvoiceNumber());
				sup.setSupplierName(pursalesList.get(i).getSupplierName());
				sup.setVendorName(pursalesList.get(i).getVendorName());
				if(pursalesList.get(i).getStatus2().equals("PURCHASE")){
					sup.setTotalPrice(pursalesList.get(i).getTotalPrice());
					sup.setTotalAmount("");
				}else{
					sup.setTotalAmount(pursalesList.get(i).getTotalPrice());
					sup.setTotalPrice("");
				}
				sup.setInvoiceStatus(pursalesList.get(i).getInvoiceStatus());
				sup.setPaymentStatus(pursalesList.get(i).getPaymentStatus());
				sup.setFabricId(pursalesList.get(i).getPursalesitemId());
				sup.setStatus2(pursalesList.get(i).getStatus2());
				sup.setItemStatus(pursalesList.get(i).getItemStatus());
				pursaleslist.add(sup);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return pursaleslist;
}

@Override
public String viewpurchasesaleitems(String personID,SupplyDataBean supplyDataBean) {
	Query q=null;List<SupplyDataBean> domainlist=new ArrayList<SupplyDataBean>();
	try{
		q=entityManager.createQuery("from PursalesItem where pursalesitem_id=? and person_ID=?");
		q.setParameter(1, supplyDataBean.getFabricId());
		q.setParameter(2, personID);
		List<PursalesItem> pursaleList=(List<PursalesItem>)q.getResultList();
		if(pursaleList.size()>0){
			supplyDataBean.setTodayDate(pursaleList.get(0).getOrderDate());
			supplyDataBean.setSupplierName(pursaleList.get(0).getSupplierName());
			supplyDataBean.setVendorName(pursaleList.get(0).getVendorName());
			supplyDataBean.setFabricInvoiceNumber(pursaleList.get(0).getInvoiceNumber());
			supplyDataBean.setDueDate(pursaleList.get(0).getDueDate());
			supplyDataBean.setQuantityTotal(pursaleList.get(0).getTotalQuantity());
			supplyDataBean.setTotalAmount(pursaleList.get(0).getTotalPrice());
			supplyDataBean.setTotalPrice(pursaleList.get(0).getTotalPrice());
			supplyDataBean.setStatus2(pursaleList.get(0).getStatus2());
			q=null;
			q=entityManager.createQuery("from PursalesdetailItem where pursalesitem_id=?");
			q.setParameter(1, pursaleList.get(0).getPursalesitemId());
			List<PursalesdetailItem> pursaledetailList=(List<PursalesdetailItem>)q.getResultList();
			if(pursaledetailList.size()>0){
				for (int i = 0; i < pursaledetailList.size(); i++) {
					SupplyDataBean sup=new SupplyDataBean();
					sup.setSerialNo(String.valueOf(i+1));
					sup.setItemName(pursaledetailList.get(i).getItemName());
					sup.setItemQuantity(pursaledetailList.get(i).getQuantity());
					sup.setItemQuantity1(pursaledetailList.get(i).getQuantity());
					sup.setItemBuyPrice(pursaledetailList.get(i).getPrice());
					sup.setTotalPrice(pursaledetailList.get(i).getTotalPrice());
					sup.setItemStatus(pursaledetailList.get(i).getPursalesItem().getItemStatus());
					domainlist.add(sup);
					supplyDataBean.setFabricList(domainlist);
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}


@Override
@Transactional(value="transactionManager")
public String updatepurchasesalesitems(String personID,String clientID,SupplyDataBean supplyDataBean) {
		String status="Fail";Query q=null;
		try{
			int personid=Integer.parseInt(personID);
			int clientid=Integer.parseInt(clientID);
			PursalesItem puritem =entityManager.find(PursalesItem.class, supplyDataBean.getFabricId());
			puritem.setPerson(entityManager.find(Person.class, personid));
			puritem.setClient(entityManager.find(Client.class, clientid));
			puritem.setOrderDate(supplyDataBean.getTodayDate());
			if(supplyDataBean.getStatus2().equals("PURCHASE")){
				puritem.setSupplierName(supplyDataBean.getSupplierName());
				puritem.setVendorName("");
				puritem.setDueDate(null);
			}
			else{
				puritem.setVendorName(supplyDataBean.getVendorName());
				puritem.setDueDate(supplyDataBean.getDueDate());
				puritem.setSupplierName("");
			}
			puritem.setInvoiceNumber(supplyDataBean.getFabricInvoiceNumber());
			puritem.setTotalQuantity(supplyDataBean.getQuantityTotal());
			puritem.setTotalPrice(supplyDataBean.getTotalAmount().replace(",", ""));
			puritem.setStatus("INSERTED");
			puritem.setStatus2(supplyDataBean.getStatus2());
			puritem.setPaymentStatus(supplyDataBean.getPaymentStatus());
			puritem.setItemStatus(supplyDataBean.getItemStatus());
			puritem.setStockStatus(supplyDataBean.getStockStatus());
			entityManager.merge(puritem);
			q=entityManager.createQuery("from PursalesdetailItem where pursalesitem_id=?");
			q.setParameter(1, supplyDataBean.getFabricId());
			List<PursalesdetailItem> pursalesdetaillist=(List<PursalesdetailItem>)q.getResultList();
			if(pursalesdetaillist.size()>0){
				for (int i = 0; i < supplyDataBean.getFabricList().size(); i++) {
					if(supplyDataBean.getStatus2().equalsIgnoreCase("SALES")){
						q=null;
						q=entityManager.createQuery("from ItemTable where person_ID=? and itemName=? and itemStatus=?");
						q.setParameter(1, personID);
						q.setParameter(2, supplyDataBean.getFabricList().get(i).getItemName());
						q.setParameter(3, supplyDataBean.getFabricList().get(i).getItemStatus());
						List<ItemTable> itemlist=(List<ItemTable>)q.getResultList();
						if(itemlist.size()>0){
							for (int j = 0; j < itemlist.size(); j++) {
									q=null;
									q=entityManager.createQuery("from Stock where item_id=? and warehouseStatus=? and person_ID=? and cmtStatus='Raw' and status='Active'");
									q.setParameter(1, itemlist.get(j).getItemId());
									q.setParameter(2, "Main Warehouse");
									q.setParameter(3, Integer.parseInt(personID));
									List<Stock> stocklist=(List<Stock>)q.getResultList();
									if(stocklist.size()>0){
										System.out.println("stoc id"+stocklist.get(0).getStock_ID());
										Stock stock=entityManager.find(Stock.class, stocklist.get(0).getStock_ID());
										stock.setStockIn(new BigDecimal(stocklist.get(0).getStockIn())
										.add(new BigDecimal(supplyDataBean.getFabricList().get(i).getItemQuantity1())).toString());
										stock.setStockOut(new BigDecimal(stocklist.get(0).getStockOut())
										.subtract(new BigDecimal(supplyDataBean.getFabricList().get(i).getItemQuantity1())).toString());
										entityManager.merge(stock);
										Stock stock1=entityManager.find(Stock.class, stocklist.get(0).getStock_ID());
										stock1.setStockIn(new BigDecimal(stocklist.get(0).getStockIn())
											.subtract(new BigDecimal(supplyDataBean.getFabricList().get(i).getItemQuantity())).toString());
										stock1.setStockOut(new BigDecimal(stocklist.get(0).getStockOut()).
												add(new BigDecimal(supplyDataBean.getFabricList().get(i).getItemQuantity())).toString());
										entityManager.merge(stock1);
									}
								}
							}
						}
					PursalesdetailItem pursaledetail=entityManager.find(PursalesdetailItem.class, pursalesdetaillist.get(i).getPursalesitemDetailsId());
					pursaledetail.setPursalesItem(entityManager.find(PursalesItem.class, supplyDataBean.getFabricId()));
					pursaledetail.setItemName(supplyDataBean.getFabricList().get(i).getItemName());
					pursaledetail.setQuantity(supplyDataBean.getFabricList().get(i).getItemQuantity());
					pursaledetail.setPrice(supplyDataBean.getFabricList().get(i).getItemBuyPrice());
					pursaledetail.setTotalPrice(supplyDataBean.getFabricList().get(i).getTotalPrice());
					pursaledetail.setStatus("INSERTED");
					pursaledetail.setStatus2(supplyDataBean.getStatus2());
					pursaledetail.setPaymentStatus(supplyDataBean.getPaymentStatus());
					pursaledetail.setStockStatus(supplyDataBean.getStockStatus());
					entityManager.merge(pursaledetail);
					status="Success";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
}

@Override
@Transactional(value="transactionManager")
public String deletepursalesitems(String personID,SupplyDataBean supplyDataBean) {
	Query q=null;
	try{
		PursalesItem puritem=entityManager.find(PursalesItem.class, supplyDataBean.getFabricId());
		puritem.setStatus("DELETED");
		entityManager.merge(puritem);
		q=entityManager.createQuery("from PursalesdetailItem where pursalesitem_id=?");
		q.setParameter(1, supplyDataBean.getFabricId());
		List<PursalesdetailItem> pursaledetaillist=(List<PursalesdetailItem>)q.getResultList();
		if(pursaledetaillist.size()>0){
			for (int i = 0; i < pursaledetaillist.size(); i++) {
				PursalesdetailItem pursalesdetail=entityManager.find(PursalesdetailItem.class, pursaledetaillist.get(i).getPursalesitemDetailsId());
				pursalesdetail.setStatus("DELETED");
				entityManager.merge(pursalesdetail);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

public List<SupplyDataBean> getitemtableviews(String personID,SupplyDataBean supplyDataBean) {
	Query q=null;List<SupplyDataBean> pursaleslist=new ArrayList<SupplyDataBean>();
	Calendar cal = Calendar.getInstance();
	try{
		if(supplyDataBean.getItemStatus2().equalsIgnoreCase("all") && supplyDataBean.getItemStatus3().equalsIgnoreCase("allday")){
			q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and status='INSERTED' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("all") && supplyDataBean.getItemStatus3().equalsIgnoreCase("30days")){
			cal.add(Calendar.DATE, -30);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("all") && supplyDataBean.getItemStatus3().equalsIgnoreCase("60days")){
			cal.add(Calendar.DATE, -60);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("all") && supplyDataBean.getItemStatus3().equalsIgnoreCase("90days")){
			cal.add(Calendar.DATE, -90);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("all") && supplyDataBean.getItemStatus3().equalsIgnoreCase("120days")){
			cal.add(Calendar.DATE, -120);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("purchase") && supplyDataBean.getItemStatus3().equalsIgnoreCase("allday")){
			q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and status='INSERTED' and status2='PURCHASE' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("purchase") && supplyDataBean.getItemStatus3().equalsIgnoreCase("30days")){
			cal.add(Calendar.DATE, -30);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' and status2='PURCHASE' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("purchase") && supplyDataBean.getItemStatus3().equalsIgnoreCase("60days")){
			cal.add(Calendar.DATE, -60);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' and status2='PURCHASE' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("purchase") && supplyDataBean.getItemStatus3().equalsIgnoreCase("90days")){
			cal.add(Calendar.DATE, -90);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' and status2='PURCHASE' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("purchase") && supplyDataBean.getItemStatus3().equalsIgnoreCase("120days")){
			cal.add(Calendar.DATE, -120);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' and status2='PURCHASE' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("sales") && supplyDataBean.getItemStatus3().equalsIgnoreCase("allday")){
			q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and status='INSERTED' and status2='SALES' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("sales") && supplyDataBean.getItemStatus3().equalsIgnoreCase("30days")){
			cal.add(Calendar.DATE, -30);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' and status2='SALES' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("sales") && supplyDataBean.getItemStatus3().equalsIgnoreCase("60days")){
			cal.add(Calendar.DATE, -60);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' and status2='SALES' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("sales") && supplyDataBean.getItemStatus3().equalsIgnoreCase("90days")){
			cal.add(Calendar.DATE, -90);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' and status2='SALES' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}else if(supplyDataBean.getItemStatus2().equalsIgnoreCase("sales") && supplyDataBean.getItemStatus3().equalsIgnoreCase("120days")){
			cal.add(Calendar.DATE, -120);
		    Date todate1 = cal.getTime();    
		    q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' and status2='SALES' ORDER BY orderDate DESC");
			q.setParameter(1, personID);
			q.setParameter(2, supplyDataBean.getItemStatus());
			q.setParameter(3, todate1);
			q.setParameter(4, date);
		}
		List<PursalesItem> pursalesList=(List<PursalesItem>)q.getResultList();
		if(pursalesList.size()>0){
			for (int i = 0; i < pursalesList.size(); i++) {
				SupplyDataBean sup=new SupplyDataBean();
				sup.setSerialNo(String.valueOf(i+1));
				sup.setTodayDate(pursalesList.get(i).getOrderDate());
				sup.setFabricInvoiceNumber(pursalesList.get(i).getInvoiceNumber());
				sup.setSupplierName(pursalesList.get(i).getSupplierName());
				sup.setVendorName(pursalesList.get(i).getVendorName());
				if(pursalesList.get(i).getStatus2().equals("PURCHASE")){
					sup.setTotalPrice(pursalesList.get(i).getTotalPrice());
					sup.setTotalAmount("");
				}else{
					sup.setTotalAmount(pursalesList.get(i).getTotalPrice());
					sup.setTotalPrice("");
				}
				sup.setInvoiceStatus(pursalesList.get(i).getInvoiceStatus());
				sup.setPaymentStatus(pursalesList.get(i).getPaymentStatus());
				sup.setFabricId(pursalesList.get(i).getPursalesitemId());
				sup.setStatus2(pursalesList.get(i).getStatus2());
				sup.setItemStatus(pursalesList.get(i).getItemStatus());
				pursaleslist.add(sup);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return pursaleslist;
}

@Transactional(value="transactionManager")
public String getPaymentDetails(String personID, SupplyDataBean supplyDataBean) {
	Query v=null;BigDecimal paid=BigDecimal.valueOf(0),balance=BigDecimal.valueOf(0),remaining=BigDecimal.valueOf(0);
	BigDecimal amount=BigDecimal.valueOf(0);
	try{
		v=entityManager.createQuery("from PursalesItem where pursalesitem_id=?");
		v.setParameter(1, supplyDataBean.getFabricId());
		List<PursalesItem> pursalesItem=(List<PursalesItem>)v.getResultList();
		if(pursalesItem.size()>0){
			v=null;
			v=entityManager.createQuery("from Payment where pursalesitem_id=?");
			v.setParameter(1, pursalesItem.get(0).getPursalesitemId());
			List<Payment> payment=(List<Payment>)v.getResultList();
			System.out.println("payment size "+payment.size());
			if(payment.size()==0){
				Payment payInsert=new Payment();
				payInsert.setBalanceAmount(pursalesItem.get(0).getTotalPrice());
				payInsert.setPaidAmount("0");
				payInsert.setTotalAmount(pursalesItem.get(0).getTotalPrice());
				payInsert.setStatus("Pending");
				payInsert.setDate(date);
				payInsert.setPursalesItem(entityManager.find(PursalesItem.class, pursalesItem.get(0).getPursalesitemId()));
				entityManager.persist(payInsert);
				System.out.println("inserted");
			}
			v=null;
			v=entityManager.createQuery("from Payment where pursalesitem_id=?");
			v.setParameter(1, pursalesItem.get(0).getPursalesitemId());
			List<Payment> payments=(List<Payment>)v.getResultList();
			System.out.println("payment size after insert "+payments.size());
			if(payments.size()>0){	
				supplyDataBean.setPaidAmoount(payments.get(0).getPaidAmount());
				supplyDataBean.setBalanceAmoount(payments.get(0).getBalanceAmount());
				supplyDataBean.setTotalPrice(payments.get(0).getTotalAmount());
				supplyDataBean.setTodayDate(payments.get(0).getPursalesItem().getOrderDate());
				supplyDataBean.setFabricInvoiceNumber(payments.get(0).getPursalesItem().getInvoiceNumber());
				supplyDataBean.setSupplierName(payments.get(0).getPursalesItem().getSupplierName());
				supplyDataBean.setVendorName(payments.get(0).getPursalesItem().getVendorName());
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Transactional(value="transactionManager")
public String generateiInvoiceno(String personID,SupplyDataBean supplyDataBean){
	try{
		PursalesItem puritem=entityManager.find(PursalesItem.class, supplyDataBean.getFabricId());
		puritem.setInvoiceStatus("Invoice");
		entityManager.merge(puritem);
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Transactional(value="transactionManager")
public String payAmount(String personID, SupplyDataBean supplyDataBean){
	Query v=null;BigDecimal paid=BigDecimal.valueOf(0),balance=BigDecimal.valueOf(0),remaining=BigDecimal.valueOf(0);
	BigDecimal amount=BigDecimal.valueOf(0);
	try{
		v=null;
		v=entityManager.createQuery("from Payment where pursalesitem_id=?");
		v.setParameter(1, supplyDataBean.getFabricId());
		List<Payment> payment=(List<Payment>)v.getResultList();
		if(payment.size()>0){				
			paid=new BigDecimal(payment.get(0).getPaidAmount());
			balance=new BigDecimal(payment.get(0).getBalanceAmount());
			remaining=new BigDecimal(supplyDataBean.getRemaining());
			amount=paid.add(remaining);
			System.out.println("paid "+paid+" bal "+balance+" rem "+remaining+" pay "+amount);
			Payment payUpdate=entityManager.find(Payment.class, payment.get(0).getPayment_ID());
			payUpdate.setPaidAmount(String.valueOf(new BigDecimal(payment.get(0).getPaidAmount()).add(new BigDecimal(supplyDataBean.getRemaining()))));
			payUpdate.setBalanceAmount(String.valueOf(new BigDecimal(payment.get(0).getBalanceAmount()).subtract(new BigDecimal(supplyDataBean.getRemaining()))));
			if(amount.compareTo(new BigDecimal(payment.get(0).getTotalAmount()))==0){
				payUpdate.setStatus("Paid");
			}else if(amount.compareTo(new BigDecimal(payment.get(0).getTotalAmount()))==-1){
				payUpdate.setStatus("Pending");
			}
			entityManager.merge(payUpdate);	
			entityManager.flush();
			entityManager.clear();
			if(payUpdate.getStatus().equals("Paid")){
				PursalesItem puritem=entityManager.find(PursalesItem.class, supplyDataBean.getFabricId());
				puritem.setInvoiceStatus("Paid");
				entityManager.merge(puritem);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Transactional(value="transactionManager")
public String addStock(String personID, SupplyDataBean supplyDataBean){
	Query v=null;
	System.out.println("Inside addStock");
	try{
		v=entityManager.createQuery("from PursalesdetailItem where pursalesitem_id=?");
		v.setParameter(1, supplyDataBean.getFabricId());
		List<PursalesdetailItem> pursalesdetailItem=(List<PursalesdetailItem>)v.getResultList();
		if(pursalesdetailItem.size()>0){
			for (int i = 0; i < pursalesdetailItem.size(); i++) {
				v=null;
				v=entityManager.createQuery("from ItemTable where itemName=? and person_ID=? and itemStatus=? and status='ACTIVE'");
				v.setParameter(1, pursalesdetailItem.get(i).getItemName());
				v.setParameter(2, Integer.parseInt(personID));
				v.setParameter(3, supplyDataBean.getItemStatus());
				List<ItemTable> itemTable=(List<ItemTable>) v.getResultList();
				if(itemTable.size()>0){
					v=null;
					v=entityManager.createQuery("from Stock where item_id=? and warehouseStatus=? and person_ID=? and cmtStatus='Raw' and status='Active'");
					v.setParameter(1, itemTable.get(0).getItemId());
					v.setParameter(2, "Main Warehouse");
					v.setParameter(3, Integer.parseInt(personID));
					List<Stock> stock=(List<Stock>)v.getResultList();
					if(stock.size()>0){
						Stock uptStock=entityManager.find(Stock.class,stock.get(0).getStock_ID());
						uptStock.setItemName(itemTable.get(0).getItemName());
						uptStock.setStockIn(String.valueOf(new BigDecimal(stock.get(0).getStockIn()).add(new BigDecimal(pursalesdetailItem.get(i).getQuantity()))));
						entityManager.merge(uptStock);
						entityManager.flush();
						entityManager.clear();
					}else{
						Stock addStock=new Stock();
						addStock.setItemName(itemTable.get(0).getItemName());
						addStock.setItemTable(entityManager.find(ItemTable.class,itemTable.get(0).getItemId()));
						addStock.setStatus("Active");
						addStock.setStockIn(pursalesdetailItem.get(i).getQuantity());
						addStock.setStockOut("0");
						addStock.setWarehouseStatus("Main Warehouse");
						addStock.setCmtStatus("Raw");
						addStock.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
						entityManager.persist(addStock);
						entityManager.flush();
						entityManager.clear();
					}					
				}	
				PursalesdetailItem puritems=entityManager.find(PursalesdetailItem.class,pursalesdetailItem.get(i).getPursalesitemDetailsId());
				puritems.setStockStatus("ADDED TO STOCK");
				entityManager.merge(puritems);
				entityManager.flush();
				entityManager.clear();
			}
			PursalesItem puritem=entityManager.find(PursalesItem.class, supplyDataBean.getFabricId());
			puritem.setStockStatus("ADDED TO STOCK");
			puritem.setDeliveredDate(supplyDataBean.getDeliveredDate());
			puritem.setInvoiceStatus("StockIn");
			entityManager.merge(puritem);
			entityManager.flush();
			entityManager.clear();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

@Override
@Transactional(value="transactionManager")
public String fabricgenerateInvoice(String personID,SupplyDataBean supplyDataBean) {
	Query q=null;
	try{
		Fabric fab=entityManager.find(Fabric.class, supplyDataBean.getFabricId());
		fab.setInvoiceStatus("INVOICE");
		entityManager.merge(fab); 
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Override
@Transactional(value="transactionManager")
public String purchaseFabricPayment(String personID,SupplyDataBean supplyDataBean) {
	Query q=null;
	try{
		q=entityManager.createQuery("from Fabric where person_ID=? and fabric_id=?");
		q.setParameter(1, personID);
		q.setParameter(2, supplyDataBean.getFabricId());
		List<Fabric> fabricList=(List<Fabric>)q.getResultList();
		q=null;
		q=entityManager.createQuery("from Payment where fabric_id=?");
		q.setParameter(1, fabricList.get(0).getFabricId());
		List<Payment> paymentList=(List<Payment>)q.getResultList();
		supplyDataBean.setFabricPurchaseDate(fabricList.get(0).getPurchaseDate());
		supplyDataBean.setFabricInvoiceNumber(fabricList.get(0).getInvoiceNumber());
		supplyDataBean.setSupplierName(fabricList.get(0).getSupplierName());
		supplyDataBean.setTotalPrice(fabricList.get(0).getTotalAmount());
		if(paymentList.size()==0){
			Payment pay=new Payment();
			pay.setPaidAmount("0");
			pay.setBalanceAmount(fabricList.get(0).getTotalAmount());
			pay.setFabric(entityManager.find(Fabric.class, fabricList.get(0).getFabricId()));
			pay.setDate(date);
			pay.setTotalAmount(fabricList.get(0).getTotalAmount());
			pay.setStatus("pending");
			entityManager.persist(pay);
		}else{
			supplyDataBean.setTotalPrice(fabricList.get(0).getTotalAmount());
			supplyDataBean.setPaidAmoount(paymentList.get(0).getPaidAmount());
			supplyDataBean.setBalanceAmoount(paymentList.get(0).getBalanceAmount());
		}
		q=null;
		q=entityManager.createQuery("from Payment where fabric_id=?");
		q.setParameter(1, fabricList.get(0).getFabricId());
		List<Payment> paymentLists=(List<Payment>)q.getResultList();
		if(paymentLists.size()>0){
			supplyDataBean.setPaidAmoount(paymentLists.get(0).getPaidAmount());
			supplyDataBean.setBalanceAmoount(paymentLists.get(0).getBalanceAmount());
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Override
@Transactional(value="transactionManager")
public String insertPayment(String personID, SupplyDataBean supplyDataBean) {
	System.out.println("fab id"+supplyDataBean.getFabricId());
	Query q=null;BigDecimal paid=BigDecimal.valueOf(0),balance=BigDecimal.valueOf(0),remaining=BigDecimal.valueOf(0);
	BigDecimal amount=BigDecimal.valueOf(0);
	try{
		q=entityManager.createQuery("from Payment where fabric_id=?");
		q.setParameter(1, supplyDataBean.getFabricId());
		List<Payment> payment=(List<Payment>)q.getResultList();
		if(payment.size()>0){
			paid=new BigDecimal(payment.get(0).getPaidAmount());
			balance=new BigDecimal(payment.get(0).getBalanceAmount());
			remaining=new BigDecimal(supplyDataBean.getRemaining());
			System.out.println("remaining--"+supplyDataBean.getRemaining());
			amount=paid.add(remaining);
			Payment payUpdate=entityManager.find(Payment.class, payment.get(0).getPayment_ID());
			payUpdate.setPaidAmount(String.valueOf(new BigDecimal(payment.get(0).getPaidAmount()).add(new BigDecimal(supplyDataBean.getRemaining()))));
			payUpdate.setBalanceAmount(String.valueOf(new BigDecimal(payment.get(0).getBalanceAmount()).subtract(new BigDecimal(supplyDataBean.getRemaining()))));
			if(amount.compareTo(new BigDecimal(payment.get(0).getTotalAmount()))==0){
			    payUpdate.setStatus("Paid");
			}else if(amount.compareTo(new BigDecimal(payment.get(0).getTotalAmount()))==-1){
			    payUpdate.setStatus("Pending");
			}
			entityManager.merge(payUpdate); 
			entityManager.flush();
			entityManager.clear();
			if(payUpdate.getStatus().equals("Paid")){
				Fabric fab=entityManager.find(Fabric.class, supplyDataBean.getFabricId());
				fab.setInvoiceStatus("Paid");
			    entityManager.merge(fab);
			}
		}
	}catch(Exception e){
			  e.printStackTrace();
	}
	return null;
}


@Override
@Transactional(value="transactionManager")
public String usernamechanged(LoginDataBean loginDataBean) {
	Query q=null;
	String status="fail";
	try {
		q=entityManager.createQuery("from User where userName=?");
		q.setParameter(1, loginDataBean.getCfusername());
		List<User> user=(List<User>) q.getResultList();
		if(user.size()>0)
		{
			status="Exists";
		}
		else
		{
			q=entityManager.createQuery("from User where userName=?");
			q.setParameter(1,loginDataBean.getUserName());
			List<User> user1=(List<User>) q.getResultList();
			if(user1.size()>0)
			{
				int userId=0;
				userId=user1.get(0).getUser_ID();
				User newUser=entityManager.find(User.class, userId);
				newUser.setUserName(loginDataBean.getCfusername());
				entityManager.merge(newUser);
				status="success";
			}
		}
	} catch (Exception e) {
	}
	return status;
}

@Override
@Transactional(value="transactionManager")
public String passwordchange(LoginDataBean loginDataBean) {
	Query q=null;
	String status="fail";
	try {
			q=entityManager.createQuery("from User where userName=? and password=?");
			q.setParameter(1,loginDataBean.getUserName());
			q.setParameter(2,loginDataBean.getPassword());
			List<User> user1=(List<User>) q.getResultList();
			if(user1.size()>0)
			{
				int userId=0;
				userId=user1.get(0).getUser_ID();
				User newUser=entityManager.find(User.class, userId);
				newUser.setPassword(loginDataBean.getCfpassword());
				entityManager.merge(newUser);
				status="success";
			}
			else{
				status="wrongpass";
			}
			
	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}

@Transactional(value="transactionManager")
public String warehouseSave(String personID, StockDataBean stockDataBean){
	try{
		for (int i = 0; i < stockDataBean.getWarelists().size(); i++) {
			if(!stockDataBean.getWarelists().get(i).getWarehouseName().equals("")){
				Warehouse insert=new Warehouse();
				insert.setName(stockDataBean.getWarelists().get(i).getWarehouseName());
				insert.setLocation(stockDataBean.getWarelists().get(i).getLocation());
				insert.setPersonIncharge(stockDataBean.getWarelists().get(i).getPersonIncharge());
				insert.setStatus("Active");
				insert.setPerson(entityManager.find(Person.class,Integer.parseInt(personID)));
				entityManager.persist(insert);
				entityManager.flush();
				entityManager.clear();
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public String getWarehouseDetails(String personID, StockDataBean stockDataBean){
	Query v=null;
	List<StockDataBean> warehlist=new ArrayList<StockDataBean>();
	try{
		v=entityManager.createQuery("from Warehouse where person_id=? and status='Active'");
		v.setParameter(1, Integer.parseInt(personID));
		List<Warehouse> warehouse=(List<Warehouse>) v.getResultList();
		System.out.println("warehouse size "+warehouse.size());
		if(warehouse.size()>0){
			for (int i = 0; i < warehouse.size(); i++) {
				StockDataBean warelist=new StockDataBean();
				warelist.setWarehouseName(warehouse.get(i).getName());
				warelist.setLocation(warehouse.get(i).getLocation());
				warelist.setPersonIncharge(warehouse.get(i).getPersonIncharge());
				warelist.setWarehouse_id(warehouse.get(i).getWarehouseId());
				warehlist.add(warelist);
			}
			stockDataBean.setWarelists(warehlist);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

@Transactional(value="transactionManager")
public String updateWarehouse(StockDataBean stockDataBean){
	try{
		if(stockDataBean.getStatus().equals("edit")){
			Warehouse updateWare=entityManager.find(Warehouse.class, stockDataBean.getWarehouse_id());
			updateWare.setName(stockDataBean.getWarehouseName());
			updateWare.setLocation(stockDataBean.getLocation());
			updateWare.setPersonIncharge(stockDataBean.getPersonIncharge());
			entityManager.merge(updateWare);
		}else if(stockDataBean.getStatus().equals("delete")){
			Warehouse updateWare=entityManager.find(Warehouse.class, stockDataBean.getWarehouse_id());
			updateWare.setStatus("DeActive");
			entityManager.merge(updateWare);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public String getManualStockOutetails(String personID,StockDataBean stockDataBean){
	Query v=null;
	List<String> warehouseList=new ArrayList<String>();
	List<String> cmtProducts=new ArrayList<String>();
	try{
		v=entityManager.createQuery("from Warehouse where person_id=? and status='Active'");
		v.setParameter(1, Integer.parseInt(personID));
		List<Warehouse> warehouse=(List<Warehouse>) v.getResultList();
		System.out.println("warehouse size "+warehouse.size());
		if(warehouse.size()>0){
			for (int i = 0; i < warehouse.size(); i++) {
				warehouseList.add(warehouse.get(i).getName());
			}
			stockDataBean.setWarehouseList(warehouseList);			
		}
		v=entityManager.createQuery("from Stock where person_id=? and warehouseStatus=? and status='Active' and cmtStatus='CmtStock'");
		v.setParameter(1, Integer.parseInt(personID));
		v.setParameter(2, stockDataBean.getWarehouseName());
		List<Stock> stock=(List<Stock>) v.getResultList();
		System.out.println("cmt stock size "+stock.size());
		if(stock.size()>0){
			for (int i = 0; i < stock.size(); i++) {
				cmtProducts.add(stock.get(i).getItemName());
			}
			HashSet<String> hasList=new HashSet<String>(cmtProducts);
			cmtProducts.clear();
			cmtProducts.addAll(hasList);
			stockDataBean.setCmtProducts(cmtProducts);	
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public String getModelDetails(String personID, StockDataBean stockDataBean){
	Query v=null;
	List<String> serilist=new ArrayList<String>();
	try{
		if(stockDataBean.getSerin().equals("")){
			System.out.println("if");
			v=entityManager.createQuery("from Stock where person_id=? and warehouseStatus=? and itemName=? and status='Active' and cmtStatus='CmtStock'");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, stockDataBean.getWarehouseName());
			v.setParameter(3, stockDataBean.getModelNo());
			List<Stock> stock=(List<Stock>) v.getResultList();
			if(stock.size()>0){
				for (int i = 0; i < stock.size(); i++) {
					serilist.add(stock.get(i).getSeriNo());
				}
			}
		}else{
			System.out.println("else");
			v=entityManager.createQuery("from Stock where person_id=? and warehouseStatus=? and itemName=? and seriNo=? and status='Active' and cmtStatus='CmtStock'");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, stockDataBean.getWarehouseName());
			v.setParameter(3, stockDataBean.getModelNo());
			v.setParameter(4, stockDataBean.getSerin());
			List<Stock> stock=(List<Stock>) v.getResultList();
			System.out.println("stock size "+stock.size());
			if(stock.size()>0){
				stockDataBean.setQuantity(stock.get(0).getStockIn());
			}
		}		
		v=null;
		v=entityManager.createQuery("from Model where person_ID=? and model=? and status='Active'");
		v.setParameter(1, Integer.parseInt(personID));
		v.setParameter(2, stockDataBean.getModelNo());
		List<Model> model=(List<Model>) v.getResultList();
		if(model.size()>0){
			stockDataBean.setSellingPrice(model.get(0).getSellPrice());
		}
		stockDataBean.setSeris(serilist);
	}catch(Exception e){
		e.printStackTrace();
	}	
	return "";
}

@Transactional(value="transactionManager")
public String saveManualStockOut(String personID, StockDataBean stockDataBean){
	Query v=null;
	try{
		ManualStock manualStock=new ManualStock();
		manualStock.setDate(date);
		manualStock.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
		manualStock.setShopName(stockDataBean.getWarehouseName());
		manualStock.setStatus("Active");
		manualStock.setTotalPrice(stockDataBean.getTotalPrice());
		entityManager.persist(manualStock);
		entityManager.flush();
		entityManager.clear();
		v=entityManager.createQuery("from ManualStock");
		List<ManualStock> manualStocks=(List<ManualStock>)v.getResultList();
		if(manualStocks.size()>0){
			for (int i = 0; i < stockDataBean.getWarelists().size(); i++) {
				if(!stockDataBean.getWarelists().get(i).getModelNo().equals("")){
					if(stockDataBean.getWarehouseName().equals("Main Warehouse")){
						v=null;
						v=entityManager.createQuery("from Stock where itemName=? and seriNo=? and warehouseStatus='Main Warehouse' and cmtStatus='CmtStock' and status='Active'");
						v.setParameter(1, stockDataBean.getWarelists().get(i).getModelNo());
						v.setParameter(2, stockDataBean.getWarelists().get(i).getSeri());
						List<Stock> stock=(List<Stock>)v.getResultList();
						if(stock.size()>0)
						{
							Stock stockupdt=entityManager.find(Stock.class, stock.get(0).getStock_ID());
							stockupdt.setStockIn(String.valueOf(new BigDecimal(stock.get(0).getStockIn()).
									subtract(new BigDecimal(stockDataBean.getWarelists().get(i).getQuantity()))));
							stockupdt.setStockOut(String.valueOf(new BigDecimal(stock.get(0).getStockOut()).
									add(new BigDecimal(stockDataBean.getWarelists().get(i).getQuantity()))));
							entityManager.merge(stockupdt);
							entityManager.flush();
							entityManager.clear();
						}
					}
					ManualStockOut stockout=new ManualStockOut();
					stockout.setModel(stockDataBean.getWarelists().get(i).getModelNo());
					stockout.setNetAmount(stockDataBean.getWarelists().get(i).getTotalPrice());
					stockout.setQuantity(stockDataBean.getWarelists().get(i).getQuantity());
					stockout.setSeri(stockDataBean.getWarelists().get(i).getSeri());
					stockout.setShopName(stockDataBean.getNewWarehouse());
					stockout.setSellingPrice(stockDataBean.getWarelists().get(i).getSellingPrice());
					stockout.setManualStock(entityManager.find(ManualStock.class, manualStocks.get(manualStocks.size()-1).getManualStockId()));
					entityManager.persist(stockout);
					entityManager.flush();
					entityManager.clear();
						v=null;
						v=entityManager.createQuery("from Stock where itemName=? and warehouseStatus=? and seriNo=? and cmtStatus='CmtStock' and status='Active'");
						v.setParameter(1, stockDataBean.getWarelists().get(i).getModelNo());
						v.setParameter(2, stockDataBean.getNewWarehouse());
						v.setParameter(3, stockDataBean.getWarelists().get(i).getSeri());
						List<Stock> stocks=(List<Stock>)v.getResultList();
						if(stocks.size()>0){
							Stock stockupdt=entityManager.find(Stock.class, stocks.get(0).getStock_ID());
							stockupdt.setStockIn(String.valueOf(new BigDecimal(stocks.get(0).getStockIn()).
									add(new BigDecimal(stockDataBean.getWarelists().get(i).getQuantity()))));
							entityManager.merge(stockupdt);
							entityManager.flush();
							entityManager.clear();
						}else{
							Stock stockadd=new Stock();
							stockadd.setStockIn(stockDataBean.getWarelists().get(i).getQuantity());
							stockadd.setStockOut("0");
							stockadd.setItemName(stockDataBean.getWarelists().get(i).getModelNo());
							stockadd.setSeriNo(stockDataBean.getWarelists().get(i).getSeri());
							stockadd.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
							stockadd.setStatus("Active");
							stockadd.setCmtStatus("CmtStock");
							stockadd.setWarehouseStatus(stockDataBean.getNewWarehouse());
							entityManager.persist(stockadd);
							entityManager.flush();
							entityManager.clear();
						}
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public String getManualStockDetails(String personID,StockDataBean stockDataBean){
	Query v=null;
	List<StockDataBean> manualList=new ArrayList<StockDataBean>();
	try{
		v=entityManager.createQuery("from ManualStock where person_ID=? and status='Active' ORDER BY date DESC");
		v.setParameter(1, Integer.parseInt(personID));
		List<ManualStock> manualStock=(List<ManualStock>)v.getResultList();
		if(manualStock.size()>0){
			for (int i = 0; i < manualStock.size(); i++) {
				StockDataBean manualStocks=new StockDataBean();
				manualStocks.setSerialNo(String.valueOf(i+1));
				manualStocks.setDate(manualStock.get(i).getDate());
				manualStocks.setWarehouseName(manualStock.get(i).getShopName());
				manualStocks.setTotalPrice(manualStock.get(i).getTotalPrice());
				manualStocks.setMnaualStockID(manualStock.get(i).getManualStockId());
				v=null;
				v=entityManager.createQuery("from ManualStockOut where manual_stock_id=?");
				v.setParameter(1, manualStock.get(i).getManualStockId());
				List<ManualStockOut> manualStkout=(List<ManualStockOut>) v.getResultList();
				if(manualStkout.size()>0){
					int count=0;
					for (int j = 0; j < manualStkout.size(); j++) {
						if(manualStock.get(i).getShopName().equals("Main Warehouse")){
							v=null;
							v=entityManager.createQuery("from Stock where itemName=? and seriNo=? and warehouseStatus=? and cmtStatus='CmtStock' and status='Active'");
							v.setParameter(1, manualStkout.get(j).getModel());
							v.setParameter(2, manualStkout.get(j).getSeri());
							v.setParameter(3, manualStkout.get(j).getShopName());
							List<Stock> stock=(List<Stock>)v.getResultList();
							if(stock.size()>0){
								if(new BigDecimal(stock.get(0).getStockIn()).compareTo(new BigDecimal(manualStkout.get(j).getQuantity()))==-1){
									count++;
								}
							}
						}else{
							v=null;
							v=entityManager.createQuery("from Stock where itemName=? and seriNo=? and warehouseStatus=? and cmtStatus='CmtStock' and status='Active'");
							v.setParameter(1, manualStkout.get(j).getModel());
							v.setParameter(2, manualStkout.get(j).getSeri());
							v.setParameter(3, manualStock.get(i).getShopName());
							List<Stock> stock=(List<Stock>)v.getResultList();
							if(stock.size()>0){
								if(new BigDecimal(stock.get(0).getStockIn()).compareTo(new BigDecimal(manualStkout.get(j).getQuantity()))==-1){
									count++;
								}
							}
						}						
					}
					if(count>0){
						manualStocks.setFlag(false);
						manualStocks.setFlag1(true);
					}else{						
						manualStocks.setFlag(true);
						manualStocks.setFlag1(false);
					}
				}				
				manualList.add(manualStocks);
			}
			stockDataBean.setWarelists(manualList);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public String getmanualStckView(String personID, StockDataBean stockDataBean){
	Query v=null;
	List<StockDataBean> manualList=new ArrayList<StockDataBean>();
	try{
		v=entityManager.createQuery("from ManualStock where manualStockId=? and status='Active'");
		v.setParameter(1, stockDataBean.getMnaualStockID());
		List<ManualStock> manualStock=(List<ManualStock>)v.getResultList();
		if(manualStock.size()>0){
			stockDataBean.setDate(manualStock.get(0).getDate());
			stockDataBean.setWarehouseName(manualStock.get(0).getShopName());
			stockDataBean.setTotalPrice(manualStock.get(0).getTotalPrice());
			v=null;
			v=entityManager.createQuery("from ManualStockOut where manual_stock_id=?");
			v.setParameter(1, stockDataBean.getMnaualStockID());
			List<ManualStockOut> manualStkout=(List<ManualStockOut>) v.getResultList();
			if(manualStkout.size()>0){
				for (int i = 0; i < manualStkout.size(); i++) {
					StockDataBean manualStocks=new StockDataBean();
					manualStocks.setSerialNo(String.valueOf(i+1));
					manualStocks.setModelNo(manualStkout.get(i).getModel());
					manualStocks.setSeri(manualStkout.get(i).getSeri());
					manualStocks.setSellingPrice(manualStkout.get(i).getSellingPrice());
					manualStocks.setTotalPrice(manualStkout.get(i).getNetAmount());
					manualStocks.setQuantity(manualStkout.get(i).getQuantity());
					manualStocks.setQuantity1(manualStkout.get(i).getQuantity());
					manualStocks.setSflag(false);
					manualStocks.setSflag1(true);
					manualList.add(manualStocks);
				}
				stockDataBean.setStklists(manualList);
				stockDataBean.setNewWarehouse(manualStkout.get(0).getShopName());
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

@Transactional(value="transactionManager")
public String updateManualStockOut(String personID,StockDataBean stockDataBean){
	Query v=null;
	try{
		v=entityManager.createQuery("from ManualStockOut where manual_stock_id=?");
		v.setParameter(1, stockDataBean.getMnaualStockID());
		List<ManualStockOut> manualStkout=(List<ManualStockOut>) v.getResultList();
		if(manualStkout.size()>0){
			ManualStock manualStk=entityManager.find(ManualStock.class,stockDataBean.getMnaualStockID());
			manualStk.setTotalPrice(stockDataBean.getTotalPrice());
			entityManager.merge(manualStk);
			entityManager.flush();
			entityManager.clear();
			for (int i = 0; i < manualStkout.size(); i++) {
				ManualStockOut manualstkupte=entityManager.find(ManualStockOut.class, manualStkout.get(i).getManualStockOutId());
				manualstkupte.setQuantity(stockDataBean.getStklists().get(i).getQuantity());
				manualstkupte.setNetAmount(stockDataBean.getStklists().get(i).getTotalPrice());
				entityManager.merge(manualstkupte);
				entityManager.flush();
				entityManager.clear();
				if(stockDataBean.getWarehouseName().equals("Main Warehouse")){
					v=null;
					v=entityManager.createQuery("from Stock where itemName=? and seriNo=? and warehouseStatus='Main Warehouse' and cmtStatus='CmtStock' and status='Active'");
					v.setParameter(1, stockDataBean.getStklists().get(i).getModelNo());
					v.setParameter(2, stockDataBean.getStklists().get(i).getSeri());
					List<Stock> stock=(List<Stock>)v.getResultList();
					if(stock.size()>0)
					{
						Stock stockupdt=entityManager.find(Stock.class, stock.get(0).getStock_ID());
						stockupdt.setStockIn(String.valueOf((new BigDecimal(stock.get(0).getStockIn()).
								add(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity1()))).
								subtract(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity()))));
						stockupdt.setStockOut(String.valueOf((new BigDecimal(stock.get(0).getStockOut()).
								subtract(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity1()))).
								add(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity()))));
						entityManager.merge(stockupdt);
						entityManager.flush();
						entityManager.clear();		
						v=null;
						v=entityManager.createQuery("from Stock where itemName=? and warehouseStatus=? and seriNo=? and cmtStatus='CmtStock' and status='Active'");
						v.setParameter(1, stockDataBean.getStklists().get(i).getModelNo());
						v.setParameter(2, stockDataBean.getStklists().get(i).getWarehouseName());
						v.setParameter(3, stockDataBean.getStklists().get(i).getSeri());
						List<Stock> stocks=(List<Stock>)v.getResultList();
						if(stocks.size()>0){
							Stock stockupdts=entityManager.find(Stock.class, stocks.get(0).getStock_ID());
							stockupdts.setStockIn(String.valueOf((new BigDecimal(stocks.get(0).getStockIn()).
									subtract(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity1()))).
									add(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity()))));							
							entityManager.merge(stockupdts);
							entityManager.flush();
							entityManager.clear();
						}
					}
				}else{
					v=null;
					System.out.println("name "+stockDataBean.getWarehouseName());
					v=entityManager.createQuery("from Stock where itemName=? and warehouseStatus=? and seriNo=? and cmtStatus='CmtStock' and status='Active'");
					v.setParameter(1, stockDataBean.getStklists().get(i).getModelNo());
					v.setParameter(2, stockDataBean.getWarehouseName());
					v.setParameter(3, stockDataBean.getStklists().get(i).getSeri());
					List<Stock> stocks=(List<Stock>)v.getResultList();
					if(stocks.size()>0){
						Stock stockupdts=entityManager.find(Stock.class, stocks.get(0).getStock_ID());
						stockupdts.setStockIn(String.valueOf((new BigDecimal(stocks.get(0).getStockIn()).
								add(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity1()))).
								subtract(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity()))));
						if(!stocks.get(0).getStockOut().equals("0")){
							stockupdts.setStockOut(String.valueOf((new BigDecimal(stocks.get(0).getStockOut()).
									subtract(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity1()))).
									add(new BigDecimal(stockDataBean.getStklists().get(i).getQuantity()))));
						}						
						entityManager.merge(stockupdts);
						entityManager.flush();
						entityManager.clear();
					}
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

@Transactional(value="transactionManager")
public String deleteManualStockOut(String personID,StockDataBean stockDataBean){
	Query v=null;
	try{
		v=entityManager.createQuery("from ManualStockOut where manual_stock_id=?");
		v.setParameter(1, stockDataBean.getMnaualStockID());
		List<ManualStockOut> manualStkout=(List<ManualStockOut>) v.getResultList();
		if(manualStkout.size()>0){
			ManualStock manualStk=entityManager.find(ManualStock.class,stockDataBean.getMnaualStockID());
			manualStk.setStatus("DeActive");
			entityManager.merge(manualStk);
			entityManager.flush();
			entityManager.clear();
			for (int i = 0; i < manualStkout.size(); i++) {
				if(stockDataBean.getWarehouseName().equals("Main Warehouse")){
					v=null;
					v=entityManager.createQuery("from Stock where itemName=? and seriNo=? and warehouseStatus='Main Warehouse' and cmtStatus='CmtStock' and status='Active'");
					v.setParameter(1, manualStkout.get(i).getModel());
					v.setParameter(2, manualStkout.get(i).getSeri());
					List<Stock> stock=(List<Stock>)v.getResultList();
					if(stock.size()>0)
					{
						Stock stockupdt=entityManager.find(Stock.class, stock.get(0).getStock_ID());
						stockupdt.setStockIn(String.valueOf((new BigDecimal(stock.get(0).getStockIn()).
								add(new BigDecimal(manualStkout.get(i).getQuantity())))));
						stockupdt.setStockOut(String.valueOf((new BigDecimal(stock.get(0).getStockOut()).
								subtract(new BigDecimal(manualStkout.get(i).getQuantity())))));
						entityManager.merge(stockupdt);
						entityManager.flush();
						entityManager.clear();		
						v=null;
						v=entityManager.createQuery("from Stock where itemName=? and warehouseStatus=? and seriNo=? and cmtStatus='CmtStock' and status='Active'");
						v.setParameter(1,manualStkout.get(i).getModel());
						v.setParameter(2,manualStkout.get(i).getShopName());
						v.setParameter(3, manualStkout.get(i).getSeri());
						List<Stock> stocks=(List<Stock>)v.getResultList();
						if(stocks.size()>0){
							Stock stockupdts=entityManager.find(Stock.class, stocks.get(0).getStock_ID());
							stockupdts.setStockIn(String.valueOf((new BigDecimal(stocks.get(0).getStockIn()).
									subtract(new BigDecimal(manualStkout.get(i).getQuantity())))));							
							entityManager.merge(stockupdts);
							entityManager.flush();
							entityManager.clear();
						}
					}
				}else{
					v=null;
					v=entityManager.createQuery("from Stock where itemName=? and warehouseStatus=? and seriNo=? and cmtStatus='CmtStock' and status='Active'");
					v.setParameter(1, manualStkout.get(i).getModel());
					v.setParameter(2, stockDataBean.getWarehouseName());
					v.setParameter(3, manualStkout.get(i).getSeri());
					List<Stock> stocks=(List<Stock>)v.getResultList();
					if(stocks.size()>0){
						Stock stockupdts=entityManager.find(Stock.class, stocks.get(0).getStock_ID());
						stockupdts.setStockIn(String.valueOf((new BigDecimal(stocks.get(0).getStockIn()).
								add(new BigDecimal(manualStkout.get(i).getQuantity())))));
						if(!stocks.get(0).getStockOut().equals("0")){
							stockupdts.setStockOut(String.valueOf((new BigDecimal(stocks.get(0).getStockOut()).
									subtract(new BigDecimal(manualStkout.get(i).getQuantity())))));
						}						
						entityManager.merge(stockupdts);
						entityManager.flush();
						entityManager.clear();
					}
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public List<String> getdebtname(String personID, String clientID,String str) {
	Query q=null;List<String> staffList=new ArrayList<String>();
	try{
		if(str.equalsIgnoreCase("staff")){
			q=entityManager.createQuery("select employeeName from Employee where person_ID=? and client_ID=? and status='ACTIVE'");
		}else if(str.equalsIgnoreCase("subcon")){
			q=entityManager.createQuery("select vendorName from Vendor where person_ID=? and client_ID=? and status='ACTIVE'");
		}
		q.setParameter(1, personID);
		q.setParameter(2, clientID);
		staffList=q.getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
	return staffList;
}

@Transactional(value="transactionManager")
public String saveDebt(String personID, String clientID,FinanceDataBean financeDataBean) {
	String status="Fail";Query q=null;
	try{
			Debt debt=new Debt();
			debt.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
			debt.setClient(entityManager.find(Client.class,Integer.parseInt(clientID)));
			debt.setName(financeDataBean.getDebtName());
			debt.setDebtDate(financeDataBean.getDebtDate());
			debt.setAmount(financeDataBean.getDebtAmount());
			debt.setReason(financeDataBean.getDebtReason());
			debt.setCategory(financeDataBean.getDebtCategory());
			debt.setStatus("ACTIVE");
			debt.setDebtStatus(financeDataBean.getDebtStatus());
			debt.setBalance(financeDataBean.getDebtBalance());
			entityManager.persist(debt);
			entityManager.flush();
			entityManager.clear();
			status="Success";
	}catch(Exception e){
		e.printStackTrace();
	}
	return status;
}

public List<FinanceDataBean> getdebtList(String personID, String clientID,FinanceDataBean financeDataBean) {
	Query q=null;List<FinanceDataBean> domainList=new ArrayList<FinanceDataBean>();
	try{
			q=entityManager.createQuery("from Debt where person_ID=? and client_ID=? and debtStatus=? and status='ACTIVE' ORDER BY debtId DESC");
			q.setParameter(1, personID);
			q.setParameter(2, clientID);
			q.setParameter(3, financeDataBean.getDebtStatus());
			List<Debt> debtList=(List<Debt>)q.getResultList();
			if(debtList.size()>0){
				for (int i = 0; i < debtList.size(); i++) {
					FinanceDataBean fin=new FinanceDataBean();
					fin.setDebtCategory(debtList.get(i).getCategory());
					fin.setDebtName(debtList.get(i).getName());
					fin.setDebtDate(debtList.get(i).getDebtDate());
					fin.setDebtAmount(debtList.get(i).getAmount());
					fin.setDebtReason(debtList.get(i).getReason());
					fin.setDebtBalance(debtList.get(i).getBalance());
					fin.setDebtId(debtList.get(i).getDebtId());
					fin.setDebtStatus(debtList.get(i).getDebtStatus());
					domainList.add(fin);
				}
			}
	}catch(Exception e){
		e.printStackTrace();
	}
	return domainList;
}

public List<String> getdebtreceivename(String personID, String clientID,String str) {
	Query q=null;List<String> nameList=new ArrayList<String>();
	try{
		q=entityManager.createQuery("select name from Debt where person_ID=? and client_ID=? and category=? and status='ACTIVE' and debtStatus='taken'");
		q.setParameter(1,personID);
		q.setParameter(2,clientID);
		q.setParameter(3,str);
		nameList=q.getResultList();
	    HashSet<String> hashList=new HashSet<String>(nameList);
	    nameList.clear();
	    nameList.addAll(hashList);
	}catch(Exception e){
		e.printStackTrace();
	}
	return nameList;
}

public String getdebtnamevaluChange(String personID, String clientID,FinanceDataBean financeDataBean) {
	Query q=null;BigDecimal temp=BigDecimal.valueOf(0);BigDecimal temp1=BigDecimal.valueOf(0);
	try{
		q=entityManager.createQuery("from Debt where person_ID=? and client_ID=? and name=? and category=? and status='ACTIVE' and debtStatus='taken'");
		q.setParameter(1,personID);
		q.setParameter(2,clientID);
		q.setParameter(3,financeDataBean.getDebtName());
		q.setParameter(4,financeDataBean.getDebtCategory());
		List<Debt> debtList=(List<Debt>)q.getResultList();
		if(debtList.size()>0){
			for (int i = 0; i < debtList.size(); i++) {
				temp=temp.add(new BigDecimal(debtList.get(i).getAmount()));
			}
		}
		q=entityManager.createQuery("from Debt where person_ID=? and client_ID=? and name=? and category=? and status='ACTIVE' and debtStatus='receive'");
		q.setParameter(1,personID);
		q.setParameter(2,clientID);
		q.setParameter(3,financeDataBean.getDebtName());
		q.setParameter(4,financeDataBean.getDebtCategory());
		List<Debt> debtLists=(List<Debt>)q.getResultList();
		if(debtLists.size()>0){
			for (int i = 0; i < debtLists.size(); i++) {
				temp1=temp1.add(new BigDecimal(debtLists.get(i).getAmount()));
			}
		}
		financeDataBean.setDebtBalance(temp.subtract(temp1).toString());
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

public String getDebtView(String personID, String clientID,FinanceDataBean financeDataBean) {
	Query q=null;
	try{
		q=entityManager.createQuery("from Debt where person_ID=? and client_ID=? and debt_id=? and status='ACTIVE'");
		q.setParameter(1, personID);
		q.setParameter(2, clientID);
		q.setParameter(3, financeDataBean.getDebtId());
		List<Debt> debtList=(List<Debt>)q.getResultList();
		if(debtList.size()>0){
			financeDataBean.setDebtCategory(debtList.get(0).getCategory());
			financeDataBean.setDebtName(debtList.get(0).getName());
			financeDataBean.setDebtDate(debtList.get(0).getDebtDate());
			financeDataBean.setDebtAmount(debtList.get(0).getAmount());
			financeDataBean.setDebtReason(debtList.get(0).getReason());
			financeDataBean.setDebtBalance(debtList.get(0).getBalance());
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Transactional(value="transactionManager")
public String debitUpdate(String personID, String clientID,FinanceDataBean financeDataBean) {
	Query q=null;String status="Fail";
	try{
		q=entityManager.createQuery("from Debt where person_ID=? and client_ID=? and debt_id=? and status='ACTIVE'");
		q.setParameter(1, personID);
		q.setParameter(2, clientID);
		q.setParameter(3, financeDataBean.getDebtId());
		List<Debt> debtList=(List<Debt>)q.getResultList();
		if(debtList.size()>0){
			Debt debt=entityManager.find(Debt.class, debtList.get(0).getDebtId());
			debt.setAmount(financeDataBean.getDebtAmount());
			debt.setReason(financeDataBean.getDebtReason());
			if(financeDataBean.getDebtStatus().equalsIgnoreCase("receive")){
				debt.setBalance(new BigDecimal(financeDataBean.getDebtBalance()).subtract(new BigDecimal(financeDataBean.getDebtAmount())).toString());
			}else{
				debt.setBalance("");
			}
			entityManager.merge(debt);
			status="Success";
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return status;
}


@Override
public List<String> getmodelName(String clientID) {
	Query q=null;
	List<String> modelList=null;
	try{
		q=entityManager.createQuery("select model from Cutterpurchaseorder where client_ID=? and status='ACTIVE' and (invoiceStatus='INVOICE' or invoiceStatus='PAID')");
		q.setParameter(1, clientID);
		modelList=(ArrayList<String>)q.getResultList();
		Set<String> dublicate=new HashSet<String>(modelList);
		modelList.clear();
		modelList.addAll(dublicate);
	}catch(Exception e){
		e.printStackTrace();
	}
	return modelList;
}


@Override
public List<String> printerModellist(String clientID) {
	Query q=null;
	List<String> modelList=null;
	try{
		q=entityManager.createQuery("select model from Printerpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus='HOLDING'");
		q.setParameter(1, clientID);
		modelList=(ArrayList<String>)q.getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
	return modelList;
}

public List<EmployeeDataBean> getEmployeeReportList(String personID,String clientID) {
	Query q=null;List<EmployeeDataBean> employeeLists=new ArrayList<EmployeeDataBean>();
	try{
		q=entityManager.createQuery("from Employee where person_ID=? and client_ID=? and status='ACTIVE' ORDER BY employee_ID DESC");
		q.setParameter(1, personID);
		q.setParameter(2, clientID);
		List<Employee> employeeList=(List<Employee>)q.getResultList();
		if(employeeList.size()>0){
			for (int i = 0; i < employeeList.size(); i++) {
				EmployeeDataBean emp=new EmployeeDataBean();
				emp.setSerialNo(String.valueOf(i+1));
				emp.setBranchName(employeeList.get(i).getBranch().getBranchName());
				emp.setEmployeeName(employeeList.get(i).getEmployeeName());
				emp.setEntryDate(employeeList.get(i).getEntryDate());
				emp.setDaily(employeeList.get(i).getDaily());
				emp.setEntryDate(employeeList.get(i).getEntryDate());
				emp.setDateofBirth(employeeList.get(i).getDob());
				emp.setAddress(employeeList.get(i).getAddress());
				emp.setPhoneNo1(employeeList.get(i).getPhoneNo1());
				emp.setCommission(employeeList.get(i).getComission());
				q=null;
				q=entityManager.createQuery("from Branch where branchName=? and person_ID=? and client_ID=? and status='ACTIVE'");
				q.setParameter(1, employeeList.get(i).getBranch().getBranchName());
				q.setParameter(2, personID);
				q.setParameter(3, clientID);
				List<Branch> branchlist=(List<Branch>)q.getResultList();
				if(branchlist.size() > 0){
					emp.setOvertime(branchlist.get(0).getOvertime());
					emp.setLate(branchlist.get(0).getLate());
				}
				employeeLists.add(emp);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return employeeLists;
}

public String getModels(String personID, StockDataBean stockDataBean){
	Query v=null;	
	try{
		if(stockDataBean.getModelNo().equals("")){
			System.out.println("if");
			v=entityManager.createQuery("select itemName from Stock where person_ID=? and cmtStatus='CmtStock' and status='Active'");
			v.setParameter(1, Integer.parseInt(personID));
			List<String> models=(List<String>)v.getResultList();
			HashSet<String> model=new HashSet<String>(models);
			models.clear();
			models.addAll(model);
			stockDataBean.setCmtProducts(models);
		}else{
			System.out.println("else");
			v=null;
			v=entityManager.createQuery("select seriNo from Stock where person_ID=? and itemName=? and cmtStatus='CmtStock' and status='Active'");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, stockDataBean.getModelNo());
			List<String> seris=(List<String>)v.getResultList();
			System.out.println("seri "+seris.size());
			HashSet<String> seri=new HashSet<String>(seris);
			seris.clear();
			seris.addAll(seri);
			stockDataBean.setSeris(seris);
		}		
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

@Override
public List<StockDataBean> getStockDetails(String personID,StockDataBean stockDataBean) {
	List<StockDataBean> stockList=new ArrayList<StockDataBean>();
	List<Stock> stock=null;
	Query v=null;
	BigDecimal amount=BigDecimal.valueOf(0);
	try{
		if(stockDataBean.getStatus().equals("view")){
			v=entityManager.createQuery("from Stock where person_ID=? and itemName=? and cmtStatus='CmtStock' and status='Active'");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, stockDataBean.getModelNo());
		}else if(stockDataBean.getStatus().equals("edit")){
			if(stockDataBean.getType().equals("raw")){
				v=entityManager.createQuery("from Stock where person_ID=? and itemName=? and cmtStatus='Raw' and status='Active'");
				v.setParameter(1, Integer.parseInt(personID));
				v.setParameter(2, stockDataBean.getItemName());
			}else if(stockDataBean.getType().equals("ready")){
				v=entityManager.createQuery("from Stock where person_ID=? and itemName=? and cmtStatus='CmtStock' and status='Active'");
				v.setParameter(1, Integer.parseInt(personID));
				v.setParameter(2, stockDataBean.getModelNo());
			}
		}
		stock=(List<Stock>)v.getResultList();
		System.out.println("stock size "+stock.size());
		if(stock.size()>0){
			int no=1;
			for (int i = 0; i < stock.size(); i++) {
				StockDataBean stockouts=new StockDataBean();
				stockouts.setSerialNo(String.valueOf(no));
				stockouts.setWarehouseName(stock.get(i).getWarehouseStatus());
				stockouts.setQuantity(stock.get(i).getStockIn());
				stockouts.setQuantity1("");
				stockouts.setModelNo(stock.get(i).getItemName());
				stockouts.setSeri(stock.get(i).getSeriNo());
				stockouts.setItemName(stockDataBean.getItemName());
				stockouts.setMnaualStockID(stock.get(i).getStock_ID());
				if(stockDataBean.getStatus().equals("view")){
					v=null;
					v=entityManager.createQuery("from Model where model=? and status='ACTIVE'");
					v.setParameter(1, stock.get(i).getItemName());
					List<Model> model=(List<Model>)v.getResultList();
					if(model.size()>0){
						stockouts.setSellingPrice(model.get(0).getSellPrice());
					}		
				}else if(stockDataBean.getStatus().equals("edit")){
					if(stockDataBean.getType().equals("raw")){
						v=null;
						v=entityManager.createQuery("from ItemTable where itemName=? and status='ACTIVE'");
						v.setParameter(1, stockDataBean.getItemName());
						List<ItemTable> itemTable=(List<ItemTable>)v.getResultList();
						if(itemTable.size()>0){
							stockouts.setSellingPrice(itemTable.get(0).getSellPrice());
						}
					}else if(stockDataBean.getType().equals("ready")){
						v=null;
						v=entityManager.createQuery("from Model where model=? and status='ACTIVE'");
						v.setParameter(1, stock.get(i).getItemName());
					
						List<Model> model=(List<Model>)v.getResultList();
						if(model.size()>0){
							stockouts.setSellingPrice(model.get(0).getSellPrice());
						}		
					}
				}	
				amount=new BigDecimal(stockouts.getQuantity()).multiply(new BigDecimal(stockouts.getSellingPrice()));
				stockouts.setTotalPrice(amount.toString());
				stockList.add(stockouts);
				no++;
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return stockList;
}

public String getProductDetails(String personID, StockDataBean stockDataBean){
	Query v=null;	
	try{
		if(stockDataBean.getType().equals("raw")){
			System.out.println("if");
		}else if(stockDataBean.getType().equals("ready")){
			System.out.println("else");
			v=null;
			v=entityManager.createQuery("select itemName from Stock where person_ID=? and cmtStatus='CmtStock' and status='Active'");
			v.setParameter(1, Integer.parseInt(personID));
			List<String> models=(List<String>)v.getResultList();
			HashSet<String> model=new HashSet<String>(models);
			models.clear();
			models.addAll(model);
			stockDataBean.setCmtProducts(models);
		}		
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

public List<SupplyDataBean> getsupplyreportview(String personID,SupplyDataBean supplyDataBean) {
	Query q=null;List<SupplyDataBean> pursaleslist=new ArrayList<SupplyDataBean>();
	try{ 
		q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and status='INSERTED' ORDER BY pursalesitemId DESC");
		q.setParameter(1, personID);
		q.setParameter(2, supplyDataBean.getItemStatus());
		List<PursalesItem> pursalesList=(List<PursalesItem>)q.getResultList();
		if(pursalesList.size()>0){
				for (int i = 0; i < pursalesList.size(); i++) {
					SupplyDataBean sup=new SupplyDataBean();
					sup.setSerialNo(String.valueOf(i+1));
					sup.setTodayDate(pursalesList.get(i).getOrderDate());
					sup.setFabricInvoiceNumber(pursalesList.get(i).getInvoiceNumber());
					sup.setSupplierName(pursalesList.get(i).getSupplierName());
					sup.setVendorName(pursalesList.get(i).getVendorName());
					if(pursalesList.get(i).getStatus2().equals("PURCHASE")){
						sup.setTotalPrice(pursalesList.get(i).getTotalPrice());
						sup.setTotalAmount("");
					}else{
						sup.setTotalAmount(pursalesList.get(i).getTotalPrice());
						sup.setTotalPrice("");
					}
					sup.setInvoiceStatus(pursalesList.get(i).getInvoiceStatus());
					sup.setPaymentStatus(pursalesList.get(i).getPaymentStatus());
					sup.setFabricId(pursalesList.get(i).getPursalesitemId());
					sup.setStatus2(pursalesList.get(i).getStatus2());
					sup.setItemStatus(pursalesList.get(i).getItemStatus());
					pursaleslist.add(sup);
				}
			}
	}catch(Exception e){
		e.printStackTrace();
	}
	return pursaleslist;
}

@Transactional(value="transactionManager")
public void updateStocks(int stockID,String quantity,String motive) {
	Stock updtStk=entityManager.find(Stock.class, stockID);	
	updtStk.setStockIn(quantity);
	updtStk.setMotive(motive);
	entityManager.merge(updtStk);
	entityManager.flush();
	entityManager.clear();
}

public List<String> getStaffNames(String personID, String type){
	List<String> staffs=null;
	Query v=null;
	try{
		if(type.equals("staff")){
			v=entityManager.createQuery("select employeeName from Employee where person_ID=? and status='ACTIVE'");
			v.setParameter(1, Integer.parseInt(personID));
		}else{
			v=entityManager.createQuery("select vendorName from Vendor where person_ID=? and divisionVendor=? and status='ACTIVE'");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, type);
		}	
		staffs=(List<String>)v.getResultList();
		System.out.println("staffs "+staffs);
		HashSet<String> staff=new HashSet<String>(staffs);
		staffs.clear();
		staffs.addAll(staff);
	}catch(Exception e){
		e.printStackTrace();
	}
	return  staffs;		
}

@Transactional(value="transactionManager")
public void savePenalty(String personID, PayrollDataBean payrollDataBean){
	Penalty penalty=new Penalty();
	penalty.setDate(date);
	penalty.setAmount(payrollDataBean.getAmount());
	penalty.setReason(payrollDataBean.getReason());
	if(payrollDataBean.getType().equals("staff")) penalty.setType("employee");
	else penalty.setType(payrollDataBean.getType());
	penalty.setStaffName(payrollDataBean.getStaffName());
	penalty.setStatus("ACTIVE");
	penalty.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
	entityManager.persist(penalty);	
}

public List<PayrollDataBean> getPenaltyDetails(String personID){
	List<PayrollDataBean> penaltylist=new ArrayList<PayrollDataBean>();
	Query v=null;
	try{
		v=entityManager.createQuery("from Penalty where person_ID=? and status='ACTIVE' ORDER BY penalty_ID DESC");
		v.setParameter(1, Integer.parseInt(personID));
		List<Penalty> penalty=(List<Penalty>)v.getResultList();
		System.out.println("penalty size "+penalty.size());
		if(penalty.size()>0){
			for (int i = 0; i < penalty.size(); i++) {
				PayrollDataBean paylist=new PayrollDataBean();
				paylist.setDate(penalty.get(i).getDate());
				paylist.setAmount(penalty.get(i).getAmount());
				paylist.setReason(penalty.get(i).getReason());
				paylist.setPenaltID(penalty.get(i).getPenalty_ID());
				paylist.setType(penalty.get(i).getType());
				paylist.setStaffName(penalty.get(i).getStaffName());
				paylist.setSerialNo(String.valueOf(i+1));
				paylist.setAflag(true);
				paylist.setAflag1(false);
				paylist.setRflag(true);
				paylist.setRflag1(false);
				penaltylist.add(paylist);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return penaltylist;
}

@Transactional(value="transactionManager")
public void penaltyUpdate(PayrollDataBean payrollDataBean){
	if(payrollDataBean.getStatus().equals("edit")){
		Penalty penalty=entityManager.find(Penalty.class, payrollDataBean.getPenaltID());
		penalty.setAmount(payrollDataBean.getAmount());
		penalty.setReason(payrollDataBean.getReason());
		entityManager.merge(penalty);
	}else if(payrollDataBean.getStatus().equals("delete")){
		Penalty penalty=entityManager.find(Penalty.class, payrollDataBean.getPenaltID());
		penalty.setStatus("DEACTIVE");
		entityManager.merge(penalty);
	}	
}

public List<StockDataBean> getrawstockreport(String personID,StockDataBean stockDataBean) {
	Query q=null;List<StockDataBean> domainList=new ArrayList<StockDataBean>();
	try{
		if(stockDataBean.getType().equalsIgnoreCase("raw")){
			q=entityManager.createQuery("from Stock where person_ID=? and cmtStatus='Raw' and status='ACTIVE'");
			q.setParameter(1, personID);
		}
		else if(stockDataBean.getType().equalsIgnoreCase("ready")){
			q=entityManager.createQuery("from Stock where person_ID=? and itemName=? and cmtStatus='CmtStock' and status='ACTIVE'");
			q.setParameter(1, personID);
			q.setParameter(2, stockDataBean.getModelNo());
		}
		List<Stock> stockList=(List<Stock>)q.getResultList();
		if(stockList.size()>0){
			for (int i = 0; i < stockList.size(); i++) {
				StockDataBean stock=new StockDataBean();
				stock.setSerialNo(String.valueOf(i+1));
				stock.setItemName(stockList.get(i).getItemName());
				stock.setStockIn(stockList.get(i).getStockIn());
				stock.setStockOut(stockList.get(i).getStockOut());
				stock.setWarehouseName(stockList.get(i).getWarehouseStatus());
				if(stockDataBean.getType().equalsIgnoreCase("raw")){
					stock.setSellingPrice(stockList.get(i).getItemTable().getSellPrice());
				}else if(stockDataBean.getType().equalsIgnoreCase("ready")){
					stock.setSeri(stockList.get(i).getSeriNo());
					q=null;
					q=entityManager.createQuery("from Model where person_ID=? and model=? and status='ACTIVE'");
					q.setParameter(1,personID);
					q.setParameter(2,stockDataBean.getModelNo());
				
					List<Model> modelList=(List<Model>)q.getResultList();
					if(modelList.size()>0){
						for (int j = 0; j < modelList.size(); j++) {
							stock.setSellingPrice(modelList.get(j).getSellPrice());
						}
					}
				}
				domainList.add(stock);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return domainList;
}

public List<PayrollDataBean> getPurchasePayment(String personID,PayrollDataBean payrollDataBean){
	List<PayrollDataBean> purchaseList=new ArrayList<PayrollDataBean>();
	System.out.println("division "+payrollDataBean.getType());
	System.out.println("supplier "+payrollDataBean.getSupplier());
	System.out.println("from date "+payrollDataBean.getFromdate());
	System.out.println("to date "+payrollDataBean.getTodate());
	Query v=null;
	try{
		if(!payrollDataBean.getStatus().equals("FABRIC")){
			String itemstatus=payrollDataBean.getStatus()+" ITEM";
			System.out.println("-------"+payrollDataBean.getFromdate());
			System.out.println("-------"+payrollDataBean.getTodate());
			if((!payrollDataBean.getType().equals("select")|| payrollDataBean.getType()!=null) && (payrollDataBean.getSupplier().equals("") || payrollDataBean.getSupplier()==null) && payrollDataBean.getFromdate()==null && payrollDataBean.getTodate()==null){
				System.out.println("type"+Integer.parseInt(personID)+"----"+itemstatus);
				v=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and status2='PURCHASE' and status='INSERTED' ORDER BY orderDate DESC");
				v.setParameter(1, Integer.parseInt(personID));
				v.setParameter(2, itemstatus);
			}
			else if((!payrollDataBean.getType().equals("select")|| payrollDataBean.getType()!=null) && (!payrollDataBean.getSupplier().equals("") || payrollDataBean.getSupplier()!=null) && payrollDataBean.getFromdate()==null && payrollDataBean.getTodate()==null){
				System.out.println("type,supplier");
				v=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and supplierName=? and status2='PURCHASE' and status='INSERTED' ORDER BY orderDate DESC");
				v.setParameter(1, Integer.parseInt(personID));
				v.setParameter(2, itemstatus);
				v.setParameter(3, payrollDataBean.getSupplier());
			}
			else if((!payrollDataBean.getType().equals("select")|| payrollDataBean.getType()!=null) && (payrollDataBean.getSupplier().equals("") || payrollDataBean.getSupplier()==null) && payrollDataBean.getFromdate()!=null && payrollDataBean.getTodate()!=null){
				System.out.println("type,fromdate,todate");
				v=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status2='PURCHASE' and status='INSERTED' ORDER BY orderDate DESC");
				v.setParameter(1, Integer.parseInt(personID));
				v.setParameter(2, itemstatus);
				v.setParameter(3, payrollDataBean.getFromdate());
				v.setParameter(4, payrollDataBean.getTodate());
			}
			else if((!payrollDataBean.getType().equals("select")|| payrollDataBean.getType()!=null) && (!payrollDataBean.getSupplier().equals("") || payrollDataBean.getSupplier()!=null) && payrollDataBean.getFromdate()!=null && payrollDataBean.getTodate()!=null){
				System.out.println("type,fromdate,todate,supplier");
				v=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and supplierName=? and status2='PURCHASE' and status='INSERTED' ORDER BY orderDate DESC");
				v.setParameter(1, Integer.parseInt(personID));
				v.setParameter(2, itemstatus);
				v.setParameter(3, payrollDataBean.getSupplier());
				v.setParameter(4, payrollDataBean.getFromdate());
				v.setParameter(5, payrollDataBean.getTodate());
			}
			List<PursalesItem> pursalesList=(List<PursalesItem>)v.getResultList();
			System.out.println("pursalesList --- "+pursalesList.size());
			if(pursalesList.size()>0){
				for (int i = 0; i < pursalesList.size(); i++) {
					PayrollDataBean purchases=new PayrollDataBean();
					purchases.setDate(pursalesList.get(i).getOrderDate());
					purchases.setSerialNo(String.valueOf(i+1));
					purchases.setInvoiceNo(pursalesList.get(i).getInvoiceNumber());
					purchases.setAmount(pursalesList.get(i).getTotalPrice());
					purchases.setStaffName(pursalesList.get(i).getSupplierName());
					purchases.setPurchaseId(pursalesList.get(i).getPursalesitemId());
					purchases.setAflag(false);
					if(pursalesList.get(i).getInvoiceStatus().equals("Paid")){
						purchases.setStatus("Paid");
						purchases.setFlag(false);
					}
					else{
						purchases.setStatus("UnPaid");
						purchases.setFlag(true);
					}
					v=null;
					v=entityManager.createQuery("from Payment where pursalesitem_id=?");
					v.setParameter(1, pursalesList.get(i).getPursalesitemId());
					List<Payment> payment=(List<Payment>)v.getResultList();
					if(payment.size()>0){
						purchases.setBalAmount(payment.get(0).getBalanceAmount());
					}else{
						purchases.setBalAmount(pursalesList.get(i).getTotalPrice());
					}
					purchaseList.add(purchases);
				}				
			}
		}else{
			if(!payrollDataBean.getType().equals("select")|| payrollDataBean.getType()!=null && payrollDataBean.getSupplier()==null && payrollDataBean.getFromdate()==null && payrollDataBean.getTodate()==null){
				v=entityManager.createQuery("from Fabric where person_ID=? and status='INSERTED' ORDER BY purchaseDate DESC");
				v.setParameter(1, Integer.parseInt(personID));
			}
			else if(!payrollDataBean.getType().equals("select")|| payrollDataBean.getType()!=null && payrollDataBean.getSupplier()!=null && payrollDataBean.getFromdate()==null && payrollDataBean.getTodate()==null){
				v=entityManager.createQuery("from Fabric where person_ID=? and supplierName=? and status='INSERTED' ORDER BY purchaseDate DESC");
				v.setParameter(1, Integer.parseInt(personID));
				v.setParameter(2, payrollDataBean.getSupplier());
			}
			else if(!payrollDataBean.getType().equals("select")|| payrollDataBean.getType()!=null && payrollDataBean.getSupplier()==null && payrollDataBean.getFromdate()!=null && payrollDataBean.getTodate()!=null){
				v=entityManager.createQuery("from Fabric where person_ID=? and purchaseDate between ? and ? and status='INSERTED' ORDER BY purchaseDate DESC");
				v.setParameter(1, Integer.parseInt(personID));
				v.setParameter(2, payrollDataBean.getFromdate());
				v.setParameter(3, payrollDataBean.getTodate());
			}
			else if(!payrollDataBean.getType().equals("select")|| payrollDataBean.getType()!=null && payrollDataBean.getSupplier()!=null && payrollDataBean.getFromdate()!=null && payrollDataBean.getTodate()!=null){
				v=entityManager.createQuery("from Fabric where person_ID=? and supplierName=? and purchaseDate between ? and ? and status='INSERTED' ORDER BY purchaseDate DESC");
				v.setParameter(1, Integer.parseInt(personID));
				v.setParameter(2, payrollDataBean.getSupplier());
				v.setParameter(3, payrollDataBean.getFromdate());
				v.setParameter(4, payrollDataBean.getTodate());
			}
			List<Fabric> fabriclist=(List<Fabric>)v.getResultList();
			System.out.println("fabriclist --- "+fabriclist.size());
			if(fabriclist.size()>0){
				for (int i = 0; i < fabriclist.size(); i++) {
					PayrollDataBean fabricList=new PayrollDataBean();
					fabricList.setDate(fabriclist.get(i).getPurchaseDate());
					fabricList.setSerialNo(String.valueOf(i+1));
					fabricList.setInvoiceNo(fabriclist.get(i).getInvoiceNumber());
					fabricList.setAmount(fabriclist.get(i).getTotalAmount());
					fabricList.setStaffName(fabriclist.get(i).getSupplierName());
					fabricList.setName(fabriclist.get(i).getFabricName());
					fabricList.setPurchaseId(fabriclist.get(i).getFabricId());
					fabricList.setAflag(false);
					if(fabriclist.get(i).getInvoiceStatus().equals("Paid")){
						fabricList.setStatus("Paid");
						fabricList.setFlag(false);
					}
					else{
						fabricList.setStatus("UnPaid");
						fabricList.setFlag(true);
					}
					v=null;
					v=entityManager.createQuery("from Payment where fabric_id=?");
					v.setParameter(1, fabriclist.get(i).getFabricId());
					List<Payment> payment=(List<Payment>)v.getResultList();
					if(payment.size()>0){
						fabricList.setBalAmount(payment.get(0).getBalanceAmount());
					}else{
						fabricList.setBalAmount(fabriclist.get(i).getTotalAmount());
					}
					purchaseList.add(fabricList);
				}
			}
		}		
	}catch(Exception e){
		e.printStackTrace();
	}
	return purchaseList;
}

@Transactional(value="transactionManager")
public String payPurchases(String personID, PayrollDataBean payrollDataBean){
	Query v=null;
	String status="Fail";
	try{		
		for (int i = 0; i < payrollDataBean.getPayrollList().size(); i++) {
			if(payrollDataBean.getPayrollList().get(i).isAflag()==true){
				if(!payrollDataBean.getType().equals("FABRIC")){
					v=entityManager.createQuery("from Payment where pursalesitem_id=?");
				}else if(payrollDataBean.getType().equals("FABRIC")){		
					v=entityManager.createQuery("from Payment where fabric_id=?");
				}
				v.setParameter(1, payrollDataBean.getPayrollList().get(i).getPurchaseId());
				List<Payment> payment=(List<Payment>)v.getResultList();
				System.out.println("payment size "+payment.size());
				if(payment.size()==0){
					Payment payInsert=new Payment();
					payInsert.setBalanceAmount("0");
					payInsert.setPaidAmount(payrollDataBean.getPayrollList().get(i).getAmount());
					payInsert.setTotalAmount(payrollDataBean.getPayrollList().get(i).getAmount());
					payInsert.setStatus("paid");
					payInsert.setDate(date);
					if(!payrollDataBean.getType().equals("FABRIC")){
						payInsert.setPursalesItem(entityManager.find(PursalesItem.class, payrollDataBean.getPayrollList().get(i).getPurchaseId()));
					}else{
						payInsert.setFabric(entityManager.find(Fabric.class, payrollDataBean.getPayrollList().get(i).getPurchaseId()));
					}					
					entityManager.persist(payInsert);
					entityManager.flush();
					entityManager.clear();	
					status="Success";
				}else{
					Payment payUpdate=entityManager.find(Payment.class, payment.get(0).getPayment_ID());
					payUpdate.setPaidAmount(String.valueOf(new BigDecimal(payment.get(0).getPaidAmount()).add(new BigDecimal(payrollDataBean.getPayrollList().get(i).getBalAmount()))));
					payUpdate.setBalanceAmount(String.valueOf(new BigDecimal(payment.get(0).getBalanceAmount()).subtract(new BigDecimal(payrollDataBean.getPayrollList().get(i).getBalAmount()))));
					payUpdate.setStatus("Paid");
					entityManager.merge(payUpdate);	
					entityManager.flush();
					entityManager.clear();
					status="Success";
				}
				if(!payrollDataBean.getType().equals("FABRIC")){
					PursalesItem puritem=entityManager.find(PursalesItem.class, payrollDataBean.getPayrollList().get(i).getPurchaseId());
					puritem.setInvoiceStatus("Paid");
					entityManager.merge(puritem);
					entityManager.flush();
					entityManager.clear();	
				}else{
					Fabric puritem=entityManager.find(Fabric.class, payrollDataBean.getPayrollList().get(i).getPurchaseId());
					puritem.setInvoiceStatus("Paid");
					entityManager.merge(puritem);
					entityManager.flush();
					entityManager.clear();	
				}			
			}
		}	
	}catch(Exception e){
		e.printStackTrace();
	}	
	return status;
}

public List<FinanceDataBean> getIncomedetails(String personID,FinanceDataBean financeDataBean){
	List<FinanceDataBean> incomeList=new ArrayList<FinanceDataBean>();
	Query v=null;
	try{
		v=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and status2='SALES' and invoiceStatus='Paid' and status='INSERTED' ORDER BY pursalesitemId DESC");
		v.setParameter(1, Integer.parseInt(personID));
		v.setParameter(2, financeDataBean.getDebtStatus());
		List<PursalesItem> pursalesList=(List<PursalesItem>)v.getResultList();
		if(pursalesList.size()>0){
			for (int i = 0; i < pursalesList.size(); i++) {
				FinanceDataBean incomes=new FinanceDataBean();
				incomes.setDebtDate(pursalesList.get(i).getOrderDate());
				incomes.setSerialNo(String.valueOf(i+1));
				incomes.setInvoiceNo(pursalesList.get(i).getInvoiceNumber());
				incomes.setDebtAmount(pursalesList.get(i).getTotalPrice());
				incomes.setDebtName(pursalesList.get(i).getVendorName());
				incomes.setDebtStatus("Paid");
				v=null;
				v=entityManager.createQuery("from Payment where pursalesitem_id=?");
				v.setParameter(1, pursalesList.get(i).getPursalesitemId());
				List<Payment> payment=(List<Payment>)v.getResultList();
				if(payment.size()>0){
					incomes.setDebtBalance(payment.get(0).getBalanceAmount());
				}else{
					incomes.setDebtBalance(pursalesList.get(i).getTotalPrice());
				}
				incomeList.add(incomes);
			}				
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return incomeList;
}


public String getMonthYears(String personID, PayrollDataBean payrollDataBean) {
	Query v=null;
	List<String> months=null;
	List<String> years=null;
	List<String> employees=null;
	try{
		v=entityManager.createQuery("select type from Month");
		months=(List<String>)v.getResultList();
		System.out.println("month "+months.size());
		v=null;
		v=entityManager.createQuery("select type from Year");
		years=(List<String>)v.getResultList();
		System.out.println("year "+years.size());
		v=null;
		v=entityManager.createQuery("select employeeName from Employee where person_ID=? and status='ACTIVE'");
		v.setParameter(1, Integer.parseInt(personID));
		employees=(List<String>)v.getResultList();
		System.out.println("employee "+employees.size());
		payrollDataBean.setMonths(months);
		payrollDataBean.setYears(years);
		payrollDataBean.setEmployees(employees);
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

public String getemployeeDetails(String personID,PayrollDataBean payrollDataBean){
	Query v=null;
	String status="Fail";
	System.out.println("month "+payrollDataBean.getMonth()+" year "+payrollDataBean.getYear());
	try{		
		v=entityManager.createQuery("from Employee where person_ID=? and employeeName=? and status='ACTIVE'");
		v.setParameter(1, Integer.parseInt(personID));
		v.setParameter(2, payrollDataBean.getEmployee());
		List<Employee> employee=(List<Employee>)v.getResultList();
		if(employee.size()>0){
			v=entityManager.createQuery("from Month where type=?");
			v.setParameter(1, payrollDataBean.getMonth());
			List<Month> month=(List<Month>)v.getResultList();
			v=null;
			v=entityManager.createQuery("from Year where type=?");
			v.setParameter(1, payrollDataBean.getYear());
			List<Year> year=(List<Year>)v.getResultList();
			v=null;
			v=entityManager.createQuery("from EmployeePayroll where employee_ID=? and month_id=? and year_id=? and status='Active'");
			v.setParameter(1, employee.get(0).getEmployee_ID());
			v.setParameter(2, month.get(0).getMonthId());
			v.setParameter(3, year.get(0).getYearId());
			List<EmployeePayroll> employeePayroll=(List<EmployeePayroll>)v.getResultList();
			if(employeePayroll.size()>0){
				status="Exist";
			}else{
				payrollDataBean.setEmployee(employee.get(0).getEmployeeName());
				payrollDataBean.setBranch(employee.get(0).getBranch().getBranchName());
			}
			getPayrolls(employee,personID,payrollDataBean);			
		}
	}catch(Exception e){
		e.printStackTrace();
	}	
	return status;
}

private void getPayrolls(List<Employee> employee, String personID, PayrollDataBean payrollDataBean) {
	Query v=null;SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
	BigDecimal taken=BigDecimal.valueOf(0),receive=BigDecimal.valueOf(0),loan=BigDecimal.valueOf(0);
	BigDecimal bonus=BigDecimal.valueOf(0),overTime=BigDecimal.valueOf(0);
	BigDecimal attend=BigDecimal.valueOf(0),over=BigDecimal.valueOf(0);
	int month=0;int year=Integer.parseInt(payrollDataBean.getYear());
	try{		
		if(payrollDataBean.getMonth().equals("January")) month=0;
		else if(payrollDataBean.getMonth().equals("February")) month=1;
		else if(payrollDataBean.getMonth().equals("March")) month=2;
		else if(payrollDataBean.getMonth().equals("April")) month=3;
		else if(payrollDataBean.getMonth().equals("May")) month=4;
		else if(payrollDataBean.getMonth().equals("June")) month=5;
		else if(payrollDataBean.getMonth().equals("July")) month=6;
		else if(payrollDataBean.getMonth().equals("August")) month=7;
		else if(payrollDataBean.getMonth().equals("September")) month=8;
		else if(payrollDataBean.getMonth().equals("October")) month=9;
		else if(payrollDataBean.getMonth().equals("November")) month=10;
		else if(payrollDataBean.getMonth().equals("December")) month=11;
		v=entityManager.createQuery("from Debt where name=? and person_ID=? and category='staff' and status='ACTIVE'");
		v.setParameter(1, employee.get(0).getEmployeeName());
		v.setParameter(2, Integer.parseInt(personID));
		List<Debt> debt=(List<Debt>)v.getResultList();
		if(debt.size()>0){
			for (int i = 0; i <debt.size(); i++) {
				if(debt.get(i).getDebtStatus().equals("taken")) taken=taken.add(new BigDecimal(debt.get(i).getAmount()));
				else if(debt.get(i).getDebtStatus().equals("receive")) receive=receive.add(new BigDecimal(debt.get(i).getAmount()));
			}
			if(taken.compareTo(receive)==1){
				loan=taken.subtract(receive);
				payrollDataBean.setLoanAmount(String.valueOf(loan));
			}
			else if(taken.compareTo(receive)==-1){
				loan=receive.subtract(taken);		
				payrollDataBean.setLoanAmount(String.valueOf("-"+loan));
			}
		}else{
			payrollDataBean.setLoanAmount("0");
		}	
		System.out.println("taken "+taken+" receive "+receive+" loan "+loan);			
		v=null;
		v=entityManager.createQuery("from Attendance where employee_ID=? and status='ACTIVE'");
		v.setParameter(1, employee.get(0).getEmployee_ID());
		List<Attendance> attendance=(List<Attendance>)v.getResultList();
		if(attendance.size()>0){
			for (int i = 0; i < attendance.size(); i++) {		
				int years=Integer.parseInt(simpleDateFormat.format(attendance.get(i).getDate()));
				System.out.println(" month "+month+" - "+attendance.get(i).getDate().getMonth()+" year "+simpleDateFormat.format(attendance.get(i).getDate()));
				if(month==attendance.get(i).getDate().getMonth() && year==years){
					attend=attend.add(new BigDecimal(attendance.get(i).getAttend()));
					over=over.add(new BigDecimal(attendance.get(i).getOverTime()));
				}
			}
			System.out.println("bonus "+attend +" over time "+over);
			System.out.println("bonus amount "+employee.get(0).getBranch().getBonus()+" over time "+employee.get(0).getBranch().getOvertime());
			int bonusAmt=Integer.parseInt(employee.get(0).getBranch().getBonus())/6;
			bonus=new BigDecimal(bonusAmt).multiply(attend);
			overTime=new BigDecimal(employee.get(0).getBranch().getOvertime()).multiply(over);
		}
		System.out.println("attendance bounus "+bonus+" -over - "+over);
		payrollDataBean.setAttendanceBonus(String.valueOf(bonus));
		payrollDataBean.setOverTime(String.valueOf(over));
	}catch(Exception e){
		e.printStackTrace();
	}
}


@Transactional(value="transactionManager")
public String payrollSave(String personID, PayrollDataBean payrollDataBean){
	Query v=null;
	BigDecimal total=BigDecimal.valueOf(0);
	try{		
		v=entityManager.createQuery("from Employee where person_ID=? and employeeName=? and status='ACTIVE'");
		v.setParameter(1, Integer.parseInt(personID));
		v.setParameter(2, payrollDataBean.getEmployee());
		List<Employee> employee=(List<Employee>)v.getResultList();
		if(employee.size()>0){
			v=entityManager.createQuery("from Month where type=?");
			v.setParameter(1, payrollDataBean.getMonth());
			List<Month> month=(List<Month>)v.getResultList();
			v=null;
			v=entityManager.createQuery("from Year where type=?");
			v.setParameter(1, payrollDataBean.getYear());
			List<Year> year=(List<Year>)v.getResultList();
			if(payrollDataBean.getLoanAmount().equals("")){
				total=new BigDecimal(payrollDataBean.getSalary()).add(new BigDecimal(payrollDataBean.getOverTime())).
						add(new BigDecimal(payrollDataBean.getAttendanceBonus())).subtract(BigDecimal.valueOf(0));
			}else{
				total=new BigDecimal(payrollDataBean.getSalary()).add(new BigDecimal(payrollDataBean.getOverTime())).
						add(new BigDecimal(payrollDataBean.getAttendanceBonus())).subtract(new BigDecimal(payrollDataBean.getLoanAmount()));
			}			
			EmployeePayroll emplPayroll=new EmployeePayroll();
			emplPayroll.setEmployee(entityManager.find(Employee.class,employee.get(0).getEmployee_ID()));
			emplPayroll.setMonth(entityManager.find(Month.class,month.get(0).getMonthId()));
			emplPayroll.setYear(entityManager.find(Year.class,year.get(0).getYearId()));
			emplPayroll.setSalary(payrollDataBean.getSalary());
			emplPayroll.setOverTime(payrollDataBean.getOverTime());
			emplPayroll.setLoanAmount(payrollDataBean.getLoanAmount());
			emplPayroll.setAttendanceBonus(payrollDataBean.getAttendanceBonus());
			emplPayroll.setStatus("Active");
			emplPayroll.setMonthSalary(String.valueOf(total));
			entityManager.persist(emplPayroll);
		}
	}catch(Exception e){
		e.printStackTrace();
	}	
	return null;
}

public String getEmployeePayrolls(String personID,PayrollDataBean payrollDataBean) {
	Query v=null;
	List<PayrollDataBean> paylist=new ArrayList<PayrollDataBean>();
	try{		
		v=entityManager.createQuery("from EmployeePayroll where status='Active' ORDER BY payroll_ID DESC");
		List<EmployeePayroll> employeePayroll=(List<EmployeePayroll>)v.getResultList();
		if(employeePayroll.size()>0){
			int serialno=1;
			for (int i = 0; i < employeePayroll.size(); i++) {
				if(employeePayroll.get(i).getEmployee().getPerson().getPerson_ID()==Integer.parseInt(personID)){
					PayrollDataBean payrolls=new PayrollDataBean();
					payrolls.setEmployee(employeePayroll.get(i).getEmployee().getEmployeeName());
					payrolls.setBranch(employeePayroll.get(i).getEmployee().getBranch().getBranchName());
					payrolls.setMonth(employeePayroll.get(i).getMonth().getType());
					payrolls.setYear(employeePayroll.get(i).getYear().getType());
					payrolls.setMonthSalary(employeePayroll.get(i).getSalary());
					payrolls.setSerialNo(String.valueOf(serialno));
					payrolls.setPenaltID(employeePayroll.get(i).getPayroll_ID());
					paylist.add(payrolls);
					serialno++;
				}
			}
			payrollDataBean.setPayrollList(paylist);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

public String getPayrollDetails(String personID,PayrollDataBean payrollDataBean){
	Query v=null;
	try{
		v=entityManager.createQuery("from Employee where person_ID=? and employeeName=? and status='ACTIVE'");
		v.setParameter(1, Integer.parseInt(personID));
		v.setParameter(2, payrollDataBean.getEmployee());
		List<Employee> employee=(List<Employee>)v.getResultList();
		if(employee.size()>0){
			v=entityManager.createQuery("from Month where type=?");
			v.setParameter(1, payrollDataBean.getMonth());
			List<Month> month=(List<Month>)v.getResultList();
			v=null;
			v=entityManager.createQuery("from Year where type=?");
			v.setParameter(1, payrollDataBean.getYear());
			List<Year> year=(List<Year>)v.getResultList();
			v=entityManager.createQuery("from EmployeePayroll where employee_ID=? and month_id=? and year_id=? and status='Active'");
			v.setParameter(1, employee.get(0).getEmployee_ID());
			v.setParameter(2, month.get(0).getMonthId());
			v.setParameter(3, year.get(0).getYearId());
			List<EmployeePayroll> employeePayroll=(List<EmployeePayroll>)v.getResultList();
			if(employeePayroll.size()>0){
				payrollDataBean.setEmployee(employeePayroll.get(0).getEmployee().getEmployeeName());
				payrollDataBean.setBranch(employeePayroll.get(0).getEmployee().getBranch().getBranchName());
				payrollDataBean.setMonth(employeePayroll.get(0).getMonth().getType());
				payrollDataBean.setYear(employeePayroll.get(0).getYear().getType());
				payrollDataBean.setMonthSalary(employeePayroll.get(0).getMonthSalary());
				payrollDataBean.setSalary(employeePayroll.get(0).getSalary());
				payrollDataBean.setOverTime(employeePayroll.get(0).getOverTime());
				payrollDataBean.setAttendanceBonus(employeePayroll.get(0).getAttendanceBonus());
				payrollDataBean.setLoanAmount(employeePayroll.get(0).getLoanAmount());
				payrollDataBean.setMonthSalary(employeePayroll.get(0).getMonthSalary());
			}
		}		
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Transactional(value="transactionManager")
public String updatePayroll(PayrollDataBean payrollDataBean){
	BigDecimal total=BigDecimal.valueOf(0);
	if(payrollDataBean.getStatus().equals("edit")){
		if(payrollDataBean.getLoanAmount().equals("")){
			total=new BigDecimal(payrollDataBean.getSalary()).add(new BigDecimal(payrollDataBean.getOverTime())).
					add(new BigDecimal(payrollDataBean.getAttendanceBonus())).subtract(BigDecimal.valueOf(0));
		}else{
			total=new BigDecimal(payrollDataBean.getSalary()).add(new BigDecimal(payrollDataBean.getOverTime())).
					add(new BigDecimal(payrollDataBean.getAttendanceBonus())).subtract(new BigDecimal(payrollDataBean.getLoanAmount()));
		}
		EmployeePayroll emplPayroll=entityManager.find(EmployeePayroll.class, payrollDataBean.getPenaltID());
		emplPayroll.setSalary(payrollDataBean.getSalary());
		emplPayroll.setOverTime(payrollDataBean.getOverTime());
		emplPayroll.setLoanAmount(payrollDataBean.getLoanAmount());
		emplPayroll.setAttendanceBonus(payrollDataBean.getAttendanceBonus());
		emplPayroll.setMonthSalary(String.valueOf(total));
		entityManager.merge(emplPayroll);
		entityManager.flush();
		entityManager.clear();
	}else if(payrollDataBean.getStatus().equals("delete")){
		EmployeePayroll emplPayroll=entityManager.find(EmployeePayroll.class, payrollDataBean.getPenaltID());
		emplPayroll.setStatus("DeActive");
		entityManager.merge(emplPayroll);
		entityManager.flush();
		entityManager.clear();
	}
	return "";
}

@Override
public String getreturnsupply(String personID, ReturnDataBean returnDataBean) {
	Query v=null;
	List<String> itemname=new ArrayList<String>();
	try{
		v=entityManager.createQuery("select itemName from Stock where person_ID=? and warehouseStatus='Main Warehouse' and cmtStatus='Raw' and status='Active'");
		v.setParameter(1, Integer.parseInt(personID));
		itemname=v.getResultList();
		returnDataBean.setItemnamelist(itemname);	
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Override
public String datatable(String personID, ReturnDataBean returnDataBean) {
	Query v=null;
	List<ReturnDataBean> item=new ArrayList<ReturnDataBean>();	
	try{
		System.out.println("item name"+returnDataBean.getItemname());
		v=entityManager.createQuery("from Stock where person_ID=? and warehouseStatus='Main Warehouse' and itemName=? and cmtStatus='Raw' and status='Active'");
		v.setParameter(1, Integer.parseInt(personID));
		v.setParameter(2, returnDataBean.getItemname());
		List<Stock> stockList=(List<Stock>)v.getResultList();
		System.out.println("size stock "+stockList.size());
		if(stockList.size()>0){
			for (int i = 0; i < stockList.size(); i++) {
				ReturnDataBean ret=new ReturnDataBean();
				ret.setItemname(stockList.get(i).getItemName());
				ret.setShoppname(stockList.get(i).getWarehouseStatus());
				ret.setSerialno(String.valueOf(i+1));
				ret.setQuantity("");
				ret.setNetprice("");
				System.out.println("eeeee"+stockList. get(i).getSeriNo());
				v=entityManager.createQuery("from ItemTable where itemName=? and status='Active'");
			    v.setParameter(1, returnDataBean.getItemname());
			    List<ItemTable>itemlist=(List<ItemTable>)v.getResultList();
			    if(itemlist.size()>0){
			    	ret.setPrice(itemlist.get(i).getSellPrice());
			    	ret.setStockin(stockList.get(i).getStockIn());				
					item.add(ret);
					System.out.println("item name"+item.get(i).getStockin());						
			    }
			}
			returnDataBean.setReturnDataBeanList(item);
			System.out.println("item return"+returnDataBean.getReturnDataBeanList().size());
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}


@Override
@Transactional(value="transactionManager")
public String save(String personID, ReturnDataBean returnDataBean) {
	Query v=null;
	try{
		 for (int i = 0; i < returnDataBean.getReturnDataBeanList().size(); i++) {
			 v=entityManager.createQuery("from Stock where itemName=? and cmtStatus='Raw' and status='Active'");
			 v.setParameter(1, returnDataBean.getReturnDataBeanList().get(i).getItemname());
			 List<Stock> stocks=(List<Stock>)v.getResultList();
			 if(stocks.size()>0){
				Stock stockupdt=entityManager.find(Stock.class, stocks.get(0).getStock_ID());
				stockupdt.setStockIn(String.valueOf(new BigDecimal(stocks.get(0).getStockIn()).
						subtract(new BigDecimal(returnDataBean.getReturnDataBeanList().get(i).getQuantity())))); 
				entityManager.merge(stockupdt);
				entityManager.flush();
				entityManager.clear();
				v=entityManager.createQuery("from SupplyReturn where date=? and itemName=? and status='Active'");			
			    v.setParameter(1, returnDataBean.getDate());
			    v.setParameter(2, returnDataBean.getItemname());
			    List<SupplyReturn> supplys=(List<SupplyReturn>)v.getResultList();
			    if (supplys.size()>0) {
			    	SupplyReturn supplysupdt=entityManager.find(SupplyReturn.class,supplys.get(0).getSupplyReturn_id());
			    	supplysupdt.setQuantity(String.valueOf(new BigDecimal(supplys.get(0).getQuantity()).
			    	add(new BigDecimal(returnDataBean.getReturnDataBeanList().get(i).getQuantity()))));
			    	
			    }
		    	else {
			         SupplyReturn insert=new SupplyReturn();
			         insert.setDate(returnDataBean.getDate());
			         insert.setItemName(returnDataBean.getReturnDataBeanList().get(i).getItemname());
			         insert.setPrice(returnDataBean.getReturnDataBeanList().get(i).getPrice());
			         insert.setQuantity(returnDataBean.getReturnDataBeanList().get(i).getQuantity());
			         insert.setPerson(entityManager.find(Person.class,Integer.parseInt(personID)));
			         insert.setNetAmount(returnDataBean.getReturnDataBeanList().get(i).getNetprice());
			         insert.setStatus("Active");
			         entityManager.persist(insert);		    		
				}					
			}
		 }
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}


@Override
public String action(String personID, ReturnDataBean returnDataBean) {
	Query q=null;  
	try{
		System.out.println("inside action"+personID);
		q=entityManager.createQuery("from SupplyReturn where status='Active' and person_id=? ORDER BY supplyReturn_id DESC");
		q.setParameter(1, Integer.parseInt(personID));
		List<SupplyReturn> supply=(List<SupplyReturn>)q.getResultList();
		System.out.println("size ---->"+supply.size());
		List<ReturnDataBean> actionnew=new ArrayList<ReturnDataBean>();
		if (supply.size()>0) {
			for (int i = 0; i < supply.size(); i++) {
				ReturnDataBean views=new ReturnDataBean();
				views.setSerialno(String.valueOf(i+1));
				views.setDate(supply.get(i).getDate());
				views.setItemname(supply.get(i).getItemName());
				views.setPrice(supply.get(i).getPrice());
				views.setQuantity(supply.get(i).getQuantity());
				views.setQuantity1(supply.get(i).getQuantity());
				views.setNetamount(supply.get(i).getNetAmount());
				actionnew.add(views);
			
		}
		
		returnDataBean.setReturnDataBeanList(actionnew);
		System.out.println("------------------------->>>>>>"+returnDataBean.getReturnDataBeanList().size());
		}
		
		
	}
catch(Exception e){
	
}
	return null;
}


@Transactional(value="transactionManager")
@Override
public String cutterInvoice(String personID, String clientID,
		ProductionDataBean productionDataBean) {
	Query q=null;
	String status="fail";
	int id=0;
	if(personID!=null && clientID!=null){
		try{
			 q=entityManager.createQuery("from Cutterpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
			 q.setParameter(1, productionDataBean.getInvoiceNo());
			 q.setParameter(2, Integer.parseInt(clientID));
			 List<Cutterpurchaseorder> purchaseList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
			 if(purchaseList.size()>0){
				 id=purchaseList.get(0).getPurchase_ID();
				 Cutterpurchaseorder cutter=entityManager.find(Cutterpurchaseorder.class, id);
				 cutter.setInvoiceStatus("INVOICE");
				 entityManager.merge(cutter);
				 status="Success";
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return status;
}

@Transactional(value="transactionManager")
@Override
public String cutterPayment(String personID, String clientID,
		ProductionDataBean productionDataBean) {
	Query q=null;
	if(personID!=null && clientID!=null){
		try{
			q=entityManager.createQuery("from Cutterpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
			 q.setParameter(1, productionDataBean.getInvoiceNo());
			 q.setParameter(2, Integer.parseInt(clientID));
			 List<Cutterpurchaseorder> purchaseList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
			 if(purchaseList.size()>0){
				 productionDataBean.setOrderDate(purchaseList.get(0).getOrderDate());
				 productionDataBean.setCutter(purchaseList.get(0).getCutter());
				 productionDataBean.setCategoryName(purchaseList.get(0).getCategory());
				 productionDataBean.setModelName(purchaseList.get(0).getModel());
				 productionDataBean.setTotalAmount(purchaseList.get(0).getValue());
				 q=null;
				 q=entityManager.createQuery("from Payment where cutter_ID=?");
				 q.setParameter(1, purchaseList.get(0).getPurchase_ID());
				 List<Payment> paymentList=(List<Payment>)q.getResultList();
				 if(paymentList.size()==0){
					 Payment payment=new Payment();
					 payment.setCutterPurchase(entityManager.find(Cutterpurchaseorder.class, purchaseList.get(0).getPurchase_ID()));
					 payment.setTotalAmount(productionDataBean.getTotalAmount());
					 payment.setBalanceAmount(productionDataBean.getTotalAmount());
					 payment.setPaidAmount("0");
					 payment.setStatus("pending");
					 payment.setDate(date);
					 entityManager.persist(payment);
				 }else{
					 productionDataBean.setBalanceAmount(paymentList.get(0).getBalanceAmount());
					 productionDataBean.setPaidAmount(paymentList.get(0).getPaidAmount());
				 }
				 q=null;
				 q=entityManager.createQuery("from Payment where cutter_ID=?");
				 q.setParameter(1, purchaseList.get(0).getPurchase_ID());
				 List<Payment> list=(List<Payment>)q.getResultList();
				 if(list.size()>0){
					 productionDataBean.setBalanceAmount(list.get(0).getBalanceAmount());
					 productionDataBean.setPaidAmount(list.get(0).getPaidAmount());
				 }
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return null;
}




@Transactional(value="transactionManager")
@Override
public String cutterPaymentsave(String personID, String clientID,
		ProductionDataBean productionDataBean) {
	Query q=null;
	String status="Fail";
	BigDecimal balance=BigDecimal.valueOf(0);
	BigDecimal paid=BigDecimal.valueOf(0);
	BigDecimal paying=BigDecimal.valueOf(0);
	BigDecimal amount=BigDecimal.valueOf(0);
	if(personID!=null && clientID!=null){
		try{
			q=entityManager.createQuery("from Cutterpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
			 q.setParameter(1, productionDataBean.getInvoiceNo());
			 q.setParameter(2, Integer.parseInt(clientID));
			 List<Cutterpurchaseorder> purchaseList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
			 if(purchaseList.size()>0){
				 q=entityManager.createQuery("from Payment where cutter_ID=?");
				 q.setParameter(1, purchaseList.get(0).getPurchase_ID());
				 List<Payment> paymentList=(ArrayList<Payment>)q.getResultList();
				 if(paymentList.size()>0)
				 {
					paying=new BigDecimal(productionDataBean.getPayingAmount());
					paid=new BigDecimal(paymentList.get(0).getPaidAmount());
					balance=new BigDecimal(paymentList.get(0).getBalanceAmount());
					amount=paid.add(paying);
					Payment payment=entityManager.find(Payment.class, paymentList.get(0).getPayment_ID());
					payment.setPaidAmount(paid.add(paying).toString());
					payment.setBalanceAmount(balance.subtract(paying).toString());
					if(amount.compareTo(new BigDecimal(paymentList.get(0).getTotalAmount()))==0){
						payment.setStatus("Paid");
					}else if(amount.compareTo(new BigDecimal(paymentList.get(0).getTotalAmount()))==-1){
						payment.setStatus("Pending");
					}
					entityManager.merge(payment);
					entityManager.flush();
					entityManager.clear();
					status="Success";
					if(payment.getStatus().equalsIgnoreCase("Paid")){
						Cutterpurchaseorder purchase=entityManager.find(Cutterpurchaseorder.class, purchaseList.get(0).getPurchase_ID());
						purchase.setInvoiceStatus("PAID");
						entityManager.merge(purchase);
					}
				 }
			 } 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return status;
}


@Override
public List<String> getmodelName(String clientID,String cutterInvoice) {
	Query q=null;
	List<String> modelList=null;
	try{
		q=entityManager.createQuery("select model from Cutterpurchaseorder where invoiceNo=? and client_ID=? and status='ACTIVE' and (invoiceStatus='INVOICE' or invoiceStatus='PAID')");
		q.setParameter(1, cutterInvoice);
		q.setParameter(2, clientID);
		modelList=(ArrayList<String>)q.getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
	return modelList;
}


@Override
public List<String> getmodelInfo(String personID, String clientID,
		ProductionDataBean productionDataBean) {
	Query q=null;
	List<String> seriList=new ArrayList<String>();
	BigDecimal value=BigDecimal.valueOf(0);
	if(personID!=null && clientID!=null){
		try{
				q=entityManager.createQuery("from Printerready where model=? and client_ID=? and status='ACTIVE' and orderStatus='WAITING'");
				q.setParameter(1, productionDataBean.getModelName());
				q.setParameter(2, clientID);
				List<Printerready> cutterList=(ArrayList<Printerready>)q.getResultList();
				if(cutterList.size()>0){
					for(int c=0;c<cutterList.size();c++){
						String name=cutterList.get(c).getSeri();
						seriList.add(name);
						value=value.add(new BigDecimal(cutterList.get(c).getQuantity()));
					}
				}
				//productionDataBean.setTotalQuantity(value.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return seriList;
}


@Override
public String getSerivalue(String value, ProductionDataBean productionDataBean) {
	Query q=null;
	String quantity="";
	try{
		if(value!=null){
			q=entityManager.createQuery("select quantity from Printerready where seri=?");
			q.setParameter(1, value);
			List<String> list=(ArrayList<String>)q.getResultList();
			if(list.size()>0){
				quantity=list.get(0);
				q=entityManager.createQuery("select printer from Model where model=?");
				q.setParameter(1, productionDataBean.getModelName());
				List<String> modelList=(ArrayList<String>)q.getResultList();
				if(modelList.size()>0){
					productionDataBean.setPrinterr(modelList.get(0));
				}
				//get motive fom printerorder
				q=entityManager.createQuery("from Printerpurchaseorder where model=? and invoiceStatus='HOLDING'");
				q.setParameter(1, productionDataBean.getModelName());
				List<Printerpurchaseorder> purchaseidlist=(ArrayList<Printerpurchaseorder>)q.getResultList();
				if(purchaseidlist.size()>0){
					for (int i = 0; i < purchaseidlist.size(); i++) {
						
						q=entityManager.createQuery("from Printerorder where ser=? and printerpurchase_id=?");
						q.setParameter(1, value);
						q.setParameter(2, purchaseidlist.get(i).getPurchase_ID());
						List<Printerorder> printerorderlist=(ArrayList<Printerorder>)q.getResultList();
						if(printerorderlist.size()>0){
							productionDataBean.setTotalseri(printerorderlist.get(0).getMotive());
						}
					}
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return quantity;
}


@Override
public List<String> printerModellist(String clientID, String invoiceNo) {
	Query q=null;
	List<String> modelList=null;
	try{
		q=entityManager.createQuery("select model from Printerpurchaseorder where client_ID=? and invoice=? and status='ACTIVE' and invoiceStatus='HOLDING'");
		q.setParameter(1, clientID);
		q.setParameter(2, invoiceNo);
		modelList=(ArrayList<String>)q.getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
	return modelList;
}


@Override
public List<String> cutterInvoicelist(String clientID) {
	Query q=null;
	List<String> invoiceList=null;
	try{
		q=entityManager.createQuery("select vendorName from Vendor where client_ID=? and status='ACTIVE' and divisionVendor='printer'");
		q.setParameter(1, clientID);
		invoiceList=(ArrayList<String>)q.getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
	return invoiceList;
}


@Override
public List<String> printerInvoicelist(String clientID) {
		Query q=null;
		List<String> invoiceList=null;
		try{
			q=entityManager.createQuery("select invoice from Printerpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus='HOLDING'");
			q.setParameter(1, clientID);
			invoiceList=(ArrayList<String>)q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return invoiceList;
}


@Override
public List<String> printerSerilist(String clientID,
		ProductionDataBean productionDataBean) {
	Query q=null;
	List<String> seriList=new ArrayList<String>();
	BigDecimal value=BigDecimal.valueOf(0);
	if(clientID!=null){
		try{
				q=entityManager.createQuery("from Printerpurchaseorder where model=? and client_ID=?  and  status='ACTIVE' and invoiceStatus='HOLDING'");
				q.setParameter(1, productionDataBean.getModelName());
				q.setParameter(2, clientID);
				List<Printerpurchaseorder> printerList=(ArrayList<Printerpurchaseorder>)q.getResultList();
				if(printerList.size()>0){
					for(int p=0;p<printerList.size();p++){
						q=null;
						q=entityManager.createQuery("select ser from Printerorder where printerpurchase_id=?");
						q.setParameter(1, printerList.get(p).getPurchase_ID());
						List<String> list=(ArrayList<String>)q.getResultList();
						seriList.addAll(list);
					}
				}
				//productionDataBean.setTotalQuantity(printerList.get(0).getTotal_quantity());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return seriList;
}

@Transactional(value="transactionManager")
@Override
public String printerInvoice(String clientID, String invoiceNo) {
	String status="Fail";
	Query q=null;
	int id=0;
	if(clientID!=null){
	try{
		q=entityManager.createQuery("from Printerreceiveorder where invoice=? and client_ID=? and status='ACTIVE'");
		q.setParameter(1, invoiceNo);
		q.setParameter(2, clientID);
		List<Printerreceiveorder> receiverList=(ArrayList<Printerreceiveorder>)q.getResultList();
		if(receiverList.size()>0){
			id=receiverList.get(0).getPurchase_ID();
			Printerreceiveorder receiver=entityManager.find(Printerreceiveorder.class, id);
			receiver.setInvoiceStatus("PAYMENT");
			entityManager.merge(receiver);
			status="Success";
		}
	}catch(Exception e){
	}
	}
	return status;
}

@Transactional(value="transactionManager")
@Override
public void printerPayment(String clientID,
		ProductionDataBean productionDataBean) {
	Query q=null;
	if(clientID!=null){
		try{
			q=entityManager.createQuery("from Printerreceiveorder where invoice=? and client_ID=? and status='ACTIVE'");
			 q.setParameter(1, productionDataBean.getInvoiceNo());
			 q.setParameter(2, Integer.parseInt(clientID));
			 List<Printerreceiveorder> purchaseList=(ArrayList<Printerreceiveorder>)q.getResultList();
			 if(purchaseList.size()>0){
				 productionDataBean.setOrderDate(purchaseList.get(0).getReceivedate());
				 productionDataBean.setPrinter(purchaseList.get(0).getPrinter());
				 productionDataBean.setModelName(purchaseList.get(0).getModel());
				 productionDataBean.setTotalAmount(purchaseList.get(0).getValue());
				 q=null;
				 q=entityManager.createQuery("from Payment where printer_ID=?");
				 q.setParameter(1, purchaseList.get(0).getPurchase_ID());
				 List<Payment> paymentList=(List<Payment>)q.getResultList();
				 if(paymentList.size()==0){
					 Payment payment=new Payment();
					 payment.setPrinterReceiver(entityManager.find(Printerreceiveorder.class, purchaseList.get(0).getPurchase_ID()));
					 payment.setTotalAmount(productionDataBean.getTotalAmount());
					 payment.setBalanceAmount(productionDataBean.getTotalAmount());
					 payment.setPaidAmount("0");
					 payment.setStatus("pending");
					 payment.setDate(date);
					 entityManager.persist(payment);
				 }else{
					 productionDataBean.setBalanceAmount(paymentList.get(0).getBalanceAmount());
					 productionDataBean.setPaidAmount(paymentList.get(0).getPaidAmount());
				 }
				 q=null;
				 q=entityManager.createQuery("from Payment where printer_ID=?");
				 q.setParameter(1, purchaseList.get(0).getPurchase_ID());
				 List<Payment> list=(List<Payment>)q.getResultList();
				 if(list.size()>0){
					 productionDataBean.setBalanceAmount(list.get(0).getBalanceAmount());
					 productionDataBean.setPaidAmount(list.get(0).getPaidAmount());
				 }
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

@Transactional(value="transactionManager")
@Override
public String printerpaymentsave(String clientID,ProductionDataBean productionDataBean) {
	Query q=null;
	String status="Fail";
	BigDecimal balance=BigDecimal.valueOf(0);
	BigDecimal paid=BigDecimal.valueOf(0);
	BigDecimal paying=BigDecimal.valueOf(0);
	BigDecimal amount=BigDecimal.valueOf(0);
	if(clientID!=null){
		try{
			q=entityManager.createQuery("from Printerreceiveorder where invoice=? and client_ID=? and status='ACTIVE'");
			 q.setParameter(1, productionDataBean.getInvoiceNo());
			 q.setParameter(2, Integer.parseInt(clientID));
			 List<Printerreceiveorder> purchaseList=(ArrayList<Printerreceiveorder>)q.getResultList();
			 if(purchaseList.size()>0){
				 q=entityManager.createQuery("from Payment where printer_ID=?");
				 q.setParameter(1, purchaseList.get(0).getPurchase_ID());
				 List<Payment> paymentList=(ArrayList<Payment>)q.getResultList();
				 if(paymentList.size()>0)
				 {
					paying=new BigDecimal(productionDataBean.getPayingAmount());
					paid=new BigDecimal(paymentList.get(0).getPaidAmount());
					balance=new BigDecimal(paymentList.get(0).getBalanceAmount());
					amount=paid.add(paying);
					Payment payment=entityManager.find(Payment.class, paymentList.get(0).getPayment_ID());
					payment.setPaidAmount(paid.add(paying).toString());
					payment.setBalanceAmount(balance.subtract(paying).toString());
					if(amount.compareTo(new BigDecimal(paymentList.get(0).getTotalAmount()))==0){
						payment.setStatus("Paid");
					}else if(amount.compareTo(new BigDecimal(paymentList.get(0).getTotalAmount()))==-1){
						payment.setStatus("Pending");
					}
					entityManager.merge(payment);
					entityManager.flush();
					entityManager.clear();
					status="Success";
					if(payment.getStatus().equalsIgnoreCase("Paid")){
						Printerreceiveorder purchase=entityManager.find(Printerreceiveorder.class, purchaseList.get(0).getPurchase_ID());
						purchase.setInvoiceStatus("PAID");
						entityManager.merge(purchase);
					}
				 }
			 } 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return status;
  }

@Override
public List<String> cmtInvoiceList(String clientID) {
	Query q=null;
	List<String> invoiceList=null;
	try{
		q=entityManager.createQuery("select vendorName from Vendor where client_ID=? and status='ACTIVE' and divisionVendor='cmt'");
		q.setParameter(1, clientID);
		invoiceList=(ArrayList<String>)q.getResultList();
	}catch(Exception e){
	}
	return invoiceList;
}

@Override
public List<String> printerInvoiceList(String clientID) {
	Query q=null;
	List<String> invoiceList=null;
	try{
		q=entityManager.createQuery("select vendorName from Vendor where client_ID=? and status='ACTIVE' and divisionVendor='printer'");
		q.setParameter(1, clientID);
		invoiceList=(ArrayList<String>)q.getResultList();
	}catch(Exception e){
	}
	return invoiceList;
}


@Override
public List<String> printerReceivemodel(String clientID, String invoiceno) {
	Query q=null;
	List<String> modelList=null;
	try{
		q=entityManager.createQuery("select model from Printerreceiveorder where client_ID=? and invoice=? and status='Active'");
		q.setParameter(1, clientID);
		q.setParameter(2, invoiceno);
		modelList=(ArrayList<String>)q.getResultList();
	}catch(Exception e){
		
	}
	return modelList;
}


@Override
public List<String> getmodelData(String personID, String clientID,
		ProductionDataBean productionDataBean) {
	Query q=null;
	List<String> seriList=new ArrayList<String>();
	BigDecimal value=BigDecimal.valueOf(0);
	if(personID!=null && clientID!=null){
		try{
				q=entityManager.createQuery("from Cmtready where model=? and client_ID=?  and status='ACTIVE' and  orderStatus='WAITING'");
				q.setParameter(1, productionDataBean.getModelName());
				q.setParameter(2, clientID);
				List<Cmtready> readyList=(ArrayList<Cmtready>)q.getResultList();
				System.out.println("list----"+readyList.size());
				if(readyList.size()>0){
					for(int c=0;c<readyList.size();c++){
						String seri=readyList.get(c).getSeri();
						seriList.add(seri);
						//value=value.add(new BigDecimal(readyList.get(c).getQuantity()));
					}
				}
				//productionDataBean.setTotalQuantity(value.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return seriList;
}

@Override
public String getSerivaluechange(String value,
		ProductionDataBean productionDataBean) {
	Query q=null;
	String quantity="";
	try{
		if(value!=null){
			q=entityManager.createQuery("select quantity from Cmtready where seri=? and orderStatus='WAITING' ");
			q.setParameter(1, value);
			List<String> list=(ArrayList<String>)q.getResultList();
			if(list.size()>0){
				quantity=list.get(0);
				q=entityManager.createQuery("select cmt from Model where model=?");
				q.setParameter(1, productionDataBean.getModelName());
				List<String> modelList=(ArrayList<String>)q.getResultList();
				if(modelList.size()>0){
					productionDataBean.setPrinterr(modelList.get(0));
				}
			}
			q=null;
			q=entityManager.createQuery("select motive from Printerorder where ser=? and status='Active'");
			q.setParameter(1, value);
			List<String> motiveList=(ArrayList<String>)q.getResultList();
			if(motiveList.size()>0){
				productionDataBean.setTotalseri(motiveList.get(0));
			}
		} 
	}catch(Exception e){
	}
	return quantity;	
}

@Override
public List<String> cmtOrderinvoice(String clientID) {
	Query q=null;
	List<String> invoiceList=null;
	try{
		q=entityManager.createQuery("select invoiceNo from Cmtpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus='HOLDING'");
		q.setParameter(1, clientID);
		invoiceList=(ArrayList<String>)q.getResultList();
	}catch(Exception e){
		
	}
	return invoiceList;
}

@Override
public String getCmtReceiveinvoice(String personID, String clientID) {
	Query q=null;
	String invoiceno="";
	String cmtlistsize="";
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	String purdate = sf.format(date);
	try{
			q=entityManager.createQuery("from Cmtreceiveorder");
			List<Cmtreceiveorder> cmtList=(List<Cmtreceiveorder>)q.getResultList();
			cmtlistsize=String.valueOf(cmtList.size()+1);
			if(cmtList.size()==0){
				invoiceno="CMR"+purdate+"00"+1;
			}
			if(cmtlistsize.length()==1){
				invoiceno="CMR"+purdate+"00"+cmtlistsize;
			}else if(cmtlistsize.length()==2){
				invoiceno="CMR"+purdate+"0"+cmtlistsize;
			}else{
				invoiceno="CMR"+purdate+cmtlistsize;
			}
	}catch(Exception e){
		e.printStackTrace();
	}
	return invoiceno;
}


@Override
public List<String> cmtOrderdemodel(String clientID, String invoiceno) {
	Query q=null;
	List<String> modellist=null;
	try{
		q=entityManager.createQuery("select model from Cmtpurchaseorder where client_ID=? and invoiceNo=? and status='ACTIVE'");
		q.setParameter(1, clientID);
		q.setParameter(2, invoiceno);
		modellist=(ArrayList<String>)q.getResultList();
	}catch(Exception e){
	}
	return modellist;
}


@Override
public List<String> cmtSerilist(String clientID,ProductionDataBean productionDataBean) {
	Query q=null;
	List<String> seriList=new ArrayList<String>();
	BigDecimal value=BigDecimal.valueOf(0);
	if(clientID!=null){
		try{
				q=entityManager.createQuery("from Cmtready where model=? and client_ID=? and  status='ACTIVE' and orderStatus='ORDERED'");
				q.setParameter(1, productionDataBean.getModelName());
				q.setParameter(2, clientID);
				List<Cmtready> cmtList=(ArrayList<Cmtready>)q.getResultList();
				if(cmtList.size()>0){
					for(int c=0;c<cmtList.size();c++){
						String seri=cmtList.get(c).getSeri();
						seriList.add(seri);
						//value=value.add(new BigDecimal(cmtList.get(c).getQuantity()));
					}
				}
				//productionDataBean.setTotalQuantity(value.toString());
		}catch(Exception e){
		}
	}
	return seriList;
}


@Override
public String cmtreceiveSerichange(String value,ProductionDataBean productionDataBean) {
	Query q=null;
	String quantity="";
	try{
		if(value!=null){
			q=entityManager.createQuery("select quantity from Cmtready where seri=? and orderStatus='ORDERED' ");
			q.setParameter(1, value);
			List<String> list=(ArrayList<String>)q.getResultList();
			if(list.size()>0){
				quantity=list.get(0);
				q=entityManager.createQuery("select cmt from Model where model=?");
				q.setParameter(1, productionDataBean.getModelName());
				List<String> modelList=(ArrayList<String>)q.getResultList();
				if(modelList.size()>0){
					productionDataBean.setPrinterr(modelList.get(0));
				}
			}
			q=null;
			q=entityManager.createQuery("select motive from Cmtorder where seri=? and status='ACTIVE'");
			q.setParameter(1, value);
			List<String> motiveList=(ArrayList<String>)q.getResultList();
			if(motiveList.size()>0){
				productionDataBean.setTotalseri(motiveList.get(0));
			}
			}
	}catch(Exception e){
		e.printStackTrace();
	}
	return quantity;	
}

@Transactional(value="transactionManager")
@Override
public String insertCmtreceive(String personID, String clientID,
		ProductionDataBean productionDataBean,List<ProductionDataBean> cmtReceivelist) {
	String status="Fail";
	Query q=null;
	if(personID!=null && clientID!=null){
		try{
			Cmtreceiveorder purchase=new Cmtreceiveorder();
			purchase.setCmt(productionDataBean.getCmt());
			purchase.setReceiveDate(productionDataBean.getReceivedate());
			purchase.setInvoiceNo(productionDataBean.getInvoiceNo());
			purchase.setModel(productionDataBean.getModelName());
			purchase.setTotalQuantity(productionDataBean.getTotalQuantity());
			purchase.setAmount(productionDataBean.getTotalValue());
			purchase.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
			purchase.setClient(entityManager.find(Client.class, Integer.parseInt(clientID)));
			purchase.setStatus("ACTIVE");
			purchase.setOrderInvoiceno(productionDataBean.getCutterInvoiceno());
			purchase.setInvoiceStatus("STOCK IN");
			purchase.setStockStatus("NOT ADDED");
			entityManager.persist(purchase);
			q=entityManager.createQuery("from Cmtreceiveorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
			q.setParameter(1, productionDataBean.getInvoiceNo());
			q.setParameter(2, clientID);
			List<Cmtreceiveorder> cmtpurchaseList=(List<Cmtreceiveorder>)q.getResultList();
			if(cmtpurchaseList.size()>0){
			int id=cmtpurchaseList.get(0).getReceive_ID();
			for(int i=0;i<cmtReceivelist.size();i++){
				try{
				if(cmtReceivelist.get(i).getSeri()!=null || !cmtReceivelist.get(i).getSeri().equalsIgnoreCase("")){
					Cmtorder order=new Cmtorder();
					order.setMotive(cmtReceivelist.get(i).getMotive());
					order.setCmtreceiveorder(entityManager.find(Cmtreceiveorder.class, id));
					order.setSeri(cmtReceivelist.get(i).getSeri());
					order.setQuantity(cmtReceivelist.get(i).getQuantity());
					order.setValue(cmtReceivelist.get(i).getValue());
					order.setStatus("ACTIVE");
					entityManager.persist(order);
					entityManager.flush();
					entityManager.clear();
				}
				}catch(NullPointerException e){
					
				}
			}
			}
			q=null;
			q=entityManager.createQuery("from Cmtpurchaseorder where model=? and client_ID=? and status='ACTIVE' and invoiceStatus='HOLDING'");
			q.setParameter(1, productionDataBean.getModelName());
			q.setParameter(2, clientID);
			List<Cmtpurchaseorder> cmtList=(ArrayList<Cmtpurchaseorder>)q.getResultList();
			if(cmtList.size()>0){
				for(int k=0;k<cmtList.size();k++){
				Cmtpurchaseorder order=entityManager.find(Cmtpurchaseorder.class, cmtList.get(k).getPurchase_ID());
				order.setInvoiceStatus("RECEIVED");
				entityManager.merge(order);
				entityManager.flush();
				entityManager.clear();
				}
			}
			for (int i = 0; i < cmtReceivelist.size(); i++) {
				if(cmtReceivelist.get(i).getSeri()!=null || !cmtReceivelist.get(i).getSeri().equalsIgnoreCase("")){
				q=null;
				q=entityManager.createQuery("from Cmtready where model=? and seri=? and status='ACTIVE' and orderStatus='ORDERED'");
				q.setParameter(1, productionDataBean.getModelName());
				q.setParameter(2, cmtReceivelist.get(i).getSeri());
				List<Cmtready> readyList=(ArrayList<Cmtready>)q.getResultList();
				if(readyList.size()>0){
					Cmtready ready=entityManager.find(Cmtready.class, readyList.get(0).getCmtready_ID());
					ready.setOrderStatus("RECEIVED");
					entityManager.merge(ready);
					entityManager.flush();entityManager.clear();
					status="Success";
				}
			}
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return status;
}

@Override
public List<ProductionDataBean> cmtstockDetails(String clientID,ProductionDataBean productionDataBean) {
	Query q=null;
	List<ProductionDataBean> beanList=null;
	try{
		beanList=new ArrayList<ProductionDataBean>();
		q=entityManager.createQuery("from Cmtreceiveorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
		q.setParameter(1, productionDataBean.getInvoiceNo());
		q.setParameter(2, clientID);
		List<Cmtreceiveorder> receiveList=(ArrayList<Cmtreceiveorder>)q.getResultList();
		if(receiveList.size()>0){
			q=null;
			q=entityManager.createQuery("from Cmtstock where cmtreceive_ID=? and status='ACTIVE'");
			q.setParameter(1, receiveList.get(0).getReceive_ID());
			List<Cmtstock> stockList=(ArrayList<Cmtstock>)q.getResultList();
			if(stockList.size()>0){
				for(int j=0;j<stockList.size();j++){
					ProductionDataBean pro=new ProductionDataBean();
					pro.setQuantity(stockList.get(j).getQuantity());
					pro.setModelName(stockList.get(j).getModel());
					pro.setInvoiceNo(productionDataBean.getInvoiceNo());
					pro.setQuantity(stockList.get(j).getQuantity());
					pro.setStockinQuantity(stockList.get(j).getBalanceQuantity());
					pro.setAddedQuantity("");
					pro.setSeri(stockList.get(j).getSeri());
					pro.setSerialNo(String.valueOf(j+1));
					pro.setCategoryId(stockList.get(j).getCmtstock_ID());
					beanList.add(pro);
				}
			}else{
				q=null;
				q=entityManager.createQuery("from Cmtorder where receive_ID=? and status='Active'");
				q.setParameter(1, receiveList.get(0).getReceive_ID());
				List<Cmtorder> orderList=(ArrayList<Cmtorder>)q.getResultList();
				if(orderList.size()>0){
					for(int i=0;i<orderList.size();i++){
						ProductionDataBean production=new ProductionDataBean();
						production.setModelName(receiveList.get(0).getModel());
						production.setInvoiceNo(productionDataBean.getInvoiceNo());
						production.setStockinQuantity(orderList.get(i).getQuantity());
						production.setAddedQuantity("");
						production.setSeri(orderList.get(i).getSeri());
						production.setValue(orderList.get(i).getValue());
						production.setQuantity(orderList.get(i).getQuantity());
						production.setSerialNo(String.valueOf(i+1));
						beanList.add(production);
					}
				}
			}
		}
	}catch(Exception e){
	}
	return beanList;
}

public void getPrinterPayments(String personID,ProductionDataBean productionDataBean){
	Query v=null;
	
	List<ProductionDataBean> printerList=new ArrayList<ProductionDataBean>();
	try{
		if(productionDataBean.getInvoiceStatus().equals("printer")){
			v=entityManager.createQuery("from Printerreceiveorder where person_ID=? and status='ACTIVE' and (invoiceStatus='INVOICE' or invoiceStatus='PAYMENT' or invoiceStatus='PAID') ORDER BY purchase_ID DESC");
			v.setParameter(1, Integer.parseInt(personID));
			List<Printerreceiveorder> printerReceive=(List<Printerreceiveorder>)v.getResultList();
			if(printerReceive.size()>0){
				for (int i = 0; i < printerReceive.size(); i++) {
					ProductionDataBean printers=new ProductionDataBean();
					printers.setSerialNo(String.valueOf(i+1));
					printers.setOrderDate(printerReceive.get(i).getReceivedate());
					printers.setInvoiceNo(printerReceive.get(i).getInvoice());
					printers.setValue(printerReceive.get(i).getValue());
					printers.setTotalQuantity(printerReceive.get(i).getTotalquantitry());
					printers.setPrinter(printerReceive.get(i).getPrinter());
					if(printerReceive.get(i).getInvoiceStatus().equals("PAID")) printers.setInvoiceStatus(printerReceive.get(i).getInvoiceStatus());
					else printers.setInvoiceStatus("UNPAID");
					v=null;
					v=entityManager.createQuery("from Payment where printer_ID=?");
					v.setParameter(1, printerReceive.get(i).getPurchase_ID());
					List<Payment> payment=(List<Payment>)v.getResultList();
					if(payment.size()>0){
						printers.setBalanceAmount(payment.get(0).getBalanceAmount());
					}else{
						printers.setBalanceAmount(printerReceive.get(i).getValue());
					}
					printerList.add(printers);
				}
				productionDataBean.setOrderQuantitylist(printerList);
			}
		}else if(productionDataBean.getInvoiceStatus().equals("cmt")){/*
			v=entityManager.createQuery("from Cmtreceiveorder where person_ID=? and status='ACTIVE' and (invoiceStatus='INVOICE' or invoiceStatus='PAYMENT' or invoiceStatus='PAID') ORDER BY receive_ID DESC");
			v.setParameter(1, Integer.parseInt(personID));
			List<Cmtreceiveorder> cmtReceive=(List<Cmtreceiveorder>)v.getResultList();
			if(cmtReceive.size()>0){
				for (int i = 0; i < cmtReceive.size(); i++) {
					ProductionDataBean printers=new ProductionDataBean();
					printers.setSerialNo(String.valueOf(i+1));
					printers.setOrderDate(cmtReceive.get(i).getReceiveDate());
					printers.setInvoiceNo(cmtReceive.get(i).getInvoiceNo());
					printers.setValue(cmtReceive.get(i).getAmount());
					printers.setTotalQuantity(cmtReceive.get(i).getTotalQuantity());
					if(cmtReceive.get(i).getInvoiceStatus().equals("PAID")) printers.setInvoiceStatus(cmtReceive.get(i).getInvoiceStatus());
					else printers.setInvoiceStatus("UNPAID");
					v=null;
					v=entityManager.createQuery("from Payment where cmt_ID=?");
					v.setParameter(1, cmtReceive.get(i).getReceive_ID());
					List<Payment> payment=(List<Payment>)v.getResultList();
					if(payment.size()>0){
						printers.setBalanceAmount(payment.get(0).getBalanceAmount());
					}else{
						printers.setBalanceAmount(cmtReceive.get(i).getAmount());
					}
					printerList.add(printers);
				}
				productionDataBean.setOrderQuantitylist(printerList);
			}*/
			
			v=entityManager.createQuery("from Cmtreceiveorder where person_ID=? and status='ACTIVE' and (invoiceStatus='PARTIAL STOCKIN' or invoiceStatus='INVOICE' or invoiceStatus='PAYMENT' or invoiceStatus='PAID') ORDER BY receive_ID DESC");
			v.setParameter(1, Integer.parseInt(personID));
			List<Cmtreceiveorder> cmtReceive=(List<Cmtreceiveorder>)v.getResultList();
			
			if(cmtReceive.size()>0){
				for (int i = 0; i < cmtReceive.size(); i++) {
					BigDecimal paid=new BigDecimal(0);
					BigDecimal unpaid=new BigDecimal(0);
					BigDecimal paidquant=new BigDecimal(0);
					BigDecimal unpaidquant=new BigDecimal(0);
					v=null;
					v=entityManager.createQuery("from CmtStockin where cmtreceive_ID=?");
					v.setParameter(1, cmtReceive.get(i).getReceive_ID());
					List<CmtStockin> cmtstkin=(List<CmtStockin>)v.getResultList();
					if(cmtstkin.size()>0){
						for (int k = 0; k < cmtstkin.size(); k++) {
							if(cmtstkin.get(k).getStatus().equalsIgnoreCase("paid")){
								paid=paid.add((new BigDecimal(cmtstkin.get(k).getStockinQuantity())).multiply(new BigDecimal(cmtstkin.get(k).getCmtValue())));
								paidquant=paidquant.add(new BigDecimal(cmtstkin.get(k).getStockinQuantity()));
							}else if(cmtstkin.get(k).getStatus().equalsIgnoreCase("unpaid")){
								unpaid=unpaid.add(new BigDecimal(cmtstkin.get(k).getStockinQuantity()).multiply(new BigDecimal(cmtstkin.get(k).getCmtValue())));
								unpaidquant=unpaidquant.add(new BigDecimal(cmtstkin.get(k).getStockinQuantity()));
							}
						}
						if (paidquant.compareTo(BigDecimal.ZERO) > 0){
							ProductionDataBean printers=new ProductionDataBean();
							printers.setSerialNo(String.valueOf(i+1));
							printers.setOrderDate(cmtReceive.get(i).getReceiveDate());
							printers.setInvoiceNo(cmtReceive.get(i).getInvoiceNo());
							printers.setCmt(cmtReceive.get(i).getCmt());
							printers.setInvoiceStatus("paid");
							printers.setValue(paid.toString());
							printers.setTotalQuantity(paidquant.toString());
							printerList.add(printers);
						}
						if(unpaidquant.compareTo(BigDecimal.ZERO) > 0){
							ProductionDataBean printers=new ProductionDataBean();
							printers.setSerialNo(String.valueOf(i+1));
							printers.setOrderDate(cmtReceive.get(i).getReceiveDate());
							printers.setInvoiceNo(cmtReceive.get(i).getInvoiceNo());
							printers.setCmt(cmtReceive.get(i).getCmt());
							printers.setInvoiceStatus("unpaid");
							printers.setValue(unpaid.toString());
							printers.setTotalQuantity(unpaidquant.toString());
							printerList.add(printers);
						}	
					}
				}
			}System.out.println("printerList -- "+printerList.size());
			productionDataBean.setOrderQuantitylist(printerList);
		}else if(productionDataBean.getInvoiceStatus().equals("cutter")){
			v=entityManager.createQuery("from Cutterpurchaseorder where person_ID=? and status='ACTIVE' and (invoiceStatus='INSERTED' or invoiceStatus='INVOICE' or invoiceStatus='PAID') ORDER BY purchase_ID DESC");
			v.setParameter(1, Integer.parseInt(personID));
			List<Cutterpurchaseorder> cutterList=(List<Cutterpurchaseorder>)v.getResultList();
			if(cutterList.size()>0){
				for (int i = 0; i < cutterList.size(); i++) {
					ProductionDataBean printers=new ProductionDataBean();
					printers.setSerialNo(String.valueOf(i+1));
					printers.setOrderDate(cutterList.get(i).getOrderDate());
					printers.setInvoiceNo(cutterList.get(i).getInvoiceNo());
					printers.setCutter(cutterList.get(i).getCutter());
					printers.setModelName(cutterList.get(i).getModel());
					printers.setValue(cutterList.get(i).getValue());
					printers.setTotalQuantity(cutterList.get(i).getTotalQuantity());
					printers.setSaving(cutterList.get(i).getSaving());
					if(cutterList.get(i).getInvoiceStatus().equals("PAID")) printers.setInvoiceStatus(cutterList.get(i).getInvoiceStatus());
					else printers.setInvoiceStatus("UNPAID");
					v=null;
					v=entityManager.createQuery("from Payment where cutter_ID=?");
					v.setParameter(1, cutterList.get(i).getPurchase_ID());
					List<Payment> payment=(List<Payment>)v.getResultList();
					if(payment.size()>0){
						printers.setBalanceAmount(payment.get(0).getBalanceAmount());
					}else{
						printers.setBalanceAmount(cutterList.get(i).getValue());
					}
					printerList.add(printers);
				}
				productionDataBean.setOrderQuantitylist(printerList);
			}
		}				
	}catch(Exception e){
		e.printStackTrace();
	}
}

public void getPayrollReports(String personID,PayrollDataBean payrollDataBean){
	Query v=null;
	List<PayrollDataBean> payrollList=new ArrayList<PayrollDataBean>();
	try{
		if(payrollDataBean.getType().equals("Penalty")){
			v=entityManager.createQuery("from Penalty where person_ID=? and status='ACTIVE'");
			v.setParameter(1, Integer.parseInt(personID));
			List<Penalty> penalty=(List<Penalty>)v.getResultList();
			if(penalty.size()>0){
				for (int i = 0; i < penalty.size(); i++) {
					PayrollDataBean paylist=new PayrollDataBean();
					paylist.setDate(penalty.get(i).getDate());
					paylist.setAmount(penalty.get(i).getAmount());
					paylist.setReason(penalty.get(i).getReason());
					paylist.setType(penalty.get(i).getType());
					paylist.setStaffName(penalty.get(i).getStaffName());
					paylist.setSerialNo(String.valueOf(i+1));
					payrollList.add(paylist);
				}
			}
		}else if(payrollDataBean.getType().equals("Salary")){
			v=entityManager.createQuery("from EmployeePayroll where status='Active'");
			List<EmployeePayroll> employeePayroll=(List<EmployeePayroll>)v.getResultList();
			if(employeePayroll.size()>0){
				int serialno=1;
				for (int i = 0; i < employeePayroll.size(); i++) {
					if(employeePayroll.get(i).getEmployee().getPerson().getPerson_ID()==Integer.parseInt(personID)){
						PayrollDataBean payrolls=new PayrollDataBean();
						payrolls.setEmployee(employeePayroll.get(i).getEmployee().getEmployeeName());
						payrolls.setBranch(employeePayroll.get(i).getEmployee().getBranch().getBranchName());
						payrolls.setMonth(employeePayroll.get(i).getMonth().getType());
						payrolls.setYear(employeePayroll.get(i).getYear().getType());
						payrolls.setMonthSalary(employeePayroll.get(i).getMonthSalary());
						payrolls.setSerialNo(String.valueOf(i+1));
						payrollList.add(payrolls);
					}
				}				
			}
		}
		payrollDataBean.setPayrollList(payrollList);
	}catch(Exception e){
		e.printStackTrace();
	}
}


public List<String> getcmtModelName(String personID)
{
	Query q=null;
	List<String> itemNameList=new ArrayList<String>();
	try{
		q=entityManager.createQuery("select itemName from Stock where person_ID=? and status='Active'");
		System.out.println("inside query"); 
		q.setParameter(1, personID);
		System.out.println(personID); 
		itemNameList=q.getResultList();		
		HashSet<String> hashlist=new HashSet<String>(itemNameList);
		itemNameList.clear();
		itemNameList.addAll(hashlist); 
	}catch(Exception e){
		e.printStackTrace();
	}
	return itemNameList;
}

/*public List<String> getseriNo(String valuechange, String personID){
	Query q=null;
	List<String> seriNo=new ArrayList<String>();
	try{
		System.out.println("inside seri");
		q=entityManager.createQuery("select seriNo from Stock where person_ID=? and status='Active' and itemName=?");
		System.out.println("inside query");
		q.setParameter(1, personID);
		q.setParameter(2, valuechange); 
		seriNo=q.getResultList();
		System.out.println(seriNo);
		HashSet<String> hashList=new HashSet<String>(seriNo); 
		seriNo.clear();
		seriNo.addAll(hashList);
	}catch(Exception e){
		e.printStackTrace();
	}	
	return seriNo;
}*/

/*@Override
public List<StockDataBean> getcmtTable(String personID,StockDataBean stockDataBean) {
	Query q=null;
	List<StockDataBean>viewList=new ArrayList<StockDataBean>();
	System.out.println("-----------------------inside getcmtTable-------------------------"); 
		try
			{
				q=entityManager.createQuery("from Stock where person_ID=? and itemName=? and seriNo=? and cmtStatus='CmtStock' and status='Active'");
				q.setParameter(1, personID);
				q.setParameter(2, stockDataBean.getItemname());
				q.setParameter(3, stockDataBean.getSeriNolist());
				List<Stock> stockList=(List<Stock>)q.getResultList();
				System.out.println(stockList.size());
					if(stockList.size() > 0){
						for (int i = 0; i < stockList.size(); i++) {
							StockDataBean obj=new StockDataBean();
							obj.setSerialNo(String.valueOf(i+1)); 
							obj.setShop(stockList.get(i).getWarehouseStatus());
							obj.setModel(stockList.get(i).getItemName()); 
							obj.setSeriDetails(stockList.get(i).getSeriNo()); 
							obj.setStock(stockList.get(i).getStockIn());
							obj.setStockID(stockList.get(i).getStock_ID());  
							q=null;
							System.out.println("-----------stock list seriNo------->"+stockList.get(i).getSeriNo()+"--------model name------>"+stockDataBean.getItemname());
							q=entityManager.createQuery("from Model where person_ID=? and seri=? and model=? and status='Active'"); 
								q.setParameter(1, personID);
								q.setParameter(2, stockList.get(i).getSeriNo());
								q.setParameter(3, stockDataBean.getItemname());
								List<Model> modelList=(List<Model>)q.getResultList();
								System.out.println("--------------modelList size---------->"+modelList.size()); 
								if(modelList.size() > 0){
									for(int j=0; j < modelList.size();j++){										
										obj.setPrice(modelList.get(i).getSellPrice()); 
									}
								}
								viewList.add(obj);
						}				
					}
 			}
		catch(Exception e)
			{
				e.printStackTrace();
			}
	return viewList;
}*/


@Transactional(value="transactionManager")
public String saveCategory(String personID, String clientID,FinanceDataBean financeDataBean) {
	Query q=null;String status="Fail";
	try{
		System.out.println("category"+financeDataBean.getTransactionCategory());
		int personid=Integer.parseInt(personID);
		int clientid=Integer.parseInt(clientID);
		q=entityManager.createQuery("from TransactionCategory where person_ID=? and client_ID=? and status='ACTIVE'");
		q.setParameter(1, personID);
		q.setParameter(2, clientID);
		List<TransactionCategory> categoryList=(List<TransactionCategory>)q.getResultList();
		if(categoryList.size()==0){
			TransactionCategory cate=new TransactionCategory();
			cate.setPerson(entityManager.find(Person.class, personid));
			cate.setClient(entityManager.find(Client.class, clientid));
			cate.setCategoryName("waste");
			cate.setDescription("waste category registration");
			cate.setStatus("ACTIVE");
			entityManager.persist(cate);
			entityManager.flush();
			entityManager.clear();
		}
		q=entityManager.createQuery("from TransactionCategory where person_ID=? and client_ID=? and categoryName=? and status='ACTIVE'");
		q.setParameter(1, personID);
		q.setParameter(2, clientID);
		q.setParameter(3, financeDataBean.getTransactionCategory());
		List<TransactionCategory> categoryLists=(List<TransactionCategory>)q.getResultList();
		if(categoryLists.size()>0){
			status="Exist";
		}else{
			TransactionCategory cate1=new TransactionCategory();
			cate1.setPerson(entityManager.find(Person.class, personid));
			cate1.setClient(entityManager.find(Client.class, clientid));
			cate1.setCategoryName(financeDataBean.getTransactionCategory());
			cate1.setDescription(financeDataBean.getTransactionDescription());
			cate1.setStatus("ACTIVE");
			entityManager.persist(cate1);
			entityManager.flush();
			entityManager.clear();
			status="Success";
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return status;
}

@Transactional(value="transactionManager")
public String transactionsave(String personID, String clientID,FinanceDataBean financeDataBean) {
	String status="Fail";Query q=null;String listsize="";
	int personid,clientid=0;
	try{
		if(personID!=null && clientID!=null){
			q=entityManager.createQuery("from TransactionCategory where person_ID=? and client_ID=? and categoryName=? and status='ACTIVE'");
			q.setParameter(1, personID);
			q.setParameter(2, clientID);
			q.setParameter(3, financeDataBean.getTransactionCategory());
			List<TransactionCategory> categorylist=(List<TransactionCategory>)q.getResultList();
			personid=Integer.parseInt(personID);
			clientid=Integer.parseInt(clientID);
			q=entityManager.createQuery("from Transaction where person_ID=? and client_ID=? and status='ACTIVE'");
			q.setParameter(1, personID);
			q.setParameter(2, clientID);
			List<Transaction> translist=(List<Transaction>)q.getResultList();
			listsize=String.valueOf(translist.size()+1);
			if(translist.size()==0){
				financeDataBean.setTransactionNumber("TN"+"0000000"+1);
			}else{
				if(listsize.length()==1){
					financeDataBean.setTransactionNumber("TN"+"0000000"+listsize);
				}else if(listsize.length()>1  && listsize.length()<=2){
					financeDataBean.setTransactionNumber("TN"+"000000"+listsize);
				}else if(listsize.length()>2 && listsize.length()<=3){
					financeDataBean.setTransactionNumber("TN"+"00000"+listsize);
				}else if(listsize.length()>3 && listsize.length()<=4){
					financeDataBean.setTransactionNumber("TN"+"0000"+listsize);
				}else if(listsize.length()>4 && listsize.length()<=5){
					financeDataBean.setTransactionNumber("TN"+"000"+listsize);
				}else if(listsize.length()>5 && listsize.length()<=6){
					financeDataBean.setTransactionNumber("TN"+"00"+listsize);
				}else if(listsize.length()>6 && listsize.length()<=7){
					financeDataBean.setTransactionNumber("TN"+"0"+listsize);
				}else{
					financeDataBean.setTransactionNumber("TN"+listsize);
				}
			}
			Transaction trans=new Transaction();
			trans.setDate(financeDataBean.getDate());
			trans.setPaymentmode(financeDataBean.getPaymentmode());
			trans.setTransactiontype(financeDataBean.getPaymenttype());
			trans.setName(financeDataBean.getName());
			trans.setAmount(financeDataBean.getAmount());
			trans.setNote(financeDataBean.getNote());
			trans.setBankname(financeDataBean.getBankname());
			trans.setCardno(financeDataBean.getCardno());
			trans.setAccountno(financeDataBean.getAccountno());
			trans.setChequeno(financeDataBean.getChequeno());
			trans.setChequedate(financeDataBean.getChequedate());
			trans.setCategoryName(financeDataBean.getTransactionCategory());
			trans.setTransactionNumber(financeDataBean.getTransactionNumber());
			trans.setTransactionCategory(entityManager.find(TransactionCategory.class, categorylist.get(0).getTransactionCategoryId()));
			if(financeDataBean.getTransactionCategory().equalsIgnoreCase("waste")){
				trans.setWasteQuantity(financeDataBean.getWasteQuantity());
			}
			trans.setStatus("ACTIVE");
			trans.setPerson(entityManager.find(Person.class,personid));
			trans.setClient(entityManager.find(Client.class, clientid));
			entityManager.persist(trans);
			status="Success";
		}
	}catch(Exception r){
		r.printStackTrace();
	}
	return status;
}

public List<String> gettransactioncategory(String personID, String clientID) {
	Query q=null;List<String> categoryList=new ArrayList<String>();
	try{
		q=entityManager.createQuery("select categoryName from TransactionCategory where person_ID=? and client_ID=? and status='ACTIVE'");
		q.setParameter(1, personID);
		q.setParameter(2, clientID);
		categoryList=q.getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
	return categoryList;
}

public List<FinanceDataBean> transactionview(String personID, String clientID,FinanceDataBean financeDataBean) {
	Query q=null;
	List<FinanceDataBean> translist=new ArrayList<FinanceDataBean>();
	try{
		if(personID!=null && clientID!=null){
			q=entityManager.createQuery("from Transaction where person_ID=? and client_ID=? and status='ACTIVE' ORDER BY transaction_ID DESC");
			q.setParameter(1, personID);
			q.setParameter(2, clientID);
			List<Transaction> list=(List<Transaction>)q.getResultList();
			if(list.size()>0){
				for(Transaction trans:list){
					FinanceDataBean fin=new FinanceDataBean();
					fin.setDate(trans.getDate());
					fin.setPaymentmode(trans.getPaymentmode());
					fin.setPaymenttype(trans.getTransactiontype());
					fin.setName(trans.getName());
					fin.setAmount(trans.getAmount());
					fin.setDebtId(trans.getTransaction_ID());
					fin.setTransactionCategory(trans.getCategoryName());
					fin.setTransactionNumber(trans.getTransactionNumber());
					translist.add(fin);
				}
			}
		}
	}catch(Exception w){
		w.printStackTrace();
	}
	return translist;
}

public void viewtransaction(String personID, String clientID, FinanceDataBean financeDataBean) {
	Query q=null;
	try{
		if(personID!=null && clientID!=null){
			q=entityManager.createQuery("from Transaction where person_ID=? and client_ID=? and transaction_ID=? and status='ACTIVE'");
			q.setParameter(1,personID);
			q.setParameter(2,clientID);
			q.setParameter(3,financeDataBean.getDebtId());
			List<Transaction> transactionList=(List<Transaction>)q.getResultList();
			if(transactionList.size()>0){
				financeDataBean.setDate(transactionList.get(0).getDate());
				financeDataBean.setTransactionNumber(transactionList.get(0).getTransactionNumber());
				financeDataBean.setPaymentmode(transactionList.get(0).getPaymentmode());
				financeDataBean.setPaymenttype(transactionList.get(0).getTransactiontype());
				financeDataBean.setAmount(transactionList.get(0).getAmount());
				financeDataBean.setName(transactionList.get(0).getName());
				financeDataBean.setNote(transactionList.get(0).getNote());
				financeDataBean.setBankname(transactionList.get(0).getBankname());
				financeDataBean.setCardno(transactionList.get(0).getCardno());
				financeDataBean.setChequeno(transactionList.get(0).getChequeno());
				financeDataBean.setChequedate(transactionList.get(0).getChequedate());
				financeDataBean.setAccountno(transactionList.get(0).getAccountno());
				financeDataBean.setDebtId(transactionList.get(0).getTransaction_ID());
				financeDataBean.setTransactionCategory(transactionList.get(0).getCategoryName());
				financeDataBean.setWasteQuantity(transactionList.get(0).getWasteQuantity());
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}	
}

@Transactional(value="transactionManager")
public String updatetransaction(String personID, String clientID, FinanceDataBean financeDataBean) {
	String status="Fail";Query q=null;
	try{
		if(personID!=null && clientID!=null){
			q=entityManager.createQuery("from TransactionCategory where person_ID=? and client_ID=? and categoryName=? and status='ACTIVE'");
			q.setParameter(1, personID);
			q.setParameter(2, clientID);
			q.setParameter(3, financeDataBean.getTransactionCategory());
			List<TransactionCategory> categorylist=(List<TransactionCategory>)q.getResultList();
			Transaction trans=entityManager.find(Transaction.class, financeDataBean.getDebtId());	
			trans.setPaymentmode(financeDataBean.getPaymentmode());
			trans.setTransactiontype(financeDataBean.getPaymenttype());
			trans.setName(financeDataBean.getName());
			trans.setAmount(financeDataBean.getAmount());
			trans.setNote(financeDataBean.getNote());
			trans.setBankname(financeDataBean.getBankname());
			trans.setCardno(financeDataBean.getCardno());
			trans.setAccountno(financeDataBean.getAccountno());
			trans.setChequeno(financeDataBean.getChequeno());
			trans.setChequedate(financeDataBean.getChequedate());
			trans.setCategoryName(financeDataBean.getTransactionCategory());
			trans.setWasteQuantity(financeDataBean.getWasteQuantity());
			trans.setTransactionCategory(entityManager.find(TransactionCategory.class,categorylist.get(0).getTransactionCategoryId()));
			entityManager.merge(trans);
			status="Success";
		}
	}catch(Exception k){
		k.printStackTrace();
	}
	return status;
}

@Transactional(value="transactionManager")
public String deletetransaction(String personID, String clientID, FinanceDataBean financeDataBean) {
	String status="Fail";
	try{
		if(personID!=null && clientID!=null){
			Transaction trans=entityManager.find(Transaction.class, financeDataBean.getDebtId());
			trans.setStatus("DEACTIVE");
			entityManager.merge(trans);
			status="Success";
		}
	}catch(Exception r){
		r.printStackTrace();
	}
	return status;
}

@Transactional(value="transactionManager")
@Override
public String cmtstockSave(String personID,String clientID,ProductionDataBean productionDataBean,List<ProductionDataBean> stockList) {
	String status="Fail";
	Query q=null;
	BigDecimal total=BigDecimal.valueOf(0);
	if(clientID!=null){
		try{
			q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and invoiceNo=? and status='ACTIVE'");
			q.setParameter(1, clientID);
			q.setParameter(2, productionDataBean.getInvoiceNo());
			List<Cmtreceiveorder> cmtreceiveList=(ArrayList<Cmtreceiveorder>)q.getResultList();
			if(cmtreceiveList.size()>0){
				for (int i = 0; i < stockList.size(); i++) {
				BigDecimal addquant=BigDecimal.valueOf(0);
				BigDecimal orderquant=BigDecimal.valueOf(0);
				q=null;
				q=entityManager.createQuery("from Cmtstock where cmtreceive_ID=? and seri=? and status='ACTIVE'");
				q.setParameter(1, cmtreceiveList.get(0).getReceive_ID());
				q.setParameter(2, stockList.get(i).getSeri());
				List<Cmtstock> cmtstoclList=(ArrayList<Cmtstock>)q.getResultList();
				System.out.println("cmtstoclList size "+cmtstoclList.size());
				if(cmtstoclList.size()==0)
				{
						Cmtstock cmtstock=new Cmtstock();
						cmtstock.setCmtreceive(entityManager.find(Cmtreceiveorder.class, cmtreceiveList.get(0).getReceive_ID()));
						cmtstock.setModel(stockList.get(i).getModelName());
						cmtstock.setSeri(stockList.get(i).getSeri());
						cmtstock.setQuantity(stockList.get(i).getQuantity());
						cmtstock.setOrderQuantity(stockList.get(i).getAddedQuantity());
						cmtstock.setBalanceQuantity(stockList.get(i).getStockinQuantity());
						if(!stockList.get(i).getAddedQuantity().equals("")){
							cmtstock.setAddQuantity(stockList.get(i).getAddedQuantity());
						}else{
							cmtstock.setAddQuantity("0");
						}
						cmtstock.setStatus("ACTIVE");
						cmtstock.setDate(date);
						entityManager.persist(cmtstock);
						entityManager.flush();
						entityManager.clear();
					
				}else{
						Cmtstock cmtstock=entityManager.find(Cmtstock.class, stockList.get(i).getCategoryId());
						if(!stockList.get(i).getAddedQuantity().equals("")){
							cmtstock.setBalanceQuantity(stockList.get(i).getStockinQuantity());
							/*cmtstock.setAddQuantity(stockList.get(i).getAddedQuantity());
							cmtstock.setOrderQuantity(stockList.get(i).getAddedQuantity());*/
							System.out.println("add quantity "+cmtstoclList.get(0).getAddQuantity());
							System.out.println("added quantity "+stockList.get(i).getAddedQuantity());
							addquant=new BigDecimal(cmtstoclList.get(0).getAddQuantity()).add(new BigDecimal(stockList.get(i).getAddedQuantity()));
							orderquant=new BigDecimal(cmtstoclList.get(0).getAddQuantity()).add(new BigDecimal(stockList.get(i).getAddedQuantity()));
							cmtstock.setAddQuantity(addquant.toString());
							cmtstock.setOrderQuantity(orderquant.toString());
							System.out.println("add -- "+addquant);
							System.out.println("order --"+orderquant);
						}
						entityManager.merge(cmtstock);
						entityManager.flush();
						entityManager.clear();
					
				}
			}
				
				//insert cmtstockin table
				for (int i = 0; i < stockList.size(); i++) {
					q=null;
					q=entityManager.createQuery("from Cmtstock where cmtreceive_ID=? and seri=? and status='ACTIVE'");
					q.setParameter(1, cmtreceiveList.get(0).getReceive_ID());
					q.setParameter(2, stockList.get(i).getSeri());
					List<Cmtstock> cmtstoclList=(ArrayList<Cmtstock>)q.getResultList();
					if(cmtstoclList.size()>0){
						if(!stockList.get(i).getAddedQuantity().equals("")){
							CmtStockin cmtStockin=new CmtStockin();
							int id=cmtstoclList.get(0).getCmtstock_ID();
							System.out.println("id -- "+id);
							cmtStockin.setCmtstockId(entityManager.find(Cmtstock.class, id));
							cmtStockin.setCmtreceive(entityManager.find(Cmtreceiveorder.class, cmtreceiveList.get(0).getReceive_ID()));
							cmtStockin.setStockinDate(date);
							cmtStockin.setStockinQuantity(stockList.get(i).getAddedQuantity());
							cmtStockin.setTotalQuantity(cmtstoclList.get(0).getQuantity());
							q=null;
							q=entityManager.createQuery("from Model where person_ID=? and model=? and status='Active'"); 
							q.setParameter(1, personID);
							q.setParameter(2, stockList.get(i).getModelName());
							List<Model> modelList=(List<Model>)q.getResultList();
							System.out.println("--------------modelList size---------->"+modelList.size()); 
							if(modelList.size() > 0){
								cmtStockin.setCmtValue(modelList.get(0).getCmt());
							}
							cmtStockin.setStatus("unpaid");
							entityManager.merge(cmtStockin);
							entityManager.flush();
							entityManager.clear();
						}
					}
				}
				
				//insert Cmtreceiveorder table
				q=null;
				q=entityManager.createQuery("from Cmtstock where cmtreceive_ID=? and status='ACTIVE'");
				q.setParameter(1, cmtreceiveList.get(0).getReceive_ID());
				List<Cmtstock> cmtstoclList1=(ArrayList<Cmtstock>)q.getResultList();
				if(cmtstoclList1.size()>0){
					for(int k=0;k<cmtstoclList1.size();k++){
						if(!cmtstoclList1.get(k).getAddQuantity().equals("")){
							total=total.add(new BigDecimal(cmtstoclList1.get(k).getAddQuantity()));
						}
					}	
				}				
				System.out.println("total "+total+" quantity "+cmtreceiveList.get(0).getTotalQuantity());
				System.out.println("INVOICE-----");
				Cmtreceiveorder cmtreceive=entityManager.find(Cmtreceiveorder.class, cmtreceiveList.get(0).getReceive_ID());
				if(cmtreceiveList.get(0).getTotalQuantity().compareTo(String.valueOf(total))==0){
					cmtreceive.setInvoiceStatus("INVOICE");
					cmtreceive.setStockStatus("ADDED");
				}else{
					cmtreceive.setInvoiceStatus("PARTIAL STOCKIN");
				}
				entityManager.merge(cmtreceive);
				entityManager.flush();entityManager.clear();
				
				//insert stock table
				for(int j=0;j<stockList.size();j++){
				q=null;
				q=entityManager.createQuery("from Stock where itemName=? and seriNo=? and warehouseStatus='Main Warehouse' and cmtStatus='CmtStock'");
				q.setParameter(1, stockList.get(j).getModelName());
				q.setParameter(2, stockList.get(j).getSeri());
				List<Stock> stkList=(ArrayList<Stock>)q.getResultList();
				if(stkList.size()>0){
					Stock stock=entityManager.find(Stock.class, stkList.get(0).getStock_ID());
					if(!stockList.get(j).getAddedQuantity().equalsIgnoreCase("")){
					stock.setStockIn(new BigDecimal(stkList.get(0).getStockIn()).add(new BigDecimal(stockList.get(j).getAddedQuantity())).toString());
					}else{
						stock.setStockIn(stkList.get(0).getStockIn());
					}
					entityManager.merge(stock);
					entityManager.flush();
					entityManager.clear();	
					status="Success";
				}else{
					Stock stock=new Stock();
					stock.setItemName(stockList.get(j).getModelName());
					stock.setSeriNo(stockList.get(j).getSeri());
					stock.setStatus("Active");
					stock.setCmtStatus("CmtStock");
					if(stockList.get(j).getAddedQuantity().equals("")){
						stock.setStockIn("0");
					}else{
					stock.setStockIn(stockList.get(j).getAddedQuantity());
					}
					stock.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
					stock.setStockDate(date);
					stock.setWarehouseStatus("Main Warehouse");
					stock.setStockOut("0");
					entityManager.persist(stock);
					entityManager.flush();
					entityManager.clear();
					status="Success";
				}
				}
			}
			}catch(Exception e){
				e.printStackTrace();
		}
	}
	return status;
}

@Transactional(value="transactionManager")
@Override
public String cmtInvoice(String clientID, String invoiceNo) {
	String status="Fail";
	Query q=null;
	if(clientID!=null){
		try{
			q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and invoiceNo=? and status='ACTIVE' and stockStatus='ADDED'");
			q.setParameter(1, clientID);
			q.setParameter(2, invoiceNo);
			List<Cmtreceiveorder> cmtrecevie=(ArrayList<Cmtreceiveorder>)q.getResultList();
			System.out.println("cmtrecevie --- "+cmtrecevie.size());
			if(cmtrecevie.size()>0){
				Cmtreceiveorder cmt=entityManager.find(Cmtreceiveorder.class, cmtrecevie.get(0).getReceive_ID());
				cmt.setInvoiceStatus("PAYMENT");
				entityManager.merge(cmt);
				
			}
			status="Success";
		}catch(Exception e){
		}
	}
	return status;
}

@Transactional(value="transactionManager")
@Override
public void cmtPayment(String clientID, ProductionDataBean productionDataBean) {
	Query q=null;
	if(clientID!=null){
		try{
			q=entityManager.createQuery("from Cmtreceiveorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
			 q.setParameter(1, productionDataBean.getInvoiceNo());
			 q.setParameter(2, Integer.parseInt(clientID));
			 List<Cmtreceiveorder> purchaseList=(ArrayList<Cmtreceiveorder>)q.getResultList();
			 if(purchaseList.size()>0){
				 productionDataBean.setOrderDate(purchaseList.get(0).getReceiveDate());
				 productionDataBean.setCmt(purchaseList.get(0).getCmt());
				 productionDataBean.setModelName(purchaseList.get(0).getModel());
				 productionDataBean.setTotalAmount(purchaseList.get(0).getAmount());
				 q=null;
				 q=entityManager.createQuery("from Payment where cmt_ID=?");
				 q.setParameter(1, purchaseList.get(0).getReceive_ID());
				 List<Payment> paymentList=(List<Payment>)q.getResultList();
				 if(paymentList.size()==0){
					 Payment payment=new Payment();
					 payment.setCmtReceiver(entityManager.find(Cmtreceiveorder.class, purchaseList.get(0).getReceive_ID()));
					 payment.setTotalAmount(productionDataBean.getTotalAmount());
					 payment.setBalanceAmount(productionDataBean.getTotalAmount());
					 payment.setPaidAmount("0");
					 payment.setStatus("pending");
					 payment.setDate(date);
					 entityManager.persist(payment);
				 }else{
					 productionDataBean.setBalanceAmount(paymentList.get(0).getBalanceAmount());
					 productionDataBean.setPaidAmount(paymentList.get(0).getPaidAmount());
				 }
				 q=null;
				 q=entityManager.createQuery("from Payment where cmt_ID=?");
				 q.setParameter(1, purchaseList.get(0).getReceive_ID());
				 List<Payment> list=(List<Payment>)q.getResultList();
				 if(list.size()>0){
					 productionDataBean.setBalanceAmount(list.get(0).getBalanceAmount());
					 productionDataBean.setPaidAmount(list.get(0).getPaidAmount());
				 }
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

@Transactional(value="transactionManager")
@Override
public String cmtPaymentsave(String clientID,ProductionDataBean productionDataBean) {
	Query q=null;
	String status="Fail";
	BigDecimal balance=BigDecimal.valueOf(0);
	BigDecimal paid=BigDecimal.valueOf(0);
	BigDecimal paying=BigDecimal.valueOf(0);
	BigDecimal amount=BigDecimal.valueOf(0);
	if(clientID!=null){
		try{
			q=entityManager.createQuery("from Cmtreceiveorder where invoiceNo=? and client_ID=? and status='ACTIVE'");
			 q.setParameter(1, productionDataBean.getInvoiceNo());
			 q.setParameter(2, Integer.parseInt(clientID));
			 List<Cmtreceiveorder> purchaseList=(ArrayList<Cmtreceiveorder>)q.getResultList();
			 if(purchaseList.size()>0){
				 q=entityManager.createQuery("from Payment where cmt_ID=?");
				 q.setParameter(1, purchaseList.get(0).getReceive_ID());
				 List<Payment> paymentList=(ArrayList<Payment>)q.getResultList();
				 if(paymentList.size()>0)
				 {
					paying=new BigDecimal(productionDataBean.getPayingAmount());
					paid=new BigDecimal(paymentList.get(0).getPaidAmount());
					balance=new BigDecimal(paymentList.get(0).getBalanceAmount());
					amount=paid.add(paying);
					Payment payment=entityManager.find(Payment.class, paymentList.get(0).getPayment_ID());
					payment.setPaidAmount(paid.add(paying).toString());
					payment.setBalanceAmount(balance.subtract(paying).toString());
					if(amount.compareTo(new BigDecimal(paymentList.get(0).getTotalAmount()))==0){
						payment.setStatus("Paid");
					}else if(amount.compareTo(new BigDecimal(paymentList.get(0).getTotalAmount()))==-1){
						payment.setStatus("Pending");
					}
					entityManager.merge(payment);
					entityManager.flush();
					entityManager.clear();
					status="Success";
					if(payment.getStatus().equalsIgnoreCase("Paid")){
						Cmtreceiveorder purchase=entityManager.find(Cmtreceiveorder.class, purchaseList.get(0).getReceive_ID());
						purchase.setInvoiceStatus("PAID");
						entityManager.merge(purchase);
					}
				 }
			 } 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return status;
}


@Override
public List<ProductionDataBean> productDetails(String personID, String clientID) {
	Query q=null;
	List<ProductionDataBean> beanList=null;
	if(personID!=null && clientID!=null){
		try{
			beanList=new ArrayList<ProductionDataBean>();
			q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and status='ACTIVE' and invoiceStatus!='PAID'  ORDER BY purchase_ID DESC");
			q.setParameter(1, clientID);
			List<Cutterpurchaseorder> cutterList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
			if(cutterList.size()>0){
				for(int i=0;i<cutterList.size();i++){
					ProductionDataBean production=new ProductionDataBean();
					production.setOrderDate(cutterList.get(i).getOrderDate());
					production.setInvoiceNo(cutterList.get(i).getInvoiceNo());
					production.setModelName(cutterList.get(i).getModel());
					production.setQuantity(cutterList.get(i).getTotalQuantity());
					production.setValue(cutterList.get(i).getValue());
					production.setInvoiceStatus("unpaid");
					production.setCutter(cutterList.get(i).getCutter());
					beanList.add(production);
				}
			}
			q=null;
			q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and status='ACTIVE' and invoiceStatus!='PAID' ORDER BY purchase_ID DESC");
			q.setParameter(1, clientID);
			List<Printerreceiveorder> printerList=(ArrayList<Printerreceiveorder>)q.getResultList();
			if(printerList.size()>0){
				for(int i=0;i<printerList.size();i++){
					ProductionDataBean production=new ProductionDataBean();
					production.setOrderDate(printerList.get(i).getReceivedate());
					production.setInvoiceNo(printerList.get(i).getInvoice());
					production.setModelName(printerList.get(i).getModel());
					production.setQuantity(printerList.get(i).getTotalquantitry());
					production.setValue(printerList.get(i).getValue());
					production.setInvoiceStatus("unpaid");
					production.setPrinter(printerList.get(i).getPrinter());
					beanList.add(production);
				}
			}
			q=null;
			q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and status='ACTIVE' and invoiceStatus!='PAID'  ORDER BY receive_ID DESC");
			q.setParameter(1, clientID);
			List<Cmtreceiveorder> cmtList=(ArrayList<Cmtreceiveorder>)q.getResultList();
			if(cmtList.size()>0){
				for(int i=0;i<cmtList.size();i++){
					ProductionDataBean production=new ProductionDataBean();
					production.setOrderDate(cmtList.get(i).getReceiveDate());
					production.setInvoiceNo(cmtList.get(i).getInvoiceNo());
					production.setModelName(cmtList.get(i).getModel());
					production.setQuantity(cmtList.get(i).getTotalQuantity());
					production.setValue(cmtList.get(i).getAmount());
					production.setInvoiceStatus("unpaid");
					production.setCmt(cmtList.get(i).getCmt());
					beanList.add(production);
				}
			}
		}catch(Exception e){
		}
	}
	return beanList;
}

@Override
public List<ProductionDataBean> productHistory(String clientID, String personID) {
	Query q=null;
	List<ProductionDataBean> beanList=null;
	if(personID!=null && clientID!=null){
	try{
		beanList=new ArrayList<ProductionDataBean>();
		q=entityManager.createQuery("select model from Cutterpurchaseorder where client_ID=? and status='ACTIVE'");
		q.setParameter(1, clientID);
		List<String> modelList=(ArrayList<String>)q.getResultList();
		Set<String> dublicateList=new HashSet<String>(modelList);
		modelList.clear();
		modelList.addAll(dublicateList);
 		if(modelList.size()>0){
		for(int a=0;a<modelList.size();a++){
		q=null;
		q=entityManager.createQuery("from Cutterpurchaseorder where  client_ID=? and model=? and status='ACTIVE' and invoiceStatus='PAID'");
		q.setParameter(1, clientID);
		q.setParameter(2, modelList.get(a));
		List<Cutterpurchaseorder> orderlist=(ArrayList<Cutterpurchaseorder>)q.getResultList();
		if(orderlist.size()>0){
			for(int k=0;k<orderlist.size();k++){
				q=null;
				q=entityManager.createQuery("from Cutterpurchase where purchase_ID=? and status='ACTIVE' ");
				q.setParameter(1, orderlist.get(k).getPurchase_ID());
				List<Cutterpurchase> purchaseList=(ArrayList<Cutterpurchase>)q.getResultList();
				if(purchaseList.size()>0){
					for(int j=0;j<purchaseList.size();j++){
						ProductionDataBean pro=new ProductionDataBean();
						pro.setModelName(orderlist.get(k).getModel());
						pro.setCutter(orderlist.get(k).getCutter());
						if(orderlist.get(k).getInvoiceStatus().equalsIgnoreCase("PAID"))
							pro.setInvoiceStatus(orderlist.get(k).getInvoiceStatus());
						else
							pro.setInvoiceStatus("unpaid");
						pro.setSeri(purchaseList.get(j).getSeri());
						pro.setQuantity(purchaseList.get(j).getQuantity());
						beanList.add(pro);
					}
				}
			}
		}
		}
		}
 		q=null;
 		q=entityManager.createQuery("select model from Printerreceiveorder where client_ID=? and status='ACTIVE'");
		q.setParameter(1, clientID);
		List<String> modelList1=(ArrayList<String>)q.getResultList();
		Set<String> dublicateList1=new HashSet<String>(modelList1);
		modelList1.clear();
		modelList1.addAll(dublicateList1);
 		if(modelList1.size()>0){
 			for(int r=0;r<modelList1.size();r++){
 				q=null;
 				q=entityManager.createQuery("from Printerreceiveorder where model=? and client_ID=? and status='ACTIVE' and invoiceStatus='PAID'");
 				q.setParameter(1, modelList1.get(r));
 				q.setParameter(2, clientID);
 				List<Printerreceiveorder> receiverList=(ArrayList<Printerreceiveorder>)q.getResultList();
 				if(receiverList.size()>0){
 					for(int m=0;m<receiverList.size();m++){
 						q=null;
 						q=entityManager.createQuery("from Printerorder where purchase_id=? and status='ACTIVE'");
 						q.setParameter(1, receiverList.get(m).getPurchase_ID());
 						List<Printerorder> printerList=(ArrayList<Printerorder>)q.getResultList();
 						if(printerList.size()>0){
 							for(int p=0;p<printerList.size();p++){
 								ProductionDataBean pro=new ProductionDataBean();
 								pro.setPrinter(receiverList.get(m).getPrinter());
 								pro.setModelName(receiverList.get(m).getModel());
 								if(receiverList.get(m).getInvoiceStatus().equalsIgnoreCase("PAID"))
 									pro.setInvoiceStatus(receiverList.get(m).getInvoiceStatus());
 								else
 									pro.setInvoiceStatus("unpaid");
 								pro.setQuantity(printerList.get(p).getQuantity());
 								pro.setSeri(printerList.get(p).getSer());
 								beanList.add(pro);
 							}
 						}
 					}
 				}
 			}
 		}
 		q=null;
 		q=entityManager.createQuery("select model from Cmtreceiveorder where client_ID=? and status='ACTIVE'");
		q.setParameter(1, clientID);
		List<String> modelList2=(ArrayList<String>)q.getResultList();
		Set<String> dublicateList2=new HashSet<String>(modelList2);
		modelList2.clear();
		modelList2.addAll(dublicateList2);
		if(modelList2.size()>0){
			for(int c=0;c<modelList2.size();c++){
				q=null;
				q=entityManager.createQuery("from Cmtreceiveorder where model=? and client_ID=? and status='ACTIVE' and invoiceStatus='PAID'");
				q.setParameter(1, modelList2.get(c));
				q.setParameter(2, clientID);
				List<Cmtreceiveorder> cmtreceiveList=(ArrayList<Cmtreceiveorder>)q.getResultList();
				if(cmtreceiveList.size()>0){
					for(int b=0;b<cmtreceiveList.size();b++){
						q=null;
						q=entityManager.createQuery("from Cmtorder where receive_ID=? and status='ACTIVE'");
						q.setParameter(1, cmtreceiveList.get(b).getReceive_ID());
						List<Cmtorder> cmtorderList=(ArrayList<Cmtorder>)q.getResultList();
						if(cmtorderList.size()>0){
							for(int d=0;d<cmtorderList.size();d++){
								ProductionDataBean pro=new ProductionDataBean();
								pro.setCmt(cmtreceiveList.get(b).getCmt());
								pro.setModelName(cmtreceiveList.get(b).getModel());
								if(cmtreceiveList.get(b).getInvoiceStatus().equalsIgnoreCase("PAID"))
									pro.setInvoiceStatus(cmtreceiveList.get(b).getInvoiceStatus());
								else
									pro.setInvoiceStatus("unpaid");
								pro.setQuantity(cmtorderList.get(d).getQuantity());
								pro.setSeri(cmtorderList.get(d).getSeri());
								beanList.add(pro);
							}
						}
					}
				}
			}
		}
		}catch(Exception e){
		}
	}
	return beanList;
}
@Override
public String supplyview(String personID, ReturnDataBean returnDataBean) {
	Query q=null;
	List<ReturnDataBean>list=new ArrayList<ReturnDataBean>();	
	try{
		q=entityManager.createQuery("from SupplyReturn where itemName=? and date=? and person_ID=? and status='ACTIVE' ");
		q.setParameter(1,returnDataBean.getItemname());
		q.setParameter(2,returnDataBean.getDate());
		q.setParameter(3,personID);
		List<SupplyReturn>subreturn=(List<SupplyReturn>)q.getResultList();		
		if(subreturn.size()>0){	
			returnDataBean.setSupplyreturnid(subreturn.get(0).getSupplyReturn_id());
			ReturnDataBean view=new ReturnDataBean();
			view.setDate(subreturn.get(0).getDate());
			view.setItemname(subreturn.get(0).getItemName());
			view.setQuantity(subreturn.get(0).getQuantity());
			view.setNetprice(subreturn.get(0).getNetAmount());
			view.setPrice(subreturn.get(0).getPrice());
			view.setQuantity1(subreturn.get(0).getQuantity());
			list.add(view);			
		}
		returnDataBean.setReturnDataBeanList(list);		
	}catch(Exception e){
		e.printStackTrace();
	}	
	return null;
}

@Override
@Transactional(value="transactionManager")
public String update(String personID, ReturnDataBean returnDataBean){
	String status="Fail";
	Query q=null;
	try{
		q=entityManager.createQuery("from Stock where itemName=? and person_ID=? and cmtStatus='Raw'");
		q.setParameter(1, returnDataBean.getReturnDataBeanList().get(0).getItemname());
		q.setParameter(2, personID);
		List<Stock> subreturn=(List<Stock>)q.getResultList();
		System.out.println("okkkk"+subreturn.size()+"------"+returnDataBean.getReturnDataBeanList().get(0).getQuantity()
				+"---------->"+subreturn.get(0).getStock_ID());		
		if (subreturn.size()>0) {
			Stock sub=entityManager.find(Stock.class, subreturn.get(0).getStock_ID());
	         sub.setStockIn(String.valueOf((new BigDecimal(subreturn.get(0).getStockIn()).add(new BigDecimal(returnDataBean.getQuantity1())))
	        		 .subtract(new BigDecimal(returnDataBean.getReturnDataBeanList().get(0).getQuantity()))));
			entityManager.merge(sub);
			q=entityManager.createQuery("from SupplyReturn where itemName=? and date=? and Status='ACTIVE'");
			q.setParameter(1, returnDataBean.getItemname());
			q.setParameter(2, returnDataBean.getDate());
			List<SupplyReturn>views=(List<SupplyReturn>)q.getResultList();
			if (views.size()>0) {
				SupplyReturn view=entityManager.find(SupplyReturn.class, views.get(0).getSupplyReturn_id());
				view.setQuantity(returnDataBean.getReturnDataBeanList().get(0).getQuantity());
				entityManager.merge(view);
			}			
		}		
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Override
public String updates(String personID, ReturnDataBean returnDataBean) {
	System.out.println("iiiiiiiii");
	String status="Fail";
	Query q=null;
	try{
		q=entityManager.createQuery("from Stock where itemName=? and person_ID=? and cmtStatus='Raw'");
		q.setParameter(1, returnDataBean.getItemname());
		q.setParameter(2, personID);
		List<Stock> stock=(List<Stock>)q.getResultList();
		System.out.println("okkkk"+stock.size()+"------"+returnDataBean.getQuantity()+"---------->"+stock.get(0).getStock_ID());		
		if (stock.size()>0) {
			returnDataBean.setStockin(stock.get(0).getStockIn());		
		}		
	}catch(Exception e){
		e.printStackTrace();
	}	
	return null;
}

@Transactional(value="transactionManager")
@Override
public String updatedelete(String personID, ReturnDataBean returnDataBean) {
	Query v=null;
	try{
		v=entityManager.createQuery("from  SupplyReturn where itemName=? and date=? and status='ACTIVE'");
		v.setParameter(1, returnDataBean.getItemname());
		v.setParameter(2, returnDataBean.getDate());
	    List<SupplyReturn> supplys=(List<SupplyReturn>)v.getResultList();
		if(supplys.size()>0){
			SupplyReturn supplier=entityManager.find(SupplyReturn.class, supplys.get(0).getSupplyReturn_id());
			supplier.setStatus("DEACTIVE");
			entityManager.merge(supplier);
			v=entityManager.createQuery("from Stock where itemName=? and person_ID=? and cmtStatus='Raw'");
			v.setParameter(1, returnDataBean.getItemname());
			v.setParameter(2, personID);
			List<Stock >liststock=(List<Stock>)v.getResultList();
			if (liststock.size()>0) {
				Stock liStock=entityManager.find(Stock.class, liststock.get(0).getStock_ID());
				liStock.setStockIn(String.valueOf(new BigDecimal(liststock.get(0).getStockIn()).
						add(new BigDecimal(supplys.get(0).getQuantity())))); 
				entityManager.merge(liStock);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

//========================CMT RETURN==================*/

@Override
public List<StockDataBean> getcmtTable(String personID,StockDataBean stockDataBean) {
	Query q=null;
	List<StockDataBean>viewList=new ArrayList<StockDataBean>();
	System.out.println("-----------------------inside getcmtTable-------------------------"); 
		try
			{
				q=entityManager.createQuery("from Stock where person_ID=? and itemName=? and seriNo=? and cmtStatus='CmtStock' and status='Active' and warehouseStatus='Main Warehouse' ");
				q.setParameter(1, personID);
				q.setParameter(2, stockDataBean.getItemname());
				q.setParameter(3, stockDataBean.getSeriNolist());
				List<Stock> stockList=(List<Stock>)q.getResultList();
				System.out.println(stockList.size());
					if(stockList.size() > 0){
						for (int i = 0; i < stockList.size(); i++) {
							StockDataBean obj=new StockDataBean();
							obj.setSerialNo(String.valueOf(i+1)); 
							obj.setShop(stockList.get(i).getWarehouseStatus());
							obj.setModel(stockList.get(i).getItemName()); 
							obj.setSeriDetails(stockList.get(i).getSeriNo()); 
							obj.setStock(stockList.get(i).getStockIn());
							obj.setStockID(stockList.get(i).getStock_ID());  
							q=null;
							System.out.println("-----------stock list seriNo------->"+stockList.get(i).getSeriNo()+"--------model name------>"+stockDataBean.getItemname());
							q=entityManager.createQuery("from Model where person_ID=? and model=? and status='Active'"); 
								q.setParameter(1, personID);
								q.setParameter(2, stockDataBean.getItemname());
								List<Model> modelList=(List<Model>)q.getResultList();
								System.out.println("--------------modelList size---------->"+modelList.size()); 
								if(modelList.size() > 0){
									obj.setPrice(modelList.get(0).getSellPrice()); 
								}
								viewList.add(obj);
						}				
					}
 			}
		catch(Exception e)
			{
				e.printStackTrace();
			}
	return viewList;
}

public List<String> getseriNo(String valuechange, String personID){
	Query q=null;
	List<String> seriNo=new ArrayList<String>();
	try{
		System.out.println("inside seri");
		q=entityManager.createQuery("select seriNo from Stock where person_ID=? and status='Active' and itemName=?");
		System.out.println("inside query");
		q.setParameter(1, personID);
		q.setParameter(2, valuechange); 
		seriNo=q.getResultList();
		System.out.println(seriNo);
		HashSet<String> hashList=new HashSet<String>(seriNo); 
		seriNo.clear();
		seriNo.addAll(hashList);
	}catch(Exception e){
		e.printStackTrace();
	}	
	return seriNo;
}

@Transactional(value="transactionManager")
@Override
public List<StockDataBean> getcmtSave(StockDataBean stockDataBean,String personID) {
	Query q=null;
	try{
		for(int i=0 ; i < stockDataBean.getCmttableview().size() ; i++){
			System.out.println("==============stockID==========="+stockDataBean.getCmttableview().get(i).getStockID()); 
			int personid=Integer.parseInt(personID);
			q=entityManager.createQuery("from CmtReturn where person_ID=? and model=? and seri=? and shop=? and date=? and status='Active'");
			q.setParameter(1, personID);
			q.setParameter(2, stockDataBean.getCmttableview().get(i).getModel());
			q.setParameter(3,stockDataBean.getCmttableview().get(i).getSeriDetails());
			q.setParameter(4, stockDataBean.getCmttableview().get(i).getShop());
			q.setParameter(5, stockDataBean.getDate());
			List<CmtReturn> cmtreturnList=(List<CmtReturn>)q.getResultList();
			System.out.println("size "+cmtreturnList.size());
			if(cmtreturnList.size()>0){
				CmtReturn cmtReturns=entityManager.find(CmtReturn.class, cmtreturnList.get(0).getCmtReturnId());
				cmtReturns.setQuantity(new BigDecimal(cmtreturnList.get(0).getQuantity()).add(new BigDecimal(stockDataBean.getCmttableview().get(0).getQty())).toString());
				entityManager.merge(cmtReturns);
			}else{
				CmtReturn cmtReturn=new CmtReturn();
				cmtReturn.setShop(stockDataBean.getCmttableview().get(i).getShop()); 
				cmtReturn.setModel(stockDataBean.getCmttableview().get(i).getModel()); 
				cmtReturn.setSeri(stockDataBean.getCmttableview().get(i).getSeriDetails()); 
				cmtReturn.setQuantity(stockDataBean.getCmttableview().get(i).getQty());   
				cmtReturn.setPrice(stockDataBean.getCmttableview().get(i).getPrice()); 
				cmtReturn.setNetprice(stockDataBean.getCmttableview().get(i).getNetprice()); 
				cmtReturn.setDate(stockDataBean.getDate());
				cmtReturn.setStatus("Active");   
				cmtReturn.setPerson(entityManager.find(Person.class, personid));
				cmtReturn.setStocks(entityManager.find(Stock.class, stockDataBean.getCmttableview().get(i).getStockID()));			
				entityManager.persist(cmtReturn); 
				entityManager.flush();
				entityManager.clear();
			}
			Stock stock=entityManager.find(Stock.class, stockDataBean.getCmttableview().get(i).getStockID()); 
			stock.setStockIn(new BigDecimal(stockDataBean.getCmttableview().get(i).getStock()).subtract(new BigDecimal(stockDataBean.getCmttableview().get(i).getQty())).toString());   
			entityManager.merge(stock);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return null; 
}

@Override
public List<StockDataBean> getmtReturn(StockDataBean stockDataBean,String personID) {
	Query q=null;
	List<StockDataBean>cmttableList=new ArrayList<StockDataBean>();
	try{
		q=entityManager.createQuery("from CmtReturn where person_ID=? and status='Active'"); 
		q.setParameter(1, personID);
		List<CmtReturn>cmt=(List<CmtReturn>)q.getResultList();
		if(cmt.size() > 0){
			for(int i=0;i < cmt.size();i++){
				StockDataBean obj=new StockDataBean();
				obj.setReturnmodel(cmt.get(i).getModel());
				obj.setSerialNo(String.valueOf(i+1));
				obj.setDate(cmt.get(i).getDate());
				obj.setReturnshop(cmt.get(i).getShop()); 
				obj.setReturnseri(cmt.get(i).getSeri()); 
				obj.setReturnstatus(cmt.get(i).getStatus()); 
				obj.setReturnquantity(cmt.get(i).getQuantity()); 
				obj.setReturnprice(cmt.get(i).getPrice()); 
				obj.setReturnnetprice(cmt.get(i).getNetprice()); 
				obj.setReturnID(cmt.get(i).getCmtReturnId()); 
				cmttableList.add(obj);  
				
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return cmttableList;
}

@Override
public List<StockDataBean> getmtReturn1(StockDataBean stockDataBean,String personID) {
	Query q=null;
	List<StockDataBean> cmttableList1=new ArrayList<StockDataBean>();
	try{
		q=entityManager.createQuery("from CmtReturn where cmt_return_id=? and person_ID=? and status='Active'"); 
		q.setParameter(1, stockDataBean.getReturnID());
		q.setParameter(2, personID);		
		List<CmtReturn>cmt=(List<CmtReturn>)q.getResultList();
		if(cmt.size() > 0){
			for(int i=0;i < cmt.size();i++){
				StockDataBean obj=new StockDataBean();
				obj.setReturnmodel1(cmt.get(i).getModel()); 
				obj.setReturnshop1(cmt.get(i).getShop()); 
				obj.setReturnseri1(cmt.get(i).getSeri()); 
				obj.setReturnstatus1(cmt.get(i).getStatus()); 
				obj.setReturnquantity1(cmt.get(i).getQuantity()); 
				obj.setReturnprice1(cmt.get(i).getPrice()); 
				obj.setReturnnetprice1(cmt.get(i).getNetprice()); 
				cmttableList1.add(obj);  
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return cmttableList1;
}

@Transactional(value="transactionManager")
@Override
public void getcmtDelete(StockDataBean stockDataBean, String personID) {
	Query q=null;
	try{
		
		q=entityManager.createQuery("from Stock where person_ID=? and itemName=? and seriNo=? and warehouseStatus=? and cmtStatus='CmtStock' and status='Active'");
		q.setParameter(1, personID); 
		q.setParameter(2, stockDataBean.getReturnmodel());
		q.setParameter(3, stockDataBean.getReturnseri());
		q.setParameter(4, stockDataBean.getReturnshop());
		List<Stock> stockList=(List<Stock>)q.getResultList();
		if(stockList.size()>0){
			q=entityManager.createQuery("from CmtReturn where person_ID=? and model=? and seri=? and shop=? and date=? and status='Active'");
			q.setParameter(1, personID);
			q.setParameter(2, stockDataBean.getReturnmodel());
			q.setParameter(3, stockDataBean.getReturnseri());
			q.setParameter(4, stockDataBean.getReturnshop());
			q.setParameter(5, stockDataBean.getDate());
			List<CmtReturn> cmtreturnList=(List<CmtReturn>)q.getResultList();
			System.out.println("List Size===========================>"+cmtreturnList);
			System.out.println("QUANTITY============================>"+cmtreturnList.get(0).getQuantity()); 
			System.out.println(stockList.get(0).getStockIn());
			if(cmtreturnList.size()>0){
				CmtReturn cmtreturn=entityManager.find(CmtReturn.class, stockDataBean.getReturnID());
				cmtreturn.setStatus("Deactive");  
				entityManager.merge(cmtreturn); 				
			}
			Stock stok=entityManager.find(Stock.class, stockList.get(0).getStock_ID());
			stok.setStockIn(new BigDecimal(stockList.get(0).getStockIn()).add(new BigDecimal(cmtreturnList.get(0).getQuantity())).toString());
			entityManager.merge(stok);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	
}


@Override
public List<StockDataBean> getedit(StockDataBean stockDataBean, String personID) {
	Query q=null;
	List<StockDataBean>cmttableList2=new ArrayList<StockDataBean>();
	try{
		q=entityManager.createQuery("from CmtReturn where cmt_return_id=? and person_ID=? and status='Active'"); 
		q.setParameter(1, stockDataBean.getReturnID());
		q.setParameter(2, personID);		
		List<CmtReturn>cmt=(List<CmtReturn>)q.getResultList();
		if(cmt.size() > 0){
			for(int i=0;i < cmt.size();i++){
				StockDataBean obj=new StockDataBean();
				obj.setReturnmodel1(cmt.get(i).getModel()); 
				obj.setReturnshop1(cmt.get(i).getShop()); 
				obj.setReturnseri1(cmt.get(i).getSeri()); 
				obj.setReturnstatus1(cmt.get(i).getStatus()); 
				obj.setReturnquantity1(cmt.get(i).getQuantity()); 
				obj.setReturnprice1(cmt.get(i).getPrice()); 
				obj.setReturnnetprice1(cmt.get(i).getNetprice()); 
				cmttableList2.add(obj);  
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return cmttableList2;
}

@Transactional(value="transactionManager")
@Override
public String cmtreturnEdit(String personID, List<StockDataBean> returnList2,
		StockDataBean stockDataBean) {
	System.out.println("quantirtyyy----"+returnList2.get(0).getReturnquantity1());
	String status="Fail";
	if(personID!=null){
	try{
		CmtReturn cmt=entityManager.find(CmtReturn.class, stockDataBean.getReturnID());
		cmt.setQuantity(returnList2.get(0).getReturnquantity1());
		entityManager.merge(cmt);
		status="Success";
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	return status;
}


@Override
public List<ProductionDataBean> getproductionData(String personID,String clientID,ProductionDataBean productionDataBean) {
	Query q=null;
	List<ProductionDataBean> beanList=null;
	if(personID!=null && clientID!=null){
		try{
			beanList=new ArrayList<ProductionDataBean>();
			if(productionDataBean.getCategoryName().equalsIgnoreCase("ALL") || productionDataBean.getCategoryName().equalsIgnoreCase("")){
				if(productionDataBean.getFromDate()!= null && productionDataBean.getToDate()!= null){
					q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and orderDate between ? and ? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, clientID);
					q.setParameter(2, productionDataBean.getFromDate());
					q.setParameter(3, productionDataBean.getToDate());
				}else{
					q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, clientID);
				}
				List<Cutterpurchaseorder> cutterList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
				if(cutterList.size()>0){
					for(int i=0;i<cutterList.size();i++){
						ProductionDataBean production=new ProductionDataBean();
						production.setOrderDate(cutterList.get(i).getOrderDate());
						production.setInvoiceNo(cutterList.get(i).getInvoiceNo());
						production.setModelName(cutterList.get(i).getModel());
						production.setQuantity(cutterList.get(i).getTotalQuantity());
						production.setValue(cutterList.get(i).getValue());
						if(cutterList.get(i).getInvoiceStatus().equalsIgnoreCase("PAID")){
							production.setInvoiceStatus("paid");
						}else{
							production.setInvoiceStatus("unpaid");
						}
						production.setCutter(cutterList.get(i).getCutter());
						beanList.add(production);
					}
				}
				q=null;
				if(productionDataBean.getFromDate()!= null && productionDataBean.getToDate()!= null){
					q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and receivedate between ? and ? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, clientID);
					q.setParameter(2, productionDataBean.getFromDate());
					q.setParameter(3, productionDataBean.getToDate());
				}else{
					q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, clientID);
				}
				List<Printerreceiveorder> printerList=(ArrayList<Printerreceiveorder>)q.getResultList();
				if(printerList.size()>0){
					for(int i=0;i<printerList.size();i++){
						ProductionDataBean production=new ProductionDataBean();
						production.setOrderDate(printerList.get(i).getReceivedate());
						production.setInvoiceNo(printerList.get(i).getInvoice());
						production.setModelName(printerList.get(i).getModel());
						production.setQuantity(printerList.get(i).getTotalquantitry());
						production.setValue(printerList.get(i).getValue());
						if(printerList.get(i).getInvoiceStatus().equalsIgnoreCase("PAID")){
							production.setInvoiceStatus("paid");
						}else{
							production.setInvoiceStatus("unpaid");
						}
						production.setPrinter(printerList.get(i).getPrinter());
						beanList.add(production);
					}
				}
				q=null;
				if(productionDataBean.getFromDate()!= null && productionDataBean.getToDate()!= null){
					q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and receiveDate between ? and ? and status='ACTIVE' ORDER BY receive_ID DESC");
					q.setParameter(1, clientID);
					q.setParameter(2, productionDataBean.getFromDate());
					q.setParameter(3, productionDataBean.getToDate());
				}else{
					q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and status='ACTIVE' ORDER BY receive_ID DESC");
					q.setParameter(1, clientID);
				}
				List<Cmtreceiveorder> cmtList=(ArrayList<Cmtreceiveorder>)q.getResultList();
				if(cmtList.size()>0){
					for(int i=0;i<cmtList.size();i++){
						ProductionDataBean production=new ProductionDataBean();
						production.setOrderDate(cmtList.get(i).getReceiveDate());
						production.setInvoiceNo(cmtList.get(i).getInvoiceNo());
						production.setModelName(cmtList.get(i).getModel());
						production.setQuantity(cmtList.get(i).getTotalQuantity());
						production.setValue(cmtList.get(i).getAmount());
						if(cmtList.get(i).getInvoiceStatus().equalsIgnoreCase("PAID")){
							production.setInvoiceStatus("paid");
						}
						else{
							production.setInvoiceStatus("unpaid");
						}
						production.setCmt(cmtList.get(i).getCmt());
						beanList.add(production);
					}
				}
				productionDataBean.setCutterflag(true);productionDataBean.setPrinterflag(true);productionDataBean.setCmtflag(true);
			}else if(productionDataBean.getCategoryName().equalsIgnoreCase("CUTTER")){
				if(productionDataBean.getFromDate()!= null && productionDataBean.getToDate()!= null){
					q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and orderDate between ? and ? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, clientID);
					q.setParameter(2, productionDataBean.getFromDate());
					q.setParameter(3, productionDataBean.getToDate());
				}else{
					q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, clientID);
				}
				List<Cutterpurchaseorder> cutterList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
				if(cutterList.size()>0){
					for(int i=0;i<cutterList.size();i++){
						ProductionDataBean production=new ProductionDataBean();
						production.setOrderDate(cutterList.get(i).getOrderDate());
						production.setInvoiceNo(cutterList.get(i).getInvoiceNo());
						production.setModelName(cutterList.get(i).getModel());
						production.setQuantity(cutterList.get(i).getTotalQuantity());
						production.setValue(cutterList.get(i).getValue());
						if(cutterList.get(i).getInvoiceStatus().equalsIgnoreCase("PAID")){
							production.setInvoiceStatus("paid");
						}else{
							production.setInvoiceStatus("unpaid");
						}
						production.setCutter(cutterList.get(i).getCutter());
						beanList.add(production);
					}
				}
				productionDataBean.setCutterflag(true);productionDataBean.setPrinterflag(false);productionDataBean.setCmtflag(false);
			}else if(productionDataBean.getCategoryName().equalsIgnoreCase("PRINTER")){
				q=null;
				if(productionDataBean.getFromDate()!= null && productionDataBean.getToDate()!= null){
					q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and receivedate between ? and ? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, clientID);
					q.setParameter(2, productionDataBean.getFromDate());
					q.setParameter(3, productionDataBean.getToDate());
				}else{
					q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and status='ACTIVE' ORDER BY purchase_ID DESC");
					q.setParameter(1, clientID);
				}
				List<Printerreceiveorder> printerList=(ArrayList<Printerreceiveorder>)q.getResultList();
				if(printerList.size()>0){
					for(int i=0;i<printerList.size();i++){
						ProductionDataBean production=new ProductionDataBean();
						production.setOrderDate(printerList.get(i).getReceivedate());
						production.setInvoiceNo(printerList.get(i).getInvoice());
						production.setModelName(printerList.get(i).getModel());
						production.setQuantity(printerList.get(i).getTotalquantitry());
						production.setValue(printerList.get(i).getValue());
						if(printerList.get(i).getInvoiceStatus().equalsIgnoreCase("PAID")){
							production.setInvoiceStatus("paid");
						}else{
							production.setInvoiceStatus("unpaid");
						}
						production.setPrinter(printerList.get(i).getPrinter());
						beanList.add(production);
					}
				}productionDataBean.setCutterflag(false);productionDataBean.setPrinterflag(true);productionDataBean.setCmtflag(false);
			}else if(productionDataBean.getCategoryName().equalsIgnoreCase("CMT")){
				q=null;
				if(productionDataBean.getFromDate()!= null && productionDataBean.getToDate()!= null){
					q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and receiveDate between ? and ? and status='ACTIVE' ORDER BY receive_ID DESC");
					q.setParameter(1, clientID);
					q.setParameter(2, productionDataBean.getFromDate());
					q.setParameter(3, productionDataBean.getToDate());
				}else{
					q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and status='ACTIVE' ORDER BY receive_ID DESC");
					q.setParameter(1, clientID);
				}
				List<Cmtreceiveorder> cmtList=(ArrayList<Cmtreceiveorder>)q.getResultList();
				if(cmtList.size()>0){
					for(int i=0;i<cmtList.size();i++){
						ProductionDataBean production=new ProductionDataBean();
						production.setOrderDate(cmtList.get(i).getReceiveDate());
						production.setInvoiceNo(cmtList.get(i).getInvoiceNo());
						production.setModelName(cmtList.get(i).getModel());
						production.setQuantity(cmtList.get(i).getTotalQuantity());
						production.setValue(cmtList.get(i).getAmount());
						if(cmtList.get(i).getInvoiceStatus().equalsIgnoreCase("PAID")){
							production.setInvoiceStatus("paid");
						}
						else{
							production.setInvoiceStatus("unpaid");
						}
						production.setCmt(cmtList.get(i).getCmt());
						beanList.add(production);
					}
				}productionDataBean.setCutterflag(false);productionDataBean.setPrinterflag(false);productionDataBean.setCmtflag(true);
			}
		}catch(Exception e){
		}
	}
	return beanList;
}


@Override
public void productionView(String clientID, ProductionDataBean productionDataBean) {
	Query q=null;
	List<ProductionDataBean> beanList=new ArrayList<ProductionDataBean>();
	if(clientID!=null){ 
	try{
		if(productionDataBean.getInvoiceNo().startsWith("CU")){
			q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and invoiceNo=? and status='ACTIVE'");
			q.setParameter(1, clientID);
			q.setParameter(2, productionDataBean.getInvoiceNo());
			List<Cutterpurchaseorder> cutterList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
			if(cutterList.size()>0){
				productionDataBean.setModelName(cutterList.get(0).getModel());
				productionDataBean.setOrderDate(cutterList.get(0).getOrderDate());
				productionDataBean.setTotalQuantity(cutterList.get(0).getTotalQuantity());
				productionDataBean.setTotalValue(cutterList.get(0).getValue());
				productionDataBean.setCmt(cutterList.get(0).getCutter());
				q=null;
				q=entityManager.createQuery("from Cutterpurchase where purchase_ID=? and status='ACTIVE'");
				q.setParameter(1, cutterList.get(0).getPurchase_ID());
				List<Cutterpurchase> purchaseList=(ArrayList<Cutterpurchase>)q.getResultList();
				if(purchaseList.size()>0){
					for(int p=0;p<purchaseList.size();p++){
						ProductionDataBean pro=new ProductionDataBean();
						pro.setSerialNo(String.valueOf(p+1));
						pro.setSeri(purchaseList.get(p).getSeri());
						pro.setQuantity(purchaseList.get(p).getQuantity());
						beanList.add(pro);
					}
					productionDataBean.setOrderQuantitylist(beanList);
				}
			}
		}else if(productionDataBean.getInvoiceNo().startsWith("Pr")){
			q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and invoice=? and status='ACTIVE'");
			q.setParameter(1, clientID);
			q.setParameter(2, productionDataBean.getInvoiceNo());
			List<Printerreceiveorder> printerList=(ArrayList<Printerreceiveorder>)q.getResultList();
			if(printerList.size()>0){
				productionDataBean.setModelName(printerList.get(0).getModel());
				productionDataBean.setOrderDate(printerList.get(0).getReceivedate());
				productionDataBean.setTotalQuantity(printerList.get(0).getTotalquantitry());
				productionDataBean.setTotalValue(printerList.get(0).getValue());
				productionDataBean.setCmt(printerList.get(0).getPrinter());
				q=null;
				q=entityManager.createQuery("from Printerorder where purchase_id=? and status='ACTIVE'");
				q.setParameter(1, printerList.get(0).getPurchase_ID());
				List<Printerorder> purchaseList=(ArrayList<Printerorder>)q.getResultList();
				if(purchaseList.size()>0){
					for(int p=0;p<purchaseList.size();p++){
						ProductionDataBean pro=new ProductionDataBean();
						pro.setSerialNo(String.valueOf(p+1));
						pro.setSeri(purchaseList.get(p).getSer());
						pro.setMotive(purchaseList.get(p).getMotive());
						pro.setValue(purchaseList.get(p).getValue());
						pro.setQuantity(purchaseList.get(p).getQuantity());
						beanList.add(pro);
					}
					productionDataBean.setOrderQuantitylist(beanList);
				}
			}
		}else if(productionDataBean.getInvoiceNo().startsWith("CMR")){
			q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and invoiceNo=? and status='ACTIVE'");
			q.setParameter(1, clientID);
			q.setParameter(2, productionDataBean.getInvoiceNo());
			List<Cmtreceiveorder> cmtList=(ArrayList<Cmtreceiveorder>)q.getResultList();
			if(cmtList.size()>0){
				productionDataBean.setModelName(cmtList.get(0).getModel());
				productionDataBean.setOrderDate(cmtList.get(0).getReceiveDate());
				productionDataBean.setTotalQuantity(cmtList.get(0).getTotalQuantity());
				productionDataBean.setTotalValue(cmtList.get(0).getAmount());
				productionDataBean.setCmt(cmtList.get(0).getCmt());
				q=null;
				q=entityManager.createQuery("from Cmtorder where receive_ID=? and status='ACTIVE'");
				q.setParameter(1, cmtList.get(0).getReceive_ID());
				List<Cmtorder> purchaseList=(ArrayList<Cmtorder>)q.getResultList();
				if(purchaseList.size()>0){
					for(int p=0;p<purchaseList.size();p++){
						ProductionDataBean pro=new ProductionDataBean();
						pro.setSerialNo(String.valueOf(p+1));
						pro.setSeri(purchaseList.get(p).getSeri());
						pro.setMotive(purchaseList.get(p).getMotive());
						pro.setValue(purchaseList.get(p).getValue());
						beanList.add(pro);
					}
					productionDataBean.setOrderQuantitylist(beanList);
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	}
}

public void getOpeningStockProducts(String personID,StockDataBean stockDataBean){
	Query v=null;
	try{
		if(stockDataBean.getType().equals("raw")){
			v=entityManager.createQuery("select itemName from ItemTable where person_ID=? and status='ACTIVE'");
			v.setParameter(1, Integer.parseInt(personID));
			List<String> items=(List<String>)v.getResultList();
			HashSet<String> item=new HashSet<String>(items);
			items.clear();
			items.addAll(item);
			System.out.println("items "+items);
			stockDataBean.setWarehouseList(items);
		}else if(stockDataBean.getType().equals("ready")){
			v=entityManager.createQuery("select model from Model where person_ID=? and status='ACTIVE'");
			v.setParameter(1, Integer.parseInt(personID));
			List<String> models=(List<String>)v.getResultList();
			HashSet<String> model=new HashSet<String>(models);
			models.clear();
			models.addAll(model);
			System.out.println("models "+models);
			stockDataBean.setCmtProducts(models);
		}			
	}catch(Exception e){
		e.printStackTrace();
	}
}

public void getModelSeris(String personID, StockDataBean stockDataBean) {
	List<String> seriList=new ArrayList<String>();
	Query v=null;
	v=entityManager.createQuery("from Cutterpurchaseorder where person_ID=? and model=? and status='ACTIVE'");
	v.setParameter(1, Integer.parseInt(personID));
	v.setParameter(2, stockDataBean.getModelNo());
	List<Cutterpurchaseorder> cutterpurchaseorder=(List<Cutterpurchaseorder>)v.getResultList();
	System.out.println("size "+cutterpurchaseorder.size());
	if(cutterpurchaseorder.size()>0){
		for (int i = 0; i < cutterpurchaseorder.size(); i++) {
			v=entityManager.createQuery("from Cutterpurchase where purchase_ID=?");
			v.setParameter(1, cutterpurchaseorder.get(i).getPurchase_ID());
			List<Cutterpurchase> cutterpurchase=(List<Cutterpurchase>)v.getResultList();
			System.out.println("sizes "+cutterpurchase.size());
			if(cutterpurchase.size()>0){
				for (int j = 0; j < cutterpurchase.size(); j++) {
					seriList.add(cutterpurchase.get(j).getSeri());
				}
			}			
		}
		HashSet<String> seri=new HashSet<String>(seriList);
		seriList.clear();
		seriList.addAll(seri);
		System.out.println("seris "+seriList);
		stockDataBean.setSeris(seriList);	
	}
}

@Transactional(value="transactionManager")
public String saveOpeningStock(String personID, StockDataBean stockDataBean){
	Query v=null;
	String status="fail";
	try{
		if(stockDataBean.getType().equals("raw")){
			v=entityManager.createQuery("from OpeningStock where person_ID=? and itemName=? and stockStatus='raw' and status='Active'");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, stockDataBean.getItemName());
		}else if(stockDataBean.getType().equals("ready")){
			v=entityManager.createQuery("from OpeningStock where person_ID=? and modelNo=? and seri=? and stockStatus='ready' and status='Active'");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, stockDataBean.getModelNo());
			v.setParameter(3, stockDataBean.getSeri());
		}
		List<OpeningStock> openingStock=(List<OpeningStock>) v.getResultList();
		if(openingStock.size()>0){
			status="Exist";
		}else{
			OpeningStock openingStk=new OpeningStock();
			openingStk.setDate(stockDataBean.getDate());
			openingStk.setItemName(stockDataBean.getItemName());
			openingStk.setQuantity(stockDataBean.getQuantity());
			openingStk.setModelNo(stockDataBean.getModelNo());
			openingStk.setSeri(stockDataBean.getSeri());
			openingStk.setStockStatus(stockDataBean.getType());
			openingStk.setStatus("Active");
			openingStk.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
			entityManager.persist(openingStk);
			status="Success";
			if(stockDataBean.getType().equals("raw")){
				v=null;
				v=entityManager.createQuery("from Stock where warehouseStatus=? and person_ID=? and itemName=? and cmtStatus='Raw' and status='Active'");
				v.setParameter(1, "Main Warehouse");
				v.setParameter(2, Integer.parseInt(personID));
				v.setParameter(3, stockDataBean.getItemName());
			}else if(stockDataBean.getType().equals("ready")){
				v=null;
				v=entityManager.createQuery("from Stock where warehouseStatus=? and person_ID=? and itemName=? and seriNo=? and cmtStatus='CmtStock' and status='Active'");
				v.setParameter(1, "Main Warehouse");
				v.setParameter(2, Integer.parseInt(personID));
				v.setParameter(3, stockDataBean.getModelNo());
				v.setParameter(4, stockDataBean.getSeri());
			}
			List<Stock> stock=(List<Stock>)v.getResultList();
			if(stock.size()>0){
				Stock uptStock=entityManager.find(Stock.class,stock.get(0).getStock_ID());
				uptStock.setStockIn(String.valueOf(new BigDecimal(stock.get(0).getStockIn()).add(new BigDecimal(stockDataBean.getQuantity()))));
				entityManager.merge(uptStock);
				entityManager.flush();
				entityManager.clear();
			}else{
				Stock addStock=new Stock();				
				addStock.setStatus("Active");
				addStock.setStockIn(stockDataBean.getQuantity());
				addStock.setStockOut("0");				
				addStock.setWarehouseStatus("Main Warehouse");
				if(stockDataBean.getType().equals("raw")){
					addStock.setCmtStatus("Raw");
					addStock.setItemName(stockDataBean.getItemName());
					v=null;
					v=entityManager.createQuery("from ItemTable where itemName=? and person_ID=? and status='ACTIVE'");
					v.setParameter(1, stockDataBean.getItemName());
					v.setParameter(2, Integer.parseInt(personID));
					List<ItemTable> itemTable=(List<ItemTable>) v.getResultList();
					addStock.setItemTable(entityManager.find(ItemTable.class,itemTable.get(0).getItemId()));
				}
				else if(stockDataBean.getType().equals("ready")){
					addStock.setItemName(stockDataBean.getModelNo());
					addStock.setSeriNo(stockDataBean.getSeri());
					addStock.setCmtStatus("CmtStock");				
				}
				addStock.setStockDate(date);
				addStock.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
				entityManager.persist(addStock);
				entityManager.flush();
				entityManager.clear();
			}	
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	 return status;
}

public String getOpeningStockDetails(String personID,StockDataBean stockDataBean){
	Query v=null;
	List<StockDataBean> stockList=new ArrayList<StockDataBean>();
	try{
		if(stockDataBean.getStatus().equals("view")){
			v=entityManager.createQuery("from OpeningStock where person_ID=? and status='Active'");
			v.setParameter(1, Integer.parseInt(personID));
		}else if(stockDataBean.getStatus().equals("change")){
			if(stockDataBean.getType().equals("raw")){
				v=entityManager.createQuery("from OpeningStock where person_ID=? and stockStatus='raw' and status='Active'");
				v.setParameter(1, Integer.parseInt(personID));
			}else if(stockDataBean.getType().equals("ready")){
				v=entityManager.createQuery("from OpeningStock where person_ID=? and stockStatus='ready' and status='Active'");
				v.setParameter(1, Integer.parseInt(personID));				
			}
		}		
		List<OpeningStock> openingStock=(List<OpeningStock>)v.getResultList();
		if(openingStock.size()>0){
			for (int i = 0; i < openingStock.size(); i++) {
				StockDataBean stocks=new StockDataBean();
				stocks.setDate(openingStock.get(i).getDate());
				stocks.setItemName(openingStock.get(i).getItemName());
				stocks.setModelNo(openingStock.get(i).getModelNo());
				stocks.setSeri(openingStock.get(i).getSeri());
				stocks.setQuantity(openingStock.get(i).getQuantity());
				stocks.setSerialNo(String.valueOf(i+1));
				if(openingStock.get(i).getStockStatus().equals("raw")) stocks.setStatus("Raw Material");
				else if(openingStock.get(i).getStockStatus().equals("ready")) stocks.setStatus("Ready Stock");
				stockList.add(stocks);
			}
			stockDataBean.setWarelists(stockList);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
}

@Override
public String transactionwaste(String personID, FinanceDataBean financeDataBean) {
	System.out.println("oooookk");
	
	Query c=null;
	List<FinanceDataBean>item=new ArrayList<FinanceDataBean>();
	try{
		c=entityManager.createQuery("from Transaction where categoryName='WASTE' and person_ID=? and status='ACTIVE'");
		c.setParameter(1,personID);
		List<Transaction>views=(List<Transaction>)c.getResultList();
		System.out.println("size stock "+views.size());
		if (views.size()>0) {
			for (int j = 0; j < views.size(); j++) {
			FinanceDataBean obj=new FinanceDataBean();
			obj.setSerialNo(String.valueOf(j+1));
			obj.setDate(views.get(j).getDate());
			obj.setTransactionNumber(views.get(j).getTransactionNumber());
			obj.setName(views.get(j).getName());
			obj.setWasteQuantity(views.get(j).getWasteQuantity());
			obj.setAmount(views.get(j).getAmount());
			obj.setPaymentmode(views.get(j).getPaymentmode());
			obj.setType(views.get(j).getTransactiontype());
			item.add(obj);
			
			}
			
		financeDataBean.setFinanceList(item);
		System.out.println("item return"+financeDataBean.getFinanceList().size());
		
				
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return null;
}


@Override
public String reportfinance(String personID, FinanceDataBean financeDataBean) {
	Query z=null;
	List<FinanceDataBean>item=new ArrayList<FinanceDataBean>();
	try{
		z=entityManager.createQuery("from Transaction where person_ID=? and status='ACTIVE'");
		z.setParameter(1, personID);
		List<Transaction>views=(List<Transaction>)z.getResultList();
		if (views.size()>0) {
			for (int i = 0; i < views.size(); i++) {
				FinanceDataBean obj=new FinanceDataBean();
				obj.setSerialNo(String.valueOf(i+1));
				obj.setDate(views.get(i).getDate());
				obj.setTransactionNumber(views.get(i).getTransactionNumber());
				obj.setName(views.get(i).getName());
				obj.setAmount(views.get(i).getAmount());
				obj.setDebtCategory(views.get(i).getCategoryName());
				obj.setPaymentmode(views.get(i).getPaymentmode());
				obj.setType(views.get(i).getTransactiontype());
				item.add(obj);
				
			}
			financeDataBean.setFinanceList(item);
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return null;
}


@Override
public String supply(String personID, ReturnDataBean returnDataBean) {
	Query x=null;
	List<ReturnDataBean>domainList=new ArrayList<ReturnDataBean>();
	try{
		if (returnDataBean.getSerialno().equalsIgnoreCase("supply")) {
			x=entityManager.createQuery("from SupplyReturn where person_ID=? and status='ACTIVE'");
			x.setParameter(1, personID);
			List<SupplyReturn>item=(List<SupplyReturn>)x.getResultList();
			if (item.size()>0) {
				for (int i = 0; i < item.size(); i++) {
					ReturnDataBean obj=new ReturnDataBean();
					obj.setSerialno(String.valueOf(i+1));
					obj.setDate(item.get(i).getDate());
					obj.setItemname(item.get(i).getItemName());
					obj.setQuantity(item.get(i).getQuantity());
					obj.setNetprice(item.get(i).getNetAmount());
					obj.setPrice(item.get(i).getPrice());
					obj.setQuantity1(item.get(i).getQuantity());
					domainList.add(obj);
					
					
				}
				returnDataBean.setReturnDataBeanList(domainList);
			}
		}else if (returnDataBean.getItemname().equalsIgnoreCase("cmt")) {
			x=entityManager.createQuery("from CmtReturn where person_ID=? and status='ACTIVE'");
			x.setParameter(1, personID);
			List<CmtReturn>list=(List<CmtReturn>)x.getResultList();
			if (list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					ReturnDataBean sub=new ReturnDataBean();
					sub.setSerialno(String.valueOf(i+1));
					sub.setShoppname(list.get(i).getShop());
					sub.setItemname(list.get(i).getModel());
					sub.setQuantity(list.get(i).getSeri());
					sub.setNetprice(list.get(i).getNetprice());
					sub.setQuantity1(list.get(i).getQuantity());
					sub.setPrice(list.get(i).getPrice());
					sub.setStockin(list.get(i).getQuantity());
					domainList.add(sub);
					
				}
				returnDataBean.setReturnDataBeanList(domainList);
			}
			
		}
			
		}catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return null;
}

@Transactional(value="transactionManager")
@Override
public String payment(String personID, String clientID,List<ProductionDataBean> payrollList) {
	Query q=null;
	String status="Fail";
	int id=0;
	List<Payment> paymentlist=null;
	try{
		for(int i=0;i<payrollList.size();i++){
			if(payrollList.get(i).isCheckBox()==true){
			if(payrollList.get(i).getInvoiceNo().startsWith("Pr")){
				q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and invoice=? and status='Active'");
				q.setParameter(1, clientID);
				q.setParameter(2, payrollList.get(i).getInvoiceNo());
				List<Printerreceiveorder> printerList=(ArrayList<Printerreceiveorder>)q.getResultList();
				if(printerList.size()>0){
					id=printerList.get(0).getPurchase_ID();
					q=null;
					q=entityManager.createQuery("from Payment where printer_ID=?");
					q.setParameter(1, printerList.get(0).getPurchase_ID());
					paymentlist=(ArrayList<Payment>)q.getResultList();
				}
			}else if(payrollList.get(i).getInvoiceNo().startsWith("CMR")){
				q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and invoiceNo=? and status='Active'");
				q.setParameter(1, clientID);
				q.setParameter(2, payrollList.get(i).getInvoiceNo());
				List<Cmtreceiveorder> cmtList=(ArrayList<Cmtreceiveorder>)q.getResultList();
				if(cmtList.size()>0){
					id=cmtList.get(0).getReceive_ID();
					q=null;
					q=entityManager.createQuery("from Payment where cmt_ID=?");
					q.setParameter(1, cmtList.get(0).getReceive_ID());
					paymentlist=(ArrayList<Payment>)q.getResultList();
				}
			}else if(payrollList.get(i).getInvoiceNo().startsWith("CU")){
				q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and invoiceNo=? and status='Active'");
				q.setParameter(1, clientID);
				q.setParameter(2, payrollList.get(i).getInvoiceNo());
				List<Cutterpurchaseorder> cutterList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
				if(cutterList.size()>0){
					id=cutterList.get(0).getPurchase_ID();
					q=null;
					q=entityManager.createQuery("from Payment where cutter_ID=?");
					q.setParameter(1, cutterList.get(0).getPurchase_ID());
					paymentlist=(ArrayList<Payment>)q.getResultList();
				}
			}
			if(paymentlist.size()==0){
				Payment pay=new Payment();
				pay.setDate(date);
				pay.setTotalAmount(payrollList.get(i).getValue());
				pay.setBalanceAmount("0");
				pay.setPaidAmount(payrollList.get(i).getValue());
				if(payrollList.get(i).getInvoiceNo().startsWith("Pr")){
					pay.setPrinterReceiver(entityManager.find(Printerreceiveorder.class, id));
				}else if(payrollList.get(i).getInvoiceNo().startsWith("CMR")){
					pay.setCmtReceiver(entityManager.find(Cmtreceiveorder.class, id)); 
				}else if(payrollList.get(i).getInvoiceNo().startsWith("CU")){
					pay.setCutterPurchase(entityManager.find(Cutterpurchaseorder.class, id));
				}
				pay.setStatus("Paid");
				entityManager.persist(pay);
				entityManager.flush();
				entityManager.clear();
				if(payrollList.get(i).getInvoiceNo().startsWith("Pr")){
					Printerreceiveorder print=entityManager.find(Printerreceiveorder.class, id);
					print.setInvoiceStatus("PAID");
					entityManager.merge(print);
				}else if(payrollList.get(i).getInvoiceNo().startsWith("CMR")){
					Cmtreceiveorder cmt=entityManager.find(Cmtreceiveorder.class, id);
					cmt.setInvoiceStatus("PAID");
					entityManager.merge(cmt);
				}else if(payrollList.get(i).getInvoiceNo().startsWith("CU")){
					Cutterpurchaseorder cut=entityManager.find(Cutterpurchaseorder.class, id);
					cut.setInvoiceStatus("PAID");
					entityManager.merge(cut);
				}
				status="Success";
			}else{
				Payment pay=entityManager.find(Payment.class, paymentlist.get(0).getPayment_ID());
				pay.setDate(date);
				pay.setTotalAmount(payrollList.get(i).getValue());
				pay.setBalanceAmount("0");
				pay.setPaidAmount(payrollList.get(i).getValue());
				if(payrollList.get(i).getInvoiceNo().startsWith("Pr")){
					pay.setPrinterReceiver(entityManager.find(Printerreceiveorder.class, id));
				}else if(payrollList.get(i).getInvoiceNo().startsWith("CMR")){
					pay.setCmtReceiver(entityManager.find(Cmtreceiveorder.class, id)); 
				}else if(payrollList.get(i).getInvoiceNo().startsWith("CU")){
					pay.setCutterPurchase(entityManager.find(Cutterpurchaseorder.class, id));
				}
				pay.setStatus("Paid");
				entityManager.merge(pay);
				entityManager.flush();
				entityManager.clear();
				if(payrollList.get(i).getInvoiceNo().startsWith("Pr")){
					Printerreceiveorder print=entityManager.find(Printerreceiveorder.class, id);
					print.setInvoiceStatus("PAID");
					entityManager.merge(print);
				}else if(payrollList.get(i).getInvoiceNo().startsWith("CMR")){
					Cmtreceiveorder cmt=entityManager.find(Cmtreceiveorder.class, id);
					cmt.setInvoiceStatus("PAID");
					entityManager.merge(cmt);
				}else if(payrollList.get(i).getInvoiceNo().startsWith("CU")){
					Cutterpurchaseorder cut=entityManager.find(Cutterpurchaseorder.class, id);
					cut.setInvoiceStatus("PAID");
					entityManager.merge(cut);
				}
				status="Success";
			}
		}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return status;
}

@Transactional(value="transactionManager")
public String payment1(String personID, String clientID,
		List<ProductionDataBean> payrollList) {
	Query q=null;
	String status="Fail";
	int id=0;
	List<Payment> paymentlist=null;
	try{
		for(int i=0;i<payrollList.size();i++){
			if(payrollList.get(i).isCheckBox()==true){
				q=entityManager.createQuery("from Cmtreceiveorder where client_ID=? and invoiceNo=? and status='Active'");
				q.setParameter(1, clientID);
				q.setParameter(2, payrollList.get(i).getInvoiceNo());
				List<Cmtreceiveorder> cmtList=(ArrayList<Cmtreceiveorder>)q.getResultList();
				if(cmtList.size()>0){
					id=cmtList.get(0).getReceive_ID();
					q=null;
					q=entityManager.createQuery("from Payment where cmt_ID=?");
					q.setParameter(1, cmtList.get(0).getReceive_ID());
					paymentlist=(ArrayList<Payment>)q.getResultList();
					
					if(paymentlist.size()==0){
						Payment pay=new Payment();
						pay.setDate(date);
						pay.setTotalAmount(cmtList.get(0).getAmount());
						pay.setPaidAmount(payrollList.get(i).getValue());
						pay.setBalanceAmount(new BigDecimal(cmtList.get(0).getAmount()).subtract(new BigDecimal(payrollList.get(i).getValue())).toString());
						pay.setCmtReceiver(entityManager.find(Cmtreceiveorder.class, id)); 
						if(new BigDecimal(cmtList.get(0).getAmount()).compareTo(new BigDecimal(payrollList.get(i).getValue()))==0){
							pay.setStatus("PAID");
						}
						pay.setStatus("Pending");
						entityManager.persist(pay);
						entityManager.flush();
						entityManager.clear();
						
						if(new BigDecimal(cmtList.get(0).getAmount()).compareTo(new BigDecimal(payrollList.get(i).getValue()))==0){
							Cmtreceiveorder cmt=entityManager.find(Cmtreceiveorder.class, id);
							cmt.setInvoiceStatus("PAID");
							entityManager.merge(cmt);
						}
						status="Success";
					}else{
						Payment pay=entityManager.find(Payment.class, paymentlist.get(0).getPayment_ID());
						pay.setDate(date);
						pay.setTotalAmount(cmtList.get(0).getAmount());
						pay.setBalanceAmount(new BigDecimal(cmtList.get(0).getAmount()).subtract(new BigDecimal(payrollList.get(i).getValue())).toString());
						pay.setPaidAmount(payrollList.get(i).getValue());
						pay.setCmtReceiver(entityManager.find(Cmtreceiveorder.class, id)); 
						if(new BigDecimal(cmtList.get(0).getAmount()).compareTo(new BigDecimal(payrollList.get(i).getValue()))==0){
							pay.setStatus("Paid");
						}
						entityManager.merge(pay);
						entityManager.flush();
						entityManager.clear();
						
						if(new BigDecimal(cmtList.get(0).getAmount()).compareTo(new BigDecimal(payrollList.get(i).getValue()))==0){
							Cmtreceiveorder cmt=entityManager.find(Cmtreceiveorder.class, id);
							cmt.setInvoiceStatus("PAID");
							entityManager.merge(cmt);
						}
						status="Success";
					}
					q=null;
					q=entityManager.createQuery("from CmtStockin where cmtreceive_ID=?");
					q.setParameter(1, id);
					List<CmtStockin> stkin=(List<CmtStockin>)q.getResultList();
					System.out.println("stkin "+stkin.size());
					if(stkin.size()>0){
						for (int j = 0; j < stkin.size(); j++) {
							CmtStockin cmtStockin=entityManager.find(CmtStockin.class, stkin.get(j).getCmtStockinId());
							cmtStockin.setStatus("paid");
							entityManager.merge(cmtStockin);
						}
					}
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return status;
}


@Override
@Transactional(value="transactionManager")
public String updatecustomer(String personID, String clientID,
		CustomerDatabean customerDatabean) {
	Query q=null;
	List<Customer> customerlist=null;
	String status="Fail";
	int id=0;
	if(personID !=null && clientID!=null){
		try{
			Query q1=null;
			q1=entityManager.createQuery("from Customer where customerName=? and status='Active'");
			q1.setParameter(1,customerDatabean.getCustomername());
			customerlist=(List<Customer>)q1.getResultList();
			System.out.println("customerlist"+customerlist);
			if(customerlist.size()>0){
					id=customerlist.get(0).getCustomer_ID();
					System.out.println("id"+id);
					Customer customer=entityManager.find(Customer.class, id);
					customer.setPhoneNumber(customerDatabean.getPhonenumber());
					customer.setPhoneNumber2(customerDatabean.getPhonenumber2());
					customer.setCategoryName(customerDatabean.getCategoryname());
					customer.setCity(customerDatabean.getCity());
					customer.setState(customerDatabean.getState());
					customer.setCountry(customerDatabean.getCountry());
					customer.setTaxNumber(customerDatabean.getTaxnumber());
					customer.setShippingAddress(customerDatabean.getShippingaddress());
					customer.setNote(customerDatabean.getNote());
					customer.setEmail(customerDatabean.getEmail());
					entityManager.merge(customer);
					status="Success";
				}
		}catch(Exception e){
			logger.error("Error Message"+e);
		}
	}
	return status;
}

@Transactional(value="transactionManager")
@Override
public String cusDelete(String personID, String clientID,
		CustomerDatabean customerDatabean) {
	Query q=null;
	int id=0;
	String status="Fail";
	List<Customer> customerlist=null;
	if(personID !=null && clientID!=null){
		try{
			q=entityManager.createQuery("from Customer where customerName=? and status='Active'");
			q.setParameter(1,customerDatabean.getCustomername());
			customerlist=(List<Customer>)q.getResultList();
			System.out.println("cusdelete"+customerlist.size());
			if(customerlist.size()>0){
				id=customerlist.get(0).getCustomer_ID();
				Customer customer=entityManager.find(Customer.class, id);
				customer.setStatus("Deactive");
					entityManager.merge(customer);
					status="Success";
				}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return status;
}
@Override
@Transactional(value="transactionManager")
public String register(String personID, String clientID,
		CustomerDatabean customerDatabean) 
{
	String status="fail";
	int personid=0;
	int clientid=0;Query q=null;
	Date date=new Date();
	if(personID !=null && clientID!=null){
	try{
		personid=Integer.parseInt(personID);
		clientid=Integer.parseInt(clientID);
		q=entityManager.createQuery("from Customer where client_ID=? and customerName=? and status='Active'");
		q.setParameter(1, clientid);
		q.setParameter(2, customerDatabean.getCustomername());
		List<Customer> res=(List<Customer>)q.getResultList();
		if(res.size()>0){
			status="Exists";
		}
		else{
		Customer customer=new Customer();
		customer.setCategoryName(customerDatabean.getCategoryname());
		customer.setCity(customerDatabean.getCity());
		customer.setCountry(customerDatabean.getCountry());
		customer.setCustomer_ID(customerDatabean.getCustomerid());
		customer.setDate(customerDatabean.getDate());
		customer.setEmail(customerDatabean.getEmail());
		customer.setPhoneNumber(customerDatabean.getPhonenumber());
		customer.setPhoneNumber2(customerDatabean.getPhonenumber2());
		customer.setTaxNumber(customerDatabean.getTaxnumber());
		customer.setState(customerDatabean.getState());
		customer.setCustomerName(customerDatabean.getCustomername());
		customer.setShippingAddress(customerDatabean.getShippingaddress());
		customer.setNote(customerDatabean.getNote());
		customer.setClient(entityManager.find(Client.class, clientid));
		customer.setPerson(entityManager.find(Person.class, personid));
		customer.setStatus("Active");
		entityManager.persist(customer);
		status="success";
		}
	}
	catch(Exception e)
	{
		logger.error("Error Message"+e);
		e.printStackTrace();
	}
	}
	return status;
}
@Override
public List<CustomerDatabean> customerView() {
	List<CustomerDatabean> customerList=null;
	Query qq=null;
	try
	{
		customerList=new ArrayList<CustomerDatabean>();
		qq=entityManager.createQuery("from Customer where status=? ORDER BY customer_ID DESC");
		qq.setParameter(1,"Active");
		List<Customer> resList=(List<Customer>)qq.getResultList();
		System.out.println("table size"+resList.size());
		if(resList.size()>0){
			for (int i = 0; i < resList.size(); i++) {
				CustomerDatabean cus=new CustomerDatabean();
				cus.setCustomername(resList.get(i).getCustomerName());
				cus.setPhonenumber(resList.get(i).getPhoneNumber());
				cus.setCity(resList.get(i).getCity());
				cus.setCountry(resList.get(i).getCountry());
				cus.setCategoryname(resList.get(i).getCategoryName());
				cus.setPhonenumber(resList.get(i).getPhoneNumber());
				cus.setPhonenumber2(resList.get(i).getPhoneNumber2());
				cus.setTaxnumber(resList.get(i).getTaxNumber());
				cus.setShippingaddress(resList.get(i).getShippingAddress());
				cus.setState(resList.get(i).getState());
				customerList.add(cus);
				
			}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return customerList;
}
@Override
public List<Customer> editcustomer(String personID, String clientID,
		CustomerDatabean customerDatabean) {
	Query q=null;
	int id;
	List<Customer> customerlist=null;
	if(personID !=null && clientID!=null){
	try{
		customerlist=new ArrayList<Customer>();
		q=entityManager.createQuery("from Customer where customerName=? and status='Active'");
		q.setParameter(1, customerDatabean.getCustomername());
		customerlist=(List<Customer>)q.getResultList();
	}catch(Exception e){
		logger.error("Error Message"+e);
		e.printStackTrace();
	}
	}		
	return customerlist;
}


@Override
public List<ProductionDataBean> getcutterData(String personID, String clientID) {
	Query q=null;
	List<ProductionDataBean> beanList=null;
	List<ProductionDataBean> proList=new ArrayList<ProductionDataBean>();
	if(personID!=null && clientID!=null){
		try{
			beanList=new ArrayList<ProductionDataBean>();
			q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and status='ACTIVE'");
			q.setParameter(1, clientID);
			List<Cutterpurchaseorder> cutterList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
			if(cutterList.size()>0){
				for(int i=0;i<cutterList.size();i++){
					q=null;
					q=entityManager.createQuery("from Cutterpurchase where purchase_ID=? and status='ACTIVE' and orderStatus='NOT READY'");
					q.setParameter(1, cutterList.get(i).getPurchase_ID());
					List<Cutterpurchase> list=(ArrayList<Cutterpurchase>)q.getResultList();
					if(list.size()>0){
						for(int j=0;j<list.size();j++){
							ProductionDataBean production=new ProductionDataBean();
							production.setModelName(cutterList.get(i).getModel());
							production.setSeri(list.get(j).getSeri());
							production.setMotive(list.get(j).getMotive());
							production.setQuantity(list.get(j).getQuantity());
							beanList.add(production);
						}
					}					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return beanList;
}

@Transactional(value="transactionManager")
@Override
public String cutterReadysave(String personID, String clientID,
		List<ProductionDataBean> cutterorderlist) {
	Query q=null;
	String status="Fail";
	if(personID!=null && clientID!=null){
		try{
			for(int i=0;i<cutterorderlist.size();i++){
				if(cutterorderlist.get(i).isCheckBox()==true){
					Printerready ready=new Printerready();
					ready.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
					ready.setClient(entityManager.find(Client.class, Integer.parseInt(clientID)));
					ready.setModel(cutterorderlist.get(i).getModelName());
					ready.setSeri(cutterorderlist.get(i).getSeri());
					ready.setQuantity(cutterorderlist.get(i).getQuantity());
					ready.setStatus("ACTIVE");
					ready.setOrderStatus("WAITING");
					entityManager.persist(ready);
					entityManager.flush();entityManager.clear();
					q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and model=? and status='ACTIVE'");
					q.setParameter(1, clientID);
					q.setParameter(2, cutterorderlist.get(i).getModelName());
					List<Cutterpurchaseorder> cutterList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
					if(cutterList.size()>0){
						for(int j=0;j<cutterList.size();j++){
						q=null;
						q=entityManager.createQuery("from Cutterpurchase where purchase_ID=? and orderStatus='NOT READY' and status='ACTIVE'");
						q.setParameter(1, cutterList.get(j).getPurchase_ID());
						List<Cutterpurchase> purchasList=(List<Cutterpurchase>)q.getResultList();
						if(purchasList.size()>0){
							for(int p=0;p<purchasList.size();p++){
								Cutterpurchase cutterpurchase=entityManager.find(Cutterpurchase.class, purchasList.get(0).getOrder_ID());
								cutterpurchase.setOrderStatus("READY PRINTER");
								entityManager.merge(cutterpurchase);
								entityManager.flush();entityManager.clear();
								status="Success";
							}
						}
					}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return status;
}

@Override
public List<String> getcutterName(String personID, String clientID) {
	Query q=null;
	List<String> nameList=null;
	if(personID!=null && clientID!=null){
		try{
			q=entityManager.createQuery("select vendorName from Vendor where client_ID=? and divisionVendor='cutter' and status='ACTIVE'");
			q.setParameter(1, clientID);
			nameList=(ArrayList<String>)q.getResultList();
		}catch(Exception e){
		}
	}
	return nameList;
}

@Override
public List<String> getPrinterreadymodel(String personID, String clientID) {
	Query q=null;
	List<String> nameList=null;
	if(personID!=null && clientID!=null){
		try{
			q=entityManager.createQuery("select model from Printerready where client_ID=? and status='ACTIVE' and orderStatus='WAITING'");
			q.setParameter(1, clientID);
			nameList=(ArrayList<String>)q.getResultList();
			Set<String> dublicate=new HashSet<String>(nameList);
			nameList.clear();
			nameList.addAll(dublicate);
		}catch(Exception e){
		}
	}
	return nameList;
}


@Override
public List<String> getModel(String clientID) {
	Query q=null;
	List<String> nameList=null;
	if(clientID!=null){
		try{
			q=entityManager.createQuery("select model from Printerready where client_ID=? and status='ACTIVE' and orderStatus='ORDERED'");
			q.setParameter(1, clientID);
			nameList=(ArrayList<String>)q.getResultList();
			Set<String> dublicate=new HashSet<String>(nameList);
			nameList.clear();
			nameList.addAll(dublicate);
		}catch(Exception e){
		}
	}
	return nameList;
}

@Override
public List<ProductionDataBean> getprinterData(String personID, String clientID) {
	Query q=null;
	List<ProductionDataBean> beanList=null;
	List<ProductionDataBean> proList=new ArrayList<ProductionDataBean>();
	if(personID!=null && clientID!=null){
		try{
			beanList=new ArrayList<ProductionDataBean>();
			q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and status='ACTIVE'");
			q.setParameter(1, clientID);
			List<Printerreceiveorder> printerList=(ArrayList<Printerreceiveorder>)q.getResultList();
			if(printerList.size()>0){
				for(int i=0;i<printerList.size();i++){
					q=null;
					q=entityManager.createQuery("from Printerorder where purchase_ID=? and status='ACTIVE' and orderstatus='NOT READY'");
					q.setParameter(1, printerList.get(i).getPurchase_ID());
					List<Printerorder> list=(ArrayList<Printerorder>)q.getResultList();
					if(list.size()>0){
						for(int j=0;j<list.size();j++){
							ProductionDataBean production=new ProductionDataBean();
							production.setModelName(printerList.get(i).getModel());
							production.setSeri(list.get(j).getSer());
							production.setMotive(list.get(j).getMotive());
							production.setValue(list.get(j).getValue());
							production.setQuantity(list.get(j).getQuantity());
							beanList.add(production);
						}
					}					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return beanList;
}

@Transactional(value="transactionManager")
@Override
public String printerReadysave(String personID, String clientID,
		List<ProductionDataBean> printerPurchaselist) {
	Query q=null;
	String status="Fail";
	if(personID!=null && clientID!=null){
		try{
			for(int i=0;i<printerPurchaselist.size();i++){
				if(printerPurchaselist.get(i).isCheckBox()==true){
					Cmtready ready=new Cmtready();
					ready.setPerson(entityManager.find(Person.class, Integer.parseInt(personID)));
					ready.setClient(entityManager.find(Client.class, Integer.parseInt(clientID)));
					ready.setModel(printerPurchaselist.get(i).getModelName());
					ready.setSeri(printerPurchaselist.get(i).getSeri());
					ready.setQuantity(printerPurchaselist.get(i).getQuantity());
					ready.setStatus("ACTIVE");
					ready.setOrderStatus("WAITING");
					entityManager.persist(ready);
					entityManager.flush();entityManager.clear();
					q=entityManager.createQuery("from Printerreceiveorder where client_ID=? and model=? and status='ACTIVE'");
					q.setParameter(1, clientID);
					q.setParameter(2, printerPurchaselist.get(i).getModelName());
					List<Printerreceiveorder> printerList=(ArrayList<Printerreceiveorder>)q.getResultList();
					if(printerList.size()>0){
						for(int j=0;j<printerList.size();j++){
						q=null;
						q=entityManager.createQuery("from Printerorder where purchase_ID=? and orderStatus='NOT READY' and status='ACTIVE'");
						q.setParameter(1, printerList.get(j).getPurchase_ID());
						List<Printerorder> purchasList=(List<Printerorder>)q.getResultList();
						if(purchasList.size()>0){
							for(int p=0;p<purchasList.size();p++){
								Printerorder cutterpurchase=entityManager.find(Printerorder.class, purchasList.get(0).getPoredrId());
								cutterpurchase.setOrderstatus("READY CMT");
								entityManager.merge(cutterpurchase);
								entityManager.flush();entityManager.clear();
								status="Success";
							}
						}
					}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return status;
}


@Override
public List<String> cmtreadyModel(String personID, String clientID) {
	Query q=null;
	List<String> nameList=null;
	if(clientID!=null){
		try{
			q=entityManager.createQuery("select model from Cmtready where client_ID=? and status='ACTIVE' and orderStatus='WAITING'");
			q.setParameter(1, clientID);
			nameList=(ArrayList<String>)q.getResultList();
			Set<String> dublicate=new HashSet<String>(nameList);
			nameList.clear();
			nameList.addAll(dublicate);
		}catch(Exception e){
		}
	}
	return nameList;
}


@Override
public List<String> getmodelnameReady(String personID, String clientID) {
	Query q=null;
	List<String> nameList=null;
	if(clientID!=null){
		try{
			q=entityManager.createQuery("select model from Cmtready where client_ID=? and status='ACTIVE' and orderStatus='ORDERED'");
			q.setParameter(1, clientID);
			nameList=(ArrayList<String>)q.getResultList();
			Set<String> dublicate=new HashSet<String>(nameList);
			nameList.clear();
			nameList.addAll(dublicate);
		}catch(Exception e){
		}
	}
	return nameList;
}

public void getHoldingDetails(String personID,ProductionDataBean productionDataBean){
	Query v=null;	
	List<ProductionDataBean> holdingList=new ArrayList<ProductionDataBean>();
	int count=1;
	try{
		v=entityManager.createQuery("from Printerpurchaseorder where person_ID=? and status='ACTIVE' and invoiceStatus='HOLDING'");
		v.setParameter(1, Integer.parseInt(personID));
		List<Printerpurchaseorder> printerPurchase=(List<Printerpurchaseorder>)v.getResultList();
		System.out.println("printer purchase size "+printerPurchase.size());
		if(printerPurchase.size()>0){
			for (int i = 0; i < printerPurchase.size(); i++) {
				ProductionDataBean holdings=new ProductionDataBean();
				holdings.setCategoryName("Printer");
				holdings.setOrderDate(printerPurchase.get(i).getOrderDate());
				holdings.setInvoiceNo(printerPurchase.get(i).getInvoice());
				holdings.setDivisionVendor(printerPurchase.get(i).getPrinter());
				holdings.setModelName(printerPurchase.get(i).getModel());
				holdings.setValue(printerPurchase.get(i).getValue());
				holdings.setTotalQuantity(printerPurchase.get(i).getTotal_quantity());
				holdings.setSerialNo(String.valueOf(count));
				holdingList.add(holdings);
				count++;
			}
		}
		v=entityManager.createQuery("from Cmtpurchaseorder where person_ID=? and status='ACTIVE' and invoiceStatus='HOLDING'");
		v.setParameter(1, Integer.parseInt(personID));
		List<Cmtpurchaseorder> cmtPurchase=(List<Cmtpurchaseorder>)v.getResultList();
		System.out.println("cmt purchase size "+cmtPurchase.size());
		if(cmtPurchase.size()>0){
			for (int i = 0; i < cmtPurchase.size(); i++) {
				ProductionDataBean holdings=new ProductionDataBean();
				holdings.setCategoryName("Cmt");
				holdings.setOrderDate(cmtPurchase.get(i).getOrderDate());
				holdings.setDivisionVendor(cmtPurchase.get(i).getCmt());
				holdings.setInvoiceNo(cmtPurchase.get(i).getInvoiceNo());
				holdings.setModelName(cmtPurchase.get(i).getModel());
				holdings.setValue(cmtPurchase.get(i).getValue());
				holdings.setTotalQuantity(cmtPurchase.get(i).getTotalQuantity());
				holdings.setSerialNo(String.valueOf(count));
				holdingList.add(holdings);
				count++;
			}
		}
		productionDataBean.setOrderQuantitylist(holdingList);
	}catch(Exception e){
		e.printStackTrace();
	}
}

public void getModelsDetails(String personID,StockDataBean stockDataBean){
	Query v=null;
	List<StockDataBean> stockList=new ArrayList<StockDataBean>();
	List<String> models=new ArrayList<String>();
	try{
		v=entityManager.createQuery("from Stock where person_id=? and status='Active' and cmtStatus='CmtStock'");
		v.setParameter(1, Integer.parseInt(personID));
		List<Stock> stock=(List<Stock>)v.getResultList();
		if(stock.size()>0){
			for (int i = 0; i < stock.size(); i++) {
				models.add(stock.get(i).getItemName());
			}
			HashSet<String> model=new HashSet<String>(models);
			models.clear();
			models.addAll(model);
			int count=1;
			for (int i = 0; i < models.size(); i++) {
				BigDecimal quantity=BigDecimal.valueOf(0);
				BigDecimal value=BigDecimal.valueOf(0);
				v=entityManager.createQuery("from Stock where person_id=? and itemName=? and status='Active' and cmtStatus='CmtStock' and stockIn!=0");
				v.setParameter(1, Integer.parseInt(personID));
				v.setParameter(2, models.get(i));
				List<Stock> stocks=(List<Stock>)v.getResultList();
				if(stocks.size()>0){
					for (int j = 0; j < stocks.size(); j++) {
						quantity=quantity.add(new BigDecimal(stocks.get(j).getStockIn()));
					}
					v=null;
					v=entityManager.createQuery("from Model where model=? and person_ID=? and status='ACTIVE'");
					v.setParameter(1, models.get(i));
					v.setParameter(2, Integer.parseInt(personID));
					List<Model> modell=(List<Model>)v.getResultList();
					if(modell.size()>0){
						value=quantity.multiply(new BigDecimal(modell.get(0).getSellPrice()));
					}
					StockDataBean stocklist=new StockDataBean();
					stocklist.setSerialNo(String.valueOf(count));
					stocklist.setModel(models.get(i));
					stocklist.setQuantity(String.valueOf(quantity));
					stocklist.setTotalPrice(String.valueOf(value));
					stockList.add(stocklist);
					count++;
				}
			}
			stockDataBean.setWarelists(stockList);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

public void getWarehouseActivity(String personID,StockDataBean stockDataBean){
	Query v=null;
	List<Date> dates=new ArrayList<Date>();
	List<StockDataBean> stockouts=new ArrayList<StockDataBean>();
	Calendar cal = Calendar.getInstance();
	BigDecimal inQuan1=BigDecimal.valueOf(0),inPrice1=BigDecimal.valueOf(0);
	BigDecimal outQuan1=BigDecimal.valueOf(0),outPrice1=BigDecimal.valueOf(0);
	try{
		cal.add(Calendar.DATE, -7);
	    Date todate1 = cal.getTime();  
	    System.out.println("date "+todate1);
		v=entityManager.createQuery("from Stock where person_ID=? and stockDate between ? and ? and status='Active' and warehouseStatus='Main Warehouse' and cmtStatus='CmtStock'");
		v.setParameter(1, Integer.parseInt(personID));	
		v.setParameter(2, todate1);
		v.setParameter(3, date);
		List<Stock> stock=(List<Stock>)v.getResultList();
		if(stock.size()>0){
			for (int i = 0; i < stock.size(); i++) {
				dates.add(stock.get(i).getStockDate());
			}
		}
		v=null;
		v=entityManager.createQuery("from ManualStock where person_ID=? and date between ? and ? and status='Active' and shopName='Main Warehouse'");
		v.setParameter(1, Integer.parseInt(personID));	
		v.setParameter(2, todate1);
		v.setParameter(3, date);
		List<ManualStock> manualStock=(List<ManualStock>)v.getResultList();
		if(manualStock.size()>0){
			for (int i = 0; i < manualStock.size(); i++) {
				dates.add(manualStock.get(i).getDate());
			}
		}
		HashSet<Date> datess=new HashSet<Date>(dates);
		dates.clear();
		dates.addAll(datess);
		System.out.println("dates "+dates);
		int count=1;
		for (int i = 0; i < dates.size(); i++) {
			BigDecimal inQuan=BigDecimal.valueOf(0),inPrice=BigDecimal.valueOf(0);
			BigDecimal outQuan=BigDecimal.valueOf(0),outPrice=BigDecimal.valueOf(0);
			v=entityManager.createQuery("from Stock where person_ID=? and stockDate=? and status='Active' and warehouseStatus='Main Warehouse' and cmtStatus='CmtStock'");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, dates.get(i));
			List<Stock> stocks=(List<Stock>)v.getResultList();
			if(stocks.size()>0){
				StockDataBean warehouseD=new StockDataBean();
				for (int j = 0; j < stocks.size(); j++) {
					inQuan=inQuan.add(new BigDecimal(stocks.get(j).getStockIn()));	
					v=null;
					v=entityManager.createQuery("from Model where model=? and person_ID=? and status='ACTIVE'");
					v.setParameter(1, stocks.get(j).getItemName());
					v.setParameter(2, Integer.parseInt(personID));
					List<Model> modell=(List<Model>)v.getResultList();
					if(modell.size()>0){
						inPrice=inPrice.add(new BigDecimal(stocks.get(j).getStockIn()).multiply(new BigDecimal(modell.get(0).getSellPrice())));
					}					
				}
				v=entityManager.createQuery("from ManualStock where person_ID=? and date=? and status='Active' and shopName='Main Warehouse'");
				v.setParameter(1, Integer.parseInt(personID));	
				v.setParameter(2, dates.get(i));
				List<ManualStock> manualStocks=(List<ManualStock>)v.getResultList();
				if(manualStocks.size()>0){
					for (int j = 0; j < manualStocks.size(); j++) {
						v=entityManager.createQuery("from ManualStockOut where manual_stock_id=?");
						v.setParameter(1, manualStocks.get(j).getManualStockId());
						List<ManualStockOut> manualStockOut=(List<ManualStockOut>)v.getResultList();
						if(manualStockOut.size()>0){
							for (int c = 0; c < manualStockOut.size(); c++) {
								outQuan=outQuan.add(new BigDecimal(manualStockOut.get(c).getQuantity()));								
								v=null;
								v=entityManager.createQuery("from Model where model=? and person_ID=? and status='ACTIVE'");
								v.setParameter(1, manualStockOut.get(c).getModel());
								v.setParameter(2, Integer.parseInt(personID));
								List<Model> modell=(List<Model>)v.getResultList();
								if(modell.size()>0){
									outPrice=outPrice.add(new BigDecimal(manualStockOut.get(c).getQuantity()).multiply(new BigDecimal(modell.get(0).getSellPrice())));
								}								
							}
						}						
					}
				}
				inQuan1=inQuan1.add(inQuan);
				inPrice1=inPrice1.add(inPrice);
				outQuan1=outQuan1.add(outQuan);
				outPrice1=outPrice1.add(outPrice);
				warehouseD.setDate(dates.get(i));
				warehouseD.setQuantity(String.valueOf(inQuan));
				warehouseD.setTotalPrice(String.valueOf(inPrice));
				warehouseD.setQuantity1(String.valueOf(outQuan));
				warehouseD.setNetprice(String.valueOf(outPrice));
				warehouseD.setSerialNo(String.valueOf(count));
				stockouts.add(warehouseD);
				count++;
			}else{
				v=entityManager.createQuery("from ManualStock where person_ID=? and date=? and status='Active' and shopName='Main Warehouse'");
				v.setParameter(1, Integer.parseInt(personID));	
				v.setParameter(2, dates.get(i));
				List<ManualStock> manualStocks=(List<ManualStock>)v.getResultList();
				if(manualStocks.size()>0){
					for (int j = 0; j < manualStocks.size(); j++) {
						v=entityManager.createQuery("from ManualStockOut where manual_stock_id=?");
						v.setParameter(1, manualStocks.get(j).getManualStockId());
						List<ManualStockOut> manualStockOut=(List<ManualStockOut>)v.getResultList();
						if(manualStockOut.size()>0){
							for (int c = 0; c < manualStockOut.size(); c++) {
								outQuan=outQuan.add(new BigDecimal(manualStockOut.get(c).getQuantity()));								
								v=null;
								v=entityManager.createQuery("from Model where model=? and person_ID=? and status='ACTIVE'");
								v.setParameter(1, manualStockOut.get(c).getModel());
								v.setParameter(2, Integer.parseInt(personID));
								List<Model> modell=(List<Model>)v.getResultList();
								if(modell.size()>0){
									outPrice=outPrice.add(new BigDecimal(manualStockOut.get(c).getQuantity()).multiply(new BigDecimal(modell.get(0).getSellPrice())));
								}								
							}
						}						
					}
				}
				outQuan1=outQuan1.add(outQuan);
				outPrice1=outPrice1.add(outPrice);
				StockDataBean warehouseD=new StockDataBean();
				warehouseD.setDate(dates.get(i));
				warehouseD.setQuantity("0");
				warehouseD.setTotalPrice("0");
				warehouseD.setQuantity1(String.valueOf(outQuan));
				warehouseD.setNetprice(String.valueOf(outPrice));
				warehouseD.setSerialNo(String.valueOf(count));
				stockouts.add(warehouseD);
				count++;
			}			
		}	
		System.out.println("size "+stockouts.size());
		stockDataBean.setQuantity(String.valueOf(inQuan1));
		stockDataBean.setQuantity1(String.valueOf(outQuan1));
		stockDataBean.setTotalPrice(String.valueOf(inPrice1));
		stockDataBean.setNetprice(String.valueOf(outPrice1));
		stockDataBean.setWarelists(stockouts);
	}catch(Exception e){
		e.printStackTrace();
	}
}


@Override
public void getbestSeller(String personID, StockDataBean stockDataBean) {
	Query q=null;
	List<StockDataBean> beanList=new ArrayList<StockDataBean>();
	List<StockDataBean> beanList1=new ArrayList<StockDataBean>();
	List<String> modelList=new ArrayList<String>();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, -60);
    Date newdate = cal.getTime(); 
    BigDecimal quantity=BigDecimal.valueOf(0);
    BigDecimal total=BigDecimal.valueOf(0);
	try{
		q=entityManager.createQuery("from ManualStock where person_ID=? and date between ? and ? and status='Active' and shopName='Main Warehouse'");
		q.setParameter(1, personID);
		q.setParameter(2, newdate);
		q.setParameter(3, date);
		List<ManualStock> manualstockList=(ArrayList<ManualStock>)q.getResultList();
		if(manualstockList.size()>0){
			for(int i=0;i<manualstockList.size();i++){
				q=null;
				q=entityManager.createQuery("from ManualStockOut where manual_stock_id=?");
				q.setParameter(1, manualstockList.get(i).getManualStockId());
				List<ManualStockOut> stockoutList=(ArrayList<ManualStockOut>)q.getResultList();
				if(stockoutList.size()>0){
					for(int j=0;j<stockoutList.size();j++){
						modelList.add(stockoutList.get(j).getModel());
					}
				}
				
			}
			Set<String> dublicate=new HashSet<String>(modelList);
			modelList.clear();
			modelList.addAll(dublicate);
			if(modelList.size()>0){
				for(int k=0;k<modelList.size();k++){
					StockDataBean stock=new StockDataBean();
					stock.setModel(modelList.get(k));	
					//stock.setSerialNo(String.valueOf(k+1));
					q=null;
					q=entityManager.createQuery("from ManualStockOut where model=?");
					q.setParameter(1, modelList.get(k));
					List<ManualStockOut> stockList=(ArrayList<ManualStockOut>)q.getResultList();
					for(int n=0;n<stockList.size();n++){
						quantity=quantity.add(new BigDecimal(stockList.get(n).getQuantity()));	
						stock.setQuantity(quantity.toString());	
						total=total.add(new BigDecimal(stockList.get(n).getNetAmount()));
						stock.setTotalPrice(total.toString());
					}
					beanList.add(stock);
				}
				
			}
			Collections.reverse(beanList);
			for(int r=0;r<beanList.size();r++){
				StockDataBean stk=new StockDataBean();
				stk.setSerialNo(String.valueOf(r+1));
				stk.setModel(beanList.get(r).getModel());
				stk.setQuantity(beanList.get(r).getQuantity());
				stk.setTotalPrice(beanList.get(r).getTotalPrice());
				beanList1.add(stk);
			}
			stockDataBean.setWarelists(beanList1);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

@Override
public List<ProductionDataBean> getProduction(String personID, String clientID,ProductionDataBean productionDataBean) {
	Query q=null;
	List<ProductionDataBean> beanList=null;
	if(personID!=null && clientID!=null){
	try{
		beanList=new ArrayList<ProductionDataBean>();
		q=entityManager.createQuery("select model from Cutterpurchaseorder where client_ID=? and status='ACTIVE'");
		q.setParameter(1, clientID);
		List<String> modelList=(ArrayList<String>)q.getResultList();
		Set<String> dublicateList=new HashSet<String>(modelList);
		modelList.clear();
		modelList.addAll(dublicateList);
 		if(modelList.size()>0){
		for(int a=0;a<modelList.size();a++){
		q=null;
		q=entityManager.createQuery("from Cutterpurchaseorder where  client_ID=? and model=? and status='ACTIVE' and invoiceStatus!='PAID'");
		q.setParameter(1, clientID);
		q.setParameter(2, modelList.get(a));
		List<Cutterpurchaseorder> orderlist=(ArrayList<Cutterpurchaseorder>)q.getResultList();
		if(orderlist.size()>0){
			for(int k=0;k<orderlist.size();k++){
				q=null;
				q=entityManager.createQuery("from Cutterpurchase where purchase_ID=? and status='ACTIVE'");
				q.setParameter(1, orderlist.get(k).getPurchase_ID());
				List<Cutterpurchase> purchaseList=(ArrayList<Cutterpurchase>)q.getResultList();
				if(purchaseList.size()>0){
					for(int j=0;j<purchaseList.size();j++){
						ProductionDataBean pro=new ProductionDataBean();
						pro.setModelName(orderlist.get(k).getModel());
						pro.setCutter(orderlist.get(k).getCutter());
						if(orderlist.get(k).getInvoiceStatus().equalsIgnoreCase("PAID"))
							pro.setInvoiceStatus(orderlist.get(k).getInvoiceStatus());
						else
							pro.setInvoiceStatus("unpaid");
						pro.setSeri(purchaseList.get(j).getSeri());
						pro.setQuantity(purchaseList.get(j).getQuantity());
						beanList.add(pro);
					}
					//productionDataBean.setCutterList(beanList);
				}
			}
		}
		}
		}
 		q=null;
 		q=entityManager.createQuery("select model from Printerreceiveorder where client_ID=? and status='ACTIVE'");
		q.setParameter(1, clientID);
		List<String> modelList1=(ArrayList<String>)q.getResultList();
		Set<String> dublicateList1=new HashSet<String>(modelList1);
		modelList1.clear();
		modelList1.addAll(dublicateList1);
 		if(modelList1.size()>0){
 			for(int r=0;r<modelList1.size();r++){
 				q=null;
 				q=entityManager.createQuery("from Printerreceiveorder where model=? and client_ID=? and status='ACTIVE' and invoiceStatus!='PAID'");
 				q.setParameter(1, modelList1.get(r));
 				q.setParameter(2, clientID);
 				List<Printerreceiveorder> receiverList=(ArrayList<Printerreceiveorder>)q.getResultList();
 				if(receiverList.size()>0){
 					for(int m=0;m<receiverList.size();m++){
 						q=null;
 						q=entityManager.createQuery("from Printerorder where purchase_id=? and status='ACTIVE'");
 						q.setParameter(1, receiverList.get(m).getPurchase_ID());
 						List<Printerorder> printerList=(ArrayList<Printerorder>)q.getResultList();
 						if(printerList.size()>0){
 							for(int p=0;p<printerList.size();p++){
 								ProductionDataBean pro=new ProductionDataBean();
 								pro.setPrinter(receiverList.get(m).getPrinter());
 								pro.setModelName(receiverList.get(m).getModel());
 								if(receiverList.get(m).getInvoiceStatus().equalsIgnoreCase("PAID"))
 									pro.setInvoiceStatus(receiverList.get(m).getInvoiceStatus());
 								else
 									pro.setInvoiceStatus("unpaid");
 								pro.setQuantity(printerList.get(p).getQuantity());
 								pro.setSeri(printerList.get(p).getSer());
 								beanList.add(pro);
 							}
 							//productionDataBean.setPrinterlist(beanList);
 						}
 					}
 				}
 			}
 		}
 		q=null;
 		q=entityManager.createQuery("select model from Cmtreceiveorder where client_ID=? and status='ACTIVE'");
		q.setParameter(1, clientID);
		List<String> modelList2=(ArrayList<String>)q.getResultList();
		Set<String> dublicateList2=new HashSet<String>(modelList2);
		modelList2.clear();
		modelList2.addAll(dublicateList2);
		if(modelList2.size()>0){
			for(int c=0;c<modelList2.size();c++){
				q=null;
				q=entityManager.createQuery("from Cmtreceiveorder where model=? and client_ID=? and status='ACTIVE' and invoiceStatus!='PAID'");
				q.setParameter(1, modelList2.get(c));
				q.setParameter(2, clientID);
				List<Cmtreceiveorder> cmtreceiveList=(ArrayList<Cmtreceiveorder>)q.getResultList();
				if(cmtreceiveList.size()>0){
					for(int b=0;b<cmtreceiveList.size();b++){
						q=null;
						q=entityManager.createQuery("from Cmtorder where receive_ID=? and status='ACTIVE'");
						q.setParameter(1, cmtreceiveList.get(b).getReceive_ID());
						List<Cmtorder> cmtorderList=(ArrayList<Cmtorder>)q.getResultList();
						if(cmtorderList.size()>0){
							for(int d=0;d<cmtorderList.size();d++){
								ProductionDataBean pro=new ProductionDataBean();
								pro.setCmt(cmtreceiveList.get(b).getCmt());
								pro.setModelName(cmtreceiveList.get(b).getModel());
								if(cmtreceiveList.get(b).getInvoiceStatus().equalsIgnoreCase("PAID"))
									pro.setInvoiceStatus(cmtreceiveList.get(b).getInvoiceStatus());
								else
									pro.setInvoiceStatus("unpaid");
								pro.setQuantity(cmtorderList.get(d).getQuantity());
								pro.setSeri(cmtorderList.get(d).getSeri());
								beanList.add(pro);
							}
							//productionDataBean.setCmtList(beanList);
						}
					}
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	return beanList;
}

@Override
public List<FinanceDataBean> getSaving(String personID, String clientID) {
	Query q=null;
	List<FinanceDataBean> financeList=null;
	if(personID!=null && clientID!=null){
		try{
			financeList=new ArrayList<FinanceDataBean>();
			q=entityManager.createQuery("from Cutterpurchaseorder where client_ID=? and status='ACTIVE' ORDER BY purchase_ID DESC");
			q.setParameter(1, clientID);
			List<Cutterpurchaseorder> cutterList=(ArrayList<Cutterpurchaseorder>)q.getResultList();
			if(cutterList.size()>0){
				for(int i=0;i<cutterList.size();i++){
					FinanceDataBean finance=new FinanceDataBean();
					finance.setSerialNo(String.valueOf(i+1));
					finance.setDate(cutterList.get(i).getOrderDate());
					finance.setName(cutterList.get(i).getModel());
					finance.setInvoiceNo(cutterList.get(i).getInvoiceNo());
					finance.setDebtCategory(cutterList.get(i).getCategory());
					finance.setWasteQuantity(cutterList.get(i).getTotalQuantity());
					finance.setAmount(cutterList.get(i).getSaving());
					finance.setDebtName(cutterList.get(i).getCutter());
					financeList.add(finance);
				}
			}
		}catch(Exception e){
		}
	}
	return financeList;
}

	
	@Override
	public void getItems(String personID, StockDataBean stockDataBean) {
		Query v=null;
		try{
			System.out.println("if");
			String itemstatus=stockDataBean.getCategory()+" ITEM";
			v=entityManager.createQuery("select itemName from ItemTable where  person_ID=? and itemStatus=?");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, itemstatus);
			List<String> items=(List<String>)v.getResultList();
			stockDataBean.setSeris(items);
			System.out.println();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<String> getsuppliername(String personID) {
		Query q=null;List<String> suppList=new ArrayList<String>();
		try{
			//q=entityManager.createQuery("select supplierName from Supplier where person_ID=? and status='ACTIVE'");
			q=entityManager.createQuery("select supplierName from Fabric where person_ID=? and  status='INSERTED' ORDER BY fabricId DESC");
			q.setParameter(1, personID);
			List<String> supplist=(List<String>)q.getResultList();
			if(supplist.size()>0){
				HashSet<String> hs=new HashSet<String>(supplist);
				supplist.clear();
				suppList.addAll(hs);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return suppList;
	}


	@Override
	public List<SupplyDataBean> getsupplierfabricpurchaseList(String personID,
			String str) {
		Query q=null;List<SupplyDataBean> purchaseFabricList=new ArrayList<SupplyDataBean>();
		try{    
		    q=entityManager.createQuery("from Fabric where person_ID=? and supplierName=? and status='INSERTED' ORDER BY fabricId DESC");
			q.setParameter(1, personID);
			q.setParameter(2, str);
			List<Fabric> fabriclist=(List<Fabric>)q.getResultList();
			if(fabriclist.size()>0){
				for (int i = 0; i < fabriclist.size(); i++) {
					SupplyDataBean supp=new SupplyDataBean();
					supp.setSerialNo(String.valueOf(i+1));
					supp.setFabricId(fabriclist.get(i).getFabricId());
					supp.setFabricPurchaseDate(fabriclist.get(i).getPurchaseDate());
					supp.setFabricInvoiceNumber(fabriclist.get(i).getInvoiceNumber());
					supp.setSupplierName(fabriclist.get(i).getSupplierName());
					supp.setFabricName(fabriclist.get(i).getFabricName());
					supp.setWeightTotal(fabriclist.get(i).getTotalWeight());
					supp.setQuantityTotal(fabriclist.get(i).getTotalQuantity());
					supp.setTotalAmount(fabriclist.get(i).getTotalAmount());
					supp.setPaymentStatus(fabriclist.get(i).getPaymentStatus());
					supp.setInvoiceStatus(fabriclist.get(i).getInvoiceStatus());
					purchaseFabricList.add(supp);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return purchaseFabricList;
	}


	@Override
	public List<SupplyDataBean> getdatefabricpurchaseList(String personID,
			Date fromDate, Date todate) {
		Query q=null;List<SupplyDataBean> purchaseFabricList=new ArrayList<SupplyDataBean>();
		try{    
			q=entityManager.createQuery("from Fabric where person_ID=? and purchaseDate between ? and ? and status='INSERTED' ORDER BY fabricId DESC");
			q.setParameter(1, personID);
			q.setParameter(2, fromDate);
			q.setParameter(3, todate);
			List<Fabric> fabriclist=(List<Fabric>)q.getResultList();
			if(fabriclist.size()>0){
				for (int i = 0; i < fabriclist.size(); i++) {
					SupplyDataBean supp=new SupplyDataBean();
					supp.setSerialNo(String.valueOf(i+1));
					supp.setFabricId(fabriclist.get(i).getFabricId());
					supp.setFabricPurchaseDate(fabriclist.get(i).getPurchaseDate());
					supp.setFabricInvoiceNumber(fabriclist.get(i).getInvoiceNumber());
					supp.setSupplierName(fabriclist.get(i).getSupplierName());
					supp.setFabricName(fabriclist.get(i).getFabricName());
					supp.setWeightTotal(fabriclist.get(i).getTotalWeight());
					supp.setQuantityTotal(fabriclist.get(i).getTotalQuantity());
					supp.setTotalAmount(fabriclist.get(i).getTotalAmount());
					supp.setPaymentStatus(fabriclist.get(i).getPaymentStatus());
					supp.setInvoiceStatus(fabriclist.get(i).getInvoiceStatus());
					purchaseFabricList.add(supp);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return purchaseFabricList;
	}


	@Override
	public List<SupplyDataBean> getinvoicetableview(String personID,
			SupplyDataBean supplyDataBean) {
		Query q=null;List<SupplyDataBean> pursaleslist=new ArrayList<SupplyDataBean>();
		System.out.println("category ---- "+supplyDataBean.getCategory());
		System.out.println("suppliername ---- "+supplyDataBean.getSupplierName());
		System.out.println("from date ---- "+supplyDataBean.getFromDate());
		System.out.println("to date ---- "+supplyDataBean.getToDate());
		System.out.println("personID ---- "+personID);
		String category=supplyDataBean.getCategory()+" ITEM";
		try{
			System.out.println("category "+category);
			if((supplyDataBean.getCategory()!=null) && supplyDataBean.getSupplierName()==null && supplyDataBean.getFromDate()==null && supplyDataBean.getToDate()==null )
			{
				System.out.println("---- category--------");
				q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and status='INSERTED' and invoiceStatus='StockIn' ORDER BY orderDate DESC");
				q.setParameter(1, personID);
				q.setParameter(2, category);
			}
			else if(supplyDataBean.getCategory()!=null &&(supplyDataBean.getSupplierName()!=null ) && supplyDataBean.getFromDate()==null && supplyDataBean.getToDate()==null )
			{
				System.out.println("---- category,supplier--------");
				q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and supplierName=? and status='INSERTED' and invoiceStatus='StockIn' ORDER BY orderDate DESC");
				q.setParameter(1, personID);
				q.setParameter(2, category);
				q.setParameter(3, supplyDataBean.getSupplierName());
			}
			else if((supplyDataBean.getCategory()!=null) && supplyDataBean.getSupplierName()==null && supplyDataBean.getFromDate()!=null && supplyDataBean.getToDate()!=null )
			{
				System.out.println("---- category,from and to date--------");
				q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and orderDate between ? and ? and status='INSERTED' and invoiceStatus='StockIn' ORDER BY orderDate DESC");
				q.setParameter(1, personID);
				q.setParameter(2, category);
				q.setParameter(3, supplyDataBean.getFromDate());
				q.setParameter(4, supplyDataBean.getToDate());
			}
			else if((supplyDataBean.getCategory()!=null) && (supplyDataBean.getSupplierName()!=null) && supplyDataBean.getFromDate()!=null && supplyDataBean.getToDate()!=null )
			{
				System.out.println("---- ALL--------");
				q=entityManager.createQuery("from PursalesItem where person_ID=? and itemStatus=? and supplierName=? and orderDate between ? and ? and status='INSERTED' and invoiceStatus='StockIn' ORDER BY orderDate DESC");
				q.setParameter(1, personID);
				q.setParameter(2, category);
				q.setParameter(3, supplyDataBean.getSupplierName());
				q.setParameter(4, supplyDataBean.getFromDate());
				q.setParameter(5, supplyDataBean.getToDate());
			}
			List<PursalesItem> pursalesList=(List<PursalesItem>)q.getResultList();
			System.out.println("pursalesList --"+pursalesList.size());
			if(pursalesList.size()>0){
				for (int i = 0; i < pursalesList.size(); i++) {
					SupplyDataBean sup=new SupplyDataBean();
					sup.setSerialNo(String.valueOf(i+1));
					sup.setTodayDate(pursalesList.get(i).getOrderDate());
					sup.setFabricInvoiceNumber(pursalesList.get(i).getInvoiceNumber());
					sup.setSupplierName(pursalesList.get(i).getSupplierName());
					sup.setVendorName(pursalesList.get(i).getVendorName());
					sup.setTotalAmount(pursalesList.get(i).getTotalPrice());
					sup.setInvoiceStatus(pursalesList.get(i).getInvoiceStatus());
					sup.setPaymentStatus(pursalesList.get(i).getPaymentStatus());
					sup.setFabricId(pursalesList.get(i).getPursalesitemId());
					sup.setStatus2(pursalesList.get(i).getStatus2());
					sup.setItemStatus(pursalesList.get(i).getItemStatus());
					pursaleslist.add(sup);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return pursaleslist;
	}


	@Override
	public List<String> getprinterModelName(String personID) {
		Query q=null;
		List<String> modelname=new ArrayList<String>();
		try{
			q=entityManager.createQuery("select model from Printerreceiveorder where person_ID=? and status='ACTIVE' ");
			q.setParameter(1, personID);
			modelname=q.getResultList();
			if(modelname.size() > 0){
				HashSet<String> model=new HashSet<String>(modelname);
				modelname.clear();
				modelname.addAll(model);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelname;
	}


	@Override
	public List<String> getprinterseriNo(String modelname, String personID) {
		Query q=null;
		List<Printerreceiveorder> printerrecord=new ArrayList<Printerreceiveorder>();
		List<String> serilist=new ArrayList<String>();
		List<String> serilist1=new ArrayList<String>();
		try{
			q=entityManager.createQuery("from Printerreceiveorder where model=? and person_ID=? and status='ACTIVE' ");
			q.setParameter(1, modelname);
			q.setParameter(2, personID);
			printerrecord=q.getResultList();
			if(printerrecord.size()>0){
				for (int i = 0; i < printerrecord.size(); i++) {
					int id=printerrecord.get(i).getPurchase_ID();
					q=entityManager.createQuery("from Printerorder where purchase_id=?  and status='ACTIVE'");
					q.setParameter(1, id);
					List<Printerorder> printerOrder=(List<Printerorder>)q.getResultList();
					if(printerOrder.size()>0){
						for (int j = 0; j < printerOrder.size(); j++) {
							serilist1.add(printerOrder.get(j).getSer());
						}
					}
				}                                                     
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("seriNo "+serilist.size());
		return serilist1;
	}


	@Override
	public List<StockDataBean> getprinterTable(String personID,
			StockDataBean stockDataBean) {
		Query q=null;
		List<StockDataBean>viewList=new ArrayList<StockDataBean>();
		try{
			q=entityManager.createQuery("from Printerorder where ser=? and status='ACTIVE' and orderstatus='READY CMT'");
			q.setParameter(1, stockDataBean.getSeriNolist());
			List<Printerorder> stocklist=(List<Printerorder>)q.getResultList();
			if(stocklist.size()>0){
				for (int i = 0; i < stocklist.size(); i++) {
					StockDataBean obj=new StockDataBean();
					obj.setSerialNo(String.valueOf(i+1)); 
					obj.setModel(stocklist.get(i).getPrinterreceiveorder().getModel()); 
					obj.setSeriDetails(stocklist.get(i).getSer()); 
					obj.setStock(stocklist.get(i).getQuantity());
					obj.setStockID(stocklist.get(i).getPoredrId());
					System.out.println("quantity "+stocklist.get(i).getQuantity());
					obj.setPrice(stocklist.get(i).getValue()); 
					System.out.println("price "+stocklist.get(i).getValue());
					viewList.add(obj);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return viewList;
	}


	@Transactional(value="transactionManager")
	public List<StockDataBean> getprinterSave(StockDataBean stockDataBean,String personID) {
		Query q=null;
		try{
			for(int i=0 ; i < stockDataBean.getPrintertableview().size() ; i++){
				System.out.println("==============stockID==========="+stockDataBean.getPrintertableview().get(i).getStockID()); 
				int personid=Integer.parseInt(personID);
				q=entityManager.createQuery("from PrinterReturn where person_ID=? and model=? and seri=? and date=? and status='Active'");
				q.setParameter(1, personID);
				q.setParameter(2, stockDataBean.getPrintertableview().get(i).getModel());
				q.setParameter(3,stockDataBean.getPrintertableview().get(i).getSeriDetails());
				q.setParameter(4, stockDataBean.getDate());
				List<PrinterReturn> printerreturnList=(List<PrinterReturn>)q.getResultList();
				System.out.println("size "+printerreturnList.size());
				if(printerreturnList.size()>0){
					PrinterReturn printerReturns=entityManager.find(PrinterReturn.class, printerreturnList.get(0).getPrinterReturnId());
					printerReturns.setQuantity(new BigDecimal(printerreturnList.get(0).getQuantity()).add(new BigDecimal(stockDataBean.getPrintertableview().get(0).getQty())).toString());
					entityManager.merge(printerReturns);
				}else{
					PrinterReturn printerReturn=new PrinterReturn();
					printerReturn.setModel(stockDataBean.getPrintertableview().get(i).getModel()); 
					printerReturn.setSeri(stockDataBean.getPrintertableview().get(i).getSeriDetails()); 
					printerReturn.setQuantity(stockDataBean.getPrintertableview().get(i).getQty());   
					printerReturn.setPrice(stockDataBean.getPrintertableview().get(i).getPrice()); 
					printerReturn.setNetPrice(stockDataBean.getPrintertableview().get(i).getNetprice()); 
					printerReturn.setDate(stockDataBean.getDate());
					printerReturn.setStatus("Active");   
					printerReturn.setPerson(entityManager.find(Person.class, personid));
					printerReturn.setPrinterorder(entityManager.find(Printerorder.class, stockDataBean.getPrintertableview().get(i).getStockID()));			
					entityManager.persist(printerReturn); 
					entityManager.flush();
					entityManager.clear();
				}
				Printerorder printerorder=entityManager.find(Printerorder.class, stockDataBean.getPrintertableview().get(i).getStockID()); 
				printerorder.setQuantity(new BigDecimal(stockDataBean.getPrintertableview().get(i).getStock()).subtract(new BigDecimal(stockDataBean.getPrintertableview().get(i).getQty())).toString());   
				entityManager.merge(printerorder);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null; 
	}


	@Override
	public List<StockDataBean> getprinterReturn(StockDataBean stockDataBean,
			String personID) {
		Query q=null;
		List<StockDataBean> printertableList=new ArrayList<StockDataBean>();
		try{
			q=entityManager.createQuery("from PrinterReturn where person_ID=? and status='Active'"); 
			q.setParameter(1, personID);
			List<PrinterReturn> printer=(List<PrinterReturn>)q.getResultList();
			if(printer.size() > 0){
				for(int i=0;i < printer.size();i++){
					StockDataBean obj=new StockDataBean();
					obj.setReturnmodel(printer.get(i).getModel());
					obj.setSerialNo(String.valueOf(i+1));
					obj.setDate(printer.get(i).getDate());
					obj.setReturnseri(printer.get(i).getSeri()); 
					obj.setReturnstatus(printer.get(i).getStatus()); 
					obj.setReturnquantity(printer.get(i).getQuantity()); 
					obj.setReturnprice(printer.get(i).getPrice()); 
					obj.setReturnnetprice(printer.get(i).getNetPrice()); 
					obj.setReturnID(printer.get(i).getPrinterReturnId()); 
					printertableList.add(obj);  
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return printertableList;
	}


	@Override
	public List<StockDataBean> getprinterReturn1(StockDataBean stockDataBean,
			String personID) {
		Query q=null;
		List<StockDataBean> printertableList1=new ArrayList<StockDataBean>();
		try{
			q=entityManager.createQuery("from PrinterReturn where printer_return_id=? and person_ID=? and status='Active'"); 
			q.setParameter(1, stockDataBean.getReturnID());
			q.setParameter(2, personID);		
			List<PrinterReturn> printer=(List<PrinterReturn>)q.getResultList();
			if(printer.size() > 0){
				for(int i=0;i < printer.size();i++){
					StockDataBean obj=new StockDataBean();
					obj.setReturnmodel1(printer.get(i).getModel()); 
					//obj.setReturnshop1(printer.get(i).getShop()); 
					obj.setReturnseri1(printer.get(i).getSeri()); 
					obj.setReturnstatus1(printer.get(i).getStatus()); 
					obj.setReturnquantity1(printer.get(i).getQuantity()); 
					obj.setReturnprice1(printer.get(i).getPrice()); 
					obj.setReturnnetprice1(printer.get(i).getNetPrice()); 
					printertableList1.add(obj);  
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return printertableList1;
	}


	@Override
	public List<StockDataBean> getPrinteredit(StockDataBean stockDataBean,
			String personID) {
		Query q=null;
		List<StockDataBean> printertableList2=new ArrayList<StockDataBean>();
		try{
			q=entityManager.createQuery("from PrinterReturn where printer_return_id=? and person_ID=? and status='Active'"); 
			q.setParameter(1, stockDataBean.getReturnID());
			q.setParameter(2, personID);		
			List<PrinterReturn> printer=(List<PrinterReturn>)q.getResultList();
			if(printer.size() > 0){
				for(int i=0;i < printer.size();i++){
					StockDataBean obj=new StockDataBean();
					obj.setReturnmodel1(printer.get(i).getModel()); 
					//obj.setReturnshop1(printer.get(i).getShop()); 
					obj.setReturnseri1(printer.get(i).getSeri()); 
					obj.setReturnstatus1(printer.get(i).getStatus()); 
					obj.setReturnquantity1(printer.get(i).getQuantity()); 
					obj.setReturnquantity2(printer.get(i).getQuantity());
					obj.setReturnprice1(printer.get(i).getPrice()); 
					obj.setReturnnetprice1(printer.get(i).getNetPrice()); 
					printertableList2.add(obj);  
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return printertableList2;
	}


	@Transactional(value="transactionManager")
	public String printerreturnEdit(String personID,
			List<StockDataBean> returnList2, StockDataBean stockDataBean) {
		System.out.println("quantirtyyy----"+returnList2.get(0).getReturnquantity1()+stockDataBean.getReturnID());
		String status="Fail";
		Query q=null;
		BigDecimal availreturn=new BigDecimal(0);
		if(personID!=null){
		try{
			q=entityManager.createQuery("from Printerorder where ser=? and status='ACTIVE' and orderstatus='READY CMT'");
			q.setParameter(1, returnList2.get(0).getReturnseri1());
			List<Printerorder> printerorder=(List<Printerorder>)q.getResultList();
			if(printerorder.size()>0)
			{
				availreturn=new BigDecimal(printerorder.get(0).getQuantity()).add(new BigDecimal(returnList2.get(0).getReturnquantity2()));
				if(new BigDecimal(returnList2.get(0).getReturnquantity1()).compareTo(availreturn)==1){
					stockDataBean.setAvail_quant(printerorder.get(0).getQuantity());
				}
				else{
					PrinterReturn printer=entityManager.find(PrinterReturn.class, stockDataBean.getReturnID());
					printer.setQuantity(returnList2.get(0).getReturnquantity1());
					entityManager.merge(printer);
					
					 
					 Printerorder printerord=entityManager.find(Printerorder.class, printerorder.get(0).getPoredrId());
					 printerord.setQuantity(""+availreturn.subtract(new BigDecimal(returnList2.get(0).getReturnquantity1())));
					entityManager.merge(printerord);
					status="Success";
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return status;
	}


	@Transactional(value="transactionManager")
	public void getprinterDelete(StockDataBean stockDataBean, String personID) {
		Query q=null;
		List<Printerreceiveorder> printerrecord=new ArrayList<Printerreceiveorder>();
		System.out.println(stockDataBean.getReturnmodel() +" ---- "+stockDataBean.getReturnseri());
		try{
			q=entityManager.createQuery("from Printerreceiveorder where model=? and person_ID=? and status='ACTIVE' ");
			q.setParameter(1, stockDataBean.getReturnmodel());
			q.setParameter(2, personID);
			printerrecord=q.getResultList();
			if(printerrecord.size() > 0){
				int purchase_id=printerrecord.get(0).getPurchase_ID();
				System.out.println("purchase_id "+purchase_id);
				System.out.println("seri "+stockDataBean.getReturnseri());
				q=entityManager.createQuery("from Printerorder where  purchase_id=? and ser=? and status='ACTIVE' and orderstatus='READY CMT' ");
				q.setParameter(1, purchase_id);
				q.setParameter(2, stockDataBean.getReturnseri());
				List<Printerorder> porderList=(List<Printerorder>)q.getResultList();
				System.out.println("porderList -- "+porderList.size());
				if(porderList.size()>0){
					q=entityManager.createQuery("from PrinterReturn where person_ID=? and model=? and seri=? and  date=? and status='Active'");
					q.setParameter(1, personID);
					q.setParameter(2, stockDataBean.getReturnmodel());
					q.setParameter(3, stockDataBean.getReturnseri());
					q.setParameter(4, stockDataBean.getDate());
					List<PrinterReturn> printerreturnList=(List<PrinterReturn>)q.getResultList();
					System.out.println("List Size===========================>"+printerreturnList.size());
					System.out.println("QUANTITY============================>"+printerreturnList.get(0).getQuantity()); 
					System.out.println(porderList.get(0).getQuantity());
					if(printerreturnList.size()>0){
						PrinterReturn printerreturn=entityManager.find(PrinterReturn.class, stockDataBean.getReturnID());
						printerreturn.setStatus("Deactive");  
						entityManager.merge(printerreturn); 				
					}
					Printerorder printerorder=entityManager.find(Printerorder.class, porderList.get(0).getPoredrId());
					printerorder.setQuantity(new BigDecimal(porderList.get(0).getQuantity()).add(new BigDecimal(printerreturnList.get(0).getQuantity())).toString());
					entityManager.merge(printerorder);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
}


	@Override
	public String seriCheck(String seri,String model) {
		Query q=null;String status="NOT EXIST";
		try{
			q=entityManager.createQuery("from Cutterpurchaseorder where model=?");
			q.setParameter(1, model);
			List<Cutterpurchaseorder> order=(List<Cutterpurchaseorder>)q.getResultList();
			System.out.println("order -- "+order.size());
			if(order.size()>0){
				for (int i = 0; i < order.size(); i++) {
					q=entityManager.createQuery("from Cutterpurchase where seri=? and purchase_ID=?");
					q.setParameter(1, seri);
					q.setParameter(2, order.get(i).getPurchase_ID());
					List<Cutterpurchase> cutter=(List<Cutterpurchase>)q.getResultList();
					System.out.println("cutter "+cutter.size());
					if(cutter.size() > 0){
						status="EXIST";
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}


	@Override
	public void getvendorCmtPayments(String personID,
			ProductionDataBean productionDataBean) {
		Query v=null;
		List<ProductionDataBean> printerList=new ArrayList<ProductionDataBean>();
		System.out.println("Inside getvendorCmtPayments");
		try{
			v=entityManager.createQuery("from Cmtreceiveorder where person_ID=? and cmt=? and status='ACTIVE' and (invoiceStatus='PARTIAL STOCKIN' or invoiceStatus='INVOICE' or invoiceStatus='PAYMENT' or invoiceStatus='PAID') ORDER BY receive_ID DESC");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, productionDataBean.getCmt());
			List<Cmtreceiveorder> cmtReceive=(List<Cmtreceiveorder>)v.getResultList();
			
			if(cmtReceive.size()>0){
				for (int i = 0; i < cmtReceive.size(); i++) {
					BigDecimal paid=new BigDecimal(0);
					BigDecimal unpaid=new BigDecimal(0);
					BigDecimal paidquant=new BigDecimal(0);
					BigDecimal unpaidquant=new BigDecimal(0);
					v=null;
					v=entityManager.createQuery("from CmtStockin where cmtreceive_ID=?");
					v.setParameter(1, cmtReceive.get(i).getReceive_ID());
					List<CmtStockin> cmtstkin=(List<CmtStockin>)v.getResultList();
					if(cmtstkin.size()>0){
						for (int k = 0; k < cmtstkin.size(); k++) {
							if(cmtstkin.get(k).getStatus().equalsIgnoreCase("paid")){
								paid=paid.add((new BigDecimal(cmtstkin.get(k).getStockinQuantity())).multiply(new BigDecimal(cmtstkin.get(k).getCmtValue())));
								paidquant=paidquant.add(new BigDecimal(cmtstkin.get(k).getStockinQuantity()));
							}else if(cmtstkin.get(k).getStatus().equalsIgnoreCase("unpaid")){
								unpaid=unpaid.add(new BigDecimal(cmtstkin.get(k).getStockinQuantity()).multiply(new BigDecimal(cmtstkin.get(k).getCmtValue())));
								unpaidquant=unpaidquant.add(new BigDecimal(cmtstkin.get(k).getStockinQuantity()));
							}
						}
						if (paidquant.compareTo(BigDecimal.ZERO) > 0){
							ProductionDataBean printers=new ProductionDataBean();
							printers.setSerialNo(String.valueOf(i+1));
							printers.setOrderDate(cmtReceive.get(i).getReceiveDate());
							printers.setInvoiceNo(cmtReceive.get(i).getInvoiceNo());
							printers.setCmt(cmtReceive.get(i).getCmt());
							printers.setInvoiceStatus("paid");
							printers.setValue(paid.toString());
							printers.setTotalQuantity(paidquant.toString());
							printerList.add(printers);
						}
						if(unpaidquant.compareTo(BigDecimal.ZERO) > 0){
							ProductionDataBean printers=new ProductionDataBean();
							printers.setSerialNo(String.valueOf(i+1));
							printers.setOrderDate(cmtReceive.get(i).getReceiveDate());
							printers.setInvoiceNo(cmtReceive.get(i).getInvoiceNo());
							printers.setCmt(cmtReceive.get(i).getCmt());
							printers.setInvoiceStatus("unpaid");
							printers.setValue(unpaid.toString());
							printers.setTotalQuantity(unpaidquant.toString());
							printerList.add(printers);
						}	
					}
				}
			}System.out.println("printerList -- "+printerList.size());
			productionDataBean.setOrderQuantitylist(printerList);
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
@Override
	public String payrollCutterchange(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		Query v=null;
		List<ProductionDataBean> printerList=new ArrayList<ProductionDataBean>();
		try{
			v=entityManager.createQuery("from Cutterpurchaseorder where person_ID=? and cutter=? and status='ACTIVE' and (invoiceStatus='INSERTED' or invoiceStatus='INVOICE' or invoiceStatus='PAID') ORDER BY purchase_ID DESC");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, productionDataBean.getCutter());
			List<Cutterpurchaseorder> cutterList=(List<Cutterpurchaseorder>)v.getResultList();
			if(cutterList.size()>0){
				for (int i = 0; i < cutterList.size(); i++) {
					ProductionDataBean printers=new ProductionDataBean();
					printers.setSerialNo(String.valueOf(i+1));
					printers.setOrderDate(cutterList.get(i).getOrderDate());
					printers.setInvoiceNo(cutterList.get(i).getInvoiceNo());
					printers.setCutter(cutterList.get(i).getCutter());
					printers.setModelName(cutterList.get(i).getModel());
					printers.setValue(cutterList.get(i).getValue());
					printers.setTotalQuantity(cutterList.get(i).getTotalQuantity());
					printers.setSaving(cutterList.get(i).getSaving());
					if(cutterList.get(i).getInvoiceStatus().equals("PAID")) printers.setInvoiceStatus(cutterList.get(i).getInvoiceStatus());
					else printers.setInvoiceStatus("UNPAID");
					v=null;
					v=entityManager.createQuery("from Payment where cutter_ID=?");
					v.setParameter(1, cutterList.get(i).getPurchase_ID());
					List<Payment> payment=(List<Payment>)v.getResultList();
					if(payment.size()>0){
						printers.setBalanceAmount(payment.get(0).getBalanceAmount());
					}else{
						printers.setBalanceAmount(cutterList.get(i).getValue());
					}
					printerList.add(printers);
				}
				productionDataBean.setOrderQuantitylist(printerList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String payrollPrinterchange(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		Query v=null;
		List<ProductionDataBean> printerList=new ArrayList<ProductionDataBean>();
		System.out.println("prprprp====="+productionDataBean.getPrinterr());
		try{
			v=entityManager.createQuery("from Printerreceiveorder where person_ID=? and printer=? and status='ACTIVE' and (invoiceStatus='INVOICE' or invoiceStatus='PAYMENT' or invoiceStatus='PAID') ORDER BY purchase_ID DESC");
			v.setParameter(1, Integer.parseInt(personID));
			v.setParameter(2, productionDataBean.getPrinterr());
			List<Printerreceiveorder> printerReceive=(List<Printerreceiveorder>)v.getResultList();
			if(printerReceive.size()>0){
				for (int i = 0; i < printerReceive.size(); i++) {
					ProductionDataBean printers=new ProductionDataBean();
					printers.setSerialNo(String.valueOf(i+1));
					printers.setOrderDate(printerReceive.get(i).getReceivedate());
					printers.setInvoiceNo(printerReceive.get(i).getInvoice());
					printers.setValue(printerReceive.get(i).getValue());
					printers.setTotalQuantity(printerReceive.get(i).getTotalquantitry());
					printers.setPrinter(printerReceive.get(i).getPrinter());
					if(printerReceive.get(i).getInvoiceStatus().equals("PAID")) printers.setInvoiceStatus(printerReceive.get(i).getInvoiceStatus());
					else printers.setInvoiceStatus("UNPAID");
					v=null;
					v=entityManager.createQuery("from Payment where printer_ID=?");
					v.setParameter(1, printerReceive.get(i).getPurchase_ID());
					List<Payment> payment=(List<Payment>)v.getResultList();
					if(payment.size()>0){
						printers.setBalanceAmount(payment.get(0).getBalanceAmount());
					}else{
						printers.setBalanceAmount(printerReceive.get(i).getValue());
					}
					printerList.add(printers);
				}
				productionDataBean.setOrderQuantitylist(printerList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public List<String> getemployeeNames(String personID,String clientID,EmployeeDataBean employeeDataBean) {
		Query q=null;
		List<String> employeenames=null;
		try{
			q=entityManager.createQuery("from Branch where branchName=? and person_ID=? and client_ID=?");
			q.setParameter(1, employeeDataBean.getBranchName());
			q.setParameter(2, Integer.parseInt(personID));
			q.setParameter(3, Integer.parseInt(personID));
			List<Branch> branch=(List<Branch>)q.getResultList();
			if(branch.size()>0){
				int id=branch.get(0).getBranch_ID();
				employeenames=new ArrayList<String>();
				q=entityManager.createQuery("select employeeName from Employee where branch_ID=? and person_ID=? and client_ID=?");
				q.setParameter(1, id);
				q.setParameter(2, Integer.parseInt(personID));
				q.setParameter(3, Integer.parseInt(personID));
				employeenames=(List<String>)q.getResultList();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return employeenames;
	}


	@Override
	public List<String> getbaranchname(String personID,String clientID) {
		Query q=null;
		List<String> branchnames=null;
		try{
			branchnames=new ArrayList<String>();
			q=entityManager.createQuery("select branchName from Branch where  person_ID=? and client_ID=?");
			q.setParameter(1, Integer.parseInt(personID));
			q.setParameter(2, Integer.parseInt(personID));
			branchnames=(List<String>)q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return branchnames;
	}


	@Override
	public List<EmployeeDataBean> getEmployeeReportList1(String personID,
			String clientID, EmployeeDataBean employeeDataBean) {
		Query q=null;List<EmployeeDataBean> employeeLists=new ArrayList<EmployeeDataBean>();
		try{
			System.out.println("=========");
			if(!employeeDataBean.getBranchName().equals("")){
				System.out.println("=====batch name====");
				q=entityManager.createQuery("from Branch where branchName=? and person_ID=? and client_ID=?");
				q.setParameter(1, employeeDataBean.getBranchName());
				q.setParameter(2, Integer.parseInt(personID));
				q.setParameter(3, Integer.parseInt(clientID));
				List<Branch> branch=(List<Branch>)q.getResultList();
				if(branch.size()>0){
					int id=branch.get(0).getBranch_ID();
					if(!employeeDataBean.getEmployeeName().equals("")){
						if(employeeDataBean.getFromDate()!=null && employeeDataBean.getToDate()!=null){
							q=entityManager.createQuery("from Employee where person_ID=? and client_ID=? and branch_ID=? and employeeName=? and entryDate between ? and ? and status='ACTIVE' ORDER BY employee_ID DESC");
							q.setParameter(1, personID);
							q.setParameter(2, clientID);
							q.setParameter(3, id);
							q.setParameter(4, employeeDataBean.getEmployeeName());
							q.setParameter(5, employeeDataBean.getFromDate());
							q.setParameter(6, employeeDataBean.getToDate());
						}else{
							q=entityManager.createQuery("from Employee where person_ID=? and client_ID=? and branch_ID=? and employeeName=? and status='ACTIVE' ORDER BY employee_ID DESC");
							q.setParameter(1, personID);
							q.setParameter(2, clientID);
							q.setParameter(3, id);
							q.setParameter(4, employeeDataBean.getEmployeeName());
						}
					}else{
						q=entityManager.createQuery("from Employee where person_ID=? and client_ID=? and branch_ID=? and status='ACTIVE' ORDER BY employee_ID DESC");
						q.setParameter(1, personID);
						q.setParameter(2, clientID);
						q.setParameter(3, id);
					}
				}
			}else{
				q=entityManager.createQuery("from Employee where person_ID=? and client_ID=? and entryDate between ? and ? and status='ACTIVE' ORDER BY employee_ID DESC");
				q.setParameter(1, personID);
				q.setParameter(2, clientID);
				q.setParameter(3, employeeDataBean.getFromDate());
				q.setParameter(4, employeeDataBean.getToDate());
			}
			List<Employee> employeeList=(List<Employee>)q.getResultList();
			if(employeeList.size()>0){
				for (int i = 0; i < employeeList.size(); i++) {
					EmployeeDataBean emp=new EmployeeDataBean();
					emp.setSerialNo(String.valueOf(i+1));
					emp.setBranchName(employeeList.get(i).getBranch().getBranchName());
					emp.setEmployeeName(employeeList.get(i).getEmployeeName());
					emp.setEntryDate(employeeList.get(i).getEntryDate());
					emp.setDaily(employeeList.get(i).getDaily());
					emp.setEntryDate(employeeList.get(i).getEntryDate());
					emp.setDateofBirth(employeeList.get(i).getDob());
					emp.setAddress(employeeList.get(i).getAddress());
					emp.setPhoneNo1(employeeList.get(i).getPhoneNo1());
					emp.setCommission(employeeList.get(i).getComission());
					q=null;
					q=entityManager.createQuery("from Branch where branchName=? and person_ID=? and client_ID=? and status='ACTIVE'");
					q.setParameter(1, employeeList.get(i).getBranch().getBranchName());
					q.setParameter(2, personID);
					q.setParameter(3, clientID);
					List<Branch> branchlist=(List<Branch>)q.getResultList();
					if(branchlist.size() > 0){
						emp.setOvertime(branchlist.get(0).getOvertime());
						emp.setLate(branchlist.get(0).getLate());
					}
					employeeLists.add(emp);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return employeeLists;
}	
	@Override
	@Transactional(value="transactionManager")
	public String savetable(String personID, String clientID,ProductionDataBean productionDataBean) {
	Query q=null;
	String status="Fail";int count=0;
	try{
		//INSERT NEWTABLE
		System.out.println("=== tablelist size ==="+productionDataBean.getTablevaluelist().size());
		Newtable table=new Newtable();
		table.setTableName(productionDataBean.getTableName());
		table.setTableNo(productionDataBean.getTableNo());
	  	table.setTablerow(productionDataBean.getTableRow());
		table.setTablecolumn(productionDataBean.getTableColumn());
		table.setStatus("ACTIVE");
		for (int i = 0; i < productionDataBean.getTablevaluelist().size(); i++) {
			//count for empty and filled cell
			System.out.println("column ---- "+productionDataBean.getTableColumn());
			System.out.println("----testing---"+(productionDataBean.getTablevaluelist().get(i).getAmodelName()));
			System.out.println("----testing---"+productionDataBean.getTablevaluelist().get(i).getAseri());
			System.out.println("----testing---"+productionDataBean.getTablevaluelist().get(i).getAcutter());
			System.out.println("----testing---"+productionDataBean.getTablevaluelist().get(i).getAtanggal());
			if(productionDataBean.getTableColumn().equalsIgnoreCase("1")){
				if(productionDataBean.getTablevaluelist().get(i).getAmodelName()==null && productionDataBean.getTablevaluelist().get(i).getAseri()==null && productionDataBean.getTablevaluelist().get(i).getAcutter()==null && productionDataBean.getTablevaluelist().get(i).getAtanggal()==null){
					count=count+1;
				}
			}
			else if(productionDataBean.getTableColumn().equalsIgnoreCase("2")){
				if( productionDataBean.getTablevaluelist().get(i).getAmodelName()==null && productionDataBean.getTablevaluelist().get(i).getAseri()==null	&&  productionDataBean.getTablevaluelist().get(i).getAcutter()==null && productionDataBean.getTablevaluelist().get(i).getAtanggal().equals(null)){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getBmodelName()==null &&productionDataBean.getTablevaluelist().get(i).getBseri()==null&& productionDataBean.getTablevaluelist().get(i).getBcutter()==null && productionDataBean.getTablevaluelist().get(i).getBtanggal()==null){
					count=count+1;
				}
			}
			else if(productionDataBean.getTableColumn().equalsIgnoreCase("3")){
				if(productionDataBean.getTablevaluelist().get(i).getAmodelName()== null && productionDataBean.getTablevaluelist().get(i).getAseri()==null && productionDataBean.getTablevaluelist().get(i).getAcutter() == null && productionDataBean.getTablevaluelist().get(i).getAtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getBmodelName()==null && productionDataBean.getTablevaluelist().get(i).getBseri()==null && productionDataBean.getTablevaluelist().get(i).getBcutter()==null && productionDataBean.getTablevaluelist().get(i).getBtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getCmodelName()==null && productionDataBean.getTablevaluelist().get(i).getCseri()==null && productionDataBean.getTablevaluelist().get(i).getCcutter()==null && productionDataBean.getTablevaluelist().get(i).getCtanggal()==null){
					count=count+1;
				}
			}
			else if(productionDataBean.getTableColumn().equalsIgnoreCase("4")){
				if(productionDataBean.getTablevaluelist().get(i).getAmodelName()==null && productionDataBean.getTablevaluelist().get(i).getAseri()==null && productionDataBean.getTablevaluelist().get(i).getAcutter()==null && productionDataBean.getTablevaluelist().get(i).getAtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getBmodelName()==null && productionDataBean.getTablevaluelist().get(i).getBseri()==null && productionDataBean.getTablevaluelist().get(i).getBcutter()==null && productionDataBean.getTablevaluelist().get(i).getBtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getCmodelName()==null && productionDataBean.getTablevaluelist().get(i).getCseri()==null && productionDataBean.getTablevaluelist().get(i).getCcutter()==null && productionDataBean.getTablevaluelist().get(i).getCtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getDmodelName()==null && productionDataBean.getTablevaluelist().get(i).getDseri()==null && productionDataBean.getTablevaluelist().get(i).getDcutter()==null && productionDataBean.getTablevaluelist().get(i).getDtanggal()==null){
					count=count+1;
				}
			}
			else if(productionDataBean.getTableColumn().equalsIgnoreCase("5")){
				if(productionDataBean.getTablevaluelist().get(i).getAmodelName()==null && productionDataBean.getTablevaluelist().get(i).getAseri()==null && productionDataBean.getTablevaluelist().get(i).getAcutter()==null && productionDataBean.getTablevaluelist().get(i).getAtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getBmodelName()==null && productionDataBean.getTablevaluelist().get(i).getBseri()==null && productionDataBean.getTablevaluelist().get(i).getBcutter()==null && productionDataBean.getTablevaluelist().get(i).getBtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getCmodelName()==null && productionDataBean.getTablevaluelist().get(i).getCseri()==null && productionDataBean.getTablevaluelist().get(i).getCcutter()==null && productionDataBean.getTablevaluelist().get(i).getCtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getDmodelName()==null && productionDataBean.getTablevaluelist().get(i).getDseri()==null && productionDataBean.getTablevaluelist().get(i).getDcutter()==null && productionDataBean.getTablevaluelist().get(i).getDtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getEmodelName()==null && productionDataBean.getTablevaluelist().get(i).getEseri()==null && productionDataBean.getTablevaluelist().get(i).getEcutter()==null && productionDataBean.getTablevaluelist().get(i).getEtanggal()==null){
					count=count+1;
				}
			}
			else if(productionDataBean.getTableColumn().equalsIgnoreCase("6")){
				if(productionDataBean.getTablevaluelist().get(i).getAmodelName()==null && productionDataBean.getTablevaluelist().get(i).getAseri()==null && productionDataBean.getTablevaluelist().get(i).getAcutter()==null && productionDataBean.getTablevaluelist().get(i).getAtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getBmodelName()==null && productionDataBean.getTablevaluelist().get(i).getBseri()==null && productionDataBean.getTablevaluelist().get(i).getBcutter()==null && productionDataBean.getTablevaluelist().get(i).getBtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getCmodelName()==null && productionDataBean.getTablevaluelist().get(i).getCseri()==null && productionDataBean.getTablevaluelist().get(i).getCcutter()==null && productionDataBean.getTablevaluelist().get(i).getCtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getDmodelName()==null && productionDataBean.getTablevaluelist().get(i).getDseri()==null && productionDataBean.getTablevaluelist().get(i).getDcutter()==null && productionDataBean.getTablevaluelist().get(i).getDtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getEmodelName()==null && productionDataBean.getTablevaluelist().get(i).getEseri()==null && productionDataBean.getTablevaluelist().get(i).getEcutter()==null && productionDataBean.getTablevaluelist().get(i).getEtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getFmodelName()==null && productionDataBean.getTablevaluelist().get(i).getFseri()==null && productionDataBean.getTablevaluelist().get(i).getFcutter()==null && productionDataBean.getTablevaluelist().get(i).getFtanggal()==null){
					count=count+1;
				}
			}
			else if(productionDataBean.getTableColumn().equalsIgnoreCase("7")){
				if(productionDataBean.getTablevaluelist().get(i).getAmodelName()==null && productionDataBean.getTablevaluelist().get(i).getAseri()==null && productionDataBean.getTablevaluelist().get(i).getAcutter()==null && productionDataBean.getTablevaluelist().get(i).getAtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getBmodelName()==null && productionDataBean.getTablevaluelist().get(i).getBseri()==null && productionDataBean.getTablevaluelist().get(i).getBcutter()==null && productionDataBean.getTablevaluelist().get(i).getBtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getCmodelName()==null && productionDataBean.getTablevaluelist().get(i).getCseri()==null && productionDataBean.getTablevaluelist().get(i).getCcutter()==null && productionDataBean.getTablevaluelist().get(i).getCtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getDmodelName()==null && productionDataBean.getTablevaluelist().get(i).getDseri()==null && productionDataBean.getTablevaluelist().get(i).getDcutter()==null && productionDataBean.getTablevaluelist().get(i).getDtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getEmodelName()==null && productionDataBean.getTablevaluelist().get(i).getEseri()==null && productionDataBean.getTablevaluelist().get(i).getEcutter()==null && productionDataBean.getTablevaluelist().get(i).getEtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getFmodelName()==null && productionDataBean.getTablevaluelist().get(i).getFseri()==null && productionDataBean.getTablevaluelist().get(i).getFcutter()==null && productionDataBean.getTablevaluelist().get(i).getFtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getGmodelName()==null && productionDataBean.getTablevaluelist().get(i).getGseri()==null && productionDataBean.getTablevaluelist().get(i).getGcutter()==null && productionDataBean.getTablevaluelist().get(i).getGtanggal()==null){
					count=count+1;
				}
			}
			else if(productionDataBean.getTableColumn().equalsIgnoreCase("8")){
				if(productionDataBean.getTablevaluelist().get(i).getAmodelName()==null && productionDataBean.getTablevaluelist().get(i).getAseri()==null && productionDataBean.getTablevaluelist().get(i).getAcutter()==null && productionDataBean.getTablevaluelist().get(i).getAtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getBmodelName()==null && productionDataBean.getTablevaluelist().get(i).getBseri()==null && productionDataBean.getTablevaluelist().get(i).getBcutter()==null && productionDataBean.getTablevaluelist().get(i).getBtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getCmodelName()==null && productionDataBean.getTablevaluelist().get(i).getCseri()==null && productionDataBean.getTablevaluelist().get(i).getCcutter()==null && productionDataBean.getTablevaluelist().get(i).getCtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getDmodelName()==null && productionDataBean.getTablevaluelist().get(i).getDseri()==null && productionDataBean.getTablevaluelist().get(i).getDcutter()==null && productionDataBean.getTablevaluelist().get(i).getDtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getEmodelName()==null && productionDataBean.getTablevaluelist().get(i).getEseri()==null && productionDataBean.getTablevaluelist().get(i).getEcutter()==null && productionDataBean.getTablevaluelist().get(i).getEtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getFmodelName()==null && productionDataBean.getTablevaluelist().get(i).getFseri()==null && productionDataBean.getTablevaluelist().get(i).getFcutter()==null && productionDataBean.getTablevaluelist().get(i).getFtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getGmodelName()==null && productionDataBean.getTablevaluelist().get(i).getGseri()==null && productionDataBean.getTablevaluelist().get(i).getGcutter()==null && productionDataBean.getTablevaluelist().get(i).getGtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getHmodelName()==null && productionDataBean.getTablevaluelist().get(i).getHseri()==null && productionDataBean.getTablevaluelist().get(i).getHcutter()==null && productionDataBean.getTablevaluelist().get(i).getHtanggal()==null){
					count=count+1;
				}
			}
			else if(productionDataBean.getTableColumn().equalsIgnoreCase("9")){
				if(productionDataBean.getTablevaluelist().get(i).getAmodelName()==null && productionDataBean.getTablevaluelist().get(i).getAseri()==null && productionDataBean.getTablevaluelist().get(i).getAcutter()==null && productionDataBean.getTablevaluelist().get(i).getAtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getBmodelName()==null && productionDataBean.getTablevaluelist().get(i).getBseri()==null && productionDataBean.getTablevaluelist().get(i).getBcutter()==null && productionDataBean.getTablevaluelist().get(i).getBtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getCmodelName()==null && productionDataBean.getTablevaluelist().get(i).getCseri()==null && productionDataBean.getTablevaluelist().get(i).getCcutter()==null && productionDataBean.getTablevaluelist().get(i).getCtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getDmodelName()==null && productionDataBean.getTablevaluelist().get(i).getDseri()==null && productionDataBean.getTablevaluelist().get(i).getDcutter()==null && productionDataBean.getTablevaluelist().get(i).getDtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getEmodelName()==null && productionDataBean.getTablevaluelist().get(i).getEseri()==null && productionDataBean.getTablevaluelist().get(i).getEcutter()==null && productionDataBean.getTablevaluelist().get(i).getEtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getFmodelName()==null && productionDataBean.getTablevaluelist().get(i).getFseri()==null && productionDataBean.getTablevaluelist().get(i).getFcutter()==null && productionDataBean.getTablevaluelist().get(i).getFtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getGmodelName()==null && productionDataBean.getTablevaluelist().get(i).getGseri()==null && productionDataBean.getTablevaluelist().get(i).getGcutter()==null && productionDataBean.getTablevaluelist().get(i).getGtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getHmodelName()==null && productionDataBean.getTablevaluelist().get(i).getHseri()==null && productionDataBean.getTablevaluelist().get(i).getHcutter()==null && productionDataBean.getTablevaluelist().get(i).getHtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getImodelName()==null && productionDataBean.getTablevaluelist().get(i).getIseri()==null && productionDataBean.getTablevaluelist().get(i).getIcutter()==null && productionDataBean.getTablevaluelist().get(i).getItanggal()==null){
					count=count+1;
				}
			}
			else if(productionDataBean.getTableColumn().equalsIgnoreCase("10")){
				if(productionDataBean.getTablevaluelist().get(i).getAmodelName()==null && productionDataBean.getTablevaluelist().get(i).getAseri()==null && productionDataBean.getTablevaluelist().get(i).getAcutter()==null && productionDataBean.getTablevaluelist().get(i).getAtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getBmodelName()==null && productionDataBean.getTablevaluelist().get(i).getBseri()==null && productionDataBean.getTablevaluelist().get(i).getBcutter()==null && productionDataBean.getTablevaluelist().get(i).getBtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getCmodelName()==null && productionDataBean.getTablevaluelist().get(i).getCseri()==null && productionDataBean.getTablevaluelist().get(i).getCcutter()==null && productionDataBean.getTablevaluelist().get(i).getCtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getDmodelName()==null && productionDataBean.getTablevaluelist().get(i).getDseri()==null && productionDataBean.getTablevaluelist().get(i).getDcutter()==null && productionDataBean.getTablevaluelist().get(i).getDtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getEmodelName()==null && productionDataBean.getTablevaluelist().get(i).getEseri()==null && productionDataBean.getTablevaluelist().get(i).getEcutter()==null && productionDataBean.getTablevaluelist().get(i).getEtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getFmodelName()==null && productionDataBean.getTablevaluelist().get(i).getFseri()==null && productionDataBean.getTablevaluelist().get(i).getFcutter()==null && productionDataBean.getTablevaluelist().get(i).getFtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getGmodelName()==null && productionDataBean.getTablevaluelist().get(i).getGseri()==null && productionDataBean.getTablevaluelist().get(i).getGcutter()==null && productionDataBean.getTablevaluelist().get(i).getGtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getHmodelName()==null && productionDataBean.getTablevaluelist().get(i).getHseri()==null && productionDataBean.getTablevaluelist().get(i).getHcutter()==null && productionDataBean.getTablevaluelist().get(i).getHtanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getImodelName()==null && productionDataBean.getTablevaluelist().get(i).getIseri()==null && productionDataBean.getTablevaluelist().get(i).getIcutter()==null && productionDataBean.getTablevaluelist().get(i).getItanggal()==null){
					count=count+1;
				}
				if(productionDataBean.getTablevaluelist().get(i).getJmodelName()==null && productionDataBean.getTablevaluelist().get(i).getJseri()==null && productionDataBean.getTablevaluelist().get(i).getJcutter()==null && productionDataBean.getTablevaluelist().get(i).getJtanggal()==null){
					count=count+1;
				}
			}
		}
		table.setEmpty(String.valueOf(count));
		int filled=(Integer.parseInt(productionDataBean.getTableColumn())*Integer.parseInt(productionDataBean.getTableRow()))-count;
		table.setFilled(String.valueOf(filled));
		entityManager.persist(table);
		
		//INSERT TABLEDETAIL
		for (int i = 0; i < productionDataBean.getTablevaluelist().size(); i++) {
			TableDetail tabledetail=new TableDetail();
			tabledetail.setAModel(productionDataBean.getTablevaluelist().get(i).getAmodelName());
			tabledetail.setASeri(productionDataBean.getTablevaluelist().get(i).getAseri());
			tabledetail.setACutter(productionDataBean.getTablevaluelist().get(i).getAcutter());
			tabledetail.setATanggal(productionDataBean.getTablevaluelist().get(i).getAtanggal());
			
			tabledetail.setBModel(productionDataBean.getTablevaluelist().get(i).getBmodelName());
			tabledetail.setBSeri(productionDataBean.getTablevaluelist().get(i).getBseri());
			tabledetail.setBCutter(productionDataBean.getTablevaluelist().get(i).getBcutter());
			tabledetail.setBTanggal(productionDataBean.getTablevaluelist().get(i).getBtanggal());
			
			tabledetail.setCModel(productionDataBean.getTablevaluelist().get(i).getCmodelName());
			tabledetail.setCSeri(productionDataBean.getTablevaluelist().get(i).getCseri());
			tabledetail.setCCutter(productionDataBean.getTablevaluelist().get(i).getCcutter());
			tabledetail.setCTanggal(productionDataBean.getTablevaluelist().get(i).getCtanggal());
			
			tabledetail.setDModel(productionDataBean.getTablevaluelist().get(i).getDmodelName());
			tabledetail.setDSeri(productionDataBean.getTablevaluelist().get(i).getDseri());
			tabledetail.setDCutter(productionDataBean.getTablevaluelist().get(i).getDcutter());
			tabledetail.setDTanggal(productionDataBean.getTablevaluelist().get(i).getDtanggal());
			
			tabledetail.setEModel(productionDataBean.getTablevaluelist().get(i).getEmodelName());
			tabledetail.setESeri(productionDataBean.getTablevaluelist().get(i).getEseri());
			tabledetail.setECutter(productionDataBean.getTablevaluelist().get(i).getEcutter());
			tabledetail.setETanggal(productionDataBean.getTablevaluelist().get(i).getEtanggal());
			
			tabledetail.setFModel(productionDataBean.getTablevaluelist().get(i).getFmodelName());
			tabledetail.setFSeri(productionDataBean.getTablevaluelist().get(i).getFseri());
			tabledetail.setFCutter(productionDataBean.getTablevaluelist().get(i).getFcutter());
			tabledetail.setFTanggal(productionDataBean.getTablevaluelist().get(i).getFtanggal());
			
			tabledetail.setGModel(productionDataBean.getTablevaluelist().get(i).getGmodelName());
			tabledetail.setGSeri(productionDataBean.getTablevaluelist().get(i).getGseri());
			tabledetail.setGCutter(productionDataBean.getTablevaluelist().get(i).getGcutter());
			tabledetail.setGTanggal(productionDataBean.getTablevaluelist().get(i).getGtanggal());
			
			tabledetail.setHModel(productionDataBean.getTablevaluelist().get(i).getHmodelName());
			tabledetail.setHSeri(productionDataBean.getTablevaluelist().get(i).getHseri());
			tabledetail.setHCutter(productionDataBean.getTablevaluelist().get(i).getHcutter());
			tabledetail.setHTanggal(productionDataBean.getTablevaluelist().get(i).getHtanggal());
			
			tabledetail.setIModel(productionDataBean.getTablevaluelist().get(i).getImodelName());
			tabledetail.setISeri(productionDataBean.getTablevaluelist().get(i).getIseri());
			tabledetail.setICutter(productionDataBean.getTablevaluelist().get(i).getIcutter());
			tabledetail.setITanggal(productionDataBean.getTablevaluelist().get(i).getItanggal());
			
			tabledetail.setJModel(productionDataBean.getTablevaluelist().get(i).getJmodelName());
			tabledetail.setJSeri(productionDataBean.getTablevaluelist().get(i).getJseri());
			tabledetail.setJCutter(productionDataBean.getTablevaluelist().get(i).getJcutter());
			tabledetail.setJTanggal(productionDataBean.getTablevaluelist().get(i).getJtanggal());
			
			q=null;
			q=entityManager.createQuery("from Newtable where tableNo=? and status='ACTIVE' ");
			q.setParameter(1, productionDataBean.getTableNo());
			List<Newtable> newtablelist=(List<Newtable>)q.getResultList();
			System.out.println("newtablelist size "+newtablelist.size());
			if(newtablelist.size()>0){
				int id=newtablelist.get(0).getTableId();
				tabledetail.setNewtable(entityManager.find(Newtable.class, id));
			}
			tabledetail.setStatus("INSERT");
			entityManager.persist(tabledetail);
			entityManager.flush();
			entityManager.clear();
			
			
		}
		
		status="Success";
					
	}catch(Exception e){
		e.printStackTrace();
	}
		return status;
	}


	@Override
	public List<ProductionDataBean> gettableview(ProductionDataBean productionDataBean) {
		System.out.println("------gettableview---------");
		Query q=null;
		BigDecimal rowcolumn=BigDecimal.valueOf(0);
		List<ProductionDataBean> tablelist=new ArrayList<ProductionDataBean>();
		try{
			q=entityManager.createQuery("from Newtable where status='ACTIVE' ");
			List<Newtable> newtablelist=(List<Newtable>)q.getResultList();
			System.out.println("newtablelist size "+newtablelist.size());
			if(newtablelist.size()>0){
				for (int i = 0; i < newtablelist.size(); i++) {
					ProductionDataBean prd=new ProductionDataBean();
					prd.setSerialNo(""+(i+1));
					prd.setTableid(String.valueOf(newtablelist.get(i).getTableId()));
					prd.setTableName(newtablelist.get(i).getTableName());
					prd.setTableNo(newtablelist.get(i).getTableNo());
					rowcolumn=new BigDecimal(newtablelist.get(i).getTablecolumn()).multiply(new BigDecimal(newtablelist.get(i).getTablerow()));
					prd.setRowcolumn(rowcolumn.toString());
					prd.setEmptycell(newtablelist.get(i).getEmpty());
					prd.setFilled(newtablelist.get(i).getFilled());
					tablelist.add(prd);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tablelist;
	}


	@Transactional(value="transactionManager")
	public String addhistory(ProductionDataBean productionDataBean) {
		String status="";
		try{
			System.out.println("--------addhistory--------");
			if(productionDataBean.getTableviewlist().size()>0){
				for (int i = 0; i < productionDataBean.getTableviewlist().size(); i++) {
					if(productionDataBean.getTableviewlist().get(i).isCheckBox()==true){
						int id=Integer.parseInt(productionDataBean.getTableviewlist().get(i).getTableid());
						Newtable newtable=entityManager.find(Newtable.class, id);
						newtable.setStatus("HISTORY");
						entityManager.merge(newtable);
						entityManager.flush();
						entityManager.clear();
						status="success";
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}


	@Override
	public  List<ProductionDataBean> gettableHistory(ProductionDataBean productionDataBean) {
		Query q=null;
		BigDecimal rowcolumn=BigDecimal.valueOf(0);
		List<ProductionDataBean> tablelist=new ArrayList<ProductionDataBean>();
		try{
			q=entityManager.createQuery("from Newtable where status='HISTORY' ");
			List<Newtable> newtablelist=(List<Newtable>)q.getResultList();
			System.out.println("newtablelist size "+newtablelist.size());
			if(newtablelist.size()>0){
				for (int i = 0; i < newtablelist.size(); i++) {
					ProductionDataBean prd=new ProductionDataBean();
					prd.setSerialNo(""+(i+1));
					prd.setTableid(""+newtablelist.get(i).getTableId());
					prd.setTableName(newtablelist.get(i).getTableName());
					prd.setTableNo(newtablelist.get(i).getTableNo());
					rowcolumn=new BigDecimal(newtablelist.get(i).getTablecolumn()).multiply(new BigDecimal(newtablelist.get(i).getTablerow()));
					prd.setRowcolumn(rowcolumn.toString());
					prd.setEmptycell(newtablelist.get(i).getEmpty());
					prd.setFilled(newtablelist.get(i).getFilled());
					tablelist.add(prd);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tablelist;
	}


	@Override
	public String checkTableno(ProductionDataBean productionDataBean) {
		String status="";
		Query q=null;
		try{
			q=entityManager.createQuery("from Newtable where tableNo=? and (status='ACTIVE' or status='HISTORY') ");
			q.setParameter(1, productionDataBean.getTableNo());
			List<Newtable> newtablelist=(List<Newtable>)q.getResultList();
			System.out.println("newtablelist size "+newtablelist.size());
			if(newtablelist.size()>0){
				status="EXIST";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}	
	
	
	
	
}