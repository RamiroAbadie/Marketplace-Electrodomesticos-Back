# Marketplace Electrodomesticos - Backend
 Trabajo Practico para la materia API, un marketplace donde se venden electrodomesticos.
👉 Este proyecto debe usarse junto con el repositorio [Marketplace-Electrodomesticos-Front](https://github.com/RamiroAbadie/Marketplace-Electrodomesticos-Front)

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
```
2. **Creá el archivo de configuración: Copiá el archivo de ejemplo y completá tus credenciales**:
```bash
   cp src/main/resources/application-example.properties src/main/resources/application.properties
```
   *Luego editá el nuevo archivo y poné tu usuario y contraseña de MySQL:*
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña

   *Agregá la clave de seguridad para JWT:*
   jwt.secret=clave_secreta_segura

3. **Levantá la base de datos (MySQL)**:
   Asegurate de tener creada una base llamada mi_base o el nombre que definas en el .properties.

## 🧑‍💻 Autores
Ramiro Abadie, Tobias Choclin., Valentino Ariotti, Patricio Assad
