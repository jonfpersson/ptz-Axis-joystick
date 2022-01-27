package com.jonfp.joystick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

public class MainCardActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.jonfp.joystick.MESSAGE";
    public final String CARDVIEW_COLOR = "#FFEB62";

    Context context;
    CardView cardview;
    ViewGroup.LayoutParams layoutparams;

    TextView textview;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main_card);
        linearLayout = (LinearLayout)findViewById(R.id.linlay);
        context = getApplicationContext();

        readLayout();

    }

    public void addNewDevice (View view){
        Intent intent = new Intent(this, newDeviceActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Opening activity");
        startActivity(intent);

    }


    public void openActivity (View view){
        Intent intent = new Intent(this, SteerActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Opening activity");
        startActivity(intent);
    }

    public void CreateCardView(String name, String ip){
        cardview = new CardView(context);

        layoutparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        cardview.setLayoutParams(layoutparams);
        cardview.setRadius(50);

        cardview.setUseCompatPadding(true);
        cardview.setMaxCardElevation(30);
        cardview.setCardBackgroundColor(Color.parseColor(CARDVIEW_COLOR));

        textview = new TextView(context); textview.setLayoutParams(layoutparams);
        textview.setText(name);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        textview.setTextColor(Color.BLACK);
        textview.setPadding(110,110,110,110);
        textview.setGravity(Gravity.CENTER);

        cardview.addView(textview);

        View.OnClickListener ocl = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCardActivity.this, SteerActivity.class);
                intent.putExtra("IP-ADRESS", ip);
                intent.putExtra("NICKNAME", name);
                startActivity(intent);
            }
        };

        cardview.setOnClickListener(ocl);
        linearLayout.addView(cardview);
    }

    public void readLayout(){
        String readData="";

        try {
            int c;
            FileInputStream inputStream = openFileInput("mainLayout.joy");

            while( (c = inputStream.read()) != -1){
                readData = readData + Character.toString((char) c);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] amountOfCards = readData.split("\\s+");

        if(!(readData.equals("")) && readData != null){

            for(int i = 0; i < amountOfCards.length; i++){
                System.out.println("INFOO: ------------- " + amountOfCards[i]);
                String[] values = amountOfCards[i].split(";");

                CreateCardView(values[0], values[1]);
            }
        }
    }


}