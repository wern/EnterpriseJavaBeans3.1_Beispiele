/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package ableitung;

import javax.persistence.Entity;

/** Konkrete Klasse zur Abbildung einer Langspielplatte. Die Gesamtheit
    aller Songs ergibt sich aus Addition der Songs auf A- und B-Seite. */
@Entity public class LangspielPlatte extends Schallplatte {
    private int tracksA;
    private int tracksB;
    
    int getAnzahlTracks() { return tracksA + tracksB; }

    public LangspielPlatte(String titel,
            int spieldauerA, int spieldauerB,
            int tracksA, int tracksB) {
        super(titel, spieldauerA, spieldauerB);
        this.tracksA = tracksA;
        this.tracksB = tracksB;
    }
    
    public LangspielPlatte() {} /* Wird vom Persistence-Provider benötigt */
}
