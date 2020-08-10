package com.example.applicationsushi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class CustomListViewCategoriePlat extends ArrayAdapter<String> {


        private String[] nom ;
        private String[] description ;
        private Activity context ;


        public CustomListViewCategoriePlat(Activity context , String[] nom , String[] description){
            super(context , R.layout.row , nom );

            this.context=context;
            this.nom=nom;
            this.description=description;
        }



        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View r = convertView;
            ViewHolder viewHolder;
            ImageView info , buy ;

            //dp data
            if(r==null){
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                r=layoutInflater.inflate(R.layout.row , null , true);
                viewHolder=new ViewHolder(r);
                r.setTag(viewHolder);

            } else {
                viewHolder=(ViewHolder)r.getTag();
            }

            viewHolder.dpNom.setText("reda");
            viewHolder.dpDescription.setText("reda");


            //image clickable
            info = (ImageView)r.findViewById(R.id.imageView2);
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context , LoginActivity.class);
                    context.startActivity(i);
                }
            });


            buy = (ImageView)r.findViewById(R.id.imageView3);
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return r;
        }


        public class ViewHolder{

            TextView dpNom;
            TextView dpDescription;
            ImageView bInfo;
            ImageView bBuy;

            ViewHolder(View v){
                dpNom=(TextView)v.findViewById(R.id.textView);
                dpDescription=(TextView)v.findViewById(R.id.textView2);
                bInfo=(ImageView) v.findViewById(R.id.imageView2);
                bBuy=(ImageView) v.findViewById(R.id.imageView3);
            }

        }

    }

