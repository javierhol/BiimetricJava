/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Model.DmlJsonDao;
import Model.IDmlJsonDao;
import Model.PropertiesJson;
import UI.ConfigForm;
import Domain.ConfigDTO;
import ds.desktop.notify.DesktopNotify;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Mauricio Herrera
 */
public class RunInBackground {

    static TrayIcon trayIcon;
    public static Image icon;
    static SystemTray tray;
    static ArrayList<String> listNav = new ArrayList<>();
    static String activeNav = "";
    static String CR = "Chrome";
    static String MZ = "Mozilla";
    static String ED = "Edge";
    static String EXP = "Explorer";
    static String AUTO_SYNC = "Inactivo";

    public static void show(String iconType) {
        if (trayIcon == null) {
            if (!SystemTray.isSupported()) {
                System.exit(0);
            }
            if (iconType.equals("gif")) {
                icon = createIcon("/images/loadinfo.net.gif", "Icon");
            } else {
                icon = createIcon("/images/fingerprint_20px.png", "Icon");
            }
            trayIcon = new TrayIcon(icon);
            trayIcon.setToolTip("Sensor Biometrico");
            tray = SystemTray.getSystemTray();
            creartePopupMenu();
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("Error clase: RunInBackground, metodo: show" + e.getMessage());
            }
        }
    }

    public static void creartePopupMenu() {
        PopupMenu menu = new PopupMenu();
        addMenuBrowser(menu);
        menu.addSeparator();
        addMenuConfig(menu);
        MenuItem FingerSync = new MenuItem("Sincronizar Huellas.");
        MenuItem AutoStart = new MenuItem("Crear Inicio Automatico");
        MenuItem RemoveAutoStart = new MenuItem("Eliminar Inicio Automatico");
        MenuItem Close = new MenuItem("Cerrar");

        FingerSync.addActionListener((e) -> {
            SincronizarHuellas sh = new SincronizarHuellas();
            Thread hilo = new Thread(sh);
            hilo.start();
        });

        AutoStart.addActionListener((e) -> {
            String r = UtilsApp.addServicesOnWindows();
            if (!r.equals("")) {
                DesktopNotify.showDesktopMessage("Aviso..!", r.trim() + "\nLa aplicación iniciara con el sistema operativo",
                        DesktopNotify.SUCCESS, 4000L);
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "La aplicación ya inicia con el sistema opoerativo..!",
                        DesktopNotify.INFORMATION, 3000L);
            }
        });
        RemoveAutoStart.addActionListener((e) -> {
            String r = UtilsApp.removeServicesOnWindows();
            if (!r.equals("")) {
                DesktopNotify.showDesktopMessage("Aviso..!", r.trim() + "\nLa aplicación ya no iniciará con el sistema operativo",
                        DesktopNotify.SUCCESS, 4000L);
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "No hay registro de auto inicio..!",
                        DesktopNotify.INFORMATION, 3000L);
            }
        });

        Close.addActionListener((e) -> {
            System.exit(0);
        });

        menu.addSeparator();
        menu.add(FingerSync);
        addAutoSyncFingerMenu(menu);
        menu.addSeparator();
        menu.add(AutoStart);
        menu.add(RemoveAutoStart);
        menu.addSeparator();
        menu.add(Close);
        trayIcon.setPopupMenu(menu);
    }

    public static void destroy() {
        tray.remove(trayIcon);
        trayIcon = null;
        icon = null;
        tray = null;
    }

    public static void addMenuBrowser(PopupMenu menu) {
        geActiveNav();
        Menu Browser = new Menu("Navegador");
        MenuItem Chrome = new MenuItem(CR);
        MenuItem Mozilla = new MenuItem(MZ);
        MenuItem Edge = new MenuItem(ED);
        MenuItem Explorer = new MenuItem(EXP);
        Chrome.addActionListener((ActionEvent e) -> {
            Chrome.setLabel("✓ Chrome");
            Mozilla.setLabel("Mozilla");
            Edge.setLabel("Edge");
            Explorer.setLabel("Explorer");
            updateBrowser("Chrome");
        });
        Mozilla.addActionListener((ActionEvent e) -> {
            Chrome.setLabel("Chrome");
            Mozilla.setLabel("✓ Mozilla");
            Edge.setLabel("Edge");
            Explorer.setLabel("Explorer");
            updateBrowser("Mozilla");
        });
        Edge.addActionListener((ActionEvent e) -> {
            Chrome.setLabel("Chrome");
            Mozilla.setLabel("Mozilla");
            Edge.setLabel("✓ Edge");
            Explorer.setLabel("Explorer");
            updateBrowser("Edge");
        });
        Explorer.addActionListener((ActionEvent e) -> {
            Chrome.setLabel("Chrome");
            Mozilla.setLabel("Mozilla");
            Edge.setLabel("Edge");
            Explorer.setLabel("✓ Explorer");
            updateBrowser("Explorer");
        });

        for (int i = 0; i < listNav.size(); i++) {
            if (listNav.get(i).equals("Chrome")) {
                Browser.add(Chrome);
            }
            if (listNav.get(i).equals("Mozilla")) {
                Browser.add(Mozilla);
            }
            if (listNav.get(i).equals("Edge")) {
                Browser.add(Edge);
            }
            if (listNav.get(i).equals("Explorer")) {
                Browser.add(Explorer);
            }
        }
        menu.add(Browser);
    }

    public static void addAutoSyncFingerMenu(PopupMenu menu) {
        try {
            getStatusAutoSync();
            Menu AutoSync = new Menu("Auto-Sincronizar Huellas");
            MenuItem Status = new MenuItem(AUTO_SYNC);
            Status.addActionListener((ActionEvent e) -> {
                updateStatusAutoSync(AUTO_SYNC.equals("Inactivo"));
            });
            AutoSync.add(Status);
            menu.add(AutoSync);
        } catch (IOException e) {
            System.out.println("Error RunInBackground, metodo: addAutoSyncFingerMenu " + e.getMessage());
        }
    }

    private static void addMenuConfig(PopupMenu menu) {
        MenuItem Config = new MenuItem("Nueva Configuración");
        Config.addActionListener((e) -> {
            try {
                IDmlJsonDao dml = new DmlJsonDao();
                ConfigDTO configActive = dml.activeNav();
                String url_api_sensor = configActive.getUrl_api();
                String serial_number_pc = configActive.getSerial_number_pc();
                String browser = configActive.getBrowser();
                ConfigForm cf = new ConfigForm();
                cf.url_api_sensor.setText(url_api_sensor);
                cf.serial_number_pc.setText(serial_number_pc);
                cf.browser.removeItem(browser);
                cf.setVisible(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });
        menu.add(Config);
    }

    private static void geActiveNav() {
        getListaNav();
        IDmlJsonDao dml = new DmlJsonDao();
        ConfigDTO activeCfNav = dml.activeNav();
        activeNav = activeCfNav.getBrowser();
        switch (activeNav) {
            case "Chrome":
                CR = "✓ Chrome";
                break;
            case "Mozilla":
                MZ = "✓ Mozilla";
                break;
            case "Edge":
                ED = "✓ Edge";
                break;
            case "Explorer":
                EXP = "✓ Explorer";
                break;
        }
    }

    private static void getListaNav() {
        listNav.clear();
        IDmlJsonDao dml = new DmlJsonDao();
        listNav = (ArrayList<String>) dml.listNav();
    }
  
    public static Image createIcon(String imagen, String icon) {
        Image imageIcon = new javax.swing.ImageIcon(RunInBackground.class.getResource(imagen)).getImage();
        return (new ImageIcon(imageIcon, icon)).getImage();
    }

    private static void getStatusAutoSync() throws IOException {
        PropertiesJson p = new PropertiesJson();
        AUTO_SYNC = (p.select().get("auto_sync")) ? "Activo" : "Inactivo";
    }

    private static void updateStatusAutoSync(boolean status) {
        HashMap obj = new HashMap();
        obj.put("auto_sync", status);
        PropertiesJson p = new PropertiesJson();
        p.insert(obj);
        DesktopNotify.showDesktopMessage("AutoSync " + ((status) ? "Activo" : "Inactivo"), "La aplicación se reiniciara para que la configuración tenga efecto",
                DesktopNotify.INFORMATION, 40000L);
        Timer timer = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                UtilsApp.restartApplication();
            }
        };
        timer.schedule(tarea, 4000, 1);
    }


    public static void updateBrowser(String Browser) {
        ConfigDTO config = new ConfigDTO();
        config.setBrowser(Browser);
        IDmlJsonDao dml = new DmlJsonDao();
        dml.changeBrowser(config);
        DesktopNotify.showDesktopMessage("Navegador Activo: " + Browser, "La aplicación se reiniciara para que la configuración tenga efecto",
                DesktopNotify.INFORMATION, 50000L);
        Timer timer = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                UtilsApp.restartApplication();
            }
        };
        timer.schedule(tarea, 4000, 1);
    }

}
