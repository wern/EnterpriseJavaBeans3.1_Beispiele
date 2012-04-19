package einstein.server.intern;

import javax.annotation.PreDestroy;
import javax.ejb.*;

import org.jboss.ejb3.annotation.CacheConfig;

import einstein.server.intern.entities.SpielerEntity;
import einstein.server.intern.entities.TischEntity;
import einstein.server.oeffentlich.*;

/** Implementierung einer Einstein-Sitzung als Stateful SessionBean. Der Status
 * der Bean besteht lediglich aus dem Namen der angemeldeten Person und ggf. einem
 * Delegate, wenn der Betreffende an einem Spieltisch sitzt und das Spielgeschehen
 * daran verfolgt.<br>
 * Die EJB enthält selbst nur die Logik für die Sessionverwaltung. Die Logik
 * für die Durchführung von Spielen ist in {@link einstein.server.intern.EinsteinDelegate}s
 * ausgelagert, um die EJB-Implementierung nicht zu überfrachten. Es gibt verschiedene
 * Arten von Delegates für Spieler und Zuschauer. Die Funktion {@link #getDelegate()}
 * liefert den aktuel instanziierten Delegate bzw. wirft eine
 * {@link einstein.server.oeffentlich.SpielException} bei dem Versuch, eine Funktion aufzurufen,
 * die es erfordert, dass die angemeldete Person als Spieler oder als Zuschauer an
 * einem Tisch sitzt.
 * 
 * @author jlessner
 *
 */
@CacheConfig(idleTimeoutSeconds=10)
@Stateful public class EinsteinSitzungImpl implements EinsteinSitzung {

	private EinsteinDelegate tischDelegate;
	private String spielerName;
    
	@EJB private EinsteinDAO dao;

    private EinsteinDelegate getDelegate() throws SpielException {
		if (tischDelegate == null)
			throw new SpielException("Du sitzt an keinem Tisch");
		return tischDelegate;
	}
    
    private String getSpielerName() throws SpielException {
        if (spielerName == null)
            throw new SpielException("Du bist nicht angemeldet");
        return spielerName;
    }

	/********** Session ************/
	public void anmelden(String anmeldeName) throws VerwaltungsException {
		if (anmeldeName == null)
			throw new VerwaltungsException("Spielername fehlt");
		SpielerEntity spieler = dao.findeSpieler(anmeldeName);
		if (spieler == null)
			throw new VerwaltungsException("Spielername " + anmeldeName + " ist unbekannt");
		spieler.setAnwesend(true);
		this.spielerName = anmeldeName;
	}

	@Remove public void abmelden() { sitzungBeenden(); }

    @PreDestroy public void sitzungWirdZerstoert() { sitzungBeenden(); }

    @PrePassivate public void passivieren() { sitzungBeenden(); }

    @PostActivate public void reaktivierungVerhindern() {
        throw new EJBException("Reaktivierung ist nicht vorgesehen");
    }

    protected void sitzungBeenden() {
        try {
            dao.sitzungBeenden(tischDelegate, spielerName);
            tischDelegate = null;
            spielerName = null;
        }
        catch(SpielException sx) { throw new EJBException(sx); }
    }


	/********* Spieltische *********/

	public Integer aktuellerTisch() {
		return (tischDelegate != null) ? new Integer(tischDelegate.getTischNr()) : null;
	}

	public void zuschauen(int pTischNr) throws SpielException {
		tischDelegate = new EinsteinZuschauerDelegate(pTischNr, getSpielerName(), dao);
	}

	public void einsteigen(int pTischNr) throws SpielException {
		tischDelegate = new EinsteinSpielerDelegate(pTischNr, getSpielerName(), dao);
	}

	public int eroeffneTisch(boolean gegenComputer) throws SpielException {
		TischEntity neuerTisch = dao.neuerTisch(gegenComputer);
		tischDelegate = new EinsteinSpielerDelegate(neuerTisch.getNr(), getSpielerName(), dao);
		return tischDelegate.getTischNr();
	}


	/********** Spielen *********/

	public SpielStatus bereit() throws SpielException, SpielStatusException {
		return getDelegate().bereit();
	}

	public Partie partieAbfragen() throws SpielException, SpielStatusException {
		return getDelegate().partieAbfragen();
	}

	public SpielStand standAbfragen() throws SpielException, SpielStatusException {
		return getDelegate().standAbfragen();
	}

	public boolean verlassen() throws SpielException {
        EinsteinDelegate delegate = getDelegate();
        tischDelegate = null;
        return delegate.verlassen();
    }

	public int wuerfeln() throws SpielException, SpielStatusException {
		return getDelegate().wuerfeln();
	}

	public SpielStand ziehen(Zug pZug) throws SpielStatusException, SpielException {
        try { return getDelegate().ziehen(pZug); }
        // Die MogelException muss hier abgefangen, explizit behandelt und
        // dann weitergeworfen werden, weil es sich dabei um eine System-Exception
        // handelt. Der EJB-Standard garantiert nicht, dass nach einer solchen
        // Exception noch die PreDestroy-Methoden aufgerufen werden, deshalb
        // erfolgt das Abmelden des Spielers für alle Fälle bereits hier.
        catch(MogelException mx) {
            sitzungBeenden();
            throw mx;
        }
	}

}
