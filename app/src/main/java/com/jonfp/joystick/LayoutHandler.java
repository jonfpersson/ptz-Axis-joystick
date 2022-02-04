package com.jonfp.joystick;

import static android.os.ParcelFileDescriptor.MODE_APPEND;

import android.content.Context;
import android.widget.LinearLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LayoutHandler {
    private Context context;
    private LinearLayout linearLayout;
    private String[] amountOfCards;

    public LayoutHandler(Context context, LinearLayout linearLayout){
        this.context = context;
        this.linearLayout = linearLayout;
    }

    public String[] read(){
        String readData = "";

        try {
            int temp;
            FileInputStream inputStream = context.openFileInput("mainLayout.joy");

            while( (temp = inputStream.read()) != -1){
                readData = readData + Character.toString((char) temp);
            }

            inputStream.close();
            if((readData.equals("")) || readData == null)
                return null;

            amountOfCards = readData.split("\\s+");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return amountOfCards;
    }


}
