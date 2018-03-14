package com.example.lloyd.androidaiphoneclassifier;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lloyd.androidaiphoneclassifier.fragments.FragmentFour;
import com.example.lloyd.androidaiphoneclassifier.fragments.FragmentOne;
import com.example.lloyd.androidaiphoneclassifier.fragments.FragmentThree;
import com.example.lloyd.androidaiphoneclassifier.fragments.FragmentTwo;

import org.neuroph.nnet.MultiLayerPerceptron;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int OPEN_INPUT_FILE = 888;
    public static ArrayAdapter arrayAdapter;
    public static List<String> resultsList = new ArrayList<String>();
    public static MultiLayerPerceptron multiLayerPerceptron;
    public static ListView lstView;
    public static  Context APP_CONTEXT = null;
    public static InputStream defaultTrainData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentOne(), "Classify from file");
        adapter.addFragment(new FragmentTwo(), "Results");
        adapter.addFragment(new FragmentThree(), "Enter Specs");
        adapter.addFragment(new FragmentFour(),"Train");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);





        ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},8);

        APP_CONTEXT = getApplicationContext();

        defaultTrainData = getResources().openRawResource(R.raw.norminput);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPEN_INPUT_FILE && resultCode == RESULT_OK)
        {
            File normInput = null;
            File input = new File(data.getData().getPath());
            try {
                normInput = File.createTempFile("temp","file");
            } catch (IOException e) {
                e.printStackTrace();
            }
            NeuralAI.normalizeDataSet(input,normInput,true,false);
            NeuralAI.classify(resultsList,multiLayerPerceptron,normInput,input);

            //output classification results





        }
    }



// Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
