package server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Jakub Wojtaś
 * @version 1.0;
 *
 */
public class Server {
    private final int SERVER_PORT;
    public boolean toClose;
    Database database;

    public Server() {
        SERVER_PORT = 12345;
        this.database = new Database();
        toClose = false;
    }

    public Server(int _SERVER_PORT) throws IOException{
        if (_SERVER_PORT > 0 && _SERVER_PORT < 65355) {
            this.SERVER_PORT = _SERVER_PORT;
            this.database = new Database();
            this.toClose = false;
        }
        else {
            throw new IOException("Invalid data on input");
        }
    }

    public void main() {
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            System.out.println("Server started!");
            while (!toClose) {
                Session session = new Session(serverSocket.accept(),this);
                session.start();
                session.join();
            }
        }
        catch (InterruptedException eI) {
            eI.printStackTrace();
        }
        catch (IOException eIO){
            eIO.printStackTrace();
        }
    }

    public Database getDatabase() {
        return database;
    }
}
