package com.wonders.frame.core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestAPIUtil {
	private static Logger logger=LoggerFactory.getLogger(RestAPIUtil.class);
	private static void init() {
		
	}
	
	static {
		init();
	}	
	
	
   public static String getData(String serverUrl,HashMap<String,String> params){
	   return getData(serverUrl, params,null);
   }
	
	public static String getData(String serverUrl,HashMap<String,String> params,String cookie){
		String result = "";
		String param = "";
		URL url = null;
		HttpURLConnection http = null;
		
		try {
			url = new URL(serverUrl);
			http = (HttpURLConnection) url.openConnection();
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setUseCaches(false);
			http.setConnectTimeout(60000);
			http.setReadTimeout(60000);
			http.setRequestMethod("POST");
			//http.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");			
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if(cookie != null){
				http.setRequestProperty("Cookie", cookie);
			}
			
			http.connect();
			
	        Set<String> key = params.keySet();
	        for (Iterator it = key.iterator(); it.hasNext();) {
	            String pname = (String) it.next();
	            param+="&"+pname+"="+params.get(pname);
	        }
			
         	OutputStreamWriter osw=new OutputStreamWriter(http.getOutputStream(),"utf-8");
          	osw.write(param);
          	osw.flush();
          	osw.close();
         			
			if (http.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(),"utf-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					result += inputLine;
				}
				in.close();
			}
		} catch (Exception e) {			
			result="failure:"+e.getMessage();
			logger.error("Exception Throwable", e);
		} finally {
			if (http != null) http.disconnect();
		}
	
		
		return result;
	}
	
	
    public static void main(String[] args){
    	
    	HashMap<String,String> hm=new HashMap<String,String>();
		
    	hm.put("startdate", "2013-12-31 00:00:00");
    	hm.put("enddate", "2014-01-01 23:23:59");
    	hm.put("batch", "1");
    	long start = (new Date()).getTime();				
		String s=getData("http://10.1.40.202:8080/fqdb/flight/getPeriodFlightInJson",hm);//webService接口：getFss
		long end = (new Date()).getTime();	
		System.out.println("......use time："+(end-start)/1000+"."+(end)%start+"ms");	

	}
	
}


