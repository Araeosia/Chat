package me.zippy120;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CrossServerChat extends JavaPlugin{
	
    static final Logger log = Logger.getLogger("Minecraft");
    static CrossServerChat plugin;
    IRCBot bot = new IRCBot(this);
    Map<Player, Boolean> recieve = new HashMap<Player, Boolean>();

    @Override
    public void onEnable(){
        log.info("Your plugin has been enabled!");
        log.info("Enabling IRC bot...");

        // Connect to the IRC server.
        try {
           bot.startBot();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Join the channel.
        bot.joinChannel("#araeosia-servers");
	
    }

    @Override
    public void onDisable(){
        log.info("Your plugin has been disabled.");
        log.info("Disabling IRC bot...");
        bot.disconnect();
    }
    public void onPlayerChatEvent(AsyncPlayerChatEvent event){
        String message = event.getFormat() + event.getMessage();
        bot.sendMessage("#araeosia-servers", message);
    }
    public void sendToServer(String message) {
        for (Player p : plugin.getServer().getOnlinePlayers()){
            if (isRecieveingMessages(p))
                p.sendMessage(message);
        }	
    }

    private boolean isRecieveingMessages(Player p) {
        if(recieve.containsKey(p) && recieve.get(p) == true)
            return true;
        return false;
    }
}
