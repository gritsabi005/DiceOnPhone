package com.example.buttonsapps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private MediaPlayer sound;
    private AudioManager volume;
    private Vibrator vibeIt;
    private SensorManager sensorIt;
    private Sensor accelerateIt;
    private int randomNumberByButton;
    private int randomNumberByShaking;
    private TextView textX;
    private TextView textY;
    private TextView textZ;
    private float shakingDifference = 8f;
    private float nowItsStill = 800f;
    private float previousX =0, previousY = 0, previousZ =0;
    private long recordedTimeBeforeShaken = 0;
    private boolean shaken = false;

    //if the delta is bigger than shakingDifference over 10 times in 5 seconds

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Salim", "onStart: ");
        //sensorIt.unregisterListener(MainActivity.this);
        //sensorIt.registerListener(MainActivity.this, accelerateIt, SensorManager.SENSOR_DELAY_NORMAL);
        if(sensorIt != null && accelerateIt != null ) {
            sensorIt.unregisterListener(MainActivity.this);
            sensorIt.registerListener(MainActivity.this, accelerateIt, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Salim", "onResume: ");
        if(sensorIt != null && accelerateIt != null ) {
            sensorIt.unregisterListener(MainActivity.this);
            sensorIt.registerListener(MainActivity.this, accelerateIt, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Salim", "onRestart: ");
        if(sensorIt != null && accelerateIt != null ) {
            sensorIt.unregisterListener(MainActivity.this);
            sensorIt.registerListener(MainActivity.this, accelerateIt, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Salim", "onPause: ");
        if(vibeIt != null){
            vibeIt.cancel();
        }
        if (sound != null) {
            sound.release();
        }
        if(sensorIt != null){
            sensorIt.unregisterListener(MainActivity.this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Salim", "onStop: ");
        if(vibeIt != null){
            vibeIt.cancel();
        }
        if (sound != null) {
            sound.release();
        }
        if(sensorIt != null){
            sensorIt.unregisterListener(MainActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Salim", "onDestroy: ");
        if(vibeIt != null){
            vibeIt.cancel();
        }
        if (sound != null) {
            sound.release();
        }
        if(sensorIt != null){
            sensorIt.unregisterListener(MainActivity.this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        vibeIt = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            SeekBar soundBar = findViewById(R.id.soundBar);
            volume = (AudioManager) getSystemService(AUDIO_SERVICE);

            //masksoundBar.setMin(volume.getStreamMinVolume(AudioManager.STREAM_MUSIC));
            soundBar.setMax(volume.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            soundBar.setProgress(volume.getStreamVolume(AudioManager.STREAM_MUSIC));

            soundBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    volume.setStreamVolume(AudioManager.STREAM_MUSIC, i, AudioManager.FLAG_VIBRATE);
                    vibeItt();
                    /*Vibrator vibeIt = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if(vibeIt != null && vibeIt.hasVibrator()){
                        vibeIt.vibrate(50);
                    }*/
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            /*Button bOne = findViewById(R.id.button1);
            bOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Sup Guys xx", Toast.LENGTH_LONG).show();
                }
            });

            Button bTwo = findViewById(R.id.button2);
            bTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Not very whatsup", Toast.LENGTH_LONG).show();
                }
            });*/

            //it gives access to sensors
            sensorIt = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            accelerateIt = sensorIt.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorIt.registerListener(MainActivity.this, accelerateIt, SensorManager.SENSOR_DELAY_NORMAL);

            Button bThree = findViewById(R.id.button3);
            bThree.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("DiscouragedApi")
                @Override
                public void onClick(View view) {

                    randomNumberByButton = (int) (1 + Math.random()*6);
                    int randomToast= (int) (1 + Math.random()*3);
                    int randomrandomToast = (int) (1 + Math.random()*2);
                    String random;
                    if(randomToast == 1){
                       if (randomrandomToast == 1) {random = /*"Not my dawg gettin "*/"";} else { random = /*"Pfft. "*/"" ;}
                    } else if (randomToast == 2){
                        if (randomrandomToast == 1) {random = /*"yeah no joke "*/ "";} else { random = /**/"";}
                    } else {
                        if (randomrandomToast == 1) {random = /*" bitch"*/"";} else { random = /*". Burn"*/"";}
                    }
                    String message;
                    if(randomNumberByButton == 1){
                        if(randomToast == 1 || randomToast == 2){
                            message = random + randomNumberByButton; } else { message = randomNumberByButton + random;
                        }
                    }

                    else if (randomNumberByButton % 2 == 0){
                        if(randomToast == 1 || randomToast == 2){
                            message = random + randomNumberByButton; } else { message = randomNumberByButton + random;
                        }
                    }
                    else{
                        if(randomToast == 1 || randomToast == 2){
                            message = random + randomNumberByButton; } else { message = randomNumberByButton + random;
                        }
                    }
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    System.out.println("You got " + randomNumberByButton);

                    vibeItt();
                    play(randomNumberByButton);
                    showPics(randomNumberByButton);
                    //@SuppressLint("DiscouragedApi") int numberOfDice = getResources().getIdentifier("dice" + randomNumber, "drawable", getPackageName());
                    //(R.foo.bar) than by name (e.g. getIdentifier("bar", "foo", null)). More... (Ctrl+F1)
                    //ImageView dicePics = findViewById(R.id.dicePics);
                    //dicePics.setImageResource(getResources().getIdentifier("dice" + randomNumber, "drawable", getPackageName()));
                    /*getIdentifier("dice" + randomNumber, "drawable", getPackageName());*/ // you use integer here so you have to convert all the dices into int
                }
            });
            return insets;
        });
    }

    public void play(int randomNumber){
        int soundIt;

        if (sound != null){
            sound.release();
        }
        if(randomNumber == 1){
            soundIt = R.raw.dice1;
        } else if(randomNumber == 2){
            soundIt = R.raw.dice2;
        } else if(randomNumber == 3){
            soundIt = R.raw.dice3;
        } else if (randomNumber == 4){
            soundIt = R.raw.dice4;
        } else if (randomNumber == 5){
            soundIt = R.raw.dice5;
        } else {
            soundIt = R.raw.dice6;
        }

        sound = MediaPlayer.create(this, soundIt);
        sound.start();
    }

    public void vibeItt(){
        if(vibeIt != null && vibeIt.hasVibrator()){
            vibeIt.vibrate(50);
        }
    }

    public void showPics(int randomNumber){
        ImageView dicePics = findViewById(R.id.dicePics);
        dicePics.setImageResource(getResources().getIdentifier("dice" + randomNumber, "drawable", getPackageName()));

    }

    @SuppressLint("DiscouragedApi")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        String currentXValue = "X is: " + x;
        String currentYValue = "Y is: " + y;
        String currentZValue = "Z is: " + z;

        textX = findViewById(R.id.textX);
        textY = findViewById(R.id.textY);
        textZ = findViewById(R.id.textZ);

        textX.setText(currentXValue);
        textY.setText(currentYValue);
        textZ.setText(currentZValue);

        Log.d("currentX", currentXValue);
        Log.d("currentY", currentYValue);
        Log.d("currentZ", currentZValue);

        View main = findViewById(R.id.main);

        // logic for if the user is shaking the phone, then the phone is vibing
        // else the the shaking is stopping, then the random number is given to the showPics and play()
        // basically, if it records the X goes to over the 10 over and over in the interval time of 500milliseconds, then it is shaking.

        long timeNow = System.currentTimeMillis();

        float changedX = Math.abs(x - previousX); // only using x so the user has to shake it horizontally

        if (changedX > shakingDifference){ // the first if compares the coordinates
            recordedTimeBeforeShaken = timeNow;
            if (!shaken){
                if (sound != null){
                    sound.release();
                }
                shaken = true;
                vibeItt();
                main.setBackgroundColor(0xFF90EE90);
            }
        }
        if (shaken && (timeNow - recordedTimeBeforeShaken >= nowItsStill) && changedX < 150f){ // the second if compares the time
            shaken = false;
            randomNumberByShaking = (int) (1 + Math.random()*6);
            //make the background color white
            vibeItt();
            showPics(randomNumberByShaking);
            play(randomNumberByShaking);
            main.setBackgroundColor(0xFFFFFFFF);
        }

        previousX = x;
        previousY = y;
        previousZ = z;

        float startRecordShaking; // how do I get it?
        float stopShaking; // perhaps after the coordinates difference return to normal number? less than 2?

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Math.abs()
    }
}

