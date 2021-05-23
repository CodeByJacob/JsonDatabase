package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Command arguments = new Command();
        Gson gson = new Gson();
        try {
            JCommander.newBuilder()
                    .addObject(arguments)
                    .build()
                    .parse(args);

            Client cl = new Client(63000,"127.0.0.1");

            if(arguments.isFile()){
               File file = new File("src\\client\\data\\" + arguments.getFilePath());
               FileReader reader = new FileReader(file);
               cl.setMsg(gson.toJson(JsonParser.parseReader(reader)));
            }
            else {
                cl.setMsg(gson.toJson(arguments));
            }
            cl.start();

        } catch (IOException eIO){
            eIO.printStackTrace();
        }
        catch (ParameterException e) {
            System.out.println("Wrong arguments\nPlease, try again");
        }

    }
}