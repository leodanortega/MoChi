package mochi.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mochi.dao.CompraDAO;
import mochi.dao.ProductoDAO;
import mochi.modelo.pojo.Compra;
import mochi.modelo.pojo.Producto;

import java.util.List;
import java.util.stream.Collectors;

public class FXMLGestionPedidosController {

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colPresentacion;

    @FXML
    private TableColumn<Producto, Double> colCosto;

    @FXML
    private TableColumn<Producto, Integer> colCantidadActual;

    @FXML
    private TableColumn<Producto, Integer> colCantidadMinima;

    @FXML
    private TextField txtBuscar;

    private ObservableList<Producto> productos;
    private ProductoDAO productoDAO;

    @FXML
    public void initialize() {
        productoDAO = new ProductoDAO();
        configurarTabla();
        cargarProductosFaltantes();
        configurarBusqueda();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        colPresentacion.setCellValueFactory(new PropertyValueFactory<>("Presentacion"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("Costo"));
        colCantidadActual.setCellValueFactory(new PropertyValueFactory<>("CantidadActual"));
        colCantidadMinima.setCellValueFactory(new PropertyValueFactory<>("CantidadMinima"));
    }

    private void cargarProductosFaltantes() {
        List<Producto> lista = productoDAO.listarProductosFaltantes();
        productos = FXCollections.observableArrayList(lista);
        tablaProductos.setItems(productos);
    }

    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                tablaProductos.setItems(productos);
            } else {
                String filtro = newValue.toLowerCase();
                List<Producto> filtrados = productos.stream()
                        .filter(p -> p.getNombre().toLowerCase().contains(filtro))
                        .collect(Collectors.toList());
                tablaProductos.setItems(FXCollections.observableArrayList(filtrados));
            }
        });
    }

    @FXML
    public void regresarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) tablaProductos.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void realizarPedido(ActionEvent actionEvent) {
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null) {
            // alerta de que seleccione producto
            return;
        }

        CompraDAO compraDAO = new CompraDAO();
        Compra compra = new Compra();
        compra.setTotal(0); // Inicial 0
        compra.setIdProveedor(1); // o un valor neutro, pues el proveedor se definirá en el formulario

        int idCompra = compraDAO.crearCompraSinDetalles(compra);

        if (idCompra > 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioRegistrarPedido.fxml"));
                Parent root = loader.load();

                FXMLFormularioRegistrarPedidoController controller = loader.getController();
                controller.inicializarDatos(productoSeleccionado.getIdProducto(), productoSeleccionado.getNombre(), idCompra);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Registrar Pedido");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

                cargarProductosFaltantes();
            } catch (Exception e) {
                // Manejar excepción
            }
        } else {
            // Mostrar error al crear compra
        }
    }

}
