package abcdjob.workonline.com.qrcode.ui.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.ContactActivity;
import abcdjob.workonline.com.qrcode.ui.Interface.InterstitialAdView;
import abcdjob.workonline.com.qrcode.ui.Interface.VideoAds;
import abcdjob.workonline.com.qrcode.ui.generatedcode.GeneratedCodeActivity;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;
import abcdjob.workonline.com.qrcode.ui.settings.SettingsActivity;
import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;


public class Method {
    private Constant Constant;
    private Settings settings;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    // UserDTO Loging logs added
    private InterstitialAdView interstitialAdView;
    private Class<? extends Method> activity;
    private VideoAds videoAd;
    public static boolean share = false, loginBack = false, allowPermitionExternalStorage = false, personalization_ad = false;
    private Context _context;
    private DBHelper dbHelper;
    String TAG="KINGSN";
    private com.facebook.ads.InterstitialAd interstitialfbAd;
    InterstitialAd mInterstitialAd;;
    private RewardedVideoAd rewardedVideoAd;
    com.facebook.ads.AdView adView;
    com.google.android.gms.ads.AdView mAdView;
    private GeneratedCodeActivity GeneratedCodeActivity;
    public HashMap<String, String> params = new HashMap<>();
    public Dialog loadingDialog;
    public SharedPrefrence preferencess;
    public UserDTO userDTO;
    TimerTask timerTask;
    Timer timer;
    Double time = 0.0;
    private int qrtime=0;
    private int qrtime1=0;
    boolean timerStarted = false;
    public String url,test,url2,checksum,orderId,mobile;

    @SuppressLint("CommitPrefEdits")
    public Method(Context context) {
        this._context = context;
        preferences = context.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        preferencess = SharedPrefrence.getInstance(context);
        userDTO = preferencess.getParentUser2(GlobalVariables.USER_DTO);
        Log.d(TAG, "Method: "+userDTO.getEmail()+userDTO.getMobile());
        editor = preferences.edit();
    }

