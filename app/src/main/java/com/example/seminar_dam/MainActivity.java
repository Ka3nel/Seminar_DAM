package com.example.seminar_dam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    final int COD_TRANZACTIE = 100;
    ListView lvTranzactii;
    List<Tranzactie> listaTranzactii = new ArrayList<>();
    ArrayList<String> listaT = new ArrayList<>();
    //ArrayAdapter<Tranzactie> adaptor;
    AdaptorTranzactie adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adaptor=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaTranzactii);
        adaptor = new AdaptorTranzactie(this, listaTranzactii);
        listaTranzactii.add(new Tranzactie(5800, "salariu", Tip.Venit, Valuta.RON, new Date()));
        lvTranzactii = findViewById(R.id.lv_tranzactii);
        lvTranzactii.setAdapter(adaptor);

        lvTranzactii.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, AdaugaActivity.class);
            intent.putExtra("TRANZACTIE", listaTranzactii.get(i));

            // pentru modificare folosim onStartActivity
            startActivityForResult(intent, COD_TRANZACTIE);
        });

        //LV <- ADAPTOR  <- LISTA
        //array adapter sa afisam intr-o lista
        //simple adapter sa afisam mai multe proprietati separate ,exp. imagini
        //array adapter lucreaza cu o singura componenta
        //getView(pozitia) -se apeleaza automat la fiecare element ce trebuie afisat

        //TEMA: sa proiectez macheta pt elementele din lista -cum vreau sa le vad afisate
        //si sa controlez afisarea la

        FloatingActionButton fabAdauga = findViewById(R.id.fab_adauga);

        fabAdauga.setOnClickListener(view -> {
            //de scris secventa de invocare a activitatii adauga
            Intent intent = new Intent(this, AdaugaActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, COD_TRANZACTIE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_TRANZACTIE && resultCode == RESULT_OK){
            if(data != null){
                Tranzactie tranzactie = (Tranzactie) data.getSerializableExtra("tranzactie");
                Log.d("ORIENT", tranzactie.getData().toString());
                listaTranzactii.add(tranzactie);
                listaT.add(tranzactie.getSuma() + " " +
                        tranzactie.getDescriere() + " " +
                        tranzactie.getTip().toString() + " " +
                        tranzactie.getValuta().toString() + " " +
                        tranzactie.getData().toString());
                //notifica lista ca a aparut ceva nou in lista
                adaptor.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_despre){
            //Tema de invocat si activitatea adauga
            Intent intent = new Intent(this, DespreActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.action_adauga){
            //Tema de invocat si activitatea adauga
            Intent intent = new Intent(this, AdaugaActivity.class);
            startActivityForResult(intent, COD_TRANZACTIE);
            return true;
        }
        if(item.getItemId() == R.id.action_inregistrari){
            Intent intent = new Intent(this, ListaInregistrari.class);
            intent.putExtra("Size", listaTranzactii.size());
            for(int i = 0; i < listaTranzactii.size(); i++) {
                intent.putExtra("Tranzactie" + i, listaTranzactii.get(i));
            }
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putStringArrayList("lista_tranzactii",listaT);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        for (String t:savedInstanceState.getStringArrayList("lista_tranzactii")) {
//            Log.d("ORIENT", "onRestoreInstanceState: " + t);
            String[] elemente = t.split(" ", 5);
//            Log.d("ORIENT", "onRestoreInstanceState: ELEMENTE: " + elemente[0]);
//            Log.d("ORIENT", "onRestoreInstanceState: ELEMENTE: " + elemente[1]);
//            Log.d("ORIENT", "onRestoreInstanceState: ELEMENTE: " + elemente[2]);
//            Log.d("ORIENT", "onRestoreInstanceState: ELEMENTE: " + elemente[3]);
//            Log.d("ORIENT", "onRestoreInstanceState: ELEMENTE: " + elemente[4]);
            Tranzactie tranzactie = new Tranzactie(Double.parseDouble(elemente[0]), elemente[1], Tip.valueOf(elemente[2]),
                    Valuta.valueOf(elemente[3]), new Date(elemente[4]));
//            Log.d("ORIENT", "onRestoreInstanceState: " + tranzactie.toString());
            listaTranzactii.add(tranzactie);
            listaT.add(t);
        }

    }
}

