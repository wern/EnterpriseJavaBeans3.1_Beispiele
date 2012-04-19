/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.suchen;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name="artikelkatalog")
public class KatalogImpl implements Katalog {

	private @PersistenceContext EntityManager em;

	public Artikel aufnehmen(Artikel artikel) {
		em.persist(artikel);
		return artikel;
	}

	public Artikel details(Artikeltreffer treffer) {
		return em.find(Artikel.class, treffer.artikelnr);
	}

	public List<Artikeltreffer> suchen(Suchkriterien kriterien) {
		String abfrage = "SELECT NEW " + Artikeltreffer.class.getName() +
			"(a.artikelnr, a.beschreibung, a.einzelpreis) FROM Artikel a" + kriterien;
		return em.createQuery(abfrage).setMaxResults(100).getResultList();
	}
	
}
