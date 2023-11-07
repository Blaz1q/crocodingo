/* Aktualna wersja: ALPHA-1.4.2
- popwawiłem wygląd użytkownika
- poprawiłem logike
- poprawiłem wylgąd końcu testów
przyszłe wersje:
-Powiadomienia
* */
package com.example.czerwone_krokodyle;

import android.app.AlertDialog;
import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scilab.forge.jlatexmath.BoldAtom;
import org.w3c.dom.Text;

import ru.noties.jlatexmath.JLatexMathDrawable;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Date;
import java.util.Set;
public class MainActivity extends AppCompatActivity implements View.OnLongClickListener{
    Czapka czapka;
    private List<Czapka> listaCzapek = new ArrayList<>();
    private List<String> actions = new ArrayList<>();
    int actionsindex = 0;
    AlertDialog wyjscie;
    String DBVERSIONval = "";
    private JLatexMathDrawable Test_Math_drawable;
    public JLatexMathDrawable[] CurrentQuestion = new JLatexMathDrawable[4];
    public String[] StringCurrentQuestion = new String[4];
    private final math_syntax Math_syn = new math_syntax();
    JSONArray czapkiJSON;
    JSONArray dbbackupJSON;
    String defaultUsername = "SuperKrokodyl26";
    boolean zaladowano_czapki = false;
    boolean zaladowanodb = false; //tylko w przypadku gdy nie ma połączenia z bazą
    String[] LitABCD = {"A","B","C","D"};
    boolean isPopupVisible = false;
    final int color_correct = 0xff88d18a;
    final int color_text_correct = 0xff0d1b15;
    final int color_incorrect = 0xfff52720;
    final int color_text_incorrect = 0xff270302;
    final int wartosc_ciastka = 7000; //2 h
    List<Integer> idList = new ArrayList<Integer>();
    List<Integer> ShuffledArray = new ArrayList<Integer>();
    List<Integer> AnswerList = new ArrayList<Integer>();
    int AnswerListPoj;
    List<String> CorrectAnswerList = new ArrayList<String>();
    List<String> Tresci_PytaniaList = new ArrayList<String>();
    List<String> Odpowiedzi_AList = new ArrayList<String>();
    List<String> Odpowiedzi_BList = new ArrayList<String>();
    List<String> Odpowiedzi_CList = new ArrayList<String>();
    List<String> Odpowiedzi_DList = new ArrayList<String>();
    List<String> WyjasnieniaList = new ArrayList<String>();
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
    int currentVol = -1;
    int current_item_index=-1;
    String czapkiilosc="";
    String[] Wynik_msg = {
            "Niestety, nie udało się tym razem.\nAle nie przejmuj się, poćwicz dalej.\nCrocodingo w Ciebie wierzy. Razem możecie zdziałać cuda!",
            "Poszło Ci nawet dobrze, ale Croco wie, że stać Cię na więcej.\nCrocodingo zaprasza na zajęcia! Ding dong! Dzwonek na lekcje!",
            "Wow! Poszło Ci naprawdę dobrze! Crocodingo chce abyś jeszcze troszkę z nim poćwiczył.\nCroco wie, że możesz stać się Mistrzem Matematyki!",
            "Croco jest pod wrażeniem. Mało kto dociera do poziomu Mistrza takiego jak Crocodingo.\nCzy to czas oddać swój tytuł Croco?",
            "Crocodingo uważa, że jesteś gotowy.\nJesteś gotowy na walkę o przetrwanie, na walkę o godność, na walkę z Najtrudniejszym Przeciwnikiem...\nJesteś gotowy na Maturę z Matematyki!"
    };
    public Boolean switchstate = true;
    public Boolean switchstatewibracje = false;
    int SET_TEST_ID = 1;
    int gloduje_co = -14400;
    int umiera_po = -172800;
    MediaPlayer bgsong;
    MediaPlayer buttonsong;
    long currentTime = new Date().getTime();
    long lastFeed = 0;
    long savedTime=0;
    public static final String FAILED_TESTS = "FAILED_TESTS";
    public static final String PASSED_TESTS = "PASSED_TESTS";
    public static final String STREAK = "STREAK";
    public static final String BEST_STREAK = "BEST_STREAK";
    public static final String MONEY = "MONEY";
    public static final String FOOD = "FOOD";
    public static final String CURRENT_ITEM_ID = "CURRENT_ITEM_ID";
    public static final String USERNAME = "USERNAME";
    public static final String LAST_FEED = "Feeding_time";
    public static final String SAVED_DATE = "Date";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH = "switch1";
    public static final String SWITCHWIBRACJE = "switchwibracje";
    public static final String VOLUME = "VOLUME";
    public static final String TUTORIAL = "TUTORIAL";
    public static final String TUTORIALTESTY = "TUTORIALTESTY";
    private static final String PURCHASED_CZAPKI = "purchased_czapki";
    private static final String final_connection= "https://jncrew.5v.pl/androidAPI.php";
    String kupioneczapkibool="";
    Dialog dialog;
    Dialog myDialog;
    Boolean showonce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);  /* <----- Tutaj ustawić główny widok aplikacji */
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
        updateAccInfo();
        LoadData();
        Log.w("DBBACKUP",loadJSONFromAssetVer2("DB.json"));
        loadJSONFromAsset();
        fetchData(0);
        //DebugSetGlodny();
        ResumeOnLongListener();
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
        PierwszeUruchomienie();
        Uruchomienie_animajca();
        ustawCzapke();
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
                        isPopupVisible = true;
                        myDialog = new Dialog(this);
                        myDialog.setContentView(R.layout.zakonczenie_testu);
                        myDialog.show();
                        myDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                isPopupVisible = false;
                            }
                        }); //muszę zrobić haxa bo nie mam parametru View aby wywołać funkcje popupTestyZakoncz() :<
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
    @Override
    protected void onResume() {

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
        try {
            InputStream is = getAssets().open("czapki.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            Log.d("MojaAplikacja", "Prawidłowo wczytano JSON: " + json);
            JSONArray jsonArray = new JSONArray(json);
            listaCzapek.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                czapkiilosc+="0";
                Log.w("JAPIERDOLE",kupioneczapkibool);
                JSONObject czapkaJson = jsonArray.getJSONObject(i);

                int id = czapkaJson.getInt("id");
                String plik = czapkaJson.getString("plik");
                String nazwa = czapkaJson.getString("nazwa");
                String opis = czapkaJson.getString("opis");
                int cena = czapkaJson.getInt("cena"); // Pobierz cenę czapki

                Czapka czapka = new Czapka(id, plik, nazwa, opis, cena); // Zaktualizuj obiekt Czapka
                listaCzapek.add(czapka);
            }
            zaladowano_czapki = true;
            for (Czapka czapka : listaCzapek) {
                Log.d("MojaAplikacja", "ID: " + czapka.getId() + ", Nazwa: " + czapka.getNazwa() + ", Opis: " + czapka.getOpis());
            }
            loadPurchasedCzapki();
            if(kupioneczapkibool==""){
                kupioneczapkibool=czapkiilosc;
            }
            loadCurrentCzapka();
            aktualizujTextPrzyciskow();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    int[] akcja;
    public void ustawCzapke(){
        if(canplayanimations){
            if(current_item_index==-1){
                ImageView czapkaimage = findViewById(R.id.current_czapka_image);
                czapkaimage.setVisibility(View.GONE);
            }
            else{
                ImageView czapkaimage = findViewById(R.id.current_czapka_image);
                czapkaimage.setVisibility(View.VISIBLE);
                Context c = getApplicationContext();
                czapkaimage.setImageResource(getResources().getIdentifier("drawable/czapa_" +String.valueOf(current_item_index+1) ,null,c.getPackageName()));
            }
        }
    }
    public void aktualizujTextPrzyciskow() {
        Button[] przycisk = {
                findViewById(R.id.kup_czapka0),
                findViewById(R.id.kup_czapka1),
                findViewById(R.id.kup_czapka2),
                findViewById(R.id.kup_czapka3),
                findViewById(R.id.kup_czapka4),
                findViewById(R.id.kup_czapka5),
                findViewById(R.id.kup_czapka6),
                findViewById(R.id.kup_czapka7)
        };
        Log.w("kurwa",String.valueOf(przycisk.length));
        akcja = new int[przycisk.length];
        Log.w("kurwaw",String.valueOf(akcja.length));
        Log.w("kurwawa",String.valueOf(kupioneczapkibool.length()));
        for (int i = 0; i < przycisk.length; i++) {
            Czapka czapka = listaCzapek.get(i);
            //Button przycisk = findViewById(getResources().getIdentifier("kup_czapka" + String.valueOf(i), "id", getPackageName()));

            Log.w("cototjest","kup_czapka" + String.valueOf(i));
            Log.w("akcja",String.valueOf(akcja[i]));
                if (i==current_item_index) {
                    przycisk[i].setText("Zdejmij");
                    akcja[i] = 2;
                } else if (kupioneczapkibool.charAt(i)=='1') {
                    przycisk[i].setText("Załóż");
                    akcja[i] = 1;
                } else {
                    przycisk[i].setText("Kup");
                    akcja[i] = 0;
                }
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
    public void pokazPopupZakupuCzapek(View view) {
        String buttonId = getResources().getResourceEntryName(view.getId());

        if (buttonId.startsWith("kup_czapka")) {
            przyciskNumer = Integer.parseInt(buttonId.substring("kup_czapka".length()));

            int obrazResId = 0;
            final int[] cenaCzapki = new int[1]; // Użyj tablicy jednoelementowej do przekazania ceny
            final String nazwaCzapki;
            final String opisCzapki;
            final String idCzapki;
            if (zaladowano_czapki && przyciskNumer < listaCzapek.size()) {
                czapka = listaCzapek.get(przyciskNumer);
                idCzapki = String.valueOf(czapka.getId());
                nazwaCzapki = czapka.getNazwa();
                opisCzapki = czapka.getOpis();
                String plikObrazu = czapka.getPlik();
                obrazResId = getResources().getIdentifier(plikObrazu, "drawable", getPackageName());
                cenaCzapki[0] = czapka.getCena(); // Przypisz cenę do tablicy
            } else {
                Log.w("MojaAplikacja", "Brak czapki dla przycisku numer: " + przyciskNumer);
                nazwaCzapki = "Nazwa czapki";
                opisCzapki = "Opis czapki";
                idCzapki = String.valueOf(przyciskNumer);
            }
            if(isPopupVisible==false){
                isPopupVisible=true;
            myDialog = new Dialog(this);
            myDialog.setContentView(R.layout.popup_layout);
            myDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        isPopupVisible = false;
                    }
                });

            ImageView czapaImage = myDialog.findViewById(R.id.czapa_image);
            TextView czapaNazwa = myDialog.findViewById(R.id.czapa_nazwa);
            TextView czapaOpis = myDialog.findViewById(R.id.czapa_opis);
            Button kupCzapaButton = myDialog.findViewById(R.id.kup_czapa);

            if (obrazResId != 0) {
                czapaImage.setImageResource(obrazResId);
            } else {
                czapaImage.setImageResource(R.drawable.circle);
            }

            czapaNazwa.setText(nazwaCzapki);
            czapaOpis.setText(opisCzapki);
            Log.w("akcjasuper",String.valueOf(akcja[Integer.valueOf(idCzapki)]));
            if(akcja[Integer.valueOf(idCzapki)]==0){
                kupCzapaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int dostepneHajsy = getMoney();
                        if (dostepneHajsy >= cenaCzapki[0]) { // Użyj tablicy cenaCzapki[0]
                            savePurchasedCzapka(idCzapki);
                            int noweHajsy = cenaCzapki[0]*-1; // Użyj tablicy cenaCzapki[0]
                            SaveMoney(noweHajsy);
                            Toast.makeText(MainActivity.this, "Zakupiono " + nazwaCzapki, Toast.LENGTH_SHORT).show();
                            myDialog.dismiss();
                            isPopupVisible=false;
                            aktualizujTextPrzyciskow();
                        } else {
                            Toast.makeText(MainActivity.this, "Nie masz wystarczająco crococoinów na zakup", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myDialog.show();
            }
            }
            else if(akcja[Integer.valueOf(idCzapki)]==1){
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(CURRENT_ITEM_ID,Integer.valueOf(idCzapki));
                editor.apply();
                current_item_index = sharedPreferences.getInt(CURRENT_ITEM_ID,-1);
                aktualizujTextPrzyciskow();
            }
            else if(akcja[Integer.valueOf(idCzapki)]==2){
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
    public int getMoney() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(MONEY, 0);
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

            }else{
                Toast.makeText(getApplicationContext(),"brak ciasteczek :(",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void loadPurchasedCzapki(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        kupioneczapkibool = sharedPreferences.getString(PURCHASED_CZAPKI, czapkiilosc);
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
    public void SaveVolume(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(VOLUME,currentVol);
        editor.apply();
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
            try{
                if(!showonce){
                    myDialog = new Dialog(this);
                    myDialog.setContentView(R.layout.popup_login);
                    myDialog.show();
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
                            database.getReference().child("Users").child(user.getUid()).setValue(users);
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
            TextView iloschajsu = findViewById(R.id.crococoinyilosc);
            iloschajsu.setText(String.valueOf(getMoney()));
            aktualizujTextPrzyciskow();
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
        }
        else if (v.getId()==R.id.Rozpocznij_test1){
            setContentView(R.layout.pytania_wybor);
            Create_Question_Buttons();
            myDialog.dismiss();
            isPopupVisible=false;
            bgmusictesty();
            AddActions("pytania_wybor");
        }
        else if (v.getId()==R.id.Rozpocznij_test2){
            setContentView(R.layout.pojedyncze_pytanie);
            Set_pytanie_Poj();
            myDialog.dismiss();
            isPopupVisible=false;
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
        czypokazana = false;
        LoadData();
        Wibracje();
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
    public void Zamknij_Popup(View v){
        if(v.getId()==R.id.zamknijlogin||v.getId()==R.id.zamknij1||v.getId()==R.id.zamknij2||v.getId()==R.id.zamknij_test1||v.getId()==R.id.zakoncz_test_przycisk_popup||v.getId()==R.id.zamknijczapka){
            myDialog.dismiss();
            isPopupVisible = false;
        }
    }
    public void PopupTestyRozpocznij(View v){
        if(isPopupVisible==false){
            if(v.getId()==R.id.testy1){
                isPopupVisible = true;
                myDialog = new Dialog(this);
                myDialog.setContentView(R.layout.rozpoczecie_testu);
                myDialog.show();
                myDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        isPopupVisible = false;
                    }
                });
            }
            else if(v.getId()==R.id.testy2){
                isPopupVisible = true;
                myDialog = new Dialog(this);
                myDialog.setContentView(R.layout.rozpoczecie_testu_poj);
                myDialog.show();
                myDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        isPopupVisible = false;
                    }
                });
            }
        }
    }
    public void PopupTestyZakoncz(View v){
        if(isPopupVisible==false){
            if(v.getId()==R.id.zakoncz_test||v.getId()==R.id.nextbutton){
                isPopupVisible = true;
                myDialog = new Dialog(this);
                myDialog.setContentView(R.layout.zakonczenie_testu);
                myDialog.show();
                myDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        isPopupVisible = false;
                    }
                });
            }
        }
    }
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
    public void fetchData(int action){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =final_connection;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), "Response: " + response,Toast.LENGTH_SHORT).show();
                        Log.d("JSON_DATA",response);
                        switch (action){
                            case 0:
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
    public void Set_Ids_List(String response){
        if(response.equals("")){
            String id,poprawne,tresc,wyjasnienie,A,B,C,D;
            Log.w("WarningDBError","DBCONNECTIONFAILED");
            try{
                zaladowanodb=true;
                JSONObject jsonObject = new JSONObject(loadJSONFromAssetVer2("DB.json"));
                JSONArray jsonArray = jsonObject.getJSONArray("API");
                if(zaladowanodb){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        id = jsonObject2.getString("id");
                        poprawne = jsonObject2.getString("poprawna_odp");
                        tresc = jsonObject2.getString("tresc");
                        wyjasnienie = jsonObject2.getString("wyjasnienie");
                        A = jsonObject2.getString("A");
                        B = jsonObject2.getString("B");
                        C = jsonObject2.getString("C");
                        D = jsonObject2.getString("D");
                        CorrectAnswerList.add(poprawne);
                        idList.add(Integer.valueOf(id));
                        Tresci_PytaniaList.add(tresc);
                        WyjasnieniaList.add(wyjasnienie);
                        Odpowiedzi_AList.add(A);
                        Odpowiedzi_BList.add(B);
                        Odpowiedzi_CList.add(C);
                        Odpowiedzi_DList.add(D);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Błąd połączenia z siecią i wczytania backupu", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("API");
                String id, poprawne, tresc, wyjasnienie, A, B, C, D;

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
                    CorrectAnswerList.add(poprawne);
                    idList.add(Integer.valueOf(id));
                    Tresci_PytaniaList.add(tresc);
                    WyjasnieniaList.add(wyjasnienie);
                    Odpowiedzi_AList.add(A);
                    Odpowiedzi_BList.add(B);
                    Odpowiedzi_CList.add(C);
                    Odpowiedzi_DList.add(D);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public int CURRENT_INDEX;
    public void Resume_Question_Buttons(){
        try{
            LinearLayout layout = findViewById(R.id.questions_select);
            int buttonStyle = com.google.android.material.R.style.Widget_Material3_Button;

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20);
            for(int i=0;i<q_num;i++){
                Button btn_tests = new Button(new ContextThemeWrapper(getApplicationContext(), buttonStyle), null, buttonStyle);
                btn_tests.setLayoutParams(params);
                btn_tests.setText("Pytanie "+ (i + 1));
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
            int buttonStyle = com.google.android.material.R.style.Widget_Material3_Button;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20);
            for(int i=0;i<q_num;i++){
                Button btn_tests = new Button(new ContextThemeWrapper(getApplicationContext(), buttonStyle), null, buttonStyle);
                btn_tests.setLayoutParams(params);
                btn_tests.setText("Pytanie "+ (i + 1));
                btn_tests.setTextSize(30);
                btn_tests.setTag(ShuffledArray.get(i));
                char odp;
                switch(AnswerList.get(i)){
                    case 0:
                        odp=' ';
                        break;
                    case 1:
                        odp = 'A';
                        break;
                    case 2:
                        odp = 'B';
                        break;
                    case 3:
                        odp = 'C';
                        break;
                    case 4:
                        odp = 'D';
                        break;
                    default:
                        odp=' ';
                        break;
                }
                int get_id = ShuffledArray.get(i);
                if(odp!=CorrectAnswerList.get(get_id).charAt(0)){
                    btn_tests.setBackgroundColor(color_incorrect);
                } else{
                    btn_tests.setBackgroundColor(color_correct);
                }
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
        poprawne = 0;
        AnswerList.clear();
        q_num = rand_num.nextInt(25-20)+20;
        for(int i=0;i<q_num;i++){
            AnswerList.add(0);
        }
        ShuffledArray = idList;
        Collections.shuffle(ShuffledArray);
        Resume_Question_Buttons();
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
                ImageView wyjasnienie = findViewById(R.id.wyjasnienie_main);
                String wyjasnienie_tekst = WyjasnieniaList.get(ShuffledArray.get(CURRENT_INDEX));
                Test_Math_drawable=Math_syn.set_Math(wyjasnienie_tekst);
                wyjasnienie.setBackground(Test_Math_drawable);
            } catch (Exception e){

            }
        }
        if(v.getId()==R.id.wyjasnij_poj){
            if(oneshot==false){
                try{
                    ImageView wyjasnienie = findViewById(R.id.tresc_pytania_i_wyjasnienie);
                    String wyjasnienie_tekst = WyjasnieniaList.get(ShuffledArray.get(CURRENT_INDEX));
                    Test_Math_drawable=Math_syn.set_Math(wyjasnienie_tekst);
                    wyjasnienie.setBackground(Test_Math_drawable);
                } catch (Exception e){

                }
            }
        }
    }
    public void Set_pytanie(){
        try{
            TextView numer_pytania = findViewById(R.id.numer_pytania);
            numer_pytania.setText("Pytanie "+ (CURRENT_INDEX + 1));
                int i = SET_TEST_ID;
                String tresc,id;

                id = idList.get(i).toString();
                tresc = Tresci_PytaniaList.get(i);
                String[] pytania = {
                        Odpowiedzi_AList.get(i),
                        Odpowiedzi_BList.get(i),
                        Odpowiedzi_CList.get(i),
                        Odpowiedzi_DList.get(i)
                };
                ImageView tes = findViewById(R.id.tresc_pytania);
                try{
                    Test_Math_drawable=Math_syn.set_Math(tresc);
                    tes.setBackground(Test_Math_drawable);
                } catch (Exception e){
                    e.printStackTrace();
                }
                Button[] buttony = {
                        findViewById(R.id.odp_A),
                        findViewById(R.id.odp_B),
                        findViewById(R.id.odp_C),
                        findViewById(R.id.odp_D)
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
                if(CURRENT_INDEX==0){
                    Button zmientekst=findViewById(R.id.prevbutton);
                    zmientekst.setEnabled(false);
                }else{
                    Button zmientekst=findViewById(R.id.prevbutton);
                    zmientekst.setEnabled(true);
                }
                if(q_num-1==CURRENT_INDEX){
                    Button zmientekst=findViewById(R.id.nextbutton);
                    zmientekst.setText("zakończ");
                    wywolajfunkcje=true;
                }else{
                    Button zmientekst=findViewById(R.id.nextbutton);
                    zmientekst.setText("Następne");
                    wywolajfunkcje=false;
                }
                UpdateAnswer();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Set_pytanie_Poj(){
        try{
            oneshot=true;
            Button wyjasnienie_button = findViewById(R.id.wyjasnij_poj);
            wyjasnienie_button.setEnabled(false);
            AnswerListPoj = 0;
            ShuffledArray = idList;
            Collections.shuffle(ShuffledArray);
            Random r = new Random();
            CURRENT_INDEX = r.nextInt(idList.size());
            SET_TEST_ID = ShuffledArray.get(CURRENT_INDEX);
            int i = SET_TEST_ID;
            String tresc,id;
            id = idList.get(i).toString();
            tresc = Tresci_PytaniaList.get(i);
            String[] pytania = {
                    Odpowiedzi_AList.get(i),
                    Odpowiedzi_BList.get(i),
                    Odpowiedzi_CList.get(i),
                    Odpowiedzi_DList.get(i)
            };
            ImageView tes = findViewById(R.id.tresc_pytania_i_wyjasnienie);
            try{
                Test_Math_drawable=Math_syn.set_Math(tresc);
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
                tresc = Tresci_PytaniaList.get(i);
                String[] pytania = {
                        Odpowiedzi_AList.get(i),
                        Odpowiedzi_BList.get(i),
                        Odpowiedzi_CList.get(i),
                        Odpowiedzi_DList.get(i)
                };
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
        try{
            Button[] buttony = {
                    findViewById(R.id.odp_A),
                    findViewById(R.id.odp_B),
                    findViewById(R.id.odp_C),
                    findViewById(R.id.odp_D)
            };
            for(int j=0;j<4;j++){
                CurrentQuestion[j] = Math_syn.set_Fancy_Math(StringCurrentQuestion[j],0x00ffffff);
                buttony[j].setBackground(CurrentQuestion[j]);
            }
            for(int j=0;j<4;j++){
                if(AnswerList.get(CURRENT_INDEX)==j+1){
                    CurrentQuestion[j] = Math_syn.set_Very_Fancy_Math(StringCurrentQuestion[j],0xff4f5d75,0xffffffff);
                    buttony[j].setBackground(CurrentQuestion[j]);
                }
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
            char odp;
            int odpint=0;
            switch(AnswerListPoj){
                case 0:
                    odp=' ';
                    break;
                case 1:
                    odp = 'A';
                    break;
                case 2:
                    odp = 'B';
                    break;
                case 3:
                    odp = 'C';
                    break;
                case 4:
                    odp = 'D';
                    break;
                default:
                    odp=' ';
                    break;
            }
            int get_id = ShuffledArray.get(CURRENT_INDEX);
            char correctansw = CorrectAnswerList.get(get_id).charAt(0);
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
                    if(odp!=CorrectAnswerList.get(get_id).charAt(0)){
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
            switch(AnswerList.get(CURRENT_INDEX)){
                case 0:
                    odp=' ';
                    break;
                case 1:
                    odp = 'A';
                    break;
                case 2:
                    odp = 'B';
                    break;
                case 3:
                    odp = 'C';
                    break;
                case 4:
                    odp = 'D';
                    break;
                default:
                    odp=' ';
                    break;
            }
            int get_id = ShuffledArray.get(CURRENT_INDEX);
            char correctansw = CorrectAnswerList.get(get_id).charAt(0);
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
                    if(odp!=CorrectAnswerList.get(get_id).charAt(0)){
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
    public void setAnswer(View v){
        try{
            if(v.getId()==R.id.odp_A){
                AnswerList.set(CURRENT_INDEX,1);
            }
            else if(v.getId()==R.id.odp_B){
                AnswerList.set(CURRENT_INDEX,2);
            }
            else if(v.getId()==R.id.odp_C){
                AnswerList.set(CURRENT_INDEX,3);
            }
            else if(v.getId()==R.id.odp_D){
                AnswerList.set(CURRENT_INDEX,4);
            }
            UpdateAnswer();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setAnswerPoj(View v){
        try{
            if(v.getId()==R.id.odp_A_Poj){
                AnswerListPoj = 1;
            }
            else if(v.getId()==R.id.odp_B_Poj){
                AnswerListPoj = 2;
            }
            else if(v.getId()==R.id.odp_C_Poj){
                AnswerListPoj = 3;
            }
            else if(v.getId()==R.id.odp_D_Poj){
                AnswerListPoj = 4;
            }
            if(oneshot==true){
                UpdateAnswer_Poj();
                Button wyjasnienie_button = findViewById(R.id.wyjasnij_poj);
                wyjasnienie_button.setEnabled(true);
            }
            oneshot=false;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public float oblicz_percenty;
    void Oblicz_Poprawne(){
        int npoprawne = 0;
        char odp;
        for(int i=0;i<q_num;i++){
            switch(AnswerList.get(i)){
                case 0:
                    odp=' ';
                    break;
                case 1:
                    odp = 'A';
                    break;
                case 2:
                    odp = 'B';
                    break;
                case 3:
                    odp = 'C';
                    break;
                case 4:
                    odp = 'D';
                    break;
                default:
                    odp=' ';
                    break;
            }
            try{
                int get_id = ShuffledArray.get(i);
            if(CorrectAnswerList.get(get_id).charAt(0)!=odp) npoprawne++;
                    else poprawne++;
            }catch (Exception e){
                e.printStackTrace();
            }
            }
        if(poprawne!=0){
            oblicz_percenty = poprawne*100/q_num;
        }else oblicz_percenty = 0;
    }
    public void Zakoncz_test(View v){
        if(v.getId()==R.id.Zakoncz_test_popup){
            myDialog.dismiss();
            isPopupVisible=false;
            Oblicz_Poprawne();
            bgmusicnormal();
            setContentView(R.layout.test_final);
            AddActions("test_final");
            try{
                TextView poprawneodp = findViewById(R.id.poprawneodpilosc);
                TextView ilosc_pytan = findViewById(R.id.iloscpytan);
                TextView msg_text = findViewById(R.id.Wynik_testu_powitanie);
                TextView textView = findViewById(R.id.wynik_procentowy);
                textView.setText(String.valueOf(oblicz_percenty) +"%");
                poprawneodp.setText(String.valueOf(poprawne));
                ilosc_pytan.setText(String.valueOf(q_num));
                if(oblicz_percenty>20&&oblicz_percenty<30){
                    SaveFood(1);
                    SaveMoney(100);
                }
                if(oblicz_percenty>=30){
                    SavePassedTest();
                    AddStreak();
                }
                if(oblicz_percenty<30){
                    msg_text.setText(Wynik_msg[0]);
                    SaveFailedTest();
                    ResetStreak();
                } else if (oblicz_percenty>=30&&oblicz_percenty<60) {
                    msg_text.setText(Wynik_msg[1]);
                    SaveFood(3);
                    SaveMoney(300);
                } else if (oblicz_percenty>=60&&oblicz_percenty<80) {
                    msg_text.setText(Wynik_msg[2]);
                    SaveFood(5);
                    SaveMoney(500);
                } else if (oblicz_percenty>=80&&oblicz_percenty<100) {
                    msg_text.setText(Wynik_msg[3]);
                    SaveFood(7);
                    SaveMoney(700);
                } else if (oblicz_percenty==100) {
                    msg_text.setText(Wynik_msg[4]);
                    SaveFood(10);
                    SaveMoney(1000);
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