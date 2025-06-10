package mochi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mochi.modelo.pojo.Usuario;
import mochi.conexionbd.Conexion; // Import correcto para tu clase de conexión

public class InicioSesionDAO {

    public static Usuario verificarCredenciales(Connection conexion, String username, String password) throws SQLException {
        Usuario usuarioSesion = null;
        Connection conexionBD = Conexion.getConexion().getConnection();
        if (conexionBD != null) {
            String consulta = "SELECT idUsuario, nombre, apellidoPaterno, apellidoMaterno, usuario, tipo " +
                    "FROM usuario WHERE usuario = ? AND password = ?";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, username);
            sentencia.setString(2, password);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                usuarioSesion = convertirRegistroUsuario(resultado);
            }
            resultado.close();
            sentencia.close();
        } else {
            throw new SQLException("Error: Sin conexión a la Base de Datos");
        }
        return usuarioSesion;
    }

    private static Usuario convertirRegistroUsuario(ResultSet resultado) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultado.getInt("idUsuario"));
        usuario.setNombre(resultado.getString("nombre"));
        usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
        usuario.setApellidoMaterno(resultado.getString("apellidoMaterno") != null ?
                resultado.getString("apellidoMaterno") : "");
        usuario.setUsername(resultado.getString("usuario"));
        usuario.setTipo(resultado.getInt("tipo"));
        return usuario;
    }

    public List<Usuario> obtenerTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT idUsuario, nombre, apellidoPaterno, apellidoMaterno, usuario, password, tipo FROM usuario";

        try (Connection conn = Conexion.getConexion().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("apellidoPaterno"),
                        rs.getString("apellidoMaterno"),
                        rs.getString("usuario"),
                        rs.getString("password"),
                        rs.getInt("tipo")
                );
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean insertarUsuario(Usuario usuario) {
        String query = "INSERT INTO usuario (nombre, apellidoPaterno, apellidoMaterno, usuario, password, tipo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellidoPaterno());
            pstmt.setString(3, usuario.getApellidoMaterno());
            pstmt.setString(4, usuario.getUsername());
            pstmt.setString(5, usuario.getPassword());
            pstmt.setInt(6, usuario.getTipo());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
