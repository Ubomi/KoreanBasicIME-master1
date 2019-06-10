package com.halbae87.koreanbasicime;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class userInput extends View {

/*    public static int touchedX, touchedY;
    String eventType="";

*/
    public userInput(Context context) {
        super(context);
    }
/*
    public boolean onTouch(View v, MotionEvent event){
        touchedX = (int)event.getX();
        touchedY = (int)event.getY();

        Log.d("userXInput", String.valueOf((int)touchedX));
        Log.d("userYInput",String.valueOf((int)touchedY));

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN : eventType = "Action Down";
            case MotionEvent.ACTION_UP : eventType = "Action Up";
        }
        return false;
    }*/

}
