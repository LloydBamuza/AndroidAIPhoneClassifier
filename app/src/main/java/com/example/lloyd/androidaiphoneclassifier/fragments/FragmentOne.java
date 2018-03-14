package com.example.lloyd.androidaiphoneclassifier.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lloyd.androidaiphoneclassifier.MainActivity;
import com.example.lloyd.androidaiphoneclassifier.NeuralAI;
import com.example.lloyd.androidaiphoneclassifier.R;

/**
 * Created by Anu on 22/04/17.
 */



public class FragmentOne extends Fragment {
    public static TextView txtFeedback;
    static boolean isReadyDisplayFeedback = false;

    final int OPEN_INPUT_FILE = 900;
    //get ui references
    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        Button btnOpenFile = (Button) view.findViewById(R.id.btnOpenFile);
        txtFeedback = (TextView) view.findViewById(R.id.txtFeedBack);
        isReadyDisplayFeedback = true;

        MainActivity.multiLayerPerceptron = NeuralAI.createNetwork(null);

        String msg = "Neural Network trained using default data and is ready.\n Ensure that you fill in ALL required fields. \n \n Note that the default train data comprises of only 5 elements so some classifications wont work until Version 2.0 where you'll be able to train the AI. \n \n \n Version: 1.0.0";
        outputFeedback(msg);


        //open file with data to classify
        btnOpenFile.setOnClickListener(e->
        {
            //Intent intent = new Intent();
            //intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            //startActivityForResult(intent, OPEN_INPUT_FILE);

            Toast.makeText(MainActivity.APP_CONTEXT,"Feature will be available in next version!",Toast.LENGTH_LONG);
        });

        return view;

    }

    public static void outputFeedback(String msg)
    {

                if(!isReadyDisplayFeedback)
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                txtFeedback.append("\n" +msg);


            }

    }



