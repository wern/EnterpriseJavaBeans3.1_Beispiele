package einstein.client.gui.modell.status;

import einstein.client.gui.modell.SpielbrettModell;

/**
 * Definiert eine Operation zum Uebersetzen des aktuellen SpielStatus,
 * so dass dieser angemessen in der GUI angezeigt werden kann.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public interface StatusUebersetzerInterface {

	/**
	 * Uebersetzt den aktuellen SpielStatus.
	 * 
	 * @param modell
	 * @return uebersetzten SpielStatus oder NULL (falls die Uebersetzer-Instanz
	 * den Status nicht behandeln konnte)
	 */
	public String uebersetzeStatus(SpielbrettModell modell);
}
