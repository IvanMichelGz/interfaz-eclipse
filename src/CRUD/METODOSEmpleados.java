package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class METODOSEmpleados {

    public Connection conexion() {
        String servidor = "jdbc:sqlserver://IVANMICHEL07XD:1433;"
                + "databaseName=EmpresaDB;"
                + "encrypt=true;"
                + "trustServerCertificate=true";
        String usuario = "sa";
        String clave = "Ivan123";

        Connection conexion = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conexion = DriverManager.getConnection(servidor, usuario, clave);
            System.out.println("Conexión establecida correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con el servidor");
            e.printStackTrace();
        }
        return conexion;
    }

    public ObservableList<ATRIBUTOSEmpleado> listarEmpleados() {
        ObservableList<ATRIBUTOSEmpleado> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT 
                id_empleado, 
                nombre_empleado AS nombre, 
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

    public void insertarEmpleado(ATRIBUTOSEmpleado e) {
        String sql = "INSERT INTO empresadb.empleado (nombre_empleado, cargo, salario, fecha_contratacion) VALUES (?,?,?,?)";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getPuesto());
            ps.setFloat(3, e.getSalario());
            ps.setDate(4, e.getFecha_ingreso());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean updateEmpleado(ATRIBUTOSEmpleado e) {
        String sql = "UPDATE empresadb.empleado SET nombre_empleado=?, cargo=?, salario=?, fecha_contratacion=? WHERE id_empleado=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getPuesto());
            ps.setFloat(3, e.getSalario());
            ps.setDate(4, e.getFecha_ingreso());
            ps.setInt(5, e.getId_empleado());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

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
}