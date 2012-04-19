/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/
package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class EmpfaengerSynchron {
	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) initialContext
				.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		connection.start();

		Destination postfach = (Destination) initialContext.lookup("queue/Praxisbuch");
		MessageConsumer empfaenger = session.createConsumer(postfach, "Bestellungstyp = 'Internet'");
		Message nachricht = empfaenger.receive();
		System.out.println("Nachricht empfangen!");
		System.out.println(((TextMessage) nachricht).getText());
		connection.stop();
		session.close();
		connection.close();
	}
}
