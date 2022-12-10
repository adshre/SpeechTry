package com.example.dell.speechtry;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.R.attr.data;
import static android.R.attr.label;
import static com.example.dell.speechtry.ViewActivity.readFromFile;

public class MainActivity extends AppCompatActivity {
    private final int SPEECH_RECOGNITION_CODE = 1;
    private TextView txtOutput;
    private ImageButton btnMicrophone;
    private TextView Match;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Match=(TextView)findViewById(R.id.match);
        Match.setTextColor(Color.parseColor("#FFFFFF"));
        txtOutput = (TextView) findViewById(R.id.txt_output);
        btnMicrophone = (ImageButton) findViewById(R.id.btn_mic);
        btnMicrophone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startSpeechToText();

            }
        });
    }

    public void search(View v) {

        String textToSaveString = txtOutput.getText().toString();
        writeToFile(textToSaveString);

        String textFromFileString = readFromFile(getApplicationContext());
        String textFromFileString1 = readFromFile1(getApplicationContext());
        String textFromFileString2 = readFromFile2(getApplicationContext());




        if (textFromFileString.contains(textToSaveString.replace("\r\n", " ").replace("\n", " "))) {
            Toast.makeText(getApplicationContext(), "both lyrics matched", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ViewActivity.class);
            intent.putExtra("MyKey",0);
            startActivity(intent);

        }else if(textFromFileString1.contains(textToSaveString.replace("\r\n", " ").replace("\n", " "))){
            Toast.makeText(getApplicationContext(), "both lyrics matched", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ViewActivity.class);
            intent.putExtra("MyKey",1);
            startActivity(intent);
        }
        else if(textFromFileString2.contains(textToSaveString.replace("\r\n", " ").replace("\n", " "))){
            Toast.makeText(getApplicationContext(), "both lyrics matched", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ViewActivity.class);
            intent.putExtra("MyKey",2);
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(), "both lyrics did not matched", Toast.LENGTH_SHORT).show();



    }

    /**
     * to pop google dialogue*
     */
    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);

        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * to display output for that result code *
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    txtOutput.setText(text);


                }
                break;
            }

        }


    }

    /*converts text spoken to speech.txt to compare */
    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("speech.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "file write failed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static String readFromFile(Context context) {
        try {
            InputStream is = context.getAssets().open("MyLyric1.txt");
            int size = is.available();
            byte buffer[] = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String readFromFile1(Context context) {
        try {
            InputStream is = context.getAssets().open("MyLyric2.txt");
            int size = is.available();
            byte buffer[] = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String readFromFile2(Context context) {
        try {
            InputStream is = context.getAssets().open("MyLyric3.txt");
            int size = is.available();
            byte buffer[] = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

