package czerwone.krokodyle.czerwone_krokodyle;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Potka extends Przedmiot{
    private final SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "sharedPrefs"; //NIE ZMIENIAĆ!
    private static final String ITEMNAME = "Potki";
    private boolean czyDostepna;
    private int czas_trwania; //0 - instant, inne w ms
    private int ZapisanaIlosc;
    private Context context;
    public Potka(int id, String plik,String[] NazwaDane,String[] OpisDane, int cena,int czas_trwania,Context ctx) {
        this.id = id;
        this.plik = plik;
        this.cena = cena;
        this.czyDostepna = true;
        this.czas_trwania = czas_trwania;
        this.czyPremium = false;
        this.context = ctx;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        this.ZapisanaIlosc = sharedPreferences.getInt(ITEMNAME+this.id,0);
        setNazwa(NazwaDane);
        setOpis(OpisDane);
    }
    public int getCzas_trwania(){
        return this.czas_trwania;
    }

    public void setCzyDostepna(boolean x){
        this.czyDostepna = x;
    }
    public boolean getCzyDostepna(){
        return czyDostepna;
    }
    public void Zjedz(){
        if(ZapisanaIlosc>0){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(ITEMNAME+this.id,(sharedPreferences.getInt(ITEMNAME+this.id,0)-1));
            editor.apply();
            this.ZapisanaIlosc = sharedPreferences.getInt(ITEMNAME+this.id,0);
        }
    }

    public int getZapisanaIlosc() {
        return ZapisanaIlosc;
    }

    public void DodajJedzenie(int ilosc){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ITEMNAME+this.id,(sharedPreferences.getInt(ITEMNAME+this.id,0)+ilosc));
        editor.apply();
        this.ZapisanaIlosc = sharedPreferences.getInt(ITEMNAME+this.id,0);
    }
}
