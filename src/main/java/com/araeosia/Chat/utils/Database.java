package com.araeosia.Chat.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;

/**
 * 
 * @author Bruce, Daniel
 */
public class Database {
	
	/**
	 * 
	 * @param player
	 * @return player's Channel
	 */
	public static Channel getChannel(Player player){
		//TODO
		return Channel.valueOf("Araeosia");
		/*
		String channel;
		try {
			if (plugin.conn == null || plugin.conn.isClosed()) plugin.dbc();
            PreparedStatement s = plugin.conn.prepareStatement ("SELECT channel FROM channels WHERE player=? ");
            s.setString(1, player.getName());
    		s.executeQuery();
    		ResultSet rs = s.getResultSet();
    		channel = rs.first();
    		rs.close();
    		s.close();
            plugin.conn.close();
            
            } catch (SQLException e){
            	e.printStackTrace();
            }
		 
		 try {
		 	Channel ch = Channel.valueOf(channel);
		 	return ch
		 } catch(Exception e){
		 	e.printStackTrace();
		 }
		 return Channel.Araeosia;
		 
		 */
	}
	
}
