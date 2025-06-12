package mochi.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mochi.dao.ProductoDAO;
import mochi.dao.PedidoDAO;
import mochi.modelo.pojo.Producto;

public class FXMLFormularioRegistrarEntradasProductoController {

    @FXML private Label lblNombreProducto;
    @FXML private Label lblPresentacionProducto;
    @FXML private TextField tfCantidadRecibida;

    private Producto producto;

    /**
     * Inicializa los datos del producto que será actualizado.
     */
    public void inicializarProducto(Producto producto) {
        if (producto != null) {
            this.producto = producto;
            lblNombreProducto.setText(producto.getNombre());
            lblPresentacionProducto.setText(producto.getPresentacion());
        }
    }

    @FXML
    private void guardarEntrada() {
        String cantidadStr = tfCantidadRecibida.getText().trim();

        if (cantidadStr.isEmpty()) {
            mostrarAlerta("Campo vacío", "Por favor, ingresa la cantidad recibida.");
            return;
        }

        int cantidadIngresada;

        try {
            cantidadIngresada = Integer.parseInt(cantidadStr);
            if (cantidadIngresada <= 0) {
                mostrarAlerta("Cantidad inválida", "La cantidad debe ser mayor que cero.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Formato incorrecto", "La cantidad ingresada debe ser un número entero.");
            return;
        }

        // Actualizar la cantidad actual del producto
        int nuevaCantidad = producto.getCantidadActual() + cantidadIngresada;
        producto.setCantidadActual(nuevaCantidad);

        ProductoDAO productoDAO = new ProductoDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();

        // Actualizar stock y limpiar pedidos dentro de una transacción
        boolean exito = productoDAO.actualizarStockYLimpiarPedidos(producto, pedidoDAO);

        if (exito) {
            cerrarVentana();
        } else {
            mostrarAlerta("Error al guardar", "No se pudo actualizar la cantidad ni limpiar pedidos.");
        }
    }

    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfCantidadRecibida.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
