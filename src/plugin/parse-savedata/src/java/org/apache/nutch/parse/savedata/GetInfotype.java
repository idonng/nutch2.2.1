package org.apache.nutch.parse.savedata;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.nutch.parse.savedata.pojo.GetConfService;


public class GetInfotype {

	public static void main(String[]args) throws Exception {	
		String url="http://guanyuda.21dianyuan.com/cc-product-76432-1-1.html" ;
		System.out.println(getInfotype(url));
	 }

	@SuppressWarnings("rawtypes")
	public static String getInfotype(String html) throws Exception
	{
	    String tag ="other";
	    Map<String, String> map= GetConfService.getInstance().getInfotype(html);
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
 	   return tag;
 	}	
}
