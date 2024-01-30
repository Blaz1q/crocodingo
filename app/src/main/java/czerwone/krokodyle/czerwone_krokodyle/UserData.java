package czerwone.krokodyle.czerwone_krokodyle;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.example.czerwone_krokodyle.R;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class UserData {
    private static final String FAILED_TESTS = "FAILED_TESTS";
    private static final String PASSED_TESTS = "PASSED_TESTS";
    private static final String LANG = "LANG";
    private static final String KOLOFORTUNY = "KOLOFORTUNY";
    private static final String CHECKEDLANG = "CHECKEDLANG";
    private static final String STREAK = "STREAK";
    private static final String STREAK_POJEDYNCZE = "STREAK_POJEDYNCZE"; //do questow;
    private static final String BEST_STREAK = "BEST_STREAK";
    private static final String MONEY = "MONEY";
    private static final String FOOD = "FOOD";
    private static final String CURRENT_ITEM_ID = "CURRENT_ITEM_ID";
    private static final String USERNAME = "USERNAME";
    private static final String LAST_FEED = "Feeding_time";
    private static final String SAVED_QUEST_DATE = "QUEST_DATE";
    private static final String SAVED_DAIlY_DATE = "DAILY_DATE";
    private static final String QUEST_PROGRESS = "QUEST_PROGGRES_"; //tutaj 3 zmienne int
    private static final String QUEST_CLAIM = "QUEST_CLAIM_"; //tutaj 3 zmienne int
    private static final String SAVED_DATE = "Date";
    private static final String SWITCH = "switch1";
    private static final String SWITCHWIBRACJE = "switchwibracje";
    private static final String VOLUME = "VOLUME";
    private static final String TUTORIAL = "TUTORIAL";
    private static final String TUTORIALTESTY = "TUTORIALTESTY";
    private static final String PURCHASED_CZAPKI = "purchased_czapki";
    private static final String SAVED_LEVEL = "SAVED_LEVEL";
    private static final String SAVED_EXP = "SAVED_EXP";
    private final SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "sharedPrefs"; //NIE ZMIENIAĆ!
    private final SharedPreferences.Editor editor;

    private final Context context;
    private int Money;
    private int Exp;
    private int Level;
    private int LastOpened;
    private int Food;
    private String Username;
    private String Lang;
    private Boolean IsMusicOn;
    private int Streak;
    private int Zdane;
    private int Niezdane;
    private int Volume;
    public int CrocoState;
    /**
     Wartości Crocostate
     0 === żywy
     1 === głodny
     2 === martwy
     */
    private boolean czy_zakrencil;
    private final String currentDate = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
    UserData(Context ctx){
        this.context = ctx;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        UpdateValues();
    }
    public boolean getCzy_zakrencil(){
        return this.czy_zakrencil;
    }
    public void Zakrec(){
        this.czy_zakrencil = true;
        editEditor(KOLOFORTUNY,true);
    }
    private void UpdateValues(){
        this.Money = sharedPreferences.getInt(MONEY, 0);
        this.Food = sharedPreferences.getInt(FOOD, 0);
        this.Lang = sharedPreferences.getString(LANG, "Pl");
        this.czy_zakrencil = sharedPreferences.getBoolean(KOLOFORTUNY,false);
    }
    public void setFood(int value){
        editEditor(FOOD,value);
        UpdateValues();
    }
    public void setMoney(int value){
        editEditor(MONEY,value);
        UpdateValues();
    }
    private void editEditor(String KEY,int value){
        editor.putInt(KEY,sharedPreferences.getInt(KEY,0)+value);
        editor.apply();
    }
    private void editEditor(String KEY,String value){
        editor.putString(KEY,value);
        editor.apply();
    }
    private void editEditor(String KEY,Boolean value){
        editor.putBoolean(KEY,value);
        editor.apply();
    }
    public int getFood(){
        return this.Food;
    }
    public int getMoney() {
        return this.Money;
    }
    public int getDaily(){
        String readableDate = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
        if(sharedPreferences.getString(SAVED_DAIlY_DATE,"N/A").equals("N/A")){
            editEditor(SAVED_DAIlY_DATE,readableDate);
        }
        else{
            readableDate = sharedPreferences.getString(SAVED_DAIlY_DATE,readableDate);
        }
        if(!readableDate.equals(currentDate)){
            Log.w("readable",readableDate);
            Log.w("readable",currentDate);
            readableDate = currentDate;
            editEditor(SAVED_DAIlY_DATE,readableDate);
            this.czy_zakrencil=false;
            editEditor(KOLOFORTUNY,false);
            return 1;
            //ResetAllQuestProgress();
            //UpdateQuestDate(); TODO
        }
        return 0;
    }
    public void ResetAllQuestProgress(List<Quests> listaQuestow){
        Log.d("PROGRESSRESET","RESET");
        for (int i = 0; i < listaQuestow.size(); i++) {
            listaQuestow.get(i).setProgress(0);
            listaQuestow.get(i).setHadPlayedsound(false);
            listaQuestow.get(i).overrideProgress(0);
            listaQuestow.get(i).resetCzyWylosowano();
            listaQuestow.get(i).checkifDone();
            editor.putBoolean(QUEST_CLAIM+ i,false);
            listaQuestow.get(i).overrideClaim(sharedPreferences.getBoolean(QUEST_CLAIM+ i,false));
            editor.putInt(QUEST_PROGRESS+ i,0);
        }
        editor.apply();
    }
    public String getLang() {
        return this.Lang;
    }
    public int UpdateQuestDate(List<Integer> listaQuestowId,List<Quests> listaQuestow,boolean zaladowano_questy){
        listaQuestowId.clear();
        if(zaladowano_questy){
            int i=0;
            Random daily_qnum_seed = new Random(Integer.valueOf(currentDate));
            for (Quests quest : listaQuestow) {
                Log.d("MojaAplikacja", "ID: " + quest.getId() + ", Tresc: " + quest.getTresc(getLang()));
                int max = daily_qnum_seed.nextInt(quest.getPrzedzial_Gorny()-quest.getPrzedzial_Dolny()+1)+quest.getPrzedzial_Dolny();
                quest.setGeneratedMax(max);
                listaQuestowId.add(i);
                i++;
            }
            Collections.shuffle(listaQuestowId,new Random(Integer.valueOf(currentDate)));
            for(int j=0;j<3;j++){
                Quests quest = listaQuestow.get(listaQuestowId.indexOf(j));
                quest.setCzyWylosowano();
                listaQuestow.set(listaQuestowId.indexOf(j),quest);
            }
        }else{
            return -1;
            //loadQuestsFromAsset();
        }
        Log.d("QID",String.valueOf(listaQuestowId));
        return 0;
    }
    public void loadQuestProgress(List<Quests> listaQuestow){
        for(int i=0;i<listaQuestow.size();i++){
            int questv = sharedPreferences.getInt(QUEST_PROGRESS+ i,0);
            listaQuestow.get(i).setProgress(questv);
            Log.d("QPROGRESS",String.valueOf(listaQuestow.get(i).getisDone()));
        }
    }
    public void addProgress(int id,List<Quests> listaQuestow){
        int questv = sharedPreferences.getInt(QUEST_PROGRESS+String.valueOf(id),0);
        if(listaQuestow.get(id).getGeneratedMax()>questv){
            listaQuestow.get(id).setProgress(questv+1);
            editor.putInt(QUEST_PROGRESS+String.valueOf(id),questv+1);
            Log.d("QUESTVALUEACTUAL",String.valueOf(listaQuestow.get(id).getProgress()));
            questPlaySound(listaQuestow.get(id));
        }else{
            //editor.putInt(QUEST_PROGRESS+String.valueOf(id),0); //debug
        }
        editor.apply();
    }
    public void questPlaySound(Quests q){
        Log.w("qsound","1");
        if(q.getisDone()){
            if(q.getCzyWylosowano()){
                if(!q.gethadPlayedsound()){
                    MediaPlayer playComplete = MediaPlayer.create(context, R.raw.questcomplete);
                    playComplete.start();
                    playComplete.setOnCompletionListener(mp -> mp.release());
                    q.setHadPlayedsound(true);
                }
            }
        }
    }
}
