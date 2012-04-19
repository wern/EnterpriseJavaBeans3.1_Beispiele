/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package transaktionen;

import javax.ejb.Remote;

@Remote
public interface Warenkorb {
	public void legeInWarenkorb(String produktName);
	public void entferneAusWarenkorb(String produktName);
    public int anzahlProdukte();
	public int bestellen(String adresse) throws ShopException;
}
