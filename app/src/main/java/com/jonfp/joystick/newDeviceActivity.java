package com.jonfp.joystick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class newDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    FileOutputStream fOut = openFileOutput("mainLayout.joy", MODE_APPEND);

                    EditText ipEditText = findViewById(R.id.ipView);
                    EditText nickEditText = findViewById(R.id.nickEditText);

                    String ip = ipEditText.getText().toString();
                    String nickname = nickEditText.getText().toString();

                    fOut.write((nickname + ";" + ip + " ").getBytes());
                    fOut.close();

                    Intent intent = new Intent(newDeviceActivity.this, MainCardActivity.class);
                    startActivity(intent);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}