import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Clase main que se encarga de la interacción con el usuario
public class SistemaArgusGuardian {

    public static void main(String[] args) {
        // --- PREPARACIÓN DE CAPAS ---
        // Creamos las instancias de los repositorios (ArrayLists por ahora)
        IDUsuaioRepositorio usuarioRepo = new RepositorioUsuarioMySQL();
        IDEvidenciaRepositorio evidenciaRepo = new RepositorioEvidenciaMySQL(usuarioRepo);
        
        // Inyectamos los repositorios en el servicio
        ServicioSistema servicio = new ServicioSistema(usuarioRepo, evidenciaRepo);
        
        // --- INTERFAZ DE USUARIO ---
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        System.out.println("=== Bienvenido al Prototipo del Sistema ARGUS GUARDIAN ===");

        // Bucle principal del menu
        while (!salir) {
            mostrarMenu();
            int opcion = 0;

            // Manejo de entrada invalida
            try {
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("\n*** ERROR: Por favor, ingrese un número válido. ***\n");
                scanner.nextLine();
                continue;
            }

            // Procesar la opción seleccionada
            switch (opcion) {
                case 1:
                    listarTodasLasEvidencias(servicio);
                    break;
                case 2:
                    buscarEvidenciaPorId(scanner, servicio);
                    break;
                case 3:
                    agregarNuevaGrabacion(scanner, servicio);
                    break;
                case 4:
                    agregarNuevoInformeTexto(scanner, servicio);
                    break;    
                case 5:
                    listarUsuarios(servicio);
                    break;
                case 6:
                    agregarNuevoUsuario(scanner, servicio);
                    break;    
                case 7:
                    buscarUsuarioPorId(scanner, servicio);
                    break;
                case 8:
                    ordenarGrabacionesPorDuracion(servicio);
                    break;
                case 9:
                    salir = true;
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("\n*** Opción no válida. Intente de nuevo. ***\n");
            }
        }
        scanner.close();
    }


