package org.apache.nutch.parse.savedata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 在线性时间内抽取主题类（新闻、博客等）网页的正文。
 * 采用了"基于行块分布函数"的方法，为保持通用性没有针对特定网站编写规则。
 */
public class GetMaintext {

	private List<String> lines;
	private final static int blocksWidth=3;
	/**
	 * 阈值增大，准确率提升，召回率下降；值变小，噪声会大，但可以抽取极短的正文
	 */
	private int threshold;
	private String html;
	/**
	 * 拟用来标记是否为主体性网页，暂未使用
	 */
	private boolean flag;
	private int start;
	private int end;
	private StringBuilder text;
	/**
	 * 存放文本中每行有效字符长度
	 */
	private ArrayList<Integer> indexDistribution;
	public static Logger logger = Logger.getLogger(GetMaintext.class);
	
	
	public GetMaintext() {
		lines = new ArrayList<String>();
		indexDistribution = new ArrayList<Integer>();
		text = new StringBuilder();
		flag = false;
		threshold	= -1;   
	}
	
	/**
	 * 暂未用，不支持自定义
	 * flag：标记是否为主体性网页
	 * threshold:初始行有效值门限
	 */
	public GetMaintext(boolean _flag,int _threshold){
		lines = new ArrayList<String>();
		indexDistribution = new ArrayList<Integer>();
		text = new StringBuilder();
		flag = _flag;
		threshold	= _threshold;
	}
	

	/**
	 * 抽取网页正文，假定为可抽取正文的网页。
	 * 
	 * @param _html 网页HTML字符串
	 * 
	 * @return 网页正文string
	 */
	public String parse(String _html) {
		return parse(_html, false);
	}
	
	/**
	 * 判断传入HTML，若是主题类网页，则抽取正文；否则输出<b>"unkown"</b>。
	 * 
	 * @param _html 网页HTML字符串
	 * @param _flag true进行主题类判断, 省略此参数则默认为false
	 * 
	 * @return 网页正文string
	 */
	public String parse(String _html, boolean _flag) {
		flag = _flag;
		html = _html;
		String result=null;
		if(_html==null||_html.length()<20){
			return result;
		}
		try {
			html = preProcess(html);
			result=getText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("网页内容==>"+_html);
			logger.error("网页正文提取失败==>", e);
		}
		
		return result;
	}
	
	/**
	 * 
	 * <p>Discription:去除文本中关于HTML的结构及脚本信息</p>
	 * @param @param source 输入的文本，存放网页源码
	 * @param @return	去除结构及脚本信息后的内容
	 * @return String 返回类型
	 * @throws
	 */
	private static String preProcess(String source) {
	
		source = source.replaceAll("(?is)<!DOCTYPE.*?>", "");
		source = source.replaceAll("(?is)<!--.*?-->", "");				
		source = source.replaceAll("(?is)<script.*?>.*?</script>", ""); 
		source = source.replaceAll("(?is)<style.*?>.*?</style>", "");   
		source = source.replaceAll("(?is)<title.*?>.*?</title>", "");
		source = source.replaceAll("&.{2,5};|&#.{2,5};", " ");			
		source = source.replaceAll("<[sS][pP][aA][nN].*?>", "");
		source = source.replaceAll("</[sS][pP][aA][nN]>", "");
		source = source.replaceAll("(?is)<[aA].*?>.*?</[aA]>"," ");
		source = source.replaceAll("(?is)<img.*?/>"," ");  
		source = source.replaceAll("<[^>'\"]*['\"].*['\"].*?>", "");	//防止html中在<>中包括大于号的判断
		source = source.replaceAll("<.*?>", "");
		source = source.replaceAll("\r\n", "\n");
		//最后去掉跨行的HTML标签
		source= replaceHtmlTag(source);
    
		//System.out.println(source);
		return source;
	
	}
	