    public void showFbBanner(Activity activity, FrameLayout FrameLayout)
    {

        adView = new com.facebook.ads.AdView(activity, GlobalVariables.settings.getFbBanner1(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container

        // Add the ad view to your activity layout
        (FrameLayout).addView(adView);

        // Request an ad
        adView.loadAd();
    }

    public void showFbBanner2(Activity activity, FrameLayout frameLayout)
    {

        adView = new com.facebook.ads.AdView(activity,GlobalVariables.settings.getFbBanner1(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container

        // Add the ad view to your activity layout
        (frameLayout).addView(adView);

        // Request an ad
        adView.loadAd();
    }

   /* public void showAddMobBanner(Context context, com.google.android.gms.ads.AdView adView)
    {

        mAdView = (com.google.android.gms.ads.AdView)adView;
        mAdView.setVisibility(View.VISIBLE);
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }*/




    //---------------Banner Ad---------------//
    private void showPersonalizedAds(FrameLayout linearLayout) {

        if (GlobalVariables.settings.getBannerAdd().equals("addmob")) {
            AdView adView = new AdView(_context);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            adView.setAdUnitId(settings.getAddmobBanner1());
            adView.setAdSize(AdSize.SMART_BANNER);
            linearLayout.addView(adView);
            adView.loadAd(adRequest);
        }
    }

    private void showNonPersonalizedAds(FrameLayout linearLayout) {
        Bundle extras = new Bundle();
        extras.putString("npa", "1");
        if (settings.equals("addmob")) {
            AdView adView = new AdView(_context);
            AdRequest adRequest = new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build();
            adView.setAdUnitId(settings.getAddmobBanner1());
            adView.setAdSize(AdSize.SMART_BANNER);
            linearLayout.addView(adView);
            adView.loadAd(adRequest);
        }
    }

    public void showBannerAd(FrameLayout linearLayout) {
        /*    Log.d("Response-ads", Boolean.toString(Constant.settings.isBanner_ad()));*/
        if(ConsentInformation.getInstance(_context).getConsentStatus() == ConsentStatus.NON_PERSONALIZED) {
            showNonPersonalizedAds(linearLayout);
        } else {
            showPersonalizedAds(linearLayout);
        }
    }
    //---------------Banner Ad---------------//

    //---------------Addmob Rewarded video ad---------------//

/*    public void showaddmobVideoAd(final Activity activity, final String uid, final String Device) {
        if (settings.getReward_add().equals("addmob")) {
           // Video_ads_limit_count(activity,uid,Device);
            if (abcdjob.V1infotech.com.Qrcode.ui.Util.Constant.VIDEO_AD_COUNT <= Integer.parseInt(settings.getReward_frequency())) {
                final ProgressDialog progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage(activity.getString(R.string.adsloading));
                progressDialog.setCancelable(false);
                progressDialog.show();
                mRewardedVideoAd = new RewardedVideoAd(activity, abcdjob.V1infotech.com.Qrcode.ui.Util.Constant.settings.getAddmob_rewarded1());
                if (settings != null) {
                    if (settings.getReward_add().equals("addmob")) {
                        if (mRewardedVideoAd != null) {
                            AdRequest adRequest;
                            if (ConsentInformation.getInstance(activity).getConsentStatus() == ConsentStatus.PERSONALIZED) {
                                adRequest = new AdRequest.Builder().build();
                            } else {
                                Bundle extras = new Bundle();
                                extras.putString("npa", "1");
                                adRequest = new AdRequest.Builder()
                                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                                        .build();
                            }

                            mRewardedVideoAd.loadAd(settings.getAddmob_rewarded1(), adRequest);
                            mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                                @Override
                                public void onRewarded(RewardItem reward) {
                                    Log.d("reward_video_ad", "reward");

                                   *//* Toast.makeText(activity, "Reward Point Added", Toast.LENGTH_SHORT).show();
                                    BalanceUpdate(uid,"Video Ads",Constant.settings.getVideo_add_point(),Constant.PublicIP,1);
                                    Ads_Count_Update(uid,Device);
                                    Video_ads_limit_count(activity,uid,Device);*//*
                                }

                                @Override
                                public void onRewardedVideoAdLeftApplication() {
                                    Log.d("reward_video_ad", "AdLeftApplication");
                                }

                                @Override
                                public void onRewardedVideoAdFailedToLoad(int i) {
                                    progressDialog.dismiss();
                                    Toast.makeText(activity, "Video Ads load Failed", Toast.LENGTH_SHORT).show();
                                    Log.d("reward_video_ad", "Failed");
                                }

                                @Override
                                public void onRewardedVideoAdClosed() {
                                    Events.VideoAdsReload adsReload = new Events.VideoAdsReload("100");
                                    EventBus.getDefault().post(adsReload);
                                    Log.d("reward_video_ad", "close");


                                }

                                @Override
                                public void onRewardedVideoAdLoaded() {
                                    //  mRewardedVideoAd.show();
                                    progressDialog.dismiss();
                                    Toast.makeText(activity, "Video Ads load", Toast.LENGTH_SHORT).show();
                                    Log.d("reward_video_ad", "Video Ads load");
                                }

                                @Override
                                public void onRewardedVideoAdOpened() {
                                    Log.d("reward_video_ad", "open");
                                }

                                @Override
                                public void onRewardedVideoStarted() {
                                    Log.d("reward_video_ad", "start");
                                }

                                @Override
                                public void onRewardedVideoCompleted() {
                                    Log.d("reward_video_ad", "completed");

                                }
                            });
                        }
                    } else {
                        progressDialog.dismiss();

                    }
                } else {
                    progressDialog.dismiss();
                }
            }else{ Toast.makeText(activity, "Daily Ads Limit Over", Toast.LENGTH_SHORT).show();}
        }else{ Toast.makeText(activity, "Daily Ads Limit Over", Toast.LENGTH_SHORT).show();}
    }*/

    //---------------End Of Addmob Rewarded video ad---------------//

    //---------------Interstitial Ad---------------//

    public void loadInter(Activity activity) {
        //Log.d(TAG, "loadInter: "+GlobalVariables.settings.getIndustrial_add());
        Log.d(TAG, "loadInter: "+ GlobalVariables.settings.getCallIndustrialOn());
        if (GlobalVariables.settings.getCallIndustrialOn().equals("addmob"))  {
            abcdjob.workonline.com.qrcode.ui.Util.Constant.AD_COUNT = abcdjob.workonline.com.qrcode.ui.Util.Constant.AD_COUNT + 1;
            if (abcdjob.workonline.com.qrcode.ui.Util.Constant.AD_COUNT == Integer.parseInt(GlobalVariables.settings.getRewardFrequency())) {
                abcdjob.workonline.com.qrcode.ui.Util.Constant.AD_COUNT = 0;
                AdRequest adRequest;
                if (ConsentInformation.getInstance(activity).getConsentStatus() == ConsentStatus.PERSONALIZED) {
                    adRequest = new AdRequest.Builder().build();
                } else {
                    Bundle extras = new Bundle();
                    extras.putString("npa", "1");
                    adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            .build();
                }

            }
        }
    }

    public void createaddmobInterstitialAd(Activity context, String adUnit){
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context,adUnit, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;


                        Log.e("KINGSN", "Addcalling activity"+GlobalVariables.adModelCount.getGenerate_clicked() );

                        if((GlobalVariables.adModelCount.getGenerate_clicked()== "GeneratedCodeActivity")) {
                            Log.e("KINGSN", "insertwallet:called " );
                            GeneratedCodeActivity generatedCodeActivity=new GeneratedCodeActivity();
                            // generatedCodeActivity.insertwallet();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent();
                                    intent.setClass(context, HomeActivity.class);
                                    context.startActivity(intent);
                                    GlobalVariables.adModelCount.setGenerate_clicked("0");
                                }
                            }, 500);
                            //Toast.makeText(GeneratedCodeActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                        }


                        ///

                    }

                });
    }



    //---------------AddMob Interstitial Ad---------------//

    public void  showaddmobInterstitialAd(Activity  activity) {
        Context context = activity;
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
            createaddmobInterstitialAd(activity, GlobalVariables.settings.getAddmobIndustrial1());

            Log.d("TAG", "The interstitial wasn't loaded yet.");

        }

        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                ifGeneratedCodeActivity(activity);

            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
                createaddmobInterstitialAd(activity, GlobalVariables.settings.getAddmobIndustrial1());

            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });


    }



    //---------------End Of Interstitial Ad---------------//

  /*  public void Video_ads_limit_count(final Activity activity, final String uid, final String Device){
    String login = RestAPI.API_Video_Ads_Count + "&user_id=" + uid + "&device_id=" + Device;
    AsyncHttpClient client = new AsyncHttpClient();
    client.get(login, null, new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Log.d("Response", new String(responseBody));
            String res = new String(responseBody);
            try {
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray(AppSid);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String success = object.getString("success");
                    if (success.equals("1")) {
                        int  Counter = Integer.parseInt(object.getString("ads_count"));
                        Constant.VIDEO_AD_COUNT = Counter;

                     *//*   int CountLimit= Integer.parseInt(Constant.settings.getDaily_spin_limit());*//*

                    }
                    else {

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    });
}

    public void Ads_Count_Update(final String uid, final String Device) {
        String login = RestAPI.API_Video_Ads_Count_update + "&user_id=" + uid + "&device_id=" + Device;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(login, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response-count", new String(responseBody));
                String res = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray(AppSid);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String success = object.getString("success");

                        if (success.equals("1")) {
                        } else {
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }*/


    //---------------Facebook Interstitial Ad---------------//

    public void showFacdebookindustrialAd(Activity activity1)
    {
        AudienceNetworkAds.initialize(activity1);
        interstitialfbAd = new com.facebook.ads.InterstitialAd(activity1, GlobalVariables.settings.getFbIndustrial1());
        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                //Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                //Log.e(TAG, "Interstitial ad dismissed.");

                //activity1.onTopResumedActivityChanged(true);
                /*if ((GlobalVariables.adModelCount.getGenerate_clicked() == "true")) {

                        GenerateFragment generateFragment=new GenerateFragment();
                        //editor.putBoolean(String.valueOf(GlobalVariables.generate_clicked),false);
                    GlobalVariables.adModelCount = new AdModelCount("true","true","true","false");

                    editor.putString(GlobalVariables.generate_clicked, "false");

                        editor.apply();
                        generateFragment.generateCode();

                } else {
                   // generateFragment.generateCode();
                    GlobalVariables.adModelCount = new AdModelCount("true","true","true","false");
                }*/
                Log.e("KINGSN", "Addcalling activity"+GlobalVariables.adModelCount.getGenerate_clicked() );

                if((GlobalVariables.adModelCount.getGenerate_clicked().equals("GeneratedCodeActivity"))) {
                    Log.e("KINGSN", "insertwallet:called " );
                    GeneratedCodeActivity generatedCodeActivity=new GeneratedCodeActivity();
                    // generatedCodeActivity.insertwallet();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.setClass(activity1, HomeActivity.class);
                            activity1.startActivity(intent);
                            GlobalVariables.adModelCount.setGenerate_clicked("0");
                        }
                    }, 1000);
                    //Toast.makeText(GeneratedCodeActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                //Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                Log.e("KINGSN", "Addcalling activity"+GlobalVariables.adModelCount.getGenerate_clicked() );

                ifGeneratedCodeActivity(activity1);

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                //Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialfbAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                //Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                //Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialfbAd.loadAd(
                interstitialfbAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    //---------------End of Facebook Interstitial Ad---------------//

    //---------------Facebook Reward Interstitial Ad---------------//

    public void showFacebookVideoAd(Context context){
        AudienceNetworkAds.initialize(context);
        rewardedVideoAd = new RewardedVideoAd(context, GlobalVariables.settings.getFbReward1());
        com.facebook.ads.RewardedVideoAdListener rewardedVideoAdListener = new com.facebook.ads.RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, com.facebook.ads.AdError error) {
                // Rewarded video ad failed to load
                Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                //GlobalVariables.adClickModel=new AdClickModel("0","1");
                rewardedVideoAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d(TAG, "Rewarded video completed!");

                // Call method to give reward
                // giveReward();
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }

    //--------------End Of Facebook Reward Interstitial Ad---------------//

    public void callindustrial(Activity  activity){
        if (!GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("false")) {
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")) {
                callfinalindus(activity);

            } else if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("on_wrong_code")) {
                callfinalindus(activity);
            } else if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("onscreen_opening")) {
                callfinalindus(activity);
            } else if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both")) {
                callfinalindus(activity);
            }


        }
    }

    public void callfinalindus(Activity activity){
        if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("addmob")) {
            showaddmobInterstitialAd(activity);

        }else if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("facebook")) {
            showFacdebookindustrialAd(activity);
        }else if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both")) {
            Log.e("KINGSN", "callfinalindus:BOTH " +GlobalVariables.adModelCount.getInterstitialadd());
            if (GlobalVariables.adModelCount.getInterstitialadd().equals("0")) {
                // GlobalVariables.adModelCount = new AdModelCount("1","0","0","0");
                showaddmobInterstitialAd(activity);
                GlobalVariables.adModelCount.setInterstitialadd("1");
            } else  if (GlobalVariables.adModelCount.getInterstitialadd().equals("1")) {
                // GlobalVariables.adModelCount = new AdModelCount("0","0","0","0");
                GlobalVariables.adModelCount.setInterstitialadd("0");
                showFacdebookindustrialAd(activity);
            }
            Log.e("KINGSN", "callfinalindus: "+GlobalVariables.adModelCount.getInterstitialadd() );
        }else if(GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("false")) {
            if(activity == GeneratedCodeActivity){

            }
        }
    }

    public void ifGeneratedCodeActivity(Activity activity){

        if((Objects.equals(GlobalVariables.adModelCount.getGenerate_clicked(), "GeneratedCodeActivity"))) {
            Log.e("KINGSN", "insertwallet:called " );
            GeneratedCodeActivity generatedCodeActivity=new GeneratedCodeActivity();
            // generatedCodeActivity.insertwallet();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setClass(activity, HomeActivity.class);
                    activity.startActivity(intent);
                    GlobalVariables.adModelCount.setGenerate_clicked("0");
                }
            }, 500);
            //Toast.makeText(GeneratedCodeActivity.this, "Updated", Toast.LENGTH_SHORT).show();

        }

    }

    public void showToasty(Activity activity,String Type,String Message){
        if(Type.equals("1")) {
            Toasty.success(activity, ""+Message, Toast.LENGTH_SHORT).show();
        }else{
            Toasty.error(activity, ""+Message, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            // Toast.makeText(context,  deviceId, Toast.LENGTH_SHORT).show();

        }else {

            final TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);



            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                //  Toast.makeText(LoginActivity.this,  deviceId, Toast.LENGTH_SHORT).show();
            }
        }

        return deviceId;

    }

    @SuppressLint("SimpleDateFormat")
    public static String convertTimestampDateToTime(String timestamp) {
        if (!timestamp.equals("")) {
            Timestamp tStamp = new Timestamp(Long.parseLong(timestamp));
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("dd MMM yyyy | hh:mm a");
            return simpleDateFormat.format(tStamp);
        } else {
            return "";
        }
    }

    public static String convertTimestampDate(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("MMM dd");
        return simpleDateFormat.format(tStamp);
    }

    public static String getFirstLetterCapital(String input) {
        String val = "";
        try {
            val = Character.toUpperCase(input.charAt(0)) + input.substring(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }

    public static String correctTimestamp(String timestampInMessage) {
        if (!timestampInMessage.equals("")) {
            long correctedTimestamp = Long.parseLong(timestampInMessage);

            if (String.valueOf(timestampInMessage).length() < 13) {

                int difference = 13 - String.valueOf(timestampInMessage).length(), i;
                String differenceValue = "1";
                for (i = 0; i < difference; i++) {
                    differenceValue += "0";
                }
                correctedTimestamp = (Long.parseLong(timestampInMessage) * Integer.parseInt(differenceValue))
                        + (System.currentTimeMillis() % (Integer.parseInt(differenceValue)));
            }
            return String.valueOf(correctedTimestamp);
        } else {
            return "";
        }


    }

    public void loadingDialogg(Activity activity) {
        loadingDialog = new Dialog(activity);
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                R.color.transparent);
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        } else {
            loadingDialog = new Dialog(activity);
            loadingDialog.setContentView(R.layout.lotiee_loading);
            loadingDialog.setCancelable(false);
            Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                    R.color.transparent);
            loadingDialog.show();
        }
    }

    public void startTimer2 (Activity activity, TextView mtext)
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time++;
                        mtext.setText(getTimerText());
                        timerStarted = true;

                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);


    }

    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        qrtime=seconds;
        qrtime1=minutes*60+qrtime;
