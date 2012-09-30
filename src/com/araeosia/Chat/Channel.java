package com.araeosia.Chat;

public enum Channel {
	GLOBAL("[g]", "2"), LOCAL("[l]", "f"); //ADD MORE D:
	
	private String prefix;
	private String color;
	
	private Channel(String pre, String color){
		this.prefix = pre;
		this.color = color;
	}
	
	public String getPrefix(){
		return prefix;
	}
	
	public String getColor(){
		return color;
	}
	
}
