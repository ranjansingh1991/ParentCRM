package in.semicolonindia.parentcrm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.semicolonindia.parentcrm.activities.SupportActivity;
import in.semicolonindia.parentcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.parentcrm.dialogs.ProgressDialog;
import in.semicolonindia.parentcrm.util.ConnectionDetector;
import in.semicolonindia.parentcrm.util.PreferencesManager;

import static in.semicolonindia.parentcrm.rest.BaseUrl.sLoginURL;
@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvForgetPwd;
    private   EditText etEmail;
    private ImageView imgHelp;
    private  EditText etPwd;
    private TextView tvAppName;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialise();
    }

    private void initialise() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPwd = (EditText) findViewById(R.id.etPwd);
        tvForgetPwd = (TextView) findViewById(R.id.tvForgetPwd);
        imgHelp = (ImageView) findViewById(R.id.imgHelp);

        Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        Typeface appFontRegular = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");

        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SupportActivity.class));
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().length() > 0 && etPwd.getText().toString().length() > 0) {
                    if (new ConnectionDetector(getApplicationContext()).isConnectingToInternet()) {
                        doLogin(etEmail.getText().toString(), etPwd.getText().toString());
                    } else {
                        final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(LoginActivity.this,
                                R.style.DialogSlideAnim);
                        mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mNoConnectionDialog.setCancelable(false);
                        mNoConnectionDialog.setCanceledOnTouchOutside(false);
                        mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                        mNoConnectionDialog.show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Enter Email & Password", Toast.LENGTH_SHORT).show();
                }

            }
        });


        tvForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            }
        });
    }

    private void doLogin(final String Email, final String pass) {
        final ProgressDialog mProgressDialog = new ProgressDialog(LoginActivity.this, "Please wait...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sLoginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        // *** Here we will create Jason Object *****//
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                            new PreferencesManager(getApplicationContext()).setEmail(Email);
                            new PreferencesManager(getApplicationContext()).setPasswordword(pass);
                            //new PreferencesManager(getApplicationContext()).setClassID(jsonObject.getString("class_id"));
                            new PreferencesManager(getApplicationContext()).setLogintype(jsonObject.getString("login_type"));
                           // new PreferencesManager(getApplicationContext()).setUserID(jsonObject.getString("login_user_id"));
                            new PreferencesManager(getApplicationContext()).setParentID(jsonObject.getString("login_user_id"));
                            new PreferencesManager(getApplicationContext()).setUserID(jsonObject.getString("login_user_id"));
                            new PreferencesManager(getApplicationContext()).setImage(jsonObject.getString("image_url"));
                            new PreferencesManager(getApplicationContext()).setName(jsonObject.getString("name"));
                            //new PreferencesManager(getApplicationContext()).setTransportID(jsonObject.getString("transport_id"));

                            //  new PreferencesManager(getApplicationContext()).setSectionID(jsonObject.getString("section_id"));

                            //********Json Array create  inside JSONObject******//
                            JSONArray jsonArray = jsonObject.getJSONArray("children");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //**** Again we will create JSONObject********//
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                new PreferencesManager(getApplicationContext()).setStudentID(jsonObject1.getString("student_id"));
                                new PreferencesManager(getApplicationContext()).setDormitoryID(jsonObject1.getString("dormitory_id"));
                                new PreferencesManager(getApplicationContext()).setTransportID(jsonObject1.getString("transport_id"));
                            }
                            mProgressDialog.dismiss();
                            //Here first of all  we chack activity login or not if login then activity will come HomeActivity....
                            new PreferencesManager(getApplicationContext()).setLogedIn(true);
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        } else {
                            Toast.makeText(LoginActivity.this, "Email & Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("status Response", String.valueOf(error));
                mProgressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("tag", "login");
                params.put("email", Email);
                params.put("password", pass);
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed(){

        //finish();
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

}

