-- Eliminar la base de datos si ya existe 
DROP DATABASE IF EXISTS argus_guardian;

-- Crear la nueva base de datos
CREATE DATABASE argus_guardian CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Seleccionar la base de datos para usarla
USE argus_guardian;

-- Tabla para almacenar los roles del sistema
CREATE TABLE Roles (
    IDRol INT AUTO_INCREMENT PRIMARY KEY,
    nombreRol VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla para almacenar los usuarios del sistema
CREATE TABLE Usuarios (
    IDUsuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombreUsuario VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nombreCompleto VARCHAR(100) NOT NULL,
	jerarquia VARCHAR(20) NOT NULL,
    destino VARCHAR(255) NULL,
    sector VARCHAR(255) NULL,
    legajo VARCHAR(20) NOT NULL,
    is_activo BOOLEAN NOT NULL DEFAULT TRUE
);


-- Tabla intermedia para la relación muchos a muchos entre usuarios y roles
CREATE TABLE UsuariosRoles (
    IDUsuario BIGINT,
    IDRol INT,
    PRIMARY KEY (IDUsuario, IDRol),
    FOREIGN KEY (IDUsuario) REFERENCES Usuarios(IDUsuario) ON DELETE CASCADE,
    FOREIGN KEY (IDRol) REFERENCES Roles(IDRol) ON DELETE RESTRICT
);

-- Tabla para las políticas de retención
CREATE TABLE PoliticasRetencion (
    IDPolitica INT AUTO_INCREMENT PRIMARY KEY,
    clasificacion VARCHAR(50) NOT NULL,
    diasRetencion INT NOT NULL
);

-- Tabla para los dispositivos físicos (Bodycams)
CREATE TABLE Dispositivos (
	IDDispositivo BIGINT AUTO_INCREMENT PRIMARY KEY,
    numeroSerie VARCHAR(100) NOT NULL UNIQUE,
    modelo VARCHAR(50),
    estado VARCHAR(50) NOT NULL DEFAULT 'Libre',
    ubicacionAsignada VARCHAR(100)
);

-- Tabla principal para las grabaciones de evidencia
CREATE TABLE Grabaciones (
    IDGrabacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    fechaHoraInicio DATETIME NOT NULL,
    duracion INT NOT NULL,
    ubicacionGPS VARCHAR(100),
    estado VARCHAR(50) NOT NULL DEFAULT 'Pendiente',
    formato VARCHAR(10),
    calidad VARCHAR(20),
    IDUsuario BIGINT NOT NULL,
    IDDispositivo BIGINT NOT NULL,
    IDPolitica INT NULL, 
    FOREIGN KEY (IDUsuario) REFERENCES Usuarios(IDUsuario) ON DELETE RESTRICT,
    FOREIGN KEY (IDDispositivo) REFERENCES Dispositivos(IDDispositivo) ON DELETE RESTRICT,
    FOREIGN KEY (IDPolitica) REFERENCES PoliticasRetencion(IDPolitica) ON DELETE SET NULL
);


-- Tabla para los casos o expedientes que agrupan evidencia
CREATE TABLE CasosSumario (
    IDCaso BIGINT AUTO_INCREMENT PRIMARY KEY,
    numeroExpediente VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
);

-- Tabla intermedia para la relación muchos a muchos entre casos y grabaciones
CREATE TABLE CasosGrabaciones (
    IDCaso BIGINT,
    IDGrabacion BIGINT,
    PRIMARY KEY (IDCaso, IDGrabacion),
    FOREIGN KEY (IDCaso) REFERENCES CasosSumario(IDCaso) ON DELETE CASCADE,
    FOREIGN KEY (IDGrabacion) REFERENCES Grabaciones(IDGrabacion) ON DELETE CASCADE
);


-- Tabla para registrar la pista de auditoría sobre las grabaciones
CREATE TABLE PistasAuditoria (
	IDEvento BIGINT AUTO_INCREMENT PRIMARY KEY,
    fechaHora DATETIME NOT NULL,
    tipoAccion VARCHAR(50) NOT NULL,
    justificacion TEXT,
    IDGrabacion BIGINT NOT NULL,
    IDUsuario BIGINT NOT NULL,
    FOREIGN KEY (IDGrabacion) REFERENCES Grabaciones(IDGrabacion) ON DELETE CASCADE,
    FOREIGN KEY (IDUsuario) REFERENCES Usuarios(IDUsuario) ON DELETE RESTRICT
);
