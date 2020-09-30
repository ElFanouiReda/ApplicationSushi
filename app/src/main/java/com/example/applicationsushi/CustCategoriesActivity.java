package com.example.applicationsushi;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class CustCategoriesActivity extends AppCompatActivity {

    ViewPager viewPager ;
    CustomViewPageCategorie customViewPageCategorie ;
    List<ClassCategorie> categories ;
    Integer[] colors = null ;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    CardView cardViewAcceuil ;
    CardView cardViewRestaurant ;
    CardView cardViewPanier ;

    Button buttonLogOut;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_categories);

        cardViewAcceuil = findViewById(R.id.cardView1);
        cardViewAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustCategoriesActivity.this , CustMenuActivity.class);
                startActivity(intent);
            }
        });


        cardViewRestaurant = findViewById(R.id.cardView4);
        cardViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustCategoriesActivity.this, CustListViewRestaurantActivity.class);

            }
        });

        cardViewPanier = findViewById(R.id.cardView3) ;
        cardViewPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustCategoriesActivity.this, CustListViewPanierActivity.class));
            }
        });

        categories = new ArrayList<>();
        categories.add(new ClassCategorie(R.drawable.categorie_nouveautes_sushi, 1 , "Trouver tout les nouveautés dans cette catégorie." , "Nouveautés") );
        categories.add(new ClassCategorie(R.drawable.categorie_summer_poke, 2 , "Il est temps de déguster nos recettes fraiches." , "Summer Poke") );
        categories.add(new ClassCategorie(R.drawable.categorie_menu, 3 , "Découvrez les menu qu'on vous propose." , "Menu") );
        categories.add(new ClassCategorie(R.drawable.categorie_plateau, 4 , "Il y'a pas mieux que nos plateaux." , "Plateaux") );
        categories.add(new ClassCategorie(R.drawable.categorie_rolls, 5 , "Les meilleurs rolls que vous pouvez prendre." , "Rolls") );
        categories.add(new ClassCategorie(R.drawable.categorie_makis, 6 , "N'oublier pas de gouter nos delicieux Maki." , "Maki") );
        categories.add(new ClassCategorie(R.drawable.categorie_accompagnement, 7 , "Profitez de nos accompagnements." , "Accompagnements") );
        categories.add(new ClassCategorie(R.drawable.categorie_dessert, 8 , "Rien n'est plus intéressant que de prendre un bon desert." , "Deserts") );
        categories.add(new ClassCategorie(R.drawable.categorie_boissons, 9 , "Un bonne bonne dégustation demande un meilleur boisson." , "Boissons") );

        customViewPageCategorie = new CustomViewPageCategorie(categories , this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(customViewPageCategorie);
        viewPager.setPadding(130 , 0 ,  130 , 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.colorViewPager1),
                getResources().getColor(R.color.colorViewPager2),
                getResources().getColor(R.color.colorViewPager3),
                getResources().getColor(R.color.colorViewPager4),
                getResources().getColor(R.color.colorViewPager5),
                getResources().getColor(R.color.colorViewPager2),
                getResources().getColor(R.color.colorViewPager3),
                getResources().getColor(R.color.colorViewPager4),
                getResources().getColor(R.color.colorViewPager5),
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


        buttonLogOut = findViewById(R.id.buttonView);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustCategoriesActivity.this, LoginActivity.class) );
            }
        });

    }
}

