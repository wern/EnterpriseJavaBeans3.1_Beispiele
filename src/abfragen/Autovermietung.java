/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package abfragen;

import java.util.List;

import javax.ejb.Remote;

@Remote public interface Autovermietung {
    public List<String> alleFahrzeuge();
    public List<String> alleFahrzeugeIn(String stadt);
    public List<String> ermittleFahrzeuge(String stadt, String kennzeichen, String modell, Integer baujahr);
    public List<String> ungenutzteFahrzeuge();
    public List<Object[]> anzahlFahrzeugeProStadt();
    public List<Object> bauhjahrSpanne();
    public List<String> mehrfachMieter();
    public List<String> stationenMitLeerfahrten();
    public List<String> kundenIn(String stadt);
    public List<Object[]> fahrtnrUndFahrername();
    public List<Object[]> fahrtUndFahrername();
    public int stadtnamenKlein();
    public int loescheFahrtenNach(String stadt);
    public void beispielDatenAnlegen();
}
