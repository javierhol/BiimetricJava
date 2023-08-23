package Http_Api;

import java.io.IOException;
import Domain.ConfigDTO;
import Model.DmlJsonDao;
import Model.IDmlJsonDao;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import Domain.TempFingerprintDTO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;

/**
 *
 * @author Mauricio Herrera
 */
public final class FingerprintUserHttpApi {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36";
    private static String SERVER_PATH;
    private static String SERIAL_NUMBER_PC;
    public static String BASIC_AUTH = "testapi:123456";

    public static void getConfig() {
        if (SERVER_PATH == null) {
            IDmlJsonDao dml = new DmlJsonDao();
            ConfigDTO config = dml.activeNav();
            SERVER_PATH = config.getUrl_api();
            SERIAL_NUMBER_PC = config.getSerial_number_pc();
        }
    }

    public static boolean postSaveFinger(TempFingerprintDTO tfdto) {
        boolean response = false;
        try {
            String encodeAuth = "Basic " + Base64.getEncoder().encodeToString(BASIC_AUTH.getBytes());
            URL obj = new URL(SERVER_PATH + "save_finger");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Acept", "*/*");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", encodeAuth);
            tfdto.setSerial_number_pc(SERIAL_NUMBER_PC);
            try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
                out.write(new Gson().toJson(tfdto));
            }

            int responseCode = con.getResponseCode();
            StringBuilder result;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            result = new StringBuilder();
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            con.disconnect();
            if (responseCode == 200 && !result.toString().equals("")) {
                response = true;
            }
        } catch (MalformedURLException ex) {
            System.out.println("Error clase: FingerprintUserHttpApi aqui, metodo: postSaveFinger " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error clase: FingerprintUserHttpApi, metodo: postSaveFinger " + ex.getMessage());
        }
        return response;
    }

    public static JsonArray postListFinger(HashMap data) {
        JsonParser parser = new JsonParser();
        JsonArray list = null;
        try {
            String encodeAuth = "Basic " + Base64.getEncoder().encodeToString(BASIC_AUTH.getBytes());
            String url = SERVER_PATH + "list_finger";
            System.out.println(url);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Acept", "*/*");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", encodeAuth);
            try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
                out.write(new Gson().toJson(data));
            }
            int responseCode = con.getResponseCode();
            StringBuilder result;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            result = new StringBuilder();
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            con.disconnect();
            System.out.println(result.toString());
            if (responseCode == 200 && !result.toString().equals("")) {
                list = parser.parse(result.toString()).getAsJsonArray();
            }
        } catch (MalformedURLException ex) {
            System.out.println("Error clase: FingerprintUserHttpApi, metodo: postListFinger " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error clase: FingerprintUserHttpApi, metodo: postListFinger " + ex.getMessage());
        }
        return list;
    }

    public static boolean postUpdateFinger(TempFingerprintDTO tfdto) {
        boolean response = false;
        try {
            String encodeAuth = "Basic " + Base64.getEncoder().encodeToString(BASIC_AUTH.getBytes());
            URL obj = new URL(SERVER_PATH + "update_finger");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Acept", "*/*");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", encodeAuth);
            tfdto.setSerial_number_pc(SERIAL_NUMBER_PC);
            try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
                out.write(new Gson().toJson(tfdto));
            }
            int responseCode = con.getResponseCode();
            StringBuilder result;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            result = new StringBuilder();
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            con.disconnect();
            if (responseCode == 200 && !result.toString().equals("")) {
                response = true;
            }
            System.out.println("result = " + result.toString());
        } catch (MalformedURLException ex) {
            System.out.println("Error clase: FingerprintUserHttpApi, metodo: postUpdateFinger " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error clase: FingerprintUserHttpApi, metodo: postUpdateFinger " + ex.getMessage());
        }
        return response;
    }

    public static JsonArray postListSincronizacion(String data) {
        JsonParser parser = new JsonParser();
        JsonArray list = null;
        try {
            String encodeAuth = "Basic " + Base64.getEncoder().encodeToString(BASIC_AUTH.getBytes());
            URL obj = new URL(SERVER_PATH + "sincronizar");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Acept", "*/*");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", encodeAuth);
//            String object = new Gson().toJson(data);
            System.out.println("data " + data);
            try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
                out.write(data);
            }
            int responseCode = con.getResponseCode();           
            StringBuilder result;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            result = new StringBuilder();
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            con.disconnect();
            if (responseCode == 200 && !result.toString().equals("")) {
                list = parser.parse(result.toString()).getAsJsonArray();
            }
        } catch (MalformedURLException ex) {
            System.out.println("Error clase: FingerprintUserHttpApi, metodo: postListSyncFinger " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error clase: FingerprintUserHttpApi, metodo: postListSyncFinger " + ex.getMessage());
        }
        return list;
    }

}
