/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.basisentity;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name="bibliothek")
public class BibliothekImpl implements Bibliothek {
	@PersistenceContext EntityManager em;
	
	public Buch aufnehmen(Buch neuesBuch) {
		em.persist(neuesBuch);
		return neuesBuch;
	}
	
	public Buch aktualisieren(Buch buch) {
		return em.merge(buch);
	}
	
}
