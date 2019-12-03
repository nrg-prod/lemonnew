package com.nrg.lemon.bo;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.nrg.lemon.dao.LemonDao;
import com.nrg.lemon.domain.CustomerDatabean;
import com.nrg.lemon.domain.EmployeeDataBean;
import com.nrg.lemon.domain.FinanceDataBean;
import com.nrg.lemon.domain.LoginDataBean;
import com.nrg.lemon.domain.PayrollDataBean;
import com.nrg.lemon.domain.ProductionDataBean;
import com.nrg.lemon.domain.ReturnDataBean;
import com.nrg.lemon.domain.StockDataBean;
import com.nrg.lemon.domain.SupplyDataBean;
import com.nrg.lemon.shared.Attendance;
import com.nrg.lemon.shared.Customer;
import com.nrg.lemon.shared.Employee;
import com.nrg.lemon.shared.Subproduct;
import com.nrg.lemon.shared.UserProduct;


@Controller("bo")
public   class LemonBoImpl implements LemonBo
{
	@Autowired
	LemonDao dao;

	@Override
	public String login(LoginDataBean loginDataBean) {
		return dao.login(loginDataBean);
	}

	@Override
	public List<UserProduct> loadmenu(LoginDataBean loginDataBean) {
		return dao.loadmenu(loginDataBean);
	}

	@Override
	public List<Subproduct> submenus(int product_ID, String productCode) {
		return dao.submenus(product_ID,productCode);
	}

	@Override
	public String insertBranch(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		return dao.insertBranch(employeeDataBean,personID,clientID);
	}

	@Override
	public List<EmployeeDataBean> getbranchInfo(String personID, String clientID) {
		return dao.getbranchInfo(personID,clientID);
	}

	@Override
	public String updateBranch(String personID, String clientID,
			EmployeeDataBean employeeDataBean) {
		return dao.updateBranch(personID,clientID,employeeDataBean);
	}

	@Override
	public String deleteBranch(String personID, String clientID,
			EmployeeDataBean employeeDataBean) {
		return dao.deleteBranch(personID,clientID,employeeDataBean);
	}

	@Override
	public List<String> branchlist(String personID, String clientID) {
		return dao.branchlist(personID,clientID);
	}

	@Override
	public String inserteEmployee(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		return dao.insertEmployee(employeeDataBean,personID,clientID);
	}

	@Override
	public List<EmployeeDataBean> getemployeeInfo(String personID,
			String clientID) {
		return dao.getemployeeInfo(personID,clientID);
	}

	@Override
	public List<Employee> employeeDetails(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		return dao.emolpoyeeDetails(employeeDataBean,personID,clientID);
	}

	@Override
	public String updateEmployee(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		return dao.updateEmployee(employeeDataBean,personID,clientID);
	}

	@Override
	public String deleteEmployee(String personID, String clientID,
			EmployeeDataBean employeeDataBean) {
		return dao.deleteEmployee(personID,clientID,employeeDataBean);
	}

	@Override
	public List<EmployeeDataBean> employeeSearch(
			EmployeeDataBean employeeDataBean, String personID, String clientID) {
		return dao.employeeSearch(employeeDataBean,personID,clientID);
	}

	@Override
	public String insertAnnouncement(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		return dao.insertAnnouncement(employeeDataBean,personID,clientID);
	}

	@Override
	public List<EmployeeDataBean> announcementInfo(String personID,
			String clientID) {
		return dao.announcementInfo(personID,clientID);
	}

	@Override
	public String updateAnnouncement(String personID, String clientID,
			EmployeeDataBean employeeDataBean) {
		return dao.updateAnnouncement(personID,clientID,employeeDataBean);
	}

	@Override
	public String deleteAnnouncement(String personID, String clientID,
			EmployeeDataBean employeeDataBean) {
		return dao.deleteAnnouncement(personID,clientID,employeeDataBean);
	}

	@Override
	public List<EmployeeDataBean> attendanceSearch(
			EmployeeDataBean employeeDataBean, String personID, String clientID) {
		return dao.attendanceSearch(employeeDataBean,personID,clientID);
	}

	@Override
	public String saveAttendance(String personID, String clientID,
			EmployeeDataBean employeeDataBean,
			List<EmployeeDataBean> attendanceList) {
		return dao.saveAttendance(personID,clientID,employeeDataBean,attendanceList);
	}

	@Override
	public List<Attendance> checkAttendance(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		return dao.checkAttendance(employeeDataBean,personID,clientID);
	}

	@Override
	public List<EmployeeDataBean> attendanceView(String personID,
			String clientID, EmployeeDataBean employeeDataBean) {
		return dao.attendanceView(personID,clientID,employeeDataBean);
	}

	@Override
	public String attendanceUpdate(EmployeeDataBean employeeDataBean,
			String personID, String clientID) {
		return dao.attendanceUpdate(employeeDataBean,personID,clientID);
	}
	
	@Override
	public String insertsupplier(String personID, String clientID, SupplyDataBean supplyDataBean) {
		return dao.insertsupplier(personID,clientID,supplyDataBean);
	}

	@Override
	public List<SupplyDataBean> getsupplierlist(String personID) {
		return dao.getsupplierlist(personID);
	}

	@Override
	public String deletesupplier(String personID, SupplyDataBean supplyDataBean) {
		return dao.deletesupplier(personID,supplyDataBean);
	}

