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

@Entity public class Adresse implements Serializable {
    @Id @GeneratedValue private int datensatzNr;
    String strasse, postleitzahl, ort;
    
    public int getDatensatzNr() { return datensatzNr; }
    public String getOrt() { return ort; }
    public void setOrt(String p) { ort = p; }
    public String getPostleitzahl() { return postleitzahl; }
    public void setPostleitzahl(String p) { postleitzahl = p; }
    public String getStrasse() { return strasse; }
    public void setStrasse(String p) { strasse = p; }
    
    public Adresse() {}
    public Adresse(String strasse, String plz, String ort) {
        setStrasse(strasse);
        setPostleitzahl(plz);
        setOrt(ort);
    }
}
