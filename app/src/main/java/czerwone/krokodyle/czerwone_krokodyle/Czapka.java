package czerwone.krokodyle.czerwone_krokodyle;

import java.io.InputStream;

public class Czapka {
    private int id;
    private String plik;
    private String nazwa;
    private String opis;
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

    public String getNazwa() {
        return nazwa;
    }

    public String getOpis() {
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
