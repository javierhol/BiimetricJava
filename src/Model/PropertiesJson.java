package Model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.io.IOException;

/**
 *
 * @author Mauricio Herrera
 */
public final class PropertiesJson {

    private static final String PROPERTY_FILE = "src/Config/properties.json";

    public PropertiesJson() {
        try {
            File archivo = new File(PROPERTY_FILE);
            if (!archivo.exists()) {
                createJsonFile();
                HashMap obj = new HashMap();
                obj.put("auto_sync", false);
                insert(obj);
            }
        } catch (Exception e) {
            System.out.println("Error clase PropertiesJson, metodo: PropertiesJson " + e.getMessage());
        }
    }

    public boolean createJsonFile() {
        boolean result = false;
        try {
            File file = new File(PROPERTY_FILE);
            result = file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error clase PropertiesJson, metodo: createJsonFile " + e.getMessage());
        }
        return result;
    }

    public int insert(HashMap obj) {
        try {
            try (FileWriter file = new FileWriter(PROPERTY_FILE)) {
                file.write(new Gson().toJson(obj));
                file.flush();
            }
            return 1;
        } catch (IOException e) {
            System.out.println("Error clase PropertiesJson, metodo: insert " + e.getMessage());
            return 0;
        }
    }

    public boolean destroy() {
        File file = new File(PROPERTY_FILE);
        return file.delete();
    }

    public HashMap<String, Boolean> select() {
        HashMap<String, Boolean> poperty = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(PROPERTY_FILE));
            if (br.readLine() != null) {
                JsonParser parser = new JsonParser();
                try (FileReader fileRead = new FileReader(PROPERTY_FILE)) {
                    JsonElement element = (JsonElement) parser.parse(fileRead);
                    poperty = new HashMap<>();
                    JsonObject jsonObject = element.getAsJsonObject();
                    poperty.put("auto_sync", jsonObject.get("auto_sync").getAsBoolean());

                }
            }
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            System.out.println("Error clase PropertiesJson, metodo: select " + e.getMessage());
        }
        return poperty;
    }

}
