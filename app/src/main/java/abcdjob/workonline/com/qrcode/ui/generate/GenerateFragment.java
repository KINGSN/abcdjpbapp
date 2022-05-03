package abcdjob.workonline.com.qrcode.ui.generate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.common.api.GoogleApi;
import com.google.gson.Gson;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.razorpay.Checkout;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import abcdjob.workonline.com.qrcode.Data_Device;
import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.HomeDataDTO;
import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.Models.codeGen;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.databinding.FragmentGenerateBinding;
import abcdjob.workonline.com.qrcode.helpers.constant.IntentKey;
import abcdjob.workonline.com.qrcode.helpers.model.Code;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.ContactActivity;
import abcdjob.workonline.com.qrcode.ui.LoginActivity;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;
import abcdjob.workonline.com.qrcode.ui.adapter.Student_Data;
import abcdjob.workonline.com.qrcode.ui.generatedcode.GeneratedCodeActivity;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;
import abcdjob.workonline.com.qrcode.ui.splash.SplashActivity;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import es.dmoral.toasty.Toasty;

import static abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables.settings;
import static android.app.Activity.RESULT_OK;
import static android.view.View.NO_ID;


public class GenerateFragment extends Fragment {
    private static final String TAG = "KINGSN";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private FragmentGenerateBinding mBinding;
    private Context mContext;
    private InterstitialAd mInterstitialAd;
    private List<Student_Data> student_data;
    public TextView std,totalcodes,correctcodes,wrongcodes, generate,studentinfo,nametxt,idtxt,citytxt,pincodetxt,mTextViewCountDown;
    private EditText inputtext,nametxtEt,pincodetxtEt,citytxtEt;
    public PinView idtxtEt;
    private Button nextbtn;
    private Spinner spinner;
    private String test;
    private String qrtext;
    private int D1;
    private int D2;
    private int D3;
    private int qrtime=0;
    private int qrtime1=0;
    private String D4;
    private String content2;
    private String D5;
    private String servertxt;
    private int count,item;
    PieChart pieChart;
    TextView tvR, tvPython, tvCPP, tvJava;
    LinearLayout ml2;
    ScrollView mainlin;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private static final long START_TIME_IN_MILLIS = 600000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    boolean timerStarted = false;
    public static String toGenText="";
    public   Method method;
    HomeDataDTO homeDataDTO;
    UserDTO userDTO;
    Data_Device globalState;
    private codeGen codeGen;
    private SharedPrefrence mprefrences;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public  String orderId="";
    final int UPI_PAYMENT = 0;

