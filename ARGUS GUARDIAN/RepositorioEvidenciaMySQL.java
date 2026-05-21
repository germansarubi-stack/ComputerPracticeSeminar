
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class RepositorioEvidenciaMySQL implements IDEvidenciaRepositorio {
    // Inyectamos el repo de usuarios para poder construir los objetos completos
    private IDUsuaioRepositorio usuarioRepo;

    public RepositorioEvidenciaMySQL(IDUsuaioRepositorio usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public Evidencia buscarPorId(int id) {
        // Para este prototipo, solo vamos a buscar en la tabla de grabaciones
        // Un sistema completo buscaría en 'grabaciones' e 'informes' (casos)
        String sql = "SELECT * FROM grabaciones WHERE id_grabacion = ?";
        try (Connection conn = conexionArgusGuardianBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirGrabacion(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar grabación por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Evidencia> listarTodas() {
        List<Evidencia> evidencias = new ArrayList<>();
        // Por simplicidad, este prototipo solo lista grabaciones.
        String sql = "SELECT * FROM Grabaciones";
        try (Connection conn = conexionArgusGuardianBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                evidencias.add(construirGrabacion(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar grabaciones: " + e.getMessage());
        }
        return evidencias;
    }

    @Override
    public Evidencia guardar(Evidencia evidencia) {
        // Usamos instanceof para saber en qué tabla guardar
        if (evidencia instanceof Grabacion) {
            return guardarGrabacion((Grabacion) evidencia);
        } else if (evidencia instanceof InformeTexto) {
            return guardarInforme((InformeTexto) evidencia);
        }
        return null;
    }

    // Metodo privado para guardar una Grabacion
    private Grabacion guardarGrabacion(Grabacion grabacion) {
        String sql = "INSERT INTO Grabaciones (fechaHoraInicio, duracion, ubicacionGPS, estado, formato, calidad, IDUsuario, IDDispositivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexionArgusGuardianBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(grabacion.getFechaCreacion()));
            stmt.setInt(2, grabacion.getDuracionSegundos());
            stmt.setString(3, grabacion.getUbicacionGPS());
            stmt.setString(4, grabacion.getEstado());
            stmt.setString(5, grabacion.getFormato());
            stmt.setString(6, grabacion.getCalidad());
            stmt.setInt(7, grabacion.getOficialAsignado().getId());
            stmt.setInt(8, 1); // ID de dispositivo Fijo por ahora

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    grabacion.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar grabación: " + e.getMessage());
        }
        return grabacion;
    }

    // Metodo privado para guardar un informe en la tabla casos
    private InformeTexto guardarInforme(InformeTexto informe) {
        String sql = "INSERT INTO CasosSumario (numeroExpediente, descripcion) VALUES (?, ?)";
        
        try (Connection conn = conexionArgusGuardianBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, informe.getNumeroExpediente());
            stmt.setString(2, informe.getDescripcion());
            
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    informe.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar informe (caso): " + e.getMessage());
        }
        return informe;
    }

    // Metodo ayuda para construir el objeto grabacion, evita N+1 query
    private Grabacion construirGrabacion(ResultSet rs) throws SQLException {
        int idOficial = rs.getInt("IDUsuario");
        Usuario oficial = usuarioRepo.buscarPorId(idOficial); // Hacemos la sub-consulta
        
        return new Grabacion(
            rs.getInt("IDGrabacion"),
            rs.getTimestamp("fechaHoraInicio").toLocalDateTime(),
            oficial,
            rs.getInt("duracion"),
            rs.getString("ubicacionGPS"),
            rs.getString("estado"),
            rs.getString("formato"),
            rs.getString("calidad")
        );
    }
}
