package org.apache.nutch.parse.savedata.pojo;

import java.io.Serializable;
import java.util.Date;


/**
 * nutch数据保存实体
 * @author zhuerdong
 *
 */
public class CrawlData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3073994655010457496L;
	
	private int id ;// serial NOT NULL,
	private String url ;// character varying,
	private Date fetchtime ;//character varying,
	private String  title  ;//character varying,
	private String  maintext ;//character varying,
	private String   snap ;//character varying,
	private Date   pubtime ;//character varying
	private String   site ;//character varying
	private int  siteid;
	private String  infotype;
	private int  browsenum;
	private int  commentnum;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getFetchtime() {
		return fetchtime;
	}
	public void setFetchtime(Date fetchtime) {
		this.fetchtime = fetchtime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMaintext() {
		return maintext;
	}
	public void setMaintext(String maintext) {
		this.maintext = maintext;
	}
	public String getSnap() {
		return snap;
	}
	public void setSnap(String snap) {
		this.snap = snap;
	}
	public Date getPubtime() {
		return pubtime;
	}
	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public int getSiteid() {
		return siteid;
	}
	public void setSiteid(int siteid) {
		this.siteid = siteid;
	}
	public String getInfotype() {
		return infotype;
	}
	public void setInfotype(String infotype) {
		this.infotype = infotype;
	}
	public int getBrowsenum() {
		return browsenum;
	}
	public void setBrowsenum(int browsenum) {
		this.browsenum = browsenum;
	}
	public int getCommentnum() {
		return commentnum;
	}
	public void setCommentnum(int commentnum) {
		this.commentnum = commentnum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
