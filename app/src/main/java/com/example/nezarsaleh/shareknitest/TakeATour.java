package com.example.nezarsaleh.shareknitest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.nezarsaleh.shareknitest.OnBoardDir.OnboardingFragment1;
import com.example.nezarsaleh.shareknitest.OnBoardDir.OnboardingFragment2;

public class TakeATour extends FragmentActivity {

    private ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_atour);
        pager = (ViewPager)findViewById(R.id.pager);







        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0 : return new TakeATourFragment1();
                    case 1 : return new TakeATourFragment2();
                    case 2 : return new TakeATourFragment3();
                    case 3 : return new TakeATourFragment4();
                    case 4 : return new TakeATourFragment5();
                    case 5 : return new TakeATourFragment6();
                    case 6 : return new TakeATourFragment7();

                    default: return null;
                }


            }

            @Override
            public int getCount() {

                return 7;
            }
        };


        pager.setAdapter(adapter);




    }

}
