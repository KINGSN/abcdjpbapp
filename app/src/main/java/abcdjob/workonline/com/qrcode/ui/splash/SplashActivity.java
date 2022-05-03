package abcdjob.workonline.com.qrcode.ui.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.onesignal.OSPermissionObserver;
import com.onesignal.OSPermissionStateChanges;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.AdModelCount;
import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.LoginActivity;
import abcdjob.workonline.com.qrcode.ui.Util.Constant;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;
import es.dmoral.toasty.Toasty;

import static abcdjob.workonline.com.qrcode.ui.Util.Constant.settings;

public class SplashActivity extends AppCompatActivity implements OSSubscriptionObserver, OSPermissionObserver {

    /**
     * Constants
     */
    private final int SPLASH_DELAY = 1500;

    /**
     * Fields
     */
    private ImageView mImageViewLogo,image_view;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    LottieAnimationView splashlotiee;
    private Method method;
    List<ApplicationInfo> packages;
    PackageManager pm;

    String oneSignalPlayerId,oneSignalPushToken;
    public UserDTO userDTO;
    private SharedPrefrence mprefrences;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        method=new Method(SplashActivity.this);

        getWindow().setBackgroundDrawable(null);
        // getAllData(SplashActivity.this);

        goToMainPage();
        initializeViews();
        OneSignal.addSubscriptionObserver((OSSubscriptionObserver) SplashActivity.this);

      /*  OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
*/
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("a55b3068-3d26-4b9c-9e82-ec1e09e0859f");
        OneSignal.addSubscriptionObserver(this);

