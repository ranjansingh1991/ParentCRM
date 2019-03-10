package in.semicolonindia.parentcrm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.beans.NotificationModel;

/**
 * Created by Rupesh on 13-10-2017.
 */

@SuppressWarnings("ALL")
public class NotificationListAdapter extends BaseAdapter {

    private Context context;
    private List<NotificationModel> noticeNamesList = null;

    public NotificationListAdapter(Context context, List<NotificationModel> noticeNamesList) {
        this.context = context;
        this.noticeNamesList = noticeNamesList;
    }

    @Override
    public int getCount() {
        return noticeNamesList.size();
    }

    @Override
    public NotificationModel getItem(int position) {
        return noticeNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.holiday_list_item, null);
        TextView tvHolidayTitle = (TextView) convertView.findViewById(R.id.tvHolidayTitle);
        TextView tvFromDate = (TextView) convertView.findViewById(R.id.tvFromDate);
        TextView tvToDate = (TextView) convertView.findViewById(R.id.tvToDate);
        TextView tvDesp = (TextView) convertView.findViewById(R.id.tvDesp);
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");

        tvHolidayTitle.setText(noticeNamesList.get(position).getTitle());
        tvFromDate.setText(noticeNamesList.get(position).getFromDate());
        tvToDate.setText(noticeNamesList.get(position).getToDate());
        tvDesp.setText(noticeNamesList.get(position).getDescription());

        tvHolidayTitle.setTypeface(appFontRegular);
        tvFromDate.setTypeface(appFontLight);
        tvToDate.setTypeface(appFontLight);
        tvDesp.setTypeface(appFontLight);

        return convertView;
    }
}
