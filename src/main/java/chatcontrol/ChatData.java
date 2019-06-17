package chatcontrol;

import UI.ChatDataProperty;

public class ChatData {
	private String userID;
	private String userNickName;
	private String chatText;
	private boolean isBadword;
	private boolean isNamed;
	private boolean havetoDisplay_Named;
	private boolean KKevent;

	//For Youtube
	private String channelID;
	private String youtubeLiveChatID;


	public ChatData(String userID, String userNickName, String chatText)
	{
		this.userID = userID;
		this.userNickName = userNickName;
		this.chatText = chatText;
		this.isBadword = false;
		this.isNamed = false;
		this.havetoDisplay_Named = false;
	}
	public ChatData(ChatDataProperty chatDataProperty) {
		this.userID = chatDataProperty.getUserID().getValue();
		this.userNickName = chatDataProperty.getUserNickName().getValue();
		this.chatText = chatDataProperty.getChatText().getValue();
		this.isBadword = chatDataProperty.getIsBadword().getValue();
		this.isNamed = chatDataProperty.getIsNamed().getValue();
		this.havetoDisplay_Named = false;
	}
	public ChatData(String userID, String userNickName, String chatText,String channelID, String youtubeLiveChatID)
	{
		this.userID = userID;
		this.userNickName = userNickName;
		this.chatText = chatText;
		this.isBadword = false;
		this.isNamed = false;
		this.havetoDisplay_Named = false;
		this.KKevent = false;
		this.channelID = channelID;
		this.youtubeLiveChatID = youtubeLiveChatID;
	}
	
	public String getChatText()
	{
		return chatText;
	}
	public String getUserID()
	{
		return userID;
	}
	public String getUserNickName()
	{
		return userNickName;
	}
	public boolean getIsBadword()
	{
		return isBadword;
	}
	public void setIsBadword(boolean isBadword)
	{
		this.isBadword=isBadword;
	}
	public void setIsNamed(boolean isNamed)
	{
		this.isNamed = isNamed;
	}
	public boolean getIsNamed()
	{
		return isNamed;
	}
	public void setHavetoDisplay_Named(boolean tf)
	{
		this.havetoDisplay_Named = tf;
	}
	public boolean getHavetoDisplay_Named()
	{
		return havetoDisplay_Named;
	}
	public void setKKevent(boolean tf)
	{
		this.KKevent = true;
	}
	public boolean getKKevent()
	{
		return this.KKevent;
	}

}
