package CRUD;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private Button btnMateriales;
    @FXML
    private Button btnEmpleados;
    @FXML
    private Button btnProveedores;
    @FXML
    private Button btnVentas;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnClientes;

    @FXML
    private StackPane contentPane; // contenedor central definido en MainView.fxml

    @FXML
    private void initialize() {
        // Al iniciar, carga la vista de Materiales por defecto
        cargarVista("MaterialView.fxml");
    }

    @FXML
    private void mostrarMateriales(ActionEvent event) {
        cargarVista("MaterialView.fxml");
    }

    @FXML
    private void mostrarEmpleados(ActionEvent event) {
        cargarVista("EmpleadoView.fxml");
    }

    @FXML
    private void mostrarProveedores(ActionEvent event) {
        cargarVista("ProveedorView.fxml");
    }

    @FXML
    private void mostrarVentas(ActionEvent event) {
        cargarVista("VentaView.fxml");
    }

    @FXML
    private void mostrarClientes(ActionEvent event) {
        cargarVista("ClienteView.fxml");
    }

    @FXML
    private void salirAplicacion(ActionEvent event) {
        btnSalir.getScene().getWindow().hide();
    }

    /** Método para reemplazar el contenido central */
    private void cargarVista(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent vista = loader.load();
            contentPane.getChildren().setAll(vista); // reemplaza el contenido
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}