/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package beziehungen;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity public class Tastatur implements Serializable {
    @Id @GeneratedValue
    private int tastaturNr;
    private String sprache;

    @OneToOne(mappedBy="angeschlosseneTastatur")
    private Rechner angeschlossenAn;

    public Rechner getAngeschlossenAn() {
        return angeschlossenAn;
    }

    public void setAngeschlossenAn(Rechner angeschlossenAn) {
        this.angeschlossenAn = angeschlossenAn;
    }

    public int getTastaturNr() { return tastaturNr; }
    public String getSprache() { return sprache; }
    public void setSprache(String p) { sprache = p; }
}
