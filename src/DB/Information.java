package DB;

public class Information{
    private boolean idCheck = true;
    private boolean myTurn = false;
    String id;
    public boolean isID(){ return idCheck; }
    public boolean isTurn() { return myTurn; }
    public void changeTurn() { myTurn = myTurn ? false : true;}

    void setID(String id){
        this.id = id;
        idCheck = false;
    }
}