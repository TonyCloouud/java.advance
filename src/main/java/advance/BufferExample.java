package advance;

import java.nio.ByteBuffer;

/**
 * @autho baifugui
 * @create 2017 08 24 13:49
 */
public class BufferExample {
    static void bufferOper(){
        ByteBuffer bb = ByteBuffer.allocate(10);
        bb.put((byte) 'A');
        bb.put((byte) 'B');
        bb.put((byte) 'C');
        bb.flip();
        System.out.println((char)bb.get());
        System.out.println((char)bb.get());

        bb.compact();
        System.out.println("compact after get:"+new String(bb.array()));

        bb.put((byte)'E');
        bb.put((byte)'D');
        System.out.println("put after get:"+new String(bb.array()));


    }

    static void compareTwoBuffer(){
        byte[] bytes = "hell word!".getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes,0,bytes.length);

        buffer.flip();
        byte[] array = new byte[bytes.length];
        while(buffer.hasRemaining()){
            buffer.get(array,0,buffer.remaining());
        }
        System.out.println(new String(array));
    }


}
