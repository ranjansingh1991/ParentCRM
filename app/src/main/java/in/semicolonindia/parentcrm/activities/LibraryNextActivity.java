package in.semicolonindia.parentcrm.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.semicolonindia.parentcrm.HomeActivity;
import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.adapters.LibraryVPAdapter;
import in.semicolonindia.parentcrm.sliding.SlidingTabLayout;
@SuppressWarnings("ALL")
public class LibraryNextActivity extends AppCompatActivity {

    LibraryVPAdapter libraryVPAdapter;
    ViewPager viewPager;
    SlidingTabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_next);
        // Here we have to intialize....
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
        tvToolbarTitle.setText(getString(R.string.library_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LibraryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        final SlidingTabLayout stLibrary = (SlidingTabLayout) findViewById(R.id.stLibrary);
        final ViewPager vpLibrary = (ViewPager) findViewById(R.id.vpLibrary);

        // using Get Bundle..........
        Bundle mBundle = this.getIntent().getExtras();

        // put all data in Constructer and pass .....
        final LibraryVPAdapter mLibraryVPAdapter = new LibraryVPAdapter(getSupportFragmentManager(),
                getApplicationContext(), mBundle.getString("class_id"),
                mBundle.getString("student_id"));

        stLibrary.setCustomTabView(R.layout.tab_title_item, R.id.tvTabName);
        stLibrary.setDistributeEvenly(true);
        vpLibrary.setAdapter(mLibraryVPAdapter);

        try {
            stLibrary.setViewPager(vpLibrary);
        } catch (Exception e) {
            Log.i("FF", e.getMessage());
        }
    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent(LibraryNextActivity.this,LibraryActivity.class);
        startActivity(intent);
        finish();
    }
}
