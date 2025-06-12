/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mochi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.DetalleVenta;

/**
 *
 * @author cuent
 */
// DetalleVentaDAO.java
public class DetalleVentaDAO {
  public void registrarDetalle(List<DetalleVenta> detalles) {
    String sql = "{CALL sp_realizar_detalle_venta(?, ?, ?, ?)}";

    Connection con = null;
    CallableStatement cs = null;

    try {
        con = Conexion.getConexion("administrador").getConnection();

        // Opcional: puedes dejar autocommit en true porque el procedimiento maneja la transacción
        // con.setAutoCommit(false);

        cs = con.prepareCall(sql);

        for (DetalleVenta d : detalles) {
            cs.setInt(1, d.getIdProducto());
            cs.setInt(2, d.getIdVenta());
            cs.setInt(3, d.getCantidadProducto());
            cs.setDouble(4, d.getTotalProducto());

            cs.execute();
        }

    } catch (SQLException e) {
        e.printStackTrace();
        // Aquí puedes hacer manejo adicional si quieres
    } finally {
        if (cs != null) {
            try {
                cs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
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



