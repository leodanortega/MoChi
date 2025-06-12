package mochi.dao;

import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNombre(rs.getString("Nombre"));
                c.setRfc(rs.getString("RFC"));
                c.setDireccion(rs.getString("Direccion"));
                c.setTelefono(rs.getString("Telefono"));
                c.setEmail(rs.getString("Email"));
                c.setFactura(rs.getBoolean("Requiere_Factura"));
                lista.add(c);
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

    public boolean agregar(Cliente c) {
        String sql = "INSERT INTO cliente(nombre, rfc, direccion, telefono, email, Requiere_Factura) VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getRfc());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getEmail());
            ps.setBoolean(6, c.isFactura());

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

    public boolean modificar(Cliente c) {
        String sql = "UPDATE cliente SET nombre = ?, rfc = ?, direccion = ?, telefono = ?, email = ?, Requiere_Factura = ? WHERE idCliente = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getRfc());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getEmail());
            ps.setBoolean(6, c.isFactura());
            ps.setInt(7, c.getIdCliente());

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

    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM cliente WHERE idCliente = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, idCliente);

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
    public List<Cliente> buscarPorNombre(String nombreBusqueda) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE nombre LIKE ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombreBusqueda + "%"); // búsqueda parcial, case sensitive depende de BD

            rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombre"));
                c.setRfc(rs.getString("rfc"));
                c.setDireccion(rs.getString("direccion"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
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
