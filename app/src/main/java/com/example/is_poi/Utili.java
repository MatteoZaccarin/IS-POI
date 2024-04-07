package com.example.is_poi;

import java.io.UnsupportedEncodingException;

public class Utili {
    public static String formattaTesto(String dirtyText){
        dirtyText=dirtyText.replace("?","'");
        try {
            byte[] bytes=dirtyText.getBytes("Windows-1252");
            String cleanText=new String(bytes, "UTF-8");
            return cleanText;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
}
