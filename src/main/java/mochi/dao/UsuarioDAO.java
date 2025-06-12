package mochi.dao;

import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellidoPaterno(rs.getString("apellidoPaterno"));
                u.setApellidoMaterno(rs.getString("apellidoMaterno"));
                u.setUsername(rs.getString("usuario"));
                u.setPassword(rs.getString("password"));
                u.setTipo(rs.getInt("tipo"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }

    public boolean agregar(Usuario u) {
        String sql = "INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, usuario, password, tipo) VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidoPaterno());
            ps.setString(3, u.getApellidoMaterno());
            ps.setString(4, u.getUsername());
            ps.setString(5, u.getPassword());
            ps.setInt(6, u.getTipo());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean modificar(Usuario u) {
        String sql = "UPDATE usuario SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, usuario = ?, password = ?, tipo = ? WHERE idUsuario = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidoPaterno());
            ps.setString(3, u.getApellidoMaterno());
            ps.setString(4, u.getUsername());
            ps.setString(5, u.getPassword());
            ps.setInt(6, u.getTipo());
            ps.setInt(7, u.getIdUsuario());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Usuario> buscarPorUsername(String usernameBusqueda) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE username LIKE ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + usernameBusqueda + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellidoPaterno(rs.getString("apellidoPaterno"));
                u.setApellidoMaterno(rs.getString("apellidoMaterno"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setTipo(rs.getInt("tipo"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
    public List<Usuario> buscarPorNombre(String nombreBusqueda) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nombre LIKE ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombreBusqueda + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellidoPaterno(rs.getString("apellidoPaterno"));
                u.setApellidoMaterno(rs.getString("apellidoMaterno"));
                u.setUsername(rs.getString("usuario"));
                u.setPassword(rs.getString("password"));
                u.setTipo(rs.getInt("tipo"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
}
