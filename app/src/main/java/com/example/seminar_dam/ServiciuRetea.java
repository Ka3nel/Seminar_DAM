package com.example.seminar_dam;

import android.util.JsonReader;
import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiciuRetea {

    public static List<Tranzactie> preiaXml(String adresa) {

        List<Tranzactie> tranzactii = new ArrayList<>();

        try {
            URL url = new URL(adresa);
            InputStream is = url.openStream();//sau url.openConnection pt mai mult control
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, null);

            int eveniment = parser.getEventType();
            String numeElem;
            Tranzactie tranzactie = null;
            String valoare = "";

            while (eveniment != XmlPullParser.END_DOCUMENT) {
                numeElem = parser.getName();

                if (eveniment == XmlPullParser.START_TAG) {
                    if (numeElem.equals("tranzactie")) {
                        tranzactie = new Tranzactie();
                        if (parser.getAttributeValue(0).equals("Venit")) {
                            tranzactie.setTip(Tip.Venit);
                        } else {
                            tranzactie.setTip(Tip.Cheltuieli);
                        }
                    }
                }

                if (eveniment == XmlPullParser.TEXT) {
                    valoare = parser.getText();
                }
                if (eveniment == XmlPullParser.END_TAG) {
                    if (numeElem.equals("suma")) {
                        tranzactie.setSuma(Double.parseDouble(valoare));
                    }
                    if (numeElem.equals("valuta")) {
                        tranzactie.setValuta(Valuta.valueOf(valoare));
                    }
                    if (numeElem.equals("data")) {
                        tranzactie.setData(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(valoare));
                    }
                    if (numeElem.equals("descriere")) {
                        tranzactie.setDescriere(valoare);
                    }
                    if (numeElem.equals("tranzactie")) {
                        tranzactii.add(tranzactie);
                    }
                }

                eveniment = parser.next();
            }

        }catch (IOException | XmlPullParserException | ParseException ex){
            ex.printStackTrace();
        }

        return tranzactii;
    }

    public static List<Tranzactie> preiaJson(String adresa) {

        List<Tranzactie> tranzactii = new ArrayList<>();

        try {
            URL url = new URL(adresa);
            InputStream is = url.openStream();

            String json = new Scanner(is).useDelimiter("\\A").next();
            JSONObject jsonObject = new JSONObject(json);

            JSONArray tranzactiiJson = jsonObject.getJSONObject("buget").getJSONArray("tranzactie");
            Tranzactie tranzactie;

            for(int i=0;i<tranzactiiJson.length();i++){
                JSONObject tranzactieJson = tranzactiiJson.getJSONObject(i);
                tranzactie = new Tranzactie();
                tranzactie.setSuma(tranzactieJson.getDouble("suma"));
                //etc.
                tranzactii.add(tranzactie);
            }

        }catch(IOException | JSONException ex){
            ex.printStackTrace();
        }

        return tranzactii;
    }
}
