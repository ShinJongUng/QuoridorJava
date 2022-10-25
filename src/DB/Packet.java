package DB;
import java.io.Serializable;

public class Packet implements Serializable{
    private static final long serialVersionUID = 1L;
    public enum State{
        Start,
        H_Move,
        H_Wall,
        C_Move,
        C_Wall,
    }
    private int x, y;
    private State state;
    private int id;
    public Packet(int id, int x, int y, Packet.State state){
        this.id = id;
        this.x = x;
        this.y = y;
        this.state = state;
    }
    public int getX(){ return this.x; }
    public int getY(){ return this.y; }
    public int getId(){ return id; }
    public State getState(){ return state; }

}