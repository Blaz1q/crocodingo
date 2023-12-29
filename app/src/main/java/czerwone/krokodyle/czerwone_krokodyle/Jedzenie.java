package czerwone.krokodyle.czerwone_krokodyle;

public class Jedzenie extends Przedmiot{
    private int nasycenie;
    public Jedzenie(int id, String plik,String[] NazwaDane,String[] OpisDane, int cena, int nasycenieval) {
        this.id = id;
        this.plik = plik;
        this.cena = cena;
        this.nasycenie = nasycenieval;
        setNazwa(NazwaDane);
        setOpis(OpisDane);
    }
    public int getNasycenie(){
        return nasycenie;
    }
}
