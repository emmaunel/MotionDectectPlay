package com.example.oluwakayode.motiondectectplay;

import android.app.Notification;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private static final String TAG = "MainActivity";
    public static final String COUNTER_KEY = "counter";
    private static final String NOTIFICAION_ID = "1";

    private SensorManager manager;
    Sensor accelerometer;

    MediaPlayer player;

    //# of times phone was picked up
    int counter = 0;

    TextView counter_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter_text = findViewById(R.id.counter_text);

        //instance of sensor
        manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        //Getting the specific sensor
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //adding sensor listener
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        if (savedInstanceState != null) {
            String text = savedInstanceState.getString(COUNTER_KEY);
            counter_text.setText(text);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] > -0.4 && event.values[1] > 9.1){
            player = MediaPlayer.create(this, R.raw.spaghet);
            player.start();
            counter+=1;
            counter_text.setText(String.valueOf(counter));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(COUNTER_KEY, counter_text.getText().toString());
    }

    public void TestNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICAION_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Pickup Counter")
                .setContentText(counter_text.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        compat.notify(1, builder.build());
    }
}
