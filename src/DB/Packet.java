package DB;
import java.io.Serializable;

public class Packet implements Serializable{
    private static final long serialVersionUID = 1L;
    public enum State{
        Start,
        Move,
        Vertical_Wall,
        Horizontal_Wall
    }
    private int x, y;
    private State state;
    private boolean turn = false;
    private int id;
    public Packet(int id, int x, int y, Packet.State state, boolean turn){
        this.id = id;
        this.x = x;
        this.y = y;
        this.state = state;
        this.turn = turn;
    }
    public int getX(){ return this.x; }
    public void setX(int x){ this.x = x; }
    public int getY(){ return this.y; }
    public void setY(int y){ this.y = y; }
    public int getId(){ return id; }
    public State getState(){ return state; }
    public boolean isTurn() {
        return turn;
    }
}