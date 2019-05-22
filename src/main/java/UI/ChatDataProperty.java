package UI;

import chatcontrol.ChatData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChatDataProperty {
    private StringProperty userID;
    private StringProperty userNickName;
    private StringProperty chatText;
    private BooleanProperty isBadword;
    private BooleanProperty isNamed;

    public ChatDataProperty(ChatData chatData)
    {
        this.userID = new SimpleStringProperty(chatData.getUserID());
        this.userNickName = new SimpleStringProperty(chatData.getUserNickName());
        this.chatText = new SimpleStringProperty(chatData.getChatText());
        this.isBadword = new SimpleBooleanProperty(chatData.getIsBadword());
        this.isNamed = new SimpleBooleanProperty(chatData.getIsNamed());
    }
    public StringProperty getChatText()
    {
        return chatText;
    }
    public StringProperty getUserID()
    {
        return userID;
    }
    public StringProperty getUserNickName()
    {
        return userNickName;
    }
    public BooleanProperty getIsBadword()
    {
        return isBadword;
    }
    public BooleanProperty getIsNamed() {
    	return isNamed; 
    }
    
    public ChatData toChatData() {
    	return new ChatData(this);
    }
}
