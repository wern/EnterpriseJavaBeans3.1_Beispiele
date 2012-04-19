/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package ableitung;

import javax.naming.InitialContext;

public class AudioClient {
    
    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        Audiothek audiothek = (Audiothek)context.lookup("AudiothekImpl/remote");
        if (args.length > 0) {
            if (args[0].equals("compactdisc") && args.length == 4) {
                CompactDisc cd = new CompactDisc(args[1],
                        Integer.parseInt(args[2]),
                        Integer.parseInt(args[3]));
                TonTraeger ergebnis = audiothek.anlegen(cd);
                System.out.println(ergebnis.getNr());
                return;
            }
            if (args[0].equals("langspielplatte") && args.length == 6) {
                LangspielPlatte lp = new LangspielPlatte(args[1],
                        Integer.parseInt(args[2]),
                        Integer.parseInt(args[3]),
                        Integer.parseInt(args[4]),
                        Integer.parseInt(args[5]));
                TonTraeger ergebnis = audiothek.anlegen(lp);
                System.out.println(ergebnis.getNr());
                return;
            }
            if (args[0].equals("single") && args.length == 4) {
                Single single = new Single(args[1],
                        Integer.parseInt(args[2]),
                        Integer.parseInt(args[3]));
                TonTraeger ergebnis = audiothek.anlegen(single);
                System.out.println(ergebnis.getNr());
                return;
            }
            else if (args[0].equals("finde") && args.length == 2) {
                int nummer = Integer.parseInt(args[1]);
                TonTraeger tonTraeger = audiothek.finde(nummer);
                if (tonTraeger != null) {
                    System.out.println(tonTraeger.getTitel() + ", " +
                            tonTraeger.getSpieldauer() + " Minuten, " +
                            tonTraeger.getAnzahlTracks() + " Songs, " +
                            tonTraeger.getClass().getSimpleName());
                }
                else
                    System.out.println("Nicht gefunden");
                return;
            }
        }
        System.out.println
            ("Aufruf: AudioClient\n" +
             "  compactdisc <Titel> <Spieldauer> <Anzahl Songs>\n" +
             "  langspielplatte <Titel> <Spieldauer A-Seite> <Spieldauer B-Seite> <Anzahl Songs A-Seite> <Anzahl Songs B-Seite>\n" +
             "  single <Titel> <Spieldauer A-Seite> <Spieldauer B-Seite>\n" +
             "  finde <Nummer>\n");
    }
}
