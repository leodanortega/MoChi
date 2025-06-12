package mochi.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.DetalleVenta;
import mochi.modelo.pojo.Venta;

public class VentaDAO {
    public int generarVenta(int idCliente) {
        String sql = "SELECT crear_venta_cliente(?) AS idVenta";
        int idGenerado = -1;

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idGenerado = rs.getInt("idVenta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

    public List<Venta> obtenerTodasLasVentas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT idVenta, Cliente_idCliente, Total, fecha FROM venta";

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setIdVenta(rs.getInt("idVenta"));
                venta.setIdCliente(rs.getInt("Cliente_idCliente"));
                venta.setTotal(rs.getDouble("Total"));
                venta.setFecha(rs.getDate("fecha").toLocalDate());

                ventas.add(venta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ventas;
    }

    public List<DetalleVenta> obtenerDetallePorVenta(int idVenta) {
        List<DetalleVenta> detalles = new ArrayList<>();
        String sql = "SELECT Producto_idProducto, Venta_idVenta, Cantidad_Producto, Total_Producto FROM detalle_venta WHERE Venta_idVenta = ?";

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setIdProducto(rs.getInt("Producto_idProducto"));
                    detalle.setIdVenta(rs.getInt("Venta_idVenta"));
                    detalle.setCantidadProducto(rs.getInt("Cantidad_Producto"));
                    detalle.setTotalProducto(rs.getDouble("Total_Producto"));
                    detalles.add(detalle);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalles;
    }
}