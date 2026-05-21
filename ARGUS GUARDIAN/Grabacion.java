import java.time.LocalDateTime;

public class Grabacion extends Evidencia {
    private int duracionSegundos;
    private String ubicacionGPS;
    private String estado = "Sin procesar";
    private String formato;
    private String calidad;


    public Grabacion(Usuario oficialAsignado, int duracionSegundos, String ubicacionGPS,String estado, String formato, String calidad) {
        super(oficialAsignado); // Llamada al constructor de la clase base Evidencia
        this.duracionSegundos = duracionSegundos;
        this.ubicacionGPS = ubicacionGPS;
        this.estado = estado;
        this.formato = formato;
        this.calidad = calidad;
    }

    // Constructor para leer de la BD
    public Grabacion(int id, LocalDateTime fechaCreacion, Usuario oficialAsignado, int duracionSegundos, String ubicacionGPS, String estado, String formato, String calidad) {
        super(id, fechaCreacion, oficialAsignado);
        this.duracionSegundos = duracionSegundos;
        this.ubicacionGPS = ubicacionGPS;
        this.estado = estado;
        this.formato = formato;
        this.calidad = calidad;
    }    

    // Getters
    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public String getUbicacionGPS() {
        return ubicacionGPS;
    }

    public String getEstado() {
        return estado;
    }

    public String getFormato() {
        return formato;
    }

    public String getCalidad() {
        return calidad;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("-------------------------");
        System.out.println("Tipo: Grabación [ID: " + getId() + "]"); 
        System.out.println("Fecha y hora: " + getFechaCreacion());
        System.out.println("Oficial a cargo: " + getOficialAsignado().getNombreCompleto());
        System.out.println("Duracion: " + duracionSegundos + " segundos");
        System.out.println("Ubicacion GPS: " + ubicacionGPS);
        System.out.println("Estado: " + estado);
        System.out.println("Formato: " + formato);
        System.out.println("Calidad: " + calidad);
        System.out.println("-------------------------");
    }
}
