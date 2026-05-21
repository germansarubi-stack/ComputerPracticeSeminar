
import java.util.List;

public interface IDEvidenciaRepositorio {
 Evidencia buscarPorId(int id);
    List<Evidencia> listarTodas();
    Evidencia guardar(Evidencia evidencia);
}
