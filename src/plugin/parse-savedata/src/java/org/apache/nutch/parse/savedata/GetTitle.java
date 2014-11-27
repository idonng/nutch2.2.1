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
		}
}
