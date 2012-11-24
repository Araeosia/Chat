package com.araeosia.Chat;

import com.araeosia.Chat.utils.Channel;
import com.araeosia.Chat.utils.Chatter;
import com.araeosia.Chat.utils.Database;

import java.util.*;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Daniel, Bruce
 *
 */
public class AraeosiaChat extends JavaPlugin{

	public Logger log = Logger.getLogger("Minecraft");
	public IRCBot bot;
	public String DBurl;
	public String DBuser;
	public String DBpassword;
	public Database database;
	public ArrayList<Channel> channels = new ArrayList<Channel>();
	public ArrayList<Chatter> chatters = new ArrayList<Chatter>();

	@Override
	public void onEnable() {
		loadConfiguration();
		database = new Database(this);
		database.initDB();
		bot = new IRCBot(this);

	}

	@Override
	public void onDisable() {
		log.info("Your plugin has been disabled.");
		log.info("Disabling IRC bot...");
	}

	public String formatMessage(Player player, String message, boolean emote, Channel channel) {
		// todo chat channels and stuff
		return getChatter(player.getName()).getDisplayName() + "§" + player.getWorld().getName() + "§" + channel.getName() + "§" + emote + "§" + message;
	}

	public String handleMessage(String message) {
		String[] args = message.split("§");
		String output = "";
		Channel channel = getChannel(args[2]);
		if (!Boolean.parseBoolean(args[4])) {
			// The custom format code will go here.
			output = "§" + channel.getPrefix() + channel.getAbbreviation() + "]§f " + args[0] + "§f: " + args[4];
		} else {
			output = "§" + channel.getPrefix() + channel.getAbbreviation() + "]§f * " + args[0] + " " + args[4];
		}
		return output;
	}

	public void loadConfiguration() {
		boolean configIsCurrentVersion = getConfig().getDouble("AraeosiaChat.technical.version") == 0.1;
		if (!configIsCurrentVersion) {
			getConfig().set("AraeosiaChat.network.host", "irc.esper.net");
			getConfig().set("AraeosiaChat.network.port", 6667);
			getConfig().set("AraeosiaChat.network.password", "");
			getConfig().set("AraeosiaChat.account.nick", "MCChatLink");
			getConfig().set("AraeosiaChat.account.identify", false);
			getConfig().set("AraeosiaChat.account.identifyPass", "");
			getConfig().set("AraeosiaChat.technical.version", 0.1);
			getConfig().set("AraeosiaChat.technical.debug", false);
			getConfig().set("AraeosiaChat.technical.compressOutput", false);
			getConfig().set("AraeosiaChat.database.url", "jdbc:mysql://192.168.5.106:3306/Araeosia");
			getConfig().set("AraeosiaChat.database.user", "chat");
			getConfig().set("AraeosiaChat.database.password", "");
			getConfig().set("AraeosiaChat.database.table1", "ChannelsIn");
			getConfig().set("AraeosiaChat.network.channel", "#araeosia");
			saveConfig();
		}
		DBurl = getConfig().getString("AraeosiaChat.database.url");
		DBuser = getConfig().getString("AraeosiaChat.database.user");
		DBpassword = getConfig().getString("AraeosiaChat.database.password");
	}

	public Chatter getChatter(String playerName) {
		for (Chatter c : chatters) {
			if (c.getName().equals(playerName)) {
				return c;
			}
		}
		return null;
	}

	public Channel getChannel(String name) {
		for (Channel c : channels) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
}
