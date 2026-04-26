# Historias de Usuario - Vaku

Este documento describe historias de usuario derivadas del funcionamiento actual del sistema y sus endpoints.

## HU-01 - Login de empleado
**Como** empleado de salud  
**Quiero** iniciar sesion con correo y contrasena  
**Para** usar los modulos internos.

- Endpoint: `POST /auth/login`
- Criterios de aceptacion:
  - Si credenciales y estado son validos, retorna JWT.
  - Si no, retorna error de autenticacion.

## HU-02 - Login de nino
**Como** acudiente/nino  
**Quiero** ingresar con documento y fecha de nacimiento  
**Para** consultar carnet.

- Endpoint: `POST /auth/loginChild`
- Criterios de aceptacion:
  - Solo usuarios con rol de nino o registro de nino.
  - Retorna JWT cuando los datos coinciden.

## HU-03 - Registrar personas del ecosistema
**Como** jefe de enfermeria  
**Quiero** registrar empleados, padres y ninos  
**Para** mantener actualizado el sistema.

- Endpoint: `POST /persons`
- Soporta lote de personas (`List<PersonsEntity>`).

## HU-04 - Evitar duplicados por documento
**Como** enfermera/jefe  
**Quiero** consultar por documento antes de registrar  
**Para** evitar duplicidades.

- Endpoint: `GET /persons/document/{document}`

## HU-05 - Actualizar perfil de empleado
**Como** jefe de enfermeria  
**Quiero** editar datos y estado del empleado  
**Para** gestionar usuarios activos/inactivos.

- Endpoint: `PUT /employee/{token}?state=...`

## HU-06 - Listar usuarios con paginacion
**Como** jefe de enfermeria  
**Quiero** ver usuarios por paginas  
**Para** manejar volumen de datos.

- Endpoint: `GET /employee?page=&size=`
- Resultado: `Page<EmployeesResponse>`.

## HU-07 - Actualizar datos de nino
**Como** enfermera/jefe  
**Quiero** corregir informacion del nino  
**Para** mantener exactitud clinica.

- Endpoint: `PUT /children/{token}`

## HU-08 - Consultar departamentos y ciudades
**Como** usuario administrativo  
**Quiero** cargar catalogos territoriales  
**Para** diligenciar formularios de direccion.

- Endpoints:
  - `GET /departments`
  - `GET /citys/{id}`

## HU-09 - Consultar vacunas disponibles
**Como** enfermera  
**Quiero** ver vacunas y su detalle  
**Para** aplicar correctamente dosis.

- Endpoints:
  - `GET /vaccines`
  - `GET /vaccines/{id}`

## HU-10 - Registrar vacuna aplicada
**Como** enfermera  
**Quiero** registrar aplicacion por nino  
**Para** mantener carnet y trazabilidad.

- Endpoint principal: `POST /vaccineApplied`
- Endpoint alterno: `POST /vaccineApplied/{emailFather}`
- Impacto funcional esperado:
  - Registro en `vaccines_applied`.
  - Ajuste de inventario segun logica de negocio.

## HU-11 - Consultar carnet de vacunacion
**Como** padre/enfermera/jefe  
**Quiero** consultar carnet del nino  
**Para** revisar dosis aplicadas y faltantes.

- Endpoints:
  - `GET /vaccinesCard/{document}`
  - `GET /vaccinesCard/info/{document}`

## HU-12 - Gestionar inventario de vacunas
**Como** jefe de enfermeria o enfermera  
**Quiero** consultar y actualizar inventario  
**Para** asegurar disponibilidad de dosis.

- Endpoints:
  - `GET /inventori`
  - `GET /inventori/{token}`
  - `PUT /inventori/{token}/{emplId}`

## HU-13 - Ver pacientes en prioridad con paginacion
**Como** enfermera/jefe  
**Quiero** ver lista paginada de citas vencidas  
**Para** priorizar seguimiento.

- Endpoint: `GET /overdueVaccinations?page=&size=`

## HU-14 - Notificar por email pacientes en prioridad
**Como** enfermera/jefe  
**Quiero** enviar recordatorios por correo  
**Para** mejorar adherencia al esquema.

- Endpoint: `POST /overdueVaccinations/send-emails`

## HU-15 - Consultar auditoria del sistema
**Como** jefe de enfermeria  
**Quiero** revisar acciones del sistema con paginacion  
**Para** control y trazabilidad operativa.

- Endpoint: `GET /audit/logs?page=&size=`
- Restriccion: acceso exclusivo a jefe de enfermeria.

## HU-16 - Descargar reportes
**Como** jefe de enfermeria  
**Quiero** descargar reportes en Excel  
**Para** analisis y reporte gerencial.

- Endpoint: `GET /auth/report`

## HU-17 - Generar PDF del carnet
**Como** usuario autorizado  
**Quiero** generar y descargar el carnet en PDF  
**Para** entregar soporte formal al paciente.

- Endpoints:
  - `POST /auth/generar`
  - `POST /auth/generarCarnet`

## Consideraciones no funcionales derivadas
- Seguridad por JWT para endpoints protegidos.
- Paginacion soportada en:
  - `employee`
  - `overdueVaccinations`
  - `audit/logs`
- Documentacion disponible en Swagger (`/swagger-ui.html`).
