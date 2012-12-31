package com.araeosia.Chat;

import com.araeosia.Chat.AraeosiaChat.MsgType;
import com.araeosia.Chat.utils.Channel;
import com.araeosia.Chat.utils.Chatter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener, CommandExecutor {

	private AraeosiaChat plugin;

	public ChatListener(AraeosiaChat plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		plugin.database.handlePlayerJoin(event.getPlayer().getName());
		plugin.bot.sendMessage(plugin.formatMessage(MsgType.JOIN, plugin.serverName, plugin.getChatter(event.getPlayer().getName())));
	}

	@EventHandler
	public void onPlayerLeave(final PlayerQuitEvent event) {
		plugin.bot.sendMessage(plugin.formatMessage(MsgType.LEAVE, plugin.serverName, plugin.getChatter(event.getPlayer().getName())));
		if(plugin.chatters.contains(plugin.getChatter(event.getPlayer().getName()))){
			plugin.chatters.remove(plugin.getChatter(event.getPlayer().getName()));
		}
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ch")) {
			if(args.length==0 || args[0].equalsIgnoreCase("help")){
				sendHelp(cs);
			}else if(args[0].equalsIgnoreCase("style")){
				
			}else if(args[0].equalsIgnoreCase("styles")){
				
			}else if(args[0].equalsIgnoreCase("join")){
				
			}else if(args[0].equalsIgnoreCase("leave")){
				
			}else if(args[0].equalsIgnoreCase("who")){
				
			}else if(args[0].equalsIgnoreCase("list")){
				
			}else if(cs.hasPermission("AraeosiaChat.admin")){
				if(args[0].equalsIgnoreCase("debug")){
					
				}else if(args[0].equalsIgnoreCase("put")){
					
				}else if(args[0].equalsIgnoreCase("nuke")){
					
				}else if(args[0].equalsIgnoreCase("mute")){
					
				}else if(args[0].equalsIgnoreCase("gmute")){
					
				}else if(args[0].equalsIgnoreCase("kick")){
					
				}else{
					sendHelp(cs);
				}
			}else{
				sendHelp(cs);
			}
			return true;
		}
		return false;
	}
	
	private void sendHelp(CommandSender cs){
		String[] helps = new String[7];
		helps[0] = "/ch help - Display this help message.";
		helps[1] = "/ch style [Style#] - Choose a chat style.";
		helps[2] = "/ch styles - List avaiable styles.";
		helps[3] = "/ch join [Channel] - Join a channel.";
		helps[4] = "/ch leave (Channel) - Leave a channel (or this one).";
		helps[5] = "/ch who - Show people in this channel.";
		helps[6] = "/ch list - List available channels.";
		cs.sendMessage(helps);
		if(cs.hasPermission("AraeosiaChat.admin")){
			String[] adminHelp = new String[6];
			adminHelp[0] = "/ch debug - Debug toggle";
			adminHelp[1] = "/ch put [Player] [Channel]";
			adminHelp[2] = "/ch nuke - Nuke the bastards.";
			adminHelp[3] = "/ch mute [Player] (Channel) - Mute a player in a channel (or this one).";
			adminHelp[4] = "/ch gmute [Player] - Globally mute a player.";
			adminHelp[5] = "/ch kick [Player] (Channel) - Kick a player from a channel (or this one).";
			cs.sendMessage(adminHelp);
		}
	}

	@EventHandler
	// Todo A better way to do this, or more use for this
	public void onPlayerCommandPreProccesEvent(final PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/me ")) {
			Player player = e.getPlayer();
			String output = "* " + player.getDisplayName() + e.getMessage();

			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerChatEvent(final PlayerChatEvent e) {
		Chatter chatter = plugin.getChatter(e.getPlayer().getName());
		Channel c = chatter.getCurrentChannel();
		plugin.bot.sendMessage(plugin.formatMessage(MsgType.MESSAGE, e.getMessage(), chatter));
		plugin.handleLocalMessage(MsgType.MESSAGE, e.getMessage(), chatter);
		e.setCancelled(true);
	}
}
