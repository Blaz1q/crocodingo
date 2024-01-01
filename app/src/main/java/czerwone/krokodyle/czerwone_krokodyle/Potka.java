package czerwone.krokodyle.czerwone_krokodyle;

public class Potka extends Przedmiot{
    private boolean czyDostepna;
    private int czas_trwania; //0 - instant, inne w ms
    public Potka(int id, String plik,String[] NazwaDane,String[] OpisDane, int cena,int czas_trwania) {
        this.id = id;
        this.plik = plik;
        this.cena = cena;
        this.czyDostepna = true;
        this.czas_trwania = czas_trwania;
        this.czyPremium = false;
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
}
