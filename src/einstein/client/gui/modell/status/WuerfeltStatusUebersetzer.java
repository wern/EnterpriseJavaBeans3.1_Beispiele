package einstein.client.gui.modell.status;

import einstein.client.gui.modell.SpielbrettModell;
import einstein.server.oeffentlich.SpielStatus;

/**
 * Uebersetzt die Stati SpielStatus.A_WUERFELT und SpielStatus.B_WUERFELT.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class WuerfeltStatusUebersetzer implements StatusUebersetzerInterface {
	protected final static String WUERFELT = "würfelt";

	public String uebersetzeStatus(SpielbrettModell modell) {
		if (modell.getStatus().equals(SpielStatus.A_WUERFELT.toString())) {
			return modell.getUserA() + " " + WUERFELT;
		} else if (modell.getStatus().equals(SpielStatus.B_WUERFELT.toString())) {
			return modell.getUserB() + " " + WUERFELT;
		}
		return null;
	}
}
