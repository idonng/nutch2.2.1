package org.apache.nutch.parse.savedata;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.hadoop.io.MD5Hash;
import org.apache.nutch.util.StringUtil;

public class SaveSnap {
	public static void main(String[] args) { 
	}	
	public static  String savesnap(String cont,String ur,String encoding) {
		 try { 
		 if(cont==null)
		 {
			 cont=ur;}
		 byte[] a= MD5Hash.digest(cont).getDigest();
		 String file = "snap/"+StringUtil.toHexString(a)+".html.gz"; 
		 GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(file));		
		 InputStream in = new ByteArrayInputStream(cont.getBytes(encoding));		
         System.out.println("Transfering bytes from input file to GZIP Format.");
         byte[] buf = new byte[1024];
         int len;     
			while((len = in.read(buf)) > 0) {
			     out.write(buf, 0, len);
			 }		      
			in.close();		
         System.out.println("Completing the GZIP file");       
			out.finish();	       
			out.close();
			return file;

				  } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
		
		
		 
	}
}
