package mochi.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mochi.dao.UsuarioDAO;
import mochi.modelo.pojo.Usuario;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FXMLGestionUsuariosController {

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, String> colNombre;

    @FXML
    private TableColumn<Usuario, String> colApellidoPaterno;

    @FXML
    private TableColumn<Usuario, String> colApellidoMaterno;

    @FXML
    private TableColumn<Usuario, String> colUsername;

    @FXML
    private TableColumn<Usuario, String> colTipo;

    @FXML
    private TextField txtBuscar;

    private UsuarioDAO usuarioDAO;

    @FXML
    public void initialize() {
        usuarioDAO = new UsuarioDAO();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoString"));

        colTipo.setMinWidth(100);  // Opcional: mejor visibilidad

        obtenerUsuarios();

        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                obtenerUsuarios();
            } else {
                buscarUsuarios(newVal);
            }
        });
    }

    private void obtenerUsuarios() {
        List<Usuario> lista = usuarioDAO.listar();
        ObservableList<Usuario> observableList = FXCollections.observableArrayList(lista);
        tablaUsuarios.setItems(observableList);
    }

    private void buscarUsuarios(String textoBusqueda) {
        List<Usuario> lista = usuarioDAO.buscarPorNombre(textoBusqueda);
        ObservableList<Usuario> observableList = FXCollections.observableArrayList(lista);
        tablaUsuarios.setItems(observableList);
    }

    @FXML
    public void regresarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) tablaUsuarios.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void agregarUsuario(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioUsuarios.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Agregar Usuario");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            obtenerUsuarios();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaError("Error al abrir el formulario de usuario.");
        }
    }

    @FXML
    public void modificarUsuario(ActionEvent actionEvent) {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlertaWarning("Selecciona un usuario para modificar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioUsuarios.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Modificar Usuario");
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLFormularioUsuariosController controlador = loader.getController();
            controlador.setUsuarioEditar(seleccionado);

            stage.showAndWait();
            obtenerUsuarios();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaError("Error al abrir el formulario de usuario.");
        }
    }

    @FXML
    public void eliminarUsuario(ActionEvent actionEvent) {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlertaWarning("Selecciona un usuario para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de eliminar el usuario seleccionado?");
        Optional<ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean eliminado = usuarioDAO.eliminar(seleccionado.getIdUsuario());
            if (eliminado) {
                mostrarAlertaInfo("Usuario eliminado correctamente.");
                obtenerUsuarios();
            } else {
                mostrarAlertaError("No se pudo eliminar el usuario.");
            }
        }
    }

    private void mostrarAlertaInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaWarning(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
