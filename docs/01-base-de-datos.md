# Base de Datos - Vaku

## 1. Resumen
La base de datos de Vaku (PostgreSQL) modela:
- Estructura geografica (`departments`, `citys`)
- Personas y roles (`persons`)
- Actores del sistema (`employees`, `parents`, `childrens`)
- Esquema de vacunacion (`vaccines`, `vaccines_applied`)
- Inventario (`inventories`, `inventories_employees`, `inventory_history`)
- Auditoria tecnica (`audit_logs`)

Fuente principal: `dataBase/vaku_bd.sql`.

## 2. Tablas

### `departments`
- PK: `depa_id`
- Campos clave: `depa_name`
- Uso: catalogo de departamentos.

### `citys`
- PK: `city_id`
- FK: `depa_id -> departments.depa_id`
- Campos clave: `city_code`, `city_name`
- Uso: catalogo de ciudades por departamento.

### `persons`
- PK: `pers_id`
- FK: `city_id -> citys.city_id`
- Unicos: `pers_document`, `pers_email`, `pers_phone`
- Campos clave: nombres, documento, sexo, direccion, fecha nacimiento, rol, telefono, email, password.
- Uso: entidad base para empleado, padre y nino.

### `childrens`
- PK: `chil_id`
- FK: `pers_id -> persons.pers_id`
- Unico: `chil_token`
- Uso: referencia de ninos del sistema.

### `parents`
- PK: `pare_id`
- FK: `pers_id -> persons.pers_id`
- Unico: `pare_token`
- Uso: referencia de padres/tutores.

### `childrens_parents`
- PK: `chpa_id`
- FK: `chil_id -> childrens.chil_id`, `pare_id -> parents.pare_id`
- Unico compuesto: (`chil_id`, `pare_id`)
- Uso: relacion nino-padre (N:M).

### `employees`
- PK: `empl_id`
- FK: `pers_id -> persons.pers_id`
- Unico: `empl_token`
- Campos clave: fecha admision, estado.
- Uso: empleados clinicos (enfermera/jefe de enfermeria).

### `inventories`
- PK: `inve_id`
- Unico: `inve_token`
- Restricciones: `inve_quantity >= 0`
- Campos clave: laboratorio, lote, cantidad, fecha.
- Uso: stock por vacuna.

### `vaccines`
- PK: `vacc_id`
- FK: `inve_id -> inventories.inve_id`
- Campos clave: nombre, edad/dosis, dosificacion.
- Uso: catalogo operativo de vacunas asociado a inventario.

### `vaccines_applied`
- PK: `vaap_id`
- FK:
  - `vacc_id -> vaccines.vacc_id`
  - `chil_id -> childrens.chil_id`
  - `empl_id -> employees.empl_id`
- Unico: `vaap_token`
- Campos clave: fecha/hora aplicacion, proxima cita, estado aplicada.
- Uso: historial de aplicacion por nino.

### `inventories_employees`
- PK: `inem_id`
- FK:
  - `empl_id -> employees.empl_id`
  - `inve_id -> inventories.inve_id`
- Uso: trazabilidad de que empleado modifico inventario.

### `inventory_history`
- PK: `inhi_id`
- FK:
  - `inve_id -> inventories.inve_id`
  - `vacc_id -> vaccines.vacc_id`
- Campos clave: fecha de evento, laboratorio, lote, cantidad, fecha inventario.
- Uso: historial de movimientos/cambios de inventario.

### `audit_logs`
- PK: `audit_id`
- Indices:
  - `idx_audit_logs_created_at`
  - `idx_audit_logs_actor_identifier`
- Uso: auditoria transversal HTTP (metodo, ruta, estado, actor, duracion, IP, etc.).

## 3. Relaciones Principales
- `departments 1 - N citys`
- `citys 1 - N persons`
- `persons 1 - 0..1 childrens`
- `persons 1 - 0..1 parents`
- `persons 1 - 0..1 employees`
- `childrens N - M parents` (via `childrens_parents`)
- `inventories 1 - N vaccines`
- `vaccines 1 - N vaccines_applied`
- `childrens 1 - N vaccines_applied`
- `employees 1 - N vaccines_applied`
- `inventories 1 - N inventories_employees`
- `inventories 1 - N inventory_history`
- `vaccines 1 - N inventory_history`

## 4. Reglas y Validaciones Relevantes
- Documento, email y telefono de `persons` son unicos.
- Tokens operativos son unicos (`chil_token`, `pare_token`, `empl_token`, `inve_token`, `vaap_token`).
- Inventario no permite negativos (`CK_INVE_QUANTITY_NON_NEGATIVE`).
- `vaccines_applied.vaap_next_appointment_date` obligatorio a nivel esquema original.

## 5. Triggers y Funciones (segun SQL base)

### `update_inventory_after_vaccine_applied()`
- Trigger: `trg_after_insert_vaccine_applied`
- Evento: `AFTER INSERT ON vaccines_applied`
- Efecto: descuenta 1 unidad de inventario de la vacuna aplicada y evita negativos.

### `log_inventory_changes()`
- Trigger: `trg_after_update_inventories`
- Evento: `AFTER UPDATE ON inventories`
- Efecto: inserta snapshot en `inventory_history`.

## 6. Observaciones Operativas
- La API tambien registra auditoria HTTP por interceptor en `audit_logs`.
- El backend actual incluye logica de notificacion por stock bajo (correo a jefe de enfermeria) en capa de servicio.
