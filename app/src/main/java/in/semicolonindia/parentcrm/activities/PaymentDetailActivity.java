package in.semicolonindia.parentcrm.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.adapters.PaymentsListAdapter;
import in.semicolonindia.parentcrm.beans.PaymentNames;
import in.semicolonindia.parentcrm.dialogs.ProgressDialog;

import static in.semicolonindia.parentcrm.rest.BaseUrl.sGetPaymentURL;

@SuppressWarnings("ALL")
public class PaymentDetailActivity extends AppCompatActivity {

    private String sStudID;
    ListView lvPayments;
    PaymentsListAdapter mPaymentsListAdapter;
    ArrayList<PaymentNames> arraylist = new ArrayList<PaymentNames>();
    ProgressDialog mProgressDialog;

    private String[] sTitle;
    private String[] sDesc;
    private String[] sAmount;
    private String[] sStatus;
    private String[] sYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        Bundle mBundle = this.getIntent().getExtras();

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("");
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        final TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        tvToolbarTitle.setTypeface(appFontBold);
        tvToolbarTitle.setText(mBundle.getString("name"));
        // tvToolbarTitle.setText(getString(R.string.payment_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentDetailActivity.this, PaymentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        sStudID = mBundle.getString("stud_id");

        lvPayments = (ListView) findViewById(R.id.lvPayments);
        getPaymentDetails();

        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mPaymentsListAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
    }

    private void getPaymentDetails() {
        mProgressDialog = new ProgressDialog(PaymentDetailActivity.this, "Loading Payment...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetPaymentURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                mProgressDialog.dismiss();
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("payment");
                            sTitle = new String[jsonArray.length()];
                            sDesc = new String[jsonArray.length()];
                            sAmount = new String[jsonArray.length()];
                            sStatus = new String[jsonArray.length()];
                            sYear = new String[jsonArray.length()];


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject paymentObject = jsonArray.getJSONObject(i);
                                PaymentNames itemDetails = new PaymentNames(paymentObject.getString("title"),
                                        paymentObject.getString("status"),
                                        paymentObject.getString("amount"),
                                        paymentObject.getString("year"),
                                        paymentObject.getString("description"));

                                arraylist.add(itemDetails);
                            }

                            mPaymentsListAdapter = new PaymentsListAdapter(getApplicationContext(), arraylist);
                            lvPayments.setAdapter(mPaymentsListAdapter);


                        } else {
                            Toast.makeText(PaymentDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Dismissing the progress dialog
                Log.e("status Response", String.valueOf(volleyError));
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_single_student_accounting");
             /* String s = new PreferencesManager(getApplicationContext()).getUserID();
              s = new PreferencesManager(getApplicationContext()).getStudentID();*/
              /*  params.put("student_id", new PreferencesManager(getApplicationContext()).getUserID());*/
                params.put("student_id", sStudID);
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
        startActivity(intent);
        finish();
    }
}
