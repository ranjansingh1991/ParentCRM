package in.semicolonindia.parentcrm.fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
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
import in.semicolonindia.parentcrm.adapters.BookListListAdapter;
import in.semicolonindia.parentcrm.beans.BookListNames;
import in.semicolonindia.parentcrm.dialogs.ProgressDialog;

import static in.semicolonindia.parentcrm.rest.BaseUrl.sGetBookListURL;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class BookListFragment extends Fragment {

    private ArrayList<BookListNames> arraylist = new ArrayList<BookListNames>();
    private BookListListAdapter mBookListListAdapter;
    private ListView lvBookList;
    String[] name;
    String[] status;
    String[] author;
    String[] price;
    String[] sClassName;
    String[] description;

    ProgressDialog mProgressDialog;
    private String sClassID;
    private String sStudID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_book_list, container, false);

        lvBookList = (ListView) rootView.findViewById(R.id.lvBookList);

        final EditText etSearch = (EditText) rootView.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                if (mBookListListAdapter != null) {
                    mBookListListAdapter.filter(text);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        // these fragment get class_id and student_id whatever Adapter to be send...
        sClassID = getArguments().getString("class_id");
        sStudID = getArguments().getString("student_id");

        // Calling Methods.........
        getBookList();

        return rootView;
    }

    private void getBookList() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Book Library...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetBookListURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("book_list");
                            name = new String[jsonArray.length()];
                            status = new String[jsonArray.length()];
                            author = new String[jsonArray.length()];
                            price = new String[jsonArray.length()];
                            description = new String[jsonArray.length()];
                            sClassName = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject bookObject = jsonArray.getJSONObject(i);
                                BookListNames itemDetails = new BookListNames(bookObject.getString("name"),
                                        bookObject.getString("author"),
                                        bookObject.getString("price"),
                                        getRoman(bookObject.getString("class_id")),
                                        bookObject.getString("description"),
                                        bookObject.getString("book_id"),
                                        sStudID);
                                arraylist.add(itemDetails);
                            }
                            mBookListListAdapter = new BookListListAdapter(getActivity(), arraylist);
                            lvBookList.setAdapter(mBookListListAdapter);
                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                        //overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                    }
                } else {
                    Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
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
                params.put("tag", "get_book_list");
                params.put("class_id", sClassID);
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private String getRoman(String value) {
        switch (value) {
            case "1":
                value = "I";
                break;
            case "2":
                value = "II";
                break;
            case "3":
                value = "III";
                break;
            case "4":
                value = "IV";
                break;
            case "5":
                value = "V";
                break;
            case "6":
                value = "VI";
                break;
            case "7":
                value = "VII";
                break;
            case "8":
                value = "VIII";
                break;
            case "9":
                value = "IX";
                break;
            case "10":
                value = "X";
                break;
            case "11":
                value = "XI";
                break;
            case "12":
                value = "XII";
                break;
        }
        return value;
    }
}