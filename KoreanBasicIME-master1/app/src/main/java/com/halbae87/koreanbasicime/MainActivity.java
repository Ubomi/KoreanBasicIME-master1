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
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.TelephonyManager;
import java.util.UUID;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends Activity {

    /*
    메인화면 만들어주는 메인 액티비티
     */

    public static Context mContext;
    public static SensorManager sensor_manager;
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
        sensor_manager = (SensorManager)getSystemService(mContext.SENSOR_SERVICE); //감각 센서 등록하기
        sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);// 방향센서 특정으로 가져오기

        Button start = (Button)findViewById(R.id.button);
        final EditText editText = (EditText)findViewById(R.id.editText);

        final Intent sort_keyboard_intent = new Intent(getApplicationContext(),SoftKeyboard.class);

        final Intent sensor_intent = new Intent(MainActivity.this, sensorManage.class);

        startService(sort_keyboard_intent);

        start.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                User = String.valueOf(editText.getText());
                User = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID); // get android_id which indicates unique value of android device
                mToast = Toast.makeText(MainActivity.this,""+User +"님, 반갑습니다. \n앱을 꼭! 종료하신 후, 키보드를 사용해주세요 :-)",Toast.LENGTH_LONG);
                mToast.setGravity(Gravity.CENTER | Gravity.CENTER,0,500);
                mToast.show();

                sensor_intent.putExtra("User",User);
                startService(sort_keyboard_intent); // start private keyboatd method
                startActivity(sensor_intent);
                finish();
            }

            
        
        });

        //mContext.stopService(sort_keyboard_intent);
        //
        //Log.d("start activity","here");
    }

}

