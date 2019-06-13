package com.halbae87.koreanbasicime;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
파이어베이스에 데이터를 넣기위한 클래스
이미 동일한 유저가 존재하는지 안하는지,
존재한다면 마지막 인덱스를 가져와서 새롭게 indexing 할 수 있도록 수정 필요
 */
public class DatabaseManager {
    public static int touchedX;
    public static int touchedY;
    public static long touchTime;
    public static float touchPressure;
    public static float axis_X;
    public static float axis_Y;
    public static float axis_Z;
    public static float touchSize;
    public static int codenum;
    public static String formatDate;
    public static String keyState;
    public static boolean Typo = true;

    public static int index =0;

    public static String S_index;

    public static int width = SoftKeyboard.width;
    public static int height = SoftKeyboard.height;

    public static DatabaseReference mDatabase;
    public static Context sContext;


    public DatabaseManager(int index, int touchedX, int touchedY, long touchTime, float touchPressure, float axis_X, float axis_Y, float axis_Z, float touchSize, int codenum, String formatDate, String keyState,boolean Typo){
        this.index = index;
        this.touchedX = touchedX;
        this.touchedY = touchedY;
        this.touchTime = touchTime;
        this.touchPressure = touchPressure;
        this.axis_X = axis_X;
        this.axis_Y = axis_Y;
        this.axis_Z = axis_Z;
        this.touchSize = touchSize;
        this.codenum = codenum;
        this.formatDate = formatDate;
        this.keyState = keyState;
        this.Typo = Typo;
    }

    public Map<String,Object> toMap(){

        HashMap<String,Object> keyInput = new HashMap<>();

        keyInput.put("index",index);
        keyInput.put("touchedX",touchedX);
        keyInput.put("touchedY",touchedY);
        keyInput.put("touchTime",touchTime);
        keyInput.put("touchPressure",touchPressure);
        keyInput.put("axis_X",axis_X);
        keyInput.put("axis_Y",axis_Y);
        keyInput.put("axis_Z",axis_Z);
        keyInput.put("touchSize",touchSize);
        keyInput.put("codenum",codenum);
        keyInput.put("formatDate",formatDate);
        keyInput.put("keyState",keyState);
        keyInput.put("Typo",Typo);


        return keyInput ;
    }

    public static void writeNewDB(boolean add, int codenum, String keyState){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String,Object> childUpdates = new HashMap<>();
        Map<String,Object> keyboardInfo = new HashMap<>();

        keyboardInfo.put("Height",height);
        keyboardInfo.put("Width",width);

        Map<String, Object> postValues = null;
        Map<String, Object> prePostValues = null;

        touchedX = SoftKeyboard.touchedX;
        touchedY = SoftKeyboard.touchedY;
        touchTime = SoftKeyboard.touchTime;
        touchPressure = SoftKeyboard.touchPressure;
        axis_X = SoftKeyboard.axis_X;
        axis_Y = SoftKeyboard.axis_Y;
        axis_Z = SoftKeyboard.axis_Z;
        touchSize = SoftKeyboard.touchSize;
        formatDate = SoftKeyboard.formatDate;
        Typo = true;

        if(add){
            DatabaseManager post = new DatabaseManager(index,touchedX,touchedY,touchTime,touchPressure,axis_X,axis_Y,axis_Z,touchSize,codenum,formatDate,keyState,Typo);
            postValues = post.toMap();
            prePostValues = keyboardInfo;
        }

        S_index = String.valueOf(index);

        childUpdates.put("/"+SoftKeyboard.User +"/"+"keyboardInfo", prePostValues);
        childUpdates.put("/"+SoftKeyboard.User+"/"+"/keyboardInput/"+S_index,postValues);

        //DatabaseReference Reference = mDatabase.child(SoftKeyboard.User).child("keyboardInput");
        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Reference_tmp = Reference.getRoot().child(SoftKeyboard.User).child("keyboardInput");

        Reference_tmp.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
                Object childMap = dataSnapshot.getValue();
                if(childMap instanceof ArrayList) {
                    ArrayList childMap_list = (ArrayList) childMap;
                    while(childMap_list.remove(null));
                    HashMap grandchildMap_list = (HashMap) childMap_list.get(0);
                    index = (int) (long)grandchildMap_list.get("index") + 1;
                    Log.v("datasnapshot", childMap_list.toString());
                    Log.v("Grandchild_idx", grandchildMap_list.get("index").toString());

                } else if(childMap instanceof HashMap) {
                    Map map = (Map) childMap;
                    Iterator it = map.keySet().iterator();
                    String key = (String) it.next();
                    Map extracted_map = (Map) map.get(key);
                    index = (int) (long) extracted_map.get("index") + 1;
                }
            }
            @Override
            public void onCancelled(@android.support.annotation.NonNull DatabaseError databaseError) {
            }
        });

        mDatabase.updateChildren(childUpdates);
    }

}
