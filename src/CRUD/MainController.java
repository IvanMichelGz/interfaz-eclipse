package CRUD;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private void mostrarMateriales() throws IOException {
        cargarVista("MaterialView.fxml");
    }

    @FXML
    private void mostrarEmpleados() throws IOException {
        cargarVista("EmpleadoView.fxml");
    }

    @FXML
    private void mostrarClientes() throws IOException {
        cargarVista("ClienteView.fxml");
    }

    private void cargarVista(String fxml) throws IOException {
        Parent vista = FXMLLoader.load(getClass().getResource(fxml));
        mainPane.setCenter(vista);
    }
    
    @FXML
    private void mostrarVenta() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("VentaView.fxml"));
        mainPane.setCenter(root);
    }
}