package CRUD;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VentasController {

    @FXML private TableView<ItemVenta> tablaCarrito;
    @FXML private TableColumn<ItemVenta, Integer> colId;
    @FXML private TableColumn<ItemVenta, String> colNombre;
    @FXML private TableColumn<ItemVenta, Integer> colCantidad;
    @FXML private TableColumn<ItemVenta, Double> colPrecio;
    @FXML private TableColumn<ItemVenta, Double> colSubtotal;

    @FXML private TextField txtProducto;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtPrecio;
    @FXML private Label labelTotal;

    private ObservableList<ItemVenta> carrito = FXCollections.observableArrayList();
    private int contadorId = 1;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colCantidad.setCellValueFactory(data -> data.getValue().cantidadProperty().asObject());
        colPrecio.setCellValueFactory(data -> data.getValue().precioProperty().asObject());
        colSubtotal.setCellValueFactory(data -> data.getValue().subtotalProperty().asObject());

        tablaCarrito.setItems(carrito);
    }

    @FXML
    private void agregarAlCarrito() {
        try {
            String nombre = txtProducto.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            double subtotal = cantidad * precio;

            ItemVenta item = new ItemVenta(contadorId++, nombre, cantidad, precio, subtotal);
            carrito.add(item);
            actualizarTotal();

            txtProducto.clear();
            txtCantidad.clear();
            txtPrecio.clear();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de entrada");
            alert.setHeaderText("Datos inválidos");
            alert.setContentText("Por favor ingresa números válidos en Cantidad y Precio.");
            alert.showAndWait();
        }
    }

    @FXML
    private void finalizarVenta() {
        if (carrito.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Venta vacía");
            alert.setHeaderText(null);
            alert.setContentText("No hay productos en el carrito.");
            alert.showAndWait();
            return;
        }

        // Aquí puedes generar el PDF con los datos del carrito
        System.out.println("Venta finalizada. Generando comprobante...");
        carrito.clear();
        contadorId = 1;
        actualizarTotal();
    }

    @FXML
    private void cancelarVenta() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Cancelar venta");
        confirm.setHeaderText("¿Seguro que deseas cancelar la venta?");
        confirm.setContentText("Se perderán todos los productos del carrito.");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            carrito.clear();
            contadorId = 1;
            actualizarTotal();
        }
    }

    private void actualizarTotal() {
        double total = carrito.stream().mapToDouble(ItemVenta::getSubtotal).sum();
        labelTotal.setText(String.format("$%.2f", total));
    }

    @FXML
    private void seleccionarVenta(MouseEvent event) {
        ItemVenta seleccionado = tablaCarrito.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtProducto.setText(seleccionado.getNombre());
            txtCantidad.setText(String.valueOf(seleccionado.getCantidad()));
            txtPrecio.setText(String.valueOf(seleccionado.getPrecio()));
        }
    }
}