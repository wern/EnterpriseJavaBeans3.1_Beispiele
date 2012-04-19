/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.dateiexport;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name="kundenerzeuger")
public class KundenerzeugerImpl implements Kundenerzeuger {
	public static final String WILLKOMMENSBRIEF =
		"Hallo $(vorname) $(name)\n" +
		"Willkommen im Club! Deine Kundenummer lautet $(kundennummer).";

	@PersistenceContext EntityManager em;
	@EJB Musterbriefexport briefexport;

	public void erzeugeKunden(int anzahl) {
		for (int i = 0; i < anzahl; i++) {
			Kunde kunde = new Kunde();
			kunde.setVorname("Peter" + i);
			kunde.setName("Mustermann" + i);
			kunde.setExportNotwendig(true);
			if (i > 5) {
				// Bei mehr als 5 Kunden auf einen Schlag produzieren wir eine Exception, um sich den
				// Rollback-Effekt anschauen zu koennen. Vorher haelt die Methode 5 Sekunden an, damit
				// man sich im Dateisystem die temporaeren Dateien anschauen kann.
				try { Thread.sleep(5000); }
				catch(InterruptedException ix) {}
				throw new EJBException("Provozierte Exception zum Ausprobieren von Rollbacks");
			}
			em.persist(kunde);
			try {
				briefexport.exportiereMusterbrief(kunde, WILLKOMMENSBRIEF);
			}
			catch(IOException iox) {
				throw new EJBException("Export fuer Neukunde fehlgeschlagen");
			}
		}
	}


}
