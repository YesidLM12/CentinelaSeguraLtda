ALTER TABLE entrenamiento ADD COLUMN IF NOT EXISTS creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE entrenamiento ADD COLUMN IF NOT EXISTS creado_por VARCHAR(60) DEFAULT 'sistema';
ALTER TABLE entrenamiento ADD COLUMN IF NOT EXISTS modifica_en TIMESTAMP;
ALTER TABLE entrenamiento ADD COLUMN IF NOT EXISTS modifica_por VARCHAR(60);

ALTER TABLE entrenamiento ALTER COLUMN hora_inicnio RENAME TO hora_inicio;

INSERT INTO entrenamiento (nombre, tipo, descripcion, contenido, requisitos, intensidad_horas, costo, cupo_maximo,
                           instructor_id, fecha_inicio, fecha_fin, hora_inicio, hora_fin, lugar, estado)
VALUES ('Defensa personal básica para vigilantes', 'DEFENSA_PERSONAL',
        'Técnicas de control, inmovilización y desplazamiento seguro.',
        'Posturas básicas, control de muñeca, derribos controlados, simulacros de reducción.',
        'Certificado médico vigente y ropa deportiva.',
        40, 850000.00, 15,
        (SELECT instructor_id FROM instructor WHERE documento = '2010203040'),
        DATE '2027-01-18', DATE '2027-02-12', TIME '08:00:00', TIME '12:00:00',
        'Gimnasio central', 'PROGRAMADO');

INSERT INTO entrenamiento (nombre, tipo, descripcion, contenido, requisitos, intensidad_horas, costo, cupo_maximo,
                           instructor_id, fecha_inicio, fecha_fin, hora_inicio, hora_fin, lugar, estado)
VALUES ('Manejo seguro de armas de dotación', 'MANEJO_ARMAS',
        'Manejo, desarme, limpieza y tiro de precisión con arma de dotación.',
        'Normas de seguridad en polígono, empuñadura, tiro en posición de pie y de rodillas, evaluación práctica.',
        'Licencia de porte vigente y examen psicofísico aprobado.',
        48, 1200000.00, 8,
        (SELECT instructor_id FROM instructor WHERE documento = '2020304050'),
        DATE '2026-11-02', DATE '2026-11-27', TIME '14:00:00', TIME '18:00:00',
        'Polígono Las Palmas', 'EN_CURSO');

INSERT INTO entrenamiento (nombre, tipo, descripcion, contenido, requisitos, intensidad_horas, costo, cupo_maximo,
                           instructor_id, fecha_inicio, fecha_fin, hora_inicio, hora_fin, lugar, estado)
VALUES ('Primeros auxilios y RCP', 'PRIMEROS_AUXILIOS',
        'Atención inicial de lesiones y reanimación cardiopulmonar.',
        'Valoración de la víctima, hemorragias, fracturas, RCP adulto y uso de desfibrilador.',
        'Ser mayor de 18 años.',
        20, 350000.00, 20,
        (SELECT instructor_id FROM instructor WHERE documento = '2030405060'),
        DATE '2025-08-04', DATE '2025-08-08', TIME '08:00:00', TIME '13:00:00',
        'Sala de capacitación', 'FINALIZADO');

INSERT INTO entrenamiento (nombre, tipo, descripcion, contenido, requisitos, intensidad_horas, costo, cupo_maximo,
                           instructor_id, fecha_inicio, fecha_fin, hora_inicio, hora_fin, lugar, estado)
VALUES ('Protocolos de seguridad privada', 'PROTOCOLOS_SEGURIDAD',
        NULL,
        'Normativa de vigilancia, control de accesos, reporte de novedades y actuación ante emergencias.',
        NULL,
        15, 300000.00, 30,
        (SELECT instructor_id FROM instructor WHERE documento = '2040506070'),
        DATE '2027-03-01', DATE '2027-03-15', TIME '18:00:00', TIME '21:00:00',
        'Auditorio', 'PROGRAMADO');

-- Curso cancelado (prueba de estado)
INSERT INTO entrenamiento (nombre, tipo, descripcion, contenido, requisitos, intensidad_horas, costo, cupo_maximo,
                           instructor_id, fecha_inicio, fecha_fin, hora_inicio, hora_fin, lugar, estado)
VALUES ('Defensa personal avanzada', 'DEFENSA_PERSONAL',
        'Técnicas avanzadas de defensa en espacios reducidos.',
        'Defensa contra armas blancas, desarme y control en el suelo.',
        'Haber aprobado el curso básico de defensa personal.',
        24, 600000.00, 10,
        (SELECT instructor_id FROM instructor WHERE documento = '2010203040'),
        DATE '2026-10-05', DATE '2026-10-09', TIME '08:00:00', TIME '11:00:00',
        'Gimnasio central', 'CANCELADO');