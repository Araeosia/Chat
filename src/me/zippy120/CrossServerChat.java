package me.zippy120;

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
	IRCBot bot = new IRCBot();
	Map<Player, Boolean> recieve = new HashMap<Player, Boolean>();
	
	/**
	 * 
	 */
	@Override
	public void onEnable(){
		log.info("Your plugin has been enabled!");
		log.info("Enabling IRC bot...");

        // Connect to the IRC server.
		try {
			IRCBot.startBot();
		} catch (Exception e) {
			e.printStackTrace();
		}

        // Join the channel.
        bot.joinChannel("#araeosia-servers");
		
	}

	/**
	 * 
	 */
	@Override
	public void onDisable(){
		log.info("Your plugin has been disabled.");
		log.info("Disabling IRC bot...");
		bot.disconnect();
	}
	
	/**
	 * 
	 * @param event
	 */
	public void onPlayerChatEvent(AsyncPlayerChatEvent event){
		String message = event.getFormat() + event.getMessage();
		bot.sendMessage("#araeosia-servers", message);
		
	}


	/**
	 * 
	 * @param message
	 */
	public void sendToServer(String message) {
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
		if(recieve.containsKey(p) && recieve.get(p) == true)
			return true;
		return false;
	}
	/**
	 * 
	 */
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("CC")){
			if (recieve.containsKey(sender)){
				if (recieve.get(sender) == true){
					sender.sendMessage(ChatColor.RED + "Cross-Server chat disabled.");
					recieve.put((Player) sender, false);
					return true;
				} else {
					sender.sendMessage(ChatColor.YELLOW + "Cross-Server chat enabled.");
					recieve.put((Player) sender, true);
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.YELLOW + "Cross-Server chat enabled.");
				recieve.put((Player) sender, true);
				return true;
			}
		}
		return false;
		
	}
}
