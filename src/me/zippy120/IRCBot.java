package me.zippy120;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class IRCBot extends PircBot{

    protected CrossServerChat plugin;
    
    public IRCBot(CrossServerChat plugin){
<<<<<<< HEAD
        this.setName("DarkHelmetMainBot");
        this.plugin = plugin;
=======
    	this.plugin = plugin;
        this.setName(plugin.$username);
>>>>>>> 694fa21... I did stuff :D
    }
    
    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message){
        plugin.sendToServer(message);
    }
<<<<<<< HEAD
=======
    public void startBot(){
    // Connect to the IRC server.
        try {
            this.connect(plugin.$username);
        } catch (NickAlreadyInUseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrcException e) {
            e.printStackTrace();
        }
>>>>>>> 694fa21... I did stuff :D

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

       this.setVerbose(false);
       
       // Join the channel.
<<<<<<< HEAD
      this.joinChannel("#araeosia-servers");
=======
       for(String s : plugin.$channels)
    	   this.joinChannel(s);
>>>>>>> 694fa21... I did stuff :D
    }
}