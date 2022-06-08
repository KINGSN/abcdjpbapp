package abcdjob.workonline.com.qrcode.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import abcdjob.workonline.com.qrcode.Data_Device;
import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.HomeDataDTO;
import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.Models.codeGen;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.databinding.ActivityHomeBinding;
import abcdjob.workonline.com.qrcode.helpers.util.PermissionUtil;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.profileFragment;
import abcdjob.workonline.com.qrcode.ui.ContactActivity;
import abcdjob.workonline.com.qrcode.ui.LoginActivity;
import abcdjob.workonline.com.qrcode.ui.Util.Constant;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;
import abcdjob.workonline.com.qrcode.ui.WalletFragment;
import abcdjob.workonline.com.qrcode.ui.adapter.RedeemAdapter;
import abcdjob.workonline.com.qrcode.ui.adapter.Redeem_Data;
import abcdjob.workonline.com.qrcode.ui.adapter.WalletAdapter;
import abcdjob.workonline.com.qrcode.ui.adapter.Wallet_Data;
import abcdjob.workonline.com.qrcode.ui.generate.GenerateFragment;
import abcdjob.workonline.com.qrcode.ui.settings.SettingsActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables.TAG;
import static android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION;
import static android.provider.Settings.canDrawOverlays;
import static com.google.android.gms.ads.AdSize.SMART_BANNER;
import static abcdjob.workonline.com.qrcode.ui.Util.Constant.*;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultWithDataListener, View.OnTouchListener {

    private ActivityHomeBinding mBinding;
    private Menu mToolbarMenu;
    private SharedPreferences preferences, preferences1;
    private SharedPreferences.Editor editor;
    LinearLayout tab1, tab2, tab3, withdwawalTab, transactionTab, redeemTab;
    public String url;
    public String test;
    public String url2;
    public static String checksum;
    public static String orderId;
    public String mobile;
    RecyclerView recyclerView, recyclerView1;
    WalletAdapter recyclerAdapter;
    RedeemAdapter recyclerAdapter1;
    private List<Wallet_Data> wallet_data;
    private List<Redeem_Data> redeem_data;
    TextView withdrawalTotal, wallet_total, maxredm, tabtt1, tabtt2, tabtt3;
    EditText payTmMobile, withdrawalAmount;
    Button withdrawalBtn;
    String Email, reqUrl;
    ProgressBar progressBar;
    TextView total;
    FirebaseUser user;
    private FrameLayout adview;
    public Dialog loadingDialog;
    com.facebook.ads.AdView adViewfb;
    HomeDataDTO homeDataDTO;
    //private Dialog dialog;
    public static Method method;
    Integer intwallet;
    private Spinner spinner_widthrawal;

    public static final String MID = DeviceID;
    public static final String WEBSITE = "DEFAULT";
    public static final String CHANNEL_ID = "WAP";
    public static final String INDUSTRY_TYPE_ID = "Retail";
    public static final String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
    Data_Device globalState;
    public final static int PERM_REQUEST_CODE_DRAW_OVERLAYS = 1234;

    public Menu getToolbarMenu() {
        return mToolbarMenu;
    }

    public void setToolbarMenu(Menu toolbarMenu) {
        mToolbarMenu = toolbarMenu;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        editor = preferences.edit();
        test = preferences.getString(GlobalVariables.USER_MOBILE, "");
        final String MID = preferences.getString(GlobalVariables.PAYTM_MID, "");
        // getWindow().setBackgroundDrawable(null);

        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method = new Method(this);
        loadingDialog = new Dialog(HomeActivity.this);
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        if (loadingDialog.getWindow() != null)
            loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        // loadingDialog.show();
        /*Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                R.color.transparent);*/
       // permissionToDrawOverlays();
      //  checkDrawOverlayPermission(HomeActivity.this);
        Checkout.preload(getApplicationContext());
        setListeners();
        initializeToolbar();
        initializeBottomBar();
        //checkInternetConnection();
        // loadDataSQL(HomeActivity.this);
        //  getHomeData(HomeActivity.this);
        // ads.showFacebookVideoAd((FragmentActivity) getContext());
        playAd();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //initOrderId();
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void checkInternetConnection() {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(ReactiveNetwork
                .observeNetworkConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    if (connectivity.state() == NetworkInfo.State.CONNECTED) {
                        mBinding.adView.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.adView.setVisibility(View.GONE);
                    }

                }, throwable -> {
                    Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                }));
    }

    private void playAd() {
        Method ads = new Method(HomeActivity.this);
        adview = (FrameLayout) findViewById(R.id.adView);
        Log.e("KINGSN", "BANNER" + preferences.getString(GlobalVariables.BANNER_ADD, "") + preferences.getString(GlobalVariables.ADDMOB_BANER, ""));
        if (Objects.equals(Objects.requireNonNull(preferences.getString(GlobalVariables.BANNER_ADD, "")).toLowerCase(), "addmob")) {
            // ads.showBannerAd(mBinding.adView);
            // AdRequest adRequest = new AdRequest.Builder().build();
            /* mBinding.adView.loadAd();*/

            final AdView mAdView = new AdView(HomeActivity.this);
            // mAdView.setAdSize(SMART_BANNER);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(preferences.getString(GlobalVariables.ADDMOB_BANER, ""));
            adview = (FrameLayout) findViewById(R.id.adView);
            adview.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            Log.e("KINGSN", "playAd: Adrequested ");
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // ads.showBannerAd(mBinding.adView);
                    mBinding.adView.setVisibility(View.VISIBLE);
                    Log.e("KINGSN", "playAd: Adrequested loaded ");
                }


                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mBinding.adView.setVisibility(View.GONE);
                    Log.e("KINGSN", "playAd: Failed to Load ");
                }

                @Override
                public void onAdOpened() {
                }

                @Override
                public void onAdClosed() {
                }
            });
            mAdView.setAdListener(new AdListener() {
            });

        } else if (Objects.equals(Objects.requireNonNull(preferences.getString(GlobalVariables.BANNER_ADD, "")).toLowerCase(), "facebook")) {
            Log.e("KINGSN", "playAd: Facebook");
            // ads.showFbBanner2(HomeActivity.this,adview);
            showFbBanner(this, (FrameLayout) findViewById(R.id.adView));

        } else if (Objects.equals(Objects.requireNonNull(preferences.getString(GlobalVariables.BANNER_ADD, "")).toLowerCase(), "both")) {
            {
                Log.e("KINGSN", "playAd: Both modelcount" + GlobalVariables.adModelCount.getBanneradd());
                if (Objects.equals(GlobalVariables.adModelCount.getBanneradd(), "0")) {

                    final AdView mAdView = new AdView(HomeActivity.this);
                    mAdView.setAdSize(SMART_BANNER);
                    mAdView.setAdUnitId(preferences.getString(GlobalVariables.ADDMOB_BANER, ""));
                    adview = (FrameLayout) findViewById(R.id.adView);
                    adview.addView(mAdView);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                    adview.setVisibility(View.INVISIBLE);
                    mAdView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {

                            // Code to be executed when an ad finishes loading.
                            mBinding.adView.setVisibility(View.VISIBLE);
                            GlobalVariables.adModelCount.setBanneradd("1");
                            adview.setVisibility(View.VISIBLE);
                        }


                        @Override
                        public void onAdOpened() {
                            // Code to be executed when an ad opens an overlay that
                            // covers the screen.
                            //  GlobalVariables.adModelCount = new AdModelCount("1","0","0","0");
                            GlobalVariables.adModelCount.setBanneradd("1");
                        }

                        @Override
                        public void onAdClicked() {
                            // Code to be executed when the user clicks on an ad.
                        }


                        @Override
                        public void onAdClosed() {
                            // Code to be executed when the user is about to return
                            // to the app after tapping on an ad.
                        }
                    });


                } else if (Objects.equals(GlobalVariables.adModelCount.getBanneradd(), "1")) {
                    showFbBanner(this, (FrameLayout) findViewById(R.id.adView));

                    // ads.showFbBanner(this,(FrameLayout) findViewById( R.id.adView));
                    adview = (FrameLayout) findViewById(R.id.adView);
                    // GlobalVariables.adModelCount = new AdModelCount("0","0","0","0");
                    GlobalVariables.adModelCount.setBanneradd("0");
                    adview.setVisibility(View.VISIBLE);
                    mBinding.adView.setVisibility(View.VISIBLE);
                }

                //

            }
        }
       /* if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("onscreen_opening")
                ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime"))
        {
           ads.callindustrial(HomeActivity.this);
        }*/
    }

    private void initializeToolbar() {
        setSupportActionBar(mBinding.toolbar);
    }

    public void showFbBanner(Activity activity, FrameLayout linearLayout) {

        adViewfb = new com.facebook.ads.AdView(activity, preferences.getString(GlobalVariables.FB_BANNER_AD1, ""), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        FrameLayout adContainer = (linearLayout);

        // Add the ad view to your activity layout
        adContainer.addView(adViewfb);

        // Request an ad
        adViewfb.loadAd();
        adview.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        setToolbarMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {
        mBinding.textViewGenerate.setOnClickListener(this);
        mBinding.textViewScan.setOnClickListener(this);
        mBinding.textViewHistory.setOnClickListener(this);

        mBinding.imageViewGenerate.setOnClickListener(this);
        mBinding.imageViewScan.setOnClickListener(this);
        mBinding.imageViewHistory.setOnClickListener(this);

        mBinding.constraintLayoutGenerateContainer.setOnClickListener(this);
        mBinding.constraintLayoutScanContainer.setOnClickListener(this);
        mBinding.constraintLayoutHistoryContainer.setOnClickListener(this);
    }

    private void initializeBottomBar() {
        clickOnGenerate();
        getVisibleFragment(HomeActivity.this);
        Log.d("KINGSU", "initializeBottomBar: " + getVisibleFragment(HomeActivity.this));
    }

    private void clickOnGenerate() {
        mBinding.textViewGenerate.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_selected));

        mBinding.textViewScan.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_normal));

        mBinding.textViewHistory.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_normal));

        mBinding.imageViewGenerate.setVisibility(View.INVISIBLE);
        mBinding.imageViewGenerateActive.setVisibility(View.VISIBLE);

        mBinding.imageViewScan.setVisibility(View.VISIBLE);
        mBinding.imageViewScanActive.setVisibility(View.INVISIBLE);

        mBinding.imageViewHistory.setVisibility(View.VISIBLE);
        mBinding.imageViewHistoryActive.setVisibility(View.INVISIBLE);

        setToolbarTitle(getString(R.string.toolbar_title_generate));
        //  GlobalVariables.adModelCount = new AdModelCount("true","true","HomeActivity","GeneratedCodeActivity");
        //GlobalVariables.adModelCount = new AdModelCount("0","0","0","0");


        showFragment(new GenerateFragment());
    }

    private void clickOnScan() {
        mBinding.textViewGenerate.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_normal));

        mBinding.textViewScan.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_selected));

        mBinding.textViewHistory.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_normal));

        mBinding.imageViewGenerate.setVisibility(View.VISIBLE);
        mBinding.imageViewGenerateActive.setVisibility(View.INVISIBLE);

        mBinding.imageViewScan.setVisibility(View.INVISIBLE);
        mBinding.imageViewScanActive.setVisibility(View.VISIBLE);

        mBinding.imageViewHistory.setVisibility(View.VISIBLE);
        mBinding.imageViewHistoryActive.setVisibility(View.INVISIBLE);

        setToolbarTitle(getString(R.string.toolbar_title_profile));
        showFragment(new profileFragment());

    }

    private void clickOnHistory() {
        mBinding.textViewGenerate.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_normal));

        mBinding.textViewScan.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_normal));

        mBinding.textViewHistory.setTextColor(
                ContextCompat.getColor(this, R.color.bottom_bar_selected));

        mBinding.imageViewGenerate.setVisibility(View.VISIBLE);
        mBinding.imageViewGenerateActive.setVisibility(View.INVISIBLE);

        mBinding.imageViewScan.setVisibility(View.VISIBLE);
        mBinding.imageViewScanActive.setVisibility(View.INVISIBLE);

        mBinding.imageViewHistory.setVisibility(View.INVISIBLE);
        mBinding.imageViewHistoryActive.setVisibility(View.VISIBLE);

        setToolbarTitle(getString(R.string.toolbar_title_wallet));
        showFragment(new WalletFragment());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_generate:
            case R.id.text_view_generate:
            case R.id.constraint_layout_generate_container:

                clickOnGenerate();
                break;

            case R.id.image_view_scan:
            case R.id.text_view_scan:
            case R.id.constraint_layout_scan_container:
                clickOnScan();
                break;

            case R.id.image_view_history:
            case R.id.text_view_history:
            case R.id.constraint_layout_history_container:
                clickOnHistory();
                break;
        }
    }

    private void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.coordinator_layout_fragment_container, fragment,
                fragment.getClass().getSimpleName());
        transaction.commit();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtil.REQUEST_CODE_PERMISSION_DEFAULT) {
            boolean isAllowed = true;

            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isAllowed = false;
                }
            }

            if (isAllowed) {
                clickOnScan();
            }
        }
    }

    public void hideAdMob() {
        if (mBinding.adView.isShown())
            mBinding.adView.setVisibility(View.GONE);
    }

    public void showAdmob() {
        if (!mBinding.adView.isShown())
            mBinding.adView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        if (mBinding.imageViewGenerateActive.getVisibility() != View.VISIBLE) {

            clickOnGenerate();

        } else {
            finish();
            finishAffinity();

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        // loadDataSQL(HomeActivity.this);
    }


    public Fragment getVisibleFragment(Activity activity) {
        FragmentManager fragmentManager = HomeActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }

    public void permissionToDrawOverlays() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
            if (!canDrawOverlays(this)) {
                Intent intent = new Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERM_REQUEST_CODE_DRAW_OVERLAYS);
            }
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERM_REQUEST_CODE_DRAW_OVERLAYS) {
            if (Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
                if (!canDrawOverlays(this)) {
                    // ADD UI FOR USER TO KNOW THAT UI for SYSTEM_ALERT_WINDOW permission was not granted earlier...
                }
            }
        }
    }*/

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission(Context context) {
        // check if we already  have permission to draw over other apps
        if (canDrawOverlays(context)) {
            // code
        } else {
            // if not construct intent to request permission
            final Intent intent = new Intent(ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            // request permission via start activity for result
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check if received result code
        // is equal our requested code for draw permission
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // if so check once again if we have permission
            if (canDrawOverlays(this)) {
                // continue here - permission was granted

            }
        }
        method.onActivityResult(HomeActivity.this,requestCode,resultCode, data);
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(data, shadowBuilder, null, 0);

        return true;
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        method.onActivityResult(HomeActivity.this,requestCode,resultCode, data);
    }*/



}
