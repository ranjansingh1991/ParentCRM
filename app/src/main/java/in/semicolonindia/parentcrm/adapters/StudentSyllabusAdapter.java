package in.semicolonindia.parentcrm.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.activities.SyllabusNextActivity;
import in.semicolonindia.parentcrm.beans.StudentNames;

/**
 * Created by Rupesh on 11-08-2017.
 */

public class StudentSyllabusAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private ArrayList<StudentNames> studentNamesList = null;
    private ArrayList<StudentNames> arraylist;
    private ListView lvStudentName;


    public StudentSyllabusAdapter(Context context, ArrayList<StudentNames> studentNamesList, ListView lvStudentName) {

        this.context = context;
        this.studentNamesList = studentNamesList;
        this.arraylist = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        this.arraylist.addAll(studentNamesList);
        this.lvStudentName = lvStudentName;

    }


    @Override
    public int getCount() {
        return studentNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final StudentSyllabusAdapter.ViewHolder holder;
        if (view == null) {
            holder = new StudentSyllabusAdapter.ViewHolder();
            view = inflater.inflate(R.layout.student_list_item, null);
            holder.tvStudentNameHeader = view.findViewById(R.id.tvStudentNameHeader);
            holder.tvEmail = view.findViewById(R.id.tvEmail);
            view.setTag(holder);
        } else {
            holder = (StudentSyllabusAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvStudentNameHeader.setText(studentNamesList.get(position).getName());
        holder.tvEmail.setText(studentNamesList.get(position).geteMail());
        holder.tvStudentNameHeader.setTypeface(appFontRegular);
        holder.tvEmail.setTypeface(appFontLight);

        return view;
    }

    public class ViewHolder {
        TextView tvStudentNameHeader;
        TextView tvEmail;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        studentNamesList.clear();
        if (charText.length() == 0) {
            studentNamesList.addAll(arraylist);
        } else {
            for (StudentNames wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    studentNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}