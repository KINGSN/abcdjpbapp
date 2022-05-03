package abcdjob.workonline.com.qrcode.https;

import android.content.Context;
import android.util.Log;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.jsonparser.JSONParser;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;

/**
 * Created by VARUN on 01/01/19.
 */
public class HttpsRequest {
    private String match;
    private Map<String, String> params;
    private Map<String, File> fileparams;
    private Context ctx;
    private JSONObject jObject;
    private SharedPrefrence sharedPreference;

    public HttpsRequest(String match, Map<String, String> params, Context ctx) {
        this.match = match;
        this.params = params;
        this.ctx = ctx;
        this.sharedPreference = SharedPrefrence.getInstance(ctx);
    }

    public HttpsRequest(String match, Map<String, String> params, Map<String, File> fileparams, Context ctx) {
        this.match = match;
        this.params = params;
        this.fileparams = fileparams;
        this.ctx = ctx;
        this.sharedPreference = SharedPrefrence.getInstance(ctx);
    }

    public HttpsRequest(String match, Context ctx) {
        this.match = match;
        this.ctx = ctx;
        this.sharedPreference = SharedPrefrence.getInstance(ctx);
    }

    public HttpsRequest(String match, JSONObject jObject, Context ctx) {
        this.match = match;
        this.jObject = jObject;
        this.ctx = ctx;
        this.sharedPreference = SharedPrefrence.getInstance(ctx);

    }

    public void stringPostJson(final String TAG, final Helper h) {
        AndroidNetworking.post(RestAPI.URL)
                .addJSONObjectBody(jObject)
                .setTag("test")
                .addHeaders(GlobalVariables.LANGUAGE, sharedPreference.getValue(GlobalVariables.LANGUAGE_SELECTION))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, " response body --->" + response.toString());
                        Log.e(TAG, " response body --->" + jObject.toString());
                        JSONParser jsonParser = new JSONParser(ctx, response);
                        if (jsonParser.RESULT) {
                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.abcdapp, jsonParser.MESSAGE, response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.abcdapp, jsonParser.MESSAGE, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }



                    @Override
                    public void onError(ANError anError) {
                      //  ProjectUtils.pauseProgressDialog();
                        Log.e(TAG, " error body --->" + anError.getErrorBody() + " error msg --->" + anError.getMessage());
                    }
                });
    }

    public void stringPost(final String TAG, final Helper h) {
        AndroidNetworking.post(RestAPI.URL+match)
                .addBodyParameter(params)
                .setTag("test")
                .addHeaders(GlobalVariables.LANGUAGE, sharedPreference.getValue(GlobalVariables.LANGUAGE_SELECTION))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, " response body --->" + response.toString());
                        Log.e(TAG, " param --->" + params.toString());
                        JSONParser jsonParser = new JSONParser(ctx, response);
                        if (jsonParser.RESULT) {
                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.abcdapp, jsonParser.MESSAGE, response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.abcdapp, jsonParser.MESSAGE, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                       // ProjectUtils.pauseProgressDialog();
                        Log.e(TAG, " error body --->" + anError.getErrorBody() + " error msg --->" + anError.getMessage());
                    }
                });
    }

    public void stringPost2(final String TAG, final Helper h) {
        AndroidNetworking.post(match)
                .addBodyParameter(params)
                .setTag("test")
                .addHeaders(GlobalVariables.LANGUAGE, sharedPreference.getValue(GlobalVariables.LANGUAGE_SELECTION))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(GlobalVariables.TAG, " response body --->" + response.toString());
                        Log.e(GlobalVariables.TAG, " param --->" + params.toString());
                       // JSONParser jsonParser = new JSONParser(ctx, response);
                        JSONParser jsonParser = null;
                        try {
                           // jsonParser = new JSONParser(ctx, response.getJSONObject(GlobalVariables.AppSid));
                            jsonParser = new JSONParser(ctx, response.getJSONObject("abcdapp"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (jsonParser.RESULT) {
                            try {
                                h.backResponse(jsonParser.RESULT,jsonParser.abcdapp, jsonParser.MESSAGE, response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.abcdapp, jsonParser.MESSAGE, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        //ProjectUtils.pauseProgressDialog();
                        Log.e(TAG, " error body --->" + anError.getErrorBody() + " error msg --->" + anError.getMessage()+anError.getResponse());
                        Log.e(GlobalVariables.TAG, " response body --->" +RestAPI.URL + match+params);
                    }

                });
    }

    public void stringGet(final String TAG, final Helper h) {
        AndroidNetworking.get(RestAPI.URL + match)
                .setTag("test")
                .addHeaders(GlobalVariables.LANGUAGE, sharedPreference.getValue(GlobalVariables.LANGUAGE_SELECTION))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, " response body --->" + response.toString());
                        JSONParser jsonParser = new JSONParser(ctx, response);
                        if (jsonParser.RESULT) {

                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.abcdapp, jsonParser.MESSAGE, response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.abcdapp, jsonParser.MESSAGE, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                       // ProjectUtils.pauseProgressDialog();
                        Log.e(TAG, " error body --->" + anError.getErrorBody() + " error msg --->" + anError.getMessage());
                    }
                });
    }

    public void imagePost(final String TAG, final Helper h) {
        AndroidNetworking.upload(RestAPI.URL + match)
                .addMultipartFile(fileparams)
                .addMultipartParameter(params)
                .setTag("uploadTest")
                .addHeaders(GlobalVariables.LANGUAGE, sharedPreference.getValue(GlobalVariables.LANGUAGE_SELECTION))
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        Log.e("Byte", bytesUploaded + "  !!! " + totalBytes);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, " response body --->" + response.toString());
                        Log.e(TAG, " param --->" + params.toString());
                        JSONParser jsonParser = new JSONParser(ctx, response);

                        if (jsonParser.RESULT) {

                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.abcdapp, jsonParser.MESSAGE, response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.abcdapp, jsonParser.MESSAGE, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        //ProjectUtils.pauseProgressDialog();
                        Log.e(TAG, " error body --->" + anError.getErrorBody() + " error msg --->" + anError.getMessage());
                    }
                });
    }

}