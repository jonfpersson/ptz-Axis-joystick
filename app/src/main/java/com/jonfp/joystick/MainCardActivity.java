package com.jonfp.joystick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainCardActivity extends AppCompatActivity {
    private Context context;
    private CardView cardview;
    private ViewGroup.LayoutParams layoutparams;
    private TextView textview;
    private LinearLayout linearLayout;
    private LayoutHandler layoutHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main_card);
        linearLayout = (LinearLayout)findViewById(R.id.linlay);
        context = getApplicationContext();

        context = MainCardActivity.this;
        addDevicesToActivity();
    }

    public void addNewDevice (View view){
        Intent intent = new Intent(this, newDeviceActivity.class);
        startActivity(intent);
    }

    public void openActivity (View view){
        Intent intent = new Intent(this, SteerActivity.class);
        startActivity(intent);
    }

    public void addDevicesToActivity(){
        layoutHandler = new LayoutHandler(context, linearLayout);
        String[] amountOfCards = layoutHandler.read();

        if(amountOfCards == null)
            return;

        for(int i = 0; i < amountOfCards.length; i++){
            String[] values = amountOfCards[i].split(";");

            PTZCard ptz = new PTZCard(values[0], values[1], context, amountOfCards);
            ptz.addDevice(linearLayout);

        }

    }
}