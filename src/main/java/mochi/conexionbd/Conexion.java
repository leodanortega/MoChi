package mochi.conexionbd;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private Connection conn;
    private static Conexion instancia;

    private Conexion(String host, String db, String username, String password) {
        conectar(host, db, username, password);
    }

    private void conectar(String host, String db, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + host + "/" + db + "?serverTimezone=America/Mexico_City";
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión a BD establecida");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public static synchronized Conexion getConexion(String perfil) {
        try {
            Properties props = cargarPropiedades(perfil);
            String host = props.getProperty("db.host");
            String db = props.getProperty("db.name");
            String username = props.getProperty("db.user." + perfil);
            String password = props.getProperty("db.password." + perfil);

            if (instancia == null || instancia.conn == null || instancia.conn.isClosed()) {
                instancia = new Conexion(host, db, username, password);
            }

            return instancia;
        } catch (IOException | SQLException e) {
            System.err.println("No se pudo obtener la conexión: " + e.getMessage());
            return null;
        }
    }

    private static Properties cargarPropiedades(String perfil) throws IOException {
        Properties props = new Properties();
        try (InputStream input = Conexion.class.getClassLoader().getResourceAsStream("Conexion/database.properties")) {
            if (input == null) {
                throw new IOException("Archivo database.properties no encontrado.");
            }
            props.load(input);
        }
        return props;
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    System.out.println("Conexión cerrada correctamente");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
