package abcdjob.workonline.com.qrcode.ui.about_us;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.databinding.ActivityAboutUsBinding;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

public class AboutUsActivity extends AppCompatActivity {


    private SharedPreferences preferences;
    private Dialog dialog;
    private Dialog loadingDialog;
    private Method method;

    com.google.android.material.textview.MaterialTextView aboutusAppname,aboutusVersion,aboutCompany,aboutEmail,app_website,appContactus,aboutContent;

    ActivityAboutUsBinding mActivityAboutUsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAboutUsBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_us);
        initializeToolbar();
        method=new Method(this);

        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        //setContentView(R.layout.activity_about_us);
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

        Method ads=new Method(AboutUsActivity.this);

        loadingDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        }, 2000);

        if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))
        {
            ads.callindustrial( AboutUsActivity.this);
            // ads.callindustrial(HomeActivity.this);
        }
        aboutusAppname= findViewById(R.id.aboutusAppname);
        aboutusVersion= findViewById(R.id.aboutusVersion);
        aboutCompany= findViewById(R.id.aboutCompany);
        aboutEmail= findViewById(R.id.aboutEmail);
        app_website= findViewById(R.id.app_website);
        appContactus= findViewById(R.id.appContactus);
        aboutContent= findViewById(R.id.aboutContent);
        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);

        aboutusAppname.setText(GlobalVariables.settings.getAppName());
        aboutusVersion.setText(GlobalVariables.settings.getAppVersion());
        aboutCompany.setText(GlobalVariables.settings.getAppDevelopedBy());
        aboutEmail.setText(GlobalVariables.settings.getAppEmail());
        app_website.setText(GlobalVariables.settings.getAppWebsite());
        appContactus.setText(GlobalVariables.settings.getAppEmail());
        aboutContent.setText(GlobalVariables.settings.getAppDescription());



    }

    private void initializeToolbar() {
        setSupportActionBar(mActivityAboutUsBinding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
