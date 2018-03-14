package com.example.lloyd.androidaiphoneclassifier.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lloyd.androidaiphoneclassifier.R;

/**
 * Created by Anu on 22/04/17.
 */



public class FragmentFour extends Fragment {
    Button btnTrain;

    public FragmentFour() {
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


        View view = inflater.inflate(R.layout.fragment_four, container, false);

        btnTrain = (Button) view.findViewById(R.id.btnTrain);

       /* btnTrain.setOnClickListener(e->
        {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(((EditText) view.findViewById(R.id.edtScreenSize)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtCamPxls)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtPlasticDesign)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtMetalDesign)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtVidRes)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtCpuCores)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtCpuSpeed)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtRam)).getText().toString() + " ");
            stringBuilder.append(((EditText) view.findViewById(R.id.edtOutput)).getText().toString());



            //create temp file
            try {
                File temp = File.createTempFile("temporary", "file");
                BufferedWriter br = new BufferedWriter(new FileWriter(temp));
                br.write(stringBuilder.toString());
                br.close();

                //normalize the data
                File normTemp = File.createTempFile("norm", "temp");
                double[] res = new double[8];
                res = NeuralAI.normalizeDataSet(temp, normTemp, true,true);
                InputStream newSet = new FileInputStream(normTemp);
                MainActivity.multiLayerPerceptron = NeuralAI.createNetwork(newSet);

               // //train
                //TrainingSet newSetLearn = TrainingSet.createFromFile(normTemp.getPath(),8,1," ");
                //MainActivity.multiLayerPerceptron.learn(newSetLearn);

                //output
                //Toast.makeText(MainActivity.APP_CONTEXT,"Training complete. Open results tab",Toast.LENGTH_LONG);
                Double expected = Double.parseDouble((((EditText)view.findViewById(R.id.edtOutput)).getText().toString()));
                MainActivity.multiLayerPerceptron.setInput(res);
                MainActivity.multiLayerPerceptron.calculate();;


                StringBuilder out;
                out = new StringBuilder("AI has learned the new data. \n Result: "+ MainActivity.multiLayerPerceptron.getOutput() + "\n Expected: "+ expected +"\n");
                MainActivity.resultsList.add(out.toString());
                MainActivity.arrayAdapter.notifyDataSetChanged();



            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        });
    }*/

        return view;
    }


}