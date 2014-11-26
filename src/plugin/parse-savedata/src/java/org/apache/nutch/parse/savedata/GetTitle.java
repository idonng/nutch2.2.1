package org.apache.nutch.parse.savedata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTitle {
	public static void main(String[]args) throws Exception {	
		String str="【深圳市】【招聘PMC,深圳市鑫巨源电子技术有限公司招聘】_中国LED人才网";
		System.out.println(getTitle(str));
	 }
	public static   String getTitle(String str){
		String source=str.trim().replace(" ","");	
		String title=source;
		String regEx="-";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(source);  
		String a=m.replaceAll(" ").trim();
		String[] b=a.split(" ");
		title=b[0];
		if(title.equals(source))
		{
			String regEx1="_";   
			Pattern p1 = Pattern.compile(regEx1);   
			Matcher m1 = p1.matcher(source);  
			String a1=m1.replaceAll(" ").trim();
			String[] b1=a1.split(" ");
			 title=b1[0];
		}
		return title;
		/*
		String title=str;
		str=str.replace(" ", "");
		 Properties pro = new Properties();
			try {
				pro.load(GetInfotype.class.getClassLoader().getResourceAsStream("site.properties"));
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
		 		  String a2=(String) entry.getValue(); 
	 	    	 Pattern p = Pattern.compile(a2); 
	 	          Matcher matcher = p.matcher(str); 
	 	          if (matcher.find()){		    	
	 	        	 	String regEx=a2; 
	 	        	 	Pattern p1 = Pattern.compile(regEx);   
	 	        	 	Matcher m1 = p1.matcher(str);  
	 	        	 	String a=m1.replaceAll(" ").trim();
	 	        	 	String[] b=a.split(" ");
	 	        	 	title=b[0].trim();
	 	        	 	if(title.substring(title.length()-1, title.length()).equals("-"))
	 	        	 	{
	 	        	 		title=title.substring(0, title.length()-1).trim();
	 	        	 	}
	 	        	 	if(title.substring(title.length()-1, title.length()).equals("_"))
	 	        	 	{
	 	        	 		title=title.substring(0, title.length()-1).trim();
	 	        	 	}
	 		    	}	    	 
	 	          }			  		
			}
			return title;
	 	*/}
}
