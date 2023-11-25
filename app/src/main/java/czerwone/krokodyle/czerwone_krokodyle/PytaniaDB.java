package czerwone.krokodyle.czerwone_krokodyle;

import android.util.Log;

public class PytaniaDB {
    private int Id;
    private String Tresc;
    private char PoprawnaOdp;
    private String odpowiedzA;
    private String odpowiedzB;
    private String odpowiedzC;
    private String odpowiedzD;
    private String[] Odpowiedzi;
    private String Wyjasnienie;
    private String Kategoria;
    private char OdpUzytkownika;

    public PytaniaDB(int id,String tresc,char poprawna,String odpA,String odpB,String odpC,String odpD,String wyjasnienie,String kategoria){
        this.Id = id;
        this.Tresc = tresc;
        this.PoprawnaOdp = poprawna;
        this.odpowiedzA = odpA;
        this.odpowiedzB = odpB;
        this.odpowiedzC = odpC;
        this.odpowiedzD = odpD;
        this.Wyjasnienie = wyjasnienie;
        this.Kategoria = kategoria;
        this.Odpowiedzi = new String[]{this.odpowiedzA, this.odpowiedzB, this.odpowiedzC, this.odpowiedzD};
        this.OdpUzytkownika = '-';
    }
    public void ZapiszOdpowiedz(char odp){
        this.OdpUzytkownika = odp;
    }
    public void ResetAnswer(){
        this.OdpUzytkownika = '-';
    }

    public char getPoprawnaOdp() {
        return PoprawnaOdp;
    }

    public char getOdpUzytkownika() {
        Log.d("dziala?",String.valueOf(this.OdpUzytkownika));
        return OdpUzytkownika;
    }

    public void Wypisz(){
        Log.d("Id",String.valueOf(this.Id));
        Log.d("Tresc",this.Tresc);
        for(int i=0;i<this.Odpowiedzi.length;i++){
            Log.d(("Odpowiedzi"+ i),Odpowiedzi[i]);
        }
        Log.d("Poprawna",String.valueOf(this.PoprawnaOdp));
        Log.d("Wyjasnienie",this.Wyjasnienie);
        Log.d("Kategoria",this.Kategoria);
    }
    public String[] getOdpowiedzi() {
        return Odpowiedzi;
    }

    public String getTresc() {
        return Tresc;
    }
}
