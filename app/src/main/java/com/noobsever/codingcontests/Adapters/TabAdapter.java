package com.noobsever.codingcontests.Adapters;



import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.noobsever.codingcontests.Adapters.Fragments.Atcoder;
import com.noobsever.codingcontests.Adapters.Fragments.Codechef;
import com.noobsever.codingcontests.Adapters.Fragments.Codeforces;
import com.noobsever.codingcontests.Adapters.Fragments.HackeRank;
import com.noobsever.codingcontests.Adapters.Fragments.Hackerearth;
import com.noobsever.codingcontests.Adapters.Fragments.Spoj;
import com.noobsever.codingcontests.Adapters.Fragments.Topcoder;
import com.noobsever.codingcontests.Constants;

import java.util.ArrayList;

public class TabAdapter extends FragmentPagerAdapter {



    private Context myContext;
    int totalTabs;
    ArrayList<String> data;
    SharedPreferences preferences;

    public TabAdapter(Context cc, FragmentManager fm, int TotalTabs, ArrayList<String> list) {
        super(fm);
        this.myContext=cc;
        this.totalTabs=TotalTabs;
        this.data= list;
    }


    @Override
    public Fragment getItem(int position) {


        Constants c= new Constants();
        if(data.get(position).equals(c.TABLE_CODEFORCES))
        {
            Codeforces codeforces = new Codeforces();
            return codeforces;
        }
        if(data.get(position).equals(c.TABLE_CODECHEF))
        {
            Codechef codechef = new Codechef();
            return codechef;
        }
        if(data.get(position).equals(c.TABLE_HACKERRANK))
        {
            HackeRank hackeRank = new HackeRank();
            return hackeRank;
        }
        if(data.get(position).equals(c.TABLE_HACKEREARTH))
        {
            Hackerearth hackerearth = new Hackerearth();
            return hackerearth;
        }
        if(data.get(position).equals(c.TABLE_TOPCODER))
        {
            Topcoder topcoder = new Topcoder();
            return topcoder;
        }
        if(data.get(position).equals(c.TABLE_SPOJ))
        {
            Spoj spoj = new Spoj();
            return spoj;
        }
        if(data.get(position).equals(c.TABLE_ATCODER))
        {
            Atcoder atcoder = new Atcoder();
            return atcoder;
        }

        return null;
    }



    @Override
    public int getCount() {
        return totalTabs;
    }
}
