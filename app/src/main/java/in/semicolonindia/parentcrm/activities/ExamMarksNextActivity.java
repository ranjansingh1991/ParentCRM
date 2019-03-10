package in.semicolonindia.parentcrm.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import in.semicolonindia.parentcrm.HomeActivity;
import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.adapters.ExamMarksListAdapter;
import in.semicolonindia.parentcrm.beans.ExamNames;
import in.semicolonindia.parentcrm.dialogs.ProgressDialog;

import static in.semicolonindia.parentcrm.rest.BaseUrl.sGetMarksURL;

@SuppressWarnings("ALL")
public class ExamMarksNextActivity extends AppCompatActivity {

    RecyclerView rvExamMarks;
    ExamMarksListAdapter mExamMarkListAdapter;
    ArrayList<ExamNames> arrayList = new ArrayList<>();
    private  String[] sMarksObtained;
    private String[] sMaxMarks;
    private String[] sSubject;
    private  String[] sGrade;
    private String[] sRemarks;
    private String sExamID;
    private String sStudID;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_marks_next);

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
        tvToolbarTitle.setText(getString(R.string.exam_marks_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExamMarksActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        sStudID = getIntent().getExtras().getString("student_id");
        sExamID = getIntent().getExtras().getString("exam_id");


        rvExamMarks = (RecyclerView) findViewById(R.id.rvExamMarks);
        rvExamMarks.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getMarksDetails();
    }

    private void getMarksDetails() {
        mProgressDialog = new ProgressDialog(ExamMarksNextActivity.this, "Loading Exam Marks...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetMarksURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    JSONObject jsonObject = null;

                    try {
                        jsonObject = new JSONObject(sResult);
                        JSONArray jsonArray = jsonObject.getJSONArray("marks");
                        sSubject = new String[jsonArray.length()];
                        sMarksObtained = new String[jsonArray.length()];
                        sMaxMarks = new String[jsonArray.length()];
                        sGrade = new String[jsonArray.length()];
                        sRemarks = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject marksObject = jsonArray.getJSONObject(i);
                            ExamNames itemDetails = new ExamNames(marksObject.getString("mark_obtained"),
                                    marksObject.getString("mark_total"),
                                    marksObject.getString("subject"),
                                    marksObject.getString("grade"),
                                    marksObject.getString("remark"));
                            arrayList.add(itemDetails);

                        }

                        mExamMarkListAdapter = new ExamMarksListAdapter(getApplicationContext(), arrayList, rvExamMarks);
                        rvExamMarks.setAdapter(mExamMarkListAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(ExamMarksNextActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }

                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Dismissing the progress dialog
                Log.e("status Response", String.valueOf(volleyError));
                mProgressDialog.dismiss();
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_student_mark_information");
                params.put("exam_id", sExamID);
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

        Intent intent=new Intent(ExamMarksNextActivity.this,ExamMarksActivity.class);
        startActivity(intent);
        finish();
    }
}


