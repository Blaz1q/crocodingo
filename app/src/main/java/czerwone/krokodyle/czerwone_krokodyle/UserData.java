package czerwone.krokodyle.czerwone_krokodyle;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
    private static final String SHARED_PREFS = "sharedPrefs"; //NIE ZMIENIAĆ!
    private Context context;
    private int Money;
    private int Exp;
    private int Level;
    private int LastOpened;
    private int Food;
    private String Username;
    private String Lang;
    private Boolean IsMusicOn;
    private int Streak;
    private int Zdane;
    private int Niezdane;
    private int Volume;
    UserData(Context ctx){
        this.context = ctx;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        this.Money = sharedPreferences.getInt("MONEY", 0);
    }
    public int getMoney() {
        return this.Money;
    }
}
