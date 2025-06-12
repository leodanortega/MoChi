package mochi.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mochi.dao.reportes.ProductoVentaDAO;
import mochi.dao.reportes.ClienteProductoDAO;
import mochi.dao.reportes.ClienteProductoTotalDAO;
import mochi.modelo.pojo.reportes.ProductoVenta;
import mochi.modelo.pojo.reportes.ClienteProducto;
import mochi.modelo.pojo.reportes.ClienteProductoTotal;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class FXMLpruebaController implements Initializable {

    // TAB 1: Productos vendidos
    @FXML
    private TableView<ProductoVenta> tvProductosVenta;
    @FXML
    private TableColumn<ProductoVenta, String> productoNombre;
    @FXML
    private TableColumn<ProductoVenta, Integer> cantidadVendida;
    @FXML
    private TabPane tabPane;

    // TAB 2: Clientes con productos
    @FXML
    private TableView<ClienteProducto> tvClienteProducto;
    @FXML
    private TableColumn<ClienteProducto, String> colNombreCliente;
    @FXML
    private TableColumn<ClienteProducto, String> colNombreProducto;

    // TAB 3: Bebida más comprada por cliente
    @FXML
    private TableView<ClienteProductoTotal> tvBebidaMasComprada;
    @FXML
    private TableColumn<ClienteProductoTotal, String> colClienteBebida;
    @FXML
    private TableColumn<ClienteProductoTotal, String> colProductoBebida;
    @FXML
    private TableColumn<ClienteProductoTotal, Integer> colTotalComprado;

    // TAB 4: Bebida menos comprada por cliente
    @FXML
    private TableView<ClienteProductoTotal> tvBebidaMenosComprada;
    @FXML
    private TableColumn<ClienteProductoTotal, String> colClienteBebidaMenos;
    @FXML
    private TableColumn<ClienteProductoTotal, String> colProductoBebidaMenos;
    @FXML
    private TableColumn<ClienteProductoTotal, Integer> colTotalCompradoMenos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TAB 1 setup
        productoNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        cantidadVendida.setCellValueFactory(new PropertyValueFactory<>("totalVendido"));
        cargarProductosVendidos();

        // TAB 2 setup
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        cargarClienteProducto();

        // TAB 3 setup
        colClienteBebida.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colProductoBebida.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colTotalComprado.setCellValueFactory(new PropertyValueFactory<>("totalComprado"));
        cargarbebidamascomprada();

        // TAB 4 setup
        colClienteBebidaMenos.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colProductoBebidaMenos.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colTotalCompradoMenos.setCellValueFactory(new PropertyValueFactory<>("totalComprado"));
        cargarbebidamenoscomprada();
    }

    private void cargarProductosVendidos() {
        List<ProductoVenta> lista = ProductoVentaDAO.obtenerProductosVendidos();
        tvProductosVenta.getItems().setAll(lista);
    }

    private void cargarClienteProducto() {
        List<ClienteProducto> lista = ClienteProductoDAO.obtenerClientesConProductos();
        tvClienteProducto.getItems().setAll(lista);
    }

    private void cargarbebidamascomprada() {
        List<ClienteProductoTotal> lista = ClienteProductoTotalDAO.obtenerBebidaMasomenosCompradaPorCliente(true);
        tvBebidaMasComprada.getItems().setAll(lista);
    }
    
    private void cargarbebidamenoscomprada() {
        List<ClienteProductoTotal> lista = ClienteProductoTotalDAO.obtenerBebidaMasomenosCompradaPorCliente(false);
        tvBebidaMenosComprada.getItems().setAll(lista);
    }

    @FXML
    private void btnRegresar(ActionEvent event) {
        Stage stage = (Stage) tvProductosVenta.getScene().getWindow();
        stage.close();
    }


}
