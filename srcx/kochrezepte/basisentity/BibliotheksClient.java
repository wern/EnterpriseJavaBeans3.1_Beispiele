/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.basisentity;

import javax.naming.InitialContext;

import kochrezepte.webrechner.Webrechner;


public class BibliotheksClient {
	public static void main(String[] args) throws Exception {
	    InitialContext context = new InitialContext();
		Bibliothek bibliothek = (Bibliothek)context.lookup("bibliothek/remote");
		Buch dieStrasse = new Buch("Die Strasse", "Cormac Mc. Carthy", 124);
		System.out.println(dieStrasse);
		dieStrasse = bibliothek.aufnehmen(dieStrasse);
		System.out.println(dieStrasse);
		dieStrasse.setAnzahlSeiten(125);
		dieStrasse = bibliothek.aktualisieren(dieStrasse);
		System.out.println(dieStrasse);
	}

}
