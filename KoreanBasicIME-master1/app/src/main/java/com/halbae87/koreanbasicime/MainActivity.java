package com.halbae87.koreanbasicime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    /*
    메인화면 만들어주는 메인 액티비티
     */

    public static Context mContext;
    public static SensorManager sm;
    public Sensor sensor;
    public float axis_X;
    public float axis_Y;
    public float axis_Z;
    public static String User;

    Toast mToast =null;
    String mToaststr;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        mContext = this;
        // Sensor part
        sm = (SensorManager)getSystemService(mContext.SENSOR_SERVICE); //감각 센서 등록하기
        sensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);// 방향센서 특정으로 가져오기

        Button start = (Button)findViewById(R.id.button);
        final EditText editText = (EditText)findViewById(R.id.editText);

        final Intent intent = new Intent(getApplicationContext(),SoftKeyboard.class);

        final Intent sensorintent = new Intent(MainActivity.this, sensorManage.class);

        startService(intent);

        start.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                User= String.valueOf(editText.getText());
                sensorintent.putExtra("User",User);
                startService(intent);

                startActivity(sensorintent);

                mToast = Toast.makeText(MainActivity.this,"                        "+User +"님, 반갑습니다. \n앱을 꼭! 종료하신 후, 키보드를 사용해주세요 :-)",Toast.LENGTH_LONG);
                mToast.setGravity(Gravity.CENTER | Gravity.CENTER,0,500);
                mToast.show();

                //finish();
            }
        });

        //mContext.stopService(intent);
        //
        //Log.d("start activity","here");
    }

}

