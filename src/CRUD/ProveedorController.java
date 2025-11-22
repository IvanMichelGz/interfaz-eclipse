package CRUD;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProveedorController {

    @FXML private TableView<ATRIBUTOSProveedor> tablaProveedores;
    @FXML private TableColumn<ATRIBUTOSProveedor, Number> colId;
    @FXML private TableColumn<ATRIBUTOSProveedor, String> colNombre;
    @FXML private TableColumn<ATRIBUTOSProveedor, String> colDireccion;
    @FXML private TableColumn<ATRIBUTOSProveedor, String> colTelefono;
    @FXML private TableColumn<ATRIBUTOSProveedor, String> colEmail;

    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;

    private final METODOSProveedor metodos = new METODOSProveedor();
    private ObservableList<ATRIBUTOSProveedor> lista;
    private ATRIBUTOSProveedor seleccionado;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(data -> data.getValue().id_proveedorProperty());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colDireccion.setCellValueFactory(data -> data.getValue().direccionProperty());
        colTelefono.setCellValueFactory(data -> data.getValue().telefonoProperty());
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());

        recargarTabla();
    }

    @FXML
    private void agregarProveedor() {
        if (validarCampos()) {
            ATRIBUTOSProveedor p = new ATRIBUTOSProveedor();
            p.setNombre(txtNombre.getText());
            p.setDireccion(txtDireccion.getText());
            p.setTelefono(txtTelefono.getText());
            p.setEmail(txtEmail.getText());

            metodos.insertarProveedor(p);
            recargarTabla();
            limpiarCampos();
            mostrarInfo("Proveedor agregado correctamente.");
        }
    }

    @FXML
    private void actualizarProveedor() {
        seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado != null && validarCampos()) {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setDireccion(txtDireccion.getText());
            seleccionado.setTelefono(txtTelefono.getText());
            seleccionado.setEmail(txtEmail.getText());

            if (metodos.updateProveedor(seleccionado)) {
                recargarTabla();
                limpiarCampos();
                mostrarInfo("Proveedor actualizado correctamente.");
            } else {
                mostrarAlerta("No se pudo actualizar el proveedor.");
            }
        }
    }

    @FXML
    private void eliminarProveedor() {
        seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (metodos.eliminarProveedor(seleccionado.getId_proveedor())) {
                recargarTabla();
                limpiarCampos();
                mostrarInfo("Proveedor eliminado correctamente.");
            } else {
                mostrarAlerta("No se pudo eliminar el proveedor.");
            }
        }
    }

    private void recargarTabla() {
        lista = metodos.listarProveedores();
        tablaProveedores.setItems(lista);
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtEmail.clear();
        tablaProveedores.getSelectionModel().clearSelection();
        seleccionado = null;
    }

    /** Validación básica de campos obligatorios */
    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            mostrarAlerta("Nombre y dirección son obligatorios.");
            return false;
        }
        if (txtTelefono.getText().isEmpty()) {
            mostrarAlerta("El teléfono es obligatorio.");
            return false;
        }
        if (txtEmail.getText().isEmpty()) {
            mostrarAlerta("El correo electrónico es obligatorio.");
            return false;
        }
        return true;
    }

    /** Muestra una alerta de advertencia */
    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Validación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /** Muestra un mensaje informativo */
    private void mostrarInfo(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}