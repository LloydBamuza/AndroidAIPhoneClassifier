package com.example.lloyd.androidaiphoneclassifier.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lloyd.androidaiphoneclassifier.MainActivity;
import com.example.lloyd.androidaiphoneclassifier.NeuralAI;
import com.example.lloyd.androidaiphoneclassifier.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Anu on 22/04/17.
 */



public class FragmentThree extends Fragment {
    Button btnClassify;

    public FragmentThree() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) 
    {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        
        Button btnClassify = (Button) view.findViewById(R.id.btnClassify);
        
        btnClassify.setOnClickListener((View e) ->
        {
            StringBuilder stringBuilder = new StringBuilder();
            
            stringBuilder.append(((EditText) view.findViewById(R.id.edtScreenSize)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtCamPxls)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtPlasticDesign)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtMetalDesign)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtVidRes)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtCpuCores)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtCpuSpeed)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtRam)).getText().toString());
            
            //create temp file
            try 
            {
                File temp = File.createTempFile("temporary","file");
                BufferedWriter br = new BufferedWriter(new FileWriter(temp));
                br.write(stringBuilder.toString());
                br.close();

                //normalize the data
                File normTemp = File.createTempFile("norm","temp");
                NeuralAI.normalizeDataSet(temp,normTemp,false,false);
                
                //classify
                Double[] finalInput = new Double[8];
                int i = 0;
                BufferedReader bw = new BufferedReader(new FileReader(normTemp));
                
                String[] toClassify = (bw.readLine()).split(" ");
                for(String s : toClassify)
                {
                    finalInput[i] = Double.parseDouble(s);
                    i++;
                }

                MainActivity.multiLayerPerceptron.setInput(finalInput[0],finalInput[1],finalInput[2],finalInput[3],finalInput[4],finalInput[5],finalInput[6],finalInput[7]);
                MainActivity.multiLayerPerceptron.calculate();
                Double result = MainActivity.multiLayerPerceptron.getOutput()[0];
                
                //display results
                StringBuilder resBuilder = new StringBuilder();

                resBuilder.append("ScreenSize: " +((EditText) view.findViewById(R.id.edtScreenSize)).getText().toString() + "\n");
                resBuilder.append("Camera Pixels: "+((EditText) view.findViewById(R.id.edtCamPxls)).getText().toString() + "\n");
                resBuilder.append("Plastic Design: "+((EditText) view.findViewById(R.id.edtPlasticDesign)).getText().toString() + "\n");
                resBuilder.append("Metal Design: "+((EditText) view.findViewById(R.id.edtMetalDesign)).getText().toString() + "\n");
                resBuilder.append("Max Video Res: "+((EditText) view.findViewById(R.id.edtVidRes)).getText().toString() + "\n");
                resBuilder.append("NO. CPU CORES: "+((EditText) view.findViewById(R.id.edtCpuCores)).getText().toString() + "\n");
                resBuilder.append("Cpu Speed: "+((EditText) view.findViewById(R.id.edtCpuSpeed)).getText().toString() + "\n");
                resBuilder.append("Ram: "+((EditText) view.findViewById(R.id.edtRam)).getText().toString()+ "\n");
                resBuilder.append(NeuralAI.catagorize(result));
                MainActivity.resultsList.add(resBuilder.toString());
                MainActivity.arrayAdapter.notifyDataSetChanged();
                MainActivity.lstView.setAdapter(MainActivity.arrayAdapter);

                        
                
                
            } 
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            



        });
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }
}