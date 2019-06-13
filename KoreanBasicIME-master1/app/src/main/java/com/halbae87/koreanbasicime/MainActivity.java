package com.halbae87.koreanbasicime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static String User;
    Toast mToast = null;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        Button start = (Button)findViewById(R.id.button);
        final EditText editText = (EditText)findViewById(R.id.editText);
        final Intent soft_keyboard_intent = new Intent(getApplicationContext(),SoftKeyboard.class);
        startService(soft_keyboard_intent);
        start.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                User = String.valueOf(editText.getText());
                mToast = Toast.makeText(MainActivity.this,""+User +"님, 반갑습니다. \n앱을 꼭! 종료하신 후, 키보드를 사용해주세요 :-)",Toast.LENGTH_LONG);
                mToast.setGravity(Gravity.CENTER | Gravity.CENTER,0,500);
                mToast.show();
                startService(soft_keyboard_intent); // start private keyboatd method
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);

                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }

            
        
        });
    }
}

