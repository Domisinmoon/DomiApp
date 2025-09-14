package com.example.miappdomi;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private Spinner spinnerEstado;
    private RadioGroup radioGroupCategoria;
    private EditText editTextProgreso;
    private CheckBox checkBoxCompletada;
    private Button btnGuardar;
    private ImageButton imageButtonLogo;
    private RecyclerView recyclerView;


    private ArrayList<String> listaTareas;
    private TareaAdapter tareaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ratingBar = findViewById(R.id.ratingBar);
        spinnerEstado = findViewById(R.id.spinner);
        radioGroupCategoria = findViewById(R.id.radioGroupCategoria);
        editTextProgreso = findViewById(R.id.editTextText2);
        checkBoxCompletada = findViewById(R.id.checkBox);
        btnGuardar = findViewById(R.id.button);
        imageButtonLogo = findViewById(R.id.imageButton);
        recyclerView = findViewById(R.id.recyclerView);


        String[] estados = {"Pendiente", "En progreso", "Completada"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                estados
        );
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapterSpinner);


        listaTareas = new ArrayList<>();
        tareaAdapter = new TareaAdapter(listaTareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tareaAdapter);


        btnGuardar.setOnClickListener(v -> guardarTarea());
    }

    private void guardarTarea() {
        float prioridad = ratingBar.getRating();
        String estado = spinnerEstado.getSelectedItem().toString();


        int selectedId = radioGroupCategoria.getCheckedRadioButtonId();
        String categoria = "";
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            categoria = radioButton.getText().toString();
        }


        String progresoTexto = editTextProgreso.getText().toString();
        if (progresoTexto.isEmpty()) {
            Toast.makeText(this, "Ingresa un valor en progreso", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean completada = checkBoxCompletada.isChecked();


        String resumen = "Categoría: " + categoria +
                " | Estado: " + estado +
                " | Prioridad: " + prioridad +
                " | Progreso: " + progresoTexto +
                (completada ? " ✅" : " ❌");


        Toast.makeText(this, resumen, Toast.LENGTH_LONG).show();


        listaTareas.add(resumen);
        tareaAdapter.notifyDataSetChanged();
    }
}
