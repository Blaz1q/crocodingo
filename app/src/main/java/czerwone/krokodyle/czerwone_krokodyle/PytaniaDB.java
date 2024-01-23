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
    private int OdpUzytkownikaInt;
    private int PoprawnaOdpInt;

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
        setPoprawnaOdpInt(this.PoprawnaOdp);
    }
    public void ZapiszOdpowiedz(char odp){
        this.OdpUzytkownika = odp;
        switch(odp){
            case 'A': this.OdpUzytkownikaInt = 1;break;
            case 'B': this.OdpUzytkownikaInt = 2;break;
            case 'C': this.OdpUzytkownikaInt = 3;break;
            case 'D': this.OdpUzytkownikaInt = 4;break;
            default: this.OdpUzytkownikaInt = 0;break;
        }
    }
    public int getOdpUzytkownikaInt(){
        return this.OdpUzytkownikaInt;
    }
    private void setPoprawnaOdpInt(char odp){
        switch(odp){
            case 'A': this.PoprawnaOdpInt = 1;break;
            case 'B': this.PoprawnaOdpInt = 2;break;
            case 'C': this.PoprawnaOdpInt = 3;break;
            case 'D': this.PoprawnaOdpInt = 4;break;
            default: this.PoprawnaOdpInt = 0;break;
        }
    }
    public int getPoprawnaOdpInt(){
        return this.PoprawnaOdpInt;
    }
    public void ResetAnswer(){
        this.OdpUzytkownika = '-';
        this.OdpUzytkownikaInt = 0;
    }

    public char getPoprawnaOdp() {
        return PoprawnaOdp;
    }

    public String getWyjasnienie() {
        return Wyjasnienie;
    }

    public char getOdpUzytkownika() {
        Log.d("dziala?",String.valueOf(this.OdpUzytkownika));
        return OdpUzytkownika;
    }
    public int getId(){
        return this.Id;
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
        Log.d("OdpUzytkownika",String.valueOf(this.OdpUzytkownika));

    }
    public String[] getOdpowiedzi() {
        return Odpowiedzi;
    }
    public String getJednaOdpowiedz(int index){
        return Odpowiedzi[index];
    }

    public String getTresc() {
        return Tresc;
    }
}
