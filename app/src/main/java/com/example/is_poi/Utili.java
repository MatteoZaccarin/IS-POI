package com.example.is_poi;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class Utili {
    public static String formattaTesto(String dirtyText){
        dirtyText=dirtyText.replace("?","'");

        try{
            byte[] bytes= dirtyText.getBytes("ISO-8859-1");
            Log.d(dirtyText,Arrays.toString(bytes));
            CharsetDetector cd = new CharsetDetector();
            cd.setText(bytes);
            CharsetMatch cm = cd.detect();

            String cleanText= new String(bytes, StandardCharsets.UTF_8);
            return cleanText;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return "";



        /*try {
            byte[] bytes=dirtyText.getBytes("Windows-1252");
            String cleanText=new String(bytes, "UTF-8");
            Log.d("->", cleanText);
            return cleanText;
        }catch (UnsupportedEncodingException e){
            Log.e("errore di codifica", e.getMessage());
        }
        return "";*/
    }
}
