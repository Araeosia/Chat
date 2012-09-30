package com.araeosia.Chat;

import java.io.IOException;


import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class IRCBot extends PircBot{
    protected AraeosiaChat plugin;
    
    public IRCBot(AraeosiaChat plugin){
    this.plugin = plugin;
        this.setName(plugin.username);
    }
    
    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message){
        plugin.sendToServer(message);
    }
    public void startBot(){
   
    // Connect to the IRC server.
        try {
            if(plugin.host!=null && plugin.username!=null && plugin.nick!=null){
                this.setLogin(plugin.username);
                this.setName(plugin.nick);
                this.connect(plugin.host, plugin.port, plugin.password);
            }else{
                AraeosiaChat.log.severe("[AraeosiaChat] Cannot connect to server! Either username, host, or nick wasn't set!");
            }
        } catch (NickAlreadyInUseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrcException e) {
            e.printStackTrace();
        }
       this.setVerbose(plugin.debug);
    }
    
    @Override
    public void onConnect(){
        if(plugin.identify){
            this.identify(plugin.identifyPass);
        }
        for(String s: plugin.channels){
     	    this.joinChannel(s);
        }
    }
}