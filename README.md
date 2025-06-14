# Guía de instalación y uso del Sistema de Gamificación UJA

Esta guía explica paso a paso cómo configurar y ejecutar tanto el **backend** (Spring Boot) como el **frontend** (React) del proyecto, así como la configuración de la base de datos y el envío de correos.

---

## 1. Requisitos previos

### 1.1 Software
- **Java JDK 17**  
- **Maven 3.6+**  
- **PostgreSQL 15+** (con **pgAdmin 9.1** o superior)  
- **Node.js LTS** (si va a ejecutar el frontend en modo desarrollo)  
- **IDE recomendado**: Visual Studio Code (con extensiones Spring Boot y Java)

### 1.2 Versiones utilizadas en desarrollo
- Spring Boot **3.4.4**  
- Maven wrapper (`./mvnw`)  
- Node.js **16+**, npm **8+**  
- React **19.1.0**, react-scripts **5.0.1**  
- PostgreSQL **15+**, pgAdmin **9.1**

---

## 2. Estructura de carpetas

```
ProyectoGamificacionUJA/
├── gamificacion-uja-backend/       ← Código del backend (Spring Boot)
│   ├── src/
│   ├── pom.xml
│   └── src/main/resources/application.properties
├── gamificacion-uja-frontend/      ← Código del frontend (React + TypeScript)
│   ├── src/
│   ├── package.json
│   └── tailwind.config.js
├── database/
│   └── backup_gamificacion_uja.sql ← Dump SQL de la base de datos
└── ProyectoUJACompleto.code-workspace
```

---

## 3. Configuración de la base de datos

### 3.1 Crear la base de datos y usuario
1. Abra **pgAdmin** y conéctese a su servidor PostgreSQL.  
2. Cree una nueva base de datos llamada:

   ```
   gamificacion_uja
   ```

3. Cree un rol/usuario con nombre:

   ```
   gamificacion_user
   ```

   y contraseña:

   ```
   Gamificacion123!_user
   ```

4. Conceda privilegios al usuario sobre la base:

   ```sql
   GRANT ALL PRIVILEGES ON DATABASE gamificacion_uja TO gamificacion_user;
   ```

### 3.2 Restaurar el backup SQL
Restauración vía terminal:

```bash
psql -U gamificacion_user -h localhost -p 5432 -d gamificacion_uja -f database/backup_gamificacion_uja.sql
```

O importe `backup_gamificacion_uja.sql` con pgAdmin (clic derecho → Restore).

---

## 4. Configuración de envío de correos

En `gamificacion-uja-backend/src/main/resources/application.properties`, ajuste:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=SU_CORREO@gmail.com
spring.mail.password=SU_CONTRASEÑA_DE_APLICACIÓN
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.ssl.trust=smtp.gmail.com
```

### Obtener contraseña de aplicación (Gmail)
1. Active **Verificación en dos pasos** en su cuenta Google.  
2. Vaya a **Seguridad → Contraseñas de aplicaciones**.  
3. Genere una nueva para “Correo” → “Otro (Gamificación UJA)”.  
4. Copie la clave de 16 caracteres (sin espacios) en `spring.mail.password`.

---

## 5. Ejecución del backend

Tienes dos formas de ejecutar el backend:

1. **Usando la terminal (Maven Wrapper)**  
   - Abre terminal en `gamificacion-uja-backend`:  
     ```bash
     cd gamificacion-uja-backend
     ./mvnw spring-boot:run
     ```  
   - Si al ejecutar este comando obtienes algún error o prefieres usar la interfaz gráfica, prueba la opción 2.  
   - La aplicación arrancará en **http://localhost:8080**.

2. **Usando el botón “Run” de la extensión Spring Boot en VS Code**  
   - Abre el proyecto `gamificacion-uja-backend` en VS Code.  
   - Asegúrate de tener instalada la extensión **Spring Boot Extension Pack** de VMware.  
   - En la barra lateral, haz clic en el **icono de Spring Boot**.  
   - Busca la clase principal con el método `main` (por ejemplo `GamificacionujaApplication.java`).  
   - Al lado del nombre de la clase verás un icono de play ▶️; haz clic en él.  
   - La extensión iniciará el backend y mostrará los logs en la pestaña **Debug Console**.  
   - La aplicación estará disponible en **http://localhost:8080**.

---

## 6. Ejecución del frontend

### Ejecución en modo desarrollo (requiere Node.js)
1. Instale [Node.js LTS](https://nodejs.org/).  
2. Abra terminal en `gamificacion-uja-frontend` y ejecute:

   ```bash
   npm install
   npm start
   ```

3. El frontend arrancará en **http://localhost:3000** y se comunicará con el backend en el puerto 8080.

---

## 7. Resumen de comandos

```bash
# Restaurar BD
psql -U gamificacion_user -h localhost -p 5432 -d gamificacion_uja -f database/backup_gamificacion_uja.sql

# Backend
cd gamificacion-uja-backend
./mvnw spring-boot:run

# Frontend (desarrollo)
cd gamificacion-uja-frontend
npm install
npm start
```

---

## 8. Contacto y soporte
Si surge cualquier duda o problema, contacte conmigo:
- **Email:** joseignaciomunoz19@gmail.com
- **Proyecto GitHub:** https://github.com/Joselillo19/GamificacionUjaDefinitivo

---
