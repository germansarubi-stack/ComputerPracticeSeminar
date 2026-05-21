import java.time.LocalDateTime;

public class InformeTexto extends Evidencia {
    private String numeroExpediente;
    private String descripcion; 
    

    public InformeTexto(Usuario oficialAsignado, String numeroExpediente, String descripcion) {
        super(oficialAsignado); // Llamada al constructor de la clase base Evidencia
        this.numeroExpediente = numeroExpediente;
        this.descripcion = descripcion;
    }

    // Constructor para leer de la BD
    public InformeTexto(int id, LocalDateTime fechaCreacion, Usuario oficialAsignado, String numeroExpediente, String descripcion) {
        super(id, fechaCreacion, oficialAsignado);
        this.numeroExpediente = numeroExpediente;
        this.descripcion = descripcion;
    }

    // Getters
    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public String getDescripcion() {
        return descripcion;
    }

   @Override
    public void mostrarDetalles() {
        System.out.println("-------------------------");
        System.out.println("Tipo: Informe [ID: " + getId() + "]"); 
        System.out.println("Fecha: " + getFechaCreacion().toLocalDate());
        System.out.println("Oficial a cargo: " + getOficialAsignado().getNombreCompleto());
        System.out.println("Número de Expediente: " + numeroExpediente);
        System.out.println("Descripcion: " + descripcion);
        System.out.println("-------------------------");
    }
}