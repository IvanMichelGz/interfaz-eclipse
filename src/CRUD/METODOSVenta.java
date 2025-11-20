package CRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class METODOSVenta {

    // Conexión a la base de datos
    public Connection conexion() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/empresadb", "root", ""
            ); // ajusta usuario/contraseña según tu config
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Obtener clientes
    public List<ATRIBUTOSClientes> obtenerClientes() {
        List<ATRIBUTOSClientes> lista = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre FROM clientes";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ATRIBUTOSClientes c = new ATRIBUTOSClientes();
                c.setId_cliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Obtener empleados
    public List<ATRIBUTOSEmpleado> obtenerEmpleados() {
        List<ATRIBUTOSEmpleado> lista = new ArrayList<>();
        String sql = "SELECT id_empleado, nombre FROM empleados";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ATRIBUTOSEmpleado e = new ATRIBUTOSEmpleado();
                e.setId_empleado(rs.getInt("id_empleado"));
                e.setNombre(rs.getString("nombre"));
                lista.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // Obtener materiales
    public List<ATRIBUTOSMaterial> obtenerMateriales() {
        List<ATRIBUTOSMaterial> lista = new ArrayList<>();
        String sql = "SELECT id_material, nombre, precio FROM materiales";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ATRIBUTOSMaterial m = new ATRIBUTOSMaterial();
                m.setId_material(rs.getInt("id_material"));
                m.setNombre(rs.getString("nombre"));
                m.setPrecio(rs.getFloat("precio")); // mejor usar double
                lista.add(m);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // Insertar una venta (solo cabecera, sin detalle)
    public int insertarVenta(int idCliente, int idEmpleado, double total) {
        String sql = "INSERT INTO ventas (id_cliente, id_empleado, total) VALUES (?, ?, ?)";
        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idCliente);
            ps.setInt(2, idEmpleado);
            ps.setDouble(3, total);
            ps.executeUpdate();

            // Obtener el ID generado de la venta
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // si falla
    }
}