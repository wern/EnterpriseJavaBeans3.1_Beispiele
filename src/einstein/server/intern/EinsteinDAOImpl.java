package einstein.server.intern;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import einstein.server.intern.entities.SpielerEntity;
import einstein.server.intern.entities.SteinEntity;
import einstein.server.intern.entities.TischEntity;
import einstein.server.oeffentlich.Position;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStatus;
import einstein.server.oeffentlich.Spieler;

/**
 * Implementierung des Einstein-DAOs auf Basis von JPA. Native SQL-Abfragen
 * sind hier nicht notwendig, wobei dazu gesagt werden muss, dass die Entities
 * {@link TischEntity} und {@link SpielerEntity} extra mit einer bidirektionalen
 * Beziehung ausgestattet wurden, um für das Löschen verwaister Tische eine
 * SQL-Abrage zu vermeiden. Das ist aber nichts Ungewöhnliches und macht noch
 * einmal deutlich, dass die Weitergabe von Entities bis auf die Client-Ebene
 * eine Kopplung an Implementierungsdetails mit sich bringen kann. Aus diesem
 * Grund werden hier keine Entities direkt an den Client heraus gegegeben sondern
 * nur entprechende "persistenz-freie" Repräsentationen wie {@link einstein.server.oeffentlich.Stein}
 * und {@link einstein.server.oeffentlich.Spieler}.
 * 
 * @author less02
 *
 */
@Stateless public class EinsteinDAOImpl implements EinsteinDAO {
	
	@PersistenceContext EntityManager em;

	public TischEntity findeTisch(int tischNr) {
		return em.find(TischEntity.class, tischNr);
	}

	public TischEntity tischAufrischen(TischEntity tisch) {
		// Der Aufruf erfolgt aus den EinsteinDelegates und je nach Anwendung ist das Entity
		// mal MANAGED und mal nicht. Für beide Fälle muss daher gewährleistet sein, dass der
		// neuste Stand aus der Datenbank geholt wird
		if (!em.contains(tisch))
			return findeTisch(tisch.getNr());
		em.refresh(tisch);
		return tisch;
	}
	
	public TischEntity neuerTisch(boolean computerGegner) {
		List<Integer> l = em.createQuery("SELECT MAX(t.nr) FROM TischEntity t").getResultList();
		int next = (l.get(0) == null) ? 1 : l.get(0) + 1;
		TischEntity neuerTisch = new TischEntity(next, computerGegner);
		neuerTisch.setStatus(SpielStatus.KEIN_SPIEL);
		em.persist(neuerTisch);
		return neuerTisch;
	}
	
	public SpielerEntity findeSpieler(String spielerName) {
		return em.find(SpielerEntity.class, spielerName);
	}

    private List<Spieler> baueSpielerListe(String abfrageAusdruck, Integer maximum) {
        Query abfrage = em.createQuery(abfrageAusdruck);
        if (maximum != null)
            abfrage = abfrage.setMaxResults(maximum);
        List<SpielerEntity> abfrageErgebnis = abfrage.getResultList();
        List<Spieler> ergebnis = new ArrayList<Spieler>();
        for (SpielerEntity spieler: abfrageErgebnis) {
            ergebnis.add(spieler.kopieren());
        }
        return ergebnis;
        
    }

    public List<Spieler> findeAnwesendeSpieler() {
        return baueSpielerListe( 
            "SELECT s FROM SpielerEntity s WHERE s.anwesend = true", null);
    }

    public List<Spieler> findeBesteSpieler() {
        return baueSpielerListe(
            "SELECT s FROM SpielerEntity s ORDER BY s.partienGewonnen", 10);
    }

	public List<SteinEntity> aktuelleAufstellung(TischEntity tisch) {
		return em.createQuery("SELECT s FROM SteinEntity s where s.tisch = ?1").
			setParameter(1, tisch).getResultList();
	}

    protected void loescheSteine(TischEntity tisch) {
        tisch.setLetzterGezogenerStein(null);
        // Der folgende Aufruf von flush() am EntityManager bewirkt, dass das
        // obige Rücksetzen der Beziehung zwischen dem Tisch und dem zuletzt
        // gezogenen Stein mit der Datenbank synchronisiert wird. Andernfalls
        // würde die folgende Massenlöschung eine Fremdschlüsselverletztung
        // produzieren. Streng genommen sollte das nicht notwendig sein, weil
        // der Entity-Manager AUTOFLUSH verwendet und selber erkennen sollte,
        // dass die Massenlöschung eine vorherige Synchronisation zumindest von
        // Tischen und Steinen notwendig macht. Hier geht es aber leider nicht
        // ohne Hilfestellung seitens der Applikation.
        em.flush();
        em.createQuery("DELETE FROM SteinEntity s WHERE s.tisch = ?1").
            setParameter(1, tisch).executeUpdate();
    }
    
