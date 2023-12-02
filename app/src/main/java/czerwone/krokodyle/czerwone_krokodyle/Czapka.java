package czerwone.krokodyle.czerwone_krokodyle;

import java.io.InputStream;

public class Czapka {
    private int id;
    private String plik;
    private String nazwa;
    private String nazwaeng;
    private String opis;
    private String opiseng;
    private int cena;
    private boolean czyZakupiona;
    private boolean czyZalozona;

    public Czapka(int id, String plik, String nazwa, String opis, int cena) {
        this.id = id;
        this.plik = plik;
        this.nazwa = nazwa;
        this.opis = opis;
        this.cena = cena;
        this.czyZakupiona = false;
        this.czyZalozona = false;
    }

    // Gettery i settery dla pól

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlik() {
        return plik;
    }
    public void setNazwaeng(String nazw){
        this.nazwaeng = nazw;
    }
    public void setOpiseng(String ops){
        this.opiseng = ops;
    }
    public String getNazwa(String lang) {
        switch (lang){
            case "En":
                return nazwaeng;
            case "Pl":
                return nazwa;
        }
        return nazwa;
    }
    public String getOpis(String lang) {
        switch (lang){
            case "En":
                return opiseng;
            case "Pl":
                return opis;
        }
        return opis;
    }
    public int getCena() {
        return cena;
    }

    public boolean isCzyZakupiona() {
        return czyZakupiona;
    }

    public void setCzyZakupiona(boolean czyZakupiona) {
        this.czyZakupiona = czyZakupiona;
    }

    public boolean isCzyZalozona() {
        return czyZalozona;
    }

    public void setCzyZalozona(boolean czyZalozona) {
        this.czyZalozona = czyZalozona;
    }

}
