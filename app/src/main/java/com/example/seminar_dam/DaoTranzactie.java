package com.example.seminar_dam;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface DaoTranzactie {
    // pentru inserarea unei inregistrari
    @Insert
    public long insert(Tranzactie tranzactie); // int sau long - returneaza campul id; void - nu returneaza; alte tipuri de date nu pot fi returnate

    // pentru inserarea mai multor inregistrari
    @Insert
    public List<Long> insertAll(List<Tranzactie> tranzactie);

    //selectie toate inregistrarile
    @Query("SELECT * FROM tranzactii")
    public List<Tranzactie> getAll();

    //selectie dupa tipul tranzactiei
    @Query("SELECT * FROM tranzactii WHERE tip = :tip")
    public List<Tranzactie> getAllTip(Tip tip);
    //s.a.m.d. @Querry

    // modificare tranzactie din baza de date cu tranzactia modificata
    //    @Query("UPDATE tranzactii SET suma = :suma, descriere = :descriere, tip = :tip, valuta = :valuta, data = :data WHERE id = :id")
    //    public int updateTranzactie(int id, double suma, String descriere, Tip tip, Valuta valuta, String data);

    //selectie dupa o perioada de timp
     @Query("SELECT * FROM tranzactii WHERE data >= :data_inceput AND data <= :data_sfarsit")
     public List<Tranzactie> getAllTimePeriod(Date data_inceput, Date data_sfarsit);

    //update pentru cand se adauga o tranzactie noua din formular
    @Update
    public int update(Tranzactie tranzactie);
}
