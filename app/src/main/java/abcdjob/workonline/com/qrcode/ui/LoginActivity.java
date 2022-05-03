package abcdjob.workonline.com.qrcode.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.razorpay.Checkout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.Models.codeGen;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.databinding.ActivitySettingsBinding;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.Util.Constant;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;

import static abcdjob.workonline.com.qrcode.Data_Device.getContext;


public class LoginActivity extends AppCompatActivity {

    Button callSignUp, loginBtn, forgotbtn;
    ImageView image;
    TextView logoText, sloganText, contactus;
    TextInputLayout Phone, Password;
    String phone, password;
    private Dialog loadingDialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String deviceid;
    private Method method;
    public UserDTO userDTO;
    private SharedPrefrence mprefrences;

    private Constant constant;

    @SuppressLint("HardwareIds")

    private String GetDeviceID() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = null;
        int readIMEI = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (deviceID == null) {
            if (readIMEI == PackageManager.PERMISSION_GRANTED) {
                deviceID = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            }
        }
        return deviceID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//

      /*  getWindow().setBackgroundDrawable(null);*/

        constant = new Constant(LoginActivity.this);
        method=new Method(LoginActivity.this);
        mprefrences = SharedPrefrence.getInstance(LoginActivity.this);
        //Hooks
        callSignUp = findViewById(R.id.signUp_screen);
        loginBtn = findViewById(R.id.login_btn);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        contactus = findViewById(R.id.contactUs);
        sloganText = findViewById(R.id.slogan_name);
        Phone = findViewById(R.id.phoneNo);
        Password = findViewById(R.id.password);
        forgotbtn = (Button) findViewById(R.id.forgotbtn);
       // deviceid = GetDeviceID();
        Checkout.preload(getApplicationContext());
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ContactActivity.class);
                intent.putExtra("callingActivity","LoginActivity");  // pass your values and retrieve them in the other Activity using keyName
                startActivity(intent);

               /* intent.putExtra("keyName", value);
                startActivity(new Intent(LoginActivity.this, ContactActivity.class));*/
                finish();
            }
        });

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Constant.DeviceID = getDeviceId(LoginActivity.this);
                GetDeviceID();
                // Toast.makeText(RegisterActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
               // Toast.makeText(LoginActivity.this,  Constant.DeviceID, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                finish();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE)
                .check();

        forgotbtn.setOnClickListener(v -> {
            //startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            finish();
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String v2;
                phone = Objects.requireNonNull(Phone.getEditText()).getText().toString().trim();
                password = Objects.requireNonNull(Password.getEditText()).getText().toString().trim();
                String deviceid = GetDeviceID();

                if (phone.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Your Phone or Password is Worng", Toast.LENGTH_SHORT).show();
                } else {

                    login(LoginActivity.this);

                }

            }
        });


        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void OpenSignUp(View view) {
        Intent loginIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public void OpenContactus(View view) {
        Intent loginIntent = new Intent(LoginActivity.this, ContactActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //initOrderId();
      //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
       // getWindow().setBackgroundDrawable(null);

    }
    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
    }

    public void onLoginClick(View View) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

    }





    public void login(Activity activity) {
        Log.d(GlobalVariables.TAG, "getHomeData: "+method.userDTO.getTotalAllQrGeneration());
        //    method.loadingDialogg(activity);
        method.loadingDialogg(LoginActivity.this);
        method.params.clear();
        method.params.put("users_login", "KINGSN");
        method.params.put("phone", phone);
        method.params.put("password", password);
        method.params.put("device_id", getDeviceId(getContext()));
        //        // method.showToasty(activity,"1",""+GlobalVariables.adminUserID);
        Log.d(GlobalVariables.TAG, "getHomeData2: called"+activity.toString());
        new HttpsRequest(RestAPI.API_Login, method.params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String message, JSONObject response) {
                Log.d(GlobalVariables.TAG, "backResponsef: "+flag);
                if (flag) {
                    // binding.tvNo.setVisibility(View.GONE);
                    try {

                        Log.d(GlobalVariables.TAG, "hKINGSN123:" + response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString());


                        userDTO = new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString(), (Type) UserDTO.class);
                        mprefrences.setParentUser2(userDTO, String.valueOf(userDTO));
                        GlobalVariables.usermDTO=userDTO;
                      //  method.preferencess.setValue(GlobalVariables.userDTO,userDTO);
                      //  Log.d(GlobalVariables.TAG, "dto: "+GlobalVariables.userDTO.getEmail());
                        editor.putString(GlobalVariables.USER_MOBILE,method.userDTO.getMobile());
                        editor.apply();

                        method.preferencess.setValue(GlobalVariables.USER_MOBILE,userDTO.getMobile());
                       method.loadingDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(activity);
                    alertDialogBuilder.setTitle("Login Failed");
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                    alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    method.loadingDialog.dismiss();
                                    activity.finish();
                                    startActivity(new Intent(activity, LoginActivity.class));
                                    //Log.d("Response",msg);
                                    // finishAffinity();

                                }
                            });

                    androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }



    @SuppressLint({"MissingPermission", "HardwareIds"})
    private String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
           // Toast.makeText(LoginActivity.this,  deviceId, Toast.LENGTH_SHORT).show();

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
}



