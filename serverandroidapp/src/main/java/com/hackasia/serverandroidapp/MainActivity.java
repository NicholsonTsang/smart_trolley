package com.hackasia.serverandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    final Handler handler = new Handler();
    TextView speedvalue;
    TextView directionvalue;
    Button enableconnect;
    Button disableconnect;
    private boolean end = false; //end of server socket reading the message -> disconnect to the client

    Socket s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedvalue = findViewById(R.id.speedvalue);
        directionvalue = findViewById(R.id.directionvalue);
        enableconnect = findViewById(R.id.enableconnectionbtn);
        disableconnect = findViewById(R.id.disableconnection);

        enableconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end = false;
                startServerSocket();
            }
        });

        disableconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end = true;
            }
        });

    }

    private void startServerSocket(){

        Thread thread = new Thread(new Runnable() {
            private String stringData = null;

            @Override
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(9004);

                    while (!end){
                        s = ss.accept(); //ss , short form of server socket
                        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

                        stringData = input.readLine();
                        updateUI(stringData);
                    }
                    s.close();
                    ss.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void updateUI(final String stringData){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(stringData.trim().length()!=0){
                    speedvalue.setText(stringData);
                }
            }
        });
    }
}
