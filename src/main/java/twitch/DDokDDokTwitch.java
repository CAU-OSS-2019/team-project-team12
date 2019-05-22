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
        	if (chatProc.checkUser(event.getActor().getName())) {
        		event.getChannel().sendMessage("안녕하세요! " + event.getActor().getName());
        	}
        }
        @Handler
        public void onMsgFired(ChannelMessageEvent event) {
        	ChatData newChat = new ChatData(event.getActor().getNick(), 
        			event.getActor().getHost(), event.getMessage());
        	chatProc.doProc(newChat);
        	if (newChat.getIsBadword()) {
        		chatDataProperty.add(new ChatDataProperty(newChat));
        	}
        	if (newChat.getHavetoDisplay_Named()) {
        		event.sendReply("안녕하세요! " + event.getActor().getNick() + "님!");
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
	        client.sendMessage(CHANNEL, "똑똑이 입장하였습니다.");
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public ArrayList<ChatDataProperty> banUser(String UserName) {
		ArrayList<ChatDataProperty> ret = new ArrayList<>();
		String banMsg = "/ban " + UserName;
		client.sendMessage(CHANNEL, banMsg);
		for (ChatDataProperty sameUser : chatDataProperty) {
			if (sameUser.getUserID().getValue().equals(UserName)) {
				ret.add(sameUser);
			}
		}
		return ret;
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
	public static void disconnect() {
		if (client != null) {
			System.out.println("Connection End");
			client.shutdown();
		}
		client = null;
	}
}
