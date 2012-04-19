/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package stateless.zinsrechner.ejb3;

import javax.naming.InitialContext;

public class Client {

	public static void main(String[] args) throws Exception{
	      InitialContext ctx = new InitialContext();
	      Zinsrechner rechner = (Zinsrechner) ctx.lookup("Zinsrechner/remote");
	      System.out.println(rechner.berechneSparSumme(24000, 5, 0.03));
	}

}
