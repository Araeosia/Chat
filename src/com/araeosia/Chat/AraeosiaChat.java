package com.araeosia.Chat;

import java.util.*;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class AraeosiaChat extends JavaPlugin implements Listener{

    static final Logger log = Logger.getLogger("Minecraft");
    static AraeosiaChat plugin;
    IRCBot bot = new IRCBot(this);
    Map<String, Boolean> recieve = new HashMap<String, Boolean>();
    
    // Variables for the IRC bot to use
    public String username;
    public String host;
    public int port=6667;
    public String nick;
    public List<String> channels;
    public boolean identify=false;
    public String identifyPass;
    public String password;
    public boolean debug=false;
    

    @Override
    public void onEnable(){
        loadConfiguration();
        log.info("[AraeosiaChat] Starting up!");
        plugin.debug("log", "[AraeosiaChat] Debug mode enabled!");
        try {
            bot.startBot();
        } catch (Exception e) {
            e.printStackTrace();
        }
	
    }

    @Override
    public void onDisable(){
        log.info("Your plugin has been disabled.");
        log.info("Disabling IRC bot...");
        bot.disconnect();
    }
    @EventHandler
    public void onPlayerEvent(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String output = player.getDisplayName() + e.getMessage();
        plugin.debug("#araeosia", "SOMEONE JUST TRIED TO SEND A MESSAGE!");
        for(String s : channels){
            bot.sendMessage(s, output);
            
        }
    }
    
    private void debug(String channel, String msg) {
		if(debug){
			if(channel.equals("log")){
				log.info( "[AraeosiaServers]:" + msg);
			}
			else{
				bot.sendMessage(channel, msg);
			}
		}
		
	}

	public void sendToServer(String message) {
    	log.info("IRC: " + message);
        for (Player p : plugin.getServer().getOnlinePlayers()){
            if (isRecieveingMessages(p))
                p.sendMessage(message);
        }	
    }

    private boolean isRecieveingMessages(Player p) {
        if(recieve.containsKey(p) && recieve.get(p))
            return true;
        return false;
    }
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
    }

    @Override
    public boolean onCommand(CommandSender sender,  Command cmd, String commandLabel, String[] args){
        if (cmd.getName().equalsIgnoreCase("CC")){
            if (recieve.containsKey(sender) && recieve.get(sender) == true){
                sender.sendMessage(ChatColor.RED + "Cross-Server chat disabled.");
                plugin.debug("log", sender.getName() + " Disabled Cross-Server Chat");
                recieve.put(sender.getName(), false);
                return true;
            } else {
                sender.sendMessage(ChatColor.GREEN + "Cross-Server chat enabled.");
                plugin.debug("log", sender.getName() + " Enabled Cross-Server Chat");
                recieve.put(sender.getName(), true);
                return true;
            }
        }
        return false;	
    }
}
