CREATE TABLE usuario(
    usuario_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    documento VARCHAR(15) NOT NULL,
    Correo VARCHAR(120) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    rol VARCHAR(25) NOT NULL,
    activo BIT NOT NULL DEFAULT 1,
    debe_cambiar_password BIT NOT NULL DEFAULT 1,
    intentos_fallidos INT NOT NULL DEFAULT 0,
    bloqueado_hasta TIMESTAMP,
    ultimo_acceso TIMESTAMP,

    CONSTRAINT PK_usuario PRIMARY KEY (usuario_id),
    CONSTRAINT UK_usuario_documento UNIQUE (documento),
    CONSTRAINT UK_usuario_correo UNIQUE (Correo),
    CONSTRAINT CK_usuario_correo CHECK ( correo REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[.][A-Za-z]{2,}$'),
    CONSTRAINT CK_usuario_documento CHECK ( documento REGEXP '^[0-9]+$'),
    CONSTRAINT CK_usuario_rol CHECK ( rol IN ('ADMINISTRADOR','COORD_OPERACIONES', 'COORD_ENTRENAMIENTO', 'ASESOR_COMERCIAL', 'CONTADOR'))
);

CREATE TABLE cliente (
    cliente_id  BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    documento VARCHAR(15)NOT NULL,
    nombre_razon_social VARCHAR(150) NOT NULL,
    representante_legal VARCHAR(100),
    correo VARCHAR(120),
    telefono VARCHAR(15) NOT NULL,
    direccion VARCHAR(150),
    ciudad VARCHAR(60),
    observaciones VARCHAR(300),
    activo BIT NOT NULL DEFAULT 1,

    CONSTRAINT PK_cliente PRIMARY KEY (cliente_id),
    CONSTRAINT UK_cliente_documento UNIQUE (documento),
    CONSTRAINT CK_cliente_correo CHECK ( correo REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[.][A-Za-z]{2,}$'),
    CONSTRAINT CK_cliente_documento CHECK ( documento REGEXP '^[0-9]+$'),
    CONSTRAINT CK_cliente_tipo CHECK ( tipo = 'PERSONA' OR representante_legal IS NOT NULL)
);

CREATE TABLE servicio(
    servicio_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    nombre VARCHAR(120) NOT NULL,
    modalidad VARCHAR(20) NOT NULL,
    medio VARCHAR(15) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    ubicacion VARCHAR(150) NOT NULL,
    tarifa DECIMAL(14,2) NOT NULL,
    duracion_dias INT NOT NULL,
    activo BIT NOT NULL DEFAULT 1,

    CONSTRAINT PK_servicio PRIMARY KEY (servicio_id),
    CONSTRAINT CK_servicio_tarifa CHECK ( tarifa > 0 ),
    CONSTRAINT CK_servicio_duracion_dias CHECK ( duracion_dias > 0 ),
    CONSTRAINT CK_servicio_modalidad CHECK ( modalidad IN ('VIGILANCIA_FIJA', 'PATRULLAJE_MOVIL', 'ESCOLTA', 'MONITOREO') ),
    CONSTRAINT CK_servicio_medio CHECK ( medio IN ('ARMADO', 'SIN_ARMAS', 'TECNOLOGICO', 'CANINO'))
);

CREATE TABLE empleado(
    empleado_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    documento VARCHAR(15) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    cargo VARCHAR(20) NOT NULL,
    numero_credencial VARCHAR(30) NOT NULL,
    vence_credencial DATE NOT NULL,
    telefono VARCHAR(15),
    fecha_ingreso DATE,
    activo BIT NOT NULL DEFAULT 1,

    CONSTRAINT PK_empleado PRIMARY KEY (empleado_id),
    CONSTRAINT UK_empleado_documento UNIQUE (documento),
    CONSTRAINT UK_empleado_numero_credencial UNIQUE (numero_credencial),
    CONSTRAINT CK_empleado_documento CHECK ( documento REGEXP '^[0-9]+$'),
    CONSTRAINT CK_empleado_numero_credencial CHECK ( numero_credencial REGEXP '^[0-9]+$'),
    CONSTRAINT CK_empleado_telefono CHECK ( telefono REGEXP '^[0-9]+$'),
    CONSTRAINT CK_empleado_cargo CHECK ( cargo IN ('VIGILANTE', 'SUPERVISOR', 'ESCOLTA', 'MANEJADOR_CANINO', 'OPERADOR_MEDIOS'))
);

CREATE TABLE equipo(
    equipo_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    tipo VARCHAR(15) NOT NULL,
    descripcion VARCHAR(150) NOT NULL,
    marca VARCHAR(60),
    numero_permiso VARCHAR(40),
    estado VARCHAR(15) NOT NULL DEFAULT 'DISPONIBLE',
    ubicacion VARCHAR(100),
    fecha_adquision DATE,

    CONSTRAINT PK_equipo PRIMARY KEY (equipo_id),
    CONSTRAINT CK_equipo_tipo CHECK ( tipo IN ('ARMA', 'COMUNICACION', ' VEHICULO', 'TECNOLOGICO', 'OTRO')),
    CONSTRAINT CK_equipo_estado CHECK ( estado IN ('DISPONIBLE', 'MANTENIMIENTO', 'BAJA') )
);

CREATE TABLE asignacion_empleado(
    asignacion_empleado_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    servicio_id BIGINT NOT NULL,
    empleado_id BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    observaciones VARCHAR(200),

    CONSTRAINT PK_asignacion_empleado PRIMARY KEY (asignacion_empleado_id),
    CONSTRAINT FK_asignacion_empleado_servicio_id FOREIGN KEY (servicio_id) REFERENCES  servicio (servicio_id),
    CONSTRAINT FK_asignacion_empleado_empleado_id FOREIGN KEY (empleado_id) REFERENCES  empleado (empleado_id),
    CONSTRAINT CK_asignacion_empleado_fecha_fin CHECK ( fecha_fin >= fecha_fin ),
    CONSTRAINT UK_asignacion_empleado UNIQUE (servicio_id,empleado_id,fecha_inicio, hora_inicio)
);

CREATE TABLE instructor (
    instructor_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    documento VARCHAR(15) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100),
    correo VARCHAR(120),
    activo BIT NOT NULL  DEFAULT 1,

    CONSTRAINT PK_instructor PRIMARY KEY (instructor_id),
    CONSTRAINT UK_instructor_documento UNIQUE (documento),
    CONSTRAINT CK_instructor_documento CHECK ( documento REGEXP '^[0-9]+$')
);

CREATE TABLE entrenamiento(
    entrenamiento_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    nombre VARCHAR(120) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    descripcion VARCHAR(500),
    contenido VARCHAR(1000),
    requisitos VARCHAR(500),
    intensidad_horas INT NOT NULL,
    costo DECIMAL(14,2) NOT NULL,
    cupo_maximo INT NOT NULL,
    instructor_id BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    hora_inicnio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    lugar VARCHAR(120),
    estado VARCHAR(15) NOT NULL  DEFAULT 'PROGRAMADO',

    CONSTRAINT PK_entrenamiento PRIMARY KEY (entrenamiento_id),
    CONSTRAINT FK_entrenamiento_instructor_id FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id),
    CONSTRAINT CK_entrenamiento_intensidad_horas CHECK ( intensidad_horas > 0 ),
    CONSTRAINT CK_entrenamiento_cupo_maximo CHECK ( cupo_maximo > 1 ),
    CONSTRAINT CK_entrenamiento_costo CHECK ( costo > 0 ),
    CONSTRAINT CK_entrenamiento_fecha_fin CHECK ( fecha_fin >= fecha_inicio )
);

