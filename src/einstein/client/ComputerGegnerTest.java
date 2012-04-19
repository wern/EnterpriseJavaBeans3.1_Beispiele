package einstein.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import einstein.server.intern.SpielBrettHelfer;
import einstein.server.intern.SteineAufsteller;
import einstein.server.intern.computergegner.MonteCarlo;
import einstein.server.intern.entities.SteinEntity;
import einstein.server.intern.entities.TischEntity;
import einstein.server.oeffentlich.Position;
import einstein.server.oeffentlich.Stein;
import einstein.server.oeffentlich.Zug;

public class ComputerGegnerTest {

    public static void druckeStellung(List<Stein> stellung) {
        for (Stein stein : stellung)
            System.out.print(stein + " ");
        System.out.println("");
    }

    public static void main(String[] args) throws Exception {
        TischEntity tisch = new TischEntity(1, true);
        List<SteinEntity> lse = new SteineAufsteller(tisch).neueAufstellung();
        List<Stein> ls = SteinEntity.kopieren(lse);
        for (int i = 0; i < ls.size(); i++)
            ls.get(i).id = ls.get(i).spielerA ? ls.get(i).nr : ls.get(i).nr+6;
        MonteCarlo gegner = new MonteCarlo();
        druckeStellung(ls);
        while(true) {
            System.out.print("" + SpielBrettHelfer.zufallsZahl16() + ">");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String zugString = in.readLine();
            StringTokenizer tokenizer = new StringTokenizer(zugString);
            int steinId = Integer.parseInt(tokenizer.nextToken());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            Zug zug = new Zug(steinId, new Position(x, y));
            if (gegner.ziehen(ls, zug)) {
                System.out.println("Du hast gewonnen!");
                break;
            }
            int wuerfelwurf = SpielBrettHelfer.zufallsZahl16();
            System.out.println(wuerfelwurf);
            zug = gegner.naechsterZug(ls, false, wuerfelwurf);
            System.out.println("Computer: " + zug);
            if (gegner.ziehen(ls, zug)) {
                System.out.println("Du hast verloren!");
                break;
            }
        }
    }
}