        OneSignal.getDeviceState();
        OneSignal.disablePush(false);
        if(NotificationManagerCompat.from(SplashActivity.this).areNotificationsEnabled())
        {
            //Do your Work
        }
        else
        {
            //Ask for permission
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    // Constant.DeviceID = getDeviceId(SplashActivity.this);
                    // GetDeviceID();
                    // Toast.makeText(RegisterActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(RegisterActivity.this,  Constant.DeviceID, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    finish();
                }


            };

            TedPermission.with(this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_WAP_PUSH)
                    .check();


        }


        // callinappupdate();
        animateLogo();
        GlobalVariables.adModelCount = new AdModelCount("0","0","0","0");

        //animateback();
        //LoadSettings();
        // goToMainPage();
        checkspamapp();

    }

    /**
     * This method initializes the views
     */
    private void initializeViews() {
        mImageViewLogo = findViewById(R.id.image_view_logo);
        //image_view = findViewById(R.id.image_view);
       /* mImageViewLogo.setVisibility(View.INVISIBLE);
        image_view.setVisibility(View.INVISIBLE);*/

    }

    /**
     * This method takes user to the main page
     */
    private void goToMainPage() {



        if (method.preferencess.getValue(GlobalVariables.USER_MOBILE).equals("")){
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }, SPLASH_DELAY);

        }else{

            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }, SPLASH_DELAY);
        }


       /* if ((isLogin.equals("true"))) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }, SPLASH_DELAY);
        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }, SPLASH_DELAY);
        }*/
    }

    /**
     * This method animates the logo
     */
    private void animateLogo() {
        mImageViewLogo.setVisibility(View.VISIBLE);
        // image_view.setVisibility(View.INVISIBLE);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_without_duration);
        fadeInAnimation.setDuration(SPLASH_DELAY);

        mImageViewLogo.startAnimation(fadeInAnimation);
    }

    private void animateback() {
        mImageViewLogo.setVisibility(View.INVISIBLE);
        image_view.setVisibility(View.VISIBLE);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_without_duration);
        fadeInAnimation.setDuration(SPLASH_DELAY);

        image_view.startAnimation(fadeInAnimation);
        //animateLogo();
        new Handler().postDelayed(this::animateLogo, 2000);
    }

   /* public void LoadSettings() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_Settings,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray(Constant.AppSid);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String joiningBonus= object.getString("joining_bonus");
                                String referRate= object.getString("per_refer");
                                String taskCoin = object.getString("dailytask_coin");
                                String perQrCoin = object.getString("per_qr_coin");
                                String redeemMin = object.getString("minimum_widthrawal");
                                String redeemamount = object.getString("min_redeem_amount");
                                String app_joining_fee = object.getString("app_joining_fee");
                                String qr_min_time = object.getString("qr_min_time");
                                String paytm_merchent_key = object.getString("paytm_merchent_key");
                               // String paytm_mid = object.getString("paytm_mid");
                                String add_type = object.getString("adds_type");
                                String banner_add = object.getString("banner_add");
                                String industrial_add = object.getString("industrial_add");
                                String industrial_call_type = object.getString("call_industrial_on");
                                String reward_add = object.getString("reward_add");
                                String native_add = object.getString("native_add");
                                String fb_banner1 = object.getString("fb_banner1");
                                String fb_indusrial1 = object.getString("fb_industrial1");
                                String fb_reward1 = object.getString("fb_reward1");
                                String fb_native = object.getString("fb_native");
                                String addmobpublisher_id = object.getString("publisher_id");
                                String addmob_banner1 = object.getString("addmob_banner1");
                                String addmob_industrial1 = object.getString("addmob_industrial1");
                                String addmob_rewarded1 = object.getString("addmob_rewarded1");
                                String addmob_native = object.getString("addmob_native");
                                String telegram_link = object.getString("telegramlink");
                                String youtube_link = object.getString("youtube_link");
                                String facebook_page = object.getString("facebook_page");
                                String new_version = object.getString("new_version");
                                String update_link = object.getString("update_link");
                                String admin_msg = object.getString("admin_msg");
                                String join_group = object.getString("join_group");
                                String reward_frequency = object.getString("reward_frequency");
////f

                                String app_name = object.getString("app_name");
                                String app_logo = object.getString("app_logo");
                                String app_version = object.getString("app_version");
                                String app_author = object.getString("app_author");
                                String app_contact = object.getString("app_contact");
                                String app_email = object.getString("app_email");
                                String app_website = object.getString("app_website");
                                String app_description = object.getString("app_description");
                                String app_developed_by = object.getString("app_developed_by");
                                String redeem_currency = object.getString("redeem_currency");
                                String payment_method1 = object.getString("payment_method1");
                                String payment_method2 = object.getString("payment_method2");
                                String payment_method3 = object.getString("payment_method3");
                                String payment_method4 = object.getString("payment_method4");
                                String widthraw_note = object.getString("widthraw_note");
                                String payment_gateway = object.getString("payment_gateway");
                                String paytm_mid = object.getString("paytm_mid");
                                String paytm_key = object.getString("paytm_key");
                                String razorpay_mid = object.getString("razorpay_mid");
                                String razorpay_key = object.getString("razorpay_key");
                                String payumoney_mid = object.getString("payumoney_mid");
                                String payumoney_key = object.getString("payumoney_key");

                                editor.putString(GlobalVariables.JOINING_BONUS,joiningBonus);
                                editor.putString(GlobalVariables.REFER_RATE, referRate);
                                editor.putString(GlobalVariables.QR_COIN, perQrCoin);
                                editor.putString(GlobalVariables.APP_JOINING_FEE, app_joining_fee);
                               // editor.putString(GlobalVariables.QR_MIN_TIME, qr_min_time);
                                editor.putInt(GlobalVariables.QR_MIN_TIME, Integer.parseInt(qr_min_time));
                                editor.putString(GlobalVariables.TASK_COINS,taskCoin);
                               // editor.putInt(GlobalVariables.REDEEM_MIN, Integer.parseInt(redeemamount));
                                editor.putString(GlobalVariables.REDEEM_AMOUNT, redeemamount);
                                editor.putString(GlobalVariables.ADD_TYPE, add_type);
                                editor.putString(GlobalVariables.BANNER_ADD, banner_add);
                                editor.putString(GlobalVariables.INDUSTRIAL_ADD, industrial_add);
                                editor.putString(GlobalVariables.INDUSTRIAL_CALL_TYPE, industrial_call_type);
                                editor.putString(GlobalVariables.REWARD_ADD, reward_add);
                                editor.putString(GlobalVariables.NATIVE_ADD, native_add);
                                editor.putString(GlobalVariables.FB_BANNER_AD1, fb_banner1);
                                editor.putString(GlobalVariables.FB_INTERSTITIAL_AD_ID_1, fb_indusrial1);
                                editor.putString(GlobalVariables.FB_NATIVE, fb_native);
                                editor.putString(GlobalVariables.FB_REWARD_AD_ID_1, fb_reward1);
                                editor.putInt(GlobalVariables.TASK_REWARD, Integer.parseInt(taskCoin));
                                editor.putString(GlobalVariables.ADDMOBPUBLISHER_ID, addmobpublisher_id);
                                editor.putString(GlobalVariables.ADDMOB_BANER, addmob_banner1);
                                editor.putString(GlobalVariables.ADDMOB_INDUSTRIAL, addmob_industrial1);
                                editor.putString(GlobalVariables.ADDMOB_REWARDED, addmob_rewarded1);
                                editor.putString(GlobalVariables.ADDMOB_NATIVE, addmob_native);
                                //editor.putString(GlobalVariables.FB_REWARD_COINS, rewardAdCoins);
                                editor.putString(GlobalVariables.TELEGRAM_LINK, telegram_link);
                                editor.putString(GlobalVariables.YOUTUBE_LINK, youtube_link);
                                editor.putString(GlobalVariables.FACEBOOK_PAGE, facebook_page);
                                editor.putFloat(GlobalVariables.NEW_VERSION, Float.parseFloat(new_version));
                                editor.putString(GlobalVariables.UPDATE_LINK, update_link);
                                editor.putString(GlobalVariables.ADMIN_MSG, admin_msg);
                                editor.putString(GlobalVariables.JOIN_GROUP, join_group);
                                editor.putString(GlobalVariables.APP_NAME, app_name);
                                editor.putString(GlobalVariables.APP_LOGO, app_logo);
                                editor.putString(GlobalVariables.APP_DESCRIPTION, app_description);
                                editor.putString(GlobalVariables.APP_VERSION, app_version);
                                editor.putString(GlobalVariables.APP_AUTHOR, app_author);
                                editor.putString(GlobalVariables.APP_EMAIL, app_email);
                                editor.putString(GlobalVariables.APP_WEBSITE, app_website);
                                editor.putString(GlobalVariables.APP_DEVELOPED_BY, app_developed_by);
                                editor.putString(GlobalVariables.PAYMENT_GATEWAY, payment_gateway);
                                editor.putString(GlobalVariables.PAYTM_MID, paytm_mid);
                                editor.putString(GlobalVariables.PAYTM_MERCHENT_KEY, paytm_key);
                                editor.putString(GlobalVariables.RAZORPAY_MID, razorpay_mid);
                                editor.putString(GlobalVariables.RAZORPAY_MERCHENT_KEY, razorpay_key);
                                editor.putString(GlobalVariables.PAYUMONEY_MID, payumoney_mid);
                                editor.putString(GlobalVariables.PAYUMONEY_MERCHENT_KEY, payumoney_key);
                                editor.putString(GlobalVariables.WIDTHRAW_NOTE, widthraw_note);


                                editor.apply();


                                settings = new Settings(joiningBonus,app_joining_fee,referRate,redeemamount,taskCoin,redeemMin,paytm_mid,add_type,banner_add,industrial_add,industrial_call_type,reward_add,native_add,fb_banner1,fb_indusrial1,fb_reward1,fb_native,addmob_banner1,addmob_industrial1,addmob_rewarded1,addmob_native
                                        ,addmobpublisher_id,telegram_link,youtube_link,facebook_page,new_version,update_link,admin_msg,join_group,app_name, app_logo, app_version, app_author, app_contact, app_email, app_website, app_description, app_developed_by
                                        ,  redeem_currency, payment_method1, payment_method2,reward_frequency);

                                GlobalVariables.settings=settings;
                                editor.apply();
                                GlobalVariables.adModelCount = new AdModelCount("0","0","0","0");
                                Log.d("Response-ls",payment_method1 );
                                Log.d("add_type",""+add_type+industrial_add );

                                editor.apply();

                            }
                            //adsConsent.checkForConsent();

                            goToMainPage();
                           // callinappupdate();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(SplashActivity.this, "Error: " + e.getMessage(),
                            //  Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(SplashActivity.this, "RESPONSE: " + error, Toast.LENGTH_SHORT).show();
                Log.e("KINGSN", "" + error.getMessage());
                //  Toast.makeText(SplashActivity.this, "ErrorV: " + error.getMessage(),
                // Toast.LENGTH_SHORT).show();

                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SplashActivity.this);
                alertDialogBuilder.setTitle("Something Went Wrong");
                alertDialogBuilder.setMessage("Please Try With Active Internet ");
                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                alertDialogBuilder.setPositiveButton(SplashActivity.this.getResources().getString(R.string.ok_message),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();

                                finishAffinity();
                            }
                        });

                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        }) { @Override
        protected Map<String, String> getParams() {

            Map<String, String> params = new HashMap<>();
            params.put("settings","");


            return params;
        }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SplashActivity.this);
        requestQueue.add(stringRequest);



    }*/

 /*   public void getAllData(Activity activity) {
        method.params.clear();
        method.params.put("device_id",method.getDeviceId(getApplicationContext()));
        Log.d(GlobalVariables.TAG, "getHomeData2: called"+activity.toString());
        new HttpsRequest(RestAPI.API_Settings, method.params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String message, JSONObject response) {

        if (flag) {
            // binding.tvNo.setVisibility(View.GONE);
            try {
                Log.d(GlobalVariables.TAG, "hKINGSN123:" + response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString());
                settings = new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString(), (Type) Settings.class);
                //globalState.setHomeData(homeDataDTO);
                //  MainActivity.setData2(adminDTO,activity);
             //   Log.d(GlobalVariables.TAG, "hKINGSN123:" + settings.getCountry() + settings.getSponsorby());
                GlobalVariables.settings = settings;
                // Toast.makeText(getContext(), "You Selected Your Slot"+homeDataDTO , Toast.LENGTH_SHORT).show();
                // setData();

                goToMainPage();


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this);
            try {
                alertDialogBuilder.setTitle(response.getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
            alertDialogBuilder.setPositiveButton(SplashActivity.this.getResources().getString(R.string.ok_message),
            new DialogInterface.OnClickListener() {
                @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();

                        //Log.d("Response",msg);
                        finishAffinity();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
                }
            }
        });
    }*/

    @Override
    public void onOSSubscriptionChanged(OSSubscriptionStateChanges stateChanges) {

        if (!stateChanges.getFrom().isSubscribed() &&
                stateChanges.getTo().isSubscribed()) {
            new AlertDialog.Builder(this)
                    .setMessage("You've successfully subscribed to push notifications!")
                    .show();

            stateChanges.getTo().getUserId();
        }

        Log.i("Debug", "onOSSubscriptionChanged: " + stateChanges);

    }




    public void checkspamapp(){
        pm = getPackageManager();
        //get a list of installed apps.
        packages = pm.getInstalledApplications(0);
        Log.d("KINGSN", "hello"+packages);

        ActivityManager mActivityManager = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        for (ApplicationInfo packageInfo : packages) {
            if((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM)==1)continue;
           else if(packageInfo.packageName.equals("abcdjob.V1infotech.com.Qrcode")) continue;
            else{
               // mActivityManager.killBackgroundProcesses("com.camel.corp.universalcopy");
                mActivityManager.killBackgroundProcesses(packageInfo.packageName);
            }
        }
    }

   private final int UPDATE_REQUEST_CODE=1612;
  public void callinappupdate() {
      AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);

