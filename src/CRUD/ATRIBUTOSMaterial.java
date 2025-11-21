package CRUD;

import javafx.beans.property.*;
import java.sql.Date;

public class ATRIBUTOSMaterial {

    private final IntegerProperty id_material = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty marca = new SimpleStringProperty();
    private final StringProperty area = new SimpleStringProperty();
    private final StringProperty unidad = new SimpleStringProperty();
    private final IntegerProperty cantidad = new SimpleIntegerProperty();
    private final DoubleProperty precio = new SimpleDoubleProperty(); // ✅ cambiado a DoubleProperty
    private final ObjectProperty<Date> fecha_ingreso = new SimpleObjectProperty<>();
    private final ObjectProperty<Date> fecha_caducidad = new SimpleObjectProperty<>();
    private final StringProperty ubicacion = new SimpleStringProperty();

    // ID
    public int getId_material() { return id_material.get(); }
    public void setId_material(int value) { id_material.set(value); }
    public IntegerProperty id_materialProperty() { return id_material; }

    // Nombre
    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty nombreProperty() { return nombre; }

    // Marca
    public String getMarca() { return marca.get(); }
    public void setMarca(String value) { marca.set(value); }
    public StringProperty marcaProperty() { return marca; }

    // Área
    public String getArea() { return area.get(); }
    public void setArea(String value) { area.set(value); }
    public StringProperty areaProperty() { return area; }

    // Unidad
    public String getUnidad() { return unidad.get(); }
    public void setUnidad(String value) { unidad.set(value); }
    public StringProperty unidadProperty() { return unidad; }

    // Cantidad
    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int value) { cantidad.set(value); }
    public IntegerProperty cantidadProperty() { return cantidad; }

    // Precio
    public double getPrecio() { return precio.get(); }
    public void setPrecio(double value) { precio.set(value); }
    public DoubleProperty precioProperty() { return precio; }

    // Fecha ingreso
    public Date getFecha_ingreso() { return fecha_ingreso.get(); }
    public void setFecha_ingreso(Date value) { fecha_ingreso.set(value); }
    public ObjectProperty<Date> fecha_ingresoProperty() { return fecha_ingreso; }

    // Fecha caducidad
    public Date getFecha_caducidad() { return fecha_caducidad.get(); }
    public void setFecha_caducidad(Date value) { fecha_caducidad.set(value); }
    public ObjectProperty<Date> fecha_caducidadProperty() { return fecha_caducidad; }

    // Ubicación
    public String getUbicacion() { return ubicacion.get(); }
    public void setUbicacion(String value) { ubicacion.set(value); }
    public StringProperty ubicacionProperty() { return ubicacion; }

    @Override
    public String toString() {
        return id_material.get() + " - " + nombre.get() + " (" + marca.get() + ")";
    }
}