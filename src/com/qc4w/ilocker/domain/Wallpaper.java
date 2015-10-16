package com.qc4w.ilocker.domain;

import java.io.Serializable;

public class Wallpaper implements Serializable {

	private static final long serialVersionUID = -6377020424041427391L;
	private int id;
	private String path;
	private boolean isPreSet;
	private boolean isCurrent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isPreSet() {
		return isPreSet;
	}

	public void setPreSet(boolean isPreSet) {
		this.isPreSet = isPreSet;
	}

	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

}
