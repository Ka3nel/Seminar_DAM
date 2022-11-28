package com.example.seminar_dam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Calendar;
import java.util.List;

public class AdaptorTranzactie extends ArrayAdapter<Tranzactie> {
    public AdaptorTranzactie(Context context, List<Tranzactie> listaTranzactii) {
        super(context, 0, listaTranzactii);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*
        1. deserializarea machetei
        2. initializare controale macheta
        3. obtinere obiect curent
        4. populare controalelor
         */

        //1
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.elem_lista,
                    parent,
                    false //obiectul adaugat nu dorim sa fie atasat parintelui
            );
        }

        //2
        ImageView ivTip = convertView.findViewById(R.id.iv_tip);
        TextView tvValuta = convertView.findViewById(R.id.tv_valuta);
        TextView tvSuma = convertView.findViewById(R.id.tv_suma);
        TextView tvData = convertView.findViewById(R.id.tv_data);
        TextView tvDescriere = convertView.findViewById(R.id.tv_descriere);

        //3
        Tranzactie tranzactie = getItem(position);

        //4
        if(tranzactie.getTip() == Tip.Cheltuieli) {
            ivTip.setImageResource(R.drawable.cheltuiala);
        }
        else {
            ivTip.setImageResource(R.drawable.venit);
        }
        tvValuta.setText(tranzactie.getValuta().toString());
        tvSuma.setText(String.valueOf(tranzactie.getSuma()));
        //nu puteam folosi getYear(), getMonth() sau getDay() pentru tranzactieExistenta.getData()
        //deoarece metodele erau depreciate
        //solutia a fost sa ma folosesc de tipul de data calendar
        Calendar cal = Calendar.getInstance();
        cal.setTime(tranzactie.getData());
        tvData.setText(cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR));

        tvDescriere.setText(tranzactie.getDescriere());

        return convertView;
    }
}
