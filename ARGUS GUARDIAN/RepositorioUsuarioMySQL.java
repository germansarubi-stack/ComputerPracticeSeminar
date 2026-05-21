
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RepositorioUsuarioMySQL implements IDUsuaioRepositorio {
    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE IDUsuario = ?";
        try (Connection conn = conexionArgusGuardianBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("IDUsuario"),
                        rs.getString("nombreUsuario"),
                        rs.getString("password"),
                        rs.getString("nombreCompleto"),
                        rs.getString("jerarquia"),
                        rs.getString("destino"),
                        rs.getString("sector"),
                        rs.getString("legajo"),
                        rs.getBoolean("is_activo"),
                        true
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        try (Connection conn = conexionArgusGuardianBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("IDUsuario"),
                    rs.getString("nombreUsuario"),
                    rs.getString("password"),
                    rs.getString("nombreCompleto"),
                    rs.getString("jerarquia"),
                    rs.getString("destino"),
                    rs.getString("sector"),
                    rs.getString("legajo"),
                    rs.getBoolean("is_activo"),
                    true
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nombreUsuario, password, nombreCompleto, jerarquia, destino, sector, legajo, is_activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexionArgusGuardianBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getPasswordHash());
            stmt.setString(3, usuario.getNombreCompleto());
            stmt.setString(4, usuario.getJerarquia());
            stmt.setString(5, usuario.getDestino());
            stmt.setString(6, usuario.getSector());
            stmt.setString(7, usuario.getLegajo());
            stmt.setBoolean(8, usuario.isIs_activo());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId(generatedKeys.getInt(1)); // Actualiza el ID en el objeto
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
        }
        return usuario;
    }
}
