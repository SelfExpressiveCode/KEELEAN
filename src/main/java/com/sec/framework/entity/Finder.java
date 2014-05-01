package com.sec.framework.entity;

import java.util.List;
import java.util.Set;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;

public class Finder<T extends BaseEntity> {

	private Class<T> entityClass;

	private String serverName;

	public Finder(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public Finder(Class<T> entityClass, String serverName) {
		this.entityClass = entityClass;
		this.serverName = serverName;
	}

	protected EbeanServer server() {
		return Ebean.getServer(serverName);
	}

	public T byId(Long id) {
		return (T) server().find(entityClass, id);
	}

	public List<T> all() {
		return server().find(entityClass).where().findList();
	}

	public Set<T> distinct() {
		return server().find(entityClass).where().findSet();
	}

	public int count() {
		return server().find(entityClass).findRowCount();
	}

	public List<T> alive() {
		return server().find(entityClass).where().eq("deleted", false)
				.findList();
	}

	public ExpressionList<T> where() {
		return server().find(entityClass).where();
	}

	public Query<T> fetch(String... paths) {
		Query<T> query = server().find(entityClass);
		for (String path : paths) {
			query = query.fetch(path);
		}
		return query;
	}

	public int countAlive() {
		return server().find(entityClass).where().eq("deleted", false)
				.findRowCount();
	}

}
