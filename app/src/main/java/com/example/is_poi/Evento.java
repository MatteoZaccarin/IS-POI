package com.example.is_poi;

import java.time.LocalDateTime;

public class Evento {
    String id;
    String NomeEvento;
    String Indirizzo;
    String Civico;
    String Comune;
    String Creatore;
    String DataOraInizio;
    String DataOraFine;
    String Descrizione;
    String Provincia;
    public Evento(){}

    public Evento(String nomeEvento, String indirizzo, String civico, String comune, String creatore, String dataOraInizio, String dataOraFine, String descrizione, String provincia) {
        NomeEvento = nomeEvento;
        Indirizzo = indirizzo;
        Civico = civico;
        Comune = comune;
        Creatore = creatore;
        DataOraInizio = dataOraInizio;
        DataOraFine = dataOraFine;
        Descrizione = descrizione;
        Provincia = provincia;
    }

    public static String dateToString(LocalDateTime a){
        return a.getDayOfMonth()+"-"+a.getMonthValue()+"-"+a.getYear()+" "+a.getHour()+":"+a.getMinute();
    }
    public static LocalDateTime stringToDate(String localDateTime){
        String data=localDateTime.split(" ")[0];
        String orario=localDateTime.split(" ")[1];
        int giorno=Integer.parseInt(data.split("-")[0]);
        int mese=Integer.parseInt(data.split("-")[1]);
        int anno=Integer.parseInt(data.split("-")[2]);
        int ora=Integer.parseInt(orario.split(":")[0]);
        int minuto=Integer.parseInt(orario.split(":")[1]);
        LocalDateTime a=LocalDateTime.of(anno, mese, giorno, ora, minuto);
        return a;
    }

    public String getDataOraInizio() {
        return DataOraInizio;
    }

    public void setDataOraInizio(String dataOraInizio) {
        DataOraInizio = dataOraInizio;
    }

    public String getDataOraFine() {
        return DataOraFine;
    }

    public void setDataOraFine(String dataOraFine) {
        DataOraFine = dataOraFine;
    }

    public String getNomeEvento() {
        return NomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        NomeEvento = nomeEvento;
    }

    public String getIndirizzo() {
        return Indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        Indirizzo = indirizzo;
    }

    public String getCivico() {
        return Civico;
    }

    public void setCivico(String civico) {
        Civico = civico;
    }

    public String getComune() {
        return Comune;
    }

    public void setComune(String comune) {
        Comune = comune;
    }

    public String getCreatore() {
        return Creatore;
    }

    public void setCreatore(String creatore) {
        Creatore = creatore;
    }
    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String provincia) {
        Provincia = provincia;
    }
}