//        editor.putString(GlobalVariables.timer_text, String.valueOf(seconds));
//        editor.apply();
        Log.e("KINGSN", "getTimerText:sec"+qrtime) ;
        Log.d(TAG, "getTimerText: "+qrtime1);
        return formatTime(seconds, minutes, hours);
    }

    @SuppressLint("DefaultLocale")
    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);

    }

 /*   public static void alert(Activity activity){

        if(GlobalVariables.usermDTO.getAllow() == "2") {
            Toast.makeText(activity, "Your account is blocked", Toast.LENGTH_SHORT).show();
            //finish();
            //return;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.customdialogup, viewGroup, false);
            TextView text = dialogView.findViewById(R.id.text);
            TextView text2 = dialogView.findViewById(R.id.cssub);
            text.setText("Contact Admin");
            text2.setText("You Are Blocked !");
            ImageView image = dialogView.findViewById(R.id.image);
            image.setImageResource(R.drawable.mainl);
            Button appUpdate = dialogView.findViewById(R.id.dialogButtonOK);
            appUpdate.setText("Contact Now");
            builder.setView(dialogView);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            appUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    activity.startActivity(new Intent(activity, ContactActivity
                            .class));
                    activity.finish();

                }
            });

        }else
        if(GlobalVariables.usermDTO.getJoiningPaid() == "1") {
            //Toast.makeText(activity, "Your account is blocked", Toast.LENGTH_SHORT).show();
            //finish();
            //return;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.customdialogup2, viewGroup, false);
            TextView text = dialogView.findViewById(R.id.cssub2);
            TextView text2 = dialogView.findViewById(R.id.cssub3);
            text.setText("One Step Away To Your Account");
            text2.setText("Just Pay The App Joining Fee and \n" +
                    " Unlock Your Dashboard");
            ImageView image = dialogView.findViewById(R.id.image);
            image.setImageResource(R.drawable.paym);
            Button appPay = dialogView.findViewById(R.id.paybtn);
            Button appPay3 = dialogView.findViewById(R.id.paybtn3);
            appPay.setText("Pay Now");
            appPay3.setText("Pay By Cash");
            appPay3.setVisibility(View.VISIBLE);
            builder.setView(dialogView);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            appPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                                        Activity.startActivity(new Intent(activity, SettingsActivity
                                                .class));
                    //loadingDialog.show();
                    // GenerateChecksum();
                    startPayment(activity);
                    // finish();


                }
            });

            appPay3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                                        activity.startActivity(new Intent(activity, SettingsActivity
                                                .class));
                    //loadingDialog.show();

                    onSuccessPay(activity,orderId);
                    // finish();

                }
            });

        }else
        if(GlobalVariables.usermDTO.getJoiningPaid()  == "3") {
            //Toast.makeText(activity, "Your account is blocked", Toast.LENGTH_SHORT).show();
            //finish();
            //return;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.customdialogup2, viewGroup, false);
            TextView text = dialogView.findViewById(R.id.cssub2);
            TextView text2 = dialogView.findViewById(R.id.cssub3);
            text.setText("Verification Pending ");
            text2.setText("Your Last  App Joining Fee  \n Payment is " +
                    " Under Verification \n Plese Wait For The Approval !");
            ImageView image = dialogView.findViewById(R.id.image);
            image.setImageResource(R.drawable.paym);
            Button appPay = dialogView.findViewById(R.id.paybtn);
            Button appPay2 = dialogView.findViewById(R.id.paybtn2);
            appPay2.setVisibility(View.VISIBLE);
            appPay.setText("Contact us");
            appPay2.setText("Exit");
            builder.setView(dialogView);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            appPay2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                                        activity.startActivity(new Intent(activity, SettingsActivity
                                                .class));
                    //loadingDialog.show();
                    //GenerateChecksum();
                    // finish();
                    activity.finish();
                    loadingDialog.dismiss();
                    activity.finishAffinity();

                }
            });

            appPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    activity.startActivity(new Intent(activity, ContactActivity
                            .class));
                    //loadingDialog.show();
                    //GenerateChecksum();
                    // finish();
                    activity.finish();
                    loadingDialog.dismiss();


                }
            });

        }
    }

    public static void onSuccessPay(Activity activity, String orderId) {

        if(orderId ==null){
            //Toast.makeText(activity, "NULL", Toast.LENGTH_LONG).show();
            //editor.putString(GlobalVariables.txn_orderId, orderId);


            Random r = new Random(System.currentTimeMillis());
            orderId = "BYCASH" + (1 + r.nextInt(2)) * 1000
                    + r.nextInt(1000);
            editor.putString(GlobalVariables.txn_orderId, orderId);

        }
        Method method = new Method(activity);
        method.loadingDialogg(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_insert_payment_verification,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                        try {
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray(GlobalVariables.AppSid);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");

                                if (success.equals("1")) {
                                    Toast.makeText(activity, "Updated", Toast.LENGTH_SHORT).show();
                                    activity.finish();
                                    // startActivity(getIntent());
                                    Method method = new Method(activity);
                                    method.loadingDialog.dismiss();

                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                                    alertDialogBuilder.setTitle(object.getString("title"));
                                    alertDialogBuilder.setMessage(object.getString("msg"));
                                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                    alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    activity.finish();
                                                    method.loadingDialog.dismiss();
                                                    activity.finishAffinity();
                                                }
                                            });

                                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    Toast.makeText(activity, object.getString("msg"), Toast.LENGTH_LONG).show();


                                } else {
                                    method.loadingDialog.dismiss();
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                                    alertDialogBuilder.setTitle(object.getString("title"));
                                    alertDialogBuilder.setMessage(object.getString("msg"));
                                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                    alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    activity.finish();
                                                    method.loadingDialog.dismiss();
                                                    activity. finishAffinity();
                                                }
                                            });

                                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    Toast.makeText(activity, object.getString("msg"), Toast.LENGTH_LONG).show();
                                }

                            }

                            // progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "Error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "RESPONSE: " + error, Toast.LENGTH_SHORT).show();
                Log.e("Error", "" + error.getMessage());
                Toast.makeText(activity, "ErrorV: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                method.loadingDialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle("Something Went Wrong");
                alertDialogBuilder.setMessage("Please Try With Active Internet ");
                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                activity.finish();
                                method.loadingDialog.dismiss();
                                activity.finishAffinity();
                            }
                        });

                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        }) { @Override
        protected Map<String, String> getParams() {

            Map<String, String> params = new HashMap<>();
            params.put("app_joining_fee_paid","");
            params.put("user_id", preferences.getString(GlobalVariables.USER_MOBILE,""));
            params.put("name",preferences.getString(GlobalVariables.USERNAME,""));
            params.put("email",preferences.getString(GlobalVariables.USER_EMAIL,""));
            params.put("paid",preferences.getString(GlobalVariables.APP_JOINING_FEE,""));
            params.put("order_id", orderId);
            params.put("city",preferences.getString(GlobalVariables.USER_CITY,""));

            return params;
        }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);



    }


    public static void startPayment(Activity activity){
        if(preferences.getString(GlobalVariables.PAYMENT_GATEWAY, "").equals("paytm_gateway")){
            GenerateChecksum(activity);
        }else if(preferences.getString(GlobalVariables.PAYMENT_GATEWAY, "").equals("razorpay_gateway")) {
            startRazorPayment(activity);
        }else if(preferences.getString(GlobalVariables.PAYMENT_GATEWAY, "").equals("payumoney_gateway")) {
            startPayuPayment(activity);
        }else{

        }
    }


    public void startRazorPayment(Activity activity) {
        Checkout checkout = new Checkout();
        *
         * You need to pass current activity in order to let Razorpay create CheckoutActivity


        final Checkout co = new Checkout();
        checkout.setKeyID(preferences.getString(GlobalVariables.PAYUMONEY_MERCHENT_KEY,""));
        *
         * Instantiate Checkout



        *
         * Set your logo here

        checkout.setImage(R.mipmap.ic_launcher_round);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "V1infotech Pvt.Ltd");
            options.put("description", "App Joining Fee");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://v1infotech.com/admin/assets/images/profile");
            options.put("currency", "INR");

            String payment = (preferences.getString(GlobalVariables.APP_JOINING_FEE,""));;

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", preferences.getString(GlobalVariables.USER_EMAIL,""));
            preFill.put("contact", preferences.getString(GlobalVariables.USER_MOBILE,""));
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }








    public static void onPaymentSuccess(Activity activity,String s, PaymentData paymentData) {

        Toast.makeText(activity, "oid"+paymentData.getOrderId()+"pid"+paymentData.getPaymentId()+"user contact" +
                paymentData.getUserContact()+"user email"+paymentData.getUserEmail()  , Toast.LENGTH_SHORT).show();
        onSuccessPay(activity,paymentData.getOrderId());

    }

  
    public static void onPaymentError(Activity activity,int i, String s, PaymentData paymentData) {

        alert(activity);

    }}
*/
}
