package einstein.server.intern.computergegner;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import einstein.server.oeffentlich.SpielException;

/**
 * Hilfsklasse zum Senden und Emfangen von Aufforderungen an Computergegner,
 * den nächsten Zug zu machen. Die Benachrichtigungen erfolgen in Form von
 * MapMasseges, die als einziges Attribut die Nummer des Spieltischs enthalten,
 * an dem ein Computerzug ansteht.
 * 
 * @author less02
 *
 */
public class JMSHelfer {
    private static final String TISCHNR_ATTRIBUT = "tischNr";
    static final String QUEUE_NAME = "queue/Praxisbuch";

    public static int leseTischNr(Message nachricht) throws SpielException {
        try {
            MapMessage map = (MapMessage) nachricht;
            return map.getInt(TISCHNR_ATTRIBUT);
        }
        catch(JMSException jmsx) {
            throw new SpielException(jmsx);
        }
    }

    public static void computergegnerAuffordern(int tischNr) throws SpielException {
        try {
            InitialContext initialContext = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) initialContext
                    .lookup("ConnectionFactory");
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
    
            MapMessage nachricht = session.createMapMessage();
            nachricht.setInt(TISCHNR_ATTRIBUT, tischNr);
    
            Destination ziel = (Destination) initialContext.lookup(QUEUE_NAME);
            MessageProducer sender = session.createProducer(ziel);
            sender.send(nachricht);
            session.close();
            connection.close();
        }
        catch(NamingException nx) {
            throw new SpielException(nx);
        }
        catch(JMSException jmsx) {
            throw new SpielException(jmsx);
        }
        
    }
    
}
