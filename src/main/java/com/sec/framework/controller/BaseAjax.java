package com.sec.framework.controller;

import com.sec.framework.entity.BaseEntity;
import com.sec.framework.util.DateUtil;

public class BaseAjax {

    public void setDefaultFields(BaseEntity entity, BaseEntity user,
            boolean update, boolean deleted) {
        if (!update) {
            entity.setCreatedAt(DateUtil.today());
            entity.setCreator(user);
        }
        entity.setDeleted(deleted);
        entity.setUpdater(user);
        entity.setUpdatedAt(DateUtil.today());
    }
}
