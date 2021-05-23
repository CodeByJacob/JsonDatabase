package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Database {
    final private Map<String,JsonElement> data;

    public Database() {
        this.data = new LinkedHashMap<>();
    }

    public Map<String, JsonElement> getDatabase() {
        return data;
    }

    private JsonObject setValue(JsonObject request) {

        JsonElement key = request.get("key");
        if(key.isJsonArray()){
            key = key.getAsJsonArray();
        }
        JsonElement value = request.get("value");
        if (value.isJsonObject()){
            value = value.getAsJsonObject();
        }


        if(!key.isJsonArray()){
            data.put(key.getAsString(),value);
        }
        else {
            JsonArray keys = key.getAsJsonArray();
            int sizeOfKeys = keys.size();
            JsonElement current = data.get(keys.get(0).getAsString());
            for(int i = 1;i < sizeOfKeys; i++){
                JsonObject previous = current.getAsJsonObject();
                current = current.getAsJsonObject().get(keys.get(i).getAsString());
                if (i == sizeOfKeys -1) {
                    previous.add(keys.get(i).getAsString(),value);
                }
                else {
                    if(current.isJsonPrimitive()){
                        previous.add(current.getAsString(),new JsonObject());
                    }
                    else if(current.isJsonObject()){
                        current = current.getAsJsonObject();
                    }
                }

            }
        }
        JsonObject responseMessage = new JsonObject();
        responseMessage.addProperty("response","OK");
        return responseMessage;
    }

    private JsonObject getValue(JsonObject request){
        Gson gson = new Gson();
        JsonObject responseMessage = new JsonObject();
        JsonElement key = request.get("key");
        System.out.println(key);
        if(key.isJsonArray()){
            key = key.getAsJsonArray();
        }

        if (!key.isJsonArray()){
           if (data.containsKey(key)){
               responseMessage.addProperty("response","OK");
               responseMessage.addProperty("value",data.get(key).getAsString());
               return responseMessage;
           }
            responseMessage.addProperty("response","ERROR");
            responseMessage.addProperty("reason","No such key");
            return responseMessage;
        }
        else{
            JsonElement currentElement;
            if (key.getAsJsonArray().size() == 1){
                currentElement = data.get(key.getAsJsonArray().get(0).getAsString());
                responseMessage.addProperty("response","OK");
                responseMessage.add("value",currentElement);
                return responseMessage;
            }
            else {
                JsonElement currentObject = data.get(key.getAsJsonArray().get(0).getAsString());
                for (int i=1;i<key.getAsJsonArray().size();i++){
                    currentObject = currentObject.getAsJsonObject().get(key.getAsJsonArray().get(i).getAsString());
                }
                currentElement = currentObject;
            }
            responseMessage.addProperty("response","OK");
            responseMessage.addProperty("value",currentElement.getAsString());
        }

        return responseMessage;
    }

    private JsonObject deleteValue(JsonObject request) {
        Gson gson = new Gson();
        JsonObject responseMessage = new JsonObject();
        JsonElement key = request.get("key");
        System.out.println(key);
        if(key.isJsonArray()){
            key = key.getAsJsonArray();
        }

        if (!key.isJsonArray()){
            if (data.containsKey(key)){
                responseMessage.addProperty("response","OK");
                data.remove(key);
                return responseMessage;
            }
            responseMessage.addProperty("response","ERROR");
            responseMessage.addProperty("reason","No such key");
            return responseMessage;
        }
        else{
            JsonElement currentElement;
            if (key.getAsJsonArray().size() == 1){
                data.remove(key.getAsJsonArray().get(0).getAsString());
                responseMessage.addProperty("response","OK");
                return responseMessage;
            }
            else {
                JsonElement currentObject = data.get(key.getAsJsonArray().get(0).getAsString());
                for (int i=1;i<key.getAsJsonArray().size() - 1;i++){
                    currentObject = currentObject.getAsJsonObject().get(key.getAsJsonArray().get(i).getAsString());
                }
                currentElement = currentObject;
                currentElement.getAsJsonObject().remove(key.getAsJsonArray().get(key.getAsJsonArray().size() -1).getAsString());
            }
            responseMessage.addProperty("response","OK");
        }

        return responseMessage;
//        JsonObject object = new JsonObject();
//        try {
//            if (data.containsKey(key)){
//                data.remove(key);
//                object.addProperty("response","OK");
//            }
//            else {
//                object.addProperty("response","ERROR");
//                object.addProperty("reason","No such key");
//            }
//        }
//        catch (IndexOutOfBoundsException eIOOBE){
//            object.addProperty("response","ERROR");
//        }
//        return object;

    }

    public JsonObject requestHandle(JsonObject request){
        String command = request.get("type").getAsString();
        request.remove("type");
        switch (command) {
            case ("set") :
                return setValue(request);
            case ("get"):
                return getValue(request);
            case("delete"):
                return deleteValue(request);
            default:
                JsonObject object = new JsonObject();
                object.addProperty("response","ERROR");
                return object;
        }
    }

    @Override
    public String toString() {
        return "Database {" +
                "data: " + data +
                '}';
    }
}

