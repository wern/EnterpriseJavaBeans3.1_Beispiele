package einstein.client;

import java.util.List;

import javax.naming.InitialContext;

import einstein.server.intern.computergegner.MonteCarlo;
import einstein.server.oeffentlich.EinsteinPortal;
import einstein.server.oeffentlich.EinsteinSitzung;
import einstein.server.oeffentlich.Partie;
import einstein.server.oeffentlich.Position;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStand;
import einstein.server.oeffentlich.SpielStatus;
import einstein.server.oeffentlich.SpielStatusException;
import einstein.server.oeffentlich.Spieler;
import einstein.server.oeffentlich.Stein;
import einstein.server.oeffentlich.VerwaltungsException;
import einstein.server.oeffentlich.Zug;

public class EinsteinTest {
    
    public static void spielerAnzeigen(List<Spieler> l) {
        System.out.print("Spieler: ");
        for (Spieler s: l) {
            System.out.print(s + " ");
        }
        System.out.println("");
    }

    public static void computerPartieSpielen(EinsteinSitzung jan)
        throws Exception {
        System.out.println("Jetzt eine Partie gegen den Computer...");
        jan.bereit();
        MonteCarlo berater = new MonteCarlo();
        SpielStand stand;
        while(true) {
            do {
                Thread.sleep(100);
                stand = jan.standAbfragen();
                System.out.print(stand.getStatus() == SpielStatus.B_WUERFELT ? "." : ":");
                System.out.flush();
            } while (stand.getStatus().spielLaeuft() &&
                     stand.getStatus() != SpielStatus.A_WUERFELT);
            System.out.println(stand.getLetzterZug());
            if (!stand.getStatus().spielLaeuft()) {
                System.out.println(stand.getStatus());
                break;
            }
            Partie partie = jan.partieAbfragen();
            int wuerfelWurf = jan.wuerfeln();
            Zug zug = berater.naechsterZug(partie.getAufstellung(), true, wuerfelWurf);
            System.out.println(zug);
            jan.ziehen(zug);
        }
    }
    
    public static void partieSpielen(EinsteinSitzung jan, EinsteinSitzung werner)
        throws Exception {
        System.out.println("Jetzt eine Partie...");
        jan.bereit();
        werner.bereit();
        MonteCarlo berater = new MonteCarlo();
        while(true) {
            Partie partie = jan.partieAbfragen();
            if (!partie.getStatus().spielLaeuft())
                break;
            int wuerfelWurf = jan.wuerfeln();
            Zug zug = berater.naechsterZug(partie.getAufstellung(), true, wuerfelWurf);
            System.out.println(zug);
            jan.ziehen(zug);
            
            partie = werner.partieAbfragen();
            if (!partie.getStatus().spielLaeuft())
                break;
            wuerfelWurf = werner.wuerfeln();
            zug = berater.naechsterZug(partie.getAufstellung(), false, wuerfelWurf);
            System.out.println(zug);
            werner.ziehen(zug);
        }
        
    }

    public static void eigenenTischVerlassen() throws Exception {
        InitialContext context = new InitialContext();
        EinsteinSitzung jan = (EinsteinSitzung)context.lookup("EinsteinSitzungImpl/remote");
        jan.anmelden("jan");
        int tischnr = jan.eroeffneTisch(false);
        jan.verlassen();
        jan.einsteigen(tischnr);
    }
    
    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        try {
        	Partie partie;
            EinsteinPortal portal = (EinsteinPortal)context.lookup("EinsteinPortalImpl/remote");
            try { portal.registrieren("jan", "lessner"); }
            catch(VerwaltungsException vx) {}
            try { portal.registrieren("werner", "eberling"); }
            catch(VerwaltungsException vx) {}
            try { portal.registrieren("jessica", "rubart"); }
            catch(VerwaltungsException vx) {}

            //eigenenTischVerlassen();
            
            spielerAnzeigen(portal.anwesendeSpieler());
        	
            EinsteinSitzung jan = (EinsteinSitzung)context.lookup("EinsteinSitzungImpl/remote");
            jan.anmelden("jan");

            spielerAnzeigen(portal.anwesendeSpieler());
            
            int tischnr = jan.eroeffneTisch(false);
            System.out.println(tischnr);
            System.out.println(jan.partieAbfragen());
            jan.bereit();
            System.out.println(jan.partieAbfragen());

            spielerAnzeigen(portal.anwesendeSpieler());
            
            EinsteinSitzung werner = (EinsteinSitzung)context.lookup("EinsteinSitzungImpl/remote");
            werner.anmelden("werner");

            spielerAnzeigen(portal.anwesendeSpieler());

            werner.einsteigen(tischnr);
            System.out.println(werner.partieAbfragen());
            werner.bereit();
            System.out.println(werner.partieAbfragen());

            spielerAnzeigen(portal.anwesendeSpieler());

            /*
            System.out.println(jan.standAbfragen());
            int wurf = jan.wuerfeln();
            System.out.println(wurf);
            partie = jan.partieAbfragen();
            System.out.println(partie);
            System.out.println("-------------------------");
            for (Stein stein : partie.getAufstellung()) {
            	if (stein.spielerA && stein.nr == wurf) {
            		jan.ziehen(new Zug(stein.id,
                               new Position(stein.position.x+1, stein.position.y-2)));
                    break;
            	}
            }
            if (true)
                return;
            System.out.println(jan.partieAbfragen());
            System.out.println(werner.standAbfragen());
            werner.wuerfeln();
            System.out.println(werner.standAbfragen());
            */
            jan.abmelden();
            
            System.out.println(werner.standAbfragen());
            werner.bereit();
            System.out.println(werner.partieAbfragen());
            
            spielerAnzeigen(portal.anwesendeSpieler());
            
            // Im Moment *MUSS* man sich abmelden, weil die Session sonst nicht
            // abgebrochen wird. Wie setzt man einen Timeout? 3 Sekunden würden
            // reichen, da ein aktiver Client ja permanent pollt.
            werner.abmelden();
            
            spielerAnzeigen(portal.anwesendeSpieler());

            /************** Jetzt ein paar Partien **************/
            jan = (EinsteinSitzung)context.lookup("EinsteinSitzungImpl/remote");
            jan.anmelden("jan");
            tischnr = jan.eroeffneTisch(false);
            werner = (EinsteinSitzung)context.lookup("EinsteinSitzungImpl/remote");
            werner.anmelden("werner");
            werner.einsteigen(tischnr);
            for (int i = 0; i < 2; i++) {
                partieSpielen(jan, werner);
            }
            jan.abmelden();
            werner.abmelden();

            /************** Und jetzt gegen den Computer ************/
            jan = (EinsteinSitzung)context.lookup("EinsteinSitzungImpl/remote");
            jan.anmelden("jan");
            jan.eroeffneTisch(true);
            try {
                computerPartieSpielen(jan);
            }
            catch(Exception x) {
                jan.abmelden();
                throw x;
            }
            
            jan.abmelden();
        }
        catch(SpielException sx) {
            sx.printStackTrace();
        }
        catch(SpielStatusException ssx) {
            ssx.printStackTrace();
        }
    }

}
