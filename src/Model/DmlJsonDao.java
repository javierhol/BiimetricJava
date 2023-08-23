package Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import Domain.ConfigDTO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mauricio Herrera
 */
public class DmlJsonDao implements IDmlJsonDao {

    private static final String CONFIG = "src/Config/Config.json";

    @Override
    public boolean createFileJson() throws IOException {
        File file = new File(CONFIG);
        return file.createNewFile();
    }

    @Override
    public List<ConfigDTO> select() {
        ArrayList<ConfigDTO> listaConfig = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(CONFIG));
            if (br.readLine() != null) {
                JsonParser parser = new JsonParser();
                try (FileReader fileRead = new FileReader(CONFIG)) {
                    JsonArray list = (JsonArray) parser.parse(fileRead);
                    for (JsonElement jsonElement : list) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        ConfigDTO cf = new ConfigDTO();
                        cf.setId(jsonObject.get("id").getAsString());
                        cf.setSerial_number_pc(jsonObject.get("serial_number_pc").getAsString());
                        cf.setUrl_api(jsonObject.get("url_api").getAsString());
                        cf.setBrowser(jsonObject.get("browser").getAsString());
                        cf.setStatus(jsonObject.get("status").getAsString());
                        listaConfig.add(cf);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error Clase: DmlJsonDao, Metodo: select" + ex.getMessage());
        }
        return listaConfig;
    }

    @Override
    public int insert(ConfigDTO config) {
        try {
            ArrayList<ConfigDTO> listaConfig = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(CONFIG));
            if (br.readLine() != null) {
                JsonParser parser = new JsonParser();
                try (FileReader fileRead = new FileReader(CONFIG)) {
                    JsonArray list = (JsonArray) parser.parse(fileRead);
                    for (JsonElement jsonElement : list) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        ConfigDTO cf = new ConfigDTO();
                        cf.setId(jsonObject.get("id").getAsString());
                        cf.setSerial_number_pc(jsonObject.get("serial_number_pc").getAsString());
                        cf.setUrl_api(jsonObject.get("url_api").getAsString());
                        cf.setBrowser(jsonObject.get("browser").getAsString());
                        cf.setStatus("Inactive");
                        listaConfig.add(cf);
                    }
                }
            }
            listaConfig.add(config);
            try (FileWriter file = new FileWriter(CONFIG)) {
                file.write(new Gson().toJson(listaConfig));
                file.flush();
            }
            return 1;
        } catch (IOException ex) {
            System.out.println("Error Clase: DmlJsonDao, Metodo: insert" + ex.getMessage());
            return 0;
        }
    }

    @Override
    public int update(ConfigDTO config) {
        try {
            ArrayList<ConfigDTO> listaConfig = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(CONFIG));
            if (br.readLine() != null) {
                JsonParser parser = new JsonParser();
                try (FileReader fileRead = new FileReader(CONFIG)) {
                    JsonArray list = (JsonArray) parser.parse(fileRead);
                    for (JsonElement jsonElement : list) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (!config.getId().equals(jsonObject.get("id").getAsString())) {
                            ConfigDTO cf = new ConfigDTO();
                            cf.setId(jsonObject.get("id").getAsString());
                            cf.setSerial_number_pc(jsonObject.get("serial_number_pc").getAsString());
                            cf.setUrl_api(jsonObject.get("url_api").getAsString());
                            cf.setBrowser(jsonObject.get("browser").getAsString());
                            cf.setStatus("Inactive");
                            listaConfig.add(cf);
                        }
                    }
                }
            }
            listaConfig.add(config);
            try (FileWriter file = new FileWriter(CONFIG)) {
                file.write(new Gson().toJson(listaConfig));
                file.flush();
            }
            return 1;
        } catch (IOException ex) {
            System.out.println("Error Clase: DmlJsonDao, Metodo: update" + ex.getMessage());
            return 0;
        }
    }
