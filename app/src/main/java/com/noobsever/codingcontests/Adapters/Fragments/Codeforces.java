package com.noobsever.codingcontests.Adapters.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noobsever.codingcontests.Adapters.RecycleAdapter;
import com.noobsever.codingcontests.Constants;
import com.noobsever.codingcontests.DatabaseHandler;
import com.noobsever.codingcontests.Models.SingleObj;
import com.noobsever.codingcontests.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Codeforces extends Fragment {


    public Codeforces() {
        // Required empty public constructor
    }

    DatabaseHandler db;
    List<SingleObj> arrayList;
    private RecyclerView recycleLayout;
    private RecycleAdapter adapter;
    private TextView txt;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view= inflater.inflate(R.layout.fragment_codeforces, container, false);
        txt = view.findViewById(R.id.emptyshow);
        db = new DatabaseHandler(getContext());
        recycleLayout = view.findViewById(R.id.cf_recycler);
        Constants c = new Constants();
        arrayList = new ArrayList<>();
        arrayList = db.getAllEvents(c.TABLE_CODEFORCES);

        if(arrayList.isEmpty())
        {
            txt.setVisibility(View.VISIBLE);
        }
        else
        {
            txt.setVisibility(View.GONE);
        }

        recycleLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecycleAdapter(arrayList, getContext());
        recycleLayout.setAdapter(adapter);
        return view;
    }



    /*@Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 1818:
            {

                adapter.ShareItem(item.getGroupId());
                return true;
            }
            case 1919:
            {

                adapter.OpenContest(item.getGroupId());
                return true;
            }
            default:
                return true;
        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if(isVisibleToUser && !isLoaded)
        {

        }

    }*/
}
