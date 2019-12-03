package com.nrg.lemon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

public class Test {

	static String pin="2344"; 
	static String amount="2000";
	
	public static void main(String... args) throws Exception{
			/*for (int i = 1; i < 6; i++) {
				for (int j = 1; j>6; j++) {
					System.out.print(" "); 
				} 
				for (int k = 1; k <= i; k++) {
					System.out.print(k); 
				}
				System.out.println(); 
				}
				
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5 - i; j++) { 
					System.out.print(" "); 
				} 
				for (int k = 0; k <= i; k++) {
					System.out.print("* "); 
				} 
					System.out.println(); 
				}*/
			
			/*URL url=new URL("http://35.166.255.46:7003/sms/nrg/rest/endPoint/service/getClassListParent/1");
			BufferedReader bf=new BufferedReader(new InputStreamReader(url.openStream()));
			System.out.println(bf.readLine());
			bf.close();*/
			/*String pinno="";
			Scanner scan=new Scanner(System.in);
			System.out.println("enter the pin");
			pinno=scan.next();
			if(!pin.equalsIgnoreCase(pinno)){
				System.out.println("enter the valid pin");
				Test tes=new Test();
				tes.test(pin);
			}else{
				System.out.println("please enter the amount");
				String amt=scan.next();
				if(amount.equalsIgnoreCase(amt)){
					System.out.println("please wait-------");
					Thread.sleep(2000);
					System.out.println("please collect the cash");
				}else if(Integer.parseInt(amount)<Integer.parseInt(amt)){
					System.out.println("please wait-------");
					Thread.sleep(2000);
					System.out.println("insufficent balance");
					System.out.println("please enter the valid amount");
					amt=scan.next();
					if(amount.equalsIgnoreCase(amt)){
						System.out.println("please collect the cash");
					}else if(Integer.parseInt(amount)>Integer.parseInt(amt)){
						System.out.println("please wait-------");
						Thread.sleep(2000);
						System.out.println("please collect the cash");
					}
				}else if(Integer.parseInt(amount)>Integer.parseInt(amt)){
					System.out.println("please wait-------");
					Thread.sleep(2000);
					System.out.println("please collect the cash");
				}
			}*/

		/*String name="nalalam";
		String reverse=new StringBuffer(name).reverse().toString();
		System.out.println(reverse);
		String input="AliveisAwesome";
		  StringBuilder input1 = new StringBuilder();
		  input1.append(input);
		  input1=input1.reverse(); 
		  System.out.println(input1);*/
		//String name=convertimage("C:\\Users\\NRG\\Downloads\\New folder\\clinden certificate.pdf");
		//System.out.println("name-------"+name);
		/*double num= 651.5176515121351;
		float round=Math.round(num*100)/100;
		System.out.println(round);*/
	}

		private static String test(String pin) throws Exception{
			Test tes=new Test();
			Scanner scan=new Scanner(System.in);
			String pinno=scan.next();
			if(!pin.equalsIgnoreCase(pinno)){
				System.out.println("please wait-------");
				Thread.sleep(5000);
				System.out.println("enter the valid pin");
				tes.main(pin);
			}
			return pin;
		}
		
		private static String convertimage(String filename)throws IOException{
	 		 String imageDataString = "";
	 		 String id="";
	 		try{
	 			String base64Image = id.split(",")[1];
	 			byte[] decoded = Base64.decodeBase64(base64Image);
	 			FileOutputStream fos = null;
	 			fos = new FileOutputStream(filename);
	 			fos.write(decoded);
	 			fos.close();
	 			 File file = new File(filename);
				 FileInputStream imageInFile = new FileInputStream(file);
		            byte[] imageData = new byte[(int) file.length()];
		            imageInFile.read(imageData);
		 
		            // Converting Image byte array into Base64 String
		             imageDataString = Base64.encodeBase64String(imageData);
	 		}catch(FileNotFoundException e){
	 			e.printStackTrace();
	 		}
			return imageDataString;
	 		
	 	}		
		
		
}
