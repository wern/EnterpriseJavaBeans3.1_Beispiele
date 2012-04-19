/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.dateiexport;

import javax.naming.InitialContext;

public class KundenClient {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		Kundenerzeuger kundenerzeuger = (Kundenerzeuger)context.lookup("kundenerzeuger/remote");
		
		int anzahlKunden = 3;
		if (args.length > 0)
			anzahlKunden = Integer.parseInt(args[0]);
		kundenerzeuger.erzeugeKunden(anzahlKunden);
	}
}
