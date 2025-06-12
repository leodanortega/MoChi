/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mochi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        String sql = "INSERT INTO detalle_venta(Producto_idProducto, Venta_idVenta, Cantidad_Producto, Total_Producto) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (DetalleVenta d : detalles) {
                ps.setInt(1, d.getIdProducto());
                ps.setInt(2, d.getIdVenta());
                ps.setDouble(3, d.getTotalProducto());
                ps.setDouble(4, d.getTotalProducto());
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

