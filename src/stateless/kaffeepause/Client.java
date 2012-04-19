/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package stateless.kaffeepause;

import java.util.concurrent.Future;

import javax.naming.InitialContext;

public class Client {

	public static void main(String[] args) throws Exception {
		InitialContext ctx = new InitialContext();
		//KaffeeMaschine maschine = (KaffeeMaschine) ctx
		//		.lookup("java:global/kaffee/KaffeeMaschine");
		KaffeeMaschine maschine = (KaffeeMaschine) ctx
			.lookup("KaffeeMaschine/remote");
		System.out.println("Starte die Kaffeemaschine!");
		Future<Kaffee> kaffee = maschine.kocheKaffee();
		System.out.println("Decke den Tisch...");
		System.out.println("Warte auf den Kaffee...");
		System.out.println("Schenke " + kaffee.get().getAnzahlTassen()
				+ " Tassen aus.");
		System.exit(0);
	}

}
