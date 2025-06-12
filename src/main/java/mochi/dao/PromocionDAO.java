package mochi.dao;

import mochi.conexionbd.Conexion;
import mochi.modelo.pojo.Promocion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PromocionDAO {

    public static Optional<Promocion> obtenerPromocionValida(int idCliente, int idProducto) {
        Optional<Promocion> promocion = Optional.empty();
        String sql = "SELECT * FROM promocion WHERE Cliente_idCliente = ? AND Producto_idProducto = ? AND CURDATE() BETWEEN Fecha_Inicio AND Fecha_Fin";

        try (Connection con = Conexion.getConexion("administrador").getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idProducto);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Promocion promo = new Promocion(
                        rs.getInt("idPromocion"),
                        rs.getInt("Cliente_idCliente"),
                        rs.getInt("Producto_idProducto"),
                        rs.getDouble("Valor/Modificador"), 
                        rs.getDate("Fecha_Inicio").toLocalDate(),
                        rs.getDate("Fecha_Fin").toLocalDate()
                );
                promocion = Optional.of(promo);
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar promoción: " + e.getMessage());
        }

        return promocion;
    }
    
    public static List<Promocion> obtenerPromocionesActivasHoy() {
    List<Promocion> promocionesActivas = new ArrayList<>();
    String sql = "SELECT * FROM promocion WHERE CURDATE() BETWEEN Fecha_Inicio AND Fecha_Fin";

    try (Connection con = Conexion.getConexion("administrador").getConnection();
         PreparedStatement stmt = con.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Promocion promo = new Promocion(
                    rs.getInt("idPromocion"),
                    rs.getInt("Cliente_idCliente"),
                    rs.getInt("Producto_idProducto"),
                    rs.getDouble("Valor/Modificador"), // Asegúrate que el nombre de la columna en la BD sea "Valor"
                    rs.getDate("Fecha_Inicio").toLocalDate(),
                    rs.getDate("Fecha_Fin").toLocalDate()
            );
            promocionesActivas.add(promo);
        }

    } catch (SQLException e) {
        System.err.println("Error al obtener promociones activas: " + e.getMessage());
    }

    return promocionesActivas;
}


}
