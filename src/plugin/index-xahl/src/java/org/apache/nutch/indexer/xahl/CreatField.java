package org.apache.nutch.indexer.xahl;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.indexer.IndexingException;
import org.apache.nutch.indexer.IndexingFilter;
import org.apache.nutch.indexer.NutchDocument;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.storage.WebPage.Field;
import org.mortbay.log.Log;

public class CreatField implements IndexingFilter {
	 private Configuration conf;
	public Configuration getConf() {
		  return this.conf;		
	}
	public void setConf(Configuration arg0) {
		this.conf=arg0;
	}
	 public NutchDocument filter(NutchDocument doc, String url, WebPage page)
		      throws IndexingException {
		    String pubtime = getString( page.getFromMetadata(new Utf8("pubtime")));
		    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		    format.setTimeZone(TimeZone.getTimeZone("GMT")); 
			Date date=null;
			if(pubtime==null||pubtime.length()==0)
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	    		
				pubtime=df.format(new Date());
			}
				try {
					date=format.parse(pubtime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		    	doc.add("pubtime", format.format(date));
		   	
		    	 String maintext=getString( page.getFromMetadata(new Utf8("maintext")));
				doc.add("maintext", maintext);	
				 String snap=getString( page.getFromMetadata(new Utf8("snap")));
				doc.add("snap", snap);
				 String site=getString( page.getFromMetadata(new Utf8("site")));
				doc.add("site", site);
				
				 String infotype=getString( page.getFromMetadata(new Utf8("infotype")));
				doc.add("infotype", infotype);
				
				 String browsenum=getString( page.getFromMetadata(new Utf8("browsenum")));
				doc.add("browsenum", browsenum);
				 String commentnum=getString( page.getFromMetadata(new Utf8("commentnum")));
				doc.add("commentnum", commentnum);
		    return doc;
	}
	@Override
	public Collection<Field> getFields() {
		// TODO Auto-generated method stub
		return null;
	}
	 public static String getString(ByteBuffer buffer)  
	  {  
		 String str=null;
		 if(buffer!=null)
				{
	        	 try {
	        		 str = new String(buffer.array() , "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					Log.info("解析content错误"+e);		
					e.printStackTrace();		
				}
				}
	          return str;  
	  }  
}
