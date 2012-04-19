package einstein.client.gui.modell;

import einstein.server.oeffentlich.SpielException;

/**
 * Das Interface für eine View zu einem BenutzerTischeModell.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public interface BenutzerTischeViewInterface {
	
	/**
	 * Fragt den Benutzer, ob ein Mitspieler gesucht wird oder der Computer der Gegner sein soll.
	 * 
	 * @return true, wenn ein Mitspieler gesucht wird; andernfalls false.
	 */
	public boolean sucheMitspieler();
	
	/**
	 * Informiert den Benutzer darueber, dass er falsch gespielt hat.
	 * 
	 * @param e
	 */
	public void falschesSpiel(SpielException e);

}
