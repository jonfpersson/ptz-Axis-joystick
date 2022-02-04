package com.jonfp.joystick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SteerActivity extends AppCompatActivity {
    String ERROR_MESSAGE = "Error, Could not send CGI request";
    public String receivedIp;
    private String PTZPassword;
    CGISender cgiSender;

    //TODO Add re-saving of password upon exit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steer);

        receivedIp = getIntent().getStringExtra("IP-ADDRESS");
        TextView ipView = findViewById(R.id.ipView);

        if(receivedIp != null)
            ipView.setText(receivedIp);

        readEncryptedCredentials();
        cgiSender = new CGISender(receivedIp, PTZPassword);

    }

    public void readEncryptedCredentials(){
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    "encrypted_key-values",
                    masterKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

            PTZPassword = sharedPreferences.getString(receivedIp, "");

            ((EditText) findViewById(R.id.passEditText)).setText(PTZPassword);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendUp(View view){
        cgiSender.setParams("move=up");
        if(!cgiSender.sendCGIRequest())
            System.out.println(ERROR_MESSAGE);
    }

    public void sendLeft(View view){
        cgiSender.setParams("move=left");
        if(!cgiSender.sendCGIRequest())
            System.out.println(ERROR_MESSAGE);
    }

    public void sendRight(View view){
        cgiSender.setParams("move=right");
        if(!cgiSender.sendCGIRequest())
            System.out.println(ERROR_MESSAGE);
    }

    public void sendDown(View view){
        cgiSender.setParams("move=down");
        if(!cgiSender.sendCGIRequest())
            System.out.println(ERROR_MESSAGE);
    }

    public void sendPan(View view){
        String pan = ((EditText) findViewById(R.id.absPan)).getText().toString();

        cgiSender.setParams("pan=" + pan);
        if(!cgiSender.sendCGIRequest())
            System.out.println(ERROR_MESSAGE);
    }

    public void sendTilt(View view){
        String tilt = ((EditText) findViewById(R.id.absTilt)).getText().toString();

        cgiSender.setParams("tilt=" + tilt);
        if(!cgiSender.sendCGIRequest())
            System.out.println(ERROR_MESSAGE);
    }

    public void sendCustom(View view){
        String cus = ((EditText) findViewById(R.id.customCGI)).getText().toString();

        cgiSender.setParams(cus);
        if(!cgiSender.sendCGIRequest())
            System.out.println(ERROR_MESSAGE);
    }

    public void zoomIn(View view){
        cgiSender.setParams("rzoom=" + "+200");
        if(!cgiSender.sendCGIRequest())
            System.out.println(ERROR_MESSAGE);
    }

    public void zoomOut(View view){
        cgiSender.setParams("rzoom=" + "-200");
        if(!cgiSender.sendCGIRequest())
            System.out.println(ERROR_MESSAGE);
    }

}