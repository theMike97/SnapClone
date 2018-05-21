package floatingheads.snapclone.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import floatingheads.snapclone.R;
import floatingheads.snapclone.activities.AddFriendsActivity;
import floatingheads.snapclone.activities.CameraPreviewActivity;
import floatingheads.snapclone.activities.ChatActivity;
import floatingheads.snapclone.net_utils.Const;
import floatingheads.snapclone.objects.CustomListAdapter;
import floatingheads.snapclone.objects.Friend;
import floatingheads.snapclone.objects.User;
import floatingheads.snapclone.objects.VolleyActions;
import floatingheads.snapclone.objects.VolleyCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private MaterialSearchView searchView;
    private static String usersURL = Const.usersURL;
    private static String friendsURL = Const.friendsURL;
    private Button backToCamera;

    /**
     * User which contains logged in user's credentials
     */
    public User masterUser;

    /**
     * Default constructor calls constructor for Fragment class initialized variables
     */
    public FriendsFragment() {
        super();
        searchView = null;
        masterUser = new User();
    }

    /**
     * Returns a View which contains a list of the user's friends, an add friends button, and a search bar
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_friends, container, false);

        Context friendsFragmentContext = this.getContext();

        setHasOptionsMenu(true);

        // create master user
        if (getArguments() != null) masterUser.setId(getArguments().getInt("uid"));
        if (getArguments() != null) masterUser.setFirstName(getArguments().getString("firstName"));
        if (getArguments() != null) masterUser.setLastName(getArguments().getString("lastName"));
        if (getArguments() != null) masterUser.setUsername(getArguments().getString("username"));
        if (getArguments() != null) masterUser.setEmail(getArguments().getString("email"));

        Toolbar toolbar = (Toolbar) inflatedView.findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.mipmap.ic_person_add_white_24dp);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Friends");
        toolbar.setTitleTextColor(Color.WHITE);

        searchView = (MaterialSearchView) inflatedView.findViewById(R.id.search_view);

        backToCamera = (Button) inflatedView.findViewById(R.id.cam_friends_btn);

        VolleyActions va = new VolleyActions(friendsFragmentContext);
        ArrayList<Friend> friendArrayList = new ArrayList<>();
        ListView friendsList = (ListView) inflatedView.findViewById(R.id.friendsListView);

        va.makeJSONArrayRequest(friendsURL, new VolleyCallback() {

            @Override
            public void onSuccessResponse(JSONArray result) {
                JSONObject user;
                String friends = null;
                int[] friendsArr;

                for (int i = 0; i < result.length(); i++) {
                    try {
                        if ((user = result.getJSONObject(i)).getInt("userID") == masterUser.getId()) {
                            friends = user.getString("friends");
//                            Log.d("callback2", friends);
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (friends == null) {
                    // also occurs if user has no friends :'(
                    Toast.makeText(friendsFragmentContext, "Unable to load user data", Toast.LENGTH_LONG).show();
                    return;
                }

                String[] tempArr = friends.split(",");
                friendsArr = new int[tempArr.length];
                for (int i = 0; i < tempArr.length; i++) {
                    friendsArr[i] = Integer.parseInt(tempArr[i]);
                }

//                Log.d("callback2", Arrays.toString(friendsArr));

                va.makeJSONArrayRequest(usersURL, new VolleyCallback() {
                    JSONObject user;
                    @Override
                    public void onSuccessResponse(JSONArray result) {
//                        Log.d("successResponse", result.toString());
                        int friendsCounter = friendsArr.length;
                        int usersIndex = 0;
                        while (friendsCounter > 0 && usersIndex < result.length()) {
                            try {
                                for (int i = 0; i < friendsArr.length; i++) {
                                    user = result.getJSONObject(usersIndex);
                                    if (user.getInt("userID") == friendsArr[i]) {
//                                        Log.d("callback2", "yes");
                                        friendArrayList.add(new Friend(
                                                user.getInt("userID"),
                                                user.getString("first_name"),
                                                user.getString("last_name"),
                                                Friend.STATUS_ACCEPTED
                                        ));
                                        friendsCounter--;
                                    }
//                                   Log.d("callback2", "" + user.getInt("userID"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            usersIndex++;
                        }
//                         Log.d("callback2", friendArrayList.toString());
                        Collections.sort(friendArrayList);
                        ListAdapter la = new CustomListAdapter(friendsFragmentContext, friendArrayList, CustomListAdapter.FRIENDS_SCREEN);
                        friendsList.setAdapter(la);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(friendsFragmentContext, "Could not connect to database", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(friendsFragmentContext, "Could not connect to database", Toast.LENGTH_LONG).show();
            }
        });

        // add friends to arraylist
//        friendArrayList.add(new Friend(1, "Quinn", "Salas", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(2, "Akira", "Demoss", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(4, "Simanta", "Mitra", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(6, "Mark", "Hammill", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(12, "Esperanza", "Spalding", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(13, "Harry", "Potter", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(21, "Hermione", "Granger", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(25, "Vamsi", "Calpakkam", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(34, "Tom", "Brady", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(54, "Magic","Johnson", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(56, "Michael", "Jordan", Friend.STATUS_ACCEPTED));

        friendsList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    Friend friend = (Friend) parent.getItemAtPosition(position);
//                    String name = friend.getFirstName() + " " + friend.getLastName();
//                    Toast.makeText(this.getContext(), name, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(), ChatActivity.class);
                    i.putExtra("uid", masterUser.getId());
                    i.putExtra("firstName", masterUser.getFirstName());
                    i.putExtra("lastName", masterUser.getLastName());
                    i.putExtra("username", masterUser.getUsername());
                    i.putExtra("email", masterUser.getEmail());
                    startActivity(i);
                }
        );

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(friendsFragmentContext, AddFriendsActivity.class);
                startActivity(i);
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                // if closed, view goes to normal
                ListAdapter la = new CustomListAdapter(friendsFragmentContext, friendArrayList, CustomListAdapter.FRIENDS_SCREEN);
                ListView friendsList = (ListView) inflatedView.findViewById(R.id.friendsListView);
                friendsList.setAdapter(la);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String name = "";
                if (newText != null && !newText.isEmpty()) {
                    ArrayList<Friend> listFound = new ArrayList<>();
                    for (Friend friend : friendArrayList) {

                        if (friend.getFirstName().toLowerCase().startsWith(newText.toLowerCase()) || friend.getLastName().toLowerCase().startsWith(newText.toLowerCase())) {
                            listFound.add(friend);
                        }

                        ListAdapter adapter = new CustomListAdapter(friendsFragmentContext, listFound, CustomListAdapter.FRIENDS_SCREEN);
                        friendsList.setAdapter(adapter);
                    }
                } else {
                    // search text is null
                    ListAdapter adapter = new CustomListAdapter(friendsFragmentContext, friendArrayList, CustomListAdapter.FRIENDS_SCREEN);
                    friendsList.setAdapter(adapter);
                }
                return true;
            }
        });

        backToCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CameraPreviewActivity.class);
                i.putExtra("uid", masterUser.getId());
                i.putExtra("firstName", masterUser.getFirstName());
                i.putExtra("lastName", masterUser.getLastName());
                startActivity(i);
            }
        });

        return inflatedView;
    }

    /**
     * Functionality for search bar
     * @param menu
     * @param menuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.search_menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
    }
}
