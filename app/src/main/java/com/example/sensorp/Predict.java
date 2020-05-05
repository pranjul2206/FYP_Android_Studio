package com.example.sensorp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

public class Predict extends AppCompatActivity {
    TextView tv;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
        Log.d("message@@","in predicted");
        tv=findViewById(R.id.pressed);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action=event.getAction();
        int keycode=event.getKeyCode();
        switch(keycode){
            case KeyEvent.KEYCODE_VOLUME_UP:{
                if(KeyEvent.ACTION_UP==action){
                    count+=1;
                    tv.setText(count+"");
                }
                break;
            }
            case KeyEvent.KEYCODE_VOLUME_DOWN:{
                if(KeyEvent.ACTION_UP==action){
                    count-=1;
                    tv.setText(count+"");
                }
                break;
            }
        }
        //returning true because we dont want to increase volume
        return true;
//        return super.dispatchKeyEvent(event);
    }
}
