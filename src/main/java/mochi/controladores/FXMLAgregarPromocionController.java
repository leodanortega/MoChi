package mochi.controladores;

import mochi.conexionbd.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FXMLAgregarPromocionController implements Initializable {

    @FXML private ComboBox<String> cbCliente;
    @FXML private ComboBox<String> cbProducto;
    @FXML private Spinner<Integer> spValorModificador;
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarClientes();
        cargarProductos();
        // Spinner de -100 a 100, inicial 0
        SpinnerValueFactory.IntegerSpinnerValueFactory vf =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(-100, 100, 0);
        spValorModificador.setValueFactory(vf);

        // Iniciar en blanco
        cbCliente.getSelectionModel().clearSelection();
        cbProducto.getSelectionModel().clearSelection();
        dpFechaInicio.setValue(null);
        dpFechaFin.setValue(null);
    }

    private void cargarClientes() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("");  // opción vacía
        try (Connection conn = Conexion.getConexion("administrador").getConnection();
             PreparedStatement pst = conn.prepareStatement(
                     "SELECT idCliente, Nombre FROM cliente ORDER BY Nombre");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getInt("idCliente") + " - " + rs.getString("Nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cbCliente.setItems(list);
    }

    private void cargarProductos() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("");  // opción vacía
        try (Connection conn = Conexion.getConexion("administrador").getConnection();
             PreparedStatement pst = conn.prepareStatement(
                     "SELECT idProducto, Nombre FROM producto ORDER BY Nombre");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getInt("idProducto") + " - " + rs.getString("Nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cbProducto.setItems(list);
    }

    @FXML
    private void btnGuardar() {
        String cli = cbCliente.getValue();
        String prod = cbProducto.getValue();
        LocalDate fi = dpFechaInicio.getValue();
        LocalDate ff = dpFechaFin.getValue();
        int valor = spValorModificador.getValue();

        if (cli == null || cli.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Debe seleccionar un cliente.");
            return;
        }
        if (prod == null || prod.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Debe seleccionar un producto.");
            return;
        }
        if (fi == null || ff == null) {
            showAlert(Alert.AlertType.WARNING, "Debe seleccionar fecha inicio y fin.");
            return;
        }
        if (ff.isBefore(fi)) {
            showAlert(Alert.AlertType.WARNING, "La fecha fin no puede ser anterior a la inicio.");
            return;
        }

        if (valor <= 0 || valor > 100) {
            showAlert(Alert.AlertType.WARNING, "Solo puede ingresar valores entre 1 y 100");
            return;
        }

        int idCliente = Integer.parseInt(cli.split(" - ")[0]);
        int idProducto = Integer.parseInt(prod.split(" - ")[0]);
        Date fechaIni = Date.valueOf(fi);
        Date fechaFn  = Date.valueOf(ff);

        String sql = ""
                + "INSERT INTO promocion "
                + "(Cliente_idCliente, Producto_idProducto, `Valor/Modificador`, Fecha_Inicio, Fecha_Fin) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion("administrador").getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idCliente);
            pst.setInt(2, idProducto);
            pst.setInt(3, valor);
            pst.setDate(4, fechaIni);
            pst.setDate(5, fechaFn);

            int filas = pst.executeUpdate();
            if (filas == 1) {
                showAlert(Alert.AlertType.INFORMATION, "Promoción guardada correctamente.");
                cerrarVentana();
            } else {
                showAlert(Alert.AlertType.ERROR, "No se pudo guardar la promoción.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void btnCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage st = (Stage) cbCliente.getScene().getWindow();
        st.close();
    }

    private void showAlert(Alert.AlertType tipo, String msg) {
        Alert a = new Alert(tipo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}