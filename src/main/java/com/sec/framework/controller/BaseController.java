package com.sec.framework.controller;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sec.framework.component.combo.Combo;
import com.sec.framework.entity.BaseEntity;
import com.sec.framework.entity.Finder;
import com.sec.framework.form.BaseForm;
import com.sec.framework.form.FormFactory;
import com.sec.framework.validate.Validator;
import com.sec.framework.validate.exception.ValidationError;

public abstract class BaseController<T extends BaseEntity, P extends BaseForm> {

	protected void setDataFromSession(T entity) {

	}

	public <M extends BaseEntity, N extends BaseEntity> void loadCasecadeCombo(
			HttpServletRequest request, Class<M> parentClass,
			Class<N> childClass, Long selectedParent, Long selectedChild) {
		Combo<M> comboParent = new Combo<M>(parentClass);
		Combo<N> comboChild = new Combo<N>(childClass);

		comboParent.load(null);
		if (selectedParent != null) {
			comboParent.selectedData(selectedParent);
			comboChild.load(selectedParent);
			comboChild.selectedData(selectedChild);
		} else {
			comboChild.load(comboParent.getItems().get(0).key);
		}

		request.setAttribute("parent", comboParent);
		request.setAttribute("child", comboChild);

	}

	public <M extends BaseEntity> void loadCombo(HttpServletRequest request,
			Class<M> entityClass, Long selected) {
		Combo<M> combo = new Combo<M>(entityClass);
		combo.load();
		if (selected != null) {
			combo.selectedData(selected);
		}
		request.setAttribute(entityClass.getSimpleName().toLowerCase()
				+ "Combo", combo);
	}

	public void blank(HttpServletRequest request,
			Class<? extends BaseEntity> entityClass,
			Class<? extends BaseForm> formClass) {
	}

	@SuppressWarnings("unchecked")
	public T create(HttpServletRequest request, HttpSession session,
			Class<T> entityClass, Class<P> formClass) {
		BaseForm form = (BaseForm) FormFactory.parseFromRequest(request,
				formClass);

		Validator.validate(form);

		if (form.hasError()) {
			T entity = (T) form.toEntity(form, entityClass);
			request.setAttribute(getClassName(entityClass), entity);
			request.setAttribute("form", form);

			return null;
		}
		T entity = (T) form.toEntity(form, entityClass);

		BaseEntity user = (BaseEntity) session.getAttribute("session_user");
		entity.setDefaultFields(user, false);
		setDataFromSession(entity);
		entity.save();

		return entity;
	}

	@SuppressWarnings("unchecked")
	public void edit(HttpServletRequest request, String id, Class<T> entityClass) {
		Finder<T> find = new Finder<T>(entityClass);
		T entity = find.byId(Long.parseLong(id));
		P form = (P) request.getAttribute("form");
		if (form != null) {
			request.setAttribute(getClassName(entityClass),
					form.toEntity(form, entityClass));
		} else {
			request.setAttribute(getClassName(entityClass), entity);
		}
	}

	private String getClassName(Class<? extends BaseEntity> entityClass) {
		return entityClass.getSimpleName().toLowerCase();
	}

	@SuppressWarnings("unchecked")
	public boolean update(HttpServletRequest request, HttpSession session,
			Class<T> entityClass, Class<P> formClass) {
		P form = (P) FormFactory.parseFromRequest(request, formClass);

		Validator.validate(form);

		if (form.hasError()) {

			Iterator<String> i = form.errors.keySet().iterator();
			while (i.hasNext()) {
				String key = i.next();
				ValidationError e = form.errors.get(key);
				System.out.println(e.toString());
			}

			T entity = (T) form.toEntity(form, entityClass);
			request.setAttribute(getClassName(entityClass), entity);
			request.setAttribute("form", form);
			return false;
		}
		T entity = (T) form.toEntity(form, entityClass);

		BaseEntity user = (BaseEntity) session.getAttribute("session_user");
		entity.setDefaultFields(user, true);
		setDataFromSession(entity);
		entity.updateViaSql(entity, entityClass);

		return true;
	}

	public void delete(String ids, Class<T> entityClass) {
		String[] idsText = ids.split(",");
		for (int i = 0; i < idsText.length; i++) {
			Finder<T> find = new Finder<T>(entityClass);
			T entity = find.byId(Long.parseLong(idsText[i]));
			entity.setDeleted(true);
			entity.update();
		}
	}

	public void delete(long id, Class<T> entityClass) {
		Finder<T> find = new Finder<T>(entityClass);
		T entity = find.byId(id);
		entity.setDeleted(true);
		entity.update();
	}

	public void detail(HttpServletRequest request, String id,
			Class<T> entityClass) {
		Finder<T> find = new Finder<T>(entityClass);
		T entity = find.byId(Long.parseLong(id));
		request.setAttribute(getClassName(entityClass), entity);
	}

}
