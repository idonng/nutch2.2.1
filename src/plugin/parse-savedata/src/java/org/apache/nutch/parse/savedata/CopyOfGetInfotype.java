package org.apache.nutch.parse.savedata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CopyOfGetInfotype {

	public static void main(String[]args) throws Exception {	
		String url="http://guanyuda.21dianyuan.com/cc-product-76432-1-1.html" ;
		System.out.println(getInfotype(url));
	 }

	@SuppressWarnings("rawtypes")
	public static String getInfotype(String html) throws Exception
	{
	    String tag ="other";
        Properties pro = new Properties();
		pro.load(CopyOfGetInfotype.class.getClassLoader().getResourceAsStream("infotype.properties"));      
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
 	          Matcher matcher = p.matcher(html); 
 	          if (matcher.find()){		    	
 	        	 	tag=a2;
 		    	}	    	 
 	          }			  		
		}
 	   return tag;
 	}	
}
