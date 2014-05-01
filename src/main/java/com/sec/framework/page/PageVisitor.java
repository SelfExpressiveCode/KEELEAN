package com.sec.framework.page;

import java.util.List;

import com.sec.framework.entity.BaseEntity;

public interface PageVisitor {

	public List<? extends BaseEntity> visitor(List<? extends BaseEntity> datas);
}
