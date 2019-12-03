package com.nrg.lemon.shared;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the employee_payroll database table.
 * 
 */
@Entity
@Table(name="employee_payroll")
@NamedQuery(name="EmployeePayroll.findAll", query="SELECT e FROM EmployeePayroll e")
public class EmployeePayroll implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int payroll_ID;

	@Column(name="attendance_bonus")
	private String attendanceBonus;

	@Column(name="loan_amount")
	private String loanAmount;

	@Column(name="month_salary")
	private String monthSalary;

	@Column(name="over_time")
	private String overTime;

	private String salary;

	private String status;
	
	//bi-directional many-to-one association to employee
	@ManyToOne
	@JoinColumn(name="employee_ID")
	private Employee employee;
	//bi-directional many-to-one association to month
	@ManyToOne
	@JoinColumn(name="month_id")
	private Month month;
	//bi-directional many-to-one association to year
	@ManyToOne
	@JoinColumn(name="year_id")
	private Year year;
		
	public EmployeePayroll() {
	}

	public int getPayroll_ID() {
		return this.payroll_ID;
	}

	public void setPayroll_ID(int payroll_ID) {
		this.payroll_ID = payroll_ID;
	}

	public String getAttendanceBonus() {
		return this.attendanceBonus;
	}

	public void setAttendanceBonus(String attendanceBonus) {
		this.attendanceBonus = attendanceBonus;
	}

	public String getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getMonthSalary() {
		return this.monthSalary;
	}

	public void setMonthSalary(String monthSalary) {
		this.monthSalary = monthSalary;
	}

	public String getOverTime() {
		return this.overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public String getSalary() {
		return this.salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Month getMonth() {
		return month;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}


}