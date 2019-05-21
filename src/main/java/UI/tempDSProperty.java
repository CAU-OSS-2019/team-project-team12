package UI;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class tempDSProperty {
    private StringProperty userID;
    private StringProperty userNickName;
    private StringProperty chatText;
    private BooleanProperty isBadword;
    private BooleanProperty isNamed;

    public tempDSProperty(String userID, String userNickName, String chatText, boolean isBadword, boolean isNamed)
    {
        this.userID = new SimpleStringProperty(userID);
        this.userNickName = new SimpleStringProperty(userNickName);
        this.chatText = new SimpleStringProperty(chatText);
        this.isBadword = new SimpleBooleanProperty(isBadword);
        this.isNamed = new SimpleBooleanProperty(isNamed);
    }
    public tempDSProperty(tempDS ds)
    {
        this.userID = new SimpleStringProperty(ds.getUserID());
        this.userNickName = new SimpleStringProperty(ds.getUserNickName());
        this.chatText = new SimpleStringProperty(ds.getChatText());
        this.isBadword = new SimpleBooleanProperty(ds.getIsBadword());
        this.isNamed = new SimpleBooleanProperty(ds.getIsNamed());
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
    public BooleanProperty getIsNamed() { return isNamed; }
}


