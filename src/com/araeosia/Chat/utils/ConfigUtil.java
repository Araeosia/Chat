package com.araeosia.Chat.utils;

import java.util.ArrayList;

import com.araeosia.Chat.AraeosiaChat;

public class ConfigUtil {

	/**
	 * 
	 */
	public static void loadConfiguration(AraeosiaChat plugin){
		boolean configIsCurrentVersion = plugin.getConfig().getDouble("AraeosiaChat.technical.version")==0.1;
		if(!configIsCurrentVersion){
			plugin.getConfig().set("AraeosiaChat.network.host", "irc.esper.net");
			plugin.getConfig().set("AraeosiaChat.network.port", 6667);
			plugin.getConfig().set("AraeosiaChat.network.password", "");
			plugin.getConfig().set("AraeosiaChat.account.username", "MCChatLink");
			plugin.getConfig().set("AraeosiaChat.account.nick", "MCChatLink");
			plugin.getConfig().set("AraeosiaChat.account.identify", false);
			plugin.getConfig().set("AraeosiaChat.account.identifyPass", "");
			plugin.getConfig().set("AraeosiaChat.technical.version", 0.1);
			plugin.getConfig().set("AraeosiaChat.technical.debug", false);
			plugin.getConfig().set("AraeosiaChat.technical.compressOutput", false);
			plugin.getConfig().set("AraeosiaChat.database.url", "jdbc:mysql://localhost:3306/minecraft");
			plugin.getConfig().set("AraeosiaChat.database.user", "minecraft");
			plugin.getConfig().set("AraeosiaChat.database.password", "");
			plugin.getConfig().set("AraeosiaChat.channels", new ArrayList<String>());
			plugin.saveConfig();
		}
		plugin.username = plugin.getConfig().getString("AraeosiaChat.account.username");
		plugin.host = plugin.getConfig().getString("AraeosiaChat.network.host");
		plugin.port = plugin.getConfig().getInt("AraeosiaChat.network.port");
		plugin.nick = plugin.getConfig().getString("AraeosiaChat.account.nick");
		plugin.channels = plugin.getConfig().getStringList("AraeosiaChat.channels");
		plugin.identify = plugin.getConfig().getBoolean("AraeosiaChat.account.identify");
		plugin.identifyPass = plugin.getConfig().getString("AraeosiaChat.account.identifyPass");
		plugin.debug = plugin.getConfig().getBoolean("AraeosiaChat.technical.debug");
		plugin.DBurl = plugin.getConfig().getString("AraeosiaChat.database.url");
		plugin.DBuser = plugin.getConfig().getString("AraeosiaChat.database.user");
		plugin.DBpassword = plugin.getConfig().getString("AraeosiaChat.database.password");
	}
}
