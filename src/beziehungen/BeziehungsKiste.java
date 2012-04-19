/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package beziehungen;

import javax.ejb.Remote;

@Remote public interface BeziehungsKiste {
    public int erzeugeBestellung();
    public int erzeugeRechner();
    public int erzeugePerson();
    public String erzeugeFirma();
    public int erzeugeVersammlung();
    public int erzeugeMitarbeiter();
    public String erzeugeKonferenz();
}
