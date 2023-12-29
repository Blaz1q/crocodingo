package czerwone.krokodyle.czerwone_krokodyle;

public class Potka extends Przedmiot{

    private boolean czyDostepna;
    public Potka(int id, String plik,String[] NazwaDane,String[] OpisDane, int cena) {
        this.id = id;
        this.plik = plik;
        this.cena = cena;
        this.czyDostepna = true;
        setNazwa(NazwaDane);
        setOpis(OpisDane);
    }
    public void setCzyDostepna(boolean x){
        this.czyDostepna = x;
    }
    public boolean getCzyDostepna(){
        return czyDostepna;
    }
}
