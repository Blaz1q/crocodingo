package czerwone.krokodyle.czerwone_krokodyle;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Jedzenie extends Przedmiot{
    private final SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "sharedPrefs"; //NIE ZMIENIAĆ!
    private int nasycenie;
    private int ZapisanaIlosc;
    private static final String ITEMNAME = "Jedzenie";
    private Context context;
    public Jedzenie(int id, String plik,String[] NazwaDane,String[] OpisDane, int cena, int nasycenieval,Context ctx) {
        this.id = id;
        this.plik = plik;
        this.cena = cena;
        this.nasycenie = nasycenieval;
        this.czyDostepna = true;
        setNazwa(NazwaDane);
        setOpis(OpisDane);
        this.context = ctx;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        this.ZapisanaIlosc = sharedPreferences.getInt(ITEMNAME+this.id,0);
    }
    public void Zjedz(String Lang){
        if(ZapisanaIlosc>0){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(ITEMNAME+this.id,(sharedPreferences.getInt(ITEMNAME+this.id,0)-1));
            editor.apply();
            this.ZapisanaIlosc = sharedPreferences.getInt(ITEMNAME+this.id,0);
        }
        else{
            Toast.makeText(context, "Brak "+getNazwa(Lang)+" :(", Toast.LENGTH_SHORT).show();
        }
    }

    public int getZapisanaIlosc() {
        return ZapisanaIlosc;
    }

    public void DodajJedzenie(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int val = sharedPreferences.getInt(ITEMNAME+this.id,0);
        val+=1;
        editor.putInt(ITEMNAME+this.id,val);
        editor.apply();
        this.ZapisanaIlosc = sharedPreferences.getInt(ITEMNAME+this.id,0);
    }
    public void DodajJedzenie(int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int val = sharedPreferences.getInt(ITEMNAME+this.id,0);
        val+=value;
        editor.putInt(ITEMNAME+this.id,val);
        editor.apply();
        this.ZapisanaIlosc = sharedPreferences.getInt(ITEMNAME+this.id,0);
    }
    public int getNasycenie(){
        return nasycenie;
    }
    public void setIlosc(int dane){
        this.ZapisanaIlosc = dane;
    }
}
