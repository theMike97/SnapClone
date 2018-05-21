package floatingheads.snapclone.objects;

/**
 * Created by QuinnSalas on 3/28/18.
 */

public class Message {

    private int ID;
    private String message;
    private String user;
    private long timestamp;
    private String imageURI;

    public Message() {
        // Constructor
    }

    public int getID() {
        return ID;
    }

    public String getMessage() { return message; }

    public String getUser() { return user; }

    public long getTimestamp() { return timestamp; }

    public String getImageURI() { return imageURI; }

    public void setID(int ID) { this.ID = ID; }

    public void setMessage(String message) { this.message = message; }

    public void setUser(String user) { this.user = user; }

    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public void setImageURI(String imageURI) { this.imageURI = imageURI; }

}
