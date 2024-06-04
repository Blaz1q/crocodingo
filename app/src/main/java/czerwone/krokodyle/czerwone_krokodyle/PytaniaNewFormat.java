package czerwone.krokodyle.czerwone_krokodyle;

import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class PytaniaNewFormat {

    private final String[] FormatTypes = {"ABX","PFX","ZMK","DEF"};

    private List<PytaniaNewFormat> Lista = new ArrayList<>();
    private int Id;
    private String[] TrescPytaniaMain = {""};
    private String[] ZdjMain = {""};
    private String[] Tresc; //różne języki
    private String[] PoprawnaOdp; //różne odpowiedzi
    private String[][] Odpowiedzi; //1.Różne pytania, 2. Różne języki
    private int[] OdpowiedziUzytkownika;// Dla różnych podpunktów
    private String[] Wyjasnienie; //Rózne języki
    private int KatID;
    private int Pkty;
    private int Podpunkty;
    private String[] Zdj;

    private String Typ;

    public PytaniaNewFormat(int ID, String[] TRESC, String[][] ODPOWIEDZI, String[] WYJASNIENIE, int KATID, int PKTY,int PODPUNKTY,String TYP){
        Boolean correct_datatype = false;
        for (int i=0;i<FormatTypes.length;i++){
            if(TYP.equals(FormatTypes[i])) correct_datatype=true;
        }
        if(correct_datatype==false){
            Log.e("WARNING","INCORRECT DATATYPE AT: "+ID);
            return;
        }
        this.Id = ID;
        this.Tresc = TRESC;
        this.Odpowiedzi = ODPOWIEDZI;
        this.Wyjasnienie = WYJASNIENIE;
        this.KatID = KATID;
        this.Pkty = PKTY;
        this.Typ = TYP;
        addToList(this);
    }
    public void addToList(PytaniaNewFormat objekt){
        Lista.add(objekt);
    }
    public void Wypisz(){
        Log.d("Id",String.valueOf(this.Id));
        for(int i=0;i<this.Odpowiedzi.length;i++){
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
    public void wypiszListe(){
        for(int i=0;i<Lista.size();i++){
            Lista.get(i).Wypisz();
        }
    }
    public void setTrescPytaniaMain(String[] trescPytaniaMain) {
        this.TrescPytaniaMain = trescPytaniaMain;
    }
    public void setZdjMain(String[] zdjMain) {
        this.ZdjMain = zdjMain;
    }
    public String getTrescPytaniaMain(String lang) {
        switch (lang){
            case "En":
                if(TrescPytaniaMain.length>=2) return TrescPytaniaMain[1];
            case "Pl":
                if(TrescPytaniaMain.length>=1) return TrescPytaniaMain[0];
        }
        return TrescPytaniaMain[0];
    }
}
