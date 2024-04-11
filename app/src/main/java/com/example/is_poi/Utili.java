package com.example.is_poi;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Utili {
    public static String formattaTesto(String dirtyText){
        dirtyText=dirtyText.replace("?","'");
        try {
            byte[] bytes=dirtyText.getBytes("iso8859-1");
            String cleanText=new String(bytes, "UTF-8");
            Log.d("->", cleanText);
            return cleanText;
        }catch (UnsupportedEncodingException e){
            Log.e("errore di codifica", e.getMessage());
        }
        return "";
    }
}
