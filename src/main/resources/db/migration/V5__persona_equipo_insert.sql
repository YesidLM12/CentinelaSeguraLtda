ALTER TABLE equipo ADD COLUMN IF NOT EXISTS creado_en    TIMESTAMP;
ALTER TABLE equipo ADD COLUMN IF NOT EXISTS creado_por   VARCHAR(60);
ALTER TABLE equipo ADD COLUMN IF NOT EXISTS modifica_en  TIMESTAMP;
ALTER TABLE equipo ADD COLUMN IF NOT EXISTS modifica_por VARCHAR(60);

ALTER TABLE equipo DROP CONSTRAINT CK_equipo_tipo;
ALTER TABLE equipo ADD CONSTRAINT CK_equipo_tipo
    CHECK (tipo IN ('ARMA', 'COMUNICACION', 'VEHICULO', 'TECNOLOGICO', 'OTRO'));

ALTER TABLE equipo DROP CONSTRAINT CK_equipo_estado;
ALTER TABLE equipo ADD CONSTRAINT CK_equipo_estado
    CHECK (estado IN ('DISPONIBLE', 'OCUPADO', 'MANTENIMIENTO', 'BAJA'));

INSERT INTO equipo (tipo, descripcion, marca, numero_permiso, estado, ubicacion, fecha_adquision)
VALUES ('ARMA', 'Pistola semiautomática calibre 9 mm', 'Glock', 'PERM-ARM-001', 'DISPONIBLE', 'Bodega central', DATE '2024-03-15');

INSERT INTO equipo (tipo, descripcion, marca, numero_permiso, estado, ubicacion, fecha_adquision)
VALUES ('ARMA', 'Escopeta calibre 12 para escolta', 'Remington', 'PERM-ARM-002', 'MANTENIMIENTO', 'Taller de armería', DATE '2023-08-10');

INSERT INTO equipo (tipo, descripcion, marca, numero_permiso, estado, ubicacion, fecha_adquision)
VALUES ('ARMA', 'Revólver calibre 38 de respaldo', 'Smith & Wesson', 'PERM-ARM-003', 'OCUPADO', 'Puesto Centro Comercial', DATE '2022-05-20');

INSERT INTO equipo (tipo, descripcion, marca, numero_permiso, estado, ubicacion, fecha_adquision)
VALUES ('COMUNICACION', 'Radio portátil con canal cifrado', 'Motorola', NULL, 'DISPONIBLE', 'Bodega central', DATE '2024-06-01');

INSERT INTO equipo (tipo, descripcion, marca, numero_permiso, estado, ubicacion, fecha_adquision)
VALUES ('COMUNICACION', 'Radio base de monitoreo', 'Kenwood', NULL, 'OCUPADO', 'Centro de monitoreo', DATE '2023-11-15');

INSERT INTO equipo (tipo, descripcion, marca, numero_permiso, estado, ubicacion, fecha_adquision)
VALUES ('TECNOLOGICO', 'Cámara dashcam 1080p con GPS', 'Garmin', NULL, 'DISPONIBLE', 'Bodega central', DATE '2025-01-20');

INSERT INTO equipo (tipo, descripcion, marca, numero_permiso, estado, ubicacion, fecha_adquision)
VALUES ('TECNOLOGICO', 'Sistema de CCTV con grabadora NVR', 'Hikvision', NULL, 'DISPONIBLE', 'Centro de monitoreo', DATE '2024-02-28');

INSERT INTO equipo (tipo, descripcion, marca, numero_permiso, estado, ubicacion, fecha_adquision)
VALUES ('VEHICULO', 'Camioneta blindada para escolta', 'Toyota', NULL, 'DISPONIBLE', 'Parqueadero', DATE '2024-09-12');

INSERT INTO equipo (tipo, descripcion, marca, numero_permiso, estado, ubicacion, fecha_adquision)
VALUES ('VEHICULO', 'Camioneta de patrullaje', 'Mazda', NULL, 'OCUPADO', 'Sector Norte', DATE '2023-04-03');