	/**
	 * 
	 * <p>Discription:基于行块分布，提取正文</p>
	 * @param @return 正文文本
	 * @return String 返回类型
	 * @throws
	 */
	private String getText() {	
		lines = Arrays.asList(html.split("\n"));
		indexDistribution.clear();	
		int empty = 0;//空行的数量		
		//统计空行、块容量序列<indexDistribution>
		for (int i = 0; i < lines.size() - blocksWidth; i++) {
			if (lines.get(i).length() == 0)
			{
				empty++;//空行++
			}
			int wordsNum = 0;
			for (int j = i; j < i + blocksWidth; j++) { 
				lines.set(j, lines.get(j).replaceAll("\\s+", ""));//去除空格
				wordsNum += lines.get(j).length();
			}
			indexDistribution.add(wordsNum);//存储块容量
//			System.out.println("行"+i+"块长度："+wordsNum);
		}
		if(indexDistribution.size()==0) return " ";	
		//统计全文有效容量
		int sum = 0;
		for (int i=0; i< indexDistribution.size(); i++)
		{
			sum += indexDistribution.get(i);
		}
		//计算每行有效长度的阈值，用于判断每行有效性[=(有效容量/总行数)*Pow(2,(空行/非空行)/2)]
		threshold = Math.min(100, (sum/indexDistribution.size())<<(empty/(lines.size()-empty)>>>1));
		threshold = Math.max(50, threshold);	
		start = -1; end = -1;
		boolean boolstart = false, boolend = false;
		boolean firstMatch = true;//前面的标题块往往比较小，应该减小与它匹配的阈值
		text.setLength(0);
		StringBuilder buffer = new StringBuilder();	
		for (int i = 0; i < indexDistribution.size() - 1; i++) {
			//new start,only once
			if(firstMatch && ! boolstart)
			{
				if (indexDistribution.get(i) > (threshold/2) && ! boolstart) {
					if (indexDistribution.get(i+1).intValue() != 0 
						|| indexDistribution.get(i+2).intValue() != 0) {
						firstMatch = false;
						boolstart = true;
						start = i;
						continue;
					}
				}			
			}
			//middle check
			if (indexDistribution.get(i) > threshold && ! boolstart) {
				if (indexDistribution.get(i+1).intValue() != 0 
					|| indexDistribution.get(i+2).intValue() != 0
					|| indexDistribution.get(i+3).intValue() != 0) {
					boolstart = true;
					start = i;
					continue;
				}
			}
			//end check
			if (boolstart) {
				if (indexDistribution.get(i).intValue() == 0 
					|| indexDistribution.get(i+1).intValue() == 0) {
					end = i;
					boolend = true;
				}
			}
			//end process
			if (boolend) {
				buffer.setLength(0);
				//System.out.println(start+1 + "\t\t" + end+1);
				for (int ii = start; ii <= end; ii++) {
					String strMark=lines.get(ii);
					if (lines.get(ii).length() < 5) continue;
					int conFlag=0;//跳过诸如“移动发KTDBW到10658333，联通发DBWY到1065566600”的语句
					if (strMark.contains("移动发")) conFlag++;
					if (strMark.contains("联通发")) conFlag++; 
					if (strMark.contains("电信发")) conFlag++; 
					if (conFlag>1)	continue;
					buffer.append(lines.get(ii) + "\n");
				}
				String str = buffer.toString();
				//跳过版权、联系方式等信息
				if (str.contains("Copyright") || str.contains("版权所有") ) continue; 
				if (str.contains("联系方式")  || str.contains("授权") ) continue;
				text.append(str);
				boolstart = boolend = false;
			}
		}
	//	System.out.println(text);//到这已经没了。
		if (start > end)
		{//最后一个行块
			buffer.setLength(0);
			int size_1 = lines.size()-1;
			for (int ii = start; ii <= size_1; ii++) {
				if (lines.get(ii).length() < 5) continue;
				buffer.append(lines.get(ii) + "\n");
			}
			String str = buffer.toString();
			//System.out.println(str);
			if ((!str.contains("Copyright"))&&(!str.contains("版权所有"))&&(!str.contains("联系方式"))&&(!str.contains("授权")) ) 
			{	
				text.append(str);
			}
		}
		
		return text.toString();
	}
	
	/**
	 * 
	 * <p>Discription:去除跨行的HTML标签</p>
	 * @param @param str 
	 * @return String 
	 * @throws
	 */
	private static String replaceHtmlTag(String str){
		char[] charArray=str.toCharArray();
		
		Boolean iStart=false;
		for(int i=0;i<charArray.length-1;i++){
			if(iStart){
				//end mark
				if(charArray[i] == '>'){
					iStart=false;
					charArray[i]=' ';
					continue;
				}
				//跳过 /n
				if (charArray[i] == '/' && charArray[i+1] == 'n'){
					i=i+1;
					continue;
				}
				charArray[i]=' ';
				continue;
			}
			//start mark
			if(charArray[i] == '<'){
				iStart=true;
				charArray[i]=' ';
				continue;
			}
		}
		
		return new String(charArray);
	}
	
	
	public static void main(String[] args)
	{
		
	}
}