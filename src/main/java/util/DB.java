package util;

/**
 * @author Murat Çelik Pegamis
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DB {

    final private String url = "jdbc:mysql://localhost/";
    final private String dbName = "proje_ee?useUnicode=true&characterEncoding=utf-8";
    final private String dbUser = "root";
    final private String dbPass = "";

    final private String driver = "com.mysql.jdbc.Driver";

    private Connection con = null;
    private PreparedStatement st;

    public DB() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url + dbName, dbUser, dbPass);
            System.out.println("Baglanti Basarili");
        } catch (Exception e) {
            System.err.println("Baglanti Hatasi : " + e);
        }
    }

    public PreparedStatement connect(String query) {
        try {
            st = con.prepareStatement(query);
            return st;
        } catch (Exception e) {
            System.err.println("Pre St Hatasi : " + e);
        }
        return st;
    }
}
