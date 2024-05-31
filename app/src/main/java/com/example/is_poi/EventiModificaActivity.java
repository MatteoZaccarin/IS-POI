package com.example.is_poi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.is_poi.databinding.ActivityEventiBinding;
import com.example.is_poi.databinding.ActivityNeweventiBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.http.GET;

public class EventiModificaActivity extends AppCompatActivity {
    private ActivityNeweventiBinding binding;
    private MainActivityViewModel viewModel;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://is-poi-default-rtdb.europe-west1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("eventi");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityNeweventiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String id=getIntent().getStringExtra("id");
        viewModel=MainActivity.viewModel;
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initObservers();
        binding.Invio.setText("Modifica");
        binding.toolbar.setTitle("Modifica Evento");
        setInfo(id);
    }

    public void setInfo(String id){
        myRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Evento e=snapshot.getValue(Evento.class);
                binding.toolbar.setTitle("Modifica: "+e.NomeEvento);
                binding.nomeEvento.setText(e.NomeEvento);
                binding.datainizio.setText(e.DataOraInizio.split(" ")[0]);
                binding.orainizio.setText(e.DataOraInizio.split(" ")[1]);
                binding.datafine.setText(e.DataOraFine.split(" ")[0]);
                binding.orafine.setText(e.DataOraFine.split(" ")[1]);
                binding.via.setText(e.Indirizzo);
                binding.comune.setText(e.Comune);
                binding.civico.setText(e.Civico);
                binding.descrizione.setText(e.Descrizione);
                setAscoltatori(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //fare alert di errore
                Log.e("errore evento", "evento non presente o cancellato");
            }
        });
    }
    private void initObservers() {
        viewModel.getUiState().observe(this, uiState -> {
            if (uiState.comuni != null) {
                // Update adapter
                java.util.List<String> listaComuni=new ArrayList<>();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        binding.getRoot().getContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        uiState.ritornaStringheComuni()
                );
                binding.comune.setAdapter(adapter);
            }
        });
    }
    public void setAscoltatori(String id){
        //ascoltatore sulla data inizio
        binding.datainizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateInizio=new DatePickerDialog(EventiModificaActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        //ascoltatore sulla ora di inizio
        binding.orainizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog time= new TimePickerDialog(EventiModificaActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                DatePickerDialog dateFine=new DatePickerDialog(EventiModificaActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                TimePickerDialog time= new TimePickerDialog(EventiModificaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time=hourOfDay+":"+minute;
                        if(Evento.stringToDate(binding.datainizio.getText().toString()+" "+binding.orainizio.getText().toString()).isAfter(Evento.stringToDate(binding.datafine.getText().toString()+" "+time))){
                            Toast.makeText(EventiModificaActivity.this, "L'orario selezionato è al di fuori dei limiti", Toast.LENGTH_SHORT).show();
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
                e.setNomeEvento(binding.nomeEvento.getText().toString());
                e.setDataOraInizio(binding.datainizio.getText().toString()+" "+binding.orainizio.getText().toString());
                e.setDataOraFine(binding.datafine.getText().toString()+" "+binding.orafine.getText().toString());
                e.setComune(binding.comune.getText().toString());
                e.setIndirizzo(binding.via.getText().toString());
                e.setCivico(binding.civico.getText().toString());
                e.setCreatore(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                e.setDescrizione(binding.descrizione.getText().toString());
                //database.getReference("eventi").push().setValue(e); da cancellare
                database.getReference("eventi/"+id).setValue(e);
                MainActivity.viewModel.fetchEventi();
                Toast.makeText(EventiModificaActivity.this, "L'evento è stato modificato con successo", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
