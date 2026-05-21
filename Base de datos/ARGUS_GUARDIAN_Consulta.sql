SELECT u.nombreCompleto, r.nombreRol
FROM Usuarios u
JOIN UsuariosRoles ur ON u.IDUsuario = ur.IDUsuario
JOIN Roles r ON ur.IDRol = r.IDRol
ORDER BY u.nombreCompleto;

SELECT * FROM Grabaciones 
WHERE IDUsuario = 1;

SELECT g.IDGrabacion, g.fechaHoraInicio, u.nombreCompleto AS oficial
FROM CasosSumario c
JOIN CasosGrabaciones cg ON c.IDCaso = cg.IDCaso
JOIN Grabaciones g ON cg.IDGrabacion = g.IDGrabacion
JOIN Usuarios u ON g.IDUsuario = u.IDUsuario
WHERE c.numeroExpediente = 'EXP:12345';

SELECT * FROM PistasAuditoria 
WHERE IDGrabacion = 1;

SELECT g.IDGrabacion, g.estado, pr.clasificacion, pr.diasRetencion
FROM Grabaciones g
LEFT JOIN PoliticasRetencion pr ON g.IDPolitica = pr.IDPolitica;