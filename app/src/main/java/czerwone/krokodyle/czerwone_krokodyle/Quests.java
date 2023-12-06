package czerwone.krokodyle.czerwone_krokodyle;

import android.content.Context;
import android.media.MediaPlayer;
import android.renderscript.ScriptGroup;
import android.util.Log;

import com.example.czerwone_krokodyle.R;

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
    private boolean isDone;
    private boolean czyWylosowano=false;
    private boolean hadPlayedsound=false;
    private String TrescENG;
    private boolean isClaimed;
    public Quests(){

    }
    public void setId(int id) {
        this.Id = id;
    }
    public void setTrescENG(String tresc){
        this.TrescENG = tresc;
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
    public int getNagroda(){
        return Nagroda;
    }
    public void setPrzedzial_Dolny(int przedzial_Dolny) {
        this.Przedzial_Dolny = przedzial_Dolny;
    }
    public void setPrzedzial_Gorny(int przedzial_Gorny){
        this.Przedzial_Gorny = przedzial_Gorny;
    }
    public String getTresc(String lang) {
        switch (lang){
            case "En":
                return TrescENG;
            case "Pl":
                return Tresc;
        }
        return Tresc;
    }
    public void setCzyWylosowano(){
        czyWylosowano=true;
    }
    public boolean getCzyWylosowano(){
        return czyWylosowano;
    }
    public void resetCzyWylosowano(){
        czyWylosowano=false;
    }
    public int getPrzedzial_Dolny() {
        return Przedzial_Dolny;
    }
    public int getPrzedzial_Gorny() {
        return Przedzial_Gorny;
    }
    public void Claim(){
        this.isClaimed = true;
    }
    public void overrideClaim(boolean claimstate){
        this.isClaimed = claimstate;
    }
    public boolean getClaim(){
        return isClaimed;
    }
    public void setGeneratedMax(int generatedMax) {
        this.GeneratedMax = generatedMax;
    }

    public int getGeneratedMax() {
        return GeneratedMax;
    }
    public void overrideProgress(int progress){
        this.Progress = progress;
    }

    public void setProgress(int progress){
        if(progress<=GeneratedMax){
        overrideProgress(progress);
        }
    }
    public boolean gethadPlayedsound(){
        return this.hadPlayedsound;
    }
    public void setHadPlayedsound(boolean hadplayed){
        this.hadPlayedsound = hadplayed;
    }
    public void checkifDone(){
        if(this.Progress==this.GeneratedMax)
            this.isDone = true;
        else
            this.isDone = false;
        Log.w("QProgress:",String.valueOf(this.Progress));
        Log.w("QGenMax:",String.valueOf(this.GeneratedMax));
    }
    public boolean getisDone(){
        checkifDone();
        Log.w("QProgress:",String.valueOf(this.Progress));
        Log.w("QGenMax:",String.valueOf(this.GeneratedMax));
        return this.isDone;
    }
    public int getProgress() {
        return Progress;
    }

    public int getId() {
        return Id;
    }
}
