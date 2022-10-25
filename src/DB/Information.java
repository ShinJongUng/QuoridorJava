package DB;

public class Information{
    public int getMyId() {
        return myId;
    }
    public void setId(int id) {
        myId = id;
    }
    private int myId = -1; // 클라 or 호스트 확인
    private int x = 0;  // 말 X 좌표
    private int y = 0;  // 말 Y 좌표
    private boolean myTurn = false; // 턴 확인
    public void changeTurn() { myTurn = myTurn ? false : true;}

    public boolean isTurn() { return myTurn; }
    public void setX(int x) { this.x = x; }
    public int getX(){ return x; }
    public void setY(int y) { this.y = y; }
    public int getY(){ return y; }


}