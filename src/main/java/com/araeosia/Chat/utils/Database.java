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
	private String table3name;

	public Database(AraeosiaChat plugin) {
		this.plugin = plugin;
		if (!initDB()) {
			plugin.log.severe("Could not connect to database! Is it configured properly?");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}
		table1name = plugin.getConfig().getString("AraeosiaChat.database.table1");
		table2name = plugin.getConfig().getString("AraeosiaChat.database.table2");
		table3name = plugin.getConfig().getString("AraeosiaChat.database.table3");
	}

	public void handlePlayerJoin(String playerName) {
		initDB();
		try {
			PreparedStatement s = conn.prepareStatement("SELECT * FROM " + table1name + " WHERE name=? AND type='1'");
			s.setString(1, playerName);
			ResultSet rs = s.executeQuery();
			if (!rs.next()) {
				PreparedStatement s2 = conn.prepareStatement("INSERT INTO " + table1name + " (name, channel, type) VALUES (?, 'A', '1')");
				s2.setString(1, playerName);
				s2.executeUpdate();
				Chatter cha = new Chatter(playerName, Style.valueOf(rs.getString("style")));
				cha.setChannel(plugin.getChannel("A"));
				plugin.chatters.add(cha);
			} else {
				Chatter cha = new Chatter(playerName, Style.A);
				cha.setChannel(plugin.getChannel(rs.getString("channel")));
				ArrayList<Channel> channels = new ArrayList<>();
				PreparedStatement s2 = conn.prepareStatement("SELECT * FROM " + table1name + " WHERE name=? AND type='2'");
				s2.setString(1, playerName);
				ResultSet rs2 = s2.executeQuery();
				while (rs2.next()) {
					Channel toAdd = plugin.getChannel(rs2.getString("channel"));
					channels.add(toAdd);
				}
				cha.setChannels(channels);
				plugin.chatters.add(cha);
			}
		} catch (SQLException e) {
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
			while (result.next()) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getMutes(Channel channel) {
		initDB();
		ArrayList<String> output = new ArrayList<>();
		try {
			PreparedStatement query = conn.prepareStatement("SELECT * FROM " + table2name + " WHERE channel=? OR channel='allchan'");
			query.setString(1, channel.getName());
			ResultSet result = query.executeQuery();
			while (result.next()) {
				output.add(result.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	public void joinChannel(String playerName, Channel channel) {
		initDB();
		try {
			PreparedStatement s = conn.prepareStatement("UPDATE " + table1name + " SET type=2 WHERE name=?");
			s.setString(1, playerName);
			s.executeUpdate();
			PreparedStatement s2 = conn.prepareStatement("INSERT INTO " + table1name + " (name, channel, type) VALUES (?, ?, '1')");
			s2.setString(1, playerName);
			s2.setString(2, channel.getAbbreviation());
			s2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setFocus(String playerName, Channel channel) {
		initDB();
		try {
			PreparedStatement s = conn.prepareStatement("UPDATE " + table1name + " SET type=2 WHERE name=?");
			s.setString(1, playerName);
			s.executeUpdate();
			PreparedStatement s2 = conn.prepareStatement("INSERT INTO " + table1name + " (name, channel, type) VALUES (?, ?, '1')");
			s2.setString(1, playerName);
			s2.setString(2, channel.getAbbreviation());
			s2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void leaveChannel(String playerName, Channel channel) {
		initDB();
		try {
			PreparedStatement s = conn.prepareStatement("DELETE FROM "+table1name+" WHERE name=? AND channel=?");
			s.setString(1, playerName);
			s.setString(2, channel.getAbbreviation());
			s.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setStyle(String playerName, Style style) {
		initDB();
		try {
			PreparedStatement s = conn.prepareStatement("UPDATE "+table3name+" SET style=? WHERE name=?");
			s.setString(1, style.techString());
			s.setString(2, playerName);
			s.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
