package com.example.applicationsushi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CustomViewPageCategorie extends PagerAdapter {

    private List<ClassCategorie> Categories ;
    private LayoutInflater layoutInflater ;
    private Context context ;

    BufferedInputStream bufferedInputStream ;
    String line = null ;
    String result = null ;


    String nomV[] ;
    String descriptionV[] ;


    public CustomViewPageCategorie(List<ClassCategorie> categories, Context context) {
        Categories = categories;
        this.context = context;
    }


    @Override
    public int getCount() {
        return Categories.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_viewpager , container , false);

        ImageView imageView ;
        TextView nom , desc ;
        Button button ;

        imageView = view.findViewById(R.id.imageView);
        nom = view.findViewById(R.id.textView);
        desc = view.findViewById(R.id.textView1);
        imageView = view.findViewById(R.id.imageView);



        imageView.setImageResource(Categories.get(position).getImage());
        nom.setText(Categories.get(position).getNom());
        desc.setText(Categories.get(position).getDescription());

        view.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //Toast.makeText(context , ""+ Categories.get(position).getIdCategorie() , Toast.LENGTH_SHORT).show();


                int _idCategorie = Categories.get(position).getIdCategorie() ;
                Intent i = new Intent( context.getApplicationContext() , CustCategoriePlat.class );
                /*i.putExtra("lesdescriptions" , descriptionV);
                i.putExtra("lesnoms" , nomV);*/
                i.putExtra("idCat" , _idCategorie);
                context.startActivity(i);
            }
        });

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


    public void GetCategorie(int id){

    }

}


