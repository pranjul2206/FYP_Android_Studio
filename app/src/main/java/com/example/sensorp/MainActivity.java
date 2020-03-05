package com.example.sensorp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    TextView tv;
    SensorManager stepm;
    SensorManager lightm;
    Sensor stepsinoption1;
    Sensor light;
    float value =0;
    int pieStepVAR=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
        finish();
        tv=findViewById(R.id.textView);

        stepm = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightm = (SensorManager)getSystemService(SENSOR_SERVICE);

        light = lightm.getDefaultSensor(Sensor.TYPE_LIGHT);
        stepsinoption1 = stepm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepm.registerListener(listener_light, light, SensorManager.SENSOR_DELAY_FASTEST);


    }
    SensorEventListener listner_step = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(pieStepVAR==0)
            {
                value=event.values[0];
                pieStepVAR=1;
                event.values[0]=0;

            }
            else{
                tv.setText((event.values[0]-value)+"");
//                        animatedPie.setInnerText((event.values[0]-value)+"");
                //animatedPie.setPercentage();
//                        animatedPie.setPieAngle(((event.values[0]-value)/5));
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    SensorEventListener listener_light = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.values[0] <= 1.0f) {
                if(stepsinoption1 == null);
                else stepm.registerListener(listner_step, stepsinoption1, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
            }else if(event.values[0] >= 5.0f) {
                stepm.unregisterListener(listner_step);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
