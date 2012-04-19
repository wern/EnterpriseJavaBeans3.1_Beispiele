/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.datentakt;

import javax.naming.InitialContext;

public class Client {
	public static void main(String[] args) throws Exception {
		TestDatenErzeuger erzeuger = (TestDatenErzeuger) new InitialContext()
				.lookup("TestDatenErzeugerBean/remote");

		erzeuger.erzeugeTestDaten();

		Fluganzeige anzeige = (Fluganzeige) new InitialContext()
				.lookup("FluganzeigeBean/remote");
		while (true) {
			Flugdaten starts = anzeige.getStarts();
			Flugdaten landungen = anzeige.getLandungen();
			dump("Starts", starts);
			dump("Landungen", landungen);
			Thread.sleep(60000);
		}
	}

	private static void dump(String prefix, Flugdaten fluege) {
		System.out.printf("\n\n" + prefix + " (Stand %TR):\n", fluege
				.getStand());
		System.out.println("---------------------------------------");
		for (Flug flug : fluege.getFluege()) {
			System.out.println(flug);
		}
	}
}
