package com.example.is_poi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.is_poi.databinding.ActivityNeweventiBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;


public class EventiAggiungiActivity extends AppCompatActivity {
    ActivityNeweventiBinding binding;
    private MainActivityViewModel viewModel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityNeweventiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String comune=getIntent().getStringExtra("comune");
        binding.comune.setText(comune);
        binding.orainizio.setText("12:00");
        binding.orafine.setText("12:00");
        viewModel=MainActivity.viewModel;
        initObservers();
        LocalDate a=LocalDate.now();
        binding.datainizio.setText(a.getDayOfMonth()+"-"+a.getMonthValue()+"-"+a.getYear());
        binding.datafine.setText(a.getDayOfMonth()+"-"+a.getMonthValue()+"-"+a.getYear());

        setAscoltatori();
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void setAscoltatori(){
        binding.datainizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateInizio=new DatePickerDialog(EventiAggiungiActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date=dayOfMonth+"-"+(month+1)+"-"+year;
                        if(Evento.stringToDate(date+" "+binding.orainizio.getText().toString()).isAfter(Evento.stringToDate(binding.datafine.getText().toString()+" "+binding.orafine.getText().toString()))){
                            binding.datafine.setText(date);
                            binding.orafine.setText(binding.orainizio.getText().toString());
                        }
                        binding.datainizio.setText(date);

                    }
                },Integer.parseInt(binding.datainizio.getText().toString().split("-")[2]),Integer.parseInt(binding.datainizio.getText().toString().split("-")[1])-1,Integer.parseInt(binding.datainizio.getText().toString().split("-")[0]));
                dateInizio.show();
            }
        });

        binding.orainizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog time= new TimePickerDialog(EventiAggiungiActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time=hourOfDay+":"+minute;
                        if(Evento.stringToDate(binding.datainizio.getText().toString()+" "+time).isAfter(Evento.stringToDate(binding.datafine.getText().toString()+" "+binding.orafine.getText().toString()))){
                            binding.datafine.setText(binding.datainizio.getText().toString());
                            binding.orafine.setText(time);
                        }
                        binding.orainizio.setText(time);
                    }
                },Integer.parseInt(binding.orainizio.getText().toString().split(":")[0]), Integer.parseInt(binding.orainizio.getText().toString().split(":")[1]), true);
                time.show();
            }
        });

        binding.datafine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateFine=new DatePickerDialog(EventiAggiungiActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date=dayOfMonth+"-"+(month+1)+"-"+year;
                        if(Evento.stringToDate(binding.datainizio.getText().toString()+" "+binding.orainizio.getText().toString()).isAfter(Evento.stringToDate(date+" "+binding.orafine.getText().toString()))){
                            binding.orafine.setText(binding.orainizio.getText().toString());
                        }
                        binding.datafine.setText(date);
                    }
                },Integer.parseInt(binding.datafine.getText().toString().split("-")[2]),Integer.parseInt(binding.datafine.getText().toString().split("-")[1])-1,Integer.parseInt(binding.datafine.getText().toString().split("-")[0]));
                Calendar minDate= Calendar.getInstance();
                minDate.set(Integer.parseInt(binding.datainizio.getText().toString().split("-")[2]),Integer.parseInt(binding.datainizio.getText().toString().split("-")[1])-1,Integer.parseInt(binding.datainizio.getText().toString().split("-")[0]));
                dateFine.getDatePicker().setMinDate(minDate.getTimeInMillis());
                dateFine.show();
            }
        });

        binding.orafine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog time= new TimePickerDialog(EventiAggiungiActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time=hourOfDay+":"+minute;
                        if(Evento.stringToDate(binding.datainizio.getText().toString()+" "+binding.orainizio.getText().toString()).isAfter(Evento.stringToDate(binding.datafine.getText().toString()+" "+time))){
                            Toast.makeText(EventiAggiungiActivity.this, "L'orario selezionato è al di fuori dei limiti", Toast.LENGTH_SHORT).show();
                            binding.orafine.performClick();
                        }else{
                            binding.orafine.setText(time);
                        }
                    }
                },Integer.parseInt(binding.orafine.getText().toString().split(":")[0]), Integer.parseInt(binding.orafine.getText().toString().split(":")[1]), true);
                time.show();
            }
        });

        binding.Invio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Evento e=new Evento();
                if(binding.nomeEvento.getText().toString().isEmpty()){
                    binding.nomeEvento.setError("Compila questo campo!!!!");
                }else{
                    if(binding.via.getText().toString().isEmpty()){
                        binding.via.setError("Compila questo campo!!!");
                    }else{
                        if(binding.comune.getText().toString().isEmpty()){
                            binding.comune.setError("Compila questo campo!!!");
                        }else{
                            e.setNomeEvento(binding.nomeEvento.getText().toString());
                            e.setDataOraInizio(binding.datainizio.getText().toString()+" "+binding.orainizio.getText().toString());
                            e.setDataOraFine(binding.datafine.getText().toString()+" "+binding.orafine.getText().toString());
                            e.setComune(binding.comune.getText().toString());
                            e.setIndirizzo(binding.via.getText().toString());
                            e.setCivico(binding.civico.getText().toString());
                            e.setCreatore(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            e.setDescrizione(binding.descrizione.getText().toString());
                            FirebaseDatabase.getInstance("https://is-poi-default-rtdb.europe-west1.firebasedatabase.app").getReference("eventi").push().setValue(e);
                            Toast.makeText(EventiAggiungiActivity.this, "L'evento è stato aggiunto con successo", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
    }
}
