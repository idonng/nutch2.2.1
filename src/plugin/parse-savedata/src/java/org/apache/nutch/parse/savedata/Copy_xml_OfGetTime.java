package org.apache.nutch.parse.savedata;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map.Entry;
import org.apache.nutch.parse.savedata.Copy_xml_OfGetTime;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Copy_xml_OfGetTime {

	public static void main(String[] args) throws Exception {	
	}
	
	@SuppressWarnings("rawtypes")
	public static String getTime(String content,String html) throws Exception
	{
		String tim=null;
        HtmlCleaner cleaner = new HtmlCleaner();       
        TagNode rootNode = cleaner.clean(content);              	
        ReadXml rx=new ReadXml();
        String  xmlFile = "./conf/conn.xml";
		InputStream is=new FileInputStream(xmlFile);
		Document doc=Jsoup.parse(is,"utf-8","");
		List<String> list=rx.getId(doc);
		for(int i=0;i<list.size();i++)
		  {
			  if(html.contains(list.get(i)))
			  {
				  Map<String, String> map= rx.getPubtime(doc, list.get(i));
				  Iterator it = map.entrySet().iterator();
				  while(it.hasNext()){
					  Entry entry = (Entry)it.next();
					  String a1=(String) entry.getKey() ;
					  String a2=(String) entry.getValue(); 
					  Pattern p = Pattern.compile(a1); 
					  Matcher matcher = p.matcher(html); 
					  if (matcher.find()){		    	
						  if(tim==null){    
							  Object[] ns0 = rootNode.evaluateXPath(a2);	        
							  for (int j=0;j<ns0.length;j++) {	
								  tim=ns0[j].toString()+tim;
							  }
							  if(tim==null &&(a1.contains("http://www.ledzmw.com/sell/")))
							  {
								  Object[] ns1 = rootNode.evaluateXPath("/body/div[5]/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td[3]/table/tbody/tr[7]/td[2]/text()");	        
								  for (int j=0;j<ns1.length;j++) {	
									  tim=ns1[j].toString()+tim; }	 
							  } 
						  }
					  }
 		 	    if(tim!=null)
	 	    	 {
	    			tim=trans(runTime(tim));}
	 	       
 		    	}	    	 
 	          }	
		  }
 	    if(tim==null||tim.length()==0)
 		{
 	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	    		
 	    	tim=df.format(new Date());
 		}
 	    return tim;	
 	}	
 		@SuppressWarnings({ "unchecked", "rawtypes" })
 		public static String runTime(String text) throws Exception { 

 		      String str = text.replaceAll("\r\n", " "); 
 		      str = str.replaceAll("\\s+", " ");      
 		      str = str.replaceAll("(?is)<!DOCTYPE.*?>", "");
 		      str = str.replaceAll("(?is)<!--.*?-->", "");				// remove html comment
 		      str = str.replaceAll("(?is)<script.*?>.*?</script>", ""); // remove javascript
 		      str = str.replaceAll("(?is)<style.*?>.*?</style>", "");   // remove css
 			  str = str.replaceAll("&.{2,5};|&#.{2,5};", " ");			// remove special char			
 			  str = str.replaceAll("(?is)<a.*?>.*?</a>"," ");
 			  str = str.replaceAll("(?is)<img.*?/>"," ");  
 		      try {  
 		          List matches = null; 
 		         Pattern p = Pattern.compile("([0-9]{4}[-|\\/](((0[13578]|(10|12)|[13578])[-|\\/](0[1-9]|[1-2][0-9]|3[0-1]|[1-9]))|((2|02)[-|\\/](0[1-9]|[1-2][0-9]|[1-9]))|((0[469]|11|[469])[-|\\/](0[1-9]|[1-2][0-9]|30|[1-9])))\\s\\d{1,2}:\\d{1,2})", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE); 
 		          Matcher matcher = p.matcher(str); 
 		          if (matcher.find() && matcher.groupCount() >= 1) { 
 		              matches = new ArrayList(); 
 		              for (int i = 1; i <= matcher.groupCount(); i++) { 
 		                  String temp = matcher.group(i); 
 		                  matches.add(temp); 
 		              } 
 		          }

 		          else { 

 		              matches = Collections.EMPTY_LIST; 

 		          } 

 		          if (matches.size() > 0) { 

 		              return ((String) matches.get(0))+":00"; 

 		          }
 		          else{ 

 		        	  try {  

 		                 List matches1 = null; 

 		                 Pattern p1 = Pattern.compile("([0-9]{4}[-|\\/|年](((0[13578]|(10|12)|[13578])[-|\\/|月](0[1-9]|[1-2][0-9]|3[0-1]|[1-9]))|((2|02)[-|\\/|月](0[1-9]|[1-2][0-9]|[1-9]))|((0[469]|11|[469])[-|\\/|月](0[1-9]|[1-2][0-9]|30|[1-9])))(日| ))", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE); 

 		                  Matcher matcher1 = p1.matcher(str); 

 		                  if (matcher1.find() && matcher1.groupCount() >= 1) { 

 		                      matches1 = new ArrayList(); 

 		                      for (int i = 1; i <= matcher1.groupCount(); i++) { 

 		                          String temp1 = matcher1.group(i); 

 		                          matches1.add(temp1); 

 		                      } 

 		                  } else { 

 		                      matches1 = Collections.EMPTY_LIST; 

 		                  }               

 		                  if (matches1.size() > 0) { 

 		                      return ((String) matches1.get(0)).trim()+" 00:00:00"; 

 		                  }

 		                  else {               	      

 		 	                 List matches2 = null; 

 			                 Pattern p2 = Pattern.compile("([0-9]{4}[-|\\/|年](((0[13578]|(10|12)|[13578])[-|\\/|月](0[1-9]|[1-2][0-9]|3[0-1]|[1-9]))|((2|02)[-|\\/|月](0[1-9]|[1-2][0-9]|[1-9]))|((0[469]|11|[469])[-|\\/|月](0[1-9]|[1-2][0-9]|30|[1-9])))日)", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE); 

 			                  Matcher matcher2 = p2.matcher(str); 

 			                  if (matcher2.find() && matcher2.groupCount() >= 1) { 

 			                      matches2 = new ArrayList(); 

 			                      for (int i = 1; i <= matcher2.groupCount(); i++) { 

 			                          String temp2 = matcher2.group(i); 

 			                          matches2.add(temp2); 

 			                      } 

 			                  } 

 			                  else { 

 			                      matches2 = Collections.EMPTY_LIST; 

 			                  }               

 			                  if (matches2.size() > 0) { 

 			                      return ((String) matches2.get(0)).trim()+" 00:00:00"; 

 			                  }

 			                  else

 			                  { 

 			                   return "";

 			                  }      	                	  

 		                  }   

 		        	  }

 		        	  catch (Exception e) {

 		        		  return "";

 						// TODO: handle exception

 					}

 		          }

 		      }

 		      catch (Exception e) {

 		    	  return "";

 				// TODO: handle exception

 			}

 		}
 				
 		public static String trans(String str)
 		{
 			String st=null;
 			String[] a=str.split("-");
 			if(a.length>1)
 			{
 				if(a[1].length()==1)
 				{
 					a[1]="0"+a[1];
 				}
 				if(a[2].split(" ")[0].length()==1)
 				{
 					a[2]="0"+a[2];
 				}
 				st=a[0]+"-"+a[1]+"-"+a[2];
 			}
 			else {
 				String[] b=str.split("/");
 				if(b.length>1)
 				{
 					if(b[1].length()==1)
 					{
 						b[1]="0"+b[1];
 					}
 					if(b[2].split(" ")[0].length()==1)
 					{
 						b[2]="0"+b[2];
 					}
 						st=b[0]+"-"+b[1]+"-"+b[2];
 				}
 				else
 				{
 					String[] c=str.split("年");
 					if(c.length>1)
 					{
 						String[] d=c[1].split("月");
 						if(d[0].length()==1)
 						{
 							d[0]="0"+d[0];
 						}
 						String[] e=d[1].split("日");
 						if(e[0].length()==1)
 						{
 							e[0]="0"+e[0];
 						}
 						st=c[0]+"-"+d[0]+"-"+e[0]+e[1];
 					}
 				}		
 			}
 			return st;
 		}

 }

 	              

