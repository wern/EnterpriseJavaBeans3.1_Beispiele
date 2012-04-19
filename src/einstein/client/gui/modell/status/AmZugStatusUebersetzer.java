package einstein.client.gui.modell.status;

import einstein.client.gui.modell.SpielbrettModell;
import einstein.server.oeffentlich.SpielStatus;

/**
 * Uebersetzt die Stati SpielStatus.A_AM_ZUG und SpielStatus.B_AM_ZUG.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class AmZugStatusUebersetzer implements StatusUebersetzerInterface {
	protected final static String AM_ZUG = "ist am Zug";

	public String uebersetzeStatus(SpielbrettModell modell) {
		if (modell.getStatus().equals(SpielStatus.A_AM_ZUG.toString())) {
			return modell.getUserA() + " " + AM_ZUG;
		} else if (modell.getStatus().equals(SpielStatus.B_AM_ZUG.toString())) {
			return modell.getUserB() + " " + AM_ZUG;
		}
		return null;
	}
}
