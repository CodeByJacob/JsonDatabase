package client;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Command {

    @Parameter(names = {"-in"})
    private String file;

    @Parameter(names = {"--type", "-t"}, description = "command type")
    private String type;

    @Parameter(names = {"--key", "-k"}, description = "data key")
    private String key;

    @Parameter(names = {"--value","-v"}, description = "value inserting to database")
    private String value;

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getFilePath() {
        return file;
    }

    public boolean isFile(){
        if(file == null){
            return false;
        }
        return true;
    }

    public String toJson(){
        return "{ " +
                (!(type == null) ? "\"type\": \"" + type + "\"" : "") +
                (!(key == null) ? ", \"key\": \"" + key + '\"' : "") +
                (!(value == null) ? ", \"value\": \"" + value + '\"' : "") +
                " }";
    }
}
