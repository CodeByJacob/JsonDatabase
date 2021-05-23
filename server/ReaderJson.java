package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jakub Wojtaś
 * @version 1.0
 *
 */
public class ReaderJson {
    private String PATH;

    public static List<String> parseStringList(String string) {
        List<String> request = new ArrayList<>();
        JsonObject obj = (JsonObject) JsonParser.parseString(string);

        obj.keySet().forEach(key -> request.add(obj.get(key).toString().replace("\"",""))) ;

        return request;
    }
}
