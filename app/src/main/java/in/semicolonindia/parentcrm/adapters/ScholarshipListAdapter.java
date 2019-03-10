package in.semicolonindia.parentcrm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.beans.ScholarNames;

/**
 * Created by MPAYAL-PC on 11/29/2017.
 */
@SuppressWarnings("all")

public class ScholarshipListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<ScholarNames> scholarNamesList = null;
    private ArrayList<ScholarNames> arraylist;

    public ScholarshipListAdapter(Context context, List<ScholarNames> scholarNamesList) {
        this.context = context;
        this.scholarNamesList = scholarNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<ScholarNames>();
        this.arraylist.addAll(scholarNamesList);
    }

    @Override
    public int getCount() {
        return scholarNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return scholarNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.scholarship_list_item, null);

            holder.tvDate = (TextView) convertView.findViewById(R.id.tvScholarDate);
            holder.tvClass = (TextView) convertView.findViewById(R.id.tvSClass);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDesp);

            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvDate.setText(scholarNamesList.get(position).getsDate());
        holder.tvClass.setText(scholarNamesList.get(position).getsClass());
        holder.tvDescription.setText(scholarNamesList.get(position).getsDesp());


        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        scholarNamesList.clear();
        if (charText.length() == 0) {
            scholarNamesList.addAll(arraylist);
        } else {
            for (ScholarNames wp : arraylist) {
                if (wp.getsClass().toLowerCase(Locale.getDefault()).contains(charText)) {
                    scholarNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        TextView tvDate;
        TextView tvClass;
        TextView tvDescription;
    }
}
