package mochi.dao.reportes;

import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.reportes.ClienteProductoTotal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteProductoTotalDAO {

    public static List<ClienteProductoTotal> obtenerBebidaMasomenosCompradaPorCliente(boolean mas) {
        List<ClienteProductoTotal> lista = new ArrayList<>();
        String sql = " ";
        if(mas == true){
         sql = """
            SELECT Cliente AS nombreCliente, Producto AS nombreProducto, TotalComprado
            FROM bebida_mas_comprada_por_cliente
            ORDER BY nombreCliente;
        """;
        }else{
           sql = """
            SELECT Cliente AS nombreCliente, Producto AS nombreProducto, TotalComprado
            FROM bebida_menos_comprada_por_cliente
            ORDER BY nombreCliente;
        """;  
        }
       try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClienteProductoTotal cpt = new ClienteProductoTotal();
                cpt.setNombreCliente(rs.getString("nombreCliente"));
                cpt.setNombreProducto(rs.getString("nombreProducto"));
                cpt.setTotalComprado(rs.getInt("TotalComprado"));
                lista.add(cpt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static List<ClienteProductoTotal> obtenerBebidaMasCompradaPorCliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
