package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class METODOSClientes {

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

    public ObservableList<ATRIBUTOSClientes> listarClientes() {
        ObservableList<ATRIBUTOSClientes> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT 
                id_cliente,
                nombre,
                apellido,
                telefono,
                email
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
}