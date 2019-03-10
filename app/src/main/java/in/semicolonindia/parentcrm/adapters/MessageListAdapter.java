package in.semicolonindia.parentcrm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.beans.TeacherNames;
import in.semicolonindia.parentcrm.civ.CircleImageView;

/**
 * Created by RANJAN SINGH on 11/20/2017.
 */
@SuppressWarnings("ALL")
public class MessageListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<TeacherNames> teacherNamesList = null;
    private ArrayList<TeacherNames> arraylist;

    public MessageListAdapter(Context context, List<TeacherNames> teacherNamesList) {
        this.context = context;
        this.teacherNamesList = teacherNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<TeacherNames>();
        this.arraylist.addAll(teacherNamesList);
    }

    @Override
    public int getCount() {
        return teacherNamesList.size();
    }

    @Override
    public TeacherNames getItem(int position) {
        return teacherNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final MessageListAdapter.ViewHolder holder;
        if (view == null) {
            holder = new MessageListAdapter.ViewHolder();
            view = inflater.inflate(R.layout.message_list_item, null);
            holder.tvItemHeading = (TextView) view.findViewById(R.id.tvItemHeading);
            holder.tvItemText = (TextView) view.findViewById(R.id.tvItemText);
            holder.tvMsgCount = (TextView) view.findViewById(R.id.tvMsgCount);
            holder.imgComponent = (CircleImageView) view.findViewById(R.id.imgComponent);
            view.setTag(holder);
        } else {
            holder = (MessageListAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvItemHeading.setText(teacherNamesList.get(position).getName());
        holder.tvItemText.setText(teacherNamesList.get(position).getEmail());
        holder.tvItemHeading.setTypeface(appFontRegular);
        holder.tvItemText.setTypeface(appFontLight);
        Picasso.with(context).load(teacherNamesList.get(position).getImgURL()).into(holder.imgComponent);
        if (teacherNamesList.get(position).getMsgCount() > 0) {
            holder.tvMsgCount.setVisibility(View.VISIBLE);
            holder.tvMsgCount.setText(String.valueOf(teacherNamesList.get(position).getMsgCount()));
        }
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        teacherNamesList.clear();
        if (charText.length() == 0) {
            teacherNamesList.addAll(arraylist);
        } else {
            for (TeacherNames wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacherNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvItemHeading;
        TextView tvItemText;
        TextView tvMsgCount;
        CircleImageView imgComponent;
    }
}
