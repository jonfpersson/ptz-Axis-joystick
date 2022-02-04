package com.jonfp.joystick;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PTZCard {
    private final String CARDVIEW_COLOR = "#FFEB62";
    private int paddingSpacing = 110;
    private String name;
    private String ip;
    private Context context;
    String[] amountOfCards;
    AlertDialog.Builder alertDialog;

    public PTZCard(String name, String ip, Context context, String[] amountOfCards, AlertDialog.Builder alertDialog){
        this.amountOfCards = amountOfCards;
        this.name = name;
        this.ip = ip;
        this.context = context;
        this.alertDialog = alertDialog;

    }

    public void addDevice(LinearLayout linearLayout){

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
        textview.setPadding(paddingSpacing, paddingSpacing, paddingSpacing, paddingSpacing);
        textview.setGravity(Gravity.CENTER);

        cardview.addView(textview);
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SteerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("IP-ADDRESS", ip);
                intent.putExtra("NICKNAME", name);
                context.startActivity(intent);
            }
        };

        View.OnLongClickListener olcl = new View.OnLongClickListener() {
            public boolean onLongClick(View v) {


                alertDialog.setTitle("Delete entry");
                alertDialog.setMessage("Are you sure you want to delete this entry?");
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeDevice(name + ";" + ip);
                    }
                });
                alertDialog.setNegativeButton(android.R.string.no, null);
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.show();

                return false;
            }
        };

        cardview.setOnClickListener(ocl);
        cardview.setOnLongClickListener(olcl);
        linearLayout.addView(cardview);

    }

    public void removeDevice(String device){

        List<String> list = new ArrayList<>(Arrays.asList(amountOfCards));
        list.remove(device);

        FileOutputStream fOut = null;
        try {
            fOut = context.openFileOutput("mainLayout.joy", Context.MODE_PRIVATE);
            for(int i = 0; i < list.size(); i++){

                fOut.write((list.get(i) + " ").getBytes());
            }


            fOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(context, MainCardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}


