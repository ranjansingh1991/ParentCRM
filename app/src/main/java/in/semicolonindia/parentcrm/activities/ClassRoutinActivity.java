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
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.semicolonindia.parentcrm.HomeActivity;
import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.adapters.AssignmentAdapter;
import in.semicolonindia.parentcrm.beans.StudentNames;
import in.semicolonindia.parentcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.parentcrm.dialogs.ProgressDialog;
import in.semicolonindia.parentcrm.util.ConnectionDetector;
import in.semicolonindia.parentcrm.util.PreferencesManager;

import static in.semicolonindia.parentcrm.rest.BaseUrl.sLoginURL;
@SuppressWarnings("ALL")
public class ClassRoutinActivity extends AppCompatActivity {

    ListView lvStudentEML;
    AssignmentAdapter mStudentListAdapter;
    ArrayList<StudentNames> arraylist = new ArrayList<StudentNames>();
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_routine);

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("");
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        final TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        final Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        tvToolbarTitle.setTypeface(appFontBold);
        tvToolbarTitle.setText(getString(R.string.class_routine_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassRoutinActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        lvStudentEML = (ListView)findViewById(R.id.lvStudentEML);
        getChildrenList();

        // Search box
        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mStudentListAdapter.filter(text);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        lvStudentEML.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // 1. Here we create a method to check Internet avalale or not.
                if (new ConnectionDetector(getApplicationContext()).isConnectingToInternet()) {

                    Bundle bundle = new Bundle();
                    StudentNames studentNames = (StudentNames) lvStudentEML.getItemAtPosition(i);
                    bundle.putString("name", studentNames.getName());
                    bundle.putString("email", studentNames.geteMail());
                    bundle.putString("class_id", studentNames.getClassID());
                    bundle.putString("section_id", studentNames.getSectionID());
                    Intent intent = new Intent(getApplicationContext(), ClassRoutingNextActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                }else {
                    final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(ClassRoutinActivity.this,
                            R.style.DialogSlideAnim);
                    mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mNoConnectionDialog.setCancelable(false);
                    mNoConnectionDialog.setCanceledOnTouchOutside(false);
                    mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mNoConnectionDialog.show();
                }
            }
        });
    }

    public void getChildrenList() {
        mProgressDialog = new ProgressDialog(ClassRoutinActivity.this, "Loading Routing ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sLoginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    mProgressDialog.dismiss();
                    try {
                        Log.d("response", response);
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray childArray = jsonObject.getJSONArray("children");
                        for (int i = 0; i < childArray.length(); i++) {
                            JSONObject childObject = childArray.getJSONObject(i);
                            StudentNames itemDetails = new StudentNames(childObject.getString("name"),
                                    childObject.getString("email"),
                                    childObject.getString("class_id"), childObject.getString("section_id"));
                            arraylist.add(itemDetails);
                        }
                        mStudentListAdapter = new AssignmentAdapter(getApplicationContext(), arraylist, lvStudentEML);
                        lvStudentEML.setAdapter(mStudentListAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ClassRoutinActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(ClassRoutinActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
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
                params.put("email", new PreferencesManager(getApplicationContext()).getEmail());
                params.put("password", new PreferencesManager(getApplicationContext()).getPass());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent(ClassRoutinActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
