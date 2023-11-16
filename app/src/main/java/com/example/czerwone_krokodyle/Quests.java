package com.example.czerwone_krokodyle;

import android.renderscript.ScriptGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class Quests {

    private int Id;
    private String Tresc;
    private int Nagroda;
    private int Exp;
    private int Przedzial_Dolny;
    private int Przedzial_Gorny;
    private int Progress;
    private int GeneratedMax;
    public Quests(){

    }
    public void setId(int id) {
        this.Id = id;
    }

    public void setTresc(String tresc) {
        this.Tresc = tresc;
    }

    public void setExp(int exp) {
        this.Exp = exp;
    }
    public void setNagroda(int nagroda) {
        this.Nagroda = nagroda;
    }
    public void setPrzedzial_Dolny(int przedzial_Dolny) {
        this.Przedzial_Dolny = przedzial_Dolny;
    }
    public void setPrzedzial_Gorny(int przedzial_Gorny){
        this.Przedzial_Gorny = przedzial_Gorny;
    }
    public String getTresc() {
        return Tresc;
    }
    public int getPrzedzial_Dolny() {
        return Przedzial_Dolny;
    }
    public int getPrzedzial_Gorny() {
        return Przedzial_Gorny;
    }

    public void setGeneratedMax(int generatedMax) {
        this.GeneratedMax = generatedMax;
    }

    public int getGeneratedMax() {
        return GeneratedMax;
    }

    public void setProgress(int progress){
        if(progress<=GeneratedMax){
        this.Progress = progress;
        }
    }
    public void addProgress(){
        if(Progress<GeneratedMax){
            this.Progress+=1;
        }

    }

    public int getProgress() {
        return Progress;
    }

    public int getId() {
        return Id;
    }
}
