package com.example.applicationsushi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;


public class CustomListViewCategoriePlat extends ArrayAdapter<String> {


        private String[] nom ;
        private String[] description ;
        private Activity context ;

        Bitmap bitmap;
        private String[] imagePath ;


        public CustomListViewCategoriePlat(Activity context , String[] nom , String[] description , String[] imagePath){
            super(context , R.layout.row , nom );

            this.context=context;
            this.nom=nom;
            this.description=description;
            this.imagePath=imagePath;

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

            viewHolder.dpNom.setText(nom[position]);
            viewHolder.dpDescription.setText(description[position]);
            new GetImageFromUrl(viewHolder.imagePlat).execute(imagePath[position]);


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
            ImageView imagePlat ;

            ViewHolder(View v){
                dpNom=(TextView)v.findViewById(R.id.textView);
                dpDescription=(TextView)v.findViewById(R.id.textView2);
                bInfo=(ImageView) v.findViewById(R.id.imageView2);
                bBuy=(ImageView) v.findViewById(R.id.imageView3);
                imagePlat=(ImageView) v.findViewById(R.id.imageView);
            }

        }


    public class GetImageFromUrl extends AsyncTask<String,Void, Bitmap> {

        ImageView imageView;

        public GetImageFromUrl(ImageView imageView){
            this.imageView=imageView;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay=url[0];
            bitmap=null;


            try {

                InputStream inputStream = new java.net.URL(urldisplay).openStream();
                bitmap= BitmapFactory.decodeStream(inputStream);

            } catch (Exception e){
                e.printStackTrace();
            }


            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }


    }
}

