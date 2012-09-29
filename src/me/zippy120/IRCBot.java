package me.zippy120;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class IRCBot extends PircBot{

	protected CrossServerChat plugin;
	
	public IRCBot(CrossServerChat plugin){
		this.setName("AraeosiaBot");
		this.plugin = plugin;
	}
	
    public void onMessage(String channel, String sender, String login, String hostname, String message){
    	plugin.sendToServer(message);
    }

	public void startBot(){
		
        // Connect to the IRC server.
			try {
				this.connect("irc.esper.net");
			} catch (NickAlreadyInUseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IrcException e) {
				e.printStackTrace();
			}

        // Join the channel.
		
      this.setVerbose(true);
	}
}