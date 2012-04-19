/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package beziehungen;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity public class Rechner {
    @Id @GeneratedValue private int inventarNr;
    @OneToOne private Tastatur angeschlosseneTastatur;
    private String modell;
    
    public Tastatur getAngeschlosseneTastatur() {
        return angeschlosseneTastatur;
    }

    public void setAngeschlosseneTastatur(Tastatur angeschlosseneTastatur) {
        this.angeschlosseneTastatur = angeschlosseneTastatur;
    }

    public int getInventarNr() { return inventarNr; }
    public String getModell() { return modell; }
    public void setModell(String p) { modell = p; }

}
