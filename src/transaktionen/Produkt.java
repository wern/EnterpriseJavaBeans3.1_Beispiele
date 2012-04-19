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

import javax.persistence.*;

@Entity public class Produkt implements Serializable {
    @Id String name;
    private int bestand;

    public Produkt() {}
    public Produkt(String name, int bestand) {
        this.name = name;
        this.bestand = bestand;
    }
    public String getName() { return name; }
    public int getBestand() { return bestand; }
    public void reduziereBestand(int p) { bestand -= p; }
}
