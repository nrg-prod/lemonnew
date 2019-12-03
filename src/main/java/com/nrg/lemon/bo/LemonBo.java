package com.nrg.lemon.bo;

import java.util.Date;
import java.util.List;

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

/**
 * This Java Class will communicate with 
 * @author Udhaya
 * @date 03-06-2016
 * @copyright NRG    
 *       
 */
public interface LemonBo 
{

	public String login(LoginDataBean loginDataBean);

	public List<UserProduct> loadmenu(LoginDataBean loginDataBean);

	public List<Subproduct> submenus(int product_ID, String productCode);

	public String insertBranch(EmployeeDataBean employeeDataBean,String personID, String clientID);

	public List<EmployeeDataBean> getbranchInfo(String personID, String clientID);

	public String updateBranch(String personID, String clientID,EmployeeDataBean employeeDataBean);

	public String deleteBranch(String personID, String clientID,EmployeeDataBean employeeDataBean);

	public List<String> branchlist(String personID, String clientID);

	public String inserteEmployee(EmployeeDataBean employeeDataBean,String personID, String clientID);

	public List<EmployeeDataBean> getemployeeInfo(String personID,String clientID);

	public List<Employee> employeeDetails(EmployeeDataBean employeeDataBean,String personID, String clientID);

	public String updateEmployee(EmployeeDataBean employeeDataBean,String personID, String clientID);

	public String deleteEmployee(String personID, String clientID,EmployeeDataBean employeeDataBean);

	public List<EmployeeDataBean> employeeSearch(EmployeeDataBean employeeDataBean, String personID, String clientID);

	public String insertAnnouncement(EmployeeDataBean employeeDataBean,String personID, String clientID);

	public List<EmployeeDataBean> announcementInfo(String personID,String clientID);

	public String updateAnnouncement(String personID, String clientID,EmployeeDataBean employeeDataBean);

	public String deleteAnnouncement(String personID, String clientID,EmployeeDataBean employeeDataBean);

	public List<EmployeeDataBean> attendanceSearch(EmployeeDataBean employeeDataBean, String personID, String clientID);

	public String saveAttendance(String personID, String clientID,EmployeeDataBean employeeDataBean,List<EmployeeDataBean> attendanceList);

	public List<Attendance> checkAttendance(EmployeeDataBean employeeDataBean,String personID, String clientID);

	public List<EmployeeDataBean> attendanceView(String personID,String clientID, EmployeeDataBean employeeDataBean);

	public String attendanceUpdate(EmployeeDataBean employeeDataBean,String personID, String clientID);
	
	public String insertsupplier(String personID, String clientID, SupplyDataBean supplyDataBean);

	public List<SupplyDataBean> getsupplierlist(String personID);

	public String deletesupplier(String personID, SupplyDataBean supplyDataBean);

	public String editsupplier(String personID, SupplyDataBean supplyDataBean);

	public String updatesupplier(String personID, SupplyDataBean supplyDataBean);

	public String purchaseFabricInvoice(String personID,SupplyDataBean supplyDataBean);

	public List<String> getsuppliernamelist(String personID,String str);

	public String insertvendor(String personID, String clientID,ProductionDataBean productionDataBean);

	public List<ProductionDataBean> getvendorlist(String personID);

	public String editVendor(String personID,ProductionDataBean productionDataBean);

	public String updatevendor(String personID,ProductionDataBean productionDataBean);

	public String deletevendor(String personID,ProductionDataBean productionDataBean);

	public String insertcategory(String personID, String clientID,ProductionDataBean productionDataBean);

	public List<ProductionDataBean> getcategorylist(String personID);

	public String editcategory(String personID,ProductionDataBean productionDataBean);

	public String deletecategory(String personID,ProductionDataBean productionDataBean);

	public List<String> getcategorynamelist(String personID);

	public String insertmodel(String personID, String clientID,ProductionDataBean productionDataBean);

	public List<ProductionDataBean> getmodelList(String personID);

	public String deletemodel(String personID,ProductionDataBean productionDataBean);

	public String editModel(String personID, ProductionDataBean productionDataBean);

	public String updatemodel(String personID,ProductionDataBean productionDataBean);

	public List<ProductionDataBean> getparticularmodelList(String personID,String category);

	public String savepurchasefabric(String personID, SupplyDataBean supplyDataBean);

	public List<SupplyDataBean> getfabricpurchaseList(String personID);

	public String viewpurchasefabric(String personID,SupplyDataBean supplyDataBean);

