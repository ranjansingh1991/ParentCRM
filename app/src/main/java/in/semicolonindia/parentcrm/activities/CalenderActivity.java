package in.semicolonindia.parentcrm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import in.semicolonindia.parentcrm.HomeActivity;

import in.semicolonindia.parentcrm.beans.CalenderModel;
import in.semicolonindia.parentcrm.beans.NotificationModel;
import in.semicolonindia.parentcrm.adapters.NotificationListAdapter;
import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.calendar.CalendarDay;
import in.semicolonindia.parentcrm.calendar.DayViewDecorator;
import in.semicolonindia.parentcrm.calendar.DayViewFacade;
import in.semicolonindia.parentcrm.calendar.MaterialCalendarView;
import in.semicolonindia.parentcrm.calendar.OnDateSelectedListener;
import in.semicolonindia.parentcrm.calendar.OnMonthChangedListener;
import in.semicolonindia.parentcrm.calendar.decorators.HighlightWeekendsDecorator;
import in.semicolonindia.parentcrm.calendar.decorators.MySelectorDecorator;
import in.semicolonindia.parentcrm.calendar.decorators.OneDayDecorator;
import in.semicolonindia.parentcrm.calendar.spans.DotSpan;


@SuppressWarnings("ALL")
public class CalenderActivity extends AppCompatActivity  {

    //MaterialCalendarView mCV_teacher_activity;
    TextView vT_selected_date;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    ProgressDialog progressDialog;
    RecyclerView rvCalender;
    //CalenderRecyclerAdapter mCalenderListAdapter;
    ArrayList<CalenderModel> arraylist = new ArrayList<CalenderModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_new);

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("");
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        final TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        tvToolbarTitle.setTypeface(appFontBold);
        tvToolbarTitle.setText(getString(R.string.calender_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalenderActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        rvCalender = (RecyclerView) findViewById(R.id.rvCalender);

       /* mCalenderListAdapter = new CalenderRecyclerAdapter(getApplicationContext(), arraylist,rvCalender);
       rvCalender.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvCalender.setAdapter(mCalenderListAdapter);*/


//        mCV_teacher_activity = (MaterialCalendarView) findViewById(R.id.mCV_teacher_activity);
//        vT_selected_date = (TextView) findViewById(R.id.vT_selected_date);
//
//        //lvCalendarLists = (ListView)findViewById(R.id.lvCalendarLists);
//

//        final Bundle bundle = this.getIntent().getExtras();
//        for (int i = 0; i < bundle.getStringArray("title").length; i++) {
//            CalenderModel mNoticeNames = new CalenderModel(
//                    bundle.getStringArray("title")[i],
//                    bundle.getStringArray("from_date")[i],
//                    bundle.getStringArray("to_date")[i],
//                    bundle.getStringArray("description")[i]);
//            arraylist.add(mNoticeNames);
//        }
//
//        mNotificationListAdapter = new CalenderRecyclerAdapter(getApplicationContext(), arraylist,rvCalender);
//        //lvCalendarLists.setAdapter(mNotificationListAdapter);
//        rvCalender.setAdapter(mNotificationListAdapter);
//
//        mCV_teacher_activity.setOnDateChangedListener(this);
//        mCV_teacher_activity.setOnMonthChangedListener(this);
//
//        mCV_teacher_activity.addDecorator(oneDayDecorator);
//
//        mCV_teacher_activity.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
//
//        // mCV_teacher_activity.setDateSelected(CalendarDay.from(2017, 6, 29), true);
//        // mCV_teacher_activity.setDateSelected(CalendarDay.from(2017, 7, 2), true);
//
//        mCV_teacher_activity.addDecorator(new EventDecorator());
//
//        mCV_teacher_activity.addDecorator(new DayViewDecorator() {
//            @Override
//            public boolean shouldDecorate(CalendarDay day) {
//                return day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
//            }
//
//            @Override
//            public void decorate(DayViewFacade view) {
//                view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.header_back)));
//                view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.header_back)));
//            }
//        });
//
//        vT_selected_date.setText(getSelectedDatesString());
//
//        mCV_teacher_activity.addDecorators(
//                new MySelectorDecorator(this),
//                new HighlightWeekendsDecorator(),
//                oneDayDecorator
//        );

    }

//    @Override
//    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//        vT_selected_date.setText(getSelectedDatesString());
//        //If you change a decorate, you need to invalidate decorators
//        oneDayDecorator.setDate(date.getDate());
//        widget.invalidateDecorators();
//    }
//
//    @Override
//    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
//        // noinspection ConstantConditions
//        // getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
//    }
//
//    private String getSelectedDatesString() {
//        CalendarDay date = mCV_teacher_activity.getSelectedDate();
//        if (date == null) {
//            return "No Selection";
//        }
//        return FORMATTER.format(date.getDate());
//    }
//
//    private class EventDecorator implements DayViewDecorator {
//
//        private final CalendarDay today;
//        private final Drawable backgroundDrawable;
//        private final int color;
//
//        private EventDecorator() {
//            today = CalendarDay.today();
//            backgroundDrawable = getResources().getDrawable(R.drawable.today_circle_background);
//            color = getResources().getColor(R.color.colorAccent);
//        }
//
//        @Override
//        public boolean shouldDecorate(CalendarDay day) {
//            return today.equals(day);
//        }
//
//        @Override
//        public void decorate(DayViewFacade view) {
//            view.setBackgroundDrawable(backgroundDrawable);
//            view.addSpan(new DotSpan(1, color));
//        }
//    }


}
