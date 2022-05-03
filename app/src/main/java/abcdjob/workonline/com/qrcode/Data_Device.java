package abcdjob.workonline.com.qrcode;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import abcdjob.workonline.com.qrcode.Models.HomeDataDTO;
import abcdjob.workonline.com.qrcode.helpers.util.SharedPrefUtil;
import abcdjob.workonline.com.qrcode.helpers.util.database.DatabaseUtil;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;

public class Data_Device extends MultiDexApplication {

    private static Data_Device sInstance;
    HomeDataDTO homeData;
    SharedPrefrence sharedPrefrence;

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        sharedPrefrence = SharedPrefrence.getInstance(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        SharedPrefUtil.init(getApplicationContext());
        DatabaseUtil.init(getApplicationContext());
        //MobileAds.initialize(this, "");


    }
    public static synchronized Data_Device getInstance() {
        return sInstance;
    }
    public HomeDataDTO getHomeData() {
        return homeData;
    }

    public void setHomeData(HomeDataDTO homeData) {
        this.homeData = homeData;
    }
}
