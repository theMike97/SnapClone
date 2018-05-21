package floatingheads.snapclone.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import floatingheads.snapclone.R;
import floatingheads.snapclone.net_utils.Const;
import floatingheads.snapclone.objects.User;
import floatingheads.snapclone.objects.VolleyActions;
import floatingheads.snapclone.objects.VolleyCallback;

public class ProfileViewActivity extends AppCompatActivity {

    private User user;
    private TextView friendsCount;
    private VolleyActions va;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profile);

        user = new User();
        friendsCount = findViewById(R.id.gen_profileFriendsNum);

        if (getIntent().hasExtra("uid")) user.setId(getIntent().getExtras().getInt("uid"));
        if (getIntent().hasExtra("firstName")) user.setFirstName(getIntent().getExtras().getString("firstName"));
        if (getIntent().hasExtra("lastName")) user.setLastName(getIntent().getExtras().getString("lastName"));
        if (getIntent().hasExtra("username")) user.setUsername(getIntent().getExtras().getString("username"));
        if (getIntent().hasExtra("email")) user.setEmail(getIntent().getExtras().getString("email"));

        va = new VolleyActions(this); // for sending request and counting friends

        String fullname = user.getFirstName() + " " + user.getLastName();
        TextView profileName = findViewById(R.id.gen_profile_name);
        profileName.setText(fullname);
        updateFriendsCount();

        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.dimAmount = 0.4f;
        wlp.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_DIM_BEHIND |
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        getWindow().setAttributes(wlp);

        Button addFriendBtn = findViewById(R.id.add_friend_button);

        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileViewActivity.this, "Friend Added", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            finish();
            return true;
        }
        return false;
    }

    private void updateFriendsCount() {
        va.makeJSONArrayRequest(Const.friendsURL, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONArray result) {

                JSONObject juser = null;
                String friends = null;

                for (int i = 0; i < result.length(); i++) {
                    try {
                        if ((juser = result.getJSONObject(i)).getInt("userID") == user.getId()) {
                            friends = juser.getString("friends");
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (friends == null) {
                    friendsCount.setText("0");
                    return;
                }
                friendsCount.setText("" + friends.split(",").length);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ProfileViewActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