    // Metodo para mostrar el menu principal
    public static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Listar todas las evidencias");
        System.out.println("2. Buscar evidencia por ID");
        System.out.println("3. Agregar nueva grabación");
        System.out.println("4. Agregar nuevo informe de texto");
        System.out.println("5. Listar usuarios del sistema");
        System.out.println("6. Agregar nuevo usuario");
        System.out.println("7. Buscar usuario por ID");
        System.out.println("8. Ordenar grabaciones por duración (ascendente)");
        System.out.println("9. Salir");
    }

    // --- Los metodos de la UI ahora solo llaman al servicio ---
    // Metodo para listar todas las evidencias
    public static void listarTodasLasEvidencias(ServicioSistema servicio) {
        System.out.println("\n--- LISTADO DE TODAS LAS EVIDENCIAS ---");
        List<Evidencia> evidencias = servicio.listarTodasLasEvidencias();
        if (evidencias.isEmpty()) {
            System.out.println("--------------------------------");
            System.out.println("No hay evidencias registradas.");
             System.out.println("--------------------------------");
            return;
        }
        for (Evidencia evidencia : evidencias) {
            evidencia.mostrarDetalles();
        }
    }

    // Metodo para buscar evidencia por ID
    public static void buscarEvidenciaPorId(Scanner scanner, ServicioSistema servicio) {
        System.out.print("Ingrese el ID de la evidencia a buscar: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            
            Evidencia evidencia = servicio.buscarEvidenciaPorId(id);
            
            if (evidencia != null) {
                System.out.println("\n--- EVIDENCIA ENCONTRADA ---");
                evidencia.mostrarDetalles();
            } else {
                System.out.println("--------------------------------");
                System.out.println("No se encontró ninguna evidencia con el ID " + id);
                System.out.println("--------------------------------");
            }
        } catch (InputMismatchException e) {
            System.out.println("--------------------------------");
            System.out.println("\n*** ERROR: ID inválido. Debe ser un número. ***");
            System.out.println("--------------------------------");
            scanner.nextLine();
        }
    }
    
    // Metodo para listar todos los usuarios
    public static void listarUsuarios(ServicioSistema servicio) {
        System.out.println("\n--- USUARIOS DEL SISTEMA ---");
        for (Usuario usuario : servicio.listarUsuarios()) {
            usuario.mostrarDetalles();
        }
    }

    // Metodo para agregar un nuevo usuario
    public static void agregarNuevoUsuario(Scanner scanner, ServicioSistema servicio) {
        System.out.println("\n--- AGREGAR NUEVO USUARIO ---");
        try {
            System.out.print("Ingrese el nombre de usuario: ");
            String nombreUsuario = scanner.nextLine();

            System.out.print("Ingrese la contraseña: ");
            String contrasena = scanner.nextLine();

            System.out.print("Ingrese el nombre completo: ");
            String nombreCompleto = scanner.nextLine();

            System.out.print("Ingrese la jerarquía: ");
            String jerarquia = scanner.nextLine();

            System.out.print("Ingrese el destino: ");
            String destino = scanner.nextLine();

            System.out.print("Ingrese el sector: ");
            String sector = scanner.nextLine();

            System.out.print("Ingrese el legajo: ");
            String legajo = scanner.nextLine();

            System.out.print("¿El usuario está activo? (true/false): ");
            boolean activo = scanner.nextBoolean();
            scanner.nextLine();

            Usuario nuevoUsuario = servicio.agregarNuevoUsuario(nombreUsuario, contrasena, nombreCompleto,
                    jerarquia, destino, sector, legajo, activo);

            if(nuevoUsuario != null) {
                System.out.println("--------------------------------");
                System.out.println("Nuevo usuario con ID " + nuevoUsuario.getId() + " agregado exitosamente");
                System.out.println("--------------------------------");
            } else {
                System.out.println("--------------------------------");
                System.out.println("Error: No se pudo crear el usuario.");
                System.out.println("--------------------------------");
            }

        } catch (InputMismatchException e) {
            System.out.println("--------------------------------");
            System.out.println("\n*** ERROR: Entrada inválida. Intente de nuevo. ***");
            System.out.println("--------------------------------");
            scanner.nextLine();
        }
    }


    // Metodo para buscar usuario por ID
    public static void buscarUsuarioPorId(Scanner scanner, ServicioSistema servicio) {
        System.out.print("Ingrese el ID del usuario a buscar: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            
            Usuario usuario = servicio.buscarUsuarioPorId(id);
            
            if (usuario != null) {
                System.out.println("\n--- USUARIO ENCONTRADO ---");
                usuario.mostrarDetalles();
            } else {
                System.out.println("--------------------------------");
                System.out.println("No se encontró ningun usuario con el ID " + id);
                System.out.println("--------------------------------");
            }
        } catch (InputMismatchException e) {
            System.out.println("--------------------------------");
            System.out.println("\n*** ERROR: ID inválido. Debe ser un número. ***");
            System.out.println("--------------------------------");
            scanner.nextLine();
        }
    }
    
    // Metodo para agregar una nueva grabacion
    public static void agregarNuevaGrabacion(Scanner scanner, ServicioSistema servicio) {
         System.out.println("\n--- AGREGAR NUEVA GRABACIÓN ---");
        try {
            // Listado de usuarios para que el operador sepa qué IDs son validos.
            listarUsuarios(servicio);
            System.out.print("Ingrese el ID del oficial a cargo de la grabación: ");
            int idOficial = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            System.out.print("Ingrese la duración en segundos: ");
            int duracion = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Ingrese la Ubicacion GPS: ");
            String ubicacion = scanner.nextLine();

            System.out.print("Ingrese el estado de la grabación: ");
            String estado = scanner.nextLine();

            System.out.print("Ingrese el formato del video: ");
            String formato = scanner.nextLine();

            System.out.print("Ingrese la calidad del video: ");
            String calidad = scanner.nextLine();

            // pase del idOficial al servicio para que este cree la relacion
            Grabacion nuevaGrabacion = servicio.agregarNuevaGrabacion(idOficial, duracion, ubicacion, estado, formato, calidad);

            if(nuevaGrabacion != null) {
                System.out.println("--------------------------------");
                System.out.println("Nueva grabación con ID " + nuevaGrabacion.getId() + " agregada exitosamente");
                System.out.println("--------------------------------");
            } else {
                System.out.println("--------------------------------");
                System.out.println("Error: No se pudo crear la grabación. Verifique que el ID del oficial sea válido.");
                System.out.println("--------------------------------");
            }

        } catch (InputMismatchException e) {
            System.out.println("--------------------------------");
            System.out.println("\n*** ERROR: Entrada inválida. Intente de nuevo. ***");
            System.out.println("--------------------------------");
            scanner.nextLine();
        }
    }

    // Metodo para agregar un nuevo informe de texto
    public static void agregarNuevoInformeTexto(Scanner scanner, ServicioSistema servicio) {
        System.out.println("\n--- AGREGAR NUEVO INFORME DE TEXTO ---");
        try {
            // Listado de usuarios para facilitar la selección.
            listarUsuarios(servicio);
            System.out.print("Ingrese el ID del oficial que redacta el informe: ");
            int idOficial = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Ingrese el número de expediente: ");
            String numeroExpediente = scanner.nextLine();

            System.out.print("Ingrese la descripción del informe: ");
            String descripcion = scanner.nextLine();

            // pase del idOficial al servicio
            InformeTexto nuevoInforme = servicio.agregarNuevoInformeTexto(idOficial, numeroExpediente, descripcion);

            if(nuevoInforme != null) {
                System.out.println("--------------------------------");
                System.out.println("Nuevo informe de texto con ID " + nuevoInforme.getId() + " agregado exitosamente");
                System.out.println("--------------------------------");
            } else {
                System.out.println("--------------------------------");
                System.out.println("Error: No se pudo crear el informe. Verifique que el ID del oficial sea válido.");
                System.out.println("--------------------------------");
            }

        } catch (InputMismatchException e) {
            System.out.println("--------------------------------");
            System.out.println("\n*** ERROR: Entrada inválida. Intente de nuevo. ***");
            System.out.println("--------------------------------");
            scanner.nextLine();
        }
    }
    
    // Metodo para ordenar grabaciones por duracion
    public static void ordenarGrabacionesPorDuracion(ServicioSistema servicio) {
        System.out.println("\n--- GRABACIONES ORDENADAS POR DURACIÓN ---");
        List<Grabacion> grabacionesOrdenadas = servicio.ordenarGrabacionesPorDuracion();
        for (Grabacion g : grabacionesOrdenadas) {
            g.mostrarDetalles();
        }
    }
}