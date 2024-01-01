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
    private String[] Tresc;
    private int Nagroda;
    private int Exp;
    private int[] Przedzial;
    private int Progress;
    private int GeneratedMax;
    private boolean isDone;
    private boolean czyWylosowano=false;
    private boolean hadPlayedsound=false;
    private boolean isClaimed;
    public Quests(){

    }
    public void setId(int id) {
        this.Id = id;
    }
    public void setTresc(String[] dane){
        int i=0;
        this.Tresc = new String[dane.length];
        while(i<dane.length){
            this.Tresc[i] = dane[i];
            i++;
        }
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
    public void setPrzedzial(int[] dane){
        Przedzial = new int[dane.length];
        this.Przedzial[0] = dane[0];
        this.Przedzial[1] = dane[1];
    }
    public String getTresc(String lang) {
        switch (lang){
            case "En":
                if(Tresc.length<2) return "N/A";
                return Tresc[1];
            case "Pl":
                if(Tresc.length<1) return "N/A";
                return Tresc[0];
        }
        return Tresc[0];
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
        return Przedzial[0];
    }
    public int getPrzedzial_Gorny() {
        return Przedzial[1];
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

    public int getExp() {
        return Exp;
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
