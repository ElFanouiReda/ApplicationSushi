package com.example.applicationsushi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustMenu extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"XXXXX" , "XXXXX" , "XXXXX" , "XXXXX" , "XXXXX" , "XXXXX" , "XXXXX" , "XXXXX"};
    String mDescription[] = {"XXXXXXXXX XXXXXXXXXX hjkkkkkk kkkkkkkkkkkk kkkkkkkkkkkkk kkkkkkkkk kkkkkkkkkkk kkkkkkkkk kkkkkkkkk" , "XXXXXXXXX XXXXXXXXXX hjkkkkkk kkkkkkkkkkkk kkkkkkkkkkkkk kkkkkkkkk kkkkkkkkkkk kkkkkkkkk kkkkkkkkk" , "XXXXXXXXX XXXXXXXXXX hjkkkkkk kkkkkkkkkkkk kkkkkkkkkkkkk kkkkkkkkk kkkkkkkkkkk kkkkkkkkk kkkkkkkkk" , "XXXXXXXXX XXXXXXXXXX hjkkkkkk kkkkkkkkkkkk kkkkkkkkkkkkk kkkkkkkkk kkkkkkkkkkk kkkkkkkkk kkkkkkkkk" , "XXXXXXXXX XXXXXXXXXX hjkkkkkk kkkkkkkkkkkk kkkkkkkkkkkkk kkkkkkkkk kkkkkkkkkkk kkkkkkkkk kkkkkkkkk" , "XXXXXXXXX XXXXXXXXXX hjkkkkkk kkkkkkkkkkkk kkkkkkkkkkkkk kkkkkkkkk kkkkkkkkkkk kkkkkkkkk kkkkkkkkk" , "XXXXXXXXX XXXXXXXXXX hjkkkkkk kkkkkkkkkkkk kkkkkkkkkkkkk kkkkkkkkk kkkkkkkkkkk kkkkkkkkk kkkkkkkkk" , "XXXXXXXXX XXXXXXXXXX hjkkkkkk kkkkkkkkkkkk kkkkkkkkkkkkk kkkkkkkkk kkkkkkkkkkk kkkkkkkkk kkkkkkkkk" };
    int mImage[] = { R.drawable.ic_sushi , R.drawable.ic_sushi , R.drawable.ic_sushi , R.drawable.ic_sushi , R.drawable.ic_sushi , R.drawable.ic_sushi , R.drawable.ic_sushi , R.drawable.ic_sushi };
    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_menu);

    listView = findViewById(R.id.listview);
    Logout = findViewById(R.id.buttonView);

    Logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(CustMenu.this, LoginActivity.class));
        }
    });


    MyAdapter myAdapter = new MyAdapter(this , mTitle , mDescription , mImage);
    listView.setAdapter(myAdapter);

    }

    class MyAdapter extends ArrayAdapter<String>{

        Context context;
        String rTitle[];
        String rDescription[];
        int rImage[];

        MyAdapter(Context c , String title[] , String description[] , int imgs[] ) {
            super(c , R.layout.row , R.id.textView , title );
            this.context = c ;
            this.rTitle = title ;
            this.rDescription = description ;
            this.rImage = imgs ;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row , parent , false);
            ImageView images = row.findViewById(R.id.imageView);
            TextView nomDuPlat = row.findViewById(R.id.textView);
            TextView Description = row.findViewById(R.id.textView2);

            images.setImageResource(rImage[position]);
            nomDuPlat.setText(rTitle[position]);
            Description.setText(rDescription[position]);

            return row;
        }
    }


}
