package mochi.dao;

import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.Proovedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProovedorDAO {

    public List<Proovedor> obtenerProveedores() {
        List<Proovedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Proovedor p = new Proovedor();
                p.setIdProovedor(rs.getInt("idProveedor"));
                p.setNombre(rs.getString("nombre"));
                p.setRfc(rs.getString("RFC"));
                p.setDireccion(rs.getString("Direccion"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("correo"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Proovedor> listar() {
        List<Proovedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Proovedor p = new Proovedor();
                p.setIdProovedor(rs.getInt("idProveedor"));
                p.setNombre(rs.getString("nombre"));
                p.setRfc(rs.getString("RFC"));
                p.setDireccion(rs.getString("Direccion"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("correo"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Solo cerrar ps y rs, no la conexión
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }

    public boolean agregar(Proovedor p) {
        String sql = "INSERT INTO proveedor(nombre, RFC, Direccion, telefono, correo) VALUES (?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getRfc());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getEmail());

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

    public boolean modificar(Proovedor p) {
        String sql = "UPDATE proveedor SET nombre = ?, RFC = ?, Direccion = ?, telefono = ?, correo = ? WHERE idProveedor = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getRfc());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getEmail());
            ps.setInt(6, p.getIdProovedor());

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

    public boolean eliminar(int idProovedor) {
        String sql = "DELETE FROM proveedor WHERE idProveedor = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, idProovedor);

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

    public String obtenerNombreProveedorPorIdCompra(int idCompra) {
        String sql = "SELECT p.nombre FROM proveedor p " +
                "JOIN compras c ON c.Proveedor_idProveedor = p.idProveedor " +
                "WHERE c.idCompras = ?";

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCompra);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
