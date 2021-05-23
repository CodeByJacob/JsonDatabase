package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Session extends Thread {
    private final Socket socket;
    final private Server server;

    public Session(Socket _socket,Server server){
        this.socket = _socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (
                DataInputStream inputStream = new DataInputStream(this.socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(this.socket.getOutputStream())
        ) {
            Gson gson = new Gson();

            String msg = inputStream.readUTF();
            System.out.println("Received: " + msg);
            JsonObject request = gson.fromJson(msg,JsonObject.class);

            String type = request.get("type").getAsString();
            if(type.equals("exit")){
                JsonObject object = new JsonObject();
                object.addProperty("response","OK");
                String msge = gson.toJson(object);
                System.out.println("Sent: " + msge);
                server.toClose = true;
                outputStream.writeUTF(msge);
                socket.close();
            }
            else {
                String ans = gson.toJson(server.getDatabase().requestHandle(request));
                System.out.println("server Sent: "+ ans);
                outputStream.writeUTF(ans);
            }
        }
        catch ( IOException eIO){
            eIO.printStackTrace();
        }

    }
}
