package floatingheads.snapclone.objects;

import android.graphics.Bitmap;

/**
 * Created by Mike on 2/24/18.
 */

public class Contact extends Friend {

    private int userID;
    private String userFirstName;
    private String userLastName;
    private String lastMessage;
    private Bitmap avatar;

    /**
     * Default constructor calls Friend constructor
     */
    public Contact() {
        super();
    }

    /**
     * Constructor calls Friend(int, String, String, String) constructor
     * @param userID
     * @param userFirstName
     * @param userLastName
     * @param lastMessage
     */
    public Contact(int userID, String userFirstName, String userLastName, String lastMessage) {
        super(userID, userFirstName,userLastName, Friend.STATUS_ACCEPTED);
        this.lastMessage = lastMessage;
        this.avatar = null;
    }

    /**
     * Constructor calls Friend(int, String, String, String) constructor and initializes avatar Bitmap
     * @param userID
     * @param userFirstName
     * @param userLastName
     * @param lastMessage
     * @param avatar
     */
    public Contact(int userID, String userFirstName, String userLastName, String lastMessage, Bitmap avatar) {
        this(userID, userFirstName, userLastName, lastMessage);
        this.avatar = avatar;
    }

    /**
     * Sets last message sent by contact to user
     * @param lastMessage
     */
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    /**
     * Returns last message sent by contact to user
     * @return
     */
    public String getLastMessage() {
        return lastMessage;
    }

}
