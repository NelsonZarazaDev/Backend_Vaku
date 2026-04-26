# Requerimientos

## Usuarios del sistema

### US01 - Jefe de enfermeria
| CAMPO | VALOR |
|---|---|
| ID STAKEHOLDER | US01 |
| NOMBRE | Jefe de enfermeria |
| DESCRIPCION | Responsable de la operacion del punto de vacunacion. Supervisa inventario, usuarios, auditoria y seguimiento de pacientes priorizados. |
| RESPONSABILIDADES | - Gestionar usuarios (alta/actualizacion/estado)<br>- Revisar auditoria del sistema<br>- Monitorear y ajustar inventario<br>- Coordinar seguimiento de citas vencidas |

### US02 - Enfermera
| CAMPO | VALOR |
|---|---|
| ID STAKEHOLDER | US02 |
| NOMBRE | Enfermera |
| DESCRIPCION | Encargada de aplicar vacunas, actualizar carnets y apoyar control de inventario y prioridad de citas. |
| RESPONSABILIDADES | - Registrar vacuna aplicada<br>- Consultar carnet y datos clinicos del nino<br>- Actualizar inventario asignado<br>- Gestionar lista de prioridad |

### US03 - Padre o acudiente
| CAMPO | VALOR |
|---|---|
| ID STAKEHOLDER | US03 |
| NOMBRE | Padre/Acudiente |
| DESCRIPCION | Usuario receptor de informacion y notificaciones para garantizar asistencia oportuna a controles de vacunacion. |
| RESPONSABILIDADES | - Entregar datos veridicos de registro<br>- Recibir notificaciones por correo<br>- Asistir a citas programadas |

### US04 - Nino (cuenta de consulta)
| CAMPO | VALOR |
|---|---|
| ID STAKEHOLDER | US04 |
| NOMBRE | Nino |
| DESCRIPCION | Su informacion clinica es consultada para seguimiento del esquema de vacunacion. |
| RESPONSABILIDADES | - Mantener trazabilidad de dosis aplicadas y pendientes (gestionado por personal de salud) |

## REQUISITOS DE ALTO NIVEL (Granularidad alta/media)

| ID STAKEHOLDER | CODIGO | ENUNCIADO DEL REQUISITO |
|---|---|---|
| US01 | RF01 | Gestionar usuarios del personal clinico (CRUD parcial y estado activo/inactivo). |
| US01 | RF02 | Consultar auditoria paginada del sistema. |
| US01/US02 | RF03 | Gestionar inventario de vacunas y trazabilidad de cambios. |
| US02 | RF04 | Registrar aplicacion de vacuna y actualizar carnet del nino. |
| US01/US02 | RF05 | Consultar y gestionar prioridad de citas vencidas con paginacion. |
| US01/US02 | RF06 | Notificar por correo a acudientes con citas vencidas. |
| US03/US04 | RF07 | Consultar informacion clinica y carnet por documento del nino. |
| US01 | RF08 | Exportar reportes y generar PDF de carnet. |
| US01/US02 | RF09 | Ingresar al sistema con autenticacion segura JWT. |

## IDENTIFICACION DE REQUERIMIENTOS FUNCIONALES (Granularidad baja)

### Requisitos de US01 (Jefe de enfermeria)
| ID STAKEHOLDER | CODIGO | DESCRIPCION |
|---|---|---|
| US01 | RF01-1 | Listar usuarios paginados (`GET /employee?page=&size=`). |
| US01 | RF01-2 | Editar perfil de empleado (`PUT /employee/{token}?state=`). |
| US01 | RF02-1 | Consultar logs de auditoria paginados (`GET /audit/logs?page=&size=`). |
| US01 | RF03-1 | Consultar inventario total (`GET /inventori`). |
| US01 | RF03-2 | Actualizar inventario por token y empleado (`PUT /inventori/{token}/{emplId}`). |
| US01 | RF08-1 | Descargar reporte Excel (`GET /auth/report`). |
| US01 | RF08-2 | Generar PDF de carnet (`POST /auth/generar`, `POST /auth/generarCarnet`). |

### Requisitos de US02 (Enfermera)
| ID STAKEHOLDER | CODIGO | DESCRIPCION |
|---|---|---|
| US02 | RF04-1 | Registrar vacuna aplicada (`POST /vaccineApplied`). |
| US02 | RF04-2 | Consultar vacunas y detalle de inventario (`GET /vaccines`, `GET /vaccines/{id}`). |
| US02 | RF05-1 | Consultar prioridad paginada (`GET /overdueVaccinations?page=&size=`). |
| US02 | RF06-1 | Enviar recordatorios por correo (`POST /overdueVaccinations/send-emails`). |
| US02 | RF07-1 | Consultar carnet e info familiar (`GET /vaccinesCard/{document}`, `GET /vaccinesCard/info/{document}`). |

### Requisitos de US03/US04 (Padre/Nino)
| ID STAKEHOLDER | CODIGO | DESCRIPCION |
|---|---|---|
| US03/US04 | RF07-2 | Consultar esquema de vacunacion por documento del nino. |
| US03 | RF06-2 | Recibir notificaciones de cita vencida via correo. |

## Historias de usuario (formato funcional)

| ID HU | STAKEHOLDER | COMO | QUIERO | PARA | CRITERIOS DE ACEPTACION | ENDPOINTS RELACIONADOS |
|---|---|---|---|---|---|---|
| HU01 | US01 | jefe de enfermeria | ver usuarios por paginas y cambiar su estado | controlar el personal habilitado | lista paginada, cambio persistente, validacion de rol | `GET /employee`, `PUT /employee/{token}` |
| HU02 | US01 | jefe de enfermeria | consultar auditoria paginada | revisar trazabilidad y cumplimiento | solo rol jefe accede, filtros page/size funcionales | `GET /audit/logs` |
| HU03 | US01/US02 | personal clinico | actualizar inventario por vacuna | evitar quiebres de stock | inventario no negativo, trazabilidad de cambio | `GET /inventori`, `PUT /inventori/{token}/{emplId}` |
| HU04 | US02 | enfermera | registrar vacuna aplicada | mantener al dia el esquema del nino | registro guardado, fecha/hora aplicacion, impacto en inventario | `POST /vaccineApplied` |
| HU05 | US01/US02 | personal clinico | listar prioridad paginada y enviar notificaciones | recuperar pacientes con citas vencidas | paginacion activa, envio de correo exitoso | `GET /overdueVaccinations`, `POST /overdueVaccinations/send-emails` |
| HU06 | US03/US04 | padre/nino | consultar carnet | conocer dosis aplicadas y pendientes | respuesta con aplicaciones e info asociada | `GET /vaccinesCard/{document}` |
| HU07 | US01 | jefe de enfermeria | exportar reportes y PDF | soportar control administrativo y atencion | descarga de archivo valida | `GET /auth/report`, `POST /auth/generar` |

## Requisitos no funcionales asociados
- RNF01: Seguridad por JWT en endpoints protegidos.
- RNF02: Paginacion en modulos de volumen (usuarios, prioridad, auditoria).
- RNF03: Disponibilidad de auditoria tecnica para trazabilidad.
- RNF04: Integridad de datos clinicos e inventario (restricciones y validaciones).
