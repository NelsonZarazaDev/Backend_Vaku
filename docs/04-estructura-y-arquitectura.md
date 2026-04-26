# Estructura y Arquitectura del Proyecto (Front + Back)

## 1. Vision General
Vaku usa una arquitectura **cliente-servidor**:
- **Frontend**: React + Vite (SPA), orientado a modulos de UI por dominio.
- **Backend**: Spring Boot (API REST), orientado a capas (controller-service-repository).
- **Base de datos**: PostgreSQL relacional para trazabilidad clinica y operativa.

Este enfoque separa claramente:
- Presentacion (UI)
- Logica de negocio
- Persistencia y consultas

## 2. Estructura del Backend

Ruta base: `Vaku_Backend/Vaku/src/main/java/com/Vaku/Vaku`

### Carpetas principales
- `apiRest/controller`: expone endpoints HTTP por dominio.
- `apiRest/service`: contiene reglas de negocio (validaciones, flujos, integraciones).
- `apiRest/repository`: acceso a datos con Spring Data JPA y consultas nativas.
- `apiRest/model/entity`: entidades persistentes (tablas).
- `apiRest/model/response`: proyecciones para respuestas optimizadas.
- `configs`: seguridad, OpenAPI/Swagger, configuraciones transversales.
- `security`: JWT y filtro de autenticacion.
- `dto`: contratos de entrada/salida para login y registro de vacuna aplicada.
- `exception`: manejo centralizado de errores.
- `email`, `emailOverdueVaccinations`: notificaciones por correo.
- `pdfVaccinationCard`, `Report`: generacion de PDF y Excel.
- `utils`: utilidades y constantes de dominio.

### Patrón aplicado
**Controller -> Service -> Repository**
- Controller: recibe request y responde HTTP.
- Service: decide reglas del caso de uso (ej. paginacion, actualizaciones, notificaciones).
- Repository: ejecuta consultas y persistencia.

## 3. Estructura del Frontend

Ruta base: `Vaku_Frontend/src`

### Carpetas principales
- `pages`: vistas por modulo (usuarios, prioridad, auditoria, inventario, etc.).
- `components`: componentes reutilizables de UI y modales.
- `hooks`: logica de consumo API y estado local por caso de uso.
- `store`: estado global con Zustand (auth de empleado/nino, contexto de vistas).
- `routes`: definicion de rutas internas.
- `constants`: URLs de API, rutas, headers compartidos.
- `utils`: helpers de negocio/UI (ej. normalizacion de roles).
- `assets`: fuentes, iconos e imagenes.

### Patrón aplicado
**Page -> Hook -> API**
- Page: renderiza experiencia y eventos de usuario.
- Hook: encapsula fetch, paginacion, loading, errores.
- API/constants: centraliza endpoints para mantenimiento sencillo.

## 4. Flujo de Arquitectura (Ejemplo)

Caso: listar usuarios paginados.
1. Front abre `Employees` y ejecuta `UseEmployees`.
2. Hook llama `GET /employee?page=&size=`.
3. Backend `EmployeesController` valida parametros.
4. `EmployessService` aplica reglas de paginacion.
5. `EmployeesRepository` consulta datos.
6. Respuesta `Page<EmployeesResponse>` vuelve al front.
7. UI renderiza tarjetas + controles de pagina.

Este mismo patrón se replica en auditoria y prioridad.

## 5. Por que esta arquitectura es ideal para Vaku

## 5.1 Dominio sensible (salud)
- Permite separar responsabilidades para reducir errores.
- Facilita trazabilidad: auditoria, historial de inventario, registros aplicados.

## 5.2 Escalabilidad funcional
- Nuevos modulos se agregan por dominio sin romper todo.
- Ejemplo: inventario y prioridad evolucionan sin tocar login/base.

## 5.3 Mantenibilidad
- Endpoints claros por controlador.
- Reglas centralizadas en servicios.
- Hooks reutilizables evitan duplicar logica en pantallas.

## 5.4 Seguridad y control
- JWT centralizado en backend.
- Restricciones por rol (ej. auditoria para jefe de enfermeria).
- Documentacion con Swagger para gobernanza tecnica.

## 5.5 Rendimiento operativo
- Paginacion en backend y frontend para modulos de alto volumen.
- Proyecciones de respuesta reducen payload innecesario.

## 5.6 Evolucion del producto
- Facil integrar nuevos reportes, notificaciones y exportaciones (PDF/Excel).
- Arquitectura preparada para crecer por casos de uso.

## 6. Conclusión
La arquitectura actual es adecuada para Vaku porque combina:
- modularidad por dominio,
- separacion de capas en backend,
- componentes y hooks reutilizables en frontend,
- y controles de seguridad/trazabilidad necesarios para un sistema de vacunacion.