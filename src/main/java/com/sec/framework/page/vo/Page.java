package com.sec.framework.page.vo;

public class Page {

	private int pageNo;

	private int pageSize;

	private String entityClassName;

	private String visitorClassName;

	private String searchFormClassName;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	public String getVisitorClassName() {
		return visitorClassName;
	}

	public void setVisitorClassName(String visitorClassName) {
		this.visitorClassName = visitorClassName;
	}

	public String getSearchFormClassName() {
		return searchFormClassName;
	}

	public void setSearchFormClassName(String searchFormClassName) {
		this.searchFormClassName = searchFormClassName;
	}

}
