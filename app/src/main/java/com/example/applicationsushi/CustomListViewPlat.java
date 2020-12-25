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

public class CustomListViewPlat extends ArrayAdapter<String> {

    private Double[] note ;
    private int[] idPlat ;
    private int[] idCategorie ;
    private Double[] prix ;
    private String[] nom;
    private String[] description;
    private String[] imagePath;
    private int[] id;
    private Activity context;

    Bitmap bitmap;

    public CustomListViewPlat(Activity context, int[] idPlat , int idCategorie[], int[] id, String[] nom,
                              String[] description, String[] imagePath , Double prix[] , Double note[]) {
        super(context, R.layout.row, nom);


        this.context = context;
        this.nom = nom;
        this.id = id;
        this.description = description;
        this.imagePath = imagePath;
        this.note = note ;
        this.idCategorie = idCategorie ;
        this.prix = prix ;
        this.idPlat = idPlat ;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder;
        ImageView info, buy;

        //dp data
        if (r == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            r = layoutInflater.inflate(R.layout.row, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) r.getTag();

        }
        viewHolder.dpNom.setText(nom[position]);
        viewHolder.dpDescription.setText(description[position]);
        new GetImageFromUrl(viewHolder.imagePlat).execute(imagePath[position]);

        //image clickable
        // info = (ImageView)r.findViewById(R.id.imageView2);
        //info.setOnClickListener(new View.OnClickListener() {
        //      @Override
        //      public void onClick(View v) {
        //         Intent i = new Intent(context , LoginActivity.class);
        //        context.startActivity(i);
        //     }
        //});


        buy = (ImageView) r.findViewById(R.id.imageView3);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ss;

                ss = LoginActivity.S;
                int idd = id[position];

                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://miamsushi.000webhostapp.com/connection/addPanier.php/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestInterface request = retrofit.create(RequestInterface.class);
                Call<JsonResponse> call = request.addPanier(ss, idd);
                call.enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                        if (response.code() == 200) {
                            JsonResponse jsonResponse = response.body();
<<<<<<< HEAD


                            /// khdma kamla
                        ClassPlat classPlatClique = new ClassPlat(idPlat[position] , idCategorie[position] ,
                                description[position] , nom[position] , prix[position] , note[position] );

                        CustMenuActivity.ClassPlatChoisi.add(classPlatClique);
=======
>>>>>>> zaid

                            Toast.makeText(context.getApplicationContext(), "Un article ajouté au panier", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonResponse> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            ;


        });

        return r;

    }


    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;

        public GetImageFromUrl(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            bitmap = null;


            try {

                InputStream inputStream = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (Exception e) {
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

    public class ViewHolder {

        TextView dpNom;
        TextView dpDescription;
        ImageView bInfo;
        ImageView bBuy;
        ImageView imagePlat;

        ViewHolder(View v) {
            dpNom = (TextView) v.findViewById(R.id.textView);
            dpDescription = (TextView) v.findViewById(R.id.textView2);
            bInfo = (ImageView) v.findViewById(R.id.imageView2);
            bBuy = (ImageView) v.findViewById(R.id.imageView3);
            imagePlat = (ImageView) v.findViewById(R.id.imageView);

        }
    }

}