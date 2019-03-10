package in.semicolonindia.parentcrm.fragments;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.activities.BenchActivity;
import in.semicolonindia.parentcrm.activities.ExtraCurriculumActivity;
import in.semicolonindia.parentcrm.activities.FeedbackActivity;
import in.semicolonindia.parentcrm.activities.GalleryActivity;
import in.semicolonindia.parentcrm.activities.LeaderBoardActivity;
import in.semicolonindia.parentcrm.activities.NewsActivity;
import in.semicolonindia.parentcrm.activities.ScholarshipActivity;
import in.semicolonindia.parentcrm.activities.SupportActivity;
import in.semicolonindia.parentcrm.adapters.HomeOptionAdapter;
import in.semicolonindia.parentcrm.beans.FirstName;
import in.semicolonindia.parentcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.parentcrm.util.ConnectionDetector;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class HomeOptionFragment extends Fragment {
    private in.semicolonindia.parentcrm.dialogs.ProgressDialog mProgressDialog;
    private ArrayList<FirstName> arraylist = new ArrayList<FirstName>();
    private HomeOptionAdapter homeOptionAdapter;
    ListView lv_Second;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_option, container, false);
        lv_Second = rootView.findViewById(R.id.lv_Second);

        HomeOptionAdapter homeOptionAdapter = new HomeOptionAdapter(getContext(), getResources().getStringArray(R.array.home_option_item_fragment));
        lv_Second.setAdapter(homeOptionAdapter);

        lv_Second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    switch (position) {
                        case 0:
                            getActivity().startActivity(new Intent(getActivity(), GalleryActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 1:
                            getActivity().startActivity(new Intent(getActivity(), NewsActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 2:
                            getActivity().startActivity(new Intent(getActivity(), FeedbackActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 3:
                            getActivity().startActivity(new Intent(getActivity(), LeaderBoardActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;

                        case 4:
                            getActivity().startActivity(new Intent(getActivity(), BenchActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 5:
                            getActivity().startActivity(new Intent(getActivity(), ScholarshipActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 6:
                            getActivity().startActivity(new Intent(getActivity(), ExtraCurriculumActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 7:
                            getActivity().startActivity(new Intent(getActivity(), SupportActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;

                    }

                } else {
                    final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(getActivity(),
                            R.style.DialogSlideAnim);
                    mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mNoConnectionDialog.setCancelable(false);
                    mNoConnectionDialog.setCanceledOnTouchOutside(false);
                    mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mNoConnectionDialog.show();
                }
            }
        });


        return rootView;
    }
}