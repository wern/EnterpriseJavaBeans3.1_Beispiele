/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package sicherheit.deklarativ;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless(name="Torwaechter")
public class TorwaechterImpl implements Torwaechter {

	@PermitAll
	public String fuerAlleMitarbeiter() {
		return "Jeder Mitarbeiter ist hier immer willkommen";
	}

	@RolesAllowed(value = "angestellter")
	public String nurFuerAngestellte() {
		return "Angestellte sind hier immer willkommen";
	}

	@RolesAllowed(value = "praktikant")
	public String nurFuerPraktikanten() {
		return "Hallo Prakti, wie geht's?";
	}
}
