# Marketplace Electrodomesticos
 Trabajo Practico para la materia API, un marketplace donde se venden electrodomesticos

## 📁 Estructura del Proyecto
src └── main └── java/com/uade/tpo/ ├── marketplace/ │ ├── controllers/ │ ├── entity/ │ │ └── dto/ │ ├── repository/ │ ├── service/ ├── exceptions/ └── resources/ ├── static/ ├── templates/ ├── application.properties # ⚠️ Ignorado en .gitignore ├── application-example.properties # ✅ Archivo base de ejemplo

## 🧪 Requisitos

- Java JDK 17 o superior
- MySQL Server
- Maven

---

## 🚀 Cómo correr el proyecto

1. **Cloná el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/marketplace.git
   cd marketplace
   
2. **Creá el archivo de configuración: Copiá el archivo de ejemplo y completá tus credenciales**:
   cp src/main/resources/application-example.properties src/main/resources/application.properties

   Luego editá el nuevo archivo y poné tu usuario y contraseña de MySQL:
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña

3. **Levantá la base de datos (MySQL)**:
   Asegurate de tener creada una base llamada mi_base o el nombre que definas en el .properties.

## 🧑‍💻 Autores
Ramiro Abadie, Tobias Choclin., Valentino Ariotti, Patricio Assad