	public String updatepurchasefabric(String personID,SupplyDataBean supplyDataBean);

	public String deletepurchasefabric(String personID,SupplyDataBean supplyDataBean);

	public List<SupplyDataBean> getfabricpurchaseList(String personID,String str);

	public String insertitems(String personID, String clientID,SupplyDataBean supplyDataBean);

	public List<SupplyDataBean> getitems(String personID, String clientID, String itemStatus);

	public String viewitems(String personID, String clientID,SupplyDataBean supplyDataBean);

	public String updateitems(String personID, SupplyDataBean supplyDataBean);

	public String deleteitems(String personID, SupplyDataBean supplyDataBean);
	
	public String generateItemsInvoice(String personID,SupplyDataBean supplyDataBean);

	public List<String> getitemNameList(String personID,SupplyDataBean supplyDataBean);

	public void getItemPrice(String personID, SupplyDataBean supplyDataBean);

	public String savePurchaseSalesItems(String personID, String clientID,SupplyDataBean supplyDataBean);

	public List<String> getvendornamelist(String personID, String str);

	public List<String> getsalesitemNameList(String personID,SupplyDataBean supplyDataBean);
	
	public List<SupplyDataBean> getitemtableview(String personID,SupplyDataBean supplyDataBean);
	
	public List<String> getcutterList(String personID, String clientID);

	public List<String> modelList(String personID, String clientID, ProductionDataBean productionDataBean);

	public String generateiInvoiceno(String personID, String clientID,ProductionDataBean productionDataBean);

	public String modelChange(ProductionDataBean productionDataBean,String personID, String clientID);

	public String insertCutterorder(String personID, String clientID,ProductionDataBean productionDataBean,List<ProductionDataBean> cutterorderlist);

	public List<ProductionDataBean> cutterPurchasedetails(String personID,String clientID, ProductionDataBean productionDataBean);

	public String getcutterOrderview(String personID, String clientID,ProductionDataBean productionDataBean);

	public String updateCutterorder(String personID, String clientID,ProductionDataBean productionDataBean);

	public String deleteCutterorder(String personID, String clientID,ProductionDataBean productionDataBean);

	public List<ProductionDataBean> cutterorderSearch(String personID,String clientID, ProductionDataBean productionDataBean);

	public List<String> getCmt(String personID, String clientID);

	public String getCmtinvoice(String personID, String clientID);

	public String insertCmtorder(String personID, String clientID,ProductionDataBean productionDataBean,List<ProductionDataBean> cmtorderlist);

	public List<ProductionDataBean> getcmtorderView(String personID,String clientID, ProductionDataBean productionDataBean);

	public List<ProductionDataBean> getcmtSearch(String personID,String clientID, ProductionDataBean productionDataBean);

	public void getcmtpurchesView(String personID, String clientID,ProductionDataBean productionDataBean);

	public String cmtorderUpdate(String personID, String clientID,ProductionDataBean productionDataBean);

	public String cmtOrderdelete(String personID, String clientID,ProductionDataBean productionDataBean);

	public List<ProductionDataBean> getcmtHolding(String personID,String clientID, ProductionDataBean productionDataBean);

	public String printerInvoiceNum(String personID,ProductionDataBean productionDataBean);

	public String printerreceiveInvoiceNum(String personID,ProductionDataBean productionDataBean);

	public List<String> getPrinterlist(String personID, String clientID);

	public String insertPrinterRecOrder(String personID, String clientID,ProductionDataBean productionDataBean,List<ProductionDataBean> printerorderlist);

	public String insertPrinterOrder(String personID, String clientID,ProductionDataBean productionDataBean,List<ProductionDataBean> printerorderlist);

	public List<ProductionDataBean> getprinterView(String clientID,String personID);

	public void getprinterpurchaseView(String personID, String clientID,ProductionDataBean productionDataBean);

	public String printerpurchaseUpdate(String personID, String clientID,ProductionDataBean productionDataBean);

	public String printerpurchaseDelete(String personID, String clientID,ProductionDataBean productionDataBean);

	public List<ProductionDataBean> getprinterHolding(String personID,String clientID, ProductionDataBean productionDataBean);

	public List<ProductionDataBean> getprinterView(String personID,String clientID, ProductionDataBean productionDataBean);

	public List<ProductionDataBean> getprinterSearch(String personID,String clientID, ProductionDataBean productionDataBean);

	public List<ProductionDataBean> vendorDetails(String personID,String clientID, ProductionDataBean productionDataBean);

	public String viewpurchasesaleitems(String personID,SupplyDataBean supplyDataBean);

