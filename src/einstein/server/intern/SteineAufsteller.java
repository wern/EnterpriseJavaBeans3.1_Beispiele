package einstein.server.intern;

import java.util.ArrayList;
import java.util.List;

import einstein.server.intern.entities.SteinEntity;
import einstein.server.intern.entities.TischEntity;
import einstein.server.oeffentlich.Position;

/** Hilfsklasse zur Ermittlung einer zufälligen Startposition eines Satzes
 * von Spielsteinen. Die einzige öffentliche Methode {@link #neueAufstellung()}
 * liefert einen Satz von 12 positionierten Steinen (6 je Spieler), wobei die
 * aufgelisteten {@link einstein.server.intern.entities.SteinEntity}s noch nicht
 * persistent sind und auch noch über keine ID verfügen. Die Persistierung ist
 * Aufgabe des {@link einstein.server.intern.EinsteinDAO}.
 * 
 * @author jlessner
 *
 */
public class SteineAufsteller {

	private TischEntity tisch;
	
	public SteineAufsteller(TischEntity tisch) {
		this.tisch = tisch;
	}
	
	public List<SteinEntity> neueAufstellung() {
		List<SteinEntity> aufstellung = new ArrayList<SteinEntity>();
		aufstellung.addAll(neueSteine(true));
		aufstellung.addAll(neueSteine(false));
		return aufstellung;
	}

	private List<SteinEntity> neueSteine(boolean spielerA) {
		List<SteinEntity> ergebnis = new ArrayList<SteinEntity>();
		boolean[] vergebeneNummern = new boolean[SpielBrettHelfer.STARTPOSITIONEN.length];
		for (int i = 0; i < SpielBrettHelfer.STARTPOSITIONEN.length; i++) {
			ergebnis.add(neuerStein(vergebeneNummern, i, spielerA));
		}
		return ergebnis;
	}

	private SteinEntity neuerStein(boolean[] vergebeneNummern, int positionsIndex, boolean spielerA) {
		int steinNr = SpielBrettHelfer.zufallsZahl16();
		if (vergebeneNummern[steinNr-1]) {
			for (steinNr = 1; steinNr <= vergebeneNummern.length; steinNr++)
				if (!vergebeneNummern[steinNr-1])
					break;
		}
		vergebeneNummern[steinNr-1] = true;
		SteinEntity neuerStein = new SteinEntity(steinNr, spielerA, tisch);
		Position pos = new Position(SpielBrettHelfer.STARTPOSITIONEN[positionsIndex]);
		if (!spielerA)
			pos.spiegeln();
		neuerStein.setPosition(pos);
		return neuerStein;
	}
	
}
