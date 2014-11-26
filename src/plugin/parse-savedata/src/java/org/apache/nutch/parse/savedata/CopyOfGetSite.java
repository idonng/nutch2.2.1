package org.apache.nutch.parse.savedata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CopyOfGetSite {
	public static void main(String[]args) throws Exception {	
		String url="http://guanyuda.21dianyuan.com/cc-product-76432-1-1.html";
		System.out.println(getSite(url));
	 }
	@SuppressWarnings("rawtypes")
	public static  String getSite(String url){
		String str ="/";
		int a= url.indexOf(str ,7);
		String baseurl=url.substring(0,a+1);
		String site="其他;0";
		Properties pro = new Properties();	
		try {
				pro.load(CopyOfGetSite.class.getClassLoader().getResourceAsStream("site.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
		Map<String, String> map = new HashMap<String , String>();
	    Set<Object> keys = pro.keySet();  
		for(Object key : keys) {
		 map.put(key.toString(), pro.getProperty(key.toString()));
		 Iterator it = map.entrySet().iterator();
	 	  while(it.hasNext()){
		  Entry entry = (Entry)it.next();
	 		  String a1=(String) entry.getKey() ;
	 		  String a2=(String) entry.getValue(); 
 	    	 Pattern p = Pattern.compile(a1); 
 	          Matcher matcher = p.matcher(baseurl); 
 	          if (matcher.find()){		    	
 	        	 	site=a2;
 		    	}	    	 
 	          }			  		
		}
			return site;
	}
}
