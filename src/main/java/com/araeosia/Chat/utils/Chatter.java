package com.araeosia.Chat.utils;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Chatter {
	private String name;
	private Channel currentChannel;
	private ArrayList<Channel> channels;
	private boolean gMuted;
	private ArrayList<Channel> mutes;
	private Style style;
	
	public Chatter(String name, Style style){
		this.name = name;
		channels = new ArrayList<>();
		mutes = new ArrayList<>();
		this.style = style;
	}
	public String getName() {
		return name;
	}
	public void setChannel(Channel c){
		this.currentChannel = c;
	}
	public void setChannels(ArrayList<Channel> channels){
		this.channels = channels;
	}
	public Channel getCurrentChannel(){
		return currentChannel;
	}
	public String getDisplayName(){
		if(getPlayer().hasPermission("AraeosiaChat.admin")){
			return "§4"+name;
		}else if(getPlayer().hasPermission("AraeosiaChat.moderator")){
			return "§a"+name;
		}else if(getPlayer().hasPermission("AraeosiaChat.veteran")){
			return "§2"+name;
		}else if(getPlayer().hasPermission("AraeosiaChat.default")){
			return "§b"+name;
		}else{
			return name;
		}
	}
	public void setGMuted(boolean gMuted){
		this.gMuted = gMuted;
	}
	public boolean getGMuted(){
		return gMuted;
	}
	public void joinChannel(Channel channel){
		if(!this.isInChannel(channel)){
			if(currentChannel!=null){
				Channel channel2 = currentChannel;
				channels.add(channel2);
			}
			currentChannel = channel;
		}
	}
	public void leaveChannel(Channel channel){
		if(this.isInChannel(channel)){
			if(this.canLeaveChannel(channel)){
				if(channels.contains(channel)){
					channels.remove(channel);
				}else{
					if(currentChannel.equals(channel)){
						currentChannel = channels.get(0);
					}
				}
			}
		}
	}
	public boolean canLeaveChannel(Channel channel){
		if(channel.isLeaveable() && !channels.isEmpty()){
			return true;
		}
		return false;
	}
	public boolean isInChannel(Channel channel){
		if(currentChannel.equals(channel) || channels.contains(channel)){
			return true;
		}
		return false;
	}
	public boolean isMutedInChannel(Channel channel){
		if(gMuted || mutes.contains(channel)){
			return true;
		}
		return false;
	}
	public Player getPlayer(){
		return Bukkit.getServer().getPlayer(name);
	}
	public Style getStyle(){
		return style;
	}
	public void setStyle(Style s){
		this.style = s;
	}
}
