package floatingheads.snapclone.objects;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by Mike on 2/23/18.
 */

public class Friend extends User implements Comparable<Friend> {

    // will incorporate avatar image soon hopefully

    public static final int STATUS_PENDING = 0, STATUS_ACCEPTED = 1, STATUS_REJECTED = 2, STATUS_BLOCKED = 3;
    private int status;


    /**
     * Constructor calls default User constructor
     */
    public Friend() {
        super();
    }

    /**
     * Constructor calls default constructor and initialized variables using User methods
     * @param userID
     * @param userFirstName
     * @param userLastName
     * @param status
     */
    public Friend(int userID, String userFirstName, String userLastName, int status) {
        this();
        setId(userID);
        setFirstName(userFirstName);
        setLastName(userLastName);
        this.status = status;
        setAvatar();
    }

    /**
     * Constructor calls default constructor and initialized variables using User methods
     * Initializes avatar
     * @param userID
     * @param userFirstName
     * @param userLastName
     * @param status
     * @param avatar
     */
    public Friend(int userID, String userFirstName, String userLastName, int status, Bitmap avatar) {
        this(userID, userFirstName, userLastName, status);
        setAvatar();
    }

    public void toggleStatusPending() {

    }

    public void toggleStatusAccepted() {

    }

    public void toggleStatusRejected() {

    }

    public void toggleStatusBlocked() {

    }

    public boolean isStatusPending() {
        return status == STATUS_PENDING;
    }

    public boolean isStatusAccepted() {
        return status == STATUS_ACCEPTED;
    }

    public boolean isStatusRejected() {
        return status == STATUS_REJECTED;
    }

    public boolean isStatusBlocked() {
        return status == STATUS_BLOCKED;
    }

    /**
     * Compared friends based on alphabetical order of names
     * A name is the friends first and last name
     *
     * Returns -1 if friend 1 comes before friend 2 (o)
     * Returns 1 if friend 1 comes after friend 2 (o)
     * Returns 0 if friends names are identical
     *
     * @param o
     * @return
     */
//    @Override
    public int compareTo(@NonNull Friend o) {

        String name1 = getFirstName() + getLastName();
        Friend f;

        if (o instanceof Friend) {
            f = (Friend) o;

            String name2 = f.getFirstName() + f.getLastName();

            if (name1.compareTo(name2) < 0)
                return -1;
            else if (name1.compareTo(name2) > 0)
                return 1;

        }

        return 0;
    }
}
