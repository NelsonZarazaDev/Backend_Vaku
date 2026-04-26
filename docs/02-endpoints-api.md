# Endpoints API - Vaku

## 1. Convenciones
- Base URL local: `http://localhost:8080`
- Autenticacion: JWT Bearer en `Authorization: Bearer <token>`
- Endpoints publicos principales:
  - `/auth/**`
  - Swagger/OpenAPI (`/swagger-ui.html`, `/v3/api-docs`)

## 2. Autenticacion y Sesion

### `POST /auth/login`
- Descripcion: login de empleado.
- Body: `LoginEmployeeRequest` (`persEmail`, `persPassword`)
- Respuesta: `AuthResponse` con JWT.

### `POST /auth/loginChild`
- Descripcion: login de nino.
- Body: `LoginChildRequest` (`persDocument`, `persDateBirth`)
- Respuesta: `AuthResponse` con JWT.

## 3. Personas, Empleados y Ninos

### `POST /persons`
- Descripcion: crea lista de personas (empleado, padre, nino segun rol).
- Body: `List<PersonsEntity>`
- Respuesta: `List<PersonsEntity>`.

### `GET /persons/document/{document}`
- Descripcion: busca persona por documento.
- Respuesta: `PersonsEntity`.

### `PUT /employee/{token}?state={true|false}`
- Descripcion: actualiza datos personales del empleado y estado.
- Body: `PersonsEntity`.
- Respuesta: `PersonsEntity`.

### `GET /employee/{email}`
- Descripcion: consulta empleado por email.
- Respuesta: `Set<EmployeesResponse>`.

### `GET /employee?page={page}&size={size}`
- Descripcion: lista paginada de usuarios (enfermera/jefe de enfermeria).
- Query params:
  - `page` default `0`
  - `size` default `12` (max `200`)
- Respuesta: `Page<EmployeesResponse>`.

### `PUT /children/{token}`
- Descripcion: actualiza datos del nino.
- Body: `PersonsEntity`.
- Respuesta: `PersonsEntity`.

## 4. Ubicacion

### `GET /departments`
- Descripcion: lista departamentos.
- Respuesta: `List<DepartmentsEntity>`.

### `GET /citys/{id}`
- Descripcion: lista ciudades por departamento.
- Respuesta: `Set<CitysResponse>`.

## 5. Vacunas, Carnet y Aplicacion

### `GET /vaccines`
- Descripcion: lista vacunas del sistema.
- Respuesta: `List<VaccinesEntity>`.

### `GET /vaccines/{id}`
- Descripcion: detalle de vacuna + datos de inventario.
- Respuesta: `Set<VaccinesResponse>`.

### `POST /vaccineApplied`
- Descripcion: registra aplicacion de vacuna (flujo recomendado).
- Body: `VaccineAppliedRequest`:
  - `vaccinesApplied` (`VaccinesAppliedEntity`)
  - `emailFather`
- Respuesta: `VaccinesAppliedEntity`.

### `POST /vaccineApplied/{emailFather}`
- Descripcion: registro alterno de aplicacion.
- Body: `VaccinesAppliedEntity`.
- Respuesta: `VaccinesAppliedEntity`.

### `GET /vaccinesCard/{document}`
- Descripcion: esquema/carnet de vacunacion del nino por documento.
- Respuesta: `Set<VaccinationCardResponse>`.

### `GET /vaccinesCard/info/{document}`
- Descripcion: info de nino + padre para carnet.
- Respuesta: `Set<InfoParentsChildrensResponse>`.

## 6. Inventario

> Nota: la ruta base actual es `inventori` (sin `y`) por compatibilidad del proyecto.

### `GET /inventori`
- Descripcion: lista de inventario de vacunas.
- Respuesta: `Set<InventoriesResponse>`.

### `GET /inventori/{token}`
- Descripcion: inventario por token.
- Respuesta: `Set<InventoriesResponse>`.

### `PUT /inventori/{token}/{emplId}`
- Descripcion: actualiza inventario y asocia empleado responsable.
- Body: `InventoriesEntity`.
- Respuesta: `InventoriesEntity`.

## 7. Prioridad (Citas vencidas)

### `GET /overdueVaccinations?page={page}&size={size}`
- Descripcion: lista paginada de pacientes con cita vencida o pendiente.
- Query params:
  - `page` default `0`
  - `size` default `10` (max `200`)
- Respuesta: `Page<OverdueAppointmentResponse>`.

### `POST /overdueVaccinations/send-emails`
- Descripcion: envia recordatorio por correo a padres en prioridad.
- Respuesta: `200 OK`.

## 8. Auditoria

### `GET /audit/logs?page={page}&size={size}`
- Descripcion: auditoria paginada del sistema.
- Query params:
  - `page` default `0`
  - `size` default `30` (max `200`)
- Respuesta: `Page<AuditLogResponse>`.
- Restriccion: solo jefe de enfermeria.

## 9. Reportes y PDF

### `GET /auth/report`
- Descripcion: descarga reporte Excel.
- Respuesta: archivo `report.xlsx`.

### `POST /auth/generar`
- Descripcion: genera PDF de carnet a partir de datos ensamblados.
- Body: `CarnetDataRequest`.
- Respuesta: PDF (`carnet-vacunacion.pdf`).

### `POST /auth/generarCarnet`
- Descripcion: genera PDF de carnet (flujo alterno).
- Body: `CarnetRequest`.
- Respuesta: PDF (`carnet.pdf`).

## 10. Swagger / OpenAPI
- UI: `GET /swagger-ui.html`
- JSON: `GET /v3/api-docs`
