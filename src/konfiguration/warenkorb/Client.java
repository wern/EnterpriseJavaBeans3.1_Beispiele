/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package konfiguration.warenkorb;

import javax.naming.InitialContext;

public class Client {

	public static void main(String[] args) throws Exception{
	      InitialContext ctx = new InitialContext();
	      Warenkorb korb = (Warenkorb) ctx.lookup("WarenkorbMitMindestwert/remote");
	      korb.legeInWarenkorb(3, "0-24342-X", 2);
	      korb.legeInWarenkorb(1, "0-815-X", 1);
	      System.out.println("Kosten des Warenkorbs: "+korb.geheZurKasse());
	}

}
