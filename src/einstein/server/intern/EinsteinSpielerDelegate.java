package einstein.server.intern;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import einstein.server.intern.computergegner.ComputerGegner;
import einstein.server.intern.computergegner.JMSHelfer;
import einstein.server.intern.entities.SpielerEntity;
import einstein.server.intern.entities.SteinEntity;
import einstein.server.oeffentlich.MogelException;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStand;
import einstein.server.oeffentlich.SpielStatus;
import einstein.server.oeffentlich.SpielStatusException;
import einstein.server.oeffentlich.Zug;

/**  Enthält die Logik für die aktive Teilnahme eines Spielers an einem Spieltisch
 * 
 * @author jlessner
 */
public class EinsteinSpielerDelegate extends EinsteinDelegate {

	private boolean spielerA;
	
	public EinsteinSpielerDelegate(int tischNr, String spielerName, EinsteinDAO dao)
		throws SpielException {
		super(tischNr, spielerName, dao);
        initialisieren();
	}

    protected void initialisieren() throws SpielException {
        SpielerEntity spieler = findeSpieler();
        if (spieler.spielt() != null)
            throw new SpielException("Du spielst bereits an Tisch " + spieler.spielt());
        if (tisch.belegt())
            throw new SpielException("Tisch " + tisch.getNr() + " ist belegt");
        if (tisch.getSpielerA() == null) {
            tisch.setSpielerA(spieler);
            spielerA = true;
            tisch.setStatus(SpielStatus.KEIN_SPIEL);
        }
        else
            tisch.setSpielerB(spieler);
    }
    
	public boolean verlassen() {
		auffrischen();
		if (tisch.getStatus().spielLaeuft())
			partieBeenden(spielerA ? SpielStatus.A_ABGEBROCHEN : SpielStatus.B_ABGEBROCHEN);
		if (spielerA)
			tisch.setSpielerA(null);
		else
			tisch.setSpielerB(null);
        return dao.loescheLeerenTisch(tisch, spielerName);
	}

	private void partieBeenden(SpielStatus endStatus) {
		tisch.setStatus(endStatus);
        SpielerEntity sieger = endStatus.siegerA() ? tisch.getSpielerA() : tisch.getSpielerB();
        SpielerEntity verlierer = endStatus.siegerA() ? tisch.getSpielerB() : tisch.getSpielerA();
        if (sieger != null) { // Möglicher Fall bei Computerpartien
            sieger.setPartienGespielt(sieger.getPartienGespielt() + 1);
            sieger.setPartienGewonnen(sieger.getPartienGewonnen() + 1);
        }
        if (verlierer != null) // Möglicher Fall bei Computerpartien
            verlierer.setPartienGespielt(verlierer.getPartienGespielt() + 1);
	}
	
	public int wuerfeln() throws SpielException, SpielStatusException {
		auffrischen();
		pruefeStatus(SpielStatus.A_WUERFELT, SpielStatus.B_WUERFELT, spielerA);
		tisch.setLetzterWuerfelwurf(SpielBrettHelfer.zufallsZahl16());
		tisch.setStatus(spielerA ? SpielStatus.A_AM_ZUG : SpielStatus.B_AM_ZUG);
		return tisch.getLetzterWuerfelwurf();
	}

    /**
     * Prüft, ob ein Zug für einen bestimmten Stein gültig ist. Ist dies nicht
     * der Fall, wird dies als Versuch gewertet, dass der betreffende Spieler
     * mogeln will und es wird eine {@link MogelException} geworfen.
     */
    protected void pruefeZug(SteinEntity stein, Zug zug) throws MogelException {
        if (stein == null)
            throw new MogelException("Stein " + zug.getSteinId() + " nicht gefunden!");
        if (stein.isSpielerA() != spielerA)
            throw new MogelException("Stein " + zug.getSteinId() + " ist nicht Dein eigener!");
        if (!zug.getZiel().erreichbar(stein.getPosition(), spielerA))
            throw new MogelException("Position " + zug.getZiel() + " nicht erreichbar für Stein " + stein);
        if (!zug.getZiel().imSpielfeld())
            throw new MogelException("Position " + zug.getZiel() + " ist nicht gültig");
        if (tisch.getLetzterWuerfelwurf() != stein.getNr() &&
            dao.steinPasstZuWurf(stein, tisch, spielerA))
            throw new MogelException("Stein " + stein + " passt nicht zu Würfelwurf " + tisch.getLetzterWuerfelwurf());
    }
    
	public SpielStand ziehen(Zug zug)
        throws SpielException, SpielStatusException {
		auffrischen();
		pruefeStatus(SpielStatus.A_AM_ZUG, SpielStatus.B_AM_ZUG, spielerA);
		SteinEntity stein = dao.findeStein(zug.getSteinId());
        pruefeZug(stein, zug);
        boolean steinGeschlagen = dao.schlageStein(zug.getZiel(), tisch);
        boolean gegnerHatSteine = steinGeschlagen ? dao.hatSteine(!spielerA, tisch) : true;
        stein.setPosition(zug.getZiel());
        tisch.setLetzteZugNr(tisch.getLetzteZugNr() + 1);
        tisch.setLetzterGezogenerStein(stein);
		// Prüfen, ob der Zug die Partie beendet
        if (!gegnerHatSteine || SpielBrettHelfer.imZiel(stein))
            partieBeenden(spielerA ? SpielStatus.A_GEWONNEN : SpielStatus.B_GEWONNEN);
        else {
            tisch.setStatus(spielerA ? SpielStatus.B_WUERFELT : SpielStatus.A_WUERFELT);
            if (tisch.isComputerGegner() && spielerA)
                JMSHelfer.computergegnerAuffordern(tisch.getNr());
        }
		return ermittleSpielStand();
	}

	public SpielStatus bereit() throws SpielException, SpielStatusException {
		auffrischen();
		pruefeStatus(!tisch.getStatus().spielLaeuft());
		SpielStatus status = tisch.getStatus();
        if (tisch.isComputerGegner())
            tisch.setStatus(SpielStatus.A_WUERFELT);
        else
            tisch.setStatus(status.bereit(spielerA));
		if (tisch.getStatus().spielLaeuft()) { // Spiel geht los
			List<SteinEntity> aufstellung = new SteineAufsteller(tisch).neueAufstellung();
			dao.speichereAufstellung(tisch, aufstellung);
            tisch.setLetzteZugNr(0);
            tisch.setLetzterWuerfelwurf(0);
            tisch.setLetzterGezogenerStein(null);
        }
		return tisch.getStatus();
	}

}
