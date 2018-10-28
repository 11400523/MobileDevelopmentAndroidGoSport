package modev.gosport.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import modev.gosport.Class.CompactCalendarTab;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int numbOfTabs = 1;
    String userID;
    Context mContext;

    public ViewPagerAdapter(FragmentManager fm, String userID, Context context) {
        super(fm);
        this.userID = userID;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
            CompactCalendarTab compactCalendarTab = new CompactCalendarTab(userID, mContext);
            return compactCalendarTab;
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }
}