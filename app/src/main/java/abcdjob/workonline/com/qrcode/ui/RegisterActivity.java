package abcdjob.workonline.com.qrcode.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hbb20.CountryCodePicker;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.ui.Util.Constant;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;


import static com.google.firebase.auth.PhoneAuthProvider.*;

public class RegisterActivity extends AppCompatActivity{

    Animation topAnim, bottomAnim,slide_in_left,slide_in_right;
    ImageView logo_image;
    TextView verifyTitle,verifySubTitle;

    TextInputEditText verifyNumber, emailEt, passwordEt, emailForgotEt, nameRegisterEt, phoneRegisterEt,
            emailRegisterEt, passwordRegisterEt, referralRegisterEt, otpEt,resetpaswdtxt1,resetpaswdtxt2;
    Button verifyotpBtn,verifyButton ;
    ImageButton registerBtn ;
    private LinearLayout verifyMobile;
    public RelativeLayout otpLayout;
    public RelativeLayout registerLayout;
    private com.chaos.view.PinView PinView;
   public String verificationID,oneSignalPlayerId,oneSignalPushToken,referedby,deviceid,name,mobileno,password, email,  phone,Password1,refferedby,firebaseOTP,countryCode;
    String codeBySystem;
    private FirebaseAuth auth,mAuth;
    OnVerificationStateChangedCallbacks callbacks;
    FirebaseUser user;
    private Dialog loadingDialog;
    String verificationcodebysystem;
    com.chaos.view.PinView pinFromUser;
    CountryCodePicker country_code_picker;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ImageButton BackBtn ;
   private Method method;
    private String GetDeviceID(){
        TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = null;
        int readIMEI= ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (readIMEI == PackageManager.PERMISSION_GRANTED) {
            deviceID = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        }
        return deviceID;
    }






