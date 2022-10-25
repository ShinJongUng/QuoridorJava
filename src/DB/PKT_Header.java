package DB;
import java.io.Serializable;

public class PKT_Header implements Serializable {


    public int size;
    public PKT_Header(int size){
        this.size = size;
    }
}