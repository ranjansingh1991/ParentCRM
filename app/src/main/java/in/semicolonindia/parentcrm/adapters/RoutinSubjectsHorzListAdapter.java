package in.semicolonindia.parentcrm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.beans.ClassRoutine;
import in.semicolonindia.parentcrm.util.PreferencesManager;


/**
 * Created by Rupesh on 06-08-2017.
 */
@SuppressWarnings("ALL")
public class RoutinSubjectsHorzListAdapter extends RecyclerView.Adapter<RoutinSubjectsHorzListAdapter.ViewHolder> {

    private Context context;
    private char[] cDayNames;
    private int nHeight;
    private ArrayList<ArrayList<ClassRoutine>> alRoutine;
    private String[] sSubjectNames;

    public RoutinSubjectsHorzListAdapter(Context context, int nHeight, ArrayList<ArrayList<ClassRoutine>> alRoutine) {
        this.context = context;
        this.nHeight = nHeight;
        cDayNames = new char[]{' ', 'S', 'M', 'T', 'W', 'T', 'F', 'S'};
        this.alRoutine = alRoutine;
        this.sSubjectNames = new PreferencesManager(context).getSubjectNames().split(",");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_subject_list_list, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        ArrayList<String> sData = new ArrayList<>();
        sData.add(sSubjectNames[position]);
        for (int i = 0; i < alRoutine.size(); i++) {
            for (int j = 0; j < alRoutine.get(i).size(); j++) {
                if (sSubjectNames[position].equalsIgnoreCase(alRoutine.get(i).get(j).getSubjectName())) {
                    sData.add(alRoutine.get(i).get(j).getStartEndTime());
                }
            }
        }
        RoutineSubjectTimeListItems mRoutineSubjectTimeListItems = new RoutineSubjectTimeListItems(context, nHeight, sData);
        holder.lvSubjectsList.setAdapter(mRoutineSubjectTimeListItems);
    }

    @Override
    public int getItemCount() {
        return sSubjectNames.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ListView lvSubjectsList;

        public ViewHolder(View itemView) {
            super(itemView);
            lvSubjectsList = (ListView) itemView.findViewById(R.id.lvSubjectsList);
        }
    }
}
