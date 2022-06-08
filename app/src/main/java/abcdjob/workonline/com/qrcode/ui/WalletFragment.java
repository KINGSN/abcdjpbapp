package abcdjob.workonline.com.qrcode.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.databinding.FragmentWalletBinding;
import abcdjob.workonline.com.qrcode.ui.Util.Constant;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;
import abcdjob.workonline.com.qrcode.ui.adapter.RedeemAdapter;
import abcdjob.workonline.com.qrcode.ui.adapter.Redeem_Data;
import abcdjob.workonline.com.qrcode.ui.adapter.WalletAdapter;
import abcdjob.workonline.com.qrcode.ui.adapter.Wallet_Data;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;
import abcdjob.workonline.com.qrcode.ui.privacy_policy.PrivayPolicyActivity;
import es.dmoral.toasty.Toasty;

import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;
import static android.view.View.VISIBLE;
import static java.lang.Integer.parseInt;

public class WalletFragment extends Fragment {

    private FragmentWalletBinding mBinding;
    private Context mContext;
    private InterstitialAd mInterstitialAd;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    LinearLayout tab1, tab2, tab3,withdwawalTab,transactionTab,redeemTab,availbaln;
    String url,test,url2;
    RecyclerView recyclerView,recyclerView1;
    WalletAdapter recyclerAdapter;
    RedeemAdapter recyclerAdapter1;
    private List<Wallet_Data> wallet_data;
    private List<Redeem_Data> redeem_data;
    TextView withdrawalTotal,wallet_total,bonus_total,maxredm,tabtt1,tabtt2,tabtt3,widthraw_note;
    TextInputLayout bankaccountETl,banknameETl,bankifscETl;
    TextInputEditText payTmMobile,banknameEt,bankifscEt,bankaccountEt,withdrawalAmount;
    Button withdrawalBtn;
    String Email,reqUrl,mobile, amount, email, name, paytm_no,bank_acc_no,bank_name,bank_ifsc,widthrawal_method;
    ProgressBar progressBar;
    TextView total;
    FirebaseUser user;
    private FrameLayout adview;
    private View linev;

    private Dialog dialog;
    private Dialog loadingDialog;
    private  Method method;

    Integer intwallet,redemamount;
    private Spinner spinner_widthrawal;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final  View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_wallet, container, false);
//        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false);

        method=new Method(requireActivity());

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent);
        availbaln= view.findViewById(R.id.availbaln);
        linev= view.findViewById(R.id.linev);
        availbaln.getWidth();
        linev.setMinimumWidth(availbaln.getWidth());
        //String  test=GlobalVariables.USER_MOBILE;
       // Toast.makeText(requireActivity(),"No Widthrawals Yet"+test,Toast.LENGTH_SHORT).show();

        Log.d(GlobalVariables.TAG, "onCreateView: "+ Method.userDTO.getWallet());

        recyclerView = view. findViewById(R.id.wallet_adapter);
        wallet_total= view.findViewById(R.id.wallet_total);
        bonus_total= view.findViewById(R.id.bonus_total);
        maxredm= view.findViewById(R.id.maxredm);
        widthraw_note= view.findViewById(R.id.widthraw_note);
        // preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        tab1= view.findViewById(R.id.tab1);
        tab2= view. findViewById(R.id.tab2);
        tab3= view.findViewById(R.id.tab3);
        withdwawalTab= view.findViewById(R.id.withdrawlTab);
        redeemTab= view.findViewById(R.id.redeemTab);
        transactionTab= view.findViewById(R.id.transctionTab);
        payTmMobile= view.findViewById(R.id.phoneET);
        banknameEt= view.findViewById(R.id.banknameET);
        bankifscEt= view.findViewById(R.id.bankifscET);
        bankaccountEt= view.findViewById(R.id.bankaccountET);
        banknameETl= view.findViewById(R.id.banknameETl);
        bankifscETl= view.findViewById(R.id.bankifscETl);
        bankaccountETl= view.findViewById(R.id.bankaccountETl);

       /* payTmMobile=(EditText)view.findViewById(R.id.phoneET);*/
        withdrawalAmount= view.findViewById(R.id.amountET);
        tabtt1= view.findViewById(R.id.tabtt1);
        tabtt2= view.findViewById(R.id.tabtt2);
        tabtt3= view.findViewById(R.id.tabtt3);

        recyclerView1=view.findViewById(R.id.wallet_redeem);
        withdrawalBtn= view.findViewById(R.id.withdrawBtn);
        progressBar= view.findViewById(R.id.progressBar1);

        spinner_widthrawal = view.findViewById(R.id.spinner_widthrawal);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.widthrawals_array, simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_widthrawal.setAdapter(adapter);




        // intwallet=Integer.parseInt(preferences.getString(GlobalVariables.USER_COINS,""));


        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        if (loadingDialog.getWindow() != null)
            loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        loadingDialog.show();
        Method ads=new Method(getContext());
