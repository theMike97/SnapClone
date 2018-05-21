package floatingheads.snapclone.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import floatingheads.snapclone.objects.VolleyCallback;

/**
 * Created by root on 2/26/18.
 */

public class UsersView extends ListView {

    private Context context;
    private ArrayList<User> usersArrayList;

    /**
     * Default constructor calls ListView constructor, initializes variables, and calls init() method
     * init() method uses ClickListener to change order of ListView
     * @param context
     */
    public UsersView(Context context) {
        super(context);
    }

    /**
     * Constructor calls ListView constructor passing in context and attrs, initialized variables and calls init() method.  Used for instantiating from xml
     * init() method uses ClickListener to change order of ListView
     * @param context
     * @param attrs
     */
    public UsersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    /**
     * Constructor calls ListView constructor passing in context and attrs, initialized variables and calls init() method.  Used for instantiating from xml
     * init() method uses ClickListener to change order of ListView
     * @param context
     * @param attrs
     * @param defStyle
     */
    public UsersView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    /**
     * Updates Users ArrayList and CustomListAdapter so changes are reflected in FriendsFragment and AddFriendsActivity
     * @param contents
     */
    public void setContents(ArrayList<User> contents) {
        usersArrayList = contents;
        ListAdapter la = new CustomListAdapter(context, usersArrayList, CustomListAdapter.USERS_SCREEN);
        setAdapter(la);
    }

    /**
     * Returns and ArrayList of Users
     * @return
     */
    public ArrayList<User> getUsers() {
        return usersArrayList;
    }

    private void init() {
        setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    VolleyActions va = new VolleyActions(context);
                    va.makeJSONArrayRequest("http://proj-309-vc-4.cs.iastate.edu:3000/users", new VolleyCallback() {
                        @Override
                        public void onSuccessResponse(JSONArray result) {
                            ArrayList<User> userArrayList = new ArrayList<>();

                            for (int i = 0; i < result.length(); i++) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = result.getJSONObject(i);
                                    int id = jsonObject.getInt("userID");
                                    String first = jsonObject.getString("first_name");
                                    String last = jsonObject.getString("last_name");
                                    String username = jsonObject.getString("username");
                                    String email = jsonObject.getString("email");

                                    User user = new User(id, first, last, username, email);
                                    userArrayList.add(user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                setContents(userArrayList);
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(context, "Could not connect to database", Toast.LENGTH_LONG).show();
                        }
                    });
                }
        );
    }
}
