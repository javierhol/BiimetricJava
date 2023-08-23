package Utils;

import Windows.CMD;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Mauricio Herrera
 */
public class UtilsApp {

    public static String COMANDO_ADD = "REG ADD HKEY_CURRENT_USER";
    public static String COMANDO_REMOVE = "REG DELETE HKEY_CURRENT_USER";
    public static String COMPLEMENT = "\\dist";
    public static String app_name = "BiometricWebBridge.exe";

    public static int count = 0;

    public static void restartApplication() {
        try {
            count++;
            String current = new java.io.File(".").getCanonicalPath() + COMPLEMENT;
            File archivo = new File(current + "\\" + app_name);
            if (!archivo.exists()) {
                app_name = "BiometricWebBridge.jar";
            }
            System.out.println(current + "\\" + app_name);
            new ProcessBuilder("cmd", "/c start /min " + current + "\\" + app_name + " ^& exit").start();
            if (count > 0) {
                System.exit(0);
            }//        
        } catch (IOException ex) {
            System.out.println("Error reiniciando " + ex);
        }
    }

    public static String addServicesOnWindows() {
        String response = "";
        Runtime cmd = Runtime.getRuntime();
        try {
            String commandRegister = "";
            String current = new java.io.File(".").getCanonicalPath() + COMPLEMENT;
            File archivo = new File(current + "\\" + app_name);
            if (!archivo.exists()) {
                app_name = "PluginLectorBiometrico.jar";
            }
            commandRegister = "\\Software\\Microsoft\\Windows\\CurrentVersion\\Run /v " + app_name + " /t REG_SZ /d";
            String comando = COMANDO_ADD + commandRegister + " " + "\"" + current + "\\" + app_name + "\" /f";
            Process proceso = cmd.exec(comando);
            BufferedReader read = new BufferedReader(new InputStreamReader(proceso.getInputStream(), CMD.Detectar_Windows()));
            String linea;
            while ((linea = read.readLine()) != null) {
                response += linea + "\n";
            }
        } catch (IOException ex) {
            response = ex.getLocalizedMessage();
        }
        return response;
    }

    public static String removeServicesOnWindows() {
        String commandRemove = "";
        String response = "";
        Runtime cmd = Runtime.getRuntime();
        try {
            String current = new java.io.File(".").getCanonicalPath() + COMPLEMENT;
            File archivo = new File(current + "\\" + app_name);
            if (!archivo.exists()) {
                app_name = "PluginLectorBiometrico.jar";
            }
            commandRemove = "\\Software\\Microsoft\\Windows\\CurrentVersion\\Run /v " + app_name + " /f";
            String comando = COMANDO_REMOVE + commandRemove;
            Process proceso = cmd.exec(comando);
            BufferedReader read = new BufferedReader(new InputStreamReader(proceso.getInputStream(), CMD.Detectar_Windows()));
            String linea;
            while ((linea = read.readLine()) != null) {
                response += linea + "\n";
            }
        } catch (IOException ex) {
            response = ex.getLocalizedMessage();
        }
        return response;
    }
}