INSERT INTO equipo (tipo, descripcion, marca, numero_permiso, estado, ubicacion, fecha_adquision)
VALUES ('OTRO', 'Kit de primeros auxilios', 'Genérica', NULL, 'BAJA', 'Depósito', DATE '2019-11-05');

ALTER TABLE empleado ADD COLUMN IF NOT EXISTS creado_en    TIMESTAMP;
ALTER TABLE empleado ADD COLUMN IF NOT EXISTS creado_por   VARCHAR(60);
ALTER TABLE empleado ADD COLUMN IF NOT EXISTS modifica_en  TIMESTAMP;
ALTER TABLE empleado ADD COLUMN IF NOT EXISTS modifica_por VARCHAR(60);

INSERT INTO empleado (documento, nombre, cargo, numero_credencial, vence_credencial, telefono, fecha_ingreso, activo)
VALUES ('1020304050', 'Carlos Andrés Gómez Ruiz', 'VIGILANTE', '10001', DATE '2027-06-30', '3001112233', DATE '2022-01-15', 1);

INSERT INTO empleado (documento, nombre, cargo, numero_credencial, vence_credencial, telefono, fecha_ingreso, activo)
VALUES ('1030405060', 'María Fernanda López', 'SUPERVISOR', '10002', DATE '2027-03-31', '3012223344', DATE '2019-07-01', 1);

INSERT INTO empleado (documento, nombre, cargo, numero_credencial, vence_credencial, telefono, fecha_ingreso, activo)
VALUES ('1040506070', 'Jorge Iván Martínez', 'ESCOLTA', '10003', DATE '2026-12-31', '3023334455', DATE '2021-05-10', 1);

INSERT INTO empleado (documento, nombre, cargo, numero_credencial, vence_credencial, telefono, fecha_ingreso, activo)
VALUES ('1050607080', 'Laura Patricia Ruiz', 'VIGILANTE', '10004', DATE '2027-09-30', '3034445566', DATE '2023-02-20', 1);

INSERT INTO empleado (documento, nombre, cargo, numero_credencial, vence_credencial, telefono, fecha_ingreso, activo)
VALUES ('1060708090', 'Andrés Felipe Torres', 'ESCOLTA', '10005', DATE '2028-01-15', '3045556677', DATE '2020-11-01', 1);

INSERT INTO empleado (documento, nombre, cargo, numero_credencial, vence_credencial, telefono, fecha_ingreso, activo)
VALUES ('1070809010', 'Diana Carolina Herrera', 'MANEJADOR_CANINO', '10006', DATE '2027-04-30', '3056667788', DATE '2022-08-18', 1);

INSERT INTO empleado (documento, nombre, cargo, numero_credencial, vence_credencial, telefono, fecha_ingreso, activo)
VALUES ('1080910111', 'Sebastián Rojas Peña', 'OPERADOR_MEDIOS', '10007', DATE '2027-08-31', '3067778899', DATE '2024-03-04', 1);

-- Credencial que vence pronto (prueba de alerta)
INSERT INTO empleado (documento, nombre, cargo, numero_credencial, vence_credencial, telefono, fecha_ingreso, activo)
VALUES ('1091011121', 'Camila Andrea Vargas', 'VIGILANTE', '10008', DATE '2026-11-30', '3078889900', DATE '2023-09-01', 1);

-- Credencial vencida (prueba de validación en asignación)
INSERT INTO empleado (documento, nombre, cargo, numero_credencial, vence_credencial, telefono, fecha_ingreso, activo)
VALUES ('1101112131', 'Hernán Darío Castro', 'VIGILANTE', '10009', DATE '2025-12-31', '3089990011', DATE '2018-06-01', 1);

-- Sin teléfono (el campo acepta NULL)
INSERT INTO empleado (documento, nombre, cargo, numero_credencial, vence_credencial, telefono, fecha_ingreso, activo)
VALUES ('1111213141', 'Luisa Fernanda Moreno', 'SUPERVISOR', '10010', DATE '2027-10-31', NULL, DATE '2025-02-14', 0);