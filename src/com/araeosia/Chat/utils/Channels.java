package com.araeosia.Chat.utils;

public class Channels {
	
	private String prefix;
	private String name;
	private boolean isPrivate;
	private boolean isStaff;
	
	public Channels(String prefix, String name, boolean isPrivate, boolean isStaff) {
		this.prefix = prefix;
		this.name = name;
		this.isPrivate = isPrivate;
		this.isStaff = isStaff;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isPrivate() {
		return isPrivate;
	}
	
	public boolean isStaff() {
		return isStaff;
	}
	
}