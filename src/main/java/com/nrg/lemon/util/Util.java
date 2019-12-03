package com.nrg.lemon.util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;



public class Util {


	public static HttpSession getSession()
	{
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	
	public static HttpSession getRequest()
	{
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	public static String getUserName()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}

	public static String getUserId()
	{
		HttpSession session = getSession();
		if(session != null)
		{
			return (String )session.getAttribute("userid");
		}
		else
		{
			return null;
		}
	}

	
	
	
	public static String getDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String ymd = dateFormat.format(cal.getTime());
		return ymd;
	}
	
	
	
}
