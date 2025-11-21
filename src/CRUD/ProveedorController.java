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

    private METODOSProveedor metodos = new METODOSProveedor();
    private ObservableList<ATRIBUTOSProveedor> lista;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(data -> data.getValue().id_proveedorProperty());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colDireccion.setCellValueFactory(data -> data.getValue().direccionProperty());
        colTelefono.setCellValueFactory(data -> data.getValue().telefonoProperty());
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());

        lista = metodos.listarProveedores();
        tablaProveedores.setItems(lista);
    }

    @FXML
    private void agregarProveedor() {
        ATRIBUTOSProveedor p = new ATRIBUTOSProveedor();
        p.setNombre(txtNombre.getText());
        p.setDireccion(txtDireccion.getText());
        p.setTelefono(txtTelefono.getText());
        p.setEmail(txtEmail.getText());

        metodos.insertarProveedor(p);
        recargarTabla();
        limpiarCampos();
    }

    @FXML
    private void actualizarProveedor() {
        ATRIBUTOSProveedor seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setDireccion(txtDireccion.getText());
            seleccionado.setTelefono(txtTelefono.getText());
            seleccionado.setEmail(txtEmail.getText());

            metodos.updateProveedor(seleccionado);
            recargarTabla();
            limpiarCampos();
        }
    }

    @FXML
    private void eliminarProveedor() {
        ATRIBUTOSProveedor seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            metodos.eliminarProveedor(seleccionado.getId_proveedor());
            recargarTabla();
            limpiarCampos();
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
    }
}