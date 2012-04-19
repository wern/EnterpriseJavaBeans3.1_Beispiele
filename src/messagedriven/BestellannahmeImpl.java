/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/
package messagedriven;

import java.util.Enumeration;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(
		name = "Bestellannahme",
		activationConfig = {
		@ActivationConfigProperty(
				propertyName = "destinationType",
				propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(
				propertyName = "destination",
				propertyValue = "queue/Praxisbuch") })
public class BestellannahmeImpl implements MessageListener {
	public void onMessage(Message nachricht) {
		try {
			MapMessage map = (MapMessage) nachricht;
			Enumeration<String> artikelnummern = map.getMapNames();
			while (artikelnummern.hasMoreElements()) {
				String artikelnummer = artikelnummern.nextElement();
				System.out.println(artikelnummer + ": "
						+ map.getInt(artikelnummer) + "Stück");
			}
		} catch (JMSException e) {
			throw new EJBException(e);
		}
	}
}
