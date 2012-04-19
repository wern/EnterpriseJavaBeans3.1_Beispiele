/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.suchen;

import java.math.BigDecimal;
import java.util.List;

import javax.naming.InitialContext;

public class ArtikelClient {
    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        Katalog katalog = (Katalog) context.lookup("artikelkatalog/remote");

        katalog.aufnehmen(new Artikel("Buecherregal", new BigDecimal("139.89"), 30));
        katalog.aufnehmen(new Artikel("Haengeschrank", new BigDecimal("89.90"), 37));
        katalog.aufnehmen(new Artikel("Aschenbecher", new BigDecimal("5.99"), 142));
        katalog.aufnehmen(new Artikel("Stehlampe", new BigDecimal("78.00"), 45));

        Suchkriterien kriterien = new Suchkriterien();
        kriterien.preisuntergrenze = new BigDecimal("10.00");
        kriterien.preisobergrenze = new BigDecimal("100.00");
        System.out.println(katalog.suchen(kriterien));

        kriterien = new Suchkriterien();
        kriterien.artikelnr = 1;
        System.out.println(katalog.suchen(kriterien));

        kriterien = new Suchkriterien();
        kriterien.teilDerBeschreibung = "Lampe";
        List<Artikeltreffer> treffer = katalog.suchen(kriterien);
        System.out.println(treffer);

        System.out.println(katalog.details(treffer.get(0)));
    }
}
