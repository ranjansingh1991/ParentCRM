package in.semicolonindia.parentcrm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.beans.SubjectNames;

/**
 * Created by Rupesh on 06-08-2017.
 */
@SuppressWarnings("ALL")
public class SubjectListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<SubjectNames> subjectNamesList = null;
    private ArrayList<SubjectNames> arraylist;
    //private ListView lvStudentName;

    public SubjectListAdapter(Context context, List<SubjectNames> subjectNamesList, ListView lvStudentName) {
        this.context = context;
        this.subjectNamesList = subjectNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<SubjectNames>();
        this.arraylist.addAll(subjectNamesList);
        //this.lvStudentName = lvStudentName;
    }
    @Override
    public int getCount() {
        return subjectNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final SubjectListAdapter.ViewHolder holder;
        if (view == null) {
            holder = new SubjectListAdapter.ViewHolder();
            view = inflater.inflate(R.layout.subject_list_item, null);
            holder.tv_Subject_Name = (TextView) view.findViewById(R.id.tv_Subject_Name);
            holder.tvYear = (TextView) view.findViewById(R.id.tv_Year);
            view.setTag(holder);
        } else {
            holder = (SubjectListAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tv_Subject_Name.setText(subjectNamesList.get(position).getName());
        holder.tvYear.setText(subjectNamesList.get(position).getYear());
        holder.tv_Subject_Name.setTypeface(appFontRegular);
        holder.tvYear.setTypeface(appFontLight);
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                switch (position) {
                    case 0:
                        //context.startActivity(new Intent(context, MessageNextActivity.class));
                        break;
                    case 1:
                       // context.startActivity(new Intent(context, MessageNextActivity.class));
                        break;
                    case 2:
                       // context.startActivity(new Intent(context, MessageNextActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });*/

        return view;
    }
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        subjectNamesList.clear();
        if (charText.length() == 0)

        {
            subjectNamesList.addAll(arraylist);
        }
        else
            {
            for (SubjectNames wp : arraylist)
            {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    subjectNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder
    {
        TextView tv_Subject_Name;
        TextView tvYear;
    }
}