	public void speichereAufstellung(TischEntity tisch, List<SteinEntity> aufstellung) {
        loescheSteine(tisch);
		for (SteinEntity stein: aufstellung)
			em.persist(stein);
	}

	public SteinEntity findeStein(int steinId) {
		return em.find(SteinEntity.class, steinId);
	}
	
	public SpielerEntity spielerAnlegen(String name, String passwort) {
		SpielerEntity spieler = new SpielerEntity(name, passwort);
		em.persist(spieler);
		return spieler;
	}

	public boolean spielerLoeschen(String name, String passwort) {
		int i = em.createQuery("DELETE FROM SpielerEntity b WHERE b.name = " + name +
							   " AND b.passwort = " + passwort).executeUpdate();
		assert(i > 1);
		return (i == 1);
	}

    public boolean steinPasstZuWurf(SteinEntity stein, TischEntity tisch, boolean spielerA) {
        String abfrage = "SELECT s FROM SteinEntity s WHERE s.tisch = ?1 AND s.spielerA = " +
            spielerA + " AND";
        if (tisch.getLetzterWuerfelwurf() < stein.getNr())
            abfrage += " s.nr < " + stein.getNr() + " AND s.nr >= " + tisch.getLetzterWuerfelwurf();
        else
            abfrage += " s.nr > " + stein.getNr() + " AND s.nr <= " + tisch.getLetzterWuerfelwurf();
        List<SteinEntity> dazwischen = em.createQuery(abfrage).
            setParameter(1, tisch).getResultList();
        return (dazwischen.size() > 0);
    }

    public boolean schlageStein(Position pos, TischEntity tisch) {
        if (tisch.getLetzterGezogenerStein() != null &&
            tisch.getLetzterGezogenerStein().getPosition().gleich(pos.x, pos.y)) {
            // Spezialfall: der zu schlagende Stein ist der zuletzt gezogene. Hier
            // muss die entsprechende Referenz des TischEntities zunächst zurück
            // gesetzt und die Änderung in die Datenbank geschrieben werden, weil
            // das Löschen sonst wegen einer Fremdschlüsselverletzung nicht klappt.
            tisch.setLetzterGezogenerStein(null);
            em.flush();
        }
        int anzahlGeschlagen =
            em.createQuery("DELETE FROM SteinEntity s WHERE s.tisch = ?1 AND s.position = ?2").
                setParameter(1, tisch).
                setParameter(2, pos).
                executeUpdate();
        return (anzahlGeschlagen > 0);
    }
    
    public boolean hatSteine(boolean spielerA, TischEntity tisch) {
        long anzahlSteine =
            (Long)em.createQuery("SELECT COUNT(s) FROM SteinEntity s WHERE s.tisch = ?1 AND s.spielerA = " + spielerA).
                setParameter(1, tisch).
                getSingleResult();
        return (anzahlSteine > 0);
    }
    
    public boolean loescheLeerenTisch(TischEntity tisch, String spieler) {
        try {
            tisch = (TischEntity)em.createQuery("SELECT t FROM TischEntity t WHERE " +
                    "t.nr=" + tisch.getNr() + " AND " +
                    "t.spielerA IS NULL AND t.spielerB IS NULL AND " +
                    "(SELECT COUNT(s) FROM SpielerEntity s WHERE s.zuschauerAn = t) = 0").
                    getSingleResult();
            // Tisch ist verwaist, kann also gelöscht werden
            loescheSteine(tisch);
            em.remove(tisch);
            return true;
        }
        catch(NoResultException nrx) {  // Tisch kann noch nicht gelöscht werden
            return false;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void sitzungBeenden (EinsteinDelegate tischDelegate, String spielerName)
        throws SpielException {
        if (tischDelegate != null)
            tischDelegate.verlassen();
        if (spielerName != null) {
            System.out.println(spielerName + " geht");
            SpielerEntity spieler = findeSpieler(spielerName);
            if (spieler != null)
                spieler.setAnwesend(false);
        }
    }


}
