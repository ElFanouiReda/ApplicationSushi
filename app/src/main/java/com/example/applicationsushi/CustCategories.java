package com.example.applicationsushi;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

public class CustCategories extends AppCompatActivity {

    ViewPager viewPager ;
    CustomViewPageCategorie customViewPageCategorie ;
    List<ClassCategorie> categories ;
    Integer[] colors = null ;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    BufferedInputStream is ;
    String line=null ;
    String result=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_categories);

        categories = new ArrayList<>();
        categories.add(new ClassCategorie(R.drawable.ic_sushi, 1 , "XXXX XXXXXXX XXXXXXX XXXXXXX XXXXXXX" , "Cat1") );
        categories.add(new ClassCategorie(R.drawable.ic_sushi, 2 , "XXXX XXXXXXX XXXXXXX XXXXXXX XXXXXXX XXXX XXXXXXX XXXXXXX XXXXXXX XXXXXXX" , "Cat2") );
        categories.add(new ClassCategorie(R.drawable.ic_sushi, 3 , "XXXX XXXXXXX XXXXXXX XXXXXXX XXXXXXX" , "Cat3") );
        categories.add(new ClassCategorie(R.drawable.ic_sushi, 4 , "XXXX XXXXXXX XXXXXXX XXXXXXX XXXXXXXXXXX XXXXXXX XXXXXXX XXXXXXX XXXXXXX" , "Cat4") );
        categories.add(new ClassCategorie(R.drawable.ic_sushi, 5 , "XXXX XXXXXXX XXXXXXX XXXXXXX XXXXXXX XXXX XXXXXXX XXXXXXX XXXXXXX XXXXXXX" , "Cat5") );

        customViewPageCategorie = new CustomViewPageCategorie(categories , this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(customViewPageCategorie);
        viewPager.setPadding(130 , 0 ,  130 , 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.colorViewPager1),
                getResources().getColor(R.color.colorViewPager2),
                getResources().getColor(R.color.colorViewPager3),
                getResources().getColor(R.color.colorViewPager4),
                getResources().getColor(R.color.colorViewPager5)
        };

        colors = colors_temp ;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position < (customViewPageCategorie.getCount() - 1)  && position < (colors.length - 1)){
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                     positionOffset, colors[position], colors[position + 1] )
                            );

                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
}



















