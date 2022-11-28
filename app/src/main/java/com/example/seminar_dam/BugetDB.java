package com.example.seminar_dam;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@TypeConverters(Convertor.class)
@Database(entities = {Tranzactie.class}, version = 1)
public abstract class BugetDB extends RoomDatabase {
    // model de proiectare singleton
    private static volatile BugetDB instanta;

    // volatile impreuna cu synchronized de la urmatoarea comanda asigura suportul asincron

    public static synchronized BugetDB getInstance(Context context) {
        if (instanta == null)  {

            instanta = Room.databaseBuilder(context, BugetDB.class, "buget.db")
                    .allowMainThreadQueries()           // doar pentru testare acum
                    .fallbackToDestructiveMigration()   // stergerea versiunii anterioare a bazei de date - se creaza baza de date cu noua versiune
                    // doar pentru testare acum
                    .build();
        }

        return instanta;
    }

    abstract public DaoTranzactie getDaoTranzactie();
}
