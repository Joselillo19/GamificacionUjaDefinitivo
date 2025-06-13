-- 1) Corrige los niveles para que tengan rangos válidos y sin solapamiento
UPDATE nivel SET nombre = 'Nivel 1', puntos_minimos = 0, puntos_maximos = 10 WHERE id = 3;
UPDATE nivel SET nombre = 'Nivel 2', puntos_minimos = 11, puntos_maximos = 20 WHERE id = 4;
UPDATE nivel SET nombre = 'Nivel 3', puntos_minimos = 21, puntos_maximos = 40 WHERE id = 2;
UPDATE nivel SET nombre = 'Nivel 4', puntos_minimos = 41, puntos_maximos = 1000 WHERE id = 1;

-- 2) Elimina la vista antigua si existe
DROP VIEW IF EXISTS view_alumnos_completo;

-- 3) Crea la vista con los campos corregidos
CREATE OR REPLACE VIEW view_alumnos_completo AS
SELECT
  a.dni,
  a.primer_apellido,
  a.segundo_apellido,
  a.nombre,
  a.plan_del_alumno,
  a.correo1                          AS correo_electronico,
  COALESCE(ci.cantidad_insignias, 0) AS cantidad_insignias,
  COALESCE(sp.puntos, 0)             AS puntos,
  COALESCE(n.nombre, '')             AS nivel_nombre,
  a.curso
FROM
  alumno a
  LEFT JOIN (
    SELECT
      ai.alumno_dni AS dni,
      COUNT(*) AS cantidad_insignias
    FROM
      alumno_insignia ai
    GROUP BY
      ai.alumno_dni
  ) ci ON a.dni = ci.dni
  LEFT JOIN (
    SELECT
      ai.alumno_dni AS dni,
      SUM(i.valor) AS puntos
    FROM
      alumno_insignia ai
      JOIN insignia i ON ai.insignia_id = i.id
    GROUP BY
      ai.alumno_dni
  ) sp ON a.dni = sp.dni
  LEFT JOIN nivel n ON sp.puntos BETWEEN n.puntos_minimos AND n.puntos_maximos;
