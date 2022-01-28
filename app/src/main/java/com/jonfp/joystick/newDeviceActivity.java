package com.jonfp.joystick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

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
                    EditText ipEditText = findViewById(R.id.ipView);
                    EditText nickEditText = findViewById(R.id.nickEditText);

                    String ip = ipEditText.getText().toString();
                    String nickname = nickEditText.getText().toString();

                    //Write layout in internal storage as file
                    FileOutputStream fOut = openFileOutput("mainLayout.joy", MODE_APPEND);
                    fOut.write((nickname + ";" + ip + " ").getBytes());
                    fOut.close();

                    //Store password in encrypted shared preferences
                    createEncryptedStorage(ip);

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

    public void createEncryptedStorage(String ip){
        EditText passEditText = findViewById(R.id.passView);
        String pass = passEditText.getText().toString();

        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    "encrypted_key-values",
                    masterKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // use the shared preferences and editor as you normally would
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ip, pass);
            editor.commit();

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

}