//

    @Override
    public int delete(ConfigDTO config) {
        try {
            ArrayList<ConfigDTO> listaConfig = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(CONFIG));
            if (br.readLine() != null) {
                JsonParser parser = new JsonParser();
                try (FileReader fileRead = new FileReader(CONFIG)) {
                    JsonArray list = (JsonArray) parser.parse(fileRead);
                    for (JsonElement jsonElement : list) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (!config.getId().equals(jsonObject.get("id").getAsString())) {
                            ConfigDTO cf = new ConfigDTO();
                            cf.setId(jsonObject.get("id").getAsString());
                            cf.setSerial_number_pc(jsonObject.get("serial_number_pc").getAsString());
                            cf.setUrl_api(jsonObject.get("url_api").getAsString());
                            cf.setBrowser(jsonObject.get("browser").getAsString());
                            cf.setStatus(jsonObject.get("status").getAsString());
                            listaConfig.add(cf);
                        }
                    }
                }
            }
            try (FileWriter file = new FileWriter(CONFIG)) {
                file.write(new Gson().toJson(listaConfig));
                file.flush();
            }
            return 1;
        } catch (IOException ex) {
            System.out.println("Error Clase: DmlJsonDao, Metodo: delete" + ex.getMessage());
            return 0;
        }
    }

    @Override
    public int changeBrowser(ConfigDTO config) {
        try {
            ArrayList<ConfigDTO> listaConfig = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(CONFIG));
            if (br.readLine() != null) {
                JsonParser parser = new JsonParser();
                try (FileReader fileRead = new FileReader(CONFIG)) {
                    JsonArray list = (JsonArray) parser.parse(fileRead);
                    for (JsonElement jsonElement : list) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        ConfigDTO cf = new ConfigDTO();
                        cf.setId(jsonObject.get("id").getAsString());
                        cf.setSerial_number_pc(jsonObject.get("serial_number_pc").getAsString());
                        cf.setUrl_api(jsonObject.get("url_api").getAsString());
                        cf.setBrowser(jsonObject.get("browser").getAsString());
                        if (config.getBrowser().equals(jsonObject.get("browser").getAsString())) {
                            cf.setStatus("Active");
                        } else {
                            cf.setStatus("Inactive");
                        }
                        listaConfig.add(cf);
                    }
                }
            }
            try (FileWriter file = new FileWriter(CONFIG)) {
                file.write(new Gson().toJson(listaConfig));
                file.flush();
            }
            return 1;
        } catch (IOException ex) {
            System.out.println("Error Clase: DmlJsonDao, Metodo: changeBrowser" + ex.getMessage());
            return 0;
        }
    }
//

    @Override
    public ConfigDTO activeNav() {
        ConfigDTO cf = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(CONFIG));
            if (br.readLine() != null) {
                JsonParser parser = new JsonParser();
                try (FileReader fileRead = new FileReader(CONFIG)) {
                    JsonArray list = (JsonArray) parser.parse(fileRead);
                    for (JsonElement jsonElement : list) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (jsonObject.get("status").getAsString().equals("Active")) {
                            cf = new ConfigDTO();
                            cf.setId(jsonObject.get("id").getAsString());
                            cf.setSerial_number_pc(jsonObject.get("serial_number_pc").getAsString());
                            cf.setUrl_api(jsonObject.get("url_api").getAsString());
                            cf.setBrowser(jsonObject.get("browser").getAsString());
                            cf.setStatus(jsonObject.get("status").getAsString());
                        }
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error Clase: DmlJsonDao, Metodo: activeNav" + ex.getMessage());
        }
        return cf;
    }

    @Override
    public List<String> listNav() {
        List<String> navList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(CONFIG));
            if (br.readLine() != null) {
                JsonParser parser = new JsonParser();
                try (FileReader fileRead = new FileReader(CONFIG)) {
                    JsonArray list = (JsonArray) parser.parse(fileRead);
                    for (JsonElement jsonElement : list) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        navList.add(jsonObject.get("browser").getAsString());
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error Clase: DmlJsonDao, Metodo: listNav " + ex.getMessage());
        }
        return navList;
    }

}