	public String updatepurchasesalesitems(String personID,String clientID, SupplyDataBean supplyDataBean);

	public String deletepursalesitems(String personID,SupplyDataBean supplyDataBean);

	public List<SupplyDataBean> getitemtableviews(String personID,SupplyDataBean supplyDataBean);

	public String getPaymentDetails(String personID, SupplyDataBean supplyDataBean);

	public String generateiInvoiceno(String personID,SupplyDataBean supplyDataBean);

	public String payAmount(String personID, SupplyDataBean supplyDataBean);

	public String addStock(String personID, SupplyDataBean supplyDataBean);

	public String insertPayment(String personID, SupplyDataBean supplyDataBean);

	public String purchaseFabricPayment(String personID,SupplyDataBean supplyDataBean);

	public String fabricgenerateInvoice(String personID,SupplyDataBean supplyDataBean);

	public String usernamechanged(LoginDataBean loginDataBean);

	public String passwordchange(LoginDataBean loginDataBean);

	public String warehouseSave(String personID, StockDataBean stockDataBean);

	public String getWarehouseDetails(String personID, StockDataBean stockDataBean);

	public String updateWarehouse(StockDataBean stockDataBean);

	public String getManualStockOutetails(String personID,StockDataBean stockDataBean);

	public String getModelDetails(String personID, StockDataBean stockDataBean);

	public String saveManualStockOut(String personID, StockDataBean stockDataBean);

	public String getManualStockDetails(String personID,StockDataBean stockDataBean);

	public String getmanualStckView(String personID, StockDataBean stockDataBean);

	public String updateManualStockOut(String personID,StockDataBean stockDataBean);

	public String deleteManualStockOut(String personID,StockDataBean stockDataBean);

	public List<String> getdebtname(String personID, String clientID,String str);

	public String saveDebt(String personID, String clientID,FinanceDataBean financeDataBean);

	public List<FinanceDataBean> getdebtList(String personID, String clientID,FinanceDataBean financeDataBean);

	public List<String> getdebtreceivename(String personID, String clientID,String str);

	public String getdebtnamevaluChange(String personID, String clientID,FinanceDataBean financeDataBean);

	public String getDebtView(String personID, String clientID,FinanceDataBean financeDataBean);

	public String debitUpdate(String personID, String clientID,FinanceDataBean financeDataBean);
	
	public List<String> getmodelName(String clientID);

	public List<String> printerModellist(String clientID);
	
	public List<EmployeeDataBean> getEmployeeReportList(String personID,String clientID);

	public List<StockDataBean> getStockDetails(String personID, StockDataBean stockDataBean);

	public String getModels(String personID, StockDataBean stockDataBean);

	public String getProductDetails(String personID, StockDataBean stockDataBean);
	
	public List<SupplyDataBean> getsupplyreportview(String personID,SupplyDataBean supplyDataBean);

	public String updateStocks(String personID, StockDataBean stockDataBean);

	public List<String> getStaffNames(String personID, String type);

	public void savePenalty(String personID, PayrollDataBean payrollDataBean);

	public List<PayrollDataBean> getPenaltyDetails(String personID);

	public void penaltyUpdate(PayrollDataBean payrollDataBean);
	
	public List<StockDataBean> getrawstockreport(String personID,StockDataBean stockDataBean);

	public List<PayrollDataBean> getPurchasePayment(String personID,PayrollDataBean payrollDataBean);

	public List<FinanceDataBean> getIncomedetails(String personID,FinanceDataBean financeDataBean);

	public String getMonthYears(String personID, PayrollDataBean payrollDataBean);

	public String getemployeeDetails(String personID,PayrollDataBean payrollDataBean);

	public String payrollSave(String personID, PayrollDataBean payrollDataBean);

	public String getEmployeePayrolls(String personID,PayrollDataBean payrollDataBean);

	public String getPayrollDetails(String personID, PayrollDataBean payrollDataBean);

	public String updatePayroll(PayrollDataBean payrollDataBean);
	
	public String getreturnsupply(String personID,ReturnDataBean returnDataBean);
	
	public String datatable(String personID,ReturnDataBean returnDataBean);

	public String save(String personID,ReturnDataBean returnDataBean);
	
	public String action(String personID,ReturnDataBean returnDataBean);
	
	public String saveCategory(String personID, String clientID,FinanceDataBean financeDataBean);

	public String transactionsave(String personID, String clientID,FinanceDataBean financeDataBean);

	public List<String> gettransactioncategory(String personID, String clientID);

