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
import in.semicolonindia.parentcrm.activities.NoticeboardNextActivity;
import in.semicolonindia.parentcrm.activities.TeacherNextActivity;
import in.semicolonindia.parentcrm.beans.StudentNames;

/**
 * Created by Rupesh on 12-08-2017.
 */
@SuppressWarnings("ALL")
public class NoticeboardStudentAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<StudentNames> studentNamesList = null;
    private ArrayList<StudentNames> arraylist;
    private ListView lvStudentName;

    public NoticeboardStudentAdapter(Context context, List<StudentNames> studentNamesList,  ListView lvStudentName) {
        this.context = context;
        this.studentNamesList = studentNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<StudentNames>();
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
        final NoticeboardStudentAdapter.ViewHolder holder;
        if (view == null) {
            holder = new NoticeboardStudentAdapter.ViewHolder();
            view = inflater.inflate(R.layout.student_list_item, null);
            holder.tvStudentNameHeader = (TextView) view.findViewById(R.id.tvStudentNameHeader);
            holder.tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            view.setTag(holder);
        } else {
            holder = (NoticeboardStudentAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvStudentNameHeader.setText(studentNamesList.get(position).getName());
        holder.tvEmail.setText(studentNamesList.get(position).geteMail());
        holder.tvStudentNameHeader.setTypeface(appFontRegular);
        holder.tvEmail.setTypeface(appFontLight);

       /* lvStudentName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(context, NoticeboardNextActivity.class);
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