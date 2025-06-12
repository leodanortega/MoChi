package mochi.dao.reportes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.reportes.ProductoVenta;

public class ProductoVentaDAO {

    public static List<ProductoVenta> obtenerProductosVendidos() {
        List<ProductoVenta> lista = new ArrayList<>();

        String sql = "SELECT idProducto, NombreProducto, TotalVendido FROM vista_total_ventas_por_producto";

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductoVenta pv = new ProductoVenta();
                pv.setIdProducto(rs.getInt("idProducto"));
                pv.setNombreProducto(rs.getString("NombreProducto"));
                pv.setTotalVendido(rs.getInt("TotalVendido"));
                lista.add(pv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}