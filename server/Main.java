package server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            Server sr = new Server(63000);
            sr.main();
        }
       catch(IOException eIO){
            eIO.printStackTrace();
       }
    }

}
