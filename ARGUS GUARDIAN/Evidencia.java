
import java.time.LocalDateTime;


public abstract class Evidencia {
    // ID y contador de las evidencias
    private int id;
    private LocalDateTime fechaCreacion;
    private Usuario oficialAsignado; 
    //private static int contadorId = 0;

    // Constructor para crear un objeto en Java sin ID todavaa
    public Evidencia(Usuario oficialAsignado) {
        //this.id = ++contadorId;
        this.fechaCreacion = LocalDateTime.now();
        this.oficialAsignado = oficialAsignado; //guarda la relación
    }

    // Nuevo constructor
    // Para crear un objeto que leemos desde la BD
    public Evidencia(int id, LocalDateTime fechaCreacion, Usuario oficialAsignado) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.oficialAsignado = oficialAsignado;
    }

    // Setter para el ID que la BD nos dará después de insertar
    public void setId(int id) {
        this.id = id;
    }

    // Getters 
    public int getId() {
        return id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Usuario getOficialAsignado() {
        return oficialAsignado;
    }

    // El metodo abstracto que forzando a las hijas a implementarlo
    public abstract void mostrarDetalles();
}