// Returns an intent object that you use to check for an update.
     Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
      appUpdateInfoTask.addOnSuccessListener( appUpdateInfo ->{
          if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                  // This example applies an immediate update. To apply a flexible update
                  // instead, pass in AppUpdateType.FLEXIBLE
                  && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
          ) {
              try {
                  // Request the update.
                  appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE
                          ,SplashActivity.this,UPDATE_REQUEST_CODE);
              }catch (IntentSender.SendIntentException exception){
                  Log.e("KINGSN", "callinappupdate: "+exception.getMessage() );
              }
//done d
              }else{
             // LoadSettings();
              goToMainPage();

          }
          });
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null)return;
        if (requestCode==UPDATE_REQUEST_CODE){
            Toasty.success(SplashActivity.this, "App Update Started", Toast.LENGTH_LONG, true).show();
            goToMainPage();
       if (resultCode != RESULT_OK){
           Log.e("KINGSN", "onActivityResult: UPDATE FLOW FAILED"+resultCode);
           goToMainPage();
       }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // callinappupdate();
        //LoadSettings();
    }

    @Override
    public void onOSPermissionChanged(OSPermissionStateChanges stateChanges) {
        if (!stateChanges.getFrom().areNotificationsEnabled()) {
            new AlertDialog.Builder(this)
                    .setMessage("You've  unsubscribed to push notifications!")
                    .show();

            Log.i("Debug", "onOSSubscriptionChanged: " + stateChanges);
        }else{

            oneSignalPushToken=  OneSignal.getDeviceState().getUserId();
            oneSignalPlayerId=   OneSignal.getDeviceState().getPushToken();
            Log.d("KINGSN", "onOSPermissionChanged: "+oneSignalPlayerId  +  "pushtoken"+oneSignalPushToken);
        }
    }

}