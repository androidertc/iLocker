package com.qc4w.ilocker.domain;

public class NotificationApp {

	private int id;
	private String label;
	private String pkgName;
	private boolean showNotification;
	private boolean hasActivity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public boolean isShowNotification() {
		return showNotification;
	}

	public void setShowNotification(boolean showNotification) {
		this.showNotification = showNotification;
	}

	public boolean isHasActivity() {
		return hasActivity;
	}

	public void setHasActivity(boolean hasActivity) {
		this.hasActivity = hasActivity;
	}

	
}
