package CRUD;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class EmpleadoController {

    @FXML private TableView<ATRIBUTOSEmpleado> tablaEmpleados;
    @FXML private TableColumn<ATRIBUTOSEmpleado, Integer> colId;
    @FXML private TableColumn<ATRIBUTOSEmpleado, String> colNombre;
    @FXML private TableColumn<ATRIBUTOSEmpleado, String> colPuesto;
    @FXML private TableColumn<ATRIBUTOSEmpleado, Float> colSalario;
    @FXML private TableColumn<ATRIBUTOSEmpleado, java.sql.Date> colFecha;

    @FXML private TextField txtNombre;
    @FXML private TextField txtPuesto;
    @FXML private TextField txtSalario;
    @FXML private DatePicker dpFecha;

    private final METODOSEmpleados metodos = new METODOSEmpleados();
    private ATRIBUTOSEmpleado seleccionado;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cell -> cell.getValue().id_empleadoProperty().asObject());
        colNombre.setCellValueFactory(cell -> cell.getValue().nombreProperty());
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
        ATRIBUTOSEmpleado e = new ATRIBUTOSEmpleado();
        e.setNombre(txtNombre.getText());
        e.setPuesto(txtPuesto.getText());
        e.setSalario(Float.parseFloat(txtSalario.getText()));
        e.setFecha_ingreso(java.sql.Date.valueOf(dpFecha.getValue()));

        metodos.insertarEmpleado(e);
        cargarTabla();
        limpiarCampos();
    }

    @FXML
    private void actualizarEmpleado() {
        if (seleccionado != null) {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setPuesto(txtPuesto.getText());
            seleccionado.setSalario(Float.parseFloat(txtSalario.getText()));
            seleccionado.setFecha_ingreso(java.sql.Date.valueOf(dpFecha.getValue()));

            metodos.updateEmpleado(seleccionado);
            cargarTabla();
            limpiarCampos();
        }
    }

    @FXML
    private void eliminarEmpleado() {
        if (seleccionado != null) {
            metodos.eliminarEmpleado(seleccionado.getId_empleado());
            cargarTabla();
            limpiarCampos();
        }
    }

    @FXML
    private void seleccionarEmpleado(MouseEvent event) {
        seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtNombre.setText(seleccionado.getNombre());
            txtPuesto.setText(seleccionado.getPuesto());
            txtSalario.setText(String.valueOf(seleccionado.getSalario()));
            dpFecha.setValue(seleccionado.getFecha_ingreso().toLocalDate());
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtPuesto.clear();
        txtSalario.clear();
        dpFecha.setValue(null);
        seleccionado = null;
    }
}