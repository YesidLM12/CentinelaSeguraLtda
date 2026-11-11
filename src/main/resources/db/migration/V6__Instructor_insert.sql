ALTER TABLE instructor ADD COLUMN IF NOT EXISTS creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE instructor ADD COLUMN IF NOT EXISTS creado_por VARCHAR(60) DEFAULT 'sistema';
ALTER TABLE instructor ADD COLUMN IF NOT EXISTS modifica_en TIMESTAMP;
ALTER TABLE instructor ADD COLUMN IF NOT EXISTS modifica_por VARCHAR(60);

INSERT INTO instructor (documento, nombre, especialidad, correo, activo)
VALUES ('2010203040', 'Ricardo Alberto Suárez', 'Defensa personal', 'rsuarez@centinela.com', 1);

INSERT INTO instructor (documento, nombre, especialidad, correo, activo)
VALUES ('2020304050', 'Patricia Elena Ospina', 'Manejo de armas', 'pospina@centinela.com', 1);

INSERT INTO instructor (documento, nombre, especialidad, correo, activo)
VALUES ('2030405060', 'Gustavo Adolfo Mejía', 'Primeros auxilios', 'gmejia@centinela.com', 1);

INSERT INTO instructor (documento, nombre, especialidad, correo, activo)
VALUES ('2040506070', 'Valentina Ríos Cárdenas', 'Protocolos de seguridad', NULL, 1);

-- Instructor inactivo (prueba de filtrado)
INSERT INTO instructor (documento, nombre, especialidad, correo, activo)
VALUES ('2050607080', 'Esteban Quintero', 'Manejo de armas', 'equintero@centinela.com', 0);