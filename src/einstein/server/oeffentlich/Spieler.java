package einstein.server.oeffentlich;

import java.io.Serializable;

/** Leichtgewichtige Repräsentation eines SpielerEntity, das 
 * zur Weitergabe an einen Client geeignet ist.
 * 
 * @author jlessner
 */
public class Spieler implements Serializable {
    public String name;
    public int partienGespielt;
    public int partienGewonnen;
    public Integer zuschauerAn;
    public Integer spielerAn;
    public boolean anwesend;
    
    public String toString() {
        return name + "/" + partienGespielt + "/" + partienGewonnen + "/" +
            (anwesend ? "da" : "weg") +
            ((zuschauerAn != null) ? "/schaut(" + zuschauerAn + ")" : "") +
            ((spielerAn != null) ? "/spielt(" + spielerAn + ")" : "");
    }
}
