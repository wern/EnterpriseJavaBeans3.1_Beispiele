package einstein.server.intern.computergegner;

import java.util.ArrayList;
import java.util.List;

import einstein.server.oeffentlich.Position;
import einstein.server.oeffentlich.Stein;
import einstein.server.oeffentlich.Zug;

/**
 * Hilfsklasse rund um das Setzen und Suchen von Spielsteinen. Die Funktionen
 * werden von der Klasse {@link einstein.server.intern.computergegner.MonteCarlo}
 * verwendet, um erlaubte Züge zu ermitteln und Zufallspartien im Hauptspeicher
 * durchzuspielen.
 * 
 * @author less02
 *
 */
class SteineHelfer {

	public static List<Stein> aufstellungKopieren(List<Stein> aufstellung) {
		List<Stein> ergebnis = new ArrayList<Stein>(aufstellung.size());
		for (Stein stein: aufstellung)
			ergebnis.add(new Stein(stein));
		return ergebnis;
	}

	public static Stein findeStein(List<Stein> aufstellung, int steinId) {
		for (Stein stein: aufstellung)
			if (stein.id == steinId)
				return stein;
		assert(false); // darf nicht auftreten
		return null;
	}

	public static Stein findeStein(List<Stein> aufstellung, Position position) {
		for (Stein stein: aufstellung)
			if (stein.position.gleich(position.x, position.y))
				return stein;
		assert(false); // darf nicht auftreten
		return null;
	}

	public static boolean hatSteine(List<Stein> aufstellung, boolean spielerA) {
		for (Stein stein: aufstellung)
			if (stein.spielerA == spielerA)
				return true;
		return false;
	}

	private static void gueltigenZugHinzufuegen(List<Zug> zuege, Stein stein, int xdelta, int ydelta) {
		Position p = new Position(stein.position.x + xdelta, stein.position.y + ydelta);
		if (p.imSpielfeld())
			zuege.add(new Zug(stein.id, p));
	}
	
	public static List<Zug> moeglicheZuege(Stein stein) {
		List<Zug> ergebnis = new ArrayList<Zug>();
		if (stein.spielerA) {
			gueltigenZugHinzufuegen(ergebnis, stein, 1, -1);
			gueltigenZugHinzufuegen(ergebnis, stein, 1,  0);
			gueltigenZugHinzufuegen(ergebnis, stein, 0, -1);
		}
		else {
			gueltigenZugHinzufuegen(ergebnis, stein, -1, 1);
			gueltigenZugHinzufuegen(ergebnis, stein, -1, 0);
			gueltigenZugHinzufuegen(ergebnis, stein,  0, 1);
		}
		return ergebnis;
	}

	public static List<Stein> moeglicheSteine(List<Stein> aufstellung, boolean spielerA, int wuerfelWurf) {
		List<Stein> ergebnis = new ArrayList<Stein>();
		for (Stein stein: aufstellung) {
			if (stein.spielerA == spielerA && stein.nr == wuerfelWurf) {
				ergebnis.add(stein);
				return ergebnis;
			}
		}
		Stein naechsteDrunter = null;
		for (Stein stein: aufstellung) {
			if (stein.spielerA == spielerA &&
				stein.nr < wuerfelWurf &&
				(naechsteDrunter == null || naechsteDrunter.nr < stein.nr)) {
				naechsteDrunter = stein;
			}
		}
        if (naechsteDrunter != null)
            ergebnis.add(naechsteDrunter);
		Stein naechsteDrueber = null;
		for (Stein stein: aufstellung) {
			if (stein.spielerA == spielerA &&
				stein.nr > wuerfelWurf &&
				(naechsteDrueber == null || naechsteDrueber.nr > stein.nr)) {
				naechsteDrueber = stein;
			}
		}
        if (naechsteDrueber != null)
            ergebnis.add(naechsteDrueber);
        assert(ergebnis.size() > 0);
		return ergebnis;
	}

}
