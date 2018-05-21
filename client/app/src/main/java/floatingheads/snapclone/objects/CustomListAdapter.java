package floatingheads.snapclone.objects;

/**
 * Created by Mike on 2/23/18.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import floatingheads.snapclone.R;

public class CustomListAdapter<T extends User> extends ArrayAdapter<T> {

    public static int FRIENDS_SCREEN = 0;
    public static int MESSAGES_SCREEN = 1;
    public static int USERS_SCREEN = 2;

    private int screen_type;

    /**
     * Constructor calls ArrayAdapter constructor and sets screen type
     * Screen type can be either FRIENDS_SCREEN, MESSAGES_SCREEN, or USERS_SCREEN
     * @param context
     * @param friends
     * @param screen_type
     */
    public CustomListAdapter(Context context, ArrayList<T> friends, int screen_type) {
        super(context, R.layout.custom_contact, friends);
        this.screen_type = screen_type;
    }

    /**
     * Returns View which contains information from either a Friend, User, or Contact
     * This view is used in a ListView.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater friendsInflator = LayoutInflater.from(getContext());
        int resource;

        if (screen_type == FRIENDS_SCREEN) {
            resource = R.layout.custom_friend;
        } else if (screen_type == MESSAGES_SCREEN) {
            resource = R.layout.custom_contact;
        } else if (screen_type == USERS_SCREEN) {
            resource = R.layout.custom_user;
        } else {
            Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG);
            throw new IllegalStateException();
        }

        View customFriendView = friendsInflator.inflate(resource, parent, false);

        User singleUser = (User) getItem(position);
//        singleUser.setId();

        TextView friendName = (TextView) customFriendView.findViewById(R.id.friend_name);
        if (screen_type == MESSAGES_SCREEN) {
            Contact singleContact = (Contact) singleUser;
            TextView lastMessage = (TextView) customFriendView.findViewById(R.id.last_message);
            lastMessage.setText(singleContact.getLastMessage());
        }
        if (screen_type == USERS_SCREEN) {
            TextView username = (TextView) customFriendView.findViewById(R.id.user_name);
            username.setText(singleUser.getUsername());
        }

        ImageView avatar = (ImageView) customFriendView.findViewById(R.id.avatar);

        String name = singleUser.getFirstName() + " " + singleUser.getLastName();
        friendName.setText(name);

        if (singleUser.getAvatar() != null) {
            avatar.setImageBitmap(singleUser.getAvatar());
        } else {
            avatar.setImageResource(R.mipmap.avatar);
        }

        return customFriendView;
    }
}
