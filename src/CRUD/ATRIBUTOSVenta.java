package CRUD;

import javafx.beans.property.*;
import java.sql.Date;

public class ATRIBUTOSVenta {
    // --- Cabecera de la venta ---
    private final IntegerProperty id_venta = new SimpleIntegerProperty();
    private final ObjectProperty<Date> fecha = new SimpleObjectProperty<>();
    private final IntegerProperty id_cliente = new SimpleIntegerProperty();
    private final StringProperty nombre_cliente = new SimpleStringProperty();
    private final IntegerProperty id_empleado = new SimpleIntegerProperty();
    private final StringProperty nombre_empleado = new SimpleStringProperty();

    // --- Relación con Material ---
    private final IntegerProperty id_material = new SimpleIntegerProperty();
    private final StringProperty nombre_material = new SimpleStringProperty();

    // --- Datos de la venta ---
    private final IntegerProperty cantidad = new SimpleIntegerProperty();
    private final DoubleProperty precio_unitario = new SimpleDoubleProperty();
    private final DoubleProperty subtotal = new SimpleDoubleProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();

    // --- ID Venta ---
    public int getId_venta() { return id_venta.get(); }
    public void setId_venta(int value) { id_venta.set(value); }
    public IntegerProperty id_ventaProperty() { return id_venta; }

    // --- Fecha ---
    public Date getFecha() { return fecha.get(); }
    public void setFecha(Date value) { fecha.set(value); }
    public ObjectProperty<Date> fechaProperty() { return fecha; }

    // --- Cliente ---
    public int getId_cliente() { return id_cliente.get(); }
    public void setId_cliente(int value) { id_cliente.set(value); }
    public IntegerProperty id_clienteProperty() { return id_cliente; }

    public String getNombre_cliente() { return nombre_cliente.get(); }
    public void setNombre_cliente(String value) { nombre_cliente.set(value); }
    public StringProperty nombre_clienteProperty() { return nombre_cliente; }

    // --- Empleado ---
    public int getId_empleado() { return id_empleado.get(); }
    public void setId_empleado(int value) { id_empleado.set(value); }
    public IntegerProperty id_empleadoProperty() { return id_empleado; }

    public String getNombre_empleado() { return nombre_empleado.get(); }
    public void setNombre_empleado(String value) { nombre_empleado.set(value); }
    public StringProperty nombre_empleadoProperty() { return nombre_empleado; }

    // --- Material ---
    public int getId_material() { return id_material.get(); }
    public void setId_material(int value) { id_material.set(value); }
    public IntegerProperty id_materialProperty() { return id_material; }

    public String getNombre_material() { return nombre_material.get(); }
    public void setNombre_material(String value) { nombre_material.set(value); }
    public StringProperty nombre_materialProperty() { return nombre_material; }

    // --- Cantidad ---
    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int value) { cantidad.set(value); }
    public IntegerProperty cantidadProperty() { return cantidad; }

    // --- Precio Unitario ---
    public double getPrecio_unitario() { return precio_unitario.get(); }
    public void setPrecio_unitario(double value) { precio_unitario.set(value); }
    public DoubleProperty precio_unitarioProperty() { return precio_unitario; }

    // --- Subtotal ---
    public double getSubtotal() { return subtotal.get(); }
    public void setSubtotal(double value) { subtotal.set(value); }
    public DoubleProperty subtotalProperty() { return subtotal; }

    // --- Total ---
    public double getTotal() { return total.get(); }
    public void setTotal(double value) { total.set(value); }
    public DoubleProperty totalProperty() { return total; }

    // --- Cálculo automático ---
    public void calcularSubtotal() {
        setSubtotal(getCantidad() * getPrecio_unitario());
    }

    @Override
    public String toString() {
        return "Venta #" + getId_venta() +
               " | Cliente: " + getNombre_cliente() +
               " | Empleado: " + getNombre_empleado() +
               " | Material: " + getNombre_material() +
               " | Cantidad: " + getCantidad() +
               " | Precio: " + getPrecio_unitario() +
               " | Subtotal: " + getSubtotal() +
               " | Total: " + getTotal();
    }
}