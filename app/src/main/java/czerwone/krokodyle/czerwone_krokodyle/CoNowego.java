package czerwone.krokodyle.czerwone_krokodyle;

public class CoNowego {
    public String Wersja;
    public String[][] Opis;
    public int code;
    CoNowego(String wersja,String[][] opis){
        this.Wersja = wersja;
        this.Opis = opis;
        this.code=0;
    }
    CoNowego(){
        this.code = -1;
    }
    public String[] getOpis(String lang) {
        String[] NA = new String[]{"N/A"};
        switch (lang){
            case "En":
                if(Opis.length<2) return NA;
                return Opis[1];
            case "Pl":
                if(Opis.length<1) return NA;
                return Opis[0];
        }
        return Opis[0];
    }
}
