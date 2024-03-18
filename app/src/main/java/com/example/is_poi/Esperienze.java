package com.example.is_poi;

import com.google.gson.annotations.SerializedName;

/*
RoadBike -> https://dati.veneto.it/export/json/Percorsi-Road-Bike-nel-Veneto.json
Scii di fondo -> https://dati.veneto.it/export/json/Percorsi-Sci-di-Fondo-nel-Veneto.json
Mountain Bike -> https://dati.veneto.it/export/json/Percorsi-Mountain-Bike-nel-Veneto.json
*/

public class Esperienze {
    String Categoria;
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


}
