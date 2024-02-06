package czerwone.krokodyle.czerwone_krokodyle;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApkChecksum;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.czerwone_krokodyle.R;

public class Achievements {

    private static final String SHARED_PREFS = "sharedPrefs"; //NIE ZMIENIAĆ!
    private int Id;
    private String[] Tresc;
    private String[] Podtytul;
    private String[] Komentarz;
    private int ProgressMax;
    private int CurrentProgress;
    private boolean hasNextStage;
    private boolean czyWidoczne;
    private int EXP;
    private boolean isComplete;
    private int stage;
    private int[] CzapkiID;
    private int[] JedzenieID;
    private int[] JedzenieIlosc;
    private int Hajs;
    private Context context;
    public boolean HasJedzenie = false;
    public boolean hasCzapki = false;
    public Achievements(int id,String[] tresc,String[] podtytul,String[] komentarz,int progressmax,boolean hasnextstage,int exp,Context ctx,int gStage){
        this.context = ctx;
        this.Id = id;
        this.czyWidoczne = true;
        this.ProgressMax = progressmax;
        this.hasNextStage = hasnextstage;
        this.EXP = exp;
        this.stage = gStage;
        this.isComplete=loadIsComplete();
        SetTytul(tresc);
        SetPodTytul(podtytul);
        SetKomentarz(komentarz);
        setCurrentProgress();
    }
    public int getStage(){
        return this.stage;
    }
    public void checkComplete(){
        if(this.isComplete!=true){
            this.isComplete=false;
            if(this.CurrentProgress >= this.ProgressMax){
                this.isComplete = true;
                Claim();
            }
        }
    }
    public boolean getHasNextStage(){
        return this.hasNextStage;
    }
    public boolean getIsComplete(){
        checkComplete();
        return this.isComplete;
    }
    public void setHajs(int money){
        this.Hajs = money;
    }
    public int getHajs(){
        return this.Hajs;
    }
    public void setCzapki(int[] czapki){
        this.CzapkiID = czapki;
        this.hasCzapki = true;
    }
    public void setJedzenie(int[] jedzID,int[] jedzIlosc){
        this.JedzenieID = jedzID;
        this.JedzenieIlosc = jedzIlosc;
        this.HasJedzenie = true;
    }
    public int[] getJedzenieID(){
        return this.JedzenieID;
    }
    public int[] getJedzenieIlosc(){
        return this.JedzenieIlosc;
    }
    public int[] getCzapkiID(){
        return this.CzapkiID;
    }
    public void Override(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("OsiagniecieID"+String.valueOf(this.Id)+"STAGE"+String.valueOf(this.stage)+"IsComplete",false);
        editor.apply();
    }
    public void Claim(){
        MediaPlayer playComplete = MediaPlayer.create(context, R.raw.achievementget);
        playComplete.start();
        playComplete.setOnCompletionListener(mp -> mp.release());
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("OsiagniecieID"+String.valueOf(this.Id)+"STAGE"+String.valueOf(this.stage)+"IsComplete",true);
        Log.d("CLAIMED",String.valueOf(this.Id)+"STAGE"+String.valueOf(this.stage)+"IsComplete");
        editor.apply();
    }
    private boolean loadIsComplete(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        boolean complete = sharedPreferences.getBoolean("OsiagniecieID"+String.valueOf(this.Id)+"STAGE"+String.valueOf(this.stage)+"IsComplete",false);
        Log.d("ISCOMPLETE","OsiagniecieID"+String.valueOf(this.Id)+"STAGE"+String.valueOf(this.stage)+"IsComplete"+String.valueOf(complete));
        return complete;
    }
    private void setCurrentProgress() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        Log.d("ACHIEVE_MEMORY","OsiagniecieID"+String.valueOf(this.Id)+"STAGE"+String.valueOf(this.stage));
        this.CurrentProgress = sharedPreferences.getInt("OsiagniecieID"+String.valueOf(this.Id)+"STAGE"+String.valueOf(this.stage),0);
    }
    public void addProgress(){
        if(CurrentProgress < ProgressMax){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("OsiagniecieID"+String.valueOf(this.Id)+"STAGE"+String.valueOf(this.stage),this.CurrentProgress+1);
            editor.apply();
            this.CurrentProgress = sharedPreferences.getInt("OsiagniecieID"+String.valueOf(this.Id)+"STAGE"+String.valueOf(this.stage),0);
            checkComplete();
        }
    }
    public int getMaxProgress(){
        return this.ProgressMax;
    }
    public void resetProgress(){
        this.CurrentProgress = 0;
    }
    public void setProgress(int x){
        this.CurrentProgress = x;
    }
    public void setCzyWidoczne(boolean czywidoczne){
        this.czyWidoczne = czywidoczne;
    }
    public boolean getCzyWidoczne() {
        return czyWidoczne;
    }
    private void SetTytul(String[] dane){
        this.Tresc = new String[dane.length];
        int i=0;
        while(i<dane.length){
            this.Tresc[i] = dane[i];
            i++;
        }
    }
    private void SetPodTytul(String[] dane){
        this.Podtytul = new String[dane.length];
        int i=0;
        while(i<dane.length){
            this.Podtytul[i] = dane[i];
            i++;
        }
    }
    private void SetKomentarz(String[] dane){
        this.Komentarz = new String[dane.length];
        int i=0;
        while(i<dane.length){
            this.Komentarz[i] = dane[i];
            i++;
        }
    }
    public int getId() {
        return Id;
    }
    public int getCurrentProgress() {
        return CurrentProgress;
    }
    public int getEXP() {
        return EXP;
    }
    public String getKomentarz(String lang) {
        switch (lang){
            case "En":
                if(Komentarz.length<2) return "N/A";
                return Komentarz[1];
            case "Pl":
                if(Komentarz.length<1) return "N/A";
                return Komentarz[0];
        }
        return Komentarz[0];
    }
    public String getPodtytul(String lang) {
        switch (lang){
            case "En":
                if(Podtytul.length<2) return "N/A";
                return Podtytul[1];
            case "Pl":
                if(Podtytul.length<1) return "N/A";
                return Podtytul[0];
        }
        return Podtytul[0];
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
}
