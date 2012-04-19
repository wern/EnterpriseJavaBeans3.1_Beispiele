/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.datentakt;

import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import kochrezepte.datentakt.Flug.Typ;

@Remote
@Singleton
@Startup
public class TestDatenErzeugerBean implements TestDatenErzeuger {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void erzeugeTestDaten() {
		em.persist(new Flug(Typ.START, "LH 4332", new Date()));
		em.persist(new Flug(Typ.LANDUNG, "AB 1234", new Date()));
	}

}
