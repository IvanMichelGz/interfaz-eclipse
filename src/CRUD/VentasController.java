package CRUD;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class VentasController {
	@FXML private TextField txtCliente;
	@FXML private TextField txtEmpleado;
	@FXML private TextField txtTotal;
	@FXML private DatePicker dpFecha;

    @FXML private TableView<ATRIBUTOSVenta> tablaVentas;
    @FXML private TableColumn<ATRIBUTOSVenta, Integer> colIdVenta;
    @FXML private TableColumn<ATRIBUTOSVenta, java.sql.Date> colFecha;
    @FXML private TableColumn<ATRIBUTOSVenta, Integer> colCliente;
    @FXML private TableColumn<ATRIBUTOSVenta, Integer> colEmpleado;
    @FXML private TableColumn<ATRIBUTOSVenta, Double> colTotal;

    @FXML private TableView<ATRIBUTOSDetalleVenta> tablaDetalle;
    @FXML private TableColumn<ATRIBUTOSDetalleVenta, Integer> colIdDetalle;
    @FXML private TableColumn<ATRIBUTOSDetalleVenta, Integer> colIdMaterial;
    @FXML private TableColumn<ATRIBUTOSDetalleVenta, Integer> colCantidad;
    @FXML private TableColumn<ATRIBUTOSDetalleVenta, Float> colPrecioUnitario;
    @FXML private TableColumn<ATRIBUTOSDetalleVenta, Float> colSubtotal;

    @FXML private TextField txtIdMaterial, txtCantidad, txtPrecioUnitario;

    private final METODOSVenta metodos = new METODOSVenta();
    private ATRIBUTOSVenta ventaSeleccionada;
    private ATRIBUTOSDetalleVenta detalleSeleccionado;

    @FXML
    public void initialize() {
        // Configurar columnas cabecera
        colIdVenta.setCellValueFactory(cell -> cell.getValue().id_ventaProperty().asObject());
        colFecha.setCellValueFactory(cell -> cell.getValue().fechaProperty());
        colCliente.setCellValueFactory(cell -> cell.getValue().id_clienteProperty().asObject());
        colEmpleado.setCellValueFactory(cell -> cell.getValue().id_empleadoProperty().asObject());
        colTotal.setCellValueFactory(cell -> cell.getValue().totalProperty().asObject());

        // Configurar columnas detalle
        colIdDetalle.setCellValueFactory(cell -> cell.getValue().id_detalleProperty().asObject());
        colIdMaterial.setCellValueFactory(cell -> cell.getValue().id_materialProperty().asObject());
        colCantidad.setCellValueFactory(cell -> cell.getValue().cantidadProperty().asObject());
        colPrecioUnitario.setCellValueFactory(cell -> cell.getValue().precio_unitarioProperty().asObject());
        colSubtotal.setCellValueFactory(cell -> cell.getValue().subtotalProperty().asObject());

        cargarVentas();
    }
    
    private void cargarVentas() {
        ObservableList<ATRIBUTOSVenta> lista = metodos.listarVentas();
        tablaVentas.setItems(lista);
    }

    private void cargarDetalle(int idVenta) {
        ObservableList<ATRIBUTOSDetalleVenta> lista = metodos.listarDetalleVenta(idVenta);
        tablaDetalle.setItems(lista);
    }

    // --- Métodos para cabecera de venta ---
    @FXML
    private void insertarVenta() {
        // Aquí defines cómo crear una nueva venta
        ATRIBUTOSVenta v = new ATRIBUTOSVenta();
        // setea los atributos necesarios (fecha, cliente, empleado, total)
        metodos.insertarVenta(v);
        cargarVentas();
    }

    @FXML
    private void actualizarVenta() {
        ventaSeleccionada = tablaVentas.getSelectionModel().getSelectedItem();
        if (ventaSeleccionada != null) {
            // Tomar los nuevos valores de los campos
            ventaSeleccionada.setId_cliente(Integer.parseInt(txtCliente.getText()));
            ventaSeleccionada.setId_empleado(Integer.parseInt(txtEmpleado.getText()));
            ventaSeleccionada.setTotal(Double.parseDouble(txtTotal.getText()));
            ventaSeleccionada.setFecha(java.sql.Date.valueOf(dpFecha.getValue()));

            // Llamar al método de actualización
            metodos.updateVenta(ventaSeleccionada);

            // Refrescar la tabla
            cargarVentas();
        }
    }



    @FXML
    private void eliminarVenta() {
        ventaSeleccionada = tablaVentas.getSelectionModel().getSelectedItem();
        if (ventaSeleccionada != null) {
            metodos.eliminarVenta(ventaSeleccionada.getId_venta());
            cargarVentas();
        }
    }

    // --- Métodos para detalle de venta ---
    @FXML
    private void insertarDetalle() {
        ventaSeleccionada = tablaVentas.getSelectionModel().getSelectedItem();
        if (ventaSeleccionada != null) {
            ATRIBUTOSDetalleVenta d = new ATRIBUTOSDetalleVenta();
            d.setId_venta(ventaSeleccionada.getId_venta());
            d.setId_material(Integer.parseInt(txtIdMaterial.getText()));
            d.setCantidad(Integer.parseInt(txtCantidad.getText()));
            d.setPrecio_unitario(Float.parseFloat(txtPrecioUnitario.getText()));
            d.setSubtotal(d.getCantidad() * d.getPrecio_unitario());

            metodos.insertarDetalleVenta(d);
            cargarDetalle(ventaSeleccionada.getId_venta());
        }
    }

    @FXML
    private void eliminarDetalle() {
        detalleSeleccionado = tablaDetalle.getSelectionModel().getSelectedItem();
        if (detalleSeleccionado != null) {
            metodos.eliminarDetalleVenta(detalleSeleccionado.getId_detalle());
            cargarDetalle(detalleSeleccionado.getId_venta());
        }
    }
}