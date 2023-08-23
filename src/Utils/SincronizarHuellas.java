package Utils;

import Model.Conexion_Sqlite;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import Http_Api.FingerprintUserHttpApi;
import java.util.HashMap;

/**
 *
 * @author Mauricio Herrera
 */
public class SincronizarHuellas implements Runnable {

    public SincronizarHuellas() {
    }

    @Override
    public void run() {
        Conexion_Sqlite cnn = null;
        System.out.println("Sincronizando..");        
        try {
            RunInBackground.show("gif");
            cnn = new Conexion_Sqlite();
            int finger_id = cnn.select();
            HashMap obj = new HashMap();
            obj.put("finger_id", finger_id);
            String objJson = new Gson().toJson(obj);
            JsonArray list = FingerprintUserHttpApi.postListSincronizacion(objJson);
            for (JsonElement jsonElement : list) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                cnn.insert(jsonObject.get("user_id").getAsString(), jsonObject.get("user_id_number").getAsString(), 
                        jsonObject.get("name").getAsString(), jsonObject.get("finger_id").getAsString(), jsonObject.get("fingerprint").getAsString());
            }
            System.out.println("Sincronizcion finalizada..");
            RunInBackground.destroy();
            RunInBackground.show("png");
        } catch (Exception e) {
             System.out.println("error clase: SincronizarHuellas " + e.getMessage());
        } finally {
            cnn = null;
        }

    }

}
