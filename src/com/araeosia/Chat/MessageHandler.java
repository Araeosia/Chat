package com.araeosia.Chat;

import org.bukkit.entity.Player;

public class MessageHandler {
	
	static AraeosiaChat plugin = new AraeosiaChat();

	/**
	 * 
	 * @param player
	 * @param message
	 */
	public static void handleGameMessage(Player player, String message) {
		String output = player.getDisplayName() + ": " + message;
		plugin.debug("#araeosia", "SOMEONE JUST TRIED TO SEND A MESSAGE!");
		plugin.sendIrcFormatted(output, player);
	}
	
	/**
	 * 
	 * @param player
	 * @param message
	 */
	public static void handleGameAction(Player player, String message) {
		String output = "* " + player.getName() + message;
		plugin.debug("#araeosia", "SOMEONE JUST TRIED TO SEND AN EMOTE!");
		plugin.sendIrcFormatted(output, player);
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	public static String handleMessage(String message) {
		// TODO Auto-generated method stub
		return null;
	}
}
