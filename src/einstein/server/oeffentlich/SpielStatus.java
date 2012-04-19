package einstein.server.oeffentlich;

/** Status einer Partie. Die Zustände A_WUERFELT, A_AM_ZUG, B_WUERFELT, B_AM_ZUG
 * repräsentieren eine laufende Partie, und zeigen Spielern und Zuschauern, welcher
 * Spieler grade mit welcher Aktion dran ist. A_GEWONNEN und B_GEWONNEN zeigen jeweils
 * an, dass eine Partie von einem Spieler gewonnen wurde. A_ABGEBROCHEN und B_ABGEBROCHEN
 * zeigen an, dass eine Partie frühzeitig abgebrochen wurde, weil ein Spieler den Tisch
 * einfach verlassen hat. Dabei hat der jeweils andere Spieler automatisch gewonnen.
 * KEIN_SPIEL heißt, dass die Partie in der Vorbereitung ist und auf die Spielbereitschaft
 * der Spieler wartet. A_BEREIT heißt, das Spieler A Spielbereitschaft signalisiert hat,
 * Spieler B aber noch nicht. B_BEREIT ist entsprechend umgekehrt. Haben beide Spieler
 * ihre Spielbereitschaft signalisiert, geht die Partie automatisch los und zeigt mit
 * A_WUERFELT oder B_WUERFELT an, wer anfängt.
 */ 
public enum SpielStatus {
	KEIN_SPIEL, A_BEREIT, B_BEREIT,
	A_WUERFELT, A_AM_ZUG,
	B_WUERFELT, B_AM_ZUG,
	A_GEWONNEN, B_GEWONNEN,
	A_ABGEBROCHEN, B_ABGEBROCHEN;
	
	public boolean spielLaeuft() {
		return wuerfeln() || ziehen();
	}
	
	public boolean wuerfeln() {
		return this == A_WUERFELT ||
			   this == B_WUERFELT;
	}

	public boolean ziehen() {
		return this == A_AM_ZUG ||
			   this == B_AM_ZUG;
	}
	
    /** Die Funktion sorgt dafür, dass jeweils der Spieler beginnt, der
     * als erster seine Spielbereitschaft signalisiert hat
     * @param spielerA true, wenn Spieler A seine Bereitschaft signalisiert,
     *   false für Spieler B
     * @return Den SpielStatus nach Bereitmeldung.
     */
	public SpielStatus bereit(boolean spielerA) {
		if (((this == A_BEREIT) && !spielerA) ||
		    ((this == B_BEREIT) && spielerA))
			return (this == A_BEREIT) ? A_WUERFELT : B_WUERFELT;
		if (!this.spielLaeuft())
			return spielerA ? A_BEREIT : B_BEREIT;
		return this;
	}
    
    public boolean siegerA() {
        return (this == A_GEWONNEN || this == B_ABGEBROCHEN);
    }
}
