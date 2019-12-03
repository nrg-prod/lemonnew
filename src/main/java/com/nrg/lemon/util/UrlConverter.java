package com.nrg.lemon.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;


@FacesConverter("com.nrg.lemon.util.UrlConverter")
public class UrlConverter implements Converter {
	private static Logger logger = Logger.getLogger(UrlConverter.class);

	   @Override
	   public Object getAsObject(FacesContext facesContext, 
	      UIComponent component, String value) {
		   String numberAsString = null ;
		   try
		   {
			   
		 
	      StringBuilder url = new StringBuilder();
	      
	     logger.debug("---------------------------------------------");
	       NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	       numberAsString = numberFormat.format(value);
	      String numbeAsString = numberFormat.format(value);
	       logger.debug(numberAsString);
	      
	      
	logger.debug("------------------inside value---------------------aa");
	     
		   }
		   catch(Exception e)
		   {
		   }
	      
	      return numberAsString;
	   }

	   @Override
	   public String getAsString(FacesContext facesContext,
	      UIComponent component, Object value) {
		   logger.debug("--------------------1-------------------------"+value);
		   String numberAsString = null ;
		   try
		   {
			   
		 
	      String url = value.toString();
	      logger.debug("string--------->"+url);
	      
	 
	       NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	      numberAsString = numberFormat.format(new BigDecimal(url));
	      String numbeAsString = numberFormat.format(new BigDecimal(url));
	       logger.debug(numberAsString);
	      
	      
	logger.debug("------------------inside value---------------------aa");
	     
		   }
		   catch(Exception e)
		   {
		   }
	         return numberAsString;
	   }
	}
