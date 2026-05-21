
import java.util.List;

public interface IDUsuaioRepositorio {
    Usuario buscarPorId(int id);
    List<Usuario> listarTodos();
    Usuario guardar(Usuario usuario);
}