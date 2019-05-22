package twitch;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.event.channel.ChannelJoinEvent;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.feature.twitch.TwitchSupport;

import UI.ChatDataProperty;
import chatcontrol.ChatData;
import chatcontrol.ChatProc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.engio.mbassy.listener.Handler;
public class DDokDDokTwitch {
	private static final String BOTNAME = "ddok_ddok";
	private static String CHANNEL;
	private static ChatProc chatProc;
	private static Client client;
	
	private static ObservableList<ChatDataProperty> chatDataProperty;
	
	public static class Listener {
        @Handler
        public void onUserJoinChannel(ChannelJoinEvent event) {
        	chatProc.checkUser(event.getActor().getHost());
        }
        @Handler
        public void onMsgFired(ChannelMessageEvent event) {
        	ChatData newChat = new ChatData(event.getActor().getNick(), 
        			event.getActor().getHost(), event.getMessage());
        	chatProc.doProc(newChat);
        	if (newChat.getIsBadword()) {
        		chatDataProperty.add(new ChatDataProperty(newChat));
        	}
        }
    }

	public DDokDDokTwitch(String UserName) {
		CHANNEL = "#" + UserName;
		chatProc = new ChatProc();
		chatDataProperty = FXCollections.observableArrayList();
	}
	
	public boolean connect() {
		try {
			String OAUTH = "oauth:alvosp0wkfaods316j6tbhpbwstr5j";
			if (OAUTH == null)
				return false;
	        client = Client.builder()
	                .server().host("irc.chat.twitch.tv").port(443)
	                .password(OAUTH).then()
	                .realName(BOTNAME)
	                .build();
	        TwitchSupport.addSupport(client);
	        client.connect();
	        client.getEventManager().registerEventListener(new Listener());
	        client.addChannel(CHANNEL);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public void banUser(String UserName) {
		String banMsg = "/ban " + UserName;
		client.sendMessage(CHANNEL, banMsg);
	}
	public ObservableList<ChatDataProperty> getChatDataObservableList() {
		return chatDataProperty;
	}
	// ToDo : Must be Fixed
	public String oauthString() {
		String ret;
		try {
			// OAUTH FILE is needed for another user
			String file = "/src/main/resources/json/twitch.json";
			InputStream is = this.getClass().getResourceAsStream(file);
	        if (is == null) {
	            throw new NullPointerException("Cannot find resource file " + file);
	        }
	        JSONTokener tokener = new JSONTokener(is);
	        JSONObject jsonObject = new JSONObject(tokener);
	        ret = (String) jsonObject.get("OAUTH");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}
}
