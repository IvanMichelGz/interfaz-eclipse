package CRUD;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Date;

public class MaterialController {

    @FXML private TableView<ATRIBUTOSMaterial> tablaMateriales;
    @FXML private TableColumn<ATRIBUTOSMaterial, Integer> colId;
    @FXML private TableColumn<ATRIBUTOSMaterial, String> colNombre;
    @FXML private TableColumn<ATRIBUTOSMaterial, String> colMarca;
    @FXML private TableColumn<ATRIBUTOSMaterial, String> colArea;
    @FXML private TableColumn<ATRIBUTOSMaterial, String> colUnidad;
    @FXML private TableColumn<ATRIBUTOSMaterial, Integer> colCantidad;
    @FXML private TableColumn<ATRIBUTOSMaterial, Float> colPrecio;
    @FXML private TableColumn<ATRIBUTOSMaterial, Date> colIngreso;
    @FXML private TableColumn<ATRIBUTOSMaterial, Date> colCaducidad;
    @FXML private TableColumn<ATRIBUTOSMaterial, String> colUbicacion;

    @FXML private TextField txtNombre, txtMarca, txtArea, txtUnidad, txtCantidad, txtPrecio, txtUbicacion;
    @FXML private DatePicker dpIngreso, dpCaducidad;

    private final METODOSMaterial metodos = new METODOSMaterial();
    private ATRIBUTOSMaterial seleccionado;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cell -> cell.getValue().id_materialProperty().asObject());
        colNombre.setCellValueFactory(cell -> cell.getValue().nombreProperty());
        colMarca.setCellValueFactory(cell -> cell.getValue().marcaProperty());
        colArea.setCellValueFactory(cell -> cell.getValue().areaProperty());
        colUnidad.setCellValueFactory(cell -> cell.getValue().unidadProperty());
        colCantidad.setCellValueFactory(cell -> cell.getValue().cantidadProperty().asObject());
        colPrecio.setCellValueFactory(cell -> cell.getValue().precioProperty().asObject());
        colIngreso.setCellValueFactory(cell -> cell.getValue().fecha_ingresoProperty());
        colCaducidad.setCellValueFactory(cell -> cell.getValue().fecha_caducidadProperty());
        colUbicacion.setCellValueFactory(cell -> cell.getValue().ubicacionProperty());

        cargarTabla();
    }

    private void cargarTabla() {
        ObservableList<ATRIBUTOSMaterial> lista = metodos.listarMateriales();
        tablaMateriales.setItems(lista);
    }

    @FXML
    private void insertar() {
        if (validarCampos()) {
            ATRIBUTOSMaterial m = new ATRIBUTOSMaterial();
            m.setNombre(txtNombre.getText());
            m.setMarca(txtMarca.getText());
            m.setArea(txtArea.getText());
            m.setUnidad(txtUnidad.getText());
            m.setCantidad(Integer.parseInt(txtCantidad.getText()));
            m.setPrecio(Float.parseFloat(txtPrecio.getText()));
            m.setFecha_ingreso(Date.valueOf(dpIngreso.getValue()));
            m.setFecha_caducidad(Date.valueOf(dpCaducidad.getValue()));
            m.setUbicacion(txtUbicacion.getText());

            metodos.insertarMaterial(m);
            cargarTabla();
            limpiar();
        }
    }

    @FXML
    private void actualizar() {
        if (seleccionado != null && validarCampos()) {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setMarca(txtMarca.getText());
            seleccionado.setArea(txtArea.getText());
            seleccionado.setUnidad(txtUnidad.getText());
            seleccionado.setCantidad(Integer.parseInt(txtCantidad.getText()));
            seleccionado.setPrecio(Float.parseFloat(txtPrecio.getText()));
            seleccionado.setFecha_ingreso(Date.valueOf(dpIngreso.getValue()));
            seleccionado.setFecha_caducidad(Date.valueOf(dpCaducidad.getValue()));
            seleccionado.setUbicacion(txtUbicacion.getText());

            metodos.updateMaterial(seleccionado);
            cargarTabla();
            limpiar();
        }
    }

    @FXML
    private void eliminar() {
        if (seleccionado != null) {
            metodos.eliminarMaterial(seleccionado.getId_material());
            cargarTabla();
            limpiar();
        }
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtMarca.clear();
        txtArea.clear();
        txtUnidad.clear();
        txtCantidad.clear();
        txtPrecio.clear();
        txtUbicacion.clear();
        dpIngreso.setValue(null);
        dpCaducidad.setValue(null);
        seleccionado = null;
        tablaMateriales.getSelectionModel().clearSelection();
    }

    @FXML
    private void seleccionarMaterial(MouseEvent event) {
        seleccionado = tablaMateriales.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtNombre.setText(seleccionado.getNombre());
            txtMarca.setText(seleccionado.getMarca());
            txtArea.setText(seleccionado.getArea());
            txtUnidad.setText(seleccionado.getUnidad());
            txtCantidad.setText(String.valueOf(seleccionado.getCantidad()));
            txtPrecio.setText(String.valueOf(seleccionado.getPrecio()));
            dpIngreso.setValue(seleccionado.getFecha_ingreso().toLocalDate());
            dpCaducidad.setValue(seleccionado.getFecha_caducidad().toLocalDate());
            txtUbicacion.setText(seleccionado.getUbicacion());
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtMarca.getText().isEmpty() || txtArea.getText().isEmpty() ||
            txtUnidad.getText().isEmpty() || txtCantidad.getText().isEmpty() || txtPrecio.getText().isEmpty() ||
            dpIngreso.getValue() == null || dpCaducidad.getValue() == null || txtUbicacion.getText().isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return false;
        }
        try {
            Integer.parseInt(txtCantidad.getText());
            Float.parseFloat(txtPrecio.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Cantidad y precio deben ser numéricos.");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Validación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}