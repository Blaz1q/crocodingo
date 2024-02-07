package czerwone.krokodyle.czerwone_krokodyle;
public class Czapka extends Przedmiot {
    private boolean czyZakupiona;
    private boolean czyZalozona;
    private boolean czyDostepna;
    private boolean czyEkskluzywna;
    private boolean czyWidoczna;

    private String waluta;

    public Czapka(int id, String plik,String[] NazwaDane,String[] OpisDane, int cena) {
        this.id = id;
        this.plik = plik;
        this.cena = cena;
        this.czyZakupiona = false;
        this.czyZalozona = false;
        this.czyWidoczna = true;
        this.czyEkskluzywna = false;
        setNazwa(NazwaDane);
        setOpis(OpisDane);
    }
    public void setWaluta(String wal){
        this.waluta = wal;
    }
    public String getWaluta(){
        return waluta;
    }
    public void setCzyWidoczna(boolean widoczna){
        this.czyWidoczna = widoczna;
    }
    public boolean getczyWidoczna(){
        return czyWidoczna;
    }
    public void setCzyDostepna(Boolean CzyJestDostepna){
        this.czyDostepna = CzyJestDostepna;
    }
    public boolean getCzyDostepna(){
        return czyDostepna;
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
