package abcdjob.workonline.com.qrcode.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.facebook.ads.InterstitialAd;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;
import abcdjob.workonline.com.qrcode.ui.settings.SettingsActivity;

import static abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables.ADMIN_PREF;


public class RefferActivity extends AppCompatActivity {

    private Dialog dialog;
    private Button shareBtn,referTv;
    private ImageButton refertgbtn,referwatsbtn,referbtnmore,referfbtn,referybtn,referfbtn2,refertgbtn2;
    FirebaseAuth auth;
    FirebaseUser user;
    String referCode = " ";
    private TextView refercontent;
    private SharedPreferences preferences;
    private Dialog loadingDialog;
    private FrameLayout adlayout2;
    private FrameLayout adview;
    private FrameLayout adView2;
    private Method method;


    InterstitialAd interstitialAd;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reffer);
        preferences = getSharedPreferences(ADMIN_PREF, MODE_PRIVATE);
        method=new Method(this);
       /* adlayout2=(FrameLayout) findViewById(R.id.adlayout2);
        adview  = (FrameLayout) findViewById(R.id.adView);
        adView2 = (FrameLayout) findViewById(R.id.adView2);*/

        referwatsbtn=(ImageButton) findViewById(R.id.referwatsbtn);
        refertgbtn=(ImageButton) findViewById(R.id.refertgbtn);
        referbtnmore=(ImageButton) findViewById(R.id.referbtnmore);
        referfbtn=(ImageButton) findViewById(R.id.referfbtn);
        referybtn=(ImageButton) findViewById(R.id.referybtn);
        referfbtn2=(ImageButton) findViewById(R.id.referfbtn2);
        refertgbtn2=(ImageButton) findViewById(R.id.refertgbtn2);
        refercontent=(TextView)findViewById(R.id.refercontent);


        /*Ads ads=new Ads();
        Ads ads2=new Ads();*/
        init();
        clickListener();
        //loadData();
        //adview  = (FrameLayout) findViewById(R.id.adView);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg));


      //  Ads Ads = new Ads();


        //ads=new Ads();
        //ads.InterstitialAd(this);

        TextPaint paint = referTv.getPaint();
        float width = paint.measureText(referCode);

        Shader textShader = new LinearGradient(0, 0, width, referTv.getTextSize(),
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#FDB54E"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP);
        referTv.getPaint().setShader(textShader);
        referTv.setText(method.userDTO.getUserReferalCode());
        referCode=(method.userDTO.getUserReferalCode());
        refercontent.setText("Invite Someone To The App \n"+"You Will Get ₹ "+
                GlobalVariables.settings.getPerRefer()+
                "  When Someone Joins using Your Referal Code");

/*
        if (GlobalVariables.aboutUsList.getBanner_ad_type().toLowerCase().equals("addmob"))
        {
            final com.google.android.gms.ads.AdView mAdView = new com.google.android.gms.ads.AdView(ReferActivity.this);
            mAdView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
            mAdView.setAdUnitId(preferences.getString(GlobalVariables.ADDMOB_BANER,""));
            adview=(FrameLayout) findViewById(R.id.adView);
            adview.addView(mAdView);
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

                final com.google.android.gms.ads.AdView mAdView = new com.google.android.gms.ads.AdView(ReferActivity.this);
                mAdView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
                mAdView.setAdUnitId(preferences.getString(GlobalVariables.ADDMOB_BANER,""));
                adview=(FrameLayout) findViewById(R.id.adView);
                adview.addView(mAdView);
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


        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
       // Ads.ScreenopeningInterstitialAd(ReferActivity.this);

/*

        if (GlobalVariables.adClickModel.getInterstitial().equals("0"))
        {
            Ads.ScreenopeningInterstitialAd(ReferActivity.this);
        }
*/


        // loadAd();

       /* if (interstitialAd.isAdLoaded()) {
            dialog.dismiss();
            interstitialAd.show();*/
/*
            interstitialAd.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    loadAd();
                }

                @Override
                public void onError(Ad ad, AdError adError) {

                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
*/
        }






    private void clickListener() {

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hey, \nDownload the " +
                        getString(R.string.app_name) +
                        ".The best earning app.\nJoin using my referral code to get bonus\n" +
                        "My referral code is  " +
                        referCode + "\n" +
                        "https://play.google.com/store/apps/details?id=" + getPackageName();
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

        referTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Refer Code", referCode);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(RefferActivity.this, "Copied", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void init() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shareBtn = findViewById(R.id.shareBtn);
        referTv = findViewById(R.id.referTv);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCancelable(false);
        if (loadingDialog.getWindow() != null)
            loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg));

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        preferences = getSharedPreferences(ADMIN_PREF, MODE_PRIVATE);

       /* String bannerID = preferences.getString(FB_BANNER_AD1, " ");
        adView = new com.facebook.ads.AdView(this, bannerID, AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.adView);
        adContainer.addView(adView);
        adView.loadAd();*/

        referbtnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hey, \nDownload the " +
                        preferences.getString(GlobalVariables.APP_NAME, "") +
                        ".The best earning app.\nJoin using my referral code to get bonus\n" +
                        "My referral code is  " +
                        preferences.getString(GlobalVariables.USER_REFERAL_CODE, "") + "\n" +
                        "https://play.google.com/store/apps/details?id=" + getPackageName();
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        referwatsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = "Hey, \nDownload the " +
                        preferences.getString(GlobalVariables.APP_NAME, "") +
                        ".The best earning app.\nJoin using my referral code to get bonus\n" +
                        "My referral code is  " +
                        preferences.getString(GlobalVariables.USER_REFERAL_CODE, "") + "\n" +
                        "https://play.google.com/store/apps/details?id=" + getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                if (sendIntent != null) {
                    startActivity(Intent.createChooser(sendIntent, "Share via Whatsapp"));
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(RefferActivity.this, "Whatsapp is not installed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        refertgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = "Hey, \nDownload the " +
                        preferences.getString(GlobalVariables.APP_NAME, "") +
                        ".The best earning app.\nJoin using my referral code to get bonus\n" +
                        "My referral code is  " +
                        preferences.getString(GlobalVariables.USER_REFERAL_CODE, "") + "\n" +
                        "https://play.google.com/store/apps/details?id=" + getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("org.telegram.messenger");
                if (sendIntent != null) {
                    startActivity(Intent.createChooser(sendIntent, "Share via Telegram"));
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(RefferActivity.this, "Telegram is not installed", Toast.LENGTH_SHORT).show();
                }

            }

        });


        referfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = "Hey, \nDownload the " +
                        preferences.getString(GlobalVariables.APP_NAME, "") +
                        ".The best earning app.\nJoin using my referral code to get bonus\n" +
                        "My referral code is  " +
                        preferences.getString(GlobalVariables.USER_REFERAL_CODE, "") + "\n" +
                        "https://play.google.com/store/apps/details?id=" + getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.katana");
                if (sendIntent != null) {
                    startActivity(Intent.createChooser(sendIntent, "Share via Facebook"));
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(RefferActivity.this, "Facebook is not installed", Toast.LENGTH_SHORT).show();
                }

            }

        });

        refertgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_VIEW);
                //sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                //sendIntent.setType("text/plain");
                sendIntent.setPackage("org.telegram.messenger");
                sendIntent.setData(Uri.parse(preferences.getString(GlobalVariables.TELEGRAM_LINK,"")));
                if (sendIntent != null) {
                    //startActivity(Intent.createChooser(sendIntent, ""));
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(RefferActivity.this, "Telegram is not installed", Toast.LENGTH_SHORT).show();
                }
            }

        });

        referfbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_VIEW);
                //sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                //sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.katana");
                sendIntent.setData(Uri.parse(preferences.getString(GlobalVariables.FACEBOOK_PAGE,"")));
                if (sendIntent != null) {
                    //startActivity(Intent.createChooser(sendIntent, ""));
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(RefferActivity.this, "Facebook App is not installed", Toast.LENGTH_SHORT).show();
                }
            }

        });

        referybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_VIEW);
                //sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                //sendIntent.setType("text/plain");
                sendIntent.setPackage("com.google.android.youtube");
                sendIntent.setData(Uri.parse(preferences.getString(GlobalVariables.YOUTUBE_LINK,"")));
                if (sendIntent != null) {
                    //startActivity(Intent.createChooser(sendIntent, ""));
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(RefferActivity.this, "Youtube App is not installed", Toast.LENGTH_SHORT).show();
                }
            }

        });


/*
        referybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse("vnd.youtube.com/channel/UC2e2buvh925MVaZLTeGj6tA?sub_confirmation=1"));
                startActivity(intent);
            }

        });
*/
    }


    /*private void loadAd() {

        String id = preferences.getString(FB_INTERSTITIAL_AD_ID_2, "");
        interstitialAd = new InterstitialAd(this, id);
        interstitialAd.loadAd();

    }*/

    @Override
    public void onBackPressed(){startActivity(new Intent(RefferActivity.this, SettingsActivity.class)); /*{
        if (interstitialAd.isAdLoaded()) {
            interstitialAd.show();
            InterstitialAdListener interstitialAdListener=new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {

                    finish();
                    startActivity(new Intent(ReferActivity.this, SuperActivity.class));

                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {

                }

                @Override
                public void onAdLoaded(Ad ad) {
                    interstitialAd.show();

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };*/

/*
            interstitialAd.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {

                    finish();

                }

                @Override
                public void onError(Ad ad, AdError adError) {

                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
*/
        }
    }



