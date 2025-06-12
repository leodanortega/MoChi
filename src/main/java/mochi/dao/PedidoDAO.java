package mochi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoDAO {

    /**
     * Elimina detalles de compras donde el producto tiene stock suficiente (cantidad actual >= cantidad mínima).
     * Usa la conexión que se le pasa para incluir esta operación en la misma transacción.
     * No elimina la compra, aunque quede sin detalles.
     */
    public void limpiarPedidosConStockSuficiente(int idProducto, Connection con) throws SQLException {
        // Consultar la cantidad actual y mínima del producto
        int cantidadActual = 0;
        int cantidadMinima = 0;

        String sqlCantidades = "SELECT Cantidad_Actual, Cantidad_Minima FROM producto WHERE idProducto = ?";
        try (PreparedStatement ps = con.prepareStatement(sqlCantidades)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cantidadActual = rs.getInt("Cantidad_Actual");
                    cantidadMinima = rs.getInt("Cantidad_Minima");
                }
            }
        }

        // Si el stock es suficiente, eliminar solo los detalles relacionados con ese producto
        if (cantidadActual >= cantidadMinima) {
            // Obtener idCompra(s) asociados al producto en detalle_compra
            String sqlComprasAsociadas = "SELECT DISTINCT Compras_idCompras FROM detalle_compra WHERE Producto_idProducto = ?";
            try (PreparedStatement psCompras = con.prepareStatement(sqlComprasAsociadas)) {
                psCompras.setInt(1, idProducto);
                try (ResultSet rsCompras = psCompras.executeQuery()) {
                    while (rsCompras.next()) {
                        int idCompra = rsCompras.getInt("Compras_idCompras");

                        // Eliminar el detalle del producto (no la compra)
                        String sqlEliminarDetalle = "DELETE FROM detalle_compra WHERE Producto_idProducto = ? AND Compras_idCompras = ?";
                        try (PreparedStatement psEliminarDetalle = con.prepareStatement(sqlEliminarDetalle)) {
                            psEliminarDetalle.setInt(1, idProducto);
                            psEliminarDetalle.setInt(2, idCompra);
                            psEliminarDetalle.executeUpdate();
                        }


                    }
                }
            }
        }
    }
}