    @SuppressLint("CommitPrefEdits")
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        mAuth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_register);
        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();

        method=new Method(RegisterActivity.this);

       // addSubscriptionObserver(this);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        pinFromUser = findViewById(R.id.pinView);

        oneSignalPushToken=  OneSignal.getDeviceState().getUserId();
        oneSignalPlayerId=   OneSignal.getDeviceState().getPushToken();
        if (oneSignalPlayerId ==""){
            //oneSignalPlayerId=" ";
            editor.putString(oneSignalPlayerId," ");
            //editor.putString(GlobalVariables.USER_ID,id);
        }
        if (oneSignalPushToken==""){
            editor.putString(oneSignalPushToken," ");
        }
        // Toast.makeText(this,oneSignalPlayerId+"\n"+oneSignalPushToken,Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        }
        //String userOtp=PinView.getText().toString();

        pinFromUser = findViewById(R.id.pinView);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent);

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Constant.DeviceID = getDeviceId(RegisterActivity.this);
                GetDeviceID();
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
                .setPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE)
                .check();

        //Firebase init

        mAuth=FirebaseAuth.getInstance();

        init();
        clickListener();
        countryCode="+"+country_code_picker.getSelectedCountryCode();

        ////////////////

    }

    @Override
    protected void onStart() {
        super.onStart();
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setBackgroundDrawable(null);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void init(){
        country_code_picker=findViewById(R.id.country_code_picker);
        deviceid = GetDeviceID();
        emailEt = findViewById(R.id.emailRegisterET);
        passwordEt = findViewById(R.id.passwordRegisterET);
        nameRegisterEt = findViewById(R.id.nameRegisterET);
        phoneRegisterEt = findViewById(R.id.phoneRegisterET);
        emailRegisterEt = findViewById(R.id.emailRegisterET);
        passwordRegisterEt = findViewById(R.id.passwordRegisterET);
        referralRegisterEt = findViewById(R.id.referralRegisterET);
        logo_image = findViewById(R.id.logo_image);
        verifyTitle = findViewById(R.id.verifyTitle);
        verifySubTitle = findViewById(R.id.verifySubTitle);
        verifyotpBtn = findViewById(R.id.verifyotpBtn);
        pinFromUser = findViewById(R.id.pinView);
        deviceid = GetDeviceID();
        verifyNumber= findViewById(R.id.verifyNumber);
        verifyButton = findViewById(R.id.verifyBtn);
        registerBtn=findViewById(R.id.registerBtn);
        PinView=findViewById(R.id.pinView);
        verifyMobile=(LinearLayout)findViewById(R.id.verifyMobile);
        otpLayout=(RelativeLayout)findViewById(R.id.otpLayout);
        registerLayout=(RelativeLayout)findViewById(R.id.registerLayout);
        BackBtn=(ImageButton) findViewById(R.id.BackBtn);

        ///////////////////// Animation///////////////////////////
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        slide_in_left = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slide_in_right = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);



        logo_image.setAnimation(topAnim);
        verifyTitle.setAnimation(slide_in_left);
        verifySubTitle.setAnimation(slide_in_left);
////////////////////Animation ////////////////////////

        if (getIntent().hasExtra("screentype")) {
            if(Objects.equals(getIntent().getStringExtra("screenType"), "1")){

                otpLayout.setVisibility(View.VISIBLE);
                registerLayout.setVisibility(View.GONE);
                verifyMobile.setVisibility(View.GONE);
            }

        }

    }

    private void clickListener(){

        country_code_picker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode="+"+country_code_picker.getSelectedCountryCode();

            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(verifyNumber.getText()).toString().isEmpty()){
                    verifyNumber.setError("Mobile No. can not be Empty");
                    // Toast.makeText(RegisterActivity.this, "Mobile No. can not be Empty", Toast.LENGTH_SHORT).show();
                    // return;
                }
                else if (verifyNumber.getText().toString().length()!=10)
                {
                    verifyNumber.setError("Insert 10 Digit Phone Number");
                    // Toast.makeText(RegisterActivity.this, "Input valid Mobile No.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String mobileNumber=countryCode+""+verifyNumber.getText().toString();
                    if(GlobalVariables.settings.getDailytaskCoin().equals("0")){
                        sendVerificationCodeToUser(mobileNumber);
                    }else  if(GlobalVariables.settings.getDailytaskCoin().equals("1")){
                        method.params.put("mobile",verifyNumber.getText().toString());
                        method.params.put("type","register");
                        method.params.put("device_id",method.getDeviceId(RegisterActivity.this));
                        method.preferencess.setValue("otp",method.Otp());
                        method.params.put("otp",method.preferencess.getValue("otp"));
                        method.checkUser(RegisterActivity.this);
                    }else  if(GlobalVariables.settings.getDailytaskCoin().equals("3")){
                        phoneRegisterEt.setText(verifyNumber.getText());
                        verifyMobile.setVisibility(View.GONE);
                        otpLayout.setVisibility(View.GONE);
                        registerLayout.setVisibility(View.VISIBLE);
                    }


                   // Toast.makeText(RegisterActivity.this, ""+mobileNumber, Toast.LENGTH_SHORT).show();
                   // loadingDialog.show();
                   //


                }




            }
        });

        verifyotpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userOtp=PinView.getText().toString();


                if (userOtp.isEmpty() || userOtp.length() < 6) {
                    PinView.setError("Wrong OTP...");
                    PinView.requestFocus();
                    return;
                }else {
                    verifyCode(userOtp);
                    loadingDialog.show();
                }


                // Toast.makeText(RegisterActivity.this, userOtp+"success"+codeBySystem, Toast.LENGTH_SHORT).show();
                if (getCredential(codeBySystem,codeBySystem).equals(userOtp)){

                    otpLayout.setVisibility(View.GONE);
                    registerLayout.setVisibility(View.VISIBLE);
                }

                //signInWithPhoneAuthCredential(credential);

            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Objects.requireNonNull(nameRegisterEt.getText()).toString();
                String email = Objects.requireNonNull(emailRegisterEt.getText()).toString();
                 phone = Objects.requireNonNull(phoneRegisterEt.getText()).toString();
                String password = Objects.requireNonNull(passwordRegisterEt.getText()).toString();
                String refferedby = Objects.requireNonNull(referralRegisterEt.getText()).toString();
                String deviceid = GetDeviceID();

                if (nameRegisterEt.getText().toString().isEmpty()){
                    nameRegisterEt.setError("Fields Cant be Empty");
                    //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phoneRegisterEt.getText().toString().isEmpty()|| phone.length() < 10){
                    phoneRegisterEt.setError("Insert A valid 10 Digit Phone Number");
                    // Toast.makeText(RegisterActivity.this, "Input valid name", Toast.LENGTH_SHORT).show();
                    return;
                }  if (emailRegisterEt.getText().toString().isEmpty()){
                    emailRegisterEt.setError("Insert A valid Email");
                    //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
                    return;

                } if (TextUtils.isEmpty(password) || password.length() < 6) {
                    passwordRegisterEt.setError("Insert A Valid 6 digit password");
                    //Toast.makeText(RegisterActivity.this, "Input 6 digit valid password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    loadingDialog.show();

                     //register(user);
                    method.params.clear();
                    method.params.put("user_register", "");
                    method.params.put("user_id",phone);
                    method.params.put("name",name);
                    method.params.put("email",email);
                    method.params.put("device_id",deviceid);
                    method.params.put("password",password);
                    method.params.put("reffered_by",refferedby);
                    method.params.put("joining_bonus",preferences.getString(GlobalVariables.JOINING_BONUS,""));
                    register2(RegisterActivity.this);


                }
            }

        });


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    private void sendVerificationCodeToUser(String mobileNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,// Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private OnVerificationStateChangedCallbacks mCallbacks =
            new OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;

                    verificationcodebysystem= s;
                    loadingDialog.show();
                    verifyMobile.setVisibility(View.GONE);
                    otpLayout.setVisibility(View.VISIBLE);




                    Toast.makeText(RegisterActivity.this, "Mobile Verification Code sent, please check", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }



                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    loadingDialog.show();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                        loadingDialog.dismiss();



                    }
                }

                @Override
                public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                    super.onCodeAutoRetrievalTimeOut(s);
                   // Toast.makeText(RegisterActivity.this, "codetimeout "+s, Toast.LENGTH_SHORT).show();

                    loadingDialog.dismiss();
                    Intent intent = new Intent(RegisterActivity.this,RegisterActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    loadingDialog.dismiss();
                   // Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();

                }
            };



    private void verifyCode(String code) {
        switch (GlobalVariables.settings.getDailytaskCoin()) {
            case "0":
                PhoneAuthCredential credential = getCredential(codeBySystem, code);
                signInWithPhoneAuthCredential(credential);
                break;
            case "1":
                if (code.equals(method.preferencess.getValue("otp"))) {
                    otpLayout.setVisibility(View.GONE);
                    registerLayout.setVisibility(View.VISIBLE);
                    verifyMobile.setVisibility(View.GONE);
                    otpLayout.setVisibility(View.GONE);
                    phoneRegisterEt.setText(verifyNumber.getText());
                    phoneRegisterEt.setFocusable(false);
                    registerLayout.setVisibility(View.VISIBLE);

                    loadingDialog.dismiss();
                }
                break;
            case "3":
                otpLayout.setVisibility(View.GONE);
                registerLayout.setVisibility(View.VISIBLE);
                verifyMobile.setVisibility(View.GONE);
                otpLayout.setVisibility(View.GONE);
                phoneRegisterEt.setText(verifyNumber.getText());
                phoneRegisterEt.setFocusable(false);
                registerLayout.setVisibility(View.VISIBLE);

                loadingDialog.dismiss();
                break;
        }


        loadingDialog.dismiss();


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Verification completed successfully here Either
                            // store the data or do whatever desire
                            /*editor.putString(mobileno, String.valueOf(verifyNumber.getText()));*/
                            verifyMobile.setVisibility(View.GONE);
                            otpLayout.setVisibility(View.GONE);
                            phoneRegisterEt.setText(verifyNumber.getText());
                            phoneRegisterEt.setFocusable(false);
                            registerLayout.setVisibility(View.VISIBLE);

                            loadingDialog.dismiss();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                loadingDialog.dismiss();
                                // Toast.makeText(RegisterActivity.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    @SuppressLint({"MissingPermission", "HardwareIds"})
    private String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);


        }else {

            final TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);



            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            }
        }

        return deviceId;

    }


    private void register2(Activity activity) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_Registation,
                response -> {
                    // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                    try {
                        loadingDialog.dismiss();
                        System.out.println(response);
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray(GlobalVariables.AppSid);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String msg = object.getString("msg");
                            String success = object.getString("success");




                            if (success.equals("1")) {
                                method.preferencess.setValue(GlobalVariables.USER_MOBILE,phone);

                               /* Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finishAffinity();*/
                                Toast.makeText(RegisterActivity.this, "Registred Successfully", Toast.LENGTH_LONG).show();
                               // progressDialog.dismiss();
                                loadingDialog.dismiss();
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                                alertDialogBuilder.setTitle(R.string.Congratulation);
                                alertDialogBuilder.setMessage(msg);
                                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);

                                alertDialogBuilder.setPositiveButton(RegisterActivity.this.getResources().getString(R.string.ok_message),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                method.preferencess.setValue(GlobalVariables.USER_MOBILE,phone);
                                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                                //Log.d("Response",msg);
                                                //finishAffinity();
                                            }
                                        });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                                // Toast.makeText(Register.this, "Congratulation! Registration Successfully", Toast.LENGTH_LONG).show();

                            } else {

                              /*  Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finishAffinity();
*/

                                //progressDialog.dismiss();
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                                alertDialogBuilder.setTitle(R.string.app_name);
                                alertDialogBuilder.setMessage(msg);
                                alertDialogBuilder.setPositiveButton(RegisterActivity.this.getResources().getString(R.string.ok_message),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                                                //Log.d("Response",msg);
                                                finishAffinity();
                                            }
                                        });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                //  Toast.makeText(Register.this, msg, Toast.LENGTH_LONG).show();

                            }
                        }
                    } catch (JSONException e) {
                       // progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(RegisterActivity.this, "RESPONSE: " + error, Toast.LENGTH_SHORT).show();
                Log.e("Error", "" + error.getMessage());
              //  Toast.makeText(RegisterActivity.this, "ErrorV: " + error.getMessage(),
                        //Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(RegisterActivity.this);
                alertDialogBuilder.setTitle("Something Went Wrong");
                alertDialogBuilder.setMessage("Please Try With Active Internet ");
                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                alertDialogBuilder.setPositiveButton(RegisterActivity.this.getResources().getString(R.string.ok_message),
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
           return method.params;
        }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);
        Log.d(GlobalVariables.TAG, "register2: "+stringRequest +method.params);



    }


}
