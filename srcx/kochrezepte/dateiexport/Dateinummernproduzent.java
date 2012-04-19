/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.dateiexport;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Dateinummernproduzent {
	@PersistenceContext EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long naechsteDateinummer() {
		Dateizaehler zaehler = em.find(Dateizaehler.class, Dateizaehler.ID);
		if (zaehler == null) {
			zaehler = new Dateizaehler();
			zaehler.setZaehlerstand(1000); // Initialer Zählerstand
			em.persist(zaehler);
		}
		zaehler.setZaehlerstand(zaehler.getZaehlerstand() + 1);
		return zaehler.getZaehlerstand();
	}

	
}
