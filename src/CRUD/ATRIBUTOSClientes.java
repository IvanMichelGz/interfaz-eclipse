package CRUD;

import javafx.beans.property.*;

public class ATRIBUTOSClientes {
    private final IntegerProperty id_cliente = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty apellido = new SimpleStringProperty();
    private final StringProperty telefono = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public int getId_cliente() { return id_cliente.get(); }
    public void setId_cliente(int value) { id_cliente.set(value); }
    public IntegerProperty id_clienteProperty() { return id_cliente; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty nombreProperty() { return nombre; }

    public String getApellido() { return apellido.get(); }
    public void setApellido(String value) { apellido.set(value); }
    public StringProperty apellidoProperty() { return apellido; }

    public String getTelefono() { return telefono.get(); }
    public void setTelefono(String value) { telefono.set(value); }
    public StringProperty telefonoProperty() { return telefono; }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public StringProperty emailProperty() { return email; }

    @Override
    public String toString() {
        return id_cliente.get() + " - " + nombre.get() + " " + apellido.get();
    }
}