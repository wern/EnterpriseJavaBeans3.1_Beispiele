package einstein.client.gui.modell;

import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStatusException;

/**
 * Das Interface für eine View zu einem WürfelModell.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public interface WuerfelViewInterface {
	
	/**
	 * Informiert die Benutzerin darueber, dass sie den falschen Wuerfel bedient hat.
	 * 
	 * @param e
	 */
	public void falscherWuerfel();
	
	/**
	 * Informiert den Benutzer darueber, dass er falsch gespielt hat.
	 * 
	 * @param e
	 */
	public void falschesSpiel(SpielException e);
	
	/**
	 * Informiert die Benutzerin darueber, dass ein falscher Status vorliegt.
	 * 
	 * @param e
	 */
	public void falscherStatus(SpielStatusException e);

}
