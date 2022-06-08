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
import abcdjob.workonline.com.qrcode.Models.codeGen;
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
    private SharedPreferences sharedPreferences;
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
         getAllData(SplashActivity.this);
        sharedPreferences=getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
      //  goToMainPage();
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



    public void getAllData(Activity activity) {
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
                settings= new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").getJSONObject("Settings").toString(), (Type) Settings.class);
//                editor.apply();

                GlobalVariables.settings=settings;
                editor.apply();

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
    }



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