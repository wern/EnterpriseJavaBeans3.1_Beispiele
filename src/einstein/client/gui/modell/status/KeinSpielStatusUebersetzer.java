package einstein.client.gui.modell.status;

import einstein.client.gui.modell.SpielbrettModell;
import einstein.server.oeffentlich.SpielStatus;

/**
 * Uebersetzt den Status SpielStatus.KEIN_SPIEL.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class KeinSpielStatusUebersetzer implements StatusUebersetzerInterface {

	public String uebersetzeStatus(SpielbrettModell modell) {
		if (modell.getStatus().equals(SpielStatus.KEIN_SPIEL.toString())) {
			return "Das Spiel hat noch nicht begonnen";
		}
		return null;
	}
}
