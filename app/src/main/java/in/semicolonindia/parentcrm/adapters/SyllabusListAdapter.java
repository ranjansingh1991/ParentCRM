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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.beans.SyllabusNames;
import in.semicolonindia.parentcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.parentcrm.util.ConnectionDetector;
import in.semicolonindia.parentcrm.util.DownloadTaskAcademicSyllabus;

/**
 * Created by Rupesh on 06-08-2017.
 */
@SuppressWarnings("ALL")
public class SyllabusListAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    private List<SyllabusNames> syllabusNamesList = null;
    private ArrayList<SyllabusNames> arraylist;
    ListView lvSyllabus;

    public SyllabusListAdapter( Activity activity, List<SyllabusNames> syllabusNamesList) {
        this.activity = activity;
        this.syllabusNamesList = syllabusNamesList;
        inflater = LayoutInflater.from(activity);
        this.arraylist = new ArrayList<SyllabusNames>();
        this.lvSyllabus = lvSyllabus;
        this.arraylist.addAll(syllabusNamesList);
    }

    @Override
    public int getCount() {
        return syllabusNamesList.size();
    }

    @Override
    public SyllabusNames getItem(int position) {
        return syllabusNamesList.get(position);
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
            convertView = inflater.inflate(R.layout.syllabus_list_item, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.btnDownload = (Button) convertView.findViewById(R.id.btnDownload);
            holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
            //holder.tvUploader = (TextView) convertView.findViewById(R.id.tvUploader);
            holder.tvFile = (TextView) convertView.findViewById(R.id.tvFile);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvDesp = (TextView) convertView.findViewById(R.id.tvDesp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvTitle.setText(syllabusNamesList.get(position).getTitle());
        holder.tvSubject.setText(syllabusNamesList.get(position).getSubject());
        //holder.tvUploader.setText(syllabusNamesList.get(position).getUploader());
        holder.tvDate.setText(syllabusNamesList.get(position).getDate());
        holder.tvFile.setText(syllabusNamesList.get(position).getFile());
        holder.tvDesp.setText(syllabusNamesList.get(position).getDesp());
        holder.tvTitle.setTypeface(appFontRegular);
        holder.btnDownload.setTypeface(appFontLight);
        holder.tvSubject.setTypeface(appFontLight);
        //holder.tvUploader.setTypeface(appFontLight);
        holder.tvDate.setTypeface(appFontLight);
        holder.tvFile.setTypeface(appFontLight);
        holder.tvDesp.setTypeface(appFontLight);

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (new ConnectionDetector(activity).isConnectingToInternet()){
                new DownloadTaskAcademicSyllabus(activity, syllabusNamesList.get(position).getFile())
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        syllabusNamesList.clear();
        if (charText.length() == 0) {
            syllabusNamesList.addAll(arraylist);
        } else {
            for (SyllabusNames wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    syllabusNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvTitle;
        Button btnDownload;
        TextView tvSubject;
        //TextView tvUploader;
        TextView tvDate;
        TextView tvFile;
        TextView tvDesp;
    }
}