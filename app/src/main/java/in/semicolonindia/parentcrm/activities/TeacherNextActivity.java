package in.semicolonindia.parentcrm.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.semicolonindia.parentcrm.HomeActivity;
import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.adapters.BookListListAdapter;
import in.semicolonindia.parentcrm.adapters.TeacherListAdapter;
import in.semicolonindia.parentcrm.beans.BookListNames;
import in.semicolonindia.parentcrm.beans.StudentNames;
import in.semicolonindia.parentcrm.beans.TeacherNames;
import in.semicolonindia.parentcrm.dialogs.ProgressDialog;
import in.semicolonindia.parentcrm.util.PreferencesManager;

import static in.semicolonindia.parentcrm.rest.BaseUrl.sGetTeacherMsg;
import static in.semicolonindia.parentcrm.rest.BaseUrl.sGetTeacherOfClass;
import static in.semicolonindia.parentcrm.rest.BaseUrl.sGetTeacherURL;
@SuppressWarnings("ALL")
public class TeacherNextActivity extends AppCompatActivity {
    private ListView lvOldMsg;
    private TeacherListAdapter mMessageListAdapter;
    private ArrayList<TeacherNames> arraylist = new ArrayList<TeacherNames>();
    private String[] sNames;
    private String[] sEmails;
    private String[] sIDs;
    private String[] sImgURL;
    ProgressDialog mProgressDialog;
    private String sClassID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_next);

        // This code apply to get the data. whatever previous activity to send data.
        Bundle mBundle = this.getIntent().getExtras();

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

        //...Here Name display in title....
        tvToolbarTitle.setText(mBundle.getString("name"));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(TeacherNextActivity.this, TeachersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);*/
                finish();
            }
        });

        sClassID = mBundle.getString("class_id");
        final ImageView imgCreateMsg = (ImageView) findViewById(R.id.imgCreateMsg);

        lvOldMsg = (ListView) findViewById(R.id.lvOldMsg);
        getMessages();
        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        //imgCreateMsg.setOnClickListener(this);

        lvOldMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle mBundle = new Bundle();
                mBundle.putString("sTeachersName", sNames[i]);
                mBundle.putString("sEmail", sEmails[i]);
                mBundle.putString("sID", sIDs[i]);
                mBundle.putString("sImgURL", sImgURL[i]);
                Intent mIntent = new Intent(getApplicationContext(), ChatActivity.class);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

               /* Bundle mBundle = new Bundle();
                TeacherNames teacherNames = (TeacherNames) lvOldMsg.getItemAtPosition(i);
                mBundle.putString("name", teacherNames.getName());
                mBundle.putString("email", teacherNames.getEmail());
                mBundle.putString("image_url", teacherNames.getImgURL());
                mBundle.putString("teacher_id", teacherNames.getID());
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtras(mBundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);*/
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getMessages() {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherNextActivity.this, "Loading Messages...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetTeacherOfClass, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (jsonObject.getJSONArray("teachers").length() > 0) {
                            JSONArray jsonArray = jsonObject.getJSONArray("teachers");
                            sNames = new String[jsonArray.length()];
                            sEmails = new String[jsonArray.length()];
                            sIDs = new String[jsonArray.length()];
                            sImgURL = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sNames[i] = jsonArray.getJSONObject(i).getString("name");
                                sEmails[i] = jsonArray.getJSONObject(i).getString("email");
                                sIDs[i] = jsonArray.getJSONObject(i).getString("teacher_id");
                                sImgURL[i] = jsonArray.getJSONObject(i).getString("image_url");
                                int nMsgCont = 0;
                                try {
                                    nMsgCont = jsonArray.getJSONObject(i).getInt("message_count");
                                } catch(JSONException e1) {
                                    e1.printStackTrace();
                                    mProgressDialog.dismiss();
                                }
                                TeacherNames mTeacherNames = new TeacherNames(sNames[i],
                                        sEmails[i], sIDs[i],
                                        sImgURL[i], nMsgCont);
                                arraylist.add(mTeacherNames);
                            }
                            mMessageListAdapter = new TeacherListAdapter(getApplicationContext(), arraylist);
                            lvOldMsg.setAdapter(mMessageListAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
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
                params.put("tag", "get_teachers_of_class");
                //params.put("email", new PreferencesManager(getApplicationContext()).getEmail());
                params.put("class_id", sClassID);
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
