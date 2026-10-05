-- Script de creación de base de datos y tabla para el Examen Práctico
CREATE DATABASE IF NOT EXISTS red_social CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE red_social;

-- Crear tabla usuarios si no existe
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL
);

-- Datos de prueba iniciales (opcionales)
INSERT INTO usuarios (nombre, apellido_paterno, apellido_materno, correo, usuario, password, fecha_nacimiento)
VALUES 
('Juan', 'Pérez', 'López', 'juan.perez@example.com', 'juanperez', 'password123', '1995-04-12'),
('Juan Carlos', 'Hernández', 'Gómez', 'jc.hernandez@example.com', 'juancarlos', 'password123', '1998-08-23'),
('Juan', 'Rodríguez', 'Sánchez', 'juan.rodriguez@example.com', 'juanr', 'password123', '2000-11-05'),
('Carlos', 'Ramírez', 'Torres', 'carlos.ramirez@example.com', 'carlosr', 'password123', '1997-02-18')
ON DUPLICATE KEY UPDATE id=id;
