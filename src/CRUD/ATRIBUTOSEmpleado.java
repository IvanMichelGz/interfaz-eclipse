package CRUD;

import javafx.beans.property.*;
import java.sql.Date;

public class ATRIBUTOSEmpleado {

    private IntegerProperty id_empleado;
    private StringProperty nombre;
    private StringProperty apellido;   // ✅ nuevo atributo
    private StringProperty puesto;
    private FloatProperty salario;
    private ObjectProperty<Date> fecha_ingreso;

    public ATRIBUTOSEmpleado() {
        this.id_empleado = new SimpleIntegerProperty();
        this.nombre = new SimpleStringProperty();
        this.apellido = new SimpleStringProperty(); // ✅ inicializado
        this.puesto = new SimpleStringProperty();
        this.salario = new SimpleFloatProperty();
        this.fecha_ingreso = new SimpleObjectProperty<>();
    }

    // --- ID ---
    public int getId_empleado() { return id_empleado.get(); }
    public void setId_empleado(int value) { id_empleado.set(value); }
    public IntegerProperty id_empleadoProperty() { return id_empleado; }

    // --- Nombre ---
    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty nombreProperty() { return nombre; }

    // --- Apellido ---
    public String getApellido() { return apellido.get(); }
    public void setApellido(String value) { apellido.set(value); }
    public StringProperty apellidoProperty() { return apellido; }

    // --- Puesto ---
    public String getPuesto() { return puesto.get(); }
    public void setPuesto(String value) { puesto.set(value); }
    public StringProperty puestoProperty() { return puesto; }

    // --- Salario ---
    public float getSalario() { return salario.get(); }
    public void setSalario(float value) { salario.set(value); }
    public FloatProperty salarioProperty() { return salario; }

    // --- Fecha ingreso ---
    public Date getFecha_ingreso() { return fecha_ingreso.get(); }
    public void setFecha_ingreso(Date value) { fecha_ingreso.set(value); }
    public ObjectProperty<Date> fecha_ingresoProperty() { return fecha_ingreso; }

    @Override
    public String toString() {
        return id_empleado.get() + " - " + nombre.get() + " " + apellido.get() + " (" + puesto.get() + ")";
    }
}