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
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import kochrezepte.datentakt.Flug.Typ;

@Remote
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class FluganzeigeBean implements Fluganzeige {

	@PersistenceContext
	private EntityManager entityManager;

	@Resource
	private SessionContext ctx;

	private volatile Flugdaten starts;
	private volatile Flugdaten landungen;

	@Override
	public Flugdaten getStarts() {
		return starts;
	}

	@Override
	public Flugdaten getLandungen() {
		return landungen;
	}

	@PostConstruct
	public void initialesLadenAnstossen() {
		ctx.getTimerService().createTimer(0, null);
	}

	@Timeout
	@Schedule(minute = "0/5", hour = "*", persistent = false)
	public void holeDaten() {
		System.out.println("Hole neue Daten ...");
		Query flugQuery = entityManager.createNamedQuery("Flug.nachTyp");

		flugQuery.setParameter("typ", Typ.START);
		@SuppressWarnings("unchecked")
		List<Flug> fluegeStart = flugQuery.getResultList();
		starts = new Flugdaten(fluegeStart, new Date());

		flugQuery.setParameter("typ", Typ.LANDUNG);
		@SuppressWarnings("unchecked")
		List<Flug> fluegeLandungen = flugQuery.getResultList();
		landungen = new Flugdaten(fluegeLandungen, new Date());
	}
}
