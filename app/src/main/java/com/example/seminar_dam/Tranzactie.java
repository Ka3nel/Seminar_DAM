package com.example.seminar_dam;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

enum Tip {Venit, Cheltuieli}
enum Valuta {EUR, RON}

@Entity(tableName = "tranzactii")
public class Tranzactie implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "suma")
    private double suma;

    @ColumnInfo(name = "descriere")
    private String descriere;

    @ColumnInfo(name = "tip")
    private Tip tip;

    @ColumnInfo(name = "valuta")
    private Valuta valuta;

    @ColumnInfo(name = "data")
    private Date data = new Date();

    public Tranzactie() {

    }

    public Tranzactie(double suma, String descriere, Tip tip, Valuta valuta, Date data) {
        this.suma = suma;
        this.data = data;
        this.descriere = descriere;
        this.tip = tip;
        this.valuta = valuta;
    }

    public void setTranzactie(Tranzactie tr) {
        if (tr != null) {
            this.suma = tr.suma;
            this.data = tr.data;
            this.descriere = tr.descriere;
            this.tip = tr.tip;
            this.valuta = tr.valuta;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public Valuta getValuta() {
        return valuta;
    }

    public void setValuta(Valuta valuta) {
        this.valuta = valuta;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "tip=" + tip +
                ", valuta=" + valuta +
                ", suma=" + suma +
                ", data=" + data +
                ", descriere='" + descriere + '\'' +
                '}';
    }
}
