package abcdjob.workonline.com.qrcode.Interface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VARUN on 01/01/19.
 */
public interface Helper {
    public void backResponse(boolean flag, JSONObject abcdapp, String msg, JSONObject response) throws JSONException;
}
