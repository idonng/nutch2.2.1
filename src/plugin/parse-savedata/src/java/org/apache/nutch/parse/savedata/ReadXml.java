package org.apache.nutch.parse.savedata;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ReadXml {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {	 
		  ReadXml rx=new ReadXml();
		  String  xmlFile = "./conf/conn.xml";
		 InputStream is=new FileInputStream(xmlFile);
		  Document doc=Jsoup.parse(is,"utf-8","");
		 List<String> list= rx.getId(doc);
		 for(int i=0;i<list.size();i++)
		 {
		 Map<String, String> map=rx.getSite(doc, list.get(i));
		 Iterator it = map.entrySet().iterator();
	 	  while(it.hasNext()){
		  Entry entry = (Entry)it.next();
	 		  String a1=(String) entry.getKey() ;
	 		  String a2=(String) entry.getValue(); 
	 		  System.out.println(a1+"---"+a2);
	 	  }
		 }
	}
	public List<String> getId(Document doc)
	{
		List<String> list=new ArrayList<String>();
		 Elements idss =doc.select("url");
		  for(Element ids:idss)
		  {
			  String id=ids.id();
			  if(id!= null)
			  {
			     list.add(id);}
		  }
		return list;
	}
	public Map<String,String> getPubtime(Document doc,String id)
	{
		 Map<String, String> map=new HashMap<String, String>();
		 Elements pubtimess = doc.select("url[id="+id+"]>pubtime");
		  for(Element pubtimes:pubtimess)
		  {
			  String pubtime1=pubtimes.attr("regex");
			  if(pubtime1!=null)
			  {		 
			 	String pubtime2=pubtimes.attr("xpath");
			  if(pubtime2!=null)
			   {
				 map.put(pubtime1, pubtime2); 
			   }
			  }
		  }	
		  return map;
	}
	public Map<String,String> getBrowsenum(Document doc,String id)
	{
		 Map<String, String> map=new HashMap<String, String>();
		 Elements pubtimess = doc.select("url[id="+id+"]>browsenum");
		  for(Element pubtimes:pubtimess)
		  {
			  String pubtime1=pubtimes.attr("regex");
			  if(pubtime1!=null)
			  {		 
			 	String pubtime2=pubtimes.attr("xpath");
			  if(pubtime2!=null)
			   {
				 map.put(pubtime1, pubtime2); 
			   }
			  }
		  }	
		  return map;
	}
	public Map<String,String> getCommentnum(Document doc,String id)
	{
		 Map<String, String> map=new HashMap<String, String>();
		 Elements pubtimess = doc.select("url[id="+id+"]>commentnum");
		  for(Element pubtimes:pubtimess)
		  {
			  String pubtime1=pubtimes.attr("regex");
			  if(pubtime1!=null)
			  {		 
			 	String pubtime2=pubtimes.attr("xpath");
			  if(pubtime2!=null)
			   {
				 map.put(pubtime1, pubtime2); 
			   }
			  }
		  }	
		  return map;
	}
	public Map<String,String> getInfotype(Document doc,String id)
	{
		 Map<String, String> map=new HashMap<String, String>();
		 Elements pubtimess = doc.select("url[id="+id+"]>infotype");
		  for(Element pubtimes:pubtimess)
		  {
			  String pubtime1=pubtimes.attr("regex");
			  if(pubtime1!=null)
			  {		 
			 	String pubtime2=pubtimes.attr("xpath");
			  if(pubtime2!=null)
			   {
				 map.put(pubtime1, pubtime2); 
			   }
			  }
		  }	
		  return map;
	}
	public Map<String,String> getSite(Document doc,String id)
	{
		 Map<String, String> map=new HashMap<String, String>();
		 String site = doc.select("url[id="+id+"]").val();	 
		 map.put(id, site); 	
		 return map;
	}
}
