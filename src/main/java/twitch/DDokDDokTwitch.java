package twitch;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
	private static LocalDateTime startTime;
	private static ObservableList<ChatDataProperty> chatDataProperty;
	private static LocalDateTime lastKKtime; //for KK event
	private static long bestKKtime; //for KK event
	
	public static class Listener {
		private Random random = new Random();
        @Handler
        public void onUserJoinChannel(ChannelJoinEvent event) {
        	if (chatProc.checkUser(event.getActor().getName())) {
        		event.getChannel().sendMessage("안녕하세요! " + event.getActor().getName());
        	}
        }
        @Handler
        public void onMsgFired(ChannelMessageEvent event) {
        	if (event.getMessage().charAt(0) == '!') {
				if (handleCustomCommand(event))
					return;
			}
        	ChatData newChat = new ChatData(event.getActor().getNick(), 
        			event.getActor().getHost(), event.getMessage());
        	chatProc.doProc(newChat);
        	if (newChat.getIsBadword()) {
        		chatDataProperty.add(new ChatDataProperty(newChat));
        	}
        	if (newChat.getHavetoDisplay_Named()) {
        		event.sendReply("안녕하세요! " + event.getActor().getNick() + "님!");
        	}
        	/////////
        	if(newChat.getKKevent())
        	{
        		event.sendReply("500개의 웃음이 수집되었습니다");
        		long timefromlastkktime = getTime(lastKKtime); //마지막 lastkktime부터 지난 시간
        		if(timefromlastkktime<bestKKtime) //더 짧은 기간 안에 500개의 웃음이 카운트되었다면
        		{
        			event.sendReply("오늘 최고 호응!!");
        			bestKKtime = timefromlastkktime;
        		}
        		
        		lastKKtime = LocalDateTime.now();
        	}
        	/////
        }
        
        ////이부분 따로 함수로 뺐어용 다시쓸라고
        private long getTime(LocalDateTime start)
        {
        	LocalDateTime uptime = LocalDateTime.now();
        	Duration duration = Duration.between(start, uptime);
        	return Math.abs(duration.getSeconds());
        }
        
        private boolean handleCustomCommand(ChannelMessageEvent event) {
        	String message = event.getMessage();
        	String command = message.replaceFirst("!", "");

			if (command.equals("업타임")) {
				long seconds = getTime(startTime);
				String uptimeString = String.format("%02d시간 %02d분 %02d초",
						seconds / 3600, (seconds % 3600) / 60, seconds % 60);
				event.sendReply(uptimeString);
			}
			else if (command.equals("목록")) {
				String ret = "업타입, 목록, 주사위, 추가";
				for (String custom: chatProc.getDataTable().getCustomCommand().keySet()) {
					ret += ", " + custom;
				}
				event.sendReply(ret);
			}
			else if (command.substring(0,3).equals("주사위")) {
				String[] splitted_command = command.split(" ");
				int sides = 6;
				if (splitted_command.length == 2) {
					try{
						sides = Integer.valueOf(splitted_command[1]);
					}
					catch (Exception e) { //If second value is not integer
						return false;
					}
				}
				else if (splitted_command.length > 2) {
					return false;
				}
				String ret = String.valueOf(random.nextInt(sides) + 1);
				event.sendReply("주사위에서 " + ret + "이 나왔습니다!");
			}
			else if (command.substring(0, 2).equals("추가"))
			{
				if(!event.getActor().getNick().equals(CHANNEL.replaceFirst("#","")))
					return false;
				String newCommand = chatProc.getDataTable().AddToCustomCommand(command);
				if (newCommand==null)
					return false;
				else
					event.sendReply(newCommand +  " 명령어가 추가되었습니다!");
			}
			else if (chatProc.getDataTable().getCustomCommand().keySet().contains(command)){
				event.sendReply(chatProc.getDataTable().getCustomCommand().get(command));
			}
			else {
				return false;
			}
        	return true;
		}
    }
	///lastKKtime하고 bestKKtime초기화 (best는 매우 큰 값으로)
	public DDokDDokTwitch(String UserName) {
		CHANNEL = "#" + UserName;
		chatProc = new ChatProc();
		chatDataProperty = FXCollections.observableArrayList();
		startTime = LocalDateTime.now();
		lastKKtime = startTime;
		bestKKtime = 100000000;
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
