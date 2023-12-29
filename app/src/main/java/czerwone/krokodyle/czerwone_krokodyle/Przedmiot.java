package czerwone.krokodyle.czerwone_krokodyle;

public class Przedmiot{
    protected int id;
    protected String plik;
    protected String[] Nazwa;
    protected String[] Opis;
    protected int cena;
    protected boolean czyPremium;
    protected void setNazwa(String[] dane){
        int i=0;
        this.Nazwa = new String[dane.length];
        while(i<dane.length){
            this.Nazwa[i] = dane[i];
            i++;
        }
    }
    protected void setOpis(String[] dane){
        int i=0;
        this.Opis = new String[dane.length];
        while(i<dane.length){
            this.Opis[i] = dane[i];
            i++;
        }
    }
    public int getId() {
        return id;
    }
    public int getCena() {
        return cena;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setCzyPremium(Boolean CzyPremium){
        this.czyPremium = CzyPremium;
    }
    public Boolean getCzyPremium(){
        return czyPremium;
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

}
