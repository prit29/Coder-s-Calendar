package com.noobsever.codingcontests.Adapters;



import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.noobsever.codingcontests.Adapters.Fragments2.LiveContest;
import com.noobsever.codingcontests.Adapters.Fragments2.LongContest;
import com.noobsever.codingcontests.Adapters.Fragments2.ShortContest;

public class TabAdapter2 extends FragmentPagerAdapter {


    private Context myContext;
    int totalTabs;

    public TabAdapter2(Context cc, FragmentManager fm, int TotalTabs) {
        super(fm);
        this.myContext=cc;
        this.totalTabs=TotalTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LiveContest liveContest = new LiveContest();
                return liveContest;
            case 1:
               LongContest longContest = new LongContest();
                return longContest;
            case 2:
                ShortContest shortContest = new ShortContest();
                return shortContest;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
