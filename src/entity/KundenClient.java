/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package entity;

import javax.naming.InitialContext;

public class KundenClient {
    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        KundenDAO dao = (KundenDAO) context.lookup("KundenDAOImpl/remote");
        if (args.length > 0) {
            if (args[0].equals("erzeuge") && args.length == 3) {
                int kundenNr = dao.erzeugeKunde(args[1], args[2]);
                System.out.println(kundenNr);
                return;
            }
            else if (args[0].equals("finde") && args.length == 2) {
                Kunde kunde = dao.findeKunde(Integer.parseInt(args[1]));
                if (kunde != null)
                    System.out.println(kunde.getVorname() + " " + kunde.getNachname());
                else
                    System.out.println("Nicht gefunden");
                return;
            }
            else if (args[0].equals("aktualisiere") && args.length == 4) {
                Kunde kunde = dao.aktualisiereKunde(Integer.parseInt(args[1]), args[2], args[3]);
                if (kunde == null)
                    System.out.println("Kein passender Kunde vorhanden");
                return;
            }
        }
        System.out.println
            ("Aufruf: KundenClient\n" +
             "  erzeuge <Vorname> <Nachname>\n" +
             "  finde <ID>\n" +
             "  aktualisiere <ID> <Vorname> <Nachname>");
    }
}
