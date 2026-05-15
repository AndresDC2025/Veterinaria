# 🐾 Sistema de Gestión Veterinaria (Microservicios)

## 📌 Descripción del proyecto

Este proyecto es un sistema de gestión para una veterinaria basado en arquitectura de microservicios. Su objetivo es administrar de forma distribuida los distintos procesos del sistema, permitiendo la gestión de mascotas, usuarios, citas médicas, tratamientos, inventario, facturación, historial médico, reseñas y autenticación.

Cada microservicio funciona de manera independiente, pero están integrados para compartir información y permitir un flujo completo de atención veterinaria.

---

## 👨‍🎓 Integrantes del proyecto

- Pablo Soto
- Maximiliano Astudillo

---

## ⚙️ Microservicios implementados

### 🐶 Mascotas
Gestión de mascotas registradas en el sistema.

### 👤 Usuarios
Gestión de usuarios del sistema (clientes).

### 🔐 Autenticación
Control de acceso, login y seguridad del sistema mediante roles.

### 📅 Citas
Gestión de citas médicas entre usuario, mascota y veterinario.

### 🩺 Veterinarios
Administración de los profesionales del sistema.

### 💊 Tratamientos
Registro y consulta de tratamientos aplicados a las mascotas.

### 📦 Inventario
Control de insumos médicos y stock disponible.

### 💰 Facturación
Generación de facturas y validación de información de citas e inventario.

### 📋 Historial Médico
Registro completo del historial clínico de cada mascota.

### ⭐ Reseñas
Sistema de opiniones y evaluaciones de los servicios.

---

## 🗄️ Base de datos

Todas las bases de datos se encuentran configuradas y funcionando correctamente en MySQL, garantizando la persistencia de datos en cada microservicio.


---

## 🧪 Ejecución del proyecto

### 1. Requisitos
- Java 17+
- Maven
- MySQL
- Spring Boot

---

### 2. Microservicios activos

El sistema está compuesto por los siguientes microservicios:

- Mascotas Service
- Usuarios Service
- Autenticación Service
- Citas Service
- Veterinarios Service
- Tratamientos Service
- Inventario Service
- Facturación Service
- Historial Médico Service
- Reseñas Service

---

### 3. Puertos utilizados

Cada microservicio se ejecuta en un puerto independiente (configurado según entorno local).

---

### 4. Ejecución

Todos los microservicios pueden ejecutarse de forma independiente y se comunican entre sí mediante APIs REST, manteniendo el flujo completo del sistema veterinario.

---

## 🧠 Nota final

El sistema implementa una arquitectura de microservicios completamente funcional, con comunicación entre servicios, persistencia en base de datos y un flujo integral de gestión veterinaria.
