package com.araeosia.Chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ChatListener implements Listener, CommandExecutor {
	private AraeosiaChat plugin;
	
	public ChatListener(AraeosiaChat plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event){
		plugin.database.handlePlayerJoin(event.getPlayer().getName());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("ch")){
			return true;
		}
		return false;
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
	public void onPlayerChatEvent(final AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		plugin.bot.sendMessage(plugin.formatMessage(player, e.getMessage(), false, plugin.getChatter(player.getName()).getCurrentChannel()));
		e.setCancelled(true);
	}
}
