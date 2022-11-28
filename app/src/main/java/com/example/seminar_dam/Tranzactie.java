package com.example.seminar_dam;

import java.io.Serializable;
import java.util.Date;

enum Tip {Venit, Cheltuieli}
enum Valuta {EUR, RON}

public class Tranzactie implements Serializable {
    private double suma;
    private String descriere;
    private Tip tip;
    private Valuta valuta;
    private Date data = new Date();
    public Tranzactie() {

    }

    public Tranzactie(double suma,String descriere,Tip tip,Valuta valuta,Date data) {
        this.suma = suma;
        this.data = data;
        this.descriere = descriere;
        this.tip = tip;
        this.valuta = valuta;
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
