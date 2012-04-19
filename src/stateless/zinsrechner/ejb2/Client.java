/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package stateless.zinsrechner.ejb2;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class Client {

	public static void main(String[] args) throws Exception {
		InitialContext ctx = new InitialContext();
		ZinsrechnerEJB2SichtRemote rechner = ((ZinsrechnerEjb2SichtHome) PortableRemoteObject
				.narrow(ctx.lookup("ZinsrechnerEjb2Sicht/home"),
						ZinsrechnerEjb2SichtHome.class)).create();
		System.out.println(rechner.berechneSparSumme(24000, 5, 0.03));
	}

}
