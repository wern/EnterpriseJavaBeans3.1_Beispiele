/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.blaettern;


import java.util.ArrayList;
import java.util.List;

public class Teilmengenlieferant<T> {
	private List<T> alles;
	private int zuletztGeliefertVon, zuletztGeliefertBis;
	
	public Teilmengenlieferant(List<T> alles) {
		this.alles = alles;
	}

	public List<T> vorhergehende(int maximaleAnzahl) {
		zuletztGeliefertVon -= maximaleAnzahl;
		if (zuletztGeliefertVon < 0)
			zuletztGeliefertVon = 0;
		zuletztGeliefertBis = zuletztGeliefertVon;
		return naechste(maximaleAnzahl);
	}
	
	public List<T> naechste(int maximaleAnzahl) {
		zuletztGeliefertVon = zuletztGeliefertBis;
		zuletztGeliefertBis = zuletztGeliefertVon + maximaleAnzahl;
		if (zuletztGeliefertBis > alles.size())
			zuletztGeliefertBis = alles.size();
		List<T> ergebnis = new ArrayList<T>(alles.subList(zuletztGeliefertVon, zuletztGeliefertBis));
		return ergebnis;
	}
}
