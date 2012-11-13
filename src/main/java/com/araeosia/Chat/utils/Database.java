package com.araeosia.Chat.utils;

import com.araeosia.Chat.AraeosiaChat;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Bruce, Daniel
 */
public class Database {

	private Connection conn;
	private AraeosiaChat plugin;

	public Database(AraeosiaChat plugin) {
		this.plugin = plugin;
		if (!initDB()) {
			plugin.log.severe("Could not connect to database! Is it configured properly?");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}
	}

	public boolean initDB() {
		try {
			if (conn == null || !conn.isValid(0)) { // Does the connection exist? Is it active?
				// Connect to the database
				conn = DriverManager.getConnection(
						plugin.getConfig().getString("AraeosiaChat.database.url"),
						plugin.getConfig().getString("AraeosiaChat.database.user"),
						plugin.getConfig().getString("AraeosiaChat.database.password"));
				if (!conn.isValid(0)) { // check if the connection is now valid
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public ArrayList<Channel> getChannels(String playerName){
		PreparedStatement query = conn.prepareStatement
	}
	public Channel getMainChannel(String playerName){
		
	}
}
