package einstein.server.intern.computergegner;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.jms.Message;
import javax.jms.MessageListener;

import einstein.server.intern.EinsteinDAO;

/** Message-Driven Bean zur Entgegennahme der Aufforderung zu einem Computerzug.
 * Die eingehenden Nachrichten enthalten jeweils die Nummer des Tischs, an dem
 * eine Computerzug ansteht. Die EJB implementiert die Logik daf�r nicht selbst,
 * sondern instanziiert einen {@link einstein.server.intern.computergegner.EinsteinComputerDelegate},
 * der �ber den Monte-Carlo-Algorithmus einen Zug berechnet. Da im {@link #onMessage(Message)}
 * sowohl gew�rfelt als auch ein Zug gemacht werden muss, verwendet die EJB
 * selbstverwaltete Transaktionen, um die beiden Operationen in separaten
 * Transaktionen durchf�hren zu k�nnen. Auf diese Weise kriegen die Zuschauer
 * und der andere Spieler fr�hzeitig eine R�ckmeldung �ber den W�rfelwurf, auch
 * wenn der Computergegnerzug anschlie�end einer langwierigen Berechnung bedarf.
 * 
 * @author jlessner
 *
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(
        propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(
        propertyName = "destination", propertyValue = JMSHelfer.QUEUE_NAME) })
@TransactionManagement(TransactionManagementType.BEAN)
public class ComputerGegner implements MessageListener {
    @EJB EinsteinDAO dao;
    @Resource MessageDrivenContext kontext;
    
    public void onMessage(Message nachricht) {
        try {
            int tischNr = JMSHelfer.leseTischNr(nachricht);
            EinsteinComputerDelegate delegate =
            	new EinsteinComputerDelegate(tischNr, dao);
            
            kontext.getUserTransaction().begin();
            int wuerfelWurf = delegate.wuerfeln();
            kontext.getUserTransaction().commit();
            
            kontext.getUserTransaction().begin();
            delegate.ziehen(wuerfelWurf);
            kontext.getUserTransaction().commit();
        }
        catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

}
