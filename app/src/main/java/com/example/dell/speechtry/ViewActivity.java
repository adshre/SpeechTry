package com.example.dell.speechtry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


/**
 * Created by DELL on 4/5/2017.
 */

public class ViewActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ImageView b1,b2,b3,b4;
    private SeekBar seekbar;
    private TextView tx1,tx2,tx3;
    private Handler myHandler = new Handler();

    public static int oneTimeOnly = 0;

    private double startTime = 0;
    private double finalTime = 0;

    private int forwardTime = 5000;
    private int backwardTime = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        final int number=getIntent().getExtras().getInt("MyKey");
        TextView helloTxt = (TextView)findViewById(R.id.helloTxt);
        if(number==0) {
            helloTxt.setText(readFromFile(getApplicationContext()));
        }
        else if(number==1) {
            helloTxt.setText(readFromFile1(getApplicationContext()));
        }
        else if(number==2) {
            helloTxt.setText(readFromFile2(getApplicationContext()));
        }
        else{
            Toast.makeText(getApplicationContext(),"Lyrics not available",Toast.LENGTH_LONG).show();
        }


        tx1 = (TextView)findViewById(R.id.textView1);
        tx2 = (TextView)findViewById(R.id.textview2);
        tx3 = (TextView)findViewById(R.id.textview3);

        b1 = (ImageView)findViewById(R.id.play);
        b2 = (ImageView)findViewById(R.id.pause);
        b3=(ImageView)findViewById(R.id.back);
        b4=(ImageView)findViewById(R.id.forward);


        tx1.setTextColor(Color.parseColor("#FFFFFF"));
        tx2.setTextColor(Color.parseColor("#FFFFFF"));
        tx3.setTextColor(Color.parseColor("#FFFFFF"));
        helloTxt.setTextColor(Color.parseColor("#FFFFFF"));

        tx1.setText("song.mp3");
    final int[] resID ={R.raw.song,R.raw.song1,R.raw.song2};
        int number1=getIntent().getExtras().getInt("MyKey");
        mediaPlayer = MediaPlayer.create(this,resID[number1]);

        seekbar=(SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(false);

        b1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing sound",Toast.LENGTH_SHORT).show();
                        mediaPlayer.start();
                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                tx3.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                tx2.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );
                seekbar.setProgress((int)startTime);
                myHandler.postDelayed(UpdateSongTime,100);
                b2.setEnabled(true);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausing sound",Toast.LENGTH_SHORT).show();
                        mediaPlayer.pause();
                b2.setEnabled(false);
                b1.setEnabled(true);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(),"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    public  static String readFromFile(Context context) {

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


    public  static String readFromFile1(Context context) {

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

    public  static String readFromFile2(Context context) {

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


    @Override
    public void onBackPressed(){
        mediaPlayer.stop();
   finish();
    }

}


