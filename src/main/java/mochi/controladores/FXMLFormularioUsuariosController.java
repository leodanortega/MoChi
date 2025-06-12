package mochi.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mochi.dao.UsuarioDAO;
import mochi.modelo.pojo.Usuario;

public class FXMLFormularioUsuariosController {

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfApellidoPaterno;

    @FXML
    private TextField tfApellidoMaterno;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private ComboBox<String> cbTipo;

    private UsuarioDAO usuarioDAO;

    private Usuario usuarioEditar;

    private ObservableList<String> tiposUsuarios = FXCollections.observableArrayList(
            "Administrador",  // tipo 1
            "Empleado"    // tipo 2
    );

    @FXML
    public void initialize() {
        usuarioDAO = new UsuarioDAO();
        cbTipo.setItems(tiposUsuarios);
    }

    public void setUsuarioEditar(Usuario u) {
        this.usuarioEditar = u;
        if (u != null) {
            tfNombre.setText(u.getNombre());
            tfApellidoPaterno.setText(u.getApellidoPaterno());
            tfApellidoMaterno.setText(u.getApellidoMaterno());
            tfUsername.setText(u.getUsername());
            pfPassword.setText(u.getPassword());
            int tipoIndex = u.getTipo() - 1;
            if (tipoIndex >= 0 && tipoIndex < tiposUsuarios.size()) {
                cbTipo.getSelectionModel().select(tipoIndex);
            } else {
                cbTipo.getSelectionModel().clearSelection();
            }
        }
    }

    @FXML
    private void guardarUsuario(ActionEvent event) {
        String nombre = tfNombre.getText().trim();
        String apellidoPaterno = tfApellidoPaterno.getText().trim();
        String apellidoMaterno = tfApellidoMaterno.getText().trim();
        String username = tfUsername.getText().trim();
        String password = pfPassword.getText();
        int tipoSeleccionado = cbTipo.getSelectionModel().getSelectedIndex() + 1; // +1 porque tipos empiezan en 1

        if (nombre.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty()
                || username.isEmpty() || password.isEmpty() || tipoSeleccionado == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, completa todos los campos y selecciona un tipo.");
            alert.showAndWait();
            return;
        }

        if (usuarioEditar == null) {
            // Nuevo usuario
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setApellidoPaterno(apellidoPaterno);
            u.setApellidoMaterno(apellidoMaterno);
            u.setUsername(username);
            u.setPassword(password);
            u.setTipo(tipoSeleccionado);

            boolean exito = usuarioDAO.agregar(u);
            if (exito) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Usuario agregado");
                alert.setHeaderText(null);
                alert.setContentText("Usuario agregado correctamente.");
                alert.showAndWait();
                cerrarVentana();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo agregar el usuario.");
                alert.showAndWait();
            }
        } else {
            // Editar usuario existente
            usuarioEditar.setNombre(nombre);
            usuarioEditar.setApellidoPaterno(apellidoPaterno);
            usuarioEditar.setApellidoMaterno(apellidoMaterno);
            usuarioEditar.setUsername(username);
            usuarioEditar.setPassword(password);
            usuarioEditar.setTipo(tipoSeleccionado);

            boolean exito = usuarioDAO.modificar(usuarioEditar);
            if (exito) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Usuario modificado");
                alert.setHeaderText(null);
                alert.setContentText("Usuario modificado correctamente.");
                alert.showAndWait();
                cerrarVentana();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo modificar el usuario.");
                alert.showAndWait();
            }
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
}
