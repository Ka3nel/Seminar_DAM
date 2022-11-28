package com.example.seminar_dam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaInregistrari extends AppCompatActivity {

    ListView lvTranzactii;
    List<Tranzactie> listaTranzactii = new ArrayList<>();
    AdaptorTranzactie adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_inregistrari);

        int size = (int)getIntent().getSerializableExtra("Size");
        for(int i = 0; i < size; i++) {
            Tranzactie tranzactie = (Tranzactie) getIntent().getSerializableExtra("Tranzactie" + i);
            listaTranzactii.add(tranzactie);
        }

        adaptor = new AdaptorTranzactie(this, listaTranzactii);
        lvTranzactii = findViewById(R.id.lv_tranzactii);
        lvTranzactii.setAdapter(adaptor);
    }
}