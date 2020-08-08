package com.example.applicationsushi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class CustomViewPageCategorie extends PagerAdapter {

    private List<ClassCategorie> Categories ;
    private LayoutInflater layoutInflater ;
    private Context context ;


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
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_viewpager , container , false);

        ImageView imageView ;
        TextView nom , desc ;
        Button button ;

        imageView = view.findViewById(R.id.imageView);
        nom = view.findViewById(R.id.textView);
        desc = view.findViewById(R.id.textView1);
        button = view.findViewById(R.id.buttonView);


        imageView.setImageResource(Categories.get(position).getImage());
        nom.setText(Categories.get(position).getNom());
        desc.setText(Categories.get(position).getDescription());


        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
