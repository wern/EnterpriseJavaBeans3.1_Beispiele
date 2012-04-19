/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package transaktionen;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;

@Stateful(name="Warenkorb")
public class WarenkorbImpl implements Warenkorb, SessionSynchronization {
    
    @EJB private Shop shop; // Referenz zur Shop-EJB (über Dependency Injaction)
    private Map<String, Integer> waren = new HashMap<String, Integer>(); // Inhalt des Korbs
    private Map<String, Integer> backup; // Für Wiederherstellung des Inhalts nach Rollback

    public void entferneAusWarenkorb(String produktName) {
        waren.remove(produktName);
    }
    
    public void legeInWarenkorb(String produktName) {
        Integer anzahlImKorb = waren.get(produktName);
        waren.put(produktName, (anzahlImKorb == null) ? 1 : anzahlImKorb + 1);
    }
    
    public int anzahlProdukte() { return waren.size(); }

    protected int erzeugeLieferauftraege(String adresse) throws ShopException {
        int letzterLieferauftrag = 0;
        for (Entry<String, Integer> entry: waren.entrySet())
            letzterLieferauftrag = shop.bestellen(entry.getKey(), entry.getValue(), adresse);
        backup = waren;
        waren = new HashMap<String, Integer>();
        return letzterLieferauftrag;
    }
    
    protected void produziereRechnung(String adresse) throws ShopException {
        if (adresse.length() < 2)
            throw new ShopException("Rechnungsadresse ist fehlerhaft");
    }
    
    public int bestellen(String adresse) throws ShopException {
        int ergebnis = erzeugeLieferauftraege(adresse);
        produziereRechnung(adresse);
        return ergebnis;
    }

    /********** Implementierung von SessionSynchronization **************/

    public void afterBegin() {}
    public void beforeCompletion() {}

    public void afterCompletion(boolean commit) {
        if (!commit && backup != null) {
            waren = backup;
            backup = null;
        }
    }

}
