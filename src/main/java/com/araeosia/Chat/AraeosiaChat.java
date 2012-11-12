package com.araeosia.Chat;

import com.araeosia.Chat.utils.Channel;
import com.araeosia.Chat.utils.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author Daniel, Bruce
 *
 */
public class AraeosiaChat extends JavaPlugin implements Listener{

	static final Logger log = Logger.getLogger("Minecraft");
	IRCBot bot = new IRCBot(this);
	Map<String, Boolean> recieve = new HashMap<String, Boolean>();
	
	// Variables for the IRC bot to use
	public String username;
	public String host;
	public int port=6667;
	public String nick;
	public boolean identify=false;
	public String identifyPass;
	public String password;
	public boolean debug=false;
	public String DBurl;
	public String DBuser;
	public String DBpassword;
	public Connection conn;
	
	public List<String> channels;
	public Channel[] mcChannels = Channel.values();

	/**
	 * 
	 */
	@Override
	public void onEnable(){
		loadConfiguration();
		this.debug("log", "[AraeosiaChat] Debug mode enabled!");
		this.debug("log", "[AraeosiaChat] Populating chatChannels!");
		try {
			bot.startBot();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			databaseInit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

	/**
	 * 
	 */
	@Override
	public void onDisable(){
		log.info("Your plugin has been disabled.");
		log.info("Disabling IRC bot...");
		bot.disconnect();
		try{ // Close the database connection.
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPlayerChatEvent(final AsyncPlayerChatEvent e){
		Player player = e.getPlayer();
		String output = player.getDisplayName() + e.getMessage();
		this.debug("#araeosia", "SOMEONE JUST TRIED TO SEND A MESSAGE!");
		for(String s : channels){
			bot.sendMessage(s, output);
			if(s.equalsIgnoreCase("#araeosia-servers")){
				bot.sendMessage(s, formatMessage(player, e.getMessage(), false, Database.getChannel(player)));
			}
		}
	}
	
	/**
	 * 
	 * @param e
	 */
	@EventHandler
	// Todo A better way to do this, or more use for this
	public void onPlayerCommandPreProccesEvent(final PlayerCommandPreprocessEvent e){
		if(e.getMessage().startsWith("/me ")){
			Player player = e.getPlayer();
			String output = "* " + player.getDisplayName() + e.getMessage();
			this.debug("#araeosia", "SOMEONE JUST TRIED TO SEND AN EMOTE!");
			for(String s : channels){
				bot.sendMessage(s, output);
				if(s.equalsIgnoreCase("#araeosia-servers")){
					bot.sendMessage(s, formatMessage(player, e.getMessage(), true, Database.getChannel(player)));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param player
	 * @param message
	 * @param emote
	 * @param channel
	 * @return
	 */
	private String formatMessage(Player player, String message, boolean emote, Channel channel) {
		// todo chat channels and stuff
		return "§" + player.getName() + "§" + player.getWorld().getName() + "§" + channel.getName() + "§" + emote + "§" + message;
	}
	
	/**
	 * 
	 * @param message
	 */
	public String handleMessage(String message){
		String[] args = message.split("§");
		String output = "";
		if(!Boolean.parseBoolean(args[4])){
			//output = "§" + Channel.valueOf(args[3]).getColor() + Channel.valueOf(args[3]).getPrefix() + " " + "§f[§9" + args[2] + "§f " + args[1] + "§f: " + args[5];
		} else {
			output = "* " + args[2] + " " + args[5];
		}
		return output;
	}
	
	/**
	 * 
	 * @param channel
	 * @param msg
	 */
	private void debug(String channel, String msg) {
		if(debug){
			if(channel.equals("log")){
				log.info( "[AraeosiaServers]: " + msg);
			} else {
				bot.sendMessage(channel, msg);
			}
		}	
	}
	
	/**
	 * 
	 * @param message
	 */
	public void sendToServer(String message) {
		log.info("IRC: " + message);
		for (Player p : this.getServer().getOnlinePlayers()){
			if (isRecieveingMessages(p))
				p.sendMessage(message);
		}	
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	private boolean isRecieveingMessages(Player p) {
		if(recieve.containsKey(p) && recieve.get(p))
			return true;
		return false;
	}
	
	/**
	 * 
	 */
	public void loadConfiguration(){
		boolean configIsCurrentVersion = getConfig().getDouble("AraeosiaChat.technical.version")==0.1;
		if(!configIsCurrentVersion){
			getConfig().set("AraeosiaChat.network.host", "irc.esper.net");
			getConfig().set("AraeosiaChat.network.port", 6667);
			getConfig().set("AraeosiaChat.network.password", "");
			getConfig().set("AraeosiaChat.account.username", "MCChatLink");
			getConfig().set("AraeosiaChat.account.nick", "MCChatLink");
			getConfig().set("AraeosiaChat.account.identify", false);
			getConfig().set("AraeosiaChat.account.identifyPass", "");
			getConfig().set("AraeosiaChat.technical.version", 0.1);
			getConfig().set("AraeosiaChat.technical.debug", false);
			getConfig().set("AraeosiaChat.technical.compressOutput", false);
			getConfig().set("AraeosiaChat.database.url", "jdbc:mysql://localhost:3306/minecraft");
			getConfig().set("AraeosiaChat.database.user", "minecraft");
			getConfig().set("AraeosiaChat.database.password", "");
			getConfig().set("AraeosiaChat.channels", new ArrayList<String>());
			saveConfig();
		}
		username = getConfig().getString("AraeosiaChat.account.username");
		host = getConfig().getString("AraeosiaChat.network.host");
		port = getConfig().getInt("AraeosiaChat.network.port");
		nick = getConfig().getString("AraeosiaChat.account.nick");
		channels = getConfig().getStringList("AraeosiaChat.channels");
		identify = getConfig().getBoolean("AraeosiaChat.account.identify");
		identifyPass = getConfig().getString("AraeosiaChat.account.identifyPass");
		debug = getConfig().getBoolean("AraeosiaChat.technical.debug");
		DBurl = getConfig().getString("AraeosiaChat.database.url");
		DBuser = getConfig().getString("AraeosiaChat.database.user");
		DBpassword = getConfig().getString("AraeosiaChat.database.password");
	}

	/**
	 * 
	 */
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("CC")){
			if (recieve.containsKey(sender) && recieve.get(sender) == true){
				sender.sendMessage(ChatColor.RED + "Cross-Server chat disabled.");
				this.debug("log", sender.getName() + " Disabled Cross-Server Chat");
				recieve.put(sender.getName(), false);
				return true;
			} else {
				sender.sendMessage(ChatColor.GREEN + "Cross-Server chat enabled.");
				this.debug("log", sender.getName() + " Enabled Cross-Server Chat");
				recieve.put(sender.getName(), true);
				return true;
			}
		}
		return false;	
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void databaseInit() throws SQLException{
		conn = DriverManager.getConnection(DBurl, DBuser, DBpassword);
		PreparedStatement QueryStatement = conn.prepareStatement("YOUR QUERY");
		QueryStatement.executeUpdate();
		QueryStatement.close();
		
	}
}
