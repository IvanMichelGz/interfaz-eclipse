package CRUD;

import javafx.beans.property.*;

public class ATRIBUTOSDetalleVenta {
    private final IntegerProperty id_detalle = new SimpleIntegerProperty();
    private final IntegerProperty id_venta = new SimpleIntegerProperty();
    private final IntegerProperty id_material = new SimpleIntegerProperty();
    private final IntegerProperty cantidad = new SimpleIntegerProperty();
    private final FloatProperty precio_unitario = new SimpleFloatProperty();
    private final FloatProperty subtotal = new SimpleFloatProperty();

    // Getters y setters
    public int getId_detalle() { return id_detalle.get(); }
    public void setId_detalle(int value) { id_detalle.set(value); }
    public IntegerProperty id_detalleProperty() { return id_detalle; }

    public int getId_venta() { return id_venta.get(); }
    public void setId_venta(int value) { id_venta.set(value); }
    public IntegerProperty id_ventaProperty() { return id_venta; }

    public int getId_material() { return id_material.get(); }
    public void setId_material(int value) { id_material.set(value); }
    public IntegerProperty id_materialProperty() { return id_material; }

    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int value) { cantidad.set(value); }
    public IntegerProperty cantidadProperty() { return cantidad; }

    public float getPrecio_unitario() { return precio_unitario.get(); }
    public void setPrecio_unitario(float value) { precio_unitario.set(value); }
    public FloatProperty precio_unitarioProperty() { return precio_unitario; }

    public float getSubtotal() { return subtotal.get(); }
    public void setSubtotal(float value) { subtotal.set(value); }
    public FloatProperty subtotalProperty() { return subtotal; }
}