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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomListViewPanier extends ArrayAdapter<String> {

    private String[] nom ;
    private String[] description ;
    private int[] quantite ;
    private String[] imagePath ;
    private int[] idPlat ;
    private Activity context ;

    Bitmap bitmap;

    public CustomListViewPanier(Activity context , int[] idPlat , String[] nom , String[] description , int[] quant , String[] imagePath){
        super(context,R.layout.row_panier,nom);

        this.context=context;
        this.nom=nom;
        this.idPlat=idPlat;
        this.description=description;
        this.quantite=quant;
        this.imagePath=imagePath;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder;
        ImageView info , unbuy ;

        //dp data
        if(r==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            r=layoutInflater.inflate(R.layout.row_panier , null , true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);

        } else {

            viewHolder=(ViewHolder)r.getTag();

        }
        viewHolder.dpNom.setText(nom[position]);
        viewHolder.dpDescription.setText(description[position]);
        viewHolder.dpQ.setText(""+quantite[position]);
        new GetImageFromUrl(viewHolder.imagePlat).execute(imagePath[position]);

        /*//image clickable
        info = (ImageView)r.findViewById(R.id.imageView2);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , LoginActivity.class);
                context.startActivity(i);
            }
        });*/


        unbuy = (ImageView)r.findViewById(R.id.imageView3);
        unbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ss ;
                int[] idd ;

                ss = LoginActivity.S ;
                idd = idPlat ;

                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://miamsushi.000webhostapp.com/connection/delPanier.php/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestInterface request = retrofit.create(RequestInterface.class);
                Call<JsonResponse> call = request.delPanier(ss,idd);
                call.enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                        if(response.code()==200){
                            JsonResponse jsonResponse = response.body();
                            Toast.makeText(context.getApplicationContext(),jsonResponse.getResponse().toString(), Toast.LENGTH_SHORT).show();
                            if(jsonResponse.getResponse().equals("Removed Successfully")){
                                Toast.makeText(context.getApplicationContext(),"One item removed successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(context.getApplicationContext(), String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponse> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(),"Erreur",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return r;
    }


    public class ViewHolder{

        TextView dpNom;
        TextView dpQ ;
        TextView dpDescription;
        ImageView bInfo;
        ImageView bUnbuy;
        ImageView imagePlat ;

        ViewHolder(View v){
            dpNom=(TextView)v.findViewById(R.id.textView);
            dpDescription=(TextView)v.findViewById(R.id.textView2);
            dpQ=(TextView)v.findViewById(R.id.textView4);
            bInfo=(ImageView) v.findViewById(R.id.imageView2);
            bUnbuy=(ImageView) v.findViewById(R.id.imageView3);
            imagePlat=(ImageView) v.findViewById(R.id.imageView);

        }
    }

    public class GetImageFromUrl extends AsyncTask<String,Void, Bitmap>{

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