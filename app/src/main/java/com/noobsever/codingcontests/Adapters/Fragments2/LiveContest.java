package com.noobsever.codingcontests.Adapters.Fragments2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noobsever.codingcontests.Adapters.RecycleAdapter;
import com.noobsever.codingcontests.Constants;
import com.noobsever.codingcontests.DatabaseHandler;
import com.noobsever.codingcontests.Models.SingleObj;
import com.noobsever.codingcontests.R;

import java.util.ArrayList;
import java.util.List;


public class LiveContest extends Fragment {



    public LiveContest() {
        // Required empty public constructor
    }



    DatabaseHandler db;
    List<SingleObj> arrayList;
    private RecyclerView recycleLayout;
    private RecycleAdapter adapter;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_live_contest, container, false);
        db = new DatabaseHandler(getContext());
        recycleLayout = view.findViewById(R.id.live_recycler);
        Constants c = new Constants();
        arrayList = new ArrayList<>();
        arrayList = db.getAllEvents(c.LIVE);
        recycleLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecycleAdapter(arrayList, getContext());
        recycleLayout.setAdapter(adapter);
        return view;
    }

}
