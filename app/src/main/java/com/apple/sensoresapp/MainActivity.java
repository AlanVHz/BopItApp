package com.apple.sensoresapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    int accion, contador;
    boolean izquierda, derecha, adelante, atras;
    TextView textResultado, orientation;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager manager = (SensorManager)getSystemService( SENSOR_SERVICE );
        textResultado = findViewById(R.id.textResultado);
        orientation = findViewById(R.id.orientation);

        manager.registerListener( this,
                manager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER ),
                SensorManager.SENSOR_DELAY_GAME );

        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador = 0;
                gameProcess( true );
            }
        });
    }

    public void gameProcess( boolean res ) {
        if( res ){
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            accion = (int)(Math.random() * 3) + 1;

            if( accion == 1 ){
                MediaPlayer voz = MediaPlayer.create( this, R.raw.izquierda );
                voz.start();
                orientation.setText("Izquierda!");
                izquierda = true;
                derecha = false; adelante = false; atras = false;
            }
            if( accion == 2 ){
                MediaPlayer voz = MediaPlayer.create( this, R.raw.derecha );
                voz.start();
                orientation.setText("Derecha!");
                derecha = true;
                izquierda = false; adelante = false; atras = false;
            }
            if( accion == 3 ){
                MediaPlayer voz = MediaPlayer.create( this, R.raw.atras );
                voz.start();
                orientation.setText("Atras!");
                atras = true;
                derecha = false; adelante = false; izquierda = false;
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // IZQUIERDA+        DERECHA-
        //Log.i("sensor", "EJE x: "+ event.values[0] );

        if( izquierda ){
            int valor = (int) event.values[0];
            if ( valor > 10  ){
                contador++;
                textResultado.setText("Movimientos exitosos: " + contador );
                izquierda = false;
                gameProcess( true );
            }
        }
        if( derecha ){
            int valor = (int) event.values[0];
            if ( valor < -10 ){
                contador++;
                textResultado.setText("Movimientos exitosos: " + contador );
                derecha = false;
                gameProcess( true );
            }
        }

        // ADELANTE+         ATRAS-
        //Log.i("sensor", "EJE y: "+ event.values[1] );

        if( atras ){
            int valor = (int) event.values[1];
            if ( valor < 0 ){
                contador++;
                textResultado.setText("Movimientos exitosos: " + contador );
                atras = false;
                gameProcess( true );
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
