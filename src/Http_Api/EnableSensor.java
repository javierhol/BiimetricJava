package Http_Api;

import java.io.IOException;
import Model.DmlJsonDao;
import Model.IDmlJsonDao;
import Ui.Enroll;
import Ui.Read;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import Domain.ConfigDTO;
import Domain.GetEnrollForm;
import Domain.GetReadForm;
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
public final class EnableSensor {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36";
    private static String SERVER_PATH;
    private static String SERIAL_NUMBER_PC;
    public static Enroll enroll;
    public static Read read;
    public static String BASIC_AUTH = "testapi:123456";

    public static void getConfig() {
        if (SERVER_PATH == null) {
            IDmlJsonDao dml = new DmlJsonDao();
            ConfigDTO config = dml.activeNav();
            SERVER_PATH = config.getUrl_api();
            SERIAL_NUMBER_PC = config.getSerial_number_pc();
        }
    }

    public static long enabled(long time) {
        long timestamp = time;
        try {
            String encodeAuth = "Basic " + Base64.getEncoder().encodeToString(BASIC_AUTH.getBytes());
            String url = SERVER_PATH + "activate_sensor";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Acept", "*/*");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", encodeAuth);
            HashMap<String, String> map = new HashMap<>();
            map.put("serial_number_pc", SERIAL_NUMBER_PC);
            map.put("timestamp", String.valueOf(timestamp));
            try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
                out.write(new Gson().toJson(map));
            }
            int responseCode = con.getResponseCode();
            StringBuilder response;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            con.disconnect();
            if (responseCode == 200 && !response.toString().equals("")) {
                enroll = null;
                read = null;
                JsonParser parser = new JsonParser();
                JsonObject objJson = parser.parse(response.toString()).getAsJsonObject();
                String option = objJson.get("option").getAsString();
                timestamp = objJson.get("updated_at").getAsLong();
                System.out.println("times " + timestamp);
                System.out.println("option " + option);
                if (option.equals("close")) {
                    read = GetReadForm.getForm();
                    read.stop();
                    read.dispose();
                    read = null;
                    enroll = GetEnrollForm.getForm();
                    enroll.stop();
                    enroll.dispose();
                    enroll = null;
                }

                if (option.equals("enroll")) {
                    read = GetReadForm.getForm();
                    read.stop();
                    read.dispose();
                    read = null;
                    if (enroll == null) {
                        enroll = GetEnrollForm.getForm();
                        enroll.setVisible(true);
                        enroll.stop();
                        enroll.prepare();
                        enroll.start();
                    }
                }

                if (option.equals("read")) {
                    enroll = GetEnrollForm.getForm();
                    enroll.stop();
                    enroll.dispose();
                    enroll = null;
                    if (read == null) {
                        read = GetReadForm.getForm();
                        Read.txtNotificacion.setText("");
                        read.setVisible(true);
                        read.stop();
                        read.prepare();
                        read.start();
                    }
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println("Error clase: EnableSensor, metodo: enable " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error clase: EnableSensor, metodo: enable " + ex.getMessage());
        }
        return timestamp;
    }

    public static void closeSensor() {
        try {
            String encodeAuth = "Basic " + Base64.getEncoder().encodeToString(BASIC_AUTH.getBytes());
            String url = SERVER_PATH + "sensor_close";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Acept", "*/*");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", encodeAuth);
            HashMap<String, String> map = new HashMap<>();
            map.put("serial_number_pc", SERIAL_NUMBER_PC);
            try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
                out.write(new Gson().toJson(map));
            }
            int responseCode = con.getResponseCode();
            StringBuilder response;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            con.disconnect();
            if (responseCode != 200) {
                System.out.println("Respuesta:" + response.toString());
            }
        } catch (MalformedURLException e) {
            System.out.println("Error clase: EnableSensor, metodo: closeSensor " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error clase: EnableSensor, metodo: closeSensor " + e.getMessage());
        }
    }

}
