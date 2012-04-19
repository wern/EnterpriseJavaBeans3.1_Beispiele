package einstein.server.intern.computergegner;

import java.util.ArrayList;
import java.util.List;

import einstein.server.intern.SpielBrettHelfer;
import einstein.server.oeffentlich.Stein;
import einstein.server.oeffentlich.Zug;

/**
 * Diese Klasse enthält den so genannten Monte-Carlo-Algorithmus zur
 * Berechnung des nächsten Zugs des Computergegners. Das Verfahren ist
 * gleichermaßen genial wie einfach: Für eine gegebene Spielstellung
 * werden alle überhaupt möglichen Züge ermittelt, und anschließend
 * werden für jeden dieser Züge {@link #ANZAHL_ZUFALLSSPIELE} Zufalls-
 * partien gespielt, d.h. beide Spieler machen zufällige, legale Züge,
 * bis die Partie zu Ende ist. Genommen wird am Ende der Zug, für den
 * die meisten darauf basierenden Zufallspartien gewonnen wurden.<br>
 * 
 * Vielen Dank an dieser Stelle an Dr. Jörg Sameith, der uns dieses
 * Verfahren so erklärt hat, dass wir's auch verstanden haben ;-)
 * 
 * @author less02
 *
 */
public class MonteCarlo {
	public static final int ANZAHL_ZUFALLSSPIELE = 100;

	public Zug naechsterZug(List<Stein> aufstellung, boolean spielerA, int wuerfelWurf) {
		List<Zug> moeglicheZuege = moeglicheZuege(aufstellung, spielerA, wuerfelWurf);
		Zug besterZug = null;
		int besteGewonneneVersuche = -1;
		for (Zug zug: moeglicheZuege) {
			int gewonneneVersuche = zufallsSpiele(aufstellung, zug, spielerA, ANZAHL_ZUFALLSSPIELE);
			if (gewonneneVersuche > besteGewonneneVersuche) {
				besterZug = zug;
				besteGewonneneVersuche = gewonneneVersuche;
			}
		}
		return besterZug;
	}

	protected int zufallsSpiele(List<Stein> aufstellung, Zug zug, boolean spielerA, int anzahlSpiele) {
		int gewonneneSpiele = 0;
		for (int s = 0; s < anzahlSpiele; s++) {
			List<Stein> neueAufstellung = SteineHelfer.aufstellungKopieren(aufstellung);
			if (ziehen(neueAufstellung, zug))
				gewonneneSpiele++;
			else {
				if (zufallsSpiel(neueAufstellung, spielerA))
					gewonneneSpiele++;
			}
		}
		return gewonneneSpiele;
	}

	protected boolean zufallsSpiel(List<Stein> aufstellung, boolean spielerA) {
		boolean amZugSpielerA = !spielerA;
		while(true) {
			int wuerfelWurf = SpielBrettHelfer.zufallsZahl16();
			List<Zug> moeglicheZuege = moeglicheZuege(aufstellung, amZugSpielerA, wuerfelWurf);
			int zufallsIndex = SpielBrettHelfer.zufallsZahl(moeglicheZuege.size());
			Zug zug = moeglicheZuege.get(zufallsIndex);
			if (ziehen(aufstellung, zug))
				return amZugSpielerA == spielerA;
			amZugSpielerA = !amZugSpielerA;
		}
	}

	public boolean ziehen(List<Stein> aufstellung, Zug zug) {
		Stein stein = SteineHelfer.findeStein(aufstellung, zug.getSteinId());
		if (SpielBrettHelfer.imZiel(zug.getZiel(), stein.spielerA))
			return true; // Ziel erreicht -> gewonnen
		Stein steinAnPosition = SteineHelfer.findeStein(aufstellung, zug.getZiel());
		if (steinAnPosition != null) {
			aufstellung.remove(steinAnPosition);
			if (!SteineHelfer.hatSteine(aufstellung, !stein.spielerA))
				return true; // Gegner hat keine Steine mehr -> gewonnen
		}
		stein.position = zug.getZiel();
		return false;
	}

	protected List<Zug> moeglicheZuege(List<Stein> aufstellung, boolean spielerA, int wuerfelWurf) {
		List<Stein> moeglicheSteine = SteineHelfer.moeglicheSteine(aufstellung, spielerA, wuerfelWurf);
		List<Zug> moeglicheZuege = new ArrayList<Zug>();
		for (Stein stein: moeglicheSteine)
			moeglicheZuege.addAll(SteineHelfer.moeglicheZuege(stein));
		assert(moeglicheZuege.size() > 0);
		return moeglicheZuege;
	}

}
