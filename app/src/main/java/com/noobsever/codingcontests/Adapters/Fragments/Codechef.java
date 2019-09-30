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
public class Codechef extends Fragment {


    public Codechef() {
        // Required empty public constructor
    }

    DatabaseHandler db;
    private RecyclerView recycleLayout;
    private List<SingleObj> arrayList;
    private RecycleAdapter adapter1;
    private TextView txt;

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_codechef, container, false);
        txt = view.findViewById(R.id.emptyshow);
        db = new DatabaseHandler(getContext());
        recycleLayout = view.findViewById(R.id.codec_recycler);
        Constants c = new Constants();
        arrayList = new ArrayList<>();
        arrayList = db.getAllEvents(c.TABLE_CODECHEF);

        if(arrayList.isEmpty())
        {
            txt.setVisibility(View.VISIBLE);
        }
        else
        {
            txt.setVisibility(View.GONE);
        }

        recycleLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter1 = new RecycleAdapter(arrayList, getContext());
        recycleLayout.setAdapter(adapter1);
        return view;
    }


   /* void load()
    {
            //Toast.makeText(getContext(), "CODECHEF", Toast.LENGTH_SHORT).show();

    }
*/
    /*@Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case 1818:
            {
                Toast.makeText(getContext(),arrayList.get(adapter1.getPosition()).getEvent(),Toast.LENGTH_SHORT).show();
                break;
            }
            case 1919:
            {
                adapter1.OpenContest(adapter1.getPosition());
                break;
            }

        }
        return super.onContextItemSelected(item);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser && !isLoaded)
        {
            load();
        }

    }*/
}
