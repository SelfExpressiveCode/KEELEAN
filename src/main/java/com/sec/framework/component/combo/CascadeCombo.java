package com.sec.framework.component.combo;

import com.sec.framework.entity.BaseEntity;

public class CascadeCombo<T extends BaseEntity, P extends BaseEntity> {
	public Combo<T> parent;

	private Combo<P> combo;

	// public void list() {
	//
	// }

	public Combo<T> getParent() {
		return parent;
	}

	public void setParent(Combo<T> parent) {
		this.parent = parent;
	}

	public Combo<P> getCombo() {
		return combo;
	}

	public void setCombo(Combo<P> combo) {
		this.combo = combo;
	}

}
