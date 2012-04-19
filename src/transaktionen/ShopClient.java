/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package transaktionen;

import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

public class ShopClient {
    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        Shop shop = (Shop) context.lookup("ShopImpl/remote");
        if (args.length > 0) {
            if (args[0].equals("einzelBestellung") && args.length == 4) {
                System.out.println(shop.bestellen(args[2], Integer.parseInt(args[3]), args[1]));
                return;
            }
            else if (args[0].equals("sammelBestellung") && args.length > 2) {
                Warenkorb korb = (Warenkorb) context.lookup("Warenkorb/remote");
                for (int i = 2; i < args.length; i++)
                    korb.legeInWarenkorb(args[i]);
                try { System.out.println(korb.bestellen(args[1])); }
                catch(Throwable t) {
                    System.out.println("Bestellung fehlgeschlagen: " + t.getMessage());
                }
                System.out.println("Anzahl Produkte im Korb: " + korb.anzahlProdukte());
                return;
            }
            else if (args[0].equals("clientBestellung") && args.length > 3) {
                UserTransaction tx = (UserTransaction)context.lookup("UserTransaction");
                try {
                    tx.begin();
                    for (int i = 1; i+2 < args.length; i += 3)
                        shop.bestellen(args[i+1], Integer.parseInt(args[i+2]), args[i]);
                    tx.commit();
                }
                catch(Throwable t) {
                    System.out.println("Bestellung fehlgeschlagen: " + t.getMessage());
                    tx.rollback();
                }
                return;
            }
            
        }
        else {
            shop.beispielDatenAnlegen();
            return;
        }
        System.out.println
            ("Aufruf: ShopClient\n" +
             "  <keine Parameter>: Erzeugung von Beispieldaten\n" +
             "  einzelBestellung <Adresse> <Artikel> <Anzahl>\n" +
             "  sammelBestellung <Adresse> <Artikel 1> ... <Artikel N>\n"+
             "  clientBestellung <Adresse 1> <Artikel 1> <Anzahl 1> ...");
    }
}