	public List<FinanceDataBean> transactionview(String personID,String clientID, FinanceDataBean financeDataBean);
	
	public String cutterInvoice(String personID, String clientID,ProductionDataBean productionDataBean);

	public String cutterPayment(String personID, String clientID,ProductionDataBean productionDataBean);

	public String cutterPaymentsave(String personID, String clientID,ProductionDataBean productionDataBean);

	public List<String> getmodelName(String clientID, String cutterInvoice);

	public List<String> getmodelInfo(String personID, String clientID,ProductionDataBean productionDataBean);

	public String getSerivalue(String value, ProductionDataBean productionDataBean);

	public List<String> printerModellist(String clientID, String invoiceNo);

	public List<String> cutterInvoicelist(String clientID);

	public List<String> printerInvoicelist(String clientID);

	public List<String> printerSerilist(String clientID,ProductionDataBean productionDataBean);

	public String printerInvoice(String clientID,String invoiceNo);

	public void printerPayment(String clientID,ProductionDataBean productionDataBean);

	public String printerpaymentsave(String clientID,ProductionDataBean productionDataBean);

	public List<String> cmtInvoiceList(String clientID);

	public List<String> printerReceivemodel(String clientID,String Invoiceno);

	public List<String> getmodelData(String personID, String clientID,ProductionDataBean productionDataBean);

	public String getSerivaluechange(String value,ProductionDataBean productionDataBean);

	public List<String> cmtOrderinvoice(String clientID);

	public String getCmtReceiveinvoice(String personID, String clientID);

	public List<String> cmtOrderdemodel(String clientID, String Invoiceno);

	public List<String> cmtSerilist(String clientID,ProductionDataBean productionDataBean);

	public String cmtreceiveSerichange(String value,ProductionDataBean productionDataBean);

	public String insertCmtreceive(String personID, String clientID,ProductionDataBean productionDataBean,List<ProductionDataBean> cmtReceivelist);
	
	public List<String> getcmtModelName(String personID);
	 
	public List<String> getseriNo(String valuechange, String personID);

	public List<StockDataBean> getcmtTable(String personID, StockDataBean stockDataBean);

	public List<StockDataBean> getcmtSave(StockDataBean stockDataBean,String personID);

	public List<ProductionDataBean> cmtstockDetails(String clientID,ProductionDataBean productionDataBean);  
	
	public List<StockDataBean> getmtReturn(StockDataBean stockDataBean,String personID);

	public void getPrinterPayments(String personID,ProductionDataBean productionDataBean);
	
	public List<StockDataBean> getmtReturn1(StockDataBean stockDataBean,String personID);

	public void getPayrollReports(String personID,PayrollDataBean payrollDataBean);
	
	public void getcmtDelete(StockDataBean stockDataBean, String personID);

	public String supplyview(String personID, ReturnDataBean returnDataBean);
	
	public void viewtransaction(String personID, String clientID, FinanceDataBean financeDataBean);

	public String updatetransaction(String personID, String clientID, FinanceDataBean financeDataBean);

	public String deletetransaction(String personID, String clientID, FinanceDataBean financeDataBean);
	
	public List<StockDataBean> getedit(StockDataBean stockDataBean,String personID);

	public String cmtreturnEdit(String personID,List<StockDataBean> returnList2, StockDataBean stockDataBean); 

	public String cmtstockSave(String personID,String clientID, ProductionDataBean productionDataBean,List<ProductionDataBean> stockList);

	public String cmtInvoice(String clientID, String invoiceNo);

	public void cmtPayment(String clientID,ProductionDataBean productionDataBean);

	public String cmtPaymentsave(String clientID,ProductionDataBean productionDataBean);

	public List<ProductionDataBean> productDetails(String personID,String clientID);

	public List<ProductionDataBean> productHistory(String clientID,String personID);  
	
	public  String update(String personID,ReturnDataBean returnDataBean);
	
	public  String updates(String personID,ReturnDataBean returnDataBean);
	
	public  String updatedelete(String personID,ReturnDataBean returnDataBean);

	public List<ProductionDataBean> getproductionData(String personID,String clientID, ProductionDataBean productionDataBean);

	public void productionView(String clientID, ProductionDataBean productionDataBean);
	
	public void getOpeningStockProducts(String personID,StockDataBean stockDataBean);

	public void getModelSeris(String personID, StockDataBean stockDataBean);

	public String saveOpeningStock(String personID, StockDataBean stockDataBean);

