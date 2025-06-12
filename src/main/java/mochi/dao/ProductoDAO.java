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
            con = Conexion.getConexion("administrador").getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("Nombre"));
                p.setPresentacion(rs.getString("Presentacion"));
                p.setCosto(rs.getDouble("Costo"));
                p.setCantidadActual(rs.getInt("Cantidad_Actual"));
                p.setCantidadMinima(rs.getInt("Cantidad_Minima"));
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
        String sql = "INSERT INTO producto(Nombre, Presentacion, Costo, Cantidad_Actual, Cantidad_Minima) VALUES (?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
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
        String sql = "UPDATE producto SET Nombre = ?, Presentacion = ?, Costo = ?, Cantidad_Actual = ?, Cantidad_Minima = ? WHERE idProducto = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConexion("administrador").getConnection();
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
            con = Conexion.getConexion("administrador").getConnection();
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

    public List<Producto> listarProductosFaltantes() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto p WHERE p.Cantidad_Actual < p.Cantidad_Minima AND NOT EXISTS ( SELECT 1 FROM detalle_compra dc WHERE dc.Producto_idProducto = p.idProducto)";

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("Nombre"));
                p.setPresentacion(rs.getString("Presentacion"));
                p.setCosto(rs.getDouble("Costo"));
                p.setCantidadActual(rs.getInt("Cantidad_Actual"));
                p.setCantidadMinima(rs.getInt("Cantidad_Minima"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Producto> listarProductosConDetalleCompra() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT p.* FROM producto p " +
                "INNER JOIN detalle_compra dc ON p.idProducto = dc.Producto_idProducto " +
                "WHERE p.Cantidad_Minima > p.Cantidad_Actual";

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("Nombre"));
                p.setPresentacion(rs.getString("Presentacion"));
                p.setCosto(rs.getDouble("Costo"));
                p.setCantidadActual(rs.getInt("Cantidad_Actual"));
                p.setCantidadMinima(rs.getInt("Cantidad_Minima"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean actualizarStockYLimpiarPedidos(Producto producto, PedidoDAO pedidoDAO) {
        Connection con = null;
        try {
            con = Conexion.getConexion("administrador").getConnection();
            con.setAutoCommit(false);

            // Actualiza la cantidad actual del producto
            String sqlActualizarStock = "UPDATE producto SET Cantidad_Actual = ? WHERE idProducto = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlActualizarStock)) {
                ps.setInt(1, producto.getCantidadActual());
                ps.setInt(2, producto.getIdProducto());
                ps.executeUpdate();
            }

            // Limpia pedidos donde el stock ya superó la cantidad mínima
            // (se implementa en PedidoDAO, recibe la conexión para usar la misma transacción)
            pedidoDAO.limpiarPedidosConStockSuficiente(producto.getIdProducto(), con);

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

