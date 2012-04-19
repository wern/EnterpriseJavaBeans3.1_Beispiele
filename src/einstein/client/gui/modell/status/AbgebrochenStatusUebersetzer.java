package einstein.client.gui.modell.status;

import einstein.client.gui.modell.SpielbrettModell;
import einstein.server.oeffentlich.SpielStatus;

/**
 * Uebersetzt die Stati SpielStatus.A_ABGEBROCHEN und SpielStatus.B_ABGEBROCHEN.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class AbgebrochenStatusUebersetzer implements StatusUebersetzerInterface {
	protected final static String ABGEBROCHEN = "hat gewonnen, da der Gegner abgebrochen hat";

	public String uebersetzeStatus(SpielbrettModell modell) {
		if (modell.getStatus().equals(SpielStatus.A_ABGEBROCHEN.toString())) {
			return modell.getUserB() + " " + ABGEBROCHEN;
		} else if (modell.getStatus().equals(SpielStatus.B_ABGEBROCHEN.toString())) {
			return modell.getUserA() + " " + ABGEBROCHEN;
		}
		return null;
	}
}
