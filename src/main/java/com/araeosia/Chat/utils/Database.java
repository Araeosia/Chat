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
	private String table1name;
	private String table2name;

	public Database(AraeosiaChat plugin) {
		this.plugin = plugin;
		if (!initDB()) {
			plugin.log.severe("Could not connect to database! Is it configured properly?");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}
		table1name = plugin.getConfig().getString("AraeosiaChat.database.table1");
		table2name = plugin.getConfig().getString("AraeosiaChat.database.table2");
	}
	
	public void handlePlayerJoin(String playerName){
		initDB();
		try {
			PreparedStatement s = conn.prepareStatement("SELECT * FROM "+table1name + " WHERE name=?");
			s.setString(1, playerName);
			ResultSet rs = s.executeQuery();
			if(!rs.next()){
				PreparedStatement s2 = conn.prepareStatement("INSERT INTO "+table1name+" (name, channel, type) VALUES (?, 'A', '1')");
				s2.setString(1, playerName);
				s2.executeUpdate();
			}
		}catch(SQLException e){
			e.printStackTrace();
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

	public ArrayList<Channel> getSecondaryChannels(String playerName) {
		initDB();
		ArrayList<Channel> output = new ArrayList<>();
		try {
			PreparedStatement query = conn.prepareStatement("SELECT * FROM " + table1name + " WHERE name=? AND type=2");
			query.setString(1, playerName);
			ResultSet result = query.executeQuery();
			while(result.next()){
				output.add(plugin.getChannel(result.getString("channel")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	public Channel getMainChannel(String playerName) {
		initDB();
		try {
			PreparedStatement query = conn.prepareStatement("SELECT * FROM " + table1name + " WHERE name=? AND type=1");
			query.setString(1, playerName);
			ResultSet result = query.executeQuery();
			return plugin.getChannel(result.getString("channel"));
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getMutes(Channel channel){
		initDB();
		ArrayList<String> output = new ArrayList<>();
		try{
			PreparedStatement query = conn.prepareStatement("SELECT * FROM " + table2name + " WHERE channel=? OR channel='allchan'");
			query.setString(1, channel.getName());
			ResultSet result = query.executeQuery();
			while(result.next()){
				output.add(result.getString("name"));
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return output;
	}
}
