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
    private int[] PoprawnaOdp;
    private String[] PoprawnaOdpMini;
    private int[] OdpowiedziUzytkownika;
    private String[] Wyjasnienie; //Rózne języki
    private String[][][] Tabela;
    private int Podpunkty;
    boolean hasInfo = false;
    boolean hasTresc = false;
    boolean hasPolecenie = false;
    boolean hasZdj = false;
    boolean hasOdpowiedzi = false;
    boolean hasOdpowiedziZdj = false;
    JSONObject translatable = null;

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
            if(pytanie.has("tresc")) {this.Tresc = initS1D(pytanie.getJSONArray("tresc")); hasTresc = true;}
            if(pytanie.has("odp")) {this.Odpowiedzi = initS2D(pytanie.getJSONArray("odp")); hasOdpowiedzi=true;}
            if(pytanie.has("odp_zdj")) this.OdpowiedziZdj = initS1D(pytanie.getJSONArray("odp_zdj"));
            if(pytanie.has("info")) {this.Info = initS1D(pytanie.getJSONArray("info")); hasInfo=true;}
            if(pytanie.has("zdj")) {this.Zdj = pytanie.getString("zdj"); hasZdj=true;}
            if(pytanie.has("translatable_info")){
                translatable = pytanie.getJSONObject("translatable_info");
            }
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
            if(pytanie.has("tresc")) {this.Tresc = initS1D(pytanie.getJSONArray("tresc")); hasTresc = true;}
            if(pytanie.has("info")) {this.Info = initS1D(pytanie.getJSONArray("info")); hasInfo=true;}
            if(pytanie.has("odp")) {this.Odpowiedzi = initS2D(pytanie.getJSONArray("odp")); hasOdpowiedzi=true;}
            if(pytanie.has("zdj")) {this.Zdj = pytanie.getString("zdj"); hasZdj=true;}
        }
        if (Typ.equals("OTWARTE")) {
            this.Pkt = pytanie.getInt("pkt");
            this.Kategoria = pytanie.getString("kat");
            this.KatID = pytanie.getInt("katID");
            this.OdpowiedziUzytkownika = new int[1];
            Arrays.fill(this.OdpowiedziUzytkownika, -1);
            if (pytanie.has("tresc")) this.Tresc = initS1D(pytanie.getJSONArray("tresc"));
            hasTresc = true;
            this.PoprawnaOdpMini = initS1D(pytanie.getJSONArray("poprawna"));
            this.Wyjasnienie = initS1D(pytanie.getJSONArray("wyjasnienie"));
            this.Polecenie = initS1D(pytanie.getJSONArray("polecenie"));
            hasPolecenie = true;
            if (pytanie.has("info")) {
                this.Info = initS1D(pytanie.getJSONArray("info"));
                hasInfo = true;
            }
            if (pytanie.has("zdj")) {
                this.Zdj = pytanie.getString("zdj");
                hasZdj = true;
            }
        }
        if(Typ.equals("DOPASUJ_NTON")){
            this.Pkt = pytanie.getInt("pkt");
            this.Kategoria = pytanie.getString("kat");
            this.KatID = pytanie.getInt("katID");
            this.PoprawnaOdp = initI1D(pytanie.getJSONArray("poprawna"));
            this.Wyjasnienie = initS1D(pytanie.getJSONArray("wyjasnienie"));
            this.Polecenie = initS1D(pytanie.getJSONArray("polecenie")); hasPolecenie=true;
            this.OdpowiedziUzytkownika = new int[PoprawnaOdp.length];
            Arrays.fill(this.OdpowiedziUzytkownika,-1);
            if(pytanie.has("tresc")) {this.Tresc = initS1D(pytanie.getJSONArray("tresc")); hasTresc = true;}
            if(pytanie.has("info")) {this.Info = initS1D(pytanie.getJSONArray("info")); hasInfo=true;}
            if(pytanie.has("odp")) {this.Odpowiedzi = initS2D(pytanie.getJSONArray("odp")); hasOdpowiedzi=true;}
            if(pytanie.has("odp_zdj")) {this.OdpowiedziZdj = initS1D(pytanie.getJSONArray("odp")); hasOdpowiedziZdj=true;}
            if(pytanie.has("zdj")) {this.Zdj = pytanie.getString("zdj"); hasZdj=true;}
        }
        if(Typ.equals("DOPASUJ_NTO1")||Typ.equals("DOPASUJ_1TO1")){
            this.Pkt = pytanie.getInt("pkt");
            this.Kategoria = pytanie.getString("kat");
            this.KatID = pytanie.getInt("katID");
            this.PoprawnaOdp = initI1D(pytanie.getJSONArray("poprawna"));
            this.Wyjasnienie = initS1D(pytanie.getJSONArray("wyjasnienie"));
            this.Polecenie = initS1D(pytanie.getJSONArray("polecenie")); hasPolecenie=true;
            this.OdpowiedziUzytkownika = new int[PoprawnaOdp.length];
            Arrays.fill(this.OdpowiedziUzytkownika,-1);
            if(pytanie.has("tresc")) {this.Tresc = initS1D(pytanie.getJSONArray("tresc")); hasTresc = true;}
            if(pytanie.has("info")) {this.Info = initS1D(pytanie.getJSONArray("info")); hasInfo=true;}
            if(pytanie.has("odp")) {this.Odpowiedzi = initS2D(pytanie.getJSONArray("odp")); hasOdpowiedzi=true;}
            if(pytanie.has("zdj")) {this.Zdj = pytanie.getString("zdj"); hasZdj=true;}
        }
        if(Typ.equals("DOPASUJ_TABELA")){
            this.Pkt = pytanie.getInt("pkt");
            this.Kategoria = pytanie.getString("kat");
            this.KatID = pytanie.getInt("katID");
            this.PoprawnaOdp = initI1D(pytanie.getJSONArray("poprawna"));
            this.Wyjasnienie = initS1D(pytanie.getJSONArray("wyjasnienie"));
            this.Polecenie = initS1D(pytanie.getJSONArray("polecenie")); hasPolecenie=true;
            this.Tabela = initS3D(pytanie.getJSONArray("tabela"));
            this.OdpowiedziUzytkownika = new int[PoprawnaOdp.length];
            Arrays.fill(this.OdpowiedziUzytkownika,-1);
            if(pytanie.has("tresc")) {this.Tresc = initS1D(pytanie.getJSONArray("tresc")); hasTresc = true;}
            if(pytanie.has("info")) {this.Info = initS1D(pytanie.getJSONArray("info")); hasInfo=true;}
            if(pytanie.has("odp")) {this.Odpowiedzi = initS2D(pytanie.getJSONArray("odp")); hasOdpowiedzi=true;}
            if(pytanie.has("zdj")) {this.Zdj = pytanie.getString("zdj"); hasZdj=true;}
        }
        if(Typ.equals("ZLOZONE")){
            this.Podpunkty = pytanie.getInt("ilosc_podpunktow");
            if(pytanie.has("info")) {this.Info = initS1D(pytanie.getJSONArray("info")); hasInfo=true;}
            if(pytanie.has("zdj")) {this.Zdj = pytanie.getString("zdj"); hasZdj=true;}
            if(pytanie.has("tresc")) {this.Tresc = initS1D(pytanie.getJSONArray("tresc")); hasTresc = true;}
            JSONArray zlozone = pytanie.getJSONArray("ListaZlozone");
            for(int i=0;i<zlozone.length();i++){
                JSONObject podpunkt = zlozone.getJSONObject(i);
                PytaniaNewFormat pytanieObj = new PytaniaNewFormat(podpunkt);
                ListaZlozone.add(pytanieObj);
            }

        }
    }
    public List<PytaniaNewFormat> getListaZlozone() {
        return ListaZlozone;
    }
    private String[][][] initS3D(JSONArray array) throws JSONException {
        String[][][] zmienna = new String[array.length()][][];
        for (int j = 0; j < array.length(); j++) {
            JSONArray Innerarray = array.getJSONArray(j);
            zmienna[j] = new String[Innerarray.length()][];
            for(int k=0; k<Innerarray.length(); k++){
                JSONArray InnerInnerarray = Innerarray.getJSONArray(k);
                zmienna[j][k] = new String[InnerInnerarray.length()];
                for(int h=0;h<InnerInnerarray.length();h++){
                    zmienna[j][k][h] = InnerInnerarray.getString(h);
                    Log.d("cos+sie+dzieje", zmienna[j][k][h]); // co tu się kurwa odpierdala
                }
            }
        }
        return zmienna;
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
        if(!Typ.equals("ZLOZONE"))
            return Pkt;
        int localpkt=0;
        for(int i=0;i<ListaZlozone.size();i++){
            localpkt+=ListaZlozone.get(i).getPkt();
        }
        return localpkt;
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

    public String getKategoria() {
        return Kategoria;
    }

    public String[] getOdpowiedzi(String lang){
        if(translatable!=null){
            if(translatable.has("odp")){
                try {
                    if(!translatable.getBoolean("odp")) return Odpowiedzi[0];
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        switch (lang){
            case "En":
                if(Odpowiedzi.length>=2) return Odpowiedzi[1];
            case "Pl":
                if(Odpowiedzi.length>=1) return Odpowiedzi[0];
        }
        String s = "";
        return new String[]{s};
    }
    public String[][] getTabela(String lang){
        if(translatable!=null){
            if(translatable.has("tabela")){
                try {
                    if(!translatable.getBoolean("tabela")) return Tabela[0];
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        switch (lang){
            case "En":
                if(Tabela.length>=2) return Tabela[1];
            case "Pl":
                if(Tabela.length>=1) return Tabela[0];
        }
        String s = "";
        return new String[][]{{s},{s}};
    }
    public String getPoprawnaOdpMini(String lang) {
        if(translatable!=null){
            if(translatable.has("poprawna")){
                try {
                    if(!translatable.getBoolean("poprawna")) return PoprawnaOdpMini[0];
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        switch (lang){
            case "En":
                if(PoprawnaOdpMini.length>=2) return PoprawnaOdpMini[1];
            case "Pl":
                if(PoprawnaOdpMini.length>=1) return PoprawnaOdpMini[0];
        }
        return "";
    }
    public String getWyjasnienie(String lang){
        if(translatable!=null){
            if(translatable.has("wyjasnienie")){
                try {
                    if(!translatable.getBoolean("wyjasnienie")) return Wyjasnienie[0];
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        switch (lang){
            case "En":
                if(Wyjasnienie.length>=2) return Wyjasnienie[1];
            case "Pl":
                if(Wyjasnienie.length>=1) return Wyjasnienie[0];
        }
        return "";
    }

    public int getKatID() {
        return KatID;
    }

    public String getInfo(String lang) {
        if(!hasInfo) return "";
        if(translatable!=null){
            if(translatable.has("info")){
                try {
                    if(!translatable.getBoolean("info")) return Info[0];
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
        if(translatable!=null){
            if(translatable.has("tresc")){
                try {
                    if(!translatable.getBoolean("tresc")) return Tresc[0];
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
        if(translatable!=null){
            if(translatable.has("polecenie")){
                try {
                    if(!translatable.getBoolean("polecenie")) return Polecenie[0];
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
        Log.d("pkty: ",""+ObliczPkty());
    }
    public void reset(){
        if(!Typ.equals("ZLOZONE")){
            Arrays.fill(OdpowiedziUzytkownika, -1);
        }else{
            for(int i=0;i<ListaZlozone.size();i++){
                Arrays.fill(ListaZlozone.get(i).OdpowiedziUzytkownika, -1);
            }
        }
    }
    public int[] getOdpowiedziUzytkownika() {
        return OdpowiedziUzytkownika;
    }
    public boolean czy_cos_zaznaczyl(){
        if(!Typ.equals("ZLOZONE")){
            for (int j : OdpowiedziUzytkownika) if (j != -1) return true;
            return false;
        }
        for(int i=0;i<ListaZlozone.size();i++){
            for(int j=0;j<ListaZlozone.get(i).OdpowiedziUzytkownika.length;j++){
                if(ListaZlozone.get(i).OdpowiedziUzytkownika[j]!=-1) return true;
            }
        }
        return false;
    }

    public boolean checkPoprawna(){
        boolean poprawna = true;
        for(int i=0;i<PoprawnaOdp.length;i++){
            if (PoprawnaOdp[i] != OdpowiedziUzytkownika[i]) {
                poprawna = false;
                break;
            }
        }
        return poprawna;
    }
    public boolean checkPoprawnaAny(int index){
        boolean poprawna = false;
        for (int i : PoprawnaOdp) {
            if (OdpowiedziUzytkownika[index] == i) {
                poprawna = true;
                break;
            }
        }
        return poprawna;
    }
    public boolean checkPoprawna(int index){
        return (PoprawnaOdp[index]==OdpowiedziUzytkownika[index]);
    }
    public int ObliczPkty(){
        switch (Typ){
            case "OTWARTE":
                if(OdpowiedziUzytkownika[0]==-1) return 0;
                else return OdpowiedziUzytkownika[0];
            case "PF":
            case "DOPASUJ_1TO1":
                int incorrect=0;
                int localpkty = Pkt;
                for(int i=0;i<OdpowiedziUzytkownika.length;i++){
                    if(!checkPoprawna(i)) incorrect++;
                }
                localpkty-=incorrect;
                if(localpkty<0) localpkty=0;
                return  localpkty;
            case "DOPASUJ_NTO1":
                boolean duplicates=false;
                int dupe_number =0;
                for (int j=0;j<OdpowiedziUzytkownika.length;j++)
                    for (int k=j+1;k<OdpowiedziUzytkownika.length;k++){
                        if (k!=j && OdpowiedziUzytkownika[k] == OdpowiedziUzytkownika[j]){
                        duplicates=true;
                        dupe_number++;
                        }
                    }
                //todo: dokończ to później bo już dostaje pierdolca
            case "DOKONCZ":
            case "DOPASUJ_TABELA":
                if(checkPoprawna()) return Pkt;
                return 0;
            case "ZLOZONE": {
                int localpkt = 0;
                for(int i=0;i<ListaZlozone.size();i++){
                    localpkt+=ListaZlozone.get(i).ObliczPkty();
                }
                return  localpkt;
            }
        }
        return 0;
    }
}
