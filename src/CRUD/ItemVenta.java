package CRUD;

import javafx.beans.property.*;

public class ItemVenta {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final IntegerProperty cantidad;
    private final DoubleProperty precio;
    private final DoubleProperty subtotal;

    public ItemVenta(int id, String nombre, int cantidad, double precio, double subtotal) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.precio = new SimpleDoubleProperty(precio);
        this.subtotal = new SimpleDoubleProperty(subtotal);
    }

    // --- ID ---
    public IntegerProperty idProperty() { return id; }
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }

    // --- Nombre (Producto) ---
    public StringProperty nombreProperty() { return nombre; }
    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }

    // --- Cantidad ---
    public IntegerProperty cantidadProperty() { return cantidad; }
    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int cantidad) { this.cantidad.set(cantidad); }

    // --- Precio ---
    public DoubleProperty precioProperty() { return precio; }
    public double getPrecio() { return precio.get(); }
    public void setPrecio(double precio) { this.precio.set(precio); }

    // --- Subtotal ---
    public DoubleProperty subtotalProperty() { return subtotal; }
    public double getSubtotal() { return subtotal.get(); }
    public void setSubtotal(double subtotal) { this.subtotal.set(subtotal); }
}