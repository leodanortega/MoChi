package mochi.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mochi.dao.ProductoDAO;
import mochi.modelo.pojo.Producto;

public class FXMLFormularioProductosController {

    @FXML private TextField tfNombre;
    @FXML private TextField tfPresentacion;
    @FXML private TextField tfCosto;
    @FXML private TextField tfCantidadActual;
    @FXML private TextField tfCantidadMinima;

    private Producto productoExistente = null;

    // Este método puede ser llamado por la ventana padre para editar un producto
    public void inicializarFormulario(Producto producto) {
        if (producto != null) {
            productoExistente = producto;
            tfNombre.setText(producto.getNombre());
            tfPresentacion.setText(producto.getPresentacion());
            tfCosto.setText(String.valueOf(producto.getCosto()));
            tfCantidadActual.setText(String.valueOf(producto.getCantidadActual()));
            tfCantidadMinima.setText(String.valueOf(producto.getCantidadMinima()));
        }
    }

    @FXML
    private void guardarProducto() {
        String nombre = tfNombre.getText().trim();
        String presentacion = tfPresentacion.getText().trim();
        String costoStr = tfCosto.getText().trim();
        String cantidadActualStr = tfCantidadActual.getText().trim();
        String cantidadMinimaStr = tfCantidadMinima.getText().trim();

        // Validación básica
        if (nombre.isEmpty() || presentacion.isEmpty() || costoStr.isEmpty()
                || cantidadActualStr.isEmpty() || cantidadMinimaStr.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor, llena todos los campos.");
            return;
        }

        double costo;
        int cantidadActual;
        int cantidadMinima;

        try {
            costo = Double.parseDouble(costoStr);
            cantidadActual = Integer.parseInt(cantidadActualStr);
            cantidadMinima = Integer.parseInt(cantidadMinimaStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "Asegúrate de que el costo sea un número decimal y las cantidades sean enteros.");
            return;
        }

        Producto nuevoProducto = new Producto(
                productoExistente != null ? productoExistente.getIdProducto() : 0,
                nombre, presentacion, costo, cantidadActual, cantidadMinima
        );

        boolean exito;
        if (productoExistente == null) {
            exito = new ProductoDAO().agregar(nuevoProducto);
        } else {
            exito = new ProductoDAO().modificar(nuevoProducto);
        }

        if (exito) {
            cerrarVentana();
        } else {
            mostrarAlerta("Error al guardar", "No se pudo guardar el producto.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
}

