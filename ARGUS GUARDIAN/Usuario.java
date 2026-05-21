
public class Usuario {
    private int id;
    private String nombreUsuario;
    private String passwordHash;
    private String nombreCompleto;
    private String jerarquia;
    private String destino;
    private String sector;
    private String legajo;
    private boolean is_activo = true;

    public Usuario(String nombreUsuario, String password, String nombreCompleto, String jerarquia,
                   String destino, String sector, String legajo, boolean is_activo) {
        this.nombreUsuario = nombreUsuario;
        this.passwordHash = PasswordUtils.hashPassword(password);
        this.nombreCompleto = nombreCompleto;
        this.jerarquia = jerarquia;
        this.destino = destino;
        this.sector = sector;
        this.legajo = legajo;
        this.is_activo = is_activo;
    }

    // Constructor para leer un usuario desde la BD. La contraseña ya viene hasheada.
    public Usuario(int id, String nombreUsuario, String passwordHash, String nombreCompleto, String jerarquia,
                   String destino, String sector, String legajo, boolean is_activo, boolean passwordHashed) {
        this.nombreUsuario = nombreUsuario;
        this.passwordHash = passwordHash;
        this.nombreCompleto = nombreCompleto;
        this.jerarquia = jerarquia;
        this.destino = destino;
        this.sector = sector;
        this.legajo = legajo;
        this.is_activo = is_activo;
        this.id = id;
    }

    // Setter para el ID que nos dara la BD
    public void setId(int id) {
        this.id = id;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getJerarquia() {
        return jerarquia;
    }

    public String getDestino() {
        return destino;
    }

    public String getSector() {
        return sector;
    }

    public String getLegajo() {
        return legajo;
    }

    public boolean isIs_activo() {
        return is_activo;
    }

    public boolean verificarPassword(String password) {
        return PasswordUtils.verifyPassword(password, passwordHash);
    }

    public void mostrarDetalles() {
        System.out.println("ID: " + id);
        System.out.println("Nombre de Usuario: " + nombreUsuario);
        System.out.println("Nombre Completo: " + nombreCompleto);
        System.out.println("Jerarquía: " + jerarquia);
        System.out.println("Destino: " + destino);
        System.out.println("Sector: " + sector);
        System.out.println("Legajo: " + legajo);
        System.out.println("Activo: " + (is_activo ? "Sí" : "No"));
        System.out.println("---------------------------");
    }
}
