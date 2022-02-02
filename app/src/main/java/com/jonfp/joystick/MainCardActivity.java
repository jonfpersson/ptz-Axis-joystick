package com.jonfp.joystick;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainCardActivity extends AppCompatActivity {
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
        startActivity(intent);
    }

    public void openActivity (View view){
        Intent intent = new Intent(this, SteerActivity.class);
        startActivity(intent);
    }

    public void readLayout(){
        String readData = "";

        try {
            int temp;
            FileInputStream inputStream = openFileInput("mainLayout.joy");

            while( (temp = inputStream.read()) != -1){
                readData = readData + Character.toString((char) temp);
            }

            inputStream.close();
            String[] amountOfCards = readData.split("\\s+");

            if((readData.equals("")) || readData == null)
                return;

            for(int i = 0; i < amountOfCards.length; i++){
                String[] values = amountOfCards[i].split(";");
                PTZCard ptz = new PTZCard(values[0], values[1], context, linearLayout);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}