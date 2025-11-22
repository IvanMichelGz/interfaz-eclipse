package CRUD;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class VentasController {

    @FXML private TextField txtCliente;
    @FXML private TextField txtEmpleado;
    @FXML private TextField txtMaterial;
    @FXML private TextField txtCantidad;
    @FXML private DatePicker dpFecha;

    @FXML private TableView<ATRIBUTOSVenta> tablaVentas;
    @FXML private TableColumn<ATRIBUTOSVenta, Integer> colIdVenta;
    @FXML private TableColumn<ATRIBUTOSVenta, String> colCliente;
    @FXML private TableColumn<ATRIBUTOSVenta, String> colEmpleado;
    @FXML private TableColumn<ATRIBUTOSVenta, String> colMaterial;
    @FXML private TableColumn<ATRIBUTOSVenta, Double> colPrecio;
    @FXML private TableColumn<ATRIBUTOSVenta, Integer> colCantidad;
    @FXML private TableColumn<ATRIBUTOSVenta, java.sql.Date> colFecha;
    @FXML private TableColumn<ATRIBUTOSVenta, Double> colSubtotal;
    @FXML private TableColumn<ATRIBUTOSVenta, Double> colTotal;

    private final METODOSVenta metodos = new METODOSVenta();
    private final METODOSMaterial metodosMaterial = new METODOSMaterial();
    private final METODOSClientes metodosClientes = new METODOSClientes();
    private final METODOSEmpleados metodosEmpleados = new METODOSEmpleados();

    private ATRIBUTOSVenta ventaSeleccionada;

    @FXML
    public void initialize() {
        colIdVenta.setCellValueFactory(cell -> cell.getValue().id_ventaProperty().asObject());
        colCliente.setCellValueFactory(cell -> cell.getValue().nombre_clienteProperty());
        colEmpleado.setCellValueFactory(cell -> cell.getValue().nombre_empleadoProperty());
        colMaterial.setCellValueFactory(cell -> cell.getValue().nombre_materialProperty());
        colPrecio.setCellValueFactory(cell -> cell.getValue().precio_unitarioProperty().asObject());
        colCantidad.setCellValueFactory(cell -> cell.getValue().cantidadProperty().asObject());
        colFecha.setCellValueFactory(cell -> cell.getValue().fechaProperty());
        colSubtotal.setCellValueFactory(cell -> cell.getValue().subtotalProperty().asObject());
        colTotal.setCellValueFactory(cell -> cell.getValue().totalProperty().asObject());

        cargarVentas();
    }

    private void cargarVentas() {
        ObservableList<ATRIBUTOSVenta> lista = metodos.listarVentas();
        tablaVentas.setItems(lista);
    }

    @FXML
    private void insertarVenta() {
        if (validarVenta()) {
            ATRIBUTOSVenta v = new ATRIBUTOSVenta();

            v.setNombre_cliente(txtCliente.getText());
            v.setNombre_empleado(txtEmpleado.getText());
            v.setNombre_material(txtMaterial.getText());

            v.setId_cliente(metodosClientes.buscarIdClientePorNombre(txtCliente.getText()));
            v.setId_empleado(metodosEmpleados.buscarIdEmpleadoPorNombre(txtEmpleado.getText()));
            v.setId_material(metodosMaterial.buscarIdPorNombre(txtMaterial.getText()));

            // Calcula precio, subtotal y total automáticamente
            double precio = metodosMaterial.buscarPrecioPorNombre(txtMaterial.getText());
            v.setPrecio_unitario(precio);

            int cantidad = Integer.parseInt(txtCantidad.getText());
            v.setCantidad(cantidad);

            double subtotal = precio * cantidad;
            v.setSubtotal(subtotal);
            v.setTotal(subtotal);

            v.setFecha(java.sql.Date.valueOf(dpFecha.getValue()));

            metodos.insertarVenta(v);
            cargarVentas();
            limpiarCamposVenta();
            mostrarInfo("Venta registrada correctamente.");
        }
    }

    @FXML
    private void actualizarVenta() {
        ventaSeleccionada = tablaVentas.getSelectionModel().getSelectedItem();
        if (ventaSeleccionada != null && validarVenta()) {
            ventaSeleccionada.setNombre_cliente(txtCliente.getText());
            ventaSeleccionada.setNombre_empleado(txtEmpleado.getText());
            ventaSeleccionada.setNombre_material(txtMaterial.getText());

            ventaSeleccionada.setId_cliente(metodosClientes.buscarIdClientePorNombre(txtCliente.getText()));
            ventaSeleccionada.setId_empleado(metodosEmpleados.buscarIdEmpleadoPorNombre(txtEmpleado.getText()));
            ventaSeleccionada.setId_material(metodosMaterial.buscarIdPorNombre(txtMaterial.getText()));

            double precio = metodosMaterial.buscarPrecioPorNombre(txtMaterial.getText());
            ventaSeleccionada.setPrecio_unitario(precio);

            int cantidad = Integer.parseInt(txtCantidad.getText());
            ventaSeleccionada.setCantidad(cantidad);

            double subtotal = precio * cantidad;
            ventaSeleccionada.setSubtotal(subtotal);
            ventaSeleccionada.setTotal(subtotal);

            ventaSeleccionada.setFecha(java.sql.Date.valueOf(dpFecha.getValue()));

            if (metodos.updateVenta(ventaSeleccionada)) {
                cargarVentas();
                limpiarCamposVenta();
                mostrarInfo("Venta actualizada correctamente.");
            } else {
                mostrarAlerta("No se pudo actualizar la venta.");
            }
        }
    }

    @FXML
    private void eliminarVenta() {
        ventaSeleccionada = tablaVentas.getSelectionModel().getSelectedItem();
        if (ventaSeleccionada != null) {
            if (metodos.eliminarVenta(ventaSeleccionada.getId_venta())) {
                cargarVentas();
                limpiarCamposVenta();
                mostrarInfo("Venta eliminada correctamente.");
            } else {
                mostrarAlerta("No se pudo eliminar la venta.");
            }
        }
    }

    private void limpiarCamposVenta() {
        txtCliente.clear();
        txtEmpleado.clear();
        txtMaterial.clear();
        txtCantidad.clear();
        dpFecha.setValue(null);
        tablaVentas.getSelectionModel().clearSelection();
        ventaSeleccionada = null;
    }

    private boolean validarVenta() {
        if (txtCliente.getText().isEmpty() || txtEmpleado.getText().isEmpty() || txtMaterial.getText().isEmpty()) {
            mostrarAlerta("Cliente, empleado y material son obligatorios.");
            return false;
        }
        if (txtCantidad.getText().isEmpty()) {
            mostrarAlerta("La cantidad es obligatoria.");
            return false;
        }
        try {
            Integer.parseInt(txtCantidad.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("La cantidad debe ser numérica.");
            return false;
        }
        if (dpFecha.getValue() == null) {
            mostrarAlerta("La fecha es obligatoria.");
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

    private void mostrarInfo(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}