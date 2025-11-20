package CRUD;

import javafx.beans.property.*;

public class ATRIBUTOSCarrito {
    private final StringProperty nombreMaterial = new SimpleStringProperty();
    private final IntegerProperty cantidad = new SimpleIntegerProperty();
    private final FloatProperty precioUnitario = new SimpleFloatProperty();
    private final FloatProperty subtotal = new SimpleFloatProperty();
    private final IntegerProperty id_material = new SimpleIntegerProperty();

    public String getNombreMaterial() { return nombreMaterial.get(); }
    public void setNombreMaterial(String value) { nombreMaterial.set(value); }
    public StringProperty nombreMaterialProperty() { return nombreMaterial; }

    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int value) { cantidad.set(value); }
    public IntegerProperty cantidadProperty() { return cantidad; }

    public float getPrecioUnitario() { return precioUnitario.get(); }
    public void setPrecioUnitario(float value) { precioUnitario.set(value); }
    public FloatProperty precioUnitarioProperty() { return precioUnitario; }

    public float getSubtotal() { return subtotal.get(); }
    public void setSubtotal(float value) { subtotal.set(value); }
    public FloatProperty subtotalProperty() { return subtotal; }

    public int getId_material() { return id_material.get(); }
    public void setId_material(int value) { id_material.set(value); }
    public IntegerProperty id_materialProperty() { return id_material; }
}