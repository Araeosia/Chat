package com.araeosia.Chat.utils;

/**
 * 
 * @author Daniel, Bruce
 *
 */
public enum Channel {
	
	
	Araeosia("[A]", "Araeosia", false, false);
	//todo
	
	
	private String prefix;
	private String name;
	private boolean isPrivate;
	private boolean isStaff;
	
	/**
	 * 
	 * @param prefix
	 * @param name
	 * @param isPrivate
	 * @param isStaff
	 */
	private Channel(String prefix, String name, boolean isPrivate, boolean isStaff) {
		this.prefix = prefix;
		this.name = name;
		this.isPrivate = isPrivate;
		this.isStaff = isStaff;
	}
	
	/**
	 * 
	 * @return prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return isPrivate
	 */
	public boolean isPrivate() {
		return isPrivate;
	}
	
	/**
	 * 
	 * @return isStaff
	 */
	public boolean isStaff() {
		return isStaff;
	}
	
}