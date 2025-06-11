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

        try (Connection con = Conexion.getConexion().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idProducto);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Promocion promo = new Promocion(
                        rs.getInt("idPromocion"),
                        rs.getInt("Cliente_idCliente"),
                        rs.getInt("Producto_idProducto"),
                        rs.getDouble("Valor"), // si en BD se llama Valor o Modificador, usa el correcto
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

}
