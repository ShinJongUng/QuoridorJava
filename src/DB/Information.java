package DB;

public class Information{
    private int myId; // 클라 or 호스트 확인
    private boolean myTurn = false; // 턴 확인
    public void setX(int x) {
        this.x = x;
    }

    private int x = 0;  // 말 X 좌표

    public void setY(int y) {
        this.y = y;
    }

    private int y = 0;  // 말 Y 좌표
    public boolean isTurn() { return myTurn; }
    public void changeTurn() { myTurn = myTurn ? false : true;}
    public int getX(){ return x; }
    public int getY(){ return y; }

    public void setId(int id) {
        myId = id;
    }
}