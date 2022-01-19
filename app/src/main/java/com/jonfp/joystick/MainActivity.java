package com.jonfp.joystick;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void sendUp(View view){
        sendCGI("move=up");
    }

    public void sendLeft(View view){
        sendCGI("move=left");

    }

    public void sendRight(View view){
        sendCGI("move=right");

    }

    public void sendDown(View view){
        sendCGI("move=down");

    }

    public void sendPan(View view){
        EditText tiltEditText = findViewById(R.id.absPan);
        String pan = tiltEditText.getText().toString();
        sendCGI("pan=" + pan);
    }

    public void sendTilt(View view){
        EditText tiltEditText = findViewById(R.id.absTilt);
        String tilt = tiltEditText.getText().toString();
        sendCGI("tilt=" + tilt);
    }

    public void sendCustom(View view){
        EditText customCommand = findViewById(R.id.customCGI);
        String cus = customCommand.getText().toString();
        sendCGI(cus);
    }

    public void sendCGI(String params){
        EditText ipEditText = findViewById(R.id.ipEditText);
        String ip = ipEditText.getText().toString();

        EditText passEditText = findViewById(R.id.passEditText);
        String pass = passEditText.getText().toString();


        if(ip != ""){
            String webPage = "http://" + ip + "/axis-cgi/com/ptz.cgi?" + params;

            String result;
            HttpGetRequest getRequest = new HttpGetRequest();

            try {
                result = getRequest.execute(webPage, pass).get();
                System.out.println(result);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}