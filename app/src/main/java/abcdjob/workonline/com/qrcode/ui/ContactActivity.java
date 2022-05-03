package abcdjob.workonline.com.qrcode.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.ui.Util.Constant;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;

import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;

public class ContactActivity extends AppCompatActivity  {

    private String name, email, message;

    private Dialog dialog;
    private EditText nameEt, phoneEt, emailEt,msgcont,contfone;
    private String contact_type, contact_id,test,data;
    private Button contbtn;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Spinner spinner_contact_us;
    SharedPreferences preferences;
    private FrameLayout adlayout2;
    private FrameLayout adview;
    private FrameLayout adView2;
    private Dialog loadingDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Ads Ads = new Ads();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

       // adlayout2=(FrameLayout) findViewById(R.id.adlayout2);
        //adview  = (FrameLayout) findViewById(R.id.adView);
        //adView2 = (FrameLayout) findViewById(R.id.adView2);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //String data = extras.getString("keyName"); // retrieve the data using keyName
            data = Objects.requireNonNull(getIntent().getExtras()).getString("callingActivity");
            Log.d("KINGSN", "onCreate: DATA"+data);
        }
        //Ads ads=new Ads();
        //Ads ads2=new Ads();
        Method ads=new Method(ContactActivity.this);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg));
        //dialog.show();

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent);
        loadingDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();


            }
        }, 2000);

       /* if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))
        {
            ads.callindustrial( ContactActivity.this);
            // ads.callindustrial(HomeActivity.this);
        }*/
        // Ads.ScreenopeningInterstitialAd(ContactActivity.this);


       /* if (GlobalVariables.adClickModel.getInterstitial().equals("0"))
        {
            Ads.ScreenopeningInterstitialAd(ContactActivity.this);
        }
*/


        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        nameEt = findViewById(R.id.contName);
        //subjecEt = findViewById(R.id.contEmail);
        msgcont = findViewById(R.id.contmsg);
        emailEt = findViewById(R.id.contEmail);
        contfone = findViewById(R.id.contfone);
        spinner_contact_us = findViewById(R.id.spinner_contact_us);
        test = preferences.getString(GlobalVariables.USER_MOBILE, "");

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_contact_us.setAdapter(adapter);
        contbtn = findViewById(R.id.contbtn);
       /* String name = nameEt.getText().toString();
        String email = emailEt.getText().toString();
        String msg = msgcont.getText().toString();
*/
        nameEt.setText(preferences.getString(GlobalVariables.USERNAME,""));
        emailEt.setText(preferences.getString(GlobalVariables.USER_EMAIL,""));
        //contfone.setText(test);
        //phoneEt.setText(preferences.getString(GlobalVariables.USER_MOBILE,""));

        contbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = nameEt.getText().toString();
                final String email = emailEt.getText().toString();
                final String subject = spinner_contact_us.getSelectedItem().toString();
                final String msg  =msgcont.getText().toString();

                if (nameEt.getText().toString().isEmpty()){
                    nameEt.setError("Fields Cant be Empty");
                    //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (emailEt.getText().toString().isEmpty()){
                    emailEt.setError("Insert A valid 10 Digit Phone Number");
                    // Toast.makeText(RegisterActivity.this, "Input valid name", Toast.LENGTH_SHORT).show();
                    return;
                }  if (spinner_contact_us.getSelectedItem().toString().isEmpty()) {
                    //emailRegisterEt.setError("Insert A valid Email");
                    Toast.makeText(ContactActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
                    return;
                } if (msgcont.getText().toString().isEmpty()) {
                    msgcont.setError("Insert Your Query");
                    // Toast.makeText(RegisterActivity.this, "Input valid name", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    uploadData();
                    }


                //String subject = subjecEt.getText().toString();
                //String spinner_contact_us = spinner_contact_us.getText().toString();
            }


        });

      /*  if (GlobalVariables.aboutUsList.getBanner_ad_type().toLowerCase().equals("addmob"))
        {
            final AdView mAdView = new AdView(ContactActivity.this);
            mAdView.setAdSize(AdSize.SMART_BANNER);
            mAdView.setAdUnitId(preferences.getString(GlobalVariables.ADDMOB_BANER,""));
            adview=(FrameLayout) findViewById(R.id.adView);
            adview.addView(mAdView);
            adview.setVisibility(View.INVISIBLE);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {

                    // Code to be executed when an ad finishes loading.
                    adview=(FrameLayout) findViewById(R.id.adView);
                    adview.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            });

          *//*  AdView mAdView2 = new AdView(MathActivity.this);
            mAdView2.setAdSize(AdSize.SMART_BANNER);
            mAdView2.setAdUnitId(preferences.getString(GlobalVariables.ADDMOB_BANER2,""));
            FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.adView2);
            frameLayout2.addView(mAdView2);
            AdRequest adRequestt = new AdRequest.Builder().build();
            mAdView2.loadAd(adRequestt);
            mAdView2.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {

                    // Code to be executed when an ad finishes loading.
                    addlayout2=(FrameLayout) findViewById(R.id.addlayout2);
                    addlayout2.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    // Code to be executed when an ad request fails.

                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            });*//*

        }else
        if (GlobalVariables.aboutUsList.getBanner_ad_type().toLowerCase().equals("facebook"))
        {

            ads.showFbBanner(this,(FrameLayout) findViewById( R.id.adView));
            adview=(FrameLayout) findViewById(R.id.adView);
            adview.setVisibility(View.VISIBLE);

        }else
        if (GlobalVariables.aboutUsList.getBanner_ad_type().toLowerCase().equals("both"))
        {

            if (GlobalVariables.interstitialAdModelCount.getInterstitialAd1().equals("0")) {

                final AdView mAdView = new AdView(ContactActivity.this);
                mAdView.setAdSize(AdSize.SMART_BANNER);
                mAdView.setAdUnitId(preferences.getString(GlobalVariables.ADDMOB_BANER,""));
                adview=(FrameLayout) findViewById(R.id.adView);
                adview.addView(mAdView);
                adview.setVisibility(View.INVISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {

                        // Code to be executed when an ad finishes loading.
                        adview=(FrameLayout) findViewById(R.id.adView);
                        adview.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Code to be executed when an ad request fails.
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when an ad opens an overlay that
                        // covers the screen.
                    }

                    @Override
                    public void onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when the user is about to return
                        // to the app after tapping on an ad.
                    }
                });


            }else {

                ads.showFbBanner(this,(FrameLayout) findViewById( R.id.adView));
                adview=(FrameLayout) findViewById(R.id.adView);
                adview.setVisibility(View.VISIBLE);
            }

            //



        }*/

    }


    private void uploadData() {
        final String name = nameEt.getText().toString();
        final String email = emailEt.getText().toString();
        final String subject = spinner_contact_us.getSelectedItem().toString();
        final String msg  =msgcont.getText().toString();
        if(test.equals("")){
            test=contfone.getText().toString();
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_contactus,
                response -> {
                    // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                    try {
                        System.out.println(response);
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray(GlobalVariables.AppSid);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String success = object.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(ContactActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                               // finish();
                                // startActivity(getIntent());
                                loadingDialog.dismiss();
                               // loadingDialog.dismiss();
                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ContactActivity.this);
                                alertDialogBuilder.setTitle(object.getString("title"));
                                alertDialogBuilder.setMessage(object.getString("msg"));
                                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                alertDialogBuilder.setPositiveButton(ContactActivity.this.getResources().getString(R.string.ok_message),
                                        (arg0, arg1) -> {
                                            finish();
                                            loadingDialog.dismiss();
                                            finishAffinity();
                                        });

                                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                //Toast.makeText(ContactActivity.this, object.getString("msg"), Toast.LENGTH_LONG).show();


                            } else {
                                loadingDialog.dismiss();
                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ContactActivity.this);
                                alertDialogBuilder.setTitle(object.getString("title"));
                                alertDialogBuilder.setMessage(object.getString("msg"));
                                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                alertDialogBuilder.setPositiveButton(ContactActivity.this.getResources().getString(R.string.ok_message),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                finish();
                                                loadingDialog.dismiss();
                                                finishAffinity();
                                            }
                                        });

                                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                //Toast.makeText(ContactActivity.this, object.getString("msg"), Toast.LENGTH_LONG).show();
                            }

                        }

                        // progressDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ContactActivity.this, "Error: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ContactActivity.this, "RESPONSE: " + error, Toast.LENGTH_SHORT).show();
                Log.e("Error", "" + error.getMessage());
               // Toast.makeText(ContactActivity.this, "ErrorV: " + error.getMessage(),
                       // Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ContactActivity.this);
                alertDialogBuilder.setTitle("Something Went Wrong");
                alertDialogBuilder.setMessage("Please Try With Active Internet ");
                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                alertDialogBuilder.setPositiveButton(ContactActivity.this.getResources().getString(R.string.ok_message),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                                loadingDialog.dismiss();
                                finishAffinity();
                            }
                        });

                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        }) { @Override
        protected Map<String, String> getParams() {

            Map<String, String> params = new HashMap<>();
           /* params.put("id", user.getUid());*/
            params.put("name", name);
            params.put("user_id",test);
            params.put("email",email);
            params.put("msg",msg);
            params.put("subject",subject);
            params.put("phone",test);

            return params;
        }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ContactActivity.this);
        requestQueue.add(stringRequest);
      //  Toast.makeText(ContactActivity.this,""+ stringRequest, Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onBackPressed() {
//        finish();
//        finishAffinity();

      //  String data = Objects.requireNonNull(getIntent().getExtras()).getString("callingActivity");
        Log.d("KINGSN", "onBackPressed: "+data);
        if(data !=null){
            Intent intent = new Intent(ContactActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(ContactActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }



}




