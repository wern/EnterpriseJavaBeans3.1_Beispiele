/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.blaettern;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

@Stateful(name="artikelsuchergebnis")
public class SuchergebnisImpl implements Suchergebnis, SuchergebnisInit {
	private Teilmengenlieferant<Artikel> teilmengenlieferant;
	@Resource private SessionContext sessionContext;
	
	public Suchergebnis initialisieren(List<Artikel> alles) {
		teilmengenlieferant = new Teilmengenlieferant<Artikel>(alles);
		return sessionContext.getBusinessObject(Suchergebnis.class);
	}
	
	public List<Artikel> naechste(int maximaleAnzahl) {
		return teilmengenlieferant.naechste(maximaleAnzahl);
	}

	public List<Artikel> vorhergehende(int maximaleAnzahl) {
		return teilmengenlieferant.vorhergehende(maximaleAnzahl);
	}
}