CREATE TABLE contratacion(
    contratacion_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    numero_confirmacion VARCHAR(20) NOT NULL,
    tipo VARCHAR(15) NOT NULL,
    cliente_id BIGINT NOT NULL,
    servicio_id BIGINT,
    entrenamiento_id BIGINT ,
    fecha_inicio DATE,
    fecha_fin DATE,
    sitio_presentacion VARCHAR(150),
    valor_total DECIMAL(14,2) NOT NULL,
    condiciones VARCHAR(500),
    estado VARCHAR(15) NOT NULL DEFAULT 'PENDIENTE',
    motivo_cancelacion VARCHAR(300),
    fecha_cancelacion TIMESTAMP,

    CONSTRAINT PK_contratación PRIMARY KEY (contratacion_id),
    CONSTRAINT FK_contratacion_cliente_id FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id),
    CONSTRAINT FK_contratacion_servicio_id FOREIGN KEY (servicio_id) REFERENCES servicio (servicio_id),
    CONSTRAINT FK_contratacion_entrenamiento FOREIGN KEY (entrenamiento_id) REFERENCES entrenamiento (entrenamiento_id),
    CONSTRAINT CK_contratacion_servicio_id CHECK ( (tipo = 'SERVICIO'      AND servicio_id IS NOT NULL AND entrenamiento_id IS NULL)
        OR (tipo = 'ENTRENAMIENTO' AND entrenamiento_id IS NOT NULL AND servicio_id IS NULL) ),
    CONSTRAINT  CK_contratacion_entrenamiento CHECK ( tipo = 'ENTRENAMIENTO'
        OR (fecha_inicio IS NOT NULL AND fecha_fin IS NOT NULL AND fecha_fin > fecha_inicio) ),
    CONSTRAINT  CK_contratacion_estado CHECK ( estado <> 'CANCELADA' OR motivo_cancelacion IS NOT NULL)
);

CREATE TABLE pago(
    pago_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    contratacion_id BIGINT NOT NULL,
    monto DECIMAL(14,2) NOT NULL,
    forma_pago VARCHAR(15) NOT NULL,
    fecha_pago DATE NOT NULL,
    referencia VARCHAR(60),
    estado VARCHAR(10) NOT NULL DEFAULT 'VIGENTE',
    motivo_anulacion VARCHAR(300),
    fecha_anulacion TIMESTAMP,
    anulado_por BIGINT,

    CONSTRAINT  PK_pago PRIMARY KEY (pago_id),
    CONSTRAINT FK_pago_contratacion_id FOREIGN KEY (contratacion_id) REFERENCES contratacion (contratacion_id),
    CONSTRAINT FK_pago_anulado_por FOREIGN KEY (anulado_por) REFERENCES usuario (usuario_id),
    CONSTRAINT CK_pago_monto CHECK ( monto > 0 ),
    CONSTRAINT CK_pago_estado CHECK ( estado <> 'ANULADO' OR motivo_anulacion IS NOT NULL )
);

CREATE TABLE parametro(
    clave VARCHAR(60) NOT NULL,
    valor VARCHAR(200) NOT NULL,
    descripcion VARCHAR(200),
    modificado_en TIMESTAMP,
    modificado_por BIGINT NOT NULL,

    CONSTRAINT PK_parametro PRIMARY KEY (clave),
    CONSTRAINT FK_parametro_modificado_por FOREIGN KEY (modificado_por) REFERENCES usuario (usuario_id)
);

