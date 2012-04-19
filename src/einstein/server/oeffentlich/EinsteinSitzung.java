package einstein.server.oeffentlich;

import javax.ejb.Remote;

/** Interface der Einstein-Sitzungs-EJB mit den Funktionen zur Eröffnung oder
 * zum Beitritt zu Spieltischen, sowie der Durchführung eines Spiels. Die
 * Einstein-Sitzung ist eine Stateful SessionBean, die einen anwesenden Spieler
 * repräsentiert. Unter Verwendung von JAAS (hier nicht realisiert) ist der
 * Spieler bereits bei Erzeugung der Bean authentifiziert. Ansonsten muss die
 * Session als allererstes über die Funktion {@link #anmelden} an einen Spieler
 * gebunden werden.<br>
 * Jeder Spieler darf nur eine Session unterhalten und kann zu einem Zeitpunkt
 * auch nur an einem Spieltisch sitzen - egal ob als Spieler oder als Zuschauer.
 */
@Remote public interface EinsteinSitzung {

	/********** Session ************/
	/**
	 * Die folgende Funktion ist nur notwendig, wenn der Spielername nicht sowieso
	 * schon über JAAS implizit übertragen wurde.
	 */
	void anmelden(String spielerName) throws VerwaltungsException;
	
	/** Die Abmeldung bewirkt auch ggf. automatisch das Verlassen eines
	 * Tisches, an dem man sitzt und ggf. das Beenden der laufenden Partie,
	 * wobei der Spieler diese Partie damit verliert.
	 */
	void abmelden();

	/********* Spieltische *********/
	
	/** Eröffnet einen neuen Tisch und gibt die Nummer des Tisches zurück
	 * @param computerGegner true, wenn der Spieler gegen den Computer spielen
	 *   will. In dem Fall kann kein anderer Spieler in die Partie einsteigen.
	 *   Der eröffnende Spieler sitzt dabei immer auf Platz A (von links oben
	 *   nach rechts unten spielend).
	 */
	int eroeffneTisch(boolean computerGegner) throws SpielException;
	
	/** Registriert den Spieler als Zuschauer an dem betreffenden Tisch.
	 * Ein Zuschauer kann dem Spielgeschehen nur als Beobachter beiwohnen
	 * und nicht eingreifen. Die weiter unten folgenden Funktionen zur
	 * Steuerung des Spiels liefern bei Zuschauern eine {@link SpielException}
	 * @throws SpielException wenn der angegeben Tisch nicht (mehr) existiert
	 */
	void zuschauen(int tischNr) throws SpielException;
	
	/** Registriert einen Spieler als aktiven Teilnehmer des Spielgeschehens
	 * an einem angegebenen Tisch. Der einsteigende Spieler erhält den erstbesten
	 * freien Platz am Tisch. Welcher Platz dem Spieler zugewiesen wurde, kann
	 * nach erfolgreichem Einstieg über die Funktionen {@link #partieAbfrage}
	 * ermittelt werden. Der Spieler auf Platz A spielt von links oben nach
	 * rechts unten, der Spieler an Platz B umgekehrt.
	 */
	void einsteigen(int tischNr) throws SpielException;
	
	/** Verlässt man einen Tisch, an dem man Spieler einer laufenden Partie ist,
	 * beendet das automatisch die Partie und erklärt den Gegner zum Sieger.
     * @return true, wenn der Tisch durch das Verlassen verwaist ist und gelöscht
     *   wurde. Sonst false.
	 * @throws SpielException wenn die angemeldete Person gar nicht an einem
	 *   Tisch sitzt
	 */
	boolean verlassen() throws SpielException;
	
	/** Liefert null, wenn man an gar keinem Tisch sitzt */
	Integer aktuellerTisch();

	/********* Spielen ***********/

	/** Signalisiert Spielbereitschaft. Hat der jeweils andere Spieler
	 * ebenfalls Spielbereitschaft signalisiert, beginnt automatisch die
	 * Partie, basierend auf einer zufälligen Anfangsaufstellung. Welcher
	 * Spieler beginnt, lässt sich am zurückgegebenen {@link SpielStatus}
	 * erkennen.
	 */
	SpielStatus bereit() throws SpielException, SpielStatusException;
	
	/**
	 * Liefert für Spieler und Zuschauer den vollständigen aktuellen
	 * Spielstand. Wenn die Partie nicht läuft, zeigt die Auftellung
	 * den Endstand der letzten Partie oder null, wenn an dem Tisch
	 * noch nie gespielt wurde. Wenn eine neue Partie gestartet wird,
	 * liefert das Partie-Objekt die Startaufstellung und der Status
	 * zeigt an, wer beginnt (A_WUERFELT oder B_WUERFELT).
	 * Auch später hinzukommende Zuschauer können über diese Funktion
	 * jederzeit den Stand einer laufenden Partie abfragen und anschließend
	 * über die Funktion naechsterZug eine Aktualisierung einholen.
	 */
	Partie partieAbfragen() throws SpielException, SpielStatusException;

    /** Alle an einem Tisch sitzende Personen (Zuschauer wie Spieler) können
     * in einer laufenden Partie über diese Funktion die Aktualisierung des
     * Spielstands erfragen. Für Zuschauer ist hier ist eine Unsicherheit
     * gegeben, da die Partie nicht auf die Zuschauer "wartet", d.h. wenn
     * zu langsam gepollt wurde, ist die Partie vielleicht schon weiter
     * fortgeschritten als ein einzelner Zug. Der Zuschauer bemerkt dies
     * daran, dass eine Lücke in den Nummern der Züge auftritt. In diesem
     * Fall muss er sich über die Funktion {@link #partieAbfragen} eine Komplett-
     * aktualisierung besorgen. Natürlich könnte man auch *immer* partieAbfragen
     * aufrufen, aber das ergibt eine höhere Systembelastung, da dann immer alle
     * Steine abgefragt werden. standAbfragen liefert die Aktualisierung sehr
     * effizient über einen Join von Tisch und zuletzt gezogenem Stein.
     * 
     * @throws SpielStatusException wenn aktuell keine Partie läuft
     */
    SpielStand standAbfragen() throws SpielException, SpielStatusException;
    
	/** Liefert das nächste Würfelergebnis. Die Session merkt sich die
	 * zuletzt zurückgegebene Augenzahl und prüft damit den
	 * nächsten Zug, um zu Verhindern, dass der Client mogelt.
	 * @throws SpielStatusException wenn der Aufrufer kein Spieler oder
	 *   nicht an der Reihe ist oder im aktuellen Zug bereits gewürfelt hat
	 */
	int wuerfeln() throws SpielException, SpielStatusException;
	
	/** Führt einen Zug aus. Im zurückgelieferter SpielStand ist der
	 * Folgestatus abzulesen. Ggf. wurde die Partie durch den Zug beendet.
	 * @throws SpielStatusException wenn der Aufrufer kein Spieler oder
	 *   nicht an der Reihe ist oder noch nicht gewürfelt hat.
     * @throws MogelException, wenn der Spieler einen ungültigen Zug
     *   abgibt. Die MogelException ist unchecked und führt daher zum
     *   Abbruch der Session.
	 */
	SpielStand ziehen(Zug zug) throws SpielException, SpielStatusException;

}
