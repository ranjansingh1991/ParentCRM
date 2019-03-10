package in.semicolonindia.parentcrm.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.parentcrm.beans.AssignmentName;
import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.parentcrm.util.ConnectionDetector;
import in.semicolonindia.parentcrm.util.DownloadTaskAssignment;

/**
 * Created by RANJAN SINGH on 10/4/2017.
 */
@SuppressWarnings("ALL")
public class AssignmentFinalAdapter extends BaseAdapter{

    Activity activity;
    LayoutInflater inflater;
    private List<AssignmentName> assignmentNamesList = null;
    private ArrayList<AssignmentName> arraylist;
    private ListView lvAssignmenttName;

    public AssignmentFinalAdapter(Activity activity, List<AssignmentName> assignmentNamesList) {
        this.activity = activity;
        this.assignmentNamesList = assignmentNamesList;
        inflater = LayoutInflater.from(activity);
        this.arraylist = new ArrayList<AssignmentName>();
        this.arraylist.addAll(assignmentNamesList);
        //this.lvAssignmenttName = lvAssignmenttName;
    }

    @Override
    public int getCount() {
        return assignmentNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return assignmentNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final AssignmentFinalAdapter.ViewHolder holder;
        if (view == null) {
            holder = new AssignmentFinalAdapter.ViewHolder();
            view = inflater.inflate(R.layout.assignment_details_list, null);
            holder.tvAssignment_Title = (TextView) view.findViewById(R.id.tvAssignmentTitle);
            holder.btnDownload = (TextView) view.findViewById(R.id.btnDownload);
            holder.tv_Question = (TextView) view.findViewById(R.id.tv_Question);
            holder.tv_Upload_Start_Date = (TextView) view.findViewById(R.id. tv_Upload_Start_Date);
            holder.tv_Submit_Start_Date = (TextView) view.findViewById(R.id.tv_Submit_Start_Date);
            holder.tv_Marks = (TextView) view.findViewById(R.id.  tv_Marks);
            holder.tv_Report_to = (TextView) view.findViewById(R.id.tv_Report_to);
            holder.tv_File_Name = (TextView) view.findViewById(R.id.tv_File_Name);
            view.setTag(holder);
        } else {
            holder = (AssignmentFinalAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvAssignment_Title.setText(assignmentNamesList.get(position).getaTitle());
        holder.tv_Question.setText(assignmentNamesList.get(position).getaQuestion());
        holder.tv_Upload_Start_Date.setText(assignmentNamesList.get(position).getaUpload_Date());
        holder.tv_Submit_Start_Date.setText(assignmentNamesList.get(position).getA_Submit_Date());
        holder.tv_Marks.setText(assignmentNamesList.get(position).getaMarks());
        holder.tv_Report_to.setText(assignmentNamesList.get(position).getReport());
        holder.tv_File_Name.setText(assignmentNamesList.get(position).getFileName());

        holder.tvAssignment_Title.setTypeface(appFontRegular);
        holder.btnDownload.setTypeface(appFontLight);
        holder.tv_Question.setTypeface(appFontLight);
        holder.tv_Upload_Start_Date.setTypeface(appFontLight);
        holder.tv_Submit_Start_Date.setTypeface(appFontLight);
        holder.tv_Marks.setTypeface(appFontLight);
        holder.tv_Report_to.setTypeface(appFontLight);
        holder.tv_File_Name.setTypeface(appFontLight);


        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 1. Here we create a method to check Internet available or not.
                if (new ConnectionDetector(activity).isConnectingToInternet()) {
                    new DownloadTaskAssignment(activity, assignmentNamesList.get(position).getFileName())
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    // 2. Here we create ConnectionDetector class to check internet connection .
                    // if connection is not available then dailog box will open
                } else {
                    final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(activity,
                            R.style.DialogSlideAnim);
                    mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mNoConnectionDialog.setCancelable(false);
                    mNoConnectionDialog.setCanceledOnTouchOutside(false);
                    mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mNoConnectionDialog.show();
                }
            }
        });

        return view;
    }

    public static class ViewHolder {
        TextView tvAssignment_Title;
        TextView btnDownload;
        TextView tv_Question;
        TextView tv_Upload_Start_Date;
        TextView tv_Submit_Start_Date;
        TextView tv_Marks;
        TextView tv_Report_to;
        TextView tv_File_Name;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        assignmentNamesList.clear();
        if (charText.length() == 0) {
            assignmentNamesList.addAll(arraylist);
        } else {
            for (AssignmentName wp : arraylist) {
                if (wp.getaTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    assignmentNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

