package floatingheads.snapclone.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import floatingheads.snapclone.R;
import floatingheads.snapclone.net_utils.Const;
import floatingheads.snapclone.objects.User;
import floatingheads.snapclone.objects.UsersView;
import floatingheads.snapclone.objects.VolleyActions;
import floatingheads.snapclone.objects.VolleyCallback;


public class AddFriendsActivity extends AppCompatActivity {

    private Context context = this;
    private String usersURL = Const.usersURL;

    private FrameLayout touchInterceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        touchInterceptor = new FrameLayout(this);
        touchInterceptor.setClickable(true);

        UsersView usersView = (UsersView) findViewById(R.id.users_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.add_search_view);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        VolleyActions va = new VolleyActions(this);
        va.makeJSONArrayRequest(usersURL, new VolleyCallback() {
            /**
             * Creates new User from result data if server successfully responds.
             *
             * @param result
             */
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
                    usersView.setContents(userArrayList);
                }
            }

            /**
             * Shows a Toast error message if server fails to respond.
             * @param volleyError
             */
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Could not connect to database", Toast.LENGTH_LONG).show();
            }
        }); //should use callback in future

        usersView.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    User user = (User) parent.getItemAtPosition(position);
                    Intent i = new Intent(getApplicationContext(), ProfileViewActivity.class);
                    i.putExtra("uid", user.getId());
                    i.putExtra("firstName", user.getFirstName());
                    i.putExtra("lastName", user.getLastName());
                    i.putExtra("username", user.getUsername());
                    i.putExtra("email", user.getEmail());

                    startActivity(i);
                }
        );
    }

    @Override
    protected void onPause() {
        if (touchInterceptor.getParent() == null) {
            ((ViewGroup) findViewById(android.R.id.content)).addView(touchInterceptor);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        ((ViewGroup) findViewById(android.R.id.content)).removeView(touchInterceptor);
        super.onResume();
    }
}
