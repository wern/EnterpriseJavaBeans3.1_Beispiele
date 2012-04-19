/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/
package abfragen;

import java.util.List;

import javax.naming.InitialContext;

public class AutoClient {
    public static void printList(List list) {
        for (Object elem: list) {
            if (elem.getClass().isArray()) {
                for (Object arrElem: (Object[])elem)
                    System.out.print(arrElem + " ");
                System.out.println();
            }
            else
                System.out.println(elem);
        }
    }

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        Autovermietung vermietung = (Autovermietung) context.lookup("AutovermietungImpl/remote");
        if (args.length > 0) {
            if (args[0].equals("alleFahrzeuge")) {
                printList(vermietung.alleFahrzeuge());
                return;
            }
            else if (args[0].equals("alleFahrzeugeIn") && args.length == 2) {
                printList(vermietung.alleFahrzeugeIn(args[1]));
                return;
            }
            else if (args[0].equals("loescheFahrtenNach") && args.length == 2) {
                System.out.println(vermietung.loescheFahrtenNach(args[1]));
                return;
            }
            else if (args[0].equals("ungenutzteFahrzeuge")) {
                printList(vermietung.ungenutzteFahrzeuge());
                return;
            }
            else if (args[0].equals("stationenMitLeerfahrten")) {
                printList(vermietung.stationenMitLeerfahrten());
                return;
            }
            else if (args[0].equals("anzahlFahrzeugeProStadt")) {
                printList(vermietung.anzahlFahrzeugeProStadt());
                return;
            }
            else if (args[0].equals("kundenIn") && args.length == 2) {
                printList(vermietung.kundenIn(args[1]));
                return;
            }
            else if (args[0].equals("fahrtnrUndFahrername")) {
                printList(vermietung.fahrtnrUndFahrername());
                return;
            }
            else if (args[0].equals("fahrtUndFahrername")) {
                printList(vermietung.fahrtUndFahrername());
                return;
            }
            else if (args[0].equals("baujahrSpanne")) {
                printList(vermietung.bauhjahrSpanne());
                return;
            }
            else if (args[0].equals("mehrfachMieter")) {
                printList(vermietung.mehrfachMieter());
                return;
            }
            else if (args[0].equals("stadtnamenKlein")) {
                System.out.println(vermietung.stadtnamenKlein());
                return;
            }
            else if (args[0].equals("ermittleFahrzeuge")) {
                String stadt = (args.length > 1 && args[1].length() > 0) ? args[1] : null;
                String kennzeichen = (args.length > 2  && args[2].length() > 0) ? args[2] : null;
                String modell = (args.length > 3 && args[3].length() > 0) ? args[3] : null;
                Integer baujahr = (args.length > 4) ? Integer.parseInt(args[4]) : null;
                printList(vermietung.ermittleFahrzeuge(stadt, kennzeichen, modell, baujahr));
                return;
            }
        }
        else {
            vermietung.beispielDatenAnlegen();
            return;
        }
        System.out.println
            ("Aufruf: AutoClient\n" +
             "  <keine Parameter>\n");
    }
}
