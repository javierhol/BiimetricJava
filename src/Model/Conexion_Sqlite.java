package Model;

import Domain.TempFingerprintDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.io.IOException;

/**
 *
 * @author Mauricio Herrera
 */
public class Conexion_Sqlite {

    private static final String FILE_DB = "src/Config/UserDB.db";
    Connection con;
    Statement stmt = null;
    ResultSet rs;

    public Conexion_Sqlite() {
        createTable();
    }

    public void connection() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + FILE_DB);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error clase Conexion_Sqlite, metodo: connection " + e.getMessage());
        }
    }

    private void createTable() {
        try {
            connection();
            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USERS "
                    + "(uniqueId CHAR(100) PRIMARY KEY NOT NULL,"
                    + "user_id CHAR(20),"
                    + "user_id_number CHAR(100),"
                    + "name CHAR(200),"
                    + "finger_id CHAR(20),"
                    + "fingerprint longblob)";
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error clase Conexion_Sqlite, metodo: createTable " + e.getMessage());
        }
    }

    public int select() {
        int id = 0;
        try {
            connection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT finger_id FROM USERS ORDER BY finger_id desc limit 1");
            while (rs.next()) {
                id = rs.getInt("finger_id");
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error clase Conexion_Sqlite, metodo: select " + e.getMessage());
        }
        return id;
    }

    public ArrayList<TempFingerprintDTO> list() {
        ArrayList<TempFingerprintDTO> lista = new ArrayList<>();
        try {
            connection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM USERS");
            while (rs.next()) {
                TempFingerprintDTO tfdto = new TempFingerprintDTO();
                tfdto.setName(rs.getString("name"));
                tfdto.setUser_id_number(rs.getString("user_id_number"));
                tfdto.setUser_id(rs.getString("user_id"));
                tfdto.setFingerprint(rs.getString("fingerprint"));
                lista.add(tfdto);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error clase Conexion_Sqlite, metodo: select " + e.getMessage());
        }
        return lista;
    }

    public boolean insert(String user_id, String user_id_number, String name, String finger_id, String fingerprint) {
        boolean response = false;
        try {
            UUID uid = UUID.randomUUID();
            connection();
            con.setAutoCommit(false);
            stmt = con.createStatement();
            String sql = "INSERT INTO USERS (uniqueId, user_id, user_id_number, name, finger_id, fingerprint) "
                    + "VALUES ('" + uid + "', '" + user_id + "', '" + user_id_number + "', '" + name + "', '" + finger_id + "', '" + fingerprint + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            con.setAutoCommit(true);
            con.close();
            response = true;
        } catch (SQLException e) {
            System.out.println("Error clase Conexion_Sqlite, metodo: insert " + e.getMessage());
        }
        return response;
    }

}
