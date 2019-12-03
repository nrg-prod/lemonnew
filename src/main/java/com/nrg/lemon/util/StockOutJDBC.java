package com.nrg.lemon.util;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nrg.lemon.domain.StockDataBean;

public class StockOutJDBC {
	static final Logger logger = LoggerFactory.getLogger(StockOutJDBC.class);
	static final String QUERY = "SELECT sum(quantity),warehouse, date, model, seri,price FROM stock_out where status='Active' GROUP BY warehouse, date, model, seri";
	static final String Date_QUERY = "SELECT sum(quantity),warehouse, date, model, seri,price FROM stock_out where date between ? and ? and status='Active' GROUP BY warehouse, date, model, seri";
	public static String status;
	public static Connection con = null;
	public static PreparedStatement preparedStatement = null;
	public static Statement stmt = null;

	public static Connection getConnection() {
		Connection con = null;
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		String DB_URL = "jdbc:mysql://localhost:3306/lemonnewdb";
		String USER = "root";
		String PASS = "AJdata@123";
		try {
		logger.info("Before getting connection:");
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(DB_URL, USER, PASS);
		logger.info("After successful connection:");
		}
		catch(Exception e) {
			logger.debug(e.getMessage());
		}
		return con;
	}
	
	public static List<StockDataBean> getStockOut() throws SQLException{
		List<StockDataBean> stockoutList=new ArrayList<StockDataBean>();
		con= getConnection();
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(QUERY);
		int no=1;
		BigDecimal amount=BigDecimal.valueOf(0);
		while (rs.next()) {
			StockDataBean stockouts=new StockDataBean();
			stockouts.setSerialNo(String.valueOf(no));
			stockouts.setDate(rs.getDate("date"));
			stockouts.setWarehouseName(rs.getString("warehouse"));
			stockouts.setQuantity(rs.getString(1));
			stockouts.setModelNo(rs.getString("model"));
			stockouts.setSeri(rs.getString("seri"));
			stockouts.setSellingPrice(rs.getString("price"));
			amount=new BigDecimal(stockouts.getQuantity()).multiply(new BigDecimal(stockouts.getSellingPrice()));
			stockouts.setTotalPrice(amount.toString());
			stockoutList.add(stockouts);
			no++;
		}
		return stockoutList;
	}
	
	public static List<StockDataBean> getStockOutView(StockDataBean stockDataBean) throws SQLException{
		List<StockDataBean> stockoutList=new ArrayList<StockDataBean>();
		java.sql.Date sqldate = new java.sql.Date(stockDataBean.getFromDate().getTime());
		java.sql.Date sqldate1 = new java.sql.Date(stockDataBean.getToDate().getTime());
		BigDecimal amount=BigDecimal.valueOf(0);
		try{
			con= getConnection();
			stmt = con.createStatement();
			preparedStatement = con.prepareStatement(Date_QUERY);
			preparedStatement.setDate(1, sqldate);
			preparedStatement.setDate(2, sqldate1);
			ResultSet rs = preparedStatement.executeQuery();
			int no=1;
			while (rs.next()) {
				StockDataBean stockouts=new StockDataBean();
				stockouts.setSerialNo(String.valueOf(no));
				stockouts.setDate(rs.getDate("date"));
				stockouts.setWarehouseName(rs.getString("warehouse"));
				stockouts.setQuantity(rs.getString(1));
				stockouts.setModelNo(rs.getString("model"));
				stockouts.setSeri(rs.getString("seri"));
				stockouts.setSellingPrice(rs.getString("price"));
				amount=new BigDecimal(stockouts.getQuantity()).multiply(new BigDecimal(stockouts.getSellingPrice()));
				stockouts.setTotalPrice(amount.toString());
				stockoutList.add(stockouts);
				no++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return stockoutList;
	}
	
	
}
