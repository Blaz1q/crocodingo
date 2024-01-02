/* Aktualna wersja: ALPHA-1.4.2
- popwawiłem wygląd użytkownika
- poprawiłem logike
- poprawiłem wylgąd końcu testów
przyszłe wersje:
-Powiadomienia
* */
package czerwone.krokodyle.czerwone_krokodyle;

import android.app.AlertDialog;
import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.czerwone_krokodyle.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import ru.noties.jlatexmath.JLatexMathDrawable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener{
    Czapka czapka;
    private List<PytaniaDB> listaPytan = new ArrayList<>();
    private List<Czapka> listaCzapek = new ArrayList<>();
    private List<Jedzenie> listaZarcia = new ArrayList<>();
    private List<Potka> listaPotek = new ArrayList<>();
    private List<Quests> listaQuestow = new ArrayList<>();
    private List<Integer> listaQuestowId = new ArrayList<>();
    // zmienne do akcji
    private List<String> actions = new ArrayList<>();
    int actionsindex = 0;
    AlertDialog wyjscie;
    private JLatexMathDrawable Test_Math_drawable;
    public JLatexMathDrawable[] CurrentQuestion = new JLatexMathDrawable[4];
    public String[] StringCurrentQuestion = new String[4];
    private math_syntax Math_syn = new math_syntax();
    int[] radioButtonsIds = {
            R.id.radioLangPl,
            R.id.radioLangEng,
            R.id.radioLangEsp
    };
    String defaultUsername = "SuperKrokodyl26";
    //zabezpieczenia
    boolean zaladowano_czapki = false;
    boolean zaladowano_questy = false;
    boolean zaladowano_jedzenie = false;
    boolean isPopupVisible = false;
    boolean zaladowanodb = false; //tylko w przypadku gdy nie ma połączenia z bazą
    String[] LitABCD = {"A","B","C","D"};
    final int color_correct = 0xff88d18a;
    final int color_text_correct = 0xff0d1b15;
    final int color_incorrect = 0xfff52720;
    final int color_text_incorrect = 0xff270302;
    final int wartosc_ciastka = 7000; //2 h (prawie)
    List<Integer> idList = new ArrayList<Integer>();
    List<Integer> ShuffledArray = new ArrayList<Integer>();
    List<Integer> AnswerList = new ArrayList<Integer>();
    int AnswerListPoj;
    List<String> CorrectAnswerList = new ArrayList<String>();
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount acct;
    TextView login_name;
    FirebaseDatabase database;
    ImageView czerwony_krokodyl;
    FirebaseAuth auth;
    Runnable runnable;
    Runnable runnable2;
    Runnable runnable3;
    Handler handler = new Handler();
    Handler handler2 = new Handler();
    Handler handler3 = new Handler();
    boolean czyZalogowany = false;
    Switch sw1;
    Switch sw2;
    int radioButtonChecked = 0;
    int currentVol = -1;
    int current_item_index=-1;
    int POPUP_EXCEPTION_MODE=0;
    int POPUP_RESOLUTION=0; // 0 - normalny, 1 - fullscreen
    String czapkiilosc="";
    String[] Wynik_msg = {
            "Niestety, nie udało się tym razem.\nAle nie przejmuj się, poćwicz dalej.\nCrocodingo w Ciebie wierzy. Razem możecie zdziałać cuda!",
            "Poszło Ci nawet dobrze, ale Croco wie, że stać Cię na więcej.\nCrocodingo zaprasza na zajęcia! Ding dong! Dzwonek na lekcje!",
            "Wow! Poszło Ci naprawdę dobrze! Crocodingo chce abyś jeszcze troszkę z nim poćwiczył.\nCroco wie, że możesz stać się Mistrzem Matematyki!",
            "Croco jest pod wrażeniem. Mało kto dociera do poziomu Mistrza takiego jak Crocodingo.\nCzy to czas oddać swój tytuł Croco?",
            "Crocodingo uważa, że jesteś gotowy.\nJesteś gotowy na walkę o przetrwanie, na walkę o godność, na walkę z Najtrudniejszym Przeciwnikiem...\nJesteś gotowy na Maturę z Matematyki!"
    };
    // ustawienia
    public Boolean switchstate = true;
    public Boolean switchstatewibracje = false;
    int SET_TEST_ID = 1;
    int gloduje_co = -14400;
    int umiera_po = -172800;
    MediaPlayer bgsong;
    MediaPlayer buttonsong;
    long currentTime = new Date().getTime();
    String currentDate = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
    Random daily_qnum_seed;
    long lastFeed = 0;
    long savedTime = 0;
    int exp;

    public static final String FAILED_TESTS = "FAILED_TESTS";
    public static final String PASSED_TESTS = "PASSED_TESTS";
    public static final String LANG = "LANG";
    public static final String CHECKEDLANG = "CHECKEDLANG";
    public static final String STREAK = "STREAK";
    public static final String STREAK_POJEDYNCZE = "STREAK_POJEDYNCZE"; //do questow;
    public static final String BEST_STREAK = "BEST_STREAK";
    public static final String MONEY = "MONEY";
    public static final String FOOD = "FOOD";
    public static final String CURRENT_ITEM_ID = "CURRENT_ITEM_ID";
    public static final String USERNAME = "USERNAME";
    public static final String LAST_FEED = "Feeding_time";
    public static final String SAVED_QUEST_DATE = "QUEST_DATE";
    public static final String QUEST_PROGRESS = "QUEST_PROGGRES_"; //tutaj 3 zmienne int
    public static final String QUEST_CLAIM = "QUEST_CLAIM_"; //tutaj 3 zmienne int
    public static final String SAVED_DATE = "Date";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH = "switch1";
    public static final String SWITCHWIBRACJE = "switchwibracje";
    public static final String VOLUME = "VOLUME";
    public static final String TUTORIAL = "TUTORIAL";
    public static final String TUTORIALTESTY = "TUTORIALTESTY";
    private static final String PURCHASED_CZAPKI = "purchased_czapki";
    private static final String SAVED_LEVEL = "SAVED_LEVEL";
    private static final String SAVED_EXP = "SAVED_EXP";
    private static final String final_connection= "https://jncrew.5v.pl/androidAPI.php";
    String kupioneczapkibool="";
    Dialog dialog;
    Dialog myDialog;
    Dialog TopBar;
    Boolean showonce = false;
    final int[] sectors = {1,2,3,4,5,6,7,8,9,10};
    final int[] sectorDegrees = new int[sectors.length];

    int randomSectorIndex = 0;

    boolean spinning = false;
    int earningsRecord = 0;
    Random random = new Random();
    Runnable loadingrunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);  /* <----- Tutaj ustawić główny widok aplikacji */
        //Toast.makeText(getApplicationContext(), "onload", Toast.LENGTH_SHORT).show();
        bgsong = MediaPlayer.create(MainActivity.this,R.raw.cktheme_1);
        login_name = findViewById(R.id.usernick);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        acct = GoogleSignIn.getLastSignedInAccount(this);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        LoadData();
        UpdateLocale();
        loadJSONFromAsset();
        loadQuestsFromAsset();
        SaveQuestDate();
        loadQuestProgress();
        loadJedzenieFromAsset();
        loadPotkiFromAsset();
        fetchData(0);
        RelativeLayout bgimg = findViewById(R.id.MAIN_LOADING_BG);
        ImageView ckrkdl = findViewById(R.id.krokodyl_loading);
        try{
            YoYo.with(Techniques.Shake).duration(1000).repeat(2).playOn(ckrkdl);
            new Handler(getMainLooper()).postDelayed(() -> {
                bgimg.setBackground(getResources().getDrawable(R.drawable.cktlo_loading2));
                //YoYo.with(Techniques.Flash).duration(200).playOn(bgimg);
                new Handler(getMainLooper()).postDelayed(() -> {
                    //YoYo.with(Techniques).duration(200).playOn(bgimg);
                    bgimg.setBackground(getResources().getDrawable(R.drawable.cktlo_loading3));
                    new Handler(getMainLooper()).postDelayed(() -> {
                        bgimg.setBackground(getResources().getDrawable(R.drawable.cktlo));
                        TextView cosiedzieje = findViewById(R.id.cosiedzieje);
                        cosiedzieje.setText("Uruchamianie");
                        new Handler(getMainLooper()).postDelayed(() -> {
                            setContentView(R.layout.main_page);
                            UstawKrokodyla();
                            Math_syn.set_Math("\\text{rozruch}");
                            updateAccInfo();
                            ResumeOnLongListener();
                            PierwszeUruchomienie();
                            Uruchomienie_animajca();
                            ustawCzapke();
                            handler.removeCallbacks(loadingrunnable);
                        }, 500);
                    }, 500);
                }, 500);
            }, 500);
        } catch (Exception e){
            e.printStackTrace();
        }
        //DebugSetGlodny();
        for(int k=0;k<100;k++){
            LevelEq(k);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Czy chcesz wyjść?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        wyjscie = builder.create();

    }
    @Override
    public void onBackPressed() {
        canplayanimations = false;
        if(actionsindex!=0){
            switch (actions.get(actionsindex-1)){
                case "select_tests":
                    DefaultMainPageActions();
                    break;
                case "pytania_wybor":
                    if(isPopupVisible==false){
                        ShowPopup(R.layout.zakonczenie_testu); //naprawiłem haxa
                    }
                    break;
                case "pytanie_wyglad":
                    setContentView(R.layout.pytania_wybor);
                    Resume_Question_Buttons();
                    RemoveAction();
                    break;
                case "test_final":
                    DefaultMainPageActions();
                    break;
                case "pytanie_wyglad_wyjasnienie":
                    //wróć do pytania_wybor_bledne
                    setContentView(R.layout.pytania_wybor_bledne);
                    Resume_Question_Buttons_Final();
                    RemoveAction();
                    break;
                case "pytania_wybor_bledne":
                    DefaultMainPageActions();
                    break;
                case "userprofile":
                    DefaultMainPageActions();
                    break;
                case "wyjasnienie":
                    //wróć do pytanie_wyglad_wyjasnienie
                    setContentView(R.layout.pytanie_wyglad_wyjasnienie);
                    Set_pytanie_bledne();
                    RemoveAction();
                    break;
                case "sklep":
                    DefaultMainPageActions();
                    break;
                case "pojedyncze_pytanie":
                    DefaultMainPageActions();
                    break;
                case "settings":
                    SaveSettings();
                    DefaultMainPageActions();
                    break;
                case "credits":
                    setContentView(R.layout.settings);
                    UpdateSettings();
                    RemoveAction();
                    break;
                default:
                    wyjscie.show();
                    break;
            }
        }else{
            wyjscie.show();
        }
        czypokazana = false;
        LoadData();
        Wibracje();
    }
    public int LevelEq(int x){
        int val = (int) Math.floor(x*50*x*1.5);
        Log.d("level",String.valueOf(x)+" : "+String.valueOf(val));
        if(x>=0){
            return val;
        }
        return 0;
    }
    public void Wyjdz(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Czy chcesz wyjść?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        wyjscie = builder.create();
        wyjscie.show();
        //ShowPopup(R.layout.wyjscie); //#TODO
    }
    public void ChangeLang(View v){
        if(v.getId()==R.id.radioLangPl){
            setLocale("Pl");
        }
        else if(v.getId()==R.id.radioLangEng){
            setLocale("En");
        }
    }
    public void setLocale(String lang) {
        changeLang(lang);
        Locale myLocale = new Locale(getLang());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        SaveSettings();
        setContentView(R.layout.settings);
        UpdateSettings();
        //Intent refresh = new Intent(this, MainActivity.class);
        //startActivity(refresh);
    }
    public void generateSectorDegrees() {
        int sectorDegree = 360/sectors.length;

        for (int i=0; i<sectors.length; i++){
            sectorDegrees[i] = (i+1)*sectorDegree;
        }
    }
    private float generateRandomDegreeToSpin() {
        Random r = new Random();
        return (360*sectors.length)+r.nextInt(360);
    }
    int deg =0;
    private void spin(){
        ImageView kolopasy = (ImageView) myDialog.findViewById(R.id.kolopaski);
        spinning = true;
        int nagroda;
        int truedeg = 0;
        kolopasy.animate().rotation(deg-12).setInterpolator(new DecelerateInterpolator()).setDuration(500);
        deg+=generateRandomDegreeToSpin();
        truedeg = (deg+4)%360 ; //4 stopnie offsetu
        int switchvalue=0;
        int przedzial = 10;
        int wartosc_dolna;
        int wartosc_gorna;
        Log.d("truedeg",String.valueOf(truedeg));
        for(int i=0;i<przedzial;i++){
            wartosc_dolna = (i*360/przedzial);
            wartosc_gorna = (i+1)*360/przedzial;
            Log.w("przedzialy",String.valueOf(wartosc_dolna)+" "+String.valueOf(wartosc_gorna));
            Log.w("przedzialyif",String.valueOf(truedeg>=wartosc_dolna&&truedeg<wartosc_gorna));
            if(truedeg>=wartosc_dolna&&truedeg<wartosc_gorna){
                switchvalue=i;
            }
        }
        int trueswitchvalue = 10-switchvalue-1;
        switch(trueswitchvalue) {
            case 0:
                nagroda = 100;
                SaveMoney(100);
                break;
            case 1:
                nagroda = 200;
                SaveMoney(200);
                break;
            case 2:
                nagroda = 1000;
                SaveMoney(1000);
                break;
            case 3:
                nagroda = 500;
                SaveMoney(500);
                break;
            case 4:
                nagroda = 5000;
                SaveMoney(5000);
                break;
            case 5:
                nagroda = 300;
                SaveMoney(300);
                break;
            case 6:
                nagroda = 400;
                SaveMoney(400);
                break;
            case 7:
                nagroda = 10;
                SaveMoney(10);
                break;
            case 8:
                nagroda = 0;
                break;
            case 9:
                nagroda = 700;

                SaveMoney(700);
                break;

            default:
                nagroda = 0;
                break;
        }

        //Toast.makeText(this,String.valueOf(trueswitchvalue),Toast.LENGTH_SHORT).show();
        new Handler(getMainLooper()).postDelayed(() -> {
            kolopasy.animate().rotation(deg+12).setInterpolator(new DecelerateInterpolator()).setDuration(10000);
        },500);
        Log.d("deg/",String.valueOf(deg%10));
        new Handler(getMainLooper()).postDelayed(() -> {
            spinning = false;
            try{
                TextView tekst_nagroda = myDialog.findViewById(R.id.nagroda);
                tekst_nagroda.setText(String.valueOf(nagroda));
                Pokaz_Topbar();
                Aktualizuj_Hajs(getMoney()-nagroda,getMoney());
                Aktualizuj_Exp(getExp(),getExp()+10000);
                Ukryj_Topbar();
                AddExp(10000);

            }catch (Exception e){
                e.printStackTrace();
                // #todo remove ability to go back when spinning the wheel
            }

        },10500);
    }
    public void krec(View v){
        if(v.getId()==R.id.obramowanie){
            if(!spinning){
                spin();
            }
        }
    }
    void Uruchomienie_animajca(){
        RelativeLayout relativeLayout = findViewById(R.id.pierwsze_uruchomienie_animacja);
        relativeLayout.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(relativeLayout);
    }
    private void CreatepopUpwindow(int id) {
        dialog=new Dialog(this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(id);
        dialog.show();
    }
    public void PierwszeUruchomienie() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(TUTORIAL, true)) {
        editor.putBoolean(TUTORIAL, false);
        editor.apply();
        CreatepopUpwindow(R.layout.popup_guide);
        RelativeLayout relativeLayout = findViewById(R.id.pierwsze_uruchomienie_animacja);
        relativeLayout.setVisibility(View.INVISIBLE);
        }
    }
    public void PierwszeUruchomienieTesty() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(TUTORIALTESTY, true)) {
            editor.putBoolean(TUTORIALTESTY, false);
            editor.apply();
            CreatepopUpwindow(R.layout.popup_guide_testy);
        }
    }
    public void Zamknij_tutorial(View view){
        if(view.getId()==R.id.welcome_button){
            dialog.dismiss();
            Uruchomienie_animajca();
        }
        if(view.getId()==R.id.welcome_button_testy){
            dialog.dismiss();
        }
    }
    void ResumeOnLongListener(){
        ImageButton menuitem1,menuitem2,menuitem3;
        menuitem1 = findViewById(R.id.ZmienNaTesty);
        menuitem2 = findViewById(R.id.OtworzSklep);
        menuitem3 = findViewById(R.id.jedzeniemenu);
        menuitem1.setOnLongClickListener(this);
        menuitem2.setOnLongClickListener(this);
        menuitem3.setOnLongClickListener(this);
    }
    boolean canplayanimations = true;
    int kropki=0;
    @Override
    protected void onResume() {
        handler.postDelayed(loadingrunnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(loadingrunnable, 400);
                try{
                    TextView loadingtext = findViewById(R.id.text_loading);
                    String dots="";
                    for(int j = kropki%3; j>=0 %3; j--){
                        dots+=".";
                    }
                    String finalDots = dots;
                    loadingtext.setText(("Loading"+ finalDots));

                    kropki++;
                }catch (Exception ee){

                }
            }
        }, 400);

            handler3.postDelayed(runnable3 = new Runnable() {
                @Override
                public void run() {
                    handler3.postDelayed(runnable3, Mrugajco);
                    if(canplayanimations){
                    Mrugnij();
                    }
                }
            },Mrugajco);
            handler2.postDelayed(runnable2 = new Runnable() {
                @Override
                public void run() {
                    handler2.postDelayed(runnable2, AnimationTimer);
                    if(canplayanimations){
                    Animation();
                    }
                }
            },AnimationTimer);


        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, UpdateTimer);
                Log.w("powinno_odjac",String.valueOf((currentTime-savedTime)/1000));
                Log.w("lastFeed_przed",String.valueOf(lastFeed));
                lastFeed -= (currentTime-savedTime)/1000;
                Log.w("lastFeed_po",String.valueOf(lastFeed));
                SaveData();
            }
        }, UpdateTimer);
        super.onResume();
        bgmusicnormal();
        ResumeTimePassage();
    }
    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(),"PAUSE",Toast.LENGTH_SHORT).show();
    handler.removeCallbacks(runnable);
    handler2.removeCallbacks(runnable2);
    handler3.removeCallbacks(runnable3);
    bgsong.pause();
    }
    private final int Mrugajco = 5*1000;
    public int AnimationTimer = 7*1000;
    private final int UpdateTimer = 30*1000; // <- 30 to sekundy
    void Animation(){
        if(lastFeed>umiera_po){
        Random r = new Random();
        AnimationTimer = (r.nextInt(22-12)+12)*1000;
        czerwony_krokodyl = findViewById(R.id.krokodyl);
        ImageView currentczapka = findViewById(R.id.current_czapka_image);
            try {
                YoYo.with(Techniques.Bounce)
                        .duration(1000)
                        .playOn(czerwony_krokodyl);
                YoYo.with(Techniques.Bounce)
                        .duration(1000)
                        .playOn(currentczapka);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void KrokodylKlikaj(View v){
        if(lastFeed>umiera_po){
            addProgress(4);
        }
        if(v.getId()==R.id.krokodyl){
            czerwony_krokodyl = findViewById(R.id.krokodyl);
            ImageView currentczapka = findViewById(R.id.current_czapka_image);
            try{
                Mrugnij();
                YoYo.with(Techniques.RubberBand)
                        .duration(700)
                        .playOn(czerwony_krokodyl);
                YoYo.with(Techniques.RubberBand)
                        .duration(700)
                        .playOn(currentczapka);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    // Metoda do obsługi kliknięcia przycisku "Kup Czapkę"
    public void loadJSONFromAsset() {
        TextView cosiedzieje = findViewById(R.id.cosiedzieje);
        cosiedzieje.setText("Ładowanie Czapek..");
        try {
            InputStream is = getAssets().open("czapki.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            //Log.d("MojaAplikacja", "Prawidłowo wczytano JSON: " + json);
            JSONArray jsonArray = new JSONArray(json);
            listaCzapek.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                czapkiilosc+="0";
                Log.w("JAPIERDOLE",kupioneczapkibool);
                JSONObject czapkaJson = jsonArray.getJSONObject(i);

                int id = czapkaJson.getInt("id");
                String plik = czapkaJson.getString("plik");
                JSONArray nazwa = czapkaJson.getJSONArray("nazwa");
                String[] nazwaArr = new String[nazwa.length()];
                Log.d("nazwaLength",String.valueOf(nazwa.length()));
                JSONArray opis = czapkaJson.getJSONArray("opis");
                Log.d("opisLength",String.valueOf(opis.length()));
                String[] opisArr = new String[opis.length()];
                for(int j=0;j<nazwa.length();j++){
                    nazwaArr[j] = nazwa.getString(j);
                }
                for(int j=0;j<opis.length();j++){
                    opisArr[j] = opis.getString(j);
                }
                Boolean czyDostepne = true;
                if(czapkaJson.has("czyDostepne")){
                    czyDostepne = czapkaJson.getBoolean("czyDostepne");
                }
                Boolean czyPremium = false;
                if(czapkaJson.has("czyPremium")){
                    czyPremium = czapkaJson.getBoolean("czyPremium");
                }
                int cena = czapkaJson.getInt("cena"); // Pobierz cenę czapki

                Czapka czapka = new Czapka(id, plik,nazwaArr,opisArr, cena); // Zaktualizuj obiekt Czapka
                czapka.setCzyDostepna(czyDostepne);
                czapka.setCzyPremium(czyPremium);
                listaCzapek.add(czapka);
            }
            zaladowano_czapki = true;
            for (Czapka czapka : listaCzapek) {
                Log.d("MojaAplikacja", "ID: " + czapka.getId() + ", Nazwa: " + czapka.getNazwa(getLang()) + ", Opis: " + czapka.getOpis(getLang()));
            }
            loadPurchasedCzapki();
            if(kupioneczapkibool==""){
                kupioneczapkibool=czapkiilosc;
            }
            loadAkcja();
            loadCurrentCzapka();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void loadJedzenieFromAsset(){
        TextView cosiedzieje = findViewById(R.id.cosiedzieje);
        cosiedzieje.setText("Ładowanie Jedzonka..");
        try {
            InputStream is = getAssets().open("jedzenie.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            //Log.d("MojaAplikacja", "Prawidłowo wczytano JSON: " + json);
            JSONArray jsonArray = new JSONArray(json);
            listaZarcia.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jedzenieJson = jsonArray.getJSONObject(i);
                int id = jedzenieJson.getInt("id");
                String plik = jedzenieJson.getString("plik");
                JSONArray nazwa = jedzenieJson.getJSONArray("nazwa");
                String[] nazwaArr = new String[nazwa.length()];
                JSONArray opis = jedzenieJson.getJSONArray("opis");
                String[] opisArr = new String[opis.length()];
                for(int j=0;j<nazwa.length();j++){
                    nazwaArr[j] = nazwa.getString(j);
                }
                for(int j=0;j<opis.length();j++){
                    opisArr[j] = opis.getString(j);
                }
                int nasycenie = jedzenieJson.getInt("nasycenie");
                int cena = jedzenieJson.getInt("cena"); // Pobierz cenę czapki
                Jedzenie jedzenie = new Jedzenie(id, plik,nazwaArr,opisArr, cena,nasycenie); // Zaktualizuj obiekt Czapka
                listaZarcia.add(jedzenie);
            }
            zaladowano_jedzenie = true;
            for (Jedzenie jedzenie : listaZarcia) {
                Log.d("MojaAplikacja", "ID: " + jedzenie.getId() + ", Nazwa: " + jedzenie.getNazwa(getLang()) + ", Opis: " + jedzenie.getOpis(getLang()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void loadPotkiFromAsset(){
        TextView cosiedzieje = findViewById(R.id.cosiedzieje);
        cosiedzieje.setText("Ładowanie Mikstur..");
        try {
            InputStream is = getAssets().open("potki.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            //Log.d("MojaAplikacja", "Prawidłowo wczytano JSON: " + json);
            JSONArray jsonArray = new JSONArray(json);
            listaPotek.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject potkiJSON = jsonArray.getJSONObject(i);
                int id = potkiJSON.getInt("id");
                String plik = potkiJSON.getString("plik");
                JSONArray nazwa = potkiJSON.getJSONArray("nazwa");
                String[] nazwaArr = new String[nazwa.length()];
                JSONArray opis = potkiJSON.getJSONArray("opis");
                String[] opisArr = new String[opis.length()];
                for(int j=0;j<nazwa.length();j++){
                    nazwaArr[j] = nazwa.getString(j);
                }
                for(int j=0;j<opis.length();j++){
                    opisArr[j] = opis.getString(j);
                }
                Boolean czyDostepne = true;
                if(potkiJSON.has("czyDostepne")){
                    czyDostepne = potkiJSON.getBoolean("czyDostepne");
                }
                int czasTrwania = 0;
                if(potkiJSON.has("czas_trwania")){
                    czasTrwania = potkiJSON.getInt("czas_trwania");
                }
                int cena = potkiJSON.getInt("cena"); // Pobierz cenę czapki
                Potka potka = new Potka(id, plik,nazwaArr,opisArr, cena,czasTrwania); // Zaktualizuj obiekt Czapka
                listaPotek.add(potka);
            }
            zaladowano_jedzenie = true;
            for (Potka potka : listaPotek) {
                Log.d("MojaAplikacja", "ID: " + potka.getId() + ", Nazwa: " + potka.getNazwa(getLang()) + ", Opis: " + potka.getOpis(getLang()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void loadAkcja(){
        akcja = new int[listaCzapek.size()];
        for(int i=0;i<akcja.length;i++){
            if (i==current_item_index) {
                akcja[i] = 2;
            } else if (kupioneczapkibool.charAt(i)=='1') {
                akcja[i] = 1;
            } else {
                akcja[i] = 0;
            }
        }

    }
    public void UpdateQuestDate(){
        listaQuestowId.clear();
        if(zaladowano_questy){
            int i=0;
            daily_qnum_seed = new Random(Integer.valueOf(currentDate));
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
            loadQuestsFromAsset();
        }
        Log.d("QID",String.valueOf(listaQuestowId));
    }
    public void loadQuestsFromAsset() {
        TextView cosiedzieje = findViewById(R.id.cosiedzieje);
        cosiedzieje.setText("Ładowanie Zadań..");
        try {
            InputStream is = getAssets().open("questy.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            //Log.d("Questy", "Prawidłowo wczytano JSON: " + json);
            JSONArray jsonArray = new JSONArray(json);
            listaQuestow.clear();
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject questJson = jsonArray.getJSONObject(i);
                int id = questJson.getInt("id");
                JSONArray jsonTresc = questJson.getJSONArray("tresc");
                String TrescArr[] = new String[jsonTresc.length()];
                JSONArray jsonPrzedzial = questJson.getJSONArray("przedzial");
                int PrzedzialArr[] = new int[jsonPrzedzial.length()];
                for(int j=0;j<jsonTresc.length();j++){
                    TrescArr[j] = jsonTresc.getString(j);
                }
                for(int j=0;j<jsonPrzedzial.length();j++){
                    PrzedzialArr[j] = jsonPrzedzial.getInt(j);
                }
                int nagroda = questJson.getInt("Nagroda");
                int exp = questJson.getInt("exp");
                Quests quest = new Quests();
                quest.setId(id);
                quest.setTresc(TrescArr);
                quest.setHadPlayedsound(false);
                quest.setNagroda(nagroda);
                quest.setPrzedzial(PrzedzialArr);
                quest.setProgress(0);
                quest.setExp(exp);
                quest.checkifDone();
                quest.overrideClaim(sharedPreferences.getBoolean(QUEST_CLAIM+String.valueOf(i),false));
                listaQuestow.add(quest);
            }
            zaladowano_questy = true;
            UpdateQuestDate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void ResetAllQuestProgress(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("PROGRESSRESET","RESET");
        for (int i = 0; i < listaQuestow.size(); i++) {
            Quests quest = listaQuestow.get(i);
            quest.setProgress(0);
            quest.setHadPlayedsound(false);
            quest.overrideProgress(0);
            quest.resetCzyWylosowano();
            quest.checkifDone();
            editor.putBoolean(QUEST_CLAIM+String.valueOf(i),false);
            quest.overrideClaim(sharedPreferences.getBoolean(QUEST_CLAIM+String.valueOf(i),false));
            editor.putInt(QUEST_PROGRESS+String.valueOf(i),0);
        }
        editor.apply();
    }
    int[] akcja;
    public void ustawCzapke(){
        if(canplayanimations){
            if(current_item_index==-1){
                ImageView czapkaimage = findViewById(R.id.current_czapka_image);
                czapkaimage.setVisibility(View.GONE);
            }
            else{
                if(lastFeed>umiera_po) {
                    ImageView czapkaimage = findViewById(R.id.current_czapka_image);
                    czapkaimage.setVisibility(View.VISIBLE);
                    Context c = getApplicationContext();
                    Czapka current = listaCzapek.get(current_item_index);
                    czapkaimage.setImageResource(getResources().getIdentifier("drawable/"+current.getPlik()+"_idle", null, c.getPackageName()));
                    }
                else{
                    ImageView czapkaimage = findViewById(R.id.current_czapka_image);
                    czapkaimage.setVisibility(View.GONE);
                }
            }
        }
    }
    public void aktualizujTextPrzyciskow(Button[] button) {

        Log.w("kurwaw",String.valueOf(akcja.length));
        Log.w("kurwawa",String.valueOf(kupioneczapkibool.length())); //XDDDD
                for(int i=0;i<button.length;i++){
                    Log.w("cototjest","kup_czapka" + String.valueOf(i));
                    if (i==current_item_index) {
                        button[i].setText(getString(R.string.zdejmij));
                        akcja[i] = 2;
                    } else if (kupioneczapkibool.charAt(i)=='1') {
                        button[i].setText(getString(R.string.zaloz));
                        akcja[i] = 1;
                    } else {
                        button[i].setText(getString(R.string.kup));
                        akcja[i] = 0;
                    }
                    Log.w("akcja",String.valueOf(akcja[i]));
                }


    }
    public String loadJSONFromAssetVer2(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    int przyciskNumer=0;
    /* stara funkcja, jest dalej używana
    public void pokazPopupZakupuCzapek(View view) {
        String buttonId = getResources().getResourceEntryName(view.getId());
        if (buttonId.startsWith("kup_czapka")) {
            przyciskNumer = Integer.parseInt(buttonId.substring("kup_czapka".length()));
            int obrazResId = 0;
            final String nazwaCzapki;
            final String opisCzapki;
            final String idCzapki;
            if (zaladowano_czapki && przyciskNumer < listaCzapek.size()) {
                czapka = listaCzapek.get(przyciskNumer);
                obrazResId = getResources().getIdentifier(czapka.getPlik(), "drawable", getPackageName());
            } else {
                Log.w("MojaAplikacja", "Brak czapki dla przycisku numer: " + przyciskNumer);
                nazwaCzapki = "Nazwa czapki";
                opisCzapki = "Opis czapki";
                idCzapki = String.valueOf(przyciskNumer);
            }
            Log.w("POPUP_VISIBLE",String.valueOf(isPopupVisible));
                if(akcja[czapka.getId()]==0){
                    if(isPopupVisible==false){
                        POPUP_EXCEPTION_MODE = 1;
                        ShowPopup(R.layout.popup_layout);
                        ImageView czapaImage = myDialog.findViewById(R.id.czapa_image);
                        TextView czapaNazwa = myDialog.findViewById(R.id.czapa_nazwa);
                        TextView czapaOpis = myDialog.findViewById(R.id.czapa_opis);
                        Button kupCzapaButton = myDialog.findViewById(R.id.kup_czapa);
                        if (obrazResId != 0) {
                            czapaImage.setImageResource(obrazResId);
                        } else {
                            czapaImage.setImageResource(R.drawable.circle);
                        }
                        czapaNazwa.setText(czapka.getNazwa(getLang()));
                        czapaOpis.setText(czapka.getOpis(getLang()));
                        Log.w("akcjasuper",String.valueOf(akcja[czapka.getId()]));
                    kupCzapaButton.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         int dostepneHajsy = getMoney();
                         if (dostepneHajsy >= czapka.getCena()) {
                            savePurchasedCzapka(String.valueOf(czapka.getId()));
                            int noweHajsy = czapka.getCena()*-1;
                            SaveMoney(noweHajsy);
                            Toast.makeText(MainActivity.this, "Zakupiono " + czapka.getNazwa(getLang()), Toast.LENGTH_SHORT).show();
                            addProgress(3);
                            ClosePopup();
                            aktualizujTextPrzyciskow();
                            updateCrococoinsInShop();
                         } else {
                            Toast.makeText(MainActivity.this, "Nie masz wystarczająco crococoinów na zakup", Toast.LENGTH_SHORT).show();
                         }
                    }
                });
                myDialog.show();
                POPUP_EXCEPTION_MODE = 0;
            }
            }
            else if(akcja[czapka.getId()]==1){
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(CURRENT_ITEM_ID,czapka.getId());
                editor.apply();
                current_item_index = sharedPreferences.getInt(CURRENT_ITEM_ID,-1);
                aktualizujTextPrzyciskow();
            }
            else if(akcja[czapka.getId()]==2){
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(CURRENT_ITEM_ID,-1);
                editor.apply();
                current_item_index = sharedPreferences.getInt(CURRENT_ITEM_ID,-1);
                aktualizujTextPrzyciskow();
            }
            Log.w("CURRENT_INDEX_CAZAPA",String.valueOf(current_item_index));
        }
    }
    */
    public Button Generuj_Przedmiot(LinearLayout parent,int PlikResID,String TytulText,int CenaText,boolean premium){
        RelativeLayout mainRelativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        mainParams.setMargins(0,0,0,dpToPx(5));
        mainRelativeLayout.setLayoutParams(mainParams);
        mainRelativeLayout.setPadding(13, 13, 13, 13);

        mainRelativeLayout.setBackgroundResource(R.drawable.custom_czapka_container);
        if(premium) mainRelativeLayout.setBackgroundResource(R.drawable.custom_czapka_premium_container);
        mainRelativeLayout.setClickable(true);
        parent.addView(mainRelativeLayout);
        TextView cena = new TextView(this);
        TextView cenashadow = new TextView(this);
        TextView nazwa = new TextView(this);
        ImageView waluta = new ImageView(this);
        Button kupButton = new Button(this);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.londrina_solid);
        Typeface typefaceoutline = ResourcesCompat.getFont(this, R.font.londrina_outline);
        kupButton.setId(View.generateViewId());
        ImageView czapkaImageView = new ImageView(this);
        czapkaImageView.setId(View.generateViewId());
        RelativeLayout.LayoutParams czapkaParams = new RelativeLayout.LayoutParams(
                dpToPx(90),
                dpToPx(90)
        );
        czapkaImageView.setBackgroundResource(R.drawable.custom_czapka_container);
        czapkaImageView.setBackgroundTintList(getResources().getColorStateList(R.color.ckdarkbez));
        czapkaImageView.setImageResource(PlikResID);
        czapkaImageView.setPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        czapkaParams.setMargins(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        mainRelativeLayout.addView(czapkaImageView,czapkaParams);

        RelativeLayout nestedRelativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams nestedParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        nestedParams.addRule(RelativeLayout.RIGHT_OF, czapkaImageView.getId());
        nestedParams.addRule(RelativeLayout.ALIGN_TOP, czapkaImageView.getId());
        nestedParams.addRule(RelativeLayout.ALIGN_BOTTOM, czapkaImageView.getId());

        nestedParams.setMargins(dpToPx(20), 0, 0, 0);
        mainRelativeLayout.addView(nestedRelativeLayout, nestedParams);


        nazwa.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));
        nazwa.setId(View.generateViewId());
        nazwa.setText(TytulText);
        nazwa.setTextSize(16);
        nazwa.setTextColor(getResources().getColor(R.color.ckblack));
        nestedRelativeLayout.addView(nazwa);

        waluta.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));
        waluta.setId(View.generateViewId());
        waluta.setImageResource(R.drawable.crococoin);
        RelativeLayout.LayoutParams walutaParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        walutaParams.addRule(RelativeLayout.ALIGN_END, cena.getId());
        walutaParams.addRule(RelativeLayout.ALIGN_TOP, kupButton.getId());
        walutaParams.addRule(RelativeLayout.ALIGN_BOTTOM, kupButton.getId());
        walutaParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        waluta.setScaleType(ImageView.ScaleType.FIT_START);
        nestedRelativeLayout.addView(waluta, walutaParams);
        cena.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));
        cena.setId(View.generateViewId());
        cena.setText(String.valueOf(CenaText));
        cena.setTypeface(typeface);
        cena.setTextColor(getResources().getColor(R.color.ckblack));
        cena.setPadding(dpToPx(20), 0, 0, 0);
        cena.setTextSize(32);
        RelativeLayout.LayoutParams cenaParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        cenaParams.addRule(RelativeLayout.ALIGN_BOTTOM, waluta.getId());
        nestedRelativeLayout.addView(cena, cenaParams);

        RelativeLayout.LayoutParams cenashadowParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        cenashadow.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));
        cenashadow.setId(View.generateViewId());
        cenashadow.setText(String.valueOf(CenaText));
        cenashadow.setTypeface(typefaceoutline,Typeface.BOLD);
        cenashadow.setTextColor(getResources().getColor(R.color.ckblack));
        cenashadow.setPadding(dpToPx(20), 0, 0, 0);
        cenashadow.setTextSize(32);
        cenashadow.setTextColor(getResources().getColorStateList(R.color.ckbez));
        cenashadowParams.addRule(RelativeLayout.ALIGN_BOTTOM, waluta.getId());
        nestedRelativeLayout.addView(cenashadow, cenashadowParams);

        kupButton.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));
        kupButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray_active)));
        kupButton.setTextColor(getResources().getColor(R.color.white));

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        nestedRelativeLayout.addView(kupButton, buttonParams);
        return kupButton;
    }
    public void Wyswietl_Zakladke(View v){
        if(v.getId()==R.id.katczapki){
            Generuj_Sklep("czapki");
        }
        if(v.getId()==R.id.katjedzenie){
            Generuj_Sklep("jedzenie");
        }
        if(v.getId()==R.id.katpotki){
            Generuj_Sklep("potki");
        }
    }
    public void Pokaz_Topbar(){
        TopBar = new Dialog(this);
        TopBar.setContentView(R.layout.topbar);
        TopBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TopBar.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.y = 0;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        TopBar.getWindow().setAttributes(layoutParams);
        RelativeLayout topbar = TopBar.findViewById(R.id.topbar);
        TextView topbarhajs = topbar.findViewById(R.id.topbartext1);
        TextView topbarhajsshadow = topbar.findViewById(R.id.topbartextshadow1);
        topbarhajs.setText(String.valueOf(getMoney()));
        topbarhajsshadow.setText(String.valueOf(getMoney()));
        TextView topbarexp = topbar.findViewById(R.id.topbartext2);
        TextView topbarexpshadow = topbar.findViewById(R.id.topbartextshadow2);
        topbarexp.setText(String.valueOf(getExp()));
        topbarexpshadow.setText(String.valueOf(getExp()));
        TopBar.show();
        YoYo.with(Techniques.FadeInDown).duration(500).playOn(topbar);
    }
    public void Ukryj_Topbar(){
        RelativeLayout topbar = TopBar.findViewById(R.id.topbar);
        new Handler(getMainLooper()).postDelayed(() -> {
            YoYo.with(Techniques.FadeOutUp).duration(500).playOn(topbar);
            new Handler(getMainLooper()).postDelayed(() -> {
                TopBar.dismiss();
            }, 500);
        }, 2000);
    }
    public void Aktualizuj_Hajs(int hajs,int nowyhajs){
        Log.d("hajs",String.valueOf(hajs));
        Log.d("nowyhajs",String.valueOf(nowyhajs));
        if(hajs!=nowyhajs){
            RelativeLayout topbar = TopBar.findViewById(R.id.topbar);
            TextView topbarhajs = topbar.findViewById(R.id.topbartext1);
            TextView topbarhajsshadow = topbar.findViewById(R.id.topbartextshadow1);
            topbarhajs.setText(String.valueOf(hajs));
            topbarhajsshadow.setText(String.valueOf(hajs));
            Log.d("mega","mega");
            new Handler(getMainLooper()).postDelayed(() -> {
                topbarhajs.setText(String.valueOf(nowyhajs));
                topbarhajsshadow.setText(String.valueOf(nowyhajs));
                YoYo.with(Techniques.RubberBand).duration(250).playOn(topbarhajs);
                YoYo.with(Techniques.RubberBand).duration(250).playOn(topbarhajsshadow);
            }, 500);
        }
    }
    public void Aktualizuj_Exp(int Exp,int nowyExp){
        Log.d("hajs",String.valueOf(Exp));
        Log.d("nowyhajs",String.valueOf(nowyExp));
        if(Exp!=nowyExp){
            RelativeLayout topbar = TopBar.findViewById(R.id.topbar);
            TextView topbarhajs = topbar.findViewById(R.id.topbartext2);
            TextView topbarhajsshadow = topbar.findViewById(R.id.topbartextshadow2);
            topbarhajs.setText(String.valueOf(Exp));
            topbarhajsshadow.setText(String.valueOf(Exp));
            Log.d("mega","mega");
            new Handler(getMainLooper()).postDelayed(() -> {
                topbarhajs.setText(String.valueOf(nowyExp));
                topbarhajsshadow.setText(String.valueOf(nowyExp));
                YoYo.with(Techniques.RubberBand).duration(250).playOn(topbarhajs);
                YoYo.with(Techniques.RubberBand).duration(250).playOn(topbarhajsshadow);
            }, 500);
        }
    }
    public void Generuj_Sklep(String kategoria){
        LinearLayout parentLayout = findViewById(R.id.sklep_scroll);
        parentLayout.removeAllViewsInLayout();
        switch (kategoria){
            case "czapki":
            {
                Button[] listaButtonow = new Button[listaCzapek.size()];
                for(int i=0;i<listaCzapek.size();i++){
                    Czapka czapa = listaCzapek.get(i);
                    int obrazResId = getResources().getIdentifier(czapa.getPlik(), "drawable", getPackageName());
                    Button kupButton = Generuj_Przedmiot(parentLayout,obrazResId,czapa.getNazwa(getLang()),czapa.getCena(),czapa.getCzyPremium());
                    int finalI = i;
                    listaButtonow[i]=kupButton;
                    kupButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int przyciskNumer = finalI;
                            kupCzapka(przyciskNumer,listaButtonow);
                        }
                    });
                }
                aktualizujTextPrzyciskow(listaButtonow);
            }
            break;
            case "jedzenie":
            {
                for(int i=0;i<listaZarcia.size();i++){
                    Jedzenie jedzenie = listaZarcia.get(i);
                    int obrazResId = getResources().getIdentifier(jedzenie.getPlik(), "drawable", getPackageName());
                    Button kupButton = Generuj_Przedmiot(parentLayout,obrazResId,jedzenie.getNazwa(getLang()),jedzenie.getCena(),jedzenie.getCzyPremium());
                    kupButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            KupCiastka(v);
                        }
                    });
                }
            }
            break;
            case "potki":
            {
                for(int i=0;i<listaPotek.size();i++){
                    Potka potka = listaPotek.get(i);
                    int obrazResId = getResources().getIdentifier(potka.getPlik(), "drawable", getPackageName());
                    Button kupButton = Generuj_Przedmiot(parentLayout,obrazResId,potka.getNazwa(getLang()),potka.getCena(),potka.getCzyPremium());
                    kupButton.setText(getString(R.string.kup));
                    kupButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            KupCiastka(v);
                        }
                    });
                }
            }
            break;
        }

    }
    public void kupCzapka(int przyciskNumer,Button[] kupButton){
            int obrazResId = 0;
            final String nazwaCzapki;
            final String opisCzapki;
            final String idCzapki;
            if (zaladowano_czapki && przyciskNumer < listaCzapek.size()) {
                czapka = listaCzapek.get(przyciskNumer);
                obrazResId = getResources().getIdentifier(czapka.getPlik(), "drawable", getPackageName());
            } else {
                Log.w("MojaAplikacja", "Brak czapki dla przycisku numer: " + przyciskNumer);
                nazwaCzapki = "Nazwa czapki";
                opisCzapki = "Opis czapki";
                idCzapki = String.valueOf(przyciskNumer);
            }
            Log.w("POPUP_VISIBLE",String.valueOf(isPopupVisible));
            if(akcja[czapka.getId()]==0){
                if(isPopupVisible==false){
                    POPUP_EXCEPTION_MODE = 1;
                    ShowPopup(R.layout.popup_layout);
                    ImageView czapaImage = myDialog.findViewById(R.id.czapa_image);
                    TextView czapaNazwa = myDialog.findViewById(R.id.czapa_nazwa);
                    TextView czapaOpis = myDialog.findViewById(R.id.czapa_opis);
                    Button kupCzapaButton = myDialog.findViewById(R.id.kup_czapa);
                    if(!czapka.getCzyDostepna()) kupCzapaButton.setEnabled(false);
                    if (obrazResId != 0) {
                        czapaImage.setImageResource(obrazResId);
                    } else {
                        czapaImage.setImageResource(R.drawable.circle);
                    }
                    czapaNazwa.setText(czapka.getNazwa(getLang()));
                    czapaOpis.setText(czapka.getOpis(getLang()));
                    Log.w("akcjasuper",String.valueOf(akcja[czapka.getId()]));
                    kupCzapaButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int dostepneHajsy = getMoney();
                            if (dostepneHajsy >= czapka.getCena()) {
                                savePurchasedCzapka(String.valueOf(czapka.getId()));
                                int noweHajsy = czapka.getCena()*-1;
                                SaveMoney(noweHajsy);
                                Toast.makeText(MainActivity.this, "Zakupiono " + czapka.getNazwa(getLang()), Toast.LENGTH_SHORT).show();
                                addProgress(3);
                                ClosePopup();
                                aktualizujTextPrzyciskow(kupButton);
                                updateCrococoinsInShop();
                            } else {
                                Toast.makeText(MainActivity.this, "Nie masz wystarczająco crococoinów na zakup", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    myDialog.show();
                    POPUP_EXCEPTION_MODE = 0;
                }
            }
            else if(akcja[czapka.getId()]==1){
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(CURRENT_ITEM_ID,czapka.getId());
                editor.apply();
                current_item_index = sharedPreferences.getInt(CURRENT_ITEM_ID,-1);
                aktualizujTextPrzyciskow(kupButton);
            }
            else if(akcja[czapka.getId()]==2){
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(CURRENT_ITEM_ID,-1);
                editor.apply();
                current_item_index = sharedPreferences.getInt(CURRENT_ITEM_ID,-1);
                aktualizujTextPrzyciskow(kupButton);
            }
            Log.w("CURRENT_INDEX_CAZAPA",String.valueOf(current_item_index));
        }
    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
    public void KupCiastka(View v){
        if(getMoney()>=200){
            SaveFood(1);
            SaveMoney(-200);
            updateCrococoinsInShop();
        }
    }
    public int GetRadioButtonChecked(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(CHECKEDLANG, 0);
    }
    public int getMoney() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(MONEY, 0);
    }
    public String getLang() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(LANG, "Pl");
    }
    public void changeLang(String lang){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANG,lang);
        editor.apply();
    }
    public void UpdateLocale(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String lang = sharedPreferences.getString(LANG,"Pl");
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
    }
    boolean czypokazana = false;
    public void ustawMiche(){
        TextView ukrytytekst = findViewById(R.id.liczba_ciastek);
        ukrytytekst.setText(getFood() +" Ciastek");
        if(getFood()>0){
            try{
                ImageView miska = findViewById(R.id.jedzeniebutton);
                miska.setImageResource(R.drawable.pelnamiskapsa);
            } catch (Exception ignored){

            }
        }
        else if(getFood()==0){
            try{
                ImageView miska = findViewById(R.id.jedzeniebutton);
                miska.setImageResource(R.drawable.pustamiskapsa);
            } catch (Exception ignored){

            }
        }
    }
    public void PokazMiske(View v){
        if(v.getId()==R.id.jedzeniemenu){
            ustawMiche();
            if(czypokazana){
                ImageView ukrytybutton = findViewById(R.id.jedzeniebutton);
                TextView ukrytytekst = findViewById(R.id.liczba_ciastek);
                YoYo.with(Techniques.SlideOutRight)
                        .duration(300)
                        .playOn(ukrytybutton);
                YoYo.with(Techniques.SlideOutRight)
                        .duration(300)
                        .playOn(ukrytytekst);

                new Handler(getMainLooper()).postDelayed(() -> {
                    try{
                        ukrytybutton.setVisibility(View.GONE);
                        ukrytytekst.setVisibility(View.GONE);

                    } catch (Exception ignored) {
                        throw new RuntimeException(ignored);
                    }
                }, 300);

                czypokazana = false;
            }
            else{
                ImageView ukrytybutton = findViewById(R.id.jedzeniebutton);
                TextView ukrytytekst = findViewById(R.id.liczba_ciastek);
                ukrytybutton.setVisibility(View.VISIBLE);
                ukrytytekst.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(300)
                        .playOn(ukrytybutton);
                YoYo.with(Techniques.SlideInRight)
                        .duration(300)
                        .playOn(ukrytytekst);
                czypokazana = true;
            }
        }
    }
    public void Animacja_Jedzenia(){
            try{
                if(lastFeed>gloduje_co&&lastFeed>umiera_po){
                    czerwony_krokodyl = findViewById(R.id.krokodyl);
                    czerwony_krokodyl.setImageResource(R.drawable.krokodylotwartypysk);
                    new Handler(getMainLooper()).postDelayed(() -> {
                        try{
                            czerwony_krokodyl.setImageResource(R.drawable.logo);

                        } catch (Exception ignored) {
                            throw new RuntimeException(ignored);
                        }
                    }, 150);
                } else if(lastFeed>umiera_po&&lastFeed<gloduje_co){
                    czerwony_krokodyl.setImageResource(R.drawable.glodnykrokodylotwartypysk);
                    new Handler(getMainLooper()).postDelayed(() -> {
                        try{
                            czerwony_krokodyl.setImageResource(R.drawable.glodnykroko);
                        } catch (Exception ignored){

                        }
                    }, 150);
                }else{
                    czerwony_krokodyl.setImageResource(R.drawable.ripcroco);
                }
            } catch (Exception e){

            }
    }
    public void Nakarm(View v){
        if(v.getId()==R.id.jedzeniebutton){
            ustawMiche();
            if(getFood()>0){
                addProgress(2);
                Hapaba();
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(lastFeed<umiera_po){
                    currentTime = new Date().getTime();
                    try{
                        editor.putLong(LAST_FEED, 1);
                    }catch (Exception e){

                    }
                }
                else{
                    try{
                        editor.putLong(LAST_FEED, sharedPreferences.getLong(LAST_FEED,0)+wartosc_ciastka);
                    }catch (Exception e){

                    }
                }
                editor.apply();
                lastFeed = sharedPreferences.getLong(LAST_FEED,0);
                LoadData();
                Animacja_Jedzenia();
                SaveFood(-1);
                ustawMiche();
                ustawCzapke();
            }else{
                Toast.makeText(getApplicationContext(),"brak ciasteczek :(",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void loadPurchasedCzapki(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        kupioneczapkibool = sharedPreferences.getString(PURCHASED_CZAPKI, czapkiilosc);
        saveNewCzapka();
        Log.w("JAPIERDOLE",kupioneczapkibool);
    }
    public void saveNewCzapka(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(kupioneczapkibool.length()<listaCzapek.size()){
            Log.d("zabij_mnie",String.valueOf(listaCzapek.size()-kupioneczapkibool.length()+1));
            while(kupioneczapkibool.length()!=listaCzapek.size()){
                kupioneczapkibool+="0";
                Log.w("JAPIERDOLE",kupioneczapkibool);
            }
            editor.putString(PURCHASED_CZAPKI,kupioneczapkibool);
            editor.apply();
        }
        if(kupioneczapkibool.length()>listaCzapek.size()){
            String newstring="";
            int h=0;
            while(newstring.length()<listaCzapek.size()){
                newstring += kupioneczapkibool.charAt(h);
                h++;
            }
            kupioneczapkibool = newstring;
            editor.putString(PURCHASED_CZAPKI,kupioneczapkibool);
            editor.apply();
        }
    }
    public void loadCurrentCzapka(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        current_item_index = sharedPreferences.getInt(CURRENT_ITEM_ID, -1);
    }
    private void savePurchasedCzapka(String czapka) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        loadPurchasedCzapki();
        StringBuilder myName = new StringBuilder(kupioneczapkibool);
        myName.setCharAt(Integer.valueOf(czapka), '1');
        kupioneczapkibool = myName.toString();
        //zakupioneCzapki.add(czapka);
        editor.putString(PURCHASED_CZAPKI, kupioneczapkibool);
        editor.apply();
        Log.w("kupione_czapy",kupioneczapkibool);
    }
    void UstawKrokodyla(){
        czerwony_krokodyl = findViewById(R.id.krokodyl);
        if(lastFeed>gloduje_co&&lastFeed>umiera_po){
           czerwony_krokodyl.setImageResource(R.drawable.logo);
        } else if(lastFeed>umiera_po&&lastFeed<gloduje_co){
            czerwony_krokodyl.setImageResource(R.drawable.glodnykroko);
        }else{
            czerwony_krokodyl.setImageResource(R.drawable.ripcroco);
        }
    }
    void Mrugnij(){
        try{
            czerwony_krokodyl = findViewById(R.id.krokodyl);
            if(lastFeed>gloduje_co&&lastFeed>umiera_po){
            czerwony_krokodyl.setImageResource(R.drawable.logomrug);
            new Handler(getMainLooper()).postDelayed(() -> {
                try{
                czerwony_krokodyl.setImageResource(R.drawable.logo);
                } catch (Exception e){

                }
            }, 200);
            } else if(lastFeed>umiera_po&&lastFeed<gloduje_co){
                czerwony_krokodyl.setImageResource(R.drawable.glodnykrokomrug);
                new Handler(getMainLooper()).postDelayed(() -> {
                    try{
                        czerwony_krokodyl.setImageResource(R.drawable.glodnykroko);
                    } catch (Exception e){

                    }
                }, 200);
            }else{
                czerwony_krokodyl.setImageResource(R.drawable.ripcroco);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void DebugSetGlodny(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(LAST_FEED,-30000);
        editor.apply();
    }
    public void ResetStreak(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(STREAK,0);
        editor.apply();
    }
    public void AddStreak(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int streak = sharedPreferences.getInt(STREAK,0);
        editor.putInt(STREAK,(streak+1));
        editor.apply();
    }
    public void AddExp(int val){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int xp = sharedPreferences.getInt(SAVED_EXP,0) + val;
        editor.putInt(SAVED_EXP,(xp));
        editor.apply();
        Log.d("EXP",String.valueOf(xp));
        while(xp>=LevelEq(getLevel())){
            xp-=CalcExp(LevelEq(getLevel()));
            AddLevel();
        }
    }
    public int getExp(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        return sharedPreferences.getInt(SAVED_EXP,0);
    }
    public int CalcExp(int val){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int xp = sharedPreferences.getInt(SAVED_EXP,0);
        Log.d("oldExp",String.valueOf(xp));
        if(xp-val>=0){
            xp-=val;
        }
        Log.d("newExp",String.valueOf(xp));
        editor.putInt(SAVED_EXP,xp); //odejmij tyle ile trzeba
        editor.apply();
        return xp;
    }
    public int getLevel(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        return sharedPreferences.getInt(SAVED_LEVEL,0);
    }
    public void AddLevel(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int lvl = sharedPreferences.getInt(SAVED_LEVEL,1);
        Toast.makeText(getApplicationContext(),"nowy level "+String.valueOf(lvl+1),Toast.LENGTH_SHORT).show();
        Log.d("newlvl",String.valueOf(lvl));
        editor.putInt(SAVED_LEVEL,(lvl+1));
        editor.apply();
    }
    public void SavePassedTest(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PASSED_TESTS,sharedPreferences.getInt(PASSED_TESTS,0)+1);
        editor.apply();
    }
    public void SaveFailedTest(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(FAILED_TESTS,sharedPreferences.getInt(FAILED_TESTS,0)+1);
        editor.apply();
    }
    public void DebugSetDate(String ddMMyyyy){
    currentDate = ddMMyyyy;
    }
    public void SaveQuestDate(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String readableDate = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
        if(sharedPreferences.getString(SAVED_QUEST_DATE,"N/A").equals("N/A")){
            editor.putString(SAVED_QUEST_DATE,readableDate);
            editor.apply();
        }
        else{
            readableDate = sharedPreferences.getString(SAVED_QUEST_DATE,readableDate);
        }
        if(!readableDate.equals(currentDate)){
            Log.w("readable",readableDate);
            Log.w("readable",currentDate);
            readableDate = currentDate;
            editor.putString(SAVED_QUEST_DATE,readableDate);
            editor.apply();
            ResetAllQuestProgress();
            UpdateQuestDate();
        }
    }
    public void UpdateQuestProgress(int number,int value){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int quest = sharedPreferences.getInt(QUEST_PROGRESS+String.valueOf(number),-1);
        if(quest==-1){
            editor.putInt(QUEST_PROGRESS+String.valueOf(number),0);
        }
        else{
            editor.putInt(QUEST_PROGRESS+String.valueOf(number),value);
        }
        editor.apply();
    }
    public void SaveMoney(int value){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MONEY,sharedPreferences.getInt(MONEY,0)+value);
        editor.apply();
    }
    public void SaveFood(int value){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(FOOD,sharedPreferences.getInt(FOOD,0)+value);
        editor.apply();
    }
    public int getFood() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(FOOD, 0);
    }
    public void SaveSettings(){
        EditText username = findViewById(R.id.editUsername);
        String value = username.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try{
            editor.putBoolean(SWITCH, sw1.isChecked());
            editor.putBoolean(SWITCHWIBRACJE, sw2.isChecked());
            editor.putString(USERNAME,username.getText().toString());
            for(int i=0;i<radioButtonsIds.length;i++){
                RadioButton radiobutton = findViewById(radioButtonsIds[i]);
                if(radiobutton.isChecked()) radioButtonChecked = i;
            }
            editor.putInt(CHECKEDLANG,radioButtonChecked);
        } catch(Exception e){
            e.printStackTrace();
        }
        editor.apply();
    }
    public void Wibracje(){
        if(switchstatewibracje) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(100);
            }
        }
    }
    public void SaveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        currentTime = new Date().getTime();
        editor.putLong(SAVED_DATE, currentTime);
        try{
            editor.putLong(LAST_FEED, lastFeed);
        }catch (Exception e){

        }
        editor.apply();
        //Toast.makeText(this, "Update.."+switchstate.toString(), Toast.LENGTH_SHORT).show();
        LoadData();
    }
    public void ResumeTimePassage(){
        currentTime = new Date().getTime();
        Log.w("saved_time",String.valueOf(savedTime));
        Log.w("current_time",String.valueOf(currentTime));
        Log.w("time_calc",String.valueOf(currentTime-savedTime));
        Log.w("lastFeed_przed",String.valueOf(lastFeed));
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        savedTime = sharedPreferences.getLong(SAVED_DATE,currentTime);
        lastFeed -= (currentTime-savedTime)/1000;
        Log.w("lastFeed_po",String.valueOf(lastFeed));
        SaveData();
    }
    public void LoadData(){
        currentTime = new Date().getTime();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        switchstate = sharedPreferences.getBoolean(SWITCH,true);
        switchstatewibracje = sharedPreferences.getBoolean(SWITCHWIBRACJE,false);
        savedTime = sharedPreferences.getLong(SAVED_DATE,currentTime);
        currentVol = sharedPreferences.getInt(VOLUME,-1);
        if(lastFeed==0){
            lastFeed = sharedPreferences.getLong(LAST_FEED,0);
        }

        Log.w("saved_time",String.valueOf(savedTime));
        Log.w("current_time",String.valueOf(currentTime));
        Log.w("time_calc",String.valueOf(currentTime-savedTime));
        Log.w("lastFeedVal",String.valueOf(lastFeed));
        if(canplayanimations) {
            if (lastFeed > gloduje_co) {
                try {
                    czerwony_krokodyl = findViewById(R.id.krokodyl);
                    czerwony_krokodyl.setImageResource(R.drawable.logo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (lastFeed < gloduje_co && lastFeed > umiera_po) {
                try {
                    czerwony_krokodyl = findViewById(R.id.krokodyl);
                    czerwony_krokodyl.setImageResource(R.drawable.glodnykroko);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (lastFeed < umiera_po) {
                try {
                    czerwony_krokodyl = findViewById(R.id.krokodyl);
                    czerwony_krokodyl.setImageResource(R.drawable.ripcroco);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        //Toast.makeText(this,"D:"+String.valueOf(lastFeed),Toast.LENGTH_SHORT).show();
        //DebugSetGlodny();
        //Toast.makeText(this,"date diff: "+String.valueOf(currentTime-sharedPreferences.getLong(SAVED_DATE,currentTime)),Toast.LENGTH_SHORT).show();
    }
    public void UpdateUserProfile(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        TextView username = findViewById(R.id.usernick_profile);
        ImageView userPic = findViewById(R.id.user_profile_pic_profile);
        TextView streak = findViewById(R.id.streakilosc);
        TextView kasa = findViewById(R.id.kasailosc);
        TextView zdane = findViewById(R.id.zdaneilosc);
        TextView nzdane = findViewById(R.id.nzdaneilosc);
        try{
            if(czyZalogowany){
                Button zaloguj = findViewById(R.id.sign_in_button);
                zaloguj.setVisibility(View.GONE);
            }
            else{
                Button wyloguj = findViewById(R.id.wyloguj);
                wyloguj.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }
        try{
            if(UserProfileUrl!=""){
                Glide.with(getApplicationContext()).load(UserProfileUrl).into(userPic);
            }
            //Toast.makeText(getApplicationContext(),String.valueOf(sharedPreferences.getInt(STREAK,0)),Toast.LENGTH_SHORT).show();
            streak.setText(String.valueOf(sharedPreferences.getInt(STREAK,0)));
            kasa.setText(String.valueOf(sharedPreferences.getInt(MONEY,0)));
            zdane.setText(String.valueOf(sharedPreferences.getInt(PASSED_TESTS,0)));
            nzdane.setText(String.valueOf(sharedPreferences.getInt(FAILED_TESTS,0)));
            username.setText(sharedPreferences.getString(USERNAME,defaultUsername));
        } catch (Exception e){

        }
    }
    public void UpdateSettings(){
        LoadData();
        SeekBar volume = findViewById(R.id.seekBar);
        AudioManager audiomanager;
        audiomanager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVol = audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC)*4;
        if(currentVol==-1){
            currentVol = maxVol/2;
        }
        //Toast.makeText(getApplicationContext(),String.valueOf(currentVol),Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        EditText username = findViewById(R.id.editUsername);
        ImageView userPic = findViewById(R.id.user_profile_pic);
        sw1 = findViewById(R.id.switch1);
        sw2 = findViewById(R.id.switch2);
        try{
            if(!Objects.equals(UserProfileUrl, "")){
                Glide.with(getApplicationContext()).load(UserProfileUrl).into(userPic);
            }
            volume.setMax(maxVol);
            volume.setProgress(currentVol);
            try {
                if (!Objects.equals(UserProfileUrl, "")) {
                    Glide.with(getApplicationContext()).load(UserProfileUrl).into(userPic);
                }
                volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                        audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                        currentVol = progress;
                        //Toast.makeText(getApplicationContext(),String.valueOf(progress),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        SaveVolume();
                    }
                });
            }catch (Exception ee){
                ee.printStackTrace();
            }
            username.setText(sharedPreferences.getString(USERNAME,defaultUsername));
            sw1.setChecked(switchstate);
            sw2.setChecked(switchstatewibracje);
            int idradio = 0;
            idradio = GetRadioButtonChecked();
            RadioButton radiobutton = findViewById(radioButtonsIds[GetRadioButtonChecked()]);
            radiobutton.setChecked(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String UserProfileUrl =""; // Uwaga! - nie zapisuje się w aplikacji!!
    void updateAccInfo(){
        login_name = findViewById(R.id.usernick);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);
        acct = GoogleSignIn.getLastSignedInAccount(this);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        if(acct!=null){
            czyZalogowany=true;
            ImageView userimg = findViewById(R.id.user_profile);
            UserProfileUrl = acct.getPhotoUrl().toString();
            login_name.setText(sharedPreferences.getString(USERNAME,defaultUsername));
            Glide.with(getApplicationContext()).load(UserProfileUrl).into(userimg);
            showonce = true;
            try{
                firebaseAuth(acct.getIdToken());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            czyZalogowany=false;
            try{
                if(!showonce){
                    ShowPopup(R.layout.popup_login);
                    showonce = true;
                }
                UserProfileUrl = "";
                ImageView userimg = findViewById(R.id.user_profile);
                userimg.setImageResource(R.drawable.usernotfound);
                login_name.setText(sharedPreferences.getString(USERNAME,defaultUsername));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void signIn(View v){
        if (v.getId()==R.id.sign_in_button){
            Intent signInIntent = gsc.getSignInIntent();
            startActivityForResult(signInIntent,1000);
        } else if (v.getId()==R.id.zaloguj){
            myDialog.dismiss();
            isPopupVisible=false;
            Intent signInIntent = gsc.getSignInIntent();
            startActivityForResult(signInIntent,1000);
        }
    }
    public void signOut(View v){
        if(v.getId()==R.id.wyloguj){
            gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    updateAccInfo();
                    DefaultMainPageActions();
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        //.makeText(getApplicationContext(), "onActivityResult", Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuth(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            Users users = new Users();
                            users.setUserId(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setProfile(user.getPhotoUrl().toString());
                            users.setUserMoney(getMoney());
                            Log.w("money",String.valueOf(users.getMoney()));
                            database.getReference().child("Users").child(user.getUid()).setValue(users);
                        }
                    }
                });
    }
    private void updateFirebaseData(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    database.getReference().child("Users").child(user.getUid()).child("money").setValue(getMoney());
                }
            }
        });
    }
    void navigateToSecondActivity(){
        //Toast.makeText(getApplicationContext(), "navigateToSecondActivity", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.main_page);
        updateAccInfo();
    }
    public void Siema2(View v){
        if(v.getId()==R.id.switch1){
            try{
                sw1 = findViewById(R.id.switch1);
                if(sw1.isChecked()){
                    switchstate=true;
                    bgmusicnormal();
                }
                else{
                    bgsong.pause();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void bgmusictesty(){
        if(switchstate){
            if(!bgsong.isPlaying()){
                int id = getResources().getIdentifier("ck_testy", "raw", getPackageName());
                bgsong = MediaPlayer.create(this,id);
                bgsong.start();
                bgsong.setLooping(true);
            }else{
                int id = getResources().getIdentifier("ck_testy", "raw", getPackageName());
                bgsong.reset();
                bgsong.release();
                bgsong = MediaPlayer.create(this,id);
                bgsong.start();
                bgsong.setLooping(true);
            }
        }
    }
    public void bgmusicnormal(){
        if(switchstate){
            if(!bgsong.isPlaying()){
                int id = getResources().getIdentifier("cktheme_1", "raw", getPackageName());
                bgsong = MediaPlayer.create(this,id);
                bgsong.start();
                bgsong.setLooping(true);
            }else{
                int id = getResources().getIdentifier("cktheme_1", "raw", getPackageName());
                bgsong.reset();
                bgsong.release();
                bgsong = MediaPlayer.create(this,id);
                bgsong.start();
                bgsong.setLooping(true);
            }
        }
    }
    public void DefaultMainPageActions(){
        setContentView(R.layout.main_page);
        updateAccInfo();
        canplayanimations = true;
        ResumeOnLongListener();
        ustawCzapke();
        ResetActions();
    }
    public void Zmien_widoki(View v) {
        canplayanimations = false;
        if(v.getId()==R.id.ustawienia){
            setContentView(R.layout.settings);
            UpdateSettings();
            AddActions("settings");
        }
        else if(v.getId()==R.id.wroc_do_pytania){
            setContentView(R.layout.pytanie_wyglad_wyjasnienie);
            Set_pytanie_bledne();
            RemoveAction();
        }
        else if(v.getId()==R.id.kolo_fortuny){
            if(isPopupVisible==false){
                POPUP_RESOLUTION = 1;
                ShowPopup(R.layout.popup_kolo_fortuny);
                generateSectorDegrees();
                deg =0;
                POPUP_RESOLUTION = 0;
            }
        }
        else if(v.getId()==R.id.questy){
            if(!isPopupVisible){
                if(zaladowano_questy){
            POPUP_RESOLUTION = 1;
            POPUP_EXCEPTION_MODE = 1;
            SaveQuestDate();
            ShowPopup(R.layout.popup_quest);
                for(int i=0;i<3;i++){
                    Quests quest = listaQuestow.get(listaQuestowId.indexOf(i));
                    String progressName = "DailyQuest"+String.valueOf(i+1);
                    String questName = "quest"+String.valueOf(i+1);
                    String questProgress = "questcompletion"+String.valueOf(i+1);
                    String questValue = "quest"+String.valueOf(i+1)+"value";
                    String questMax = "questvalue"+String.valueOf(i+1)+"max";
                    String questProgressImgID = "questcomplete"+String.valueOf(i+1);
                    String questIcon = "questIcon"+String.valueOf(i+1);
                    String questNagroda = "nagroda"+String.valueOf(i+1);
                    String questNagrodashadow = "nagrodashadow"+String.valueOf(i+1);
                    String claim = "claim"+String.valueOf(i+1);
                    String questy_completion;
                    int resIDnagroda = getResources().getIdentifier(questNagroda, "id", getPackageName());
                    int resIDprogress = getResources().getIdentifier(progressName, "id", getPackageName());
                    int resIDprogressImg = getResources().getIdentifier(questProgressImgID, "id", getPackageName());
                    int resIDquestname = getResources().getIdentifier(questName, "id", getPackageName());
                    int resIDquestProgress = getResources().getIdentifier(questProgress, "id", getPackageName());
                    int resIDnagrodashadow = getResources().getIdentifier(questNagrodashadow, "id", getPackageName());
                    int resIDclaim = getResources().getIdentifier(claim, "id", getPackageName());
                    ProgressBar progress = (ProgressBar) myDialog.findViewById(resIDprogress);
                    ImageView questProgressImg = myDialog.findViewById(resIDprogressImg);
                    Button claimbutton = myDialog.findViewById(resIDclaim);
                    if(quest.getisDone()){
                        questy_completion = "(done)";
                        int finalI = i;
                        if(quest.getClaim()!=true){
                            claimbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Claim(listaQuestowId.indexOf(finalI));
                                    claimbutton.setEnabled(false);
                                    claimbutton.setVisibility(View.GONE);
                                }
                            });
                        }else{
                            claimbutton.setVisibility(View.GONE);
                            claimbutton.setEnabled(false);
                        }
                        //questProgressImg.setImageResource(R.drawable.done);
                    }else{
                        claimbutton.setEnabled(false);
                        questy_completion = ("("+String.valueOf(quest.getProgress())+"/"+String.valueOf(quest.getGeneratedMax())+")");
                    } //questProgressImg.setImageResource(R.drawable.notdone);
                    TextView questtresc = myDialog.findViewById(resIDquestname);
                    TextView nagroda = myDialog.findViewById(resIDnagroda);
                    TextView nagrodashadow = myDialog.findViewById(resIDnagrodashadow);
                    nagroda.setText("+"+String.valueOf(quest.getNagroda()));
                    nagrodashadow.setText("+"+String.valueOf(quest.getNagroda()));
                    questtresc.setText(quest.getTresc(getLang()));
                    TextView progresstextval = myDialog.findViewById(resIDquestProgress);
                    progresstextval.setText(questy_completion);
                    progress.setMax(quest.getGeneratedMax());
                    progress.setProgress(quest.getProgress());
                }

            POPUP_RESOLUTION = 0;
            POPUP_EXCEPTION_MODE = 0;
            myDialog.show();
                }
            }
        }
        else if(v.getId()==R.id.user_profile){
            setContentView(R.layout.user_profile);
            UpdateUserProfile();
            AddActions("userprofile");
        }
        else if(v.getId()==R.id.wroc){
            SaveSettings();
            DefaultMainPageActions();
        }
        else if(v.getId()==R.id.zakoncz_test_bledne){
            DefaultMainPageActions();
        }
        else if(v.getId()==R.id.creditsbtn){
            SaveSettings();
            setContentView(R.layout.credits);
            AddActions("credits");
        }
        else if (v.getId()==R.id.OtworzSklep){
            setContentView(R.layout.sklep);
            updateCrococoinsInShop();
            Generuj_Sklep("jedzenie");
            AddActions("sklep");
        }
        else if(v.getId()==R.id.wroc_do_pytan_bledne){
            setContentView(R.layout.pytania_wybor_bledne);
            Resume_Question_Buttons_Final();
            AddActions("pytania_wybor_bledne");
        }
        else if(v.getId()==R.id.zobacz_bledne){
            setContentView(R.layout.pytania_wybor_bledne);
            Resume_Question_Buttons_Final();
            AddActions("pytania_wybor_bledne");
        } else if (v.getId()==R.id.wroc_z_creditsow) {
            DefaultMainPageActions();
        } else if (v.getId()==R.id.wrocmenu) {
           DefaultMainPageActions();
        }
        else if(v.getId()==R.id.wroc_do_menu_poj){
            DefaultMainPageActions();
            bgmusicnormal();
        }
        else if (v.getId()==R.id.ZmienNaTesty){
            setContentView(R.layout.select_tests);
            PierwszeUruchomienieTesty();
            AddActions("select_tests");
            updateLoader();
        }
        else if (v.getId()==R.id.Rozpocznij_test1){
            setContentView(R.layout.pytania_wybor);
            Create_Question_Buttons();
            ClosePopup();
            bgmusictesty();
            AddActions("pytania_wybor");
        }
        else if (v.getId()==R.id.Rozpocznij_test2){
            setContentView(R.layout.pojedyncze_pytanie);
            CHALLENGE_MODE = false;
            Set_pytanie_Poj();
            ClosePopup();
            bgmusictesty();
            AddActions("pojedyncze_pytanie");
        }
        else if (v.getId()==R.id.wroc_do_pytan){
            setContentView(R.layout.pytania_wybor);
            Resume_Question_Buttons();
            AddActions("pytania_wybor");
        }
        else if (v.getId()==R.id.testy3){
            setContentView(R.layout.testy_debug);
            AddActions("testy_debug");
            fetchData(1);
        }//do testowania, nie będzie wspierane w późniejszych wersjach
        else if(v.getId()==R.id.testychallenge){
            setContentView(R.layout.pytanie_wyglad_challenge);
            CHALLENGE_MODE = true;
            Set_pytanie_Poj();
            ilosc_blednych=0;
            ClosePopup();
            bgmusictesty();
            AddActions("challenge_pytanie");
        }
        czypokazana = false;
        LoadData();
        Wibracje();
    }

    boolean CHALLENGE_MODE = false;
    public void updateCrococoinsInShop(){
        TextView iloschajsu = findViewById(R.id.crococoinyilosc);
        iloschajsu.setText(String.valueOf(getMoney()));
    }
    public void ResetActions(){
        actions.clear();
        actionsindex=0;
        Log.w("actionslist",actions.toString());
        Log.w("actionsindex",String.valueOf(actionsindex));
    }
    public void AddActions(String str){
        actions.add(str);
        actionsindex++;
        Log.w("actionslist",actions.toString());
        Log.w("actionsindex",String.valueOf(actionsindex));
    }
    public void RemoveAction(){
        actions.remove(actionsindex-1);
        actionsindex--;
        Log.w("actionslist",actions.toString());
        Log.w("actionsindex",String.valueOf(actionsindex));
    }
    //tutaj sekcja z popupami #SECPOPUP
    public void ShowPopup(int layout){
        isPopupVisible = true;
        if(POPUP_RESOLUTION==1)
            myDialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        else
            myDialog = new Dialog(this);
        myDialog.setContentView(layout);
        myDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                isPopupVisible = false;
            }
        });
        switch(POPUP_EXCEPTION_MODE){
            case 0:myDialog.show();break;
            case 1:/*don't show dialog */break;
            default:myDialog.show();break;
        }
    }
    public void Zamknij_Popup(View v){
        ClosePopup();
    }
    public void PopupTestyRozpocznij(View v){
        if(isPopupVisible==false){
            if(v.getId()==R.id.testy1){
                ShowPopup(R.layout.rozpoczecie_testu);
            }
            else if(v.getId()==R.id.testy2){
                ShowPopup(R.layout.rozpoczecie_testu_poj);
            }
        }
    }
    public void PopupTestyZakoncz(View v){
        if(isPopupVisible==false){
            if(v.getId()==R.id.zakoncz_test||v.getId()==R.id.nextbutton){
                ShowPopup(R.layout.zakonczenie_testu);
            }
        }
    }
    public void ClosePopup(){
        myDialog.dismiss();
        isPopupVisible = false;
    }
    // koniec sekcji
    public void Hapaba(){
        String name = "hapaba";
        Random r = new Random();
        name+=String.valueOf(r.nextInt(4)+1);
        int id = getResources().getIdentifier(name, "raw", getPackageName());
        //buttonsong.reset();
        buttonsong = MediaPlayer.create(this,id);
        buttonsong.start();
        buttonsong.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            };
        });
    }
    // tutaj sekcja z pobieraniem danych z pamięci #SECMEMO
    public void loadQuestProgress(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        for(int i=0;i<listaQuestow.size();i++){
            Quests quests = listaQuestow.get(i);
            int questv = sharedPreferences.getInt(QUEST_PROGRESS+String.valueOf(i),0);
            quests.setProgress(questv);
            Log.d("QPROGRESS",String.valueOf(quests.getisDone()));
        }
    }
    // koniec sekcji
    // tutaj sekcja z przypisywaniem danych do pamięci #SECMEMO
    public void SaveVolume(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(VOLUME,currentVol);
        editor.apply();
    }
    public void addProgress(int id){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int questv = sharedPreferences.getInt(QUEST_PROGRESS+String.valueOf(id),0);
        Quests quest = listaQuestow.get(id);
        if(quest.getGeneratedMax()>questv){
            quest.setProgress(questv+1);
            editor.putInt(QUEST_PROGRESS+String.valueOf(id),questv+1);
            Log.d("QUESTVALUEACTUAL",String.valueOf(quest.getProgress()));
            questPlaySound(quest);
        }else{
            //editor.putInt(QUEST_PROGRESS+String.valueOf(id),0); //debug
        }

        editor.apply();
    }
    public void Claim(int id){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean claim = sharedPreferences.getBoolean(QUEST_CLAIM+String.valueOf(id),false);
        Quests quest = listaQuestow.get(id);
        if(quest.getClaim()!=true){
            quest.Claim();
            SaveMoney(quest.getNagroda());
            AddExp(quest.getExp());
            Pokaz_Topbar();
            Aktualizuj_Hajs(getMoney()-quest.getNagroda(),getMoney());
            Aktualizuj_Exp(getExp()-quest.getExp(),getExp());
            Ukryj_Topbar();
            editor.putBoolean(QUEST_CLAIM+String.valueOf(id),true);
        }
        editor.apply();
    }
    public void questPlaySound(Quests q){
        Log.w("qsound","1");
        if(q.getisDone()){
            if(q.getCzyWylosowano()){
                if(!q.gethadPlayedsound()){
                    MediaPlayer playComplete = MediaPlayer.create(this,R.raw.questcomplete);
                    playComplete.start();
                    playComplete.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        };
                    });
                    q.setHadPlayedsound(true);
                }
            }
        }
    }
    MediaPlayer GlobalPlaySound;
    public void PlaySound(String filename,boolean isLooping){
        int ID = getResources().getIdentifier(filename, "raw", getPackageName());
        GlobalPlaySound = MediaPlayer.create(this,ID);
        GlobalPlaySound.start();
        GlobalPlaySound.setLooping(isLooping);
        GlobalPlaySound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            };
        });
    }
    public void StopSound(){
        GlobalPlaySound.setLooping(false);
        GlobalPlaySound.stop();
        GlobalPlaySound.release();
    }
    // koniec sekcji
    // tutaj sekcja z testami #SECTEST
    public void Zmien_Id_wartosc(View v){
        if(v.getId()==R.id.ZmienIdminus){
            if(SET_TEST_ID>1){
                TextView id_wartosc = findViewById(R.id.ZmienIDtekst);
                SET_TEST_ID--;
                id_wartosc.setText(String.valueOf(SET_TEST_ID));
            }
        }
        else if(v.getId()==R.id.ZmienIdplus){
            TextView id_wartosc = findViewById(R.id.ZmienIDtekst);
            SET_TEST_ID++;
            id_wartosc.setText(String.valueOf(SET_TEST_ID));
        }
    }
    public void Zmien_id(View v){
        if(v.getId()==R.id.apply_id){
            fetchData(1);
        }
    }
    public void updateLoader(){
        if(actions.size()>0){
            if(actions.get(actions.size()-1)=="select_tests"){
                if(zaladowanodb){
                    ProgressBar pb = findViewById(R.id.dbloaded);
                    pb.setVisibility(View.GONE);
                    TextView tv = findViewById(R.id.ladowanie_tekst);
                    tv.setVisibility(View.GONE);
                }
            }
        }
    }
    public void Set_Ids_List(String response){
        if(response.equals("")){
            String id,poprawne,tresc,wyjasnienie,A,B,C,D,kategoria;
            Log.w("WarningDBError","DBCONNECTIONFAILED");
            Toast.makeText(this,"Wystąpił bład, wczytano lokalne pytania",Toast.LENGTH_SHORT).show();
            try{
                JSONObject jsonObject = new JSONObject(loadJSONFromAssetVer2("DB.json"));
                JSONArray jsonArray = jsonObject.getJSONArray("API");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        id = jsonObject2.getString("id");
                        poprawne = jsonObject2.getString("poprawna_odp");
                        tresc = jsonObject2.getString("tresc");
                        wyjasnienie = jsonObject2.getString("wyjasnienie");
                        kategoria = jsonObject2.getString("kategoria");
                        A = jsonObject2.getString("A");
                        B = jsonObject2.getString("B");
                        C = jsonObject2.getString("C");
                        D = jsonObject2.getString("D");
                        CorrectAnswerList.add(poprawne);
                        idList.add(Integer.valueOf(id));
                        PytaniaDB pytanie = new PytaniaDB(Integer.valueOf(id),tresc,poprawne.charAt(0),A,B,C,D,wyjasnienie,kategoria);
                        listaPytan.add(pytanie);
                        zaladowanodb=true;
                    }
                updateLoader();
            }catch (Exception ee){
                zaladowanodb=false;
                ee.printStackTrace();
                Toast.makeText(getApplicationContext(), "Błąd połączenia z siecią i wczytania backupu", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("API");
                String id, poprawne, tresc, wyjasnienie, A, B, C, D,kategoria;

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    id = jsonObject2.getString("id");
                    poprawne = jsonObject2.getString("poprawna_odp");
                    tresc = jsonObject2.getString("tresc");
                    wyjasnienie = jsonObject2.getString("wyjasnienie");
                    A = jsonObject2.getString("A");
                    B = jsonObject2.getString("B");
                    C = jsonObject2.getString("C");
                    D = jsonObject2.getString("D");
                    kategoria = jsonObject2.getString("kategoria");
                    CorrectAnswerList.add(poprawne);
                    idList.add(Integer.valueOf(id));
                    PytaniaDB pytanie = new PytaniaDB(Integer.valueOf(id),tresc,poprawne.charAt(0),A,B,C,D,wyjasnienie,kategoria);
                    listaPytan.add(pytanie);
                    pytanie.Wypisz();
                    zaladowanodb=true;
                }
                updateLoader();
            } catch (JSONException e) {
                e.printStackTrace();
                zaladowanodb=false;
            }
        }
    }
    public int CURRENT_INDEX;
    public void Resume_Question_Buttons(){
        Log.d("testaction","creating");
        try{
            LinearLayout layout = findViewById(R.id.questions_select);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20);
            for(int i=0;i<q_num;i++){
                Button btn_tests = new Button(this);
                btn_tests.setLayoutParams(params);
                btn_tests.setText(getString(R.string.pytanie)+" "+(i+1));
                btn_tests.setTextSize(30);
                btn_tests.setTag(ShuffledArray.get(i));

                if(AnswerList.get(i)!=0){
                    btn_tests.setBackgroundColor(Color.parseColor("#4f5d75"));
                    btn_tests.setTextColor(Color.parseColor("#ffffff"));
                } else{
                    btn_tests.setBackgroundColor(Color.parseColor("#333333"));
                    btn_tests.setTextColor(Color.parseColor("#ffffff"));
                }
                btn_tests.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            CURRENT_INDEX = idList.indexOf(v.getTag());
                            SET_TEST_ID = ShuffledArray.get(CURRENT_INDEX);
                            setContentView(R.layout.pytanie_wyglad);
                            AddActions("pytanie_wyglad");
                            Set_pytanie();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                layout.addView(btn_tests);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public Random rand_num = new Random();
    public int q_num;
    public int poprawne;
    public void Resume_Question_Buttons_Final(){
        try{
            LinearLayout layout = findViewById(R.id.questions_select_bledne);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20);
            for(int i=0;i<q_num;i++){
                Button btn_tests = new Button(this);
                btn_tests.setLayoutParams(params);
                btn_tests.setText(getString(R.string.pytanie)+" "+(i+1));
                btn_tests.setTextSize(30);
                btn_tests.setTag(ShuffledArray.get(i));
                PytaniaDB pytanie = listaPytan.get(ShuffledArray.get(i));
                if(pytanie.getOdpUzytkownika()!=pytanie.getPoprawnaOdp()){
                    btn_tests.setBackgroundColor(color_incorrect);
                } else{
                    btn_tests.setBackgroundColor(color_correct);
                }
                if(pytanie.getOdpUzytkownika()=='-')
                    btn_tests.setBackgroundColor(R.color.ckbez);
                btn_tests.setTextColor(Color.parseColor("#000000"));

                btn_tests.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            CURRENT_INDEX = idList.indexOf(v.getTag());
                            SET_TEST_ID = ShuffledArray.get(CURRENT_INDEX);
                            setContentView(R.layout.pytanie_wyglad_wyjasnienie);
                            AddActions("pytanie_wyglad_wyjasnienie");
                            Set_pytanie_bledne();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                layout.addView(btn_tests);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Create_Question_Buttons(){
        Log.d("testaction","creating");
        poprawne = 0;
        AnswerList.clear();
        q_num = rand_num.nextInt(25-20)+20;
        for(int i=0;i<q_num;i++){
            AnswerList.add(0);
        }
        ResetAllQuestions();
        ShuffledArray = idList;
        Collections.shuffle(ShuffledArray);
        Resume_Question_Buttons();
    }
    public void ResetAllQuestions(){
        Log.d("testaction","reseting");
        for(int i=0;i<listaPytan.size();i++){
        PytaniaDB pytania = listaPytan.get(i);
        pytania.ResetAnswer();
        listaPytan.set(i,pytania);
        }
    }
    boolean wywolajfunkcje=false;
    public void NextPytanie(View v){
        if(v.getId()==R.id.nextbutton||v.getId()==R.id.nextbuttonbledne){
            if(wywolajfunkcje==true){
                PopupTestyZakoncz(v);
            }
            if(CURRENT_INDEX<q_num-1){
                try{
                    CURRENT_INDEX++;
                    SET_TEST_ID = ShuffledArray.get(CURRENT_INDEX);
                    if(v.getId()==R.id.nextbutton){
                        Set_pytanie();
                        Button zmientekst = findViewById(R.id.nextbutton);
                    }
                    if(v.getId()==R.id.nextbuttonbledne) Set_pytanie_bledne();
                } catch (Exception e){

                }
            }
        }
        else if(v.getId()==R.id.nextpojpytanie){
            Set_pytanie_Poj();
        }
    }
    public void PrevPytanie(View v){
        if(v.getId()==R.id.prevbutton||v.getId()==R.id.prevbuttonbledne){
            if(CURRENT_INDEX>0){
                try{
                    wywolajfunkcje = false;
                    CURRENT_INDEX--;
                    SET_TEST_ID = ShuffledArray.get(CURRENT_INDEX);
                    if(v.getId()==R.id.prevbutton){
                        Set_pytanie();
                    }
                    if(v.getId()==R.id.prevbuttonbledne) Set_pytanie_bledne();
                } catch (Exception e){

                }
            }
        }
    }
    boolean oneshot=true;
    public void Wyjasnij(View v){

        if(v.getId()==R.id.wyjasnienie){
            setContentView(R.layout.wyjasnienie);
            AddActions("wyjasnienie");

            try{
                PytaniaDB pytanie = listaPytan.get(ShuffledArray.get(CURRENT_INDEX));
                ImageView wyjasnienie = findViewById(R.id.wyjasnienie_main);
                String wyjasnienie_tekst = pytanie.getWyjasnienie();
                wyjasnienie.setBackground(Math_syn.set_Math(wyjasnienie_tekst));
            } catch (Exception e){

            }
        }
        if(v.getId()==R.id.wyjasnij_poj){
            if(oneshot==false){
                try{
                    PytaniaDB pytanie = listaPytan.get(CURRENT_INDEX);
                    ImageView wyjasnienie = findViewById(R.id.tresc_pytania_i_wyjasnienie);
                    String wyjasnienie_tekst = pytanie.getWyjasnienie();
                    Test_Math_drawable=Math_syn.set_Math(wyjasnienie_tekst);
                    wyjasnienie.setBackground(Test_Math_drawable);
                } catch (Exception e){

                }
            }
        }
    }
    int ilosc_blednych=0;
    public void Set_pytanie(){
        Log.d("testaction","setting_question");
        try{
            TextView numer_pytania = findViewById(R.id.numer_pytania);
            numer_pytania.setText(getString(R.string.pytanie)+" "+(CURRENT_INDEX + 1));
            int i = SET_TEST_ID;
            String tresc,id;
            PytaniaDB pytanie = listaPytan.get(ShuffledArray.get(CURRENT_INDEX));
            ImageView tes = findViewById(R.id.tresc_pytania);
            Log.d("testaction","setting_tresc");
            try{
                tes.setBackground(Math_syn.set_Math(pytanie.getTresc()));
            } catch (Exception e){
                e.printStackTrace();
            }
            Log.d("testaction","setting_tresc");
            Button[] buttony = {
                    findViewById(R.id.odp_A),
                    findViewById(R.id.odp_B),
                    findViewById(R.id.odp_C),
                    findViewById(R.id.odp_D)
            };//#todo odmałp to
            Log.d("testaction","setting_tresc");
            Log.d("testaction","setting_odpowiedzi");
            for(int j=0;j<4;j++){
                try {
                    Log.d("testaction",String.valueOf(j));
                    StringCurrentQuestion[j] = "\\text{" + LitABCD[j] + ": }" + pytanie.getJednaOdpowiedz(j);
                    CurrentQuestion[j] = Math_syn.set_Fancy_Math(StringCurrentQuestion[j],0x00ffffff);
                    buttony[j].setBackground(CurrentQuestion[j]);
                }catch (Exception e){
                    e.printStackTrace();
                    buttony[j].setText("crash");
                }
            }
            if(CURRENT_INDEX==0){
                Button zmientekst=findViewById(R.id.prevbutton);
                zmientekst.setEnabled(false);
            }else{
                Button zmientekst=findViewById(R.id.prevbutton);
                zmientekst.setEnabled(true);
            }
            if(q_num-1==CURRENT_INDEX){
                Button zmientekst=findViewById(R.id.nextbutton);
                zmientekst.setText("zakończ"); //#todo tłumacz
                wywolajfunkcje=true;
            }else{
                Button zmientekst=findViewById(R.id.nextbutton);
                zmientekst.setText(getString(R.string.next));
                wywolajfunkcje=false;
            }
            UpdateAnswer();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Set_pytanie_Poj(){
        Random r = new Random();
        CURRENT_INDEX = r.nextInt(idList.size());
        try{
            oneshot=true;
            if(CHALLENGE_MODE!=true){
                Button wyjasnienie_button = findViewById(R.id.wyjasnij_poj);
                wyjasnienie_button.setEnabled(false);
            }
            AnswerListPoj = 0;
            ShuffledArray = idList;
            Collections.shuffle(ShuffledArray);
            PytaniaDB pytanie = listaPytan.get(CURRENT_INDEX);
            String[] pytania = pytanie.getOdpowiedzi();
            ImageView tes = findViewById(R.id.tresc_pytania_i_wyjasnienie);
            try{
                Test_Math_drawable=Math_syn.set_Math(pytanie.getTresc());
                tes.setBackground(Test_Math_drawable);
            } catch (Exception e){
                e.printStackTrace();
            }
            Button[] buttony = {
                    findViewById(R.id.odp_A_Poj),
                    findViewById(R.id.odp_B_Poj),
                    findViewById(R.id.odp_C_Poj),
                    findViewById(R.id.odp_D_Poj)
            };
            for(int j=0;j<4;j++){
                try {
                    buttony[j].setText("");
                    StringCurrentQuestion[j] = "\\text{" + LitABCD[j] + ": }" + pytania[j];
                    CurrentQuestion[j] = Math_syn.set_Fancy_Math(StringCurrentQuestion[j],0x00ffffff);
                    buttony[j].setBackground(CurrentQuestion[j]);
                }catch (Exception e){
                    e.printStackTrace();
                    buttony[j].setText("crash");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Set_pytanie_bledne(){
        try{
            int i = SET_TEST_ID;
            String tresc,id;
            id = idList.get(i).toString();
            PytaniaDB pytanie = listaPytan.get(ShuffledArray.get(CURRENT_INDEX));
            tresc = pytanie.getTresc();
            String[] pytania = pytanie.getOdpowiedzi();
            ImageView tes = findViewById(R.id.tresc_pytaniabledne);
            try{
                Test_Math_drawable=Math_syn.set_Math(tresc);
                tes.setBackground(Test_Math_drawable);
            } catch (Exception e){
                e.printStackTrace();
            }
            Button[] buttony = {
                    findViewById(R.id.odp_Abledne),
                    findViewById(R.id.odp_Bbledne),
                    findViewById(R.id.odp_Cbledne),
                    findViewById(R.id.odp_Dbledne)
            };
            for(int j=0;j<4;j++){
                try {
                    buttony[j].setText("");
                    StringCurrentQuestion[j] = "\\text{" + LitABCD[j] + ": }" + pytania[j];
                    CurrentQuestion[j] = Math_syn.set_Fancy_Math(StringCurrentQuestion[j],0x00ffffff);
                    buttony[j].setBackground(CurrentQuestion[j]);
                }catch (Exception e){
                    e.printStackTrace();
                    buttony[j].setText("crash");
                }
            }
            UpdateAnswerBledne();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void UpdateAnswer(){
        Log.d("testaction","updating_answear");
        try{
            Button[] buttony = {
                    findViewById(R.id.odp_A),
                    findViewById(R.id.odp_B),
                    findViewById(R.id.odp_C),
                    findViewById(R.id.odp_D)
            };
            for(int j=0;j<4;j++){
                CurrentQuestion[j] = Math_syn.set_Fancy_Math(StringCurrentQuestion[j],0x00ffffff);
                if(AnswerList.get(CURRENT_INDEX)==j+1){
                    CurrentQuestion[j] = Math_syn.set_Very_Fancy_Math(StringCurrentQuestion[j],0xff4f5d75,0xffffffff);
                }
                buttony[j].setBackground(CurrentQuestion[j]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void UpdateAnswer_Poj(){
        try{
            Button[] buttony = {
                    findViewById(R.id.odp_A_Poj),
                    findViewById(R.id.odp_B_Poj),
                    findViewById(R.id.odp_C_Poj),
                    findViewById(R.id.odp_D_Poj)
            };
            PytaniaDB pytanie = listaPytan.get(CURRENT_INDEX);
            char odp = pytanie.getOdpUzytkownika();
            int odpint=0;
            char correctansw = pytanie.getPoprawnaOdp();
            switch (correctansw){
                case 'A':
                    odpint=1;
                    break;
                case 'B':
                    odpint=2;
                    break;
                case 'C':
                    odpint=3;
                    break;
                case 'D':
                    odpint=4;
                    break;
                default:
                    odpint=0;
                    break;
            }
            int current_color= 0x00ffffff;
            for(int j=0;j<4;j++){
                current_color= 0x00ffffff;
                if(AnswerListPoj==j+1){
                    if(pytanie.getOdpUzytkownika()!=pytanie.getPoprawnaOdp()){
                        current_color = color_incorrect;
                    }
                }
                CurrentQuestion[j] = Math_syn.set_Very_Fancy_Math(StringCurrentQuestion[j],current_color,color_text_incorrect);
                buttony[j].setBackground(CurrentQuestion[j]);
            }
            if(odpint!=0){
                CurrentQuestion[odpint-1] = Math_syn.set_Very_Fancy_Math(StringCurrentQuestion[odpint-1],color_correct,color_text_correct);
                buttony[odpint-1].setBackground(CurrentQuestion[odpint-1]);
            }
            if(pytanie.getOdpUzytkownika()==pytanie.getPoprawnaOdp()){
                addProgress(0); //może progress do challenge?
            }else{
                if(CHALLENGE_MODE==true){
                    ilosc_blednych++;
                    String japka = "zycie_";
                        int resID = getResources().getIdentifier((japka+String.valueOf(3-ilosc_blednych+1)), "id", getPackageName());
                        ImageView img = findViewById(resID);
                        img.setImageResource(R.drawable.japkodead);
                        PlaySound("life_lost",false);
                }
                if(ilosc_blednych>=3){
                    new Handler(getMainLooper()).postDelayed(() -> {
                        DefaultMainPageActions();
                    }, 2500);
                }
            }
            if(CHALLENGE_MODE==true){
                new Handler(getMainLooper()).postDelayed(() -> {
                    Set_pytanie_Poj();
                }, 2500);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void UpdateAnswerBledne(){
        try{

            Button[] buttony = {
                    findViewById(R.id.odp_Abledne),
                    findViewById(R.id.odp_Bbledne),
                    findViewById(R.id.odp_Cbledne),
                    findViewById(R.id.odp_Dbledne)
            };
            for(int j=0;j<4;j++){
                CurrentQuestion[j] = Math_syn.set_Fancy_Math(StringCurrentQuestion[j],0x00ffffff);
                buttony[j].setBackground(CurrentQuestion[j]);
                buttony[j].setText("");
            }
            char odp;
            int odpint=0;
            PytaniaDB pytanie = listaPytan.get(ShuffledArray.get(CURRENT_INDEX));
            odp = pytanie.getOdpUzytkownika();
            char correctansw = pytanie.getPoprawnaOdp();
            switch (correctansw){
                case 'A':
                    odpint=1;
                    break;
                case 'B':
                    odpint=2;
                    break;
                case 'C':
                    odpint=3;
                    break;
                case 'D':
                    odpint=4;
                    break;
                default:
                    odpint=0;
                    break;
            }
            int current_color= 0x00ffffff;

            for(int j=0;j<4;j++){
                current_color= 0x00ffffff;
                if(AnswerList.get(CURRENT_INDEX)==j+1){
                    if(pytanie.getOdpUzytkownika()!=pytanie.getPoprawnaOdp()){
                        current_color = color_incorrect;
                    }
                }
                CurrentQuestion[j] = Math_syn.set_Very_Fancy_Math(StringCurrentQuestion[j],current_color,color_text_incorrect);
                buttony[j].setBackground(CurrentQuestion[j]);
            }
            if(odpint!=0){
                CurrentQuestion[odpint-1] = Math_syn.set_Very_Fancy_Math(StringCurrentQuestion[odpint-1],color_correct,color_text_incorrect);
                buttony[odpint-1].setBackground(CurrentQuestion[odpint-1]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setAnswerParent(){
        // #TODO wrzuć wszystko do jednej funkcji
    }
    public void setAnswer(View v){
        try{
            PytaniaDB pytanie = listaPytan.get(ShuffledArray.get(CURRENT_INDEX));
            if(v.getId()==R.id.odp_A){
                AnswerList.set(CURRENT_INDEX,1);
                pytanie.ZapiszOdpowiedz('A');
            }
            else if(v.getId()==R.id.odp_B){
                AnswerList.set(CURRENT_INDEX,2);
                pytanie.ZapiszOdpowiedz('B');
            }
            else if(v.getId()==R.id.odp_C){
                AnswerList.set(CURRENT_INDEX,3);
                pytanie.ZapiszOdpowiedz('C');
            }
            else if(v.getId()==R.id.odp_D){
                AnswerList.set(CURRENT_INDEX,4);
                pytanie.ZapiszOdpowiedz('D');
            }
            listaPytan.set(CURRENT_INDEX,pytanie);
            UpdateAnswer();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setAnswerPoj(View v){
        PytaniaDB pytanie = listaPytan.get(CURRENT_INDEX);
        try{
            if(v.getId()==R.id.odp_A_Poj){
                AnswerListPoj = 1;
                pytanie.ZapiszOdpowiedz('A');
            }
            else if(v.getId()==R.id.odp_B_Poj){
                AnswerListPoj = 2;
                pytanie.ZapiszOdpowiedz('B');
            }
            else if(v.getId()==R.id.odp_C_Poj){
                AnswerListPoj = 3;
                pytanie.ZapiszOdpowiedz('C');
            }
            else if(v.getId()==R.id.odp_D_Poj){
                AnswerListPoj = 4;
                pytanie.ZapiszOdpowiedz('D');
            }
            listaPytan.set(CURRENT_INDEX,pytanie);
            if(oneshot==true){
                UpdateAnswer_Poj();
                if(CHALLENGE_MODE!=true){
                    Button wyjasnienie_button = findViewById(R.id.wyjasnij_poj);
                    wyjasnienie_button.setEnabled(true);
                }

            }
            oneshot=false;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public double oblicz_percenty;
    public int brak_odp=0;
    void Oblicz_Poprawne(){
        int npoprawne = 0;
        brak_odp=0;
        char odp;
        for(int i=0;i<q_num;i++){
            PytaniaDB pytanie = listaPytan.get(ShuffledArray.get(i));
            odp = pytanie.getOdpUzytkownika();
            try{
                int get_id = ShuffledArray.get(i);
            if(pytanie.getPoprawnaOdp()!=odp){
                npoprawne++;
                if(odp=='-') brak_odp++;
            }
            else poprawne++;
            }catch (Exception e){
                e.printStackTrace();
            }
            }
        if(poprawne!=0){
            oblicz_percenty = poprawne*100/q_num;
        }else oblicz_percenty = 0;
    }
    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
    public void Zakoncz_test(View v){
        if(v.getId()==R.id.Zakoncz_test_popup){
            ClosePopup();
            Oblicz_Poprawne();
            bgmusicnormal();
            setContentView(R.layout.test_final);
            addProgress(1);
            AddActions("test_final");
            try{

                TextView wynik_pkt = findViewById(R.id.wynik_punktowy);
                TextView ilosc_pytan = findViewById(R.id.iloscpytan);
                TextView msg_text = findViewById(R.id.opisWyniku);
                TextView procenty = findViewById(R.id.wynik_procentowy);
                TextView poprawne_chart = findViewById(R.id.chart_pozytywne);
                TextView negatywne_chart = findViewById(R.id.chart_negatywne);
                TextView pominiete_chart = findViewById(R.id.chart_pominiete);
                TextView nagroda1 = findViewById(R.id.nagroda1);
                TextView nagrodashadow1 = findViewById(R.id.nagrodashadow1);

                LinearLayout.LayoutParams poprawneWeight = (LinearLayout.LayoutParams) poprawne_chart.getLayoutParams();
                LinearLayout.LayoutParams negatywneWeight = (LinearLayout.LayoutParams) negatywne_chart.getLayoutParams();
                LinearLayout.LayoutParams pominieteWeight = (LinearLayout.LayoutParams) pominiete_chart.getLayoutParams();

                poprawne_chart.setText(String.valueOf(poprawne));
                negatywne_chart.setText(String.valueOf(q_num-poprawne-brak_odp));
                pominiete_chart.setText(String.valueOf(brak_odp));

                poprawneWeight.weight = (float) poprawne/q_num;
                negatywneWeight.weight = (float) (q_num-poprawne-brak_odp)/q_num;
                pominieteWeight.weight = (float) brak_odp/q_num;

                procenty.setText(fmt(oblicz_percenty) +"%");
                wynik_pkt.setText("("+String.valueOf(poprawne)+"/"+String.valueOf(q_num)+")");
                if(oblicz_percenty>=30){
                    SavePassedTest();
                    AddStreak();
                }
                if(oblicz_percenty<30){
                    nagroda1.setText("0");
                    nagrodashadow1.setText("0");
                    if(oblicz_percenty>20){

                        SaveFood(1);
                        SaveMoney(100);
                        nagroda1.setText("100");
                        nagrodashadow1.setText("100");
                        Pokaz_Topbar();
                        Aktualizuj_Hajs(getMoney()-100,getMoney());
                        Ukryj_Topbar();
                    }
                    msg_text.setText(Wynik_msg[0]);
                    SaveFailedTest();
                    ResetStreak();
                } else if (oblicz_percenty>=30&&oblicz_percenty<60) {
                    msg_text.setText(Wynik_msg[1]);
                    SaveFood(3);
                    SaveMoney(300);
                    nagroda1.setText("300");
                    nagrodashadow1.setText("300");
                    Pokaz_Topbar();
                    Aktualizuj_Hajs(getMoney()-300,getMoney());
                    Ukryj_Topbar();
                } else if (oblicz_percenty>=60&&oblicz_percenty<80) {
                    msg_text.setText(Wynik_msg[2]);
                    SaveFood(5);
                    SaveMoney(500);
                    nagroda1.setText("500");
                    nagrodashadow1.setText("500");
                    Pokaz_Topbar();
                    Aktualizuj_Hajs(getMoney()-500,getMoney());
                    Ukryj_Topbar();
                } else if (oblicz_percenty>=80&&oblicz_percenty<100) {
                    msg_text.setText(Wynik_msg[3]);
                    SaveFood(7);
                    SaveMoney(700);
                    nagroda1.setText("700");
                    nagrodashadow1.setText("700");
                    Pokaz_Topbar();
                    Aktualizuj_Hajs(getMoney()-700,getMoney());
                    Ukryj_Topbar();
                } else if (oblicz_percenty==100) {
                    msg_text.setText(Wynik_msg[4]);
                    SaveFood(10);
                    SaveMoney(1000);
                    nagroda1.setText("1000");
                    nagrodashadow1.setText("1000");
                    Pokaz_Topbar();
                    Aktualizuj_Hajs(getMoney()-1000,getMoney());
                    Ukryj_Topbar();
                }
                if(czyZalogowany==true){
                    updateFirebaseData(acct.getIdToken());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            }
        }
    public void parseJson(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("API");
            for(int i=SET_TEST_ID-1;i<SET_TEST_ID;i++){ // tutaj zmien SET_TEST_ID;
                String tresc,wyjasnienie,id;
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                id = jsonObject2.getString("id");
                tresc = jsonObject2.getString("tresc");
                wyjasnienie = jsonObject2.getString("wyjasnienie");
                String[] pytania = {
                        jsonObject2.getString("A"),
                        jsonObject2.getString("B"),
                        jsonObject2.getString("C"),
                        jsonObject2.getString("D")
                };
                TextView tes = findViewById(R.id.tresc);
                TextView wyjasnienie_text = findViewById(R.id.wyjasnienie);
                try{
                    tes.setText("");
                Test_Math_drawable=Math_syn.set_Math("\\text{id:"+id+" }"+tresc);
                tes.setBackground(Test_Math_drawable);
                tes.setTextSize(1,40);
                } catch (Exception e){
                    e.printStackTrace();
                    try{
                        tes.setText("crash");
                    }catch (Exception e2){
                        e2.printStackTrace();
                    }
                }
                Button[] buttony = {
                        findViewById(R.id.opt1),
                        findViewById(R.id.opt2),
                        findViewById(R.id.opt3),
                        findViewById(R.id.opt4)
                    };
                for(int j=0;j<4;j++){
                    try {
                        buttony[j].setText("");
                        Test_Math_drawable = Math_syn.set_Math("\\text{" + j + ": }" + pytania[j]);
                        buttony[j].setBackground(Test_Math_drawable);
                    }catch (Exception e){
                            e.printStackTrace();
                            try{
                                buttony[j].setText("crash");
                            } catch (Exception ee){
                                ee.printStackTrace();
                            }
                        }
                }
                try {
                    wyjasnienie_text.setText("");
                    Test_Math_drawable = Math_syn.set_Math(wyjasnienie);
                    wyjasnienie_text.setBackground(Test_Math_drawable);
                }catch (Exception e){
                        e.printStackTrace();
                        try{
                            wyjasnienie_text.setText("crash");
                        }
                        catch (Exception ee){
                            ee.printStackTrace();
                        }
                    }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void fetchData(int action){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =final_connection;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), "Response: " + response,Toast.LENGTH_SHORT).show();
                        //Log.d("JSON_DATA",response);
                        switch (action){
                            case 0:
                                TextView cosiedzieje = findViewById(R.id.cosiedzieje);
                                cosiedzieje.setText("Pobieranie Pytań..");
                                Set_Ids_List(response);
                                break;
                            case 1:
                                parseJson(response);
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response_Error",error.toString());
                Set_Ids_List("");
            }
        });
        queue.add(stringRequest);
    }
    @Override
    public boolean onLongClick(View v) {
        ImageButton imgbtn;
        if(v.getId()==R.id.OtworzSklep){
            imgbtn = findViewById(R.id.OtworzSklep);
            YoYo.with(Techniques.Bounce).duration(1000).playOn(imgbtn);
        }
        if(v.getId()==R.id.jedzeniemenu){
            imgbtn = findViewById(R.id.jedzeniemenu);
            YoYo.with(Techniques.Bounce).duration(1000).playOn(imgbtn);
        }
        if(v.getId()==R.id.ZmienNaTesty){
             imgbtn = findViewById(R.id.ZmienNaTesty);
            YoYo.with(Techniques.Bounce).duration(1000).playOn(imgbtn);
        }
        return false;
    }
}