package abcdjob.workonline.com.qrcode.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.ui.Util.Constant;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;

import static com.google.firebase.auth.PhoneAuthProvider.*;

public class ForgotActivity extends AppCompatActivity {
    String codeBySystem;
    private SharedPreferences.Editor editor;
    String verificationcodebysystem;

    private EditText emailEt, passwordEt, numForgotEt,resetpaswdtxt1, resetpaswdtxt2;
    private TextView resendTv;
    private ImageButton loginBtn, forgotBtn2, registerBtn, otpBtn, resendTv1, frgBack, vrfbackBtn;
    private RelativeLayout loginLayout, forgotLayout, registerLayout, otpLayout, otpLayout2, resetPaswdlay;
    private Button signnbtn, forgotBtn, verifyotpBtn, resetpswdBtn, resetloginback;
    private com.chaos.view.PinView PinView;
    private LinearLayout cnflay1, cnflay2;
    private ProgressBar progressBar;

    private FirebaseAuth auth, mAuth;
    OnVerificationStateChangedCallbacks callbacks;
    FirebaseUser user;
    private Dialog loadingDialog;
    String password, email, name, phone, Password1, mobileno, refferedby, firebaseOTP,countryCode;
    String verificationID, oneSignalPlayerId, oneSignalPushToken;
    ForceResendingToken resendToken;
    int count = 60, RC_SIGN_IN = 1;
    private SharedPreferences preferences;
    Dialog dialog;
    private Method method;
    public String deviceid;
    com.chaos.view.PinView pinFromUser;
    CountryCodePicker country_code_picker;
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
        auth = FirebaseAuth.getInstance();
        mAuth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        method=new Method(ForgotActivity.this);
        init();
        clickListener();
    }

    @SuppressLint("CutPasteId")
    private void init() {
        country_code_picker=findViewById(R.id.country_code_picker);
        deviceid = GetDeviceID();
        numForgotEt = findViewById(R.id.numForgotET);
        //forgotBtn2 = findViewById(R.id.forgotBtn2);
        forgotBtn = findViewById(R.id.forgotBtn);
        registerBtn = findViewById(R.id.registerBtn);
        forgotLayout = findViewById(R.id.forgotLayout);
        registerLayout = findViewById(R.id.registerLayout);
        otpLayout = findViewById(R.id.otpLayout);
        resetPaswdlay = findViewById(R.id.resetPaswdlay);
        resendTv = findViewById(R.id.resendTv);
        // resendTv1 = findViewById(R.id.resendTv1);
        verifyotpBtn = findViewById(R.id.verifyotpBtn);
        // otpBtn = findViewById(R.id.verifyBtn);
        PinView = findViewById(R.id.pinView);
        vrfbackBtn = findViewById(R.id.vrfbackBtn);
        frgBack = findViewById(R.id.frgBack);
        resetpaswdtxt1 = findViewById(R.id.resetpaswdtxt1);
        resetpaswdtxt2 = findViewById(R.id.resetpaswdtxt2);
        resetpswdBtn = findViewById(R.id.resetpswdBtn);
        resetloginback = findViewById(R.id.resetloginback);
        cnflay1 = findViewById(R.id.cnflay1);
        cnflay2 = findViewById(R.id.cnflay2);
        progressBar = findViewById(R.id.progress_bar);
        pinFromUser = findViewById(R.id.pinView);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                R.color.transparent);


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Constant.DeviceID = getDeviceId(ForgotActivity.this);
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
        auth = FirebaseAuth.getInstance();
        mAuth=FirebaseAuth.getInstance();

        if (getIntent().hasExtra("screentype")) {
            if(Objects.equals(getIntent().getStringExtra("screenType"), "2")){

                forgotLayout.setVisibility(View.GONE);
                otpLayout.setVisibility(View.VISIBLE);
            }

        }

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
    private void clickListener() {
        countryCode="+"+country_code_picker.getSelectedCountryCode();
        country_code_picker.setOnCountryChangeListener(() -> countryCode="+"+country_code_picker.getSelectedCountryCode());
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // loadingDialog.show();
                if (numForgotEt.getText().toString().isEmpty()){
                    numForgotEt.setError("Mobile No. can not be Empty");
                    // Toast.makeText(RegisterActivity.this, "Mobile No. can not be Empty", Toast.LENGTH_SHORT).show();
                    // return;
                }
                else if (numForgotEt.getText().toString().length()!=10)
                {
                    numForgotEt.setError("Insert 10 Digit Phone Number");
                    // Toast.makeText(RegisterActivity.this, "Input valid Mobile No.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String mobileNumber=countryCode+""+numForgotEt.getText().toString();
                    if(GlobalVariables.settings.getDailytaskCoin().equals("0")){
                        loadingDialog.show();
                        sendVerificationCodeToUser(mobileNumber);
                    }else  if(GlobalVariables.settings.getDailytaskCoin().equals("1")){
                        method.params.put("mobile",numForgotEt.getText().toString());
                        method.params.put("type","forgot_pass");
                        method.params.put("device_id",method.getDeviceId(ForgotActivity.this));
                        method.preferencess.setValue("otp",method.Otp());
                        method.params.put("otp",method.preferencess.getValue("otp"));
                        method.checkUser(ForgotActivity.this);
                    }else  if(GlobalVariables.settings.getDailytaskCoin().equals("3")){
                        loadingDialog.show();
                        sendVerificationCodeToUser(mobileNumber);
                    }



                }



            }
        });

        verifyotpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userOtp= Objects.requireNonNull(PinView.getText()).toString();


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
                    resetPaswdlay.setVisibility(View.VISIBLE);
                }

                //signInWithPhoneAuthCredential(credential);

            }
        });

        resetpswdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (resetpaswdtxt1.getText().toString().equals("") || resetpaswdtxt2.getText().toString().equals(""))
                {
                    if(resetpaswdtxt1.getText().toString().equals(""))
                    {
                        resetpaswdtxt1.setError("Insert A Password");
                    }  else if (resetpaswdtxt2.getText().toString().equals(""))
                    {
                        resetpaswdtxt2.setError("Insert The Same Password As Above");
                        //Toast.makeText(RegisterActivity.this,"Please Provide 6 Character Password",Toast.LENGTH_SHORT).show();
                    }

                    // Toast.makeText(RegisterActivity.this,"Fields can not be Empty",Toast.LENGTH_SHORT).show();
                }
                else if (resetpaswdtxt1.getText().toString().length()<6)
                {
                    resetpaswdtxt1.setError("New Pasword Lenght Should be 6 Digit");
                }

                else if (resetpaswdtxt1.getText().toString().equals(resetpaswdtxt2.getText().toString()))
                {
                    loadingDialog.show();
                    updatePassword();

                }
                else
                {
                    resetpaswdtxt2.setError("Password Didn't Matched");
                    //Toast.makeText(RegisterActivity.this,"Pass does not match",Toast.LENGTH_SHORT).show();
                }

                ////////////////////////////////////////////////////////////////////


            }
        });


        resendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpLayout.setVisibility(View.GONE);
                forgotLayout.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.GONE);
                resetPaswdlay.setVisibility(View.GONE);

            }
        });

        frgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotBtn.setClickable(true);
                forgotLayout.setVisibility(View.VISIBLE);
                otpLayout.setVisibility(View.GONE);
                resetPaswdlay.setVisibility(View.GONE);
                Intent intent = new Intent(ForgotActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        resetloginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
                otpLayout.setVisibility(View.GONE);
                resetPaswdlay.setVisibility(View.GONE);
                cnflay1.setVisibility(View.VISIBLE);
                cnflay2.setVisibility(View.GONE);

            }
        });

    }

    private void sendVerificationCodeToUser(String mobileNumber) {
        //loadingDialog.dismiss();

      /*  PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber( mobileNumber)       // Phone number to verify
                        .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);*/


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber,        // Phone number to verify
                20,                 // Timeout duration
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
                    forgotLayout.setVisibility(View.GONE);
                    otpLayout.setVisibility(View.VISIBLE);
                    forgotBtn.setClickable(false);



                    Toast.makeText(ForgotActivity.this, "Password reset Code sent, please check", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }

                @Override
                public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                    super.onCodeAutoRetrievalTimeOut(s);
                    loadingDialog.dismiss();
                    onBackPressed();
                    Toast.makeText(ForgotActivity.this, "Timeout", Toast.LENGTH_SHORT).show();
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
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    loadingDialog.dismiss();
                    Toast.makeText(ForgotActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(ForgotActivity.this,"verification failed", Toast.LENGTH_SHORT).show();
                    Log.w("tagd", "onVerificationFailed", e);

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                        Log.e("Error", "" +"Invalid request");
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded
                        Log.e("Error", "" +"The SMS quota for the project has been exceeded");
                    }

                    // Show a message and update the UI
                    Toast.makeText(ForgotActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            };

    /*private void verifyCode(String code) {
        PhoneAuthCredential credential = getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);

        loadingDialog.dismiss();


    }*/

    private void verifyCode(String code) {
        PhoneAuthCredential credential = getCredential(codeBySystem, code);
        switch (GlobalVariables.settings.getDailytaskCoin()) {
            case "0":

                signInWithPhoneAuthCredential(credential);
                break;
            case "1":
                if (code.equals(method.preferencess.getValue("otp"))) {
                    forgotLayout.setVisibility(View.GONE);
                    otpLayout.setVisibility(View.GONE);
                    resetPaswdlay.setVisibility(View.VISIBLE);
                    loadingDialog.dismiss();

                }
                break;
            case "3":
                signInWithPhoneAuthCredential(credential);
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

                            forgotLayout.setVisibility(View.GONE);
                            otpLayout.setVisibility(View.GONE);
                            resetPaswdlay.setVisibility(View.VISIBLE);
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

    private void updatePassword() {

        final String mobile = numForgotEt.getText().toString();
        final String password = resetpaswdtxt2.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_password_update,
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
                                    Toast.makeText(ForgotActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                    // finish();
                                    // startActivity(getIntent());
                                    loadingDialog.dismiss();
                                    // loadingDialog.dismiss();
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ForgotActivity.this);
                                    alertDialogBuilder.setTitle(object.getString("title"));
                                    alertDialogBuilder.setMessage(object.getString("msg"));
                                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                    alertDialogBuilder.setPositiveButton(ForgotActivity.this.getResources().getString(R.string.ok_message),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                   // finish();
                                                    Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    loadingDialog.dismiss();
                                                    //finishAffinity();
                                                }
                                            });

                                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    Toast.makeText(ForgotActivity.this, object.getString("msg"), Toast.LENGTH_LONG).show();


                                } else {
                                    loadingDialog.dismiss();
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ForgotActivity.this);
                                    alertDialogBuilder.setTitle(object.getString("title"));
                                    alertDialogBuilder.setMessage(object.getString("msg"));
                                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                    alertDialogBuilder.setPositiveButton(ForgotActivity.this.getResources().getString(R.string.ok_message),
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
                                    Toast.makeText(ForgotActivity.this, object.getString("msg"), Toast.LENGTH_LONG).show();
                                }

                            }

                            // progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ForgotActivity.this, "Error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(ForgotActivity.this, "RESPONSE: " + error, Toast.LENGTH_SHORT).show();
                Log.e("Error", "" + error.getMessage());
              //  Toast.makeText(ForgotActivity.this, "ErrorV: " + error.getMessage(),
                       // Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ForgotActivity.this);
                alertDialogBuilder.setTitle("Something Went Wrong");
                alertDialogBuilder.setMessage("Please Try With Active Internet ");
                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                alertDialogBuilder.setPositiveButton(ForgotActivity.this.getResources().getString(R.string.ok_message),
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
                //params.put("uid", user.getUid());
                params.put("mobile", mobile);

                params.put("password",password);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ForgotActivity.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ForgotActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
    }



}