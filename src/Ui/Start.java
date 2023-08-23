package UI;

import java.io.IOException;
import Model.PropertiesJson;
import Utils.RunInBackground;
import Utils.SincronizarHuellas;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import Http_Api.EnableSensor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Mauricio Herrera
 */
public class Start {

    public static Timer timer = new Timer();
    public static long timestamp = (new Date().getTime() / 1000);

    public static void main(String[] args) {

        disableAccessWarnings();
        try {
            Properties props = new Properties();
            props.put("logoString", "M-Systems");
            AcrylLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("error confiig form");
        }
        try {
            File archivo = new File("src/Config/Config.json");
            if (!archivo.exists()) {
                ConfigForm cfg = new ConfigForm();
                cfg.setVisible(true);
                return;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                System.out.println("timestamp " + timestamp);
                EnableSensor.getConfig();
                timestamp = EnableSensor.enabled(timestamp);
            }
        };
        timer.schedule(tarea, 0, 1000);
        PropertiesJson p = new PropertiesJson();       
        if (p.select().get("auto_sync")) {
            SincronizarHuellas sh = new SincronizarHuellas();
            Thread hilo = new Thread(sh);
            hilo.start();
        } else {      
            RunInBackground.show("png");          
        }
    }

    @SuppressWarnings("unchecked")
    public static void disableAccessWarnings() {
        try {
            Class unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Object unsafe = field.get(null);
            Method putObjectVolatile = unsafeClass.getDeclaredMethod("putObjectVolatile", Object.class, long.class, Object.class);
            Method staticFieldOffset = unsafeClass.getDeclaredMethod("staticFieldOffset", Field.class);
            Class loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field loggerField = loggerClass.getDeclaredField("logger");
            Long offset = (Long) staticFieldOffset.invoke(unsafe, loggerField);
            putObjectVolatile.invoke(unsafe, loggerClass, offset, null);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException ignored) {
            System.out.println("Error StartClass " + ignored.getMessage());
        }
    }

}
