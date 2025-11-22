package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class METODOSEmpleados {

    private final String servidor = "jdbc:sqlserver://IVANMICHEL07XD:1433;"
            + "databaseName=EmpresaDB;"
            + "encrypt=true;"
            + "trustServerCertificate=true";
    private final String usuario = "sa";
    private final String clave = "Ivan123";

    /** Conexión a la base de datos */
    public Connection conexion() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(servidor, usuario, clave);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con el servidor");
            e.printStackTrace();
        }
        return null;
    }

    /** Listar empleados con apellido */
    public ObservableList<ATRIBUTOSEmpleado> listarEmpleados() {
        ObservableList<ATRIBUTOSEmpleado> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT 
                id_empleado, 
                nombre_empleado AS nombre, 
                apellido_empleado AS apellido, 
                cargo AS puesto, 
                salario, 
                fecha_contratacion AS fecha_ingreso 
            FROM empresadb.empleado
        """;

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ATRIBUTOSEmpleado e = new ATRIBUTOSEmpleado();
                e.setId_empleado(rs.getInt("id_empleado"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setPuesto(rs.getString("puesto"));
                e.setSalario(rs.getFloat("salario"));
                e.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                lista.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /** Insertar empleado con apellido */
    public void insertarEmpleado(ATRIBUTOSEmpleado e) {
        String sql = "INSERT INTO empresadb.empleado (nombre_empleado, apellido_empleado, cargo, salario, fecha_contratacion) VALUES (?,?,?,?,?)";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setString(3, e.getPuesto());
            ps.setFloat(4, e.getSalario());
            ps.setDate(5, e.getFecha_ingreso());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /** Actualizar empleado con apellido */
    public boolean updateEmpleado(ATRIBUTOSEmpleado e) {
        String sql = "UPDATE empresadb.empleado SET nombre_empleado=?, apellido_empleado=?, cargo=?, salario=?, fecha_contratacion=? WHERE id_empleado=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setString(3, e.getPuesto());
            ps.setFloat(4, e.getSalario());
            ps.setDate(5, e.getFecha_ingreso());
            ps.setInt(6, e.getId_empleado());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /** Eliminar empleado */
    public boolean eliminarEmpleado(int id) {
        String sql = "DELETE FROM empresadb.empleado WHERE id_empleado=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // ============================
    // Métodos extra para autocompletado
    // ============================

    /** Listar solo nombres de empleados (para autocompletado) */
    public List<String> listarNombresEmpleados() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nombre_empleado FROM empresadb.empleado";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("nombre_empleado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /** Buscar ID de empleado por nombre */
    public int buscarIdEmpleadoPorNombre(String nombre) {
        String sql = "SELECT id_empleado FROM empresadb.empleado WHERE nombre_empleado = ?";
        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_empleado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // si no existe
    }
}