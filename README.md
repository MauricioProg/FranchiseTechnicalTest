# Franchise Management API - Franchise Core

Esta es una API reactiva de alto rendimiento diseñada para la gestión de franquicias, sucursales y productos. El proyecto sigue los principios de **Arquitectura Hexagonal**, asegurando un código desacoplado, testeable y escalable.

## 🚀 Tecnologías y Versiones
* **Java**: 17 (Amazon Corretto)
* **Spring Boot**: 3.4.2
* **Base de Datos**: MongoDB Atlas (Cloud)
* **Gestor de Dependencias**: Gradle 8.5
* **Contenerización**: Docker & Docker Compose

---

## 🛠️ Configuración de Entornos (Perfiles)

El proyecto utiliza **Spring Profiles** para gestionar las configuraciones de conexión:

1.  **Perfil `local`**: Configurado en `src/main/resources/application-local.yml`. Ideal para ejecución desde el IDE. Conecta directamente al clúster de MongoDB Atlas.
2.  **Perfil `docker`**: Configurado en `src/main/resources/application-docker.yml`. Se activa automáticamente en contenedores y utiliza variables de entorno para mayor seguridad.

---

## 🐳 Ejecución con Docker (Modo Recomendado)

Para desplegar la aplicación completa en un contenedor aislado:

1.  Asegúrate de tener Docker Desktop iniciado.
2.  Desde la raíz del proyecto, ejecuta:
    ```bash
    docker compose down && docker compose up --build
    ```
*La API estará disponible en: `http://localhost:8080`*

---

## 💻 Ejecución en Local (Desarrollo)

Si deseas ejecutar la aplicación desde **IntelliJ IDEA**:

1.  Abre **Run/Debug Configurations**.
2.  En el campo **Active Profiles**, escribe: `local`.
3.  Presiona **Run**.

**Desde la terminal (Gradle Wrapper):**
```bash
./gradlew bootRun --args='--spring.profiles.active=local'