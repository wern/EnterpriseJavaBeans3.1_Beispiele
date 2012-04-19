/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.blaettern;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateless(name="artikelkatalog")
public class KatalogImpl implements Katalog {

	private @PersistenceContext EntityManager em;

	public Artikel aufnehmen(Artikel artikel) {
		em.persist(artikel);
		return artikel;
	}

	public Suchergebnis sucheAlleArtikel() {
		String abfrage = "SELECT a FROM Artikel a";
		List<Artikel> alleErgebnisse = em.createQuery(abfrage).getResultList();
		try {
			SuchergebnisInit ergebnisLieferant = (SuchergebnisInit)new InitialContext().lookup("artikelsuchergebnis/local");
			return ergebnisLieferant.initialisieren(alleErgebnisse);
		}
		catch(NamingException nx) {
			throw new EJBException(nx);
		}
	}
	
}