//        HomeActivity HomeActivity =new HomeActivity();
//        HomeActivity.loadDataSQL(requireActivity());
        //indusads=new Ads();
        // ads.InterstitialAd(this);

       /* dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg));

        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1200);*/



        /////////setting text from server////
        wallet_total.setText(""+ Method.userDTO.getWallet()+ " ₹");
        bonus_total.setText(""+ Method.userDTO.getBonusBalance()+ " ₹");
        maxredm.setText("Minimum Redeem = "+GlobalVariables.settings.getMinRedeemAmount());
        widthraw_note.setText(GlobalVariables.settings.getWidthrawNote());

        withdwawalTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tab1.setVisibility(View.GONE);
                tab2.setVisibility(VISIBLE);
                tab3.setVisibility(View.GONE);
                tabtt2.setTextColor((getResources().getColor(R.color.BloddColour)));
                tabtt1.setTextColor((getResources().getColor(R.color.gnt_white)));
                tabtt3.setTextColor((getResources().getColor(R.color.gnt_white)));
                withdrawalAmount.setText(""+ Method.userDTO.getWallet());
             //   withdrawalAmount.setEnabled(false);
              /* Ads Ads = new Ads();
                if (GlobalVariables.adClickModel.getInterstitial().equals("0"))
                {
                    Ads.ScreenopeningInterstitialAd(WalletActivity.this);
                }*/
                //

            }
        });
        transactionTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tab1.setVisibility(VISIBLE);
                tab2.setVisibility(View.GONE);
                tab3.setVisibility(View.GONE);
                tabtt1.setTextColor((getResources().getColor(R.color.BloddColour)));
                tabtt2.setTextColor((getResources().getColor(R.color.gnt_white)));
                tabtt3.setTextColor((getResources().getColor(R.color.gnt_white)));


            }
        });

        redeemTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tab1.setVisibility(View.GONE);
                tab2.setVisibility(View.GONE);
                tab3.setVisibility(VISIBLE);
                tabtt3.setTextColor((getResources().getColor(R.color.BloddColour)));
                tabtt1.setTextColor((getResources().getColor(R.color.gnt_white)));
                tabtt2.setTextColor((getResources().getColor(R.color.gnt_white)));
                //callRedeemdata();
                recyclerView1.setAdapter(null);
               /* dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_loading);
                dialog.setCancelable(false);
                if (dialog.getWindow() != null)
                    dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg));

                dialog.show();*/

                loadingDialog.show();


                redeem_data = new ArrayList<>();
                recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerAdapter1= new RedeemAdapter(redeem_data,getContext());
                //textView=(TextView)findViewById(R.id.textView);
                // recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));


                JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(
                        Request.Method.GET,RestAPI.API_withdrawal_History+ Method.userDTO.getMobile(),null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // Do something with response
                                //mTextView.setText(response.toString());

                                // Toast.makeText(requireActivity(),""+response,Toast.LENGTH_SHORT).show();

                                // Process the JSON
                                try{
                                    // Loop through the array elements
                                    for(int i=0;i<response.length();i++){
                                        // Get current json object
                                        JSONObject ob = response.getJSONObject(i);

                                        //JSONObject ob=array.getJSONObject(i);
                                        Redeem_Data ld2=new Redeem_Data(ob.getString("payment_method"),ob.getString("ammount"),ob.getString("paytm_no")
                                                ,ob.getString("status"),ob.getString("req_date"),ob.getString("paid_date")
                                                ,ob.getString("cancelled_date"),ob.getString("txn_no"));
                                        redeem_data.add(ld2);

                                        loadingDialog.dismiss();


                                    }
                                    recyclerAdapter1= new RedeemAdapter(redeem_data,getContext());
                                    recyclerView1.setAdapter(recyclerAdapter1);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }loadingDialog.dismiss();
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                // Do something when error occurred
                                dialog.dismiss();
                                Toast.makeText(requireActivity(),"Error...!",Toast.LENGTH_SHORT).show();
                                loadingDialog.dismiss();
                            }
                        }
                );

                RequestQueue requestQueue1= Volley.newRequestQueue(getContext());
                requestQueue1.add(jsonArrayRequest1);

                //Toast.makeText(WalletActivity.this,"No Widthrawals Yet",Toast.LENGTH_SHORT).show();

            }
        });


        wallet_data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter= new WalletAdapter(wallet_data,getContext());



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, RestAPI.API_Transaction_History+ Method.userDTO.getMobile(),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        //Toast.makeText(requireActivity(),""+response,Toast.LENGTH_SHORT).show();
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject ob = response.getJSONObject(i);

                                //JSONObject ob=array.getJSONObject(i);

                                Wallet_Data ld1=new Wallet_Data(ob.getString("amount"),ob.getString("status"),ob.getString("date"),ob.getString("txn"));
                                wallet_data.add(ld1);
                                //Toast.makeText(requireActivity(), "hello"+ob.getString("amount"), Toast.LENGTH_LONG).show();


                            }
                            recyclerAdapter= new WalletAdapter(wallet_data,getContext());
                            //recyclerView.setLayoutManager(new LinearLayoutManager(view));
                            //GridLayoutManager lm = new GridLayoutManager(view, 1);
                            // recyclerView.setLayoutManager();
                            recyclerView.setAdapter(recyclerAdapter);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.dismiss();
                                }
                            }, 1000);
                            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))
                            {
                                ads.callindustrial((FragmentActivity) getContext());
                                // ads.callindustrial(HomeActivity.this);
                            }

                            //loadingDialog.dismiss();

                        }catch (JSONException e){
                            e.printStackTrace();
                          //  Toast.makeText(requireActivity(), "hello"+e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        //Toast.makeText(WalletActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);


        ////////////CALLING bANNER ADD  ///////////////////////


        ////////////////////////////////
        spinner_widthrawal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

                if (position == 2){payTmMobile.setHint("Paytm Number");
                    banknameETl.setVisibility(View.GONE);
                    bankifscETl.setVisibility(View.GONE);
                    bankaccountETl.setVisibility(View.GONE);
                    payTmMobile.setInputType(InputType.TYPE_CLASS_PHONE);
                    payTmMobile.setText(test);
                }else if (position == 4){payTmMobile.setHint("Enter Your UPI Adress");
                    banknameETl.setVisibility(View.GONE);
                    bankifscETl.setVisibility(View.GONE);
                   bankaccountETl.setVisibility(View.GONE);
                    payTmMobile.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                    payTmMobile.setText("");
                }else if (position == 0){
                  /*  banknameEt.setHint("Enter Bank Name");
                    bankifscEt.setHint("Enter Bank Ifsc Code");
                    bankaccountEt.setHint("Enter Bank Account Number");*/
                    banknameEt.setInputType(InputType.TYPE_CLASS_TEXT);
                    bankifscEt.setInputType(InputType.TYPE_CLASS_TEXT);
                    banknameETl.setVisibility(VISIBLE);
                    bankifscETl.setVisibility(VISIBLE);
                    bankaccountETl.setVisibility(VISIBLE);
                    payTmMobile.setText(test);

                }else if (position == 1){
                    payTmMobile.setInputType(InputType.TYPE_CLASS_PHONE);
                    payTmMobile.setText(test);
                    banknameEt.setInputType(InputType.TYPE_CLASS_TEXT);
                    bankifscEt.setInputType(InputType.TYPE_CLASS_TEXT);
                    banknameETl.setVisibility(View.GONE);
                    bankifscETl.setVisibility(View.GONE);
                    bankaccountETl.setVisibility(View.GONE);
                }else{payTmMobile.setHint("Select Payment Method");}
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        //////////////////////////////////


        withdrawalBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                paytm_no = Objects.requireNonNull(payTmMobile.getText()).toString();
                amount = Objects.requireNonNull(withdrawalAmount.getText()).toString();
                bank_acc_no= Objects.requireNonNull(bankaccountEt.getText()).toString();
                bank_name= Objects.requireNonNull(banknameEt.getText()).toString();
                bank_ifsc= Objects.requireNonNull(bankifscEt.getText()).toString();

                name = Method.userDTO.getName();
                mobile = Method.userDTO.getMobile();
                final String widthrawal_method= spinner_widthrawal.getSelectedItem().toString();
               // Toast.makeText(getContext(), widthrawal_method, Toast.LENGTH_SHORT).show();

                if (Method.userDTO.getWidthrawalAllow().equals("0")) {
                    switch (widthrawal_method) {
                        case "PAYTM":
                            //  Toast.makeText(getContext(), "pay1", Toast.LENGTH_SHORT).show();
                            if (payTmMobile.getText().toString().isEmpty()) {

                                payTmMobile.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Insert Your Paytm Number", Toast.LENGTH_SHORT).show();

                            } else if (withdrawalAmount.getText().toString().isEmpty()) {
                                Toast.makeText(getContext(), "upi1", Toast.LENGTH_SHORT).show();
                                withdrawalAmount.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Enter Redeem ammount", Toast.LENGTH_SHORT).show();

                            } else {

                                insert_Withdrawal();

                            }

                            break;
                        case "UPI":
                            if (payTmMobile.getText().toString().isEmpty()) {

                                payTmMobile.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Insert Your UPI Number", Toast.LENGTH_SHORT).show();
                                payTmMobile.setError("Insert Your UPI Number");
                                // return;
                            }else
                            if (withdrawalAmount.getText().toString().isEmpty()) {
                                withdrawalAmount.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Enter Redeem ammount", Toast.LENGTH_SHORT).show();

                            }else
                            if (Integer.parseInt(withdrawalAmount.getText().toString())>Integer.parseInt(Method.userDTO.getWallet())) {
                                withdrawalAmount.setError("Invalid Amount ");
                                Toast.makeText(getContext(), "Enter Redeem ammount Lesser or Equal to Your Wallet Balance", Toast.LENGTH_SHORT).show();

                            }  else {


                                insert_Withdrawal();

                            }

                            break;
                        case "BANK ACCOUNT":

                            if (payTmMobile.getText().toString().isEmpty()) {

                                payTmMobile.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Insert Your Paytm Number", Toast.LENGTH_SHORT).show();

                            }else
                            if (withdrawalAmount.getText().toString().isEmpty()) {
                                withdrawalAmount.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Enter Redeem ammount", Toast.LENGTH_SHORT).show();

                            }else
                            if (bankaccountEt.getText().toString().isEmpty()) {
                                bankaccountEt.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Enter Account Number", Toast.LENGTH_SHORT).show();

                            }else
                            if (banknameEt.getText().toString().isEmpty()) {
                                banknameEt.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Enter Bank Name", Toast.LENGTH_SHORT).show();
                                // return;
                            }else
                            if (bankifscEt.getText().toString().isEmpty()) {
                                bankifscEt.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Enter Bank Ifsc Code", Toast.LENGTH_SHORT).show();

                            } else {

                                insert_Withdrawal();

                            }
                            //return;
                            break;
                        case "CASH PAYMENT":

                            if (payTmMobile.getText().toString().isEmpty()) {

                                payTmMobile.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Insert Your Paytm Number", Toast.LENGTH_SHORT).show();

                            }else
                            if (withdrawalAmount.getText().toString().isEmpty()) {
                                withdrawalAmount.setError("Fields Cant be Empty");
                                Toast.makeText(getContext(), "Enter Redeem ammount", Toast.LENGTH_SHORT).show();


                            } else {

                                insert_Withdrawal();

                            }
                            //return;
                            break;
                    }

                }else{
                    Toasty.error(requireActivity(), "You Have Been Restricted For Requesting Widthrawal. Contact Admin..!", Toast.LENGTH_LONG, true).show();
                }

            }
        });

        return view;



    }


    @SuppressLint("SetTextI18n")
    public void initializeAd(){



    }



    public void insert_Withdrawal()  {

        loadingDialog.show();
            //final Integer intwallet;
            final Float redemamount = Float.valueOf((withdrawalAmount.getText()).toString());
           // Toast.makeText(getContext(), ""+redemamount, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), "hello1", Toast.LENGTH_SHORT).show();

        paytm_no = Objects.requireNonNull(payTmMobile.getText()).toString();
        amount = Objects.requireNonNull(withdrawalAmount.getText()).toString();
        bank_acc_no= Objects.requireNonNull(bankaccountEt.getText()).toString();
        bank_name= Objects.requireNonNull(banknameEt.getText()).toString();
        bank_ifsc= Objects.requireNonNull(bankifscEt.getText()).toString();

        name = Method.userDTO.getName();
        mobile = Method.userDTO.getMobile();
        final String widthrawal_method= spinner_widthrawal.getSelectedItem().toString();



           // Toast.makeText(requireActivity(),""+ preferences.getInt(GlobalVariables.USER_COINS, 0),   Toast.LENGTH_SHORT).show();

        //  Toast.makeText(requireActivity(),""+ preferences.getInt(GlobalVariables.REDEEM_MIN, 0),Toast.LENGTH_SHORT).show();
        if
        ( (redemamount) <= (Float.parseFloat(Method.userDTO.getWallet())))
        {

            if
            (Float.parseFloat(Method.userDTO.getWallet()) >= (Integer.parseInt(Method.settings.getMinRedeemAmount())))
            {
               // Toast.makeText(getContext(), "hello2", Toast.LENGTH_SHORT).show();


              if ((redemamount> ( Float.parseFloat(Method.settings.getMinRedeemAmount()))))

                { // Toast.makeText(getContext(), "hello3", Toast.LENGTH_SHORT).show();

                    StringRequest request = new StringRequest(Request.Method.POST, RestAPI.API_INSERT_WIDTHRAWAL,
                            new com.android.volley.Response.Listener<String>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onResponse(String response) {
                                   // Toast.makeText(requireActivity(), ""+response, Toast.LENGTH_SHORT).show();

                                   // Toast.makeText(getContext(), "hello4", Toast.LENGTH_SHORT).show();

                                    try {
                                        System.out.println(response);
                                        JSONObject jsonObject = new JSONObject(response);

                                        JSONArray jsonArray = jsonObject.getJSONArray(GlobalVariables.AppSid);

                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String success = object.getString("success");

                                            if (success.equals("1")) {

                                            wallet_total.setText("" + ((Float.parseFloat(Method.userDTO.getWallet())) - ((Float.parseFloat(Method.settings.getMinRedeemAmount())))));
                                            //editor.putInt(GlobalVariables.USER_COINS, Integer.parseInt(String.valueOf(((preferences.getInt(GlobalVariables.USER_COINS,0))-(preferences.getInt(GlobalVariables.REDEEM_MIN,0))))));

                                            // wallet_total.setText(""+(preferences.getInt(GlobalVariables.USER_COINS,0)));

                                            Toast.makeText(requireActivity(), " Redeemed Successfully",
                                                    Toast.LENGTH_SHORT).show();
                                            payTmMobile.setText("");
                                            withdrawalAmount.setText("");
                                            loadingDialog.dismiss();
                                           // progressBar.setVisibility(View.GONE);
                                           // progressBar.setVisibility(View.GONE);
                                            withdrawalBtn.setVisibility(View.INVISIBLE);
                                                loadingDialog.dismiss();
                                                // loadingDialog.dismiss();
                                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getContext());
                                                alertDialogBuilder.setTitle(object.getString("title"));
                                                alertDialogBuilder.setMessage(object.getString("msg"));
                                                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                                alertDialogBuilder.setPositiveButton(getContext().getResources().getString(R.string.ok_message),
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface arg0, int arg1) {
                                                                // finish();
                                                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                                                startActivity(intent);
                                                                loadingDialog.dismiss();
                                                                //finishAffinity();

                                                            }
                                                        });

                                                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                                alertDialog.show();
                                               // Toast.makeText(getContext(), object.getString("msg"), Toast.LENGTH_LONG).show();


                                            //  wallet_total.setText(preferences.getInt(GlobalVariables.USER_COINS,0)-preferences.getInt(GlobalVariables.REDEEM_MIN,0));


                                        } else  {

                                                payTmMobile.setText("");
                                                withdrawalAmount.setText("");
                                                loadingDialog.dismiss();
                                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getContext());
                                                alertDialogBuilder.setTitle(object.getString("title"));
                                                alertDialogBuilder.setMessage(object.getString("msg"));
                                                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                                alertDialogBuilder.setPositiveButton(getContext().getResources().getString(R.string.ok_message),
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface arg0, int arg1) {
                                                                //finish();
                                                                loadingDialog.dismiss();
                                                                //finishAffinity();
                                                                startActivity(new Intent(requireActivity(), HomeActivity.class));

                                                            }
                                                        });

                                                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                                alertDialog.show();
                                                //Toast.makeText(getContext(), object.getString("msg"), Toast.LENGTH_LONG).show();
                                            }

                                        }

                                        // progressDialog.dismiss();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        //Toast.makeText(getContext(), "Error: " + e.getMessage(),
                                                //Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        loadingDialog.dismiss();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            // params.put("email", email);
                            params.put("user_id", abcdjob.workonline.com.qrcode.ui.Util.Method.userDTO.getMobile());
                            params.put("amount", amount);
                            params.put("paytm_no", paytm_no);
                            params.put("name", name);
                            params.put("widthrawal_method",widthrawal_method);
                            params.put("bank_acc_no",bank_acc_no);
                            params.put("bank_ifsc",bank_ifsc);
                            params.put("bank_name",bank_name);

                            return params;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(requireActivity());
                    queue.add(request);
                   // Toast.makeText(requireActivity(), "" + request, Toast.LENGTH_LONG).show();
                }
                else {
                    //progressBar.setVisibility(View.GONE);
                    loadingDialog.dismiss();
                  withdrawalAmount.setText("0");
                    Toast.makeText(requireActivity(), String.format("%s Rs To Redeem", "Please Enter Only " + Method.settings.getMinRedeemAmount()), Toast.LENGTH_SHORT).show();
                }


//
            }
            else {
                //progressBar.setVisibility(View.GONE);
                loadingDialog.dismiss();
                Toasty.error(requireActivity(), ("You Need atleast " + GlobalVariables.settings.getMinRedeemAmount() + " Rs To Ask for Widthrawal"), Toast.LENGTH_LONG, true).show();
               //Toast.makeText(requireActivity(), ("You Need atleast " + preferences.getInt(GlobalVariables.REDEEM_MIN, 0) + " Rs To Ask for Widthrawal"), Toast.LENGTH_SHORT).show();
            }
        }else{
            loadingDialog.dismiss();
            withdrawalAmount.setError("Inavlid Amount");
            withdrawalAmount.setText("0");
            Toast.makeText(requireActivity(), String.format("%s  To Redeem", "Please Enter Valid Amount "), Toast.LENGTH_SHORT).show();

        }
        }


}
