ALTER TABLE cliente ADD COLUMN IF NOT EXISTS creado_en    TIMESTAMP;
ALTER TABLE cliente ADD COLUMN IF NOT EXISTS creado_por   VARCHAR(60);
ALTER TABLE cliente ADD COLUMN IF NOT EXISTS modifica_en  TIMESTAMP;
ALTER TABLE cliente ADD COLUMN IF NOT EXISTS modifica_por VARCHAR(60);



INSERT INTO cliente (tipo, documento, nombre_razon_social, representante_legal, correo, telefono, direccion, ciudad, observaciones, activo)
VALUES
    ('PERSONA', '1036547891', 'Andrea Restrepo Ocampo', NULL, 'andrea.restrepo@gmail.com', '3104567812', 'Carrera 43A # 1-50, Apto 502', 'Medellín', 'Cliente recurrente, paga de contado', 1),
    ('PERSONA', '71234567', 'Jorge Iván Ramírez Zapata', NULL, 'jramirez@hotmail.com', '3206789045', 'Calle 10 # 20-30', 'Envigado', NULL, 1),
    ('PERSONA', '43876521', 'Lucía Mesa Gallego', NULL, NULL, '6044446789', 'Transversal 25 # 74-10', 'Bello', 'Requiere escolta los fines de semana', 1),
    ('PERSONA', '1152200334', 'Santiago Gil Londoño', NULL, 'santiago.gil@outlook.com', '3012345678', 'Calle 80 # 52-14', 'Medellín', 'Solicitó capacitación en primeros auxilios', 1),
    ('PERSONA', '98765432', 'Carmen Elisa Pineda', NULL, 'carmen.pineda@yahoo.com', '3168901234', 'Vereda El Ajizal, Finca La Esperanza', 'Rionegro', 'Cuenta suspendida por falta de pago', 0),
    ('EMPRESA', '900123456', 'Conjunto Residencial Altos del Poblado', 'Marta Elena Vásquez', 'administracion@altosdelpoblado.co', '6045551234', 'Carrera 30 # 7-15', 'Medellín', 'Contrato anual de vigilancia 24/7', 1),
    ('EMPRESA', '8301234567', 'Distribuidora La Montaña S.A.S.', 'Hernán Osorio Cárdenas', 'compras@lamontana.com.co', '3157418800', 'Zona Industrial Itagüí, Bodega 12', 'Itagüí', NULL, 1),
    ('EMPRESA', '901456789', 'Clínica San Rafael de Prueba', 'Patricia Londoño', 'gerencia@clinicaprueba.com', '6042335566', 'Calle 50 # 45-20', 'Medellín', 'Pendiente renovación de contrato', 1),
    ('EMPRESA', '800987654', 'Transportes Rápidos del Oriente Ltda.', 'Óscar Duque Muñoz', NULL, '6047770011', 'Autopista Medellín-Bogotá km 22', 'Rionegro', 'Contrato finalizado en 2025', 0),
    ('EMPRESA', '900555111', 'Colegio Bilingüe Santa Teresa', 'Luis Carlos Arango', 'rectoria@santateresa.edu.co', '6043329900', 'Carrera 65 # 38-40', 'Bello', 'Requiere cursos de defensa personal para docentes', 1);
