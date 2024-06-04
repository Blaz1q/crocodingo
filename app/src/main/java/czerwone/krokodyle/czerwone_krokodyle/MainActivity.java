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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
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

import ru.noties.jlatexmath.JLatexMathDrawable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
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
    private List<PytaniaDB> listaPytan = new ArrayList<>();
    private List<PytaniaDB> listaShuffled = new ArrayList<>();
    private List<Czapka> listaCzapek = new ArrayList<>();
    private List<Jedzenie> listaZarcia = new ArrayList<>();
    private List<Potka> listaPotek = new ArrayList<>();
    private List<Quests> listaQuestow = new ArrayList<>();
    private List<Integer> listaQuestowId = new ArrayList<>();
    private List<Achievements> listaOsiagniec = new ArrayList<>();
    private List<Integer> listaKategorii = new ArrayList<>();
    private List<List<PytaniaDB>> podzielonaListaPytan = new ArrayList<>();
    // zmienne do akcji
    private List<String> actions = new ArrayList<>();
    private List<Integer> popups = new ArrayList<>();
    private UserData User;
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
    boolean zaladowano_osiagniecia = false;
    boolean isPopupVisible = false;
    boolean zaladowanodb = false; //tylko w przypadku gdy nie ma połączenia z bazą
    String[] LitABCD = {"A","B","C","D"};
    final int color_correct = 0xff88d18a;
    final int color_text_correct = 0xff0d1b15;
    final int color_incorrect = 0xfff52720;
    final int color_text_incorrect = 0xff270302;
    final int wartosc_ciastka = 7000; //2 h (prawie)
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
    boolean isExitEnabled = true;
    Switch sw1;
    Switch sw2;
    int radioButtonChecked = 0;
    int currentVol = -1;
    int current_item_index=-1;
    int POPUP_EXCEPTION_MODE=0;
    int POPUP_RESOLUTION=0; // 0 - normalny, 1 - fullscreen
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
    private static final String PURCHASEDCZAPKA = "PURCHASEDCZAPKA";
    private static final String SAVED_LEVEL = "SAVED_LEVEL";
    private static final String SAVED_EXP = "SAVED_EXP";
    private static final String final_connection= "https://jncrew.5v.pl/androidAPI.php";
    AppUpdateManager appUpdateManager;
    Dialog dialog;
    Dialog myDialog;
    Dialog TopBar;
    Boolean showonce = false;
    int SELECTED_FOOD=0;
    int SELECTED_LIST=0;
    String[] Consumables = {"Jedzenie","Potki"};
    final int[] sectors = {1,2,3,4,5,6,7,8,9,10};
    final int[] sectorDegrees = new int[sectors.length];

    boolean spinning = false;
    Random random = new Random();
    Runnable loadingrunnable;
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
        User = new UserData(getApplicationContext());
        canplayanimations = false;
        checkForUpdates();
        LoadData();
        UpdateLocale();
        loadJSONFromAsset();
        loadQuestsFromAsset();
        SaveQuestDate();
        loadQuestProgress();
        loadPotkiFromAsset();
        loadJedzenieFromAsset();
        loadOsiagnieciaFromAsset();
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
                            canplayanimations = true;
                            UstawKrokodyla();
                            Math_syn.set_Math("\\text{rozruch}");
                            ResumeOnLongListener();
                            showUpdatePopup();
                            updateAccInfo();
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
    private void checkForUpdates(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                        // Pass the intent that is returned by 'getAppUpdateInfo()'.
                        appUpdateInfo,
                        // an activity result launcher registered via registerForActivityResult
                        registerForActivityResult(
                                new ActivityResultContracts.StartIntentSenderForResult(),
                                new ActivityResultCallback<ActivityResult>() {
                                    @Override
                                    public void onActivityResult(ActivityResult result) {
                                        // handle callback
                                        if (result.getResultCode() != RESULT_OK) {
                                            Log.d("UpdateHandler","Update flow failed! Result code: " + result.getResultCode());
                                            // If the update is canceled or fails,
                                            // you can request to start the update again.
                                        }
                                        else{

                                        }Log.d("UpdateHandler","Update flow successfull!");
                                    }
                                }),
                        // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                        // flexible updates.
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build());
            }
        });
    }
    @Override
    public void onBackPressed() {
        canplayanimations = false;
        if(isExitEnabled){
        if(actions.size()!=0){
            switch (actions.get(actions.size()-1)){
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
                case "pytania_wybor_kategoria":
                    DefaultMainPageActions();break;
                default:
                    wyjscie.show();
                    break;
            }
        }else{
            wyjscie.show();
        }
        }
        LoadData();
        Wibracje();
    }
    public void showUpdatePopup(){
        if(!User.checkVersion()){
            CoNowego update = loadUpdateFromAsset();
            if(update.code!=-1){
                POPUP_EXCEPTION_MODE = 1;
                ShowPopup(R.layout.popup_update);
                TextView wersja = myDialog.findViewById(R.id.patchnotes);
                wersja.setText(getString(R.string.conowego)+update.Wersja);
                LinearLayout parent = myDialog.findViewById(R.id.patchnotes_content);
                for(int i=0;i<update.getOpis(getLang()).length;i++){
                    TextView tresc = new TextView(this);
                    tresc.setText("- "+update.getOpis(getLang())[i]);
                    tresc.setTextColor(getColor(R.color.ckblack));
                    tresc.setTextSize(30);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    tresc.setLayoutParams(params);
                    tresc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    Typeface typeface = ResourcesCompat.getFont(this, R.font.londrina_solid);
                    tresc.setTypeface(typeface);
                    parent.addView(tresc);
                }
                myDialog.show();
                POPUP_EXCEPTION_MODE =0;
            }
        }
    }
    public int LevelEq(int x){
        int val = (int) Math.floor(x*50*x*1.5);
        //Log.d("level",String.valueOf(x)+" : "+String.valueOf(val));
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
            User.setLang("Pl");
        }
        else if(v.getId()==R.id.radioLangEng){
            User.setLang("En");
        }
        setLocale(User.getLang());
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(getLang());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        SaveSettings();
        setContentView(R.layout.settings);
        UpdateSettings();
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
        isExitEnabled = false;
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
            kolopasy.getRotation();
        },500);
        Log.d("deg/",String.valueOf(deg%10));
        new Handler(getMainLooper()).postDelayed(() -> {
            spinning = false;
            isExitEnabled = true;
            try{
                TextView tekst_nagroda = myDialog.findViewById(R.id.nagroda);
                tekst_nagroda.setText(String.valueOf(nagroda));
                Pokaz_Topbar();
                Aktualizuj_Hajs(getMoney()-nagroda,getMoney());
                Aktualizuj_Exp(getExp(),getExp()+5000);
                Ukryj_Topbar();
                AddExp(5000);

            }catch (Exception e){
                e.printStackTrace();
            }

        },10500);
    }
    public void krec(View v){
        if(v.getId()==R.id.obramowanie){
            if(!User.getCzy_zakrencil()){
                if(!spinning){
                    spin();
                    User.Zakrec();
                }
            }else{
                Toast.makeText(this, "Limit jeden na dzień!", Toast.LENGTH_SHORT).show();
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
            addProgressOsiagniecia(9);
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
                boolean czyDostepne = true;
                boolean czywidoczna = true;
                if(czapkaJson.has("czyWidoczna")){
                    czywidoczna = czapkaJson.getBoolean("czyWidoczna");
                }
                if(czapkaJson.has("czyDostepne")){
                    czyDostepne = czapkaJson.getBoolean("czyDostepne");
                }
                Boolean czyPremium = false;
                if(czapkaJson.has("czyPremium")){
                    czyPremium = czapkaJson.getBoolean("czyPremium");
                }
                int cena = czapkaJson.getInt("cena"); // Pobierz cenę czapki
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                Czapka czapka = new Czapka(id, plik,nazwaArr,opisArr, cena); // Zaktualizuj obiekt Czapka
                czapka.setCzyDostepna(sharedPreferences.getBoolean("DOSTEPNACZAPKA"+String.valueOf(czapka.getId()),czyDostepne));
                czapka.setCzyPremium(czyPremium);
                czapka.setCzyWidoczna(czywidoczna);

                boolean czykupiona = sharedPreferences.getBoolean(PURCHASEDCZAPKA+String.valueOf(czapka.getId()),false);
                czapka.setCzyZakupiona(czykupiona);
                listaCzapek.add(czapka);
            }
            zaladowano_czapki = true;
            for (Czapka czapka : listaCzapek) {
                Log.d("MojaAplikacja", "ID: " + czapka.getId() + ", Nazwa: " + czapka.getNazwa(getLang()) + ", Opis: " + czapka.getOpis(getLang()));
            }
            loadAkcja();
            loadCurrentCzapka();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public CoNowego loadUpdateFromAsset(){
        String wersja="";
        String[][] finalopis;
        try {
            InputStream is = getAssets().open("update.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            Log.d("MojaAplikacja", "Prawidłowo wczytano JSON: " + json);
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject updateJSON = jsonArray.getJSONObject(i);
                JSONArray opisArray = updateJSON.getJSONArray("Opis");
                String[][] opis = new String[opisArray.length()][]; // Inicjalizacja tablicy opis
                for (int j = 0; j < opisArray.length(); j++) {
                    JSONArray opisInner = opisArray.getJSONArray(j);
                    opis[j] = new String[opisInner.length()]; // Inicjalizacja tablicy wewnętrznej
                    for(int k=0; k<opisInner.length(); k++){
                        opis[j][k] = opisInner.getString(k); // Przypisanie wartości do tablicy
                    }
                }
                wersja = updateJSON.getString("Wersja");
                Log.d("wersja", wersja);
                for(int j=0; j<opis.length; j++){
                    for(int k=0; k<opis[j].length; k++){
                        Log.d("japierdole", "attempt" + opis[j][k]);
                    }
                }
                String wersjaCroco = getString(R.string.version);
                Log.d("wersja", wersjaCroco);
                if(wersja.equals(wersjaCroco)){
                    Log.d("KURWWWWAAAA","KUTWWAAARRAAR");
                    CoNowego nowyupdate = new CoNowego(wersja,opis);
                    return nowyupdate;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        CoNowego nowyupdate = new CoNowego();
        return nowyupdate;
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
                Jedzenie jedzenie = new Jedzenie(id, plik,nazwaArr,opisArr, cena,nasycenie,getApplicationContext()); // Zaktualizuj obiekt Czapka
                if(jedzenieJson.has("czyDostepne")){
                    jedzenie.setCzyDostepna(jedzenieJson.getBoolean("czyDostepne"));
                }
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
    public void loadOsiagnieciaFromAsset() {
        TextView cosiedzieje = findViewById(R.id.cosiedzieje);
        cosiedzieje.setText("Ładowanie Osiągnięć..");
        try {
            InputStream is = getAssets().open("osiagniecia.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            //Log.d("MojaAplikacja", "Prawidłowo wczytano JSON: " + json);
            JSONArray jsonArray = new JSONArray(json);
            int stagecounter=0;
            listaOsiagniec.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject osiagnieciaJSON = jsonArray.getJSONObject(i);
                int Id = osiagnieciaJSON.getInt("Id");
                //String plik = jedzenieJson.getString("plik"); później
                JSONArray Tytul = osiagnieciaJSON.getJSONArray("Tytul");
                JSONArray Podtytul = osiagnieciaJSON.getJSONArray("Podtytul");
                JSONArray Komentarz = osiagnieciaJSON.getJSONArray("Komentarz");
                JSONObject Nagroda = osiagnieciaJSON.getJSONObject("Nagroda");
                boolean czyWidoczne = true;
                String[] Tytularr = new String[Tytul.length()];
                String[] Podtytularr = new String[Podtytul.length()];
                String[] Komentarzarr = new String[Komentarz.length()];
                for (int j = 0; j < Tytul.length(); j++) {
                    Tytularr[j] = Tytul.getString(j);
                }
                for (int j = 0; j < Podtytul.length(); j++) {
                    Podtytularr[j] = Podtytul.getString(j);
                }
                for (int j = 0; j < Komentarz.length(); j++) {
                    Komentarzarr[j] = Komentarz.getString(j);
                }
                int MaxProgress = osiagnieciaJSON.getInt("MaxProgress");
                boolean hasNextStage = osiagnieciaJSON.getBoolean("hasNextStage");

                int Exp = Nagroda.getInt("Exp");
                Achievements osiagniecie = new Achievements(Id, Tytularr, Podtytularr, Komentarzarr, MaxProgress, hasNextStage, Exp,getApplicationContext(),stagecounter);
                osiagniecie.setHajs(Nagroda.getInt("Hajs"));
                if(Nagroda.has("JedzenieID")&&Nagroda.has("JedzenieIlosc")){
                    JSONArray JedzenieID = Nagroda.getJSONArray("JedzenieID");
                    JSONArray JedzenieIlosc = Nagroda.getJSONArray("JedzenieIlosc");
                    if(JedzenieIlosc.length()==JedzenieID.length()){
                        int[] JedzenieIDs = new int[JedzenieID.length()];
                        int[] JedzenieIloscs = new int[JedzenieIlosc.length()];
                        for(int j=0;j<JedzenieID.length();j++){
                            JedzenieIDs[j] = JedzenieID.getInt(j);
                            JedzenieIloscs[j] = JedzenieIlosc.getInt(j);
                        }
                        osiagniecie.setJedzenie(JedzenieIDs,JedzenieIloscs);
                    }
                }
                if(Nagroda.has("PotkaID")&&Nagroda.has("JedzenieIlosc")){
                    JSONArray PotkaID = Nagroda.getJSONArray("PotkaID");
                    JSONArray PotkaIlosc = Nagroda.getJSONArray("PotkaIlosc");
                    if(PotkaIlosc.length()==PotkaID.length()){
                        int[] PotkiIDs = new int[PotkaID.length()];
                        int[] PotkiIloscs = new int[PotkaIlosc.length()];
                        for(int j=0;j<PotkaID.length();j++){
                            PotkiIDs[j] = PotkaID.getInt(j);
                            PotkiIloscs[j] = PotkaIlosc.getInt(j);
                        }
                        osiagniecie.setPotki(PotkiIDs,PotkiIloscs);
                    }
                }
                if(Nagroda.has("CzapkaID")){
                    JSONArray CzapkaID = Nagroda.getJSONArray("CzapkaID");
                    int[] CzapkaIDs = new int[CzapkaID.length()];
                    for(int j=0;j<CzapkaID.length();j++){
                        CzapkaIDs[j] = CzapkaID.getInt(j);
                    }
                    osiagniecie.setCzapki(CzapkaIDs);
                }
                if (osiagnieciaJSON.has("CzyWidoczne")) {
                    czyWidoczne = osiagnieciaJSON.getBoolean("CzyWidoczne");
                    osiagniecie.setCzyWidoczne(czyWidoczne);
                }
                if(hasNextStage)stagecounter++;
                else stagecounter=0;
                listaOsiagniec.add(osiagniecie);
            }
            zaladowano_osiagniecia = true;
            for (Achievements achievements : listaOsiagniec) {
                Log.d("MojaAplikacja", "ID: " + achievements.getId() + ", Nazwa: " + achievements.getTresc(getLang()) + ", Opis: " + achievements.getKomentarz(getLang())+", Progress: "+ achievements.getCurrentProgress()+", CzyWidoczne: "+achievements.getCzyWidoczne());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void addProgressOsiagniecia(int id){
        for(int i=0;i<listaOsiagniec.size();i++){
            if(listaOsiagniec.get(i).getId()==id){
                if(!listaOsiagniec.get(i).getIsComplete()){
                    listaOsiagniec.get(i).addProgress();
                    Log.d("JakiQuest",listaOsiagniec.get(i).getTresc(getLang()));
                    Log.d("JakiStage",String.valueOf(listaOsiagniec.get(i).getStage()));
                    if(listaOsiagniec.get(i).getIsComplete()){
                        Log.d("Czapki",String.valueOf(listaOsiagniec.get(i).hasCzapki));
                        Log.d("Jedzenie",String.valueOf(listaOsiagniec.get(i).HasJedzenie));
                        if(listaOsiagniec.get(i).getIsComplete()==true){
                            Pokaz_Topbar();
                            Aktualizuj_Hajs(getMoney(),getMoney()+listaOsiagniec.get(i).getHajs());
                            Aktualizuj_Exp(getExp(),getExp()+getMoney()+listaOsiagniec.get(i).getEXP());
                            Ukryj_Topbar();
                            AddExp(listaOsiagniec.get(i).getEXP());
                            SaveMoney(listaOsiagniec.get(i).getHajs());
                            if(listaOsiagniec.get(i).hasCzapki){
                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                int[] czapkiid = listaOsiagniec.get(i).getCzapkiID();

                                for(int j=0;j<czapkiid.length;j++){
                                    Log.d("czapkaID",String.valueOf(czapkiid[j]));
                                    editor.putBoolean(PURCHASEDCZAPKA+String.valueOf(czapkiid[j]),true);
                                    editor.apply();
                                    listaCzapek.get(czapkiid[j]).setCzyZakupiona(true);
                                }
                            }
                            if(listaOsiagniec.get(i).HasJedzenie){
                                int[] jedzenieid = listaOsiagniec.get(i).getJedzenieID();
                                int[] jedzenieilosc = listaOsiagniec.get(i).getJedzenieIlosc();
                                for(int j=0;j<jedzenieilosc.length;j++){
                                    listaZarcia.get(jedzenieid[j]).DodajJedzenie(jedzenieilosc[j]);
                                }
                            }
                            if(listaOsiagniec.get(i).HasPotki){
                                int[] jedzenieid = listaOsiagniec.get(i).getPotkiID();
                                int[] jedzenieilosc = listaOsiagniec.get(i).getPotkiIlosc();
                                for(int j=0;j<jedzenieilosc.length;j++){
                                    listaPotek.get(jedzenieid[j]).DodajJedzenie(jedzenieilosc[j]);
                                }
                            }
                        }
                    }
                    break;
                }
            }
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
                Potka potka = new Potka(id, plik,nazwaArr,opisArr, cena,czasTrwania,getApplicationContext()); // Zaktualizuj obiekt Czapka
                if(potkiJSON.has("czyDostepne")){
                    potka.setCzyDostepna(potkiJSON.getBoolean("czyDostepne"));
                }
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
            } else if (listaCzapek.get(i).isCzyZakupiona()) {
                akcja[i] = 1;
            } else {
                akcja[i] = 0;
            }
        }

    }
    public void UpdateQuestDate(){
        if(User.UpdateQuestDate(listaQuestowId,listaQuestow,zaladowano_questy)==-1){
            loadQuestsFromAsset();
        }
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
                for(int i=0;i<button.length;i++){
                    Log.w("cototjest","kup_czapka" + String.valueOf(i));
                    if (i==current_item_index) {
                        button[i].setText(getString(R.string.zdejmij));
                        akcja[i] = 2;
                    } else if (listaCzapek.get(i).isCzyZakupiona()) {
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
    public void Generuj_Achievementy(LinearLayout parent,Achievements osiagniecie,boolean show){
        RelativeLayout mainRelativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        mainParams.setMargins(0,0,0,dpToPx(5));
        mainRelativeLayout.setLayoutParams(mainParams);
        mainRelativeLayout.setPadding(13, 13, 13, 13);
        mainRelativeLayout.setBackgroundResource(R.drawable.custom_quest_bg);

        TextView Nazwa = new TextView(this);
        TextView Podtytul = new TextView(this);
        TextView Komentarz = new TextView(this);
        ProgressBar Progress = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        TextView TextProgress = new TextView(this);
        Nazwa.setId(View.generateViewId());
        Podtytul.setId(View.generateViewId());
        Komentarz.setId(View.generateViewId());
        Progress.setId(View.generateViewId());
        TextProgress.setId(View.generateViewId());
        Nazwa.setTextSize(20);
        Nazwa.setTextColor(getResources().getColor(R.color.ckblack));
        if(!osiagniecie.getCzyWidoczne()){
            Nazwa.setTextColor(getResources().getColor(R.color.ckgolden));
        }
        Podtytul.setTextColor(getResources().getColor(R.color.ckblack));
        Komentarz.setTextColor(getResources().getColor(R.color.ckblack));
        TextProgress.setTextColor(getResources().getColor(R.color.ckblack));
        Podtytul.setTextSize(15);
        Komentarz.setTextSize(13);
        TextProgress.setTextSize(15);
        TextProgress.setText("("+osiagniecie.getCurrentProgress()+"/"+osiagniecie.getMaxProgress()+")");
        if(show){
            Nazwa.setText(osiagniecie.getTresc(getLang()));
            Komentarz.setText(osiagniecie.getKomentarz(getLang()));
            Podtytul.setText(osiagniecie.getPodtytul(getLang()));
        }else{
            Nazwa.setText("???");
            Komentarz.setText("???");
            Podtytul.setText("???");
        }

        Progress.setMax(osiagniecie.getMaxProgress());
        Progress.setProgress(osiagniecie.getCurrentProgress());
        // Ustawienie layout params dla wszystkich TextView
        RelativeLayout.LayoutParams nazwaParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams podtytulParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams komentarzParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams progressParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams textprogressParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        // Dodanie reguł dla Podtytulu i Komentarza
        podtytulParams.addRule(RelativeLayout.BELOW, Nazwa.getId());
        komentarzParams.addRule(RelativeLayout.BELOW, Podtytul.getId());
        progressParams.addRule(RelativeLayout.BELOW, Komentarz.getId());
        textprogressParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        textprogressParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        // Ustawienie layout params dla Podtytulu i Komentarza
        Podtytul.setLayoutParams(podtytulParams);
        Komentarz.setLayoutParams(komentarzParams);
        TextProgress.setLayoutParams(textprogressParams);
        Progress.setLayoutParams(progressParams);
        Progress.setProgressDrawable(getResources().getDrawable(getResources().getIdentifier("custom_progressbar", "drawable", getPackageName())));
        Progress.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.ckred)));
        // Dodanie TextView do RelativeLayout
        mainRelativeLayout.addView(Nazwa, nazwaParams);
        mainRelativeLayout.addView(Podtytul);
        mainRelativeLayout.addView(Komentarz);
        mainRelativeLayout.addView(Progress);
        mainRelativeLayout.addView(TextProgress);
        parent.addView(mainRelativeLayout);
    }
    public Button Generuj_Przedmiot(LinearLayout parent,int PlikResID,String TytulText,int CenaText,boolean premium,boolean dostepne,String Opis,boolean odblokowana,boolean czywidoczne){
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
        if(!czywidoczne&&!odblokowana){
            mainRelativeLayout.setVisibility(View.GONE);
        }
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
        if (!dostepne&&!odblokowana){
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);  //0 means grayscale
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
            czapkaImageView.setColorFilter(cf);
            czapkaImageView.setImageAlpha(128);
        }
        czapkaImageView.setPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        czapkaParams.setMargins(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        czapkaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POPUP_EXCEPTION_MODE = 1;
                ShowPopup(R.layout.popup_layout);
                ImageView czapaImage = myDialog.findViewById(R.id.czapa_image);
                TextView czapaNazwa = myDialog.findViewById(R.id.czapa_nazwa);
                TextView czapaOpis = myDialog.findViewById(R.id.czapa_opis);
                czapaImage.setImageResource(PlikResID);
                czapaNazwa.setText(TytulText);
                czapaOpis.setText(Opis);
                myDialog.show();
                POPUP_EXCEPTION_MODE = 0;
            }
        });
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
    boolean isTopbarVisible=false;
    public void Pokaz_Topbar(){
        if(!isTopbarVisible){
            isTopbarVisible=true;
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
    }
    public void Ukryj_Topbar(){
        RelativeLayout topbar = TopBar.findViewById(R.id.topbar);
        new Handler(getMainLooper()).postDelayed(() -> {
            YoYo.with(Techniques.FadeOutUp).duration(500).playOn(topbar);
            new Handler(getMainLooper()).postDelayed(() -> {
                TopBar.dismiss();
                isTopbarVisible=false;
            }, 500);
        }, 2000);
    }
    public void Aktualizuj_Hajs(int hajs,int nowyhajs){
        Log.d("hajs",String.valueOf(hajs));
        Log.d("nowyhajs",String.valueOf(nowyhajs));
        if(isTopbarVisible){
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
    }
    public void Aktualizuj_Exp(int Exp,int nowyExp){
        Log.d("hajs",String.valueOf(Exp));
        Log.d("nowyhajs",String.valueOf(nowyExp));
        if(isTopbarVisible){
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
                    Button kupButton = new Button(this);
                    if(listaCzapek.get(i).getczyWidoczna()||listaCzapek.get(i).isCzyZakupiona()){
                        kupButton = Generuj_Przedmiot(parentLayout,obrazResId,czapa.getNazwa(getLang()),czapa.getCena(),czapa.getCzyPremium(),czapa.getCzyDostepna(),czapa.getOpis(getLang()),czapa.isCzyZakupiona(),czapa.getczyWidoczna());
                    }
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
                    Button kupButton = Generuj_Przedmiot(parentLayout,obrazResId,jedzenie.getNazwa(getLang()),jedzenie.getCena(),jedzenie.getCzyPremium(),jedzenie.getCzyDostepna(),jedzenie.getOpis(getLang()),false,true);
                    kupButton.setText(getString(R.string.kup));
                    kupButton.setEnabled(jedzenie.getCzyDostepna());
                    int finalI = i;
                    kupButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            KupCiastka(v, finalI);
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
                    Button kupButton = Generuj_Przedmiot(parentLayout,obrazResId,potka.getNazwa(getLang()),potka.getCena(),potka.getCzyPremium(),potka.getCzyDostepna(),potka.getOpis(getLang()),false,true);
                    kupButton.setText(getString(R.string.kup));
                    kupButton.setEnabled(potka.getCzyDostepna());
                    int finalI = i;
                    kupButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            KupPotki(v, finalI);
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
            Log.w("POPUP_VISIBLE",String.valueOf(isPopupVisible));
            if(akcja[listaCzapek.get(przyciskNumer).getId()]==0){
                if(!listaCzapek.get(przyciskNumer).getCzyDostepna()){
                    Toast.makeText(MainActivity.this, "Nie możesz kupić tej czapki", Toast.LENGTH_SHORT).show();
                }else{
                if(User.getMoney()<listaCzapek.get(przyciskNumer).getCena()){
                    Toast.makeText(MainActivity.this, "Nie masz wystarczająco crococoinów na zakup", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(isPopupVisible==false) {
                        POPUP_EXCEPTION_MODE = 1;
                        ShowPopup(R.layout.kupienie_czapki);
                        Button kupCzapaButton = myDialog.findViewById(R.id.kup_czapa);
                        Log.w("akcjasuper", String.valueOf(akcja[listaCzapek.get(przyciskNumer).getId()]));
                            kupCzapaButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int dostepneHajsy = getMoney();
                                    if (dostepneHajsy >= listaCzapek.get(przyciskNumer).getCena()) {
                                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean(PURCHASEDCZAPKA + listaCzapek.get(przyciskNumer).getId(), true);
                                        editor.apply();
                                        sharedPreferences.getBoolean(PURCHASEDCZAPKA + listaCzapek.get(przyciskNumer).getId(), false);
                                        listaCzapek.get(przyciskNumer).setCzyZakupiona(true);
                                        int noweHajsy = listaCzapek.get(przyciskNumer).getCena() * -1;
                                        SaveMoney(noweHajsy);
                                        Toast.makeText(MainActivity.this, "Zakupiono " + listaCzapek.get(przyciskNumer).getNazwa(getLang()), Toast.LENGTH_SHORT).show();
                                        addProgress(3);
                                        ClosePopup();
                                        aktualizujTextPrzyciskow(kupButton);
                                        updateCrococoinsInShop();
                                        addProgressOsiagniecia(5);
                                    }
                                }
                            });
                            myDialog.show();
                            POPUP_EXCEPTION_MODE = 0;
                        }
                    }
                }
            }
            else if(akcja[listaCzapek.get(przyciskNumer).getId()]==1){
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(CURRENT_ITEM_ID,listaCzapek.get(przyciskNumer).getId());
                editor.apply();
                current_item_index = sharedPreferences.getInt(CURRENT_ITEM_ID,-1);
                aktualizujTextPrzyciskow(kupButton);
            }
            else if(akcja[listaCzapek.get(przyciskNumer).getId()]==2){
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
    public void KupCiastka(View v,int idJedzenia){
        if(getMoney()>=listaZarcia.get(idJedzenia).getCena()){
            listaZarcia.get(idJedzenia).DodajJedzenie();
            SaveMoney(-listaZarcia.get(idJedzenia).getCena());
            updateCrococoinsInShop();
        }
        else{
            Toast.makeText(MainActivity.this, "Nie masz wystarczająco crococoinów na zakup", Toast.LENGTH_SHORT).show();
        }
    }
    public void KupPotki(View v,int idPotki){
        if(getMoney()>=listaPotek.get(idPotki).getCena()){
            listaPotek.get(idPotki).DodajJedzenie(1);
            SaveMoney(-listaPotek.get(idPotki).getCena());
            updateCrococoinsInShop();
            addProgressOsiagniecia(6);
        }
        else{
            Toast.makeText(MainActivity.this, "Nie masz wystarczająco crococoinów na zakup", Toast.LENGTH_SHORT).show();
        }
    }
    public int GetRadioButtonChecked(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(CHECKEDLANG, 0);
    }
    public int getMoney() {
        return User.getMoney();
    }
    public String getLang() {
        return User.getLang();
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
        ImageView JedzenieButton = findViewById(R.id.jedzeniebutton);
        TextView ukrytytekst = findViewById(R.id.liczba_ciastek);
        TextView ukrytytekst2 = findViewById(R.id.liczba_ciastekShadow);
        ImageView zdj = findViewById(R.id.rzeczimg);
        switch(SELECTED_LIST){
            case 0:
            {
                Log.d("??",String.valueOf(listaZarcia.size()));
                String ilosc = String.valueOf(listaZarcia.get(SELECTED_FOOD).getZapisanaIlosc());
                zdj.setImageResource(getResources().getIdentifier("drawable/"+listaZarcia.get(SELECTED_FOOD).getPlik(), null, getApplicationContext().getPackageName()));
                ukrytytekst.setText(ilosc);
                ukrytytekst2.setText(ilosc);
                ustawStateJedzenia(SELECTED_FOOD);
            }
            break;
            case 1:
            {
                String ilosc = String.valueOf(listaPotek.get(SELECTED_FOOD).getZapisanaIlosc());
                zdj.setImageResource(getResources().getIdentifier("drawable/"+listaPotek.get(SELECTED_FOOD).getPlik(), null, getApplicationContext().getPackageName()));
                ukrytytekst.setText(ilosc);
                ukrytytekst2.setText(ilosc);
                ustawStateJedzenia(SELECTED_FOOD);
            }
            break;
        }

    }
    public void ustawStateJedzenia(int id){
        switch (SELECTED_LIST){
            case 0:
            {
                ImageView miska = findViewById(R.id.jedzeniebutton);
                if(listaZarcia.get(id).getZapisanaIlosc()>0){
                    miska.setImageResource(R.drawable.pelnamiskapsa);
                }
                else{
                    miska.setImageResource(R.drawable.pustamiskapsa);
                }
            }
                break;// jedzenie
            case 1:
                ImageView miska = findViewById(R.id.jedzeniebutton);
                miska.setImageResource(R.drawable.garnek);
                break;
        }
    }
    boolean czyselectpokazany = false;
    public void PokazJedzenieSelect(View v){
        LinearLayout container = findViewById(R.id.selectJedzenie);
        if(czyselectpokazany){
            YoYo.with(Techniques.SlideOutRight)
                    .duration(300)
                    .playOn(container);
            new Handler(getMainLooper()).postDelayed(() -> {
                try{
                    container.setVisibility(View.GONE);
                } catch (Exception ignored) {
                    throw new RuntimeException(ignored);
                }
            }, 300);
            czyselectpokazany = false;
        }
        else{
            container.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInRight)
                    .duration(300)
                    .playOn(container);
            czyselectpokazany = true;
        }
        PokazMiske(v);
    }
    public void PokazMiske(View v){
        if(czypokazana){
            RelativeLayout container = findViewById(R.id.karmieniecontainer);
            LinearLayout strzalki = findViewById(R.id.strzalki);
            YoYo.with(Techniques.SlideOutLeft)
                    .duration(300)
                    .playOn(container);
            YoYo.with(Techniques.SlideOutLeft)
                    .duration(300)
                    .playOn(strzalki);

            new Handler(getMainLooper()).postDelayed(() -> {
                try{
                    container.setVisibility(View.GONE);
                    strzalki.setVisibility(View.GONE);
                } catch (Exception ignored) {
                    throw new RuntimeException(ignored);
                }
            }, 300);
            czypokazana = false;
        }
        else{
            RelativeLayout container = findViewById(R.id.karmieniecontainer);
            LinearLayout strzalki = findViewById(R.id.strzalki);
            container.setVisibility(View.VISIBLE);
            strzalki.setVisibility(View.VISIBLE);

            YoYo.with(Techniques.SlideInLeft)
                    .duration(300)
                    .playOn(container);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(300)
                    .playOn(strzalki);
            czypokazana = true;
        }
        ustawMiche();
    }
    public void ZmienPrzedmiotInc(View v){
        int ListSize=0;
        if(SELECTED_LIST==0) ListSize = listaZarcia.size();
        if(SELECTED_LIST==1) ListSize = listaPotek.size();
        if(ListSize>SELECTED_FOOD+1){
            SELECTED_FOOD++;
        }
        else{
            SELECTED_FOOD=0;
        }
        ustawMiche();
    }
    public void ZmienPrzedmiotDec(View v){
        int ListSize=0;
        if(SELECTED_LIST==0) ListSize = listaZarcia.size();
        if(SELECTED_LIST==1) ListSize = listaPotek.size();
        if(SELECTED_FOOD>0){
            SELECTED_FOOD--;
        }
        else{
            SELECTED_FOOD=ListSize-1;
        }
        ustawMiche();
    }
    public void ZmienListeInc(View v){
        if(Consumables.length>SELECTED_LIST+1){
            SELECTED_LIST++;
        }
        else{
            SELECTED_LIST=0;
        }
        SELECTED_FOOD=0;
        ustawMiche();
    }
    public void ZmienListeDec(View v){
        if(SELECTED_LIST>0){
            SELECTED_LIST--;
        }
        else{
            SELECTED_LIST=Consumables.length-1;
        }
        SELECTED_FOOD=0;
        ustawMiche();
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
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            switch (SELECTED_LIST){
                case 0:
                {
                    if(listaZarcia.get(SELECTED_FOOD).getZapisanaIlosc()>0){
                        if(lastFeed<umiera_po){
                            Toast.makeText(getApplicationContext(),"Croco nie żyje..",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            addProgress(2);
                            Hapaba();
                            editor.putLong(LAST_FEED, sharedPreferences.getLong(LAST_FEED,0)+listaZarcia.get(SELECTED_FOOD).getNasycenie());
                            editor.apply();
                            lastFeed = sharedPreferences.getLong(LAST_FEED,0);
                            LoadData();
                            Animacja_Jedzenia();
                            listaZarcia.get(SELECTED_FOOD).Zjedz(getLang());
                            ustawMiche();
                            ustawCzapke();
                            User.Stats("ZJEDZONE_JEDZENIE",1);
                            addProgressOsiagniecia(12);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"brak ciasteczek :(",Toast.LENGTH_SHORT).show();
                    }
                }
                    break;
                case 1:
                {
                    if(listaPotek.get(SELECTED_FOOD).getZapisanaIlosc()>0){
                        switch (listaPotek.get(SELECTED_FOOD).getId()){
                            case 0:
                                editor.putLong(LAST_FEED, 0);
                                editor.apply();
                                break;
                        }
                        listaPotek.get(SELECTED_FOOD).Zjedz();
                        addProgress(5);
                        Hapaba();
                        lastFeed = sharedPreferences.getLong(LAST_FEED,0);
                        LoadData();
                        Animacja_Jedzenia();
                        ustawMiche();
                        ustawCzapke();
                        User.Stats("ZJEDZONE_POTKI",1);
                    }else{
                        Toast.makeText(getApplicationContext(),"brak potki :(",Toast.LENGTH_SHORT).show();
                    }
                }
                    break;
            }

        }
    }
    public void loadCurrentCzapka(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        current_item_index = sharedPreferences.getInt(CURRENT_ITEM_ID, -1);
    }
    void UstawKrokodyla(){
        czerwony_krokodyl = findViewById(R.id.krokodyl);
        //Toast.makeText(this, "LF"+String.valueOf(lastFeed), Toast.LENGTH_SHORT).show();
        if(lastFeed>gloduje_co&&lastFeed>umiera_po){
           czerwony_krokodyl.setImageResource(R.drawable.logo);
           User.CrocoState = 0;
        } else if(lastFeed>umiera_po&&lastFeed<gloduje_co){
            czerwony_krokodyl.setImageResource(R.drawable.glodnykroko);
            User.CrocoState = 1;
        }else{
            czerwony_krokodyl.setImageResource(R.drawable.ripcroco);
            User.CrocoState = 2;
            addProgressOsiagniecia(4);
        }
        //Toast.makeText(this, String.valueOf(User.CrocoState), Toast.LENGTH_SHORT).show();
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
        User.Stats(PASSED_TESTS,1);
    }
    public void SaveFailedTest(){
        User.Stats(FAILED_TESTS,1);
    }
    public void DebugSetDate(String ddMMyyyy){
    currentDate = ddMMyyyy;
    }
    public void SaveQuestDate(){
        if(User.getDaily()==1){
            User.ResetAllQuestProgress(listaQuestow);
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
        User.setMoney(value);
    }
    public void SaveFood(int value){
        User.setFood(value);
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
        //DebugSetGlodny();
        if(lastFeed==0){
            lastFeed = sharedPreferences.getLong(LAST_FEED,0);
        }
        Log.w("saved_time",String.valueOf(savedTime));
        Log.w("current_time",String.valueOf(currentTime));
        Log.w("time_calc",String.valueOf(currentTime-savedTime));
        Log.w("lastFeedVal",String.valueOf(lastFeed));
        if(canplayanimations) {
            UstawKrokodyla();
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
            kasa.setText(String.valueOf(User.getMoney()));
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
                    addProgressOsiagniecia(7);
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
        else if(v.getId()==R.id.Osiagniecia){
            setContentView(R.layout.osiagniecia);
            LinearLayout lin = findViewById(R.id.osiagniecia_scroll);
            int acID=-1;
            boolean prevshown=false;
            for(int i=0;i<listaOsiagniec.size();i++){
                Achievements ac = listaOsiagniec.get(i);
                if(acID!=-1){
                    if(acID!=ac.getId()) prevshown=false;
                }
                if(!prevshown) {
                    acID = ac.getId();
                    prevshown = true;
                    if(ac.getIsComplete()&&ac.getHasNextStage()){
                        prevshown=false;
                    }
                    else{
                        if(ac.getCzyWidoczne()){
                            Generuj_Achievementy(lin, ac,true);
                        }else{
                            if(ac.getIsComplete()){
                                Generuj_Achievementy(lin, ac,true);
                            }else{
                                Generuj_Achievementy(lin, ac,false);
                            }
                        }
                    }
                }
            }
            AddActions("Osiagniecia");
        }
        else if(v.getId()==R.id.kolo_fortuny){
            if(isPopupVisible==false){
                if(czyselectpokazany) PokazJedzenieSelect(v);
                POPUP_RESOLUTION = 1;
                ShowPopup(R.layout.popup_kolo_fortuny);
                generateSectorDegrees();
                deg =0;
                POPUP_RESOLUTION = 0;
            }
        }
        else if(v.getId()==R.id.questy){
            if(!isPopupVisible){
                if(czyselectpokazany) PokazJedzenieSelect(v);
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
            addProgressOsiagniecia(3);
        }
        else if (v.getId()==R.id.OtworzSklep){
            setContentView(R.layout.sklep);
            updateCrococoinsInShop();
            Generuj_Sklep("czapki");
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
            ResetAllQuestions();
            setContentView(R.layout.pytania_wybor);
            Create_Question_Buttons();
            ClosePopup();
            bgmusictesty();
            AddActions("pytania_wybor");
        }
        else if (v.getId()==R.id.Rozpocznij_test2){
            ResetAllQuestions();
            setContentView(R.layout.pojedyncze_pytanie);
            CHALLENGE_MODE = false;
            Set_pytanie_Poj();
            ClosePopup();
            bgmusictesty();
            AddActions("pojedyncze_pytanie");
        }
        else if(v.getId()==R.id.testy_kategoria){
            ResetAllQuestions();
            setContentView(R.layout.pytania_wybor_kategoria);
            Create_Kategoria_Buttons();
            ClosePopup();
            bgmusictesty();
            AddActions("pytania_wybor_kategoria");
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
        czyselectpokazany = false;
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
        Log.w("actionslist",actions.toString());
    }
    public void ResetActions(List<String> Stringlist){
        Stringlist.clear();
        Log.w("stringlist",Stringlist.toString());
    }
    public void AddActions(String str){
        actions.add(str);
        Log.w("actionslist",actions.toString());
    }
    public void AddActions(String str,List<String> Stringlist){
        Stringlist.add(str);
        Log.w("stringlist",Stringlist.toString());
    }
    public void RemoveAction(){
        actions.remove(actions.size()-1);
        Log.w("actionslist",actions.toString());
    }
    public void RemoveAction(List<String> Stringlist){
        Stringlist.remove(Stringlist.size()-1);
        Log.w("stringlist",Stringlist.toString());
    }
    //tutaj sekcja z popupami #SECPOPUP
    public void ShowPopup(int layout){
        popups.add(layout);
        if(popups.size()>1){
            if(popups.get(popups.size()-1).equals(popups.get(popups.size()-2))) popups.remove(popups.size()-1);
        }
        if(!isPopupVisible){
            Log.d("isvisible","przeszlo");
        isPopupVisible = true;
        if(POPUP_RESOLUTION==1)
            myDialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        else
            myDialog = new Dialog(this);

        myDialog.setContentView(popups.get(0));
        myDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                isPopupVisible = false;
                popups.remove(0);
                ShowPopup();
            }
        });
        switch(POPUP_EXCEPTION_MODE){
            case 0:myDialog.show();break;
            case 1:/*don't show dialog */break;
            default:myDialog.show();break;
        }
        }
    }
    public void ShowPopup(){
        if(popups.size()>0){
        if(!isPopupVisible){
            isPopupVisible = true;
            if(POPUP_RESOLUTION==1)
                myDialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
            else
                myDialog = new Dialog(this);

            myDialog.setContentView(popups.get(0));


            myDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    isPopupVisible = false;
                    popups.remove(0);
                }
            });
            switch(POPUP_EXCEPTION_MODE){
                case 0:myDialog.show();break;
                case 1:/*don't show dialog */break;
                default:myDialog.show();break;
            }
        }
    }
    }
    public void Zamknij_Popup(View v){
        ClosePopup();
    }
    public void ClosePopup(){
        myDialog.dismiss();
        isPopupVisible = false;
        popups.remove(0);
        ShowPopup();
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
        User.loadQuestProgress(listaQuestow);
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
        User.addProgress(id,listaQuestow);
    }
    public void Claim(int id){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Quests quest = listaQuestow.get(id);
        if(quest.getClaim()!=true){
            addProgressOsiagniecia(10);
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
        int katIDs=0;
        if(response.equals("")){
            String id,poprawne,tresc,wyjasnienie,A,B,C,D,kategoria,KatID;
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
                        PytaniaDB pytanie = new PytaniaDB(Integer.valueOf(id),tresc,poprawne.charAt(0),A,B,C,D,wyjasnienie,kategoria,0);
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
                String id, poprawne, tresc, wyjasnienie, A, B, C, D,kategoria,KatID;
                boolean helper=false;
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
                    KatID = jsonObject2.getString("katID");
                    int j=0;
                    boolean hasKat = false;
                    while(j<listaKategorii.size()){
                        if(listaKategorii.get(j)==Integer.valueOf(KatID)){
                            hasKat=true;
                        }
                        j++;
                    }
                    if(!hasKat){
                        listaKategorii.add(Integer.valueOf(KatID));
                        podzielonaListaPytan.add(new ArrayList<PytaniaDB>());
                    }
                    PytaniaDB pytanie = new PytaniaDB(Integer.valueOf(id),tresc,poprawne.charAt(0),A,B,C,D,wyjasnienie,kategoria,Integer.valueOf(KatID));
                    podzielonaListaPytan.get(listaKategorii.indexOf(Integer.valueOf(KatID))).add(pytanie);
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
        try{
            listaShuffled.addAll(listaPytan);
        }catch (Exception e){
            
        }
        //DebugTestWypiszPytania();
        //DebugTestWypiszKategorie(9);
    }
    public void DebugTestWypiszPytania(){
        for(int i=0;i<podzielonaListaPytan.size();i++){
            for(int j=0;j<podzielonaListaPytan.get(i).size();j++){
                podzielonaListaPytan.get(i).get(j).Wypisz();
            }
        }
    }
    public void DebugTestWypiszKategorie(int id){
        id=id-1;
        for(int j=0;j<podzielonaListaPytan.get(id).size();j++){
            podzielonaListaPytan.get(id).get(j).Wypisz();
        }
    }
    public int CURRENT_INDEX;
    public int KATEGORIA_ID;
    public void Create_Kategoria_Buttons(){
        Log.d("testaction","creating kategoria");
        try{
            LinearLayout layout = findViewById(R.id.questions_select);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20);
            for(int i=0;i<podzielonaListaPytan.size();i++){
                Button btn_tests = new Button(this);
                btn_tests.setLayoutParams(params);
                btn_tests.setText(podzielonaListaPytan.get(i).get(0).getKategoria()+" ("+podzielonaListaPytan.get(i).size()+")");
                btn_tests.setTextSize(30);
                btn_tests.setTag(i);
                int finalI = i;
                btn_tests.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            KATEGORIA_ID = finalI;
                            SET_TEST_ID = listaShuffled.get(KATEGORIA_ID).getId();
                            setContentView(R.layout.pytanie_kategoria);
                            AddActions("pojedyncze_pytanie");
                            Set_pytanie_kategoria();
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
                btn_tests.setTag(i);
                if(listaShuffled.get(i).getOdpUzytkownika()!='-'){
                    btn_tests.setBackgroundColor(Color.parseColor("#4f5d75"));
                    btn_tests.setTextColor(Color.parseColor("#ffffff"));
                } else{
                    btn_tests.setBackgroundColor(Color.parseColor("#333333"));
                    btn_tests.setTextColor(Color.parseColor("#ffffff"));
                }
                int finalI = i;
                btn_tests.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            setContentView(R.layout.pytanie_wyglad);
                            CURRENT_INDEX = finalI;
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
                btn_tests.setTag(i);
                PytaniaDB pytanie = listaShuffled.get(i);
                if(pytanie.getOdpUzytkownika()!=pytanie.getPoprawnaOdp()){
                    btn_tests.setBackgroundColor(color_incorrect);
                } else{
                    btn_tests.setBackgroundColor(color_correct);
                }
                if(pytanie.getOdpUzytkownika()=='-')
                    btn_tests.setBackgroundColor(R.color.ckbez);
                btn_tests.setTextColor(Color.parseColor("#000000"));

                int finalI = i;
                btn_tests.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            CURRENT_INDEX = finalI;
                            SET_TEST_ID = listaShuffled.get(CURRENT_INDEX).getId();
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
        int localqnum=0;
        ResetAllQuestions();
        List<PytaniaDB> ListaPytan = new ArrayList<>();
        List<PytaniaDB> PreShuffle = new ArrayList<>();
        for(int i=0;i<podzielonaListaPytan.size();i++){
            PreShuffle.clear();
            localqnum=0;
            localqnum = rand_num.nextInt(3)+1;
            PreShuffle.addAll(podzielonaListaPytan.get(i));
            Collections.shuffle(PreShuffle);
            if(localqnum<=podzielonaListaPytan.get(i).size()){
                for(int j=0;j<localqnum;j++){
                    ListaPytan.add(PreShuffle.get(j));
                }
            }
            else{
                for(int j=0;j<podzielonaListaPytan.get(i).size();j++){
                    ListaPytan.add(PreShuffle.get(j));
                }
            }

        }
        q_num = ListaPytan.size();
        listaShuffled.clear();
        listaShuffled.addAll(ListaPytan);
        //Collections.shuffle(listaShuffled);
        Resume_Question_Buttons();
    }
    public void ResetAllQuestions(){
        Log.d("testaction","reseting");
        listaShuffled.clear();
        for(int i=0;i<listaPytan.size();i++){
            listaShuffled.add(listaPytan.get(i));
            listaShuffled.get(i).ResetAnswer();
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
                    SET_TEST_ID = listaShuffled.get(CURRENT_INDEX).getId();
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
        else if(v.getId()==R.id.nextkatpytanie){
            Set_pytanie_kategoria();
        }
    }
    public void PrevPytanie(View v){
        if(v.getId()==R.id.prevbutton||v.getId()==R.id.prevbuttonbledne){
            if(CURRENT_INDEX>0){
                try{
                    wywolajfunkcje = false;
                    CURRENT_INDEX--;
                    SET_TEST_ID = listaShuffled.get(CURRENT_INDEX).getId();
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
                PytaniaDB pytanie = listaShuffled.get(CURRENT_INDEX);
                ImageView wyjasnienie = findViewById(R.id.wyjasnienie_main);
                String wyjasnienie_tekst = pytanie.getWyjasnienie();
                wyjasnienie.setBackground(Math_syn.set_Math(wyjasnienie_tekst));
            } catch (Exception e){

            }
        }
        if(v.getId()==R.id.wyjasnij_poj){
            if(oneshot==false){
                try{
                    PytaniaDB pytanie = listaShuffled.get(CURRENT_INDEX);
                    ImageView wyjasnienie = findViewById(R.id.tresc_pytania_i_wyjasnienie);
                    String wyjasnienie_tekst = pytanie.getWyjasnienie();
                    Test_Math_drawable=Math_syn.set_Math(wyjasnienie_tekst);
                    wyjasnienie.setBackground(Test_Math_drawable);
                } catch (Exception e){

                }
            }
        }
        if(v.getId()==R.id.wyjasnij_kat){
            if(oneshot==false){
                try{
                    PytaniaDB pytanie = podzielonaListaPytan.get(KATEGORIA_ID).get(CURRENT_INDEX);
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
        TextView numer_pytania = findViewById(R.id.numer_pytania);
        numer_pytania.setText(getString(R.string.pytanie)+" "+(CURRENT_INDEX + 1));
        Button[] buttony = {
                findViewById(R.id.odp_A),
                findViewById(R.id.odp_B),
                findViewById(R.id.odp_C),
                findViewById(R.id.odp_D)
        };
        ImageView tes = findViewById(R.id.tresc_pytania);
        PytaniaDB pytanie = listaShuffled.get(CURRENT_INDEX);
        SetPytanieParent(buttony,pytanie,tes);
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
    }
    int rerollindex=-1;
    public void Set_pytanie_kategoria(){
        Random r = new Random();
        CURRENT_INDEX = r.nextInt(podzielonaListaPytan.get(KATEGORIA_ID).size());
        switch(rerollindex){
            case -1:{
                rerollindex=CURRENT_INDEX;
            }break;
            default:{
                Log.d("index",String.valueOf(CURRENT_INDEX));
                if(podzielonaListaPytan.get(KATEGORIA_ID).size()>1) {
                    while (rerollindex == CURRENT_INDEX) {
                        CURRENT_INDEX = r.nextInt(podzielonaListaPytan.get(KATEGORIA_ID).size());
                        Log.d("ROLLINN",String.valueOf(CURRENT_INDEX));
                    }
                    rerollindex=CURRENT_INDEX;
                }
            }break;
        }

        oneshot=true;
        Button[] buttony = {
                findViewById(R.id.odp_A_Poj),
                findViewById(R.id.odp_B_Poj),
                findViewById(R.id.odp_C_Poj),
                findViewById(R.id.odp_D_Poj)
        };
        ResetAllQuestions();
        listaShuffled.clear();
        listaShuffled.addAll(podzielonaListaPytan.get(KATEGORIA_ID));
        listaShuffled.get(CURRENT_INDEX).ResetAnswer();
        ImageView tes = findViewById(R.id.tresc_pytania_i_wyjasnienie);
        PytaniaDB pytanie = listaShuffled.get(CURRENT_INDEX);
        SetPytanieParent(buttony,pytanie,tes);
    }
    public void Set_pytanie_Poj(){
        Random r = new Random();
        CURRENT_INDEX = r.nextInt(listaPytan.size());
        oneshot=true;
        if(CHALLENGE_MODE!=true){
            Button wyjasnienie_button = findViewById(R.id.wyjasnij_poj);
            wyjasnienie_button.setEnabled(false);
        }
        Button[] buttony = {
                findViewById(R.id.odp_A_Poj),
                findViewById(R.id.odp_B_Poj),
                findViewById(R.id.odp_C_Poj),
                findViewById(R.id.odp_D_Poj)
        };
        Collections.shuffle(listaShuffled);
        listaShuffled.get(CURRENT_INDEX).ResetAnswer();
        ImageView tes = findViewById(R.id.tresc_pytania_i_wyjasnienie);
        PytaniaDB pytanie = listaShuffled.get(CURRENT_INDEX);
        SetPytanieParent(buttony,pytanie,tes);
    }
    public void Set_pytanie_bledne(){
        Button[] buttony = {
                findViewById(R.id.odp_Abledne),
                findViewById(R.id.odp_Bbledne),
                findViewById(R.id.odp_Cbledne),
                findViewById(R.id.odp_Dbledne)
        };
        ImageView tes = findViewById(R.id.tresc_pytaniabledne);
        PytaniaDB pytanie = listaShuffled.get(CURRENT_INDEX);
        SetPytanieParent(buttony,pytanie,tes);
        setBledneParent(buttony,pytanie);
    }
    public void UpdateAnswer_Poj(int questID){
        Button[] buttony = {
                findViewById(R.id.odp_A_Poj),
                findViewById(R.id.odp_B_Poj),
                findViewById(R.id.odp_C_Poj),
                findViewById(R.id.odp_D_Poj)
        };
        PytaniaDB pytanie = listaShuffled.get(CURRENT_INDEX);
        setBledneParent(buttony,pytanie);
        if(pytanie.getOdpUzytkownika()==pytanie.getPoprawnaOdp()){
            addProgress(questID); //może progress do challenge?
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
    }
    public void setAnswer(View v){
        int[] ids = {
                R.id.odp_A,
                R.id.odp_B,
                R.id.odp_C,
                R.id.odp_D
        };
        Button[] buttony = new Button[ids.length];
        for(int i=0;i<ids.length;i++){
            buttony[i] = findViewById(ids[i]);
        }
        PytaniaDB pytanie = listaShuffled.get(CURRENT_INDEX);
        setAnswerParent(v,ids,pytanie);
        UpdateAnswerParent(buttony);
    }
    public void setAnswerPoj(View v){
        int[] ids = {
                R.id.odp_A_Poj,
                R.id.odp_B_Poj,
                R.id.odp_C_Poj,
                R.id.odp_D_Poj
        };
        PytaniaDB pytanie = listaShuffled.get(CURRENT_INDEX);
        setAnswerParent(v,ids,pytanie);
        if(oneshot==true){
            UpdateAnswer_Poj(0);
            if(CHALLENGE_MODE!=true){
                Button wyjasnienie_button = findViewById(R.id.wyjasnij_poj);
                wyjasnienie_button.setEnabled(true);
            }
        }
        oneshot=false;
    }
    public void setAnswerKategoria(View v){
        int[] ids = {
                R.id.odp_A_Poj,
                R.id.odp_B_Poj,
                R.id.odp_C_Poj,
                R.id.odp_D_Poj
        };
        PytaniaDB pytanie = listaShuffled.get(CURRENT_INDEX);
        setAnswerParent(v,ids,pytanie);
        if(oneshot==true){
            UpdateAnswer_Poj(6);
            if(CHALLENGE_MODE!=true){
                Button wyjasnienie_button = findViewById(R.id.wyjasnij_kat);
                wyjasnienie_button.setEnabled(true);
            }
        }
        oneshot=false;
    }
    public double oblicz_percenty;
    public int brak_odp=0;
    void Oblicz_Poprawne(){
        int npoprawne = 0;
        brak_odp=0;
        char odp;
        for(int i=0;i<q_num;i++){
            PytaniaDB pytanie = listaShuffled.get(i);
            odp = pytanie.getOdpUzytkownika();
            try{
                int get_id = listaShuffled.get(i).getId();
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
            addProgressOsiagniecia(11);
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
                TextView nagrodaMultiplier = findViewById(R.id.nagrodamultiplier1);

                LinearLayout.LayoutParams poprawneWeight = (LinearLayout.LayoutParams) poprawne_chart.getLayoutParams();
                LinearLayout.LayoutParams negatywneWeight = (LinearLayout.LayoutParams) negatywne_chart.getLayoutParams();
                LinearLayout.LayoutParams pominieteWeight = (LinearLayout.LayoutParams) pominiete_chart.getLayoutParams();

                poprawne_chart.setText(String.valueOf(poprawne));
                negatywne_chart.setText(String.valueOf(q_num-poprawne-brak_odp));
                pominiete_chart.setText(String.valueOf(brak_odp));

                poprawneWeight.weight = (float) poprawne/q_num;
                negatywneWeight.weight = (float) (q_num-poprawne-brak_odp)/q_num;
                pominieteWeight.weight = (float) brak_odp/q_num;
                double mnoznik = 1;
                if(User.CrocoState==1){
                    mnoznik=0.7;
                }
                if(User.CrocoState==2){
                    mnoznik=0.5;
                }
                int finalnagroda;
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

                        //SaveFood(1);
                        finalnagroda = (int) Math.round(100*mnoznik);
                        SaveMoney(finalnagroda);
                        nagroda1.setText(String.valueOf(finalnagroda));
                        nagrodashadow1.setText(String.valueOf(finalnagroda));
                        Pokaz_Topbar();
                        Aktualizuj_Hajs(getMoney()-finalnagroda,getMoney());
                        Ukryj_Topbar();
                    }
                    msg_text.setText(Wynik_msg[0]);
                    SaveFailedTest();
                    ResetStreak();
                } else if (oblicz_percenty>=30&&oblicz_percenty<60) {
                    msg_text.setText(Wynik_msg[1]);
                    SaveFood(3);
                    finalnagroda = (int) Math.round(300*mnoznik);
                    SaveMoney(finalnagroda);
                    nagroda1.setText(String.valueOf(finalnagroda));
                    nagrodashadow1.setText(String.valueOf(finalnagroda));
                    Pokaz_Topbar();
                    Aktualizuj_Hajs(getMoney()-finalnagroda,getMoney());
                    Ukryj_Topbar();
                } else if (oblicz_percenty>=60&&oblicz_percenty<80) {
                    msg_text.setText(Wynik_msg[2]);
                    SaveFood(5);
                    finalnagroda = (int) Math.round(500*mnoznik);
                    SaveMoney(finalnagroda);
                    nagroda1.setText(String.valueOf(finalnagroda));
                    nagrodashadow1.setText(String.valueOf(finalnagroda));
                    Pokaz_Topbar();
                    Aktualizuj_Hajs(getMoney()-finalnagroda,getMoney());
                    Ukryj_Topbar();
                } else if (oblicz_percenty>=80&&oblicz_percenty<100) {
                    msg_text.setText(Wynik_msg[3]);
                    SaveFood(7);
                    finalnagroda = (int) Math.round(700*mnoznik);
                    SaveMoney(finalnagroda);
                    nagroda1.setText(String.valueOf(finalnagroda));
                    nagrodashadow1.setText(String.valueOf(finalnagroda));
                    Pokaz_Topbar();
                    Aktualizuj_Hajs(getMoney()-finalnagroda,getMoney());
                    Ukryj_Topbar();
                } else if (oblicz_percenty==100) {
                    msg_text.setText(Wynik_msg[4]);
                    SaveFood(10);
                    finalnagroda = (int) Math.round(1000*mnoznik);
                    SaveMoney(finalnagroda);
                    nagroda1.setText(String.valueOf(finalnagroda));
                    nagrodashadow1.setText(String.valueOf(finalnagroda));
                    Pokaz_Topbar();
                    Aktualizuj_Hajs(getMoney()-finalnagroda,getMoney());
                    Ukryj_Topbar();
                }
                if(czyZalogowany==true){
                    updateFirebaseData(acct.getIdToken());
                }
                nagrodaMultiplier.setText(String.valueOf(mnoznik*100)+"%");
            }catch (Exception e){
                e.printStackTrace();
            }
            }
        }
    public void setBledneParent(Button[] buttony,PytaniaDB pytanie) {
        try{
            for(int j=0;j<4;j++){
                CurrentQuestion[j] = Math_syn.set_Fancy_Math(StringCurrentQuestion[j],0x00ffffff);
                buttony[j].setBackground(CurrentQuestion[j]);
                buttony[j].setText("");
            }
            char odp;
            int odpint=pytanie.getPoprawnaOdpInt();
            odp = pytanie.getOdpUzytkownika();
            char correctansw = pytanie.getPoprawnaOdp();
            int current_color= 0x00ffffff;

            for(int j=0;j<4;j++){
                current_color= 0x00ffffff;
                if(listaShuffled.get(CURRENT_INDEX).getOdpUzytkownikaInt()==j+1){
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
    public void SetPytanieParent(Button[] buttony, PytaniaDB pytanie, ImageView MiejsceNaPytanie){
        Log.d("testaction","setting_question");
        try{
            String tresc;
            Log.d("testaction","setting_tresc");
            try{
                MiejsceNaPytanie.setBackground(Math_syn.set_Math(pytanie.getTresc()));
            } catch (Exception e){
                e.printStackTrace();
            }
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
            UpdateAnswerParent(buttony);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setAnswerParent(View v,int[] ids,PytaniaDB pytanie){
        try{
            if(v.getId()==ids[0]){
                pytanie.ZapiszOdpowiedz('A');
            }
            else if(v.getId()==ids[1]){
                pytanie.ZapiszOdpowiedz('B');
            }
            else if(v.getId()==ids[2]){
                pytanie.ZapiszOdpowiedz('C');
            }
            else if(v.getId()==ids[3]){
                pytanie.ZapiszOdpowiedz('D');
            }
            listaShuffled.set(CURRENT_INDEX,pytanie);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void UpdateAnswerParent(Button[] buttony){
        Log.d("testaction","updating_answer");
        try{
            for(int j=0;j<4;j++){
                CurrentQuestion[j] = Math_syn.set_Fancy_Math(StringCurrentQuestion[j],0x00ffffff);
                if(listaShuffled.get(CURRENT_INDEX).getOdpUzytkownikaInt()==j+1){
                    CurrentQuestion[j] = Math_syn.set_Very_Fancy_Math(StringCurrentQuestion[j],0xff4f5d75,0xffffffff);
                }
                buttony[j].setBackground(CurrentQuestion[j]);
            }
        } catch (Exception e){
            e.printStackTrace();
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
                                //TextView cosiedzieje = findViewById(R.id.cosiedzieje);
                                //cosiedzieje.setText("Pobieranie Pytań..");
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