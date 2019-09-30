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
public class Atcoder extends Fragment {


    public Atcoder() {
        // Required empty public constructor
    }


    private DatabaseHandler db;
    private RecyclerView recycleLayout;
    private RecycleAdapter adapter5;
    private TextView txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_atcoder, container, false);

        txt = view.findViewById(R.id.emptyshow);

        db = new DatabaseHandler(getContext());
        recycleLayout = view.findViewById(R.id.atc_recycler);
        Constants c= new Constants();
        List<SingleObj> arrayList = new ArrayList<>();
        arrayList = db.getAllEvents(c.TABLE_ATCODER);

        if(arrayList.isEmpty())
        {
            txt.setVisibility(View.VISIBLE);
        }
        else
        {
                txt.setVisibility(View.GONE);
        }

        recycleLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter5 = new RecycleAdapter(arrayList,getContext());
        recycleLayout.setAdapter(adapter5);

        return view;
    }

}
