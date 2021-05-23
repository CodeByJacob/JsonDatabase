package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 *  @author Jakub Wojtaś
 * @version 1.0
 *
 */
public class Client extends Thread{
    private static int SERVER_PORT;
    private static String SERVER_ADDRESS;
    private String msg;

    /**
     * DEFAULT CONSTRUCTOR FOR SERVER ON LOCALHOST
     */
    public Client() {
        SERVER_PORT = 12345;
        SERVER_ADDRESS = "127.0.0.1";
        this.msg = "";
    }

    public Client(int _SERVER_PORT,String _SERVER_ADDRESS) throws IOException {
        if ((_SERVER_PORT > 0 && _SERVER_PORT < 65355) && (_SERVER_ADDRESS.matches("([0-9]{1,3}.){3}[0-9]{1,3}"))) {
            this.SERVER_PORT = _SERVER_PORT;
            this.SERVER_ADDRESS = _SERVER_ADDRESS;
            this.msg = "";
        }
        else {
            throw new IOException("Invalid data on input");
        }
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public synchronized void start() {
        try (
                Socket socket = new Socket(SERVER_ADDRESS,SERVER_PORT);
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            outputStream.writeUTF(this.msg);
            System.out.println("Sent: " + msg);
            System.out.println("Received: " + inputStream.readUTF());

            //TODO JSON ( wysylanie  i odbieranie )
        }
        catch (IOException eIO){
            eIO.printStackTrace();
        }
    }
}
