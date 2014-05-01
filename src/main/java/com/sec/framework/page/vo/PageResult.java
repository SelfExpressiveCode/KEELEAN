package com.sec.framework.page.vo;

import java.util.List;

import com.sec.framework.entity.BaseEntity;

public class PageResult {

	private List<? extends BaseEntity> results = null;

	private int count;

	public List<? extends BaseEntity> getResults() {
		return results;
	}

	public void setResults(List<? extends BaseEntity> results) {
		this.results = results;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
