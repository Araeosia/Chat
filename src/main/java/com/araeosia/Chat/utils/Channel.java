package com.araeosia.Chat.utils;

import java.util.ArrayList;

public class Channel {
	
	
	private String prefix;
	private String name;
	private boolean isPrivate;
	private boolean isStaff;
	private boolean isLeaveable;
	private ArrayList<Chatter> chatters = new ArrayList<>();
	
	public Channel(String prefix, String name, boolean isPrivate, boolean isStaff, boolean isLeaveable) {
		this.prefix = prefix;
		this.name = name;
		this.isPrivate = isPrivate;
		this.isStaff = isStaff;
		this.isLeaveable = isLeaveable;
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
	
	public boolean isLeaveable() {
		return isLeaveable;
	}
	
	public ArrayList<Chatter> getChatters(){
		return chatters;
	}
}