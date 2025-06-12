/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mochi.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.Venta;

public class VentaDAO {
 public int generarVenta(int idCliente) {
    String sql = "SELECT crear_venta_cliente(?) AS idVenta";
    int idGenerado = -1;

    try (Connection con = Conexion.getConexion().getConnection();
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
}
