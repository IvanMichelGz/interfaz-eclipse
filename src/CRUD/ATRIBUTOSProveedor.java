package CRUD;

import javafx.beans.property.*;

public class ATRIBUTOSProveedor {
    private final IntegerProperty id_proveedor = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty direccion = new SimpleStringProperty();
    private final StringProperty telefono = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public int getId_proveedor() { return id_proveedor.get(); }
    public void setId_proveedor(int value) { id_proveedor.set(value); }
    public IntegerProperty id_proveedorProperty() { return id_proveedor; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty nombreProperty() { return nombre; }

    public String getDireccion() { return direccion.get(); }
    public void setDireccion(String value) { direccion.set(value); }
    public StringProperty direccionProperty() { return direccion; }

    public String getTelefono() { return telefono.get(); }
    public void setTelefono(String value) { telefono.set(value); }
    public StringProperty telefonoProperty() { return telefono; }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public StringProperty emailProperty() { return email; }
}