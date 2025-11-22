package CRUD;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class EmpleadoController {

    @FXML private TableView<ATRIBUTOSEmpleado> tablaEmpleados;
    @FXML private TableColumn<ATRIBUTOSEmpleado, Integer> colId;
    @FXML private TableColumn<ATRIBUTOSEmpleado, String> colNombre;
    @FXML private TableColumn<ATRIBUTOSEmpleado, String> colApellido; // ✅ nuevo
    @FXML private TableColumn<ATRIBUTOSEmpleado, String> colPuesto;
    @FXML private TableColumn<ATRIBUTOSEmpleado, Float> colSalario;
    @FXML private TableColumn<ATRIBUTOSEmpleado, java.sql.Date> colFecha;

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido; // ✅ nuevo
    @FXML private TextField txtPuesto;
    @FXML private TextField txtSalario;
    @FXML private DatePicker dpFecha;

    private final METODOSEmpleados metodos = new METODOSEmpleados();
    private ATRIBUTOSEmpleado seleccionado;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cell -> cell.getValue().id_empleadoProperty().asObject());
        colNombre.setCellValueFactory(cell -> cell.getValue().nombreProperty());
        colApellido.setCellValueFactory(cell -> cell.getValue().apellidoProperty()); // ✅ nuevo
        colPuesto.setCellValueFactory(cell -> cell.getValue().puestoProperty());
        colSalario.setCellValueFactory(cell -> cell.getValue().salarioProperty().asObject());
        colFecha.setCellValueFactory(cell -> cell.getValue().fecha_ingresoProperty());

        cargarTabla();
    }

    private void cargarTabla() {
        ObservableList<ATRIBUTOSEmpleado> lista = metodos.listarEmpleados();
        tablaEmpleados.setItems(lista);
    }

    @FXML
    private void guardarEmpleado() {
        if (validarCampos()) {
            ATRIBUTOSEmpleado e = new ATRIBUTOSEmpleado();
            e.setNombre(txtNombre.getText());
            e.setApellido(txtApellido.getText()); // ✅ nuevo
            e.setPuesto(txtPuesto.getText());
            e.setSalario(Float.parseFloat(txtSalario.getText()));
            e.setFecha_ingreso(java.sql.Date.valueOf(dpFecha.getValue()));

            metodos.insertarEmpleado(e);
            cargarTabla();
            limpiarCampos();
            mostrarInfo("Empleado agregado correctamente.");
        }
    }

    @FXML
    private void actualizarEmpleado() {
        if (seleccionado != null && validarCampos()) {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setApellido(txtApellido.getText()); // ✅ nuevo
            seleccionado.setPuesto(txtPuesto.getText());
            seleccionado.setSalario(Float.parseFloat(txtSalario.getText()));
            seleccionado.setFecha_ingreso(java.sql.Date.valueOf(dpFecha.getValue()));

            if (metodos.updateEmpleado(seleccionado)) {
                cargarTabla();
                limpiarCampos();
                mostrarInfo("Empleado actualizado correctamente.");
            } else {
                mostrarAlerta("No se pudo actualizar el empleado.");
            }
        }
    }

    @FXML
    private void eliminarEmpleado() {
        if (seleccionado != null) {
            if (metodos.eliminarEmpleado(seleccionado.getId_empleado())) {
                cargarTabla();
                limpiarCampos();
                mostrarInfo("Empleado eliminado correctamente.");
            } else {
                mostrarAlerta("No se pudo eliminar el empleado.");
            }
        }
    }

    @FXML
    private void seleccionarEmpleado(MouseEvent event) {
        seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtNombre.setText(seleccionado.getNombre());
            txtApellido.setText(seleccionado.getApellido()); // ✅ nuevo
            txtPuesto.setText(seleccionado.getPuesto());
            txtSalario.setText(String.valueOf(seleccionado.getSalario()));
            if (seleccionado.getFecha_ingreso() != null) {
                dpFecha.setValue(seleccionado.getFecha_ingreso().toLocalDate());
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear(); // ✅ nuevo
        txtPuesto.clear();
        txtSalario.clear();
        dpFecha.setValue(null);
        tablaEmpleados.getSelectionModel().clearSelection();
        seleccionado = null;
    }

    /** Validación básica de campos obligatorios */
    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtPuesto.getText().isEmpty()) {
            mostrarAlerta("Nombre, apellido y puesto son obligatorios.");
            return false;
        }
        try {
            Float.parseFloat(txtSalario.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("El salario debe ser numérico.");
            return false;
        }
        if (dpFecha.getValue() == null) {
            mostrarAlerta("La fecha de ingreso es obligatoria.");
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