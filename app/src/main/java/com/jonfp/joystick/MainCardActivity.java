package com.jonfp.joystick;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainCardActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.jonfp.joystick.MESSAGE";

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

    }


    public void addNewDevice (View view){
        /*Intent intent = new Intent(this, newDeviceActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Opening activity");
        startActivity(intent);*/
        CreateCardViewProgrammatically();

    }

    public void openActivity (View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Opening activity");
        startActivity(intent);
    }

    public void CreateCardViewProgrammatically(){
        cardview = new CardView(context);

        layoutparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        cardview.setLayoutParams(layoutparams);
        cardview.setRadius(50);
        cardview.setPadding(0, 90, 0, 0);
        cardview.setCardBackgroundColor(Color.parseColor("#FFEB62"));

        cardview.setContentPadding(20, 10, 20, 10);
        cardview.setMaxCardElevation(50);
        cardview.setCardElevation(90);

        textview = new TextView(context); textview.setLayoutParams(layoutparams);
        textview.setText("CardView Programmatically");
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        textview.setTextColor(Color.BLACK);
        textview.setPadding(25,25,25,25);
        textview.setGravity(Gravity.CENTER);


        cardview.addView(textview);
        linearLayout.addView(cardview);
    }

}