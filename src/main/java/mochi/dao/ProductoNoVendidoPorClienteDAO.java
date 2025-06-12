
package mochi.dao;

import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.ProductoNoVendidoPorCliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoNoVendidoPorClienteDAO {

    public List<ProductoNoVendidoPorCliente> listarTodos() {
        List<ProductoNoVendidoPorCliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM vista_productos_no_vendidos_por_cliente";

        try (Connection con = Conexion.getConexion("empleado").getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductoNoVendidoPorCliente registro = new ProductoNoVendidoPorCliente();
                registro.setIdCliente(rs.getObject("idCliente") != null ? rs.getInt("idCliente") : null);
                registro.setNombreCliente(rs.getString("NombreCliente"));
                registro.setIdProducto(rs.getInt("idProducto"));
                registro.setNombreProducto(rs.getString("NombreProducto"));

                lista.add(registro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
