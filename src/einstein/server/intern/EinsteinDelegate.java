package einstein.server.intern;

import java.util.List;

import einstein.server.intern.entities.SpielerEntity;
import einstein.server.intern.entities.SteinEntity;
import einstein.server.intern.entities.TischEntity;
import einstein.server.oeffentlich.Partie;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStand;
import einstein.server.oeffentlich.SpielStatus;
import einstein.server.oeffentlich.SpielStatusException;
import einstein.server.oeffentlich.Zug;

/**
 * Basisklasse f�r die Implementierung der Spiellogik f�r Spieler,
 * Zuschauer und den Computergegner. W�rde man dies alles direkt in
 * {@link einstein.server.intern.EinsteinSitzungImpl} hinein stecken,
 * w�rde die EJB viel zu gro� werden. Daher wird die Logik �ber das
 * Delegate-Muster in verschiedene Klassen ausgelagert. Als Vorteil
 * k�nnen in diesen Klassen auch wieder ungest�rt vom EJB-Komponentenmodell
 * objekt-orientierte Mechanismen genutzt werden. Deutlich wird dies
 * hier an den abgeleiteten Delegate-Klasen f�r die verschiedenen Sichten
 * auf das Spiel.
 */
public abstract class EinsteinDelegate {
	protected TischEntity tisch;
	protected String spielerName;
	protected EinsteinDAO dao;

	public EinsteinDelegate(int tischNr, String spielerName, EinsteinDAO dao)
		throws SpielException {
		this.dao = dao;
		tisch = dao.findeTisch(tischNr);
		if (tisch == null)
			throw new SpielException("Tisch " + tischNr + " nicht (mehr) vorhanden");
        this.spielerName = spielerName;
	}

    protected SpielerEntity findeSpieler() throws SpielException {
        SpielerEntity spieler = dao.findeSpieler(spielerName);
        if (spieler == null)
            throw new SpielException("Du bist nicht registriert");
        return spieler;
    }
    
	public int getTischNr() { return tisch.getNr(); }

	protected void auffrischen() {
		tisch = dao.tischAufrischen(tisch);
	}

	protected void pruefeStatus(boolean statusOK) throws SpielStatusException {
		if (!statusOK)
			throw new SpielStatusException("Falscher Status " + tisch.getStatus());
	}

	protected void pruefeStatus(SpielStatus statusA, SpielStatus statusB, boolean spielerA)
		throws SpielStatusException {
		pruefeStatus(tisch.getStatus() == statusA && spielerA ||
					 tisch.getStatus() == statusB && !spielerA);
	}
    
    protected SpielStand ermittleSpielStand() {
        Zug zug;
        if (tisch.getLetzterGezogenerStein() != null)
            zug = new Zug(tisch.getLetzterGezogenerStein().getId(),
                      tisch.getLetzterGezogenerStein().getPosition());
        else
            zug = null;
        return new SpielStand(
                tisch.getLetzterWuerfelwurf(),
                tisch.getLetzteZugNr(),
                zug, tisch.getStatus());
    }

	public Partie partieAbfragen() throws SpielStatusException {
		auffrischen();
		List<SteinEntity> steine = dao.aktuelleAufstellung(tisch);
        String spielerA = (tisch.getSpielerA() != null) ?
            tisch.getSpielerA().getName() : null;
        String spielerB = (tisch.getSpielerB() != null) ?
            tisch.getSpielerB().getName() : null;
		return new Partie(spielerA, spielerB, tisch.isComputerGegner(),
                          ermittleSpielStand(), SteinEntity.kopieren(steine));
	}

    public SpielStand standAbfragen() {
        auffrischen();
        return ermittleSpielStand();
    }

    abstract public boolean verlassen() throws SpielException;
	abstract public int wuerfeln() throws SpielException, SpielStatusException;
	abstract public SpielStand ziehen(Zug zug) throws SpielException, SpielStatusException;
	abstract public SpielStatus bereit() throws SpielException, SpielStatusException;

}
