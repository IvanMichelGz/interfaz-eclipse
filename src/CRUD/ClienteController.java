package CRUD;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class ClienteController {

    @FXML private TableView<ATRIBUTOSClientes> tablaClientes;
    @FXML private TableColumn<ATRIBUTOSClientes, Integer> colId;
    @FXML private TableColumn<ATRIBUTOSClientes, String> colNombre;
    @FXML private TableColumn<ATRIBUTOSClientes, String> colApellido;
    @FXML private TableColumn<ATRIBUTOSClientes, String> colTelefono;
    @FXML private TableColumn<ATRIBUTOSClientes, String> colEmail;

    @FXML private TextField txtNombre, txtApellido, txtTelefono, txtEmail;

    private final METODOSClientes metodos = new METODOSClientes();
    private ATRIBUTOSClientes seleccionado;

    @FXML
    public void initialize() {
        // Configuración de columnas con propiedades del modelo
        colId.setCellValueFactory(cell -> cell.getValue().id_clienteProperty().asObject());
        colNombre.setCellValueFactory(cell -> cell.getValue().nombreProperty());
        colApellido.setCellValueFactory(cell -> cell.getValue().apellidoProperty());
        colTelefono.setCellValueFactory(cell -> cell.getValue().telefonoProperty());
        colEmail.setCellValueFactory(cell -> cell.getValue().emailProperty());

        cargarTabla();
    }

    /** Carga todos los clientes desde la BD en la tabla */
    private void cargarTabla() {
        ObservableList<ATRIBUTOSClientes> lista = metodos.listarClientes();
        tablaClientes.setItems(lista);
    }

    /** Inserta un nuevo cliente */
    @FXML
    private void guardarCliente() {
        if (validarCampos()) {
            ATRIBUTOSClientes c = new ATRIBUTOSClientes();
            c.setNombre(txtNombre.getText());
            c.setApellido(txtApellido.getText());
            c.setTelefono(txtTelefono.getText());
            c.setEmail(txtEmail.getText());

            metodos.insertarCliente(c);
            cargarTabla();
            limpiarCampos();
            mostrarInfo("Cliente agregado correctamente.");
        }
    }

    /** Actualiza el cliente seleccionado */
    @FXML
    private void actualizarCliente() {
        if (seleccionado != null && validarCampos()) {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setApellido(txtApellido.getText());
            seleccionado.setTelefono(txtTelefono.getText());
            seleccionado.setEmail(txtEmail.getText());

            if (metodos.updateCliente(seleccionado)) {
                cargarTabla();
                limpiarCampos();
                mostrarInfo("Cliente actualizado correctamente.");
            } else {
                mostrarAlerta("No se pudo actualizar el cliente.");
            }
        }
    }

    /** Elimina el cliente seleccionado */
    @FXML
    private void eliminarCliente() {
        if (seleccionado != null) {
            if (metodos.eliminarCliente(seleccionado.getId_cliente())) {
                cargarTabla();
                limpiarCampos();
                mostrarInfo("Cliente eliminado correctamente.");
            } else {
                mostrarAlerta("No se pudo eliminar el cliente.");
            }
        }
    }

    /** Selecciona un cliente de la tabla y carga sus datos en los campos */
    @FXML
    private void seleccionarCliente(MouseEvent event) {
        seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtNombre.setText(seleccionado.getNombre());
            txtApellido.setText(seleccionado.getApellido());
            txtTelefono.setText(seleccionado.getTelefono());
            txtEmail.setText(seleccionado.getEmail());
        }
    }

    /** Limpia los campos de texto y la selección */
    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtEmail.clear();
        tablaClientes.getSelectionModel().clearSelection();
        seleccionado = null;
    }

    /** Validación básica de campos obligatorios */
    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty()) {
            mostrarAlerta("Nombre y apellido son obligatorios.");
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