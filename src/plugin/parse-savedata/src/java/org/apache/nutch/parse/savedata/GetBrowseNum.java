package org.apache.nutch.parse.savedata;


import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map.Entry;
import org.apache.nutch.parse.savedata.pojo.GetConfService;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;


public class GetBrowseNum {

		public static void main(String[] args) throws Exception {	
		}	
		@SuppressWarnings("rawtypes")
		public static String getBrowseNum(String content,String html) throws Exception
		{
			String hot=null;
	        HtmlCleaner cleaner = new HtmlCleaner();       
	        TagNode rootNode = cleaner.clean(content);              	
	        Map<String, String> map= GetConfService.getInstance().getBrowsenum(html);
			Iterator it = map.entrySet().iterator();
			while(it.hasNext()){
				Entry entry = (Entry)it.next();
				String a1=(String) entry.getKey() ;
				String a2=(String) entry.getValue(); 
				Pattern p = Pattern.compile(a1); 
				Matcher matcher = p.matcher(html); 
				if (matcher.find()){		    	
					if(hot==null){    
						Object[] ns0 = rootNode.evaluateXPath(a2);	        
						for (int j=0;j<ns0.length;j++) {	
							hot=ns0[j].toString()+hot;}
						if(hot!=null){			 		 	    	 
							hot=runBrowseNum(hot);}
					}	    	   
				}			  	
			}		
			if(hot==null||hot.length()==0){
				hot="0";}	  
			return hot;	
		}	
	 		public static String runBrowseNum(String text)  { 	     
	 			String source = text.replaceAll("\r\n", ""); 
	 			source = source.replaceAll("(?is)<!DOCTYPE.*?>", "");
	 			source = source.replaceAll("(?is)<!--.*?-->", "");				
	 			source = source.replaceAll("(?is)<script.*?>.*?</script>", ""); 
	 			source = source.replaceAll("(?is)<style.*?>.*?</style>", "");   
	 			source = source.replaceAll("&.{2,5};|&#.{2,5};", " ");			
	 			source = source.replaceAll("<[sS][pP][aA][nN].*?>", "");
	 			source = source.replaceAll("</[sS][pP][aA][nN]>", "");
	 			source = source.replaceAll("(?is)<[aA].*?>.*?</[aA]>","");
	 			source = source.replaceAll("(?is)<img.*?/>"," ");  
	 			source = source.replaceAll("<[^>'\"]*['\"].*['\"].*?>", "");	//防止html中在<>中包括大于号的判断
	 			source = source.replaceAll("<.*?>", "");
	 			source = source.replaceAll("\r\n", "\n");
	 			String regEx="[^0-9]";   
	 			Pattern p = Pattern.compile(regEx);   
	 			Matcher m = p.matcher(source);
	 			String a=m.replaceAll(" ").trim();
	 			String[] b=a.split(" ");
	 			return b[0];			
	 		}
 }
