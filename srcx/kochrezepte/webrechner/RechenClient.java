/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.webrechner;

import javax.naming.InitialContext;

/* Dieser Client demonstriert die Wirkung des WebserviceInterceptors. Der Einfachheit halber
 * verwendet er das Remote-Interface der Webrechner-Bean statt diese als Webservice anzusprechen.
 * Das soll an dieser Stelle lediglich die Abhängigkeit zur Installation eines Webservice-Toolkits sparen.
 * Die Wirkung des Interceptors ist vom physischen Kommunikationsprotokoll unabhängig.
 */
public class RechenClient {
	public static void main(String[] args) throws Exception {
	    InitialContext context = new InitialContext();
		Webrechner rechner = (Webrechner)context.lookup("webrechner/remote");
		System.out.println(rechner.dividieren(6, 2));
		System.out.println(rechner.dividieren(6, 4));
		System.out.println(rechner.dividieren(6, 0));
	}

}
