package floatingheads.snapclone.objects;

import com.android.volley.VolleyError;
import org.json.JSONArray;

/**
 * Created by Mike on 4/6/18.
 */

public interface VolleyCallback {

    /**
     * Implements onResponse from Volley HTTP request for JSONArrays
     * @param result
     */
    void onSuccessResponse(JSONArray result);

    /**
     * Implements onErrorResponse from Volley HTTP request
     * @param volleyError
     */
    void onErrorResponse(VolleyError volleyError);

}
