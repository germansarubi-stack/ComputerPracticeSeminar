
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Clase que contiene toda la lógica de negocio
public class ServicioSistema {
    private IDUsuaioRepositorio usuarioRepo;
    private IDEvidenciaRepositorio evidenciaRepo;

   public ServicioSistema(IDUsuaioRepositorio usuarioRepo, IDEvidenciaRepositorio evidenciaRepo) {
        this.usuarioRepo = usuarioRepo;
        this.evidenciaRepo = evidenciaRepo;

        // Inicializamos el repositorio de usuarios para tener usuarios disponibles
        //this.usuarioRepo.inicializar();

        // Obtenemos los usuarios que acaba de crear para poder usarlos en la inicialización de evidencias
        //Usuario u1 = this.usuarioRepo.buscarPorId(1);
        //Usuario u2 = this.usuarioRepo.buscarPorId(2);
        
        // Inicializamos el repositorio de evidencias con los usuarios obtenidos
        //this.evidenciaRepo.inicializar(u1, u2);
    }

    // Listar todas las evidencias
    public List<Evidencia> listarTodasLasEvidencias() {
        return evidenciaRepo.listarTodas();
    }

    // Buscar evidencia por ID
    public Evidencia buscarEvidenciaPorId(int id) {
        return evidenciaRepo.buscarPorId(id);
    }

    // Buscar usuario por ID
    public Usuario buscarUsuarioPorId(int id) {
        return usuarioRepo.buscarPorId(id);
    }

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepo.listarTodos();
    }

    // Agregar nuevo usuario
    public Usuario agregarNuevoUsuario(String nombreUsuario, String contrasena, String nombreCompleto,
            String jerarquia, String destino, String sector, String legajo, boolean activo) {
        Usuario nuevoUsuario = new Usuario(nombreUsuario, contrasena, nombreCompleto, jerarquia, destino, sector, legajo,
                activo);
        return usuarioRepo.guardar(nuevoUsuario);
        //return nuevoUsuario;
    }

    // Agregar nueva grabacion
    public Grabacion agregarNuevaGrabacion(int idOficial, int duracion, String ubicacionGPS,String estado, String formato, String calidad) {
        Usuario oficial = usuarioRepo.buscarPorId(idOficial);
        if (oficial == null) {
            System.err.println("Error: No se encontró el oficial con ID " + idOficial);
            return null; // No se puede crear evidencia sin un oficial valido
        }
        Grabacion nuevaGrabacion = new Grabacion(oficial,duracion, ubicacionGPS, estado, formato, calidad);
        return (Grabacion) evidenciaRepo.guardar(nuevaGrabacion);
        //return nuevaGrabacion;
    }

    // Agregar nuevo informe de texto
    public InformeTexto agregarNuevoInformeTexto(int idOficial, String numeroExpediente, String descripcion) {
        Usuario oficial = usuarioRepo.buscarPorId(idOficial);
        if (oficial == null) {
            System.err.println("Error: No se encontró el oficial con ID " + idOficial);
            return null; // No se puede crear evidencia sin un oficial valido
        }
        InformeTexto nuevoInforme = new InformeTexto(oficial, numeroExpediente, descripcion);
        return (InformeTexto) evidenciaRepo.guardar(nuevoInforme);
        //return nuevoInforme;
    }

    // Ordenar grabaciones por duracion
    public List<Grabacion> ordenarGrabacionesPorDuracion() {
        List<Grabacion> grabaciones = new ArrayList<>();
        for (Evidencia ev : evidenciaRepo.listarTodas()) {
            if (ev instanceof Grabacion) {
                grabaciones.add((Grabacion) ev);
            }
        }
        Collections.sort(grabaciones, Comparator.comparingInt(Grabacion::getDuracionSegundos));
        return grabaciones;
    }
}