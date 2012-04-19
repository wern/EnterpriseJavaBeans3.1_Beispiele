package einstein.server.oeffentlich;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable public class Position implements Serializable {
	public int x, y;
	
	public Position() {}
	
	public Position(int[] pos) {
		this(pos[0], pos[1]);
	}
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void spiegeln() {
		int xalt = x;
		x = y;
		y = xalt;
	}
    
    public boolean imSpielfeld() {
        return x > 0 && x < 6 && y > 0 && y < 6;
    }
    
    public boolean gleich(int x, int y) {
        return (this.x == x && this.y == y);
    }
    
    public boolean erreichbar(Position startpunkt, boolean spielerA) {
        if (startpunkt.x == x && startpunkt.y == y)
            return false;
        if (spielerA)
            return ((startpunkt.x == x || startpunkt.x == x-1) &&
                    (startpunkt.y == y || startpunkt.y == y+1));
        else
            return ((startpunkt.x == x || startpunkt.x == x+1) &&
                    (startpunkt.y == y || startpunkt.y == y-1));
    }
    
    public String toString() { return x + "," + y; }
}