	@Override
	public String editsupplier(String personID, SupplyDataBean supplyDataBean) {
		return dao.editsupplier(personID,supplyDataBean);
	}

	@Override
	public String updatesupplier(String personID, SupplyDataBean supplyDataBean) {
		return dao.updatesupplier(personID,supplyDataBean);
	}

	@Override
	public String purchaseFabricInvoice(String personID,SupplyDataBean supplyDataBean) {
		return dao.purchaseFabricInvoice(personID,supplyDataBean);
	}

	@Override
	public List<String> getsuppliernamelist(String personID,String str) {
		return dao.getsuppliernamelist(personID,str);
	}

	@Override
	public String insertvendor(String personID, String clientID,ProductionDataBean productionDataBean) {
		return dao.insertvendor(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> getvendorlist(String personID) {
		return dao.getvendorlist(personID);
	}

	@Override
	public String editVendor(String personID,ProductionDataBean productionDataBean) {
		return dao.editVendor(personID,productionDataBean);
	}

	@Override
	public String updatevendor(String personID,ProductionDataBean productionDataBean) {
		return dao.updatevendor(personID,productionDataBean);
	}

	@Override
	public String deletevendor(String personID,ProductionDataBean productionDataBean) {
		return dao.deletevendor(personID,productionDataBean);
	}

	@Override
	public String insertcategory(String personID, String clientID,ProductionDataBean productionDataBean) {
		return dao.insertcategory(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> getcategorylist(String personID) {
		return dao.getcategorylist(personID);
	}

	@Override
	public String editcategory(String personID,ProductionDataBean productionDataBean) {
		return dao.editcategory(personID,productionDataBean);
	}

	@Override
	public String deletecategory(String personID,ProductionDataBean productionDataBean) {
		return dao.deletecategory(personID,productionDataBean);
	}

	@Override
	public List<String> getcategorynamelist(String personID) {
		return dao.getcategorynamelist(personID);
	}

	@Override
	public String insertmodel(String personID, String clientID,ProductionDataBean productionDataBean) {
		return dao.insertmodel(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> getmodelList(String personID) {
		return dao.getmodelList(personID);
	}

	@Override
	public String deletemodel(String personID,ProductionDataBean productionDataBean) {
		return dao.deletemodel(personID,productionDataBean);
	}

	@Override
	public String editModel(String personID,ProductionDataBean productionDataBean) {
		return dao.editModel(personID,productionDataBean);
	}

	@Override
	public String updatemodel(String personID,ProductionDataBean productionDataBean) {
		return dao.updatemodel(personID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> getparticularmodelList(String personID,String category) {
		return dao.getparticularmodelList(personID,category);
	}

	@Override
	public String savepurchasefabric(String personID,SupplyDataBean supplyDataBean) {
		return dao.savepurchasefabric(personID,supplyDataBean);
	}

	@Override
	public List<SupplyDataBean> getfabricpurchaseList(String personID) {
		return dao.getfabricpurchaseList(personID);
	}

	@Override
	public String viewpurchasefabric(String personID,SupplyDataBean supplyDataBean) {
		return dao.viewpurchasefabric(personID,supplyDataBean);
	}

	@Override
	public String updatepurchasefabric(String personID,SupplyDataBean supplyDataBean) {
		return dao.updatepurchasefabric(personID,supplyDataBean);
	}

	@Override
	public String deletepurchasefabric(String personID,SupplyDataBean supplyDataBean) {
		return dao.deletepurchasefabric(personID,supplyDataBean);
	}

	@Override
	public List<SupplyDataBean> getfabricpurchaseList(String personID,String str) {
		return dao.getfabricpurchaseList(personID,str);
	}

	@Override
	public String insertitems(String personID, String clientID,SupplyDataBean supplyDataBean) {
		return dao.insertitems(personID,clientID,supplyDataBean);
	}

	@Override
	public List<SupplyDataBean> getitems(String personID, String clientID,String itemStatus) {
		return dao.getitems(personID,clientID,itemStatus);
	}

	@Override
	public String viewitems(String personID, String clientID,SupplyDataBean supplyDataBean) {
		return dao.viewitems(personID,clientID,supplyDataBean);
	}

	@Override
	public String updateitems(String personID, SupplyDataBean supplyDataBean) {
		return dao.updateitems(personID,supplyDataBean);
	}

	@Override
	public String deleteitems(String personID, SupplyDataBean supplyDataBean) {
		return dao.deleteitems(personID,supplyDataBean);
	}

	@Override
	public String generateItemsInvoice(String personID,SupplyDataBean supplyDataBean) {
		return dao.generateItemsInvoice(personID,supplyDataBean);
	}

	@Override
	public List<String> getitemNameList(String personID,SupplyDataBean supplyDataBean) {
		return dao.getitemNameList(personID,supplyDataBean);
	}

	@Override
	public void getItemPrice(String personID, SupplyDataBean supplyDataBean) {
		dao.getItemPrice(personID,supplyDataBean);
	}

	@Override
	public String savePurchaseSalesItems(String personID, String clientID,SupplyDataBean supplyDataBean) {
		return dao.savePurchaseSalesItems(personID,clientID,supplyDataBean);
	}

	@Override
	public List<String> getvendornamelist(String personID,String str) {
		return dao.getvendornamelist(personID,str);
	}

	@Override
	public List<String> getsalesitemNameList(String personID,SupplyDataBean supplyDataBean) {
		return dao.getsalesitemNameList(personID,supplyDataBean);
	}

	@Override
	public List<SupplyDataBean> getitemtableview(String personID,SupplyDataBean supplyDataBean) {
		return dao.getitemtableview(personID,supplyDataBean);
	}
	
	@Override
	public List<String> getcutterList(String personID, String clientID) {
		return dao.getcutterList(personID,clientID);
	}

	@Override
	public List<String> modelList(String personID, String clientID,ProductionDataBean productionDataBean) {
		return dao.modelList(personID,clientID,productionDataBean);
	}

	@Override
	public String generateiInvoiceno(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.generateiInvoiceno(personID,clientID,productionDataBean);
	}

	@Override
	public String modelChange(ProductionDataBean productionDataBean,
			String personID, String clientID) {
		return dao.modelChange(productionDataBean,personID,clientID);
	}

	@Override
	public String insertCutterorder(String personID, String clientID,ProductionDataBean productionDataBean,List<ProductionDataBean> cutterorderlist) {
		return dao.insertCutterorder(personID,clientID,productionDataBean,cutterorderlist);
	}

	@Override
	public List<ProductionDataBean> cutterPurchasedetails(String personID,String clientID,ProductionDataBean productionDataBean) {
		return dao.cutterPurchasedetails(personID,clientID,productionDataBean);
	}

	@Override
	public String getcutterOrderview(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.getcutterOrderview(personID,clientID,productionDataBean);
	}

	@Override
	public String updateCutterorder(String personID, String clientID,ProductionDataBean productionDataBean) {
		return dao.updateCutterorder(personID,clientID,productionDataBean);
	}

	@Override
	public String deleteCutterorder(String personID, String clientID,ProductionDataBean productionDataBean) {
		return dao.deleteCutterorder(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> cutterorderSearch(String personID,
			String clientID, ProductionDataBean productionDataBean) {
		return dao.cutterorderSearch(personID,clientID,productionDataBean);
	}

	@Override
	public List<String> getCmt(String personID, String clientID) {
		return dao.getCmt(personID,clientID);
	}

	@Override
	public String getCmtinvoice(String personID, String clientID) {
		return dao.getCmtinvoice(personID,clientID);
	}

	@Override
	public String insertCmtorder(String personID, String clientID,
			ProductionDataBean productionDataBean,List<ProductionDataBean> cmtorderlist) {
		return dao.insertCmtorder(personID,clientID,productionDataBean,cmtorderlist);
	}

	@Override
	public List<ProductionDataBean> getcmtorderView(String personID,
			String clientID, ProductionDataBean productionDataBean) {
		return dao.getcmtorderView(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> getcmtSearch(String personID,
			String clientID, ProductionDataBean productionDataBean) {
		return dao.getcmtSearch(personID,clientID,productionDataBean);
	}

	@Override
	public void getcmtpurchesView(String personID, String clientID,
			ProductionDataBean productionDataBean) {
			dao.getcmtpurchesView(personID,clientID,productionDataBean);
	}

	@Override
	public String cmtorderUpdate(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.cmtorderUpdate(personID,clientID,productionDataBean);
	}

	@Override
	public String cmtOrderdelete(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.cmtOrderdelete(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> getcmtHolding(String personID,
			String clientID, ProductionDataBean productionDataBean) {
		return dao.getcmtHolding(personID,clientID,productionDataBean);
	}

	@Override
	public List<String> getPrinterlist(String personID, String clientID) {
		return dao.getPrinterlist(personID,clientID);
	}

	@Override
	public String printerInvoiceNum(String personID,
			ProductionDataBean productionDataBean) {
		return dao.printerInvoiceNum(personID,productionDataBean);
	}

	@Override
	public String printerreceiveInvoiceNum(String personID,
			ProductionDataBean productionDataBean) {
		return dao.printerreceiveInvoiceNum(personID,productionDataBean);
	}

	@Override
	public String insertPrinterRecOrder(String personID, String clientID,
			ProductionDataBean productionDataBean,
			List<ProductionDataBean> printerorderlist) {
		return dao.insertPrinterRecOrder(personID,clientID,productionDataBean,printerorderlist);
	}

	@Override
	public String insertPrinterOrder(String personID, String clientID,
			ProductionDataBean productionDataBean,
			List<ProductionDataBean> printerorderlist) {
		return dao.insertPrinterOrder(personID,clientID,productionDataBean,printerorderlist);
	}

	@Override
	public List<ProductionDataBean> getprinterView(String clientID,
			String personID) {
		return dao.getprinterView(clientID,personID);
	}

	@Override
	public void getprinterpurchaseView(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		dao.getprinterpurchaseView(personID,clientID,productionDataBean);
		
	}

	@Override
	public String printerpurchaseUpdate(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.printerpurchaseUpdate(personID,clientID,productionDataBean);
	}

	@Override
	public String printerpurchaseDelete(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.printerpurchaseDelete(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> getprinterHolding(String personID,
			String clientID, ProductionDataBean productionDataBean) {
		return dao.getprinterHolding(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> getprinterView(String personID,
			String clientID, ProductionDataBean productionDataBean) {
		return dao.getprinterView(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> getprinterSearch(String personID,
			String clientID, ProductionDataBean productionDataBean) {
		return dao.getprinterSearch(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> vendorDetails(String personID,
			String clientID, ProductionDataBean productionDataBean) {
		return dao.vendorDetails(personID,clientID,productionDataBean);
	}
	
	@Override
	public String viewpurchasesaleitems(String personID,SupplyDataBean supplyDataBean) {
		return dao.viewpurchasesaleitems(personID,supplyDataBean);
	}

	@Override
	public String updatepurchasesalesitems(String personID,String clientID,SupplyDataBean supplyDataBean) {
		return dao.updatepurchasesalesitems(personID,clientID,supplyDataBean);
	}

	@Override
	public String deletepursalesitems(String personID,SupplyDataBean supplyDataBean) {
		return dao.deletepursalesitems(personID,supplyDataBean);
	}

	@Override
	public List<SupplyDataBean> getitemtableviews(String personID, SupplyDataBean supplyDataBean) {
		return dao.getitemtableviews(personID,supplyDataBean);
	}
	
	public String getPaymentDetails(String personID, SupplyDataBean supplyDataBean){
		return dao.getPaymentDetails(personID,supplyDataBean);
	}

	public String generateiInvoiceno(String personID,SupplyDataBean supplyDataBean){
		return dao.generateiInvoiceno(personID,supplyDataBean);
	}
	
	public String payAmount(String personID, SupplyDataBean supplyDataBean){
		return dao.payAmount(personID,supplyDataBean);
	}
	
	public String addStock(String personID, SupplyDataBean supplyDataBean){
		return dao.addStock(personID,supplyDataBean);
	}
	
	public String insertPayment(String personID, SupplyDataBean supplyDataBean){
		return dao.insertPayment(personID,supplyDataBean);
	}

	public String purchaseFabricPayment(String personID,
			SupplyDataBean supplyDataBean){
		return dao.purchaseFabricPayment(personID,supplyDataBean);
	}

	public String fabricgenerateInvoice(String personID,
			SupplyDataBean supplyDataBean){
		return dao.fabricgenerateInvoice(personID,supplyDataBean);
	}
	
	@Override
	public String usernamechanged(LoginDataBean loginDataBean) {
		return dao.usernamechanged(loginDataBean);
	}

	@Override
	public String passwordchange(LoginDataBean loginDataBean) {
		return dao.passwordchange(loginDataBean);
	}
	
	public String warehouseSave(String personID, StockDataBean stockDataBean){
		return dao.warehouseSave(personID,stockDataBean);
	}
	
	public String getWarehouseDetails(String personID, StockDataBean stockDataBean){
		return dao.getWarehouseDetails(personID,stockDataBean);
	}
	
	public String updateWarehouse(StockDataBean stockDataBean){
		return dao.updateWarehouse(stockDataBean);
	}
	
	public String getManualStockOutetails(String personID,StockDataBean stockDataBean){
		return dao.getManualStockOutetails(personID,stockDataBean);
	}
	
	public String getModelDetails(String personID, StockDataBean stockDataBean){
		return dao.getModelDetails(personID,stockDataBean);
	}
	
	public String saveManualStockOut(String personID, StockDataBean stockDataBean){
		return dao.saveManualStockOut(personID,stockDataBean);
	}
	
	public String getManualStockDetails(String personID,StockDataBean stockDataBean){
		return dao.getManualStockDetails(personID,stockDataBean);
	}
	
	public String getmanualStckView(String personID, StockDataBean stockDataBean){
		return dao.getmanualStckView(personID,stockDataBean);
	}
	
	public String updateManualStockOut(String personID,StockDataBean stockDataBean){
		return dao.updateManualStockOut(personID,stockDataBean);
	}
	
	public String deleteManualStockOut(String personID,StockDataBean stockDataBean){
		return dao.deleteManualStockOut(personID,stockDataBean);
	}
	
	public List<String> getdebtname(String personID, String clientID,String str) {
		return dao.getdebtname(personID,clientID,str);
	}

	public String saveDebt(String personID, String clientID,FinanceDataBean financeDataBean) {
		return dao.saveDebt(personID,clientID,financeDataBean);
	}

	public List<FinanceDataBean> getdebtList(String personID, String clientID,FinanceDataBean financeDataBean) {
		return dao.getdebtList(personID,clientID,financeDataBean);
	}

	public List<String> getdebtreceivename(String personID, String clientID,String str) {
		return dao.getdebtreceivename(personID,clientID,str);
	}

	public String getdebtnamevaluChange(String personID, String clientID,FinanceDataBean financeDataBean) {
		return dao.getdebtnamevaluChange(personID,clientID,financeDataBean);
	}

	public String getDebtView(String personID, String clientID,FinanceDataBean financeDataBean) {
		return dao.getDebtView(personID,clientID,financeDataBean);
	}
	
	public String debitUpdate(String personID, String clientID,FinanceDataBean financeDataBean) {
		return dao.debitUpdate(personID,clientID,financeDataBean);
	}
	@Override
	public List<String> getmodelName(String clientID) {
		return dao.getmodelName(clientID);
	}

	@Override
	public List<String> printerModellist(String clientID) {
		return dao.printerModellist(clientID);
	}
	
	public List<EmployeeDataBean> getEmployeeReportList(String personID,String clientID) {
		return dao.getEmployeeReportList(personID,clientID);
	}

	@Override
	public List<StockDataBean> getStockDetails(String personID,StockDataBean stockDataBean) {
		return dao.getStockDetails(personID,stockDataBean);
	}
	
	public String getModels(String personID, StockDataBean stockDataBean){
		return dao.getModels(personID,stockDataBean);
	}
	
	public String getProductDetails(String personID, StockDataBean stockDataBean){
		return dao.getProductDetails(personID,stockDataBean);
	}
	
	public List<SupplyDataBean> getsupplyreportview(String personID,SupplyDataBean supplyDataBean) {
		return dao.getsupplyreportview(personID,supplyDataBean);
	}
	
	public String updateStocks(String personID, StockDataBean stockDataBean){
		for (int i = 0; i < stockDataBean.getWarelists().size(); i++) {
			if(!stockDataBean.getWarelists().get(i).getQuantity1().equals("")){
				dao.updateStocks(stockDataBean.getWarelists().get(i).getMnaualStockID(),stockDataBean.getWarelists().get(i).getQuantity1(),stockDataBean.getWarelists().get(i).getMotive());	
			}
		}
		return "";
	}
	
	public List<String> getStaffNames(String personID, String type){
		return dao.getStaffNames(personID,type);		
	}
	
	public void savePenalty(String personID, PayrollDataBean payrollDataBean){
		dao.savePenalty(personID,payrollDataBean);
	}
	
	public List<PayrollDataBean> getPenaltyDetails(String personID){
		return dao.getPenaltyDetails(personID);
	}
	
	public void penaltyUpdate(PayrollDataBean payrollDataBean){
		dao.penaltyUpdate(payrollDataBean);
	}
	
	public List<StockDataBean> getrawstockreport(String personID,StockDataBean stockDataBean) {
		return dao.getrawstockreport(personID,stockDataBean);
	}
	
	public List<PayrollDataBean> getPurchasePayment(String personID,PayrollDataBean payrollDataBean){
		return dao.getPurchasePayment(personID,payrollDataBean);
	}

	public List<FinanceDataBean> getIncomedetails(String personID,FinanceDataBean financeDataBean){
		return dao.getIncomedetails(personID,financeDataBean);
	}
	
	public String getMonthYears(String personID, PayrollDataBean payrollDataBean){
		return dao.getMonthYears(personID,payrollDataBean);
	}
	
	public String getemployeeDetails(String personID,PayrollDataBean payrollDataBean){
		return dao.getemployeeDetails(personID,payrollDataBean);
	}
	
	public String payrollSave(String personID, PayrollDataBean payrollDataBean){
		return dao.payrollSave(personID,payrollDataBean);
	}
	
	public String getEmployeePayrolls(String personID,PayrollDataBean payrollDataBean){
		return dao.getEmployeePayrolls(personID,payrollDataBean);
	}
	
	public String getPayrollDetails(String personID,PayrollDataBean payrollDataBean){
		return dao.getPayrollDetails(personID,payrollDataBean);
	}
	
	public String updatePayroll(PayrollDataBean payrollDataBean){
		return dao.updatePayroll(payrollDataBean);
	}
	
	@Override
	public String getreturnsupply(String personID, ReturnDataBean returnDataBean) {
		return dao.getreturnsupply(personID,returnDataBean);
	}

	@Override
	public String datatable(String personID,ReturnDataBean returnDataBean){
		return dao.datatable(personID,returnDataBean);	
	}

	@Override
	public String save(String personID, ReturnDataBean returnDataBean) {
		return  dao.save(personID,returnDataBean);
	}

	@Override
	public String action(String personID, ReturnDataBean returnDataBean) {		
		return  dao.action(personID,returnDataBean);
	}
	
	public String saveCategory(String personID, String clientID,FinanceDataBean financeDataBean) {
		return dao.saveCategory(personID,clientID,financeDataBean);
	}

	public String transactionsave(String personID, String clientID,FinanceDataBean financeDataBean) {
		return dao.transactionsave(personID,clientID,financeDataBean);
	}

	public List<String> gettransactioncategory(String personID, String clientID) {
		return dao.gettransactioncategory(personID,clientID);
	}

	public List<FinanceDataBean> transactionview(String personID,String clientID, FinanceDataBean financeDataBean) {
		return dao.transactionview(personID,clientID,financeDataBean);
	}
	
	@Override
	public String cutterInvoice(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.cutterInvoice(personID,clientID,productionDataBean);
	}

	@Override
	public String cutterPayment(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.cutterPayment(personID,clientID,productionDataBean);
	}

	@Override
	public String cutterPaymentsave(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.cutterPaymentsave(personID,clientID,productionDataBean);
	}

	@Override
	public List<String> getmodelName(String clientID,String cutterInvoice) {
		return dao.getmodelName(clientID,cutterInvoice);
	}

	@Override
	public List<String> getmodelInfo(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.getmodelInfo(personID,clientID,productionDataBean);
	}

	@Override
	public String getSerivalue(String value, ProductionDataBean productionDataBean) {
		return dao.getSerivalue(value,productionDataBean);
	}

	@Override
	public List<String> printerModellist(String clientID, String invoiceNo) {
		return dao.printerModellist(clientID,invoiceNo);
	}

	@Override
	public List<String> cutterInvoicelist(String clientID) {
		return dao.cutterInvoicelist(clientID);
	}

	@Override
	public List<String> printerInvoicelist(String clientID) {
		return dao.printerInvoicelist(clientID);
	}

	@Override
	public List<String> printerSerilist(String clientID,ProductionDataBean productionDataBean) {
		return dao.printerSerilist(clientID,productionDataBean);
	}

	@Override
	public String printerInvoice(String clientID, String invoiceNo) {
		return dao.printerInvoice(clientID,invoiceNo);
	}

	@Override
	public void printerPayment(String clientID,ProductionDataBean productionDataBean) {
		dao.printerPayment(clientID,productionDataBean);
	}

	@Override
	public String printerpaymentsave(String clientID,ProductionDataBean productionDataBean) {
		return dao.printerpaymentsave(clientID,productionDataBean);
	}

	@Override
	public List<String> cmtInvoiceList(String clientID) {
		return dao.cmtInvoiceList(clientID);
	}

	@Override
	public List<String> printerReceivemodel(String clientID, String Invoiceno) {
		return dao.printerReceivemodel(clientID,Invoiceno);
	}

	@Override
	public List<String> getmodelData(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.getmodelData(personID,clientID,productionDataBean);
	}

	@Override
	public String getSerivaluechange(String value,ProductionDataBean productionDataBean) {
		return dao.getSerivaluechange(value,productionDataBean);
	}

	@Override
	public List<String> cmtOrderinvoice(String clientID) {
		return dao.cmtOrderinvoice(clientID);
	}

	@Override
	public String getCmtReceiveinvoice(String personID, String clientID) {
		return dao.getCmtReceiveinvoice(personID,clientID);
	}

	@Override
	public List<String> cmtOrderdemodel(String clientID, String Invoiceno) {
		return dao.cmtOrderdemodel(clientID,Invoiceno);
	}

	@Override
	public List<String> cmtSerilist(String clientID,ProductionDataBean productionDataBean) {
		return dao.cmtSerilist(clientID,productionDataBean);
	}

	@Override
	public String cmtreceiveSerichange(String value,ProductionDataBean productionDataBean) {
		return dao.cmtreceiveSerichange(value,productionDataBean);
	}

	@Override
	public String insertCmtreceive(String personID, String clientID,
			ProductionDataBean productionDataBean,List<ProductionDataBean> cmtReceivelist) {
		return dao.insertCmtreceive(personID,clientID,productionDataBean,cmtReceivelist);
	}
	
	@Override
	public List<String> getcmtModelName(String personID) {
		return dao.getcmtModelName(personID);
	}
	
	@Override
	public List<StockDataBean> getcmtTable(String personID,StockDataBean stockDataBean) {
		return dao.getcmtTable(personID,stockDataBean); 
	}

	@Override
	public List<String> getseriNo(String valuechange, String personID) {
		return dao.getseriNo(valuechange,personID);
	}

	@Override
	public List<StockDataBean> getcmtSave(StockDataBean stockDataBean,String personID) {
			return dao.getcmtSave(stockDataBean,personID);
	}

	@Override
	public List<ProductionDataBean> cmtstockDetails(String clientID,
			ProductionDataBean productionDataBean) {
		return dao.cmtstockDetails(clientID,productionDataBean);
	}
	@Override
	public List<StockDataBean> getmtReturn(StockDataBean stockDataBean,String personID) {
				return dao.getmtReturn(stockDataBean,personID);
	}
	public void getPrinterPayments(String personID,ProductionDataBean productionDataBean){
		dao.getPrinterPayments(personID,productionDataBean);
	}
	
	public void getPayrollReports(String personID,PayrollDataBean payrollDataBean){
		dao.getPayrollReports(personID,payrollDataBean);
	}
	
	public String supplyview(String personID, ReturnDataBean returnDataBean){
		return dao.supplyview(personID,returnDataBean);
	}
		
	public void viewtransaction(String personID, String clientID, FinanceDataBean financeDataBean) {
		dao.viewtransaction(personID,clientID,financeDataBean);
	}

	public String updatetransaction(String personID, String clientID, FinanceDataBean financeDataBean) {
		return dao.updatetransaction(personID,clientID,financeDataBean);
	}

	public String deletetransaction(String personID, String clientID, FinanceDataBean financeDataBean) {
		return dao.deletetransaction(personID,clientID,financeDataBean);
	}

	@Override
	public String cmtstockSave(String personID,String clientID,ProductionDataBean productionDataBean,List<ProductionDataBean> stockList) {
		return dao.cmtstockSave(personID,clientID,productionDataBean,stockList);
	}

	@Override
	public String cmtInvoice(String clientID, String invoiceNo) {
		return dao.cmtInvoice(clientID,invoiceNo);
	}

	@Override
	public void cmtPayment(String clientID,ProductionDataBean productionDataBean) {
		dao.cmtPayment(clientID,productionDataBean);
	}
	@Override
	public List<StockDataBean> getmtReturn1(StockDataBean stockDataBean,String personID) {
			return dao.getmtReturn1(stockDataBean,personID); 
	}

	@Override
	public void getcmtDelete(StockDataBean stockDataBean, String personID) {
		dao.getcmtDelete(stockDataBean,personID);
		
	}

	@Override
	public List<StockDataBean> getedit(StockDataBean stockDataBean,String personID) {
		
		return dao.getedit(stockDataBean,personID);
	}

	@Override
	public String cmtreturnEdit(String personID,
			List<StockDataBean> returnList2, StockDataBean stockDataBean) {
		return dao.cmtreturnEdit(personID,returnList2,stockDataBean);
	}


	@Override
	public String cmtPaymentsave(String clientID,ProductionDataBean productionDataBean) {
		return dao.cmtPaymentsave(clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> productDetails(String personID,String clientID) {
		return dao.productDetails(personID,clientID);
	}

	@Override
	public List<ProductionDataBean> productHistory(String clientID,String personID) {
		return dao.productHistory(clientID,personID);
	}
	
	@Override
	public String update(String personID, ReturnDataBean returnDataBean) {
		
		return dao.update(personID,returnDataBean);
	}
	@Override
	public String updates(String personID, ReturnDataBean returnDataBean) {
		
		return dao.updates(personID,returnDataBean);
	}

	@Override
	public String updatedelete(String personID, ReturnDataBean returnDataBean) {
		
		return dao.updatedelete(personID,returnDataBean);
	}

	@Override
	public List<ProductionDataBean> getproductionData(String personID,String clientID,ProductionDataBean productionDataBean) {
		return dao.getproductionData(personID,clientID,productionDataBean);
	}

	@Override
	public void productionView(String clientID, ProductionDataBean productionDataBean) {
		dao.productionView(clientID,productionDataBean);
	}
	
	public void getOpeningStockProducts(String personID,StockDataBean stockDataBean){
		 dao.getOpeningStockProducts(personID,stockDataBean);
	}
	
	public void getModelSeris(String personID, StockDataBean stockDataBean){
		 dao.getModelSeris(personID,stockDataBean);
	}
	
	public String saveOpeningStock(String personID, StockDataBean stockDataBean){
		 return dao.saveOpeningStock(personID,stockDataBean);
	}
	
	public String getOpeningStockDetails(String personID,StockDataBean stockDataBean){
		return dao.getOpeningStockDetails(personID,stockDataBean);
	}
	
	@Override
	public String transactionwaste(String personID, FinanceDataBean financeDataBean) {
		return dao.transactionwaste(personID,financeDataBean);
	}

	@Override
	public String reportfinance(String personID, FinanceDataBean financeDataBean) {
		
		return dao.reportfinance(personID ,financeDataBean);
	}

	@Override
	public String supply(String personID, ReturnDataBean returnDataBean) {
		return dao.supply(personID, returnDataBean);
	}

	@Override
	public String payment(String personID, String clientID,List<ProductionDataBean> payrollList) {
		return dao.payment(personID,clientID,payrollList);
	}
	
	@Override
	public String updatecustomer(String personID, String clientID,
			CustomerDatabean customerDatabean) {
		return dao.updatecustomer(personID,clientID,customerDatabean);
	}
	@Override
	public String cusDelete(String personID, String clientID,
			CustomerDatabean customerDatabean) {
		return dao.cusDelete(personID,clientID,customerDatabean);
	}
	@Override
	public String register(String personID, String clientID,
			CustomerDatabean customerDatabean) {
		return dao.register(personID,clientID,customerDatabean);
	}
	@Override
	public List<CustomerDatabean> customerView() {
		return dao.customerView();
	}
	@Override
	public List<Customer> editcustomer(String personID, String clientID,
			CustomerDatabean customerDatabean) {
		return dao.editcustomer(personID,clientID,customerDatabean);
	}
	
	public String payPurchases(String personID, PayrollDataBean payrollDataBean) {
		return dao.payPurchases(personID,payrollDataBean);
	}

	@Override
	public List<ProductionDataBean> getcutterData(String personID,String clientID) {
		return dao.getcutterData(personID,clientID);
	}

	@Override
	public String cutterReadysave(String personID, String clientID,List<ProductionDataBean> cutterorderlist) {
		return dao.cutterReadysave(personID,clientID,cutterorderlist);
	}

	@Override
	public List<String> getcutterName(String personID, String clientID) {
		return dao.getcutterName(personID,clientID);
	}

	@Override
	public List<String> getPrinterreadymodel(String personID, String clientID) {
		return dao.getPrinterreadymodel(personID,clientID);
	}

	@Override
	public List<String> getModel(String clientID) {
		return dao.getModel(clientID);
	}

	@Override
	public List<ProductionDataBean> getprinterData(String personID,String clientID) {
		return dao.getprinterData(personID,clientID);
	}

	@Override
	public String printerReadysave(String personID, String clientID,List<ProductionDataBean> printerPurchaselist) {
		return dao.printerReadysave(personID,clientID,printerPurchaselist);
	}

	@Override
	public List<String> cmtreadyModel(String personID, String clientID) {
		return dao.cmtreadyModel(personID,clientID);
	}

	@Override
	public List<String> getmodelnameReady(String personID, String clientID) {
		return dao.getmodelnameReady(personID,clientID);
	}
	public void getHoldingDetails(String personID,ProductionDataBean productionDataBean){
		dao.getHoldingDetails(personID,productionDataBean);
	}
	
	public void getModelsDetails(String personID,StockDataBean stockDataBean){
		dao.getModelsDetails(personID,stockDataBean);
	}
	
	public void getWarehouseActivity(String personID,StockDataBean stockDataBean){
		dao.getWarehouseActivity(personID,stockDataBean);
	}

	@Override
	public void getbestSeller(String personID, StockDataBean stockDataBean) {
		dao.getbestSeller(personID,stockDataBean);
	}

	@Override
	public List<ProductionDataBean> getProduction(String personID,String clientID,ProductionDataBean productionDataBean) {
		return dao.getProduction(personID,clientID,productionDataBean);
	}

	@Override
	public List<FinanceDataBean> getSaving(String personID, String clientID) {
		return dao.getSaving(personID,clientID);
	}

	@Override
	public void getItems(String personID, StockDataBean stockDataBean) {
		dao.getItems(personID, stockDataBean);
	}

	@Override
	public List<String> getsuppliername(String personID) {
		return dao.getsuppliername(personID);
	}

	@Override
	public List<SupplyDataBean> getsupplierfabricpurchaseList(String personID,
			String str) {
		return dao.getsupplierfabricpurchaseList(personID,str);
	}

	@Override
	public List<SupplyDataBean> getdatefabricpurchaseList(String personID,
			Date fromDate, Date todate) {
		return dao.getdatefabricpurchaseList(personID,fromDate,todate);
	}

	@Override
	public List<SupplyDataBean> getinvoicetableview(String personID,
			SupplyDataBean supplyDataBean) {
		return dao.getinvoicetableview(personID,supplyDataBean);
	}

	@Override
	public List<String> getprinterModelName(String personID) {
		
		return dao.getprinterModelName(personID);
	}

	@Override
	public List<String> getprinterseriNo(String modelname, String personID) {
		
		return dao.getprinterseriNo(modelname,personID);
	}

	@Override
	public List<StockDataBean> getprinterTable(String personID,
			StockDataBean stockDataBean) {
		return dao.getprinterTable(personID,stockDataBean);
	}

	@Override
	public List<StockDataBean> getprinterSave(StockDataBean stockDataBean,
			String personID) {
	
		return dao.getprinterSave(stockDataBean,personID);
	}

	@Override
	public List<StockDataBean> getprinterReturn(StockDataBean stockDataBean,
			String personID) {
		return dao.getprinterReturn(stockDataBean,personID);
	}

	@Override
	public List<StockDataBean> getprinterReturn1(StockDataBean stockDataBean,
			String personID) {
		return dao.getprinterReturn1(stockDataBean,personID);
	}

	@Override
	public List<StockDataBean> getPrinteredit(StockDataBean stockDataBean,
			String personID) {
		return dao.getPrinteredit(stockDataBean,personID);
	}

	@Override
	public String printerreturnEdit(String personID,
			List<StockDataBean> returnList2, StockDataBean stockDataBean) {
		return dao.printerreturnEdit(personID,returnList2,stockDataBean);
	}

	@Override
	public void getprinterDelete(StockDataBean stockDataBean, String personID) {
		dao.getprinterDelete(stockDataBean,personID);
	}

	@Override
	public String seriCheck(String seri,String model) {
		return dao.seriCheck(seri,model);
	}

	@Override
	public List<ProductionDataBean> getcmtorderView1(String personID,
			String clientID, ProductionDataBean productionDataBean) {
		return dao.getcmtorderView1(personID,clientID,productionDataBean);
	}

	@Override
	public String payrollCutterchange(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.payrollCutterchange(personID,clientID,productionDataBean);
	}

	@Override
	public String payrollPrinterchange(String personID, String clientID,
			ProductionDataBean productionDataBean) {
		return dao.payrollPrinterchange(personID,clientID,productionDataBean);
	}

	@Override
	public String payment1(String personID, String clientID,
			List<ProductionDataBean> payrollList) {
		return dao.payment1(personID,clientID,payrollList);
	}

	@Override
	public void getvendorCmtPayments(String personID,
			ProductionDataBean productionDataBean) {
		 dao.getvendorCmtPayments(personID,productionDataBean);
	}

	@Override
	public List<String> printerInvoiceList(String clientID) {
		return dao.printerInvoiceList(clientID);
	}

	@Override
	public List<String> getemployeeNames(String personID,
			String clientID,EmployeeDataBean employeeDataBean) {
		
		return dao.getemployeeNames(personID,clientID, employeeDataBean);
	}

	@Override
	public List<String> getbaranchname(String personID,
			String clientID) {
	
		return dao.getbaranchname(personID,clientID);
	}

	@Override
	public List<EmployeeDataBean> getEmployeeReportList1(String personID,
			String clientID, EmployeeDataBean employeeDataBean) {
	
		return dao.getEmployeeReportList1(personID,clientID,employeeDataBean);
	}
	
	
	@Override
	public String savetable(String personID, String clientID,ProductionDataBean productionDataBean) {
		return dao.savetable(personID,clientID,productionDataBean);
	}

	@Override
	public List<ProductionDataBean> gettableview(ProductionDataBean productionDataBean) {
		return dao.gettableview(productionDataBean);
		
	}

	@Override
	public String addhistory(ProductionDataBean productionDataBean) {
		
		return dao.addhistory(productionDataBean);
	}

	@Override
	public List<ProductionDataBean> gettableHistory(ProductionDataBean productionDataBean) {
		return dao.gettableHistory(productionDataBean);
		
	}

	@Override
	public String checkTableno(ProductionDataBean productionDataBean) {
		
		return dao.checkTableno(productionDataBean);
	}
	
	
	
	
}

