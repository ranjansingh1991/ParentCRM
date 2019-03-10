/*
package in.semicolonindia.parentcrm.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.beans.CalenderModel;
import in.semicolonindia.parentcrm.calendar.CalendarDay;
import in.semicolonindia.parentcrm.calendar.DayViewDecorator;
import in.semicolonindia.parentcrm.calendar.DayViewFacade;
import in.semicolonindia.parentcrm.calendar.MaterialCalendarView;
import in.semicolonindia.parentcrm.calendar.decorators.HighlightWeekendsDecorator;
import in.semicolonindia.parentcrm.calendar.decorators.MySelectorDecorator;
import in.semicolonindia.parentcrm.calendar.decorators.OneDayDecorator;
import in.semicolonindia.parentcrm.calendar.spans.DotSpan;

*/
/**
 * Created by Rupesh on 26-02-2018.
 *//*

@SuppressWarnings("ALL")
public class CalenderRecyclerAdapter extends RecyclerView.Adapter<CalenderRecyclerAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    private List<CalenderModel> acalenderNamesList = null;
    private ArrayList<CalenderModel> arrayList;

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    ProgressDialog progressDialog;


    public CalenderRecyclerAdapter(Context context, List<CalenderModel>
            acalenderNamesList, RecyclerView rv_Attendence) {
        this.context = context;
        this.acalenderNamesList = acalenderNamesList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<CalenderModel>();
        this.arrayList.addAll(acalenderNamesList);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView;
        CalenderRecyclerAdapter.ViewHolder mViewHolder = null;
        if (viewType == 0) {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calender_header, parent, false);
            mViewHolder = new CalenderRecyclerAdapter.ViewHolder(rootView, viewType);
        } else {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holiday_list_item, parent, false);
            mViewHolder = new CalenderRecyclerAdapter.ViewHolder(rootView, viewType);
        }
        return mViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(CalenderRecyclerAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            holder.mCV_teacher_activity.setOnDateChangedListener(context);
            holder.mCV_teacher_activity.setOnMonthChangedListener(context);
            holder.mCV_teacher_activity.addDecorator(oneDayDecorator);
            holder.mCV_teacher_activity.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
            holder.mCV_teacher_activity.addDecorator(new EventDecorator());
            holder.mCV_teacher_activity.addDecorator(new DayViewDecorator() {

                @Override
                public boolean shouldDecorate(CalendarDay day) {
                    return day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
                }

                @Override
                public void decorate(DayViewFacade view) {
                    view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.header_back)));
                    view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.header_back)));
                }
            });

            holder.mCV_teacher_activity.addDecorators(
                    new MySelectorDecorator(context),
                    new HighlightWeekendsDecorator(),
                    oneDayDecorator
            );
        }
    }

    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        hvT_selected_date.setText(getSelectedDatesString());
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        // noinspection ConstantConditions
        // getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }

    private String getSelectedDatesString() {
        CalendarDay date = mCV_teacher_activity.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }

    private class EventDecorator implements DayViewDecorator {

        private final CalendarDay today;
        private final Drawable backgroundDrawable;
        private final int color;

        private EventDecorator() {
            today = CalendarDay.today();
            backgroundDrawable = getResources().getDrawable(R.drawable.today_circle_background);
            color = getResources().getColor(R.color.colorAccent);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return today.equals(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(backgroundDrawable);
            view.addSpan(new DotSpan(1, color));

        }else{
            holder.tvHolidayTitle.setText(acalenderNamesList.get(position).getTitle());
            holder.tvFromDate.setText(acalenderNamesList.get(position).getFromDate());
            holder.tvToDate.setText(acalenderNamesList.get(position).getToDate());
            holder.tvDesp.setText(acalenderNamesList.get(position).getDescription());

        }

    }




    @Override
    public int getItemCount() {
        return acalenderNamesList.size() + 1;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHolidayTitle;
        TextView tvFromDate;
        TextView tvToDate;
        TextView tvDesp;
        MaterialCalendarView mCV_teacher_activity;

        public ViewHolder(View itemView, int nPosition) {
            super(itemView);
            if (nPosition == 0) {
                mCV_teacher_activity = (MaterialCalendarView) itemView.findViewById(R.id.mCV_teacher_activity);
            } else {
                TextView tvHolidayTitle = (TextView) itemView.findViewById(R.id.tvHolidayTitle);
                TextView tvFromDate = (TextView) itemView.findViewById(R.id.tvFromDate);
                TextView tvToDate = (TextView) itemView.findViewById(R.id.tvToDate);
                TextView tvDesp = (TextView) itemView.findViewById(R.id.tvDesp);
            }
        }
    }
}
*/
