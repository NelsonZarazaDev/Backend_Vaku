# Marco Legal y Cumplimiento (Colombia)

> Nota: este documento es una guia tecnica de cumplimiento para el proyecto. No reemplaza asesoria juridica profesional.

## 1. Alcance legal del sistema
Vaku procesa:
- Datos personales generales (identificacion, contacto, direccion)
- Datos sensibles de salud (esquema de vacunacion, citas, historia clinica asociada)
- Datos de menores de edad

Por lo anterior, el sistema debe operar con enfoque de **proteccion reforzada**.

## 2. Normativa base aplicable

## 2.1 Proteccion de datos personales
- **Ley Estatutaria 1581 de 2012**: marco general de tratamiento de datos personales.
- **Decreto 1377 de 2013** (compilado en normativa posterior): lineamientos para autorizacion, politicas y derechos del titular.

Implicaciones en Vaku:
- Politica de tratamiento publicada y vigente.
- Consentimiento previo, expreso e informado (o base legal aplicable).
- Mecanismo para consulta, correccion, actualizacion y supresion.
- Registro de finalidades del tratamiento por tipo de dato.

## 2.2 Historia clinica y datos de salud
- **Resolucion 1995 de 1999 (MinSalud)**: reglas de manejo, organizacion y reserva de historia clinica.
- **Ley 2015 de 2020**: historia clinica electronica interoperable.

Implicaciones en Vaku:
- Acceso restringido por rol y necesidad funcional.
- Trazabilidad de accesos y cambios (auditoria).
- Conservacion integra y disponibilidad de registros clinicos.

## 2.3 Menores de edad
- Aplicacion reforzada del regimen de habeas data para datos de ninos y adolescentes.

Implicaciones en Vaku:
- Minimizacion de datos recolectados.
- Tratamiento en interes superior del menor.
- Control de acceso estricto para datos sensibles.

## 3. Requisitos legales traducidos a controles tecnicos

| Requisito legal | Control tecnico sugerido/implementado |
|---|---|
| Acceso solo autorizado a datos sensibles | JWT + roles (`jefe de enfermeria`, `enfermera`) + validacion en servicios |
| Trazabilidad de operaciones | `audit_logs` + logs de inventario/historial |
| Integridad de historia clinica e inventario | restricciones SQL + validaciones backend |
| Derecho de actualizacion/correccion | endpoints de actualizacion de personas/ninos/empleados |
| Transparencia de tratamiento | documentacion funcional + politicas de datos (pendiente formalizar si aplica) |

## 4. Brechas comunes a vigilar
- Falta de procedimiento formal para atencion de PQRS de titulares de datos.
- Falta de matriz de retencion y eliminacion de datos por tipo de registro.
- Falta de procedimiento formal de gestion de incidentes de seguridad.
- Falta de pruebas periodicas de control de accesos por rol.

## 5. Recomendaciones de cumplimiento inmediato
1. Publicar politica de tratamiento de datos y aviso de privacidad.
2. Definir procedimiento documentado para derechos ARCO (acceso, rectificacion, cancelacion, oposicion).
3. Establecer protocolo de incidente de seguridad y notificacion.
4. Ejecutar revisiones trimestrales de accesos y auditoria.
5. Mantener evidencia de consentimientos y/o base legal aplicable.

## 6. Fuentes oficiales (consulta)
- Ley 1581 de 2012 (Funcion Publica): https://www.funcionpublica.gov.co/eva/gestornormativo/norma.php?i=49981
- Decreto 1377 de 2013 (Funcion Publica): https://www.funcionpublica.gov.co/eva/gestornormativo/norma.php?i=53646
- Resolucion 1995 de 1999 (MinSalud PDF): https://www.minsalud.gov.co/normatividad_nuevo/resoluci%C3%93n%201995%20de%201999.pdf
- Ley 2015 de 2020 (Funcion Publica): https://www1.funcionpublica.gov.co/eva/gestornormativo/norma.php?i=105472
