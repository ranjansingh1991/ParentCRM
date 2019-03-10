package in.semicolonindia.parentcrm.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import in.semicolonindia.parentcrm.adapters.TransportListAdapter;
import in.semicolonindia.parentcrm.beans.TransportNames;

import static in.semicolonindia.parentcrm.rest.BaseUrl.sGetTransportURL;
@SuppressWarnings("ALL")
public class TransportNextActivity extends AppCompatActivity {

    ListView lvTransport;
    TransportListAdapter mTransportListAdapter;
    ArrayList<TransportNames> arraylist = new ArrayList<TransportNames>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_next);

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
        Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        tvToolbarTitle.setTypeface(appFontBold);

        //...Here Name display in title....
        tvToolbarTitle.setText(mBundle.getString("name"));

        //tvToolbarTitle.setText(getString(R.string.transport_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportNextActivity.this, TransportActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        lvTransport = (ListView) findViewById(R.id.lvTransport);
        // Calling getTransport Methods.......
        getTransports();

        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mTransportListAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
    }

    private void getTransports() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetTransportURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("transport");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject transportObject = jsonArray.getJSONObject(i);
                                TransportNames transportNames = new TransportNames(transportObject.getString("transport_id"),
                                        transportObject.getString("route_name"),
                                        transportObject.getString("number_of_vehicle"),
                                        transportObject.getString("description"),
                                        transportObject.getString("route_fare"));

                                arraylist.add(transportNames);
                            }
                            mTransportListAdapter = new TransportListAdapter(getApplicationContext(), arraylist, lvTransport);
                            lvTransport.setAdapter(mTransportListAdapter);

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e)

                    {
                        e.printStackTrace();
                    }
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Status Response", String.valueOf(error));

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_transports");
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent(getApplicationContext(),TransportActivity.class);
        startActivity(intent);
        finish();
    }
}
