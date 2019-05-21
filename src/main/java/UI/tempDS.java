package UI;

public class tempDS {
    private String userID;
    private String userNickName;
    private String chatText;


    private boolean isBadword;
    private boolean isNamed;

    public tempDS(String userID, String userNickName, String chatText)
    {
        this.userID = userID;
        this.userNickName = userNickName;
        this.chatText = chatText;
        this.isBadword = false;
        this.isNamed = false;
    }
    public void setIsBadword(boolean badword) {
        isBadword = badword;
    }
    public void setIsNamed(boolean isNamed)
    {
        this.isNamed = isNamed;
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
    public boolean getIsNamed()
    {
        return isNamed;
    }

}

