package czerwone.krokodyle.czerwone_krokodyle;

import android.content.pm.ApkChecksum;

public class Achievements {
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
    public Achievements(int id,String[] tresc,String[] podtytul,String[] komentarz,int progressmax,boolean hasnextstage,int exp){
        this.Id = id;
        this.czyWidoczne = true;
        this.ProgressMax = progressmax;
        this.hasNextStage = hasnextstage;
        this.EXP = exp;
        this.isComplete=false;
        SetTytul(tresc);
        SetPodTytul(podtytul);
        SetKomentarz(komentarz);
    }
    public void checkComplete(){
        this.isComplete = false;
        if(this.CurrentProgress >= this.ProgressMax){
            this.isComplete = true;
        }
    }
    public boolean getHasNextStage(){
        return this.hasNextStage;
    }
    public boolean getIsComplete(){
        return this.isComplete;
    }
    public void setCurrentProgress(int currentProgress) {
        this.CurrentProgress = currentProgress;
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
    public int getProgressMax() {
        return ProgressMax;
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
