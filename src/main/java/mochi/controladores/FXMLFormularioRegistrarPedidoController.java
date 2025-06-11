package mochi.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import mochi.dao.CompraDAO;
import mochi.dao.ProovedorDAO;
import mochi.modelo.pojo.DetalleCompra;
import mochi.modelo.pojo.Proovedor;

import java.util.List;

public class FXMLFormularioRegistrarPedidoController {

    @FXML private Label lblNombreProducto;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtPrecioCompra;
    @FXML private ComboBox<Proovedor> cmbProveedor;

    private int idProducto;
    private int idCompra;

    private ObservableList<Proovedor> listaProveedores;

    public void inicializarDatos(int idProducto, String nombreProducto, int idCompra) {
        this.idProducto = idProducto;
        this.idCompra = idCompra;
        lblNombreProducto.setText(nombreProducto);
        cargarProveedores();
    }

    private void cargarProveedores() {
        ProovedorDAO dao = new ProovedorDAO();
        List<Proovedor> proveedores = dao.obtenerProveedores();
        if (proveedores != null && !proveedores.isEmpty()) {
            listaProveedores = FXCollections.observableArrayList(proveedores);
            cmbProveedor.setItems(listaProveedores);
        } else {
            mostrarAlerta("No se encontraron proveedores. Verifica la base de datos.");
        }
    }

    @FXML
    private void confirmarPedido() {
        try {
            String cantidadStr = txtCantidad.getText().trim();
            String precioStr = txtPrecioCompra.getText().trim();

            if (cantidadStr.isEmpty() || precioStr.isEmpty()) {
                mostrarAlerta("Todos los campos deben estar completos.");
                return;
            }

            int cantidad = Integer.parseInt(cantidadStr);
            double precio = Double.parseDouble(precioStr);
            Proovedor proveedorSeleccionado = cmbProveedor.getValue();

            if (proveedorSeleccionado == null) {
                mostrarAlerta("Debes seleccionar un proveedor.");
                return;
            }

            if (cantidad <= 0 || precio <= 0) {
                mostrarAlerta("La cantidad y el precio deben ser mayores a cero.");
                return;
            }

            DetalleCompra detalle = new DetalleCompra(idCompra, idProducto, cantidad, precio);
            detalle.setIdProveedor(proveedorSeleccionado.getIdProovedor());

            CompraDAO dao = new CompraDAO();

            boolean exito = dao.agregarDetalle(detalle);

            if (exito) {
                double nuevoTotal = dao.obtenerTotalPorCompra(idCompra);
                boolean actualizado = dao.actualizarCompraProveedorTotal(idCompra, proveedorSeleccionado.getIdProovedor(), nuevoTotal);

                if (!actualizado) {
                    mostrarAlerta("Error al actualizar la compra con proveedor y total.", Alert.AlertType.WARNING);
                } else {
                    mostrarAlerta("Pedido registrado con éxito.", Alert.AlertType.INFORMATION);
                    // Ya no se cierra automáticamente la ventana
                }

            } else {
                mostrarAlerta("No se pudo registrar el pedido. Verifica los datos.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("La cantidad y el precio deben ser valores numéricos.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        mostrarAlerta(mensaje, Alert.AlertType.WARNING);
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Mensaje");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
