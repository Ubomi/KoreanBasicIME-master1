package com.example.keyboard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class newPre {

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      JSONParser parser = new JSONParser();
      
      Set User_set;
      List Users = new ArrayList();
      List key;
      int height;
      int width;
   
      int backnum = 0;


      try {
         // call the json file
         Object obj = parser.parse(
               new FileReader("C:\\Users\\dbqha\\OneDrive\\바탕 화면\\Keyboard\\Data\\koreanbasicime_0605.json"));
      
         JSONObject data = (JSONObject) obj;

         // Fix the data type
         User_set = data.keySet();
         Users.addAll(User_set);
      
         // user 마다 다 돌기 
      
         for (int i = 0; i < Users.size(); i++) {

            String user = (String) Users.get(i); //user 이름이 담긴 array list 생성
            JSONObject userDB = (JSONObject) data.get(user);
            JSONObject keyboardInfo = (JSONObject)userDB.get("keyboardInfo");
            
            height = ((Long) keyboardInfo.get("Height")).intValue(); 
            width =  ((Long) keyboardInfo.get("Width")).intValue(); 
            
            height = height/4;
            width = width/10;
            
            System.out.println("Height : "+ height + "Width : "+width);
            double threshold = Math.sqrt(Math.pow(height, 2)+ Math.pow(width, 2));
            
            JSONArray keyboardInput = (JSONArray)userDB.get("keyboardInput");

            // Get userDB's each keyboard input
            for (int idx = 0; idx < keyboardInput.size(); idx++) {
               //keyboardInput = userDB.get(idx);
               System.out.println("--------------------------------------");
               System.out.println("User: " + user + " index: " + idx + " backnum: " + backnum);
               
               JSONObject currentInput = (JSONObject)keyboardInput.get(idx);
               
               int codenum = ((Long) currentInput.get("codenum")).intValue();

               // Change the label of "typo"
               if (codenum == -5) {
                  // case : user push the backspace
                  
                  backnum = backnum+1;
                  System.out.println("백스페이스 누른 인덱스 :"+idx);

                  int nxtIndex;
                  nxtIndex = idx + 1;

                  JSONObject nextInput = (JSONObject)keyboardInput.get(nxtIndex);

                  int nxtCodenum = ((Long) nextInput.get("codenum")).intValue();

                  if (nxtCodenum != -5) {
                     if(idx - backnum ==-1) {
                        //case : user push the backspace without pre-keyboard input
                        backnum =0;
                        
                     }else {
                        //case : user finished to erase the keyboard and fix the typo 
                        
                        int typoIndex = idx+1 -2*backnum;
                        JSONObject typoInput;
                        
                        typoInput=(JSONObject) keyboardInput.get(typoIndex); 
                     
                        // Check if the typo input and current input is near by
                        int typoXlocation= ((Long) typoInput.get("touchedX")).intValue();
                        int typoYlocation= ((Long) typoInput.get("touchedY")).intValue();
                        int curXlocation= ((Long) currentInput.get("touchedX")).intValue();
                        int curYlocation= ((Long) currentInput.get("touchedY")).intValue();
                        
                        double distX = typoXlocation-curXlocation;
                        double distY = typoYlocation -curYlocation;
                        
                        double distance = Math.sqrt(Math.pow(distX,2)+Math.pow( distY, 2));
                        System.out.println("distance : " + distance);
                        System.out.println("threshold : " + threshold);
                        
                        if(distance <= threshold) {
                           System.out.println(typoIndex+" false");
                        }
                        backnum = 0;
                     }
                  }
                   
               }else {
                     continue;
                  }
            }
         }

      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (ParseException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   
   }
   
}   
