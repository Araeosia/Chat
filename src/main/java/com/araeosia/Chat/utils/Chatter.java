package com.araeosia.Chat.utils;

import java.util.ArrayList;

public class Chatter {
	private String name;
	private Channel currentChannel;
	private ArrayList<Channel> channels;
	private boolean gMuted;
	private ArrayList<Channel> mutes;
	
	public Chatter(String name){
		this.name = name;
		channels = new ArrayList<>();
		mutes = new ArrayList<>();
	}
	public void setChannel(Channel c){
		this.currentChannel = c;
	}
	public void setChannels(ArrayList<Channel> channels){
		this.channels = channels;
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
}
