package com.nrg.lemon.domain;

import java.io.Serializable;
import java.util.Date;

public class EmployeeDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 456557282408327684L;
	private String branchName;
	private String bonus;
	private String overtime;
	private String late;
	private Date entryDate;
	private String employeeName;
	private String daily;
	private String commission;
	private Date dateofBirth;
	private String address;
	private String phoneNo1;
	private String phoneNo2;
	private String bloodType;
	private String alergy;
	private String contactName1;
	private String contactName2;
	private String emergencyContactno1;
	private String emergencyContactno2;
	private String emergencyContactno3;
	private String emergencyContactno4;
	private String emergencyAddress1;
	private String emergencyAddress2;
	private String employeeBranch;
	private Date updatedDate;
	private Date announcementDate;
	private String postBy;
	private String announcement;
	private int hiddenValue;
	private Date attendanceDate;
	private String attend;
	private String forfeit;
	private String reward;
	private String serialNo;
	private Date fromDate;
	private Date toDate;
	
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public String getAttend() {
		return attend;
	}

	public void setAttend(String attend) {
		this.attend = attend;
	}

	public String getForfeit() {
		return forfeit;
	}

	public void setForfeit(String forfeit) {
		this.forfeit = forfeit;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public int getHiddenValue() {
		return hiddenValue;
	}

	public void setHiddenValue(int hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

	public Date getAnnouncementDate() {
		return announcementDate;
	}

	public void setAnnouncementDate(Date announcementDate) {
		this.announcementDate = announcementDate;
	}

	public String getPostBy() {
		return postBy;
	}

	public void setPostBy(String postBy) {
		this.postBy = postBy;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getEmployeeBranch() {
		return employeeBranch;
	}

	public void setEmployeeBranch(String employeeBranch) {
		this.employeeBranch = employeeBranch;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDaily() {
		return daily;
	}

	public void setDaily(String daily) {
		this.daily = daily;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public Date getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo1() {
		return phoneNo1;
	}

	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}

	public String getPhoneNo2() {
		return phoneNo2;
	}

	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getAlergy() {
		return alergy;
	}

	public void setAlergy(String alergy) {
		this.alergy = alergy;
	}

	public String getContactName1() {
		return contactName1;
	}

	public void setContactName1(String contactName1) {
		this.contactName1 = contactName1;
	}

	public String getContactName2() {
		return contactName2;
	}

	public void setContactName2(String contactName2) {
		this.contactName2 = contactName2;
	}

	public String getEmergencyContactno1() {
		return emergencyContactno1;
	}

	public void setEmergencyContactno1(String emergencyContactno1) {
		this.emergencyContactno1 = emergencyContactno1;
	}

	public String getEmergencyContactno2() {
		return emergencyContactno2;
	}

	public void setEmergencyContactno2(String emergencyContactno2) {
		this.emergencyContactno2 = emergencyContactno2;
	}

	public String getEmergencyContactno3() {
		return emergencyContactno3;
	}

	public void setEmergencyContactno3(String emergencyContactno3) {
		this.emergencyContactno3 = emergencyContactno3;
	}

	public String getEmergencyContactno4() {
		return emergencyContactno4;
	}

	public void setEmergencyContactno4(String emergencyContactno4) {
		this.emergencyContactno4 = emergencyContactno4;
	}

	public String getEmergencyAddress1() {
		return emergencyAddress1;
	}

	public void setEmergencyAddress1(String emergencyAddress1) {
		this.emergencyAddress1 = emergencyAddress1;
	}

	public String getEmergencyAddress2() {
		return emergencyAddress2;
	}

	public void setEmergencyAddress2(String emergencyAddress2) {
		this.emergencyAddress2 = emergencyAddress2;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getOvertime() {
		return overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

	public String getLate() {
		return late;
	}

	public void setLate(String late) {
		this.late = late;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}
