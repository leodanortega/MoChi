package mochi.conexionbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion{
    private Connection conn;
    private String host;
    private String db;
    private String username;
    private String password;

    private static Conexion conexion;
    //SÓLO PARA EFECTOS DE LA PRUEBA
    public Conexion()
    {
        host = "localhost:3306";
        db = "mydb";
        username = "administrador"; //LO MEJOR ES INCLUIR OTRO USUARIO
        password = "g7V!xP#2rLq9e@Mf";
        //regresa un objeto del tipo especificado, com.mysql.jdbc.Driver es la clase que implementa java.sql.Driver
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Intentamos conectarnos a la base de Datos
            System.out.println("Conectando a la base...");
            String url ="jdbc:mysql://"+host+"/"+db + "?serverTimezone=America/Mexico_City";
            conn = DriverManager.getConnection(url,username, password);
            System.out.println("Conexion a BD establecida");
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println("Se produjo un error inesperado: "+e.getMessage());
        }
        conexion = this;
    }

    public Conexion(String host, String db, String username, String password) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.host = host;
        this.db = db;
        this.username = username;
        this.password = password;
        conn = DriverManager.getConnection ("jdbc:mysql://" + host +"/"+db,username,password);
        conexion = this;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() {
        try
        {
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println ("Error: " + e.getMessage () + "\n" + e.getErrorCode ());
        }
    }

    public static Conexion getConexion() {
        if (conexion == null) {
            conexion = new Conexion(); // Asegura que haya una instancia si no existe
        }
        return conexion;
    }

    public static void setConexion(Conexion conexion) {
        Conexion.conexion = conexion;
    }
}
