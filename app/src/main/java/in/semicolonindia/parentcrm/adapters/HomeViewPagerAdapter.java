package in.semicolonindia.parentcrm.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.semicolonindia.parentcrm.fragments.HomeMenuFragment;
import in.semicolonindia.parentcrm.fragments.HomeOptionFragment;

/**
 * Created by RANJAN SINGH on 8/28/2017.
 */
@SuppressWarnings("ALL")
public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] sTitle = {"Menu", "Options"};

    public HomeViewPagerAdapter(FragmentManager fm, Context contex) {
        super(fm);
       this.context=contex;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                // getBookList();
                fragment = new HomeMenuFragment();
                // used to initialise QuestionsTab
                break;
            case 1:
                fragment = new HomeOptionFragment();        // used to initialise QueryTab
                break;
        }
        return fragment;
    }
    @Override
    public int getCount() {
        return sTitle.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sTitle[position];
    }
}
