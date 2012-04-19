/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package transaktionen;

import java.io.Serializable;

public class Bestellung implements Serializable {
    private int menge;
    private String produktName, adresse;
    
    public Bestellung(String produktName, String mengeStr, String adresse) {
        this.produktName = produktName;
        this.menge = Integer.parseInt(mengeStr);
        this.adresse = adresse;
    }
    
    public Bestellung(String produktName, String adresse) {
        this(produktName, "1", adresse);
    }

    public String getAdresse() { return adresse; }
    public int getMenge() { return menge; }
    public String getProduktName() { return produktName; }
}
