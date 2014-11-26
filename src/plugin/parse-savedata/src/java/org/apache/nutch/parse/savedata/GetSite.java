package org.apache.nutch.parse.savedata;


import java.io.IOException;
import org.apache.nutch.parse.savedata.pojo.GetConfService;


public class GetSite {
	public static void main(String[]args) throws Exception {	
		String url="http://bbs.elecfans.com/forum.php?gid=48";
		System.out.println(getSite(url));
	 }
	public static  String getSite(String url) throws IOException{
		String site="其他;0";
		site=GetConfService.getInstance().getSite(url);
		return site;
	}
}
