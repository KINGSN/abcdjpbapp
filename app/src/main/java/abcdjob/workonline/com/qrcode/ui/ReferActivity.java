package abcdjob.workonline.com.qrcode.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;
import abcdjob.workonline.com.qrcode.ui.adapter.ReferAdapter;
import abcdjob.workonline.com.qrcode.ui.adapter.Refer_Data;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;
import es.dmoral.toasty.Toasty;

import static abcdjob.workonline.com.qrcode.Data_Device.getContext;
import static abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables.*;

public class ReferActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Dialog loadingDialog;
    RecyclerView refer_recycler;
    ReferAdapter referAdapter;
    private List<Refer_Data> refer_data;
    String url,test,url2;
    String ReferCode ;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);

        preferences =this.getSharedPreferences(ADMIN_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();

        test=preferences.getString(USER_MOBILE,"");

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent);
        refer_recycler=findViewById(R.id.refer_recycler);

        refer_data = new ArrayList<>();
        refer_recycler.setLayoutManager(new LinearLayoutManager(ReferActivity.this));
        referAdapter= new ReferAdapter(refer_data,ReferActivity.this);
        ReferCode=preferences.getString(GlobalVariables.USER_REFERAL_CODE,"");

        //Toast.makeText(ReferActivity.this,preferences.getString(GlobalVariables.USER_REFERAL_CODE,""), Toast.LENGTH_LONG).show();

      //  Toast.makeText(ReferActivity.this,""+ReferCode, Toast.LENGTH_LONG).show();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST, RestAPI.API_Refer_History+ReferCode,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        //Toast.makeText(requireActivity(),""+response,Toast.LENGTH_SHORT).show();
                        // Process the JSON
                        Log.e("KINGSN", "onResponse: "+response.length());
                        Log.e("KINGSN", "onResponse: "+response);
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++)
                            {
                                // Get current json object
                                JSONObject ob = response.getJSONObject(i);
                                String success = ob.getString("success");

                                //JSONObject ob=array.getJSONObject(i);

                                Refer_Data ld1=new Refer_Data(ob.getString("reffered_paid"),ob.getString("joining_time"),ob.getString("name"));
                                refer_data.add(ld1);
                                //Toast.makeText(requireActivity(), "hello"+ob.getString("amount"), Toast.LENGTH_LONG).show();


                            }
                             referAdapter= new ReferAdapter(refer_data,getContext());
                            //recyclerView.setLayoutManager(new LinearLayoutManager(view));
                            //GridLayoutManager lm = new GridLayoutManager(view, 1);
                            // recyclerView.setLayoutManager();
                             refer_recycler.setAdapter(referAdapter);

                        }catch (JSONException e){
                            e.printStackTrace();
                            //  Toast.makeText(requireActivity(), "hello"+e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                 new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(ReferActivity.this, "RESPONSE: " + error, Toast.LENGTH_SHORT).show();
                Log.e("Error", "" + error.getMessage());
               // Toast.makeText(ReferActivity.this, "ErrorV: " + error.getMessage(),
                        //Toast.LENGTH_SHORT).show();
                Toasty.error(ReferActivity.this, "No Refferals Found..!", Toast.LENGTH_LONG, true).show();
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(ReferActivity.this, HomeActivity.class));
                    finish();
                }, 1500);
                loadingDialog.dismiss();
            }
        }) {  @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("user_Referal_history","");
            params.put("users_id",test);
            params.put("refer_code",ReferCode);

            return params;
        }
        };


        RequestQueue requestQueue= Volley.newRequestQueue(ReferActivity.this);
        requestQueue.add(jsonArrayRequest);



    }
}