package in.semicolonindia.parentcrm.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.semicolonindia.parentcrm.fragments.BookListFragment;
import in.semicolonindia.parentcrm.fragments.BookRequestFragment;


/**
 * Created by Rupesh on 07-08-2017.
 */
@SuppressWarnings("ALL")
public class LibraryVPAdapter extends FragmentStatePagerAdapter {

    private String sClassID;
    private String sStudID;
    private Context context;
    // private String[] sTitle = {"book list", "book requests"};
    private String[] sTitle = {"book requests"};

    public LibraryVPAdapter(FragmentManager fm, Context context, String sClassID, String sStudID) {
        super(fm);
        this.context = context;
        this.sClassID = sClassID;
        this.sStudID = sStudID;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle bundle = null;
        switch (position) {
           /* case 0:
                // pass class_id and student_id in fragment(BookListFragment)using setArguments(bundle)....
                bundle = new Bundle();
                bundle.putString("class_id", sClassID);
                bundle.putString("student_id", sStudID);
                fragment = new BookListFragment();
                fragment.setArguments(bundle);
                // used to initialise QuestionsTab
                break;*/
            case 0:
                // pass student_id in fragment(BookRequestFragment)using setArguments(bundle)....
                bundle = new Bundle();
                bundle.putString("student_id", sStudID);
                fragment = new BookRequestFragment();
                fragment.setArguments(bundle);
                // used to initialise QueryTab
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
