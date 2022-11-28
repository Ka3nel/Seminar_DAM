package com.example.seminar_dam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Serializable {

    final int COD_ADAUGARE_TRANZACTIE = 100;
    final int COD_MODIFICARE_TRANZACTIE = 200;
    int index_modificare = 0;
    ListView lvTranzactii;
    List<Tranzactie> listaTranzactii = new ArrayList<>();
    ArrayList<String> listaT = new ArrayList<>();
    //ArrayAdapter<Tranzactie> adaptor;
    AdaptorTranzactie adaptor;
    // date pentru querry
    EditText data1, data2;
    DatePicker dp1, dp2;
    DatePickerDialog dpDialog1, dpDialog2;
    Button btnFiltreaza;

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
            index_modificare = i;
            // pentru modificare folosim onStartActivity
            startActivityForResult(intent, COD_MODIFICARE_TRANZACTIE);
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
            startActivityForResult(intent, COD_ADAUGARE_TRANZACTIE);
        });

        // creare pop-up date-picker pentru querry
        data1 = findViewById(R.id.data_inceput);
        data1.setOnClickListener(view ->  {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                dpDialog1 = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dp1 = view;
                                data1.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                dpDialog1.show();
        });

        data2 = findViewById(R.id.data_sfarsit);
        data2.setOnClickListener(view ->  {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                dpDialog2 = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dp2 = view;
                                data2.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                dpDialog2.show();
        });

        btnFiltreaza = findViewById(R.id.btn_filtreaza);
        btnFiltreaza.setOnClickListener(view -> {
            Date date1 = new Date(dp1.getYear() -1900, dp1.getMonth(), dp1.getDayOfMonth());
            Date date2 = new Date(dp2.getYear() -1900, dp2.getMonth(), dp2.getDayOfMonth());

            listaTranzactii.clear();
            listaTranzactii.addAll(
            BugetDB.getInstance(getApplicationContext()).
                    getDaoTranzactie().
                    getAllTimePeriod(date1, date2)
            );
            adaptor.notifyDataSetChanged();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_ADAUGARE_TRANZACTIE && resultCode == RESULT_OK){
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
        if(requestCode == COD_MODIFICARE_TRANZACTIE && resultCode == RESULT_OK){
            if(data != null){
                Tranzactie tranzactie = (Tranzactie) data.getSerializableExtra("tranzactie");

                Log.d("ORIENT", tranzactie.getData().toString());
                listaTranzactii.get(index_modificare).setTranzactie(tranzactie);
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
            startActivityForResult(intent, COD_ADAUGARE_TRANZACTIE);
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

        if(item.getItemId() == R.id.importa_json){

            new Thread(() -> {

                List<Tranzactie> tranzactiiJSON = ServiciuRetea.preiaJson((Constante.urlJson));
                listaTranzactii.addAll(tranzactiiJSON);

                runOnUiThread(() -> {
                    adaptor.notifyDataSetChanged();
                });
            }).start();
        }

        if(item.getItemId() == R.id.importa_xml){

            new Thread(() -> {

                List<Tranzactie> tranzactiiXml = ServiciuRetea.preiaXml((Constante.urlXml));
                listaTranzactii.addAll(tranzactiiXml);

                runOnUiThread(() -> {
                    adaptor.notifyDataSetChanged();
                });
            }).start();
        }

        if(item.getItemId() == R.id.salveazaBD){
            BugetDB.getInstance(getApplicationContext()).
                    getDaoTranzactie().
                    insertAll(listaTranzactii);
        }

        if(item.getItemId() == R.id.restaureazaBD){
            listaTranzactii.addAll(
                    BugetDB.getInstance(getApplicationContext()).
                            getDaoTranzactie().
                            getAll()
            );
            adaptor.notifyDataSetChanged();
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

//tema 10:  filtrare in lista din main pe perioade de timp
//          salvare directa in baza de date la adaugarea sau modificarea unui element din formular
//          adaugare thread pentru a face getInstance din BugetDB