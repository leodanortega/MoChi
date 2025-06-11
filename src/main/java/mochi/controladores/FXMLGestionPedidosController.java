package mochi.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mochi.dao.ProductoDAO;
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
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Pedido");
        alerta.setHeaderText(null);
        alerta.setContentText("Funcionalidad de realizar pedido aún no implementada.");
        alerta.showAndWait();
    }
}
