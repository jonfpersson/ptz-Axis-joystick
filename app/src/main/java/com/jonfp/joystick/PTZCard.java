package com.jonfp.joystick;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class PTZCard {
    public final String CARDVIEW_COLOR = "#FFEB62";

    public PTZCard(String name, String ip, Context context, LinearLayout linearLayout){
        ViewGroup.LayoutParams layoutparams;
        CardView cardview = new CardView(context);
        layoutparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WRAP_CONTENT);

        cardview.setLayoutParams(layoutparams);
        cardview.setRadius(50);

        cardview.setUseCompatPadding(true);
        cardview.setMaxCardElevation(30);
        cardview.setCardBackgroundColor(Color.parseColor(CARDVIEW_COLOR));

        TextView textview = new TextView(context);
        textview.setLayoutParams(layoutparams);
        textview.setText(name);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        textview.setTextColor(Color.BLACK);
        textview.setPadding(110,110,110,110);
        textview.setGravity(Gravity.CENTER);

        cardview.addView(textview);
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SteerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("IP-ADRESS", ip);
                intent.putExtra("NICKNAME", name);
                context.startActivity(intent);
            }
        };

        cardview.setOnClickListener(ocl);
        linearLayout.addView(cardview);

    }
}
