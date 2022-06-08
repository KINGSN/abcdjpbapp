package abcdjob.workonline.com.qrcode;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import abcdjob.workonline.com.qrcode.ui.ReferActivity;
import abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.ui.Util.Method;
import abcdjob.workonline.com.qrcode.ui.Util.RestAPI;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;

import static abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables.USER_CITY;
import static abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables.USER_EMAIL;
import static abcdjob.workonline.com.qrcode.ui.Util.GlobalVariables.USER_PASSWORD;


public class profileFragment extends Fragment {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText nameEt, phoneEt, emailEt, cityEt, passwordEt, cnfpassEt;
    private Button editBtn;
    private Dialog dialog;
    private FirebaseAuth auth;
    private FirebaseUser user;
   public TextView usermailT, foneT, totalEarned, idT, mobilet, usernamet, total_refered, total_earn,rank;
    // private FrameLayout adlayout2;
    private FrameLayout adview;
    private FrameLayout adlayout1;
    private FrameLayout adView2;
    private Dialog loadingDialog;
    private LinearLayout adlayout2;
    String url, test, url2;
    Integer Tearn;
    private ImageView phome,pwallet,preferals;

    public Method method;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


              method=new Method(requireActivity());
            //test = preferences.getString(GlobalVariables.USER_MOBILE, "");
            test = Method.userDTO.getMobile();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_profile, container, false);

        preferences = this.requireActivity().getSharedPreferences(GlobalVariables.ADMIN_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();

        test = preferences.getString(GlobalVariables.USER_MOBILE, "");
       // mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_profile);
        method=new Method(requireActivity());
        super.onCreate(savedInstanceState);
        nameEt = view.findViewById(R.id.nameET);
        phoneEt = view.findViewById(R.id.phoneET);
        passwordEt = view.findViewById(R.id.passwordET);
        cnfpassEt = view.findViewById(R.id.confirm_pass);
        emailEt = view.findViewById(R.id.emailET);
        cityEt = view.findViewById(R.id.cityET);
        totalEarned = view.findViewById(R.id.total_earn);
        editBtn = view.findViewById(R.id.editBtn);
        usermailT = view.findViewById(R.id.Usermail);
        usernamet = view.findViewById(R.id.username);
        mobilet = view.findViewById(R.id.MOBILE);
        total_refered = view.findViewById(R.id.total_refered);
        total_earn = view.findViewById(R.id.total_earn);
        idT = view.findViewById(R.id.rank);
        LinearLayout adContainer = view.findViewById(R.id.adlayout2);
        phome= view.findViewById(R.id.phome);
        pwallet= view.findViewById(R.id.pwallet);
        preferals= view.findViewById(R.id.preferals);

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg));

       // dialog.show();

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1200);*/
        abcdjob.workonline.com.qrcode.ui.Util.Method ads=new abcdjob.workonline.com.qrcode.ui.Util.Method(getContext());
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent);

        loadingDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        }, 1200);
        if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))
        {
            ads.callindustrial((FragmentActivity) getContext());
            // ads.callindustrial(HomeActivity.this);
        }


        usermailT.setText(Method.userDTO.getEmail());
        totalEarned.setText(Method.userDTO.getTotalPaid()+" ₹");
        idT.setText(Method.userDTO.getUserReferalCode());
        nameEt.setText(Method.userDTO.getName());
        emailEt.setText(Method.userDTO.getEmail());
        phoneEt.setText(Method.userDTO.getMobile());
        passwordEt.setText(Method.userDTO.getPassword());
        cnfpassEt.setText(Method.userDTO.getPassword());
        cityEt.setText(Method.userDTO.getCity());
        mobilet.setText(Method.userDTO.getMobile());
        usernamet.setText(Method.userDTO.getName());
        total_refered.setText(Method.userDTO.getTotalReferals());


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        nameEt.setEnabled(false);
        cityEt.setEnabled(false);
        passwordEt.setEnabled(false);
        cnfpassEt.setEnabled(false);
        emailEt.setEnabled(false);
        phoneEt.setEnabled(false);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(requireActivity(), "king", Toast.LENGTH_LONG).show();
                String text = editBtn.getText().toString();

                if (text.equals("EDIT")) {
                    editBtn.setText("UPDATE");
                    nameEt.setEnabled(true);
                    cityEt.setEnabled(true);
                    passwordEt.setEnabled(true);
                    cnfpassEt.setEnabled(true);
                    cityEt.setEnabled(true);
                    emailEt.setEnabled(true);
                    phoneEt.setEnabled(false);


                }

                if (text.equals("UPDATE")) {
                    editBtn.setText("EDIT");
                    nameEt.setEnabled(false);
                    cityEt.setEnabled(false);
                    passwordEt.setEnabled(false);
                    cnfpassEt.setEnabled(false);
                    emailEt.setEnabled(false);
                    phoneEt.setEnabled(false);

                    String name = nameEt.getText().toString();
                    String email = emailEt.getText().toString();
                    String password = passwordEt.getText().toString();
                    if (name.isEmpty())
                        return;


                    editor.putString(USER_CITY, cityEt.getText().toString());
                    editor.putString(USER_EMAIL, emailEt.getText().toString());
                    editor.putString(USER_PASSWORD, passwordEt.getText().toString());
                    editor.apply();
                    loadingDialog.show();
                    uploadData();

                }

            }
        });

        phome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( requireActivity(), HomeActivity.class));

            }
        });

       /* pwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent( requireActivity(),WalletFragment.class));
                Intent intent = new Intent(requireActivity(), WalletFragment.class);
                startActivity(intent);
            }
        });*/

        preferals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getContext(), ReferActivity.class));
            }
        });

        return view;
    }

    private void uploadData() {


        final EditText nameEt = requireView().findViewById(R.id.nameET);
        String name = nameEt.getText().toString();
      //  Toast.makeText(requireActivity(), "" + name, Toast.LENGTH_LONG).show();
        final String email = emailEt.getText().toString();

        final String password = passwordEt.getText().toString();
        final String city = cityEt.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_update_profile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                nameEt.setText(name);
                                usernamet.setText(name);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingDialog.dismiss();
                                    }
                                }, 1000);
                                Toast.makeText(requireActivity(), "Updated", Toast.LENGTH_SHORT).show();

                            } else if (success.equals("0")) {

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                           // Toast.makeText(requireActivity(), "Error: " + e.getMessage(),
                                   // Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error", "" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("users_profile_update","" );
                params.put("user_id", abcdjob.workonline.com.qrcode.ui.Util.Method.userDTO.getMobile());
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("city", city);
                Log.d(GlobalVariables.TAG, "uploadData: "+params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);


      //  Toast.makeText(requireActivity(), "" + stringRequest, Toast.LENGTH_LONG).show();


    }



}