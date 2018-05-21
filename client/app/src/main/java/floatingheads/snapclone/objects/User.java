package floatingheads.snapclone.objects;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mike on 2/26/18.
 */

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Bitmap avatar;

    /**
     * Default constructor initialized all objects to null and id to -1
     */
    public User() {
        id = -1;
        firstName = null;
        lastName = null;
        username = null;
        email = null;
        avatar = null;
    }

    /**
     * Calls User() and sets id
     * @param id
     */
    public User(int id) {
        this();
        this.id = id;
    }

    /**
     * Calls User(int) and sets firstName and lastName
     * @param id
     * @param firstName
     * @param lastName
     */
    public User(int id, String firstName, String lastName) {
        this(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Calls User(int, String, String) and sets username
     * @param id
     * @param firstName
     * @param lastName
     * @param username
     */
    public User(int id, String firstName, String lastName, String username) {
        this(id, firstName, lastName);
        this.username = username;
    }

    /**
     * Calls User(int, String, String, String) and sets email
     * @param id
     * @param firstName
     * @param lastName
     * @param username
     * @param email
     */
    public User(int id, String firstName, String lastName, String username, String email) {
        this(id, firstName, lastName, username);
        this.email = email;
    }

    /**
     * Calls User(int, String, String, String, String) and sets avatar
     * @param id
     * @param firstName
     * @param lastName
     * @param username
     * @param email
     * @param avatar
     */
    public User(int id, String firstName, String lastName, String username, String email, Bitmap avatar) {
        this(id, firstName, lastName, username, email);
        this.avatar = avatar;
    }

    /**
     * Sets user's id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets user's first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets user's last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets user's username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets user's email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets user's avatar (WIP)
     */
    public void setAvatar() {
        this.avatar = avatar;
    }

    /**
     * Returns user's id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Returns user's first name
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns user's last name
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns user's username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns user's email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns user's avatar
     * @return
     */
    public Bitmap getAvatar() {
        return avatar;
    }

    /**
     * Returns String representation of User object in format "User: id, firstName, lastName, username, email"
     * @return
     */
    @Override
    public String toString() {
        return "User: " + getId() + ", " + getFirstName() + ", " + getLastName() + ", " + getUsername() + ", " + getEmail();
    }
}
