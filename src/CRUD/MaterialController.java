package CRUD;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class MaterialController {

    @FXML private TableView<ATRIBUTOSMaterial> tablaMateriales;
    @FXML private TableColumn<ATRIBUTOSMaterial, Integer> colId;
    @FXML private TableColumn<ATRIBUTOSMaterial, String> colNombre;
    @FXML private TableColumn<ATRIBUTOSMaterial, String> colMarca;
    @FXML private TableColumn<ATRIBUTOSMaterial, String> colArea;
    @FXML private TableColumn<ATRIBUTOSMaterial, String> colUnidad;
    @FXML private TableColumn<ATRIBUTOSMaterial, Integer> colCantidad;
    @FXML private TableColumn<ATRIBUTOSMaterial, Double> colPrecio;
    @FXML private TableColumn<ATRIBUTOSMaterial, java.sql.Date> colFechaIngreso;
    @FXML private TableColumn<ATRIBUTOSMaterial, java.sql.Date> colFechaCaducidad;
    @FXML private TableColumn<ATRIBUTOSMaterial, String> colUbicacion;

    @FXML private TextField txtNombre, txtMarca, txtArea, txtUnidad, txtCantidad, txtPrecio, txtUbicacion;
    @FXML private DatePicker dpFechaIngreso, dpFechaCaducidad;

    private final METODOSMaterial metodos = new METODOSMaterial();
    private ATRIBUTOSMaterial seleccionado;

    @FXML
    public void initialize() {
        // Configuración de columnas con propiedades del modelo
        colId.setCellValueFactory(cell -> cell.getValue().id_materialProperty().asObject());
        colNombre.setCellValueFactory(cell -> cell.getValue().nombreProperty());
        colMarca.setCellValueFactory(cell -> cell.getValue().marcaProperty());
        colArea.setCellValueFactory(cell -> cell.getValue().areaProperty());
        colUnidad.setCellValueFactory(cell -> cell.getValue().unidadProperty());
        colCantidad.setCellValueFactory(cell -> cell.getValue().cantidadProperty().asObject());
        colPrecio.setCellValueFactory(cell -> cell.getValue().precioProperty().asObject());
        colFechaIngreso.setCellValueFactory(cell -> cell.getValue().fecha_ingresoProperty());
        colFechaCaducidad.setCellValueFactory(cell -> cell.getValue().fecha_caducidadProperty());
        colUbicacion.setCellValueFactory(cell -> cell.getValue().ubicacionProperty());

        cargarTabla();
    }

    /** Carga todos los materiales desde la BD en la tabla */
    private void cargarTabla() {
        ObservableList<ATRIBUTOSMaterial> lista = metodos.listarMateriales();
        tablaMateriales.setItems(lista);
    }

    /** Inserta un nuevo material */
    @FXML
    private void guardarMaterial() {
        if (validarCampos()) {
            ATRIBUTOSMaterial m = new ATRIBUTOSMaterial();
            m.setNombre(txtNombre.getText());
            m.setMarca(txtMarca.getText());
            m.setArea(txtArea.getText());
            m.setUnidad(txtUnidad.getText());
            m.setCantidad(Integer.parseInt(txtCantidad.getText()));
            m.setPrecio(Double.parseDouble(txtPrecio.getText()));
            m.setFecha_ingreso(java.sql.Date.valueOf(dpFechaIngreso.getValue()));
            m.setFecha_caducidad(java.sql.Date.valueOf(dpFechaCaducidad.getValue()));
            m.setUbicacion(txtUbicacion.getText());

            metodos.insertarMaterial(m);
            cargarTabla();
            limpiarCampos();
            mostrarInfo("Material agregado correctamente.");
        }
    }

    /** Actualiza el material seleccionado */
    @FXML
    private void actualizarMaterial() {
        if (seleccionado != null && validarCampos()) {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setMarca(txtMarca.getText());
            seleccionado.setArea(txtArea.getText());
            seleccionado.setUnidad(txtUnidad.getText());
            seleccionado.setCantidad(Integer.parseInt(txtCantidad.getText()));
            seleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
            seleccionado.setFecha_ingreso(java.sql.Date.valueOf(dpFechaIngreso.getValue()));
            seleccionado.setFecha_caducidad(java.sql.Date.valueOf(dpFechaCaducidad.getValue()));
            seleccionado.setUbicacion(txtUbicacion.getText());

            if (metodos.updateMaterial(seleccionado)) {
                cargarTabla();
                limpiarCampos();
                mostrarInfo("Material actualizado correctamente.");
            } else {
                mostrarAlerta("No se pudo actualizar el material.");
            }
        }
    }

    /** Elimina el material seleccionado */
    @FXML
    private void eliminarMaterial() {
        if (seleccionado != null) {
            if (metodos.eliminarMaterial(seleccionado.getId_material())) {
                cargarTabla();
                limpiarCampos();
                mostrarInfo("Material eliminado correctamente.");
            } else {
                mostrarAlerta("No se pudo eliminar el material.");
            }
        }
    }

    /** Selecciona un material de la tabla y carga sus datos en los campos */
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
            if (seleccionado.getFecha_ingreso() != null) {
                dpFechaIngreso.setValue(seleccionado.getFecha_ingreso().toLocalDate());
            }
            if (seleccionado.getFecha_caducidad() != null) {
                dpFechaCaducidad.setValue(seleccionado.getFecha_caducidad().toLocalDate());
            }
            txtUbicacion.setText(seleccionado.getUbicacion());
        }
    }

    /** Limpia los campos de texto y la selección */
    private void limpiarCampos() {
        txtNombre.clear();
        txtMarca.clear();
        txtArea.clear();
        txtUnidad.clear();
        txtCantidad.clear();
        txtPrecio.clear();
        txtUbicacion.clear();
        dpFechaIngreso.setValue(null);
        dpFechaCaducidad.setValue(null);
        tablaMateriales.getSelectionModel().clearSelection();
        seleccionado = null;
    }

    /** Validación básica de campos obligatorios */
    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtMarca.getText().isEmpty()) {
            mostrarAlerta("Nombre y marca son obligatorios.");
            return false;
        }
        try {
            Integer.parseInt(txtCantidad.getText());
            Double.parseDouble(txtPrecio.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Cantidad y precio deben ser numéricos.");
            return false;
        }
        if (dpFechaIngreso.getValue() == null || dpFechaCaducidad.getValue() == null) {
            mostrarAlerta("Las fechas son obligatorias.");
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