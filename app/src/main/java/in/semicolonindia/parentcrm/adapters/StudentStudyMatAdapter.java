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
import java.util.Locale;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.activities.StudyMatNextActivity;
import in.semicolonindia.parentcrm.beans.StudentNames;

/**
 * Created by Rupesh on 28-09-2017.
 */
@SuppressWarnings("ALL")
public class StudentStudyMatAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private ArrayList<StudentNames> studentNamesList = null;
    private ArrayList<StudentNames> arraylist;
    private ListView lvStudentName;


    public StudentStudyMatAdapter(Context context, ArrayList<StudentNames> studentNamesList, ListView lvStudentName) {

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
    public Object getItem(int i) {
        return studentNamesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final StudentStudyMatAdapter.ViewHolder holder;
        if (view == null) {
            holder = new StudentStudyMatAdapter.ViewHolder();
            view = inflater.inflate(R.layout.student_list_item, null);
            holder.tvStudentNameHeader = view.findViewById(R.id.tvStudentNameHeader);
            holder.tvEmail = view.findViewById(R.id.tvEmail);
            view.setTag(holder);
        } else {
            holder = (StudentStudyMatAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvStudentNameHeader.setText(studentNamesList.get(i).getName());
        holder.tvEmail.setText(studentNamesList.get(i).geteMail());
        holder.tvStudentNameHeader.setTypeface(appFontRegular);
        holder.tvEmail.setTypeface(appFontLight);

/*
        lvStudentName.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, StudyMatNextActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/


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
