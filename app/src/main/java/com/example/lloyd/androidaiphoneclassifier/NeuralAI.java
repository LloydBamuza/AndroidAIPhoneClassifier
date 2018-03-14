package com.example.lloyd.androidaiphoneclassifier;


import android.support.annotation.Nullable;

import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NeuralAI
{
    static double normalize(double val, double min, double max)
    {

        double out;

        out = ((val - min)/(max - min));

        return out;
    }


    public static  double[] normalizeDataSet(File input, File output, Boolean isTrainData,Boolean returnNorm)
    {
        int count = 0;
        String buffer;
        String[] temp = new String[9];
        String normalizedVals;
        List<String> allData = new ArrayList<>();

        BufferedReader bufferedReader =  null;
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter;
        double screenSize =0,cameraPixels =0,isMostPlastic=0,isMostMetal=0,videoRecRes=0,numCores=0,maxCpuSpeed=0,ram=0,out = 0;
        try
        {


            bufferedReader = new BufferedReader(new FileReader(input));





            //read a line
            while(((buffer = bufferedReader.readLine()) != null) && buffer != "") {
                count++;


                buffer.trim();
                //split and place into variables
                temp = buffer.split(" ");

                screenSize = Double.parseDouble(temp[0]);
                cameraPixels = Double.parseDouble(temp[1]);
                isMostPlastic = Double.parseDouble(temp[2]);
                isMostMetal = Double.parseDouble(temp[3]);
                videoRecRes = Double.parseDouble(temp[4]);
                numCores = Double.parseDouble(temp[5]);
                maxCpuSpeed = Double.parseDouble(temp[6]);
                ram = Double.parseDouble(temp[7]);

                if(isTrainData)
                    out = Double.parseDouble(temp[8]);


                //normalize data
                screenSize = normalize(screenSize, 3.0, 6.3);
                cameraPixels = normalize(cameraPixels, 5.0, 20.0);
                isMostMetal = normalize(isMostMetal, 0.0, 1.0);
                isMostPlastic = normalize(isMostPlastic, 0.0, 1.0);
                videoRecRes = normalize(videoRecRes, 114.0, 4000.0);
                numCores = normalize(numCores, 1.0, 10.0);
                maxCpuSpeed = normalize(maxCpuSpeed, 800, 2500.0);
                ram = normalize(ram, 128.0, 6000.0);

                if(isTrainData)
                    normalizedVals = screenSize + " " + cameraPixels + " " + isMostPlastic + " " + isMostMetal + " " + videoRecRes + " " + numCores + " " + maxCpuSpeed + " " + ram+ " " + out;
                else
                    normalizedVals = screenSize + " " + cameraPixels + " " + isMostPlastic + " " + isMostMetal + " " + videoRecRes + " " + numCores + " " + maxCpuSpeed + " " + ram;

                allData.add(normalizedVals);
                // write normalized data to output file






            }
            bufferedWriter = new BufferedWriter(new FileWriter(output));
            for(String s : allData)
            {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }






            bufferedReader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bufferedReader.close();
                bufferedWriter.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return new double[] {screenSize ,cameraPixels ,isMostMetal,isMostPlastic ,isMostMetal ,videoRecRes , numCores ,maxCpuSpeed , ram};

    }

  /*  public static MultiLayerPerceptron createNetwork(File input)
    {
        MultiLayerPerceptron myMLP = new MultiLayerPerceptron(TransferFunctionType.TANH,8,4,1);
        TrainingSet trainData = null;

        trainData = TrainingSet.createFromFile(input.getPath(),8,1," ");

        myMLP.learn(trainData);

        return myMLP;
    }*/

    public static MultiLayerPerceptron createNetwork(@Nullable InputStream newData)
    {
        InputStream input = MainActivity.defaultTrainData;
        MultiLayerPerceptron myMLP = new MultiLayerPerceptron(TransferFunctionType.TANH,8,4,1);

        File data;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int size = 0;
        byte[] buffer = new byte[1024];

        try {

            while ((size = input.read(buffer, 0, 1024)) >= 0) {
                byteArrayOutputStream.write(buffer, 0, size);
            }

            if(newData != null)
            {
                while ((size = newData.read(buffer, 0, 1024)) >= 0) {
                    byteArrayOutputStream.write(buffer, 0, size);
                }
            }
            data = File.createTempFile("temp","file");
            input.close();
            buffer = byteArrayOutputStream.toByteArray();
            FileOutputStream fos = new FileOutputStream(data );
            fos.write(buffer);
            fos.close();
            myMLP.learn(TrainingSet.load(data.getPath()));


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return myMLP;
    }

    public static String catagorize(double result)
    {
        String msg = " No output. Please train AI with a bigger dataset";

        if(result >= 0 && result <= 0.33333333)
        {
            double perc = (((result)/ 0.33333333) * 100)/10;
            msg = "The device is an entry-level device with a score of: "+ (int)perc + " in a range of [1 - 10]";
        }

        if(result >= 0.33333333 && result <= 0.66666666)
        {
            double perc = (((result)/ 0.666666666) * 100)/10;
            msg = "The device is a mid-level device with a score of: "+ (int)perc + " in a range of [1 - 10]";
        }

        if(result >= 0.66666666 && result <= 1)
        {
            double perc = (((result)/ 1) * 100)/10;
            msg = "The device is a high-end device with a score of: "+ (int)perc + " in a range of [1 - 10]";
        }

        return msg;
    }

    public static void classify(List<String> lst, MultiLayerPerceptron ai, File input, File originalInputData)
    {
        TrainingSet dataSet = TrainingSet.createFromFile(input.getPath(),8,0," ");

        //list of test data
        List<TrainingElement> inputList = dataSet.trainingElements();

        //list of original, non-normalized input data
        dataSet = TrainingSet.createFromFile(originalInputData.getPath(),8,0," ");
        //dataSet.(new String[] {"Screen Size","Camera Pixels", "Plastic Design?", "Metal Design", "Max Video Record Res", "NO. CPU cores", "Max Cpu Speed"});
        List<TrainingElement> origList = dataSet.elements();


        //classify
        int pos = 0;
        for(TrainingElement d : inputList)
        {
            StringBuilder stringBuilder = new StringBuilder();

            ai.setInput(d.getInput());
            ai.calculate();
           // stringBuilder.append("\n"+ Arrays.toString(dataSet.getColumnNames()) );
            stringBuilder.append("\n" + (origList.get(pos).toString()));
            stringBuilder.append("\n" + catagorize(ai.getOutput()[0]));
            pos++;
            stringBuilder.append("\n -------------------------------------------------------");
            lst.add(stringBuilder.toString());

        }


    }

}