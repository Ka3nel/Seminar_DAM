package com.example.seminar_dam;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import java.util.Calendar;
import java.util.Date;

public class AdaugaActivity extends AppCompatActivity {

    RadioGroup rgValuta;
    RadioButton rbEur, rbRon;
    Spinner spTranzactii;
    EditText etSuma, etDescriere;
    DatePicker dpData;
    Button btnAdauga;
    String[] arraySpinner = new String[] {"Venit", "Cheltuieli"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga);

        rgValuta = findViewById(R.id.rg_valuta);
        spTranzactii = findViewById(R.id.sp_tranzactie);
        etSuma = findViewById(R.id.et_suma);
        etDescriere = findViewById(R.id.et_descriere);
        dpData = findViewById(R.id.dp_data);
        btnAdauga = findViewById(R.id.btn_adauga);
        rbEur = findViewById(R.id.rb_eur);
        rbRon = findViewById(R.id.rb_ron);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, arraySpinner);
        spTranzactii.setAdapter(adapter);

        Tranzactie tranzactieExistenta = (Tranzactie) getIntent().getSerializableExtra("TRANZACTIE");

        if(tranzactieExistenta != null) { //daca am primit un obiect deja construit
            etSuma.setText(String.valueOf(tranzactieExistenta.getSuma()));
            etDescriere.setText(tranzactieExistenta.getDescriere());
            if(tranzactieExistenta.getTip() == Tip.Venit)
                spTranzactii.setSelection(adapter.getPosition("Venit"));
            else
                spTranzactii.setSelection(adapter.getPosition("Cheltuieli"));
            if(tranzactieExistenta.getValuta() == Valuta.EUR)
                rbEur.setChecked(true);
            else
                rbRon.setChecked(true);
            //nu puteam folosi getYear(), getMonth() sau getDay() pentru tranzactieExistenta.getData()
            //deoarece metodele erau depreciate
            //solutia a fost sa ma folosesc de tipul de data calendar
            Calendar cal = Calendar.getInstance();
            cal.setTime(tranzactieExistenta.getData());
            dpData.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            //schimbare text buton
            btnAdauga.setText("Salveaza modificari");
            //schimbare titlu activitate
            setTitle(R.string.modifica_activity_name);
        }

        btnAdauga.setOnClickListener(view -> {
            Tranzactie tranzactie = initializeazaTranzactie();

            Intent intent = new Intent();
            intent.putExtra("tranzactie", tranzactie);
            setResult(RESULT_OK,intent);

            finish();
        });


//        Spinner spinner = findViewById(R.id.spinner);
//        spinner.setOnItemSelectedListener(this);
//        ArrayAdapter ad = new ArrayAdapter(this,
//                android.R.layout.simple_spinner_item.);
//        ad.setDropDownViewResource(
//                android.R.layout
//                        .simple_spinner_dropdown_item);
//        spino.setAdapter(ad);
    }

    private Tranzactie initializeazaTranzactie() {
        Tranzactie tranzactie = new Tranzactie();

        tranzactie.setSuma(Double.parseDouble(etSuma.getText().toString()));
        tranzactie.setDescriere(etDescriere.getText().toString());
        tranzactie.setData(new Date(dpData.getYear() -1900, dpData.getMonth(), dpData.getDayOfMonth()));
        if(rgValuta.getCheckedRadioButtonId() == R.id.rb_eur)
            tranzactie.setValuta(Valuta.EUR);
        else
            tranzactie.setValuta(Valuta.RON);
        String tipTranzactie = spTranzactii.getSelectedItem().toString();
        tranzactie.setTip(Tip.valueOf(tipTranzactie));

        return tranzactie;
    }

}