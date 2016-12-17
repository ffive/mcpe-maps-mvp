package com.sopa.mvvc.datamodel;

/**
 * Created by Komlev on 29.04.2015.
 */

public class HouseAd {

	public HouseAd(String msg, String link, String pkg) {
		this.msg = msg;
		this.link = link;
		this.pkg = pkg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public boolean isShown() {

		return shown;
	}

	public void setShown(boolean shown) {
		this.shown = shown;
	}

	private boolean shown;
	private String msg,link,pkg;


}