	public String getOpeningStockDetails(String personID,StockDataBean stockDataBean);
	
	public String transactionwaste(String personID,FinanceDataBean financeDataBean); 
	
	public String reportfinance(String personID,FinanceDataBean financeDataBean); 
	
	public String supply(String personID,ReturnDataBean returnDataBean);

	public String payment(String personID, String clientID,List<ProductionDataBean> payrollList); 
	
	public String updatecustomer(String personID, String clientID,CustomerDatabean customerDatabean);
	
	public String register(String personID, String clientID,CustomerDatabean customerDatabean);
	
	public List<CustomerDatabean> customerView();
	
	public List<Customer> editcustomer(String personID, String clientID,CustomerDatabean customerDatabean);
	
	public String cusDelete(String personID, String clientID,CustomerDatabean customerDatabean);
	
	public String payPurchases(String personID, PayrollDataBean payrollDataBean);

	public List<ProductionDataBean> getcutterData(String personID,String clientID);

	public String cutterReadysave(String personID, String clientID,List<ProductionDataBean> cutterorderlist);

	public List<String> getcutterName(String personID, String clientID);

	public List<String> getPrinterreadymodel(String personID, String clientID);

	public List<String> getModel(String clientID);

	public List<ProductionDataBean> getprinterData(String personID,String clientID);

	public String printerReadysave(String personID, String clientID,List<ProductionDataBean> printerPurchaselist);

	public List<String> cmtreadyModel(String personID, String clientID);

	public List<String> getmodelnameReady(String personID, String clientID);
	
	public void getHoldingDetails(String personID,ProductionDataBean productionDataBean);

	public void getModelsDetails(String personID, StockDataBean stockDataBean);

	public void getWarehouseActivity(String personID,StockDataBean stockDataBean);

	public void getbestSeller(String personID, StockDataBean stockDataBean);

	public List<ProductionDataBean> getProduction(String personID,String clientID, ProductionDataBean productionDataBean);

	public List<FinanceDataBean> getSaving(String personID, String clientID);

	public void getItems(String personID, StockDataBean stockDataBean);

	public List<String> getsuppliername(String personID);

	public List<SupplyDataBean> getsupplierfabricpurchaseList(String personID,
			String str);

	public List<SupplyDataBean> getdatefabricpurchaseList(String personID,
			Date fromDate, Date todate);

	public List<SupplyDataBean> getinvoicetableview(String personID,
			SupplyDataBean supplyDataBean);

	public List<String> getprinterModelName(String personID);

	public List<String> getprinterseriNo(String modelname, String personID);

	public List<StockDataBean> getprinterTable(String personID,
			StockDataBean stockDataBean);

	public List<StockDataBean> getprinterSave(StockDataBean stockDataBean,
			String personID);

	public List<StockDataBean> getprinterReturn(StockDataBean stockDataBean,
			String personID);

	public List<StockDataBean> getprinterReturn1(StockDataBean stockDataBean,
			String personID);

	public List<StockDataBean> getPrinteredit(StockDataBean stockDataBean,
			String personID);

	public String printerreturnEdit(String personID,
			List<StockDataBean> returnList2, StockDataBean stockDataBean);

	public void getprinterDelete(StockDataBean stockDataBean, String personID);

	public String seriCheck(String seri, String model);

	public List<ProductionDataBean> getcmtorderView1(String personID,
			String clientID, ProductionDataBean productionDataBean);


	public String payrollCutterchange(String personID, String clientID,
			ProductionDataBean productionDataBean);

	public String payrollPrinterchange(String personID, String clientID,
			ProductionDataBean productionDataBean);
	
	public String payment1(String personID, String clientID,
			List<ProductionDataBean> orderQuantitylist);

	public void getvendorCmtPayments(String personID,
			ProductionDataBean productionDataBean);

	public List<String> printerInvoiceList(String clientID);

	public List<String> getemployeeNames(String personID,
			String clientID, EmployeeDataBean employeeDataBean);

	public List<String> getbaranchname(String personID,
			String clientID);

	public List<EmployeeDataBean> getEmployeeReportList1(String personID,
			String clientID, EmployeeDataBean employeeDataBean);

	public String savetable(String personID, String clientID,ProductionDataBean productionDataBean);

	public List<ProductionDataBean> gettableview(ProductionDataBean productionDataBean);

	public String addhistory(ProductionDataBean productionDataBean);

	public List<ProductionDataBean> gettableHistory(ProductionDataBean productionDataBean);

	public String checkTableno(ProductionDataBean productionDataBean);


}

