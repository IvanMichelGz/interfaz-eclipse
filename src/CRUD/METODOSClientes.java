package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class METODOSClientes {

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

    /** Listar todos los clientes */
    public ObservableList<ATRIBUTOSClientes> listarClientes() {
        ObservableList<ATRIBUTOSClientes> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT id_cliente, nombre, apellido, telefono, email
            FROM empresadb.cliente
        """;

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ATRIBUTOSClientes c = new ATRIBUTOSClientes();
                c.setId_cliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /** Insertar un nuevo cliente */
    public void insertarCliente(ATRIBUTOSClientes c) {
        String sql = "INSERT INTO empresadb.cliente (nombre, apellido, telefono, email) VALUES (?,?,?,?)";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getEmail());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /** Actualizar un cliente existente */
    public boolean updateCliente(ATRIBUTOSClientes c) {
        String sql = "UPDATE empresadb.cliente SET nombre=?, apellido=?, telefono=?, email=? WHERE id_cliente=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getEmail());
            ps.setInt(5, c.getId_cliente());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /** Eliminar un cliente por ID */
    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM empresadb.cliente WHERE id_cliente=?";

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

    /** Listar solo nombres de clientes (para autocompletado) */
    public List<String> listarNombresClientes() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nombre FROM empresadb.cliente";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /** Buscar ID de cliente por nombre */
    public int buscarIdClientePorNombre(String nombre) {
        String sql = "SELECT id_cliente FROM empresadb.cliente WHERE nombre = ?";
        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_cliente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // si no existe
    }
}