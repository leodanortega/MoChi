package mochi.dao;

import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setPresentacion(rs.getString("presentacion"));
                p.setCosto(rs.getDouble("costo"));
                p.setCantidadActual(rs.getInt("cantidadActual"));
                p.setCantidadMinima(rs.getInt("cantidadMinima"));
                lista.add(p);
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

    public boolean agregar(Producto p) {
        String sql = "INSERT INTO producto(nombre, presentacion, costo, cantidadActual, cantidadMinima) VALUES (?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion().getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getPresentacion());
            ps.setDouble(3, p.getCosto());
            ps.setInt(4, p.getCantidadActual());
            ps.setInt(5, p.getCantidadMinima());

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

    public boolean modificar(Producto p) {
        String sql = "UPDATE producto SET nombre = ?, presentacion = ?, costo = ?, cantidadActual = ?, cantidadMinima = ? WHERE idProducto = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion().getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getPresentacion());
            ps.setDouble(3, p.getCosto());
            ps.setInt(4, p.getCantidadActual());
            ps.setInt(5, p.getCantidadMinima());
            ps.setInt(6, p.getIdProducto());

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

    public boolean eliminar(int idProducto) {
        String sql = "DELETE FROM producto WHERE idProducto = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion().getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, idProducto);

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
}

