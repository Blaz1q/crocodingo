package czerwone.krokodyle.czerwone_krokodyle;

import java.io.InputStream;

public class Czapka {
    private int id;
    private String plik;
    private String[] Nazwa;
    private String[] Opis;
    private int cena;
    private boolean czyZakupiona;
    private boolean czyZalozona;
    private boolean czyDostepna;
    private boolean czyPremium;
    private String waluta;

    public Czapka(int id, String plik,String[] NazwaDane,String[] OpisDane, int cena) {
        this.id = id;
        this.plik = plik;
        this.cena = cena;
        this.czyZakupiona = false;
        this.czyZalozona = false;
        setNazwa(NazwaDane);
        setOpis(OpisDane);
    }
    private void setNazwa(String[] dane){
        int i=0;
        this.Nazwa = new String[dane.length];
        while(i<dane.length){
            this.Nazwa[i] = dane[i];
            i++;
        }
    }
    private void setOpis(String[] dane){
        int i=0;
        this.Opis = new String[dane.length];
        while(i<dane.length){
            this.Opis[i] = dane[i];
            i++;
        }
    }
    // Gettery i settery dla pól
    public void setWaluta(String wal){
        this.waluta = wal;
    }
    public String getWaluta(){
        return waluta;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlik() {
        return plik;
    }
    public String getNazwa(String lang) {
        switch (lang){
            case "En":
                if(Nazwa.length<2) return "N/A";
                return Nazwa[1];
            case "Pl":
                if(Nazwa.length<1) return "N/A";
                return Nazwa[0];
        }
        return Nazwa[0];
    }
    public String getOpis(String lang) {
        switch (lang){
            case "En":
                if(Opis.length<2) return "N/A";
                return Opis[1];
            case "Pl":
                if(Opis.length<1) return "N/A";
                return Opis[0];
        }
        return Opis[0];
    }
    public void setCzyDostepna(Boolean CzyJestDostepna){
        this.czyDostepna = CzyJestDostepna;
    }
    public Boolean getCzyDostepna(){
        return czyDostepna;
    }
    public void setCzyPremium(Boolean CzyPremium){
        this.czyPremium = CzyPremium;
    }
    public Boolean getCzyPremium(){
        return czyPremium;
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
