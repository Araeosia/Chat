package com.araeosia.Chat;

import java.io.IOException;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class IRCBot extends ListenerAdapter implements Listener {

	private AraeosiaChat plugin;
	private PircBotX bot;
	private Channel channel;

	public IRCBot(AraeosiaChat plugin) {
		this.plugin = plugin;
		bot = new PircBotX();
		bot.setName(plugin.getConfig().getString("AraeosiaChat.account.nick"));
		try {
			bot.connect(plugin.getConfig().getString("AraeosiaChat.network.host"), plugin.getConfig().getInt("AraeosiaChat.network.port"));
			if(plugin.getConfig().getBoolean("AraeosiaChat.account.identify")){
				bot.identify(plugin.getConfig().getString("AraeosiaChat.account.identifyPass"));
			}
			bot.joinChannel(plugin.getConfig().getString("AraeosiaChat.network.channel"));
			channel = bot.getChannel(plugin.getConfig().getString("AraeosiaChat.network.channel"));
		} catch (IOException | IrcException e) {
			e.printStackTrace();
		}
	}
	public void disconnectIRC(){
		bot.partChannel(channel, "Shutting down...");
		bot.disconnect();
	}
	public void sendMessage(String s){
		bot.sendMessage(channel, s);
	}
	@Override
	public void onMessage(MessageEvent event){
		if(event.getChannel().equals(channel)){
			plugin.handleMessage(event.getMessage());
		}
	}
}