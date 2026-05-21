USE argus_guardian;

INSERT INTO Roles (nombreRol)
VALUES ("Oficial de campo"),("Administrador"),("Supervisor");
        
INSERT INTO Usuarios (nombreUsuario, password, nombreCompleto, jerarquia, destino, sector, legajo, is_activo)
VALUES ("SAgerman", "HASHED_PASSWORD_PLACEHOLDER", "German Sarubi", "Cabo Primero", "Ushuaia", "Centro", "AB4896", true),
		("BOflorencia", "HASHED_PASSWORD_PLACEHOLDER", "Florencia Bordon", "Sargento", "Posadas", "Zona Sur", "OP79428", true),
        ("Admin.Sistema", "HASHED_PASSWORD_PLACEHOLDER", "Admin General", "Cabo Segundo", "CABA", "CABA", "MN59857", true);
        
INSERT INTO UsuariosRoles (IDUsuario, IDRol) 
VALUES (1, 1), 
		(2, 2), 
		(3, 2), 
		(3, 3); 

INSERT INTO PoliticasRetencion (clasificacion, diasRetencion)
VALUES ("Caso abierto", 3650),
		("Transito", 365),
        ("Familiar", 1825);
        
INSERT INTO Dispositivos (numeroSerie, modelo, ubicacionAsignada)
VALUES ("AA1234566", "Motorola", "Comisaria 3, Ushuaia"),
		("AB425398", "Motorola", "Comisaria 15, Posadas"),
        ("AC897382", "Motorola", "Comisaria 7, Olavarria BS AS");
        
INSERT INTO Grabaciones (fechaHoraInicio, duracion, ubicacionGPS, estado, formato, calidad, IDUsuario, IDDispositivo, IDPolitica) 
VALUES ('2025-10-02 14:30:00', 320, '-54.80, -68.30', 'Catalogado', 'MP4', '1080p@30fps', 1, 1, 1),
		('2025-10-02 15:10:00', 650, '-54.81, -68.32', 'Pendiente', 'MP4', '1080p@30fps', 1, 2, NULL),
		('2025-10-02 16:00:00', 120, '-54.82, -68.33', 'Catalogado', 'MP4', '1080p@30fps', 2, 3, 2);        
        
 
INSERT INTO CasosSumario (numeroExpediente, descripcion)      
VALUES ("EXP:12345", "Allanamiento casa relacionada narcotrafico...."), 
		("EXP:98546", "Intervencion violencia falimiar casa...."),
        ("EXP:32547", "Robo en curso en el centro de....");
        
        
INSERT INTO CasosGrabaciones (IDCaso, IDGrabacion) 
VALUES (1, 1),
		(1, 3),
		(2, 1);       
        
INSERT INTO PistasAuditoria (fechaHora, tipoAccion, justificacion, IDGrabacion, IDUsuario) 
VALUES ('2025-10-02 18:00:00', 'Visualización', NULL, 1, 2),
		('2025-10-02 18:05:00', 'Asociación a Caso', 'Asociado al caso C-2025-178', 1, 2),
		('2025-10-02 18:10:00', 'Exportación', 'Solicitud para fiscalía.', 3, 3);