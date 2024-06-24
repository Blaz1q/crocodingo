package czerwone.krokodyle.czerwone_krokodyle;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PytaniaNewFormat {

    //private final String[] FormatTypes = {"ABX","PFX","ZMK","DEF"};
    private List<PytaniaNewFormat> ListaZlozone = new ArrayList<>();
    private int Id;
    private int Pkt;
    private String Typ ;
    private String Kategoria ;
    private int KatID = 0;
    private String[] Info;
    private String Zdj;
    private String[] Polecenie;
    private String[] Tresc;
    private String[][] Odpowiedzi;
    private String[] OdpowiedziZdj;
    private String[] ZdjMain = {""};
    private int[] PoprawnaOdp;
    private String[] PoprawnaOdpMini;
    private int[] OdpowiedziUzytkownika;
    private String[] Wyjasnienie; //Rózne języki
    private int Podpunkty;
    boolean hasInfo = false;
    boolean hasTresc = false;
    boolean hasPolecenie = false;
    boolean hasZdj = false;
    boolean hasOdpowiedzi = false;

    public PytaniaNewFormat(JSONObject pytanie) throws JSONException {
        //Log.d("Loading","kapi kapi kapiciulo");
        this.Typ = pytanie.getString("typ");
        this.Id = pytanie.getInt("id");
        Log.d("typ",this.Typ);
        if(Typ.equals("DOKONCZ")){
            this.Pkt = pytanie.getInt("pkt");
            this.Kategoria = pytanie.getString("kat");
            this.KatID = pytanie.getInt("katID");
            this.PoprawnaOdp = initI1D(pytanie.getJSONArray("poprawna"));
            this.OdpowiedziUzytkownika = new int[PoprawnaOdp.length];
            Arrays.fill(this.OdpowiedziUzytkownika,-1);
            this.Wyjasnienie = initS1D(pytanie.getJSONArray("wyjasnienie"));
            this.Polecenie = initS1D(pytanie.getJSONArray("polecenie")); hasPolecenie=true;
            if(pytanie.has("odp")) {this.Odpowiedzi = initS2D(pytanie.getJSONArray("odp")); hasOdpowiedzi=true;}
            if(pytanie.has("odp_zdj")) this.OdpowiedziZdj = initS1D(pytanie.getJSONArray("odp_zdj"));
            if(pytanie.has("info")) {this.Info = initS1D(pytanie.getJSONArray("info")); hasInfo=true;}
            if(pytanie.has("zdj")) {this.Zdj = pytanie.getString("zdj"); hasZdj=true;}
            //Log.d("length",""+Info.length);
        }
        if(Typ.equals("PF")){
            this.Pkt = pytanie.getInt("pkt");
            this.Kategoria = pytanie.getString("kat");
            this.KatID = pytanie.getInt("katID");
            this.PoprawnaOdp = initI1D(pytanie.getJSONArray("poprawna"));
            this.Wyjasnienie = initS1D(pytanie.getJSONArray("wyjasnienie"));
            this.Polecenie = initS1D(pytanie.getJSONArray("polecenie")); hasPolecenie=true;
            this.OdpowiedziUzytkownika = new int[PoprawnaOdp.length];
            Arrays.fill(this.OdpowiedziUzytkownika,-1);
            if(pytanie.has("info")) {this.Info = initS1D(pytanie.getJSONArray("info")); hasInfo=true;}
            if(pytanie.has("odp")) {this.Odpowiedzi = initS2D(pytanie.getJSONArray("odp")); hasOdpowiedzi=true;}
            if(pytanie.has("zdj")) {this.Zdj = pytanie.getString("zdj"); hasZdj=true;}
        }
        if (Typ.equals("OTWARTE")) {
            this.Pkt = pytanie.getInt("pkt");
            this.Kategoria = pytanie.getString("kat");
            this.KatID = pytanie.getInt("katID");
            this.OdpowiedziUzytkownika = new int[1];
            Arrays.fill(this.OdpowiedziUzytkownika,-1);
            this.PoprawnaOdpMini = initS1D(pytanie.getJSONArray("poprawna"));
            this.Wyjasnienie = initS1D(pytanie.getJSONArray("wyjasnienie"));
            this.Polecenie = initS1D(pytanie.getJSONArray("polecenie")); hasPolecenie = true;
            if (pytanie.has("info")) { this.Info = initS1D(pytanie.getJSONArray("info")); hasInfo = true;}
            if (pytanie.has("zdj")) { this.Zdj = pytanie.getString("zdj"); hasZdj = true; }
        }
        if(Typ.equals("ZLOZONE")){
            this.Podpunkty = pytanie.getInt("ilosc_podpunktow");
            if(pytanie.has("info")) {this.Info = initS1D(pytanie.getJSONArray("info")); hasInfo=true;}
            if(pytanie.has("zdj")) {this.Zdj = pytanie.getString("zdj"); hasZdj=true;}
            for(int i=0;i<this.Podpunkty;i++){
                //Log.d("forloop","podpunkt"+(i+1));
                JSONObject podpunkt = pytanie.getJSONObject("podpunkt"+(i+1));
                PytaniaNewFormat pytanieObj = new PytaniaNewFormat(podpunkt);
                ListaZlozone.add(pytanieObj);
            }
        }
    }
    public List<PytaniaNewFormat> getListaZlozone() {
        return ListaZlozone;
    }

    private String[][] initS2D(JSONArray array) throws JSONException {
        String[][] zmienna = new String[array.length()][];
        for (int j = 0; j < array.length(); j++) {
            JSONArray Innerarray = array.getJSONArray(j);
            zmienna[j] = new String[Innerarray.length()]; // Inicjalizacja tablicy wewnętrznej
            for(int k=0; k<Innerarray.length(); k++){
                zmienna[j][k] = Innerarray.getString(k); // Przypisanie wartości do tablicy
                Log.d("cos+sie+dzieje",zmienna[j][k]);
            }
        }
        return zmienna;
    }
    private String[] initS1D(JSONArray array) throws JSONException {
        String[] zmienna = new String[array.length()];
        for(int i=0;i<array.length();i++){
            zmienna[i] = array.getString(i);
            Log.d("cos+sie+dzieje",zmienna[i]);
        }
        return zmienna;
    }
    private int[] initI1D(JSONArray array) throws JSONException {
        int[] zmienna = new int[array.length()];
        for(int i=0;i<array.length();i++){
            zmienna[i] = array.getInt(i);
        }
        return zmienna;
    }

    public int getPkt() {
        return Pkt;
    }

    public String getTyp() {
        return Typ;
    }
    public void Wypisz(){
        Log.d("Id",String.valueOf(this.Id));
        for(int i=0;i<this.Tresc.length;i++){
            Log.d("Tresc",this.Tresc[i]);
        }
        for(int i=0;i<this.Odpowiedzi.length;i++){
            Log.d(("Odpowiedzi"+ i),Odpowiedzi[i][0]);
        }
        Log.d("Poprawna",String.valueOf(this.PoprawnaOdp));
        for(int i=0;i<this.Wyjasnienie.length;i++){
            Log.d(("Odpowiedzi"+ i),Wyjasnienie[i]);
        }
        Log.d("KatID",String.valueOf(this.KatID));
    }

    public int[] getPoprawnaOdp() {
        return PoprawnaOdp;
    }

    public String[] getOdpowiedzi(String lang) {
        switch (lang){
            case "En":
                if(Odpowiedzi.length>=2) return Odpowiedzi[1];
            case "Pl":
                if(Odpowiedzi.length>=1) return Odpowiedzi[0];
        }
        String s = "";
        return new String[]{s};
    }
    public String getPoprawnaOdpMini(String lang) {
        switch (lang){
            case "En":
                if(PoprawnaOdpMini.length>=2) return PoprawnaOdpMini[1];
            case "Pl":
                if(PoprawnaOdpMini.length>=1) return PoprawnaOdpMini[0];
        }
        return "";
    }
    public String getWyjasnienie(String lang){
        switch (lang){
            case "En":
                if(Wyjasnienie.length>=2) return Wyjasnienie[1];
            case "Pl":
                if(Wyjasnienie.length>=1) return Wyjasnienie[0];
        }
        return "";
    }

    public String getInfo(String lang) {
        if(!hasInfo) return "";
        switch (lang){
            case "En":
                if(Info.length>=2) return Info[1];
            case "Pl":
                if(Info.length>=1) return Info[0];
        }
        return "";
    }

    public String getTresc(String lang) {
        if(!hasTresc) return "";
        switch (lang){
            case "En":
                if(Tresc.length>=2) return Tresc[1];
            case "Pl":
                if(Tresc.length>=1) return Tresc[0];
        }
        return "";
    }

    public String getPolecenie(String lang) {
        if(!hasPolecenie) return "";
        switch (lang){
            case "En":
                if(Polecenie.length>=2) return Polecenie[1];
            case "Pl":
                if(Polecenie.length>=1) return Polecenie[0];
        }
        return "";
    }
    public void setOdpowiedziUzytkownika(int odpowiedz,int index){
        if(index<OdpowiedziUzytkownika.length)
            this.OdpowiedziUzytkownika[index] = odpowiedz;
    }

    public int[] getOdpowiedziUzytkownika() {
        return OdpowiedziUzytkownika;
    }

    public boolean checkPoprawna(){
        boolean poprawna = true;
        for(int i=0;i<PoprawnaOdp.length;i++){
            if(PoprawnaOdp[i]!=OdpowiedziUzytkownika[i]) poprawna = false;
        }
        return poprawna;
    }
    public boolean checkPoprawna(int index){
        return PoprawnaOdp[index]==OdpowiedziUzytkownika[index];
    }
}
