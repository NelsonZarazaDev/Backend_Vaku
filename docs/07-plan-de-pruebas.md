# Plan de Pruebas

## 1. Objetivo
Verificar que Vaku cumpla:
- Requisitos funcionales (usuarios, prioridad, inventario, vacunacion, auditoria)
- Requisitos no funcionales (seguridad, integridad de datos, estabilidad)

## 2. Alcance
- Backend (API REST, reglas de negocio, validaciones)
- Frontend (flujos, paginacion, formularios, manejo de errores)
- Integraciones (correo, PDF, Excel)

## 3. Estrategia de pruebas

## 3.1 Niveles
- **Unitarias**: servicios y utilidades.
- **Integracion**: controlador + servicio + repositorio + BD de prueba.
- **API funcional**: endpoints con casos OK y KO.
- **E2E funcional**: flujos principales en UI.
- **Regresion**: modulos criticos por release.

## 3.2 Tipos de prueba
- Funcionales (RF/RNF)
- Seguridad basica (authN/authZ)
- Datos (integridad referencial y reglas de inventario)
- Usabilidad (mensajes, feedback, paginacion)

## 4. Ambientes
- DEV local: backend + frontend + PostgreSQL.
- QA recomendado: datos anonimizados, correo de sandbox, logs habilitados.

## 5. Datos de prueba sugeridos
- 1 jefe de enfermeria activo.
- 2 enfermeras (1 activa, 1 inactiva).
- 5 ninos con esquemas distintos.
- 1 lote de vacunas con stock > 10 y otro <= 10.
- 10+ registros de auditoria y prioridad para validar paginacion.

## 6. Casos de prueba clave (resumen)

| ID | Modulo | Caso | Resultado esperado |
|---|---|---|---|
| PT-01 | Auth | Login empleado valido | Retorna JWT y acceso a modulos segun rol |
| PT-02 | Auth | Login invalido | Error 403/401 y mensaje controlado |
| PT-03 | Usuarios | Listado paginado | Devuelve `content`, `totalPages`, `totalElements` |
| PT-04 | Usuarios | Actualizar empleado | Cambios persistidos y estado actualizado |
| PT-05 | Prioridad | Listado paginado | Muestra pagina correcta y navegacion funcional |
| PT-06 | Prioridad | Envio de correos | Respuesta 200 y evidencia de envio |
| PT-07 | Vacunacion | Registrar vacuna aplicada | Registro creado y fecha/hora de aplicacion |
| PT-08 | Inventario | Actualizar inventario | Cantidad/lote/laboratorio actualizados |
| PT-09 | Inventario | Stock bajo | Disparo de notificacion al jefe |
| PT-10 | Carnet | Consulta por documento | Retorna esquema y datos asociados |
| PT-11 | Auditoria | Acceso con rol no autorizado | Rechazo por permisos |
| PT-12 | Auditoria | Acceso jefe paginado | Respuesta correcta y ordenada por fecha |

## 7. Criterios de aceptacion de pruebas
- Cobertura funcional de flujos criticos >= 95% de casos definidos.
- Cero defectos bloqueantes en:
  - auth
  - vacuna aplicada
  - inventario
  - auditoria
- Paginacion funcional en usuarios, prioridad y auditoria.

## 8. Automatizacion recomendada
- Backend:
  - JUnit + Spring Boot Test para servicios/controladores.
  - Pruebas de repositorio con dataset controlado.
- Frontend:
  - Pruebas de componentes/hooks.
  - E2E con escenarios de login, paginacion y registro de vacuna.

## 9. Evidencias por ciclo
- Reporte de ejecucion (pasan/fallan).
- Bitacora de defectos con severidad y estado.
- Evidencia de logs/auditoria para flujos sensibles.

## 10. Checklist de salida a produccion
- [ ] Smoke test completo en ambiente objetivo.
- [ ] Credenciales y variables de entorno validadas.
- [ ] Endpoints de Swagger accesibles para soporte.
- [ ] Procedimiento de rollback documentado.
