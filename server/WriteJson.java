package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteJson {

    public static String createJson(JsonObject object){
        JsonArray dataJson = new JsonArray();
        dataJson.add(object);
        return dataJson.toString().replace("[","").replace("]","");
    }
}
