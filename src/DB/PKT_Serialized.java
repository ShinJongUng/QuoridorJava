package DB;

import java.io.*;
import java.nio.ByteBuffer;

public class PKT_Serialized {
    int headerSize = 0;
    public PKT_Serialized(){
        PKT_Header header = new PKT_Header(10);
        byte[] head = PKT_Serialized.toByteArray(header);
        headerSize = head.length;
    }
    public void Serialized(Object obj, ByteBuffer inputBuffer){
        byte[] buf = toByteArray(obj);
        PKT_Header header = new PKT_Header(buf.length);
        byte[] head = toByteArray(header);
        inputBuffer.put(head);
        inputBuffer.put(buf);
    }
    public <T> T DeSerialized(Class<T> type, ByteBuffer inputBuffer){ //역역직렬화코드 PKT_Header + Object Data
        try {
            byte[] byte_Header = new byte[headerSize];
            inputBuffer.get(byte_Header, 0, headerSize);
            PKT_Header header = toObject(byte_Header, PKT_Header.class);
            System.out.println("입력받은 데이터 : " + header.size);

            byte[] bytes = new byte[header.size];
            inputBuffer.get(bytes, 0, header.size);
            return toObject(bytes, type);
        }
        catch(Exception e){
            System.out.println("불가능합니다.");
            return null;
        }
    }
    static byte[] toByteArray (Object obj)
    {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            bos.close();
            bytes = bos.toByteArray();
        }
        catch (IOException ex) {
            System.out.println("변경 실패0");
        }
        return bytes;
    }
    public <T> T toObject (byte[] bytes, Class<T> type)
    {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
        }
        catch (IOException ex) {
            System.out.println("변경 실패1");
        }
        catch (ClassNotFoundException ex) {
            System.out.println("변경 실패2");
        }
        return type.cast(obj);
    }
}
