package com.headquarter.com.headquarter.activity.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.AdapterRecycler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    ArrayList<String> listDatos;
    RecyclerView recycler;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        recycler = rootView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        listDatos = new ArrayList<String>();

        for(int i = 0;i<50;i++){
            listDatos.add("Dato "+i);
        }

        AdapterRecycler adapter = new AdapterRecycler(listDatos);
        recycler.setAdapter(adapter);

        return rootView;
    }

}