    public static final String WEBSITE = "DEFAULT";
    public static final String CHANNEL_ID = "WAP";
    public static final String INDUSTRY_TYPE_ID = "Retail";
    public static final String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
    public static String checksum;
    public String intentd="";
    private  int codeGenDelay = 1500;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            test=preferences.getString(GlobalVariables.USER_MOBILE,"");
        }
        globalState = Data_Device.getInstance();

        Bundle extras = requireActivity().getIntent().getExtras();
        if(extras == null) {
            intentd="";
        } else {
            intentd=extras.getString("activity");;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final  View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_generate, container, false);
        //nextbtn = (Button) view.findViewById(R.id.nextbtn);
        //std=(TextView) view.findViewById(R.id.gencoder);
        method=new Method(requireActivity());
        totalcodes=(TextView) view.findViewById(R.id.totalcodes);
        correctcodes=(TextView) view.findViewById(R.id.correctcodes);
        wrongcodes=(TextView) view.findViewById(R.id.wrongcodes);
        nametxt=(TextView) view.findViewById(R.id.nametxt);
        idtxt=(TextView) view.findViewById(R.id.idtxt);
        citytxt=(TextView) view.findViewById(R.id.citytxt);
        pincodetxt=(TextView) view.findViewById(R.id.pincodetxt);
         generate=(TextView) view.findViewById(R.id.text_view_generate);
       // studentinfo=(TextView) view.findViewById(R.id.studentinfo);
        inputtext =(EditText) view.findViewById(R.id.edit_text_content);
        nametxtEt=(EditText) view.findViewById(R.id.nametxtEt);
        idtxtEt=(PinView) view.findViewById(R.id.idtxtEt);
        citytxtEt=(EditText) view.findViewById(R.id.citytxtEt);
        pincodetxtEt=(EditText) view.findViewById(R.id.pintxtEt);
        spinner=(Spinner) view.findViewById(R.id.spinner_types2);

        pieChart = (PieChart) view.findViewById(R.id.piechart);
        mainlin=(ScrollView)view.findViewById(R.id.mainlin) ;
        ml2=(LinearLayout)view.findViewById(R.id.ml2) ;
        mTextViewCountDown =view. findViewById(R.id.text_view_countdown);

       // mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_generate, container, false);
        preferences = this.requireActivity().getSharedPreferences(GlobalVariables.ADMIN_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        generate.setVisibility(View.VISIBLE);
        test=("1");
        editor = preferences.edit();
       // item= Integer.parseInt("1");
        item= preferences.getInt(GlobalVariables.QRCOUNT,0)+1;
       // Toast.makeText(getActivity(), "" + item, Toast.LENGTH_SHORT).show();



        mprefrences = SharedPrefrence.getInstance(getActivity());

        method.loadingDialogg(requireActivity());
        getHomeData(requireActivity());
        spinner.setSelection(1);
        spinner.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);



       // spinner.setSelection(1);
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("","");
        clipboard.setPrimaryClip(clip);

        timer = new Timer();


        nametxtEt.setLongClickable(false);
        idtxtEt.setLongClickable(false);
        citytxtEt.setLongClickable(false);
        pincodetxtEt.setLongClickable(false);
        nametxt. setTextIsSelectable(false);
        idtxt. setTextIsSelectable(false);
        citytxt. setTextIsSelectable(false);
        pincodetxt. setTextIsSelectable(false);


        nametxt.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        nametxtEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("","");
                clipboard.setPrimaryClip(clip);
                startTapped();

            }

        });

        nametxtEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("","");
                clipboard.setPrimaryClip(clip);
                Log.e("ontouch", "onTouch:started " );

                //startStopTapped();
                int inType = nametxtEt.getInputType(); // backup the input type
               // nametxtEt.setInputType(InputType.TYPE_NULL); // disable soft input
                nametxtEt.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                nametxtEt.onTouchEvent(event); // call native handler
                nametxtEt.setInputType(inType); // restore input type
                startTapped();
                generate.isClickable();{
                    generate.setClickable(true);
                }
                return true; // consume touch even

            }
        });


        pincodetxtEt.setCustomInsertionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        if(nametxtEt.hasFocus()){
           // startTimer2();
            Log.e("nametext", "onCreateView:has focus");
        }

        nametxtEt.setCustomInsertionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
               // startTimer();
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });





  generate.setOnClickListener(new View.OnClickListener() {
   // String qrtext = inputtext.getText().toString();

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: generated clicked");
        editor.apply();
        //Toast.makeText(getActivity(),preferences.getString(GlobalVariables.D1,""), Toast.LENGTH_SHORT).show();
        // Toast.makeText(getActivity(),""+qrtext, Toast.LENGTH_SHORT).show();
        if(nametxtEt.getText().toString().isEmpty()) {

            nametxtEt.setError("Fields Cant be Empty");
            //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());
            }
            generate.setClickable(true);
            return;
        }if (!nametxtEt.getText().toString().equals(GlobalVariables.codeGen.getStudentName())){
            nametxtEt.setError("Input Text Doesn't matched");
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());
            }
            generate.setClickable(true);
            return;
        }else if(nametxtEt.getText().toString().equals(GlobalVariables.codeGen.getStudentName())){
            idtxtEt.callOnClick();
            idtxtEt.requestFocus();

        }
        if(idtxtEt.getText().toString().isEmpty()){
            idtxtEt.setError("Fields Cant be Empty");
            idtxtEt.animate();
            idtxtEt.setLineColor(
                    ResourcesCompat.getColor(requireActivity().getResources(), R.color.colorPrimary, getActivity().getTheme()));
            //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());}
            generate.setClickable(true);
            return;
        } if (!idtxtEt.getText().toString().equals(GlobalVariables.codeGen.getIdNumber())){
           if(idtxtEt.getText().toString().isEmpty()){
            idtxtEt.setError("Fields Cant be Empty");
            //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());}
            generate.setClickable(true);
            return;
        }   idtxtEt.setError("Input Text Doesn't matched");
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());
            }

            generate.setClickable(true);
            return;
        }
        if(citytxtEt.getText().toString().isEmpty()){
            citytxtEt.setError("Fields Cant be Empty");
            //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());}
            generate.setClickable(true);
            return;
        } if (!citytxtEt.getText().toString().equals(GlobalVariables.codeGen.getEcity())){
            citytxtEt.setError("Input Text Doesn't matched");
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());
            }
            generate.setClickable(true);
            return;
        }if(pincodetxtEt.getText().toString().isEmpty()){
            pincodetxtEt.setError("Fields Cant be Empty");

            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());}
            generate.setClickable(true);

        }if (!pincodetxtEt.getText().toString().equals(GlobalVariables.codeGen.getPinCode())){
            pincodetxtEt.setError("Input Text Doesn't matched");
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());
            }
            generate.setClickable(true);

        }else {


            StopTapped();
             qrtext = nametxtEt.getText() + "\n" + idtxtEt.getText() + "\n" + citytxtEt.getText() + "\n" +
                    pincodetxtEt.getText();
          //  String qrtext = preferences.getString(GlobalVariables.D1,"");

            Log.d(TAG, "onClick: generatedTextCode"+qrtext);
            Log.d(TAG, "onClick: RequiredTextCode"+toGenText);

            if (qrtext.equals(toGenText)) {
                Log.d(TAG, "onClick: equal");
            }else{
                Log.d(TAG, "onClick: Notequal");
            }

            if (method.userDTO.getCodeGenAllow().equals("0")) {


            if (!Objects.equals(qrtext, toGenText)) {
                //Toast.makeText(getActivity(), "NOT EQUAL", Toast.LENGTH_SHORT).show();
                Toasty.error(requireActivity(), "NOT EQUAL \n InCorrect Qr Code Generation", Toast.LENGTH_LONG, true).show();

                editor.putString(GlobalVariables.Txn_amount, String.valueOf(0));
                editor.putString(GlobalVariables.Txn_type, getString(R.string.Qrcodegentxn));
                editor.putString(GlobalVariables.Txn_status, "CREDIT");
                editor.apply();


            } else {
                Toasty.success(requireActivity(), "EQUAL TEXT \n Correct Qr Code Generation", Toast.LENGTH_LONG, true).show();


                Log.d(TAG, "requiredTime :="+ preferences.getInt(GlobalVariables.QR_MIN_TIME, 0)+"\n qrtime "+qrtime+"\n qrtime1 :"+qrtime1);
                if (qrtime1 > (Character.getNumericValue(settings.getQrMinTime().charAt(0)))) {

                    method.preferencess.setValue(GlobalVariables.Txn_amount, settings.getPerQrCoin());
                    method.preferencess.setValue(GlobalVariables.Txn_type, getString(R.string.Qrcodegentxn));
                    method.preferencess.setValue(GlobalVariables.Txn_status, "CREDIT");


                    Log.d(TAG, "Txn_amount: " +preferences.getString(GlobalVariables.Txn_amount, ""));
                    editor.apply();
                    editor.apply();
                } else {

                    method.preferencess.setValue(GlobalVariables.Txn_amount, String.valueOf(0));
                    method.preferencess.setValue(GlobalVariables.Txn_type, getString(R.string.Qrcodegentxn));
                    method.preferencess.setValue(GlobalVariables.Txn_status, "CREDIT");

                    Log.d(TAG, "Txn_amount: " +preferences.getString(GlobalVariables.Txn_amount, ""));
                }
            }

            inputtext.setText(qrtext);
            count = Integer.parseInt(test);
            count++;
            String content = String.valueOf(inputtext.getText());
            test = String.valueOf(count);
            // StopTapped();
           /* GlobalVariables.adModelCount = new AdModelCount("true","true","true","true");
            editor.apply();*/
                generate.isClickable();{
                    generate.setClickable(false);
                }
               // insertwallet();
             generateCode();



        }else{

                Toasty.error(requireActivity(), "You Have Been Restricted For Generating Qr Code. Contact Admin..!", Toast.LENGTH_LONG, true).show();
            }

        }
    }
});


        return view;

    }


    private void setListeners() {
        mBinding.spinnerTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getSelectedView()).setTextColor(ContextCompat.getColor(mContext,
                        position == 0 ? R.color.text_hint : R.color.text_regular));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initializeCodeTypesSpinner() {
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.code_types, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        mBinding.spinnerTypes.setAdapter(arrayAdapter);
    }




    public void generateCode() {
        Intent intent = new Intent(getContext(), GeneratedCodeActivity.class);
        if (inputtext.getText() != null) {
            String content =String.valueOf(inputtext.getText()).trim();
            int type = spinner.getSelectedItemPosition();



            if (!TextUtils.isEmpty(content) && type != 0) {

                boolean isValid = true;

                switch (type) {
                    case Code.BAR_CODE:
                        if (content.length() > 80) {
                            Toast.makeText(getContext(),
                                    getString(R.string.error_barcode_content_limit),
                                    Toast.LENGTH_SHORT).show();
                            isValid = false;
                        }
                        break;

                    case Code.QR_CODE:
                        if (content.length() > 1000) {
                            Toast.makeText(getActivity(),
                                    getString(R.string.error_qrcode_content_limit),
                                    Toast.LENGTH_SHORT).show();
                            isValid = false;
                        }
                        break;

                    default:
                        isValid = false;
                        break;
                }

                if (isValid) {
                    Code code = new Code(content, type);
                    intent.putExtra(IntentKey.MODEL, code);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getActivity(),
                        getString(R.string.error_provide_proper_content_and_type),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void getHomeData(Activity activity) {
        Log.d(TAG, "getHomeData: "+method.userDTO.getTotalAllQrGeneration());

        method.params.clear();
        method.params.put("user_mobile",method.preferencess.getValue(GlobalVariables.USER_MOBILE));
        method.params.put("device_id",method.getDeviceId(activity.getApplicationContext()));
        //        // method.showToasty(activity,"1",""+GlobalVariables.adminUserID);
        Log.d(GlobalVariables.TAG, "getHomeData2: called"+activity.toString());
        new HttpsRequest(RestAPI.API_Settings, method.params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String message, JSONObject response) {

                if (flag) {
                    // binding.tvNo.setVisibility(View.GONE);
                    try {
                        Log.d(GlobalVariables.TAG, "hKINGSN123:" + response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString());


                        userDTO = new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").getJSONObject("profile").toString(), (Type) UserDTO.class);
                        mprefrences.setParentUser2(userDTO, "userDTO");
                        method.userDTO=userDTO;
                      //  preferences.setParentUser3(userDTO, userDTO);

                        GlobalVariables.usermDTO=userDTO;
                        GlobalVariables.settings= new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").getJSONObject("Settings").toString(), (Type) Settings.class);

                        GlobalVariables.codeGen= new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").getJSONObject("codeGen").toString(), (Type) codeGen.class);

                        Log.d(TAG, "backResponse: "+GlobalVariables.settings.getPublisherId());



                        Log.d(GlobalVariables.TAG, "hKINGSN123:" + GlobalVariables.codeGen.getStudentName() );

                         setData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    try {
                        alertDialogBuilder.setTitle(response.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
     private void setData()
   {

       //HomeActivity.alert(requireActivity());
       alert(requireActivity());

       nametxt.setText(GlobalVariables.codeGen.getStudentName());
       idtxt.setText(GlobalVariables.codeGen.getIdNumber());
       pincodetxt.setText(GlobalVariables.codeGen.getPinCode());
       citytxt.setText(GlobalVariables.codeGen.getEcity());

       toGenText= (GlobalVariables.codeGen.getStudentName())+"\n"
               +(GlobalVariables.codeGen.getIdNumber())+"\n"
               +(GlobalVariables.codeGen.getEcity())+"\n"
               +(GlobalVariables.codeGen.getPinCode());

       mprefrences.setValue("toGenText", toGenText);
      // editor.apply();

       Log.d(TAG, "setData: "+toGenText);

        // Set the percentage of language used
       totalcodes.setText("Total Codes : "+ userDTO.getTotalAllQrGeneration());
       correctcodes.setText("Today's Codes : "+userDTO.getTodaysCodes());
       wrongcodes.setText("History Day's : " + ((Integer.parseInt(userDTO.getTotalQrGeneration()))));

       String innerVal=totalcodes.getText().toString()+"\n" +correctcodes.getText().toString();
       int wrongcodes=((Integer.parseInt(userDTO.getTotalAllQrGeneration())) - ((Integer.parseInt(userDTO.getCorrectQrGeneration()))));

       // Set the percentage of language used

        int a=Integer.parseInt(userDTO.getCorrectQrGeneration());
        int b=Integer.parseInt(userDTO.getTotalAllQrGeneration());
       D1 = (int) (((double) a / (double) b) * 100);

       D2 = (int) (((double) wrongcodes / (double) b) * 100);

   //    Toast.makeText(getContext(),String.valueOf(x), Toast.LENGTH_SHORT).show();
       Log.d(TAG, "setData: "+(Integer.parseInt(userDTO.getCorrectQrGeneration())+Integer.parseInt(userDTO.getTotalAllQrGeneration())));
     //  D2=(((wrongcodes)/Integer.parseInt(userDTO.getTotalAllQrGeneration()))*100);
     //  Toast.makeText(getContext(),String.valueOf(D1)+""+D2, Toast.LENGTH_SHORT).show();

       pieChart.clearChart();

       pieChart.setUseInnerValue(true);
       // pieChart.getInnerPaddingColor();
       pieChart.setHighlightStrength(D2);
       pieChart.setInnerValueSize(20);
       pieChart.setInnerValueString(innerVal);
       pieChart.setInnerPaddingColor(Color.parseColor("#E0E0E0"));
       pieChart.setInnerPaddingColor(2);
       pieChart.setInnerValueColor(Color.parseColor("#008000"));
       // pieChart.setInnerPadding(0);


       /* "\n"+"Wallet Balance : "+preferences.getFloat(GlobalVariables.USER_COINS,0)+ " ₹"*/
       // Set the data and color to the pie chart
       pieChart.addPieSlice(
               new PieModel(
                       "D1",

                       Integer.parseInt(String.valueOf(D1)),
                       Color.parseColor("#FFA726")));
       pieChart.addPieSlice(
               new PieModel(
                       "D2",
                       Integer.parseInt(String.valueOf(D2)),
                       // Integer.parseInt(correctcodes.getText().toString()),
                       Color.parseColor("#66BB6A")));
     /*  pieChart.addPieSlice(
               new PieModel(
                       "D3",
                       // Integer.parseInt(wrongcodes.getText().toString()),
                       Integer.parseInt(String.valueOf(D3)),
                       Color.parseColor("#EF5350")));*/

       // To animate the pie chart
       pieChart.startAnimation();

       new Handler().postDelayed(() -> {
           method.loadingDialog.dismiss();
         //  method.preferencess.setValue("intent","");
       }, Integer.parseInt(GlobalVariables.usermDTO.getCodegenTimer())*1000);

      /* if(!method.preferencess.getValue("intent").equals("")){
           Log.d(TAG, "setData: delay by admin");
           new Handler().postDelayed(() -> {

         method.preferencess.setValue("intent","");
           }, Integer.parseInt(GlobalVariables.usermDTO.getCodegenTimer())*1000);
       }else{
       method.loadingDialog.dismiss();
       }
*/
    }

    @Override
    public void onResume() {
        super.onResume();
      //  mInterstitialAd.loadAd(new AdRequest.Builder().build());
      //  LoadSettings();
        getHomeData(requireActivity());
    }

    private void initializeAd() {
        if (mContext == null) {
            return;
        }

       // mInterstitialAd = new InterstitialAd(mContext);
      //  mInterstitialAd.setAdUnitId(getString(R.string.admob_test_interstitial_ad_unit_id));
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
               /* mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);*/
            }
        }.start();

        mTimerRunning = true;
       /* mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);*/
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();

    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
        Log.e("timer", "updateCountDownText: "+ timeLeftFormatted);
    }

    private void startTimer2()
    {
        if (getActivity() != null) {
            timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            time++;
                            mTextViewCountDown.setText(getTimerText());
                            timerStarted = true;

                        }
                    });
                }

            };
            timer.scheduleAtFixedRate(timerTask, 0 ,1000);

            Thread timerTask = new Thread() {
                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(1000);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Calendario para obtener fecha & hora
                                    time++;
                                    mTextViewCountDown.setText(getTimerText());
                                    timerStarted = true;
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        Log.v("InterruptedException", e.getMessage());
                    }

                }
            };

        }

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

    public void startTapped() {
        if (!timerStarted) {
            timerStarted = true;
            //setButtonUI("STOP", R.color.red);

            startTimer2();
          //  method.startTimer2(requireActivity(),mTextViewCountDown);
        }

    }
        public void StopTapped()
        {


            timerStarted = false;
            String finaltimerText= String.valueOf(mTextViewCountDown.getText());
            //setButtonUI("START", R.color.green);
            mTextViewCountDown.setText(getTimerText());
           // editor.putString(GlobalVariables.timer_text, (String) mTextViewCountDown.getText());
            Log.e("KINGSN", String.format("startStopTapped:%s", mTextViewCountDown.getText()));
            Log.e("KINGSN", "finalTimer: "+finaltimerText );
            timerTask.cancel();
            generate.isClickable();{
           // generate.setClickable(false);
        }

    }



    public void enablegen()
    {
      if(mBinding.textViewGenerate.isClickable())  {
          mBinding.textViewGenerate.setClickable(false);
        }else if(!mBinding.textViewGenerate.isClickable()){
        mBinding.textViewGenerate.setClickable(true);
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        if(timerTask != null){
            timerTask.cancel();
            //cancel timer task and assign null
        }
    }

    public void alert(Activity activity){

        if(method.userDTO.getAllow().equals("2")) {
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
                    activity. finish();

                }
            });

        }else
        if(method.userDTO.getJoiningPaid().equals("1")) {
            //Toast.makeText(HomeActivity.this, "Your account is blocked", Toast.LENGTH_SHORT).show();
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
                                        /*startActivity(new Intent(HomeActivity.this, SettingsActivity
                                                .class));*/
                    method.loadingDialogg(activity);
                    // GenerateChecksum();
                    startPayment(requireActivity());
                    // finish();


                }
            });

            appPay3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                                        /*startActivity(new Intent(HomeActivity.this, SettingsActivity
                                                .class));*/
                    method.loadingDialogg(activity);

                    onSuccessPay(activity,orderId);
                    // finish();

                }
            });

        }else
        if(method.userDTO.getJoiningPaid().equals("3")) {
            //Toast.makeText(HomeActivity.this, "Your account is blocked", Toast.LENGTH_SHORT).show();
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
                                        /*startActivity(new Intent(HomeActivity.this, SettingsActivity
                                                .class));*/
                    method.loadingDialogg(activity);
                    //GenerateChecksum();
                    // finish();
                    activity. finish();
                    //loadingDialog.dismiss();
                    activity.finishAffinity();

                }
            });

            appPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    activity. startActivity(new Intent(activity, ContactActivity
                            .class));
                    //loadingDialog.show();
                    //GenerateChecksum();
                    // finish();
                    activity.finish();
                  method.loadingDialog.dismiss();


                }
            });

        }
    }

    public  void startPayment(Activity activity){
        if(GlobalVariables.settings.getPaymentGateway().equals("paytm_gateway")){
            GenerateChecksum(activity);
        }else if(GlobalVariables.settings.getPaymentGateway().equals("razorpay_gateway")) {
            startRazorPayment(activity);
        }else if(GlobalVariables.settings.getPaymentGateway().equals("payumoney_gateway")) {
            startPayuPayment(activity);
        }if(GlobalVariables.settings.getPaymentGateway().equals("upiId")) {
            String upiId = GlobalVariables.settings.getUpiId();
            String name = (method.userDTO.getName());
            String desc = "Add Money";
            String note = (method.userDTO.getMobile());
            // String note = noteEt.getText().toString();
            payUsingUpi(settings.getAppJoiningFee(), upiId, name, note);
        }
    }


    public  void startRazorPayment(Activity activity) {
        Checkout checkout = new Checkout();
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

        final Checkout co = new Checkout();
        checkout.setKeyID(GlobalVariables.settings.getPayumoneyKey());
        /**
         * Instantiate Checkout
         */


        /**
         * Set your logo here
         */
        checkout.setImage(R.mipmap.ic_launcher_round);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "V1infotech Pvt.Ltd");
            options.put("description", "App Joining Fee");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://v1infotech.com/admin/assets/images/profile");
            options.put("currency", "INR");

            String payment = (GlobalVariables.settings.getAppJoiningFee());;

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", method.userDTO.getEmail());
            preFill.put("contact", method.userDTO.getMobile());
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public  void  GenerateChecksum(Activity activity) {
        method.loadingDialogg(activity);
        Random r = new Random(System.currentTimeMillis());
        orderId = "QRCODE" + (1 + r.nextInt(2)) * 1000
                + r.nextInt(1000);
        //  editor.putString(GlobalVariables.txn_orderId, orderId);


        String url="https://v1infotech.com/admin/paytm1/generateChecksum.php";


        Map<String, String> params = new HashMap<>();
        params.put( "MID" , GlobalVariables.settings.getPaytmMid());
        params.put( "ORDER_ID" , orderId);
        params.put( "CUST_ID" , method.userDTO.getUserReferalCode());
        params.put( "MOBILE_NO" , method.userDTO.getMobile());
        params.put( "EMAIL" , method.userDTO.getEmail());
        params.put( "CHANNEL_ID" , "WAP");
        params.put( "TXN_AMOUNT" , GlobalVariables.settings.getAppJoiningFee());
        params.put( "WEBSITE" , WEBSITE);
        params.put( "INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
        params.put( "CALLBACK_URL", CALLBACK_URL);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
// Request a string response from the provided URL.
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(HomeActivity.this,response,Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.has("CHECKSUMHASH"))
                            {
                                checksum=jsonObject.getString("CHECKSUMHASH");
                                onStartTransaction(activity);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(HomeActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                activity.finish();
                activity.startActivity(requireActivity().getIntent());
                method.loadingDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<String, String>();
                params.put( "MID" , GlobalVariables.settings.getPaytmMid());
                params.put( "ORDER_ID" , orderId);
                params.put( "CUST_ID" , method.userDTO.getUserReferalCode());
                params.put( "MOBILE_NO" , method.userDTO.getMobile());
                params.put( "EMAIL" , method.userDTO.getEmail());
                params.put( "CHANNEL_ID" , "WAP");
                params.put( "TXN_AMOUNT" , GlobalVariables.settings.getAppJoiningFee());
                params.put( "WEBSITE" , WEBSITE);
                params.put( "INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
                params.put( "CALLBACK_URL", CALLBACK_URL);

                return params;
            }
        };


        queue.add(stringRequest);
// Access the RequestQueue through your singleton class.
        //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public  void startPayuPayment(Activity activity){

    }


    public void onStartTransaction(Activity activity) {
        PaytmPGService Service = PaytmPGService.getProductionService();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put( "MID" , GlobalVariables.settings.getPaytmMid());
        // Key in your staging and production MID available in your dashboard


        paramMap.put( "ORDER_ID" , orderId);
        paramMap.put( "CUST_ID" , method.userDTO.getUserReferalCode());
        paramMap.put( "MOBILE_NO" , method.userDTO.getMobile());
        paramMap.put( "EMAIL" , method.userDTO.getEmail());
        paramMap.put( "CHANNEL_ID" , "WAP");
        paramMap.put( "TXN_AMOUNT" , GlobalVariables.settings.getAppJoiningFee());
        paramMap.put( "WEBSITE" , WEBSITE);
        paramMap.put( "INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
        paramMap.put( "CALLBACK_URL", CALLBACK_URL);
        paramMap.put( "CHECKSUMHASH" , checksum);



        PaytmOrder Order = new PaytmOrder((HashMap<String, String>) paramMap);



        Service.initialize(Order, null);



        Service.startPaymentTransaction(activity, true,
                true, new PaytmPaymentTransactionCallback() {

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        System.out.println("===== onTransactionResponse " + inResponse.toString());
                        if (Objects.equals(inResponse.getString("STATUS"), "TXN_SUCCESS")) {
                            //    Payment Success
                            Toast.makeText(activity," Transaction success",Toast.LENGTH_LONG).show();

                            //uploadData();
                            onSuccessPay(activity,orderId);
                        } else if (!inResponse.getBoolean("STATUS")) {
                            //    Payment Failed
                            Toast.makeText(activity," Transaction Failed",Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getContext(), HomeActivity.class));
                            activity.finish();
                            activity. startActivity(requireActivity().getIntent());
                            method.loadingDialog.dismiss();

                        }
                    }

                    @Override
                    public void networkNotAvailable() {
                        // network error
                        //clickOnGenerate();
                        activity. finish();
                        startActivity(requireActivity().getIntent());
                        method.loadingDialog.dismiss();
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // AuthenticationFailed
                        activity.finish();
                        startActivity(requireActivity().getIntent());

                        // clickOnGenerate();
                        //startActivity(new Intent(getContext(), HomeActivity.class));
                        method.loadingDialog.dismiss();
                    }

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // UI Error
                        // clickOnGenerate();
                        activity.finish();
                        startActivity(requireActivity().getIntent());
                        method.loadingDialog.dismiss();
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                        //  Web page loading error
                        //clickOnGenerate();
                        //startActivity(new Intent(getContext(), HomeActivity.class));
                        activity.finish();
                        startActivity(requireActivity().getIntent());
                        method.loadingDialog.dismiss();
                    }

                    @Override
                    public void onBackPressedCancelTransaction() {
                        // on cancelling transaction

                        //clickOnGenerate();
                        // startActivity(new Intent(getContext(), HomeActivity.class));
                        activity.finish();
                        startActivity(requireActivity().getIntent());
                        method.loadingDialog.dismiss();

                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        // maybe same as onBackPressedCancelTransaction()
                        //clickOnGenerate();
                        //startActivity(new Intent(getContext(), HomeActivity.class));
                        activity.finish();
                        startActivity(requireActivity().getIntent());
                        method.loadingDialog.dismiss();
                    }
                });
    }


    private void onSuccessPay(Activity activity, String orderId) {

        if(orderId ==null){
            //Toast.makeText(HomeActivity.this, "NULL", Toast.LENGTH_LONG).show();
            //editor.putString(GlobalVariables.txn_orderId, orderId);


            Random r = new Random(System.currentTimeMillis());
            orderId = "BYCASH" + (1 + r.nextInt(2)) * 1000
                    + r.nextInt(1000);
            //  editor.putString(GlobalVariables.txn_orderId, this.orderId);
            method.preferencess.setValue(GlobalVariables.txn_orderId,orderId);

        }
          method.loadingDialogg(activity);
        String finalOrderId = orderId;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_insert_payment_verification,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                        try {
                        //    System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray(GlobalVariables.AppSid);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");

                                if (success.equals("1")) {
                                    Toast.makeText(activity, "Updated", Toast.LENGTH_SHORT).show();
                                    activity.finish();
                                    // startActivity(getIntent());
                                     method.loadingDialog.dismiss();
//                                    loadingDialog.dismiss();
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
                                                    activity.finishAffinity();
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
                                activity. finishAffinity();
                            }
                        });

                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        }) { @Override
        protected Map<String, String> getParams() {

            Map<String, String> params = new HashMap<>();
            params.put("app_joining_fee_paid","");
            params.put("user_id", method.userDTO.getMobile());
            params.put("name",method.userDTO.getName());
            params.put("email", method.userDTO.getEmail());
            params.put("paid",method.userDTO.getJoiningPaid());
            params.put("order_id", finalOrderId);
            params.put("city",method.userDTO.getCity());

            return params;
        }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);



    }

    public void payUsingUpi( String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(requireActivity().getPackageManager())) {
            requireActivity().startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(requireActivity(),"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPI_PAYMENT) {
            if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    Log.d("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            } else {
                Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);

    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String[] response = str.split("&");
            for (String s : response) {
                String[] equalStr = s.split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                //  Toast.makeText(Payment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
                // showPay_Status_AlertDialog(1);
              //  onSuccessPay(orderId);
                onSuccessPay(requireActivity(),orderId);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                //Toast.makeText(Payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Toast.makeText(requireActivity()," Transaction Failed",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getContext(), HomeActivity.class));
                requireActivity().finish();
                requireActivity(). startActivity(requireActivity().getIntent());
                method.loadingDialog.dismiss();
            }
            else {
                Toast.makeText(requireActivity(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Toast.makeText(requireActivity()," Transaction Failed",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getContext(), HomeActivity.class));
                requireActivity().finish();
                requireActivity(). startActivity(requireActivity().getIntent());
                method.loadingDialog.dismiss();
            }

    }


}
