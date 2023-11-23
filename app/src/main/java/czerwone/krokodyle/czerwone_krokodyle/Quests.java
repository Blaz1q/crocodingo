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
    private boolean hadPlayedsound=false;
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
