package floatingheads.snapclone.objects;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import floatingheads.snapclone.controllers.AppController;

/**
 * Created by Mike on 2/26/18.
 */

public class VolleyActions {

    private View view;
    private ViewGroup viewGroup;
    private Context context;

    /**
     * Default constructor sets context and sets view to null
     * @param context
     */
    public VolleyActions(Context context) {
        this.context = context;
        view = null;
    }

    /**
     * Sends HTTP GET request to server and retrieves a JSONArray
     * Uses VolleyCallback interface to route onResponse and onErrorResponse method implementation back to class and method from which this method was called
     * Adds request to AppController queue
     * @param url
     * @param volleyCallback
     */
    public void makeJSONArrayRequest(String url, final VolleyCallback volleyCallback) {
        JsonArrayRequest jar = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                volleyCallback.onSuccessResponse(response);
                if (response != null) {
//                    Toast.makeText(context, "Got Response!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyCallback.onErrorResponse(error);
                VolleyLog.e("Error", error.getMessage());
//                Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jar);
    }

//    public void makeStringRequest(String url, Object o) {
//        // Request a string response from the provided URL.
//        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Display the response string.
//                String tmp = "Response is " + response;
//                if (o instanceof TextView && o != null) {
//                    TextView mTextView = (TextView) o;
//                    mTextView.setText(response);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // TODO
//            }
//        });
//
//        // Add the request to the RequestQueue.
//        AppController.getInstance().addToRequestQueue(req);
//    }

//    public void makeJSONobjRequest(String url, Object o) {
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", "AbCdEfGh123456");
//
//        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//
//                    VolleyLog.v("Response:%n %s", response.toString(4));
//                    String ip = response.getString("ip");
//                                /*
//                            String userId = (String) response.get("userId");
//                            String id = (String) response.get("id");
//                            String title = response.getString("title");
//                            String body = response.getString("body");
//                            */
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("Error: ", error.getMessage());
//            }
//        });
//
//        // add the request object to the queue to be executed
//        AppController.getInstance().addToRequestQueue(req);
//
//    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    private void updateViewGroup(ListView listView) {

    }

    private void updateViewGroup(MessagesView messagesView) {

    }

    /**
     * Sets view
     * @param view
     */
    public void setView(View view) {
        this.view = view;
    }

    private void updateTextView(TextView textView, String response) {
        textView.setText(response);
    }
}
