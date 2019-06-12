package com.halbae87.koreanbasicime;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


public class sensorManage extends Activity implements SensorEventListener {

    /*
    단말기의 기울기를 측정하려면 Activity가 따로 존재해야한다.
    SensorManage Activity는 액티비티이지만 실행해도 화면이 나오지 않는 투명 액티비티이다.
    사용자가 키보드를 킬때마다 액티비티가 구현되지만, 보이지는 않는것 !
     */

    public static Context sContext;
    public static android.hardware.SensorManager sm;
    public Sensor sensor;
    public float axis_X;
    public float axis_Y;
    public float axis_Z;

    public static String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_manage);
        sContext = this;

        User = getIntent().getStringExtra("User");

        if(User != null) {      // save user id to device.
            SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("User",User);
            editor.commit();
        }
        sm = (SensorManager) getSystemService(sContext.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("test",MODE_PRIVATE);
        User = prefs.getString("User","0");
        sm.registerListener((SensorEventListener)sContext,sensor,sm.SENSOR_DELAY_FASTEST);
    }
    public void onPause(){
        super.onPause();
        sm.unregisterListener((SensorEventListener)sContext);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.d("메소드 호출","onSensorChanged 메소드 호출");
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION){
            axis_X = event.values[0];
            axis_Y = event.values[1];
            axis_Z = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}