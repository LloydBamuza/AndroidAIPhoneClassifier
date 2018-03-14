package com.example.lloyd.androidaiphoneclassifier.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lloyd.androidaiphoneclassifier.MainActivity;
import com.example.lloyd.androidaiphoneclassifier.R;

/**
 * Created by Anu on 22/04/17.
 */



public class FragmentTwo extends Fragment {

    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_two, container, false);
        //get widget references
        MainActivity.lstView = (ListView) view.findViewById(R.id.lstResults);

        //create adaptor for listview
        MainActivity.arrayAdapter = new ArrayAdapter(MainActivity.APP_CONTEXT,android.R.layout.simple_list_item_1,MainActivity.resultsList);
        MainActivity.lstView.setAdapter(MainActivity.arrayAdapter);
        return view;
    }

}