package mochi.dao.reportes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.reportes.ClienteProducto;

public class ClienteProductoDAO {

    public static List<ClienteProducto> obtenerClientesConProductos() {
        List<ClienteProducto> lista = new ArrayList<>();
        String sql = "SELECT idCliente, NombreCliente, idProducto, NombreProducto FROM vista_productos_no_vendidos_por_cliente"; // <- cambia el nombre si aplica

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClienteProducto cp = new ClienteProducto();
                cp.setIdCliente(rs.getInt("idCliente"));
                cp.setNombreCliente(rs.getString("NombreCliente"));
                cp.setIdProducto(rs.getInt("idProducto"));
                cp.setNombreProducto(rs.getString("NombreProducto"));

                lista.add(cp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
