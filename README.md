ARGUS GUARDIAN 🛡️
Prototipo Operacional para la Gestión y Custodia de Evidencia Digital (Bodycams)

Este proyecto presenta el desarrollo e implementación del sistema ARGUS GUARDIAN, un software diseñado para centralizar, catalogar y 
asegurar la integridad de la evidencia digital generada por cámaras corporales de fuerzas de seguridad.

El sistema fue desarrollado bajo un enfoque riguroso de ingeniería de software, transitando desde el modelado conceptual en UML hasta un 
prototipo funcional en Java conectado de forma directa a una base de datos relacional MySQL.

🛠️ Tecnologías Utilizadas
Lenguaje: Java SE (versión 8 o superior).
Base de Datos: MySQL Server & MySQL Workbench.
IDE de Desarrollo: Visual Studio Code.
Driver de Conexión: MySQL Connector/J (JDBC).
Modelado: Lenguaje de Modelado Unificado (UML).

📐 Arquitectura del Sistema
La aplicación fue construida respetando el patrón de diseño de Arquitectura en 3 Capas, garantizando un bajo acoplamiento, alta cohesión 
y la separación total de responsabilidades:

Capa de Presentación (UI - SistemaArgusGuardian): Interfaz interactiva por consola encargada de capturar la entrada del usuario y 
renderizar los datos estructurados.
Capa de Servicio (Lógica de Negocio - ServicioSistema): El componente "cerebro" del sistema. Valida las reglas de negocio, orquesta las 
operaciones y actúa como puente intermedio.
Capa de Almacenamiento (Acceso a Datos - Repository/MySQL): Encapsula el código JDBC y las sentencias SQL (SELECT, INSERT, etc.). 
Se implementó mediante Interfaces (IDUsuaioRepositorio e IDEvidenciaRepositorio), lo que permitió migrar el motor de un simulador en 
memoria (ArrayList) a una base de datos real con cero impacto en la lógica de negocio.

🧬 Aplicación de Pilares POO
Abstracción: Implementada en la clase abstracta Evidencia, definiendo la estructura e identidad base de cualquier elemento probatorio sin 
forzar una implementación concreta.
Herencia: Explotada mediante las clases hijas Grabacion e InformeTexto (Casos), que extienden las capacidades de la clase base.
Encapsulamiento: Restricción de acceso mediante atributos privados (private) expuestos controladamente por métodos accesores 
(getters/setters).
Polimorfismo: Evidenciado en la persistencia y lectura dinámica de colecciones, donde objetos heterogéneos (Grabacion / InformeTexto) 
responden a sus propios métodos de visualización en tiempo de ejecución.

📂 Estructura del Repositorio
Para simplificar el despliegue del prototipo (paquete por defecto), los archivos se organizan de la siguiente manera:

├─ ARGUS GUARDIAN/         # Todos los archivos de código fuente (.java)
│  ├─ DatabaseManager.java
│  ├─ Evidencia.java
│  ├─ Grabacion.java
│  ├─ IDEvidenciaRepositorio.java
│  ├─ IDUsuaioRepositorio.java
│  ├─ InformeTexto.java
│  ├─ RepositorioEvidenciaMySQL.java
│  ├─ RepositorioUsuarioMySQL.java
│  ├─ ServicioSistema.java
│  ├─ SistemaArgusGuardian.java
│  └─ Usuario.java
├─ lib/                    # Librerías externas
│  └─ mysql-connector-j-X.X.X.jar
├─ script_database.sql     # Script de creación de tablas e inserts iniciales
└─ README.md               # Este archivo instructivo

🚀 Instrucciones de Despliegue y Ejecución
1. Preparación de la Base de Datos
Abra MySQL Workbench y conéctese a su servidor local. 2)Ejecute el script SQL incluido en el repositorio para estructurar la base de 
datos argus_guardian y poblar las tablas base (usuarios, etc.). 3)Abra el archivo DatabaseManager.java y configure sus credenciales
locales en las constantes: private static final String USER = "tu_usuario"; private static final String PASSWORD = "tu_password";

3. Compilación del Código
Abra una terminal en la raíz del proyecto (TP3 o equivalente) y ejecute el siguiente comando para generar los ejecutables compilados en 
la carpeta bin: 
En Windows (CMD / PowerShell):javac -d bin -cp "lib*.jar" "ARGUS GUARDIAN*.java" 
En Linux / Mac:javac -d bin -cp "lib/" "ARGUS GUARDIAN/.java"

4. Ejecución de la Aplicación
Una vez compilado correctamente, ejecute el sistema vinculando el classpath del driver JDBC: 
En Windows (CMD / PowerShell):java -cp "bin;lib*.jar" SistemaArgusGuardian 
En Linux / Mac:java -cp "bin:lib/*" SistemaArgusGuardian

📊 Metodología de Desarrollo
El ciclo de vida del proyecto se rigió bajo el Proceso Unificado de Desarrollo (PUD), dividiéndose en fases iterativas de Incepción, 
Elaboración, Construcción y Transición. Asimismo, se diseñó e implementó un Plan de Pruebas estricto, catalogando Casos de Prueba 
Funcionales (CPF) y definiendo el flujo de Tratamiento de Defectos para asegurar el control de calidad (QA) previo al despliegue.
