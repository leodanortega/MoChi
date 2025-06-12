package mochi.dao;

import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.Compra;
import mochi.modelo.pojo.DetalleCompra;

import java.sql.*;
import java.util.List;

public class CompraDAO {

    public int crearCompraSinDetalles(Compra compra) {
        String sql = "INSERT INTO compras (Proveedor_idProveedor, Total, fecha) VALUES (?, ?, CURRENT_DATE)";
        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, compra.getIdProveedor());
            ps.setDouble(2, compra.getTotal());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public boolean agregarDetalle(DetalleCompra detalle) {
        String sql = "INSERT INTO detalle_compra (Compras_idCompras, Producto_idProducto, Cantidad, Precio_Compra) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detalle.getIdCompra());
            ps.setInt(2, detalle.getIdProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioCompra());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener total acumulado de detalles de una compra
    public double obtenerTotalPorCompra(int idCompra) {
        String sql = "SELECT SUM(Cantidad * Precio_Compra) AS Total FROM detalle_compra WHERE Compras_idCompras = ?";
        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCompra);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Método para actualizar proveedor y total de compra
    public boolean actualizarCompraProveedorTotal(int idCompra, int idProveedor, double total) {
        String sql = "UPDATE compras SET Proveedor_idProveedor = ?, Total = ? WHERE idCompras = ?";
        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);
            ps.setDouble(2, total);
            ps.setInt(3, idCompra);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}