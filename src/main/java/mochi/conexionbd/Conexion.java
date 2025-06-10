package mochi.conexionbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Connection conn;
    private String host;
    private String db;
    private String username;
    private String password;

    private static Conexion instancia;  // singleton

    // Constructor privado para evitar instancias externas
    private Conexion() {
        this.host = "localhost:3306";
        this.db = "mydb";
        this.username = "administrador";
        this.password = "g7V!xP#2rLq9e@Mf";
        conectar();
    }

    // Constructor privado con parámetros
    private Conexion(String host, String db, String username, String password) {
        this.host = host;
        this.db = db;
        this.username = username;
        this.password = password;
        conectar();
    }

    private void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + host + "/" + db + "?serverTimezone=America/Mexico_City";
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión a BD establecida");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    // Método público para obtener la instancia única (sin parámetros)
    public static synchronized Conexion getConexion() {
        if (instancia == null || instancia.conn == null) {
            instancia = new Conexion();
        } else {
            try {
                if (instancia.conn.isClosed()) {
                    instancia = new Conexion();
                }
            } catch (SQLException e) {
                System.err.println("Error comprobando estado de la conexión: " + e.getMessage());
                instancia = new Conexion();
            }
        }
        return instancia;
    }

    // Método público para obtener la instancia única con parámetros personalizados
    public static synchronized Conexion getConexion(String host, String db, String username, String password) {
        if (instancia == null || instancia.conn == null) {
            instancia = new Conexion(host, db, username, password);
        } else {
            try {
                if (instancia.conn.isClosed()) {
                    instancia = new Conexion(host, db, username, password);
                }
            } catch (SQLException e) {
                System.err.println("Error comprobando estado de la conexión: " + e.getMessage());
                instancia = new Conexion(host, db, username, password);
            }
        }
        return instancia;
    }

    public Connection getConnection() {
        return conn;
    }

    // Cerrar conexión SOLO cuando se quiera finalizar la sesión
    public void close() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    System.out.println("Conexión cerrada correctamente");
                }
            } catch (SQLException e) {
                System.err.println("Error cerrando la conexión: " + e.getMessage());
            }
        }
    }

    // Getters y setters si los necesitas (opcional)
    // ...
}

