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

/** Abstrakte Basisklasse für Vinyl-Schallplatten mit zwei Seiten
    (A und B). Die gesamtspieldauer ergibt sich aus der Addition
    der Spielzeiten beider Seiten. Die Anzahl der Songs ist auch
    hier noch offen */
@Entity public abstract class Schallplatte extends TonTraeger {
    private int dauerA;
    private int dauerB;

    int getSpieldauer() {
        return dauerA + dauerB;
    }

    protected Schallplatte(String titel, int spieldauerA, int spieldauerB) {
        super(titel);
        this.dauerA = spieldauerA;
        this.dauerB = spieldauerB;
    }
    
    public Schallplatte() {} /* Wird vom Persistence-Provider benötigt */
}
