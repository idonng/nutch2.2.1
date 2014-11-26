package org.apache.nutch.parse.savedata;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Copy_xml_of_GetInfotype {

	public static void main(String[]args) throws Exception {	
		String url="http://guanyuda.21dianyuan.com/cc-product-76432-1-1.html" ;
		System.out.println(getInfotype(url));
	 }

	@SuppressWarnings("rawtypes")
	public static String getInfotype(String html) throws Exception
	{
	    String tag ="other";
	    ReadXml rx=new ReadXml();
        String  xmlFile = "./conf/conn.xml";
		InputStream is=new FileInputStream(xmlFile);
		Document doc=Jsoup.parse(is,"utf-8","");
		List<String> list=rx.getId(doc);
		for(int i=0;i<list.size();i++)
		  {
			  if(html.contains(list.get(i)))
			  {
				  Map<String, String> map= rx.getInfotype(doc, list.get(i));
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
		}
 	   return tag;
 	}	
}
