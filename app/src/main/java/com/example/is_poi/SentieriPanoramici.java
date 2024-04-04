package com.example.is_poi;

import com.google.gson.annotations.SerializedName;
/*
piste ciclabili -> https://dati.veneto.it/export/json/Percorsi-Ciclabili-Panoramici-nel-Veneto.json
sentieri storici -> https://dati.veneto.it/export/json/Sentieri-Storico-Culturali-e-Panoramici-nel-Veneto.json
sentieri enogastronomici -> https://dati.veneto.it/export/json/Sentieri-Enogastronomici-nel-Veneto.json
*/
public class SentieriPanoramici {
    String Categoria;
    @SerializedName("Link al GPX/KML")
    String link;
    String Titolo;
    String Sintesi;
    String Descrizione;
    @SerializedName("Consigli dell'autore")
    String consigliAutore;
    @SerializedName("Informazioni sulla Sicurezza")
    String informazioniSicurezza;
    String Equipaggiamento;
    @SerializedName("Consigli suggerimenti e link utili")
    String consigli;
    @SerializedName("Punto di partenza")
    String PuntoDiPartenza;
    @SerializedName("Punto di arrivo")
    String PuntoDiArrivo;
    @SerializedName("Tutto l'anno")
    String tuttoAnno;
    String Gennaio;String Febbraio;String Marzo;String Aprile;String Maggio;String Giugno;
    String Luglio;String Agosto;String Settembre;String Ottobre;String Novembre;String Dicembre;

    int image;
}
