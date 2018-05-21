package floatingheads.snapclone.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Quinn Salas on 3/22/18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static final String TAG = "FirebaseID";

    /**
     * Get updated InstanceID token
     */
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshToken);
    }

    /**
     * Persist token to third-party server
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement
    }
}
