package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Determinar si el cronometro esta en ejecucion
    private boolean running;
    //contar los segundos cuando el cronometro esta en ejecucion
    private int seconds = 0;
    //Determinar si el cronometro esta en ejecucion
    private int contador = 0;

    private boolean vuelta;

    private boolean reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        //Invocar hilo para cronometrar
        runTimer();
    }

    public void onClickStart(View view) {
        running=true;
    }

    public void onClickSave(View view) {

        if (running) {
            contador++;
            vuelta = true;
        }
    }

    public void onClickStop(View view) {
        running=false;
    }

    public void onClickReset(View view) {
        running=false;
        reset=true;
        seconds=0;
        contador = 0;
    }

            public void onSaveInstanceState(Bundle savedInstanceState) {
            savedInstanceState.putInt("seconds", seconds);
            savedInstanceState.putBoolean("running", running);
            super.onSaveInstanceState(savedInstanceState);
        }

    private void runTimer(){

        TextView timeView =(TextView) findViewById(R.id.time_view);

        TextView vueltaView =(TextView) findViewById(R.id.vuelta_view);
        //Declarar un handles
        Handler handler = new Handler();
        //Invocar metodo post e instanciar runnable
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);

                if(vuelta){
                    String ride = vueltaView.getText().toString() + "\n" + time;
                    vueltaView.setText(ride);
                    vuelta = false;
                } else{
                    if (reset){
                        vueltaView.setText("");
                        reset = false;
                    }
                }

                if(contador == 5)
                    running = false;

                if(running)
                    seconds ++;

                handler.postDelayed(this,1000);
            }
        });







    }
}