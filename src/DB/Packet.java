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
    private State state;
    private int x, y;
    public Packet(State state, int x, int y){
        this.state = state;
        this.x = x;
        this.y = y;
    }
    public State isState(){
        return state;
    }
}