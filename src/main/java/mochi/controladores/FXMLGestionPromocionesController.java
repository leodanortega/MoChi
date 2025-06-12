package mochi.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mochi.dao.PromocionDAO;
import mochi.modelo.pojo.Promocion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import mochi.conexionbd.Conexion;

public class FXMLGestionPromocionesController {

    @FXML
    private TableView<ObservableList<String>> tablaPromociones;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    private ObservableList<Promocion> promociones;

    private PromocionDAO promocionDAO;

    @FXML
    public void initialize() {
        promocionDAO = new PromocionDAO();

        cargarPromociones();
    }

    @FXML
    private void filtrarPromociones() {
        cargarPromociones();
    }

    void cargarPromociones() {
        tablaPromociones.getItems().clear();
        tablaPromociones.getColumns().clear();

        String sql = "SELECT p.idPromocion, c.Nombre AS Cliente, pr.Nombre AS Producto, " +
                "p.`Valor/Modificador`, p.Fecha_Inicio, p.Fecha_Fin " +
                "FROM promocion p " +
                "LEFT JOIN cliente c ON p.Cliente_idCliente = c.idCliente " +
                "LEFT JOIN producto pr ON p.Producto_idProducto = pr.idProducto " +
                "WHERE 1=1";

        String clienteFiltro = txtBuscar.getText();
        if (clienteFiltro != null && !clienteFiltro.isEmpty()) {
            sql += " AND c.Nombre LIKE ?";
        }

        try (Connection conn = Conexion.getConexion("administrador").getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            if (clienteFiltro != null && !clienteFiltro.isEmpty()) {
                pst.setString(1, "%" + clienteFiltro + "%");
            }

            ResultSet rs = pst.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();


            for (int i = 2; i <= cols; i++) {
                final int colIndex = i - 1;
                TableColumn<ObservableList<String>, String> col = new TableColumn<>(md.getColumnLabel(i));
                col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(colIndex)));
                tablaPromociones.getColumns().add(col);
            }

            ObservableList<ObservableList<String>> rows = FXCollections.observableArrayList();
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= cols; i++) {
                    row.add(rs.getString(i));
                }
                rows.add(row);
            }

            tablaPromociones.setItems(rows);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void regresarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) tablaPromociones.getScene().getWindow();
        stage.close();
    }

    public void agregarPromocion(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLAgregarPromocion.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Agregar Promoción");
            stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana anterior
            stage.showAndWait();

            cargarPromociones(); // recargar después de cerrar

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void modificarPromocion(ActionEvent actionEvent) {

    }*/

    public void eliminarPromocion(ActionEvent actionEvent) {
        // 1) Obtener la fila seleccionada
        ObservableList<String> fila = tablaPromociones.getSelectionModel().getSelectedItem();
        if (fila == null) {
            showAlert(Alert.AlertType.WARNING, "Selecciona primero una promoción para eliminar.");
            return;
        }

        // 2) Extraer el idPromocion (está en la primera columna)
        int idPromocion;
        try {
            idPromocion = Integer.parseInt(fila.get(0));
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID de promoción inválido.");
            return;
        }

        // 3) Confirmación
        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION,
                "¿Eliminar promoción?",
                ButtonType.YES, ButtonType.NO
        );
        confirm.setHeaderText(null);
        confirm.showAndWait();
        if (confirm.getResult() != ButtonType.YES) return;

        // 4) Ejecutar DELETE en la BD
        String sql = "DELETE FROM promocion WHERE idPromocion = ?";
        try (Connection conn = Conexion.getConexion("administrador").getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, idPromocion);
            int filas = pst.executeUpdate();
            if (filas == 1) {
                showAlert(Alert.AlertType.INFORMATION, "Promoción eliminada correctamente.");
                tablaPromociones.getItems().clear();
                cargarPromociones();  // recarga la tabla para reflejar el cambio

            } else {
                showAlert(Alert.AlertType.ERROR, "No se encontró la promoción especificada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al eliminar promoción");
        }
    }

    private void showAlert(Alert.AlertType alertType, String mensaje) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
