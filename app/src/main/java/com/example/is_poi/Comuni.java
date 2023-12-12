package com.example.is_poi;

import com.google.gson.annotations.SerializedName;
//https://dati.veneto.it/export/json/SUPERFICIE-TERRITORIALE-IN-KMQ-COMUNI-DEL-VENETO.json
public class Comuni {
    String Comune;
    @SerializedName("Superficie Kmq")
    String superficie;
    @SerializedName("Codice Istat comune (numerico)")
    String codice;

}